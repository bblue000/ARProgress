package androidrubick.overlayanywhere;

import android.content.DialogInterface;

/**
 * 综合的监听器，包含Overlay的一些回调
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/1/27.
 */
public interface AROverlayListener extends DialogInterface.OnShowListener, DialogInterface.OnDismissListener,
DialogInterface.OnCancelListener, DialogInterface.OnKeyListener {
}
