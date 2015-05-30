/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class elle_homegenius_infrajni_InfraNative */

#ifndef _Included_elle_homegenius_infrajni_InfraNative
#define _Included_elle_homegenius_infrajni_InfraNative

/*
 * Class:     elle_homegenius_infrajni_InfraNative
 * Method:    test
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_elle_homegenius_infrajni_InfraNative_test
  (JNIEnv *x, jobject y);

/*
 * Class:     elle_homegenius_infrajni_InfraNative
 * Method:    testInt
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_elle_homegenius_infrajni_InfraNative_testInt
  (JNIEnv *x, jobject y);

/*
 * Class:     elle_homegenius_infrajni_InfraNative
 * Method:    getOneBrandName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_elle_homegenius_infrajni_InfraNative_getOneBrandName
  (JNIEnv *env, jobject obj, jint id);

/*
 * Class:     elle_homegenius_infrajni_InfraNative
 * Method:    getBrandLen
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_elle_homegenius_infrajni_InfraNative_getBrandLen
  (JNIEnv *env, jobject obj);

/*
 * Class:     elle_homegenius_infrajni_InfraNative
 * Method:    getAirCommandByBrand
 * Signature: (IIIIIIIII)[B
 */
JNIEXPORT jbyteArray JNICALL Java_elle_homegenius_infrajni_InfraNative_getAirCommandByBrand
  (JNIEnv *env, jobject obj, jint brand, jint id, jint temp, jint airflowRate, jint manualFlow, jint autoFlow, jint onoff, jint key, jint model);

/*
 * Class:     elle_homegenius_infrajni_InfraNative
 * Method:    getAirCommandById
 * Signature: (IIIIIIII)[B
 */
JNIEXPORT jbyteArray JNICALL Java_elle_homegenius_infrajni_InfraNative_getAirCommandById
  (JNIEnv *env, jobject obj, jint id, jint temp, jint airflowRate, jint manualFlow, jint autoFlow, jint onoff, jint key, jint model);


/*
 * Class:     elle_homegenius_infrajni_InfraNative
 * Method:    getAirOneBrandNameById
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_elle_homegenius_infrajni_InfraNative_getAirOneBrandNameById
  (JNIEnv *env, jobject obj, jint id, jint language);

/*
 * Class:     elle_homegenius_infrajni_InfraNative
 * Method:    getAirBrandLenById
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_elle_homegenius_infrajni_InfraNative_getAirBrandLenById
  (JNIEnv *env, jobject obj, jint id);

/*
 * Class:     elle_homegenius_infrajni_InfraNative
 * Method:    getAirAllBrandListLen
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_elle_homegenius_infrajni_InfraNative_getAirAllBrandListLen
  (JNIEnv *env, jobject obj);


#endif
