package ru.ifedorov.favorites

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = R.string.favorites_title),
        modifier = modifier,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.headlineLarge
    )
}
