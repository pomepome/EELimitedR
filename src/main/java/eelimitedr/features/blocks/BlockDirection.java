package eelimitedr.features.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import eelimitedr.features.EELimitedR;
import eelimitedr.features.items.EEItems;
import eelimitedr.utils.WorldUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class BlockDirection extends BlockContainer
{
	public BlockDirection(Material p_i45386_1_,String id)
	{
		super(p_i45386_1_);
		this.setCreativeTab(EELimitedR.creativeTabEE).setBlockName(id);
		GameRegistry.registerBlock(this, id);
		GameRegistry.registerTileEntity(createNewTileEntity(null, 0).getClass(), "tileentity_"+id);
	}
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack stack)
	{
		TileEntity tile = world.getTileEntity(x, y, z);

		if (stack.hasTagCompound() && stack.stackTagCompound.getBoolean("EEBlock"))
		{
			stack.stackTagCompound.setInteger("x", x);
			stack.stackTagCompound.setInteger("y", y);
			stack.stackTagCompound.setInteger("z", z);

			tile.readFromNBT(stack.stackTagCompound);
		}

		world.setBlockMetadataWithNotify(x, y, z, WorldUtils.getRelativeOrientation(entityLiving), 2);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int noclue)
	{
		IInventory tile = (IInventory) world.getTileEntity(x, y, z);

		if (tile == null)
		{
			return;
		}

		for (int i = 0; i < tile.getSizeInventory(); i++)
		{
			ItemStack stack = tile.getStackInSlotOnClosing(i);
			if (stack == null)
			{
				continue;
			}

			WorldUtils.spawnEntityItem(world, stack, x, y, z);
		}

		world.func_147453_f(x, y, z, block);
		super.breakBlock(world, x, y, z, block, noclue);
	}

	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
	{
		if (world.isRemote)
		{
			return;
		}

		ItemStack stack = player.getHeldItem();

		if (stack != null && stack.getItem() == EEItems.Phil)
		{
			int orientation = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

			if (orientation == 0)
			{
				world.setBlockMetadataWithNotify(x, y, z, 2, 2);
			}

			if (orientation == 1)
			{
				world.setBlockMetadataWithNotify(x, y, z, 5, 2);
			}

			if (orientation == 2)
			{
				world.setBlockMetadataWithNotify(x, y, z, 3, 2);
			}

			if (orientation == 3)
			{
				world.setBlockMetadataWithNotify(x, y, z, 4, 2);
			}
		}
	}
}
