package com.edream.utils.transitions;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.edream.utils.transitions.databinding.IncludeProfileBinding;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by EdreamDev-01 on 2017. 12. 29..
 */

public class ProfilePhotoView extends FrameLayout {

    public IncludeProfileBinding mBinding;

    @DrawableRes
    private int[] coverImgRes = {R.drawable.profile_cover01, 0, 0, 0, 0, 0, 0, 0, 0};

    public ProfilePhotoView(@NonNull Context context) {
        super(context);
        initView();
    }

    public ProfilePhotoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ProfilePhotoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.include_profile, null, false);
    }

    /**
     * @param index 1 ~ 9 cover iamge index
     */
    public void setCover(int index) {
        if (0 < index && index < 10) {
            mBinding.profileCover.setImageResource(coverImgRes[index - 1]);
        }
    }

    public void setPhoto(String memberId) {
        ImageLoader.getInstance().displayImage(PhotoUrl.getMainBigThumbImageUrl(memberId), mBinding.profilePhoto);
    }

    public void setPhoto(String memberId, ImageLoadingListener listener) {
        ImageLoader.getInstance().displayImage(PhotoUrl.getMainBigThumbImageUrl(memberId), mBinding.profilePhoto, listener);
    }

    public void setCoverVisiblity(int visiblity) {
        mBinding.profileCover.setVisibility(visiblity);
    }

    public void setPhotoVisibility(int visibility) { mBinding.profilePhoto.setVisibility(visibility); }

    public ImageView getProfileBG() {
        return mBinding.profileBg;
    }

    public ImageView getDeco() {
        return mBinding.profileDeco;
    }
}
