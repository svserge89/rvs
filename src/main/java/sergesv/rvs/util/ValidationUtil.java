package sergesv.rvs.util;

import sergesv.rvs.exception.EntityConflictException;
import sergesv.rvs.exception.EntityNotFoundException;

import java.util.function.Supplier;

import static sergesv.rvs.util.DateTimeUtil.MAX_CHANGE_TIME;

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

    public static Supplier<EntityConflictException> voteAgainSupplier() {
        return () -> new EntityConflictException(
                String.format("Can not vote again after %s", MAX_CHANGE_TIME));
    }

    public static void checkException(boolean exists,
                                      Supplier<? extends RuntimeException> supplier) {
        if (!exists) {
            throw supplier.get();
        }
    }

    private ValidationUtil() {
    }
}
