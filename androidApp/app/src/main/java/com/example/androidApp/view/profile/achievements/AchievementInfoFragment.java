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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.androidApp.RecyclerViewInterface;
import com.example.androidApp.model.DateConverter;
import com.example.androidApp.model.entity.Contest;
import com.example.androidApp.model.entity.UserEvent;
import com.example.androidApp.presenter.server.RequestGenerator;
import com.example.androidApp.presenter.server.ServiceGenerator;
import com.example.androidApp.presenter.server.service.AchievementApi;
import com.example.androidApp.presenter.server.service.AttachmentApi;
import com.example.androidApp.presenter.server.service.UserEventApi;
import com.example.androidApp.view.contest_search.contest_list.ContestAdapter;
import com.example.androidApp.view.profile.achievements.attachment_list.AttachmentAdapter;
import com.example.androidapp.R;
import com.example.androidapp.databinding.FragmentAchievementInfoBinding;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class AchievementInfoFragment extends Fragment implements RecyclerViewInterface {
    public AchievementInfoFragment(Contest contest, UserEvent userEvent) {
        super(R.layout.fragment_achievement_info);
        this.contest = contest;
        this.userEvent = userEvent;
    }

    private final Contest contest;
    private Long achievementId;
    private final UserEvent userEvent;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private FragmentAchievementInfoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAchievementInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    TextView datePicker, notificationPicker;
    ImageButton clearDate, clearNotification;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = binding.attachmentsList;
        TextView contestTitle = binding.contestTitle;
        TextView contestInfo = binding.contestInfo;
        ImageButton backButton = binding.btnBack;
        ImageButton deleteAchievementButton = binding.deleteAchievement;
        datePicker = binding.datePicker;
        notificationPicker = binding.notificationPicker;
        clearDate = binding.btnClearDate;
        clearNotification = binding.btnClearNotification;

        contestTitle.setText(contest.getTitle());
        contestInfo.setText(contest.toString());
        backButton.setOnClickListener(v -> closeFragment());
        deleteAchievementButton.setOnClickListener(v -> deleteAchievement());


        updateDatePickerText();
        updateNotificationPickerText();


        datePicker.setOnClickListener(v -> {
            setUserEventDate(userEvent, requireContext(), () -> {
                updateDatePickerText();
                saveChanges();
            });

        });
        notificationPicker.setOnClickListener(v -> {
            setUserEventNotification(userEvent, requireContext(), () -> {
                updateNotificationPickerText();
                saveChanges();
            });
        });
        clearDate.setOnClickListener(v -> {
            userEvent.setStart_time(null);
            userEvent.setEnd_time(null);
            saveChanges();
            updateDatePickerText();
        });

        clearNotification.setOnClickListener(v -> {
            userEvent.setNotification_time(null);
            saveChanges();
            updateNotificationPickerText();
        });

        AttachmentAdapter adapter = new AttachmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        AttachmentApi attachmentApi = ServiceGenerator.createService(AttachmentApi.class);
        AchievementApi achievementApi = ServiceGenerator.createService(AchievementApi.class);
        compositeDisposable.add(
                RequestGenerator.getInstance().makeApiCall(
                        achievementApi.getAchievement(userEvent.getUser_id(), userEvent.getContest_id())
                                        .flatMap(achievement -> {
                                            achievementId = achievement.getId();
                                            return attachmentApi.getAttachments(achievementId);
                                        }),
                        attachments -> {
                            adapter.attachments = attachments;
                            adapter.notifyDataSetChanged();
                        }
                ));
    }

    private void updateNotificationPickerText() {
        if (userEvent.getNotification_time() == null) {
            clearNotification.setVisibility(GONE);
            notificationPicker.setText("Установить уведомление");
            return;
        }
        String text = DateConverter.date2String(userEvent.getNotification_time(), "dd.MM.yy") +
                " " + DateConverter.date2String(userEvent.getNotification_time(), "HH:mm");
        notificationPicker.setText(text);
        clearNotification.setVisibility(VISIBLE);
    }

    private void updateDatePickerText() {
        if (userEvent.getStart_time() == null) {
            clearDate.setVisibility(GONE);
            datePicker.setText("Установить дату");
            return;
        }
        String text = DateConverter.date2String(userEvent.getStart_time(), "dd.MM.yy") +
                '\n' + DateConverter.date2String(userEvent.getStart_time(), "HH:mm") +
                "-" + DateConverter.date2String(userEvent.getEnd_time(), "HH:mm");
        datePicker.setText(text);
        clearDate.setVisibility(VISIBLE);
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

    private void saveChanges() {
        UserEventApi userEventApi = ServiceGenerator.createService(UserEventApi.class);
        RequestGenerator.getInstance().makeApiCall(
                userEventApi.updateUserEvent(userEvent),
                r -> {}
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    @Override
    public void onItemClick(int position) {

        Toast.makeText(getContext(), "clicked item " + position, Toast.LENGTH_SHORT).show();
    }
}
