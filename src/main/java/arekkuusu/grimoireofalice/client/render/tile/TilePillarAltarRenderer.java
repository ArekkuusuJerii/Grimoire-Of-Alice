package arekkuusu.grimoireofalice.client.render.tile;

import arekkuusu.grimoireofalice.block.tile.TilePillarAltar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

public class TilePillarAltarRenderer extends TileEntitySpecialRenderer<TilePillarAltar> {

	@Override
	public void renderTileEntityAt(TilePillarAltar te, double x, double y, double z, float partialTicks, int destroyStage) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5F, y + 1.5, z + 0.5);
		GlStateManager.scale(1.5, 1.5, 1.5);
		GlStateManager.rotate(te.tickCount, 0F, 1F, 0F);
		ItemStack stack = te.getItemStack();
		Minecraft mc = Minecraft.getMinecraft();
		if(stack != null) {
			mc.getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
		}
		GlStateManager.popMatrix();
	}
}