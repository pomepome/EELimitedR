package eelimitedr.utils;

import static eelimitedr.utils.RecipeUtils.addSRecipe;
import static eelimitedr.utils.StackUtils.changeAmount;
import static eelimitedr.utils.StackUtils.changeFrom;
import static eelimitedr.utils.StackUtils.gs;
import static eelimitedr.utils.StackUtils.nullCheck;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import eelimitedr.features.items.EEItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public class RecipeUtils
{
	public static void addRecipe(IRecipe recipe)
	{
		if(!nullCheck(recipe))
		{
			return;
		}
		GameRegistry.addRecipe(recipe);
	}
	public static void addRecipe(Object result,Object... objs)
	{
		if(!nullCheck(result) || !nullCheck(objs))
		{
			return;
		}
		GameRegistry.addShapedRecipe(gs(result), objs);
	}
	public static void addSRecipe(Object result,Object... objs)
	{
		if(!nullCheck(result) || !nullCheck(objs))
		{
			return;
		}
		GameRegistry.addShapelessRecipe(gs(result), objs);
	}

	public static void addPlainExchange(Object result,Object... objs)
	{
		if(EEItems.Phil != null)
		{
			addPlainExchange(result, 0, EEItems.Phil, objs);
		}
	}

	public static void addValiantRecipe(ItemStack result, Item catal, Object valA, Object valB,Object...objs)
	{
		addValiantRecipe(result, 0, catal, valA, valB, objs);
	}

	public static void addValiantRecipe(ItemStack result, Object valA, Object valB,Object...objs)
	{
		if(EEItems.Phil != null)
		{
			addValiantRecipe(result, EEItems.Phil, valA, valB, objs);
		}
	}

	private static void addValiantRecipe(ItemStack result,int dummy,Item alchCatal,Object valA,Object valB,Object...objs)
	{
		if(valA != null)
		{
			List list = new ArrayList();
			for(Object obj : objs)
			{
				list.add(obj);
			}
			list.add(valA);
			if(alchCatal != null)
			{
				list.add(alchCatal);
			}
			addSRecipe(result, list.toArray());
		}
		if(valB != null)
		{
			List list = new ArrayList();
			for(Object obj : objs)
			{
				list.add(obj);
			}
			list.add(valB);
			if(alchCatal != null)
			{
				list.add(alchCatal);
			}
			addSRecipe(result, list.toArray());
		}
	}
	public static void addRoteryRecipe(Object result,Object objA,Object objB,Object objC)
	{
		addRecipe(result,"ABA","BCB","ABA",'A',objA,'B',objB,'C',objC);
		addRecipe(result,"ABA","BCB","ABA",'A',objB,'B',objA,'C',objC);
	}
	public static void addNormalExchangeRecipe(Object ingr,Object... results)
	{
		if(EEItems.Phil != null)
		{
			addNormalExchangeRecipe(0, EEItems.Phil, ingr,results);
		}
	}
	public static void addNormalExchangeRecipeRing(Object ingr,Object... results)
	{
		if(EEItems.Phil != null)
		{
			addNormalExchangeRecipeRing(0, EEItems.Phil, ingr,results);
		}
	}
	public static void addNormalExchangeRecipeRingLastModified(Object ingr,Object last,Object... results)
	{
		if(EEItems.Phil != null)
		{
			addNormalExchangeRecipeRingLastModified(0, EEItems.Phil, ingr, last,results);
		}
	}
	public static void addExchangeRecipe(Item catal, Object result, Object...objs)
	{
		if(catal == null)
		{
			if(EEItems.Phil != null)
			{
				addExchangeRecipe(result, 0, EEItems.Phil, objs);
			}
		}
		else if(catal == EEItems.dummy)
		{
			addExchangeRecipe(result, 0, null, objs);
		}
		else
		{
			addExchangeRecipe(result, 0, catal, objs);
		}
	}
	public static void addExchangeRecipeLastModified(Object result, Object last, Object...objs)
	{
		if(EEItems.Phil != null)
		{
			addExchangeRecipeLastModified(result, 0, EEItems.Phil, last, changeFrom(objs));
		}
	}
	private static void addExchangeRecipe(Object result,int dummy,Item alchemyCatal, Object...stacks)
	{
		int stackSize = result instanceof ItemStack ? ((ItemStack)result).stackSize : 1;
		ItemStack resStack = gs(result);
		if(!nullCheck(result) || !nullCheck(resStack) || !nullCheck(stacks) || stacks.length == 0)
		{
			return;
		}
		int usable = alchemyCatal == null ? 9 : 8;
		for(int i = 1;i <= usable / stacks.length;i++)
		{
			if(stackSize * i > resStack.getMaxStackSize())
			{
				break;
			}
			List stackList = new ArrayList();
			for(int j = 0;j < i;j++)
			{
				for(Object stack : stacks)
				{
					stackList.add(stack);
				}
			}
			if(alchemyCatal != null)
			{
				stackList.add(alchemyCatal);
			}
			addSRecipe(changeAmount(resStack,stackSize * i),stackList.toArray());
		}
	}
	private static void addExchangeRecipeLastModified(Object result,int dummy,Item alchemyCatal,Object last, ItemStack...stacks)
	{
		int stackSize = result instanceof ItemStack ? ((ItemStack)result).stackSize : 1;
		ItemStack resStack = gs(result);
		if(last == null || !nullCheck(result) || !nullCheck(stacks) || stacks.length == 0)
		{
			return;
		}
		int usable = alchemyCatal == null ? 9 : 8;
		for(int i = 1;i <= usable / stacks.length;i++)
		{
			if(stackSize * i > resStack.getMaxStackSize())
			{
				break;
			}
			resStack = i < usable/stacks.length ? changeAmount(resStack,stackSize * i) : gs(last);
			List<ItemStack> stackList = new ArrayList<ItemStack>();
			for(int j = 0;j < i;j++)
			{
				for(ItemStack stack : stacks)
				{
					stackList.add(stack);
				}
			}
			stackList.add(gs(alchemyCatal));
			addSRecipe(resStack,stackList.toArray());
		}
	}
	private static void addPlainExchange(Object result,int dummy,Item alchCatal,Object...stacks)
	{
		if(!nullCheck(result) || !nullCheck(stacks) || stacks.length == 0)
		{
			return;
		}
		List list = new ArrayList();
		for(Object obj : stacks)
		{
			list.add(obj);
		}
		list.add(alchCatal);
		addSRecipe(result, list.toArray());
	}
	private static void addNormalExchangeRecipeRingLastModified(int dummy,Item alchemyCatal,Object ingr,Object last,Object... results)
	{
		if(!nullCheck(ingr) || !nullCheck(alchemyCatal) || !nullCheck(results))
		{
			return;
		}
		for(int i = 0;i < 8;i++)
		{
			int index = i % results.length;
			ItemStack resStack = gs(results[index]);
			if(i >= results.length)
			{
				int first = StackUtils.getFirstIndex(resStack, results);
				if(first > -1)
				{
					if(gs(results[first]) != null)
					{
						resStack.stackSize = gs(results[first]).stackSize * (i + 1) / (first + 1);
						if(resStack.stackSize > resStack.getMaxStackSize())
						{
							continue;
						}
					}
				}
			}
			if(i == 7)
			{
				resStack = gs(last);
			}
			List<ItemStack> list = new ArrayList<ItemStack>();
			for(int j = 0;j <= i;j++)
			{
				list.add(gs(ingr));
			}
			list.add(gs(alchemyCatal));
			addSRecipe(resStack,list.toArray());
		}
	}
	private static void addNormalExchangeRecipe(int dummy,Item alchemyCatal,Object ingr,Object... results)
	{
		if(!nullCheck(ingr) || !nullCheck(alchemyCatal))
		{
			return;
		}
		for(int i = 1;i <= 8;i++)
		{
			ItemStack resStack = gs(results[i - 1]);
			List<ItemStack> list = new ArrayList<ItemStack>();
			for(int ingrCount = 0;ingrCount < i;ingrCount++)
			{
				list.add(gs(ingr,1));
			}
			list.add(gs(alchemyCatal));
			addSRecipe(resStack,list.toArray());
		}
	}
	private static void addNormalExchangeRecipeRing(int dummy,Item alchemyCatal,Object ingr,Object... results)
	{
		if(!nullCheck(ingr) || !nullCheck(alchemyCatal) || !nullCheck(results))
		{
			return;
		}
		for(int i = 0;i < 8;i++)
		{
			int index = i % results.length;
			ItemStack resStack = gs(results[index]);
			if(i >= results.length)
			{
				int first = StackUtils.getFirstIndex(resStack, results);
				if(first > -1)
				{
					if(gs(results[first]) != null)
					{
						resStack.stackSize = gs(results[first]).stackSize * (i + 1) / (first + 1);
						if(resStack.stackSize > resStack.getMaxStackSize())
						{
							continue;
						}
					}
				}
			}
			List<ItemStack> list = new ArrayList<ItemStack>();
			list.add(gs(alchemyCatal));
			for(int j = 0;j <= i;j++)
			{
				list.add(gs(ingr));
			}
			addSRecipe(resStack,list.toArray());
		}
	}
}
