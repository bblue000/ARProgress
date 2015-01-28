package androidrubick.overlayanywhere;

/**
 * 遮盖视图对象接口，本包中提供的对外API，
 * {@link androidrubick.overlayanywhere.AROverlayBuilder}创建后将会提供一个{@code AROverlay} 对象。
 *
 * <p/>
 *
 *
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/1/28.
 */
public interface AROverlay {

    /**
     * 显示遮盖视图
     */
    public void show() ;

    /**
     * 隐藏遮盖视图
     */
    public void hide();

}
