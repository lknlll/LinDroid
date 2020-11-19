#include "JDCNReflexJava.h"

using namespace lindroid;
using namespace reflex;
void CNReflex::GetInterfaceObject(JavaVM *vm,JNIEnv *env, const char *path, jobject *objptr) {
    jclass cls = env->FindClass(path);
    if (!cls) {
        return;
    }
    jmethodID constr = env->GetMethodID(cls, "<init>", "()V");
    if (!constr) {
        env->DeleteLocalRef(cls);
        return;
    }
    jobject obj = env->NewObject(cls, constr);
    if (!obj) {
        env->DeleteLocalRef(cls);
        return;
    }

    (*objptr) = env->NewGlobalRef(obj);

    env->DeleteLocalRef(cls);
    env->DeleteLocalRef(obj);
    g_JavaVM = vm;
}
jobject CNReflex::getInstance(JNIEnv *env, jclass obj_class) {
    jmethodID c_id = env->GetMethodID(obj_class, "<init>", "()V");
    jobject obj = env->NewObject(obj_class, c_id);
    return obj;
}

void CNReflex::callVoidJavaMethod(std::string methodName, std::string methodParam) {
    if (g_JavaVM == NULL) {
        return;
    }
    int status;
    JNIEnv *env = NULL;
    bool isAttached = false;

    status = g_JavaVM->GetEnv((void **) &env, JNI_VERSION_1_6);

    if (status < 0)  //JNIEnv指针仅在创建它的线程有效。如果我们需要在其他线程访问JVM，必须先调用AttachCurrentThread将当前线程与JVM进行关联，然后才能获得JNIEnv对象
    {
        status = g_JavaVM->AttachCurrentThread(&env, NULL);
        if (status < 0) {
            return;
        }
        isAttached = true;
    }

    if (isAttached)  //
    {
        jclass cls = env->GetObjectClass(g_InterfaceObject);
        if (cls != 0) {

            jmethodID mid = env->GetStaticMethodID(cls, methodName.c_str(), methodParam.c_str());
            if (mid != 0) {
                env->CallStaticVoidMethod(cls, mid);
            }
            env->DeleteLocalRef(cls);
        }
        g_JavaVM->DetachCurrentThread();
    } else {
        jclass cls = env->FindClass(g_JavaClassName);
        if (cls != 0) {

            jobject obj = getInstance(env, cls);
            jmethodID mid = env->GetMethodID(cls, methodName.c_str(), methodParam.c_str());
            if (mid != 0) {
                env->CallVoidMethod(obj, mid);
            }
            env->DeleteLocalRef(cls);
            env->DeleteLocalRef(obj);
        }
    }
}

void CNReflex::callIntJavaMethod(std::string methodName, std::string methodParam,int errorCode) {
    if (g_JavaVM == NULL) {
        return;
    }
    int status;
    JNIEnv *env = NULL;
    bool isAttached = false;

    status = g_JavaVM->GetEnv((void **) &env, JNI_VERSION_1_6);

    if (status < 0) {
        status = g_JavaVM->AttachCurrentThread(&env, NULL);
        if (status < 0) {
            return;
        }
        isAttached = true;
    }

    if (isAttached)  //
    {
        jclass cls = env->GetObjectClass(g_InterfaceObject);
        if (cls != 0) {

            jmethodID mid = env->GetStaticMethodID(cls, methodName.c_str(), methodParam.c_str());
            if (mid != 0) {
                env->CallStaticVoidMethod(cls, mid, errorCode);
            }
            env->DeleteLocalRef(cls);
        }
        g_JavaVM->DetachCurrentThread();
    } else {
        jclass cls = env->FindClass(g_JavaClassName);
        if (cls != 0) {

            jobject obj = getInstance(env, cls);
            jmethodID mid = env->GetMethodID(cls, methodName.c_str(), methodParam.c_str());
            if (mid != 0) {
                env->CallVoidMethod(obj, mid, errorCode);
            }
            env->DeleteLocalRef(cls);
            env->DeleteLocalRef(obj);
        }
    }
}