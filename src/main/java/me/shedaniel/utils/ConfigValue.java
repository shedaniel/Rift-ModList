package me.shedaniel.utils;

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
	
	public static ConfigValue createConfigValue(String category, String name, Object value) {
		for (ValueType type : ValueType.values())
			for (Class c : type.getValueClasses())
				if (c.isInstance(value))
					return new ConfigValue(name, category, type, value);
		throw new NullPointerException("Value not supported!");
	}
	
	public enum ValueType {
		INT(Integer.class, int.class), LONG(Long.class, long.class), FLOAT(Float.class, float.class), DOUBLE(Double.class, double.class), STRING(String.class);
		
		private Class[] c;
		
		ValueType(Class... c) {
			this.c = c;
		}
		
		public Class[] getValueClasses() {
			return c;
		}
	}
	
}
