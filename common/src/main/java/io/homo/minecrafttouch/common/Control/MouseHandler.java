package io.homo.minecrafttouch.common.Control;

import com.mojang.blaze3d.Blaze3D;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import io.homo.minecrafttouch.common.Control.Inputs.MouseButton;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Mth;
import net.minecraft.util.SmoothDouble;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWDropCallback;

public class MouseHandler extends AbstractInputHandler {
    private final Minecraft minecraft;
    private boolean isLeftPressed;
    private boolean isMiddlePressed;
    private boolean isRightPressed;
    private double xpos;
    private double ypos;
    private int fakeRightMouse;
    private int activeButton = -1;
    private boolean ignoreFirstMove = true;
    private int clickDepth;
    private double mousePressedTime;
    private final SmoothDouble smoothTurnX = new SmoothDouble();
    private final SmoothDouble smoothTurnY = new SmoothDouble();
    private double accumulatedDX;
    private double accumulatedDY;
    private double accumulatedScroll;
    private double lastMouseEventTime = Double.MIN_VALUE;
    private boolean mouseGrabbed;

    public MouseHandler(Minecraft pMinecraft) {
        super(pMinecraft);
        this.minecraft = pMinecraft;
        this.addButtons();

    }
    public void addButtons(){

        this.Buttons.add(new MouseButton(0)); //left
        this.Buttons.add(new MouseButton(1)); //right
    }

    private void onPress(long pWindowPointer, int pButton, int pAction, int pModifiers) {
        if (pWindowPointer == this.minecraft.getWindow().getWindow()) {
            this.activeButton=pButton;
            boolean flag = pAction == 1;
            if (Minecraft.ON_OSX && pButton == 0) {
                if (flag) {
                    if ((pModifiers & 2) == 2) {
                        pButton = 1;
                        ++this.fakeRightMouse;
                    }
                } else if (this.fakeRightMouse > 0) {
                    pButton = 1;
                    --this.fakeRightMouse;
                }
            }

            if (flag) {
                if (this.minecraft.options.touchscreen && this.clickDepth++ > 0) {
                    return;
                }

                this.activeButton = pButton;
                this.mousePressedTime = Blaze3D.getTime();
            } else if (this.activeButton != -1) {
                if (this.minecraft.options.touchscreen && --this.clickDepth > 0) {
                    return;
                }

                this.activeButton = -1;
            }


            boolean[] aboolean = new boolean[]{false};
            if (this.minecraft.getOverlay() == null) {
                if (this.minecraft.screen == null) {
                    if (!this.mouseGrabbed && flag) {
                        this.grabMouse();
                    }
                } else {
                    double d0 = this.xpos * (double)this.minecraft.getWindow().getGuiScaledWidth() / (double)this.minecraft.getWindow().getScreenWidth();
                    double d1 = this.ypos * (double)this.minecraft.getWindow().getGuiScaledHeight() / (double)this.minecraft.getWindow().getScreenHeight();
                    Screen screen = this.minecraft.screen;
                    if (flag) {
                        screen.afterMouseAction();
                        int finalPButton = pButton;
                        Screen.wrapScreenError(() -> {
                            if (!aboolean[0]) {
                                aboolean[0] = this.minecraft.screen.mouseClicked(d0, d1, finalPButton);
                            }

                        }, "mouseClicked event handler", screen.getClass().getCanonicalName());
                    } else {
                        int finalPButton = pButton;
                        Screen.wrapScreenError(() -> {
                            if (!aboolean[0]) {
                                aboolean[0] = this.minecraft.screen.mouseReleased(d0, d1, finalPButton);
                            }

                        }, "mouseReleased event handler", screen.getClass().getCanonicalName());
                    }
                }
            }

            if (!aboolean[0] && (this.minecraft.screen == null || this.minecraft.screen.passEvents) && this.minecraft.getOverlay() == null) {
                if (pButton == 0) {
                    this.isLeftPressed = flag;
                } else if (pButton == 2) {
                    this.isMiddlePressed = flag;
                } else if (pButton == 1) {
                    this.isRightPressed = flag;
                }

                KeyMapping.set(Type.MOUSE.getOrCreate(pButton), flag);
                if (flag) {
                    if (this.minecraft.player.isSpectator() && pButton == 2) {
                        this.minecraft.gui.getSpectatorGui().onMouseMiddleClick();
                    } else {
                        KeyMapping.click(Type.MOUSE.getOrCreate(pButton));
                    }
                }
            }

        }

    }
    private void click(int button,boolean isClicked){
        KeyMapping.set(InputConstants.Type.MOUSE.getOrCreate(button), isClicked);
        if (isClicked) {
            if (this.minecraft.player.isSpectator() && button == 2) {
                this.minecraft.gui.getSpectatorGui().onMouseMiddleClick();
            } else {
                KeyMapping.click(InputConstants.Type.MOUSE.getOrCreate(button));
            }
        }
    }
    private void onScroll(long pWindowPointer, double pXOffset, double pYOffset) {
        if (pWindowPointer == Minecraft.getInstance().getWindow().getWindow()) {
            double offset = pYOffset;
            if (Minecraft.ON_OSX && pYOffset == 0.0) {
                offset = pXOffset;
            }

            double d0 = (this.minecraft.options.discreteMouseScroll ? Math.signum(offset) : offset) * this.minecraft.options.mouseWheelSensitivity;
            if (this.minecraft.getOverlay() == null) {
                if (this.minecraft.screen != null) {
                    this.minecraft.screen.afterMouseAction();
                } else if (this.minecraft.player != null) {
                    if (this.accumulatedScroll != 0.0 && Math.signum(d0) != Math.signum(this.accumulatedScroll)) {
                        this.accumulatedScroll = 0.0;
                    }

                    this.accumulatedScroll += d0;
                    int i = (int)this.accumulatedScroll;
                    if (i == 0) {
                        return;
                    }

                    this.accumulatedScroll -= i;

                    if (this.minecraft.player.isSpectator()) {
                        if (this.minecraft.gui.getSpectatorGui().isMenuActive()) {
                            this.minecraft.gui.getSpectatorGui().onMouseScrolled(-i);
                        } else {
                            float f = Mth.clamp(this.minecraft.player.getAbilities().getFlyingSpeed() + (float)i * 0.005F, 0.0F, 0.2F);
                            this.minecraft.player.getAbilities().setFlyingSpeed(f);
                        }
                    } else {
                        this.minecraft.player.getInventory().swapPaint(i);
                    }
                }
            }
        }

    }

