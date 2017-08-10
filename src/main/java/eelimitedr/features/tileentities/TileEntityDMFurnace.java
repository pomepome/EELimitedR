package eelimitedr.features.tileentities;

import cpw.mods.fml.common.registry.GameRegistry;
import eelimitedr.utils.InventoryUtils;
import eelimitedr.utils.StackUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityDMFurnace extends TileEntity implements ISidedInventory
{
	private ItemStack[] inventory = new ItemStack[getSizeInventory()];

	public int progress;
	public int fuelBurnTime;
	public int fuelBurnTimeRemaining;


	@Override
	public int getSizeInventory()
	{
		return 19;
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
		return "D.M. Furnace";
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
		if(slot == 9)
		{
			return TileEntityFurnace.isItemFuel(stack);
		}
		return FurnaceRecipes.smelting().getSmeltingResult(stack) != null && slot < 9;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		if(side < 2)
		{
			return new int[]{0,1,2,3,4,5,6,7,8,9};
		}
		return new int[]{10,11,12,13,14,15,16,17,18};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side)
	{
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side)
	{
		return slot > 9;
	}

	@Override
	public void updateEntity()
	{
		gatherIngredients();

		if(fuelBurnTimeRemaining > 0)
		{
			fuelBurnTimeRemaining--;
		}

		if(worldObj.isRemote)
		{
			return;
		}

		if(fuelBurnTimeRemaining == 0)
		{
			if(inventory[9] != null && canSmelt())
			{
				fuelBurnTimeRemaining = fuelBurnTime = getFuelBurnTime(inventory[9]);
				inventory[9].stackSize--;
				if(inventory[9].stackSize == 0)
				{
					inventory[9] = null;
				}
			}
			else
			{
				fuelBurnTime = 0;
			}
		}
		if(canProcess())
		{
			progress++;
			if(progress >= getWorkTime())
			{
				ItemStack result = getSmeltingResult();
				InventoryUtils.pushStackInInv(this, result, true, 10, 18);

				ItemStack bonus = getBonus(result);
				if(bonus != null)
				{
					InventoryUtils.pushStackInInv(this, bonus, true, 10, 18);
				}
				inventory[0].stackSize--;
				if(inventory[0].stackSize == 0)
				{
					inventory[0] = null;
				}
				progress = 0;
			}
		}
		else
		{
			progress = 0;
		}
		this.markDirty();
	}

	public void readFromNBT(NBTTagCompound nbt)
    {
		super.readFromNBT(nbt);

        inventory = InventoryUtils.readInventoryFromNBT(this, nbt);

        progress = nbt.getInteger("Progress");
        fuelBurnTime = nbt.getInteger("FuelBurnTime");
        fuelBurnTimeRemaining = nbt.getInteger("FuelBurnTimeRemaining");
    }

    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        InventoryUtils.writeInventoryToNBT(this, nbt);

        nbt.setInteger("Progress", progress);
        nbt.setInteger("FuelBurnTime",fuelBurnTime);
        nbt.setInteger("FuelBurnTimeRemaining", fuelBurnTimeRemaining);
    }

	public int getProcessScaled(int size)
    {
		if(getWorkTime() == 0)
		{
			return 0;
		}
        return (int)(this.progress * size) / getWorkTime();
    }
	public int getFuelBurnTimeScaled(int scale)
	{
		if(fuelBurnTime == 0)
		{
			return 0;
		}
		return (int)(this.fuelBurnTimeRemaining * scale) / fuelBurnTime;
	}

	private static int getWorkTime()
	{
		return 5;
	}

	private ItemStack getSmeltingResult()
	{
		if(inventory[0] != null)
		{
			return FurnaceRecipes.smelting().getSmeltingResult(inventory[0]);
		}
		return null;
	}
	private void gatherIngredients()
	{
		if(inventory[0] == null)
		{
			for(int i = 1;i < 9;i++)
			{
				if(inventory[i] != null)
				{
					inventory[0] = inventory[i];
					inventory[i] = null;
					return;
				}
			}
		}
	}
	private boolean canProcess()
	{
		return fuelBurnTimeRemaining > 0 && canSmelt();
	}
	private boolean canSmelt()
	{
		ItemStack result = getSmeltingResult();
		return result != null && InventoryUtils.pushStackInInv(this, result, false, 10, 18) == null;
	}
	private int getFuelBurnTime(ItemStack stack)
	{
		if(stack != null)
		{
			return TileEntityFurnace.getItemBurnTime(stack) / 16;
		}
		return 0;
	}
	private ItemStack getBonus(ItemStack result)
	{
		if(result == null)
		{
			return null;
		}
		if(worldObj.rand.nextInt(4) > 0)
		{
			return StackUtils.changeAmount(result, result.stackSize * 3);
		}
		return null;
	}
}
