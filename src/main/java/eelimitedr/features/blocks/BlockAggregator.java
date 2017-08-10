package eelimitedr.features.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eelimitedr.features.EELimitedR;
import eelimitedr.features.tileentities.TileEntityAggregator;
import eelimitedr.registry.GuiIds;
import eelimitedr.registry.UnlocalizedRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockAggregator extends BlockDirection implements UnlocalizedRegistry
{

	private IIcon top,front;

	public BlockAggregator()
	{
		super(Material.rock,AggregatorID);
		this.setBlockTextureName("glowstone");
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TileEntityAggregator();
	}

	@Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
		top = reg.registerIcon("eelimitedr:aggregator_top");
		front = reg.registerIcon("eelimitedr:aggregator_front");
    }

	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
		if(side == 1)
		{
			return top;
		}
		if(meta == 0 && side == 3)
		{
			return front;
		}
		else if(side == meta)
		{
			return front;
		}
        return side == 1 ? top : Blocks.glowstone.getBlockTextureFromSide(0);
    }

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote&&!player.isSneaking())
		{
			player.openGui(EELimitedR.getInstance(), GuiIds.IGUI_AGGREGATOR, world, x, y, z);
		}

		return true;
	}

}
