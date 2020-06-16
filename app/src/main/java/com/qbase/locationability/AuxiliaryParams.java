package com.qbase.locationability;

/**
 * Created by qay
 */
public class AuxiliaryParams {

    /**
     * 保存图片地址
     */
    public final static String SD_PHOTOS = PathUtil.getInstance().getImagePath().getAbsolutePath() + "/";
    /**
     * 保存图片地址
     */
    public final static String SD_DOWNLOAD = PathUtil.getInstance().getFilePath().getAbsolutePath() + "/";
    /**
     * 历史保存
     */
    public final static String SD_HISTORY = PathUtil.getInstance().getHistoryPath().getAbsolutePath() + "/";
    /**
     * 录音保存地址
     */
    public final static String SD_VOICE = PathUtil.getInstance().getVoicePath().getAbsolutePath() + "/";

    /**
     * 文件保存地址
     */
    public final static String SD_FILE = PathUtil.getInstance().getFilePath().getAbsolutePath() + "/";
    /**
     * 录像保存地址
     */
    public final static String SD_VIDEO = PathUtil.getInstance().getVideoPath().getAbsolutePath() + "/";
    /**
     * 保存version地址
     */
    public final static String SD_VERSION = SD_DOWNLOAD + "version.txt";
    /**
     * 版本控制text
     */
    public static final String VERSION_TEXT = "appfiles/version.txt";

    /**
     * 使用本地文件路径
     */
    public static final String PATH_FILA_HEAD = "file://";
    /**
     * 分页页数
     */
    public static final int PAGE_SIZE = 10;
    /**
     * 图片后缀名
     */
    public final static String IMAGE_SUFFIX = ".png";
}
