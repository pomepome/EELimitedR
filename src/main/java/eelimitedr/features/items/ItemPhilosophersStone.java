package eelimitedr.features.items;

import eelimitedr.features.EELimitedR;
import eelimitedr.features.entities.EntityMobRandomizer;
import eelimitedr.features.items.interfaces.IExtraFunction;
import eelimitedr.features.items.interfaces.IProjectileShooter;
import eelimitedr.guis.inventory.PhilData;
import eelimitedr.registry.GuiIds;
import eelimitedr.registry.UnlocalizedRegistry;
import eelimitedr.utils.enums.EnumSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPhilosophersStone extends ItemEESpecial implements GuiIds,UnlocalizedRegistry,IExtraFunction,IProjectileShooter
{

	private static PhilData data;

	public ItemPhilosophersStone()
	{
		super(PhilStoneID);
	}

	@Override
	public boolean shootProcectile(EntityPlayer player, ItemStack is)
	{
		player.worldObj.spawnEntityInWorld(new EntityMobRandomizer(player.worldObj,player));
		player.worldObj.playSoundAtEntity(player, EnumSounds.TRANSMUTE.getPath(),1.0F,1.0F);
		return true;
	}

	@Override
	public void onExtraFunction(EntityPlayer p, ItemStack is)
	{
		p.openGui(EELimitedR.getInstance(), IGUI_PHILWORKBENCH, p.worldObj, (int)p.posX, (int)p.posY, (int)p.posZ);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		if(entity instanceof EntityPlayer && !world.isRemote)
		{
			if(data == null)
			{
				data = getPhilData(world);
			}
			data.markDirty();
		}
	}

	public static PhilData getPhilData(World world)
	{
		PhilData pData = null;

		pData = (PhilData)world.loadItemData(PhilData.class, "Phil");
		if(pData == null)
		{
			pData = new PhilData("Phil");
			pData.markDirty();
			world.setItemData("Phil", pData);
		}

		return pData;
	}
}
