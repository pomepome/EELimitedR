package eelimitedr.features.items;

import java.util.List;

import com.google.common.collect.Lists;

import eelimitedr.net.PacketHandler;
import eelimitedr.net.PacketSpawnParticle;
import eelimitedr.utils.InventoryUtils;
import eelimitedr.utils.WorldUtils;
import eelimitedr.utils.enums.EnumSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class ItemEETool extends ItemChargeable {

	public ItemEETool(String id,int numCharges, int damage)
	{
		super(id, numCharges, damage);
		this.setContainerItem(null);
	}
	public ItemEETool(String id, int damage)
	{
		this(id, 1, damage);
	}

	protected boolean isWood(World world,int x,int y,int z)
	{
		return isWood(world.getBlock(x, y, z));
	}
	public boolean isLeaves(World w,int x,int y,int z)
    {
        return isLeaves(w.getBlock(x, y, z));
    }
    public boolean isLeaves(Block b)
    {
        if (b == null)
        {
            return false;
        }

        String name = b.getUnlocalizedName();
        return name.toLowerCase().contains("leaves") && b instanceof IShearable;
    }
    public Block getBlock(World w, int x, int y, int z)
    {
        return w.getBlock(x, y, z);
    }
    public void breakBlock(World w, int x, int y, int z)
    {
        Block b = w.getBlock(x, y, z);

        if (b != null)
        {
            b.dropBlockAsItem(w, x, y, z, w.getBlockMetadata(x, y, z), 0);
            b.breakBlock(w, x, y, z, b, 0);
            w.setBlock(x, y, z, Blocks.air);
        }
    }
    public void breakWood(World w,int x,int y,int z)
    {
    	breakBlock(w,x,y,z);
    }
	protected void deforestAOE(World world, ItemStack stack, EntityPlayer player, int emcCost)
	{
		byte charge = (byte)getChargeLevel(stack);
		boolean flag = false;
		if (charge == 0 || world.isRemote)
		{
			return;
		}

		List<ItemStack> drops = Lists.newArrayList();

		for (int x = (int) player.posX - (5 * charge); x <= player.posX + (5 * charge); x++)
		{
			for (int y = (int) player.posY - (10 * charge); y <= player.posY + (10 * charge); y++)
			{
				for (int z = (int) player.posZ - (5 * charge); z <= player.posZ + (5 * charge); z++)
				{
					Block block = world.getBlock(x, y, z);

					if (block == Blocks.air)
					{
						continue;
					}
					if (isWood(block) || isLeaves(block))
					{
						if(!InventoryUtils.useResource(player, emcCost, true))
						{
							break;
						}
						PacketHandler.sendToAll(new PacketSpawnParticle("largesmoke",x + 0.5,y + 0.5,z + 0.5));
						breakBlock(world,x,y,z);
						flag = true;
					}
				}
			}
		}
		if(flag)
		{
			WorldUtils.playSoundAtPlayer(player, EnumSounds.CHARGE.getPath(), 1.0f, 1.0f);
		}
		player.swingItem();
	}
}
