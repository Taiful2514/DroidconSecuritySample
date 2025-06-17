#include <jni.h>
#include <string>
#include <sys/ptrace.h>
#include <sys/prctl.h> // <-- Add this header for prctl and PR_SET_DUMPABLE
#include <signal.h>    // <-- Add this header for raise(SIGTRAP)
#include <android/log.h> // For logging
#include <sys/stat.h>
#include <fcntl.h> // <-- Required for O_RDONLY
#include <unistd.h>    // for open, read, close
#include <fstream>
#include <sys/system_properties.h>

#define LOG_TAG "JNI_OnLoad"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

// Define your "key" as a C-style string constant
// IMPORTANT: This key is hardcoded and can be extracted from the binary!
// For real secrets, consider more robust methods (e.g., proper key derivation, secure storage solutions).

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_droidconsecuritysample_util_SecurityUtils_getBaseUrl(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("https://jsonplaceholder.typicode.com/");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_droidconsecuritysample_util_SecurityUtils_getPaidKey(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("droidcon123");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_droidconsecuritysample_util_SecurityUtils_getSSLPin(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("/UzJAZYxLBnEpBwXAcmd4WHi7f8aYgfMExGnoyp5B04=");
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_droidconsecuritysample_util_SecurityUtils_pinningUrl(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("jsonplaceholder.typicode.com");
}

bool isBeingDebugged() {
    char buf[1024] = {0};
    int status_fd = open("/proc/self/status", O_RDONLY);
    if (status_fd == -1) return false;

    read(status_fd, buf, sizeof(buf) - 1);
    close(status_fd);

    char* tracer = strstr(buf, "TracerPid:");
    if (!tracer) return false;

    int pid = atoi(tracer + strlen("TracerPid:"));
    return pid != 0;
}

bool checkForFrida() {
    FILE* fp = fopen("/proc/self/maps", "r");
    if (fp == nullptr) return false;

    char line[1024];
    while (fgets(line, sizeof(line), fp)) {
        if (strstr(line, "frida") || strstr(line, "gadget")) {
            fclose(fp);
            return true;
        }
    }
    fclose(fp);
    return false;
}

void applyAntiDebugging() {
    // Block debugger from attaching
    ptrace(PTRACE_TRACEME, 0, 0, 0);

    // Disable core dumps
    prctl(PR_SET_DUMPABLE, 0);

    if (isBeingDebugged() || checkForFrida()) {
        __android_log_print(ANDROID_LOG_ERROR, "AntiDebug", "Frida or debugger detected!");
        raise(SIGKILL);  // or exit(1);
    }

    // Optional: Trigger SIGTRAP to crash if being debugged
   // raise(SIGTRAP);
}

// Root check
bool isRooted() {
    const std::string rootPaths[] = {
            "/system/xbin/su",
            "/system/bin/su",
            "/sbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/.ext/.su",
            "/system/usr/we-need-root/su",
            "/system/app/Superuser.apk",
            "/data/data/com.noshufou.android.su"
    };

    // Check for existence of root-related files
    for (const auto& path : rootPaths) {
        std::ifstream file(path.c_str());
        if (file.good()) {
            file.close();
            return true; // File exists, device is likely rooted
        }
    }

    // Check if 'su' command is executable
    if (system("which su > /dev/null 2>&1") == 0) {
        return true; // 'su' command is available, device is likely rooted
    }

    return false; // No root indicators found
}

// Signature check
bool isSignatureValid(JNIEnv* env, jobject context) {
    jclass contextClass = env->GetObjectClass(context);

    jmethodID getPackageManager = env->GetMethodID(contextClass, "getPackageManager", "()Landroid/content/pm/PackageManager;");
    jobject pm = env->CallObjectMethod(context, getPackageManager);

    jmethodID getPackageName = env->GetMethodID(contextClass, "getPackageName", "()Ljava/lang/String;");
    jstring packageName = (jstring)env->CallObjectMethod(context, getPackageName);

    jclass pmClass = env->GetObjectClass(pm);
    jmethodID getPackageInfo = env->GetMethodID(pmClass, "getPackageInfo",
                                                "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    jobject pkgInfo = env->CallObjectMethod(pm, getPackageInfo, packageName, 0x08000000); // GET_SIGNING_CERTIFICATES

    jclass pkgInfoClass = env->GetObjectClass(pkgInfo);
    jfieldID signingInfoField = env->GetFieldID(pkgInfoClass, "signingInfo", "Landroid/content/pm/SigningInfo;");
    jobject signingInfo = env->GetObjectField(pkgInfo, signingInfoField);

    jclass signingInfoClass = env->GetObjectClass(signingInfo);
    jmethodID getSigners = env->GetMethodID(signingInfoClass, "getApkContentsSigners", "()[Landroid/content/pm/Signature;");
    jobjectArray signatures = (jobjectArray)env->CallObjectMethod(signingInfo, getSigners);

    jobject signature = env->GetObjectArrayElement(signatures, 0);
    jclass signatureClass = env->GetObjectClass(signature);
    jmethodID toByteArray = env->GetMethodID(signatureClass, "toByteArray", "()[B");
    jbyteArray sigBytes = (jbyteArray)env->CallObjectMethod(signature, toByteArray);

    // Now hash the signature
    jclass messageDigestClass = env->FindClass("java/security/MessageDigest");
    jmethodID getInstance = env->GetStaticMethodID(messageDigestClass, "getInstance", "(Ljava/lang/String;)Ljava/security/MessageDigest;");
    jstring algo = env->NewStringUTF("SHA-256");
    jobject digest = env->CallStaticObjectMethod(messageDigestClass, getInstance, algo);

    jmethodID update = env->GetMethodID(messageDigestClass, "update", "([B)V");
    env->CallVoidMethod(digest, update, sigBytes);

    jmethodID digestMethod = env->GetMethodID(messageDigestClass, "digest", "()[B");
    jbyteArray hash = (jbyteArray)env->CallObjectMethod(digest, digestMethod);

    // Convert hash to hex string
    jsize len = env->GetArrayLength(hash);
    jbyte* hashBytes = env->GetByteArrayElements(hash, NULL);

    char hex[3 * 32]; // 32 bytes max, 2 chars per byte + ':'s
    char* ptr = hex;

    for (int i = 0; i < len; ++i) {
        sprintf(ptr, "%02X", (unsigned char)hashBytes[i]);
        ptr += 2;
        if (i != len - 1) *ptr++ = ':';
    }
    *ptr = '\0';

    env->ReleaseByteArrayElements(hash, hashBytes, JNI_ABORT);

    std::string calculated(hex);
    std::string expected = "71:02:D2:0E:28:53:2B:96:B9:74:1D:1E:FA:D0:21:F9:A8:A2:01:C2:C6:1E:C9:F4:77:8D:63:DF:36:C1:34:DA";

    return calculated == expected;
}

// Emulator detection
bool isProbablyEmulator() {
    char prop[PROP_VALUE_MAX];

    __system_property_get("ro.kernel.qemu", prop);
    if (strcmp(prop, "1") == 0) return true;

    __system_property_get("ro.product.model", prop);
    if (strstr(prop, "Emulator") || strstr(prop, "Android SDK built for x86")) return true;

    __system_property_get("ro.product.manufacturer", prop);
    if (strstr(prop, "Genymotion")) return true;

    __system_property_get("ro.hardware", prop);
    if (strstr(prop, "goldfish") || strstr(prop, "ranchu") || strstr(prop, "qemu")) return true;

    return false;
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved) {
    JNIEnv* env;
    if (vm->GetEnv((void**)&env, JNI_VERSION_1_6) != JNI_OK) {
        LOGE("Returning from JNICALL ERROR");
        return JNI_ERR;
    }
    applyAntiDebugging(); // Call early

    // Grab context via Java class (must pass from app)
    jclass activityThread = env->FindClass("android/app/ActivityThread");
    jmethodID currentApplication = env->GetStaticMethodID(activityThread, "currentApplication", "()Landroid/app/Application;");
    jobject context = env->CallStaticObjectMethod(activityThread, currentApplication);


/*
    if (isRooted() || !isSignatureValid(env, context)) {
        LOGE("Security check failed. Exiting...");
        exit(1); // kill app
    }
*/

/*
    if(isProbablyEmulator()){
        LOGE("Security check failed Emulator. Exiting...");
        exit(1); // kill app
    }
*/


    LOGI("All security checks passed.");
    return JNI_VERSION_1_6;
}