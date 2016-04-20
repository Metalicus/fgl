package com.khvatov.alex.finishedgameslist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.khvatov.alex.adapter.DbAdapter;

public class PlatformDetailActivity extends AppCompatActivity {

    private EditText edtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platform_detail);

        edtName = (EditText) findViewById(R.id.platformDetailName);
        final Button btnSave = (Button) findViewById(R.id.platformDetailSave);
        if (btnSave != null) {
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    savePlatform();
                    backToMainActivity();
                }
            });
        }
    }

    private void savePlatform() {
        if (TextUtils.isEmpty(edtName.getText())) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.validation_title));
            builder.setMessage(getString(R.string.platform_validation_message));
            final AlertDialog alertDialog = builder.create();
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(android.R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return;
        }

        try (final DbAdapter adapter = new DbAdapter(getBaseContext())) {
            adapter.open();
            adapter.createPlatform(edtName.getText().toString());
        }
    }

    private void backToMainActivity() {
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
