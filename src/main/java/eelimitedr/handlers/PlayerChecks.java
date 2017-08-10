package eelimitedr.handlers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

import eelimitedr.features.items.EEItems;
import eelimitedr.utils.PlayerUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;

public class PlayerChecks
{
	private static final List<EntityPlayerMP> fireChecks = Lists.newArrayList();

	private static final Logger logger = LogManager.getLogger("EELimited|PlayerChecks");

	public static void update()
	{
		for(int i = 0;i < fireChecks.size();i++)
		{
			EntityPlayerMP player = fireChecks.get(i);

			if (!isPlayerFireImmune(player))
			{
				if (player.isImmuneToFire())
				{
					PlayerUtils.setEntityImmuneToFire(player, false);
				}
				ItemBucket b;
				fireChecks.remove(i);
				logger.debug("Removed " + player.getCommandSenderName() + " from fire checks.");
			}
		}
	}
	public static void addPlayerFireChecks(EntityPlayerMP player)
	{
		if (!fireChecks.contains(player))
		{
			fireChecks.add(player);
		}
	}
	public static void removePlayerFireChecks(EntityPlayerMP player)
	{
		if (fireChecks.contains(player))
		{
			for(int i = 0;i < fireChecks.size();i++)
			{
				if (fireChecks.get(i).equals(player))
				{
					fireChecks.remove(i);
					logger.debug("Removed " + player + " from fire checks.");
					return;
				}
			}
		}
	}
	private static boolean isPlayerFireImmune(EntityPlayer player)
	{
		if (player.capabilities.isCreativeMode)
		{
			return true;
		}
		if(EEItems.Volc == null)
		{
			return false;
		}
		boolean flag = false;
		for (int i = 0; i < 36; i++)
		{
			ItemStack stack = player.inventory.getStackInSlot(i);

			if(stack == null)
			{
				continue;
			}
			Item item = stack.getItem();
			if (item == EEItems.Volc)
			{
				flag = true;
			}
			/*
			if (item instanceof ItemAlchemyBag)
			{
				BagData data = ((ItemAlchemyBag)item).getData(stack, player.worldObj);
				if(data != null && EEProxy.getStackFromInv(data.inventory, new ItemStack(EEItems.Volc)) != null)
				{
					flag = true;
				}
			}*/

		}

		return flag;
	}
}
