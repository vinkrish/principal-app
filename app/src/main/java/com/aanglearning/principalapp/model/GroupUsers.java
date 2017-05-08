package com.aanglearning.principalapp.model;

import java.util.ArrayList;

/**
 * Created by Vinay on 03-04-2017.
 */

public class GroupUsers {
    private ArrayList<UserGroup> userGroupList;
    private ArrayList<Student> students;
    private ArrayList<Teacher> teachers;

    ArrayList<UserGroup> getUserGroupList() {
        return userGroupList;
    }

    void setUserGroupList(ArrayList<UserGroup> userGroupList) {
        this.userGroupList = userGroupList;
    }

    ArrayList<Student> getStudents() {
        return students;
    }

    void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    void setTeachers(ArrayList<Teacher> teachers) {
        this.teachers = teachers;
    }
}
