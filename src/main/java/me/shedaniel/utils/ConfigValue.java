package me.shedaniel.utils;

import me.shedaniel.gui.components.GuiConfigTextField;

public class ConfigValue {
	
	private String name, category;
	private ValueType type;
	private Object object;
	
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
		return new ConfigValue(name, category, type, value);
	}
	
	public ConfigValue clone() {
		return new ConfigValue(name, category, type, object);
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
		TEXT(GuiConfigTextField.TextFieldInputType.TEXT), NUMBER(GuiConfigTextField.TextFieldInputType.NUMBER), NUMBER_WITH_DECIMALS(GuiConfigTextField.TextFieldInputType.NUMBER_WITH_DECIMALS), BOOLEAN;
		
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
