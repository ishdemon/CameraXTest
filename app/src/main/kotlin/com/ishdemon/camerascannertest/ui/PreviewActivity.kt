package com.ishdemon.camerascannertest.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ishdemon.camerascannertest.theme.CameraTestTheme
import com.ishdemon.camerascannertest.ui.preview.PreviewScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviewActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraTestTheme {
                PreviewScreen(
                    albumId = getActivityParams()
                )
            }
        }
    }

    private fun getActivityParams(): String {
        return intent.getStringExtra("albumId")?:""
    }

    companion object {
        fun launch(activity: Activity, albumId:String, requestCode: Int = -1) = activity.startActivityForResult(
            Intent(activity, PreviewActivity::class.java).apply {
               putExtra("albumId", albumId )
            }, requestCode)
    }
}