/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_frank_ndk_ImageBlur */

#ifndef _Included_com_frank_ndk_ImageBlur
#define _Included_com_frank_ndk_ImageBlur
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_frank_ndk_ImageBlur
 * Method:    blurPixels
 * Signature: ([IIII)V
 */
JNIEXPORT void JNICALL Java_com_frank_ndk_ImageBlur_blurPixels
  (JNIEnv *, jclass, jintArray, jint, jint, jint);

/*
 * Class:     com_frank_ndk_ImageBlur
 * Method:    blurBitmap
 * Signature: (Landroid/graphics/Bitmap;I)V
 */
JNIEXPORT void JNICALL Java_com_frank_ndk_ImageBlur_blurBitmap
  (JNIEnv *, jclass, jobject, jint);

#ifdef __cplusplus
}
#endif
#endif
