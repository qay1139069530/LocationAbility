package com.qbase.locationability;

import android.content.Context;
import android.os.Environment;
import android.text.format.Time;

import java.io.File;

/**
 * Created by qay
 */

public class PathUtil {
    public static String pathPrefix;
    public static final String historyPathName = "/chat/";
    public static final String imagePathName = "/image/";
    public static final String voicePathName = "/voice/";
    public static final String filePathName = "/file/";
    public static final String videoPathName = "/video/";
    public static final String netdiskDownloadPathName = "/netdisk/";
    public static final String meetingPathName = "/meeting/";
    public static final String imageHeadPathName = "/image/head";
    public static final String voiceExtension = ".mp3";
    public static final String imageExtension = "";

    private final static Object obj = new Object();

    private static File storageDir = null;
    private static PathUtil instance = null;
    private File voicePath = null;
    private File imagePath = null;
    private File imageHeadPath = null;
    private File imageThumbPath = null;
    private File historyPath = null;
    private File videoPath = null;
    private File videoThumbPath = null;
    private File filePath;


    private final String savePath = "/Cloudy/data/";

    public static PathUtil getInstance() {
        synchronized (obj) {
            if (instance == null)
                instance = new PathUtil();
            return instance;
        }

    }

    public void initDirs(String paramString1, String paramString2, Context paramContext) {
        String str = paramContext.getPackageName();
        pathPrefix = savePath + str + "/";
        this.voicePath = generateVoicePath(paramString1, paramString2, paramContext);
        if (!this.voicePath.exists())
            this.voicePath.mkdirs();
        this.imagePath = generateImagePath(paramString1, paramString2, paramContext);
        if (!this.imagePath.exists())
            this.imagePath.mkdirs();

        this.imageHeadPath = generateImageHeadPath(paramString1, paramString2, paramContext);
        if (!this.imageHeadPath.exists())
            this.imageHeadPath.mkdirs();
        this.historyPath = generateHistoryPath(paramString1, paramString2, paramContext);
        if (!this.historyPath.exists())
            this.historyPath.mkdirs();

        this.imageThumbPath = generateImageThumbPath(paramString1, paramString2, paramContext);
        if (!this.imageThumbPath.exists())
            this.imageThumbPath.mkdirs();

        this.videoPath = generateVideoPath(paramString1, paramString2, paramContext);
        if (!this.videoPath.exists())
            this.videoPath.mkdirs();
        this.videoThumbPath = generateVideoThumbPath(paramString1, paramString2, paramContext);
        if (!this.videoThumbPath.exists())
            this.videoThumbPath.mkdirs();
        this.filePath = generateFiePath(paramString1, paramString2, paramContext);
        if (this.filePath.exists())
            return;
        this.filePath.mkdirs();
    }

    public static String getImagePath(String remoteUrl) {
        String imageName = remoteUrl.substring(remoteUrl.lastIndexOf("/") + 1, remoteUrl.length());
        String path = PathUtil.getInstance().getImagePath() + "/" + imageName;
        return path;

    }


    public static String getThumbnailImagePath(String thumbRemoteUrl) {
        String thumbImageName = thumbRemoteUrl.substring(thumbRemoteUrl.lastIndexOf("/") + 1, thumbRemoteUrl.length());
        String path = PathUtil.getInstance().getImagePath() + "/" + "th" + thumbImageName;
        return path;
    }

    public File getImagePath() {
        if (!this.imagePath.exists()) {
            this.imagePath.mkdirs();
        }

        return this.imagePath;
    }

    public File getImageHeadPath() {
        if (!this.imageHeadPath.exists()) {
            this.imageHeadPath.mkdirs();
        }

        return this.imageHeadPath;
    }

    public File getVideoThumbPath() {

        if (!this.videoThumbPath.exists()) {
            this.videoThumbPath.mkdirs();
        }

        return
                this.videoThumbPath;
    }

    public File getVoicePath() {
        if (!this.voicePath.exists()) {
            this.voicePath.mkdirs();
        }

        return this.voicePath;
    }

    public File getFilePath() {
        if (!this.filePath.exists()) {
            this.filePath.mkdirs();
        }

        return this.filePath;
    }

    public File getVideoPath() {
        if (!this.videoPath.exists()) {
            this.videoPath.mkdirs();
        }

        return this.videoPath;
    }

