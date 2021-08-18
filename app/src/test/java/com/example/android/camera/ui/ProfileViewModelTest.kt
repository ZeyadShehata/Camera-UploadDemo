package com.example.android.camera.ui

import android.graphics.Bitmap
import androidx.lifecycle.Observer
import com.example.android.camera.InstantExecutorExtension
import com.example.android.camera.TestCoroutinesProvider
import com.example.android.camera.network.FileUploadRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.ByteArrayOutputStream

@ExperimentalCoroutinesApi
internal class ProfileViewModelTest {
    @RegisterExtension
    @JvmField
    val instantExecutorExtension: InstantExecutorExtension =
        InstantExecutorExtension()


    @Mock
    val repo: FileUploadRepo = Mockito.mock(FileUploadRepo::class.java)

    @Mock
    val bitmapMock = mock<Bitmap>()
    val bos = ByteArrayOutputStream()
    var observer = mock<Observer<Boolean>>()
    var observer2 = mock<Observer<String>>()
    var observer3 = mock<Observer<Bitmap>>()
    var observer4 = mock<Observer<Boolean>>()
    var observer5 = mock<Observer<Boolean>>()
    lateinit var sut: ProfileViewModel
    // val fileUploadResponse = UploadResponseModel("zc9kfb7ied8sfk2dhf397itzms66q0kw","z.bmp","bmp","https://v2.convertapi.com/d/zc9kfb7ied8sfk2dhf397itzms66q0kw")

    @BeforeEach
    fun setUp() {

        bitmapMock.compress(Bitmap.CompressFormat.PNG, 90, bos)
        sut = ProfileViewModel(
            repo,
            TestCoroutinesProvider(instantExecutorExtension.testCoroutineDispatcher)
        )

    }


    @Test
    fun setAddButtonClicked() {
        //  instantExecutorExtension.runBlockingTest {
        // val actualResultList = mutableListOf<Boolean>()
        var expectedResult = true
        sut.addButtonClicked.observeForever(observer)
        sut.setAddButtonClicked(true)
        assertEquals(expectedResult, sut.addButtonClicked.value)
        sut.setAddButtonClicked(false)
        expectedResult = false
        assertEquals(expectedResult, sut.addButtonClicked.value)
        sut.setAddButtonClicked(true)
        expectedResult = true
        assertEquals(expectedResult, sut.addButtonClicked.value)
        verify(observer, times(2)).onChanged(true)
        verify(observer, times(1)).onChanged(false)
        sut.addButtonClicked.removeObserver(observer)
    }

    @Test
    fun setMissingData() {
        sut.missingData.observeForever(observer)

        var expectedResult = false
        sut.setMissingData(false)
        assertEquals(expectedResult, sut.missingData.value)

        expectedResult = true
        sut.setMissingData(true)
        assertEquals(expectedResult, sut.missingData.value)

        verify(observer, times(1)).onChanged(false)
        verify(observer, times(1)).onChanged(true)
        sut.missingData.removeObserver(observer)
    }

    @Test
    fun setWaitingForReply() {
        sut.waitingForReply.observeForever(observer)

        var expectedResult = false
        sut.setWaitingForReply(false)
        assertEquals(expectedResult, sut.waitingForReply.value)

        expectedResult = true
        sut.setWaitingForReply(true)
        assertEquals(expectedResult, sut.waitingForReply.value)

        verify(observer, times(1)).onChanged(false)
        verify(observer, times(1)).onChanged(true)
        sut.waitingForReply.removeObserver(observer)
    }

    @Test
    fun setCameraButtonClicked() {
        sut.cameraButtonClicked.observeForever(observer)

        var expectedResult = false
        sut.setCameraButtonClicked(false)
        assertEquals(expectedResult, sut.cameraButtonClicked.value)

        expectedResult = true
        sut.setCameraButtonClicked(true)
        assertEquals(expectedResult, sut.cameraButtonClicked.value)

        verify(observer, times(1)).onChanged(false)
        verify(observer, times(1)).onChanged(true)
        sut.cameraButtonClicked.removeObserver(observer)
    }

