package com.ishdemon.camerascannertest.ui.preview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.ishdemon.camerascannertest.common.DataState.Empty
import com.ishdemon.camerascannertest.common.DataState.Error
import com.ishdemon.camerascannertest.common.DataState.Loading
import com.ishdemon.camerascannertest.common.DataState.Success
import com.ishdemon.camerascannertest.theme.CameraTestTheme
import com.ishdemon.camerascannertest.ui.components.FullScreenLoader
import com.ishdemon.camerascannertest.ui.viewmodel.PhotosViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviewActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraTestTheme {
                val viewModel:PhotosViewModel = hiltViewModel()
                val fileState = viewModel.imagesFolderState.collectAsState()
                LaunchedEffect(key1 = Unit){
                    viewModel.readFilesFromAlbum(getActivityParams())
                }
                when(val state = fileState.value){
                    Empty -> {}
                    is Error -> {}
                    Loading -> FullScreenLoader()
                    is Success -> {
                        PreviewScreen(
                            viewModel = viewModel,
                            files = ArrayList(state.data)
                        )
                    }
                }
            }
        }
    }

    private fun getActivityParams(): String {
        return intent.getStringExtra(PARAM)?:""
    }

    companion object {
        const val PARAM = "albumId"
        fun launch(activity: Activity, albumId:String, requestCode: Int = -1) = activity.startActivityForResult(
            Intent(activity, PreviewActivity::class.java).apply {
               putExtra(PARAM, albumId )
            }, requestCode)
    }
}