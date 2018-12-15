package me.shedaniel.gui;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class RiftMod {
	
	@Nullable
	private NativeImage nativeImage;
	private List<String> authors;
	private String id, name, versions, url, description;
	private ResourceLocation resourceLocation;
	
	public RiftMod(String id, File file) {
		this(id, id, file);
	}
	
	public RiftMod(String id, String name, File file) {
		this.id = id;
		this.name = name;
		this.versions = "Unidentified";
		this.url = "Unidentified";
		this.description = "A mod for Rift.";
		this.authors = new ArrayList<>();
		this.nativeImage = null;
		tryLoadPackIcon(file);
	}
	
	private void tryLoadPackIcon(File file) {
		if (!file.isFile()) return;
		try (JarFile jar = new JarFile(file)) {
			JarEntry entry = jar.getJarEntry("pack.png");
			if (entry != null) {
				InputStream inputStream = jar.getInputStream(entry);
				this.nativeImage = NativeImage.read(inputStream);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getVersions() {
		return versions;
	}
	
	public void setVersions(String versions) {
		this.versions = versions;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
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
		if (this.resourceLocation == null) {
			if (this.nativeImage == null)
				this.resourceLocation = new ResourceLocation("textures/misc/unknown_pack.png");
			else
				this.resourceLocation = Minecraft.getInstance().getTextureManager().getDynamicTextureLocation("texturepackicon", new DynamicTexture(this.nativeImage));
		}
		return resourceLocation;
	}
	
	@Override
	public String toString() {
		return "RiftMod(" + id + ")[" + name + "]: " + authors.toString();
	}
	
	public static String loadValueFromJar(File file, String value) {
		return loadValueFromJar(file, value, "Unidentified");
	}
	
	public static String loadValueFromJar(File file, String value, String defaultAnswer) {
		if (!file.isFile()) return defaultAnswer;
		try (JarFile jar = new JarFile(file)) {
			JarEntry entry = jar.getJarEntry("riftmod.json");
			if (entry != null) {
				InputStream inputStream = jar.getInputStream(entry);
				JsonElement element = new JsonParser().parse(new InputStreamReader(inputStream));
				JsonObject object = element.getAsJsonObject();
				if (object.has(value))
					return object.get(value).getAsString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultAnswer;
	}
	
}
