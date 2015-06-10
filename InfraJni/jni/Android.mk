LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := InfraJni
LOCAL_SRC_FILES := InfraJni.c InfraLib.c AirInfo.c AirCompare.c AirCommand.c LearnInOut.c TvCommand.c TvInfo.c 

include $(BUILD_SHARED_LIBRARY)
