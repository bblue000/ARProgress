package androidrubick.overlayanywhere;

import android.content.DialogInterface;
import android.view.KeyEvent;

/**
 * 综合的监听器，包含Overlay的一些回调的缺省实现，方便直接重写想要的方法
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/1/27.
 */
public class ARSimpleOverlayListener implements AROverlayListener {

    @Override
    public void onCancel(DialogInterface dialog) {

    }

    @Override
    public void onDismiss(DialogInterface dialog) {

    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onShow(DialogInterface dialog) {

    }

}
