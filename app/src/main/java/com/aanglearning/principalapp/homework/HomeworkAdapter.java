package com.aanglearning.principalapp.homework;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aanglearning.principalapp.R;
import com.aanglearning.principalapp.model.Homework;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vinay on 24-04-2017.
 */

class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.ViewHolder>{
    private List<Homework> homeworks;

    HomeworkAdapter(List<Homework> homeworks) {
        this.homeworks = homeworks;
    }

    public List<Homework> getDataSet() {
        return homeworks;
    }

    @UiThread
    void setDataSet(List<Homework> homeworks) {
        this.homeworks = homeworks;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.homework_item, parent, false);
        return new HomeworkAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(homeworks.get(position));
    }

    @Override
    public int getItemCount() {
        return homeworks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.subject_name) TextView subjectName;
        @BindView(R.id.hw_message) TextView hwMessage;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(final Homework homework) {
            subjectName.setText(homework.getSubjectName());
            hwMessage.setText(homework.getHomeworkMessage());
        }
    }
}
