package eelimitedr.utils;

import static eelimitedr.utils.StackUtils.areStacksEqual;
import static eelimitedr.utils.StackUtils.changeAmount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eelimitedr.features.EELimitedR;
import eelimitedr.features.items.EEItems;
import eelimitedr.registry.FuelValueRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;

public class InventoryUtils
{
	public static int getOpSide(int side)
	{
		return ForgeDirection.getOrientation(side).getOpposite().ordinal();
	}
	public static int[] getSlotsFromSide(ISidedInventory inv,int side)
	{
		return inv.getAccessibleSlotsFromSide(side);
	}
	public static ItemStack[] copyStacks(List<ItemStack> list)
	{
		ItemStack[] arr = new ItemStack[list.size()];
		for(int i = 0;i < arr.length;i++)
		{
			arr[i] = list.get(i);
		}
		return arr;
	}
	public static int[] getAccecibleSlots(IInventory inv, int side)
	{
		if(inv instanceof ISidedInventory)
		{
			return ((ISidedInventory)inv).getAccessibleSlotsFromSide(side);
		}
		int[] slots = new int[inv.getSizeInventory()];
		for(int i = 0;i < slots.length;i++)
		{
			slots[i] = i;
		}
		return slots;
	}
	public static ItemStack pushStackInInv(IInventory inv,ItemStack stack,boolean doUse,int lower_limit,int higher_limit)
	{
		if(inv == null || stack == null)
		{
			return null;
		}
		ItemStack remain = stack.copy();
		if(!doUse)
		{
			ItemStack[] imInv = copyStacks(inv, lower_limit, higher_limit);
			for(int i = 0;i < imInv.length;i++)
			{
				int max = Math.min(inv.getInventoryStackLimit(), imInv[i] != null ? imInv[i].getMaxStackSize() : 64);

				if(imInv[i] == null)
				{
					remain.stackSize -= Math.min(max, remain.stackSize);
				}
				else if(remain.stackSize + imInv[i].stackSize <= max)
				{
					return null;
				}
				else
				{
					remain.stackSize -= Math.min(max - imInv[i].stackSize,remain.stackSize);
				}
				if(remain.stackSize == 0)
				{
					remain = null;
					break;
				}
			}
		}
		else
		{
			if(pushStackInInv(inv, stack, false, lower_limit, higher_limit) != null)
			{
				return pushStackInInv(inv, stack, false, lower_limit, higher_limit);
			}
			for(int i = lower_limit;i <= higher_limit;i++)
			{
				int id = i;
				int max = Math.min(inv.getInventoryStackLimit(), inv.getStackInSlot(id) != null ? inv.getStackInSlot(id).getMaxStackSize() : 64);

				ItemStack content = inv.getStackInSlot(id);

				if(content != null && content.stackSize >= max)
				{
					continue;
				}

				if(content == null)
				{
					int toPut = Math.min(max, remain.stackSize);
					inv.setInventorySlotContents(id, changeAmount(stack,toPut));
					remain.stackSize -= toPut;
				}
				else if(areStacksEqual(content, stack))
				{
					ItemStack copy = content.copy();
					if(content.stackSize + remain.stackSize <= max)
					{
						copy.stackSize += remain.stackSize;
						remain.stackSize = 0;
					}
					else if(copy.stackSize < max)
					{
						int toPut = max - copy.stackSize;
						remain.stackSize -= toPut;
						copy.stackSize = max;
					}
					inv.setInventorySlotContents(id, copy);
				}
				if(remain.stackSize == 0)
				{
					remain = null;
					break;
				}
			}
			inv.markDirty();
		}
		return remain;
	}

	public static ItemStack getStackFromInv(IInventory inv,ItemStack stack)
	{
		if(inv == null || stack == null)
		{
			return null;
		}
		for(int i = 0;i < inv.getSizeInventory();i++)
		{
			ItemStack s = inv.getStackInSlot(i);
			if(areStacksEqual(s, stack))
			{
				return s.copy();
			}
		}
		return null;
	}

