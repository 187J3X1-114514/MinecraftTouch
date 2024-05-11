package io.homo.minecrafttouch.common.Mixin.TouchHelp;

import io.homo.minecrafttouch.common.Native.TabTip;
import net.minecraft.client.gui.components.EditBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EditBox.class)
public class EditBoxMixin {
    @Unique
    private boolean minecraftTouch$isFocused = false;
    @Inject(method = "setFocus",at=@At(value = "RETURN"))
    public void setFocus(boolean pFocused, CallbackInfo ci) {
        if (pFocused && !minecraftTouch$isFocused){
            minecraftTouch$isFocused = true;
            TabTip.Open();
        }
        if (!pFocused && minecraftTouch$isFocused){
            minecraftTouch$isFocused = false;
            TabTip.Close();
        }
    }

}
