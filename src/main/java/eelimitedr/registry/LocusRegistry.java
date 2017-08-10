package eelimitedr.registry;

import static eelimitedr.features.items.EEItems.*;
import static eelimitedr.features.blocks.EEBlocks.*;
import static eelimitedr.utils.StackUtils.*;

import java.util.HashMap;
import java.util.Map;

import eelimitedr.utils.StackUtils;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class LocusRegistry
{
	private static Map<ItemStack,Integer> destMap = new HashMap<ItemStack,Integer>();
	private static Map<ItemStack,Integer> fuelMap = new HashMap<ItemStack,Integer>();

	public static void registerDest(ItemStack stack,int objective)
	{
		if(stack == null)
		{
			return;
		}
		if(isDestValid(stack) == 0)
		{
			destMap.put(stack,objective);
		}
	}
	public static int isDestValid(ItemStack stack)
	{
		if(stack == null)
		{
			return 0;
		}
		for(Map.Entry<ItemStack, Integer> entry: destMap.entrySet())
		{
			if(StackUtils.areStacksEqual(stack, entry.getKey()))
			{
				return entry.getValue();
			}
		}
		return 0;
	}
	public static int getFuelValue(ItemStack stack)
	{
		if(stack == null)
		{
			return 0;
		}
		for(Map.Entry<ItemStack, Integer> entry : fuelMap.entrySet())
		{
			ItemStack fuel = entry.getKey();
			if(StackUtils.areStacksEqual(stack, fuel))
			{
				return fuelMap.get(fuel);
			}
		}
		return 0;
	}
	public static void registerFuelValue(ItemStack stack,int value)
	{
		if(getFuelValue(stack) == 0)
		{
			fuelMap.put(stack, value);
		}
	}
	public static void init()
	{
		LocusRegistry.registerFuelValue(gs(AlchCoal), 64);
    	LocusRegistry.registerFuelValue(gs(Blocks.diamond_block), 4096 * 9);
    	LocusRegistry.registerFuelValue(gs(Mobius), 176);
    	LocusRegistry.registerFuelValue(gs(DM),114000 / 4);
    	LocusRegistry.registerDest(gs(DMBlock),114000);
	}
}