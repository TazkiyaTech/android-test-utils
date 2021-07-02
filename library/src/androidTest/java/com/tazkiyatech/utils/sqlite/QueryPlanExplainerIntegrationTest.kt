package com.tazkiyatech.utils.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QueryPlanExplainerIntegrationTest {

    private val someDatabase = SomeDatabase(ApplicationProvider.getApplicationContext())
    private var queryPlanExplainer = QueryPlanExplainer(someDatabase.readableDatabase)

    @Before
    fun setUp() {
        someDatabase.createTable()
    }

    @After
    fun tearDown() {
        someDatabase.dropTable()
        someDatabase.close()
    }

    @Test
    fun explainQueryPlanForSelectStatement_when_no_where_clause_provided_1() {
        // Given.
        val expected = listOf(QueryPlanRow("SCAN TABLE TableA"))

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForSqlStatement("SELECT * FROM TableA")

        // Then.
        assertEquals(expected, actual)
    }

    @Test
    fun explainQueryPlanForSelectStatement_when_no_where_clause_provided_2() {
        // Given.
        val expected = listOf(QueryPlanRow("SCAN TABLE TableA"))

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForSelectStatement(
            "TableA",
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )

        // Then.
        assertEquals(expected, actual)
    }

    @Test
    fun explainQueryPlanForUpdateStatement_when_no_where_clause_provided_1() {
        // Given.
        val expected = listOf(QueryPlanRow("SCAN TABLE TableA"))

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForSqlStatement("UPDATE TableA SET ColumnB = 1")

        // Then.
        assertEquals(expected, actual)
    }

    @Test
    fun explainQueryPlanForUpdateStatement_when_no_where_clause_provided_2() {
        // Given.
        val expected = listOf(QueryPlanRow("SCAN TABLE TableA"))

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForUpdateStatement(
            "TableA",
            ContentValues().apply { put("ColumnB", 1) },
            null,
            null
        )

        // Then.
        assertEquals(expected, actual)
    }

    @Test
    fun explainQueryPlanForSelectStatement_when_where_clause_matches_ColumnA_1() {
        // Given.
        val expected = listOf(
            QueryPlanRow("SEARCH TABLE TableA USING INTEGER PRIMARY KEY (rowid=?)")
        )

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForSqlStatement(
            "SELECT * FROM TableA WHERE ColumnA = 1"
        )

        // Then.
        assertEquals(expected, actual)
    }

    @Test
    fun explainQueryPlanForSelectStatement_when_where_clause_matches_ColumnA_2() {
        // Given.
        val expected = listOf(
            QueryPlanRow("SEARCH TABLE TableA USING INTEGER PRIMARY KEY (rowid=?)")
        )

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForSelectStatement(
            "TableA",
            null,
            "ColumnA = ? ", arrayOf("1"),
            null,
            null,
            null,
            null
        )

        // Then.
        assertEquals(expected, actual)
    }

    @Test
    fun explainQueryPlanForUpdateStatement_when_where_clause_matches_ColumnA_1() {
        // Given.
        val expected = listOf(
            QueryPlanRow("SEARCH TABLE TableA USING INTEGER PRIMARY KEY (rowid=?)")
        )

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForSqlStatement(
            "UPDATE TableA SET ColumnB = 1 WHERE ColumnA = 1"
        )

        // Then.
        assertEquals(expected, actual)
    }

    @Test
    fun explainQueryPlanForUpdateStatement_when_where_clause_matches_ColumnA_2() {
        // Given.
        val expected = listOf(
            QueryPlanRow("SEARCH TABLE TableA USING INTEGER PRIMARY KEY (rowid=?)")
        )

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForUpdateStatement(
            "TableA",
            ContentValues().apply { put("ColumnB", 1) },
            "ColumnA = ?", arrayOf("1")
        )

        // Then.
        assertEquals(expected, actual)
    }

    @Test
    fun explainQueryPlanForSelectStatement_when_where_clause_matches_ColumnB_1() {
        // Given.
        val expected = listOf(
            QueryPlanRow("SEARCH TABLE TableA USING COVERING INDEX ColumnB_ColumnC_on_TableA (ColumnB=?)")
        )

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForSqlStatement(
            "SELECT * FROM TableA WHERE ColumnB = 1"
        )

        // Then.
        assertEquals(expected, actual)
    }

    @Test
    fun explainQueryPlanForSelectStatement_when_where_clause_matches_ColumnB_2() {
        // Given.
        val expected = listOf(
            QueryPlanRow("SEARCH TABLE TableA USING COVERING INDEX ColumnB_ColumnC_on_TableA (ColumnB=?)")
        )

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForSelectStatement(
            "TableA",
            null,
            "ColumnB = ?", arrayOf("1"),
            null,
            null,
            null,
            null
        )

        // Then.
        assertEquals(expected, actual)
    }

    @Test
    fun explainQueryPlanForUpdateStatement_when_where_clause_matches_ColumnB_1() {
        // Given.
        val expected = listOf(
            QueryPlanRow("SEARCH TABLE TableA USING INDEX ColumnB_ColumnC_on_TableA (ColumnB=?)")
        )

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForSqlStatement(
            "UPDATE TableA SET ColumnB = 1 WHERE ColumnB = 2"
        )

        // Then.
        assertEquals(expected, actual)
    }

    @Test
    fun explainQueryPlanForUpdateStatement_when_where_clause_matches_ColumnB_2() {
        // Given.
        val expected = listOf(
            QueryPlanRow("SEARCH TABLE TableA USING INDEX ColumnB_ColumnC_on_TableA (ColumnB=?)")
        )

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForUpdateStatement(
            "TableA",
            ContentValues().apply { put("ColumnB", 1) },
            "ColumnB = ?", arrayOf("1")
        )

        // Then.
        assertEquals(expected, actual)
    }

    @Test
    fun explainQueryPlanForSelectStatement_when_where_clause_matches_ColumnC_1() {
        // Given.
        val expected = listOf(
            QueryPlanRow("SEARCH TABLE TableA USING INDEX ColumnC_on_TableA (ColumnC=?)")
        )

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForSqlStatement(
            "SELECT * FROM TableA WHERE ColumnC = '1'"
        )

        // Then.
        assertEquals(expected, actual)
    }

    @Test
    fun explainQueryPlanForSelectStatement_when_where_clause_matches_ColumnC_2() {
        // Given.
        val expected = listOf(
            QueryPlanRow("SEARCH TABLE TableA USING INDEX ColumnC_on_TableA (ColumnC=?)")
        )

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForSelectStatement(
            "TableA",
            null,
            "ColumnC = ? ", arrayOf("1"),
            null,
            null,
            null,
            null
        )

        // Then.
        assertEquals(expected, actual)
    }

    @Test
    fun explainQueryPlanForUpdateStatement_when_where_clause_matches_ColumnC_1() {
        // Given.
        val expected = listOf(
            QueryPlanRow("SEARCH TABLE TableA USING INDEX ColumnC_on_TableA (ColumnC=?)")
        )

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForSqlStatement(
            "UPDATE TableA SET ColumnB = 1 WHERE ColumnC = '1'"
        )

        // Then.
        assertEquals(expected, actual)
    }

    @Test
    fun explainQueryPlanForUpdateStatement_when_where_clause_matches_ColumnC_2() {
        // Given.
        val expected = listOf(
            QueryPlanRow("SEARCH TABLE TableA USING INDEX ColumnC_on_TableA (ColumnC=?)")
        )

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForUpdateStatement(
            "TableA",
            ContentValues().apply { put("ColumnB", 1) },
            "ColumnC = ? ", arrayOf("1")
        )

        // Then.
        assertEquals(expected, actual)
    }

    @Test
    fun explainQueryPlanForSelectStatement_when_where_clause_matches_ColumnB_and_ColumnC_1() {
        // Given.
        val expected = listOf(
            QueryPlanRow("SEARCH TABLE TableA USING COVERING INDEX ColumnB_ColumnC_on_TableA (ColumnB=? AND ColumnC=?)")
        )

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForSqlStatement(
            "SELECT * FROM TableA WHERE ColumnB = 1 AND ColumnC = '1'"
        )

        // Then.
        assertEquals(expected, actual)
    }

    @Test
    fun explainQueryPlanForSelectStatement_when_where_clause_matches_ColumnB_and_ColumnC_2() {
        // Given.
        val expected = listOf(
            QueryPlanRow("SEARCH TABLE TableA USING COVERING INDEX ColumnB_ColumnC_on_TableA (ColumnB=? AND ColumnC=?)")
        )

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForSelectStatement(
            "TableA",
            null,
            "ColumnB = ? AND ColumnC = ? ", arrayOf("1", "1"),
            null,
            null,
            null,
            null
        )

        // Then.
        assertEquals(expected, actual)
    }

    @Test
    fun explainQueryPlanForUpdateStatement_when_where_clause_matches_ColumnB_and_ColumnC_1() {
        // Given.
        val expected = listOf(
            QueryPlanRow("SEARCH TABLE TableA USING INDEX ColumnB_ColumnC_on_TableA (ColumnB=? AND ColumnC=?)")
        )

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForSqlStatement(
            "UPDATE TableA SET ColumnB = 2 WHERE ColumnB = 1 AND ColumnC = '1'"
        )

        // Then.
        assertEquals(expected, actual)
    }

    @Test
    fun explainQueryPlanForUpdateStatement_when_where_clause_matches_ColumnB_and_ColumnC_2() {
        // Given.
        val expected = listOf(
            QueryPlanRow("SEARCH TABLE TableA USING INDEX ColumnB_ColumnC_on_TableA (ColumnB=? AND ColumnC=?)")
        )

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForUpdateStatement(
            "TableA",
            ContentValues().apply { put("ColumnB", 2) },
            "ColumnB = ? AND ColumnC = ? ", arrayOf("1", "1")
        )

        // Then.
        assertEquals(expected, actual)
    }

    @Test
    fun explainQueryPlanForSelectStatement_when_where_clause_matches_ColumnB_or_ColumnC_1() {
        // Given.
        val expected: List<QueryPlanRow> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            listOf(
                QueryPlanRow("MULTI-INDEX OR"),
                QueryPlanRow("INDEX 1"),
                QueryPlanRow("SEARCH TABLE TableA USING COVERING INDEX ColumnB_ColumnC_on_TableA (ColumnB=?)"),
                QueryPlanRow("INDEX 2"),
                QueryPlanRow("SEARCH TABLE TableA USING INDEX ColumnC_on_TableA (ColumnC=?)")
            )
        } else {
            listOf(
                QueryPlanRow("SEARCH TABLE TableA USING COVERING INDEX ColumnB_ColumnC_on_TableA (ColumnB=?)"),
                QueryPlanRow("SEARCH TABLE TableA USING INDEX ColumnC_on_TableA (ColumnC=?)")
            )
        }

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForSqlStatement(
            "SELECT ColumnA, ColumnB, ColumnC FROM TableA WHERE ColumnB = 1 OR ColumnC = '1'"
        )

        // Then.
        assertEquals(expected, actual)
    }

    @Test
    fun explainQueryPlanForSelectStatement_when_where_clause_matches_ColumnB_or_ColumnC_2() {
        // Given.
        val expected: List<QueryPlanRow> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            listOf(
                QueryPlanRow("MULTI-INDEX OR"),
                QueryPlanRow("INDEX 1"),
                QueryPlanRow("SEARCH TABLE TableA USING COVERING INDEX ColumnB_ColumnC_on_TableA (ColumnB=?)"),
                QueryPlanRow("INDEX 2"),
                QueryPlanRow("SEARCH TABLE TableA USING INDEX ColumnC_on_TableA (ColumnC=?)")
            )
        } else {
            listOf(
                QueryPlanRow("SEARCH TABLE TableA USING COVERING INDEX ColumnB_ColumnC_on_TableA (ColumnB=?)"),
                QueryPlanRow("SEARCH TABLE TableA USING INDEX ColumnC_on_TableA (ColumnC=?)")
            )
        }

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForSelectStatement(
            "TableA",
            arrayOf("ColumnA", "ColumnB", "ColumnC"),
            "ColumnB = ? OR ColumnC = ?", arrayOf("1", "1"),
            null,
            null,
            null,
            null
        )

        // Then.
        assertEquals(expected, actual)
    }

    @Test
    fun explainQueryPlanForSelectStatement_when_where_clause_matches_ColumnB_and_orderBy_matches_ColumnA_1() {
        // Given.
        val expected = listOf(
            QueryPlanRow("SEARCH TABLE TableA USING COVERING INDEX ColumnB_ColumnC_on_TableA (ColumnB=?)"),
            QueryPlanRow("USE TEMP B-TREE FOR ORDER BY")
        )

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForSqlStatement(
            "SELECT ColumnA, ColumnB, ColumnC FROM TableA WHERE ColumnB = 1 ORDER BY ColumnA ASC"
        )

        // Then.
        assertEquals(expected, actual)
    }

    @Test
    fun explainQueryPlanForSelectStatement_when_where_clause_matches_ColumnB_and_orderBy_matches_ColumnA_2() {
        // Given.
        val expected = listOf(
            QueryPlanRow("SEARCH TABLE TableA USING COVERING INDEX ColumnB_ColumnC_on_TableA (ColumnB=?)"),
            QueryPlanRow("USE TEMP B-TREE FOR ORDER BY")
        )

        // When.
        val actual = queryPlanExplainer.explainQueryPlanForSelectStatement(
            "TableA",
            arrayOf("ColumnA", "ColumnB", "ColumnC"),
            "ColumnB = ?", arrayOf("1"),
            null,
            null,
            "ColumnA",
            null
        )

        // Then.
        assertEquals(expected, actual)
    }
}

