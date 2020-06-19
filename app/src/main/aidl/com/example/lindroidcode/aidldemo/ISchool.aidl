// ISchool.aidl
package com.example.lindroidcode.aidldemo; //Not genrated

// Declare any non-default types here with import statements
import com.example.lindroidcode.aidldemo.Student;

interface ISchool {
    String getSchoolName();
    int getStudentNum();

    Student getStudent();
}
