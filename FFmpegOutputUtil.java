package com.symphonyrecords.mediacomp.design;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.bravobit.ffmpeg.ExecuteBinaryResponseHandler;
import nl.bravobit.ffmpeg.FFmpeg;


@SuppressWarnings({"unused", "WeakerAccess"})
public class FFmpegOutputUtil {

    private static String OUTPUT_TAG = "OUTPUT_";

    private static String separator = System.getProperty("line.separator");


    public FFmpegOutputUtil() {
    }


    public interface GetMetaData {
        void onMetadataRetrieved(String metadata);
    }

    public static void getMediaInfo(Context context, String input, GetMetaData metaData) {
        List<String> commandList = new LinkedList<>();
        commandList.add("-i");
        commandList.add(input);
        String[] command = commandList.toArray(new String[0]);
        try {
            FFmpeg.getInstance(context).execute(command, new ExecuteBinaryResponseHandler() {
                @Override
                public void onFailure(String s) {
                    Log.d("line.separator", "Failed to convert: Reason:\n" + s);
//                    Log.d("convertTest_onSuccess", "Successfully Converted - MEDIA_METADATA-AUDIO :\n " + s);
                    metaData.onMetadataRetrieved(s);
                    String usefulMetadata = getUsefulDataFromOutput2(s);
                    Log.d("BBB_usefulMetadata", usefulMetadata);

                    // TODO: 10/26/2018
                    Log.d(OUTPUT_TAG + "WritingApplication", getSingleValueFromOutputEndsWithBreakLine(usefulMetadata, "encoder"));
                    Log.d(OUTPUT_TAG + "creation_time", getSingleValueFromOutputEndsWithBreakLine(usefulMetadata, "creation_time"));
                    Log.d(OUTPUT_TAG + "major_brand", getSingleValueFromOutputEndsWithBreakLine(usefulMetadata, "major_brand"));
                    Log.d(OUTPUT_TAG + "minor_version", getSingleValueFromOutputEndsWithBreakLine(usefulMetadata, "minor_version"));
                    Log.d(OUTPUT_TAG + "compatible_brands", getSingleValueFromOutputEndsWithBreakLine(usefulMetadata, "compatible_brands"));
                    Log.d(OUTPUT_TAG + "handler_name", getSingleValueFromOutputEndsWithBreakLine(usefulMetadata, "handler_name"));
                    Log.d(OUTPUT_TAG + "comment", getSingleValueFromOutputEndsWithBreakLine(usefulMetadata, "comment"));
                    Log.d(OUTPUT_TAG + "copyright", getSingleValueFromOutputEndsWithBreakLine(usefulMetadata, "copyright"));
                    Log.d(OUTPUT_TAG + "OverallBitrate_DataRate", getSingleValueFromOutputEndsWithBreakLine(usefulMetadata, "bitrate"));
                    Log.d(OUTPUT_TAG + "Duration", getSingleValueFromOutputEndsWithComma(usefulMetadata, "Duration", ","));
                    Log.d(OUTPUT_TAG + "start", getSingleValueFromOutputEndsWithComma(usefulMetadata, "start:", ","));

//                    Log.d(OUTPUT_TAG + "FILE_MimeType", getMediaMimeType(input));


/////////////////////////////////////////////////////****VIDEO****///////////////////////////////////////////////////////////////////////////
                    String videoLine = removeEverythingBefore(findWordAndReturnWholeLine(usefulMetadata, "Video:"), "Video:");
                    Log.i(OUTPUT_TAG, "VIDEO ************ VIDEO ************");
                    Log.d(OUTPUT_TAG + "ALL_VIDEO_INFO", videoLine);
                    Log.d(OUTPUT_TAG + "VIDEO_FPS", getFPS(videoLine));
                    Log.d(OUTPUT_TAG + "VIDEO_Bitrate", getBitrate(videoLine));
                    Log.d(OUTPUT_TAG + "VIDEO_Resolution", getVideoResolution(input));
                    Log.d(OUTPUT_TAG + "VIDEO_Codec", getCodec(videoLine));

/////////////////////////////////////////////////////****AUDIO****///////////////////////////////////////////////////////////////////////////
                    String audioLine = removeEverythingBefore(findWordAndReturnWholeLine(usefulMetadata, "Audio:"), "Audio:");
                    Log.i(OUTPUT_TAG, "AUDIO ************ AUDIO ************");
                    Log.d(OUTPUT_TAG + "ALL_AUDIO_INFO", audioLine);
                    Log.d(OUTPUT_TAG + "AUDIO_Bitrate", getBitrate(audioLine));
                    Log.d(OUTPUT_TAG + "AUDIO_SampleRate", getAudioSampleRate(audioLine));
                    Log.d(OUTPUT_TAG + "AUDIO_AudioChanel", getAudioChanel(audioLine));
                    Log.d(OUTPUT_TAG + "AUDIO_Codec", getCodec(audioLine));
                }
                @Override
                public void onSuccess(String s) {
                }
                @Override
                public void onFinish() {
                }
            });
        } catch (Throwable ignored) {
        }
    }


