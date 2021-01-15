package com.tazkiyatech.utils.test.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import java.util.*

/**
 * Helper class for building up a [List] of [QueryPlanRow] objects
 * that explain the strategy or plan that SQLite will use to implement a specific SQL query.
 *
 * @property database the [SQLiteDatabase] object against which to run the "EXPLAIN QUERY PLAN" command.
 */
class QueryPlanExplainer(private val database: SQLiteDatabase) {

    /**
     * Composes and executes an "EXECUTE QUERY PLAN" command for the SQLite query provided.
     *
     * @param sql the SQLite SELECT/UPDATE/etc statement for which to run the "EXPLAIN QUERY PLAN" query.
     * @return the result of the "EXPLAIN QUERY PLAN" command.
     */
    fun explainQueryPlanForSqlStatement(sql: String): List<QueryPlanRow> {
        val explainQueryPlanStatement = "EXPLAIN QUERY PLAN $sql"
        return executeExplainQueryPlanStatement(explainQueryPlanStatement, null)
    }

    /**
     * Composes and executes an "EXECUTE QUERY PLAN" command
     * for the SELECT query that would be composed from the parameters provided.
     *
     * @return the result of the "EXPLAIN QUERY PLAN" command.
     * @see SQLiteDatabase.query
     */
    fun explainQueryPlanForSelectStatement(table: String,
                                           columns: Array<String?>?,
                                           selection: String?,
                                           selectionArgs: Array<String>?,
                                           groupBy: String?,
                                           having: String?,
                                           orderBy: String?,
                                           limit: String?): List<QueryPlanRow> {
        val sb = StringBuilder()

        sb.append("EXPLAIN QUERY PLAN SELECT ")

        if (columns == null || columns.isEmpty()) {
            sb.append(" * ")
        } else {
            sb.append(columns.joinToString(", "))
        }

        sb.append(" FROM ")
        sb.append(table)

        if (!selection.isNullOrEmpty()) {
            sb.append(" WHERE ")
            sb.append(selection)
        }

        if (!groupBy.isNullOrEmpty()) {
            sb.append(" GROUP BY ")
            sb.append(groupBy)
        }

        if (!having.isNullOrEmpty()) {
            sb.append(" HAVING ")
            sb.append(having)
        }

        if (!orderBy.isNullOrEmpty()) {
            sb.append(" ORDER BY ")
            sb.append(orderBy)
        }

        if (!limit.isNullOrEmpty()) {
            sb.append(" LIMIT ")
            sb.append(limit)
        }

        val sql = sb.toString()
        return executeExplainQueryPlanStatement(sql, selectionArgs)
    }

    /**
     * Composes and executes an "EXECUTE QUERY PLAN" command
     * for the UPDATE query that would be composed from the parameters provided.
     *
     * @return the result of the "EXPLAIN QUERY PLAN" command.
     * @see SQLiteDatabase.update
     */
    fun explainQueryPlanForUpdateStatement(table: String,
                                           contentValues: ContentValues,
                                           selection: String?,
                                           selectionArgs: Array<String>?): List<QueryPlanRow> {
        val sb = StringBuilder()

        sb.append("EXPLAIN QUERY PLAN UPDATE ")
        sb.append(table)
        sb.append(" SET ")

        val keys = contentValues.keySet()
        var firstKey = true

        for (key in keys) {
            if (!firstKey) {
                sb.append(", ")
            }

            sb.append(key)
            sb.append(" = ")

            when (val value = contentValues[key]) {
                null -> sb.append("NULL")
                is Number -> sb.append(value)
                is Boolean -> {
                    if (value) {
                        sb.append("1")
                    } else {
                        sb.append("0")
                    }
                }
                else -> sb.append("'$value'")
            }

            firstKey = false
        }

        if (!selection.isNullOrEmpty()) {
            sb.append(" WHERE ")
            sb.append(selection)
        }

        return executeExplainQueryPlanStatement(sb.toString(), selectionArgs)
    }

    /**
     * Executes the sql command provided on the database contained within this class.
     *
     * @param sql           the "EXPLAIN QUERY PLAN" command to call.
     * @param selectionArgs the values to use in place of the ?s in the where clause of `sql`.
     * @return the result of the "EXPLAIN QUERY PLAN" command.
     */
    private fun executeExplainQueryPlanStatement(sql: String,
                                                 selectionArgs: Array<String>?): List<QueryPlanRow> {
        database.rawQuery(sql, selectionArgs).use { cursor ->
            val queryPlanRowList: MutableList<QueryPlanRow> = ArrayList()

            while (cursor.moveToNext()) {
                val detailColumnIndex = cursor.getColumnIndex("detail")
                val detail = cursor.getString(detailColumnIndex)
                queryPlanRowList.add(QueryPlanRow(detail))
            }

            return queryPlanRowList
        }
    }
}