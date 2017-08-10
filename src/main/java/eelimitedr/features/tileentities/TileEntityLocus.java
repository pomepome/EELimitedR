package eelimitedr.features.tileentities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eelimitedr.registry.LocusRegistry;
import eelimitedr.utils.InventoryUtils;
import eelimitedr.utils.StackUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLocus extends TileEntity implements ISidedInventory
{
	ItemStack[] inventory;
	public int procTime,currentBurnTime;

	private static final Logger log = LogManager.getLogger("EELimited|Locus");

	public TileEntityLocus()
	{
		inventory = new ItemStack[11];
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if(!canProcess())
		{
			currentBurnTime = 0;
		}
		else
		{
			if(worldObj.isRemote)
			{
				return;
			}
			currentBurnTime++;
			if(currentBurnTime >= getBurnTime())
			{
				currentBurnTime = 0;
				procTime += LocusRegistry.getFuelValue(inventory[8]);
				inventory[8].stackSize--;
				if(inventory[8].stackSize == 0)
				{
					inventory[8] = null;
				}
			}
			if(procTime >= LocusRegistry.isDestValid(inventory[9]))
			{
				insertOutput();
				procTime = 0;
			}
		}
		/*
		if(inventory[0] == null)
		{
			sortInventory();
		}
		*/
		gatherFuel();
		markDirty();
	}
	private int getBurnTime()
	{
		return 10;
	}
	public int getProcessScaled(int size)
    {
		if(inventory[9] == null)
		{
			return 0;
		}
        return (int)(this.procTime * size) / LocusRegistry.isDestValid(inventory[9]);
    }
	public int getBurnTimeScaled(int scale)
	{
		if(currentBurnTime == 0)
		{
			return scale;
		}
		return currentBurnTime * scale / getBurnTime();
	}
	public void sortInventory()
	{
		ItemStack[] sorted = InventoryUtils.sort(this,true,0,8);
		for(int i = 0;i < 8;i++)
		{
			inventory[i] = sorted[i];
		}
	}
	public ItemStack getFirstFuel()
	{
		for(int i = 0;i < 8;i++)
		{
			if(inventory[i] != null)
			{
				ItemStack stack = inventory[i].copy();
				inventory[i] = null;
				return stack;
			}
		}
		return null;
	}
	public void gatherFuel()
	{
		if(inventory[8] != null)
		{
			return;
		}
		ItemStack toReplace = getFirstFuel();
		if(toReplace == null)
		{
			return;
		}
		inventory[8] = toReplace;
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);

		inventory = InventoryUtils.readInventoryFromNBT(this, nbt);

		procTime = nbt.getInteger("procTime");
		currentBurnTime = nbt.getInteger("current");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		nbt.setInteger("procTime", procTime);
		nbt.setInteger("current", currentBurnTime);

		InventoryUtils.writeInventoryToNBT(this, nbt);
	}
	public boolean canProcess()
	{
		if(inventory[8] != null && inventory[9] != null)
		{
			return InventoryUtils.pushStackInInv(this,StackUtils.normalizeStack(inventory[9]),false,10,10) == null;
		}
		return false;
	}
	public void insertOutput()
	{
		System.out.println("Outputting:"+ inventory[9].getDisplayName());
		InventoryUtils.pushStackInInv(this,StackUtils.normalizeStack(inventory[9]),true,10,10);
	}
	private boolean process()
	{
		if(!canProcess())
		{
			return false;
		}
		inventory[8].stackSize--;
		if(inventory[8].stackSize <= 0)
		{
			inventory[8] = null;
		}
		insertOutput();
		return true;
	}
	@Override
	public int getSizeInventory() {
		return 11;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		ItemStack stack = inventory[slot];
		if(stack != null)
		{
			if(stack.stackSize <= amount)
			{
				inventory[slot] = null;
			}
			else
			{
				stack = stack.splitStack(amount);
				if(stack.stackSize == 0)
				{
					inventory[slot] = null;
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		if(inventory[slot] != null)
		{
			ItemStack is = inventory[slot];
			inventory[slot] = null;
			return is;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack content)
	{
		inventory[slot] = content;
	}

	@Override
	public String getInventoryName()
	{
		return "locus";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
	{
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		return LocusRegistry.getFuelValue(stack) > 0;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if(side == 0 || side == 1)
		{
			return new int[]{0,1,2,3,4,5,6,7,8};
		}
		return new int[]{10};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side)
	{
		return side < 2;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side)
	{
		return side > 1;
	}

}
