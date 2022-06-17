package com.psj.bing;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author psj
 * @date 2022/6/14 22:10
 * @File: HttpUtils.java
 * @Software: IntelliJ IDEA
 */
public class HttpUtils {
    /**
     * 获取HTTP连接
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static HttpURLConnection getHttpUrlConnection(String url) throws IOException {
        URL httpUrl = new URL(url);
        HttpURLConnection httpConnection = (HttpURLConnection) httpUrl.openConnection();
        httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
        return httpConnection;
    }

    /**
     * 读取指定URL返回的内容
     * @param url
     * @return
     * @throws IOException
     */
    public static String getHttpContent(String url) throws IOException {
        HttpURLConnection httpUrlConnection = getHttpUrlConnection(url);
        StringBuilder stringBuilder = new StringBuilder();
        // 获得输入流
        InputStream input = httpUrlConnection.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(input);
        byte[] buffer = new byte[1024];
        int len = -1;
        // 读到文件末尾就返回-1
        try {
            while ((len = bis.read(buffer)) != -1) {
                stringBuilder.append(new String(buffer, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpUrlConnection.disconnect();
        }

        return stringBuilder.toString();

    }
}
