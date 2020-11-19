#include "reflex/JDCNReflexJava.h"
#include "com_example_lindroidcode_nativerelated_LinNative.h"
#include<string.h>

using namespace lindroid::reflex;

/**
 * 负责Java方法和本地C函数的链接，JNI_OnLoad方法在每一个.so库中只能存在一个
 * @param vm
 * @return
 */
jint JNI_OnLoad(JavaVM *vm, void *) {
    g_JavaVM = vm;
    //JNIEnv类型, 指向全部JNI方法的指针,只在创建它的线程有效，不能跨线程传递
    JNIEnv *env;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return -1;
    }

    CNReflex::GetInterfaceObject(vm,env, g_JavaClassName, &g_InterfaceObject);

    return JNI_VERSION_1_6;
}

/**
 * C字符串转java字符串
 * ###需要主动释放内存 jsret###
 * */
jstring CharToJstring(JNIEnv* env, const char* pStr){
    int        strLen    = strlen(pStr);
    jclass     jstrObj   = env->FindClass("java/lang/String");
    jmethodID  methodId  = env->GetMethodID(jstrObj, "<init>", "([BLjava/lang/String;)V");
    jbyteArray byteArray = env->NewByteArray(strLen);
    jstring    encode    = env->NewStringUTF("utf-8");

    env->SetByteArrayRegion(byteArray, 0, strLen, (jbyte*)pStr);

    jstring jsret = (jstring)env->NewObject(jstrObj, methodId, byteArray, encode);

    env->DeleteLocalRef(byteArray);
    env->DeleteLocalRef(encode);
    //env->DeleteLocalRef(methodId);
    env->DeleteLocalRef(jstrObj);

    return jsret;
}

int char2int(unsigned char &c) {
    int height = (int)(c >> 4 & 0x0f) ;
    int low = (int)(c & 0x0f) ;

    return height << 4 | low ;
}

int* convertRgb2Color(unsigned char *output , int size , int &colorLength){
    int arg = 0 ;
    if (size % 3 != 0) {
        arg = 1 ;
    }
    colorLength = size / 3 + arg ;
    int* color = new int[colorLength] ;
    int red , green , blue ;
    for (int i = 0; i < colorLength; ++i) {
        red = char2int(output[i * 3]) ;
        green = char2int(output[i * 3 + 1]) ;
        blue = char2int(output[i * 3 + 2]) ;
        color[i] = (red << 16) | (green << 8) | blue | 0xFF000000;
        /*if (i <= 10) {
            JNILOGE("i=%d , output[i * 3]=%x , output[i * 3 + 1]=%x , output[i * 3 + 2]=%x" ,i , output[i * 3] , output[i * 3 + 1] , output[i * 3 + 2]) ;
            JNILOGE("i=%d , red=%x , green=%x , blue=%x" ,i , red , green , blue) ;
            JNILOGD("color[i]=%x" , color[i]) ;
        }*/
    }
    if (0 != arg) {
        color[colorLength - 1] = 0xFF000000;
    }

    return color ;
}

JNIEXPORT int JNICALL Java_com_example_lindroidcode_nativerelated_LinNative_initJni(JNIEnv *, jclass){
    return 0;
}

JNIEXPORT jobjectArray JNICALL
Java_com_example_lindroidcode_nativerelated_LinNative_fetchTwoDimensionalArray(JNIEnv *env,
                                                                               jclass clazz,
                                                                               jint size) {
    // 声明一个对象数组
    jobjectArray result;
    // 找到对象数组中具体的对象类型,[I 指的就是数组类型
    jclass intArrayCls = env->FindClass("[I");

    if (intArrayCls == NULL) {
        return NULL;
    }
    // 相当于初始化一个对象数组，用指定的对象类型
    result = env->NewObjectArray(size, intArrayCls, NULL);

    if (result == NULL) {
        return NULL;
    }
    for (int i = 0; i < size; ++i) {
        // 用来给整型数组填充数据的缓冲区
        jint tmp[256];
        // 声明一个整型数组
        jintArray iarr = env->NewIntArray(size);
        if (iarr == NULL) {
            return NULL;
        }
        for (int j = 0; j < size; ++j) {
            tmp[j] = i + j;
        }
        // 给整型数组填充数据
        env->SetIntArrayRegion(iarr, 0, size, tmp);
        // 给对象数组指定位置填充数据，这个数据就是一个一维整型数组
        env->SetObjectArrayElement(result, i, iarr);
        // 释放局部引用
        env->DeleteLocalRef(iarr);
    }
    return result;
}