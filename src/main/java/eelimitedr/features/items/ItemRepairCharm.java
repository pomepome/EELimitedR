package eelimitedr.features.items;

import eelimitedr.features.items.interfaces.IAlchChestUpdate;
import eelimitedr.handlers.CommonHandler;
import eelimitedr.registry.RepairCharmRegistry;
import eelimitedr.registry.UnlocalizedRegistry;
import eelimitedr.utils.FixContainer;
import eelimitedr.utils.InventoryUtils;
import eelimitedr.utils.StackUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRepairCharm extends ItemEESpecial implements UnlocalizedRegistry,IAlchChestUpdate
{
	public ItemRepairCharm()
	{
		super(RepairCharmID);
	}

	@Override
	public void onUpdateInAlchChest(World world, IInventory inv, int x, int y, int z, ItemStack stack)
	{
		if(CommonHandler.ticks1s == 0)
		{
			for(int i = 0;i < inv.getSizeInventory();i++)
			{
				if(inv.getStackInSlot(i) == null)
				{
					continue;
				}
				ItemStack invStack = inv.getStackInSlot(i).copy();
				if(invStack.getItem().isRepairable() && invStack.getItemDamage() > 0)
				{
					invStack.setItemDamage(invStack.getItemDamage() - 1);
					inv.setInventorySlotContents(i, invStack);
					inv.markDirty();
				}
			}
		}
	}
	@Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
		if(!(par3Entity instanceof EntityPlayer))
		{
			return;
		}
		EntityPlayer player = (EntityPlayer)par3Entity;

		IInventory inv = player.inventory;
		for(int i = 0;i < inv.getSizeInventory();i++)
		{
			ItemStack invStack = inv.getStackInSlot(i);
			if(canRepair(inv,invStack))
			{
				ItemStack newStack = invStack.copy();
				newStack.setItemDamage(0);
				inv.setInventorySlotContents(i, newStack);
				inv.markDirty();
			}

		}
    }

	private boolean canRepair(IInventory inv, ItemStack stack)
	{
		if(stack == null || !stack.getItem().isRepairable())
		{
			return false;
		}

		ItemStack covStack = RepairCharmRegistry.getNewStack(stack.getItem());

		if(covStack == null)
		{
			return false;
		}

		int used = stack.getMaxDamage() - stack.getItemDamage();

		boolean flag = covStack.stackSize == 1 ? used == stack.getMaxDamage() - 1 : used <= stack.getMaxStackSize() / covStack.stackSize;

		if(!flag)
		{
			return false;
		}

		int consume = covStack.stackSize == 1 ? 1 : covStack.stackSize - 1;

		return InventoryUtils.useStackFromInv(inv, StackUtils.changeAmount(covStack,consume), true);
	}
}
