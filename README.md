# Rift-ModList
This mod adds a mod list to your client.

Forked from https://gitlab.com/gbui/RiftModList

### Screenshot
![Screenshot](https://media.discordapp.net/attachments/480755664675667980/523507883330240524/unknown.png)

### For Developers
There is some stuff you can do to make your mod show more information for this mod:
You can add more data to your `riftmod.json` file.
```json
{
  "id": "riftmodlist",
  "name": "Rift Mod List",
  "version": "1.0-SNAPSHOT",
  "authors": [
    "Danielshe"
  ],
  "listeners": [
    "me.shedaniel.RiftModList"
  ],
  "url": "https://shedaniel.me",
  "description": "Adds a mod list to your client."
}
```
To add a icon for your mod. As the mod simply uses the `pack.png` file. To use a different file, add `icon_file` to your `riftmod.json` file like this:
```json
{
  "id": "riftmodlist",
  "name": "Rift Mod List",
  "version": "1.0-SNAPSHOT",
  "authors": [
    "Danielshe"
  ],
  "listeners": [
    "me.shedaniel.RiftModList"
  ],
  "url": "https://shedaniel.me",
  "description": "Adds a mod list to your client.",
  "icon_file": "custom_icon.png"
}
```
Then the mod will load from `custom_icon.png`. Please make the icon resolution as 128x128.
### Config Gui
Create a new class and implement it to `OpenModConfigListener`. Put the class in the `riftmod.json` with `"config_listener"`. <br>Demo here:
```java
public class MyModConfigListener implements OpenModConfigListener { 
    @Override 
    public void openConfigGui() { 
        
    } 
     
    @Override 
    public void onSave(List<ConfigValue> values) { 
        
    } 
     
    @Override 
    public boolean autoSaveOnGuiExit() { 
        return false; 
    }
}
```
```json
{
  "config_listener": "random.package.MyModConfigListener"
}
```
To use the mod newest config gui, do this:
```java
List<ConfigValue> list = new ArrayList<>();
list.add(ConfigValue.createConfigValue("category name", "name of the value", GuiConfigTextField.TextFieldInputType.TEXT, "default value")); 
Minecraft.getInstance().displayGuiScreen(RiftModList.getConfigScreen(this, RiftModList.guiModList, list, RiftModList.getModByID("MOD_ID")));
```
Enable `autoSaveOnGuiExit()` if you want it to save on force quit like pressing esc.

### Gradle Repo or idk
I can't get it to jitpack if you can help me kthx
