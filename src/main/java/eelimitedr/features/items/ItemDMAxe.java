package eelimitedr.features.items;

import eelimitedr.registry.UnlocalizedRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class ItemDMAxe extends ItemEETool
{
    public ItemDMAxe()
    {
        super(UnlocalizedRegistry.DMAxeID, 6);
    }

    public boolean canHarvestBlock(Block par1Block)
    {
        return par1Block.getMaterial() != Material.rock;
    }
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int meta, float minX, float minY, float minZ)
    {
		System.out.println(world.getBlock(x, y, z).getUnlocalizedName());
        //EEProxy.mc.thePlayer.sendChatMessage(EEProxy.getSide(par2EntityPlayer, par4, par5, par6).name());;
    	if(getChargeLevel(stack) > 0 && (isWood(world,x,y,z) || isLeaves(world,x,y,z)))
    	{
    		deforestAOE(world,stack,player, 4);
    	}

        return true;
    }
	@Override
    public float getDigSpeed(ItemStack stack, Block block,int metadata)
    {
        return block.getMaterial() == Material.wood ? 20 : 2.5F;
    }
}