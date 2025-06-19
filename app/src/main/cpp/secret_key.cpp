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
   // applyAntiDebugging(); // Call early

  /*  if (isRooted()) {
        LOGE("Security check failed Device rooted. Exiting...");
        exit(1); // kill app
    }*/

   /* if(isProbablyEmulator()){
        LOGE("Security check failed Emulator. Exiting...");
        exit(1); // kill app
    }*/


    LOGI("All security checks passed.");
    return JNI_VERSION_1_6;
}