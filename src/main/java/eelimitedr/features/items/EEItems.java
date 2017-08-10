package eelimitedr.features.items;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import eelimitedr.features.items.armor.EnumArmorType;
import eelimitedr.features.items.armor.ItemDMArmor;
import eelimitedr.features.items.entities.ItemLavaOrb;
import eelimitedr.features.items.entities.ItemMobRandomizer;
import eelimitedr.features.items.entities.ItemWaterOrb;
import eelimitedr.registry.UnlocalizedRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

public class EEItems implements UnlocalizedRegistry
{

	private static Map<String,Boolean> enabledMap = new HashMap<String, Boolean>();

	private static Configuration ItemConfig;

	public static Item Phil;
	public static Item DM;
	public static Item AlchCoal;
	public static Item Mobius;

	public static Item Volc;
	public static Item Ever;
	public static Item Klein;
	public static Item Repair;
	public static Item Cov;
	public static Item Swift;
	public static Item Archangel;
	public static Item IronBand;
	public static Item BlackHoleBand;

	public static Item DMAxe;
	public static Item DMPickaxe;
	public static Item DMShovel;
	public static Item DMHoe;
	public static Item DMSword;
	public static Item DMShears;

	public static Item DMArmor_HEAD;
	public static Item DMArmor_CHEST;
	public static Item DMArmor_LEGS;
	public static Item DMArmor_FEET;

	public static Item lavaOrb,waterOrb,mobRandomizer;
	public static Item dummy = new Item();

	public static void onInit()
	{
		if(isItemEnabled(PhilStoneID))
		{
			Phil = new ItemPhilosophersStone();
		}
		if(isItemEnabled(DarkMatterID))
		{
			DM = new ItemEE(DarkMatterID);
		}
		if(isItemEnabled(AlchID))
		{
			AlchCoal = new ItemEE(AlchID);
		}
		if(isItemEnabled(MobiusID))
		{
			Mobius = new ItemEE(MobiusID);
		}
		if(isItemEnabled(DMArmor_HeadID))
		{
			DMArmor_HEAD = new ItemDMArmor(EnumArmorType.HEAD);
		}
		if(isItemEnabled(DMArmor_ChestID))
		{
			DMArmor_CHEST = new ItemDMArmor(EnumArmorType.CHEST);
		}
		if(isItemEnabled(DMArmor_LegsID))
		{
			DMArmor_LEGS = new ItemDMArmor(EnumArmorType.LEGS);
		}
		if(isItemEnabled(DMArmor_FeetID))
		{
			DMArmor_FEET = new ItemDMArmor(EnumArmorType.FEET);
		}
		if(isItemEnabled(VolcID))
		{
			Volc = new ItemVolcanite();
		}
		if(isItemEnabled(EverID))
		{
			Ever = new ItemEvertide();
		}
		if(isItemEnabled(KleinID))
		{
			Klein = new ItemKleinStar();
		}
		if(isItemEnabled(RepairCharmID))
		{
			Repair = new ItemRepairCharm();
		}
		if(isItemEnabled(CovID))
		{
			Cov = new ItemCovalenceDust();
		}
		if(isItemEnabled(SwiftID))
		{
			Swift = new ItemSwiftWolfRing();
		}
		if(isItemEnabled(ArchangelID))
		{
			Archangel = new ItemArchangelsSmite();
		}
		if(isItemEnabled(IronBandID))
		{
			IronBand = new ItemEE(IronBandID);
		}
		if(isItemEnabled(BlackHoleBandID))
		{
			BlackHoleBand = new ItemBlackHoleRing();
		}
		if(isItemEnabled(DMSwordID))
		{
			DMSword = new ItemDMSword();
		}
		if(isItemEnabled(DMPickaxeID))
		{
			DMPickaxe = new ItemDMPickaxe();
		}
		if(isItemEnabled(DMHoeID))
		{
			DMHoe = new ItemDMHoe();
		}
		if(isItemEnabled(DMShearsID))
		{
			DMShears = new ItemDMShears();
		}
		if(isItemEnabled(DMShovelID))
		{
			DMShovel = new ItemDMShovel();
		}
		if(isItemEnabled(DMAxeID))
		{
			DMAxe = new ItemDMAxe();
		}
		lavaOrb = new ItemLavaOrb();
		waterOrb = new ItemWaterOrb();
		mobRandomizer = new ItemMobRandomizer();
	}

	public static boolean isItemEnabled(String id)
	{
		if(enabledMap.containsKey(id))
		{
			return enabledMap.get(id);
		}
		boolean enabled = ItemConfig.getBoolean(id+"_enabled", "Enabled", true, "Is the item: "+ id +" is enabled?");
		enabledMap.put(id, enabled);
		ItemConfig.save();
		return enabled;
	}

	public static void onPreInit(File configDir)
	{
		File configFile = new File(configDir,"EELimitedR_Items.cfg");
		ItemConfig = new Configuration(configFile);
	}
}
