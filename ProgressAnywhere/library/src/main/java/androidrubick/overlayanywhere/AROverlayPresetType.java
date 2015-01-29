package androidrubick.overlayanywhere;

/**
 * 预置的一些type
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/1/29.
 */
public enum AROverlayPresetType {

    /**
     * 类似Dialog的浮动窗口，可以取消
     */
    Floating {
        @Override
        void config(AROverlayDialog dialog) {
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
        }
    },

    /**
     * 作为某个父View的子控件
     */
    AsSubView {
        @Override
        void config(AROverlayDialog dialog) {
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        }
    },


    ;

    /*package*/ abstract void config(AROverlayDialog dialog) ;
}
