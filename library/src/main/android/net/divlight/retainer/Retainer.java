package net.divlight.retainer;

import android.os.Bundle;

import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;

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
     * to a container object (a auto-generated fragment that is called setRetainInstance(true)). If
     * the fields is already preserved by a container object, it will be restored.
     * <p>
     * This method is intended to be called in {@link FragmentActivity#onCreate(Bundle)}.
     *
     * @param <T>    The class of target activity.
     * @param target Target activity for retaining objects.
     */
    @UiThread
    public static <T extends FragmentActivity> void bind(T target) {
        bind(target, target.getSupportFragmentManager(), RetainFragment.DEFAULT_TAG);
    }

    /**
     * To preserve {@link net.divlight.retainer.annotation.Retain} annotated fields, Bind a target
     * to a container object (a auto-generated fragment that is called setRetainInstance(true)). If
     * the fields is already preserved by a container object, it will be restored.
     * <p>
     * This method is intended to be called in {@link Fragment#onCreate(Bundle)}.
     *
     * @param <T>    The class of target fragment.
     * @param target Target fragment for retaining objects.
     */
    @UiThread
    public static <T extends Fragment> void bind(T target) {
        bind(target, target.getChildFragmentManager(), RetainFragment.DEFAULT_TAG);
    }

    /**
     * To preserve {@link net.divlight.retainer.annotation.Retain} annotated fields, Bind a target
     * to a container object (a auto-generated fragment that is called setRetainInstance(true)). If
     * the fields is already preserved by a container object, it will be restored.
     * <p>
     * This method is intended to be called in {@link FragmentActivity#onCreate(Bundle)} or
     * {@link Fragment#onCreate(Bundle)}.
     *
     * @param <T>             The class of target activity or fragment.
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
