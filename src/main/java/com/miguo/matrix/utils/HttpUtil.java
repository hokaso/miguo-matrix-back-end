package com.miguo.matrix.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import org.springframework.http.HttpStatus;

/**
 * @author Hocassian
 */
public class HttpUtil {

    public static String doGet(String urlPath, HashMap<String, Object> params) throws Exception {
        StringBuilder sb = new StringBuilder(urlPath);
        if (params != null && !params.isEmpty()) {
            sb.append("?");
            Set<Entry<String, Object>> set = params.entrySet();
            for (Entry<String, Object> entry : set) {
                String key = entry.getKey();
                String value = "";
                if (null != entry.getValue()) {
                    value = entry.getValue().toString();
                    // 转码
                    value = URLEncoder.encode(value, StandardCharsets.UTF_8);
                }
                sb.append(key).append("=").append(value).append("&");
            }

            sb.deleteCharAt(sb.length() - 1);
        }
        URL url = new URL(sb.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            StringBuilder sbs = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sbs.append(line);
            }
            return sbs.toString();
        }

        return null;
    }
}