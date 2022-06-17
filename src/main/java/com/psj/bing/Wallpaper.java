package com.psj.bing;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author psj
 * @date 2022/6/14 22:31
 * @File: Wallpaper.java
 * @Software: IntelliJ IDEA
 */
public class Wallpaper {
    // BING API
    private static String BING_API = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=10&nc=1612409408851&pid=hp&FORM=BEHPTB&uhd=1&uhdwidth=3840&uhdheight=2160";
    private static String BING_URL = "https://cn.bing.com";

    public static void main(String[] args) throws IOException {
        String httpContent = HttpUtils.getHttpContent(BING_API);
        JSONObject jsonObject = JSON.parseObject(httpContent);
        JSONArray jsonArray = jsonObject.getJSONArray("images");

//        System.out.println(jsonArray.get(0));
        jsonObject = (JSONObject) jsonArray.get(0);

        // 获取图片地址
        String url = BING_URL + (String) jsonObject.get("url");
//        System.out.println(url);
        url = url.substring(0, url.indexOf("&"));

        // 获取图片时间
        String enddate = (String) jsonObject.get("enddate");
//        System.out.println(enddate);  // 20220614
        LocalDate localDate = LocalDate.parse(enddate, DateTimeFormatter.BASIC_ISO_DATE.BASIC_ISO_DATE);
//        System.out.println(localDate);  // 2022-06-14
        enddate = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
//        System.out.println(enddate);

        // 获取图片版权
        String copyright = (String) jsonObject.get("copyright");

        List<Images> imagesList = FileUtils.readBing();

        imagesList.set(0,new Images(copyright, enddate, url));
        imagesList = imagesList.stream().distinct().collect(Collectors.toList());
        FileUtils.writeBing(imagesList);
        FileUtils.writeReadme(imagesList);
    }
}
