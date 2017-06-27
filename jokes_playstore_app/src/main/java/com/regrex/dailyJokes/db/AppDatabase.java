package com.regrex.dailyJokes.db;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;
import com.regrex.dailyJokes.model.JokeSingle;

/**
 * Created by surinder on 19-Jun-17.
 */

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {

    public static final String NAME = "AppDatabase";

    public static final int VERSION = 5;

    @Migration(version = 4, priority = 5, database = AppDatabase.class)
    public static class Migration2 extends AlterTableMigration<JokeSingle> {

        public Migration2(Class<JokeSingle> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {

            addColumn(SQLiteType.INTEGER, "alreadyRead");
        }
    }
}
