package me.shedaniel.gui;

import me.shedaniel.gui.components.GuiConfigCheckBox;
import me.shedaniel.gui.components.GuiConfigSlider;
import me.shedaniel.gui.components.GuiConfigOnOffButton;
import me.shedaniel.gui.components.GuiConfigTextField;
import me.shedaniel.utils.ConfigValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiEventHandler;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiConfigCategory extends GuiEventHandler {
    
    protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png");
    protected static final ResourceLocation ARROWS_TEXTURES = new ResourceLocation("textures/gui/resource_packs.png");
    
    private List<ConfigValue> configValues;
    private Map<ConfigValue, IGuiEventListener> listeners;
    private GuiConfigScreen gui;
    private int width;
    private String name;
    private boolean contracted;
    private int xPos, yPos;
    
    public GuiConfigCategory(String name, GuiConfigScreen gui) {
        this.name = name;
        this.configValues = new ArrayList<>();
        this.listeners = new HashMap<>();
        this.gui = gui;
        this.width = gui.width;
        this.contracted = false;
        this.xPos = -100;
        this.yPos = -100;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public List<ConfigValue> getConfigValues() {
        return configValues;
    }
    
    public Map<ConfigValue, IGuiEventListener> getListeners() {
        return listeners;
    }
    
    @Override
    protected List<IGuiEventListener> getChildren() {
        List<IGuiEventListener> a = new ArrayList<>();
        configValues.forEach(configValue -> {
            if (listeners.containsKey(configValue))
                a.add(listeners.get(configValue));
        });
        return a;
    }
    
    public int getSize() {
        if (contracted)
            return 20;
        return configValues.size() * 24 + 25;
    }
    
    public void initComponents() {
        for(int i = 0; i < configValues.size(); i++) {
            ConfigValue value = configValues.get(i);
            if (value.getType().equals(ConfigValue.ValueType.BOOLEAN_BUTTON)) {
                GuiConfigOnOffButton button = null;
                listeners.put(value, button = new GuiConfigOnOffButton(1000 + i, Minecraft.getInstance().fontRenderer.getStringWidth(value.getName() + ": ") + 16, 0, value.getAsBoolean()));
            } else if (value.getType().equals(ConfigValue.ValueType.BOOLEAN_CHECKBOX)) {
                GuiConfigCheckBox checkBox = null;
                listeners.put(value, checkBox = new GuiConfigCheckBox(1000 + i, 10, 0, value.getName(), value.getAsBoolean()));
            } else if (value.getType().equals(ConfigValue.ValueType.SLIDER)) {
                GuiConfigSlider slider = null;
                listeners.put(value, slider = new GuiConfigSlider(1000 + i, Minecraft.getInstance().fontRenderer.getStringWidth(value.getName() + ": ") + 16, 0, value.getAsDouble()) {
                    @Override
                    protected String getDisplayString(double sliderValue) {
                        return value.getSliderNameProvider().apply(sliderValue);
                    }
                });
            } else {
                GuiConfigTextField textField = null;
                listeners.put(value, textField = new GuiConfigTextField(value.getType().getTextFieldInputType(), 1000 + i, Minecraft.getInstance().fontRenderer, Minecraft.getInstance().fontRenderer.getStringWidth(value.getName() + ": ") + 16,
                        0, 200, 16, textField));
                textField.setMaxStringLength(256);
                try {
                    textField.setText(value.getAsString());
                } catch (Exception e) {
                    textField.setText("Can't load default value");
                }
            }
        }
    }
    
    public void drawCategory(int slotIndex, int xPos, int yPos, int heightIn, int mouseXIn, int mouseYIn, float partialTicks) {
        this.xPos = xPos;
        this.yPos = yPos;
        Minecraft.getInstance().getTextureManager().bindTexture(BUTTON_TEXTURES);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        
        int j = mouseYIn > yPos && mouseYIn < yPos + 20 ? 20 : 0;
        //Start and End
        this.drawTexturedModalRect(0, yPos, 0, 66 + j, 20, 20);
        this.drawTexturedModalRect(this.width - 20, yPos, 180, 66 + j, 20, 20);
        
        //Middle
        if (this.width > 39)
            for(int i = 20; i < width - 20; i += 10)
                this.drawTexturedModalRect(i, yPos, 10, 66 + j, 10, 20);
        
        this.drawString(Minecraft.getInstance().fontRenderer, this.name, 12, yPos + 6, mouseYIn > yPos && mouseYIn < yPos + 20 ? 16777120 : 14737632);
        
        j = mouseYIn > yPos && mouseYIn < yPos + 20 ? 32 : 0;
        Minecraft.getInstance().getTextureManager().bindTexture(ARROWS_TEXTURES);
        if (contracted)
            Gui.drawModalRectWithCustomSizedTexture(this.width - 18, yPos + 7, 114, 5 + j, 16, 16, 256, 256);
        else
            Gui.drawModalRectWithCustomSizedTexture(this.width - 18, yPos + 7, 82, 20 + j, 16, 16, 256, 256);
        if (!contracted)
            for(int i = 0; i < configValues.size(); i++) {
                ConfigValue value = configValues.get(i);
                IGuiEventListener listener = getChildren().get(i);
                if (listener instanceof GuiConfigTextField) {
                    this.drawString(Minecraft.getInstance().fontRenderer, value.getName() + ":", 10, yPos + 28 + i * 24, 14737632);
                    ((GuiConfigTextField) listener).y = (yPos + 28 + i * 24 - 4);
                    ((GuiConfigTextField) listener).drawTextField(mouseXIn, mouseYIn, partialTicks);
                } else if (listener instanceof GuiConfigCheckBox) {
                    ((GuiConfigCheckBox) listener).setY(yPos + 28 + i * 24);
                    ((GuiConfigCheckBox) listener).drawCheckBox(mouseXIn, mouseYIn, partialTicks);
                } else if (listener instanceof GuiConfigOnOffButton) {
                    this.drawString(Minecraft.getInstance().fontRenderer, value.getName() + ":", 10, yPos + 28 + i * 24, 14737632);
                    ((GuiConfigOnOffButton) listener).y = (yPos + 28 + i * 24 - 4);
                    ((GuiConfigOnOffButton) listener).render(mouseXIn, mouseYIn, partialTicks);
                } else if (listener instanceof GuiConfigSlider) {
                    this.drawString(Minecraft.getInstance().fontRenderer, value.getName() + ":", 10, yPos + 28 + i * 24, 14737632);
                    ((GuiConfigSlider) listener).y = (yPos + 28 + i * 24 - 4);
                    ((GuiConfigSlider) listener).render(mouseXIn, mouseYIn, partialTicks);
                }
            }
    }
    
    public boolean mouseClicked(double mouseX, double mouseY, int p_mouseClicked_5_) {
        if (mouseY > yPos && mouseY < yPos + 20) {
            contracted = !contracted;
            Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            return true;
        }
        for(ConfigValue configValue : configValues) {
            try {
                IGuiEventListener iguieventlistener = listeners.get(configValue);
                if (iguieventlistener.mouseClicked(mouseX, mouseY, p_mouseClicked_5_)) {
                    this.focusOn(iguieventlistener);
                    if (p_mouseClicked_5_ == 0)
                        this.setDragging(true);
                    return true;
                }
            } catch (Exception e) {
            }
        }
        return false;
    }
    
    @Override
    public void focusChanged(boolean focused) {
        if (focused) {
            for(Map.Entry<String, GuiConfigCategory> entry : gui.getCategories().entrySet()) {
                if (!entry.getValue().equals(this)) {
                    entry.getValue().setFocused(null);
                    for(IGuiEventListener listener : entry.getValue().getChildren())
                        listener.focusChanged(false);
                }
            }
        } else {
            for(IGuiEventListener listener : this.getChildren())
                listener.focusChanged(false);
        }
    }
}