package com.khvatov.alex.finishedgameslist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.khvatov.alex.dialog.PlatformSelectDialog;
import com.khvatov.alex.entity.Game;
import com.khvatov.alex.entity.Platform;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class GameDetailActivity extends AppCompatActivity implements
        PlatformSelectDialog.PlatformSelectListener, DatePickerDialog.OnDateSetListener {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    private Game game = new Game();
    private EditText editPlatform;
    private EditText editName;
    private EditText editDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        editName = (EditText) findViewById(R.id.gameDetailName);

        editDate = (EditText) findViewById(R.id.gameDetailFinishDate);
        //noinspection ConstantConditions
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        editPlatform = (EditText) findViewById(R.id.gameDetailPlatform);
        //noinspection ConstantConditions
        editPlatform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPlatformDialog();
            }
        });
    }

    @Override
    public void onSelect(Platform platform) {
        game.setPlatform(platform);
        editPlatform.setText(platform == null ? "" : platform.getName());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        game.setFinishedDate(c.getTime());
        editDate.setText(DATE_FORMAT.format(game.getFinishedDate()));
    }

    private void showSelectPlatformDialog() {
        final PlatformSelectDialog dialog = new PlatformSelectDialog();
        dialog.show(getSupportFragmentManager(), "PlatformSelectDialog");
    }

    private void showDatePickerDialog() {
        final DatePickerFragment dialog = new DatePickerFragment(this);
        dialog.show(getSupportFragmentManager(), "DatePickerFragment");
    }

    public static class DatePickerFragment extends DialogFragment {

        private final DatePickerDialog.OnDateSetListener listener;

        public DatePickerFragment(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), listener, year, month, day);
        }
    }

}
