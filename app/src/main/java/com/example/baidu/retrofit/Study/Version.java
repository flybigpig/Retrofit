package com.example.baidu.retrofit.Study;

/**
 * 记录android 版本特点
 *
 */
public class Version {
    /**
     *
     *

     Version 5.0:

     1.material design


     2.支持多种设备

     Android系统的身影早已出现在多种设备中，比如：智能手机、平板电脑、笔记本电脑、智能电视、汽车、智能手表甚至是各种家用电子产品等。

     3.全新的通知中心设计

     谷歌在Android 5.0中加入了全新风格的通知系统。改进后的通知系统会优先显示对用户来说比较重要的信息，而将不太紧急的内容隐藏起来。用户只需要向下滑动就可以查看全部的通知内容，如图1-2所示。

     4.支持 64 位 ART虚拟机

     Android 5.0内部的性能上也提升了不少，它放弃了之前一直使用的Dalvik虚拟机，改用了ART虚拟机，实现了真正的跨平台编译，在ARM、X86、MIPS 等无处不在。

     5.Overview

     多任务视窗现在有了一

     6.设备识别解锁

     现在个人识别解锁已经被普遍使用，比如当特定的智能手表出现在Android设备的附近时，就会直接绕过锁屏界面进行操作。而Android 5.0也增加了这种针对特定设备识别解锁的模式。换句话说，当设备没有检测到附近有可用的信任设备时，就会启动安全模式以防止未授权访问。

     7.Ok Google语音指令

     当手机处于待机状态时，对你的手机轻轻说声“Ok Google”，手机即刻被唤醒，只需说出简单的语言指令，如播放音乐、查询地点、拨打电话和设定闹钟等，一切只需“说说”而已。

     8.Face unlock面部解锁

     在Android 5.0中，Google花费大力气优化了面部解锁功能。当用户拿起手机处理锁屏界面上的消息通知时，面部解锁功能便自动被激活。随意浏览几条消息之后，手机已经默默地完成了面部识别。



     Version 6.0:

     1.应用权限管理

     在Android 6.0中，应用许可提示可以自定义了。它允许对应用的权限进行高度管理，比如应用能否使用位置、相机、网络和通信录等，这些都开放给开发者和用户。此前的 Android 系统的应用权限管理只能靠第三方应用来实现，在Android 6.0中应用权限管理成为系统级的功能。

     2.Android Pay

     Android Pay是Android支付统一标准。Android 6.0系统中集成了Android Pay，其特性在于简洁、安全和可选性。它是一个开放性平台，用户可以选择谷歌的服务或者使用银行的App来使用它。Android Pay支持Android 4.4以后的系统设备并且可以使用指纹来进行支付。

     3.指纹支持

     虽然很多厂商的 Android 手机实现了指纹的支持，但是这些手机都使用了非谷歌认证的技术。这一次谷歌提供的指纹识别支持，旨在统一指纹识别的技术方案。

     4.Doze电量管理

     Android 6.0自带Doze电量管理功能。手机静止不动一段时间后，会进入Doze电量管理模式。谷歌表示，当屏幕处于关闭状态时，平均续航时间可提高30%。

     5.App Links

     Android 6.0加强了软件间的关联，允许开发者将App和他们的Web域名关联。谷歌大会展示了App Links的应用场景，比如你的手机邮箱里收到一封邮件，内文里有一个Twitter链接，点击该链接可以直接跳转到Twitter应用，而不再是网页。

     6.Now on Tap

     在桌面或App的任意界面，长按Home键即可激活Now on Tap，它会识别当前屏幕上的内容并创建Now卡片。比如，你和某人聊天时提到一起去一家餐馆吃饭，这时你长按Home键，Now on Tap就会创建Now卡片提供这家餐馆的地址和评价等相关信息。如屏幕中出现电话号码，它就会提供一个拨号的标志，你可以直接拨打；而无须先复制，然后再粘贴到拨号界面。它还可以识别日历、地址、音乐、地标等信息。Now on Tap能够快速便捷地帮助用户，完成用户需求，这在很大程度上解放了用户的嘴和双手。这可以说是自Google Now发布以来最为重大的一次升级。


    Version 7.0：



     Version 9.0:   Pie

     1.室内WiFi定位 RTT

        支持IEEE 802.11mc WiFi协议，通过该协议可以实现基于WiFi的室内定位，

     2.异形屏支持

        就是俗称的刘海屏支持，根据DisplayCutout可以获得刘海屏的缺口数量、位置和大小等相关信息。方便开发者进行适配。

     3.多摄像头支持

        在9.0上，你可以同时获取多个视频流。

     4.ImageDecoder

        9.0引入了新的图像类ImageDecoder，提供了更加现代化的方法来解码图片。用于替代老的BitmapFactory 和 BitmapFactory.Options APIs。

     5.Animation

        引入AnimatedImageDrawable类用于绘制和显示GIF。





     Oreo 8.0:

     1.画中画模式

        允许Activity启动picture-in-picture (PIP) mode。

     2. Notification

        引入channel的概念，必须设置。

     3. 自动填充框架

        没有仔细看，应该需要Google Service配合，国内不用考虑了。

     4.可下载字体

        8.0以后支持应用后期下载字体文件而不是打包在APK里面。这样可以有效减少APK体积。

     5.XML中声明字体

        8.0以后支持将字体文件保存在resource资源文件夹中，同时生成对应的R文件，这样就不必再放在asset文件夹中了。并且支持在对应xml中编写字体库。

     6.自适应大小的TextView

        官方支持TextView根据控件尺寸来决定其内部文字的大小。（我之前还自己写过一个类似的，很多坑，现在终于有官方的了，喜大普奔）

     7.新的WebView API
         Version API
         Google SafeBrowsing API
         Termination Handle API
         Renderer Importance API

     8.快捷菜单

     就是常见的桌面在长按某个应用图标，可以弹出一些子菜单，方便用户直接实现某步操作，



     Nougat 7.0

     1.多窗口支持（分屏显示）

        官方详细文档

     2.手机和平板设备上，用户可以同时运行两个应用在同一屏幕上。

        TV上，应用可以将自己设置为画中画模式，允许用户在浏览别的应用时继续显示。
     3.Notification增强

        其实每代新版本发布，或多或少都会Notification进行一定的优化和调整，介于这次调增相对大些，就大致介绍下。

     4.自定义消息风格

     5.打包通知

     6.直接回复

     7.自定义view

     8.JIT/AOT 交叉编译

     9.在5.0引入ART模式，以AOT编译模式替代了JIT模式，在7.0后，Google又在ART模式中新加入了JIT模式的编译器，让JIT帮助AOT进行混合编译，提高应用的运行性能，节省磁盘空间占用。提高应用和系统更新速度。

     10.SurfaceView加强
        7.0后使用SurfaceView将会比TextureView更加省电。

     11.Vulkan支持

     12.新Emoji

     13.OpenGL ES 3.2

     14.VR支持


     *
     */
}
