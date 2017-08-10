package eelimitedr.features.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.common.registry.GameRegistry;
import eelimitedr.features.EELimitedR;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;

public class ItemEE extends Item
{
	public ItemEE(String id)
	{
		super();
		setUnlocalizedName(id).setTextureName("eelimitedr:"+id).setCreativeTab(EELimitedR.creativeTabEE);
		GameRegistry.registerItem(this, id);
	}
	protected boolean isWood(Block b)
    {
        if (b == null)
        {
            return false;
        }

        String name = b.getUnlocalizedName();
        return name.toLowerCase().contains("wood") || name.toLowerCase().contains("log");
    }
}
