package com.ishdemon.camerascannertest.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.ishdemon.camerascannertest.R

@OptIn(ExperimentalTextApi::class)
private fun getGoogleFontFamily(
    name: String,
    provider: GoogleFont.Provider = googleFontProvider,
    weights: List<FontWeight>
): FontFamily {
    return FontFamily(
        weights.map {
            Font(GoogleFont(name), provider, it)
        }
    )
}

@OptIn(ExperimentalTextApi::class)
private val googleFontProvider: GoogleFont.Provider by lazy {
    GoogleFont.Provider(
        "com.google.android.gms.fonts",
        "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
}

@OptIn(ExperimentalTextApi::class)
val AppFontTypography = Typography(
    defaultFontFamily = getGoogleFontFamily(
        name = "Poppins",
        weights = listOf(
            FontWeight.Normal,
            FontWeight.Bold,
            FontWeight.ExtraLight,
            FontWeight.SemiBold
        )
    ),
)