package com.edream.utils.transitions.transitions;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by EdreamDev-01 on 2018. 1. 2..
 */

public abstract class TransitionActivity extends Activity {
    protected ImageView mTransitionView;

    protected EnterScreenAnimations mEnterScreenAnimations;
    protected ExitScreenAnimations mExitScreenAnimations;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
    }

    protected abstract long transitionDuration();

    protected abstract View mainContainer();

    protected abstract View topContainer();

    protected abstract View bottomContainer();

    protected abstract ImageView enlaredView();

    protected void startTransition(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            initThumbnailView();
            mEnterScreenAnimations = new EnterScreenAnimations(mTransitionView, enlaredView(), mainContainer(), transitionDuration());
            mExitScreenAnimations = new ExitScreenAnimations(mTransitionView, enlaredView(), topContainer(), bottomContainer(), transitionDuration());
            runEnteringAnimation();
        } else {
            mainContainer().setAlpha(1);
        }
    }

    private void initThumbnailView() {
        FrameLayout androidContent = (FrameLayout) getWindow().getDecorView().findViewById(android.R.id.content);

        mTransitionView = new ImageView(this);
        androidContent.addView(mTransitionView);

        Bundle bundle = getIntent().getExtras();

        Bitmap thumbnailImage = bundle.getParcelable(TransitionUtils.KEY_THUMBNAIL_IMAGE);
        int thumbnailTop = bundle.getInt(TransitionUtils.KEY_THUMBNAIL_INIT_TOP_POSITION);
        int thumbnailLeft = bundle.getInt(TransitionUtils.KEY_THUMBNAIL_INIT_LEFT_POSITION);
        int thumbnailWidth = bundle.getInt(TransitionUtils.KEY_THUMBNAIL_INIT_WIDTH);
        int thumbnailHeight = bundle.getInt(TransitionUtils.KEY_THUMBNAIL_INIT_HEIGHT);
        ImageView.ScaleType scaleType = (ImageView.ScaleType) bundle.getSerializable(TransitionUtils.KEY_SCALE_TYPE);

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mTransitionView.getLayoutParams();
        layoutParams.height = thumbnailHeight;
        layoutParams.width = thumbnailWidth;
        layoutParams.setMargins(thumbnailLeft, thumbnailTop, 0, 0);

        mTransitionView.setScaleType(scaleType);
        mTransitionView.setImageBitmap(thumbnailImage);
    }

    private void runEnteringAnimation() {
        enlaredView().getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            int mFrames = 0;

            @Override
            public boolean onPreDraw() {
                switch (mFrames++) {
                    case 0:
                        final int[] finalLocationOnTheScreen = TransitionUtils.getLocationOnScreen(enlaredView());

                        mEnterScreenAnimations.playEnteringAnimation(
                                finalLocationOnTheScreen[0],
                                finalLocationOnTheScreen[1],
                                enlaredView().getWidth(),
                                enlaredView().getHeight());

                        return true;
                    case 1:
                        return true;
                }

                TransitionCallbackMgr.getInstance().setThumbnailViewVisibleState(false);
                enlaredView().getViewTreeObserver().removeOnPreDrawListener(this);

                return true;
            }
        });
    }

    protected void finishTransition() {
        if (mEnterScreenAnimations == null) {
            finish();
            return;
        }

        mEnterScreenAnimations.cancelRunningAnimations();

        Bundle bundle = getIntent().getExtras();

        int thumbnailTop = bundle.getInt(TransitionUtils.KEY_THUMBNAIL_INIT_TOP_POSITION);
        int thumbnailLeft = bundle.getInt(TransitionUtils.KEY_THUMBNAIL_INIT_LEFT_POSITION);
        int thumbnailWidth = bundle.getInt(TransitionUtils.KEY_THUMBNAIL_INIT_WIDTH);
        int thumbnailHeight = bundle.getInt(TransitionUtils.KEY_THUMBNAIL_INIT_HEIGHT);

        mExitScreenAnimations.playExitAnimations(thumbnailTop, thumbnailLeft, thumbnailWidth, thumbnailHeight, mEnterScreenAnimations.getInitialThumbnailMatrixValues());
    }
}
