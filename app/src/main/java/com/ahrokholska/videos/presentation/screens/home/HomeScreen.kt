package com.ahrokholska.videos.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.ahrokholska.videos.R
import com.ahrokholska.videos.presentation.model.VideoUI

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    HomeScreenContent(
        videos = viewModel.videos.collectAsState().value
    )
}

@Composable
fun HomeScreenContent(videos: List<VideoUI>?) {
    Scaffold { innerPadding ->
        if (videos == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (videos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(R.string.no_data))
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(videos) { video ->
                    SubcomposeAsyncImage(
                        modifier = Modifier.clickable { },
                        model = video.imageURL,
                        loading = { CircularProgressIndicator() },
                        error = {
                            Icon(
                                painter = rememberVectorPainter(image = Icons.Filled.Clear),
                                contentDescription = null
                            )
                        },
                        success = {
                            SubcomposeAsyncImageContent()
                            val itemBackgroundColor =
                                MaterialTheme.colorScheme.background.copy(alpha = 0.7f)
                            Icon(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .background(
                                        color = itemBackgroundColor,
                                        shape = CircleShape
                                    )
                                    .padding(5.dp)
                                    .size(30.dp),
                                painter = rememberVectorPainter(image = Icons.Outlined.PlayArrow),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(5.dp)
                                    .background(
                                        color = itemBackgroundColor,
                                        shape = RoundedCornerShape(5.dp)
                                    )
                                    .padding(5.dp),
                                text = video.durationFormatted
                            )
                        },
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreenContent(videos = listOf())
}