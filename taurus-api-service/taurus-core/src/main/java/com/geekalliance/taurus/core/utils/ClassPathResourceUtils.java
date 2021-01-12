package com.geekalliance.taurus.core.utils;

import com.geekalliance.taurus.toolkit.StringPool;
import com.geekalliance.taurus.toolkit.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

@Slf4j
public class ClassPathResourceUtils {
    public static String getResourceContent(String resourcePath) {
        ClassPathResource resource = null;
        if (StringUtils.isNotBlank(resourcePath)) {
            resource = new ClassPathResource(resourcePath);
        } else {
            throw new RuntimeException("resource path not blank");
        }
        StringBuffer buffer = new StringBuffer();
        try {
            return getResourceContent(resource.getInputStream());
        } catch (IOException e) {
            log.error("get resource content by classpath io exception");
        }
        return buffer.toString();
    }

    private static String getResourceContent(InputStream inputStream) {
        StringBuffer buffer = new StringBuffer();
        try {
            //利用输入流获取XML文件内容
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StringPool.UTF_8));
            String line = "";
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            br.close();
        } catch (UnsupportedEncodingException e) {
            log.error("get resource content unsupported encoding exception");
        } catch (IOException e) {
            log.error("get resource content io exception");
        }
        return buffer.toString();
    }
}
