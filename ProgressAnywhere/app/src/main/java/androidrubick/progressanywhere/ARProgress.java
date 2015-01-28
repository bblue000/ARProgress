package androidrubick.progressanywhere;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

    protected final Rect mRootRect = new Rect();
    protected final Rect mShowRect = new Rect();
    protected RelativeLayout mRoot;
    protected RelativeLayout mContent;
    protected boolean mCloseOnTouchOutside;
    protected WeakReference<View> mBindBoundView;
    protected ARProgressRootImpl.Callback mCallback = new ARProgressRootImpl.Callback() {
        @Override
        public boolean onMeasured(int widthMeasureSpec, int heightMeasureSpec) {
            updateBindBoundToContent();
            return true;
        }

        @Override
        public boolean onLayouted(boolean changed, int l, int t, int r, int b) {
            relayout(changed, l, t, r, b);
            return true;
        }
    };

    protected ARProgress(Context context) {
        this(context, 0);
    }

    protected ARProgress(Context context, int defStyleRes) {
        super(context, defStyleRes);

        ARProgressRootImpl root = new ARProgressRootImpl(getContext(), null, defStyleRes);
        root.setCallback(mCallback);
        mRoot = root;
        super.setContentView(mRoot, new WindowManager.LayoutParams(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT));

        // ignore float window
        getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);

        getLayoutInflater().inflate(R.layout.paprogress_content__ar, mRoot);
        mContent = (RelativeLayout) super.findViewById(R.id.paprogress_root__ar_content);

        TypedArray a = getContext().obtainStyledAttributes(null, R.styleable.ARPAProgress, 0, defStyleRes);
        Drawable contentBg = a.getDrawable(R.styleable.ARPAProgress_contentBackground__PA);
        int layoutId = a.getResourceId(R.styleable.ARPAProgress_contentLayout__PA, 0);
        int animStyleId = a.getResourceId(R.styleable.ARPAProgress_android_windowAnimationStyle, 0);
        boolean closeOnTouchOutside = a.getBoolean(R.styleable.ARPAProgress_android_windowCloseOnTouchOutside, true);
        a.recycle();

        setContentBackground(contentBg);
        if (layoutId > 0) {
            setContentView(layoutId);
        }
        getWindow().getAttributes().windowAnimations = animStyleId;
        setCanceledOnTouchOutside(closeOnTouchOutside);
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
    }

    public View getBindBoundView() {
        return null == mBindBoundView ? null : mBindBoundView.get();
    }

    protected void initBindBound() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        mRootRect.set(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        mShowRect.set(mRootRect);
    }

    protected void updateBindBoundToContent() {
        // 重新绑定目标View的显示区域（相对于整个屏幕）
        View target = getBindBoundView();
        if (null == target) {
            initBindBound();
        } else {
            target.getGlobalVisibleRect(mShowRect);
        }

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mContent.getLayoutParams();
        lp.width = mShowRect.width();
        lp.height = mShowRect.height();
    }

    protected void relayout(boolean changed, int l, int t, int r, int b) {
        int offsetX = 0;
        int offsetY = 0;
        if (mRoot.getGlobalVisibleRect(mRootRect)) {
            offsetX = mRootRect.left;
            offsetY = mRootRect.top;
        }
        int left = Math.max(0, mShowRect.left - offsetX);
        int top = Math.max(0, mShowRect.top - offsetY);
        mContent.layout(left, top, left + mShowRect.width(), top + mShowRect.height());
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        mCloseOnTouchOutside = cancel;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        if (mCloseOnTouchOutside && isShowing() && !mShowRect.contains(x, y)) {
            cancel();
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void show() {
        if (isShowing()) {
            super.show();
            return ;
        }
        super.show();
    }

}
