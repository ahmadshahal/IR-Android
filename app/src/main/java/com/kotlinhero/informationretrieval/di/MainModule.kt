package com.kotlinhero.informationretrieval.di

import com.kotlinhero.informationretrieval.data.api.DocumentsApi
import com.kotlinhero.informationretrieval.ui.viewmodels.DocumentsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val MainModule = module {
    factory { DocumentsApi(get()) }
    viewModel { DocumentsViewModel(get()) }
}