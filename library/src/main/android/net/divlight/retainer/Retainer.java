package net.divlight.retainer;

import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

/**
 * An utility class that helps retaining objects in an Activity / Fragment during configuration
 * changes.
 * <p>
 * Call <code>Retainer.onCreate(this)</code> and <code>Retainer.onDestroy(this)</code> in your
 * activity or fragment. {@link net.divlight.retainer.annotation.Retain} annotated fields will be
 * automatically preserved by Retainer.
 */
public class Retainer {
    private static final String TAG = Retainer.class.getSimpleName();

    private Retainer() {
    }

    /**
     * To preserve {@link net.divlight.retainer.annotation.Retain} annotated fields, Bind a target
     * to a container object(a auto-generated fragment that is called setRetainInstance(true)).
     * <p>
     * This method is intended to be called in {@link FragmentActivity#onCreate(Bundle)}.
     *
     * @param target Target activity for retaining objects.
     */
    @UiThread
    public static <T extends FragmentActivity> void onCreate(T target) {
        onCreate(target, target.getSupportFragmentManager(), RetainFragment.DEFAULT_TAG);
    }

    /**
     * To preserve {@link net.divlight.retainer.annotation.Retain} annotated fields, Bind a target
     * to a container object(a auto-generated fragment that is called setRetainInstance(true)).
     * <p>
     * This method is intended to be called in {@link Fragment#onCreate(Bundle)}.
     *
     * @param target Target fragment for retaining objects.
     */
    @UiThread
    public static <T extends Fragment> void onCreate(T target) {
        onCreate(target, target.getChildFragmentManager(), RetainFragment.DEFAULT_TAG);
    }

    /**
     * To preserve {@link net.divlight.retainer.annotation.Retain} annotated fields, Bind a target
     * to a container object(a auto-generated fragment that is called setRetainInstance(true)).
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
    public static <T> void onCreate(T target, FragmentManager fragmentManager, String tag) {
        final Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            final Object<T> object = ((RetainFragment<T>) fragment).getObject();
            if (object != null) {
                object.restore(target);
            }
        } else {
            fragmentManager.beginTransaction()
                    .add(new RetainFragment<T>(), tag)
                    .commitAllowingStateLoss();
        }
    }

    /**
     * Save {@link net.divlight.retainer.annotation.Retain} annotated fields to a container object.
     * <p>
     * This method is intended to be called in {@link FragmentActivity#onDestroy()}.
     *
     * @param target Target activity for retaining objects.
     */
    @UiThread
    public static <T extends FragmentActivity> void onDestroy(T target) {
        onDestroy(target, target.getSupportFragmentManager(), RetainFragment.DEFAULT_TAG);
    }

    /**
     * Save {@link net.divlight.retainer.annotation.Retain} annotated fields to a container object.
     * <p>
     * This method is intended to be called in {@link Fragment#onDestroy()}.
     *
     * @param target Target fragment for retaining objects.
     */
    @UiThread
    public static <T extends Fragment> void onDestroy(T target) {
        onDestroy(target, target.getChildFragmentManager(), RetainFragment.DEFAULT_TAG);
    }

    /**
     * Save {@link net.divlight.retainer.annotation.Retain} annotated fields to a container object.
     * <p>
     * This method is intended to be called in {@link FragmentActivity#onDestroy()} or
     * {@link Fragment#onDestroy()}.
     *
     * @param target          Target activity or fragment for retaining objects.
     * @param fragmentManager FragmentManager used for finding the container fragment.
     * @param tag             The tag name for the container fragment.
     */
    @SuppressWarnings("unchecked")
    @UiThread
    public static <T> void onDestroy(T target, FragmentManager fragmentManager, String tag) {
        final Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            Log.w(TAG, "Could not find the fragment for preserving instance states."
                    + " Make sure not to forget calling Retainer#onCreate.");
            return;
        }

        try {
            final String packageName = target.getClass().getPackage().getName();
            final String className = getObjectClassName(target.getClass());
            final Class<?> clazz = Class.forName(packageName + "." + className);
            final Object<T> object = (Object<T>) clazz.newInstance();
            object.save(target);
            ((RetainFragment<T>) fragment).setObject(object);
        } catch (Exception e) {
            Log.w(TAG, "Failed to create a object instance for preserving instance states.");
        }
    }

    private static String getObjectClassName(Class<?> targetClass) {
        final String targetClassName = targetClass.getSimpleName();
        final ObjectClassNameBuilder builder = new ObjectClassNameBuilder(targetClassName);
        Class<?> clazz = targetClass.getEnclosingClass();
        while (clazz != null) {
            builder.addEnclosingClassSimpleName(clazz.getSimpleName());
            clazz = clazz.getEnclosingClass();
        }
        return builder.build();
    }
}
