package com.tazkiyatech.utils.test.database

/**
 * Represents a row within the output of an "EXPLAIN QUERY PLAN" command.
 */
data class QueryPlanRow(val detail: String)