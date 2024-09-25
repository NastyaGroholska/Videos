package com.ahrokholska.videos.presentation.screens.video

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun VideoFullScreen(viewModel: VideoFullViewModel = hiltViewModel()) {
    VideoFullScreenContent(exoPlayer = viewModel.exoPlayer)
}

@Composable
fun VideoFullScreenContent(exoPlayer: ExoPlayer?) {
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AndroidView(
                modifier = Modifier.align(Alignment.Center),
                factory = { context ->
                    PlayerView(context).apply {
                        player = exoPlayer
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun VideoFullScreenPreview() {
    VideoFullScreenContent(exoPlayer = null)
}