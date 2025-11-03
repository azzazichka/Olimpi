package com.example.androidApp.view.contest_search.contest_list;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.androidApp.model.entity.Contest;
import com.example.androidapp.R;

public class ContestDialogFragment extends DialogFragment {

    public static ContestDialogFragment newInstance(Contest contest) {
        ContestDialogFragment fragment = new ContestDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("contest", contest);
        fragment.setArguments(args);
        return fragment;
    }

    Contest contest;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contest = (Contest) getArguments().getSerializable("contest");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contest_dialog, container, false);

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        }

        TextView contest_title = view.findViewById(R.id.contest_title_dialog);
        if (contest != null) {
            contest_title.setText(contest.getTitle());
        }
        return view;
    }
}
