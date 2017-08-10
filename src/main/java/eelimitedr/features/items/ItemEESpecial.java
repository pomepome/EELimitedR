package eelimitedr.features.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;

public class ItemEESpecial extends ItemEE
{
	protected int toolDamage;

	public ItemEESpecial(String id)
	{
		super(id);
		this.setMaxStackSize(1).setContainerItem(this);
	}

	public ItemEESpecial(String id,int damage)
	{
		this(id);
		toolDamage = damage;
	}

	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack p_77630_1_)
    {
        return false;
    }
	@Override
	public Multimap getAttributeModifiers(ItemStack stack)
    {
        return this.getItemAttributeModifier();
    }
	private Multimap getItemAttributeModifier()
    {
        if (toolDamage == 0)
        {
            return HashMultimap.create();
        }

        Multimap multimap = HashMultimap.create();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)this.toolDamage, 0));
        return multimap;
    }

}
