package com.udacity.jwdnd.course1.cloudstorage.utils;

import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class EscapeString {

    public static String escape(String arg) {
        return Jsoup.clean(StringEscapeUtils.escapeJava(StringEscapeUtils.escapeHtml4(arg)), Whitelist.basic());
    }
}
