package eelimitedr.features.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eelimitedr.registry.UnlocalizedRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

public class ItemKleinStar extends ItemEE implements UnlocalizedRegistry
{
	private IIcon[] icons = new IIcon[6];
	private int[] maxEMCs = new int[]{50000,200000,800000,3200000,12800000,51200000};
	public ItemKleinStar()
	{
		super(KleinID);
		this.setHasSubtypes(true).setMaxStackSize(1).setMaxDamage(0).setNoRepair();
	}
	public String getUnlocalizedName(ItemStack par1ItemStack)
    {
		return "item.Klein_"+par1ItemStack.getItemDamage();
    }
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		for(int i = 0;i < 6;i++)
		{
			par3List.add(new ItemStack(this,1,i));
		}
    }
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
		for(int i = 0;i < 6;i++)
		{
			icons[i] = par1IconRegister.registerIcon("eelimitedr:Klein"+i);
		}
    }
	/**
     * Determines if the durability bar should be rendered for this item.
     * Defaults to vanilla stack.isDamaged behavior.
     * But modders can use this for any data they wish.
     *
     * @param stack The current Item Stack
     * @return True if it should render the 'durability' bar.
     */
    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }

    /**
     * Queries the percentage of the 'Durability' bar that should be drawn.
     *
     * @param stack The current ItemStack
     * @return 1.0 for 100% 0 for 0%
     */
    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
    	NBTTagCompound nbt = stack.getTagCompound();
    	int damage  = stack.getItemDamage();
    	if(nbt == null)
    	{
    		nbt = new NBTTagCompound();
    		nbt.setDouble("EMC",0);
    		nbt.setDouble("MaxEMC",getMaxEMC(damage));
    		stack.setTagCompound(nbt);
    	}
    	if(nbt.getDouble("EMC") > nbt.getDouble("MaxEMC"))
    	{
    		nbt.setDouble("EMC",nbt.getDouble("MaxEMC"));
    	}
    	if(nbt.getDouble("MaxEMC") == 0)
    	{
    		return 1;
    	}
    	return 1 - (nbt.getDouble("EMC") / nbt.getDouble("MaxEMC"));
    }
	public double getMaxEMC(int damage)
	{
		return maxEMCs[damage];
	}
	public IIcon getIconFromDamage(int par1)
    {
		int meta = MathHelper.clamp_int(par1,0,5);
		return icons[meta];
    }
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4)
	{
		NBTTagCompound nbt = itemStack.getTagCompound();
		if(nbt != null)
		{
			list.add("EMC:"+nbt.getDouble("EMC"));
			list.add("MaxEMC:"+nbt.getDouble("MaxEMC"));
		}
	}
}
