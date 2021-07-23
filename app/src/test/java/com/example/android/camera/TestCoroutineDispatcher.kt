package com.example.android.camera

import com.example.android.camera.utils.CoroutineContextProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlin.coroutines.CoroutineContext

open class TestCoroutinesProvider(dispatcher: CoroutineDispatcher) : CoroutineContextProvider(){
    override val Main: CoroutineContext by lazy { dispatcher  }
    override val IO : CoroutineContext by lazy { dispatcher }
}