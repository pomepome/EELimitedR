package eelimitedr.features.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eelimitedr.features.EELimitedR;
import eelimitedr.features.tileentities.TileEntityDMFurnace;
import eelimitedr.registry.GuiIds;
import eelimitedr.registry.UnlocalizedRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockDMFurnace extends BlockDirection implements UnlocalizedRegistry
{
	private IIcon front,other;
	public BlockDMFurnace()
	{
		super(Material.rock, DMFurnaceID);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TileEntityDMFurnace();
	}
	@Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
		front = reg.registerIcon("eelimitedr:DMFurnace_front");
		other = reg.registerIcon("eelimitedr:DMBlock");
    }

	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
		if(meta == 0 && side == 3)
		{
			return front;
		}
		else if(side == meta)
		{
			return front;
		}
        return other;
    }
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote&&!player.isSneaking())
		{
			player.openGui(EELimitedR.getInstance(), GuiIds.IGUI_DMFURNACE, world, x, y, z);
		}

		return true;
	}

}
