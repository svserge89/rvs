package sergesv.rvs.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import sergesv.rvs.model.*;
import sergesv.rvs.web.to.MenuEntryTo;
import sergesv.rvs.web.to.RestaurantTo;
import sergesv.rvs.web.to.UserTo;
import sergesv.rvs.web.to.VoteEntryTo;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class ToUtil {
    // From RestaurantTo to Restaurant
    public static Restaurant toModel(RestaurantTo restaurantTo) {
        return new Restaurant(restaurantTo.getId(), restaurantTo.getName(), null);
    }

    // From MenuEntryTo to MenuEntry with restaurant
    public static MenuEntry toModel(MenuEntryTo menuEntryTo, Restaurant restaurant) {
        return new MenuEntry(menuEntryTo.getId(), menuEntryTo.getName(), menuEntryTo.getPrice(),
                menuEntryTo.getDate(), restaurant);
    }

    // From UserTo to User
    public static User toModel(UserTo userTo, PasswordEncoder passwordEncoder) {
        Set<Role> roles = new HashSet<>();

        if (userTo.isAdmin()) {
            roles.add(Role.ROLE_ADMIN);
        }
        if (userTo.isRegular()) {
            roles.add(Role.ROLE_USER);
        }

        return new User(userTo.getId(), userTo.getNickName(), userTo.getFirstName(),
                userTo.getLastName(), userTo.getEmail(),
                passwordEncoder.encode(userTo.getPassword()), roles);
    }

    // From VoteEntryTo to VoteEntry with user
    public static VoteEntry toModel(VoteEntryTo voteEntryTo, User user) {
        return new VoteEntry(0, user, toModel(voteEntryTo.getRestaurant()),
                voteEntryTo.getDateTime().toLocalDate(), voteEntryTo.getDateTime().toLocalTime());
    }

    // From Restaurant to RestaurantTo without rating
    public static RestaurantTo toTo(Restaurant restaurant) {
        return toTo(restaurant, null);
    }

    // From Restaurant to RestaurantTo with rating
    public static RestaurantTo toTo(Restaurant restaurant, Long rating) {
        List<MenuEntryTo> menuEntryTos = null;

        if (restaurant.getMenuEntries() != null) {
            menuEntryTos = restaurant.getMenuEntries().stream()
                    .map(ToUtil::toTo)
                    .collect(Collectors.toList());
        }

        return new RestaurantTo(restaurant.getId(), restaurant.getName(), menuEntryTos, rating);
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
        return new VoteEntryTo(toTo(voteEntry.getRestaurant()),
                LocalDateTime.of(voteEntry.getDate(), voteEntry.getTime()));
    }

    private ToUtil() {
    }
}
