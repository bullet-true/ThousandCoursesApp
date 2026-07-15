package ru.ifedorov.courses

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
    onDetailsClick: (courseId: Int) -> Unit,
    onFilterClick: () -> Unit,
    onSortClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        CoursesUiState.Loading -> LoadingContent(
            modifier = modifier
        )

        is CoursesUiState.Error -> ErrorContent(
            message = stringResource(id = R.string.courses_loading_error),
            modifier = modifier
        )

        is CoursesUiState.Content -> {
            CoursesScreen(
                courses = state.courses,
                modifier = modifier,
                onFavoriteClick = viewModel::onFavoriteClick,
                onDetailsClick = onDetailsClick,
                onFilterClick = onFilterClick,
                onSortClick = onSortClick
            )
        }
    }
}
