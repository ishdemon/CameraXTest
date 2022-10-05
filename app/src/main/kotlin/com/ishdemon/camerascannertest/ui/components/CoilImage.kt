package com.ishdemon.camerascannertest.ui.components

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ishdemon.camerascannertest.R
import java.io.File

@ExperimentalCoilApi
@Composable
fun CoilImage(
    modifier: Modifier = Modifier,
    imageUrI: Uri,
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.Crop
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrI)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    )
}

@ExperimentalCoilApi
@Composable
fun CoilImage(
    modifier: Modifier = Modifier,
    file: File,
    loadState: ((Boolean) -> Unit)? = null,
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.Crop
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(file)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier,
        onSuccess = {
            if (loadState != null) {
                loadState(true)
            }
        }
    )
}