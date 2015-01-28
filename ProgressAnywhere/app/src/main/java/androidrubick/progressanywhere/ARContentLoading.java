package androidrubick.progressanywhere;

import android.content.DialogInterface;
import android.view.View;

import java.util.WeakHashMap;

/**
 * 内容加载——如ListView、WebView等加载界面数据时，显示的loading画面，可以覆盖在指定View或container上
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/1/27.
 */
public class ARContentLoading {

    private ARContentLoading() { /* no instance needed */ }

    private static final WeakHashMap<View, ARProgress> sInstanceMap = new WeakHashMap<View, ARProgress>(5);
    /**
     *
     * 使用默认的进度视图（系统决定，例如转菊花之类）
     *
     * @param overLayFromView 以该View的边界为准，在其内部区域，显示加载进度，该进度视图覆盖在该View之上
     */
    public static void show(View overLayFromView) {
        show(overLayFromView, 0);
    }

    /**
     *
     * 允许client自定义自身的加载进度视图
     *
     * @param overLayFromView 以该View的边界为准，在其内部区域，显示加载进度，该进度视图覆盖在该View之上
     * @param layoutResId client定义的进度布局资源ID
     * @return {@link androidrubick.progressanywhere.ARProgress}对象，返回它，让client能够自定义隐藏
     */
    public static void show(final View overLayFromView, int layoutResId) {
        if (null == overLayFromView) {
            throw new NullPointerException("overLayFromView is null");
        }
        ARProgress progress = ARProgressBuilder.from(overLayFromView.getContext())
                .view(layoutResId)
                .listeners(new ARSimpleProgressListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        overLayFromView.setTag(R.id.contentTag__PA, null);
                    }
                })
                .bindBoundView(overLayFromView).build();
        overLayFromView.setTag(R.id.contentTag__PA, progress);
        progress.show();
    }

    /**
     *
     * 允许client自定义自身的加载进度视图
     *
     * @param overLayFromView 以该View的边界为准，在其内部区域，显示加载进度，该进度视图覆盖在该View之上
     * @param loadingView client定义的进度布局
     * @return {@link androidrubick.progressanywhere.ARProgress}对象，返回它，让client能够自定义隐藏
     */
    public static ARProgress show(View overLayFromView, View loadingView) {
        if (null == overLayFromView) {
            throw new NullPointerException("overLayFromView is null");
        }
        if (null == loadingView) {
            throw new NullPointerException("loadingView is null");
        }
        return ARProgressBuilder.from(overLayFromView.getContext())
                .view(loadingView)
                .bindBoundView(overLayFromView).build();
    }

    /**
     * 如果之前在overLayFromView上显示了加载进度，调用该方法将隐藏进度
     */
    public static void hide(View overLayFromView) {
        if (null == overLayFromView) {
            throw new NullPointerException("overLayFromView is null");
        }
        Object obj = overLayFromView.getTag(R.id.contentTag__PA);
        if (!(obj instanceof ARProgress)) {
            return ;
        }
        ARProgress progress = (ARProgress) obj;
        progress.dismiss();
        overLayFromView.setTag(R.id.contentTag__PA, null);
    }

}
