package eelimitedr.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerUtils
{
	public static void setEntityImmuneToFire(Entity target,boolean flag)
    {
    	ReflectionUtil.setEntityFireImmunity(target, flag);
    }
    public static void setPlayerSpeed(EntityPlayer target,float value)
    {
    	ReflectionUtil.setPlayerCapabilityWalkspeed(target.capabilities, value);
    }
}
