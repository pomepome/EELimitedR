package eelimitedr.features.tileentities;

import eelimitedr.features.blocks.EEBlocks;
import eelimitedr.features.items.interfaces.IAlchChestUpdate;
import eelimitedr.utils.InventoryUtils;
import eelimitedr.utils.StackUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityAlchChest extends TileEntity implements IInventory
{
	public boolean prevTorchOn, isEETorchOn;

	private ItemStack[] inventory = new ItemStack[getSizeInventory()];

	@Override
	public int getSizeInventory()
	{
		return 104;
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
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		inventory[slot] = stack;
	}

	@Override
	public String getInventoryName()
	{
		return "Alchemical Chest";
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
	public void openInventory()
	{
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
	{
		return true;
	}
	@Override
	public void updateEntity()
	{
		super.updateEntity();

		isEETorchOn = InventoryUtils.getStackFromInv(this, StackUtils.gs(EEBlocks.EETorch)) != null;

		if(prevTorchOn != isEETorchOn)
		{
			prevTorchOn = isEETorchOn;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}

		for(int i =0;i < getSizeInventory();i++)
		{
			if(inventory[i] != null && inventory[i].getItem() instanceof IAlchChestUpdate)
			{
				((IAlchChestUpdate)inventory[i].getItem()).onUpdateInAlchChest(worldObj, this, xCoord, yCoord, zCoord, inventory[i]);
			}
		}
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);

		inventory = InventoryUtils.readInventoryFromNBT(this, nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		InventoryUtils.writeInventoryToNBT(this, nbt);
	}
}
