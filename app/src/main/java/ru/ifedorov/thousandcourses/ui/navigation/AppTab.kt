package ru.ifedorov.thousandcourses.ui.navigation

import ru.ifedorov.thousandcourses.R

enum class AppTab(
    val labelRes: Int,
    val iconRes: Int,
    val selectedIconRes: Int
) {
    Home(
        labelRes = R.string.app_tab_home,
        iconRes = R.drawable.ic_tab_home,
        selectedIconRes = R.drawable.ic_tab_home_selected
    ),
    Favorites(
        labelRes = R.string.app_tab_favorites,
        iconRes = R.drawable.ic_tab_favorites,
        selectedIconRes = R.drawable.ic_tab_favorites_selected
    ),
    Account(
        labelRes = R.string.app_tab_account,
        iconRes = R.drawable.ic_tab_account,
        selectedIconRes = R.drawable.ic_tab_account_selected
    )
}