    private void onDrop(long pWindow, List<Path> pPaths) {
        if (this.minecraft.screen != null) {
            this.minecraft.screen.onFilesDrop(pPaths);
        }

    }

    @Override
    public void setup(long pWindowPointer) {
        InputConstants.setupMouseCallbacks(pWindowPointer, (p_91591_, p_91592_, p_91593_) -> {
            this.minecraft.execute(() -> {
                this.onMove(p_91591_, p_91592_, p_91593_);
            });
        }, (p_91566_, p_91567_, p_91568_, p_91569_) -> {
            this.minecraft.execute(() -> {
                this.onPress(p_91566_, p_91567_, p_91568_, p_91569_);
            });
        }, (p_91576_, p_91577_, p_91578_) -> {
            this.minecraft.execute(() -> {
                this.onScroll(p_91576_, p_91577_, p_91578_);
            });
        }, (p_91536_, p_91537_, p_91538_) -> {
            Path[] apath = new Path[p_91537_];

            for(int i = 0; i < p_91537_; ++i) {
                apath[i] = Paths.get(GLFWDropCallback.getName(p_91538_, i));
            }

            this.minecraft.execute(() -> {
                this.onDrop(p_91536_, Arrays.asList(apath));
            });
        });
    }

    private void onMove(long pWindowPointer, double pXpos, double pYpos) {
        if (pWindowPointer == Minecraft.getInstance().getWindow().getWindow()) {
            if (this.ignoreFirstMove) {
                this.xpos = pXpos;
                this.ypos = pYpos;
                this.ignoreFirstMove = false;
            }

            Screen screen = this.minecraft.screen;
            if (screen != null && this.minecraft.getOverlay() == null) {
                double d0 = pXpos * (double)this.minecraft.getWindow().getGuiScaledWidth() / (double)this.minecraft.getWindow().getScreenWidth();
                double d1 = pYpos * (double)this.minecraft.getWindow().getGuiScaledHeight() / (double)this.minecraft.getWindow().getScreenHeight();
                Screen.wrapScreenError(() -> {
                    screen.mouseMoved(d0, d1);
                }, "mouseMoved event handler", screen.getClass().getCanonicalName());
                if (this.activeButton != -1 && this.mousePressedTime > 0.0) {
                    double d2 = (pXpos - this.xpos) * (double)this.minecraft.getWindow().getGuiScaledWidth() / (double)this.minecraft.getWindow().getScreenWidth();
                    double d3 = (pYpos - this.ypos) * (double)this.minecraft.getWindow().getGuiScaledHeight() / (double)this.minecraft.getWindow().getScreenHeight();
                    Screen.wrapScreenError(() -> {
                        screen.mouseDragged(d0, d1, this.activeButton, d2, d3);
                    }, "mouseDragged event handler", screen.getClass().getCanonicalName());
                }

                screen.afterMouseMove();
            }

            this.minecraft.getProfiler().push("mouse");
            if (this.isMouseGrabbed() && this.minecraft.isWindowActive()) {
                this.accumulatedDX += pXpos - this.xpos;
                this.accumulatedDY += pYpos - this.ypos;
            }

            this.turnPlayer();
            this.xpos = pXpos;
            this.ypos = pYpos;
            this.minecraft.getProfiler().pop();
        }

    }

