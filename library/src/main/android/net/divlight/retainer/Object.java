package net.divlight.retainer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public abstract class Object<T> extends Fragment {
    public static String getTag(Class<?> targetClass) {
        return targetClass.getName().toUpperCase().replaceAll("\\.", "_") + "_OBJECT";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public abstract void save(T target);

    public abstract void restore(T target);
}
