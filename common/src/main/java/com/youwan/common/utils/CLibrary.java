package com.youwan.common.utils;

import com.sun.jna.Library;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public interface CLibrary extends Library {


    /**
     * USB连接
     *
     * @param i 串口号
     * @return状态
     */
    Integer CVR_InitComm(int i);

    /**
     * 卡认证
     *
     * @return 状态
     */
    Integer CVR_Authenticate();

    /**
     * 读卡操作
     *
     * @param active
     * @return 状态
     */
    Integer CVR_Read_Content(Integer active);

    /**
     * 读卡操作
     *
     * @param pLen
     * @return 状态
     */
    int GetPeopleSex(ByteBuffer strTmp, IntBuffer strLen);

    /**
     * 关闭连接
     *
     * @return 状态
     */
    Integer CVR_CloseComm();

}