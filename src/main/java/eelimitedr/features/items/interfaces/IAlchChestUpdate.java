package eelimitedr.features.items.interfaces;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IAlchChestUpdate
{
	public void onUpdateInAlchChest(World world, IInventory inv, int x,int y,int z,ItemStack stack);
}
