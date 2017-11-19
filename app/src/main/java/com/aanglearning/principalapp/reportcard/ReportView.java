package com.aanglearning.principalapp.reportcard;

import com.aanglearning.principalapp.model.Clas;
import com.aanglearning.principalapp.model.Section;

import java.util.List;

/**
 * Created by Vinay on 16-11-2017.
 */

public interface ReportView {
    void showProgress();

    void hideProgress();

    void showError(String message);

    void showClass(List<Clas> classList);

    void showSection(List<Section> sectionList);

    void showExam(List<Exam> exams);

    void showExamSubject(List<ExamSubject> examSubjects);

    void showScore(List<Mark> marks);

    void showActivity(List<Activity> activityList);

    void showActivityScore(List<ActivityScore> activityScores);
}
