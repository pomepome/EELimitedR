package eelimitedr.registry;

import java.util.HashMap;
import java.util.Map;

import eelimitedr.features.items.EEItems;
import eelimitedr.utils.FixContainer;
import eelimitedr.utils.StackUtils;
import eelimitedr.utils.enums.EnumFixLevel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RepairCharmRegistry
{
	private static Map<Item,FixContainer> fixMap = new HashMap<Item, FixContainer>();

	public static void registerFix(Item item,EnumFixLevel level,int count)
	{
		if(item == null)
		{
			return;
		}
		if(!fixMap.containsKey(item))
		{
			fixMap.put(item, new FixContainer(level,count));
		}
	}
	public static ItemStack getNewStack(Item item)
	{
		if(item == null || !fixMap.containsKey(item))
		{
			return null;
		}
		FixContainer container = fixMap.get(item);
		return StackUtils.gs(EEItems.Cov,container.numCount,container.fixLevel.getItemDamage());
	}
}
