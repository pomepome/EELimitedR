package eelimitedr.features;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import eelimitedr.crativetabs.CreativeTabEE;
import eelimitedr.features.entities.EntityLavaProjectile;
import eelimitedr.features.entities.EntityMobRandomizer;
import eelimitedr.features.entities.EntityNovaPrimed;
import eelimitedr.features.entities.EntityWaterProjectile;
import eelimitedr.features.items.EEItems;
import eelimitedr.features.renderers.RenderNovaTNTPrimed;
import eelimitedr.handlers.CommonHandler;
import eelimitedr.proxies.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.config.Configuration;

@Mod(modid="EELimitedR",name="EELimitedR",version="1.0")
public class EELimitedR
{
	private static EELimitedR instance;

	public static final CreativeTabs creativeTabEE = new CreativeTabEE();

	@SidedProxy(serverSide="eelimitedr.proxies.CommonProxy",clientSide="eelimitedr.proxies.ClientProxy")
	public static CommonProxy proxy;

	public static boolean keepPhilInventory;
	public static boolean disableResource;

	public static Achievement getPhil;
    public static Achievement getDM;

	public EELimitedR()
	{
		instance = this;
	}

	int nextID = 0;

	public void registerEntity(Class<? extends Entity> clas,String name)
    {
    	EntityRegistry.registerModEntity(clas, name, nextID, this,500,1,false);
    	nextID++;
    }

	@EventHandler
	public void onPreInit(FMLPreInitializationEvent e)
	{
		proxy.onPreInit(e.getModConfigurationDirectory());

		Configuration config = new Configuration(new File(e.getModConfigurationDirectory(),"EELimitedR.cfg"));
		keepPhilInventory = config.getBoolean("keepPhilInventory", "inventory", false, "Keeps contents after closing philosopher's stone's portable crafting GUI.(if true. they will be saved on savedata.)");
		disableResource = config.getBoolean("disableResource", "general", false, "Should I disable its resource system? (not recommended!)");
		config.save();
	}


	@EventHandler
	public void onInit(FMLInitializationEvent e)
	{
		proxy.onInit();
		registerEntity(EntityLavaProjectile.class,"lava_orb");
		registerEntity(EntityWaterProjectile.class,"water_orb");
		registerEntity(EntityMobRandomizer.class,"randomizer");
		registerEntity(EntityNovaPrimed.class,"NovaPrimed");
		RenderingRegistry.registerEntityRenderingHandler(EntityNovaPrimed.class, new RenderNovaTNTPrimed());

		FMLCommonHandler.instance().bus().register(new CommonHandler());
		Blocks.command_block.setCreativeTab(CreativeTabs.tabRedstone);
	}

	public static EELimitedR getInstance()
	{
		return instance;
	}
	public void registerAchievements()
    {
    	if(EEItems.Phil != null)
    	{
    		List<Achievement> achievements = new ArrayList<Achievement>();
    		AchievementPage page;

    		getPhil = new Achievement("getPhil","getPhil",0,0,EEItems.Phil,AchievementList.portal).registerStat();
    		achievements.add(getPhil);
    		if(EEItems.DM != null)
    		{
        		getDM = new Achievement("getDM","getDM",2,1,EEItems.DM,getPhil).registerStat();
        		achievements.add(getDM);
    		}
    		page = new AchievementPage("EELimited",achievements.toArray(new Achievement[0]));
    		AchievementPage.registerAchievementPage(page);
    	}
    }

}
