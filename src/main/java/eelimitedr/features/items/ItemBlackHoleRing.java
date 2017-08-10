package eelimitedr.features.items;

import java.util.List;

import eelimitedr.features.items.interfaces.IAlchChestUpdate;
import eelimitedr.registry.UnlocalizedRegistry;
import eelimitedr.utils.InventoryUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

public class ItemBlackHoleRing extends ItemRing implements UnlocalizedRegistry,IAlchChestUpdate
{

	public ItemBlackHoleRing()
	{
		super(BlackHoleBandID);
	}
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer p)
    {
        boolean flag = true;
        MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(w, p, flag);

        if (mop != null)
        {
        	if(mop.typeOfHit == MovingObjectType.BLOCK)
        	{
        		int x = mop.blockX;
        		int y = mop.blockY;
        		int z = mop.blockZ;
        		Block b = w.getBlock(x, y, z);
        		if(b instanceof BlockStaticLiquid || (b instanceof BlockFluidClassic && ((BlockFluidClassic)b).isSourceBlock(w, x, y, z)))
        		{
        			w.setBlockToAir(x, y, z);
        		}
        	}
        }
        return is;
    }

	@Override
	public void onUpdateInAlchChest(World world, IInventory inv, int x, int y, int z, ItemStack stack)
	{
		
	}

	@Override
	public void doPassive(World world, EntityPlayer player, ItemStack is, boolean isActivated)
	{
		if(isActivated)
		{

			AxisAlignedBB bBox = player.boundingBox.expand(11, 11, 11);
			List<EntityItem> itemList = world.getEntitiesWithinAABB(EntityItem.class, bBox);

			for (EntityItem item : itemList)
			{
				if (InventoryUtils.pushStackInInv(player.inventory, item.getEntityItem(), false, 0, 36) == null)
				{
					item.delayBeforeCanPickup = 0;
					double d1 = (player.posX - item.posX);
					double d2 = (player.posY + (double)player.getEyeHeight() - item.posY);
					double d3 = (player.posZ - item.posZ);
					double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);

					item.motionX += d1 / d4 * 0.1D;
					item.motionY += d2 / d4 * 0.1D;
					item.motionZ += d3 / d4 * 0.1D;

					item.moveEntity(item.motionX, item.motionY, item.motionZ);
				}
			}
		}
	}
}
