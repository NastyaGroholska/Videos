package com.ahrokholska.videos.presentation.screens.video

import android.view.View
import androidx.annotation.OptIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun VideoFullScreen(viewModel: VideoFullViewModel = hiltViewModel()) {
    VideoFullScreenContent(
        exoPlayer = viewModel.exoPlayer,
        hasPrevious = viewModel.hasPrevious.collectAsState().value,
        hasNext = viewModel.hasNext.collectAsState().value,
        onPrevClick = viewModel::setPrevVideo,
        onNextClick = viewModel::setNextVideo
    )
}

@OptIn(UnstableApi::class)
@Composable
fun VideoFullScreenContent(
    exoPlayer: ExoPlayer?,
    hasPrevious: Boolean,
    hasNext: Boolean,
    onPrevClick: () -> Unit = {},
    onNextClick: () -> Unit = {}
) {
    var isControllerVisible by rememberSaveable { mutableStateOf(true) }
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        exoPlayer?.let {
                            player = exoPlayer
                            setShowRewindButton(false)
                            setShowFastForwardButton(false)
                            setControllerAnimationEnabled(false)
                            isControllerFullyVisible
                            controllerShowTimeoutMs = 2000
                            setShowPreviousButton(false)
                            setShowNextButton(false)

                            setControllerVisibilityListener(
                                PlayerView.ControllerVisibilityListener { visibility ->
                                    isControllerVisible = visibility == View.VISIBLE
                                })
                        }
                    }
                }
            )
            if (isControllerVisible) {
                Row(horizontalArrangement = Arrangement.spacedBy(100.dp)) {
                    Icon(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable(enabled = hasPrevious) { onPrevClick() }
                            .padding(5.dp),
                        imageVector = Icons.Filled.SkipPrevious,
                        contentDescription = null,
                        tint = if (hasPrevious) Color.White else MaterialTheme.colorScheme.secondary
                    )
                    Icon(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable(enabled = hasNext) { onNextClick() }
                            .padding(5.dp),
                        imageVector = Icons.Filled.SkipNext,
                        contentDescription = null,
                        tint = if (hasNext) Color.White else MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
    var wasPlaying by rememberSaveable { mutableStateOf(false) }
    val lifeCycleOwner = LocalLifecycleOwner.current
    exoPlayer?.let {
        DisposableEffect(lifeCycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_PAUSE) {
                    wasPlaying = exoPlayer.isPlaying
                    exoPlayer.pause()
                }
                if (event == Lifecycle.Event.ON_START) {
                    if (wasPlaying) {
                        isControllerVisible = false
                        exoPlayer.play()
                    }
                }
            }
            lifeCycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifeCycleOwner.lifecycle.removeObserver(observer)
            }
        }
    }
}

@Preview
@Composable
private fun VideoFullScreenPreview() {
    VideoFullScreenContent(exoPlayer = null, hasPrevious = true, hasNext = true)
}