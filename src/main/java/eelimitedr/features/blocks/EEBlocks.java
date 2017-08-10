package eelimitedr.features.blocks;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import eelimitedr.registry.UnlocalizedRegistry;
import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;

public class EEBlocks implements UnlocalizedRegistry
{
	private static final Map<String,Boolean> enabledMap = new HashMap<String, Boolean>();

	private static Configuration blockConfig;

	public static Block EETorch;
	public static Block Aggregator;
	public static Block AlchChest;
	public static Block Locus;
	public static Block DMBlock;
	public static Block DMFurnace;
	public static Block NovaTNT;

	public static boolean isBlockEnabled(String id)
	{
		if(enabledMap.containsKey(id))
		{
			return enabledMap.get(id);
		}
		boolean enabled = blockConfig.getBoolean(id+"_enabled", "Enabled", true, "Is the block: "+ id +" is enabled?");
		enabledMap.put(id, enabled);
		blockConfig.save();
		return enabled;
	}

	public static void onInit()
	{
		if(isBlockEnabled(EETorchID))
		{
			EETorch = new BlockEETorch();
		}
		if(isBlockEnabled(AggregatorID))
		{
			Aggregator = new BlockAggregator();
		}
		if(isBlockEnabled(AlchChestID))
		{
			AlchChest = new BlockAlchChest();
		}
		if(isBlockEnabled(LocusID))
		{
			Locus = new BlockLocus();
		}
		if(isBlockEnabled(DMBlockID))
		{
			DMBlock = new BlockEE(DMBlockID);
		}
		if(isBlockEnabled(DMFurnaceID))
		{
			DMFurnace = new BlockDMFurnace();
		}
		if(isBlockEnabled(NovaTNTID))
		{
			NovaTNT = new BlockNovaTNT();
		}
	}

	public static void onPreInit(File configDir)
	{
		File configFile = new File(configDir,"EELimitedR_Blocks.cfg");
		blockConfig = new Configuration(configFile);
	}
}
