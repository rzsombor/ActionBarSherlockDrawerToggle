package hu.scythe.actionbarsherlockdrawertoggledemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class WhatDialog extends DialogFragment {

	public static final String TAG = WhatDialog.class.getSimpleName();
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder.setTitle(R.string.dialog_title_what);
		builder.setMessage(R.string.dialog_content_what);
		builder.setNeutralButton(android.R.string.ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dismiss();
			}
		});
		
		return builder.create();
	}
	
}
