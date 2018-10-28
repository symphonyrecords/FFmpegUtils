# FFmpegUtils
This class will help you to get media metadata using [FFMPEG](https://www.ffmpeg.org)


## Usage:

    FFmpegOutputUtil.getMediaInfo(this, mediaPath, new FFmpegOutputUtil.GetMetaData() {
        @Override
        public void onMetadataRetrieved(Map<String, String> map) {
           // Log.d("VIDEO_Resolution"  , map.get("VIDEO_Resolution"));
            for (String key : map.keySet()) {
                Log.d("DEBUG_"+key  ,  map.get(key)      );
            }
        }
    });
    
    
## Sample output

    D/DEBUG_WritingApplication: AVC Coding
    D/DEBUG_creation_time: 2016-10-04T02:22:26.000000Z
    D/DEBUG_major_brand: M4V 
    D/DEBUG_minor_version: 1
    D/DEBUG_compatible_brands: M4V mp42isom
    D/DEBUG_handler_name: Mainconcept MP4 Video Media Handler
    D/DEBUG_OverallBitrate_DataRate: 1066 kb/s
    D/DEBUG_Duration: 00:00:15.09
    D/DEBUG_start: 0.000000
    D/DEBUG_VIDEO_INFO: Video: h264 (avc1 / 0x31637661), yuv420p(tv, smpte170m), 640x360, 935 kb/s, 30 fps
    D/DEBUG_VIDEO_FPS: 30 fps
    D/DEBUG_VIDEO_Bitrate: 935 kb/s
    D/DEBUG_VIDEO_Resolution: 640x360
    D/DEBUG_VIDEO_Codec: h264
    D/DEBUG_VIDEO_ColorSpace: yuv420p
    D/DEBUG_AUDIO_INFO: Audio: aac (mp4a / 0x6134706D), 44100 Hz, stereo, fltp, 125 kb/s (default)
    D/DEBUG_AUDIO_Bitrate: 125 kb/s
    D/DEBUG_AUDIO_SampleRate: 44100 Hz
    D/DEBUG_AUDIO_AudioChanel: 2
    D/DEBUG_AUDIO_Codec: aac

