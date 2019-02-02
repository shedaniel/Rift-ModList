package me.shedaniel.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GuiModListContent extends GuiSlot {
    
    private List<RiftMod> modList = new ArrayList<>();
    private GuiModList parent;
    private FontRenderer fontRenderer;
    private int currentIndex = -1;
    
    public GuiModListContent(GuiModList parent, String searchTerm) {
        super(parent.getMinecraftInstance(), parent.width, parent.height, 60, parent.height - 40, 40);
        this.parent = parent;
        this.modList = new ArrayList<>();
        this.fontRenderer = mc.fontRenderer;
        setShowSelectionBox(true);
        searchFilter(searchTerm);
    }
    
    @Override
    public int getListWidth() {
        return this.width - 48 * 2;
    }
    
    public int getCurrentIndex() {
        return currentIndex;
    }
    
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
    
    public List<RiftMod> getModList() {
        return modList;
    }
    
    @Override
    protected int getSize() {
        return modList.size();
    }
    
    @Override
    protected boolean isSelected(int slotIndex) {
        return slotIndex == currentIndex;
    }
    
    @Override
    protected void drawBackground() {
        parent.drawDefaultBackground();
    }
    
    protected int getY(int index) {
        return top + 4 - getAmountScrolled() + index * slotHeight + headerPadding;
    }
    
    protected int getX(int index) {
        return left + width / 2 - getListWidth() / 2 + 2;
    }
    
    @Override
    protected void drawSlot(int slotIndex, int xPos, int yPos, int heightIn, int mouseXIn, int mouseYIn, float partialTicks) {
        int i = this.getY(slotIndex);
        int j = this.getX(slotIndex);
        RiftMod mod = modList.get(slotIndex);
        Minecraft.getInstance().getTextureManager().bindTexture(mod.getModIcon());
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        Gui.drawModalRectWithCustomSizedTexture(j, i, 0, 0, 32, 32, 32, 32);
        String modName = mod.getName();
        int j1 = fontRenderer.getStringWidth(modName);
        if (j1 > 157)
            modName = fontRenderer.trimStringToWidth(modName, 157 - fontRenderer.getStringWidth("...")) + "...";
        this.fontRenderer.drawStringWithShadow(modName, (float) (j + 32 + 2), (float) (i + 1), 16777215);
        String authors = I18n.format("riftmodlist.authors");
        for(String name : mod.getAuthors()) {
            authors += " " + name;
        }
        if (mod.getAuthors().size() == 0)
            authors += " " + I18n.format("riftmodlist.noone");
        List<String> list = fontRenderer.listFormattedStringToWidth(authors, 157);
        for(String l : list) {
            fontRenderer.drawStringWithShadow(l, (float) (j + 32 + 2), (float) (i + 12 + 10 * list.indexOf(l)), 8421504);
        }
        new GuiButton(700 + slotIndex, left + width / 2 + getListWidth() / 2 - 61, i + 6, 50, 20, I18n.format("riftmodlist.view")) {}.render(mouseXIn, mouseYIn, partialTicks);
    }
    
    @Override
    protected int getContentHeight() {
        return this.getSize() * 40;
    }
    
    @Override
    public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
        int x = left + width / 2 + getListWidth() / 2 - 61, y = 0, index = 0;
        while (true) {
            if (getY(index) > p_mouseClicked_3_) {
                index--;
                y = getY(index);
                break;
            }
            index++;
        }
        if (p_mouseClicked_1_ > x && p_mouseClicked_1_ < x + 50 && p_mouseClicked_3_ > y + 6 && p_mouseClicked_3_ < y + 26 && index < modList.size()) {
            Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            Minecraft.getInstance().displayGuiScreen(new GuiModListView(modList.get(index)));
            currentIndex = -1;
            GuiModList.lastIndex = -1;
        } else if (isMouseInList(p_mouseClicked_1_, p_mouseClicked_3_)) {
            Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            currentIndex = index;
        }
        return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
    }
    
    @Override
    protected int getScrollBarX() {
        return this.width - 46;
    }
    
    public void searchFilter(String searchTerm) {
        String currentId = currentIndex > 0 ? modList.get(MathHelper.clamp(currentIndex, 0, modList.size() - 1)).getId() : "";
        List<RiftMod> modList = new ArrayList<>();
        if (searchTerm.replaceAll(" ", "").equals(""))
            modList = new LinkedList<>(GuiModList.modList);
        else
            for(RiftMod mod : GuiModList.modList) {
                List<String> list = new LinkedList<>(mod.getAuthors());
                list.addAll(Arrays.asList(new String[]{mod.getId(), mod.getDescription(), mod.getName(), mod.getUrl()}));
                if (hasMatch(searchTerm, list.toArray(new String[list.size()])))
                    modList.add(mod);
            }
        this.modList = new LinkedList<>(modList);
        int i = -1;
        for(int j = 0; j < modList.size(); j++) {
            RiftMod mod = modList.get(j);
            if (mod.getId().equals(currentId))
                i = j;
        }
        setCurrentIndex(i);
    }
    
    private boolean hasMatch(String searchTerm, String... items) {
        for(String i : items)
            if (i.toLowerCase().contains(searchTerm.toLowerCase()))
                return true;
        return false;
    }
    
}
