package eelimitedr.features.items;

import static eelimitedr.utils.StackUtils.*;

import eelimitedr.features.entities.EntityHomingArrow;
import eelimitedr.features.items.interfaces.IProjectileShooter;
import eelimitedr.registry.UnlocalizedRegistry;
import eelimitedr.utils.InventoryUtils;
import eelimitedr.utils.StackUtils;
import eelimitedr.utils.WorldUtils;
import eelimitedr.utils.enums.EnumSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemArchangelsSmite extends ItemChargeable implements UnlocalizedRegistry, IProjectileShooter
{
	public ItemArchangelsSmite()
	{
		super(ArchangelID, 64);
		this.setContainerItem(null);
	}

	@Override
	protected boolean useResource(ItemStack stack, EntityPlayer player)
	{
		if(player.capabilities.isCreativeMode)
		{
			return true;
		}
		if(InventoryUtils.useStackFromInv(player.inventory, gs(Items.arrow), true))
		{
			return true;
		}
		else if(InventoryUtils.useStackFromInv(player.inventory, gs(Blocks.cobblestone), true))
		{
			addCharge(stack, 2);
			return true;
		}
		return false;
	}

	public ItemStack onItemRightClick(ItemStack var1, World world, EntityPlayer player)
    {
		EntityHomingArrow arrow = null;
		if(getChargeLevel(var1) > 0)
		{
			addCharge(var1, -1, true);
			arrow = new EntityHomingArrow(world, player, 4.0F);
		}
		else
		{
			if(InventoryUtils.useStackFromInv(player.inventory, gs(Items.arrow), true))
			{
				arrow = new EntityHomingArrow(world, player, 4.0F);
				arrow.setPickable(true);
			}
			else if(InventoryUtils.useStackFromInv(player.inventory, gs(Blocks.cobblestone), true))
			{
				addCharge(var1, 2, true);
				arrow = new EntityHomingArrow(world, player, 4.0F);
			}
			else if(InventoryUtils.useResource(player, 1, true, false))
			{
				arrow = new EntityHomingArrow(world, player, 4.0F);
			}
		}

		if(arrow != null)
		{
			world.spawnEntityInWorld(arrow);
		}

		return var1;
    }

	@Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
    {
		super.onUpdate(stack, world, entity, par4, par5);

		if(world.isRemote)
		{
			return;
		}

		ItemBow bow;

		if(isArrowBursting(stack) && entity instanceof EntityPlayer && areStacksEqual(stack, ((EntityPlayer)entity).getCurrentEquippedItem()))
		{
			EntityPlayer player = (EntityPlayer)entity;
			int currentCharge = getCurrentCharge(stack);
			addCharge(stack, -1, true);
			if(currentCharge - 1 < 0)
			{
				stack.getTagCompound().setBoolean("isBursting", false);
				WorldUtils.playSoundAtPlayer(player, EnumSounds.UNCHARGE.getPath(), 1, 1);
				return;
			}
			WorldUtils.playSoundAtPlayer(player, "random.bow", 1, 1);
			EntityHomingArrow arrow = new EntityHomingArrow(world, player, 4.0F);
			world.spawnEntityInWorld(arrow);
		}
		else
		{
			setBurstingMode(stack, false);
		}
    }

	@Override
	protected int getChargeSpeed()
	{
		return 1;
	}

	private boolean isArrowBursting(ItemStack stack)
	{
		if(stack.hasTagCompound())
		{
			NBTTagCompound tag = stack.getTagCompound();
			if(tag.hasKey("isBursting"))
			{
				return tag.getBoolean("isBursting");
			}
			else
			{
				tag.setBoolean("isBursting", false);
			}
		}
		return false;
	}

	private int summonArrows(EntityPlayer player, ItemStack is,int num)
	{
		int actually = 0;
		if(num <= getChargeLevel(is))
		{
			addCharge(is, -num, true);
			actually = num;
		}
		else
		{
			actually = getChargeLevel(is);
			int toCost = num - getChargeLevel(is);
			setCurrentCharge(is, 0, true);
			for(int i = 0;i < toCost / 3;i++)
			{
				if(InventoryUtils.useStackFromInv(player.inventory, StackUtils.gs(Blocks.cobblestone), true))
				{
					actually += 3;
				}
			}
			for(int i = actually;i < num;i++)
			{
				if(InventoryUtils.useStackFromInv(player.inventory, gs(Items.arrow), true))
				{
					actually++;
				}
				else
				{
					break;
				}
			}
			for(int i = actually;i < num;i++)
			{
				if(InventoryUtils.useResource(player, 1, true, false))
				{
					actually++;
				}
				else
				{
					break;
				}
			}
		}
		for(int i = 0;i < actually;i++)
		{
			EntityHomingArrow arrow = new EntityHomingArrow(player.worldObj, player, 4.0F);
			player.worldObj.spawnEntityInWorld(arrow);
		}
		return num - actually;
	}

	@Override
	public boolean shootProcectile(EntityPlayer player, ItemStack is)
	{
		if(!isArrowBursting(is))
		{
			setBurstingMode(is, true);
		}
		else
		{
			setBurstingMode(is, false);
		}
		return false;
	}

	@Override
	public void startCharging(EntityPlayer player, ItemStack stack)
	{
		if(isArrowBursting(stack))
		{
			setBurstingMode(stack, false);
		}
		else
		{
			super.startCharging(player, stack);
		}

	}

	public boolean onEntitySwing(EntityLivingBase entity, ItemStack stack)
    {
		if(!entity.worldObj.isRemote && entity instanceof EntityPlayer)
		{
			summonArrows((EntityPlayer)entity, stack, 7);
		}
		return false;
    }
	protected boolean canUncharge()
	{
		return false;
	}
	private void setBurstingMode(ItemStack stack, boolean flag)
	{
		stack.getTagCompound().setBoolean("isBursting", flag);
	}
}