	public static boolean useStackFromInv(IInventory inv,ItemStack stack,boolean doUse)
	{
		if(inv == null || stack == null)
		{
			return false;
		}
		
		if(inv instanceof InventoryPlayer &&((InventoryPlayer)inv).player.capabilities.isCreativeMode)
		{
			return true;
		}
		if(!doUse)
		{
			int count = 0;
			for(int i = 0;i < inv.getSizeInventory();i++)
			{
				ItemStack invStack = inv.getStackInSlot(i);
				if(areStacksEqual(stack, invStack))
				{
					count += invStack.stackSize;
				}
			}
			return count >= stack.stackSize;
		}
		else
		{
			if(!useStackFromInv(inv, stack, false))
			{
				return false;
			}

			ItemStack remain = stack.copy();

			for(int i = 0;i < inv.getSizeInventory();i++)
			{
				if(!areStacksEqual(stack, inv.getStackInSlot(i)))
				{
					continue;
				}
				ItemStack invStack = inv.getStackInSlot(i).copy();
				if(invStack.stackSize > remain.stackSize)
				{
					invStack.stackSize -= remain.stackSize;
					remain.stackSize = 0;
				}
				else
				{
					remain.stackSize -= invStack.stackSize;
					invStack = null;
				}
				inv.setInventorySlotContents(i, invStack);
				inv.markDirty();
				if(remain.stackSize == 0)
				{
					remain = null;
					break;
				}
			}

			return remain == null;
		}
	}

	private static boolean isInteger(double d)
	{
		return d == (int)d;
	}

	public static void decrItem(InventoryPlayer inv,Item item,int amount)
    {
    	if(inv.player.capabilities.isCreativeMode)
    	{
    		return;
    	}
    	for(int i = 0;i < amount;i++)
    	{
    		inv.consumeInventoryItem(item);
    	}
    	inv.markDirty();
    	inv.player.inventoryContainer.detectAndSendChanges();
    }

	public static boolean useResource(EntityPlayer player,double amount,boolean doUse)
	{
		return useResource(player, amount, doUse, true);
	}

	public static boolean useResource(EntityPlayer player,double amount,boolean doUse,boolean includeFuels)
    {
		if(player == null)
		{
			return false;
		}

		if(player.capabilities.isCreativeMode || EELimitedR.disableResource)
		{
			return true;
		}

		double stored = ResourceContainer.get(player);
		IInventory inv = player.inventory;

		if(!doUse)
		{
			double actually = stored + amount;


			double count = 0;

			for(int i = 0;i < inv.getSizeInventory();i++)
			{
				if(inv.getStackInSlot(i) == null)
				{
					continue;
				}
				ItemStack stack = inv.getStackInSlot(i).copy();
				if(EEItems.Klein != null && stack.getItem() == EEItems.Klein)
				{
					count += EMCUtils.getEMC(stack);
				}
				if(includeFuels && FuelValueRegistry.getFuelValue(stack) > 0)
				{
					count += FuelValueRegistry.getFuelValue(stack) * stack.stackSize;
				}
			}
			return count >= actually;
		}
		else
		{
			double emc = stored + amount;

			if(!useResource(player, amount, false))
			{
				return false;
			}
			if(EEItems.Klein != null)
			{
				for(int i = 0;i < inv.getSizeInventory();i++)
				{
					if(inv.getStackInSlot(i) == null)
					{
						continue;
					}
					ItemStack stack = inv.getStackInSlot(i).copy();
					if(stack.getItem() == EEItems.Klein)
					{
						double storedEMC = EMCUtils.getEMC(stack);
						if(storedEMC >= emc)
						{
							emc = 0;
							storedEMC -= emc;
						}
						else
						{
							emc -= storedEMC;
							storedEMC = 0;
						}
						EMCUtils.setEMC(stack, storedEMC);
						inv.setInventorySlotContents(i, stack);
						if(emc == 0)
						{
							break;
						}
					}
				}
			}
			if(emc > 0 && includeFuels)
			{
				for(int i = 0;i < inv.getSizeInventory();i++)
				{
					if(inv.getStackInSlot(i) == null)
					{
						continue;
					}
					ItemStack stack = inv.getStackInSlot(i).copy();
					if(FuelValueRegistry.getFuelValue(stack) > 0)
					{
						double d = FuelValueRegistry.getFuelValue(stack);
						int toRemove = (int)(emc / d);
						if(toRemove == 0)
						{
							continue;
						}
						if(stack.stackSize > toRemove)
						{
							stack.stackSize -= toRemove;
							emc -= d * toRemove;
						}
						else
						{
							emc -= d * stack.stackSize;
							stack.stackSize = 0;
						}
						if(stack.stackSize == 0)
						{
							stack = null;
						}
						inv.setInventorySlotContents(i, stack);
						inv.markDirty();
					}
					if(emc == 0)
					{
						break;
					}
				}
			}
			ResourceContainer.set(player, emc);
		}
    	savePlayerInventory(player);
    	return true;
    }
    public static void savePlayerInventory(EntityPlayer p)
    {
    	p.inventory.markDirty();
    	p.inventoryContainer.detectAndSendChanges();
    }

