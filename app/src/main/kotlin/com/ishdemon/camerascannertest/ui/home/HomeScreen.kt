package com.ishdemon.camerascannertest.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus.Denied
import com.google.accompanist.permissions.PermissionStatus.Granted
import com.google.accompanist.permissions.rememberPermissionState
import com.ishdemon.camerascannertest.common.DataState.Empty
import com.ishdemon.camerascannertest.common.DataState.Error
import com.ishdemon.camerascannertest.common.DataState.Loading
import com.ishdemon.camerascannertest.common.DataState.Success
import com.ishdemon.camerascannertest.common.getActivity
import com.ishdemon.camerascannertest.ui.CameraActivity.Companion
import com.ishdemon.camerascannertest.ui.components.AppBottomBar
import com.ishdemon.camerascannertest.ui.components.FullScreenLoader
import com.ishdemon.camerascannertest.ui.destinations.DetailScreenDestination
import com.ishdemon.camerascannertest.ui.viewmodel.PhotosViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: PhotosViewModel,
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current
    //val viewModel: PhotosViewModel = viewModel()
    val albumState = viewModel.albumState.collectAsState()
    val scrollState = rememberLazyGridState()
    val filePermissionState = rememberPermissionState(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

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
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    when (val status = filePermissionState.status) {
                        is Denied -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                val textToShow = if (status.shouldShowRationale) {
                                    // If the user has denied the permission but the rationale can be shown,
                                    // then gently explain why the app requires this permission
                                    "File Permissions are required to store the images. Please grant the permission."
                                } else {
                                    // If it's the first time the user lands on this feature, or the user
                                    // doesn't want to be asked again for this permission, explain that the
                                    // permission is required
                                    "File Permissions required to store the images. " +
                                            "Please grant the permission"
                                }
                                Text(
                                    textAlign = TextAlign.Center,
                                    text = textToShow
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
                                    onClick = { filePermissionState.launchPermissionRequest() },
                                ) {
                                    Text("Request permission")
                                }
                            }
                        }
                        Granted -> {
                            AlbumGrid(
                                gridState = scrollState,
                                modifier = modifier
                                    .padding(paddingValues = it),
                                albums = state.data,
                                onAlbumClicked = { albumId ->
                                    navigator.navigate(DetailScreenDestination(albumId))
                                }
                            )
                        }
                    }
                } else {
                    AlbumGrid(
                        gridState = scrollState,
                        modifier = modifier
                            .padding(paddingValues = it),
                        albums = state.data,
                        onAlbumClicked = { albumId ->
                            navigator.navigate(DetailScreenDestination(albumId))
                        }
                    )
                }
            }
        }
    }
}