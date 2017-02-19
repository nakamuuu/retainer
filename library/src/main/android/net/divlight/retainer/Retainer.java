package net.divlight.retainer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

public class Retainer {
    private static final String TAG = Retainer.class.getSimpleName();

    private Retainer() {
    }

    public static <T extends FragmentActivity> void onCreate(T target) {
        onCreate(target.getSupportFragmentManager(), target);
    }

    public static <T extends Fragment> void onCreate(T target) {
        onCreate(target.getChildFragmentManager(), target);
    }

    @SuppressWarnings("unchecked")
    private static <T> void onCreate(FragmentManager fragmentManager, T target) {
        final String tag = RetainFragment.getTag(target.getClass());
        final Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            final Object<T> object = ((RetainFragment<T>) fragment).getObject();
            if (object != null) {
                object.restore(target);
            }
        } else {
            fragmentManager.beginTransaction().add(new RetainFragment<T>(), tag).commit();
        }
    }

    public static <T extends FragmentActivity> void onDestroy(T target) {
        onDestroy(target.getSupportFragmentManager(), target);
    }

    public static <T extends Fragment> void onDestroy(T target) {
        onDestroy(target.getChildFragmentManager(), target);
    }

    @SuppressWarnings("unchecked")
    private static <T> void onDestroy(FragmentManager fragmentManager, T target) {
        final String tag = RetainFragment.getTag(target.getClass());
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
