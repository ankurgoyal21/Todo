package com.mysolutions.todo.data;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.mysolutions.todo.data.dao.TodoItemDao;
import com.mysolutions.todo.data.dao.TodoItemDao_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public final class TodoRoomDatabase_Impl extends TodoRoomDatabase {
  private volatile TodoItemDao _todoItemDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `todo_table` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `checked` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"be8277787fc4cb9d589180ff2b478636\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `todo_table`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsTodoTable = new HashMap<String, TableInfo.Column>(4);
        _columnsTodoTable.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1));
        _columnsTodoTable.put("title", new TableInfo.Column("title", "TEXT", true, 0));
        _columnsTodoTable.put("description", new TableInfo.Column("description", "TEXT", true, 0));
        _columnsTodoTable.put("checked", new TableInfo.Column("checked", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTodoTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTodoTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTodoTable = new TableInfo("todo_table", _columnsTodoTable, _foreignKeysTodoTable, _indicesTodoTable);
        final TableInfo _existingTodoTable = TableInfo.read(_db, "todo_table");
        if (! _infoTodoTable.equals(_existingTodoTable)) {
          throw new IllegalStateException("Migration didn't properly handle todo_table(com.mysolutions.todo.data.model.TodoItem).\n"
                  + " Expected:\n" + _infoTodoTable + "\n"
                  + " Found:\n" + _existingTodoTable);
        }
      }
    }, "be8277787fc4cb9d589180ff2b478636", "95132fe6a6d2c3992016bf0dfd88428b");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "todo_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `todo_table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public TodoItemDao todoItemDao() {
    if (_todoItemDao != null) {
      return _todoItemDao;
    } else {
      synchronized(this) {
        if(_todoItemDao == null) {
          _todoItemDao = new TodoItemDao_Impl(this);
        }
        return _todoItemDao;
      }
    }
  }
}
