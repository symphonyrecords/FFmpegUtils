# FFmpegUtils
This class will help you to get media metadata using [FFMPEG](https://www.ffmpeg.org)

## Usage:

    FFmpegOutputUtil.getMediaInfo(this,inPath, new FFmpegOutputUtil.GetMetaData() {
        @Override
        public void onMetadataRetrieved(Map<String, String> map) {
           // Log.d("VIDEO_Resolution"  , map.get("VIDEO_Resolution"));
            for (String key : map.keySet()) {
                Log.d("DEBUG_"+key  ,  map.get(key)      );
            }
        }
    });
    
    
## Sample output

    D/OUTPUT_WritingApplication: : Lavf55.48.100
    D/OUTPUT_major_brand: : isom
    D/OUTPUT_minor_version: : 512
    D/OUTPUT_compatible_brands: : isomiso2avc1mp41
    D/OUTPUT_handler_name: : VideoHandler
    D/OUTPUT_comment: : http://www.aparat.com/kurdmedia1
    D/OUTPUT_copyright: : copyright
    D/OUTPUT_OverallBitrate_DataRate: : 2330 kb/s
    D/OUTPUT_Duration: : 00:00:03.05
    D/OUTPUT_start: 0.000000
    I/OUTPUT_: VIDEO ************ VIDEO ************
    D/OUTPUT_VIDEO_INFO: Video: h264 (avc1 / 0x31637661), yuv420p, 1280x720 [SAR 1:1 DAR 16:9], 2195 kb/s, 23.98 fps
    D/OUTPUT_VIDEO_FPS: 23.98 fps
    D/OUTPUT_VIDEO_Bitrate: 2195 kb/s
    D/OUTPUT_VIDEO_Resolution: 1280x720
    D/OUTPUT_VIDEO_Codec: h264
    I/OUTPUT_: AUDIO ************ AUDIO ************
    D/OUTPUT_AUDIO_INFO: Audio: aac (mp4a / 0x6134706D), 44100 Hz, stereo, fltp, 128 kb/s (default)
    D/OUTPUT_AUDIO_Bitrate: 128 kb/s
    D/OUTPUT_AUDIO_SampleRate: 44100 Hz
    D/OUTPUT_AUDIO_AudioChanel: 2
    D/OUTPUT_AUDIO_Codec: aac
