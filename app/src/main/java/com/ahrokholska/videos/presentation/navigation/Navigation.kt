package com.ahrokholska.videos.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahrokholska.videos.presentation.screens.home.HomeScreen
import com.ahrokholska.videos.presentation.screens.video.VideoFullScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    SharedTransitionLayout {
        NavHost(navController = navController, startDestination = Screen.Home) {
            composable<Screen.Home> {
                HomeScreen(
                    onVideoClick = { navController.navigate(Screen.VideoFull(it)) },
                    animatedVisibilityScope = this,
                    sharedTransitionScope = this@SharedTransitionLayout
                )
            }

            composable<Screen.VideoFull> {
                VideoFullScreen(
                    animatedVisibilityScope = this,
                    sharedTransitionScope = this@SharedTransitionLayout
                )
            }
        }
    }
}