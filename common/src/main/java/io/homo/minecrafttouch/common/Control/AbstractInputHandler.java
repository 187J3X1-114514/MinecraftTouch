package io.homo.minecrafttouch.common.Control;

import io.homo.minecrafttouch.common.Control.Inputs.AbstractButton;
import net.minecraft.client.Minecraft;
import java.util.ArrayList;

public abstract class AbstractInputHandler {
    protected final Minecraft minecraft;
    protected int activeButton = -1;
    protected final ArrayList<AbstractButton> Buttons = new ArrayList<>();

    public AbstractInputHandler(Minecraft pMinecraft) {
        this.minecraft = pMinecraft;
    }
    protected abstract void addButtons();
    public abstract void setup(long pWindowPointer);

    public int getActiveButton() {
        return activeButton;
    }

    public ArrayList<AbstractButton> getButtons() {
        return Buttons;
    }
}