	public static ItemStack pushStackInInv(IInventory inv,ItemStack stack,boolean doUse)
	{
		return pushStackInInv(inv, stack, doUse, 0, inv.getSizeInventory());
	}

	public static ItemStack[] copyStacks(IInventory inv,int lower_limit,int higher_limit)
	{
		ItemStack[] stacks = new ItemStack[higher_limit - lower_limit + 1];
		for(int i = 0;i <= higher_limit - lower_limit;i++)
		{
			ItemStack stack = inv.getStackInSlot(i + lower_limit);
			stacks[i] = stack != null ? stack.copy() : null;
		}
		return stacks;
	}
	public static ItemStack[] sort(IInventory inventory,boolean optSize,int lower_limit,int higher_limit)
    {
    	List<ItemStack> list = new ArrayList<ItemStack>();
    	List<ItemInfo> itemInfos = new ArrayList<ItemInfo>();
    	for(int i = lower_limit;i <= higher_limit;i++)
    	{
    		if(inventory.getStackInSlot(i) == null)
        	{
        		continue;
        	}
    		boolean flag = false;
    		for(int j = 0;j < itemInfos.size();j++)
    		{
    			if(areStacksEqual(itemInfos.get(j).stack,inventory.getStackInSlot(i)))
    			{
    				itemInfos.get(j).amount += inventory.getStackInSlot(i).stackSize;
    				flag = true;
    				break;
    			}
    		}
    		if(!flag)
    		{
    			itemInfos.add(new ItemInfo(inventory.getStackInSlot(i),inventory.getStackInSlot(i).stackSize));
    		}
    	}
    	Collections.sort(itemInfos, new ComparatorItemInfo());
    	for(ItemInfo info : itemInfos)
    	{
    		ItemStack key = info.stack;
    		int limit = Math.min(key.getMaxStackSize(), 64);
    		int size = info.amount;
    		while(size > 0)
    		{
    			if(size > limit)
    			{
    				size -= limit;
    				list.add(new ItemStack(key.getItem(),limit,key.getItemDamage()));
    			}
    			else
    			{
    				list.add(new ItemStack(key.getItem(),size,key.getItemDamage()));
    				size = 0;
    			}
    		}
    	}
    	if(optSize)
    	{
    		if(list.size() < higher_limit - lower_limit)
    		{
    			for(int i = 0;i < higher_limit - list.size() + 1;i++)
    			{
    				list.add(null);
    			}
    		}
    	}
    	return list.toArray(new ItemStack[0]);
    }
	public static void writeInventoryToNBT(IInventory inv,NBTTagCompound nbt)
	{
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			if (inv.getStackInSlot(i) == null)
			{
				continue;
			}

			NBTTagCompound subNBT = new NBTTagCompound();
			subNBT.setByte("Slot", (byte) i);
			inv.getStackInSlot(i).writeToNBT(subNBT);
			list.appendTag(subNBT);
		}

		nbt.setTag("Items", list);
	}
	public static ItemStack[] readInventoryFromNBT(IInventory inv,NBTTagCompound nbt)
	{
		ItemStack[] stacks = new ItemStack[inv.getSizeInventory()];

		if(nbt.hasKey("Items"))
		{
			NBTTagList list = nbt.getTagList("Items", 10);
			stacks = new ItemStack[inv.getSizeInventory()];
			for (int i = 0; i < list.tagCount(); i++)
			{
				NBTTagCompound subNBT = list.getCompoundTagAt(i);
				byte slot = subNBT.getByte("Slot");

				if (slot >= 0 && slot < inv.getSizeInventory())
				{
					stacks[slot] = ItemStack.loadItemStackFromNBT(subNBT);
				}
			}
		}
		return stacks.clone();
	}
}