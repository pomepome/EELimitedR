package eelimitedr.proxies;

import java.io.File;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.NetworkRegistry;
import eelimitedr.features.EELimitedR;
import eelimitedr.features.KeyRegistry;
import eelimitedr.features.blocks.EEBlocks;
import eelimitedr.features.entities.EntityLavaProjectile;
import eelimitedr.features.entities.EntityMobRandomizer;
import eelimitedr.features.entities.EntityWaterProjectile;
import eelimitedr.features.items.EEItems;
import eelimitedr.handlers.ClientHandler;
import eelimitedr.handlers.GuiHandler;
import eelimitedr.net.PacketHandler;
import eelimitedr.registry.AggregatorRegistry;
import eelimitedr.registry.FuelValueRegistry;
import eelimitedr.registry.LocusRegistry;
import eelimitedr.registry.RecipeRegistry;
import eelimitedr.utils.WorldUtils;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy
{

	@Override
	public void onInit()
	{
		EEItems.onInit();
		EEBlocks.onInit();
		WorldUtils.Init();
		RecipeRegistry.onInit();
		AggregatorRegistry.init();
		FuelValueRegistry.onInit();
		NetworkRegistry.INSTANCE.registerGuiHandler(EELimitedR.getInstance(), new GuiHandler());
		RenderingRegistry.registerEntityRenderingHandler(EntityLavaProjectile.class,new RenderSnowball(EEItems.lavaOrb));
		RenderingRegistry.registerEntityRenderingHandler(EntityWaterProjectile.class,new RenderSnowball(EEItems.waterOrb));
		RenderingRegistry.registerEntityRenderingHandler(EntityMobRandomizer.class,new RenderSnowball(EEItems.mobRandomizer));

		MinecraftForge.EVENT_BUS.register(new ClientHandler());
		PacketHandler.register();
		KeyRegistry.registerKies();
		LocusRegistry.init();
	}

	@Override
	public void onPreInit(File configDir)
	{
		EEItems.onPreInit(configDir);
		EEBlocks.onPreInit(configDir);
	}
}
