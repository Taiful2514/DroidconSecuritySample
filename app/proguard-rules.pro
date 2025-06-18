# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# Parceler library

# Most Important One
-repackageclasses 'abcXYZ'
-obfuscationdictionary method-dictionary.txt
-classobfuscationdictionary class-dictionary.txt
-overloadaggressively

-printmapping mapping.txt # Generates the mapping file for original-to-obfuscated class names. Required for decoding obfuscated stack traces (especially for crash reports).

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/* # Excludes certain optimizations that may be risky or unstable (e.g., arithmetic simplifications, field/class merging). This can avoid runtime issues, but limits performance improvements.
-optimizationpasses 3 # Tells ProGuard to repeat the optimization step 3 times. More passes can give better optimization but may slightly increase build time.

# Retrofit
-keepattributes Signature
-keepattributes EnclosingMethod
-keepattributes InnerClasses
-keepattributes Annotation

-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
-dontwarn retrofit2.**

# For OkHttp (often used with Retrofit)
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

# For Converter Factories (e.g., GsonConverterFactory)
-keep class retrofit2.converter.gson.** { *; }
-dontwarn retrofit2.converter.gson.**

# RxJava3 Adapter for Retrofit
-keep class retrofit2.adapter.rxjava3.** { *; }
-dontwarn retrofit2.adapter.rxjava3.**

# Keep RxJava3 types if used in method signatures
-keep class io.reactivex.rxjava3.** { *; }
-dontwarn io.reactivex.rxjava3.**


# Gson
-keepattributes *Annotation*

-keep class sun.misc.Unsafe { *; } # Important for Android API < 26
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Keep all classes that are annotated with @SerializedName
-keepclassmembers class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# Don't warn about missing stuff that Gson handles
-dontwarn com.google.gson.**

# Model classes
-keep class com.example.droidconsecuritysample.data.dto.** { *; }

# RecyclerView ViewHolder
-keep public class * extends androidx.recyclerview.widget.RecyclerView$Adapter { *; }
-keep public class * extends androidx.recyclerview.widget.RecyclerView$ViewHolder { *; }
-keep class **$*ViewHolder { *; }


# Android views
-keep class com.google.android.material.bottomsheet.BottomSheetBehavior { *; }

-keepclasseswithmembers class * {
   public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
   public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity {
  public void *(android.view.View);
}

-keepclasseswithmembernames class * {
    native <methods>;
}