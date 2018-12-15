package me.shedaniel.gui;

import net.minecraft.util.ResourceLocation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RiftMod {
	
	private List<String> authors;
	private String id, name;
	private ResourceLocation resourceLocation;
	
	public RiftMod(String id, File file) {
		this(id, id, file);
	}
	
	public RiftMod(String id, String name, File file) {
		this.id = id;
		this.name = name;
		this.authors = new ArrayList<>();
		resourceLocation = new ResourceLocation("textures/misc/unknown_pack.png");
	}
	
	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public List<String> getAuthors() {
		return authors;
	}
	
	public ResourceLocation getModIcon() {
		return resourceLocation;
	}
	
	@Override
	public String toString() {
		return "RiftMod(" + id + ")[" + name + "]: " + authors.toString();
	}
}
