package io.homo.minecrafttouch.common.Mixin.Mouse;

import io.homo.minecrafttouch.common.MinecraftTouch;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin_Mouse {
    @Inject(method = "turnPlayer", at = @At("HEAD"), cancellable = true)
    public void turnPlayer(CallbackInfo ci) {
        MinecraftTouch.HandlerManager.getMouseHandler().turnPlayer();
        ci.cancel();
    }
    @Inject(method = "setup", at = @At("HEAD"), cancellable = true)
    public void setup(long pWindowPointer, CallbackInfo ci) {
        MinecraftTouch.HandlerManager.getMouseHandler().setup(pWindowPointer);
        ci.cancel();
    }
    @Inject(method = "isLeftPressed", at = @At("HEAD"), cancellable = true)
    public void isLeftPressed(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(MinecraftTouch.HandlerManager.getMouseHandler().isLeftPressed());
        cir.cancel();
    }
    @Inject(method = "isMiddlePressed", at = @At("HEAD"), cancellable = true)
    public void isMiddlePressed(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(MinecraftTouch.HandlerManager.getMouseHandler().isMiddlePressed());
        cir.cancel();
    }
    @Inject(method = "isRightPressed", at = @At("HEAD"), cancellable = true)
    public void isRightPressed(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(MinecraftTouch.HandlerManager.getMouseHandler().isRightPressed());
        cir.cancel();
    }
    @Inject(method = "xpos", at = @At("HEAD"), cancellable = true)
    public void xpos(CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue(MinecraftTouch.HandlerManager.getMouseHandler().xpos());
        cir.cancel();
    }
    @Inject(method = "ypos", at = @At("HEAD"), cancellable = true)
    public void ypos(CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue(MinecraftTouch.HandlerManager.getMouseHandler().ypos());
        cir.cancel();
    }
    @Inject(method = "isMouseGrabbed", at = @At("HEAD"), cancellable = true)
    public void isMouseGrabbed(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(MinecraftTouch.HandlerManager.getMouseHandler().isMouseGrabbed());
        cir.cancel();
    }
    @Inject(method = "setIgnoreFirstMove", at = @At("HEAD"), cancellable = true)
    public void setIgnoreFirstMove(CallbackInfo ci) {
        MinecraftTouch.HandlerManager.getMouseHandler().setIgnoreFirstMove();
        ci.cancel();
    }
    @Inject(method = "cursorEntered", at = @At("HEAD"), cancellable = true)
    public void cursorEntered(CallbackInfo ci) {
        MinecraftTouch.HandlerManager.getMouseHandler().cursorEntered();
        ci.cancel();
    }
    @Inject(method = "grabMouse", at = @At("HEAD"), cancellable = true)
    public void grabMouse(CallbackInfo ci) {
        MinecraftTouch.HandlerManager.getMouseHandler().grabMouse();
        ci.cancel();
    }
    @Inject(method = "releaseMouse", at = @At("HEAD"), cancellable = true)
    public void releaseMouse(CallbackInfo ci) {
        MinecraftTouch.HandlerManager.getMouseHandler().releaseMouse();
        ci.cancel();
    }
}

