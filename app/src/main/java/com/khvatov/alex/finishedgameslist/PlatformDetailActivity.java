package com.khvatov.alex.finishedgameslist;

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
import android.widget.RelativeLayout;

import com.khvatov.alex.adapter.DbAdapter;
import com.khvatov.alex.entity.Game;
import com.khvatov.alex.entity.Platform;

public class PlatformDetailActivity extends AppCompatActivity {

    private Long id;

    private RelativeLayout layout;
    private TextInputEditText edtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platform_detail);

        layout = (RelativeLayout) findViewById(R.id.platform_edit_layout);
        edtName = (TextInputEditText) findViewById(R.id.platformDetailName);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.platform_edit_toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);

        final Intent intent = getIntent();
        final Bundle extras = intent.getExtras();
        if (extras != null && extras.containsKey(Platform.ID)) {
            id = extras.getLong(Game.ID);
            edtName.setText(extras.getString(Platform.NAME));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.platform_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_platform_save:

                if (validate()) {
                    savePlatform();
                    backToMainActivity();
                }

                return true;
            case R.id.action_platform_delete:

                if (id != null)
                    checkToDelete();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(edtName.getText())) {
            final Snackbar snackbar = Snackbar.make(layout, R.string.game_validation_message_platform, Snackbar.LENGTH_SHORT);
            snackbar.show();
            return false;
        }

        return true;
    }

    private void checkToDelete() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.delete_title));
        builder.setMessage(getString(R.string.delete_platform_message));
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deletePlatform();
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

    private void deletePlatform() {
        try (final DbAdapter adapter = new DbAdapter(getBaseContext())) {
            adapter.open();
            adapter.deletePlatform(id);
        }
    }

    private void savePlatform() {
        try (final DbAdapter adapter = new DbAdapter(getBaseContext())) {
            adapter.open();

            if (id != null) {
                adapter.updatePlatform(id, edtName.getText().toString());
            } else {
                adapter.createPlatform(edtName.getText().toString());
            }
        }
    }

    private void backToMainActivity() {
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
