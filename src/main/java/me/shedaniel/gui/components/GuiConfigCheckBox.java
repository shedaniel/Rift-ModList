package me.shedaniel.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.SoundEvents;

public class GuiConfigCheckBox extends Gui implements IGuiEventListener {
    
    private int id, x, y;
    private boolean visible, enabled, isSelected;
    private String displayName;
    private final FontRenderer fontRenderer;
    
    public GuiConfigCheckBox(int id, int x, int y, String displayName, boolean isSelected) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.displayName = displayName;
        this.isSelected = isSelected;
        this.visible = true;
        this.enabled = true;
        this.fontRenderer = Minecraft.getInstance().fontRenderer;
    }
    
    public void drawCheckBox(int mouseX, int mouseY, float partialTicks) {
        if (isVisible()) {
            drawRect(this.x, this.y - 4, this.x + 16, this.y - 4 + 16, -6250336);
            drawRect(this.x + 1, this.y - 4 + 1, this.x + 16 - 1, this.y - 4 + 16 - 1, -16777216);
            
            if (isSelected()) {
                drawRect(this.x + 4, this.y - 4 + 4, this.x + 16 - 4, this.y - 4 + 16 - 4, -6250336);
            }
            
            fontRenderer.drawStringWithShadow(getDisplayName(), x + 24, y, 14737632);
        }
    }
    
    @Override
    public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
        if (isEnabled() && isVisible()) {
            boolean flag = p_mouseClicked_1_ >= this.x && p_mouseClicked_1_ < this.x + 16 && p_mouseClicked_3_ >= this.y - 4 && p_mouseClicked_3_ < this.y + 16 - 4;
            if (flag && p_mouseClicked_5_ == 0) {
                this.setSelected(!isSelected());
                Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                return true;
            }
        }
        return false;
    }
    
    public boolean isSelected() {
        return isSelected;
    }
    
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    public boolean isVisible() {
        return visible;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public int getId() {
        return id;
    }
    
    @Override
    public void focusChanged(boolean focused) {
    
    }
    
    public static void drawRect(int left, int top, int right, int bottom, int color) {
        if (left < right) {
            int i = left;
            left = right;
            right = i;
        }
        
        if (top < bottom) {
            int j = top;
            top = bottom;
            bottom = j;
        }
        
        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color4f(f, f1, f2, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos((double) left, (double) bottom, 0.0D).endVertex();
        bufferbuilder.pos((double) right, (double) bottom, 0.0D).endVertex();
        bufferbuilder.pos((double) right, (double) top, 0.0D).endVertex();
        bufferbuilder.pos((double) left, (double) top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    @Override
    public boolean canFocus() {
        return true;
    }
}
