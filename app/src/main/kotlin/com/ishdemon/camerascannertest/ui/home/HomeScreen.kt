package com.ishdemon.camerascannertest.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ishdemon.camerascannertest.ui.CameraActivity.Companion
import com.ishdemon.camerascannertest.common.DataState.Empty
import com.ishdemon.camerascannertest.common.DataState.Error
import com.ishdemon.camerascannertest.common.DataState.Loading
import com.ishdemon.camerascannertest.common.DataState.Success
import com.ishdemon.camerascannertest.common.getActivity
import com.ishdemon.camerascannertest.ui.components.AppBottomBar
import com.ishdemon.camerascannertest.ui.components.FullScreenLoader
import com.ishdemon.camerascannertest.ui.home.AlbumGrid
import com.ishdemon.camerascannertest.ui.viewmodel.PhotosViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel: PhotosViewModel = viewModel()
    val albumState = viewModel.albumState.collectAsState()
    val scrollState = rememberLazyGridState()

    Scaffold(
        bottomBar = { AppBottomBar(fabShape = RoundedCornerShape(50)) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    context.getActivity()?.let {
                        Companion.launch(it)
                    }
                },
                shape = RoundedCornerShape(50),
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.CameraEnhance,"",)
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
    ) {

        when (val state = albumState.value) {
            Empty -> {

            }
            is Error -> {

            }
            Loading -> {
                FullScreenLoader(
                    modifier = Modifier.padding(it)
                )
            }
            is Success -> {
                AlbumGrid(
                    gridState = scrollState,
                    modifier = modifier
                        .padding(paddingValues = it),
                    albums = state.data,
                    onAlbumClicked = { index ->
                        //navigator.navigate(DetailsScreenDestination(index))
                    }
                )
            }
        }

    }
}