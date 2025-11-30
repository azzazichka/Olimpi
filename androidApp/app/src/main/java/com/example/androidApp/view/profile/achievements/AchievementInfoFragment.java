package com.example.androidApp.view.profile.achievements;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.androidApp.model.entity.UserEvent.setUserEventDate;
import static com.example.androidApp.model.entity.UserEvent.setUserEventNotification;

import android.os.Bundle;
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

import com.example.androidApp.model.DateConverter;
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


        if (userEvent.getStart_time() != null) {
            String text = DateConverter.date2String(userEvent.getStart_time(), "dd.MM.yy") +
                    '\n' + DateConverter.date2String(userEvent.getStart_time(), "HH:mm") +
                    "-" + DateConverter.date2String(userEvent.getEnd_time(), "HH:mm");
            datePicker.setText(text);
            clearDate.setVisibility(VISIBLE);
        }
        if (userEvent.getNotification_time() != null) {
            String text = DateConverter.date2String(userEvent.getNotification_time(), "dd.MM.yy") +
                    " " + DateConverter.date2String(userEvent.getNotification_time(), "HH:mm");
            notificationPicker.setText(text);
            clearNotification.setVisibility(VISIBLE);
        }



        datePicker.setOnClickListener(v -> {
            setUserEventDate(userEvent, requireContext(), datePicker, clearDate);
        });
        notificationPicker.setOnClickListener(v -> {
            setUserEventNotification(userEvent, requireContext(), notificationPicker, clearNotification);
        });
        clearDate.setOnClickListener(v -> {
            clearDate.setVisibility(GONE);
            userEvent.setStart_time(null);
            userEvent.setEnd_time(null);
            datePicker.setText("Установить дату");
        });

        clearNotification.setOnClickListener(v -> {
            clearNotification.setVisibility(GONE);
                userEvent.setNotification_time(null);
            notificationPicker.setText("Установить уведомление");
        });

    }

    private void closeFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.popBackStack();
    }

    private void deleteAchievement() {
        AchievementApi achievementApi = ServiceGenerator.createService(AchievementApi.class);
        UserEventApi userEventApi = ServiceGenerator.createService(UserEventApi.class);

        compositeDisposable.add(
                RequestGenerator.getInstance().makeApiCall(
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

        UserEventApi userEventApi = ServiceGenerator.createService(UserEventApi.class);
        RequestGenerator.getInstance().makeApiCall(
                userEventApi.updateUserEvent(userEvent),
                r -> {}
        );
    }
}
