package eelimitedr.guis;

import org.lwjgl.opengl.GL11;

import eelimitedr.features.tileentities.TileEntityAggregator;
import eelimitedr.features.tileentities.TileEntityDMFurnace;
import eelimitedr.guis.containers.ContainerAggregator;
import eelimitedr.guis.containers.ContainerDMFurnace;
import eelimitedr.registry.AggregatorRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiDMFurnace extends GuiContainer
{
	private static final ResourceLocation texture = new ResourceLocation("eelimitedr","textures/gui/dmfurnace.png");

	TileEntityDMFurnace tile;
	public GuiDMFurnace(TileEntityDMFurnace tile,EntityPlayer p)
	{
		super(new ContainerDMFurnace(tile,p.inventory));
		this.tile = tile;
	}
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
		String s = StatCollector.translateToLocal("container.dmfurnace");
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        //this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 60, this.ySize - 96 + 2, 4210752);
    }
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		this.drawTexturedModalRect((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize);

		int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;

		if (this.tile.fuelBurnTimeRemaining != 0)
        {
            int i1 = this.tile.getFuelBurnTimeScaled(13);
            this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 1);
            i1 = this.tile.getProcessScaled(24);
            this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
        }
	}
}
