package com.example.perceptordeinseguridad;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

public interface MenuAnimations {

    default void floatButtonAnimations(View focus, View fView, ImageButton fImage, Context context){
        fImage.setVisibility(View.INVISIBLE);
        Animation animFloatButton;
        animFloatButton = AnimationUtils.loadAnimation(context, R.anim.show_float_view);
        Animation animFloatImage;
        animFloatImage = AnimationUtils.loadAnimation(context, R.anim.show_float_image);
        Animation focusAnimation;
        focusAnimation = AnimationUtils.loadAnimation(context, R.anim.focus_initial);
        fView.startAnimation(animFloatButton);
        fImage.startAnimation(animFloatImage);
        fImage.setVisibility(View.VISIBLE);
        focus.startAnimation(focusAnimation);
    }
}
