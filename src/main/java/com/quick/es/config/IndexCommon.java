package com.quick.es.config;

import cn.hutool.core.date.DateUtil;

/**
 *
 */
public class IndexCommon {

    private final static String INDEX_SUFFIX_FORMAT = "yyyyMMddHHmmss";
//    private final static String INDEX_SUFFIX_FORMAT = "yyyy-MM-dd";

    public static String getIndexTemplate(String indexPrefix) {
        return indexPrefix + "template";
    }

    public static String getIndexPattern(String indexPrefix) {
        return indexPrefix + "*";
    }

    public static String getWriteIndex(String indexPrefix){
        return indexPrefix + "write";
    }

    public static String getSearchIndex(String indexPrefix) {
        return indexPrefix + "search";
    }

    public static String getCurrentFormatIndex(String indexPrefix) {
        String format = DateUtil.format(DateUtil.date(), INDEX_SUFFIX_FORMAT);
        return indexPrefix + format;
    }
}