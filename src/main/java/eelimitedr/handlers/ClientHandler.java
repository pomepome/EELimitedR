package eelimitedr.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import eelimitedr.features.items.EEItems;
import eelimitedr.features.items.ItemChargeable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ClientHandler
{
	@SubscribeEvent
	public void livingUpdate(LivingEvent.LivingUpdateEvent event)
	{
		EntityLivingBase living = event.entityLiving;/*
		if(living instanceof EntityBat && EELimited.noBats)
		{
			living.attackEntityFrom(DamageSource.outOfWorld, 1000);
		}
		*/
	}
	@SubscribeEvent
	public void livingHurt(LivingHurtEvent event)
	{
		EntityLivingBase e = event.entityLiving;
		Entity cause = event.source.getEntity();
		if(e instanceof EntityPlayer&&!(e instanceof EntityTameable)&&event.source == DamageSource.inWall)
		{
			EntityPlayer p = (EntityPlayer)e;
			if(p != null&&p!=null&&p.worldObj != null&&p.worldObj.getBlock((int)p.posX,(int)p.posY,(int)p.posZ).isBed(p.worldObj,(int)p.posX,(int)p.posY,(int)p.posZ, p))
			{
				event.setCanceled(true);
			}
		}
		if(cause instanceof EntityPlayer)
		{
			EntityPlayer p = (EntityPlayer)cause;
			ItemStack current = p.getCurrentEquippedItem();
			if(current != null&&current.getItem() == EEItems.DMSword)
			{
				if(((ItemChargeable)current.getItem()).getChargeLevel(current) > 0)
				e.setFire(30);
			}
		}
	}
}
