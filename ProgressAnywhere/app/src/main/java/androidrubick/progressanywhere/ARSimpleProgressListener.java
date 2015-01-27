package androidrubick.progressanywhere;

import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * 默认实现，方便直接重写想要的方法
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/1/27.
 */
public class ARSimpleProgressListener implements ARProgressListener {
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
