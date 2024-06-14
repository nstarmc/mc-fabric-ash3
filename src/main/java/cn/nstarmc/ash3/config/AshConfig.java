package cn.nstarmc.ash3.config;

import cn.nstarmc.ash3.AshMod;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = AshMod.MOD_ID)
public class AshConfig implements ConfigData {
    public boolean showHud = true;

    @ConfigEntry.ColorPicker
    public int hudColor = 0xeeeeee;
    
    /**
     * ARGB color: alpha / red / green / blue
     */
    public int hudBackgroundColor = 0x77000000;

    public boolean showFps = true;

    public boolean showCoords = true;

    public boolean showDirection = true;
    
    public boolean showLightLevel = true;
    
    public boolean showBiome = true;
    
    public boolean showTime = true;
    
    public boolean conciseCoords = false;
    
    public boolean showBackground = true;
}