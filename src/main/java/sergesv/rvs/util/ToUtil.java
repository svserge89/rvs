package sergesv.rvs.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import sergesv.rvs.model.*;
import sergesv.rvs.model.security.Role;
import sergesv.rvs.web.to.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static sergesv.rvs.util.DateTimeUtil.getCurrentDate;

public final class ToUtil {
    // From RestaurantTo to Restaurant
    public static Restaurant toModel(RestaurantTo restaurantTo) {
        return new Restaurant(0, restaurantTo.getName(), null, null);
    }

    // From MenuEntryTo to MenuEntry with restaurant
    public static MenuEntry toModel(MenuEntryTo menuEntryTo, Restaurant restaurant) {
        return new MenuEntry(0, menuEntryTo.getName(), menuEntryTo.getPrice(),
                Optional.ofNullable(menuEntryTo.getDate()).orElse(getCurrentDate()), restaurant);
    }

    // From UserTo to User
    public static User toModel(UserTo userTo, PasswordEncoder passwordEncoder) {
        Set<Role> roles = new HashSet<>();

        if (Optional.ofNullable(userTo.getAdmin()).orElse(false)) {
            roles.add(Role.ROLE_ADMIN);
        }
        if (Optional.ofNullable(userTo.getRegular()).orElse(false)) {
            roles.add(Role.ROLE_USER);
        }

        return new User(0, userTo.getNickName(), userTo.getFirstName(),
                userTo.getLastName(), userTo.getEmail(),
                passwordEncoder.encode(userTo.getPassword()), roles);
    }

    // From VoteEntryTo to VoteEntry with user
    public static VoteEntry toModel(VoteEntryTo voteEntryTo, User user) {
        return new VoteEntry(0, user, toModel(voteEntryTo.getRestaurant()),
                voteEntryTo.getDateTime().toLocalDate(), voteEntryTo.getDateTime().toLocalTime());
    }

    // From Restaurant to RestaurantTo
    public static RestaurantTo toTo(Restaurant restaurant, boolean withMenu, boolean withRating) {
        LinkedHashSet<MenuEntryTo> menuEntryTos = null;

        if (withMenu) {
            menuEntryTos = restaurant.getMenuEntry().stream()
                    .map(ToUtil::toTo)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }

        return new RestaurantTo(restaurant.getId(), restaurant.getName(), menuEntryTos,
                withRating ? restaurant.getVoteEntry().size() : null);
    }

    // From RestaurantWithRating to RestaurantTo
    public static RestaurantTo toTo(RestaurantWithRating restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), null,
                (int) restaurant.getRating());
    }

    // From MenuEntry to MenuEntryTo
    public static MenuEntryTo toTo(MenuEntry menuEntry) {
        return new MenuEntryTo(menuEntry.getId(), menuEntry.getName(),
                menuEntry.getPrice(), menuEntry.getDate());
    }

    // From User to UserTo without password
    public static UserTo toTo(User user) {
        return new UserTo(user.getId(), user.getNickName(), user.getFirstName(), user.getLastName(),
                user.getEmail(), null, user.getRoles().contains(Role.ROLE_ADMIN),
                user.getRoles().contains(Role.ROLE_USER));
    }

    // From VoteEntry to VoteEntryTo
    public static VoteEntryTo toTo(VoteEntry voteEntry) {
        return new VoteEntryTo(toTo(voteEntry.getRestaurant(), false, false),
                LocalDateTime.of(voteEntry.getDate(), voteEntry.getTime()));
    }

    private ToUtil() {
    }
}
