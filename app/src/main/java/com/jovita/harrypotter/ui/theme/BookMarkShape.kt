package com.jovita.harrypotter.ui.theme

import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

class BookMarkShape (private val cornerRadius: Dp) : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: androidx.compose.ui.unit.LayoutDirection,
        density: androidx.compose.ui.unit.Density
    ): Outline {
        val cornerRadiusPx = with(density) { cornerRadius.toPx() }
        val path = Path().apply {
            // Top left corner
            moveTo(0f, 0f)
            // Top right corner
            lineTo(size.width, 0f)
            // Bottom right corner
            lineTo(size.width, size.height - cornerRadiusPx)
            // Bottom triangle point
            lineTo(size.width / 2, size.height)
            // Bottom left corner
            lineTo(0f, size.height - cornerRadiusPx)
            close()
        }
        return Outline.Generic(path)
    }
}