package com.geekalliance.taurus.base.api.auth.register.utils;

import com.geekalliance.taurus.base.api.auth.register.entity.RegisterApplication;
import com.geekalliance.taurus.base.api.auth.register.entity.RegisterApplications;
import com.geekalliance.taurus.core.utils.ClassPathResourceUtils;
import com.geekalliance.taurus.toolkit.StringPool;
import com.geekalliance.taurus.toolkit.utils.CollectionUtils;
import com.geekalliance.taurus.toolkit.utils.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Description
 * @Date 2019/12/22
 * @Author maxuqiang
 **/
@Slf4j
public class GenRegisterResourceUtil {
    private static final String APPLICATION_INIT = "i18n/application_init_" + LocaleContextHolder.getLocale() + StringPool.DOT_XML;

    public static List<RegisterApplication> getApplication(String resourcePath) {
        return getApplicationsByContent(getResourceContent(resourcePath));
    }

    public static List<RegisterApplication> getApplication() {
        return getApplicationsByContent(getResourceContent(APPLICATION_INIT));
    }

    private static String getResourceContent(String resourcePath) {
        if (StringUtils.isNotBlank(resourcePath)) {
            return ClassPathResourceUtils.getResourceContent(resourcePath);
        }
        return StringPool.EMPTY;
    }

    private static List<RegisterApplication> getApplicationsByContent(String content) {
        List<RegisterApplication> applications = new ArrayList<>();
        try {
            RegisterApplications registerApplications = (RegisterApplications) XmlUtils.xmlStrToObject(RegisterApplications.class, content);
            if (Objects.nonNull(registerApplications) && CollectionUtils.isNotEmpty(registerApplications.getApplications())) {
                registerApplications.getApplications().forEach(app -> {
                    applications.add(app);
                });
            }
        } catch (JAXBException e) {
            log.error("init application jaxb exception", e);
        } catch (IOException e) {
            log.error("init application io exception");
        }
        return applications;
    }
}
