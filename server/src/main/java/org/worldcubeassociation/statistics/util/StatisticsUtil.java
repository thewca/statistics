package org.worldcubeassociation.statistics.util;

public class StatisticsUtil {
    private static final String QUERY_PATH = "db/query/%s.sql";

    public static String getQuery(String queryName) {
        return LoadResourceUtil.getResource(String.format(QUERY_PATH, queryName));
    }
}
