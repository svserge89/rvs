package sergesv.rvs.util;

import org.springframework.dao.DataIntegrityViolationException;
import sergesv.rvs.exception.EntityConflictException;
import sergesv.rvs.exception.EntityNotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

    public static Supplier<EntityConflictException> userAlreadyExistsSupplier() {
        return () -> new EntityConflictException(
                "User with the same nickname or email is already exists");
    }

    public static Supplier<EntityConflictException> restaurantAlreadyExistsSupplier() {
        return () -> new EntityConflictException("Restaurant with the same name is already exists");
    }

    public static void checkException(boolean exists,
                                      Supplier<? extends RuntimeException> supplier) {
        if (!exists) {
            throw supplier.get();
        }
    }

    public static <T> T checkException(Supplier<T> userSupplier,
                                       Supplier<EntityConflictException> conflictSupplier) {
        try {
            return userSupplier.get();
        } catch (DataIntegrityViolationException exception) {
            var conflictException = conflictSupplier.get();

            conflictException.initCause(exception);
            throw conflictException;
        }
    }

    public static String getConstraintViolationsMessage(ConstraintViolationException exception) {
        return exception.getConstraintViolations().stream()
                .map(constraintViolation -> String.format("Field '%s': '%s' - %s",
                        constraintViolation.getPropertyPath(),
                        constraintViolation.getInvalidValue(), constraintViolation.getMessage()))
                .collect(Collectors.joining(". "));
    }

    private ValidationUtil() {
    }
}
