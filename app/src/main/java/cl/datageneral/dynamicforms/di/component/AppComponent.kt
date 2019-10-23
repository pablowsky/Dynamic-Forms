package cl.datageneral.dynamicforms.di.component

import cl.datageneral.dynamicforms.DGApplication
import cl.datageneral.dynamicforms.di.module.ActivityBindingModule
import cl.datageneral.dynamicforms.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Pablo Molina on 29-03-2019. s.pablo.molina@gmail.com
 */
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: DGApplication)

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: DGApplication): Builder
        fun build(): AppComponent
    }
}