package com.ishdemon.camerascannertest.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ishdemon.camerascannertest.data.domain.Album
import com.ishdemon.camerascannertest.ui.components.AlbumListItem

@Composable
fun AlbumGrid(
    modifier: Modifier = Modifier,
    gridState: LazyGridState,
    albums: List<Album>,
    onAlbumClicked: (String) -> Unit
) {
        LazyVerticalGrid(
            modifier = modifier,
            state = gridState,
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp)
        ) {
            itemsIndexed(albums) { index, album ->
                AlbumListItem(
                    index = index,
                    album = album,
                    onAlbumClicked = onAlbumClicked
                )
            }
        }
}