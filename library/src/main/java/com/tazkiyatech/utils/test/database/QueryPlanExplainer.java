package com.tazkiyatech.utils.test.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Helper class for building up a {@link List} of {@link QueryPlanRow} objects
 * that explain the strategy or plan that SQLite will use to implement a specific SQL query.
 */
public class QueryPlanExplainer {

    @NonNull private final SQLiteDatabase database;

    /**
     * Constructor.
     *
     * @param database the {@link SQLiteDatabase} object against which to run the "EXPLAIN QUERY PLAN" command.
     */
    public QueryPlanExplainer(@NonNull SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * Composes and executes an "EXECUTE QUERY PLAN" command
     * for the SQLite query provided.
     *
     * @param sql the (non-null) SQLite SELECT/UPDATE/etc statement for which to run the "EXPLAIN QUERY PLAN" query.
     * @return the result of the "EXPLAIN QUERY PLAN" command.
     */
    public List<QueryPlanRow> explainQueryPlanForSqlStatement(@NonNull String sql) {
        sql = "EXPLAIN QUERY PLAN " + sql;
        return executeExplainQueryPlanStatement(sql, null);
    }

    /**
     * Composes and executes an "EXECUTE QUERY PLAN" command
     * for the SELECT query that would be composed from the parameters provided.
     *
     * @return the result of the "EXPLAIN QUERY PLAN" command.
     * @see SQLiteDatabase#query(String, String[], String, String[], String, String, String, String)
     */
    public List<QueryPlanRow> explainQueryPlanForSelectStatement(@NonNull String table,
                                                                 @Nullable String[] columns,
                                                                 @Nullable String selection,
                                                                 @Nullable String[] selectionArgs,
                                                                 @Nullable String groupBy,
                                                                 @Nullable String having,
                                                                 @Nullable String orderBy,
                                                                 @Nullable String limit) {
        final StringBuilder sb = new StringBuilder();
        sb.append("EXPLAIN QUERY PLAN SELECT ");

        if (columns == null || columns.length == 0) {
            sb.append(" * ");
        } else {
            boolean firstColumn = true;

            for (String column : columns) {
                if (!firstColumn) {
                    sb.append(", ");
                }

                sb.append(column);

                firstColumn = false;
            }
        }

        sb.append(" FROM ");
        sb.append(table);

        if (!TextUtils.isEmpty(selection)) {
            sb.append(" WHERE ");
            sb.append(selection);
        }

        if (!TextUtils.isEmpty(groupBy)) {
            sb.append(" GROUP BY ");
            sb.append(groupBy);
        }

        if (!TextUtils.isEmpty(having)) {
            sb.append(" HAVING ");
            sb.append(having);
        }

        if (!TextUtils.isEmpty(orderBy)) {
            sb.append(" ORDER BY ");
            sb.append(orderBy);
        }

        if (!TextUtils.isEmpty(limit)) {
            sb.append(" LIMIT ");
            sb.append(limit);
        }

        return executeExplainQueryPlanStatement(sb.toString(), selectionArgs);
    }

    /**
     * Composes and executes an "EXECUTE QUERY PLAN" command
     * for the UPDATE query that would be composed from the parameters provided.
     *
     * @return the result of the "EXPLAIN QUERY PLAN" command.
     * @see SQLiteDatabase#update(String, ContentValues, String, String[])
     */
    public List<QueryPlanRow> explainQueryPlanForUpdateStatement(@NonNull String table,
                                                                 @NonNull ContentValues contentValues,
                                                                 @Nullable String selection,
                                                                 @Nullable String[] selectionArgs) {
        final StringBuilder sb = new StringBuilder();
        sb.append("EXPLAIN QUERY PLAN UPDATE ");

        sb.append(table);

        sb.append(" SET ");

        final Set<String> keys = contentValues.keySet();

        boolean firstKey = true;

        for (String key : keys) {
            if (!firstKey) {
                sb.append(", ");
            }

            sb.append(key);
            sb.append(" = ");

            if (contentValues.get(key) == null) {
                sb.append("NULL");
            } else if (contentValues.get(key) instanceof Boolean) {
                Boolean value = (Boolean) contentValues.get(key);

                if (value) {
                    sb.append("1");
                } else {
                    sb.append("0");
                }
            } else if (contentValues.get(key) instanceof Number) {
                sb.append(contentValues.get(key).toString());
            } else {
                sb.append("'");
                sb.append(contentValues.get(key).toString());
                sb.append("' ");
            }

            firstKey = false;
        }

        if (!TextUtils.isEmpty(selection)) {
            sb.append(" WHERE ");
            sb.append(selection);
        }

        return executeExplainQueryPlanStatement(sb.toString(), selectionArgs);
    }

    /**
     * Executes the sql command provided on the database contained within this class.
     *
     * @param sql           the "EXPLAIN QUERY PLAN" command to call.
     * @param selectionArgs the values to use in place of the ?s in the where clause of <code>sql</code>.
     * @return the result of the "EXPLAIN QUERY PLAN" command.
     */
    private List<QueryPlanRow> executeExplainQueryPlanStatement(@NonNull String sql,
                                                                @Nullable String[] selectionArgs) {

        try (Cursor cursor = database.rawQuery(sql, selectionArgs)) {

            List<QueryPlanRow> queryPlanRowList = new ArrayList<>();

            while (cursor.moveToNext()) {
                final int detailColumnIndex = cursor.getColumnIndex("detail");

                final String detail = cursor.getString(detailColumnIndex);

                queryPlanRowList.add(new QueryPlanRow(detail));
            }

            return queryPlanRowList;
        }
    }
}
