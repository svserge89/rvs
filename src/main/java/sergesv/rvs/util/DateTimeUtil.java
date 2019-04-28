package sergesv.rvs.util;

import java.time.LocalDate;
import java.time.LocalTime;

public final class DateTimeUtil {
    public static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    public static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);

    private static final LocalTime MAX_CHANGE_TIME = LocalTime.of(11, 0);

    public static boolean checkTime(LocalTime localTime) {
        return localTime.getSecond() <= MAX_CHANGE_TIME.getSecond();
    }

    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    public static LocalTime getCurrentTime() {
        return LocalTime.now();
    }

    private DateTimeUtil() {
    }
}