    @Test
    fun setGalleryButtonClicked() {
        sut.galleryButtonClicked.observeForever(observer)

        var expectedResult = false
        sut.setGalleryButtonClicked(false)
        assertEquals(expectedResult, sut.galleryButtonClicked.value)

        expectedResult = true
        sut.setGalleryButtonClicked(true)
        assertEquals(expectedResult, sut.galleryButtonClicked.value)

        verify(observer, times(1)).onChanged(false)
        verify(observer, times(1)).onChanged(true)
        sut.galleryButtonClicked.removeObserver(observer)
    }

    @Test
    fun setUploadSuccess() {
        sut.uploadSuccess.observeForever(observer)

        var expectedResult = false
        sut.setUploadSuccesss(false)
        assertEquals(expectedResult, sut.uploadSuccess.value)

        expectedResult = true
        sut.setUploadSuccesss(true)
        assertEquals(expectedResult, sut.uploadSuccess.value)

        verify(observer, times(1)).onChanged(false)
        verify(observer, times(1)).onChanged(true)
        sut.uploadSuccess.removeObserver(observer)
    }

    @Test
    fun setUploadFail() {
        sut.uploadFail.observeForever(observer)

        var expectedResult = false
        sut.setUploadFail(false)
        assertEquals(expectedResult, sut.uploadFail.value)

        expectedResult = true
        sut.setUploadFail(true)
        assertEquals(expectedResult, sut.uploadFail.value)

        verify(observer, times(1)).onChanged(false)
        verify(observer, times(1)).onChanged(true)
        sut.uploadFail.removeObserver(observer)
    }

    @Test
    fun setFileName() {
        sut.fileName.observeForever(observer2)

        var expectedResult = "sdasasdasd"
        sut.setFileName("sdasasdasd")
        assertEquals(expectedResult, sut.fileName.value)

        expectedResult = "harro"
        sut.setFileName("harro")
        assertEquals(expectedResult, sut.fileName.value)

        verify(observer2, times(1)).onChanged("sdasasdasd")
        verify(observer2, times(1)).onChanged("harro")
        sut.fileName.removeObserver(observer2)
    }

    @Test
    fun setImageBitmap() {
        sut.imageBitmap.observeForever(observer3)

        val expectedResult = bitmapMock
        sut.setImageBmap(bitmapMock)
        assertEquals(expectedResult, sut.imageBitmap.value)

        verify(observer3, times(1)).onChanged(bitmapMock)

        sut.imageBitmap.removeObserver(observer3)
    }

    @Test
    fun uploadPicture() {
        var expectedUploadResult = true
        var expectedFailure = false
        var missingData = false
        sut.uploadSuccess.observeForever(observer)
        sut.uploadFail.observeForever(observer4)
        sut.missingData.observeForever(observer5)
        instantExecutorExtension.runBlockingTest {
            sut.setImageBmap(bitmapMock)
            sut.setFileName("z")
            val bos = ByteArrayOutputStream()
            bitmapMock.compress(Bitmap.CompressFormat.PNG, 90, bos)

            //successful upload test
            whenever(repo.getPhoto(sut.fileName.value.toString(), bos.toByteArray())).thenReturn(
                true
            )
            sut.uploadPicture()
            assertEquals(expectedUploadResult, sut.uploadSuccess.value)
            assertEquals(expectedFailure, sut.uploadFail.value)
            assertEquals(missingData, sut.missingData.value)

            //failed upload test
            expectedUploadResult = false
            expectedFailure = true
            whenever(repo.getPhoto(sut.fileName.value.toString(), bos.toByteArray())).thenReturn(
                false
            )
            sut.uploadPicture()
            assertEquals(expectedUploadResult, sut.uploadSuccess.value)
            assertEquals(expectedFailure, sut.uploadFail.value)
            assertEquals(missingData, sut.missingData.value)

            //missing file name and/or image bitmap
            missingData = true
            sut.setFileName("")
            sut.uploadPicture()
            assertEquals(missingData, sut.missingData.value)
            verify(observer,times(1)).onChanged(true)
            verify(observer,times(1)).onChanged(false)
            verify(observer4,times(1)).onChanged(true)
            verify(observer4,times(1)).onChanged(false)
            verify(observer5, times(1)).onChanged(true)

        }
//        verifyNoMoreInteractions(repo)
    }
}