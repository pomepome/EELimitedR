package eelimitedr.features.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eelimitedr.features.EELimitedR;
import eelimitedr.features.tileentities.TileEntityAlchChest;
import eelimitedr.registry.GuiIds;
import eelimitedr.registry.UnlocalizedRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAlchChest extends BlockDirection implements UnlocalizedRegistry
{

	private IIcon top,front,other;

	public BlockAlchChest()
	{
		super(Material.rock, AlchChestID);
		this.setBlockTextureName("obsidian");
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TileEntityAlchChest();
	}

	@Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
		top = reg.registerIcon("eelimitedr:AlchChest_top");
		front = reg.registerIcon("eelimitedr:AlchChest_front");
		other = reg.registerIcon("eelimitedr:AlchChest_side");
    }

	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
		if(side <= 1)
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
		return other;
    }
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote&&!player.isSneaking())
		{
			player.openGui(EELimitedR.getInstance(), GuiIds.IGUI_ALCHCHEST, world, x, y, z);
		}
		return true;
	}
	public int getLightValue(IBlockAccess var1, int var2, int var3, int var4)
    {
		TileEntity tile = var1.getTileEntity(var2, var3, var4);
		if(tile instanceof TileEntityAlchChest)
		{
			return ((TileEntityAlchChest)tile).isEETorchOn ? 15 : 0;
		}
		return 0;
    }
}
