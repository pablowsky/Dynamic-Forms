package cl.datageneral.dynamicforms.di.module

import cl.datageneral.dynamicforms.ui.main.FormActivity
import cl.datageneral.dynamicforms.ui.main.FormModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [FormModule::class])
    abstract fun bindFormActivity(): FormActivity


}