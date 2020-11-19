
#ifndef JDCNSAMPLE_CURL_JDCNREFLEXJAVA_H
#define JDCNSAMPLE_CURL_JDCNREFLEXJAVA_H

#include <jni.h>
#include <string>

#ifdef __cplusplus
extern "C" {
#endif
static JavaVM *g_JavaVM = NULL;//定义一个全局Java VM引用对象, JavaVM是虚拟机在JNI中的表示，一个JVM中只有一个JavaVM对象，这个对象是线程共享的
static jobject g_InterfaceObject = 0;//定义一个全局Java object对象，对于java层的类对象
static const char *g_JavaClassName = "com/example/lindroidcode/nativerelated/LinNativeUtils";//Reflex Java Class Path
namespace lindroid {
    namespace reflex {
        class CNReflex {
        public:
            static void GetInterfaceObject(JavaVM *vm,JNIEnv *env, const char *path, jobject *objptr);

            static jobject getInstance(JNIEnv *env, jclass obj_class);

            static void callVoidJavaMethod(std::string methodName, std::string methodParam);

            static void callIntJavaMethod(std::string methodName, std::string methodParam,int errorCode);
        };
    }
}

#ifdef __cplusplus
}
#endif
#endif //JDCNSAMPLE_CURL_JDCNREFLEXJAVA_H
