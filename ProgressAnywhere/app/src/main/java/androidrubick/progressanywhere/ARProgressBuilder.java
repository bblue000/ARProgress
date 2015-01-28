package androidrubick.progressanywhere;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/1/26.
 */
/*package*/ class ARProgressBuilder {

    public static ARProgressBuilder from(Context context) {
        return new ARProgressBuilder(context);
    }

    protected Context mContext;
    protected ARProgressListener mARProgressListener;
    protected boolean mCancelable = true;
    protected boolean mCanceledOnTouchOutside = true;
    protected int mStyleId;

    protected View mBindBoundView;

    protected static final int VIEW_NONE = 0;
    protected static final int VIEW_RES = 1;
    protected static final int VIEW_INS = 2;
    protected static final int VIEW_INS_LP = 3;
    protected int mViewType = VIEW_NONE;
    protected int mViewRes;
    protected View mViewIns;
    protected ViewGroup.LayoutParams mViewResLp;
    protected ARProgressBuilder(Context context) {
        mContext = context;
    }

    public ARProgressBuilder listeners(ARProgressListener listener) {
        mARProgressListener = listener;
        return this;
    }

    /**
     * Sets whether this dialog is cancelable with the
     * {@link android.view.KeyEvent#KEYCODE_BACK BACK} key.
     *
     * @param cancel  如果没有设置默认为true
     */
    public ARProgressBuilder cancelable(boolean...cancel) {
        mCanceledOnTouchOutside = getValue(cancel, true);
        return this;
    }

    /**
     * Sets whether this dialog is canceled when touched outside the window's
     * bounds. If setting to true, the dialog is set to be cancelable if not
     * already set.
     *
     * @param cancel Whether the dialog should be canceled when touched outside
     *            the window. 如果没有设置默认为true
     */
    public ARProgressBuilder cancelOnTouchOutside(boolean...cancel) {
        mCanceledOnTouchOutside = getValue(cancel, true);
        return this;
    }

    /**
     * 设置，将会从中查找
     * @param styleId
     * @return
     *
     * @see androidrubick.progressanywhere.R.styleable#ARPAProgress
     * @see androidrubick.progressanywhere.R.styleable#ARPAProgress_contentLayout__PA
     * @see androidrubick.progressanywhere.R.styleable#ARPAProgress_contentBackground__PA
     */
    public ARProgressBuilder style(int styleId) {
        mStyleId = styleId;
        return this;
    }

    public ARProgressBuilder view(int layoutId) {
        mViewType = VIEW_RES;
        mViewRes = layoutId;
        mViewIns = null;
        mViewResLp = null;
        return this;
    }

    public ARProgressBuilder view(View view) {
        mViewType = VIEW_INS;
        mViewRes = 0;
        mViewIns = view;
        mViewResLp = null;
        return this;
    }

    public ARProgressBuilder view(View view, WindowManager.LayoutParams layoutParams) {
        mViewType = VIEW_INS_LP;
        mViewRes = 0;
        mViewIns = view;
        mViewResLp = layoutParams;
        return this;
    }

    public ARProgressBuilder bindBoundView(View view) {
        mBindBoundView = view;
        return this;
    }

    private boolean getValue(boolean[] from, boolean ifnull) {
        boolean val = false;
        if (null == from || from.length == 0) {
            val = ifnull;
        }
        val = from[0];
        return val;
    }

    /**
     * 创建全局
     */
    public ARProgress build() {
        ContextThemeWrapper context = new ContextThemeWrapper(mContext, R.style.ARPAProgressStyleDefault);
        if (mStyleId > 0) {
            // update
            context.setTheme(mStyleId);
        }
        ARProgress dialog = new ARProgress(context, R.style.ARPAProgressRestrict);

        dialog.setOnShowListener(mARProgressListener);
        dialog.setOnDismissListener(mARProgressListener);
        dialog.setOnCancelListener(mARProgressListener);
        dialog.setOnKeyListener(mARProgressListener);

        dialog.setCancelable(mCancelable);
        dialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
        switch (mViewType) {
            case VIEW_RES:
                dialog.setContentView(mViewRes);
                break;
            case VIEW_INS:
                dialog.setContentView(mViewIns);
                break;
            case VIEW_INS_LP:
                dialog.setContentView(mViewIns, mViewResLp);
                break;
        }

        if (null != mBindBoundView) {
            dialog.setBindBoundView(mBindBoundView);
        }
        return dialog;
    }

}
