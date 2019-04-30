package sergesv.rvs.util;

import sergesv.rvs.exception.EntityNotFoundException;

import java.util.function.Supplier;

public final class ValidationUtil {
    public static Supplier<EntityNotFoundException> restaurantNotFoundSupplier(long menuEntryId) {
        return () -> new EntityNotFoundException(
                String.format("Not found Restaurant entity with id = %d", menuEntryId));
    }

    public static Supplier<EntityNotFoundException> menuEntryNotFoundSupplier(long menuEntryId,
                                                                              long restaurantId) {
        return () -> new EntityNotFoundException(
                String.format("Not found MenuEntry entity with id = %d and restaurantId = %d",
                        menuEntryId, restaurantId));
    }

    public static Supplier<EntityNotFoundException> userNotFoundSupplier(long userId) {
        return () -> new EntityNotFoundException(
                String.format("Not found User with id = %d", userId));
    }

    public static void checkExists(boolean exists,
                                   Supplier<? extends RuntimeException> supplier) {
        if (!exists) {
            throw supplier.get();
        }
    }

    private ValidationUtil() {
    }
}
