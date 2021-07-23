package com.example.android.camera

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.TestWatcher


@ExperimentalCoroutinesApi
class InstantExecutorExtension : BeforeEachCallback, AfterEachCallback, TestWatcher {

    val testCoroutineDispatcher = TestCoroutineDispatcher()
    val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)
    @ExperimentalCoroutinesApi
    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain( testCoroutineDispatcher)
        ArchTaskExecutor.getInstance()
            .setDelegate(object : TaskExecutor() {
                override fun executeOnDiskIO(runnable: Runnable) = runnable.run()

                override fun postToMainThread(runnable: Runnable) = runnable.run()

                override fun isMainThread(): Boolean = true
            })
    }

    @ExperimentalCoroutinesApi
    override fun afterEach(context: ExtensionContext?) {
        ArchTaskExecutor.getInstance().setDelegate(null)
        Dispatchers.resetMain()
        testCoroutineScope.cleanupTestCoroutines()
    }
    fun runBlockingTest(block:suspend TestCoroutineScope.() -> Unit) = testCoroutineScope.runBlockingTest{block()}
}

