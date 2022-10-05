package com.ishdemon.camerascannertest.ui.preview

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.ishdemon.camerascannertest.common.DataState
import com.ishdemon.camerascannertest.common.DataState.Empty
import com.ishdemon.camerascannertest.common.DataState.Error
import com.ishdemon.camerascannertest.common.DataState.Loading
import com.ishdemon.camerascannertest.common.DataState.Success
import com.ishdemon.camerascannertest.theme.CameraTestTheme
import com.ishdemon.camerascannertest.ui.viewmodel.PhotosViewModel
import com.ishdemon.camerascannertest.ui.components.CoilImage
import com.ishdemon.camerascannertest.ui.components.FullScreenLoader
import com.ishdemon.camerascannertest.ui.components.PagerIndicator
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.serialization.Serializable
import java.io.File

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPagerApi::class, ExperimentalCoilApi::class)
@Destination
@Composable
fun PreviewScreen(
    modifier: Modifier = Modifier,
    viewModel: PhotosViewModel,
    files: @Serializable ArrayList<File>,
    index: Int = 0,
) {

    CameraTestTheme {

        Scaffold {
            val pagerState = rememberPagerState(initialPage = index)
            Box(modifier = modifier) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    HorizontalPager(
                        count = files.size,
                        state = pagerState,
                        // Add 32.dp horizontal padding to 'center' the pages
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                    ) { page ->

                        CoilImage(
                            modifier = Modifier.fillMaxSize(),
                            file = files[page]
                        )
                    }
                }
                PagerIndicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    indicatorCount = files.size,
                    indicatorSize = 8.dp,
                    space = 4.dp,
                    pagerState = pagerState
                )
            }
        }
    }
}