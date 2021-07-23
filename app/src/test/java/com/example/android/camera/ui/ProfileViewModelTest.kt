package com.example.android.camera.ui

import androidx.lifecycle.Observer
import com.example.android.camera.InstantExecutorExtension
import com.example.android.camera.TestCoroutinesProvider
import com.example.android.camera.network.FileUploadRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.mockito.Mock
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

@ExperimentalCoroutinesApi
internal class ProfileViewModelTest {
    @RegisterExtension
    @JvmField
    val instantExecutorExtension: InstantExecutorExtension =
        InstantExecutorExtension()


    @Mock
    val repo : FileUploadRepo = mock()

    lateinit var sut: ProfileViewModel
    @BeforeEach
    fun setUp() {
        sut= ProfileViewModel(repo,TestCoroutinesProvider(instantExecutorExtension.testCoroutineDispatcher))
    }


    @Test
    fun setAddButtonClicked() {
        /*instantExecutorExtension.runBlockingTest {  }*/
        val expectedValue = true
        val addButtonClickedObserver: Observer<Boolean> = mock()
        sut.addButtonClicked.observeForever(addButtonClickedObserver)

        sut.setAddButtonClicked(expectedValue)

        val actualResult = sut.addButtonClicked.value

        verify(addButtonClickedObserver,times(1)).onChanged(expectedValue)
        verifyNoMoreInteractions(addButtonClickedObserver)
    }

    @Test
    fun setMissingData() {
    }
}