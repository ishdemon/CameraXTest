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
    placeholder: Int = R.drawable.ic_baseline_broken_image_24,
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.Crop
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrI)
            .crossfade(true)
            .placeholder(placeholder)
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
    placeholder: Int = R.drawable.ic_baseline_broken_image_24,
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.Crop
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(file)
            .crossfade(true)
            .placeholder(placeholder)
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    )
}