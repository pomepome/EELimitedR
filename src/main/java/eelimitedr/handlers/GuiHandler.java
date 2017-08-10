package eelimitedr.handlers;

import cpw.mods.fml.common.network.IGuiHandler;
import eelimitedr.features.tileentities.TileEntityAggregator;
import eelimitedr.features.tileentities.TileEntityAlchChest;
import eelimitedr.features.tileentities.TileEntityDMFurnace;
import eelimitedr.features.tileentities.TileEntityLocus;
import eelimitedr.guis.GuiAggregator;
import eelimitedr.guis.GuiAlchChest;
import eelimitedr.guis.GuiDMFurnace;
import eelimitedr.guis.GuiLocus;
import eelimitedr.guis.GuiPhilWorkbench;
import eelimitedr.guis.containers.ContainerAggregator;
import eelimitedr.guis.containers.ContainerAlchChest;
import eelimitedr.guis.containers.ContainerDMFurnace;
import eelimitedr.guis.containers.ContainerLocus;
import eelimitedr.guis.containers.ContainerPhilWorkbench;
import eelimitedr.registry.GuiIds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler,GuiIds
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == IGUI_AGGREGATOR)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile instanceof TileEntityAggregator)
			{
				return new ContainerAggregator((TileEntityAggregator)tile, player);
			}
		}
		if(ID == IGUI_ALCHCHEST)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile instanceof TileEntityAlchChest)
			{
				return new ContainerAlchChest((TileEntityAlchChest)tile, player.inventory);
			}
		}
		if(ID == IGUI_PHILWORKBENCH)
		{
			return new ContainerPhilWorkbench(player.inventory);
		}
		if(ID == IGUI_DMLOCUS)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile instanceof TileEntityLocus)
			{
				return new ContainerLocus((TileEntityLocus)tile, player.inventory);
			}
		}
		if(ID == IGUI_DMFURNACE)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile instanceof TileEntityDMFurnace)
			{
				return new ContainerDMFurnace((TileEntityDMFurnace)tile, player.inventory);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == IGUI_AGGREGATOR)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile instanceof TileEntityAggregator)
			{
				return new GuiAggregator((TileEntityAggregator)tile, player);
			}
		}
		if(ID == IGUI_ALCHCHEST)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile instanceof TileEntityAlchChest)
			{
				return new GuiAlchChest((TileEntityAlchChest)tile, player.inventory);
			}
		}
		if(ID == IGUI_PHILWORKBENCH)
		{
			return new GuiPhilWorkbench(player.inventory);
		}
		if(ID == IGUI_DMLOCUS)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile instanceof TileEntityLocus)
			{
				return new GuiLocus((TileEntityLocus)tile, player);
			}
		}
		if(ID == IGUI_DMFURNACE)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile instanceof TileEntityDMFurnace)
			{
				return new GuiDMFurnace((TileEntityDMFurnace)tile, player);
			}
		}
		return null;
	}

}
