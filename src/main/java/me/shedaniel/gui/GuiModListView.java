package me.shedaniel.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class GuiModListView extends GuiScreen {
    
    private RiftMod mod;
    
    public GuiModListView(RiftMod mod) {
        this.mod = mod;
    }
    
    @Override
    protected void initGui() {
        if (!mod.getUrl().equals("Unidentified")) {
            addButton(new GuiButton(601, this.width - 80, 20, 70, 20, I18n.format("riftmodlist.visit_website")) {
                @Override
                public void onClick(double mouseX, double mouseY) {
                    super.onClick(mouseX, mouseY);
                    try {
                        Util.getOSType().openURL(new URL(mod.getUrl()));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        addButton(new GuiButton(602, this.width - 85, this.height - 25, 80, 20, I18n.format("gui.done")) {
            @Override
            public void onClick(double mouseX, double mouseY) {
                GuiModListView.this.mc.displayGuiScreen(new GuiModList());
            }
        });
    }
    
    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.overlayBackground(0, 52, 64, 64, 64, 255, 255);
        Minecraft.getInstance().getTextureManager().bindTexture(mod.getModIcon());
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        Gui.drawModalRectWithCustomSizedTexture(10, 10, 0, 0, 32, 32, 32, 32);
        
        //Draw Dark thing
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        this.overlayBackground(52, this.height, 32, 32, 32, 255, 255);
        this.overlayBackground(this.width - 90, this.width, this.height - 30, this.height, 64, 64, 64, 255, 255);
        
        //Draw Shade
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
        GlStateManager.disableAlphaTest();
        GlStateManager.shadeModel(7425);
        GlStateManager.disableTexture2D();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(0, 52D + 4, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 0).endVertex();
        bufferbuilder.pos(this.width, 52D + 4, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 0).endVertex();
        bufferbuilder.pos(this.width, 52D, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
        bufferbuilder.pos(0, 52D, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
        tessellator.draw();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(this.width - 90 - 4, this.height - 30, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
        bufferbuilder.pos(this.width, this.height - 30, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
        bufferbuilder.pos(this.width, this.height - 30 - 4, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 0).endVertex();
        bufferbuilder.pos(this.width - 90 - 4, this.height - 30 - 4, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 0).endVertex();
        tessellator.draw();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(this.width - 90 - 4, this.height, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 0).endVertex();
        bufferbuilder.pos(this.width - 90, this.height, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 100).endVertex();
        bufferbuilder.pos(this.width - 90, this.height - 30 - 4, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 100).endVertex();
        bufferbuilder.pos(this.width - 90 - 4, this.height - 30 - 4, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableAlphaTest();
        GlStateManager.disableBlend();
        
        //Render info
        String modName = mod.getName();
        int j1 = fontRenderer.getStringWidth(modName);
        if (j1 > this.width - 48 - 90)
            modName = fontRenderer.trimStringToWidth(modName, this.width - 48 - 90 - 3) + "...";
        this.fontRenderer.drawStringWithShadow(modName, 48, 11, 16777215);
        String versions = I18n.format("riftmodlist.versions", mod.getVersions());
        j1 = fontRenderer.getStringWidth(versions);
        if (j1 > this.width - 48 - 90)
            versions = fontRenderer.trimStringToWidth(versions, this.width - 48 - 90 - 3) + "...";
        this.fontRenderer.drawStringWithShadow(versions, 48, 21, 8421504);
        if (!mod.getUrl().equals("Unidentified")) {
            String url = I18n.format("riftmodlist.url", mod.getUrl());
            j1 = fontRenderer.getStringWidth(url);
            if (j1 > this.width - 48 - 90)
                url = fontRenderer.trimStringToWidth(url, this.width - 48 - 90 - 3) + "...";
            this.fontRenderer.drawStringWithShadow(url, 48, 31, 8421504);
        }
        String id = I18n.format("riftmodlist.id", mod.getId());
        j1 = fontRenderer.getStringWidth(id);
        if (j1 > this.width - 48 * 2)
            id = fontRenderer.trimStringToWidth(id, this.width - 48 * 2 - 3) + "...";
        this.fontRenderer.drawStringWithShadow(id, 48, (float) (60), 8421504);
        String description = mod.getDescription();
        List<String> list = this.mc.fontRenderer.listFormattedStringToWidth(description, this.width - 48 * 2);
        for(int i1 = 0; i1 < list.size(); ++i1)
            this.fontRenderer.drawStringWithShadow(list.get(i1), 48, (float) (75 + 10 * i1), 16777215);
        
        super.render(mouseX, mouseY, partialTicks);
    }
    
    /**
     * Overlays the background to hide scrolled items
     */
    protected void overlayBackground(int startX, int endX, int startY, int endY, int red, int green, int blue, int startAlpha, int endAlpha) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        this.mc.getTextureManager().bindTexture(Gui.OPTIONS_BACKGROUND);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32.0F;
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos((double) startX, (double) endY, 0.0D).tex(0, (double) ((float) endY / 32.0F)).color(red, green, blue, endAlpha).endVertex();
        bufferbuilder.pos((double) endX, (double) endY, 0.0D).tex((double) ((float) (endX - startX) / 32.0F), (double) ((float) endY / 32.0F)).color(red, green, blue, endAlpha).endVertex();
        bufferbuilder.pos((double) endX, (double) startY, 0.0D).tex((double) ((float) (endX - startX) / 32.0F), (double) ((float) startY / 32.0F)).color(red, green, blue, startAlpha).endVertex();
        bufferbuilder.pos((double) startX, (double) startY, 0.0D).tex(0, (double) ((float) startY / 32.0F)).color(red, green, blue, startAlpha).endVertex();
        tessellator.draw();
    }
    
    protected void overlayBackground(int startY, int endY, int red, int green, int blue, int startAlpha, int endAlpha) {
        overlayBackground(0, this.width, startY, endY, red, green, blue, startAlpha, endAlpha);
    }
    
    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (p_keyPressed_1_ == 256 && this.allowCloseWithEscape()) {
            this.close();
            this.mc.displayGuiScreen(new GuiModList());
            return true;
        } else {
            return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
        }
    }
    
}
