package com.curso.android.module4.cityspots.utils

import android.content.Context
import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

// ✅ Parte 1: errores tipados (3+)
sealed class CaptureError {
    data object CameraClosedUnexpectedly : CaptureError()
    data object CaptureFailed : CaptureError()
    data object FileIoError : CaptureError()
}

// ✅ Resultado tipado de captura
sealed class CapturePhotoResult {
    data class Success(val uri: Uri) : CapturePhotoResult()
    data class Failure(val error: CaptureError) : CapturePhotoResult()
}

class CameraUtils(private val context: Context) {

    private val fileNameFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)

    fun createImageFile(): File {
        val storageDir = context.filesDir
        val timeStamp = fileNameFormat.format(Date())
        val fileName = "spot_$timeStamp.jpg"
        return File(storageDir, fileName)
    }

    /**
     * ✅ Challenge: NO lanza excepción hacia arriba.
     * Devuelve Success(uri) o Failure(CaptureError).
     */
    suspend fun capturePhoto(imageCapture: ImageCapture): CapturePhotoResult =
        suspendCoroutine { continuation ->

            val photoFile = createImageFile()
            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        continuation.resume(CapturePhotoResult.Success(Uri.fromFile(photoFile)))
                    }

                    override fun onError(exception: ImageCaptureException) {
                        // limpiar archivo si se alcanzó a crear
                        photoFile.delete()

                        val mapped = when (exception.imageCaptureError) {
                            ImageCapture.ERROR_CAMERA_CLOSED ->
                                CaptureError.CameraClosedUnexpectedly

                            ImageCapture.ERROR_FILE_IO ->
                                CaptureError.FileIoError

                            // incluye ERROR_CAPTURE_FAILED y cualquier otro
                            else ->
                                CaptureError.CaptureFailed
                        }

                        continuation.resume(CapturePhotoResult.Failure(mapped))
                    }
                }
            )
        }

    fun deleteImage(uri: Uri): Boolean {
        return try {
            val file = File(uri.path ?: return false)
            if (file.exists()) file.delete() else true
        } catch (_: Exception) {
            false
        }
    }
}
