package com.tazkiyatech.utils.test.database;

import java.util.Objects;

/**
 * Represents a row within the output of an "EXPLAIN QUERY PLAN" command.
 */
public class QueryPlanRow {

    private final String detail;

    /**
     * Constructor.
     */
    public QueryPlanRow(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryPlanRow that = (QueryPlanRow) o;
        return Objects.equals(detail, that.detail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(detail);
    }

    @Override
    public String toString() {
        return "QueryPlanRow{" +
                "detail='" + detail + '\'' +
                '}';
    }
}
