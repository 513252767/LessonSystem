package com.hung.util;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author Hung
 */
public class ErrorTest {
    @Test
    public void logTest() throws IOException {
        try {
            NullPointerException nullPointerException = new NullPointerException();
            String text= nullPointerException.getMessage() + nullPointerException.getCause();
            File file = new File("log/error.log");
            PrintWriter printWriter = new PrintWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(printWriter);
            bufferedWriter.write(text);
            bufferedWriter.flush();

            LogWriter.log("1");

        }catch (Exception e){
            StackTraceElement[] stackTrace = e.getStackTrace();
            LogWriter.log(LocalDateTime.now()+"  "+ Arrays.toString(stackTrace) +"   "+e.getMessage());
        }

    }
}
