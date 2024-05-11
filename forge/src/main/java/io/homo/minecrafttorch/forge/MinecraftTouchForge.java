package io.homo.minecrafttorch.forge;

import io.homo.minecrafttouch.common.MinecraftTouch;
import net.minecraftforge.fml.common.Mod;

@Mod(MinecraftTouch.MOD_ID)
public class MinecraftTouchForge {
    public MinecraftTouchForge() {
        MinecraftTouch.init();
    }
}