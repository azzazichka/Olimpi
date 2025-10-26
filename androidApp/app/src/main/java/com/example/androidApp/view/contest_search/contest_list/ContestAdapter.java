package com.example.androidApp.view.contest_search.contest_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidApp.model.DateConverter;
import com.example.androidApp.model.entity.Contest;
import com.example.androidapp.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContestAdapter extends RecyclerView.Adapter<ContestAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<Contest> contests;

    public ContestAdapter(Context context) {
        setHasStableIds(true);

        this.contests = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ContestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.contest_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContestAdapter.ViewHolder holder, int position) {
        Contest contest = contests.get(position);
        Date dateStart = contest.getDate_start();
        Date dateEnd = contest.getDate_end();

        String dateStartStr = DateConverter.date2String(dateStart, "dd.MM.yy");
        String dateEndStr = DateConverter.date2String(dateEnd, "dd.MM.yy");
        String grade = contest.getLow_grade() + "-" + contest.getUp_grade() + " класс";

        String date = dateStartStr + "-" + dateEndStr;
        String lvl = contest.getLvl() != null ? contest.getLvl() + "ур" : null;

        holder.titleView.setText(contest.getTitle());

        if (lvl != null) {
            holder.lvlView.setText(lvl);
        }

        holder.dateView.setText(date);
        holder.gradeView.setText(grade);
    }

    @Override
    public int getItemCount() {
        return contests.size();
    }

    @Override
    public long getItemId(int position) {
        return contests.get(position).getId();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleView, lvlView, dateView, gradeView;

        ViewHolder(View view){
            super(view);
            titleView = view.findViewById(R.id.contest_title);
            lvlView = view.findViewById(R.id.contest_lvl);
            dateView = view.findViewById(R.id.contest_date);
            gradeView = view.findViewById(R.id.contest_grade);
        }
    }

    public void updateList(List<Contest> newContestsList) {
        DiffCallback diffCallback = new DiffCallback(this.contests, newContestsList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.contests.clear();
        this.contests.addAll(newContestsList);
        diffResult.dispatchUpdatesTo(this);
    }
}
