package net.divlight.retainer;

import android.os.Bundle;

import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RetainFragment<T> extends Fragment {
    public static final String DEFAULT_TAG = RetainFragment.class.getName()
            .toUpperCase(Locale.ENGLISH)
            .replaceAll("\\.", "_");

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
