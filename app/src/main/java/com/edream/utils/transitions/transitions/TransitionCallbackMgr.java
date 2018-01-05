package com.edream.utils.transitions.transitions;

/**
 * Created by EdreamDev-01 on 2018. 1. 3..
 */

public class TransitionCallbackMgr {

    public interface TransitionCallBackListener {
        void onTransition(boolean visibility);
    }

    private static TransitionCallbackMgr mInstance;
    public static TransitionCallbackMgr getInstance() {
        if (mInstance == null) {
            mInstance = new TransitionCallbackMgr();
        }

        return mInstance;
    }

    private TransitionCallBackListener mListener;

    public void setListener(TransitionCallBackListener listener) {
        mListener = listener;
    }

    public void setThumbnailViewVisibleState(boolean visibility) {
        if (mListener != null) {
            mListener.onTransition(visibility);
        }
    }
}
