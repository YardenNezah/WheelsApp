package com.example.wheelsapp.main;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;

import com.example.wheelsapp.R;
import com.example.wheelsapp.db.local.AppDatabase;
import com.example.wheelsapp.db.local.ThemeDao;
import com.example.wheelsapp.interfaces.LocalDatabaseInitializer;
import com.example.wheelsapp.models.Theme;

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class SettingsDialog extends DialogFragment implements LocalDatabaseInitializer {

    ThemeDao themeDao;
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(3);

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View v = getLayoutInflater().inflate(R.layout.settings_layout, null, false);
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(v)
                .setPositiveButton("Save", (di, pos) -> {
                    RadioGroup rg = v.findViewById(R.id.settings_rg);
                    int checked = rg.getCheckedRadioButtonId();
                    if (checked == R.id.settings_dark_mode) {
                        executor.execute(() -> {
                            Theme theme = themeDao.getThemeSingleValue();
                            if (theme == null) {
                                System.out.println("Insert");
                                themeDao.insertTheme(new Theme(getResources().getColor(R.color.color_theme_dark)));
                            }else {
                                System.out.println("Empty");
                                themeDao.updateTheme(getResources().getColor(R.color.color_theme_dark));
                            }

                        });

                    } else if (checked == R.id.settings_light_mode) {
                        executor.execute(() -> {
                            Theme theme = themeDao.getThemeSingleValue();
                            if (theme == null)
                                themeDao.insertTheme(new Theme(getResources().getColor(R.color.color_theme_light)));
                            else
                                themeDao.updateTheme(getResources().getColor(R.color.color_theme_light));
                        });
                    }
                })
                .setNegativeButton("Close", null)
                .create();
        return dialog;
    }

    @Override
    public void initializeRepo(Application context) {
        executor.execute(() -> themeDao = AppDatabase.getInstance(context).themeDao());

    }
}
