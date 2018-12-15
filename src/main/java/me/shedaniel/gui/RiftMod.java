package me.shedaniel.gui;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.dimdev.riftloader.ModInfo;
import org.dimdev.riftloader.RiftLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipException;

public class RiftMod {
	
	private List<String> authors;
	private String id, name, verions, url, description;
	private ResourceLocation resourceLocation;
	
	public RiftMod(String id, File file) {
		this(id, id, file);
	}
	
	public RiftMod(String id, String name, File file) {
		this.id = id;
		this.name = name;
		this.verions = "Unidentified";
		this.url = "Unidentified";
		this.description = "A mod for Rift.";
		this.authors = new ArrayList<>();
		resourceLocation = new ResourceLocation("textures/misc/unknown_pack.png");
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getVerions() {
		return verions;
	}
	
	public void setVerions(String verions) {
		this.verions = verions;
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
		} catch (Exception e) {e.printStackTrace();}
		return defaultAnswer;
	}
	
}
