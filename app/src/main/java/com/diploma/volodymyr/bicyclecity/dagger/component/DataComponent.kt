package com.diploma.volodymyr.bicyclecity.dagger.component

import com.diploma.volodymyr.bicyclecity.dagger.module.RepositoryModule
import dagger.Component

@Component(modules = [RepositoryModule::class])
interface DataComponent {
}