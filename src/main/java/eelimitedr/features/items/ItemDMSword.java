package eelimitedr.features.items;

import java.util.List;

import eelimitedr.registry.UnlocalizedRegistry;
import eelimitedr.utils.InventoryUtils;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ItemDMSword extends ItemEETool
{
    public ItemDMSword()
    {
        super(UnlocalizedRegistry.DMSwordID,7,13);
    }

    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
    {
        if (getChargeLevel(par1ItemStack) > 0)
        {
            par2EntityLivingBase.attackEntityFrom(DamageSource.causeMobDamage(par3EntityLivingBase), toolDamage);
        }
        return super.hitEntity(par1ItemStack, par2EntityLivingBase, par3EntityLivingBase);
    }
    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
        return EnumAction.block;
    }
    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
    	if(!world.isRemote)
    	{
    		attackNearbyEntities(stack, player);
    	}
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }
    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 7200000;
    }

    private void attackNearbyEntities(ItemStack stack,EntityPlayer player)
    {
    	int chargeLvl = getCurrentCharge(stack);
    	if(chargeLvl == 0)
    	{
    		return;
    	}
    	if(!InventoryUtils.useResource(player, 126 * chargeLvl, true))
    	{
    		return;
    	}
    	double size = chargeLvl * 2 + 1;
    	AxisAlignedBB bBox = player.boundingBox.expand(size / 2, size / 2, size / 2);
    	World w = player.worldObj;
    	List<EntityLiving> mobList = w.getEntitiesWithinAABB(EntityLiving.class, bBox);
    	for(EntityLiving mob : mobList)
    	{
    		mob.attackEntityFrom(DamageSource.causePlayerDamage(player), (chargeLvl <= 3 ? 8.0f : 10.0f));
    	}
    	setCurrentCharge(stack, 0, true);
    }
}