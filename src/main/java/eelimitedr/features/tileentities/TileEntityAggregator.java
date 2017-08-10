package eelimitedr.features.tileentities;

import static eelimitedr.utils.StackUtils.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eelimitedr.registry.AggregatorRegistry;
import eelimitedr.utils.InventoryUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityAggregator extends TileEntity implements ISidedInventory
{

	private static final Logger log = LogManager.getLogger("EELimitedR:Aggregator");

	public double progress;
	public int savedLightValue;

	private ItemStack[] inventory = new ItemStack[10];

	@Override
	public int getSizeInventory() {
		return 10;
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
		if(slot < 0 || slot >= getSizeInventory())
		{
			return;
		}
		inventory[slot] = stack;
	}

	@Override
	public String getInventoryName()
	{
		return "Aggregator";
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
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	public int getProcessScaled(int size)
    {
        return (int)(this.progress * size) / getWorkTime();
    }
	public int getSunLevelScaled(int scale)
	{
		return (int)(this.getMultiplierLight() * scale);
	}
	private int getWorkTime()
	{
		return 2400;
	}

	public int getSunLevel()
	{
		if(!worldObj.isRemote)
		{
			savedLightValue = worldObj.getBlockLightValue(xCoord, yCoord + 1, zCoord);
		}
		return savedLightValue;
	}
	public double getMultiplierLight()
	{
		return (double)getSunLevel() / 15;
	}

	public boolean canProcess()
	{
		double multiplier = getMultiplierLight() * AggregatorRegistry.get(inventory[4]);
		if(inventory[4] != null && multiplier > 0)
		{
			return InventoryUtils.pushStackInInv(this, gs(Blocks.glowstone), false,5,9) == null;
		}
		return false;
	}

	public void insertOutput()
	{
		if(InventoryUtils.pushStackInInv(this, gs(Blocks.glowstone), true,6,9) != null)
		{
			InventoryUtils.pushStackInInv(this, gs(Blocks.glowstone), true, 5, 5);
		}

	}

	private boolean process()
	{
		if(!canProcess())
		{
			return false;
		}
		inventory[4].stackSize--;
		if(inventory[4].stackSize <= 0)
		{
			inventory[4] = null;
		}
		insertOutput();
		return true;
	}

	private void gatherFuel()
	{
		if(inventory[4] != null)
		{
			return;
		}
		for(int i = 0;i < 4;i++)
		{
			if(inventory[i] != null)
			{
				ItemStack stack = inventory[i].copy();
				inventory[i] = null;
				inventory[4] = stack;
				break;
			}
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		if(slot > 4 && slot <= 9)
		{
			return false;
		}
		return AggregatorRegistry.get(stack) > 0;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if(side == 0 || side == 1)
		{
			return new int[]{0,1,2,3,4};
		}
		return new int[]{5,6,7,8,9};
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

	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if(worldObj.isRemote)
		{
			return;
		}

		gatherFuel();

		if(canProcess())
		{
			progress += getMultiplierLight() * AggregatorRegistry.get(inventory[4]);
			if(progress >= getWorkTime())
			{
				progress = 0;
				process();
			}
		}
		else if(inventory[4] == null)
		{
			progress = 0;
		}

		gatherFuel();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);

		inventory = InventoryUtils.readInventoryFromNBT(this, nbt);

		progress = nbt.getDouble("progress");
		savedLightValue = nbt.getInteger("light");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		nbt.setDouble("progress", progress);
		nbt.setInteger("light",savedLightValue);

		InventoryUtils.writeInventoryToNBT(this, nbt);
	}

}
