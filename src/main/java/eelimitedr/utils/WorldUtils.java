package eelimitedr.utils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.server.FMLServerHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class WorldUtils
{
    private static List<Class> peacefuls = new ArrayList<Class>();
	private static List<Class> mobs = new ArrayList<Class>();

	public static void Init()
    {
		loadEntityLists();
    }

	public static World getWorld()
	{
		World w = FMLServerHandler.instance().getServer().getEntityWorld();
		if(w == null)
		{
			w = FMLClientHandler.instance().getWorldClient();
		}
		return w;
	}

	public static int getRelativeOrientation(EntityLivingBase ent)
    {
    	int direction = 0;
		int facing = MathHelper.floor_double(ent.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (facing == 0)
		{
			direction = ForgeDirection.NORTH.ordinal();
		}
		else if (facing == 1)
		{
			direction = ForgeDirection.EAST.ordinal();
		}
		else if (facing == 2)
		{
			direction = ForgeDirection.SOUTH.ordinal();
		}
		else if (facing == 3)
		{
			direction = ForgeDirection.WEST.ordinal();
		}
		return direction;
    }

	public static void spawnEntityItem(World world, ItemStack stack, Entity entity)
	{
		spawnEntityItem(world, stack, entity.posX, entity.posY, entity.posZ);
	}

	public static void spawnEntityItem(World world, ItemStack stack, double x, double y, double z)
	{
    	float jump = ((float) world.rand.nextGaussian() * 0.05F + 0.2F);
    	spawnEntityItem(world, stack, x, y, z, jump);
	}
    public static void spawnEntityItem(World world, ItemStack stack, double x, double y, double z,float jump)
	{
    	float f = world.rand.nextFloat() * 0.8F + 0.1F;
		float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
		EntityItem entityitem;

		for (float f2 = world.rand.nextFloat() * 0.8F + 0.1F; stack.stackSize > 0; world.spawnEntityInWorld(entityitem))
		{
			int j1 = world.rand.nextInt(21) + 10;

			if (j1 > stack.stackSize)
				j1 = stack.stackSize;

			stack.stackSize -= j1;
			entityitem = new EntityItem(world, (double)((float) x + f), (double)((float) y + f1), (double)((float) z + f2), new ItemStack(stack.getItem(), j1, stack.getItemDamage()));
			float f3 = 0.05F;
			entityitem.motionX = (double)((float) world.rand.nextGaussian() * f3);
			entityitem.motionY = jump;
			entityitem.motionZ = (double)((float) world.rand.nextGaussian() * f3);

			if (stack.hasTagCompound())
			{
				entityitem.getEntityItem().setTagCompound((NBTTagCompound)stack.getTagCompound().copy());
			}
		}
	}

	private static void loadEntityLists()
	{
		//Peacefuls
		peacefuls.add(EntityCow.class);
		peacefuls.add(EntityMooshroom.class);
		peacefuls.add(EntitySheep.class);
		peacefuls.add(EntityPig.class);
		peacefuls.add(EntityChicken.class);
		peacefuls.add(EntityOcelot.class);
		peacefuls.add(EntityVillager.class);
		peacefuls.add(EntitySquid.class);
		peacefuls.add(EntityHorse.class);

		//Mobs
		mobs.add(EntityZombie.class);
		mobs.add(EntitySkeleton.class);
		mobs.add(EntitySpider.class);
		mobs.add(EntityCaveSpider.class);
		mobs.add(EntityCreeper.class);
		mobs.add(EntityEnderman.class);
		mobs.add(EntitySilverfish.class);
		mobs.add(EntityGhast.class);
		mobs.add(EntityPigZombie.class);
		mobs.add(EntitySlime.class);
		mobs.add(EntityWitch.class);
		mobs.add(EntityBlaze.class);
		mobs.add(EntityFireball.class);
	}
	public static boolean canFillTank(IFluidHandler tank, Fluid fluid, int side)
	{
		ForgeDirection dir = ForgeDirection.getOrientation(side);

		if (tank.canFill(dir, fluid))
		{
			boolean canFill = false;

			for (FluidTankInfo tankInfo : tank.getTankInfo(dir))
			{
				if (tankInfo.fluid == null)
				{
					canFill = true;
					break;
				}

				if (tankInfo.fluid.getFluid() == fluid && tankInfo.fluid.amount < tankInfo.capacity)
				{
					canFill = true;
					break;
				}
			}

			return canFill;
		}

		return false;
	}

	public static void fillTank(IFluidHandler tank, Fluid fluid, int side, int quantity)
	{
		tank.fill(ForgeDirection.getOrientation(side), new FluidStack(fluid, quantity), true);
	}
	public static ArrayList<ItemStack> getBlockDrops(World world, EntityPlayer player, Block block, ItemStack stack, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);

		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, stack) > 0 && block.canSilkHarvest(world, player, x, y, z, meta))
		{
			return Lists.newArrayList(new ItemStack(block, 1, meta));
		}

		return block.getDrops(world, x, y, z, meta, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack));
	}
	public static Entity getRandomEntity(World world, Entity toRandomize)
	{
		Class entClass = toRandomize.getClass();

		if (peacefuls.contains(entClass))
		{
			return getNewEntityInstance((Class) getRandomListEntry(peacefuls, entClass), world);
		}
		else if (mobs.contains(entClass))
		{
			return getNewEntityInstance((Class) getRandomListEntry(mobs, entClass), world);
		}
		else if (world.rand.nextInt(2) == 0)
		{
			return new EntitySlime(world);
		}
		else
		{
			return new EntitySheep(world);
		}
	}
	public static Entity getNewEntityInstance(Class c, World world)
	{
		try
		{
			Constructor constr = c.getConstructor(World.class);
			Entity ent = (Entity) constr.newInstance(world);

			if (ent instanceof EntitySkeleton)
			{
				if (world.rand.nextInt(2) == 0)
				{
					((EntitySkeleton) ent).setSkeletonType(1);
					ent.setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
				}
				else
				{
					ent.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
				}
			}
			else if (ent instanceof EntityPigZombie)
			{
				ent.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
			}

			return ent;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
	public static Object getRandomListEntry(List<?> list, Object toExclude)
	{
		Object obj;

		do
		{
			int random = randomIntInRange(list.size() - 1, 0);
			obj = list.get(random);
		}
		while(obj.equals(toExclude));

		return obj;
	}
	public static int randomIntInRange(int max, int min)
	{
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}
	/**
	 * Gets an AABB for AOE digging operations. The offset increases both the breadth and depth of the box.
	 */
	public static AxisAlignedBB getBroadDeepBox(Coordinates coords, ForgeDirection direction, int offset)
	{
		if (direction.offsetX > 0)
		{
			return AxisAlignedBB.getBoundingBox(coords.x - offset, coords.y - offset, coords.z - offset, coords.x, coords.y + offset, coords.z + offset);
		}
		else if (direction.offsetX < 0)
		{
			return AxisAlignedBB.getBoundingBox(coords.x, coords.y - offset, coords.z - offset, coords.x + offset, coords.y + offset, coords.z + offset);
		}
		else if (direction.offsetY > 0)
		{
			return AxisAlignedBB.getBoundingBox(coords.x - offset, coords.y - offset, coords.z - offset, coords.x + offset, coords.y, coords.z + offset);
		}
		else if (direction.offsetY < 0)
		{
			return AxisAlignedBB.getBoundingBox(coords.x - offset, coords.y, coords.z - offset, coords.x + offset, coords.y + offset, coords.z + offset);
		}
		else if (direction.offsetZ > 0)
		{
			return AxisAlignedBB.getBoundingBox(coords.x - offset, coords.y - offset, coords.z - offset, coords.x + offset, coords.y + offset, coords.z);
		}
		else if (direction.offsetZ < 0)
		{
			return AxisAlignedBB.getBoundingBox(coords.x - offset, coords.y - offset, coords.z, coords.x + offset, coords.y + offset, coords.z + offset);
		}
		return AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0);
	}

	/**
	 * Returns in AABB that is always 3x3 orthogonal to the side hit, but varies in depth in the direction of the side hit
	 */
	public static AxisAlignedBB getDeepBox(Coordinates coords, ForgeDirection direction, int depth)
	{
		if (direction.offsetX != 0)
		{
			if (direction.offsetX > 0)
			{
				return AxisAlignedBB.getBoundingBox(coords.x - depth, coords.y - 1, coords.z - 1, coords.x, coords.y + 1, coords.z + 1);
			}
			else return AxisAlignedBB.getBoundingBox(coords.x, coords.y - 1, coords.z - 1, coords.x + depth, coords.y + 1, coords.z + 1);
		}
		else if (direction.offsetY != 0)
		{
			if (direction.offsetY > 0)
			{
				return AxisAlignedBB.getBoundingBox(coords.x - 1, coords.y - depth, coords.z - 1, coords.x + 1, coords.y, coords.z + 1);
			}
			else return AxisAlignedBB.getBoundingBox(coords.x - 1, coords.y, coords.z - 1, coords.x + 1, coords.y + depth, coords.z + 1);
		}
		else
		{
			if (direction.offsetZ > 0)
			{
				return AxisAlignedBB.getBoundingBox(coords.x - 1, coords.y - 1, coords.z - depth, coords.x + 1, coords.y + 1, coords.z);
			}
			else return AxisAlignedBB.getBoundingBox(coords.x - 1, coords.y - 1, coords.z, coords.x + 1, coords.y + 1, coords.z + depth);
		}
	}
	public static void igniteNearby(World world, EntityPlayer player)
	{
		for (int x = (int) (player.posX - 8); x <= player.posX + 8; x++)
			for (int y = (int) (player.posY - 5); y <= player.posY + 5; y++)
				for (int z = (int) (player.posZ - 8); z <= player.posZ + 8; z++)
					if (world.rand.nextInt(128) == 0 && world.isAirBlock(x, y, z))
					{
						checkedPlaceBlock(((EntityPlayerMP) player), x, y, z, Blocks.fire, 0);
					}
	}
	public static boolean hasBreakPermission(EntityPlayerMP player, int x, int y, int z)
	{
		return hasEditPermission(player, x, y, z)
				&& !ForgeHooks.onBlockBreakEvent(player.worldObj, player.theItemInWorldManager.getGameType(), player, x, y, z).isCanceled();
	}

	public static boolean hasEditPermission(EntityPlayerMP player, int x, int y, int z)
	{
		return player.canPlayerEdit(x, y, z, player.worldObj.getBlockMetadata(x, y, z), null)
				&& !MinecraftServer.getServer().isBlockProtected(player.worldObj, x, y, z, player);
	}
	public static boolean checkedPlaceBlock(EntityPlayerMP player, int x, int y, int z, Block toPlace, int toPlaceMeta)
	{
		if (!hasEditPermission(player, x, y, z))
		{
			return false;
		}
		World world = player.worldObj;
		BlockSnapshot before = BlockSnapshot.getBlockSnapshot(world, x, y, z);
		world.setBlock(x, y, z, toPlace);
		world.setBlockMetadataWithNotify(x, y, z, toPlaceMeta, 3);
		BlockEvent.PlaceEvent evt = new BlockEvent.PlaceEvent(before, Blocks.air, player); // Todo verify can use air here
		MinecraftForge.EVENT_BUS.post(evt);
		if (evt.isCanceled())
		{
			world.restoringBlockSnapshots = true;
			before.restore(true, false);
			world.restoringBlockSnapshots = false;
			return false;
		}
		return true;
	}
	/**
	 * Gets an AABB for AOE digging operations. The charge increases only the breadth of the box.
	 * Y level remains constant. As such, a direction hit is unneeded.
	 */
	public static AxisAlignedBB getFlatYBox(Coordinates coords, int offset)
	{
		return AxisAlignedBB.getBoundingBox(coords.x - offset, coords.y, coords.z - offset, coords.x + offset, coords.y, coords.z + offset);
	}
	public static List<TileEntity> getTileEntitiesWithinAABB(World world, AxisAlignedBB bBox)
	{
		List<TileEntity> list = Lists.newArrayList();

		for (int i = (int) bBox.minX; i <= bBox.maxX; i++)
			for (int j = (int) bBox.minY; j <= bBox.maxY; j++)
				for (int k = (int) bBox.minZ; k <= bBox.maxZ; k++)
				{
					TileEntity tile = world.getTileEntity(i, j, k);
					if (tile != null)
					{
						list.add(tile);
					}
				}

		return list;
	}
	public static void playSoundAtPlayer(EntityPlayer p, String path, float volume,float pitch)
	{
		p.worldObj.playSoundAtEntity(p, path, volume, pitch);
	}
	public static void placeFluid(World world, EntityPlayer player, double  costPerPlace,int chargeLevel, Block block, Fluid fluid, int x,int y,int z,int side)
	{
		if(world == null || fluid == null || player == null)
		{
			return;
		}
		ForgeDirection fd = ForgeDirection.getOrientation(InventoryUtils.getOpSide(BlockPistonBase.determineOrientation(world, x, y, z, player)));
		if(fd == ForgeDirection.UP)
		{
			if(block.canPlaceBlockAt(world, x, y - 1, z) && InventoryUtils.useResource(player, costPerPlace, true))
			{
				world.setBlock(x, y - 1, z, block);
			}
		}
		else if(fd == ForgeDirection.DOWN)
		{
			int rad = chargeLevel == 0 ? 0 : (chargeLevel < 7 ? 1 : 2);

			System.out.println("Charge:"+chargeLevel);
			System.out.println("Rad:"+rad);

			for(int dx = -rad;dx <= rad;dx++)
			{
				for(int dz = -rad;dz <= rad;dz++)
				{
					int ox = x + dx;
					int oz = z + dz;
					if(block.canPlaceBlockAt(world, ox, y + 1, oz) && InventoryUtils.useResource(player, costPerPlace, true))
					{
						world.setBlock(ox, y + 1, oz, block);
					}
				}
			}
		}
		else
		{
			int length = chargeLevel + 1;
			int mx = x;
			int my = side == 0 ? y - 1 : (side == 1 ? y + 1 : y);
			int mz = z;
			for(int i = 0 ;i < length;i++)
			{
				if(block.canPlaceBlockAt(world, mx, my, mz) &&  InventoryUtils.useResource(player, costPerPlace, true))
				{
					world.setBlock(mx, my, mz, block);
				}
				mx += fd.offsetX;
				mz += fd.offsetZ;
			}
		}
	}

    public static boolean doInterdiction(World var1, double var2, double var3, double var4, boolean var5,float radius)
    {
    	boolean flag = false;

        float var6 = radius;
        List var7 = var1.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getBoundingBox((double)((float)var2 - var6), (double)((float)var3 - var6), (double)((float)var4 - var6), (double)((float)var2 + var6), (double)var3 + (double)var6, (double)((float)var4 + var6)));
        Iterator var9 = var7.iterator();

        while (var9.hasNext())
        {
            Entity var8 = (Entity)var9.next();
            PushEntities(var8, var2, var3, var4);
            flag = true;
        }

        List var15 = var1.getEntitiesWithinAABB(EntityArrow.class, AxisAlignedBB.getBoundingBox((double)((float)var2 - var6), (double)((float)var3 - var6), (double)((float)var4 - var6), (double)((float)var2 + var6), (double)var3 + (double)var6, (double)((float)var4 + var6)));
        Iterator var11 = var15.iterator();

        while (var11.hasNext())
        {
            Entity var10 = (Entity)var11.next();

            if (((EntityArrow)var10).shootingEntity != null && !(((EntityArrow)var10).shootingEntity instanceof EntityPlayer))
            {
                PushEntities(var10, var2, var3, var4);
                flag = true;
            }
        }

        List var14 = var1.getEntitiesWithinAABB(EntityLargeFireball.class, AxisAlignedBB.getBoundingBox((double)((float)var2 - var6), (double)((float)var3 - var6), (double)((float)var4 - var6), (double)((float)var2 + var6), (double)var3 + (double)var6, (double)((float)var4 + var6)));
        Iterator var13 = var14.iterator();

        while (var13.hasNext())
        {
            Entity var12 = (Entity)var13.next();
            PushEntities(var12, var2, var3, var4);
            flag = true;
        }

        List var16 = var1.getEntitiesWithinAABB(EntitySlime.class, AxisAlignedBB.getBoundingBox((double)((float)var2 - var6), (double)((float)var3 - var6), (double)((float)var4 - var6), (double)((float)var2 + var6), (double)var3 + (double)var6, (double)((float)var4 + var6)));
        Iterator var17 = var16.iterator();

        while (var17.hasNext())
        {
            Entity var18 = (Entity)var17.next();
            PushEntities(var18, var2, var3, var4);
            flag = true;
        }
        return flag;
    }

    private static void PushEntities(Entity var1, double var2, double var3, double var4)
    {
        if (!(var1 instanceof EntityPlayer))
        {
            double var6 = (double)((float)var2) - var1.posX;
            double var8 = (double)((float)var3) - var1.posY;
            double var10 = (double)((float)var4) - var1.posZ;
            double var12 = var6 * var6 + var8 * var8 + var10 * var10;
            var12 *= var12;

            if (var12 <= Math.pow(10.0D, 5.0D))
            {
                double var14 = -(var6 * 0.019999999552965164D / var12) * Math.pow(10.0D, 5.0D) * 1.2d;
                double var16 = -(var8 * 0.019999999552965164D / var12) * Math.pow(10.0D, 5.0D) * 0.7d;
                double var18 = -(var10 * 0.019999999552965164D / var12) * Math.pow(10.0D, 5.0D) * 1.2d;

                if (var14 > 0.0D)
                {
                    var14 = 0.22D;
                }
                else if (var14 < 0.0D)
                {
                    var14 = -0.22D;
                }

                if (var16 > 0.2D)
                {
                    var16 = 0.12000000000000001D;
                }
                else if (var16 < -0.1D)
                {
                    var16 = 0.12000000000000001D;
                }

                if (var18 > 0.0D)
                {
                    var18 = 0.22D;
                }
                else if (var18 < 0.0D)
                {
                    var18 = -0.22D;
                }

                var1.motionX += var14;
                var1.motionY += var16;
                var1.motionZ += var18;
            }
        }
    }
}
