package io.homo.minecrafttouch.common.Native;

import io.homo.minecrafttouch.common.MinecraftTouch;
import io.homo.minecrafttouch.common.Native.Lib.Const;
import io.homo.minecrafttouch.common.Native.Lib.Shell32;
import io.homo.minecrafttouch.common.Native.Lib.User32;

import java.io.IOException;
import java.util.prefs.Preferences;
public class TabTip {
    public static void Open() {
        //PS:用一堆try防止打开键盘崩游戏
        Thread the = new Thread(()->{
            try{
                Preferences rk = Preferences.userRoot().node("SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion");
                if (rk != null) {
                    if (("10").equals(rk.get("ProductName", ""))) {
                        final String TabTipAutoInvokeKey = "EnableDesktopModeAutoInvoke";
                        int EnableDesktopModeAutoInvoke = Preferences.systemNodeForPackage(TabTip.class).getInt(TabTipAutoInvokeKey, -1);
                        if (EnableDesktopModeAutoInvoke != 1)
                            Preferences.systemNodeForPackage(TabTip.class).putInt(TabTipAutoInvokeKey, 1);
                    }
                }

                try {
                    Runtime.getRuntime().exec(Const.TabTipExecPath);
                } catch (IOException ex) {
                    int rValue = Shell32.ShellExecute(0, "open", Const.TabTipExecPath, null, null, Const.SW_SHOWNORMAL);
                    MinecraftTouch.LOGGER.debug("open TabTip.exe -> {}",rValue);
                }
            }catch (Exception e){
                MinecraftTouch.LOGGER.error("Failed to open touch keyboard -> {}", e.getMessage());
            }

        },"TapTip");
        the.setDaemon(true);
        the.start();
    }
    public static void Close() {
        try{
            User32.SendMessage(User32.FindWindow(Const.TabTipWindowClassName, null), Const.WM_SYSCOMMAND, Const.SC_CLOSE, 0);
        }catch (Exception e){
            MinecraftTouch.LOGGER.error("Failed to close touch keyboard -> {}", e.getMessage());
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Open();
        Thread.sleep(3000);
        Close();
        Thread.sleep(1000);
    }
}
