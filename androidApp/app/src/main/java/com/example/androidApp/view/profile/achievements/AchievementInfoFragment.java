package com.example.androidApp.view.profile.achievements;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidApp.model.entity.Achievement;
import com.example.androidApp.model.entity.Contest;
import com.example.androidApp.model.entity.UserEvent;
import com.example.androidApp.presenter.server.RequestGenerator;
import com.example.androidApp.presenter.server.ServiceGenerator;
import com.example.androidApp.presenter.server.service.AchievementApi;
import com.example.androidApp.presenter.server.service.UserEventApi;
import com.example.androidapp.R;
import com.example.androidapp.databinding.FragmentAchievementInfoBinding;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class AchievementInfoFragment extends Fragment {
    public AchievementInfoFragment(Contest contest, UserEvent userEvent) {
        super(R.layout.fragment_achievement_info);
        this.contest = contest;
        this.userEvent = userEvent;
    }

    private final Contest contest;
    private final UserEvent userEvent;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private FragmentAchievementInfoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAchievementInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = binding.attachmentsList;
        TextView contestTitle = binding.contestTitle;
        TextView contestInfo = binding.contestInfo;
        ImageButton backButton = binding.btnBack;
        ImageButton deleteAchievementButton = binding.deleteAchievement;
        TextView datePicker = binding.datePicker;
        TextView notificationPicker = binding.notificationPicker;
        ImageButton clearDate = binding.btnClearDate;
        ImageButton clearNotification = binding.btnClearNotification;

        contestTitle.setText(contest.getTitle());
        contestInfo.setText(contest.toString());
        backButton.setOnClickListener(v -> closeFragment());
        deleteAchievementButton.setOnClickListener(v -> deleteAchievement());

    }

    private void closeFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.popBackStack();
    }

    private void deleteAchievement() {
        AchievementApi achievementApi = ServiceGenerator.createService(AchievementApi.class);
        UserEventApi userEventApi = ServiceGenerator.createService(UserEventApi.class);

        compositeDisposable.add(
                RequestGenerator.getInstance().getDisposable(
                        "Удалено",
                        userEventApi.deleteUserEvent(userEvent.getId()).
                                flatMap(r -> achievementApi.deleteAchievement(contest.getId())
                            ),
                        r -> closeFragment())
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