class SomeDatabase(context: Context) : SQLiteOpenHelper(context, "SomeDatabase", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        // nothing to do
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // nothing to do
    }

    fun createTable() {
        val database = writableDatabase
        database.execSQL(CREATE_TABLE_A)
        database.execSQL(CREATE_INDEX_COLUMN_B_COLUMN_C_FOR_TABLE_A)
        database.execSQL(CREATE_INDEX_COLUMN_C_FOR_TABLE_A)
    }

    fun dropTable() {
        val database = writableDatabase
        database.execSQL(DROP_TABLE_A)
        database.execSQL(DROP_INDEX_COLUMN_B_COLUMN_C_FOR_TABLE_A)
        database.execSQL(DROP_INDEX_COLUMN_C_FOR_TABLE_A)
    }

    companion object {

        private const val CREATE_TABLE_A = "CREATE TABLE TableA " +
                " ( " +
                "ColumnA INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ColumnB INTEGER NOT NULL, " +
                "ColumnC TEXT NOT NULL " +
                " ) "

        private const val DROP_TABLE_A = "DROP TABLE IF EXISTS TableA"

        private const val CREATE_INDEX_COLUMN_B_COLUMN_C_FOR_TABLE_A =
            "CREATE INDEX ColumnB_ColumnC_on_TableA " +
                    " ON TableA" +
                    " (ColumnB ASC, ColumnC ASC)"

        private const val CREATE_INDEX_COLUMN_C_FOR_TABLE_A = "CREATE INDEX ColumnC_on_TableA " +
                " ON TableA" +
                " (ColumnC ASC)"

        private const val DROP_INDEX_COLUMN_B_COLUMN_C_FOR_TABLE_A =
            "DROP INDEX IF EXISTS ColumnB_ColumnC_on_TableA"

        private const val DROP_INDEX_COLUMN_C_FOR_TABLE_A = "DROP INDEX IF EXISTS ColumnC_on_TableA"
    }
}