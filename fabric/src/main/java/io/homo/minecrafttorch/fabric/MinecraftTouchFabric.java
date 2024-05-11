package io.homo.minecrafttorch.fabric;

import io.homo.minecrafttorch.fabric.Config.ConfigHandlerFabric;
import io.homo.minecrafttouch.common.MinecraftTouch;
import net.fabricmc.api.ModInitializer;

public class MinecraftTouchFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        MinecraftTouch.init();
        ConfigHandlerFabric.init();
    }
}