package eelimitedr.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import eelimitedr.features.items.EEItems;
import eelimitedr.utils.InventoryUtils;
import eelimitedr.utils.RecipeUtils;
import eelimitedr.utils.ResourceContainer;
import eelimitedr.utils.StackUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class CommonHandler
{
	public static int ticksRepair = 0;
	public static int ticks1s = 0;

	private static boolean isInit = false;

	@SubscribeEvent
	public void onPlayerUpdate(TickEvent.PlayerTickEvent e) throws Exception
	{
		if(e.phase != Phase.END)
		{
			return;
		}

		if(!isInit)
		{
			isInit = true;
			addSmeltingExchange();
		}

		PlayerChecks.update();
		if(!e.player.worldObj.isRemote)
		{
			ticksRepair = (ticksRepair + 1) % 5;
			ticks1s = (ticks1s + 1) % 20;
		}

		EntityPlayer player = e.player;

		if(player.capabilities.isCreativeMode)
		{
			ResourceContainer.set(player, 0);
			return;
		}

		if(InventoryUtils.getStackFromInv(player.inventory, StackUtils.gs(EEItems.Swift,1,1)) != null)
		{
			player.capabilities.allowFlying = true;
		}
		else
		{
			player.capabilities.isFlying = false;
			player.capabilities.allowFlying = false;
		}
	}

	private void addSmeltingExchange()
	{
		Map<Object,ItemStack> furnaceRecipes = FurnaceRecipes.smelting().getSmeltingList();
		for(Map.Entry<Object, ItemStack>  entry : furnaceRecipes.entrySet())
		{
			Object src = entry.getKey();
			ItemStack dest = entry.getValue();

			if(dest.stackSize * 7 > dest.getMaxStackSize())
			{
				continue;
			}

			dest.stackSize *= 7;

			List list = new ArrayList();
			for(int i = 0;i < 7;i++)
			{
				list.add(src);
			}
			list.add(StackUtils.gs(Items.coal,1,-1));
			RecipeUtils.addPlainExchange(dest, list.toArray());
		}
	}
}
