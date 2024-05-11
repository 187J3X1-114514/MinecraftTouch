package io.homo.minecrafttouch.common.Mixin.TouchHelp;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractSelectionList.class)
public abstract class AbstractSelectionListMixin extends AbstractContainerEventHandler {
    @Shadow protected int y0;
    @Shadow protected int y1;
    @Shadow public abstract void setScrollAmount(double pScroll);
    @Shadow public abstract int getMaxScroll();
    @Shadow public abstract double getScrollAmount();
    @Shadow protected abstract int getMaxPosition();
    @Inject(method = "mouseDragged",at=@At(value = "HEAD"))
    public void mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY, CallbackInfoReturnable<Boolean> callbackInfo) {
        if (!super.mouseDragged(mouseX, mouseY, button, dragX, dragY)){
            double maxScroll = Math.max(1, this.getMaxScroll());
            int height = this.y1 - this.y0;
            int thumbHeight = Mth.clamp((int)((float)(height * height) / (float)this.getMaxPosition()), 32, height - 8);
            double scrollFactor = Math.max(1.0, maxScroll / (double)(height - thumbHeight));
            this.setScrollAmount((this.getScrollAmount() + (-dragY*0.65) * scrollFactor));
        }
    }

}
