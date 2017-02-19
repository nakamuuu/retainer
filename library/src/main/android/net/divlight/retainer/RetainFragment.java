package net.divlight.retainer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public class RetainFragment<T> extends Fragment {
    public static String getTag(Class<?> targetClass) {
        return targetClass.getName().toUpperCase().replaceAll("\\.", "_") + "_RETAINER";
    }

    @Nullable
    private Object<T> object;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    public Object<T> getObject() {
        return object;
    }

    public void setObject(@Nullable Object<T> object) {
        this.object = object;
    }
}
