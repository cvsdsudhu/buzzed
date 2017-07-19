package com.buzzed.utility;

import android.content.Context;
import android.view.View;

import com.buzzed.R;


public class Dialog extends android.app.Dialog {

    public Dialog(Context context) {

        super(context, R.style.FullHeightDialog);
        this.setContentView(R.layout.loading_dialog);
        this.setCancelable(false);
    }
}
