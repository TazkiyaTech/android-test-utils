package com.tazkiyatech.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class QueryPlanExplainerIntegrationTest {

    private QueryPlanExplainer queryPlanExplainer;
    private SomeDatabase someDatabase;

    @Before
    public void setUp() {
        someDatabase = new SomeDatabase(getContext());
        someDatabase.createTable();

        queryPlanExplainer = new QueryPlanExplainer(someDatabase.getReadableDatabase());
    }

    @After
    public void tearDown() {
        someDatabase.dropTable();
        someDatabase.close();
    }

    @Test
    public void explainQueryPlanForSelectStatement_when_no_where_clause_provided_1() {
        // Given.
        List<QueryPlanRow> expected = Collections.singletonList(new QueryPlanRow("SCAN TABLE TableA"));

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForSqlStatement("SELECT * FROM TableA");

        // Then.
        assertEquals(expected, actual);
    }

    @Test
    public void explainQueryPlanForSelectStatement_when_no_where_clause_provided_2() {
        // Given.
        List<QueryPlanRow> expected = Collections.singletonList(new QueryPlanRow("SCAN TABLE TableA"));

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForSelectStatement(
                "TableA",
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        // Then.
        assertEquals(expected, actual);
    }

    @Test
    public void explainQueryPlanForUpdateStatement_when_no_where_clause_provided_1() {
        // Given.
        List<QueryPlanRow> expected = Collections.singletonList(new QueryPlanRow("SCAN TABLE TableA"));

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForSqlStatement("UPDATE TableA SET ColumnB = 1");

        // Then.
        assertEquals(expected, actual);
    }

    @Test
    public void explainQueryPlanForUpdateStatement_when_no_where_clause_provided_2() {
        // Given.
        List<QueryPlanRow> expected = Collections.singletonList(new QueryPlanRow("SCAN TABLE TableA"));

        ContentValues contentValues = new ContentValues();
        contentValues.put("ColumnB", 1);

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForUpdateStatement(
                "TableA",
                contentValues,
                null,
                null
        );

        // Then.
        assertEquals(expected, actual);
    }

    @Test
    public void explainQueryPlanForSelectStatement_when_where_clause_matches_ColumnA_1() {
        // Given.
        List<QueryPlanRow> expected = Collections.singletonList(
                new QueryPlanRow("SEARCH TABLE TableA USING INTEGER PRIMARY KEY (rowid=?)")
        );

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForSqlStatement(
                "SELECT * FROM TableA WHERE ColumnA = 1"
        );

        // Then.
        assertEquals(expected, actual);
    }

    @Test
    public void explainQueryPlanForSelectStatement_when_where_clause_matches_ColumnA_2() {
        // Given.
        List<QueryPlanRow> expected = Collections.singletonList(
                new QueryPlanRow("SEARCH TABLE TableA USING INTEGER PRIMARY KEY (rowid=?)")
        );

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForSelectStatement(
                "TableA",
                null,
                "ColumnA = ? ",
                new String[]{"1"},
                null,
                null,
                null,
                null
        );

        // Then.
        assertEquals(expected, actual);
    }

    @Test
    public void explainQueryPlanForUpdateStatement_when_where_clause_matches_ColumnA_1() {
        // Given.
        List<QueryPlanRow> expected = Collections.singletonList(
                new QueryPlanRow("SEARCH TABLE TableA USING INTEGER PRIMARY KEY (rowid=?)")
        );

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForSqlStatement(
                "UPDATE TableA SET ColumnB = 1 WHERE ColumnA = 1"
        );

        // Then.
        assertEquals(expected, actual);
    }

    @Test
    public void explainQueryPlanForUpdateStatement_when_where_clause_matches_ColumnA_2() {
        // Given.
        List<QueryPlanRow> expected = Collections.singletonList(
                new QueryPlanRow("SEARCH TABLE TableA USING INTEGER PRIMARY KEY (rowid=?)")
        );

        ContentValues contentValues = new ContentValues();
        contentValues.put("ColumnB", 1);

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForUpdateStatement(
                "TableA",
                contentValues,
                "ColumnA = ?",
                new String[]{"1"}
        );

        // Then.
        assertEquals(expected, actual);
    }

    @Test
    public void explainQueryPlanForSelectStatement_when_where_clause_matches_ColumnB_1() {
        // Given.
        List<QueryPlanRow> expected = Collections.singletonList(
                new QueryPlanRow("SEARCH TABLE TableA USING COVERING INDEX ColumnB_ColumnC_on_TableA (ColumnB=?)")
        );

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForSqlStatement(
                "SELECT * FROM TableA WHERE ColumnB = 1"
        );

        // Then.
        assertEquals(expected, actual);
    }

    @Test
    public void explainQueryPlanForSelectStatement_when_where_clause_matches_ColumnB_2() {
        // Given.
        List<QueryPlanRow> expected = Collections.singletonList(
                new QueryPlanRow("SEARCH TABLE TableA USING COVERING INDEX ColumnB_ColumnC_on_TableA (ColumnB=?)")
        );

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForSelectStatement(
                "TableA",
                null,
                "ColumnB = ?",
                new String[]{"1"},
                null,
                null,
                null,
                null
        );

        // Then.
        assertEquals(expected, actual);
    }

    @Test
    public void explainQueryPlanForUpdateStatement_when_where_clause_matches_ColumnB_1() {
        // Given.
        List<QueryPlanRow> expected = Collections.singletonList(
                new QueryPlanRow("SEARCH TABLE TableA USING INDEX ColumnB_ColumnC_on_TableA (ColumnB=?)")
        );

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForSqlStatement(
                "UPDATE TableA SET ColumnB = 1 WHERE ColumnB = 2"
        );

        // Then.
        assertEquals(expected, actual);
    }

    @Test
    public void explainQueryPlanForUpdateStatement_when_where_clause_matches_ColumnB_2() {
        // Given.
        List<QueryPlanRow> expected = Collections.singletonList(
                new QueryPlanRow("SEARCH TABLE TableA USING INDEX ColumnB_ColumnC_on_TableA (ColumnB=?)")
        );

        ContentValues contentValues = new ContentValues();
        contentValues.put("ColumnB", 1);

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForUpdateStatement(
                "TableA",
                contentValues,
                "ColumnB = ?",
                new String[]{"1"}
        );

        // Then.
        assertEquals(expected, actual);
    }

    @Test
    public void explainQueryPlanForSelectStatement_when_whereClauseMatchesColumnC_1() {
        // Given.
        List<QueryPlanRow> expected = Collections.singletonList(
                new QueryPlanRow("SEARCH TABLE TableA USING INDEX ColumnC_on_TableA (ColumnC=?)")
        );

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForSqlStatement(
                "SELECT * FROM TableA WHERE ColumnC = '1'"
        );

        // Then.
        assertEquals(expected, actual);
    }

    @Test
    public void explainQueryPlanForSelectStatement_when_whereClauseMatchesColumnC_2() {
        // Given.
        List<QueryPlanRow> expected = Collections.singletonList(
                new QueryPlanRow("SEARCH TABLE TableA USING INDEX ColumnC_on_TableA (ColumnC=?)")
        );

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForSelectStatement(
                "TableA",
                null,
                "ColumnC = ? ",
                new String[]{"1"},
                null,
                null,
                null,
                null
        );

        // Then.
        assertEquals(expected, actual);
    }

    @Test
    public void explainQueryPlanForUpdateStatement_when_whereClauseMatchesColumnC_1() {
        // Given.
        List<QueryPlanRow> expected = Collections.singletonList(
                new QueryPlanRow("SEARCH TABLE TableA USING INDEX ColumnC_on_TableA (ColumnC=?)")
        );

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForSqlStatement(
                "UPDATE TableA SET ColumnB = 1 WHERE ColumnC = '1'"
        );

        // Then.
        assertEquals(expected, actual);
    }

    @Test
    public void explainQueryPlanForUpdateStatement_when_whereClauseMatchesColumnC_2() {
        // Given.
        List<QueryPlanRow> expected = Collections.singletonList(
                new QueryPlanRow("SEARCH TABLE TableA USING INDEX ColumnC_on_TableA (ColumnC=?)")
        );

        ContentValues contentValues = new ContentValues();
        contentValues.put("ColumnB", 1);

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForUpdateStatement(
                "TableA",
                contentValues,
                "ColumnC = ? ",
                new String[]{"1"}
        );

        // Then.
        assertEquals(expected, actual);
    }

    @Test
    public void explainQueryPlanForSelectStatement_when_where_clause_matches_ColumnB_and_ColumnC_1() {
        // Given.
        List<QueryPlanRow> expected = Collections.singletonList(
                new QueryPlanRow("SEARCH TABLE TableA USING COVERING INDEX ColumnB_ColumnC_on_TableA (ColumnB=? AND ColumnC=?)")
        );

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForSqlStatement(
                "SELECT * FROM TableA WHERE ColumnB = 1 AND ColumnC = '1'"
        );

        // Then.
        assertEquals(expected, actual);
    }

    @Test
    public void explainQueryPlanForSelectStatement_when_where_clause_matches_ColumnB_and_ColumnC_2() {
        // Given.
        List<QueryPlanRow> expected = Collections.singletonList(
                new QueryPlanRow("SEARCH TABLE TableA USING COVERING INDEX ColumnB_ColumnC_on_TableA (ColumnB=? AND ColumnC=?)")
        );

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForSelectStatement(
                "TableA",
                null,
                "ColumnB = ? AND ColumnC = ? ",
                new String[]{"1", "1"},
                null,
                null,
                null,
                null);

        // Then.
        assertEquals(expected, actual);
    }

    @Test
    public void explainQueryPlanForUpdateStatement_when_where_clause_matches_ColumnB_and_ColumnC_1() {
        // Given.
        List<QueryPlanRow> expected = Collections.singletonList(
                new QueryPlanRow("SEARCH TABLE TableA USING INDEX ColumnB_ColumnC_on_TableA (ColumnB=? AND ColumnC=?)")
        );

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForSqlStatement(
                "UPDATE TableA SET ColumnB = 2 WHERE ColumnB = 1 AND ColumnC = '1'"
        );

        // Then.
        assertEquals(expected, actual);
    }

    @Test
    public void explainQueryPlanForUpdateStatement_when_where_clause_matches_ColumnB_and_ColumnC_2() {
        // Given.
        List<QueryPlanRow> expected = Collections.singletonList(
                new QueryPlanRow("SEARCH TABLE TableA USING INDEX ColumnB_ColumnC_on_TableA (ColumnB=? AND ColumnC=?)")
        );

        ContentValues contentValues = new ContentValues();
        contentValues.put("ColumnB", 2);

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForUpdateStatement(
                "TableA",
                contentValues,
                "ColumnB = ? AND ColumnC = ? ",
                new String[]{"1", "1"});

        // Then.
        assertEquals(expected, actual);
    }

    @Test
    public void explainQueryPlanForSelectStatement_when_where_clause_matches_ColumnB_or_ColumnC_1() {
        // Given.
        List<QueryPlanRow> expected = Arrays.asList(
                new QueryPlanRow("MULTI-INDEX OR"),
                new QueryPlanRow("INDEX 1"),
                new QueryPlanRow("SEARCH TABLE TableA USING COVERING INDEX ColumnB_ColumnC_on_TableA (ColumnB=?)"),
                new QueryPlanRow("INDEX 2"),
                new QueryPlanRow("SEARCH TABLE TableA USING INDEX ColumnC_on_TableA (ColumnC=?)")
        );

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForSqlStatement(
                "SELECT ColumnA, ColumnB, ColumnC FROM TableA WHERE ColumnB = 1 OR ColumnC = '1'"
        );

        // Then.
        assertEquals(expected, actual);
    }

    @Test
    public void explainQueryPlanForSelectStatement_when_where_clause_matches_ColumnB_or_ColumnC_2() {
        // Given.
        List<QueryPlanRow> expected = Arrays.asList(
                new QueryPlanRow("MULTI-INDEX OR"),
                new QueryPlanRow("INDEX 1"),
                new QueryPlanRow("SEARCH TABLE TableA USING COVERING INDEX ColumnB_ColumnC_on_TableA (ColumnB=?)"),
                new QueryPlanRow("INDEX 2"),
                new QueryPlanRow("SEARCH TABLE TableA USING INDEX ColumnC_on_TableA (ColumnC=?)")
        );

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForSelectStatement(
                "TableA",
                new String[]{"ColumnA", "ColumnB", "ColumnC"},
                "ColumnB = ? OR ColumnC = ?",
                new String[]{"1", "1"},
                null,
                null,
                null,
                null
        );

        // Then.
        assertEquals(expected, actual);
    }

    @Test
    public void explainQueryPlanForSelectStatement_when_where_clause_matches_ColumnB_and_orderBy_matches_ColumnA_1() {
        // Given.
        QueryPlanRow queryPlanRow1 = new QueryPlanRow("SEARCH TABLE TableA USING COVERING INDEX ColumnB_ColumnC_on_TableA (ColumnB=?)");
        QueryPlanRow queryPlanRow2 = new QueryPlanRow("USE TEMP B-TREE FOR ORDER BY");

        List<QueryPlanRow> expected = Arrays.asList(
                queryPlanRow1,
                queryPlanRow2
        );

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForSqlStatement(
                "SELECT ColumnA, ColumnB, ColumnC FROM TableA WHERE ColumnB = 1 ORDER BY ColumnA ASC"
        );

        // Then.
        assertEquals(expected, actual);
    }

    @Test
    public void explainQueryPlanForSelectStatement_when_where_clause_matches_ColumnB_and_orderBy_matches_ColumnA_2() {
        // Given.
        QueryPlanRow queryPlanRow1 = new QueryPlanRow("SEARCH TABLE TableA USING COVERING INDEX ColumnB_ColumnC_on_TableA (ColumnB=?)");
        QueryPlanRow queryPlanRow2 = new QueryPlanRow("USE TEMP B-TREE FOR ORDER BY");

        List<QueryPlanRow> expected = Arrays.asList(
                queryPlanRow1,
                queryPlanRow2
        );

        // When.
        List<QueryPlanRow> actual = queryPlanExplainer.explainQueryPlanForSelectStatement(
                "TableA",
                new String[]{"ColumnA", "ColumnB", "ColumnC"},
                "ColumnB = ?",
                new String[]{"1"},
                null,
                null,
                "ColumnA",
                null
        );

        // Then.
        assertEquals(expected, actual);
    }

    /**
     * @return the {@link Context} for the target application being instrumented.
     */
    private Context getContext() {
        return ApplicationProvider.getApplicationContext();
    }

    private static class SomeDatabase extends SQLiteOpenHelper {

        private static final String CREATE_TABLE_A =
                "CREATE TABLE TableA " +
                        " ( " +
                        "ColumnA INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "ColumnB INTEGER NOT NULL, " +
                        "ColumnC TEXT NOT NULL " +
                        " ) ";

        private static final String DROP_TABLE_A =
                "DROP TABLE IF EXISTS TableA";

        private static final String CREATE_INDEX_COLUMN_B_COLUMN_C_FOR_TABLE_A =
                "CREATE INDEX ColumnB_ColumnC_on_TableA " +
                        " ON TableA" +
                        " (ColumnB ASC, ColumnC ASC)";

        private static final String CREATE_INDEX_COLUMN_C_FOR_TABLE_A =
                "CREATE INDEX ColumnC_on_TableA " +
                        " ON TableA" +
                        " (ColumnC ASC)";

        private static final String DROP_INDEX_COLUMN_B_COLUMN_C_FOR_TABLE_A =
                "DROP INDEX IF EXISTS ColumnB_ColumnC_on_TableA";

        private static final String DROP_INDEX_COLUMN_C_FOR_TABLE_A =
                "DROP INDEX IF EXISTS ColumnC_on_TableA";

        /**
         * Constructor.
         */
        SomeDatabase(@NonNull Context context) {
            super(context, "SomeDatabase", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // nothing to do
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // nothing to do
        }

        void createTable() {
            SQLiteDatabase database = getWritableDatabase();

            database.execSQL(CREATE_TABLE_A);
            database.execSQL(CREATE_INDEX_COLUMN_B_COLUMN_C_FOR_TABLE_A);
            database.execSQL(CREATE_INDEX_COLUMN_C_FOR_TABLE_A);
        }

        void dropTable() {
            SQLiteDatabase database = getWritableDatabase();

            database.execSQL(DROP_TABLE_A);
            database.execSQL(DROP_INDEX_COLUMN_B_COLUMN_C_FOR_TABLE_A);
            database.execSQL(DROP_INDEX_COLUMN_C_FOR_TABLE_A);
        }
    }
}