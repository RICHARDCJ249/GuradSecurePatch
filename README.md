# 为霜

#### 介绍
- 对于领创平板管理的研究方案

#### 软件架构
1. 用于建立对TB3—850F（科大讯飞定制版）的第三方管控
2. 基于系统内所提供的接口
3. 功能（已实现）
    - 安装科大讯飞未认证应用
    - 隐藏应用
    - 打开USB调试
    - 解锁TF卡使用权限
    - 允许设置锁屏密码
    - 允许打开文件管理
    - 允许打开WIFI高级设置
    - 允许关闭网络防火墙
    - 蓝牙
4. 增强功能
    - 天气查询（每天一百次刷新机会）
    - 增强系统日历，自动导入中国农历及节假日等内容

#### 安装教程

1. 请于**领创平板管理**初始化前输入初始密码打开USB调试
2. 接入计算机后进入ADB工具包目录，按住*shift*点击右键选择在此处打开CMD，输入 
<br>`adb shell am start -a android.settings.APPLICATION_DEVELOPMENT_SETTINGS`手机将唤起开发者模式，打开允许OEM解锁开关
3. 重启手机，于开机前按住音量键上下键，进入*Recovery*模式，使用音量键选择*Reboot to Bootloader*选项并确定，等待重启
4. 重启成功后，进入*Bootloader*，接入计算机后进入ADB工具包目录，按住*shift*点击右键选择在此处打开CMD，输入<br>`fastboot oem unlock`( **本操作将清除数据** ）<br>根据提示操作，手机端确定后重启
5. 打开已下载的*TB3-850F.zip*,解压至本地文件夹，在命令行中输入<br>`fastboot flash system system.img`<br>等待完成后，输入<br>`fastboot flash boot boot.img`**（请将*system.img*,*boot.img*替换为本地目录）**<br>，完成后请按住音量下键与电源键强制重启
6. 请于日历应用中输入 **www.weishuang.com** 唤起应用



#### 使用说明

1. 本应用采用内部账号使用策略。

#### 感谢

1. 所有参与赞助与开发的人
2. 感谢以下开源库作者
    >- [CircleImageview](https://github.com/hdodenhof/CircleImageView)
    >- [Circular-Progress-Button](https://github.com/dmytrodanylyk/circular-progress-button/wiki/User-Guide)
3. 天气API，万年历API [www.mxnzp.com](https://github.com/MZCretin/RollToolsApi)

#### 未完善

1. 账号管理机制
2. 应用背景更换机制
3. OTG功能

#### 赞助
- 支付宝账号：15056091700

