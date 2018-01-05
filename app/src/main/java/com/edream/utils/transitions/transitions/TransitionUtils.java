package com.edream.utils.transitions.transitions;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by EdreamDev-01 on 2018. 1. 3..
 */

public class TransitionUtils {

    public static final String KEY_TRANSITION_ENABLED = "KEY_TRANSITION";
    public static final String KEY_THUMBNAIL_IMAGE = "KEY_THUMBNAIL_IMAGE";
    public static final String KEY_THUMBNAIL_INIT_TOP_POSITION = "KEY_THUMBNAIL_INIT_TOP_POSITION";
    public static final String KEY_THUMBNAIL_INIT_LEFT_POSITION = "KEY_THUMBNAIL_INIT_LEFT_POSITION";
    public static final String KEY_THUMBNAIL_INIT_WIDTH = "KEY_THUMBNAIL_INIT_WIDTH";
    public static final String KEY_THUMBNAIL_INIT_HEIGHT = "KEY_THUMBNAIL_INIT_HEIGHT";
    public static final String KEY_SCALE_TYPE = "KEY_SCALE_TYPE";

    public static Intent getStartIntent(Context context, Class<?> cls, ImageView thumbnailView) {
        int[] screenLocation = getLocationOnScreen(thumbnailView);

        Intent intent = new Intent(context, cls);
        intent.putExtra(KEY_TRANSITION_ENABLED, true);
        intent.putExtra(KEY_THUMBNAIL_IMAGE, ((BitmapDrawable) thumbnailView.getDrawable()).getBitmap());
        intent.putExtra(KEY_THUMBNAIL_INIT_LEFT_POSITION, screenLocation[0]);
        intent.putExtra(KEY_THUMBNAIL_INIT_TOP_POSITION, screenLocation[1]);
        intent.putExtra(KEY_THUMBNAIL_INIT_WIDTH, thumbnailView.getWidth());
        intent.putExtra(KEY_THUMBNAIL_INIT_HEIGHT, thumbnailView.getHeight());
        intent.putExtra(KEY_SCALE_TYPE, thumbnailView.getScaleType());

        return intent;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int[] getLocationOnScreen(View view) {
        int[] position = new int[2];
        view.getLocationOnScreen(position);
        position[1]  = position[1] - getStatusBarHeight(view.getContext());
        return position;
    }
}
