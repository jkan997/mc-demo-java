package com.adobe.demo.mcdemo;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

/**
 *
 * @author jakaniew
 */
public class IOHelper {

    public static void readInputStreamToOutputStream(InputStream is, OutputStream os) throws IOException {
        byte[] buf = new byte[100 * 1024];
        int count;
        while ((count = is.read(buf)) > 0) {
            os.write(buf, 0, count);
        }
    }

    public static byte[] readInputStreamToBytes(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        readInputStreamToOutputStream(is, bos);
        bos.close();
        return bos.toByteArray();
    }

    public static String readInputStreamToString(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        readInputStreamToOutputStream(is, bos);
        bos.close();
        String res = new String(bos.toByteArray(), "UTF-8");
        return res;
    }

    public static String readReaderToString(Reader initialReader) throws IOException {
        char[] arr = new char[8 * 1024];
        StringBuilder buffer = new StringBuilder();
        int numCharsRead;
        while ((numCharsRead = initialReader.read(arr, 0, arr.length)) != -1) {
            buffer.append(arr, 0, numCharsRead);
        }
        initialReader.close();
        return buffer.toString();
    }

    public static void close(InputStream is) {
        try {
            is.close();
        } catch (Exception ex) {
        };
    }

    public static void close(OutputStream os) {
        try {
            os.close();
        } catch (Exception ex) {
        };
    }

    public static void copyFile(String src, String dest) throws IOException {
        FileInputStream fis = new FileInputStream(src);
        FileOutputStream fos = new FileOutputStream(dest);
        readInputStreamToOutputStream(fis, fos);
        fis.close();
        fos.close();
    }
}
