package me.shedaniel.gui;

import com.sun.org.apache.xml.internal.serializer.ToHTMLStream;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class RiftMod {
	
	private List<String> authors;
	private String id, name;
	
	public RiftMod(String id) {
		this(id, id);
	}
	
	public RiftMod(String id, String name) {
		this.id = id;
		this.name = name;
		this.authors = new ArrayList<>();
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
		return new ResourceLocation("textures/misc/unknown_pack.png");
	}
	
	@Override
	public String toString() {
		return "RiftMod(" + id + ")[" + name + "]: " + authors.toString();
	}
}
