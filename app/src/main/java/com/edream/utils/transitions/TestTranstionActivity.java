package com.edream.utils.transitions;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.edream.utils.transitions.databinding.ActivityTestBinding;
import com.edream.utils.transitions.transitions.TransitionActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by EdreamDev-01 on 2018. 1. 2..
 */

public class TestTranstionActivity extends TransitionActivity {

    ActivityTestBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        initView();
        startTransition(savedInstanceState);
    }

    void initView() {
        ImageLoader.getInstance().displayImage(PhotoUrl.getMainBigThumbImageUrl("판교몬"), mBinding.detailProfileLayout.profilePhoto);
    }

    @Override
    protected long transitionDuration() {
        return 650;
    }

    @Override
    protected View mainContainer() {
        return mBinding.scrollView;
    }

    @Override
    protected ImageView enlaredView() {
        mBinding.detailProfileLayout.profilePhoto.setVisibility(View.INVISIBLE);
        return mBinding.detailProfileLayout.profilePhoto;
    }

    @Override
    protected View topContainer() {
        return mBinding.topLayout;
    }

    @Override
    protected View bottomContainer() {
        return mBinding.bottomLayout;
    }

    @Override
    public void onBackPressed() {
        finishTransition();
    }
}
