package com.hung.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 日志记录
 * @author Hung
 */
public class LogWriter {
    /**
     *    可以写作配置：true写文件; false输出控制台
     */
    private static boolean fileLog = true;
    private static String logFileName = "log/error.log";

    public static void log(String info) throws IOException {
        OutputStream out = getOutputStream();
        out.write(info.getBytes(StandardCharsets.UTF_8));
    }

    public static OutputStream getOutputStream() throws IOException {
        if (fileLog) {
            File file = new File(logFileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            return new FileOutputStream(file);
        } else {
            return System.out;
        }
    }
}