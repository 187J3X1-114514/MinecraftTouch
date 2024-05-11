package io.homo.minecrafttorch.fabric.Config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.homo.minecrafttouch.common.Config.ModConfigData;
import me.shedaniel.autoconfig.AutoConfig;

public class ModMenu implements ModMenuApi{
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(ModConfigData.class, parent).get();
    }
}
