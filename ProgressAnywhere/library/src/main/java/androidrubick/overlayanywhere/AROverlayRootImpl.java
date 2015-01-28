package androidrubick.overlayanywhere;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/1/28.
 */
/*package*/ class AROverlayRootImpl extends RelativeLayout {

    /*package*/ interface Callback {
        /**
         *
         * @param widthMeasureSpec
         * @param heightMeasureSpec
         * @return 是否需要重新测量
         */
        boolean onMeasured(int widthMeasureSpec, int heightMeasureSpec) ;

        /**
         *
         * @param changed
         * @param l
         * @param t
         * @param r
         * @param b
         * @return 是否需要覆盖View本身的onLayout
         */
        boolean onLayouted(boolean changed, int l, int t, int r, int b) ;
    }

    private Callback mCallback;
    public AROverlayRootImpl(Context context) {
        this(context, null);
    }

    public AROverlayRootImpl(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AROverlayRootImpl(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (null != mCallback && mCallback.onMeasured(widthMeasureSpec, heightMeasureSpec)) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (null != mCallback && mCallback.onLayouted(changed, l, t, r, b)) {
            return;
        }
        super.onLayout(changed, l, t, r, b);
    }

}
