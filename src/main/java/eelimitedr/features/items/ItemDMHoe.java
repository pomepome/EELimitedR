package eelimitedr.features.items;

import cpw.mods.fml.common.eventhandler.Event;
import eelimitedr.registry.UnlocalizedRegistry;
import eelimitedr.utils.WorldUtils;
import eelimitedr.utils.enums.EnumSounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;

public class ItemDMHoe extends ItemEETool
{
    public ItemDMHoe()
    {
        super(UnlocalizedRegistry.DMHoeID, 4, 6);
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int x, int y, int z, int side, float par8, float par9, float par10)
    {
    	boolean flag = false;
    	Block i1 = par3World.getBlock(x, y, z);
        if (!par2EntityPlayer.canPlayerEdit(x, y, z, side, par1ItemStack)||!(i1 == Blocks.grass || i1 == Blocks.dirt))
        {
            return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, x, y, z, side, par8, par9, par10);
        }
        else
        {
        	int chargeLvl = getChargeLevel(par1ItemStack);
        	if(chargeLvl == 0)
        	{
        		return useHoe(par1ItemStack, par2EntityPlayer, par3World, x, y, z, side);
        	}
        	int radius = chargeLvl;

        	for(int dx = -radius;dx <= radius;dx++)
        	{
        		for(int dz = -radius;dz <= radius;dz++)
        		{
        			int mx = x + dx;
        			int mz = z + dz;
        			useHoe(par1ItemStack, par2EntityPlayer, par3World, mx, y, mz, side);
        			flag = true;
        		}
        	}
        }

        if(flag)
    	{
    		WorldUtils.playSoundAtPlayer(par2EntityPlayer, EnumSounds.CHARGE.getPath(), 1, 1);
    	}

        return flag;
    }

	private boolean useHoe(ItemStack par1ItemStack,EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,int par6, int par7)
	{
		UseHoeEvent event = new UseHoeEvent(par2EntityPlayer, par1ItemStack, par3World, par4, par5, par6);

		if (MinecraftForge.EVENT_BUS.post(event))
		{
		    return false;
		}

		if (event.getResult() == Event.Result.ALLOW)
		{
		    return true;
		}

		Block i1 = par3World.getBlock(par4, par5, par6);
		Block b1 = par3World.getBlock(par4, par5 + 1, par6);
		if(b1 != null && (b1.getMaterial() == Material.plants || b1.getMaterial() == Material.vine))
		{
			b1.dropBlockAsItem(par3World, par4, par5 + 1, par6, par3World.getBlockMetadata(par4, par5 + 1, par6), 0);
			par3World.setBlock(par4, par5 + 1, par6,Blocks.air);
			par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 1.5F), (double)((float)par6 + 0.5F), b1.stepSound.getBreakSound(), (b1.stepSound.getVolume() + 1.0F) / 2.0F, b1.stepSound.getPitch() * 0.8F);
		}
		boolean air = par3World.isAirBlock(par4, par5 + 1, par6);

		if (par7 != 0 && air && (i1 == Blocks.grass || i1 == Blocks.dirt))
		{
		    Block block = Blocks.farmland;
		    par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), block.stepSound.getStepResourcePath(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);

		    if (par3World.isRemote)
		    {
		        return true;
		    }
		    else
		    {
		        par3World.setBlock(par4, par5, par6, block);
		        return true;
		    }
		}
		else
		{
		    return false;
		}
	}
}