#include <jni.h>
#include <android/log.h>
#include <stdio.h>
#include "InfraNativexx.h"
#include "InfraLib.h"

JNIEXPORT void JNICALL Java_elle_homegenius_infrajni_InfraNative_test
  (JNIEnv *x, jobject y)
{
	return;
}

JNIEXPORT jint JNICALL Java_elle_homegenius_infrajni_InfraNative_testInt
  (JNIEnv *x, jobject y)
{
	return 100;
}

/*
 * Class:     elle_homegenius_infrajni_InfraNative
 * Method:    getOneBrandName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_elle_homegenius_infrajni_InfraNative_getOneBrandName
  (JNIEnv *env, jobject obj, jint id)
{
	char *str = "hello jni~get me~";
	return (*env)->NewStringUTF(env, str);
}

/*
 * Class:     elle_homegenius_infrajni_InfraNative
 * Method:    getBrandLen
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_elle_homegenius_infrajni_InfraNative_getBrandLen
  (JNIEnv *env, jobject obj)
{
	return 1;
}

/*
 * Class:     elle_homegenius_infrajni_InfraNative
 * Method:    getAirCommandByBrand
 * Signature: (IIIIIIIII)[B
 */
JNIEXPORT jbyteArray JNICALL Java_elle_homegenius_infrajni_InfraNative_getAirCommandByBrand
  (JNIEnv *env, jobject obj, jint brand, jint id, jint temp, jint airflowRate, jint manualFlow, jint autoFlow, jint onoff, jint key, jint model)
{
	unsigned char data[200];
	unsigned int brandid = 0;
	int len = 0;
	brandid = getAirIdInDb(brand,id);
	len = getAirControlData(data,brandid,temp,airflowRate,manualFlow,autoFlow,onoff,key,model);
	jbyteArray result = (*env)->NewByteArray(env,len);
	jbyte* buf = (*env)->GetByteArrayElements(env,result,0);
	memcpy(buf,&data[0],len);
	(*env)->ReleaseByteArrayElements(env,result,buf,0);
	return result;
}

/*
 * Class:     elle_homegenius_infrajni_InfraNative
 * Method:    getAirCommandById
 * Signature: (IIIIIIII)[B
 */
JNIEXPORT jbyteArray JNICALL Java_elle_homegenius_infrajni_InfraNative_getAirCommandById
  (JNIEnv *env, jobject obj, jint id, jint temp, jint airflowRate, jint manualFlow, jint autoFlow, jint onoff, jint key, jint model)
{
	unsigned char data[200];
	int len = 0;
	len = getAirControlData(data,id,temp,airflowRate,manualFlow,autoFlow,onoff,key,model);
	jbyteArray result = (*env)->NewByteArray(env,len);
	jbyte* buf = (*env)->GetByteArrayElements(env,result,0);
	memcpy(buf,&data[0],len);
	(*env)->ReleaseByteArrayElements(env,result,buf,0);
	return result;
}

/*
 * Class:     elle_homegenius_infrajni_InfraNative
 * Method:    getAirOneBrandNameById
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_elle_homegenius_infrajni_InfraNative_getAirOneBrandNameById
  (JNIEnv *env, jobject obj, jint id, jint language)
{
	char name[100];
	int len = 0;
	len = getBrandNameById(TYPE_AIR,id,language,name);
	return (*env)->NewStringUTF(env, name);
}

/*
 * Class:     elle_homegenius_infrajni_InfraNative
 * Method:    getAirBrandLenById
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_elle_homegenius_infrajni_InfraNative_getAirBrandLenById
  (JNIEnv *env, jobject obj, jint id)
{
	return getBrandProductLenById(TYPE_AIR,id);
}

/*
 * Class:     elle_homegenius_infrajni_InfraNative
 * Method:    getAirAllBrandListLen
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_elle_homegenius_infrajni_InfraNative_getAirAllBrandListLen
  (JNIEnv *env, jobject obj)
{
	return getBrandLenByType(TYPE_AIR);
}

