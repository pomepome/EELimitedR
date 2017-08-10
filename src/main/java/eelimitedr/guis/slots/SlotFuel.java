package eelimitedr.guis.slots;
import cpw.mods.fml.common.registry.GameRegistry;
import eelimitedr.registry.LocusRegistry;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;


public class SlotFuel extends Slot
{

	public SlotFuel(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return TileEntityFurnace.isItemFuel(stack);
	}

}
