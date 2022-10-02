package com.ishdemon.camerascannertest

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ishdemon.camerascannertest.common.getActivity

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Scaffold(
        bottomBar = { AppBottomBar(fabShape = RoundedCornerShape(50)) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    context.getActivity()?.let {
                        CameraActivity.launch(it)
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

    }
}

@Composable
fun AppBottomBar(fabShape : RoundedCornerShape) {

    BottomAppBar(
        elevation = 10.dp,
        backgroundColor = MaterialTheme.colors.primaryVariant,
        cutoutShape = fabShape
    ) {

        IconButton(
            onClick = {
                /* doSomething() */
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "",
                tint = MaterialTheme.colors.onPrimary
            )
        }
        // The actions should be at the end of the BottomAppBar
        Spacer(Modifier.weight(1f, true))
        IconButton(
            onClick = {
                /* doSomething() */
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Sort,
                contentDescription = "",
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }
}