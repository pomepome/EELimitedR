package eelimitedr.features.items;

import eelimitedr.features.entities.EntityLavaProjectile;
import eelimitedr.features.items.interfaces.IProjectileShooter;
import eelimitedr.handlers.PlayerChecks;
import eelimitedr.registry.UnlocalizedRegistry;
import eelimitedr.utils.Constants;
import eelimitedr.utils.InventoryUtils;
import eelimitedr.utils.PlayerUtils;
import eelimitedr.utils.WorldUtils;
import eelimitedr.utils.enums.EnumSounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidHandler;

public class ItemVolcanite extends ItemChargeable implements IProjectileShooter
{
	public ItemVolcanite()
    {
        super(UnlocalizedRegistry.VolcID,8);
        this.setMaxStackSize(1).setContainerItem(this).setMaxDamage(3);
    }

    public void doVaporize(ItemStack var1, World var2, EntityPlayer var3, int range)
    {
        boolean var4 = false;

        if (range % 2 == 0)
        {
            range++;
        }

        int count = (range + 1) / 2;
        int ox = (int) var3.posX - count;
        int oy = (int) var3.posY - count;
        int oz = (int) var3.posZ - count;

        for (int i = 0; i < range; i++)
        {
            for (int j = 0; j < range; j++)
            {
                for (int k = 0; k < range; k++)
                {
                    int nx = ox + i;
                    int ny = oy + j;
                    int nz = oz + k;

                    if (var2.getBlock(nx, ny, nz).getMaterial() == Material.water)
                    {
                        var4 = true;
                        var2.setBlock(nx, ny, nz, Blocks.air);
                    }
                }
            }
        }
        if (var4)
        {
            WorldUtils.playSoundAtPlayer(var3, "random.fizz", 1.0F, 1.2F / (var2.rand.nextFloat() * 0.2F + 0.9F));
        }
    }

    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
        if (var3.isSneaking())
        {
            doVaporize(var1, var2, var3, 21);
        }

        return var1;
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int x, int y, int z, int side, float par8, float par9, float par10)
    {
    	WorldUtils.placeFluid(par3World, par2EntityPlayer, 378, this.getChargeLevel(par1ItemStack), Blocks.flowing_lava, FluidRegistry.LAVA, x, y, z, side);
    	setCurrentCharge(par1ItemStack, 0, true);
    	return true;
    }
    @Override
    public final void onUpdate(ItemStack itemstack, World world, Entity entity, int par4, boolean par5)
    {
    	super.onUpdate(itemstack, world, entity, par4, par5);
    	if(entity instanceof EntityPlayer)
    	{
    		EntityPlayer player = (EntityPlayer)entity;

    		int x = (int) Math.floor(player.posX);
    		int y = (int) (player.posY - player.getYOffset());
    		int z = (int) Math.floor(player.posZ);
			Block b = world.getBlock(x, y - 1, z);
			Block bu = world.getBlock(x, y, z);

    		if ((b.getMaterial() == Material.lava) && bu == Blocks.air)
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
    			if((b.getMaterial() == Material.water) && bu == Blocks.air && InventoryUtils.getStackFromInv(player.inventory, new ItemStack(EEItems.Ever)) != null)
				{
					return;
				}
    			if (player.capabilities.getWalkSpeed() != Constants.PLAYER_WALK_SPEED)
    			{
    				PlayerUtils.setPlayerSpeed(player, Constants.PLAYER_WALK_SPEED);
    			}
    		}

    		if (!world.isRemote)
    		{
    			if (!player.isImmuneToFire())
    			{
    				PlayerUtils.setEntityImmuneToFire(player, true);
    			}

    			PlayerChecks.addPlayerFireChecks((EntityPlayerMP) player);
    		}
    	}
    }

	@Override
	public boolean shootProcectile(EntityPlayer player, ItemStack is) {
		player.worldObj.spawnEntityInWorld(new EntityLavaProjectile(player.worldObj, player));
		player.worldObj.playSoundAtEntity(player, EnumSounds.TRANSMUTE.getPath(),1.0F,1.0F);
		return true;
	}
}