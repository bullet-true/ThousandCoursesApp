package ru.ifedorov.thousandcourses.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ifedorov.ui.theme.ThousandCoursesTheme

private val BottomBarHeight = 80.dp
private val BottomBarDividerHeight = 1.5.dp
private val BottomBarItemGap = 8.dp
private val BottomBarItemTopPadding = 12.dp
private val BottomBarIconContainerCornerRadius = 24.dp
private val BottomBarIconContainerWidth = 64.dp
private val BottomBarIconContainerHeight = 32.dp
private val BottomBarIconSize = 24.dp

@Composable
fun BottomBar(
    selectedTab: TopLevelDestination,
    onTabClick: (TopLevelDestination) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
    ) {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outline,
            thickness = BottomBarDividerHeight
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(BottomBarHeight)
                .padding(top = BottomBarItemTopPadding),
            horizontalArrangement = Arrangement.spacedBy(BottomBarItemGap),
            verticalAlignment = Alignment.Top
        ) {
            TopLevelDestination.entries.forEach { tab ->
                BottomBarItem(
                    tab = tab,
                    selected = tab == selectedTab,
                    onClick = { onTabClick(tab) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun BottomBarItem(
    tab: TopLevelDestination,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    val tint = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onBackground
    }

    Column(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(BottomBarIconContainerCornerRadius))
                .background(
                    color = if (selected) {
                        MaterialTheme.colorScheme.surfaceVariant
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
                .size(width = BottomBarIconContainerWidth, height = BottomBarIconContainerHeight),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(
                    id = if (selected) {
                        tab.selectedIconRes
                    } else {
                        tab.iconRes
                    }
                ),
                contentDescription = stringResource(id = tab.labelRes),
                tint = Color.Unspecified,
                modifier = Modifier.size(BottomBarIconSize)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(id = tab.labelRes),
            color = tint,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview
@Composable
private fun BottomBarPreview() {
    ThousandCoursesTheme {
        BottomBar(
            selectedTab = TopLevelDestination.Home,
            onTabClick = {}
        )
    }
}

@Preview
@Composable
private fun BottomBarItemPreview() {
    ThousandCoursesTheme {
        BottomBarItem(
            tab = TopLevelDestination.Home,
            selected = false,
            onClick = {}
        )
    }
}
