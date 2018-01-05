package com.edream.utils.transitions.transitions;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.daasuu.ei.Ease;
import com.daasuu.ei.EasingInterpolator;

/**
 * Created by danylo.volokh on 3/16/16.
 */
public class EnterScreenAnimations {

    private final ImageView mImageTo;
    private final ImageView mAnimatedView;
    private View mMainContainer;
    private View mTopContainer;
    private View mBottomContainer;

    private final long mDuration;
    private AnimatorSet mEnteringAnimation;

    private float[] mInitThumbnailMatrixValues = new float[9];

    private int mToTop;
    private int mToLeft;
    private int mToWidth;
    private int mToHeight;

    public EnterScreenAnimations(ImageView animatedView, ImageView imageTo, View mainContainer, long duration) {
        mAnimatedView = animatedView;
        mImageTo = imageTo;
        mMainContainer = mainContainer;
        mDuration = duration;
    }

    public void playEnteringAnimation(int left, int top, int width, int height) {

        mToLeft = left;
        mToTop = top;
        mToWidth = width;
        mToHeight = height;


        AnimatorSet imageAnimatorSet = createEnteringImageAnimation();

        Animator mainContainerFadeAnimator = createEnteringFadeAnimator();

        mEnteringAnimation = new AnimatorSet();
        mEnteringAnimation.setDuration(mDuration);
        mEnteringAnimation.addListener(new SimpleAnimationListener() {

            @Override
            public void onAnimationCancel(Animator animation) {
                mEnteringAnimation = null;
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if (mEnteringAnimation != null) {
                    mEnteringAnimation = null;

                    PropertyValuesHolder propertyScaleX = PropertyValuesHolder.ofFloat("scaleX", 1, mToWidth / (float) mAnimatedView.getWidth());
                    PropertyValuesHolder propertyScaleY = PropertyValuesHolder.ofFloat("scaleY", 1, mToHeight / (float) mAnimatedView.getHeight());
                    ObjectAnimator popAnimator = ObjectAnimator.ofPropertyValuesHolder(mAnimatedView, propertyScaleX, propertyScaleY);
                    popAnimator.setDuration(100);
//                    popAnimator.setStartDelay(100);
                    popAnimator.setInterpolator(new EasingInterpolator(Ease.BACK_OUT));
                    popAnimator.addListener(new SimpleAnimationListener() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mImageTo.setVisibility(View.VISIBLE);
                            mAnimatedView.setVisibility(View.INVISIBLE);
                        }
                    });
                    popAnimator.start();
                }
            }
        });

        mEnteringAnimation.playTogether(
                imageAnimatorSet,
                mainContainerFadeAnimator
        );

        mEnteringAnimation.start();
    }

    private ObjectAnimator createEnteringFadeAnimator() {
        ObjectAnimator fadeInAnimator = ObjectAnimator.ofFloat(mMainContainer, "alpha", 0.0f, 1.0f);
        fadeInAnimator.setInterpolator(new EasingInterpolator(Ease.QUAD_IN));
        return fadeInAnimator;
    }

    @NonNull
    private AnimatorSet createEnteringImageAnimation() {

        ValueAnimator positionAnimator = createEnteringImagePositionAnimator();

        AnimatorSet enteringImageAnimation = new AnimatorSet();
        enteringImageAnimation.playTogether(positionAnimator);
        return enteringImageAnimation;
    }

    @NonNull
    private ValueAnimator createEnteringImagePositionAnimator() {
        final int mToRight = this.mToLeft + mToWidth;
        final int mToTop = this.mToTop;
        final int mToBottom = this.mToTop + mToHeight;

        int destLeft = mToLeft + (mToWidth / 2) - mAnimatedView.getWidth() / 2;
        int destTop = mToTop + (mToHeight / 2) - mAnimatedView.getHeight() / 2;

        final Path arcPath = new Path();
        arcPath.moveTo(mAnimatedView.getX(), mAnimatedView.getY());
        arcPath.quadTo(mAnimatedView.getX(), destTop, destLeft, destTop);

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

    private ObjectAnimator createEnteringImageMatrixAnimator() {
        Matrix initMatrix = MatrixUtils.getImageMatrix(mAnimatedView);
        initMatrix.getValues(mInitThumbnailMatrixValues);

        final Matrix endMatrix = MatrixUtils.getImageMatrix(mImageTo);

        mAnimatedView.setScaleType(ImageView.ScaleType.MATRIX);

        return ObjectAnimator.ofObject(mAnimatedView, MatrixEvaluator.ANIMATED_TRANSFORM_PROPERTY, new MatrixEvaluator(), initMatrix, endMatrix);
    }

    public float[] getInitialThumbnailMatrixValues() {
        return mInitThumbnailMatrixValues;
    }

    public void cancelRunningAnimations() {

        if (mEnteringAnimation != null) {
            // cancel existing animation
            mEnteringAnimation.cancel();
        }
    }
}
