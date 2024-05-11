package io.homo.minecrafttouch.common.Native.Lib;

import com.sun.jna.Library;
import com.sun.jna.Native;



public class Shell32 {

    public static int ShellExecute(int hwnd, String lpOperation, String lpFile, String lpParameters, String lpDirectory, int nShowCmd){
        return IShell32.INSTANCE.ShellExecuteA(hwnd,lpOperation,lpFile,lpParameters,lpDirectory,nShowCmd);
    }

    public interface IShell32 extends Library {
        IShell32 INSTANCE = Native.load("shell32", IShell32.class);

        int ShellExecuteA(int hwnd, String lpOperation, String lpFile, String lpParameters, String lpDirectory, int nShowCmd);

    }
}
