package eelimitedr.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import eelimitedr.features.items.EEItems;
import eelimitedr.utils.InventoryUtils;
import eelimitedr.utils.ResourceContainer;
import eelimitedr.utils.StackUtils;
import net.minecraft.entity.player.EntityPlayer;

public class CommonHandler
{
	public static int ticksRepair = 0;
	public static int ticks1s = 0;

	@SubscribeEvent
	public void onPlayerUpdate(TickEvent.PlayerTickEvent e) throws Exception
	{
		if(e.phase != Phase.END)
		{
			return;
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
}
