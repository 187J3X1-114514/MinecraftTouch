package io.homo.minecrafttouch.common.Control;

import net.minecraft.client.Minecraft;

public class TouchHandler extends net.minecraft.client.MouseHandler{
    private final Minecraft minecraft;
    public TouchHandler(Minecraft pMinecraft) {
        super(pMinecraft);
        this.minecraft = pMinecraft;
    }
}
