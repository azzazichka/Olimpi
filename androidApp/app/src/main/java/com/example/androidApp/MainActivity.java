package com.example.androidApp;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.androidApp.presenter.server.RequestGenerator;
import com.example.androidApp.presenter.server.requests.UserAuth;
import com.example.androidApp.view.AuthFragment;
import com.example.androidApp.view.contest_search.ContestSearchFragment;
import com.example.androidApp.view.contest_search.SearchFilterFragment;
import com.example.androidApp.view.profile.achievements.AchievementsFragment;
import com.example.androidApp.view.profile.ProfileFragment;
import com.example.androidApp.view.user_events.CalendarFragment;
import com.example.androidApp.view.user_events.UserEventsFragment;
import com.example.androidapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    SharedPreferences sharedPref;
    ContentLoadingProgressBar loading_progress_bar;
    BottomNavigationView bottomNavigationView;
    LinearLayout bottomSheetLayout;
    BottomSheetBehavior<LinearLayout> bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        RequestGenerator.getInstance().setMainActivity(this);

        loading_progress_bar = findViewById(R.id.loading);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setVisibility(GONE);

        bottomSheetLayout = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        bottomSheetLayout.setVisibility(GONE);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {

            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        LiveData<Boolean> authData = UserAuth.getInstance().getAuthData();
        authData.observe(this, authed -> {
            Log.i("AZZA", "onCreate: auth " + authed);
            if (Boolean.FALSE.equals(authed)) {
                logOutUser();
            } else {
                bottomNavigationView.setVisibility(VISIBLE);
                bottomSheetLayout.setVisibility(VISIBLE);
                bottomNavigationView.setSelectedItemId(R.id.nav_profile);

            }

            hideLoad();
        });

        showLoad();
        UserAuth.getInstance().userAuth(sharedPref.getString("api_key", ""));
    }

    public void hideLoad() {
        loading_progress_bar.hide();
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void showLoad() {
        loading_progress_bar.show();
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void logOutUser() {
        bottomNavigationView.setVisibility(GONE);
        bottomSheetLayout.setVisibility(GONE);


        UserAuth.getInstance().logOut();

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("api_key", "");
        editor.apply();

        loadFragment(new AuthFragment(), R.id.fragment_container);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        Fragment bottomSelectedFragment = null;
        int itemId = item.getItemId();

        if (itemId == R.id.nav_user_events) {
            selectedFragment = new UserEventsFragment();
            bottomSelectedFragment = new CalendarFragment();
        } else if (itemId == R.id.nav_contest_search) {
            selectedFragment = new ContestSearchFragment();
            bottomSelectedFragment = new SearchFilterFragment();
        } else if (itemId == R.id.nav_profile) {
            selectedFragment = new ProfileFragment();
            bottomSelectedFragment = new AchievementsFragment();
        }
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        boolean res = true;
        res &= loadFragment(selectedFragment, R.id.fragment_container);
        res &= loadFragment(bottomSelectedFragment, R.id.fragment_container_bottom_sheet);
        return res;
    }

    private boolean loadFragment(Fragment fragment, int containerId) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(containerId, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}