package androidrubick.progressanywhere;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.lang.ref.WeakReference;

/**
 * 用于显示进度框，继承自{@link android.app.Dialog}
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/1/26.
 */
public class ARProgress extends Dialog {

    protected final int[] mShowLocationOnScreen = new int[2];
    protected final Rect mShowRect = new Rect();
    protected final RectF mShowRectF = new RectF();
    protected RelativeLayout mRoot;
    protected RelativeLayout mContent;
    protected WeakReference<View> mBindBoundView;

    protected ARProgress(Context context) {
        this(context, 0);
    }

    protected ARProgress(Context context, int defStyleRes) {
        super(context, defStyleRes);

        super.setContentView(R.layout.paprogress_root__ar);
        mRoot = (RelativeLayout) super.findViewById(R.id.paprogress_root__ar_container);
        mContent = (RelativeLayout) super.findViewById(R.id.paprogress_root__ar_content);

        TypedArray a = getContext().obtainStyledAttributes(R.styleable.ARPAProgress);
        Drawable contentBg = a.getDrawable(R.styleable.ARPAProgress_contentBackground__PA);
        int layoutId = a.getResourceId(R.styleable.ARPAProgress_contentLayout__PA, 0);
        a.recycle();

        setContentBackground(contentBg);
        if (layoutId > 0) {
            setContentView(layoutId);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView(layoutResID, Gravity.CENTER);
    }

    /**
     * @param gravity 内容相对父控件位置，请使用绝对的位置
     */
    public void setContentView(int layoutResID, int gravity) {
        mContent.removeAllViews();
        getLayoutInflater().inflate(layoutResID, mContent);
        applyByGravity(mContent.getChildAt(0), gravity);
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, Gravity.CENTER);
    }

    /**
     * @param gravity 内容相对父控件位置，请使用绝对的位置
     */
    public void setContentView(View view, int gravity) {
        if (null != view.getParent()) {
            throw new IllegalArgumentException("view has a parent already");
        }
        mContent.removeAllViews();
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (null == params) {
            params = generateDefaultLayoutParams();
        }
        mContent.addView(view, params);
        applyByGravity(view, gravity);
    }

    /**
     * @param gravity 请使用绝对的位置
     */
    protected void applyByGravity(View child, int gravity) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) child.getLayoutParams();
        int hGravity = gravity & Gravity.HORIZONTAL_GRAVITY_MASK;
        int vGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;
        switch (hGravity) {
            case Gravity.LEFT:
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                break;
            case Gravity.RIGHT:
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                break;
            case Gravity.CENTER_HORIZONTAL:
            default:
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                break;
        }
        switch (vGravity) {
            case Gravity.TOP:
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                break;
            case Gravity.BOTTOM:
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                break;
            case Gravity.CENTER_VERTICAL:
            default:
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                break;
        }
        // update
        child.setLayoutParams(params);
    }

    /**
     * Returns a set of default layout parameters. These parameters are requested
     * when the View passed to {@link #setContentView(View)} has no layout parameters
     * already set. If null is returned, an exception is thrown from addView.
     *
     * @return a set of default layout parameters or null
     */
    protected RelativeLayout.LayoutParams generateDefaultLayoutParams() {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        return lp;
    }

    /**
     * 设置根View背景
     *
     * @param drawable 背景drawable
     */
    public void setBackground(Drawable drawable) {
        if (null == drawable) {
            drawable = new BitmapDrawable(getContext().getResources());
        }
        mRoot.setBackgroundDrawable(drawable);
    }

    /**
     * 设置根View背景
     *
     * @param resId 背景drawable res ID
     */
    public void setBackgroundResource(int resId) {
        if (resId <= 0) {
            Drawable drawable = new BitmapDrawable(getContext().getResources());
            setContentBackground(drawable);
            return ;
        }
        mRoot.setBackgroundResource(resId);
    }

    /**
     * 设置根View背景
     *
     * @param color 背景颜色
     */
    public void setBackgroundColor(int color) {
        mRoot.setBackgroundColor(color);
    }

    /**
     * 设置内容背景
     *
     * @param drawable 背景drawable
     */
    public void setContentBackground(Drawable drawable) {
        if (null == drawable) {
            drawable = new BitmapDrawable(getContext().getResources());
        }
        mContent.setBackgroundDrawable(drawable);
    }

    /**
     * 设置内容背景
     *
     * @param resId 背景drawable res ID
     */
    public void setContentBackgroundResource(int resId) {
        if (resId <= 0) {
            Drawable drawable = new BitmapDrawable(getContext().getResources());
            setContentBackground(drawable);
            return ;
        }
        mContent.setBackgroundResource(resId);
    }

    /**
     * 设置内容背景
     *
     * @param color 背景颜色
     */
    public void setContentBackgroundColor(int color) {
        mContent.setBackgroundColor(color);
    }

    @Override
    public View findViewById(int id) {
        return mContent.findViewById(id);
    }

    /**
     * 绑定目标View的显示区域（相对于整个屏幕）
     *
     * @param view 绑定目标View的显示区域（相对于整个屏幕）
     */
    public void setBindBoundView(View view) {
        if (null == view) {
            mBindBoundView = null;
        } else {
            mBindBoundView = new WeakReference<View>(view);
        }
        updateBindBound(view);
    }

    public View getBindBoundView() {
        return null == mBindBoundView ? null : mBindBoundView.get();
    }

    /**
     * 绑定目标View的显示区域（相对于整个屏幕）
     *
     * @param target 目标View
     */
    protected void updateBindBound(View target) {
        if (null == target) {
            initBindBound();
        } else {
//            target.getLocationOnScreen(mShowLocationOnScreen);
            target.getLocationInWindow(mShowLocationOnScreen);
            target.getGlobalVisibleRect(mShowRect);
            copyToRectF();
        }
    }

    protected void initBindBound() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        mShowRect.set(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        copyToRectF();
    }

    protected void copyToRectF() {
        mShowRectF.set(mShowRect);
    }

    protected void updateBindBoundToContent() {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mContent.getLayoutParams();
        lp.leftMargin = mShowLocationOnScreen[0];//mShowRect.left;
        lp.topMargin = mShowLocationOnScreen[1];//mShowRect.top;
        lp.width = mShowRect.width();
        lp.height = mShowRect.height();
        mContent.setLayoutParams(lp);
    }

    public void show() {
        if (isShowing()) {
            super.show();
            return ;
        }

        updateBindBound(getBindBoundView());
        updateBindBoundToContent();
        super.show();
    }

}
