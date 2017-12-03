package net.divlight.retainer;

import android.arch.lifecycle.LifecycleOwner;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * An utility class that helps retaining objects in an Activity / Fragment during configuration
 * changes.
 * <p>
 * Call <code>Retainer.bind(this)</code> in your activity or fragment. The fields annotated with
 * {@link net.divlight.retainer.annotation.Retain} will be automatically preserved by Retainer.
 */
public class Retainer {
    private Retainer() {
    }

    /**
     * To preserve {@link net.divlight.retainer.annotation.Retain} annotated fields, Bind a target
     * to a container object(a auto-generated fragment that is called setRetainInstance(true)). If
     * the fields is already preserved by a container object, it will be restored.
     * <p>
     * This method is intended to be called in {@link FragmentActivity#onCreate(Bundle)}.
     *
     * @param target Target activity for retaining objects.
     */
    @UiThread
    public static <T extends FragmentActivity> void bind(T target) {
        bind(target, target.getSupportFragmentManager(), RetainFragment.DEFAULT_TAG);
    }

    /**
     * To preserve {@link net.divlight.retainer.annotation.Retain} annotated fields, Bind a target
     * to a container object(a auto-generated fragment that is called setRetainInstance(true)). If
     * the fields is already preserved by a container object, it will be restored.
     * <p>
     * This method is intended to be called in {@link Fragment#onCreate(Bundle)}.
     *
     * @param target Target fragment for retaining objects.
     */
    @UiThread
    public static <T extends Fragment> void bind(T target) {
        bind(target, target.getChildFragmentManager(), RetainFragment.DEFAULT_TAG);
    }

    /**
     * To preserve {@link net.divlight.retainer.annotation.Retain} annotated fields, Bind a target
     * to a container object(a auto-generated fragment that is called setRetainInstance(true)). If
     * the fields is already preserved by a container object, it will be restored.
     * <p>
     * This method is intended to be called in {@link FragmentActivity#onCreate(Bundle)} or
     * {@link Fragment#onCreate(Bundle)}.
     *
     * @param target          Target activity or fragment for retaining objects.
     * @param fragmentManager FragmentManager used for committing the container fragment.
     * @param tag             The tag name for the container fragment.
     */
    @SuppressWarnings("unchecked")
    @UiThread
    public static <T extends LifecycleOwner> void bind(T target, FragmentManager fragmentManager, String tag) {
        new RetainFragmentBinder(target, fragmentManager, tag).bindFragment();
    }
}
