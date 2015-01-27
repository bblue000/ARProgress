package androidrubick.progressanywhere;

import android.view.View;

/**
 * 内容加载——如ListView、WebView等加载界面数据时，显示的覆盖
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/1/27.
 */
public class ARContentLoading {

    private ARContentLoading() { /* no instance needed */ }

    /**
     *
     * 使用默认的进度视图（系统决定，例如转菊花之类）
     *
     * @param overLayFromView 以该View的边界为准，在其内部区域，显示加载进度，该进度视图覆盖在该View之上
     *
     * @return {@link androidrubick.progressanywhere.ARProgress}对象，返回它，让client能够自定义隐藏
     */
    public static ARProgress of(View overLayFromView) {
        return of(overLayFromView, 0);
    }

    /**
     *
     * 允许client自定义自身的加载进度视图
     *
     * @param overLayFromView 以该View的边界为准，在其内部区域，显示加载进度，该进度视图覆盖在该View之上
     * @param layoutResId client定义的进度布局资源ID
     * @return {@link androidrubick.progressanywhere.ARProgress}对象，返回它，让client能够自定义隐藏
     */
    public static ARProgress of(View overLayFromView, int layoutResId) {
        if (null == overLayFromView) {
            throw new NullPointerException("overLayFromView is null");
        }
        return null;
    }

    /**
     *
     * 允许client自定义自身的加载进度视图
     *
     * @param overLayFromView 以该View的边界为准，在其内部区域，显示加载进度，该进度视图覆盖在该View之上
     * @param loadingView client定义的进度布局
     * @return {@link androidrubick.progressanywhere.ARProgress}对象，返回它，让client能够自定义隐藏
     */
    public static ARProgress of(View overLayFromView, View loadingView) {
        if (null == overLayFromView) {
            throw new NullPointerException("overLayFromView is null");
        }
        if (null == loadingView) {
            throw new NullPointerException("loadingView is null");
        }
        return null;
    }

}
