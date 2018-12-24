package me.shedaniel.utils;

import me.shedaniel.gui.components.GuiConfigTextField;

import java.util.function.Function;

public class ConfigValue {
    
    private String name, category;
    private ValueType type;
    private Object object;
    private Function<Double, String> nameProvider;
    
    private ConfigValue(String name, String category, ValueType type, Object value) {
        this.name = name;
        this.category = category;
        this.type = type;
        this.object = value;
    }
    
    public String getName() {
        return name;
    }
    
    public String getCategory() {
        return category;
    }
    
    public ValueType getType() {
        return type;
    }
    
    public Object getObject() {
        return object;
    }
    
    public static ConfigValue createConfigValue(String category, String name, ValueType type, Object value) {
        if (type.equals(ValueType.SLIDER))
            throw new IllegalArgumentException("Use another method for sliders.");
        return new ConfigValue(name, category, type, value);
    }
    
    public static ConfigValue createSliderConfigValue(String category, String name, ValueType type, double position, Function<Double, String> nameProvider) {
        if (!type.equals(ValueType.SLIDER))
            throw new IllegalArgumentException("This method is only for sliders.");
        if (position < 0D || position > 1D)
            throw new IllegalArgumentException("position should be between 0.0 to 1.0. Currently = " + position);
        ConfigValue configValue = new ConfigValue(name, category, type, position);
        configValue.nameProvider = nameProvider;
        return configValue;
    }
    
    public ConfigValue clone() {
        ConfigValue configValue = new ConfigValue(name, category, type, object);
        configValue.nameProvider = nameProvider;
        return configValue;
    }
    
    public Function<Double, String> getSliderNameProvider() {
        return nameProvider;
    }
    
    public ConfigValue setObject(Object object) {
        this.object = object;
        return this;
    }
    
    @Override
    public String toString() {
        return String.format("ConfigValue[%s] in %s, type = %s, object = %s", getName(), getCategory(), getType().name(), String.valueOf(getObject()));
    }
    
    public enum ValueType {
        TEXT(GuiConfigTextField.TextFieldInputType.TEXT), NUMBER(GuiConfigTextField.TextFieldInputType.NUMBER), NUMBER_WITH_DECIMALS(GuiConfigTextField.TextFieldInputType.NUMBER_WITH_DECIMALS), BOOLEAN_CHECKBOX, BOOLEAN_BUTTON, SLIDER;
        
        private GuiConfigTextField.TextFieldInputType textFieldInputType;
        
        ValueType(GuiConfigTextField.TextFieldInputType textFieldInputType) {
            this.textFieldInputType = textFieldInputType;
        }
        
        ValueType() {
            this(null);
        }
        
        public GuiConfigTextField.TextFieldInputType getTextFieldInputType() {
            return textFieldInputType;
        }
        
    }
    
    public String getAsString() {
        return String.valueOf(getObject());
    }
    
    public long getAsLong() {
        return Long.parseLong(getAsString());
    }
    
    public double getAsDouble() {
        return Double.parseDouble(getAsString());
    }
    
    public boolean getAsBoolean() {
        return Boolean.parseBoolean(getAsString());
    }
    
}
