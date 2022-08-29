package de.miraisoft.ash2.config;

import de.miraisoft.ash2.AshMod;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = AshMod.MOD_ID)
public class AshConfig implements ConfigData {
    public boolean showHud = true;

    @ConfigEntry.ColorPicker
    public int hudColor = 0xeeeeee;

    public boolean showFps = true;

    public boolean showCoords = true;

    public boolean showDirection = true;
    
    public boolean showLightLevel = true;
    
    public boolean showBiome = true;
    
    public boolean showTime = true;
    
    public boolean conciseCoords = false;
}