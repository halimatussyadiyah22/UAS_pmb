package org.halimatussyadiyah.pmb.query;

public class BiodataQuery {
    public static final String STATS_QUERY =
            "SELECT b.total_biodata, c.total_card " +
            "FROM " +
            "(SELECT COUNT(*) total_biodata FROM biodata) b, " +
            "(SELECT COUNT(*) total_card FROM card) c ";
}

