package com.example.androidApp.view.contest_search;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidApp.MainActivity;
import com.example.androidApp.presenter.UserAuth;
import com.example.androidapp.R;

public class SearchFilterFragment extends Fragment {
    public SearchFilterFragment() {
        super(R.layout.fragment_search_filter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
