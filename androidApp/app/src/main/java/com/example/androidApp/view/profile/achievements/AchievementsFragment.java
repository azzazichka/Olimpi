package com.example.androidApp.view.profile.achievements;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.androidApp.RecyclerViewInterface;
import com.example.androidApp.model.entity.Contest;
import com.example.androidApp.presenter.server.RequestGenerator;
import com.example.androidApp.presenter.server.ServiceGenerator;
import com.example.androidApp.presenter.server.requests.ContestRequests;
import com.example.androidApp.presenter.server.requests.UserAuth;
import com.example.androidApp.presenter.server.service.AchievementApi;
import com.example.androidApp.presenter.server.service.UserEventApi;
import com.example.androidApp.view.contest_search.contest_list.ContestAdapter;
import com.example.androidapp.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class AchievementsFragment extends Fragment implements RecyclerViewInterface {
    public AchievementsFragment() {
        super(R.layout.fragment_achievements);
    }

    private ContestAdapter adapter;
    private LiveData<List<Contest>> contestsData;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.achievements_list);
        adapter = new ContestAdapter(view.getContext(), this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        recyclerView.setAdapter(adapter);
        adapter.updateList(new ArrayList<>());

        AchievementApi achievementApi = ServiceGenerator.createService(AchievementApi.class);

        ContestRequests.getInstance().clearContestData();
        contestsData = ContestRequests.getInstance().getContestsData();
        contestsData.observe(getViewLifecycleOwner(), adapter::updateList);

        compositeDisposable.add(
                RequestGenerator.getInstance().makeApiCall(
                        achievementApi.getAchievements(),
                        achievements -> {
                            ContestRequests.getInstance().updateContestsListByAchievements(achievements);
                        })
        );

    }


    @Override
    public void onItemClick(int position) {
        Contest clickedContest = adapter.getContests().get(position);
        UserEventApi userEventApi = ServiceGenerator.createService(UserEventApi.class);

        compositeDisposable.add(
                RequestGenerator.getInstance().makeApiCall(
                        userEventApi.getUserEvent(UserAuth.getInstance().getUser().getId(), clickedContest.getId()),
                        userEvent -> {
                            FragmentManager fragmentManager = getParentFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            AchievementInfoFragment achievementInfoFragment = new AchievementInfoFragment(clickedContest, userEvent);
                            fragmentTransaction.replace(R.id.fragment_container_bottom_sheet, achievementInfoFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        })
        );

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
