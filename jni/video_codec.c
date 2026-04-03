// jni/video_codec.c
#include "video_codec.h"
#include <media/NdkMediaCodec.h>
#include <media/NdkMediaFormat.h>
#include <android/log.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include <arpa/inet.h>

#define LOG_TAG "VideoCodec"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

// 读取函数声明
int read_(int fd, char* buf, size_t size, int max_size);
int readyz(int fd, char* buf, int size_max);

void video_decode(int fd, ANativeWindow* window, int* running) {
    char buff[77];
    int ret = read_(fd, buff, 69, 69);
    if (ret != 69) {
        LOGE("读取视频头失败");
        return;
    }
    
    int width, height;
    ret = read_(fd, (char*)&width, 4, 69);
    if (ret != 4) return;
    ret = read_(fd, (char*)&height, 4, 69);
    if (ret != 4) return;
    
    width = ntohl(width);
    height = ntohl(height);
    LOGI("视频分辨率: %dx%d", width, height);
    
    ANativeWindow_setBuffersGeometry(window, width, height, WINDOW_FORMAT_RGBX_8888);
    
    AMediaCodec* codec = AMediaCodec_createDecoderByType("video/avc");
    AMediaFormat* format = AMediaFormat_new();
    AMediaFormat_setString(format, "mime", "video/avc");
    AMediaFormat_setInt32(format, AMEDIAFORMAT_KEY_WIDTH, width);
    AMediaFormat_setInt32(format, AMEDIAFORMAT_KEY_HEIGHT, height);
    
    AMediaCodec_configure(codec, format, window, NULL, 0);
    AMediaCodec_start(codec);
    
    LOGI("视频解码开始");
    
    int buffersize = 1024 * 1024 * 6;
    char* buffer = malloc(buffersize);
    int qinputCount = 0,inputCount = 0, outputCount = 0;
    ssize_t bufidx = AMediaCodec_dequeueInputBuffer(codec, -1);
    size_t bufsize;
    uint8_t* buf = AMediaCodec_getInputBuffer(codec, bufidx, &bufsize);
            int size = readyz(fd, (char*)buf, bufsize);
              if (size > 0) 
            AMediaCodec_queueInputBuffer(codec, bufidx, 0, size, 0, 0);
    while (*running) {
        bufidx = AMediaCodec_dequeueInputBuffer(codec, 0);
        if (bufidx >= 0) {
            
            buf = AMediaCodec_getInputBuffer(codec, bufidx, &bufsize);
            size = readyz(fd, (char*)buf, bufsize);
            if (size > 0) {
                AMediaCodec_queueInputBuffer(codec, bufidx, 0, size, 0, 0);
                inputCount++;
            }
        }else{
        readyz(fd,buffer,buffersize); 
        qinputCount++;
        }
        AMediaCodecBufferInfo info;
        while(1){
        ssize_t outidx = AMediaCodec_dequeueOutputBuffer(codec, &info, 0);
        if (outidx < 0) 
            break;
            
                AMediaCodec_releaseOutputBuffer(codec, outidx, 1); 
            outputCount++;
        }
        
        if (inputCount % 30 == 0) {
            LOGI("视频: 输入=%d 输出=%d,丢弃=%d", inputCount, outputCount,qinputCount);
        }
    }
    
   // free(buffer);
    AMediaCodec_stop(codec);
    AMediaCodec_delete(codec);
    AMediaFormat_delete(format);
}
