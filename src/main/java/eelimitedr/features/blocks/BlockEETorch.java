package eelimitedr.features.blocks;

import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import eelimitedr.registry.UnlocalizedRegistry;
import eelimitedr.utils.WorldUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEETorch extends BlockEE implements UnlocalizedRegistry
{
    private int powerCycle;

    public BlockEETorch()
    {
        super(EETorchID, Material.circuits);
        this.setTickRandomly(true);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        return null;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    public int getLightValue(IBlockAccess var1, int var2, int var3, int var4)
    {
        return 15;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 2;
    }

    private boolean canPlaceTorchOn(World var1, int var2, int var3, int var4)
    {
        if (var1.doesBlockHaveSolidTopSurface(var1, var2, var3, var4))
        {
            return true;
        }
        else
        {
            Block var5 = var1.getBlock(var2, var3, var4);
            return (var5 != null && var5.canPlaceTorchOnTop(var1, var2, var3, var4));
        }
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return par1World.isSideSolid(par2 - 1, par3, par4, EAST,  true) ||
               par1World.isSideSolid(par2 + 1, par3, par4, WEST,  true) ||
               par1World.isSideSolid(par2, par3, par4 - 1, SOUTH, true) ||
               par1World.isSideSolid(par2, par3, par4 + 1, NORTH, true) ||
               canPlaceTorchOn(par1World, par2, par3 - 1, par4);
    }
    /**
     * Called when a block is placed using an item. Used often for taking the facing and figuring out how to position
     * the item. Args: x, y, z, facing
     */
    public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        int var10 = par9;

        if (par5 == 1 && this.canPlaceTorchOn(par1World, par2, par3 - 1, par4))
        {
            var10 = 5;
        }

        if (par5 == 2 && par1World.isSideSolid(par2, par3, par4 + 1, NORTH, true))
        {
            var10 = 4;
        }

        if (par5 == 3 && par1World.isSideSolid(par2, par3, par4 - 1, SOUTH, true))
        {
            var10 = 3;
        }

        if (par5 == 4 && par1World.isSideSolid(par2 + 1, par3, par4, WEST, true))
        {
            var10 = 2;
        }

        if (par5 == 5 && par1World.isSideSolid(par2 - 1, par3, par4, EAST, true))
        {
            var10 = 1;
        }

        par1World.scheduleBlockUpdate(par2, par3, par4, this, 1);
        return var10;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {
        super.updateTick(var1, var2, var3, var4, var5);

        if (this.powerCycle > 0)
        {
            WorldUtils.doInterdiction(var1, var2, var3, var4, true, 5f);
            --this.powerCycle;
        }

        WorldUtils.doInterdiction(var1, var2, var3, var4, false, 5f);

        if (var1.getBlockMetadata(var2, var3, var4) == 0)
        {
            this.onBlockAdded(var1, var2, var3, var4);
        }

        var1.scheduleBlockUpdate(var2, var3, var4, this, 1);
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        if (par1World.getBlockMetadata(par2, par3, par4) == 0)
        {
            if (par1World.isSideSolid(par2 - 1, par3, par4, EAST, true))
            {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, 1, 2);
            }
            else if (par1World.isSideSolid(par2 + 1, par3, par4, WEST, true))
            {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
            }
            else if (par1World.isSideSolid(par2, par3, par4 - 1, SOUTH, true))
            {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
            }
            else if (par1World.isSideSolid(par2, par3, par4 + 1, NORTH, true))
            {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
            }
            else if (this.canPlaceTorchOn(par1World, par2, par3 - 1, par4))
            {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
            }
        }

        this.dropTorchIfCantStay(par1World, par2, par3, par4);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4,Block b)
    {
        var1.scheduleBlockUpdate(var2, var3, var4, this, 1);
        int var6;

        if (var1.isBlockIndirectlyGettingPowered(var2, var3, var4))
        {
            for (var6 = 0; var6 <= 2; ++var6)
            {
                WorldUtils.doInterdiction(var1, var2, var3, var4, true, 10);
            }

            this.powerCycle = 16;
        }

        if (this.func_150109_e(var1, var2, var3, var4))
        {
            int l = var1.getBlockMetadata(var2, var3, var4);
            boolean flag = false;

            if (!var1.isSideSolid(var2 - 1, var3, var4, EAST, true) && l == 1)
            {
                flag = true;
            }

            if (!var1.isSideSolid(var2 + 1, var3, var4, WEST, true) && l == 2)
            {
                flag = true;
            }

            if (!var1.isSideSolid(var2, var3, var4 - 1, SOUTH, true) && l == 3)
            {
                flag = true;
            }

            if (!var1.isSideSolid(var2, var3, var4 + 1, NORTH, true) && l == 4)
            {
                flag = true;
            }

            if (!this.func_150107_m(var1, var2, var3 - 1, var4) && l == 5)
            {
                flag = true;
            }

            if (flag)
            {
                this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4), 0);
                var1.setBlockToAir(var2, var3, var4);
                return;
            }
            else
            {
                return;
            }
        }
        else
        {
            return;
        }
    }
    private boolean func_150107_m(World p_150107_1_, int p_150107_2_, int p_150107_3_, int p_150107_4_)
    {
        if (World.doesBlockHaveSolidTopSurface(p_150107_1_, p_150107_2_, p_150107_3_, p_150107_4_))
        {
            return true;
        }
        else
        {
            Block block = p_150107_1_.getBlock(p_150107_2_, p_150107_3_, p_150107_4_);
            return block.canPlaceTorchOnTop(p_150107_1_, p_150107_2_, p_150107_3_, p_150107_4_);
        }
    }
    protected boolean func_150109_e(World p_150109_1_, int p_150109_2_, int p_150109_3_, int p_150109_4_)
    {
        if (!this.canPlaceBlockAt(p_150109_1_, p_150109_2_, p_150109_3_, p_150109_4_))
        {
            if (p_150109_1_.getBlock(p_150109_2_, p_150109_3_, p_150109_4_) == this)
            {
                this.dropBlockAsItem(p_150109_1_, p_150109_2_, p_150109_3_, p_150109_4_, p_150109_1_.getBlockMetadata(p_150109_2_, p_150109_3_, p_150109_4_), 0);
                p_150109_1_.setBlockToAir(p_150109_2_, p_150109_3_, p_150109_4_);
            }

            return false;
        }
        else
        {
            return true;
        }
    }
    private boolean dropTorchIfCantStay(World var1, int var2, int var3, int var4)
    {
        if (!this.canPlaceBlockAt(var1, var2, var3, var4))
        {
            this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4), 1);
            var1.setBlock(var2, var3, var4, Blocks.air);
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    public MovingObjectPosition collisionRayTrace(World var1, int var2, int var3, int var4, Vec3 var5, Vec3 var6)
    {
        int var7 = var1.getBlockMetadata(var2, var3, var4) & 7;
        float var8 = 0.15F;

        if (var7 == 1)
        {
            this.setBlockBounds(0.0F, 0.2F, 0.5F - var8, var8 * 2.0F, 0.8F, 0.5F + var8);
        }
        else if (var7 == 2)
        {
            this.setBlockBounds(1.0F - var8 * 2.0F, 0.2F, 0.5F - var8, 1.0F, 0.8F, 0.5F + var8);
        }
        else if (var7 == 3)
        {
            this.setBlockBounds(0.5F - var8, 0.2F, 0.0F, 0.5F + var8, 0.8F, var8 * 2.0F);
        }
        else if (var7 == 4)
        {
            this.setBlockBounds(0.5F - var8, 0.2F, 1.0F - var8 * 2.0F, 0.5F + var8, 0.8F, 1.0F);
        }
        else
        {
            float var9 = 0.1F;
            this.setBlockBounds(0.5F - var9, 0.0F, 0.5F - var9, 0.5F + var9, 0.6F, 0.5F + var9);
        }

        return super.collisionRayTrace(var1, var2, var3, var4, var5, var6);
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5)
    {
        var1.scheduleBlockUpdate(var2, var3, var4, this, 1);
        int var6 = var1.getBlockMetadata(var2, var3, var4);
        double var7 = (double)((float)var2 + 0.5F);
        double var9 = (double)((float)var3 + 0.7F);
        double var11 = (double)((float)var4 + 0.5F);
        double var13 = 0.2199999988079071D;
        double var15 = 0.27000001072883606D;

        if (var6 == 1)
        {
            var1.spawnParticle("smoke", var7 - var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
        }
        else if (var6 == 2)
        {
            var1.spawnParticle("smoke", var7 + var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
        }
        else if (var6 == 3)
        {
            var1.spawnParticle("smoke", var7, var9 + var13, var11 - var15, 0.0D, 0.0D, 0.0D);
        }
        else if (var6 == 4)
        {
            var1.spawnParticle("smoke", var7, var9 + var13, var11 + var15, 0.0D, 0.0D, 0.0D);
        }
        else
        {
            var1.spawnParticle("smoke", var7, var9, var11, 0.0D, 0.0D, 0.0D);
        }
    }
}