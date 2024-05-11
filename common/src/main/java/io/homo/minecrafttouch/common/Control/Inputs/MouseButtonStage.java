package io.homo.minecrafttouch.common.Control.Inputs;

public class MouseButtonStage extends AbstractStage{
    private final int buttonType;
    private boolean isScroll;
    public MouseButtonStage(int buttonType){
        this.buttonType = buttonType;
    }

    public int getButtonType() {
        return buttonType;
    }

    public boolean isScroll() {
        return isScroll;
    }

    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }
}
