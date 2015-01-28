package androidrubick.overlayanywhere;

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
public class AROverlayBuilder {

    public static AROverlayBuilder from(Context context) {
        return new AROverlayBuilder(context);
    }

    protected Context mContext;
    protected AROverlayListener mAROverlayListener;
    protected boolean mCancelableSet;
    protected boolean mCancelable;
    protected boolean mCanceledOnTouchOutsideSet;
    protected boolean mCanceledOnTouchOutside;
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
    protected AROverlayBuilder(Context context) {
        mContext = context;
    }

    public AROverlayBuilder listeners(AROverlayListener listener) {
        mAROverlayListener = listener;
        return this;
    }

    /**
     * Sets whether this dialog is cancelable with the
     * {@link android.view.KeyEvent#KEYCODE_BACK BACK} key.
     *
     * @param cancel  如果没有设置默认为true
     */
    public AROverlayBuilder cancelable(boolean...cancel) {
        mCancelable = getValue(cancel, AROverlayDialog.DEFAULT_CANCELABLE);
        mCancelableSet = true;
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
    public AROverlayBuilder cancelOnTouchOutside(boolean...cancel) {
        mCanceledOnTouchOutside = getValue(cancel, AROverlayDialog.DEFAULT_CLOSEONTOUCHOUTSIDE);
        mCanceledOnTouchOutsideSet = true;
        return this;
    }

    /**
     * 设置，将会从中查找
     * @param styleId
     * @return
     *
     * @see androidrubick.overlayanywhere.R.styleable#ARPAProgress
     * @see androidrubick.overlayanywhere.R.styleable#ARPAProgress_contentLayout__PA
     * @see androidrubick.overlayanywhere.R.styleable#ARPAProgress_contentBackground__PA
     * @see androidrubick.overlayanywhere.R.styleable#ARPAProgress_cancelable__PA
     * @see androidrubick.overlayanywhere.R.styleable#ARPAProgress_android_windowAnimationStyle
     * @see androidrubick.overlayanywhere.R.styleable#ARPAProgress_android_windowCloseOnTouchOutside
     */
    public AROverlayBuilder style(int styleId) {
        mStyleId = styleId;
        return this;
    }

    public AROverlayBuilder view(int layoutId) {
        mViewType = VIEW_RES;
        mViewRes = layoutId;
        mViewIns = null;
        mViewResLp = null;
        return this;
    }

    public AROverlayBuilder view(View view) {
        mViewType = VIEW_INS;
        mViewRes = 0;
        mViewIns = view;
        mViewResLp = null;
        return this;
    }

    public AROverlayBuilder view(View view, WindowManager.LayoutParams layoutParams) {
        mViewType = VIEW_INS_LP;
        mViewRes = 0;
        mViewIns = view;
        mViewResLp = layoutParams;
        return this;
    }

    public AROverlayBuilder bindBoundView(View view) {
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
    public AROverlay build() {
//        return new AROverlayWrapper(this);
        return buildInner();
    }

    protected AROverlay buildInner() {
        ContextThemeWrapper context = new ContextThemeWrapper(mContext, R.style.AROverlayStyleDefault);
        if (mStyleId > 0) {
            // update
            context.setTheme(mStyleId);
        }
        AROverlayDialog dialog = new AROverlayDialog(context, R.style.AROverlayRestrict);

        dialog.setOnShowListener(mAROverlayListener);
        dialog.setOnDismissListener(mAROverlayListener);
        dialog.setOnCancelListener(mAROverlayListener);
        dialog.setOnKeyListener(mAROverlayListener);

        if (mCancelableSet) {
            dialog.setCancelable(mCancelable);
        }
        if (mCanceledOnTouchOutsideSet) {
            dialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
        }
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
