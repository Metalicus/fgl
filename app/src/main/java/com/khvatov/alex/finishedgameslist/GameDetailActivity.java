package com.khvatov.alex.finishedgameslist;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;

import com.khvatov.alex.adapter.DbAdapter;
import com.khvatov.alex.dialog.PlatformSelectDialog;
import com.khvatov.alex.entity.Game;
import com.khvatov.alex.entity.Platform;
import com.khvatov.alex.utils.Const;

import java.util.Calendar;
import java.util.Date;

public class GameDetailActivity extends AppCompatActivity implements
        PlatformSelectDialog.PlatformSelectListener, DatePickerDialog.OnDateSetListener {

    private Long id;
    private Platform platform;
    private Date date;

    private RelativeLayout layout;
    private TextInputEditText editPlatform;
    private TextInputEditText editName;
    private TextInputEditText editDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        layout = (RelativeLayout) findViewById(R.id.game_edit_layout);

        editName = (TextInputEditText) findViewById(R.id.gameDetailName);

        editDate = (TextInputEditText) findViewById(R.id.gameDetailFinishDate);
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        editPlatform = (TextInputEditText) findViewById(R.id.gameDetailPlatform);
        editPlatform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPlatformDialog();
            }
        });

        final Toolbar toolbar = (Toolbar) findViewById(R.id.game_edit_toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);

        final Intent intent = getIntent();
        final Bundle extras = intent.getExtras();
        if (extras != null && extras.containsKey(Game.ID)) {
            id = extras.getLong(Game.ID);
            platform = (Platform) extras.getSerializable(Game.PLATFORM);
            date = (Date) extras.getSerializable(Game.FINISHED_DATE);

            updatePlatformUI();
            updateDateUI();
            editName.setText(extras.getString(Game.NAME));
        } else {
            date = new Date();
            updateDateUI();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_game_save:

                if (validate()) {
                    saveGame();
                    backToMainActivity();
                }

                return true;
            case R.id.action_game_delete:

                if (id != null)
                    checkToDelete();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updatePlatformUI() {
        editPlatform.setText(platform == null ? "" : platform.getName());
    }

    private void updateDateUI() {
        editDate.setText(Const.DATE_FORMAT.format(date));
    }

    @Override
    public void onSelect(Platform platform) {
        this.platform = platform;
        updatePlatformUI();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        this.date = c.getTime();
        updateDateUI();
    }

    private boolean validate() {

        if (TextUtils.isEmpty(editName.getText())) {
            makeSnackbar(layout, R.string.game_validation_message_name);
            return false;
        }

        if (platform == null) {
            makeSnackbar(layout, R.string.game_validation_message_platform);
            return false;
        }

        return true;
    }

    private void makeSnackbar(RelativeLayout layout, int messageId) {
        final Snackbar snackbar = Snackbar.make(layout, messageId, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void saveGame() {
        try (final DbAdapter adapter = new DbAdapter(getBaseContext())) {
            adapter.open();

            if (id != null) {
                adapter.updateGame(id, editName.getText().toString(), date, platform);
            } else {
                adapter.createGame(editName.getText().toString(), date, platform);
            }
        }
    }

    private void checkToDelete() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.delete_title));
        builder.setMessage(getString(R.string.delete_game_message));
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteGame();
                dialog.dismiss();
                backToMainActivity();
            }
        });
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteGame() {
        try (final DbAdapter adapter = new DbAdapter(getBaseContext())) {
            adapter.open();
            adapter.deleteGame(id);
        }
    }

    private void showSelectPlatformDialog() {
        final PlatformSelectDialog dialog = new PlatformSelectDialog();
        dialog.show(getSupportFragmentManager(), "PlatformSelectDialog");
    }

    private void showDatePickerDialog() {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog dialog = new DatePickerDialog(this, this, year, month, day);
        dialog.show();
    }

    private void backToMainActivity() {
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
