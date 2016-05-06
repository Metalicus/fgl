package com.khvatov.alex.finishedgameslist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.khvatov.alex.adapter.DbAdapter;
import com.khvatov.alex.entity.Game;
import com.khvatov.alex.entity.Platform;

public class PlatformDetailActivity extends AppCompatActivity {

    private Long id;

    private TextInputEditText edtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platform_detail);

        edtName = (TextInputEditText) findViewById(R.id.platformDetailName);
        final Button btnSave = (Button) findViewById(R.id.platformDetailSave);
        if (btnSave != null) {
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    savePlatform();
                }
            });
        }

        final Intent intent = getIntent();
        final Bundle extras = intent.getExtras();
        if (extras != null && extras.containsKey(Platform.ID)) {
            id = extras.getLong(Game.ID);
            edtName.setText(extras.getString(Platform.NAME));
        }
    }

    private void savePlatform() {
        if (TextUtils.isEmpty(edtName.getText())) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.validation_title));
            builder.setMessage(getString(R.string.platform_validation_message));
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            try (final DbAdapter adapter = new DbAdapter(getBaseContext())) {
                adapter.open();

                if (id != null) {
                    adapter.updatePlatform(id, edtName.getText().toString());
                } else {
                    adapter.createPlatform(edtName.getText().toString());
                }
            }

            onBackPressed();
        }
    }
}
