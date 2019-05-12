package sergesv.rvs.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public final class DateTimeUtil {
    public static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    public static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);

    public static boolean checkTime(LocalTime localTime, LocalTime maxVoteTime) {
        return localTime.toSecondOfDay() <= maxVoteTime.toSecondOfDay();
    }

    private static LocalTime testLocalTime = null;

    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    public static LocalTime getCurrentTime() {
        return Optional.ofNullable(testLocalTime).orElse(LocalTime.now());
    }

    public static void setTestLocalTime(LocalTime localTime) {
        testLocalTime = localTime;
    }

    private DateTimeUtil() {
    }
}
