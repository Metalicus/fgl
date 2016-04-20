package com.khvatov.alex.finishedgameslist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.khvatov.alex.adapter.DbAdapter;
import com.khvatov.alex.dialog.PlatformSelectDialog;
import com.khvatov.alex.entity.Platform;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GameDetailActivity extends AppCompatActivity implements
        PlatformSelectDialog.PlatformSelectListener, DatePickerDialog.OnDateSetListener {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());


    private Platform platform;
    private Date date;

    private EditText editPlatform;
    private EditText editName;
    private EditText editDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        editName = (EditText) findViewById(R.id.gameDetailName);

        editDate = (EditText) findViewById(R.id.gameDetailFinishDate);
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        editPlatform = (EditText) findViewById(R.id.gameDetailPlatform);
        editPlatform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPlatformDialog();
            }
        });

        final Button btnSave = (Button) findViewById(R.id.gameDetailSave);
        if (btnSave != null) {
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveGame();
                    backToMainActivity();
                }
            });
        }
    }

    @Override
    public void onSelect(Platform platform) {
        this.platform = platform;
        editPlatform.setText(platform == null ? "" : platform.getName());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        this.date = c.getTime();
        editDate.setText(DATE_FORMAT.format(date));
    }

    private void saveGame() {
        if (!validate())
            return;

        try (final DbAdapter adapter = new DbAdapter(getBaseContext())) {
            adapter.open();
            adapter.createGame(editName.getText().toString(), date, platform);
        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(editName.getText())) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Warning");
            builder.setMessage("Name of a game can not be empty");
            final AlertDialog alertDialog = builder.create();
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return false;
        }

        if (platform == null) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Warning");
            builder.setMessage("Platform of a game can not be empty");
            final AlertDialog alertDialog = builder.create();
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return false;
        }

        if (date == null) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Warning");
            builder.setMessage("Finished date can not be empty");
            final AlertDialog alertDialog = builder.create();
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return false;
        }

        return true;
    }

    private void showSelectPlatformDialog() {
        final PlatformSelectDialog dialog = new PlatformSelectDialog();
        dialog.show(getSupportFragmentManager(), "PlatformSelectDialog");
    }

    private void showDatePickerDialog() {
        final DatePickerFragment dialog = new DatePickerFragment(this);
        dialog.show(getSupportFragmentManager(), "DatePickerFragment");
    }

    private void backToMainActivity() {
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
