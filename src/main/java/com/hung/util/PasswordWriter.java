package com.hung.util;

import com.hung.util.aes.AesUtil;

import java.io.*;

/**
 * 记住密码记录文件
 * @author Hung
 */
public class PasswordWriter {

    private static final String LOG_FILE_NAME = "password.txt";

    public static String[] read() {
        String[] strings = new String[2];
        try {
            FileReader reader = getReader();
            BufferedReader bufferedReader = new BufferedReader(reader);

            //记录账号
            String name = bufferedReader.readLine();
            strings[0]=name;
            String p = bufferedReader.readLine();
            String password = AesUtil.decryptStr(p, name);
            strings[1]=password;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings;
    }

    public static void write(String name,String password){
        try {
            FileWriter writer = getWriter();
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(name);
            String encryptStr = AesUtil.encryptStr(password, name);
            bufferedWriter.newLine();
            bufferedWriter.write(encryptStr);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static FileReader getReader() throws IOException {
        File file = new File(LOG_FILE_NAME);
        if (!file.exists()) {
            file.createNewFile();
        }
        return new FileReader(file);
    }

    public static FileWriter getWriter() throws IOException {
        File file = new File(LOG_FILE_NAME);
        if (!file.exists()) {
            file.createNewFile();
        }
        return new FileWriter(file);
    }

}
