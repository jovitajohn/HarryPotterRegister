package com.jovita.harrypotter.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.jovita.myapplication.R


@Composable
fun AppThemeWithBackground( content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()
        .background(GriffindorGold)) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.2f), // Adjust the transparency of the image
            contentScale = ContentScale.Crop
        )

        //Main app content
        content()
    }
}
