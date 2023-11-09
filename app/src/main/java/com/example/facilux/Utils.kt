package com.example.facilux


import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import java.lang.Exception

object Utils {

    fun createImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri? {
        val contentResolver: ContentResolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.DISPLAY_NAME, "image_filename.jpg")
        }

        val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        return imageUri?.let { uri ->
            try {
                contentResolver.openOutputStream(uri)?.use { outputStream ->
                    if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)) {
                        return@let uri
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            contentResolver.delete(uri, null, null)
            null
        }
    }

    fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            inputStream?.use {
                return BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Converção e reversão de Uri para String.
    fun uriToString(uri: Uri?): String? {
        return uri?.toString()
    }

    fun stringToUri(uriString: String?): Uri? {
        return uriString?.let { Uri.parse(it) }
    }
}