package io.homo.minecrafttouch.common;

import com.mojang.logging.LogUtils;
import io.homo.minecrafttouch.common.Control.HandlerManager;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;

public class MinecraftTouch {
	public static final String MOD_ID = "minecrafttouch";
	private static MinecraftTouch instance;
	public static final Logger LOGGER = LogUtils.getLogger();
	public static HandlerManager HandlerManager;
	public static MinecraftTouch getInstance() {
		return instance;
	}
	public static void init() {
		HandlerManager = new HandlerManager(Minecraft.getInstance());
	}


}
