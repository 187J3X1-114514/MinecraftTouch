package io.homo.minecrafttouch.common.Native.Lib;

import com.sun.jna.Library;
import com.sun.jna.Native;
public class User32 {
    private interface IUser32 extends Library {
        IUser32 INSTANCE = Native.load("user32", IUser32.class);
        int FindWindowA(String lpClassName, String lpWindowName);
        boolean RegisterTouchWindow(long hwnd ,int ulFlags);
        boolean UnRegisterTouchWindowA(long hwnd);
        boolean IsTouchWindowA(long hwnd,long pulFlags);
        int GetTouchInputInfoA(long hTouchInput, int cInputs, long pInputs, int cbSize, long __functionAddress);
        boolean SendMessageA(long hwnd, int msg, long wParam,long lParam);
    }

    public static int FindWindow(String lpClassName, String lpWindowName){
        return IUser32.INSTANCE.FindWindowA(lpClassName,lpWindowName);
    }

    public static boolean RegisterTouchWindow(long hwnd ,int ulFlags){
        return IUser32.INSTANCE.RegisterTouchWindow(hwnd,ulFlags);
    }

    public static boolean UnRegisterTouchWindow(long hwnd){
        return IUser32.INSTANCE.UnRegisterTouchWindowA(hwnd);
    }

    public static boolean IsTouchWindow(long hwnd,long pulFlags){
        return IUser32.INSTANCE.IsTouchWindowA(hwnd,pulFlags);
    }

    public static int GetTouchInputInfo(long hTouchInput, int cInputs, long pInputs, int cbSize, long __functionAddress){
        return IUser32.INSTANCE.GetTouchInputInfoA(hTouchInput, cInputs, pInputs, cbSize, __functionAddress);
    }
    public static boolean SendMessage(long hwnd, int msg, long wParam,long lParam) {
        return IUser32.INSTANCE.SendMessageA(hwnd, msg, wParam, lParam);
    }
}
