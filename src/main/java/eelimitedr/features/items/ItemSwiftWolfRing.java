package eelimitedr.features.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eelimitedr.features.items.interfaces.IChargeable;
import eelimitedr.registry.UnlocalizedRegistry;
import eelimitedr.utils.InventoryUtils;
import eelimitedr.utils.WorldUtils;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemSwiftWolfRing extends ItemRing implements UnlocalizedRegistry, IChargeable
{
	private IIcon red,purple;

	public ItemSwiftWolfRing()
	{
		super(SwiftID);
	}

	@Override
	public void doPassive(World world, EntityPlayer player, ItemStack is, boolean isActivated)
	{
		if(player.fallDistance > 0)
		{
			player.fallDistance = 0;
		}
		if(world.isRemote || !player.capabilities.isFlying)
		{
			return;
		}
		int damage = is.getItemDamage();
		if((damage & 0x1) == 1)
		{
			//flying
			if(!InventoryUtils.useResource(player, 0.3, true))
			{
				damage &= 0xe;
			}
		}
		else if(damage > 1)
		{
			if(!InventoryUtils.useResource(player, 0.9, true))
			{
				damage = 1;
			}
			else
			{
				WorldUtils.doInterdiction(world, player.posX, player.posY + 10, player.posZ, false, 10);
			}
		}
	}

	public IIcon getIconFromDamage(int par1)
    {
		if(par1 == 2)
		{
			return red;
		}
		else if(par1 == 3)
		{
			return purple;
		}
        return par1 == 1 ? On : Off;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
    	super.registerIcons(par1IconRegister);
    	red = par1IconRegister.registerIcon("eelimitedr:SwiftRed");
    	purple = par1IconRegister.registerIcon("eelimitedr:SwiftPurple");
    }

    public void onActivated(EntityPlayer player,ItemStack is)
    {
    	int damage = is.getItemDamage();
    	if(damage == 0)
    	{
    		if(InventoryUtils.useResource(player, 18, false))
    		{
    			damage = 1;
    		}
    	}
    	else if(damage == 1)
    	{
    		damage = 0;
    	}
    	else if(damage == 2)
    	{
    		damage = 3;
    	}
    	else
    	{
    		damage = 2;
    	}
    	is.setItemDamage(damage);
    }

	@Override
	public void startCharging(EntityPlayer player, ItemStack stack)
	{
		int damage = stack.getItemDamage();
		if(damage < 2)
		{
			damage += 2;
		}
		else
		{
			damage -= 2;
		}
		stack.setItemDamage(damage);
	}

	@Override
	public int getChargeLevel(ItemStack is) {
		return 0;
	}
}
