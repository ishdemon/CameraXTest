package com.ishdemon.camerascannertest.ui.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.ishdemon.camerascannertest.common.DataState.Empty
import com.ishdemon.camerascannertest.common.DataState.Error
import com.ishdemon.camerascannertest.common.DataState.Loading
import com.ishdemon.camerascannertest.common.DataState.Success
import com.ishdemon.camerascannertest.ui.components.FullScreenLoader
import com.ishdemon.camerascannertest.ui.destinations.PreviewScreenDestination
import com.ishdemon.camerascannertest.ui.viewmodel.PhotosViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: PhotosViewModel,
    navigator: DestinationsNavigator,
    albumId: String
) {

    val fileState = viewModel.imagesFolderState.collectAsState()
    val scrollState = rememberLazyGridState()

    LaunchedEffect(key1 = Unit){
        viewModel.readFilesFromAlbum(albumId)
    }

    Scaffold {

        when (val state = fileState.value) {
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
                ImageGrid(
                    gridState = scrollState,
                    modifier = modifier
                        .padding(paddingValues = it),
                    images = state.data,
                    onPhotoClicked = { index ->
                        navigator.navigate(PreviewScreenDestination(
                            index = index,
                            files = ArrayList(state.data)
                        ))
                    }
                )
            }
        }

    }
}