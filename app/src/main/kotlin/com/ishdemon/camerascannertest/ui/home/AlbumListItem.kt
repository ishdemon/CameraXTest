package com.ishdemon.camerascannertest.ui.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import com.ishdemon.camerascannertest.data.domain.Album
import com.ishdemon.camerascannertest.theme.Black80
import com.ishdemon.camerascannertest.theme.White80
import com.ishdemon.camerascannertest.theme.lightWhite

@OptIn(ExperimentalCoilApi::class)
@Composable
fun AlbumListItem(
    modifier: Modifier = Modifier,
    index: Int,
    album: Album,
    onAlbumClicked: (String) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = 4.dp,
        modifier = modifier
            .height(200.dp)
            .padding(all = 12.dp)
            .clickable {
                onAlbumClicked(album.id)
            }
    ) {
        CoilImage(
            imageUrI = Uri.parse(album.thumbUri)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Black80)
        ){
            Column(
                modifier = Modifier.background(Black80).padding(10.dp).align(Companion.BottomStart),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier,
                    text = album.album_name,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    color = lightWhite,
                    fontSize = 16.sp
                )
                Text(
                    modifier = Modifier,
                    text = album.album_date,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Light,
                    maxLines = 1,
                    color = lightWhite,
                    fontSize = 14.sp
                )

            }

            Text(
                modifier = Modifier.align(Alignment.Center),
                overflow = TextOverflow.Ellipsis,
                text = "${album.count}",
                color = White80,
                fontSize = 48.sp
            )
        }
    }
}