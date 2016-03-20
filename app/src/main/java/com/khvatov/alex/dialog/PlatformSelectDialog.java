package com.khvatov.alex.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.khvatov.alex.adapter.PlatformListAdapter;
import com.khvatov.alex.entity.Platform;
import com.khvatov.alex.finishedgameslist.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Modal dialog with platform list
 */
public class PlatformSelectDialog extends DialogFragment {

    private PlatformSelectListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final List<Platform> platforms = new ArrayList<>();
        platforms.add(new Platform("Playstation 3"));
        platforms.add(new Platform("Playstation 4"));
        platforms.add(new Platform("PSP"));
        platforms.add(new Platform("PSVita"));
        platforms.add(new Platform("Sega Mega Drive 2"));
        platforms.add(new Platform("Dendy"));

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.platform_dialog_title);
        final PlatformListAdapter adapter = new PlatformListAdapter(getActivity(), platforms);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                listener.onSelect(adapter.getItem(which));
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
