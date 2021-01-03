package com.geekalliance.taurus.toolkit.utils;

import com.geekalliance.taurus.toolkit.StringPool;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maxuqiang
 */
public class EscapeUtils {
    public static String ESCAPE_CHAR = StringPool.SLASH;

    public static String ESCAPE_KEY = "escape " + StringPool.SINGLE_QUOTE + EscapeUtils.ESCAPE_CHAR + StringPool.SINGLE_QUOTE;

    private static List<String> SPECIAL_CHAR = new ArrayList<>();

    static {
        SPECIAL_CHAR.add(StringPool.SLASH);
        SPECIAL_CHAR.add(StringPool.UNDERSCORE);
        SPECIAL_CHAR.add(StringPool.PERCENT);
        SPECIAL_CHAR.add(StringPool.LEFT_SQ_BRACKET);
        SPECIAL_CHAR.add(StringPool.RIGHT_SQ_BRACKET);
        SPECIAL_CHAR.add(StringPool.HAT);
    }

    public static Boolean isEscapeChar(String before) {
        for (String s : SPECIAL_CHAR) {
            if (before.contains(s)) {
                return true;
            }
        }
        return false;
    }

    public static Object sqlEscapeChar(String param) {
        if (StringUtils.isNotBlank(param)) {
            if (param.contains(ESCAPE_CHAR)) {
                param = param.replaceAll(ESCAPE_CHAR + ESCAPE_CHAR, ESCAPE_CHAR + ESCAPE_CHAR);
            }
            for (String s : SPECIAL_CHAR) {
                if (param.contains(s)) {
                    if (StringPool.LEFT_SQ_BRACKET.equals(s) || StringPool.RIGHT_SQ_BRACKET.equals(s) || StringPool.HAT.equals(s)) {
                        s = StringPool.BACK_SLASH + s;
                    }
                    param = param.replaceAll(s, ESCAPE_CHAR + s);
                }
            }
        }
        return param;
    }
}
