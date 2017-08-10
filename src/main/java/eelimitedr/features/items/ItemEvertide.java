package eelimitedr.features.items;

import eelimitedr.features.entities.EntityWaterProjectile;
import eelimitedr.features.items.interfaces.IProjectileShooter;
import eelimitedr.registry.UnlocalizedRegistry;
import eelimitedr.utils.Constants;
import eelimitedr.utils.InventoryUtils;
import eelimitedr.utils.PlayerUtils;
import eelimitedr.utils.WorldUtils;
import eelimitedr.utils.enums.EnumSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidHandler;

public class ItemEvertide extends ItemChargeable implements IProjectileShooter,IFluidContainerItem
{
	public void onUpdate(ItemStack par1ItemStack, World world, Entity par3Entity, int par4, boolean par5)
    {
		super.onUpdate(par1ItemStack, world, par3Entity, par4, par5);
        if (par3Entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer)par3Entity;
			if (player.getAir() < 300)
        	{
            	player.setAir(300);
        	}
			int x = (int) Math.floor(player.posX);
			int y = (int) (player.posY - player.getYOffset());
			int z = (int) Math.floor(player.posZ);
			Block b = world.getBlock(x, y - 1, z);
			Block bu = world.getBlock(x, y, z);

			if ((b.getMaterial() == Material.water) && bu == Blocks.air)
			{
				if (!player.isSneaking())
				{
					player.motionY = 0.0D;
					player.fallDistance = 0.0F;
					player.onGround = true;
				}

				if (!world.isRemote && player.capabilities.getWalkSpeed() < 0.25F)
				{
					PlayerUtils.setPlayerSpeed(player, 0.25F);
				}
			}
			else if (!world.isRemote)
			{
				if((b.getMaterial() == Material.lava) && bu == Blocks.air && InventoryUtils.getStackFromInv(player.inventory, new ItemStack(EEItems.Volc)) != null)
				{
					return;
				}
				if (player.capabilities.getWalkSpeed() != Constants.PLAYER_WALK_SPEED)
				{
					PlayerUtils.setPlayerSpeed(player, Constants.PLAYER_WALK_SPEED);
				}
			}
        }
    }

    public ItemEvertide()
    {
        super(UnlocalizedRegistry.EverID,8);
        this.setMaxStackSize(1).setContainerItem(this);
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int x, int y, int z, int side, float par8, float par9, float par10)
    {
    	WorldUtils.placeFluid(par3World, par2EntityPlayer, 0, this.getChargeLevel(par1ItemStack), Blocks.flowing_water, FluidRegistry.WATER, x, y, z, side);
    	setCurrentCharge(par1ItemStack, 0, true);
    	return true;
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack)
    {
        return false;
    }

	@Override
	public boolean shootProcectile(EntityPlayer player, ItemStack is) {
		player.worldObj.spawnEntityInWorld(new EntityWaterProjectile(player.worldObj, player));
		player.worldObj.playSoundAtEntity(player, EnumSounds.TRANSMUTE.getPath(),1.0F,1.0F);
		return true;
	}

	@Override
	public FluidStack getFluid(ItemStack container)
	{
		return new FluidStack(FluidRegistry.WATER,1000);
	}

	@Override
	public int getCapacity(ItemStack container) {
		return 1000;
	}

	@Override
	public int fill(ItemStack container, FluidStack resource, boolean doFill)
	{
		return 1000;
	}

	@Override
	public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain)
	{
		return new FluidStack(FluidRegistry.WATER,1000);
	}
}
