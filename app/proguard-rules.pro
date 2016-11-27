# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/wangzhen/Library/Android/sdk/tools/proguard/proguard-android.txt
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
## API里的类最好都避免混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.support.multidex.MultiDexApplication
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends java.lang.Throwable {*;}
-keep public class * extends java.lang.Exception {*;}

# 指定不去忽略包可见的库类的成员
-dontskipnonpubliclibraryclassmembers

# 指定不去忽略非公共的库类
-dontskipnonpubliclibraryclasses

# 不优化泛型和反射
-keepattributes Signature
-keepattributes *Annotation*
# 不优化内部类
-keepattributes InnerClasses
-keepattributes EnclosingMethod

-keepnames class * implements java.io.Serializable
-keep public class * implements java.io.Serializable {
        public *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#-libraryjars libs/core.jar
#-libraryjars libs/gson-2.2.2.jar
#-libraryjars libs/libammsdk.jar
#-libraryjars libs/TencentLocationSDK_v4.5.8_r160216_1427.jar
#-libraryjars libs/TencentMapSDK_Raster_v1.0.6.jar

# 保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

# 保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保护指定类的成员，如果此类受到保护他们会保护的更好
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# 保持枚举 enum 类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

################### region for xUtils
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,*Annotation*,Synthetic,EnclosingMethod

-keep public class org.xutils.** {
    public protected *;
}
-keep public interface org.xutils.** {
    public protected *;
}
-keepclassmembers class * extends org.xutils.** {
    public protected *;
}
-keepclassmembers class * extends org.xutils.http.RequestParams {*;}
-keepclassmembers class * {
   void *(android.view.View);
   *** *Click(...);
   *** *Event(...);
}
#################### end region

#实体类不要混淆
-keep class com.notonly.calendar.domain.**{*;}

#Glide不混淆
-keep class com.bumptech.glide.integration.okhttp3.OkHttpGlideModule
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#ButterKnife不混淆
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#Gson
-keep class com.google.gson.**{*;}
-dontwarn com.google.gson.**

#Tencent
-keep class com.tencent.**{*;}
-dontwarn com.tencent.**

#Bmob
-keep class cn.bmob.**{*;}
-dontwarn cn.bmob.**

# 蒲公英混淆
-dontwarn com.pgyersdk.**
-keep class com.pgyersdk.** { *; }