package me.shedaniel.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class GuiConfigOnOffButton extends GuiButton {
    
    protected boolean isSelected;
    
    public GuiConfigOnOffButton(int buttonId, int x, int y, boolean isSelected) {
        super(buttonId, x, y, "");
        this.width = 150;
        this.height = 20;
        this.isSelected = isSelected;
    }
    
    public boolean isSelected() {
        return isSelected;
    }
    
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    
    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            Minecraft minecraft = Minecraft.getInstance();
            FontRenderer fontrenderer = minecraft.fontRenderer;
            minecraft.getTextureManager().bindTexture(BUTTON_TEXTURES);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
            this.renderBg(minecraft, mouseX, mouseY);
            int j = 14737632;
            
            if (!this.enabled) {
                j = 10526880;
            } else if (isSelected) {
                j = 65280;
            } else {
                j = 16711680;
            }
            
            this.drawCenteredString(fontrenderer, (isSelected ? "true" : "false"), this.x + this.width / 2, this.y + (this.height - 8) / 2, j);
        }
    }
    
    @Override
    public void onClick(double mouseX, double mouseY) {
        this.isSelected = !this.isSelected;
    }
}
