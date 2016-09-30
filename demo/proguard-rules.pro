# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\Gray\android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
#-verbose
#-ignorewarnings
-dontpreverify
-keepattributes Signature
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes EnclosingMethod


-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * implements android.os.IInterface {*;}
-keep public class * extends android.os.IInterface {*;}

-dontwarn android.support.v4.**
-dontwarn android.support.v7.**
-keep class android.support.v4.** { *; }
-keep class android.support.v7.** { *; }
-keep class android.support.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment
-keep public class android.webkit.**{*;}


-keep class * implements android.os.Parcelable {
public static final android.os.Parcelable$Creator *;
}

#腾讯广告
-keep class com.qq.e.** {
    public protected *;
}
-keep class android.support.v4.app.NotificationCompat**{
    public *;
}
#百度
-keep class com.baidu.**{
    public protected *;
}
#360
-keep class com.qhad.**{
    *;
}
#优效
-keep class com.youxiaoad.ssp.**{
    *;
}
# 有米
-keep class th.ds.** {
    *;
}