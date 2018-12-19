package me.shedaniel.utils;

import me.shedaniel.gui.components.GuiConfigTextField;

public class ConfigValue {
	
	private String name, category;
	private GuiConfigTextField.TextFieldInputType type;
	private Object object;
	
	private ConfigValue(String name, String category, GuiConfigTextField.TextFieldInputType type, Object value) {
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
	
	public GuiConfigTextField.TextFieldInputType getType() {
		return type;
	}
	
	public Object getObject() {
		return object;
	}
	
	public static ConfigValue createConfigValue(String category, String name, GuiConfigTextField.TextFieldInputType type, Object value) {
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
}
