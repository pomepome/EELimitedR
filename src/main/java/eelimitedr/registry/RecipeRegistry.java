package eelimitedr.registry;

import static eelimitedr.utils.RecipeUtils.*;
import static eelimitedr.utils.StackUtils.gs;
import static eelimitedr.utils.StackUtils.normalizeStack;

import java.util.ArrayList;
import java.util.List;

import eelimitedr.features.blocks.EEBlocks;
import eelimitedr.features.items.EEItems;
import eelimitedr.recipes.RecipeFix;
import eelimitedr.utils.enums.EnumFixLevel;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class RecipeRegistry
{

	private static final int LAPIS_DAMAGE = 4;

	public static void onInit()
	{
		registerSpecialItemRecipes();
		registerBlockRecipes();
		registerExchangeRecipes();
		registerRestorationRecipes();
		registerFixRecipes();
	}

	private static void registerSpecialItemRecipes()
	{
		addRoteryRecipe(EEItems.Phil, Items.redstone, Items.glowstone_dust, Items.slime_ball);
		addRoteryRecipe(EEItems.DM,Blocks.diamond_block,EEItems.Mobius,EEItems.Phil);
		addRecipe(EEItems.DMPickaxe, "DDD"," I "," I ",'D',EEItems.DM,'I',Items.diamond);
		addRecipe(EEItems.DMShovel, "D","I","I",'D',EEItems.DM,'I',Items.diamond);
		addRecipe(EEItems.DMHoe, "DD "," I "," I ",'D',EEItems.DM,'I',Items.diamond);
		addRecipe(EEItems.DMSword, "D","D","I",'D',EEItems.DM,'I',Items.diamond);
		addRecipe(EEItems.DMAxe, "DD ","DI "," I ",'D',EEItems.DM,'I',Items.diamond);
		addRecipe(EEItems.DMShears, " D","I ",'D',EEItems.DM,'I',Items.diamond);
		addRecipe(EEItems.DMArmor_CHEST,"D D","DDD","DDD",'D',EEItems.DM);
		addRecipe(EEItems.DMArmor_HEAD, "DDD","D D",'D',EEItems.DM);
		addRecipe(EEItems.DMArmor_LEGS,"DDD","D D","D D",'D',EEItems.DM);
		addRecipe(EEItems.DMArmor_FEET,"D D","D D",'D',EEItems.DM);
		addRoteryRecipe(EEBlocks.DMFurnace, EEItems.DM, Blocks.furnace, EEItems.Phil);
		addRecipe(EEBlocks.Aggregator,"GDG","DRD","GFG",'G',Blocks.glowstone,'D',Items.diamond,'R',Items.redstone,'F',Blocks.furnace);
		//TODO Obsidian aggregator recipe here.
		addRecipe(EEBlocks.AlchChest,"LMH","SDS","ICI",'L',gs(EEItems.Cov,1,0),'M',gs(EEItems.Cov,1,1),'H',gs(EEItems.Cov,1,2),'S',Blocks.stone,'D',Items.diamond,'I',Items.iron_ingot,'C',Blocks.chest);
		addRecipe(EEBlocks.AlchChest,"HML","SDS","ICI",'L',gs(EEItems.Cov,1,0),'M',gs(EEItems.Cov,1,1),'H',gs(EEItems.Cov,1,2),'S',Blocks.stone,'D',Items.diamond,'I',Items.iron_ingot,'C',Blocks.chest);
		//TODO Nova catalyst recipe here.
		addRecipe("DDD","DAD","DDD",'D',EEBlocks.DMBlock,'A',EEBlocks.Aggregator);
		//TODO Destruction catalyst recipe here.
		//TODO Hyperkinetic lens recipe here.
		//TODO Catalytic lens recipe here.
		//TODO Soul Stone recipe here.
		addRecipe(EEItems.Ever,"WWW","DDD","WWW",'W',Items.water_bucket,'D',EEItems.DM);
		addRecipe(EEItems.Volc,"LLL","DDD","LLL",'L',Items.lava_bucket,'D',EEItems.DM);
		addRecipe(EEItems.Ever,"IDI","WEW","WIW",'E',EEItems.Ever,'W',Items.water_bucket,'D',Items.diamond,'I',Items.iron_ingot);
		addRecipe(EEItems.Volc,"IDI","LVL","LIL",'V',EEItems.Volc,'L',Items.lava_bucket,'D',Items.diamond,'I',Items.iron_ingot);
		addRecipe(EEItems.Ever,"IDI","EEE","EIE",'E',EEItems.Ever,'D',Items.diamond,'I',Items.iron_ingot);
		addRecipe(EEItems.Volc,"IDI","LLL","LIL",'L',EEItems.Volc,'D',Items.diamond,'I',Items.iron_ingot);
		addRecipe(EEItems.IronBand,"III","ILI","III",'I',Items.iron_ingot,'L',Items.lava_bucket);
		addRecipe(EEItems.IronBand,"III","ILI","III",'I',Items.iron_ingot,'L',EEItems.Volc);
		//TODO Black Hole Band recipe here.
		addRecipe(EEItems.Archangel,"BFB","DID","BFB",'B',Items.bow,'F',Items.feather,'D',EEItems.DM,'I',EEItems.IronBand);
		//TODO Ring of Ignition recipe here.
		addRoteryRecipe(EEItems.Swift,EEItems.DM,Items.feather,EEItems.IronBand);
		//TODO Harvest goddess Band recipe here.
		addRecipe(EEItems.Repair,"LMH","SPS","HML",'L',gs(EEItems.Cov,1,0),'M',gs(EEItems.Cov,1,1),'H',gs(EEItems.Cov,1,2),'S',Items.string,'P',Items.paper);
		addRecipe(EEItems.Repair,"HML","SPS","LMH",'L',gs(EEItems.Cov,1,0),'M',gs(EEItems.Cov,1,1),'H',gs(EEItems.Cov,1,2),'S',Items.string,'P',Items.paper);
		//TODO Watch of Flowing Time recipe here.
		//TODO Gem of Eternal Density recipe here.
		addRecipe(EEBlocks.DMBlock,"DD","DD",'D',EEItems.DM);
		addSRecipe(gs(EEItems.DM,4),EEBlocks.DMBlock);
		addRecipe(gs(EEBlocks.EETorch,2),"RDR","DPD","GGG",'R',Blocks.redstone_torch,'D',Items.diamond,'P',EEItems.Phil,'G',Items.glowstone_dust);
	}

	private static void registerExchangeRecipes()
	{
		addExchangeRecipe(EEItems.Phil, Items.slime_ball,Items.redstone,Items.glowstone_dust);
		addExchangeRecipe(null,gs(Blocks.diamond_block,4),EEItems.DM);
		addNormalExchangeRecipeRing(Blocks.dirt,Blocks.sand,Blocks.clay,gs(Blocks.sand,3),Blocks.cobblestone);
		addNormalExchangeRecipeRing(Blocks.sand, Blocks.dirt,Blocks.clay);
		addExchangeRecipe(null,Blocks.soul_sand,Blocks.sand,Items.glowstone_dust);
		addExchangeRecipe(null,gs(Blocks.soul_sand,4),Blocks.cobblestone,Blocks.glowstone);
		addNormalExchangeRecipeRingLastModified(Blocks.sandstone, Blocks.redstone_ore, gs(Blocks.sand,4), gs(Blocks.sand,8), gs(Blocks.sand,12), gs(Blocks.cobblestone,4));
		addExchangeRecipe(null,gs(Blocks.gravel), Blocks.dirt, Blocks.sand);
		addNormalExchangeRecipeRing(Blocks.gravel, gs(Blocks.sand,2),gs(Blocks.dirt,4),gs(Items.flint,2),gs(Blocks.dirt,8),gs(Blocks.sand,10),gs(Items.flint,4));
		addNormalExchangeRecipeRing(Items.flint, gs(Items.clay_ball,3), gs(Blocks.gravel, 3));
		addNormalExchangeRecipeRing(Blocks.clay, gs(Items.clay_ball,4), Blocks.cobblestone);
		addExchangeRecipe(null,gs(Blocks.dirt), Items.clay_ball,Items.clay_ball);
		addNormalExchangeRecipeRingLastModified(Blocks.cobblestone, Blocks.redstone_ore, gs(Blocks.dirt,4),gs(Blocks.clay,4));
		addExchangeRecipe(null,Blocks.netherrack, Blocks.cobblestone, Items.redstone);
		addExchangeRecipeLastModified(Blocks.cobblestone, Blocks.redstone_ore, Blocks.stone);
		addNormalExchangeRecipeRing(Blocks.redstone_ore, gs(Blocks.cobblestone,8), gs(Items.redstone,9), gs(Blocks.cobblestone,24), Blocks.iron_ore);
		addExchangeRecipe(null,gs(Blocks.sand,2), Blocks.soul_sand);
		addExchangeRecipeLastModified(Blocks.stone, Blocks.redstone_ore, Blocks.netherrack);
		addExchangeRecipeLastModified(gs(Blocks.sand,3), Blocks.ice,Blocks.glass,Blocks.glass);
		addExchangeRecipeLastModified(gs(Blocks.glass,8), gs(Items.dye,1,LAPIS_DAMAGE), Blocks.ice);
		addNormalExchangeRecipeRingLastModified(gs(Items.dye,1,LAPIS_DAMAGE), Blocks.lapis_ore, Blocks.obsidian, gs(Blocks.ice,16));
		addExchangeRecipe(null,gs(Blocks.obsidian,9), Blocks.lapis_block);
		addExchangeRecipe(null,gs(Items.diamond,2), Blocks.lapis_block,Blocks.lapis_block,Blocks.lapis_block,Blocks.lapis_block,Blocks.lapis_block,Blocks.lapis_block,Blocks.lapis_block,Blocks.obsidian);
		addExchangeRecipeLastModified(gs(Items.dye,1,LAPIS_DAMAGE), Blocks.lapis_ore, Blocks.obsidian);
		addNormalExchangeRecipeRing(Blocks.lapis_ore, gs(Items.dye,8,LAPIS_DAMAGE), gs(Blocks.obsidian,16), gs(Items.dye,24,LAPIS_DAMAGE),Items.diamond);
		addNormalExchangeRecipeRing(Items.iron_ingot, gs(Items.glowstone_dust,6), gs(Blocks.glowstone,3), gs(Items.glowstone_dust,18),Items.gold_ingot);
		addNormalExchangeRecipeRing(Items.gold_ingot, gs(Items.iron_ingot,4), gs(Items.iron_ingot,8), gs(Items.iron_ingot,12), Items.diamond);
		addExchangeRecipe(null,gs(Items.gold_ingot,4), Items.diamond);
		addExchangeRecipe(null,Blocks.mossy_cobblestone, Blocks.cobblestone, Items.wheat_seeds);
		addExchangeRecipe(null,Blocks.grass, Blocks.dirt, Items.wheat_seeds);
		addExchangeRecipe(Items.golden_apple, Items.gold_ingot, Items.gold_ingot, Items.gold_ingot, Items.gold_ingot, Items.gold_ingot, Items.gold_ingot, Items.gold_ingot,Items.apple);
		addNormalExchangeRecipeRing(Items.coal, Blocks.cobblestone, gs(Items.redstone,3), gs(Blocks.cobblestone,3),Items.glowstone_dust);
		addNormalExchangeRecipe(Items.sugar, Items.redstone, gs(Items.redstone,2), gs(Items.coal,2), Items.gunpowder, gs(Items.redstone,5), Items.glowstone_dust, gs(Items.redstone,7), gs(Items.gunpowder,2));
		addSRecipe(gs(Items.coal,2),gs(Items.redstone),gs(Items.redstone),gs(Items.redstone),gs(EEItems.Phil));
		addExchangeRecipe(Items.gunpowder, Items.redstone, Items.redstone, Items.redstone, Items.redstone);
		addExchangeRecipe(Items.glowstone_dust,Items.redstone,Items.redstone,Items.redstone,Items.redstone,Items.redstone,Items.redstone);
		addExchangeRecipe(Items.gunpowder, Items.redstone,Items.coal,Items.coal);
		addNormalExchangeRecipeRing(Items.gunpowder, gs(Items.redstone,4), gs(Items.redstone,8), gs(Items.glowstone_dust,2), gs(Items.redstone,16), gs(Items.redstone,20),Blocks.glowstone);
		addNormalExchangeRecipeRing(Items.glowstone_dust, gs(Items.redstone, 6), gs(Items.gunpowder,3));
		addNormalExchangeRecipe(Blocks.glowstone, gs(Items.glowstone_dust,4), gs(Items.glowstone_dust,8), gs(Items.glowstone_dust,12), gs(Blocks.iron_ore,2), gs(Items.glowstone_dust,20), gs(Items.glowstone_dust,24), gs(Items.glowstone_dust,28),Blocks.gold_ore);
		addSRecipe(Items.water_bucket, Blocks.cactus, Blocks.cactus, Items.bucket);
		addSRecipe(Items.water_bucket, EEItems.Ever, Items.bucket);
		addExchangeRecipe(Items.lava_bucket, Items.coal, Items.redstone,Items.bucket);
		addExchangeRecipe(null,Blocks.tnt,Items.gunpowder,Items.gunpowder,Blocks.sand);
		registerAlchCoalRecipes();
		addExchangeRecipe(EEItems.Mobius, EEItems. AlchCoal, EEItems.AlchCoal, EEItems.AlchCoal);
		addExchangeRecipe(null,Blocks.glowstone,EEItems.Mobius);
		addExchangeRecipe(null,Blocks.log, Blocks.cactus,Blocks.cactus);
		addExchangeRecipe(null,gs(Blocks.sapling,2), Blocks.dirt,Blocks.dirt,Items.wheat_seeds);
		registerRotateRecipes();
		for(int i = 0;i < 4;i++)
		{
			addValiantRecipe(gs(Blocks.log,2,i), Items.water_bucket, EEItems.Ever, gs(Blocks.sapling,1,i),Blocks.dirt);
		}
		addPlainExchange(gs(Items.wheat_seeds,2), gs(Blocks.sapling, 1, -1), gs(Blocks.sapling, 1, -1));
		addPlainExchange(gs(Items.wheat_seeds,3), gs(Blocks.sapling, 1, -1), gs(Blocks.sapling, 1, -1), gs(Blocks.sapling, 1, -1));
		for(int i = 0;i < 4;i++)
		{
			addValiantRecipe(gs(Blocks.log,6,i), Items.water_bucket, EEItems.Ever, gs(Blocks.sapling,1,i), gs(Blocks.sapling,1,i), gs(Blocks.sapling,1,i),Blocks.dirt,Blocks.dirt,Blocks.dirt);
		}
		for(int i = 0;i < 4;i++)
		{
			addExchangeRecipe(null,gs(Blocks.log,4,i), gs(Blocks.sapling,1,i), gs(Blocks.sapling,1,i), gs(Blocks.sapling,1,i), gs(Blocks.sapling,1,i));
		}
		addPlainExchange(gs(Items.wheat_seeds,5), gs(Blocks.sapling, 1, -1), gs(Blocks.sapling, 1, -1), gs(Blocks.sapling, 1, -1), gs(Blocks.sapling, 1, -1), gs(Blocks.sapling, 1, -1));
		addPlainExchange(gs(Items.wheat_seeds,6), gs(Blocks.sapling, 1, -1), gs(Blocks.sapling, 1, -1), gs(Blocks.sapling, 1, -1), gs(Blocks.sapling, 1, -1), gs(Blocks.sapling, 1, -1), gs(Blocks.sapling, 1, -1));
		addPlainExchange(gs(Items.wheat_seeds,7), gs(Blocks.sapling, 1, -1), gs(Blocks.sapling, 1, -1), gs(Blocks.sapling, 1, -1), gs(Blocks.sapling, 1, -1), gs(Blocks.sapling, 1, -1), gs(Blocks.sapling, 1, -1), gs(Blocks.sapling, 1, -1));
		addExchangeRecipe(null,Blocks.red_mushroom, Items.glowstone_dust,Blocks.red_flower);
		addExchangeRecipe(null,Blocks.brown_mushroom, Items.glowstone_dust,Blocks.yellow_flower);
		addExchangeRecipe(Items.apple, Items.redstone, Blocks.red_flower);
		registerSlimeballRecipes();
		addExchangeRecipe(EEItems.dummy, Blocks.web, Items.slime_ball, Items.string, Items.string);
		addPlainExchange(gs(Items.wheat_seeds, 3),Items.slime_ball, Items.wheat_seeds);
		addPlainExchange(gs(Items.egg, 2),Items.slime_ball, Items.feather);
		addPlainExchange(gs(Items.fish, 2), Items.slime_ball, Items.fish);
		addPlainExchange(gs(Items.porkchop, 2), Items.slime_ball, Items.porkchop);
		addValiantRecipe(gs(Items.milk_bucket), Items.water_bucket, EEItems.Ever, Items.bucket, gs(Items.dye,1,15));
		registerRedFlowerRecipes();
		registerYellowFlowerRecipes();
		addExchangeRecipe(null, Blocks.gold_block, Blocks.iron_block, Blocks.iron_block, Blocks.iron_block, Blocks.iron_block);
		addExchangeRecipe(null, Blocks.diamond_block, Blocks.gold_block, Blocks.gold_block, Blocks.gold_block, Blocks.gold_block);
		addExchangeRecipe(null, gs(Items.string,5),Items.feather);
		addExchangeRecipe(null, Items.bone, gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1),Items.stick);
		addExchangeRecipe(null, Items.bone, Items.leather, Items.stick);
		addExchangeRecipe(null, gs(Items.string, 8), Items.bone);

		addPlainExchange(gs(Items.string,4), gs(Blocks.wool, 1, -1));
		addPlainExchange(gs(Items.leather), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1));
		for(int i = 0;i < 16;i++)
		{
			int dye_damage = 0xf & (~i);
			addPlainExchange(gs(Items.dye, 2, dye_damage), gs(Blocks.wool, 1, i), gs(Blocks.wool, 1, i), gs(Blocks.wool, 1, i));
		}
		addPlainExchange(gs(Items.leather,2), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1));
		addPlainExchange(gs(Items.string,20), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1));
		addPlainExchange(gs(Items.leather,3), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1));
		addPlainExchange(gs(Items.string,28), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1));
		addPlainExchange(gs(Items.leather,4), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1), gs(Blocks.wool, 1, -1));

		addExchangeRecipe(null, gs(Items.string, 8), Items.leather);

	}

	private static void registerRestorationRecipes()
	{
		addPlainExchange(Items.reeds, Items.paper);
		addPlainExchange(gs(Items.clay_ball,4),Blocks.clay);
		addPlainExchange(gs(Items.paper,3), Items.book);
		addPlainExchange(gs(Items.stick,3), gs(Blocks.fence,1,-1));
		addPlainExchange(Blocks.cobblestone, gs(Blocks.stone_slab,3));
		addPlainExchange(Blocks.stone,Blocks.stone_slab);
		addPlainExchange(Blocks.sandstone,gs(Blocks.stone_slab,1,1));
		for(int i = 0;i < 6;i++)
		{
			addPlainExchange(gs(Blocks.planks,1,i), gs(Blocks.wooden_slab,1,i));
		}
		addPlainExchange(gs(Items.stick,7), Blocks.ladder, Blocks.ladder);
		addPlainExchange(gs(Blocks.planks,6), Blocks.wooden_door);
		addPlainExchange(gs(Blocks.planks), Blocks.trapdoor);
		addPlainExchange(gs(Items.iron_ingot), Blocks.iron_door);
		addPlainExchange(gs(Items.stick,25),Items.sign);
		addPlainExchange(Items.reeds, Items.sugar);
		addPlainExchange(gs(Blocks.log,2), Blocks.planks, Blocks.planks, Blocks.planks, Blocks.planks, Blocks.planks, Blocks.planks, Blocks.planks, Blocks.planks);
		addPlainExchange(gs(Blocks.planks,4), Items.stick, Items.stick, Items.stick, Items.stick, Items.stick, Items.stick, Items.stick, Items.stick);
		addPlainExchange(gs(Items.coal,2), Blocks.torch, Blocks.torch, Blocks.torch, Blocks.torch, Blocks.torch, Blocks.torch, Blocks.torch, Blocks.torch);
		addPlainExchange(gs(Items.stick,3), Items.bowl, Items.bowl);
		addPlainExchange(gs(Items.iron_ingot,3), Blocks.rail, Blocks.rail, Blocks.rail, Blocks.rail, Blocks.rail, Blocks.rail, Blocks.rail, Blocks.rail);
		addPlainExchange(gs(Items.gold_ingot,8), Blocks.golden_rail, Blocks.golden_rail, Blocks.golden_rail, Blocks.golden_rail, Blocks.golden_rail, Blocks.golden_rail, Blocks.golden_rail, Blocks.golden_rail);
		addPlainExchange(gs(Items.iron_ingot,8), Blocks.detector_rail, Blocks.detector_rail, Blocks.detector_rail, Blocks.detector_rail, Blocks.detector_rail, Blocks.detector_rail, Blocks.detector_rail, Blocks.detector_rail);
		addPlainExchange(gs(Items.iron_ingot,5), Items.minecart);
		addPlainExchange(Blocks.pumpkin, Blocks.lit_pumpkin);
		addPlainExchange(gs(Items.iron_ingot,5), Items.chest_minecart);
		addPlainExchange(gs(Items.iron_ingot), Items.furnace_minecart);
		addPlainExchange(gs(Blocks.planks,5), Items.boat);
		addPlainExchange(gs(Items.iron_ingot,3), Items.bucket);
		addPlainExchange(Items.iron_ingot, Items.flint_and_steel);
		addPlainExchange(gs(Items.wheat,3), Items.bread);
		addPlainExchange(Items.wheat_seeds, Items.wheat);
		addPlainExchange(gs(Blocks.planks,2), Blocks.oak_stairs, Blocks.oak_stairs);
		addPlainExchange(gs(Blocks.cobblestone,2), Blocks.stone_stairs,Blocks.stone_stairs);
		addPlainExchange(gs(Blocks.planks,2), Items.painting);
		addPlainExchange(gs(Items.stick,3), Items.fishing_rod);
		addPlainExchange(Blocks.cobblestone, Blocks.lever);
		addPlainExchange(Items.redstone, Blocks.redstone_torch);
		addPlainExchange(gs(Items.redstone,3), Items.repeater);
		addPlainExchange(gs(Items.gold_ingot), Items.clock);
		addPlainExchange(gs(Items.iron_ingot,4), Items.compass);
		addPlainExchange(Items.compass, Items.map);
		addPlainExchange(gs(Blocks.stone,2), Blocks.stone_button);
		addPlainExchange(gs(Blocks.stone,2), Blocks.stone_pressure_plate);
		addPlainExchange(gs(Blocks.planks,2), Blocks.wooden_pressure_plate);
		addPlainExchange(gs(Blocks.cobblestone,7), Blocks.dispenser);
		addPlainExchange(gs(Blocks.wool,3), Items.bed);
		addPlainExchange(Blocks.planks, Items.wooden_shovel);
		addPlainExchange(gs(Blocks.planks,2), Items.wooden_hoe);
		addPlainExchange(gs(Blocks.planks,2), Items.wooden_sword);
		addPlainExchange(gs(Blocks.planks,3), Items.wooden_axe);
		addPlainExchange(gs(Blocks.planks,3), Items.wooden_pickaxe);
		addPlainExchange(Blocks.cobblestone, Items.stone_shovel);
		addPlainExchange(gs(Blocks.cobblestone,2), Items.stone_hoe);
		addPlainExchange(gs(Blocks.cobblestone,2), Items.stone_sword);
		addPlainExchange(gs(Blocks.cobblestone,3), Items.stone_axe);
		addPlainExchange(gs(Blocks.cobblestone,3), Items.stone_pickaxe);
		addPlainExchange(gs(Items.iron_ingot,1), Items.iron_shovel);
		addPlainExchange(gs(Items.iron_ingot,2), Items.iron_hoe);
		addPlainExchange(gs(Items.iron_ingot,2), Items.iron_sword);
		addPlainExchange(gs(Items.iron_ingot,3), Items.iron_axe);
		addPlainExchange(gs(Items.iron_ingot,3), Items.iron_pickaxe);
		addPlainExchange(gs(Items.gold_ingot,1), Items.golden_shovel);
		addPlainExchange(gs(Items.gold_ingot,2), Items.golden_hoe);
		addPlainExchange(gs(Items.gold_ingot,2), Items.golden_sword);
		addPlainExchange(gs(Items.gold_ingot,3), Items.golden_axe);
		addPlainExchange(gs(Items.gold_ingot,3), Items.golden_pickaxe);
		addPlainExchange(gs(Items.diamond,1), Items.diamond_shovel);
		addPlainExchange(gs(Items.diamond,2), Items.diamond_hoe);
		addPlainExchange(gs(Items.diamond,2), Items.diamond_sword);
		addPlainExchange(gs(Items.diamond,3), Items.diamond_axe);
		addPlainExchange(gs(Items.diamond,3), Items.diamond_pickaxe);
		addPlainExchange(gs(Items.leather,5), Items.leather_helmet);
		addPlainExchange(gs(Items.leather,8), Items.leather_chestplate);
		addPlainExchange(gs(Items.leather,7), Items.leather_leggings);
		addPlainExchange(gs(Items.leather,4), Items.leather_boots);
		addPlainExchange(gs(Items.iron_ingot,5), Items.iron_helmet);
		addPlainExchange(gs(Items.iron_ingot,8), Items.iron_chestplate);
		addPlainExchange(gs(Items.iron_ingot,7), Items.iron_leggings);
		addPlainExchange(gs(Items.iron_ingot,4), Items.iron_boots);
		addPlainExchange(gs(Items.gold_ingot,5), Items.golden_helmet);
		addPlainExchange(gs(Items.gold_ingot,8), Items.golden_chestplate);
		addPlainExchange(gs(Items.gold_ingot,7), Items.golden_leggings);
		addPlainExchange(gs(Items.gold_ingot,4), Items.golden_boots);
		addPlainExchange(gs(Items.diamond,5), Items.diamond_helmet);
		addPlainExchange(gs(Items.diamond,8), Items.diamond_chestplate);
		addPlainExchange(gs(Items.diamond,7), Items.diamond_leggings);
		addPlainExchange(gs(Items.diamond,4), Items.diamond_boots);
	}

	private static void registerBlockRecipes()
	{

	}

	private static void registerFixRecipes()
	{
		registerFixRecipe(Items.wooden_shovel, EnumFixLevel.LOW, 1);
	}

	private static void registerFixRecipe(Item item,EnumFixLevel level,int count)
	{

		if(item == null || EEItems.Cov == null || level == null)
		{
			return;
		}

		RepairCharmRegistry.registerFix(item, level, count);

		List ingrList = new ArrayList();
		ingrList.add(gs(item,1,-1));
		for(int i = 0; i < count;i++)
		{
			ingrList.add(normalizeStack(RepairCharmRegistry.getNewStack(item)));
		}
		RecipeFix fix = new RecipeFix(gs(item), ingrList);
		addRecipe(fix);
	}

	private static void registerRotateRecipes()
	{
		if(EEItems.Phil != null)
		{
			registerRotationRecipe(Blocks.sapling, EEItems.Phil, 4);
		}
	}

	private static void registerRotationRecipe(Object obj,Item alchCatal,int max)
	{
		for(int i = 0;i < max;i++)
		{
			int next = (i + 1) % max;
			addSRecipe(gs(obj,1,next),gs(obj,1,i),alchCatal);
		}
	}

	private static void registerAlchCoalRecipes()
	{
		if(EEItems.Phil != null)
		{
			registerAlchCoalRecipe(EEItems.Phil);
		}
	}
	private static void registerRedFlowerRecipes()
	{
		if(EEItems.Phil != null)
		{
			registerRedFlowerRecipe(EEItems.Phil);
		}
	}
	private static void registerYellowFlowerRecipes()
	{
		if(EEItems.Phil != null)
		{
			registerYellowFlowerRecipe(EEItems.Phil);
		}
	}
	private static void registerAlchCoalRecipe(Item alchCatal)
	{
		for(int i = 0;i < 3;i++)
		{
			List list1 = new ArrayList();
			List list2 = new ArrayList();

			for(int j = 0;j <= i;j++)
			{
				list1.add(Items.coal);
				list2.add(Items.coal);
				list1.add(Items.glowstone_dust);
				list2.add(Items.glowstone_dust);
			}
			list1.add(Items.lava_bucket);
			list2.add(EEItems.Volc);

			list1.add(alchCatal);
			list2.add(alchCatal);

			addSRecipe(gs(EEItems.AlchCoal,i+1), list1.toArray());
			if(EEItems.Volc != null)
			{
				addSRecipe(gs(EEItems.AlchCoal,i+1), list2.toArray());
			}
		}
	}
	private static void registerSlimeballRecipes()
	{
		for(int i = 0;i < 2;i++)
		{
			List list = new ArrayList();
			for(int j = 0;j <= i;j++)
			{
				list.add(Items.wheat_seeds);
				list.add(Items.reeds);
				list.add(Blocks.sapling);
			}
			addValiantRecipe(gs(Items.slime_ball,i + 1), null, Items.water_bucket, EEItems.Ever, list.toArray());
		}
	}
	private static void registerRedFlowerRecipe(Item alchCatal)
	{
		for(int i = 0;i < 3;i++)
		{
			List list = new ArrayList();
			for(int j = 0;j <= i;j++)
			{
				list.add(Items.redstone);
				list.add(Items.wheat_seeds);
			}
			addValiantRecipe(gs(Blocks.red_flower,(i + 1) * 2), Items.water_bucket, EEItems.Ever, list.toArray());
		}
	}
	private static void registerYellowFlowerRecipe(Item alchCatal)
	{
		for(int i = 0;i < 3;i++)
		{
			List list = new ArrayList();
			for(int j = 0;j <= i;j++)
			{
				list.add(Items.glowstone_dust);
				list.add(Items.wheat_seeds);
			}
			addValiantRecipe(gs(Blocks.yellow_flower,(i + 1) * 2), Items.water_bucket, EEItems.Ever, list.toArray());
		}
	}
}
