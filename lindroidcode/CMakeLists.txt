CMAKE_MINIMUM_REQUIRED(VERSION 3.4.1)
FILE(GLOB native_srcs "src/main/jni/*.cpp") # native source dir
FILE(GLOB reflex_srcs "src/main/jni/reflex/*.cpp")

ADD_LIBRARY(
        lindroid
        SHARED
        ${native_srcs}
        ${reflex_srcs}
)


SET(CMAKE_VERBOSE_MAKEFILE on)
SET(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -mfloat-abi=softfp ")
SET(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11 -fexceptions -frtti -lz -mfloat-abi=softfp ")
FIND_LIBRARY(
        log-lib # Name of the path variable.
        log     # NDK library name that CMake locates.
)
TARGET_LINK_LIBRARIES(lindroid #main lib
        ${log-lib})