package me.shedaniel;

import me.shedaniel.gui.GuiModList;
import org.dimdev.riftloader.listener.InitializationListener;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

public class RiftModList implements InitializationListener {
	
	public static GuiModList guiModList;
	
	@Override
	public void onInitialization() {
		MixinBootstrap.init();
		Mixins.addConfiguration("mixins.riftmodlist.json");
	}
	
}
