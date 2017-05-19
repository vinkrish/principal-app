package com.aanglearning.principalapp.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vinay.
 */
public class SqlDbHelper extends SQLiteOpenHelper implements SqlConstant {
    private static SqlDbHelper dbHelper;
    public SQLiteDatabase sqliteDatabase;

    private SqlDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SqlDbHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new SqlDbHelper(context.getApplicationContext());
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ATTENDANCE);
        db.execSQL(CREATE_CLASS);
        db.execSQL(CREATE_CLASS_SUBJECT_GROUP);
        db.execSQL(CREATE_HOMEWORK);
        db.execSQL(CREATE_SCHOOL);
        db.execSQL(CREATE_SECTION);
        db.execSQL(CREATE_STUDENT);
        db.execSQL(CREATE_SUBJECT);
        db.execSQL(CREATE_SUBJECT_GROUP);
        db.execSQL(CREATE_SUBJECT_GROUP_SUBJECT);
        db.execSQL(CREATE_SUBJECT_STUDENT);
        db.execSQL(CREATE_SUBJECT_TEACHER);
        db.execSQL(CREATE_TEACHER);
        db.execSQL(CREATE_TIMETABLE);
        db.execSQL(CREATE_GROUPS);
        db.execSQL(CREATE_CHAT);
        db.execSQL(CREATE_SERVICE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS attendance");
        db.execSQL("DROP TABLE IF EXISTS class");
        db.execSQL("DROP TABLE IF EXISTS class_subject_group");
        db.execSQL("DROP TABLE IF EXISTS homework");
        db.execSQL("DROP TABLE IF EXISTS school");
        db.execSQL("DROP TABLE IF EXISTS section");
        db.execSQL("DROP TABLE IF EXISTS student");
        db.execSQL("DROP TABLE IF EXISTS subject");
        db.execSQL("DROP TABLE IF EXISTS subject_group");
        db.execSQL("DROP TABLE IF EXISTS subject_group_subject");
        db.execSQL("DROP TABLE IF EXISTS subject_student");
        db.execSQL("DROP TABLE IF EXISTS subject_teacher");
        db.execSQL("DROP TABLE IF EXISTS teacher");
        db.execSQL("DROP TABLE IF EXISTS timetable");
        db.execSQL("DROP TABLE IF EXISTS groups");
        db.execSQL("DROP TABLE IF EXISTS chat");
        db.execSQL("DROP TABLE IF EXISTS service");
        onCreate(db);
    }

    public void deleteTables() {
        sqliteDatabase = dbHelper.getWritableDatabase();
        sqliteDatabase.delete("attendance", null, null);
        sqliteDatabase.delete("class", null, null);
        sqliteDatabase.delete("class_subject_group", null, null);
        sqliteDatabase.delete("homework", null, null);
        sqliteDatabase.delete("school", null, null);
        sqliteDatabase.delete("section", null, null);
        sqliteDatabase.delete("student", null, null);
        sqliteDatabase.delete("subject", null, null);
        sqliteDatabase.delete("subject_group", null, null);
        sqliteDatabase.delete("subject_group_subject", null, null);
        sqliteDatabase.delete("subject_student", null, null);
        sqliteDatabase.delete("subject_teacher", null, null);
        sqliteDatabase.delete("teacher", null, null);
        sqliteDatabase.delete("timetable", null, null);
        sqliteDatabase.delete("groups", null, null);
        sqliteDatabase.delete("chat", null, null);
        sqliteDatabase.delete("service", null, null);
    }
}
