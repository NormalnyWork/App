package com.normalnywork.plants.utils

import android.content.Context
import android.webkit.MimeTypeMap
import androidx.core.net.toUri
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

object FileTools : KoinComponent {

    private val context: Context by inject()

    fun getFileFromUri(uri: String): File {
        val fileExtension = getFileExtension(context, uri)
        val fileName = "temp_file" + if (fileExtension != null) ".$fileExtension" else ""

        val tempFile = File(context.cacheDir, fileName)
        tempFile.createNewFile()

        try {
            val oStream = FileOutputStream(tempFile)
            val inputStream = context.contentResolver.openInputStream(uri.toUri())

            inputStream?.let {
                copy(inputStream, oStream)
            }

            oStream.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return tempFile
    }

    private fun getFileExtension(context: Context, uri: String): String? {
        val fileType: String? = context.contentResolver.getType(uri.toUri())
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(fileType)
    }

    private fun copy(source: InputStream, target: OutputStream) {
        val buf = ByteArray(8192)
        var length: Int
        while (source.read(buf).also { length = it } > 0) {
            target.write(buf, 0, length)
        }
    }
}