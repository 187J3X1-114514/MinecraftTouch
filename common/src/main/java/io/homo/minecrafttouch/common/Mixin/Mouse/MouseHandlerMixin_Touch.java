package io.homo.minecrafttouch.common.Mixin.Mouse;

import io.homo.minecrafttouch.common.MinecraftTouch;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin_Touch {
    @Inject(method = "setup", at = @At("HEAD"), cancellable = true)
    public void setup(long pWindowPointer, CallbackInfo ci) {
        MinecraftTouch.HandlerManager.getTouchHandler().setup(pWindowPointer);
        ci.cancel();
    }
}

