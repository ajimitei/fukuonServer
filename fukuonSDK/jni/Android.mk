LOCAL_PATH := $(call my-dir)
 
include $(CLEAR_VARS)
 
LOCAL_MODULE    := FFmpegUtil
LOCAL_SRC_FILES := FFmpegUtil.c

LOCAL_LDLIBS := -llog -ljnigraphics -lz -landroid
LOCAL_SHARED_LIBRARIES := libavformat libavcodec libswscale libavutil libavfilter libwsresample

include $(BUILD_SHARED_LIBRARY)
$(call import-module,ffmpeg-2.5.3/android/arm)