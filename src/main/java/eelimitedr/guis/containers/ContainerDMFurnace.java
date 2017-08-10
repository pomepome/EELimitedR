package eelimitedr.guis.containers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eelimitedr.features.tileentities.TileEntityDMFurnace;
import eelimitedr.guis.slots.SlotFuel;
import eelimitedr.guis.slots.SlotRaw;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerDMFurnace extends Container
{

	private TileEntityDMFurnace tile;

	private int lastFuelBurnTimeRemaining,lastFuelBurnTime,lastProgress;

	public ContainerDMFurnace(TileEntityDMFurnace tile,InventoryPlayer player)
	{
		this.tile = tile;
		tile.openInventory();

		// Main ingredient slot
		this.addSlotToContainer(new SlotRaw(tile, 0, 56,17));

		// Ingredient spare slots
		for(int j = 0;j < 4;j++)
		{
			for(int i = 0;i < 2;i++)
			{
				this.addSlotToContainer(new SlotRaw(tile, j * 2 + i + 1,20 + i * 18, 8 + 18 * j));
			}
		}

		//Fuel slot
		this.addSlotToContainer(new SlotFuel(tile, 9, 56, 53));

		//Main result slot
		this.addSlotToContainer(new SlotFurnace(player.player,tile, 10, 116,35));

		//Sub result slot
		for(int i = 0;i < 4;i++)
		{
			for(int j = 0;j < 2;j++)
			{
				this.addSlotToContainer(new SlotFurnace(player.player,tile, 11 + i * 2 + j, 138 + j * 18, 8 + i * 18));
			}
		}

		//Player Inventory
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		//Player Hotbar
		for (int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return tile.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex)
	{
		Slot slot = this.getSlot(slotIndex);

		if (slot == null || !slot.getHasStack())
		{
			return null;
		}

		ItemStack stack = slot.getStack();
		ItemStack newStack = stack.copy();

		if(slotIndex < 19)
		{
			if(!this.mergeItemStack(stack, 19, 54, false))
			{
				return null;
			}
		}
		else if(FurnaceRecipes.smelting().getSmeltingResult(newStack) != null)
		{
			if(!this.mergeItemStack(stack, 0, 1, false))
			{
				if(!this.mergeItemStack(stack, 1, 9, false))
				{
					return null;
				}
			}
		}
		else if(TileEntityFurnace.isItemFuel(stack))
		{
			if(!this.mergeItemStack(stack, 9, 10, false))
			{
				return null;
			}
		}
		else
		{
			return null;
		}

		if (stack.stackSize == 0)
		{
			slot.putStack((ItemStack)null);
		}
		else
		{
			slot.onSlotChanged();
		}

		return newStack;
	}
	public void addCraftingToCrafters(ICrafting p_75132_1_)
    {
        super.addCraftingToCrafters(p_75132_1_);
        p_75132_1_.sendProgressBarUpdate(this, 0, tile.progress);
        p_75132_1_.sendProgressBarUpdate(this, 1, tile.fuelBurnTimeRemaining);
        p_75132_1_.sendProgressBarUpdate(this, 2, tile.fuelBurnTime);
    }
	public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if(lastProgress != tile.progress)
            {
            	icrafting.sendProgressBarUpdate(this, 0, tile.progress);
            }
            if(lastFuelBurnTimeRemaining != tile.fuelBurnTimeRemaining)
            {
            	icrafting.sendProgressBarUpdate(this, 1, tile.fuelBurnTimeRemaining);
            }
            if(lastFuelBurnTime != tile.fuelBurnTime)
            {
            	icrafting.sendProgressBarUpdate(this, 2, tile.fuelBurnTime);
            }
        }
        lastProgress = tile.progress;
        lastFuelBurnTimeRemaining = tile.fuelBurnTimeRemaining;
        lastFuelBurnTime = tile.fuelBurnTime;
    }
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value)
    {
		if(id == 0)
		{
			tile.progress = value;
		}
		else if(id == 1)
		{
			tile.fuelBurnTimeRemaining = value;
		}
		else if(id == 2)
		{
			tile.fuelBurnTime = value;
		}
    }
}
