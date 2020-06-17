package com.qbase.locationability;

import android.text.TextUtils;

import java.io.File;
import java.io.FileWriter;

public class LogUtil {

    public static void saveLog(final String log) {
        AsyncExec.submitEvent(() -> {
            try {
                if (TextUtils.isEmpty(log)) {
                    return;
                }
                String fileName = AuxiliaryParams.SD_FILE + File.separator + "Gps-" + TimeUtil.getCurrentTime(TimeUtil.YYYY_MM_DD_BARS) + ".log";
                File path = new File(AuxiliaryParams.SD_FILE);
                // 创建目录
                if (!path.exists()) {
                    path.mkdirs();
                }

                FileWriter stream = new FileWriter(fileName, true);
                String strDate = TimeUtil.getCurrentTime();// 获取当前时间

                if (log.length() < 2) {
                    stream.write("\r\n\r\n");
                    stream.flush();
                    stream.close();
                    return;
                }
                stream.write(strDate);
                stream.write("\n");
                stream.write("##");
                stream.write("##");
                stream.write(log);
                stream.write("\r\n");
                stream.flush();
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static String getLocationString(SignPositionBean bean) {
        if (bean == null) {
            return "";
        }
        return "定位方式：" + bean.getLocationType() + "--" +
                "精度-米：" + bean.getAccuracy() + "--" +
                "星数：" + bean.getSatellites() + "--" +
                "定位时间：" + bean.getTime() + "--" +
                "地址：" + bean.getAddress() + "\n";
    }
}
