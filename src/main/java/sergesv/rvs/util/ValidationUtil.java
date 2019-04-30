package sergesv.rvs.util;

import sergesv.rvs.exception.EntityNotFoundException;

import java.util.function.Supplier;

public final class ValidationUtil {
    public static Supplier<EntityNotFoundException> getNotFoundSupplier(long id) {
        return () -> new EntityNotFoundException(
                String.format("Not found entity with id '%d'", id));
    }

    private ValidationUtil() {
    }
}
