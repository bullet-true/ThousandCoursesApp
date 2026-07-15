package ru.ifedorov.courses

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.ifedorov.ui.component.ErrorContent
import ru.ifedorov.ui.component.LoadingContent

@Composable
fun CoursesRoute(
    modifier: Modifier = Modifier,
    viewModel: CoursesViewModel = hiltViewModel(),
    onDetailsClick: (courseId: Int) -> Unit = {},
    onFilterClick: () -> Unit = {},
    onSortClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        uiState.isLoading -> LoadingContent(
            innerPadding = PaddingValues(),
            modifier = modifier
        )

        uiState.errorMessage != null -> ErrorContent(
            message = stringResource(id = R.string.courses_loading_error),
            innerPadding = PaddingValues(),
            modifier = modifier
        )

        else -> {
            CoursesScreen(
                courses = uiState.courses,
                modifier = modifier,
                onFavoriteClick = viewModel::onFavoriteClick,
                onDetailsClick = onDetailsClick,
                onFilterClick = onFilterClick,
                onSortClick = onSortClick
            )
        }
    }
}
