# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/wangke1/Library/Android/android-sdk-macosx/tools/proguard/proguard-android.txt
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
# 指定处理完后要输出的jar,war,ear和目录的名称
# -outjars {}
-keep class org.apache.http.conn.scheme.LayeredSocketFactory
-keep class wangke.netlib.RequestService
-keep class wangke.netlib.interfaces.*
-keepclassmembers class wangke.netlib.* {*;}
-keepclassmembers class wangke.netlib.interfaces.* {*;}




