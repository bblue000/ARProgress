package androidrubick.overlayanywhere;

import android.graphics.Rect;
import android.view.View;

/**
 * 遮盖视图对象接口，本包中提供的对外API，
 * {@link androidrubick.overlayanywhere.AROverlayBuilder}创建后将会提供一个{@code AROverlay} 对象。
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/1/28.
 */
public interface AROverlay {

    /**
     * findViewById
     */
    public View findViewById(int id) ;

    /**
     * 显示遮盖视图，根据最近一次调用的其他{@code show} 方法再次显示遮盖视图
     */
    public void show() ;

    /**
     * 在指定区域显示遮盖视图
     */
    public void show(int left, int top, int right, int bottom) ;

    /**
     * 在指定区域显示遮盖视图
     */
    public void show(Rect rect) ;

    /**
     * 在指定View控件上显示遮盖视图
     */
    public void show(View view) ;

    /**
     * 隐藏遮盖视图（类似setView(GONE)）
     */
    public void hide();

    /**
     * 从Window中移除遮盖视图
     */
    public void dismiss();

}
