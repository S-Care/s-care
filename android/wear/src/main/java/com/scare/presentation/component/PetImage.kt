package com.scare.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun PetImage(
    image: Painter
) {
    Image(
        painter = image,
        contentDescription = null,
        Modifier.size(100.dp)
    )
}