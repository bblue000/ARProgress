package androidrubick.overlayanywhere;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/1/28.
 */
/*package*/ class AROverlayWrapper implements AROverlay {

    private static final ReferenceQueue<AROverlay> sRefQueue = new ReferenceQueue<AROverlay>();
    private AROverlayBuilder mBaseBuilder;
    private WeakReference<AROverlay> mBase;
    public AROverlayWrapper(AROverlayBuilder builder) {
        sRefQueue.poll();
        mBaseBuilder = builder;
    }

    @Override
    public void show() {
        sRefQueue.poll();
        AROverlay wrapped = getBaseOverlayOrBuild();
        wrapped.show();
    }

    @Override
    public void hide() {
        sRefQueue.poll();
        AROverlay wrapped = getBaseOverlayOrBuild();
        wrapped.hide();
    }

    private AROverlay getBaseOverlayOrBuild() {
        AROverlay wrapped = getBaseOverlay();
        if (null == wrapped) {
            wrapped = mBaseBuilder.buildInner();
            mBase = new WeakReference<AROverlay>(wrapped);
        }
        return wrapped;
    }

    private AROverlay getBaseOverlay() {
        AROverlay wrapped = null;
        final WeakReference<AROverlay> ref = mBase;
        if (null != ref) {
            wrapped = ref.get();
        }
        return wrapped;
    }
}
