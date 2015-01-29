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
    protected AROverlayPresetType mPresetType;
    protected int mStyleId;
    protected int mAnimationStyleId;

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
        mCanceledOnTouchOutside = getValue(cancel, AROverlayDialog.DEFAULT_CANCELONTOUCHOUTSIDE);
        mCanceledOnTouchOutsideSet = true;
        return this;
    }

    /**
     * 设置风格——{@link androidrubick.overlayanywhere.R.styleable#ARPAProgress}中的相关属性，将会从{@code styleId}
     * 和context中查找相应的属性
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

    /**
     * 设置动画style；该style中需要定义{@link android.R.attr#windowEnterAnimation}和{@link android.R.attr#windowExitAnimation}
     */
    public AROverlayBuilder animationStyle(int styleId) {
        mAnimationStyleId = styleId;
        return this;
    }

    /**
     * 设置遮盖视图中显示的内容的布局资源ID
     */
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

    /**
     * 遮盖视图将会绑定参数View区域显示（调用{@link AROverlay#show()}方法）
     *
     * <p/>
     *
     * <b>注意：</b>当view可见状态时{@link android.view.View#GONE} 时，将抛出异常；该种情况可以考虑使用可见的
     * 父控件或者同级控件
     */
    public AROverlayBuilder bindBoundView(View view) {
        mBindBoundView = view;
        return this;
    }

    /**
     * 预置的设置，暂时只配置是否可以取消Overlay（点击回退按钮、点击指定区域之外）
     * <p/>
     */
    public AROverlayBuilder presetType(AROverlayPresetType type) {
        mPresetType = type;
        mCancelableSet = false;
        mCanceledOnTouchOutsideSet = false;
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
        return buildInner();
    }

    protected AROverlay buildInner() {
        ContextThemeWrapper context = new ContextThemeWrapper(mContext, R.style.AROverlayStyleDefault);
        if (mStyleId > 0) {
            // update
            context.setTheme(mStyleId);
        }
        AROverlayDialog dialog = new AROverlayDialog(context, R.style.AROverlayRestrict);

        if (mAnimationStyleId > 0) {
            dialog.setAnimationStyle(mAnimationStyleId);
        }

        dialog.setOnShowListener(mAROverlayListener);
        dialog.setOnDismissListener(mAROverlayListener);
        dialog.setOnCancelListener(mAROverlayListener);
        dialog.setOnKeyListener(mAROverlayListener);

        if (null != mPresetType) {
            mPresetType.config(dialog);
        }
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
