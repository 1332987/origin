package com.youwan;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface JNATestDll extends Library {

    public interface CLibrary extends Library {
        CLibrary INSTANCE = (CLibrary) Native.load("Termb", CLibrary.class);

        Integer CVR_InitComm(int i);

        Integer CVR_CloseComm();
    }

    public static void main(String[] args) {
        System.out.println(CLibrary.INSTANCE.CVR_InitComm(1001));
        System.out.println(CLibrary.INSTANCE.CVR_CloseComm());
    }
}