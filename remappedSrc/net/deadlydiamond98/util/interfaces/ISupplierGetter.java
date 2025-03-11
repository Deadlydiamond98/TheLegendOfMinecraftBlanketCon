package net.deadlydiamond98.util.interfaces;

import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public interface ISupplierGetter {
    default <T> @Nullable T getObj(Supplier<T> supplier) {
        return supplier != null ? supplier.get() : null;
    }
}
