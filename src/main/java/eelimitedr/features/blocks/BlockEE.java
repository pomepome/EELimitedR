package eelimitedr.features.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import eelimitedr.features.EELimitedR;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockEE extends Block {

	public BlockEE(String id)
	{
		this(id,Material.rock);

	}
	public BlockEE(String id,Material material)
	{
		super(material);
		this.setBlockName(id).setCreativeTab(EELimitedR.creativeTabEE).setBlockTextureName("eelimitedr:"+id);
		GameRegistry.registerBlock(this, id);
	}

}
