package ru.ifedorov.thousandcourses.navigation

import ru.ifedorov.thousandcourses.R

enum class TopLevelDestination(
    val route: String,
    val labelRes: Int,
    val iconRes: Int,
    val selectedIconRes: Int
) {
    Home(
        route = "home",
        labelRes = R.string.app_tab_home,
        iconRes = R.drawable.ic_tab_home,
        selectedIconRes = R.drawable.ic_tab_home_selected
    ),
    Favorites(
        route = "favorites",
        labelRes = R.string.app_tab_favorites,
        iconRes = R.drawable.ic_tab_favorites,
        selectedIconRes = R.drawable.ic_tab_favorites_selected
    ),
    Account(
        route = "account",
        labelRes = R.string.app_tab_account,
        iconRes = R.drawable.ic_tab_account,
        selectedIconRes = R.drawable.ic_tab_account_selected
    );

    companion object {
        fun fromRoute(route: String?): TopLevelDestination =
            entries.firstOrNull { tab -> tab.route == route } ?: Home
    }
}
