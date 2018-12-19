package me.shedaniel.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiConfigScreen extends GuiScreen {
	
	private GuiScreen parent;
	protected int top;
	protected double amountScrolled;
	private Map<String, GuiConfigCategory> categories;
	protected int initialClickY = -2;
	private boolean clickedScrollbar;
	private RiftMod mod;
	private List<IGuiEventListener> children = Lists.<IGuiEventListener>newArrayList();
	
	public GuiConfigScreen(GuiScreen parent, RiftMod mod, int width, int height) {
		this.parent = parent;
		this.mod = mod;
		this.width = width;
		this.height = height;
		this.top = 52;
		this.categories = new HashMap<>();
	}
	
	@Override
	public void tick() {
		for (Map.Entry<String, GuiConfigCategory> entry : getCategories().entrySet()) {
			entry.getValue().setWidth(this.width);
			entry.getValue().tick();
		}
	}
	
	public RiftMod getMod() {
		return mod;
	}
	
	public void resetCategories() {
		this.categories = new HashMap<>();
	}
	
	public void setCategories(Map<String, GuiConfigCategory> categories) {
		this.categories = categories;
	}
	
	public Map<String, GuiConfigCategory> getCategories() {
		return categories;
	}
	
	public void addListener(GuiConfigCategory gui) {
		this.children.add(gui);
	}
	
	@Override
	public List<IGuiEventListener> getChildren() {
		return this.children;
	}
	
	protected int getContentHeight() {
		int i = 600;
		for (Map.Entry<String, GuiConfigCategory> entry : getCategories().entrySet())
			i += entry.getValue().getSize();
		return i;
	}
	
	protected void bindAmountScrolled() {
		this.amountScrolled = MathHelper.clamp(this.amountScrolled, 0.0D, (double) this.getMaxScroll());
	}
	
	public int getMaxScroll() {
		return Math.max(0, this.getContentHeight() - this.top + 4);
	}
	
	public int getAmountScrolled() {
		return (int) amountScrolled;
	}
	
	public boolean isMouseInList(double mouseX, double mouseY) {
		return mouseY >= (double) this.top;
	}
	
	/**
	 * Scrolls the slot by the given amount. A positive value scrolls down, and a negative value scrolls up.
	 */
	public void scrollBy(int amount) {
		this.amountScrolled += (double) amount;
		this.bindAmountScrolled();
		this.initialClickY = -2;
	}
	
	protected int getScrollBarX() {
		return this.width - 8;
	}
	
	public int getListWidth() {
		return this.width - 48 - 48;
	}
	
	public int getX() {
		return this.width / 2 - this.getListWidth() / 2 + 2;
	}
	
	public int getY() {
		return this.top + 4 - (int) this.amountScrolled;
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		int k = getX();
		int l = getY();
		
		int i = this.getScrollBarX();
		int j = i + 6;
		this.bindAmountScrolled();
		GlStateManager.disableLighting();
		GlStateManager.disableFog();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		this.overlayBackground(this.top, this.height, 32, 32, 32, 255, 255);
		this.drawSelectionBox(k, l, mouseX, mouseY, partialTicks);
		this.overlayBackground(0, this.top, 64, 64, 64, 255, 255);
		
		String modName = mod.getName();
		if (fontRenderer.getStringWidth(modName) > this.width - 48 - 90)
			modName = fontRenderer.trimStringToWidth(modName, this.width - 48 - 90 - 3) + "...";
		this.fontRenderer.drawStringWithShadow(modName, 48, 11, 16777215);
		mc.getTextureManager().bindTexture(mod.getModIcon());
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		Gui.drawModalRectWithCustomSizedTexture(10, 10, 0, 0, 32, 32, 32, 32);
		
		GlStateManager.disableDepthTest();
		GlStateManager.enableBlend();
		GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
		GlStateManager.disableAlphaTest();
		GlStateManager.shadeModel(7425);
		GlStateManager.disableTexture2D();
		int i1 = 4;
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		bufferbuilder.pos(0, this.top + 4, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 0).endVertex();
		bufferbuilder.pos(this.width, this.top + 4, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 0).endVertex();
		bufferbuilder.pos(this.width, this.top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
		bufferbuilder.pos(0, this.top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
		tessellator.draw();
		int j1 = this.getMaxScroll();
		
		if (j1 > 0) {
			int k1 = (int) ((float) ((this.height - this.top) * (this.height - this.top)) / (float) this.getContentHeight());
			k1 = MathHelper.clamp(k1, 32, this.height - this.top - 8);
			int l1 = (int) this.amountScrolled * (this.height - this.top - k1) / j1 + this.top;
			
			if (l1 < this.top) {
				l1 = this.top;
			}
			
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
			bufferbuilder.pos((double) i, (double) this.height, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
			bufferbuilder.pos((double) j, (double) this.height, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
			bufferbuilder.pos((double) j, (double) this.top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
			bufferbuilder.pos((double) i, (double) this.top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
			tessellator.draw();
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
			bufferbuilder.pos((double) i, (double) (l1 + k1), 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
			bufferbuilder.pos((double) j, (double) (l1 + k1), 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
			bufferbuilder.pos((double) j, (double) l1, 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
			bufferbuilder.pos((double) i, (double) l1, 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
			tessellator.draw();
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
			bufferbuilder.pos((double) i, (double) (l1 + k1 - 1), 0.0D).tex(0.0D, 1.0D).color(192, 192, 192, 255).endVertex();
			bufferbuilder.pos((double) (j - 1), (double) (l1 + k1 - 1), 0.0D).tex(1.0D, 1.0D).color(192, 192, 192, 255).endVertex();
			bufferbuilder.pos((double) (j - 1), (double) l1, 0.0D).tex(1.0D, 0.0D).color(192, 192, 192, 255).endVertex();
			bufferbuilder.pos((double) i, (double) l1, 0.0D).tex(0.0D, 0.0D).color(192, 192, 192, 255).endVertex();
			tessellator.draw();
		}
		this.renderDecorations(mouseX, mouseY);
		GlStateManager.enableTexture2D();
		GlStateManager.shadeModel(7424);
		GlStateManager.enableAlphaTest();
		GlStateManager.disableBlend();
	}
	
	public int getSize() {
		return this.categories.entrySet().size();
	}
	
	protected void updateItemPos(int entryID, int insideLeft, int yPos, float partialTicks) {
	}
	
	protected void drawSelectionBox(int insideLeft, int insideTop, int mouseXIn, int mouseYIn, float partialTicks) {
		int i = this.getSize();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		
		int j = 0;
		int m = insideTop;
		for (Map.Entry<String, GuiConfigCategory> entry : getCategories().entrySet()) {
			GuiConfigCategory category = entry.getValue();
			
			this.drawSlot(entry.getKey(), j, insideLeft, m, category.getSize(), mouseXIn, mouseYIn, partialTicks);
			m += category.getSize();
			j++;
		}
	}
	
	protected void drawSlot(String categoryName, int slotIndex, int xPos, int yPos, int heightIn, int mouseXIn, int mouseYIn, float partialTicks) {
		categories.get(categoryName).drawCategory(slotIndex, xPos, yPos, heightIn, mouseXIn, mouseYIn, partialTicks);
	}
	
	protected void checkScrollbarClick(double mouseX, double mouseY, int button) {
		this.clickedScrollbar = button == 0 && mouseX >= (double) this.getScrollBarX() && mouseX < (double) (this.getScrollBarX() + 6);
	}
	
	@Override
	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
		this.checkScrollbarClick(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
		
		if (this.isMouseInList(p_mouseClicked_1_, p_mouseClicked_3_)) {
			int i = this.getEntryAt(p_mouseClicked_1_, p_mouseClicked_3_);
			
			if (i == -1 && p_mouseClicked_5_ == 0) {
				return true;
			} else if (i == -1) {
				return this.clickedScrollbar;
			}
		}
		
		for (IGuiEventListener iguieventlistener : this.children) {
			boolean flag = iguieventlistener.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
			if (flag) {
				this.focusOn(iguieventlistener);
				if (p_mouseClicked_5_ == 0)
					this.setDragging(true);
				
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
		if (this.getFocused() != null) {
			this.getFocused().mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
		}
		
		this.getChildren().forEach((p_195081_5_) ->
		{
			p_195081_5_.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
		});
		return false;
	}
	
	@Override
	public boolean mouseDragged(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_) {
		if (super.mouseDragged(p_mouseDragged_1_, p_mouseDragged_3_, p_mouseDragged_5_, p_mouseDragged_6_, p_mouseDragged_8_)) {
			return true;
		} else if (p_mouseDragged_5_ == 0 && this.clickedScrollbar) {
			if (p_mouseDragged_3_ < (double) this.top) {
				this.amountScrolled = 0.0D;
			} else if (p_mouseDragged_3_ > (double) this.width) {
				this.amountScrolled = (double) this.getMaxScroll();
			} else {
				double d0 = (double) this.getMaxScroll();
				
				if (d0 < 1.0D)
					d0 = 1.0D;
				
				int i = (int) ((float) ((this.width - this.top) * (this.width - this.top)) / (float) this.getContentHeight());
				i = MathHelper.clamp(i, 32, this.width - this.top - 8);
				double d1 = d0 / (double) (this.width - this.top - i);
				
				if (d1 < 1.0D)
					d1 = 1.0D;
				
				this.amountScrolled += p_mouseDragged_8_ * d1;
				this.bindAmountScrolled();
			}
			return true;
		}
		return false;
	}
	
	@Override
	public boolean mouseScrolled(double p_mouseScrolled_1_) {
		this.amountScrolled -= p_mouseScrolled_1_ * 20;
		return true;
	}
	
	public int getEntryAt(double x, double y) {
		int i = this.width / 2 - this.getListWidth() / 2;
		int j = this.width / 2 + this.getListWidth() / 2;
		int k = MathHelper.floor(y - (double) this.top) + (int) this.amountScrolled - 4;
		int l = -1;
		int m = 0;
		for (Map.Entry<String, GuiConfigCategory> entry : getCategories().entrySet()) {
			m += entry.getValue().getSize();
			l++;
			if (m > k)
				break;
		}
		return x < (double) this.getScrollBarX() && x >= (double) i && x <= (double) j && l >= 0 && k >= 0 && l < this.getSize() ? l : -1;
	}
	
	protected void renderDecorations(int mouseXIn, int mouseYIn) {
	}
	
	protected void overlayBackground(int startY, int endY, int red, int green, int blue, int startAlpha, int endAlpha) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		this.mc.getTextureManager().bindTexture(Gui.OPTIONS_BACKGROUND);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		float f = 32.0F;
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		bufferbuilder.pos((double) 0, (double) endY, 0.0D).tex(0.0D, (double) ((float) endY / 32.0F)).color(red, green, blue, endAlpha).endVertex();
		bufferbuilder.pos((double) (0 + this.width), (double) endY, 0.0D).tex((double) ((float) this.width / 32.0F), (double) ((float) endY / 32.0F)).color(red, green, blue, endAlpha).endVertex();
		bufferbuilder.pos((double) (0 + this.width), (double) startY, 0.0D).tex((double) ((float) this.width / 32.0F), (double) ((float) startY / 32.0F)).color(red, green, blue, startAlpha).endVertex();
		bufferbuilder.pos((double) 0, (double) startY, 0.0D).tex(0.0D, (double) ((float) startY / 32.0F)).color(red, green, blue, startAlpha).endVertex();
		tessellator.draw();
	}
	
	@Override
	public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
		if (p_keyPressed_1_ == 256 && this.allowCloseWithEscape()) {
			this.close();
			this.mc.displayGuiScreen(parent);
			return true;
		} else {
			return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
		}
	}
	
}
