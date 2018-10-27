# FFmpegUtils
This class will help you to get media metadata with ffmpeg

# Usage:

    FFmpegOutputUtil.getMediaInfo(this,mediaPath, new FFmpegOutputUtil.GetMetaData() {
        @Override
        public void onMetadataRetrieved(String metadata) {
        }
    });

