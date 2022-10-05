package com.ishdemon.camerascannertest.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ishdemon.camerascannertest.theme.CameraTestTheme
import com.ishdemon.camerascannertest.ui.NavGraphs
import com.ishdemon.camerascannertest.ui.viewmodel.PhotosViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraTestTheme {
                val engine = rememberAnimatedNavHostEngine(
                    rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING,
                )
                val navController = engine.rememberNavController()
                DestinationsNavHost(
                    engine = engine,
                    navController = navController,
                    navGraph = NavGraphs.root,
                    dependenciesContainerBuilder = {
                        dependency(hiltViewModel<PhotosViewModel>(this@MainActivity))
                    }
                )
            }
        }
    }
}