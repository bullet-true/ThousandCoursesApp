package ru.ifedorov.thousandcourses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import ru.ifedorov.thousandcourses.ui.CoursesViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val coursesViewModel: CoursesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val coursesUiState by coursesViewModel.uiState.collectAsStateWithLifecycle()

            ThousandCoursesApp(coursesUiState = coursesUiState)
        }
    }
}
