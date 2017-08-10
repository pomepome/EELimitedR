package eelimitedr.features.items;

import java.util.List;

import eelimitedr.registry.UnlocalizedRegistry;
import eelimitedr.utils.WorldUtils;
import eelimitedr.utils.enums.EnumSounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDMPickaxe extends ItemEETool
{

    public ItemDMPickaxe()
    {
        super(UnlocalizedRegistry.DMPickaxeID, 9);
    }
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List itemList)
    {
    	ItemStack is = new ItemStack(this,1);
    	is.addEnchantment(Enchantment.fortune,10);
    	itemList.add(is);
    }
    @Override
    public float getDigSpeed(ItemStack stack, Block block,int meta)
    {
    	int base = block.getMaterial() == Material.rock ? 30 : 8;
    	if(getCurrentCharge(stack) > 0)
    	{
    		base *= 2;
    	}
        return base;
    }
    public boolean func_150897_b(Block par1Block)
    {
        return true;
    }
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
    	if(world.isRemote)
    	{
    		return false;
    	}
    	boolean flag = false;
    	Block block = world.getBlock(x, y, z);
    	int meta = world.getBlockMetadata(x, y, z);
    	if(getChargeLevel(stack) == 0 || !isOre(block))
    	{
    		return false;
    	}
    	for(int dx = -10;dx <= 10;dx++)
    	{
    		for(int dy = -10;dy <= 10;dy++)
    		{
    			for(int dz = -10;dz <= 10;dz++)
    			{
    				int mx = x + dx,my = y + dy,mz = z + dz;
					int metadata = world.getBlockMetadata(mx, my, mz);
    				Block target = world.getBlock(mx, my, mz);
    				if(target != null && target == block && meta == metadata)
    				{
    					ItemStack gathered = new ItemStack(block,1,metadata);

    					WorldUtils.spawnEntityItem(world, gathered, player);

    					world.setBlockToAir(mx, my, mz);
    					flag = true;
    				}
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
    private boolean isOre(Block block)
    {
    	if(block == null)
    	{
    		return false;
    	}
    	return block.getLocalizedName().toLowerCase().contains("ore");
    }
}