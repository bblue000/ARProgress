package androidrubick.progressanywhere;

import android.content.DialogInterface;

/**
 * 综合的监听器
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/1/27.
 */
public interface ARProgressListener extends DialogInterface.OnShowListener, DialogInterface.OnDismissListener,
DialogInterface.OnCancelListener, DialogInterface.OnKeyListener {
}
