package com.example.parkingap6800.activities

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.drawable.Drawable
import com.example.parkingap6800.viewmodels.QrCodeViewModel

// Adds the bounding box around the Qr code
// Help from: https://github.com/android/camera-samples/blob/main/CameraX-MLKit/app/src/main/java/com/example/camerax_mlkit/QrCodeDrawable.kt
class QrCodeDrawable(qrCodeViewModel: QrCodeViewModel) : Drawable() {
    private val boundingRectPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.YELLOW
        strokeWidth = 5F
        alpha = 200
    }

    /*
    Don't think I need this
    private val contentRectPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.YELLOW
        alpha = 255
    }*/

    private val qrCodeViewModel = qrCodeViewModel
    //private val contentPadding = 25
    //Not providing text, so I don't think we need this

    override fun draw(canvas: Canvas) {
        canvas.drawRect(qrCodeViewModel.boundingRect, boundingRectPaint)
    }

    override fun setAlpha(alpha: Int) {
        boundingRectPaint.alpha = alpha
    }

    override fun setColorFilter(p0: ColorFilter?) {
        boundingRectPaint.colorFilter = colorFilter
    }

    @Deprecated("Deprecated in Java")
    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

}