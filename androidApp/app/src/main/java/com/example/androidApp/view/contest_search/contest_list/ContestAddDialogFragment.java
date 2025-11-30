package com.example.androidApp.view.contest_search.contest_list;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import static com.example.androidApp.model.entity.UserEvent.setUserEventDate;
import static com.example.androidApp.model.entity.UserEvent.setUserEventNotification;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.androidApp.model.DateConverter;
import com.example.androidApp.model.entity.Achievement;
import com.example.androidApp.model.entity.Contest;
import com.example.androidApp.model.entity.UserEvent;
import com.example.androidApp.presenter.server.RequestGenerator;
import com.example.androidApp.presenter.server.ServiceGenerator;
import com.example.androidApp.presenter.server.requests.UserAuth;
import com.example.androidApp.presenter.server.service.AchievementApi;
import com.example.androidApp.presenter.server.service.UserEventApi;
import com.example.androidapp.R;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ContestAddDialogFragment extends DialogFragment {
    public static ContestAddDialogFragment newInstance(Contest contest) {
        ContestAddDialogFragment fragment = new ContestAddDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("contest", contest);
        fragment.setArguments(args);
        return fragment;
    }

    Contest contest;
    UserEvent userEvent = new UserEvent();
//    Calendar date = Calendar.getInstance();
    TextView date_picker, notification_picker;
    ImageButton clear_date, clear_notification;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contest = (Contest) getArguments().getSerializable("contest");
        }
        userEvent.setUser_id(UserAuth.getInstance().getUser().getId());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_contest_add, container, false);

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        }

        TextView contest_title = view.findViewById(R.id.contest_title_dialog);
        ImageButton close_btn = view.findViewById(R.id.btn_close_dialog);
        ImageButton add_btn = view.findViewById(R.id.btn_add_dialog);

        clear_date = view.findViewById(R.id.btn_clear_date);
        date_picker = view.findViewById(R.id.date_picker);

        clear_notification = view.findViewById(R.id.btn_clear_notification);
        notification_picker = view.findViewById(R.id.notification_picker);

        if (contest != null) {
            contest_title.setText(contest.getTitle());
            userEvent.setTitle(contest.getTitle());
            userEvent.setContest_id(contest.getId());
        }

        clear_date.setOnClickListener(v -> {
            clear_date.setVisibility(GONE);
            userEvent.setStart_time(null);
            userEvent.setEnd_time(null);
            date_picker.setText("Установить дату");
        });

        clear_notification.setOnClickListener(v -> {
            clear_notification.setVisibility(GONE);
            userEvent.setNotification_time(null);
            notification_picker.setText("Установить уведомление");
        });

        date_picker.setOnClickListener(v -> {
            setUserEventDate(userEvent, requireContext(), () -> {
                String text = DateConverter.date2String(userEvent.getStart_time(), "dd.MM.yy") +
                        '\n' + DateConverter.date2String(userEvent.getStart_time(), "HH:mm") +
                        "-" + DateConverter.date2String(userEvent.getEnd_time(), "HH:mm");
                date_picker.setText(text);
                clear_date.setVisibility(VISIBLE);
            });
        });
        notification_picker.setOnClickListener(v -> {
            setUserEventNotification(userEvent, requireContext(), () -> {
                String text = DateConverter.date2String(userEvent.getNotification_time(), "dd.MM.yy") +
                        " " + DateConverter.date2String(userEvent.getNotification_time(), "HH:mm");
                notification_picker.setText(text);
                clear_notification.setVisibility(VISIBLE);
            });
        });


        close_btn.setOnClickListener(v -> dismiss());
        add_btn.setOnClickListener(v -> {
            UserEventApi userEventApi = ServiceGenerator.createService(UserEventApi.class);
            AchievementApi achievementApi = ServiceGenerator.createService(AchievementApi.class);
            Achievement achievement = new Achievement(userEvent.getUser_id(), userEvent.getContest_id());

            compositeDisposable.add(
                    RequestGenerator.getInstance().makeApiCall(
                            "Олимпиада добавлена",
                            userEventApi.createUserEvent(userEvent)
                                    .flatMap(r -> achievementApi.createAchievement(achievement)),
                            r -> dismiss())
            );
        });
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.dispose();
    }

}