    public File getHistoryPath() {
        if (!this.historyPath.exists()) {
            this.historyPath.mkdirs();
        }
        return this.historyPath;
    }

    public File getImageThumbPath() {
        if (!this.imageThumbPath.exists()) {
            this.imageThumbPath.mkdirs();
        }
        return this.imageThumbPath;
    }

    private static File getStorageDir(Context paramContext) {
        if (storageDir == null) {
            File localFile = Environment.getExternalStorageDirectory();
            if (localFile.exists())
                return localFile;
            storageDir = paramContext.getFilesDir();
        }
        return storageDir;
    }

    private static File generateImagePath(String paramString1, String paramString2, Context paramContext) {
        String str = null;
        if (paramString1 == null)
            str = pathPrefix + paramString2 + "/image/";
        else
            str = pathPrefix + paramString1 + "/" + paramString2 + "/image/";
        return new File(getStorageDir(paramContext), str);
    }

    private static File generateImageHeadPath(String paramString1, String paramString2, Context paramContext) {
        String str = null;
        if (paramString1 == null)
            str = pathPrefix + paramString2 + "/image/head/";
        else
            str = pathPrefix + paramString1 + "/" + paramString2 + "/image/head/";
        return new File(getStorageDir(paramContext), str);
    }

    private static File generateImageThumbPath(String paramString1, String paramString2, Context paramContext) {
        String str = null;
        if (paramString1 == null)
            str = pathPrefix + paramString2 + "/image/thumb/";
        else
            str = pathPrefix + paramString1 + "/" + paramString2 + "/image/thumb/";
        return new File(getStorageDir(paramContext), str);
    }

    /**
     * 音频文件
     *
     * @param uid
     * @return
     */
    private static String getVoiceFileName(String uid) {
        Time now = new Time();
        now.setToNow();
        return uid + now.toString().substring(0, 15) + voiceExtension;
    }


    /**
     * 照片文件
     *
     * @param uid
     * @return
     */
    private static String getImageFileName(String uid) {
        Time now = new Time();
        now.setToNow();
        return uid + now.toString().substring(0, 15) + imageExtension;
    }

    private static File generateVideoThumbPath(String paramString1, String paramString2, Context paramContext) {
        String str = null;
        if (paramString1 == null)
            str = pathPrefix + paramString2 + "/video/thumb/";
        else
            str = pathPrefix + paramString1 + "/" + paramString2 + "/video/thumb/";
        return new File(getStorageDir(paramContext), str);
    }


    private static File generateVoicePath(String paramString1, String paramString2, Context paramContext) {
        String str = null;
        if (paramString1 == null)
            str = pathPrefix + paramString2 + "/voice/";
        else
            str = pathPrefix + paramString1 + "/" + paramString2 + "/voice/";
        return new File(getStorageDir(paramContext), str);
    }

    private static File generateFiePath(String paramString1, String paramString2, Context paramContext) {
        String str = null;
        if (paramString1 == null)
            str = pathPrefix + paramString2 + "/file/";
        else
            str = pathPrefix + paramString1 + "/" + paramString2 + "/file/";
        return new File(getStorageDir(paramContext), str);
    }

    private static File generateVideoPath(String paramString1, String paramString2, Context paramContext) {
        String str = null;
        if (paramString1 == null)
            str = pathPrefix + paramString2 + "/video/";
        else
            str = pathPrefix + paramString1 + "/" + paramString2 + "/video/";
        return new File(getStorageDir(paramContext), str);
    }

    private static File generateHistoryPath(String paramString1, String paramString2, Context paramContext) {
        String str = null;
        if (paramString1 == null)
            str = pathPrefix + paramString2 + "/chat/";
        else
            str = pathPrefix + paramString1 + "/" + paramString2 + "/chat/";
        return new File(getStorageDir(paramContext), str);
    }

    private static File generateMessagePath(String paramString1, String paramString2, Context paramContext) {
        File localFile = new File(generateHistoryPath(paramString1, paramString2, paramContext), paramString2 + File.separator + "Msg.db");
        return localFile;
    }

    public static File getTempPath(File paramFile) {
        File localFile = new File(paramFile.getAbsoluteFile() + ".tmp");
        return localFile;
    }
}
