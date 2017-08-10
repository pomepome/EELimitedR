package eelimitedr.guis.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class SlotRaw extends Slot
{

	public SlotRaw(IInventory inv, int slot, int x, int y)
	{
		super(inv, slot, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return FurnaceRecipes.smelting().getSmeltingResult(stack) != null;
	}

}
