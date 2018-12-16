# Rift-ModList
This mod adds a mod list to your client.

Forked from https://gitlab.com/gbui/RiftModList

### Screenshot
![Screenshot](https://media.discordapp.net/attachments/480755664675667980/523507883330240524/unknown.png)

### For Developers
There is some stuff you can do to make your mod show more information for this mod:
You can add more data to your `riftmod.json` file.
```
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
```
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
Then the mod will load from `custom_icon.png`. Please make the icon resolution as 128x128.<br><br>
To enable the config button on your mod:<br>
Create a static method somewhere like this:
```java
public static void randomMethod() {
  //Open your idk gui for config
}
```
Then add `configure_gui` to your `riftmod.json`. The value should be `random.package.YourClass$randomMethod`. I know this is ugly but deal with it now, I might change in the future.

I would love to receive Pull Requests :)
