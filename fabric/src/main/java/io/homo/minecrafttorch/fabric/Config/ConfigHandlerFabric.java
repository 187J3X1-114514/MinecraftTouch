package io.homo.minecrafttorch.fabric.Config;

import io.homo.minecrafttouch.common.Config.ModConfigData;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

public class ConfigHandlerFabric {
    public static void init(){
        AutoConfig.register(ModConfigData.class, Toml4jConfigSerializer::new);
    }
}
