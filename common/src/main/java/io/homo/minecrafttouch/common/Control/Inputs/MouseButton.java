package io.homo.minecrafttouch.common.Control.Inputs;

public class MouseButton extends AbstractButton{
    public MouseButtonStage stage;
    public MouseButton(int buttonType){
        this.stage = new MouseButtonStage(buttonType);
    }
}
