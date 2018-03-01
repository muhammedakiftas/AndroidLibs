package com.sinifdefterimpro.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sinifdefterimpro.R;

/**
 * Created by muham on 2.11.2017.
 */

public class MyToast {

    public MyToast(Activity activity, Drawable drw, String text){
        LayoutInflater inflater = activity.getLayoutInflater();
        View toastLayout = inflater.inflate(R.layout.custom_toast, (ViewGroup) activity.findViewById(R.id.custom_toast_layout));
        TextView toastText = (TextView) toastLayout.findViewById(R.id.custom_toast_message);
        toastText.setText(text);
        ImageView toastImage = (ImageView) toastLayout.findViewById(R.id.custom_toast_image);
        toastImage.setImageDrawable(drw);

        Toast toast = new Toast(activity.getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastLayout);
        toast.show();
    }
}
