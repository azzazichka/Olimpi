package com.example.androidApp;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.androidApp.presenter.UserAuth;
import com.example.androidApp.view.AuthFragment;
import com.example.androidApp.view.contest_search.ContestSearchFragment;
import com.example.androidApp.view.profile.ProfileFragment;
import com.example.androidApp.view.user_events.UserEventsFragment;
import com.example.androidapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    SharedPreferences sharedPref;
    ContentLoadingProgressBar loading_progress_bar;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        loading_progress_bar = findViewById(R.id.loading);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setVisibility(GONE);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        LiveData<Boolean> authData = UserAuth.getInstance().getAuthData();
        authData.observe(this, authed -> {
            if (Boolean.FALSE.equals(authed)) {
                logOutUser();
            } else {
                bottomNavigationView.setVisibility(VISIBLE);
                bottomNavigationView.setSelectedItemId(R.id.nav_profile);

            }

            hideLoad(loading_progress_bar, this);
        });

        showLoad(loading_progress_bar, this);
        UserAuth.getInstance().userAuth(sharedPref.getString("api_key", ""));
    }

    public static void hideLoad(ContentLoadingProgressBar loading_progress_bar, Activity activity) {
        loading_progress_bar.hide();
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static void showLoad(ContentLoadingProgressBar loading_progress_bar, Activity activity) {
        loading_progress_bar.show();
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void logOutUser() {
        bottomNavigationView.setVisibility(GONE);

        UserAuth.getInstance().logOut();

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("api_key", "");
        editor.apply();

        loadFragment(new AuthFragment());
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        int itemId = item.getItemId();

        if (itemId == R.id.nav_user_events) {
            selectedFragment = new UserEventsFragment();
        } else if (itemId == R.id.nav_contest_search) {
            selectedFragment = new ContestSearchFragment();
        } else if (itemId == R.id.nav_profile) {
            selectedFragment = new ProfileFragment();
        }

        return loadFragment(selectedFragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}