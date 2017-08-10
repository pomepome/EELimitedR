package eelimitedr.features.items.armor;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eelimitedr.handlers.CommonHandler;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;

public class ItemDMArmor extends ItemArmor implements ISpecialArmor
{

	private final EnumArmorType type;

	public ItemDMArmor(EnumArmorType type)
	{
		super(ArmorMaterial.DIAMOND,0,type.ordinal());
		this.setMaxDamage(100000).setCreativeTab(eelimitedr.features.EELimitedR.creativeTabEE).setUnlocalizedName("DMArmor_"+type.name).setHasSubtypes(false);
		this.type = type;
		GameRegistry.registerItem(this,"DMArmor_"+type.name);
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage,int slot)
	{
		EnumArmorType t = ((ItemDMArmor)armor.getItem()).type;
		if (source.isExplosion())
		{
			return new ArmorProperties(1, 1.0D, 5000000);
		}

		if (t == EnumArmorType.HEAD && source == DamageSource.fall)
		{
			return new ArmorProperties(1, 1.0D, 1000000);
		}

		if (t == EnumArmorType.HEAD || t == EnumArmorType.FEET)
		{
			return new ArmorProperties(0, 0.2D, 25000000);
		}

		return new ArmorProperties(0, 0.3D, 3500000);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot)
	{
		EnumArmorType t = ((ItemDMArmor) armor.getItem()).type;
		return (t == EnumArmorType.HEAD || t == EnumArmorType.FEET) ? 4 : 6;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot)
	{
		stack.damageItem(damage, entity);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons (IIconRegister par1IconRegister)
	{
		String type = this.type.name;

		this.itemIcon = par1IconRegister.registerIcon("eelimitedr:dm_armors/"+type);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture (ItemStack stack, Entity entity, int slot, String type)
	{
		char index = this.type == EnumArmorType.LEGS ? '2' : '1';
		return "eelimitedr:textures/armor/darkmatter_"+index+".png";
	}

	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		if(!world.isRemote && itemStack.getItemDamage() > 0 && CommonHandler.ticksRepair == 0)
		{
			itemStack.setItemDamage(Math.max(0, itemStack.getItemDamage() - 1));
		}
	}
}