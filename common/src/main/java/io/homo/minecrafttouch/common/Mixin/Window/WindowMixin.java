package io.homo.minecrafttouch.common.Mixin.Window;

import com.mojang.blaze3d.platform.DisplayData;
import com.mojang.blaze3d.platform.ScreenManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.platform.WindowEventHandler;
import io.homo.minecrafttouch.common.MinecraftTouch;
import io.homo.minecrafttouch.common.Native.Lib.Const;
import io.homo.minecrafttouch.common.Native.Lib.User32;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public class WindowMixin {
    @Final
    @Shadow private long window;
    @Inject(method = "<init>",at=@At("TAIL"))
    public void Window(WindowEventHandler EventHandler, ScreenManager ScreenManager, DisplayData DisplayData, String PreferredFullscreenVideoMode, String Title, CallbackInfo ci) {
        if (User32.RegisterTouchWindow(window,Const.TWF_FINETOUCH)){
            MinecraftTouch.LOGGER.info("Successfully registered touch window -> {}", window);
        }
    }
    @Inject(method = "close",at=@At("TAIL"))
    public void close(CallbackInfo ci) {
        User32.UnRegisterTouchWindow(window);
    }

}
