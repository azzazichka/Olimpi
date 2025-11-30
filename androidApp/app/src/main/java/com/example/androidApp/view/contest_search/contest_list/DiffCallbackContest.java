package com.example.androidApp.view.contest_search.contest_list;

import androidx.recyclerview.widget.DiffUtil;

import com.example.androidApp.model.entity.Contest;

import java.util.List;
import java.util.Objects;

public class DiffCallbackContest extends DiffUtil.Callback {
    private final List<Contest> oldList;
    private final List<Contest> newList;

    public DiffCallbackContest(List<Contest> oldList, List<Contest> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.equals(oldList.get(oldItemPosition).getId(), newList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}