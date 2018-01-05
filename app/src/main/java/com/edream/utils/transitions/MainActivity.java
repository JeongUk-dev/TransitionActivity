package com.edream.utils.transitions;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.edream.utils.transitions.databinding.ActivityMainBinding;
import com.edream.utils.transitions.transitions.TransitionCallbackMgr;
import com.edream.utils.transitions.transitions.TransitionUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MainActivity extends Activity implements TransitionCallbackMgr.TransitionCallBackListener {

    private ActivityMainBinding mBinding;
    private ImageView sharedViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        TransitionCallbackMgr.getInstance().setListener(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    void initView() {
        ImageLoader.getInstance().displayImage(PhotoUrl.getMainSmallThumbImageUrl("판교몬"), mBinding.mainProfile);
        mBinding.mainProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedViewHolder = (ImageView) v;

                int[] location = new int[2];

                sharedViewHolder.getLocationOnScreen(location);

                Intent intent = TransitionUtils.getStartIntent(MainActivity.this, TestTranstionActivity.class, sharedViewHolder);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onTransition(boolean visibility) {
        sharedViewHolder.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }
}
