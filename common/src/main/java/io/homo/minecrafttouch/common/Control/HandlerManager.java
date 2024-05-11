package io.homo.minecrafttouch.common.Control;

import net.minecraft.client.Minecraft;

public class HandlerManager {
    public MouseHandler mouseHandler;
    public TouchHandler touchHandler;
    public HandlerManager(Minecraft mc){
        mouseHandler = new MouseHandler(mc);
        touchHandler = new TouchHandler(mc);
    }
    public MouseHandler getMouseHandler(){
        return mouseHandler;
    }
    public TouchHandler getTouchHandler(){
        return touchHandler;
    }
    //public AbstractInputHandler getHandler(){

    //}
}
