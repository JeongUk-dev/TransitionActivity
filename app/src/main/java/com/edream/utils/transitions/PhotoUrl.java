package com.edream.utils.transitions;

public class PhotoUrl {

    public static String getOrgImageUrl(String mMemberID, int mPhotoNum) {
        StringBuilder retStr = new StringBuilder("http://youphoto.youmedate.net/loveting/");
        retStr.append("photo/")
                .append(mMemberID)
                .append("_")
                .append(String.valueOf(mPhotoNum))
                .append(".jpg");
        return retStr.toString();
    }

    public static String getThumbImageUrl(String mMemberID, int mPhotoNum) {
        StringBuilder retStr = new StringBuilder("http://youphoto.youmedate.net/loveting/");
        if (mPhotoNum == 2 || mPhotoNum == 3 || mPhotoNum == 4) {
            retStr.append("photo/thumb" + mPhotoNum + "/thumb_");
        } else {
            retStr.append("photo/thumb/thumb_");
        }
        retStr.append(mMemberID)
                .append(".jpg");
        return retStr.toString();
    }

    public static String getMainBigThumbImageUrl(String mMemberID) {
        StringBuilder retStr = new StringBuilder("http://youphoto.youmedate.net/loveting/");
        retStr.append("photo/thumb_big/bigthumb")
                .append("_")
                .append(mMemberID)
                .append(".jpg");
        return retStr.toString();
    }

    public static String getMainSmallThumbImageUrl(String mMemberID) {
        StringBuilder retStr = new StringBuilder("http://youphoto.youmedate.net/loveting//");
        retStr.append("photo/thumb/thumb")
                .append("_")
                .append(mMemberID)
                .append(".jpg");
        return retStr.toString();
    }

    public static String getSelfDatingImageUrl(int mSelfDatingSeq, int mPhotoNum) {
        StringBuilder retStr = new StringBuilder("http://youphoto.youmedate.net/loveting/");
        retStr.append("photo/self_dating")
                .append("/")
                .append(String.valueOf(mSelfDatingSeq))
                .append("_")
                .append(String.valueOf(mPhotoNum))
                .append(".jpg");
        return retStr.toString();
    }

    public static String getStampOrgImageUrl(int mPartNum, int kind, String mMemberId, int mPhotoNum) {
        StringBuilder retStr = new StringBuilder("http://youphoto.youmedate.net/loveting/");
        retStr.append("stamp/part")
                .append(String.valueOf(mPartNum))
                .append("/")
                .append(String.valueOf(kind))
                .append("/")
                .append(mMemberId)
                .append("_")
                .append(String.valueOf(mPhotoNum))
                .append(".jpg");
        return retStr.toString();
    }

    public static String getStampThumbImageUrl(int mPartNum, int kind, String mMemberId, int mPhotoNum) {
        StringBuilder retStr = new StringBuilder("http://youphoto.youmedate.net/loveting/");
        retStr.append("stamp/part")
                .append(String.valueOf(mPartNum))
                .append("/")
                .append(String.valueOf(kind))
                .append("_thumb/")
                .append(mMemberId)
                .append("_")
                .append(String.valueOf(mPhotoNum))
                .append(".jpg");
        return retStr.toString();
    }

    public static String getBannerUrl(int mBannerSeq) {
        StringBuilder retStr = new StringBuilder("http://youphoto.youmedate.net/loveting/");
        retStr.append("photo")
                .append("/eventbanner")
                .append("/")
                .append(mBannerSeq)
                .append(".jpg");

        return retStr.toString();
    }

    // bestPhoto원
    public static String getBestPhotoOrgImageUrl(int mBestPhotoJoinSeq) {
        StringBuilder retStr = new StringBuilder("http://youphoto.youmedate.net/loveting/");
        retStr.append("photo/best_photo/")
                .append(mBestPhotoJoinSeq)
                .append(".jpg");
        return retStr.toString();
    }

    // bestPhoto썸네일
    public static String getBestPhotoThumbImageUrl(int mBestPhotoJoinSeq) {
        StringBuilder retStr = new StringBuilder("http://youphoto.youmedate.net/loveting/");
        retStr.append("photo/best_photo/")
                .append(mBestPhotoJoinSeq)
                .append("_thumb.jpg");
        return retStr.toString();
    }

    // bestPhoto이벤트 타이틀 이미지
    public static String getBestPhotoEventImageUrl(int mBestPhotoEventSeq) {
        StringBuilder retStr = new StringBuilder("http://youphoto.youmedate.net/loveting/");
        retStr.append("photo/best_photo/title_")
                .append(mBestPhotoEventSeq)
                .append(".jpg");
        return retStr.toString();
    }
}
