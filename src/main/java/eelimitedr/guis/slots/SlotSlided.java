package eelimitedr.guis.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotSlided extends Slot
{
	public SlotSlided(IInventory inv, int id, int x, int y)
	{
		super(inv, id, x - 40, y - 70);
	}
}
