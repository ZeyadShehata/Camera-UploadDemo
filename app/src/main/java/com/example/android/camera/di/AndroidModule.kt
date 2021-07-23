package com.example.android.camera.di

import com.example.android.camera.network.FileAPI
import com.example.android.camera.network.FileUploadApi
import com.example.android.camera.network.FileUploadRepo
import com.example.android.camera.ui.ProfileViewModel
import com.example.android.camera.utils.CoroutineContextProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {
    single{CoroutineContextProvider()}


    single<FileUploadApi> {FileAPI.retrofitService }
    single{FileUploadRepo(get())}
    viewModel{ProfileViewModel(get(),get())}
}