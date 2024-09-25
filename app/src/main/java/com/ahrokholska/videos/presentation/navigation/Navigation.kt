package com.ahrokholska.videos.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahrokholska.videos.presentation.screens.home.HomeScreen
import com.ahrokholska.videos.presentation.screens.video.VideoFullScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home) {
        composable<Screen.Home> {
            HomeScreen(onVideoClick = { navController.navigate(Screen.VideoFull(it)) })
        }

        composable<Screen.VideoFull> {
            VideoFullScreen()
        }
    }
}