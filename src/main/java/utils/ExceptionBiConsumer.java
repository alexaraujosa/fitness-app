package utils;

import java.util.function.Function;

/**
 * Represents a function that accepts two arguments and either succeeds or throws an exception.
 * This is based on the {@link java.util.function.BiConsumer} Functional Interface.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(Object, Object)}.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <E> the type of the exceptions that might be thrown
 *
 * @see Function
 * @since 1.8
 */
@FunctionalInterface
public interface ExceptionBiConsumer<T, U, E extends Throwable> {
    void accept(T t, U u) throws E;
}
