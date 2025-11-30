package com.example.androidApp.model.entity;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.androidApp.model.DateConverter;

import java.util.Calendar;
import java.util.Date;

public class UserEvent {
    private Long id;

    private String title;

    private Date start_time;

    private Date end_time;

    private Date notification_time;

    private Long contest_id;

    private Long user_id;

    public UserEvent(Long id, String title, Date start_time, Date end_time, Date notification_time, Long contest_id, Long user_id) {
        this.id = id;
        this.title = title;
        this.start_time = start_time;
        this.end_time = end_time;
        this.notification_time = notification_time;
        this.contest_id = contest_id;
    }

    public UserEvent() {

    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Date getNotification_time() {
        return notification_time;
    }

    public void setNotification_time(Date notification_time) {
        this.notification_time = notification_time;
    }

    @Override
    public String toString() {
        return "UserEvent{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", notification_time=" + notification_time +
                ", contest_id=" + contest_id +
                '}';
    }

    public Long getContest_id() {
        return contest_id;
    }

    public void setContest_id(Long contest_id) {
        this.contest_id = contest_id;
    }



    public static void setUserEventDate(UserEvent userEvent, Context context, TextView date_picker, ImageButton clear_date) {
        Calendar currentCalendar = Calendar.getInstance();
        Calendar date = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener listenerTimeEnd =
                (view, hourOfDay, minute) -> {
                    date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    date.set(Calendar.MINUTE, minute);
                    userEvent.setEnd_time(date.getTime());

                    String text = DateConverter.date2String(userEvent.getStart_time(), "dd.MM.yy") +
                            '\n' + DateConverter.date2String(userEvent.getStart_time(), "HH:mm") +
                            "-" + DateConverter.date2String(userEvent.getEnd_time(), "HH:mm");
                    date_picker.setText(text);
                    clear_date.setVisibility(VISIBLE);

                    view.setVisibility(GONE);
                };


        TimePickerDialog timeEndPickerDialog = new TimePickerDialog(
                context,
                listenerTimeEnd,
                currentCalendar.get(Calendar.HOUR_OF_DAY),
                currentCalendar.get(Calendar.MINUTE),
                true);


        TimePickerDialog.OnTimeSetListener listenerTimeStart =
                (view, hourOfDay, minute) -> {
                    date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    date.set(Calendar.MINUTE, minute);
                    userEvent.setStart_time(date.getTime());
                    timeEndPickerDialog.show();
                    view.setVisibility(GONE);
                };


        TimePickerDialog timeStartPickerDialog = new TimePickerDialog(
                context,
                listenerTimeStart,
                currentCalendar.get(Calendar.HOUR_OF_DAY),
                currentCalendar.get(Calendar.MINUTE),
                true);

        DatePickerDialog.OnDateSetListener listenerDate =
                (view, year, month, dayOfMonth) -> {
                    date.set(Calendar.YEAR, year);
                    date.set(Calendar.MONTH, month);
                    date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    timeStartPickerDialog.show();
                    view.setVisibility(GONE);
                };


        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                listenerDate,
                currentCalendar.get(Calendar.YEAR),
                currentCalendar.get(Calendar.MONTH),
                currentCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public static void setUserEventNotification(UserEvent userEvent, Context context, TextView notification_picker, ImageButton clear_notification) {
        Calendar currentCalendar = Calendar.getInstance();
        Calendar date = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener listenerTime =
                (view, hourOfDay, minute) -> {
                    date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    date.set(Calendar.MINUTE, minute);
                    userEvent.setNotification_time(date.getTime());


                    String text = DateConverter.date2String(userEvent.getNotification_time(), "dd.MM.yy") +
                            " " + DateConverter.date2String(userEvent.getNotification_time(), "HH:mm");
                    notification_picker.setText(text);
                    clear_notification.setVisibility(VISIBLE);
                    view.setVisibility(GONE);
                };


        TimePickerDialog timePickerDialog = new TimePickerDialog(
                context,
                listenerTime,
                currentCalendar.get(Calendar.HOUR_OF_DAY),
                currentCalendar.get(Calendar.MINUTE),
                true);

        DatePickerDialog.OnDateSetListener listenerDay =
                (view, year, month, dayOfMonth) -> {
                    date.set(Calendar.YEAR, year);
                    date.set(Calendar.MONTH, month);
                    date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    timePickerDialog.show();
                    view.setVisibility(GONE);
                };


        DatePickerDialog dayPickerDialog = new DatePickerDialog(
                context,
                listenerDay,
                currentCalendar.get(Calendar.YEAR),
                currentCalendar.get(Calendar.MONTH),
                currentCalendar.get(Calendar.DAY_OF_MONTH));
        dayPickerDialog.show();
    }
}
