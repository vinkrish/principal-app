package com.aanglearning.principalapp.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.aanglearning.principalapp.model.Teacher;
import com.aanglearning.principalapp.util.AppGlobal;

/**
 * Created by Vinay on 28-03-2017.
 */

public class TeacherDao {

    public static int insert(Teacher teacher) {
        String sql = "insert into teacher(Id, TeacherName, Image, SchoolId, DateOfBirth, Mobile, Qualification, DateOfJoining, Gender, Email) " +
                "values(?,?,?,?,?,?,?,?,?,?)";
        SQLiteDatabase db = AppGlobal.getSqlDbHelper().getWritableDatabase();
        db.beginTransactionNonExclusive();
        SQLiteStatement stmt = db.compileStatement(sql);
        try {
            stmt.bindLong(1, teacher.getId());
            stmt.bindString(2, teacher.getTeacherName());
            stmt.bindString(3, teacher.getImage());
            stmt.bindLong(4, teacher.getSchoolId());
            stmt.bindString(5, teacher.getDateOfBirth());
            stmt.bindString(6, teacher.getMobile());
            stmt.bindString(7, teacher.getQualification());
            stmt.bindString(8, teacher.getDateOfJoining());
            stmt.bindString(9, teacher.getGender());
            stmt.bindString(10, teacher.getEmail());
            stmt.execute();
            stmt.clearBindings();
        } catch (Exception e) {
            db.endTransaction();
            return 0;
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return 1;
    }

    public static Teacher getTeacher() {
        Teacher teacher = new Teacher();
        SQLiteDatabase sqliteDatabase = AppGlobal.getSqlDbHelper().getReadableDatabase();
        Cursor c = sqliteDatabase.rawQuery("select * from teacher", null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            teacher.setId(c.getLong(c.getColumnIndex("Id")));
            teacher.setTeacherName(c.getString(c.getColumnIndex("TeacherName")));
            teacher.setImage(c.getString(c.getColumnIndex("Image")));
            teacher.setSchoolId(c.getLong(c.getColumnIndex("SchoolId")));
            teacher.setDateOfBirth(c.getString(c.getColumnIndex("DateOfBirth")));
            teacher.setMobile(c.getString(c.getColumnIndex("Mobile")));
            teacher.setQualification(c.getString(c.getColumnIndex("Qualification")));
            teacher.setDateOfJoining(c.getString(c.getColumnIndex("DateOfJoining")));
            teacher.setGender(c.getString(c.getColumnIndex("Gender")));
            teacher.setEmail(c.getString(c.getColumnIndex("Email")));
            c.moveToNext();
        }
        c.close();
        return teacher;
    }

    public static int clear() {
        String sql = "delete from teacher";
        SQLiteDatabase db = AppGlobal.getSqlDbHelper().getWritableDatabase();
        db.beginTransactionNonExclusive();
        SQLiteStatement stmt = db.compileStatement(sql);
        try {
            stmt.execute();
            stmt.clearBindings();
        } catch (Exception e) {
            db.endTransaction();
            return 0;
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return 1;
    }
}
