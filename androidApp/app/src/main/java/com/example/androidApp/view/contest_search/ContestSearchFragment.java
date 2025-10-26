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
import com.example.androidApp.presenter.ServerRequests;
import com.example.androidApp.view.contest_search.contest_list.ContestAdapter;
import com.example.androidapp.R;

import java.util.ArrayList;
import java.util.List;

public class ContestSearchFragment extends Fragment {
    public ContestSearchFragment() {
        super(R.layout.fragment_contest_search);
    }

    LiveData<List<Contest>> contestsData;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.contests_list);
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_contest_list_layout);

        ContestAdapter adapter = new ContestAdapter(view.getContext());
        recyclerView.setAdapter(adapter);

        contestsData = ServerRequests.getInstance().getContestsData();
        contestsData.observe(getViewLifecycleOwner(), adapter::updateList);

        List<String> subjects = new ArrayList<>();
        subjects.add("математика");
        subjects.add("информатика");
        subjects.add("физика");

        ServerRequests.getInstance().updateContestsListBySubjects(subjects);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ServerRequests.getInstance().updateContestsListBySubjects(subjects);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
