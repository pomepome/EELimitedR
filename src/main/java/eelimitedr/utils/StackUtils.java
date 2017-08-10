package eelimitedr.utils;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class StackUtils
{
	public static ItemStack gs(Object item)
	{
		if(item == null)
		{
			return null;
		}
		if(item instanceof Item)
		{
			return new ItemStack((Item)item);
		}
		else if(item instanceof Block)
		{
			return new ItemStack((Block)item);
		}
		else if(item instanceof ItemStack)
		{
			return ((ItemStack)item).copy();
		}
		return null;
	}
	public static ItemStack gs(Object item,int amount)
	{
		ItemStack stack = gs(item);
		if(stack == null)
		{
			return null;
		}
		stack.stackSize = amount;
		return stack;
	}
	public static ItemStack gs(Object item,int amount,int damage)
	{
		ItemStack stack = gs(item);
		if(stack == null)
		{
			return null;
		}
		stack.stackSize = amount;
		if(damage == -1)
		{
			damage = 32767;
		}
		stack.setItemDamage(damage);
		return stack;
	}
	public static ItemStack changeAmount(Object stack,int amount)
	{
		if(stack == null)
		{
			return null;
		}
		ItemStack newStack = stack instanceof ItemStack ? ((ItemStack)stack).copy() : gs(stack);
		newStack.stackSize = amount;
		return newStack;
	}
	public static boolean nullCheck(Object...objs)
	{
		for(Object obj : objs)
		{
			if(obj == null)
			{
				return false;
			}
		}
		return true;
	}
	public static boolean nullCheck(ItemStack...stacks)
	{
		for(Object obj : stacks)
		{
			if(obj == null)
			{
				return false;
			}
		}
		return true;
	}
	public static ItemStack[] changeFrom(Object...objs)
	{
		if(!nullCheck(objs))
		{
			return null;
		}
		ItemStack[] stacks = new ItemStack[objs.length];
		for(int i = 0;i < stacks.length;i++)
		{
			stacks[i] = gs(objs[i]);
		}
		return stacks;
	}
	public static int getFirstIndex(ItemStack stack,Object...toSearch)
	{
		for(int i = 0;i < toSearch.length;i++)
		{
			if(areStacksEqual(stack, toSearch[i]))
			{
				return i;
			}
		}
		return -1;
	}
	public static boolean areStacksEqual(Object stack1, Object stack2)
	{

		if(stack1 == null || stack2 == null)
		{
			return false;
		}
		ItemStack newStack1 = gs(stack1);
		ItemStack newStack2 = gs(stack2);
		if(newStack1 == null || newStack2 == null)
		{
			return false;
		}
		if(newStack1.getItemDamage() != newStack2.getItemDamage())
		{
			return false;
		}
		if(newStack1.getItem() instanceof ItemBlock && newStack2.getItem() instanceof ItemBlock)
		{
			return ((ItemBlock)newStack1.getItem()).field_150939_a == ((ItemBlock)newStack2.getItem()).field_150939_a;
		}
		return newStack1.getItem() == newStack2.getItem();
	}
	public static ItemStack copyStack(ItemStack source)
	{
		return source != null ? source.copy() : null;
	}
	public static ItemStack normalizeStack(ItemStack itemStack)
	{
		return changeAmount(itemStack, 1);
	}
}
