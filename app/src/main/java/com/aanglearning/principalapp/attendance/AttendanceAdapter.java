package com.aanglearning.principalapp.attendance;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aanglearning.principalapp.R;
import com.aanglearning.principalapp.model.Attendance;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vinay on 23-04-2017.
 */

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder>{
    private ArrayList<Attendance> absentees;

    AttendanceAdapter(ArrayList<Attendance> absentees) {
        this.absentees = absentees;
    }

    public ArrayList<Attendance> getDataSet() {
        return absentees;
    }

    @UiThread
    public void setDataSet(ArrayList<Attendance> absentees) {
        this.absentees = absentees;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_item, parent, false);
        return new AttendanceAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(absentees.get(position));
    }

    @Override
    public int getItemCount() {
        return absentees.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.absent) TextView absent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(final Attendance attendance) {
            if(attendance.getTypeOfLeave().equals("NA")) {
                name.setText("No Absentees");
            } else {
                name.setText(attendance.getStudentName());
                absent.setText("Absent");
            }
        }
    }
}
