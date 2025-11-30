package com.example.androidApp.view.profile.achievements;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.androidApp.MainActivity;
import com.example.androidApp.RecyclerViewInterface;
import com.example.androidApp.model.entity.Achievement;
import com.example.androidApp.model.entity.Contest;
import com.example.androidApp.model.entity.UserEvent;
import com.example.androidApp.presenter.server.RequestGenerator;
import com.example.androidApp.presenter.server.ServiceGenerator;
import com.example.androidApp.presenter.server.requests.ContestRequests;
import com.example.androidApp.presenter.server.requests.UserAuth;
import com.example.androidApp.presenter.server.service.AchievementApi;
import com.example.androidApp.presenter.server.service.ContestApi;
import com.example.androidApp.presenter.server.service.UserEventApi;
import com.example.androidApp.view.AuthFragment;
import com.example.androidApp.view.contest_search.contest_list.ContestAdapter;
import com.example.androidapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class AchievementsFragment extends Fragment implements RecyclerViewInterface {
    public AchievementsFragment() {
        super(R.layout.fragment_achievements);
    }

    private ContestAdapter adapter;
    private LiveData<List<Contest>> contestsData;
    private CompositeDisposable compositeDisposable;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        compositeDisposable = new CompositeDisposable();
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
                RequestGenerator.getInstance().getDisposable(
                        achievementApi.getAchievements(),
                        achievements -> {
                            ContestRequests.getInstance().updateContestsListByAchievements(achievements);
                        })
        );

    }


    @Override
    public void onItemClick(int position) {
        Contest clickedContest = adapter.getContests().get(position);
        Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
        UserEventApi userEventApi = ServiceGenerator.createService(UserEventApi.class);

        compositeDisposable.add(
                RequestGenerator.getInstance().getDisposable(
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
    public void onStop() {
        super.onStop();
        compositeDisposable.dispose();
    }
}
