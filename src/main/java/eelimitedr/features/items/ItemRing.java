package eelimitedr.features.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eelimitedr.features.items.interfaces.IModeChange;
import eelimitedr.utils.InventoryUtils;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public abstract class ItemRing extends ItemEE implements IModeChange
{
    IIcon On, Off;
    String texture = "";

    public ItemRing(String name)
    {
        super(name);
        texture = name;
        this.setMaxDamage(0).setMaxStackSize(1).setContainerItem(null);
        setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
    }
    @Override
    public final void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        doPassive(par2World, (EntityPlayer)par2World.playerEntities.get(0), par1ItemStack, par1ItemStack.getItemDamage() > 0);
    }

    public IIcon getIconFromDamage(int par1)
    {
        return par1 == 1 ? On : Off;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        On = par1IconRegister.registerIcon("eelimitedr:" + texture + "On");
        Off = par1IconRegister.registerIcon("eelimitedr:" + texture + "Off");
    }
    public abstract void doPassive(World world, EntityPlayer player, ItemStack is,boolean isActivated);
    public void onActivated(EntityPlayer player,ItemStack is)
    {
    	int prevDamage = is.getItemDamage();
    	boolean flag = false;
    	if(useResource())
    	{
    		if(prevDamage == 0)
    		{
    			if(!InventoryUtils.useResource(player,1,false))
    			{
    				flag = false;
    			}
    		}
    		else
    		{
    			flag = true;
    		}
    	}
    	else
    	{
    		flag = true;
    	}
    	if(flag)
    	{
    		is.setItemDamage(1 - prevDamage);
    	}
    }
    protected boolean useResource()
    {
    	return false;
    }
}