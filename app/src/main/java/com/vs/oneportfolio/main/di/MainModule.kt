package com.vs.oneportfolio.main.di

import com.vs.oneportfolio.main.presentaion.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mainModule = module {
    viewModelOf(::HomeViewModel)
}
