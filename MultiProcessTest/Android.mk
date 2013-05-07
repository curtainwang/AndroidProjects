LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := multiprocess test

# Only compile source java files in this apk.
LOCAL_SRC_FILES := $(call all-java-files-under, src)
LOCAL_SRC_FILES += \
        src/com/myq/android/MultiProcessTest/IDateTimeService.aidl
        
LOCAL_PACKAGE_NAME := MultiProcessTest

LOCAL_SDK_VERSION := current

include $(BUILD_PACKAGE)

# Use the folloing include to make our test apk.
include $(call all-makefiles-under,$(LOCAL_PATH))
