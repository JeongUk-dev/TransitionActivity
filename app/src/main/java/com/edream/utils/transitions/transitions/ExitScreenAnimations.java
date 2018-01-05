package com.edream.utils.transitions.transitions;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.daasuu.ei.Ease;
import com.daasuu.ei.EasingInterpolator;

/**
 * Created by danylo.volokh on 3/16/16.
 */
public class ExitScreenAnimations {
    private final ImageView mAnimatedView;
    private final ImageView mEnlargedView;
    private View mTopContainer;
    private View mBottomContainer;

    private final long mDuration;
    private AnimatorSet mExitingAnimation;

    private int mToTop;
    private int mToLeft;
    private int mToWidth;
    private int mToHeight;

    private float[] mToThumbnailMatrixValues;

    public ExitScreenAnimations(ImageView animatedView, ImageView enlargedView, View topContainer, View bottomContainer, long duration) {
        mAnimatedView = animatedView;
        mEnlargedView = enlargedView;
        mTopContainer = topContainer;
        mBottomContainer = bottomContainer;
        mDuration = duration;
    }

    public void playExitAnimations(int toTop, int toLeft, int toWidth, int toHeight, float[] toThumbnailMatrixValues) {
        mToTop = toTop;
        mToLeft = toLeft;
        mToWidth = toWidth;
        mToHeight = toHeight;

        mToThumbnailMatrixValues = toThumbnailMatrixValues;

        if (mExitingAnimation == null) {
            playExitingAnimation();
        }
    }

    private void playExitingAnimation() {

        mAnimatedView.setVisibility(View.VISIBLE);
        mEnlargedView.setVisibility(View.INVISIBLE);

        AnimatorSet imageAnimatorSet = createExitingImageAnimation();

        AnimatorSet mainContainerSplitAnimator = createExitingSplitAnimator();

        mExitingAnimation = new AnimatorSet();
        mExitingAnimation.setDuration(mDuration);
        mExitingAnimation.addListener(new SimpleAnimationListener() {

            @Override
            public void onAnimationEnd(Animator animation) {
                PropertyValuesHolder propertyScaleX = PropertyValuesHolder.ofFloat("scaleX", (float) mEnlargedView.getWidth() / mToWidth, 1);
                PropertyValuesHolder propertyScaleY = PropertyValuesHolder.ofFloat("scaleY", (float) mEnlargedView.getHeight() / mToHeight, 1);
                ObjectAnimator popAnimator = ObjectAnimator.ofPropertyValuesHolder(mAnimatedView, propertyScaleX, propertyScaleY);
                popAnimator.setDuration(100);
                popAnimator.setInterpolator(new EasingInterpolator(Ease.BACK_OUT));
                popAnimator.addListener(new SimpleAnimationListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        TransitionCallbackMgr.getInstance().setThumbnailViewVisibleState(true);

                        mExitingAnimation = null;

                        Activity activity = (Activity) mAnimatedView.getContext();
                        activity.finish();
                        activity.overridePendingTransition(0, 0);

                    }
                });
                popAnimator.start();
            }
        });


        mExitingAnimation.playTogether(imageAnimatorSet, mainContainerSplitAnimator);

        mExitingAnimation.start();
    }

    private AnimatorSet createExitingImageAnimation() {

        ValueAnimator positionAnimator = createExitingImagePositionAnimator();

        AnimatorSet exitingImageAnimation = new AnimatorSet();
        exitingImageAnimation.playTogether(positionAnimator);

        return exitingImageAnimation;
    }

    @NonNull
    private ValueAnimator createExitingImagePositionAnimator() {
        int[] locationOnScreen = TransitionUtils.getLocationOnScreen(mAnimatedView);

        final Path arcPath = new Path();
        arcPath.moveTo(locationOnScreen[0], locationOnScreen[1]);
        arcPath.quadTo(locationOnScreen[0], mToTop, mToLeft, mToTop);

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            float point[] = {0, 0};

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = animation.getAnimatedFraction();
                final PathMeasure pm = new PathMeasure(arcPath, false);
                pm.getPosTan(pm.getLength() * val, point, null);
                mAnimatedView.setX(point[0]);
                mAnimatedView.setY(point[1]);
            }
        });

        return animator;
    }


    private ObjectAnimator createExitingImageMatrixAnimator() {

        Matrix initialMatrix = MatrixUtils.getImageMatrix(mAnimatedView);

        Matrix endMatrix = new Matrix();
        endMatrix.setValues(mToThumbnailMatrixValues);

        mAnimatedView.setScaleType(ImageView.ScaleType.MATRIX);

        return ObjectAnimator.ofObject(mAnimatedView, MatrixEvaluator.ANIMATED_TRANSFORM_PROPERTY,
                new MatrixEvaluator(), initialMatrix, endMatrix);
    }

    private AnimatorSet createExitingSplitAnimator() {

        PropertyValuesHolder translationTop = PropertyValuesHolder.ofFloat("translationY", mTopContainer.getHeight() * -1);
        PropertyValuesHolder fadeOutTop = PropertyValuesHolder.ofFloat("alpha", 1, 0);
        ObjectAnimator splitTopAnimator = ObjectAnimator.ofPropertyValuesHolder(mTopContainer, translationTop, fadeOutTop);

        PropertyValuesHolder translationBottom = PropertyValuesHolder.ofFloat("translationY", mBottomContainer.getHeight());
        PropertyValuesHolder fadeOutBottom = PropertyValuesHolder.ofFloat("alpha", 1, 0);
        ObjectAnimator splitBottomAnimator = ObjectAnimator.ofPropertyValuesHolder(mBottomContainer, translationBottom, fadeOutBottom);

        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.setInterpolator(new EasingInterpolator(Ease.QUAD_OUT));
        animatorSet.playTogether(splitTopAnimator, splitBottomAnimator);

        return animatorSet;
    }
}
