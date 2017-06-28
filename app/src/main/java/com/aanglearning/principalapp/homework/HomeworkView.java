package com.aanglearning.principalapp.homework;

import com.aanglearning.principalapp.model.Clas;
import com.aanglearning.principalapp.model.Homework;
import com.aanglearning.principalapp.model.Section;

import java.util.List;

/**
 * Created by Vinay on 21-04-2017.
 */

interface HomeworkView {
    void showProgress();

    void hideProgress();

    void showError(String message);

    void showOffline(String tableName);

    void showClass(List<Clas> clasList);

    void showSection(List<Section> sectionList);

    void showHomeworks(List<Homework> homeworks);
}
