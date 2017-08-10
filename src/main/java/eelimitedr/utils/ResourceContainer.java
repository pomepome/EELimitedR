package eelimitedr.utils;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;

public class ResourceContainer
{
	private static Map<String,Double> map = new HashMap();

	public static void set(EntityPlayer player, double resource)
	{
		String name = player.getCommandSenderName();
		if(map.containsKey(name))
		{
			map.remove(name);
		}
		map.put(name, resource);
	}

	public static double get(EntityPlayer player)
	{
		String name = player.getCommandSenderName();
		if(map.containsKey(name))
		{
			return map.get(name);
		}
		else
		{
			set(player, 0);
		}
		return 0;
	}
}
