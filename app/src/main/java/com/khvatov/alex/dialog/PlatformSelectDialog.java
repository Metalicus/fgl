package com.khvatov.alex.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.khvatov.alex.adapter.DbAdapter;
import com.khvatov.alex.adapter.PlatformListAdapter;
import com.khvatov.alex.entity.Platform;
import com.khvatov.alex.finishedgameslist.R;

/**
 * Modal dialog with platform list
 */
public class PlatformSelectDialog extends DialogFragment {

    private PlatformSelectListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        try (final DbAdapter adapter = new DbAdapter(getContext())) {
            adapter.open();

            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.platform_dialog_title);
            final PlatformListAdapter listAdapter = new PlatformListAdapter(getActivity(), adapter.getPlatforms());
            builder.setAdapter(listAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    listener.onSelect(listAdapter.getItem(which));
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    listener.onSelect(null);
                }
            });
            return builder.create();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = ((PlatformSelectListener) activity);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
        }
    }

    public interface PlatformSelectListener {
        void onSelect(Platform platform);
    }
}
