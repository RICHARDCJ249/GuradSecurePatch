package com.iflytek.tab1.mia;

import java.util.List;

public interface IMDM {
    /**
     * 控制WiFi高级选项
     *
     * @param b
     */
    public void controlWiFiProxy(boolean b);

    /**
     * 控制蓝牙开关
     *
     * @param b
     */
    public void controlBluetooth(boolean b);

    /**
     * 控制防火墙开关
     *
     * @param b
     */
    public void controlFireWall(boolean b);

    /**
     * 控制USB调试
     *
     * @param b
     */
    public void controlUSb(boolean b);

    /**
     * 控制TF卡
     *
     * @param b
     */
    public void controlTFCard(boolean b);

    /**
     * 读取App白名单
     */
    public List<String> readAppWhiteList();

    /**
     * 写入App白名单
     *
     * @param s
     */
    public void writeAppWhiteList(String s);

    /**
     * 删除App白名单
     *
     * @param s
     */
    public void deletAppWhiteList(String s);

    /**
     * 读取网络白名单
     */
    public List<String> readIPWhiteList();

    /**
     * 读取网络白名单
     *
     * @param s
     */
    public void readIPWhiteList(String s);

    /**
     * 控制App隐藏
     * @param b true ;false ;
     */
    public void controlApp(boolean b, String s);

    /**
     * 静默安装App
     */
    public void slientInstall(String s);

    /**
     * 强行停止应用
     */
    public void killAppProcess(String s);
}
