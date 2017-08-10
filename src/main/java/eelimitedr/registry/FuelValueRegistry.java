package eelimitedr.registry;

import static eelimitedr.utils.StackUtils.*;

import java.util.HashMap;
import java.util.Map;

import eelimitedr.utils.StackUtils;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class FuelValueRegistry
{
	private static Map<ItemStack,Double> map = new HashMap();

	public static void onInit()
	{
		registerFuel(gs(Items.redstone), 18);
		registerFuel(gs(Items.coal),12);
		registerFuel(gs(Items.glowstone_dust), 126);
		registerFuel(gs(Items.gunpowder),189);
	}

	public static void registerFuel(ItemStack stack,double value)
	{
		if(stack != null && getFuelValue(stack) == 0)
		{
			map.put(StackUtils.normalizeStack(stack), value);
		}
	}

	public static double getFuelValue(ItemStack stack)
	{
		for(Map.Entry<ItemStack, Double> entry : map.entrySet())
		{
			if(StackUtils.areStacksEqual(stack, entry.getKey()))
			{
				return entry.getValue();
			}
		}
		return 0;
	}
}
