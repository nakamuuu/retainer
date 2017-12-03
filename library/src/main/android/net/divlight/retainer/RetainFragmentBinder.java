package net.divlight.retainer;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

@SuppressWarnings("unchecked")
class RetainFragmentBinder<T extends LifecycleOwner> implements LifecycleObserver {
    private static final String TAG = RetainFragmentBinder.class.getSimpleName();

    private final T target;
    private final FragmentManager fragmentManager;
    private final String tag;

    RetainFragmentBinder(T target, FragmentManager fragmentManager, String tag) {
        this.target = target;
        this.fragmentManager = fragmentManager;
        this.tag = tag;
    }

    void bindFragment() {
        target.getLifecycle().addObserver(this);

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

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        final Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            Log.w(TAG, "Could not find the fragment for preserving instance states.");
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
