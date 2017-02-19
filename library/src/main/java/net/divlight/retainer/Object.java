package net.divlight.retainer;

@SuppressWarnings("unused")
public interface Object<T> {
    void save(T target);

    void restore(T target);
}