    public static String findWordAndReturnWholeLine(String input, String word) {
        try {
            Scanner scanner = new Scanner(input);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.contains(word)) {
                    return line;
                }
            }
            scanner.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getSingleValueFromOutputEndsWithComma(String input, String word, String separator) {
        try {
            String yourValue;
            int ind = input.indexOf(word);
            if (ind >= 0) {
                yourValue = input.substring(ind + word.length(), input.length()).trim();
                yourValue = removeEverythingAfter(yourValue, separator);
                return yourValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getSingleValueFromOutputEndsWithBreakLine(String input, String word) {
        try {
            String yourValue;
            int ind = input.indexOf(word);
            if (ind >= 0) {
                yourValue = input.substring(ind + word.length(), input.length()).trim();
//                Log.d("FFsFss", String.valueOf(ind));
                yourValue = removeEverythingAfter(yourValue, separator);
                return yourValue;
            }else
                return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getUsefulDataFromOutput2(String input) {
        try {
//            String a = removeEverythingBefore(input, "Input");
            return removeEverythingAfter(removeEverythingBefore(input, "Input"), "Output");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String removeEverythingBefore(String input, String word) {
        try {
            return input.substring(input.indexOf(word));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String removeEverythingBefore(String input, String word, int howManyOfStartedWord) {
        try {
            return input.substring(input.indexOf(word) + howManyOfStartedWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String removeEverythingAfter(String input, String word) {
        try {
            return input.split(word)[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String removeLastXNumberChar(String str, int howMany) {
        try {
            if (str != null && str.length() > 0 && str.charAt(str.length() - howMany) == 'x') {
                str = str.substring(0, str.length() - howMany);
            }
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String removeFirstXNumberChar(String str, int howMany) {
        try {
            return str.substring(howMany);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getSize(String s) {
        try {
            String fileSizeReadable = "";
            if (s.contains("size=")) {
                String str = s.substring(s.lastIndexOf("size="));
                String substr = removeFirstXNumberChar(str, 5);
                String oo = removeLastXNumberChar(substr.trim(), 2);
                String result = oo.split("kB")[0];
                long size = 0;
                try {
                    size = Long.parseLong(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (size > 0)
                    fileSizeReadable = getReadableFileSize(size * 1024);
            }
            return fileSizeReadable;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static long getProgress(String message, long videoLengthInMillis) {
        try {
            Pattern pattern = Pattern.compile("time=([\\d\\w:]{8}[\\w.][\\d]+)");
            if (message.contains("speed")) {
                Matcher matcher = pattern.matcher(message);
                //noinspection ResultOfMethodCallIgnored
                String tempTime = "00:00:00";
                if (matcher.find()) {
                    try {
                        tempTime = String.valueOf(matcher.group(1));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String[] arrayTime = tempTime.split("[:|.]");
                    long currentTime =
                            TimeUnit.HOURS.toMillis(Long.parseLong(arrayTime[0]))
                                    + TimeUnit.MINUTES.toMillis(Long.parseLong(arrayTime[1]))
                                    + TimeUnit.SECONDS.toMillis(Long.parseLong(arrayTime[2]))
                                    + Long.parseLong(arrayTime[3]);

                    @SuppressWarnings("UnusedAssignment")
                    long result = 0;
                    result = 100 * currentTime / videoLengthInMillis;

                    if (result > 100)
                        return 100;
                    else
                        return result;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static String getRemainingTime(String message, long videoLengthInMillis) {
        try {
            Pattern pattern = Pattern.compile("time=([\\d\\w:]{8}[\\w.][\\d]+)");
            if (message.contains("speed")) {
                Matcher matcher = pattern.matcher(message);
                @SuppressWarnings("UnusedAssignment")
                String tempTime = "";
                if (matcher.find()) {
                    tempTime = String.valueOf(matcher.group(1));
                    String[] arrayTime = tempTime.split("[:|.]");
                    long time =
                            TimeUnit.HOURS.toMillis(Long.parseLong(arrayTime[0])) +
                                    TimeUnit.MINUTES.toMillis(Long.parseLong(arrayTime[1])) +
                                    TimeUnit.SECONDS.toMillis(Long.parseLong(arrayTime[2])) +
                                    Math.round(Long.parseLong(arrayTime[3]));
                    String speed = message.substring(message.indexOf("speed=") + 1, message.indexOf("x")).split("=")[1];
                    long eta = Math.round((Math.round(videoLengthInMillis) - time) / Float.valueOf(speed));
                    String estimatedTime = toTimer(eta);
                    Log.d("getRemainingTime", "EstimateTime -> " + estimatedTime);
                    return estimatedTime;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String toTimer(long millis) {
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));
        if (hours != 0 && hours > 0)
            return String.format(Locale.ENGLISH, "%02d:%02d:%02d", hours, minutes, seconds);
        else
            return String.format(Locale.ENGLISH, "%02d:%02d", minutes, seconds);
    }


    public static String getSpeed1(String message) {
        try {
            Pattern pattern = Pattern.compile("time=([\\d\\w:]{8}[\\w.][\\d]+)");
            if (message.contains("speed")) {
                Matcher matcher = pattern.matcher(message);
                if (matcher.find()) {
                    String speed = message.substring(message.indexOf("speed=") + 1, message.indexOf("x")).split("=")[1];
                    Log.d("getRemainingTime", "speed -> " + speed);
                    return speed;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getSpeed2(String s) {
        String substr;
        try {
            String str;
            substr = "";
            if (s.contains("speed=")) {
                str = s.substring(s.lastIndexOf("speed="));
                substr = removeFirstXNumberChar(str, 6);
            }
            removeLastXNumberChar(substr.trim(), 1);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getReadableFileSize(long size) {
        try {
            final int BYTES_IN_KILOBYTES = 1024;
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ENGLISH);
            final DecimalFormat dec = new DecimalFormat("###.##", symbols);
            final String KILOBYTES = " KB";
            final String MEGABYTES = " MB";
            final String GIGABYTES = " GB";
            float fileSize = 0;
            String suffix = KILOBYTES;
            if (size > BYTES_IN_KILOBYTES) {
                fileSize = size / BYTES_IN_KILOBYTES;
                if (fileSize > BYTES_IN_KILOBYTES) {
                    fileSize = fileSize / BYTES_IN_KILOBYTES;
                    if (fileSize > BYTES_IN_KILOBYTES) {
                        fileSize = fileSize / BYTES_IN_KILOBYTES;
                        suffix = GIGABYTES;
                    } else {
                        suffix = MEGABYTES;
                    }
                }
            }
            return String.valueOf(dec.format(fileSize) + suffix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String RemoveEveryCharBeforeFirstComma(String input) {
        try {
//         String oo =  input.replaceFirst("\\w*,", "");
            return input.substring(input.lastIndexOf(',') + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getFPS(String input) {
        try {
            String a = removeEverythingAfter(input, "fps");
            String b = RemoveEveryCharBeforeFirstComma(a).trim();
            if (!b.equals(""))
                return b+" fps";
            else
                return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getBitrate(String input) {
        try {
            String a = removeEverythingAfter(input, "kb/s");
            String b = RemoveEveryCharBeforeFirstComma(a).trim();
            if (!b.equals(""))
                return b+" kb/s";
            else
                return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }



    public static String getVideoResolution(String input) {
        try {
            String width = getVideoWIDTH(input);
            String height = getVideoHEIGHT(input);
            if (!width.equals("") && !height.equals("")) {
                return width + "x" + height;
            }else
                return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String getVideoWIDTH(String inPath) {
        try {
            MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
            metaRetriever.setDataSource(inPath);
            String width = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            if (width != null) return width;
            else return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String getVideoHEIGHT(String inPath) {
        try {
            MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
            metaRetriever.setDataSource(inPath);
            String height = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            if (height != null) return height;
            else return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";

        }
    }






    public static String getCodec(String input) {
        try {
            String a = removeEverythingAfter(input, ",");
            a = a.substring(a.lastIndexOf(':') + 1);

            //   \\(  //This part matches the opening bracket
            //   .*?  //This part matches anything in between
            //   \\)  //This part matches the closing bracket
            a = a.replaceAll("\\(.*?\\)", "");

            return a.trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getAudioChanel(String input) {
        try {
            if (input.contains("stereo"))
                return String.valueOf(2);
            else if (input.contains("mono"))
                return String.valueOf(1);
            else
                return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getAudioSampleRate(String input) {
        try {
            String a = removeEverythingAfter(input, "Hz");
            return RemoveEveryCharBeforeFirstComma(a).trim()+ " Hz";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }



}

