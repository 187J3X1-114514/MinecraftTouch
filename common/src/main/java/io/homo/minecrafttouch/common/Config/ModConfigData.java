package io.homo.minecrafttouch.common.Config;

import io.homo.minecrafttouch.common.MinecraftTouch;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.annotation.Config;
//我是ClothConfig的🐕 ((((
@Config(name = MinecraftTouch.MOD_ID)
public class ModConfigData implements ConfigData {
    @ConfigEntry.Category("Core")
    CoreConfig cc = new CoreConfig();
}
class CoreConfig implements ConfigData {
    boolean a = false;
}
