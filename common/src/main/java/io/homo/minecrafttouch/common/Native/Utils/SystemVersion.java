package io.homo.minecrafttouch.common.Native.Utils;

import io.homo.minecrafttouch.common.Native.TabTip;

import java.util.prefs.Preferences;

public class SystemVersion {
    public boolean isEnableMod(){
        boolean isEnable = true;
        boolean isWin = System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOWS");

        return System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOWS");
    }
}
