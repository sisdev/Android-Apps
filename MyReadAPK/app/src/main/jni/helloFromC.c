//
// Created by Dell on 11-10-2016.
//
#include <jni.h>

JNIEXPORT jstring Java_in_sisoft_myreadapk_MainActivity_stringFromJNI( JNIEnv* env,jobject thiz )
{

        return (*env)->NewStringUTF(env, "Hello Learn2Crack from JNI !");
}

