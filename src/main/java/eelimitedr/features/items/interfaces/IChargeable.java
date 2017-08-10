package eelimitedr.features.items.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IChargeable
{
	void startCharging(EntityPlayer player, ItemStack stack);
	int getChargeLevel(ItemStack is);
}
