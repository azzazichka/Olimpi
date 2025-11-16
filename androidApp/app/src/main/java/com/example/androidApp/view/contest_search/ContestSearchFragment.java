package com.example.androidApp.view.contest_search;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.androidApp.model.entity.Contest;
import com.example.androidApp.presenter.server.requests.ContestRequests;
import com.example.androidApp.view.contest_search.contest_list.ContestAdapter;
import com.example.androidApp.view.contest_search.contest_list.ContestInfoDialogFragment;
import com.example.androidApp.view.contest_search.contest_list.RecyclerViewInterface;
import com.example.androidapp.R;

import java.util.ArrayList;
import java.util.List;

public class ContestSearchFragment extends Fragment implements RecyclerViewInterface {
    public ContestSearchFragment() {
        super(R.layout.fragment_contest_search);
    }

    LiveData<List<Contest>> contestsData;
    ContestAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.contests_list);
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_contest_list_layout);

        adapter = new ContestAdapter(view.getContext(), this);
        recyclerView.setAdapter(adapter);

        contestsData = ContestRequests.getInstance().getContestsData();
        contestsData.observe(getViewLifecycleOwner(), adapter::updateList);

        List<String> subjects = new ArrayList<>();
        subjects.add("астрономия");
        subjects.add("физика");

        ContestRequests.getInstance().updateContestsListBySubjects(subjects);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            ContestRequests.getInstance().updateContestsListBySubjects(subjects);
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onItemClick(int position) {
        Contest clicked_contest = adapter.getContests().get(position);
        ContestInfoDialogFragment customDialog = ContestInfoDialogFragment.newInstance(clicked_contest);
        customDialog.show(getParentFragmentManager(), "contest dialog");
    }
}
