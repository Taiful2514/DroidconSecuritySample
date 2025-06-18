
cmake_minimum_required(VERSION 3.4.1)

# Name of your native library (will result in libnative-key-reader.so)
set(NATIVE_LIB_NAME "native-lib")

add_library(
        ${NATIVE_LIB_NAME}
        SHARED
        secret_key.cpp # List your C++ source file here
)


find_library(log-lib log)
target_link_libraries( ${NATIVE_LIB_NAME} ${log-lib} )