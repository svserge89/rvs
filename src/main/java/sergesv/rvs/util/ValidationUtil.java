package sergesv.rvs.util;

import org.springframework.dao.DataIntegrityViolationException;
import sergesv.rvs.exception.BadRequestException;
import sergesv.rvs.exception.EntityConflictException;
import sergesv.rvs.model.EntityWithId;
import sergesv.rvs.model.MenuEntry;
import sergesv.rvs.model.Restaurant;
import sergesv.rvs.model.User;
import sergesv.rvs.web.to.UserTo;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.time.LocalTime;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class ValidationUtil {
    public static final String CHECK_EMAIL_REGEXP =
            "^[A-Za-z0-9._%\\-+!#$&/=?^|~]+@[A-Za-z0-9.-]+[.][A-Za-z]+$";
    public static final int EMAIL_SIZE = 100;
    public static final int NICK_NAME_SIZE = 100;
    public static final int FIRST_NAME_SIZE = 100;
    public static final int LAST_NAME_SIZE = 100;
    public static final int RESTAURANT_NAME_SIZE = 100;
    public static final int MENU_ENTRY_NAME_SIZE = 100;
    public static final int ENCRYPTED_PASSWORD_SIZE = 60;

    public static <T extends EntityWithId> Supplier<EntityNotFoundException>
    entityNotFoundSupplier(Class<T> entityClass, long id) {
        return () -> new javax.persistence.EntityNotFoundException(
                String.format("Unable to find %s with id %d", entityClass.getCanonicalName(), id));
    }

    public static Supplier<EntityNotFoundException> entityNotFoundSupplier(long menuEntryId,
                                                                           long restaurantId) {
        return () -> new EntityNotFoundException(
                String.format("Unable to find %s with id %d and restaurantId %d",
                        MenuEntry.class.getCanonicalName(), menuEntryId, restaurantId));
    }

    public static Supplier<EntityConflictException> voteAgainSupplier(LocalTime maxVoteTime) {
        return () -> new EntityConflictException(
                String.format("Can not change vote again after %s", maxVoteTime));
    }

    public static Supplier<EntityConflictException> deleteCurrentUserSupplier() {
        return () -> new EntityConflictException("Can not delete current authorized user");
    }

    public static void checkException(boolean condition,
                                      Supplier<? extends RuntimeException> supplier) {
        if (!condition) {
            throw supplier.get();
        }
    }

    public static <T> T checkExistsException(Supplier<T> supplier, Class<T> entityClass) {
        try {
            return supplier.get();
        } catch (DataIntegrityViolationException exception) {
            String message;

            if (entityClass == User.class) {
                message = "%s with the same nickname or email is already exists";
            } else if (entityClass == Restaurant.class) {
                message = "%s with the same name is already exists";
            } else if (entityClass == MenuEntry.class) {
                message = "%s with the same name, date, and restaurantId is already exists";
            } else {
                message = "%s is already exists";
            }

            throw new EntityConflictException(
                    String.format(message, entityClass.getCanonicalName()), exception);
        }
    }

    public static String checkPassword(UserTo userTo) {
        return Optional.ofNullable(userTo.getPassword())
                .orElseThrow(() -> new BadRequestException("Password is null"));
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
