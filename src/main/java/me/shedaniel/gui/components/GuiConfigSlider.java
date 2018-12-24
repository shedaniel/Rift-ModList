package me.shedaniel.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public abstract class GuiConfigSlider extends GuiButton {
    
    private double sliderValue;
    public boolean dragging;
    
    public GuiConfigSlider(int i, int x, int y, double f) {
        super(i, x, y, 150, 20, "");
        this.sliderValue = f;
        this.displayString = getDisplayString(sliderValue);
    }
    
    @Override
    public int getHoverState(boolean flag) {
        return 0;
    }
    
    @Override
    protected void renderBg(Minecraft minecraft, int p_mouseDragged_2_, int p_mouseDragged_3_) {
        if (this.visible) {
            if (this.dragging) {
                this.sliderValue = ((double) (p_mouseDragged_2_ - (this.x + 4)) / (double) (this.width - 8));
                this.sliderValue = MathHelper.clamp(sliderValue, 0.0D, 1.0D);
                
                this.displayString = getDisplayString(this.sliderValue);
            }
            
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            drawTexturedModalRect(x
                            + (int) (sliderValue * (width - 8)),
                    this.y, 0, 66, 4, 20);
            drawTexturedModalRect(x
                            + (int) (sliderValue * (width - 8)) + 4,
                    this.y, 196, 66, 4, 20);
        }
    }
    
    @Override
    public void onClick(double p_mousePressed_1_, double p_mousePressed_3_) {
        sliderValue = ((double) (p_mousePressed_1_ - (this.x + 4)) / (double) (width - 8));
        sliderValue = MathHelper.clamp(sliderValue, 0.0D, 1.0D);
        
        this.displayString = getDisplayString(this.id);
        this.dragging = true;
    }
    
    protected abstract String getDisplayString(double sliderValue);
    
    @Override
    public boolean mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_3_2) {
        this.dragging = false;
        return super.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_3_2);
    }
    
}
