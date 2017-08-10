package eelimitedr.features.items;

import java.util.List;

import eelimitedr.registry.UnlocalizedRegistry;
import eelimitedr.utils.WorldUtils;
import eelimitedr.utils.enums.EnumSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemDMShovel extends ItemEETool
{
    public ItemDMShovel()
    {
        super(UnlocalizedRegistry.DMShovelID, 4, 9);
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block,int meta)
    {
        return (block.getMaterial() == Material.ground || block.getMaterial() == Material.clay || block.getMaterial() == Material.grass || block.getMaterial() == Material.sand) ? 10 : 2.5F;
    }
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
    	if(world.isRemote || getChargeLevel(stack) == 0)
    	{
    		return false;
    	}
    	boolean flag = false;

    	Block target = world.getBlock(x, y, z);
    	int meta = world.getBlockMetadata(x, y, z);
    	if(target == null || (target.getMaterial() != Material.ground && target.getMaterial() != Material.clay && target.getMaterial() != Material.grass && target.getMaterial() != Material.sand))
    	{
    		return false;
    	}

    	int radius = getChargeLevel(stack);

    	ForgeDirection dir = ForgeDirection.getOrientation(BlockPistonBase.determineOrientation(world, x, y, z, player));

    	for(int dx = -radius;dx <= radius;dx++)
    	{
    		for(int dz = -radius;dz <= radius;dz++)
    		{
    			int mx = x + dx,mz = z + dz;
    			Block block = world.getBlock(mx, y, mz);

    			int metadata = world.getBlockMetadata(mx, y, mz);

    			if(block.getMaterial() == Material.ground || block.getMaterial() == Material.clay || block.getMaterial() == Material.grass || block.getMaterial() == Material.sand)
    			{
    				List<ItemStack> dropList = block.getDrops(world, mx, y, mz, metadata, 0);

    				for(ItemStack gathered : dropList)
    				{
    						WorldUtils.spawnEntityItem(world, gathered, player);
    				}

    				world.setBlockToAir(mx, y, mz);
    				world.markBlockForUpdate(mx, y, mz);
    				flag = true;
    			}
    		}
    	}

    	if(flag)
    	{
    		WorldUtils.playSoundAtPlayer(player, EnumSounds.CHARGE.getPath(), 1, 1);
    	}

    	setCurrentCharge(stack, 0, true);

    	return flag;
    }
}
