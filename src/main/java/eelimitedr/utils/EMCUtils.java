package eelimitedr.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;

public class EMCUtils
{
	private static Map<ItemStack, Double> EMCMap = new HashMap<ItemStack, Double>();

	public static void registerEMC(Object obj,double emc)
	{
		if(!hasKey(obj))
		{
			EMCMap.put(StackUtils.gs(obj), emc);
		}
	}

	private static boolean hasKey(Object obj)
	{
		for(Entry<ItemStack, Double> entry : EMCMap.entrySet())
		{
			if(StackUtils.areStacksEqual(entry.getKey(), obj))
			{
				return true;
			}
		}
		return false;
	}

	public static double getEMC(ItemStack is)
    {
    	if(is.hasTagCompound())
    	{
    		return is.getTagCompound().getDouble("EMC");
    	}
    	return 0;
    }
    public static void removeEMC(ItemStack is,int ammount)
    {
    	if(getEMC(is) > ammount)
    	{
    		setEMC(is,getEMC(is) - ammount);
    	}
    }
    public static double getMaxEMC(ItemStack is)
    {
    	if(is.hasTagCompound())
    	{
    		return is.getTagCompound().getDouble("MaxEMC");
    	}
    	return 0;
    }
    public static void setEMC(ItemStack is,double EMC)
    {
    	if(is.hasTagCompound())
    	{
    		if(EMC > getMaxEMC(is))
    		{
    			EMC = getMaxEMC(is);
    		}
    		is.getTagCompound().setDouble("EMC",EMC);
    	}
    }
}
