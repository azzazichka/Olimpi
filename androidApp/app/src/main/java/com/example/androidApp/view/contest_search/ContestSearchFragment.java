package com.example.androidApp.view.contest_search;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.androidApp.MainActivity;
import com.example.androidApp.model.entity.Contest;
import com.example.androidApp.model.entity.UserEvent;
import com.example.androidApp.presenter.server.RequestGenerator;
import com.example.androidApp.presenter.server.ServiceGenerator;
import com.example.androidApp.presenter.server.requests.ContestRequests;
import com.example.androidApp.presenter.server.service.UserEventApi;
import com.example.androidApp.view.contest_search.contest_list.ContestAdapter;
import com.example.androidApp.view.contest_search.contest_list.ContestInfoDialogFragment;
import com.example.androidApp.RecyclerViewInterface;
import com.example.androidapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ContestSearchFragment extends Fragment implements RecyclerViewInterface {
    public ContestSearchFragment() {
        super(R.layout.fragment_contest_search);
    }

    LiveData<List<Contest>> contestsData;
    ContestAdapter adapter;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.contests_list);
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_contest_list_layout);

        adapter = new ContestAdapter(view.getContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        ContestRequests.getInstance().clearContestData();
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
        Contest clickedContest = adapter.getContests().get(position);

        UserEventApi userEventApi = ServiceGenerator.createService(UserEventApi.class);
        compositeDisposable.add(
            RequestGenerator.getInstance().getDisposable(
                    userEventApi.getUserEvents(),
                    userEvents -> {
                        Long userEventId = -1L;
                        for (UserEvent userEvent : userEvents) {
                            if (Objects.equals(userEvent.getContest_id(), clickedContest.getId())) {
                                userEventId = userEvent.getId();
                                break;
                            }
                        }
                        ContestInfoDialogFragment customDialog = ContestInfoDialogFragment.newInstance(clickedContest, userEventId);
                        customDialog.show(getParentFragmentManager(), "contest dialog");
                    })
        );
    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.dispose();
    }
}
