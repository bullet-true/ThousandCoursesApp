package ru.ifedorov.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.ifedorov.ui.R
import ru.ifedorov.ui.theme.ThousandCoursesTheme

private const val FAVORITE_BUTTON_BACKGROUND_ALPHA = 0.3f
private const val INFO_BADGE_BACKGROUND_ALPHA = 0.3f
private const val COURSE_DESCRIPTION_ALPHA = 0.7f
private val CardHeight = 236.dp
private val CardImageHeight = 114.dp
private val CardCornerRadius = 16.dp
private val CardImageCornerRadius = 12.dp
private val FavoriteButtonSize = 28.dp
private val FavoriteButtonOuterPadding = 8.dp
private val FavoriteButtonInnerPadding = 6.dp
private val FavoriteButtonIconSize = 16.dp
private val InfoBadgeCornerRadius = 12.dp
private val InfoBadgeIconSize = 12.dp
private val InfoBadgeHorizontalPadding = 6.dp
private val InfoBadgeVerticalPadding = 4.dp
private val InfoBadgeContentGap = 4.dp
private val MoreDetailsIconSize = 16.dp
private val ImagePlaceholderStartColor = Color(0xFFFF8A00)
private val ImagePlaceholderEndColor = Color(0xFFFFC400)

@Composable
fun CourseCard(
    course: CourseCardUiModel,
    onFavoriteClick: (courseId: Int) -> Unit,
    onDetailsClick: (courseId: Int) -> Unit,
    modifier: Modifier = Modifier,
    imageContent: @Composable BoxScope.() -> Unit = { CourseImage(course = course) }
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(CardHeight)
            .clip(RoundedCornerShape(CardCornerRadius))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        CourseCardImage(
            course = course,
            onFavoriteClick = onFavoriteClick,
            imageContent = imageContent
        )
        CourseCardContent(
            course = course,
            onDetailsClick = onDetailsClick
        )
    }
}

@Composable
private fun CourseImage(course: CourseCardUiModel) {
    if (course.imageUrl == null) {
        CourseImagePlaceholder()
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = course.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun CourseCardContent(
    course: CourseCardUiModel,
    onDetailsClick: (courseId: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            text = course.title,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = course.description,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = COURSE_DESCRIPTION_ALPHA),
            style = MaterialTheme.typography.bodySmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = course.price,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium,
            )
            Row(
                modifier = Modifier.clickable {
                    onDetailsClick(course.id)
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.course_details_button),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelSmall
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(MoreDetailsIconSize)
                )
            }
        }
    }
}

@Composable
private fun CourseCardImage(
    course: CourseCardUiModel,
    onFavoriteClick: (courseId: Int) -> Unit,
    imageContent: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(CardImageHeight)
            .clip(RoundedCornerShape(CardImageCornerRadius))
    ) {
        imageContent()

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = FavoriteButtonOuterPadding, end = FavoriteButtonOuterPadding)
                .size(FavoriteButtonSize)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = FAVORITE_BUTTON_BACKGROUND_ALPHA))
                .clickable(role = Role.Button) { onFavoriteClick(course.id) }
                .padding(FavoriteButtonInnerPadding),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(
                    id = if (course.isFavorite) {
                        R.drawable.ic_bookmark_favorite
                    } else {
                        R.drawable.ic_bookmark
                    }
                ),
                contentDescription = stringResource(
                    id = if (course.isFavorite) {
                        R.string.course_remove_from_favorites
                    } else {
                        R.string.course_add_to_favorites
                    }
                ),
                modifier = Modifier.size(FavoriteButtonIconSize),
                tint = Color.Unspecified
            )
        }

        InfoBadge(
            rating = course.rating,
            date = course.date,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 8.dp, bottom = 8.dp)
        )
    }
}

@Composable
private fun InfoBadge(
    rating: String,
    date: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CourseMetaBadgeItem {
            Icon(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(InfoBadgeIconSize)
            )
            Text(
                text = rating,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelMedium
            )
        }
        CourseMetaBadgeItem {
            Text(
                text = date,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
private fun CourseMetaBadgeItem(
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = INFO_BADGE_BACKGROUND_ALPHA),
                shape = RoundedCornerShape(InfoBadgeCornerRadius)
            )
            .padding(horizontal = InfoBadgeHorizontalPadding, vertical = InfoBadgeVerticalPadding),
        horizontalArrangement = Arrangement.spacedBy(InfoBadgeContentGap),
        verticalAlignment = Alignment.CenterVertically,
        content = content
    )
}

@Composable
private fun CourseImagePlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.horizontalGradient(
                    listOf(
                        ImagePlaceholderStartColor,
                        ImagePlaceholderEndColor
                    )
                )
            )
    )
}

private val previewCourse = CourseCardUiModel(
    id = 1,
    title = "Java-разработчик с нуля",
    description =
    "Освойте backend-разработку и программирование на Java, " +
        "фреймворки Spring и Maven, работу с базами данных и API. " +
        "Создайте свой собственный проект, собрав портфолио и став востребованным специалистом " +
        "для любой IT компании.",
    price = "999 ₽",
    rating = "4.9",
    date = "22 Мая 2024",
    isFavorite = true
)

@Preview(showBackground = true)
@Composable
private fun CourseCardPreview() {
    ThousandCoursesTheme {
        CourseCard(
            course = previewCourse,
            onFavoriteClick = {},
            onDetailsClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CourseCardImagePreview() {
    ThousandCoursesTheme {
        CourseCardImage(
            course = previewCourse,
            onFavoriteClick = {},
            imageContent = { CourseImagePlaceholder() }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InfoBadgePreview() {
    ThousandCoursesTheme {
        InfoBadge(
            rating = "4.9",
            date = "22 Мая 2024",
        )
    }
}