    public void turnPlayer() {
        double currentTime = Blaze3D.getTime();
        double deltaTime = currentTime - this.lastMouseEventTime;
        this.lastMouseEventTime = currentTime;
        if (this.mouseGrabbed && this.minecraft.isWindowActive() && this.activeButton == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            double sensitivity = this.minecraft.options.sensitivity * 0.6 + 0.2;
            double sensitivityCubed = sensitivity * sensitivity * sensitivity;
            double turnFactor = sensitivityCubed * 8.0;
            double deltaX;
            double deltaY;

            if (this.minecraft.options.smoothCamera) {
                double smoothDeltaX = this.smoothTurnX.getNewDeltaValue(this.accumulatedDX * turnFactor, deltaTime * turnFactor);
                double smoothDeltaY = this.smoothTurnY.getNewDeltaValue(this.accumulatedDY * turnFactor, deltaTime * turnFactor);
                deltaX = smoothDeltaX;
                deltaY = smoothDeltaY;
            } else if (this.minecraft.options.getCameraType().isFirstPerson() && this.minecraft.player != null && this.minecraft.player.isScoping()) {
                this.smoothTurnX.reset();
                this.smoothTurnY.reset();
                deltaX = this.accumulatedDX * sensitivityCubed;
                deltaY = this.accumulatedDY * sensitivityCubed;
            } else {
                this.smoothTurnX.reset();
                this.smoothTurnY.reset();
                deltaX = this.accumulatedDX * turnFactor;
                deltaY = this.accumulatedDY * turnFactor;
            }

            this.accumulatedDX = 0.0;
            this.accumulatedDY = 0.0;
            int invertDirection = 1;

            if (this.minecraft.options.invertYMouse) {
                invertDirection = -1;
            }

            this.minecraft.getTutorial().onMouse(deltaX, deltaY);

            if (this.minecraft.player != null) {
                this.minecraft.player.turn(deltaX, deltaY * (double) invertDirection);
            }
        } else {
            this.accumulatedDX = 0.0;
            this.accumulatedDY = 0.0;
        }

    }

    public boolean isLeftPressed() {
        return this.isLeftPressed;
    }

    public boolean isMiddlePressed() {
        return this.isMiddlePressed;
    }

    public boolean isRightPressed() {
        return this.isRightPressed;
    }

    public double xpos() {
        return this.xpos;
    }

    public double ypos() {
        return this.ypos;
    }

    public double getXVelocity() {
        return this.accumulatedDX;
    }//

    public double getYVelocity() {
        return this.accumulatedDY;
    }//

    public void setIgnoreFirstMove() {
        this.ignoreFirstMove = true;
    }

    public boolean isMouseGrabbed() {
        return this.mouseGrabbed;
    }

    public void grabMouse() {
        if (this.minecraft.isWindowActive() && !this.mouseGrabbed) {
            if (!Minecraft.ON_OSX) {
                KeyMapping.setAll();
            }

            this.mouseGrabbed = true;
            this.xpos = ((double) this.minecraft.getWindow().getScreenWidth() / 2);
            this.ypos = ((double) this.minecraft.getWindow().getScreenHeight() / 2);
            //InputConstants.grabOrReleaseMouse(this.minecrafttouch.getWindow().getWindow(), 212995, this.xpos, this.ypos);
            this.minecraft.setScreen(null);
            //this.minecrafttouch.missTime = 10000;
            this.ignoreFirstMove = true;
        }

    }

    public void releaseMouse() {
        if (this.mouseGrabbed) {
            this.mouseGrabbed = false;
            this.xpos = ((double) this.minecraft.getWindow().getScreenWidth() / 2);
            this.ypos = ((double) this.minecraft.getWindow().getScreenHeight() / 2);
            //InputConstants.grabOrReleaseMouse(this.minecrafttouch.getWindow().getWindow(), 212993, this.xpos, this.ypos);
        }

    }

    public void cursorEntered() {
        this.ignoreFirstMove = true;
    }
}
