package com.example.parkingap6800.viewmodels

import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import com.google.mlkit.vision.barcode.common.Barcode

class QrCodeViewModel(barcode: Barcode) {
    var boundingRect: Rect = barcode.boundingBox!!
    var qrContent: String = ""
    var qrCodeTouchCallback = { v: View, e: MotionEvent -> false}

    /*
    This code deals with barcodes that are URLs
    Will need to change to deal with the type of information
    that we want (what do payment qr codes return?)
    init {
        when (barcode.valueType) {
            Barcode.TYPE_URL -> {
                qrContent = barcode.url!!.url!!
                qrCodeTouchCallback = {v: View, e: MotionEvent ->
                    if (e.action == MotionEvent.ACTION_DOWN &&
                            boundingRect.contains(e.getX().toInt(),
                            e.getY().toInt())){
                        //Will probs need to change this
                        val openBrowserIntent = Intent(Intent.ACTION_VIEW)
                        v.context.startActivity(openBrowserIntent)
                        //
                    }
                    true
                }
            }
        }
    }
    */
}