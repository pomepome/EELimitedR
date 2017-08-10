package eelimitedr.features.items;

import static eelimitedr.utils.StackUtils.areStacksEqual;

import eelimitedr.features.items.interfaces.IChargeable;
import eelimitedr.utils.WorldUtils;
import eelimitedr.utils.enums.EnumSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemChargeable extends ItemEESpecial implements IChargeable
{

	int numCharges;

	public ItemChargeable(String name,int numCharge)
	{
		super(name);
		numCharges = numCharge;
		this.setMaxStackSize(1);
	}
	public ItemChargeable(String name,int numCharge,int damage)
	{
		super(name,damage);
		numCharges = numCharge;
		this.setMaxStackSize(1);
	}
	@Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return getChargeLevel(stack) > 0;
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
    	int charge = getChargeLevel(stack);
    	return numCharges == 0 ? 1.0 : 1.0 - ((double)charge / (double)numCharges);
    }

    @Override
	public void startCharging(EntityPlayer player, ItemStack stack)
	{
		int currentCharge = getChargeLevel(stack);

		if (player.isSneaking())
		{
			if(currentCharge > 0 && canUncharge())
			{
				WorldUtils.playSoundAtPlayer(player, EnumSounds.UNCHARGE.getPath(), 1, 1);
				stack.stackTagCompound.setInteger("Charge",0);
				stack.stackTagCompound.setBoolean("isCharging", false);
			}
		}
		else if (currentCharge < numCharges)
		{
			if(stack.stackTagCompound.getBoolean("isCharging"))
			{
				stack.stackTagCompound.setBoolean("isCharging", false);
			}
			else
			{
				stack.stackTagCompound.setBoolean("isCharging", true);
			}
		}
		else if(canUncharge())
		{
			WorldUtils.playSoundAtPlayer(player, EnumSounds.UNCHARGE.getPath(), 1, 1);
			stack.stackTagCompound.setInteger("Charge",0);
			stack.stackTagCompound.setBoolean("isCharging", false);
		}
	}

	@Override
	public int getChargeLevel(ItemStack is)
	{
		return getCurrentCharge(is);
	}
	@Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
    {
		if(!stack.hasTagCompound() || world.isRemote)
		{
			getCurrentCharge(stack);
			return;
		}
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt.getBoolean("isCharging") && entity instanceof EntityPlayer&& areStacksEqual(stack, ((EntityPlayer)entity).getCurrentEquippedItem()))
		{
			EntityPlayer player = (EntityPlayer)entity;
			WorldUtils.playSoundAtPlayer(player, EnumSounds.CHARGETICK.getPath(), 0.5F, 1f);
			int currentCharge = getCurrentCharge(stack);
			int ticks = nbt.getInteger("ticksCharging") + 1;
			if(getChargeSpeed() == 0 || ticks % getChargeSpeed() == 0)
			{
				if(getChargeSpeed() > 1)
				{
					WorldUtils.playSoundAtPlayer(player, EnumSounds.CHARGE.getPath(), 0.5f, 0.5f + (0.5f * (currentCharge + 1)) / numCharges);
				}
				if(useResource(stack,player))
				{
					addCharge(stack, 1);
					if(currentCharge + 1 >= numCharges)
					{
						stack.getTagCompound().setBoolean("isCharging", false);
						ticks = 0;
					}
				}
				else
				{
					stack.getTagCompound().setBoolean("isCharging", false);
					ticks = 0;
				}
			}
			nbt.setInteger("ticksCharging", ticks);
		}
		else
		{
			stack.getTagCompound().setBoolean("isCharging", false);
			nbt.setInteger("ticksCharging",0);
		}
    }
	protected void setCurrentCharge(ItemStack stack,int charge)
	{
		setCurrentCharge(stack, charge, false);
	}
	protected void setCurrentCharge(ItemStack stack,int charge,boolean cancelCharging)
	{
		if(charge < 0 || charge > numCharges)
		{
			return;
		}
		stack.getTagCompound().setInteger("Charge", charge);
		if(cancelCharging)
		{
			stack.stackTagCompound.setBoolean("isCharging", false);
		}
	}
	protected void addCharge(ItemStack stack,int count)
	{
		addCharge(stack, count, false);
	}
	protected void addCharge(ItemStack stack,int count,boolean cancelCharging)
	{
		int simulated = getCurrentCharge(stack) + count;
		if(simulated < 0)
		{
			simulated = 0;
		}
		else if(simulated > numCharges)
		{
			simulated = numCharges;
		}
		setCurrentCharge(stack, simulated, cancelCharging);
	}

	protected int getCurrentCharge(ItemStack stack)
	{
		if(!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Charge"))
		{
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("Charge", 0);
			tag.setInteger("ticksCharging", 0);
			tag.setBoolean("isCharging", false);
			stack.setTagCompound(tag);
			return 0;
		}
		return stack.getTagCompound().getInteger("Charge");
	}

	protected boolean useResource(ItemStack stack,EntityPlayer player)
	{
		return true;
	}

	protected int getChargeSpeed()
	{
		return 5;
	}

	protected boolean canUncharge()
	{
		return true;
	}
}
