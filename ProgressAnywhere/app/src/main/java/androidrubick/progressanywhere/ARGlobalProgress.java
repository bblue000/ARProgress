package androidrubick.progressanywhere;

import android.content.Context;

/**
 * 全局使用的加载过程进度条
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/1/27.
 */
public class ARGlobalProgress {

    private ARGlobalProgress() { /* no instance needed */ }

    /**
     *
     * @param context 同时作为Token
     */
    public static void show(Context context) {
        showByToken(context, context);
    }

    /**
     *
     * @param context 同时作为Token
     *
     * @see androidrubick.progressanywhere.ARSimpleProgressListener ARSimpleProgressListener
     */
    public static void show(Context context, ARProgressListener listener) {
        showByToken(context, context);
    }

    /**
     *
     * @param context 作为Token
     */
    public static void hide(Context context) {
        hideByToken(context, context);
    }

    public static void showByToken(Context context, Object token) {

    }

    public static void hideByToken(Context context, Object token) {

    }

    public static void showAsOverlay(Context context) {

    }
}
