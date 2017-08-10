package eelimitedr.crativetabs;

import eelimitedr.features.items.EEItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTabEE extends CreativeTabs {

	public CreativeTabEE() {
		super("EELimitedR");
	}

	@Override
	public Item getTabIconItem()
	{
		return EEItems.Phil != null ? EEItems.Phil: Items.redstone;
	}

}
