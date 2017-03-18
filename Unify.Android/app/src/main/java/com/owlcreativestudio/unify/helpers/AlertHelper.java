package com.owlcreativestudio.unify.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertHelper {
    public static void show(Context context, String title, String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton(android.R.string.yes, listener).show();
    }

    public static void show(Context context, String title, String message) {
        new AlertDialog.Builder(context).setTitle(title).setMessage(message).show();
    }
}
