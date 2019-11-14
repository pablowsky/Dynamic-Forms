package cl.datageneral.dynamicforms.ui.main

import cl.datageneral.dynamicforms.data.DataManager
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * Created by Pablo Molina on 14-10-2019. s.pablo.molina@gmail.com
 */
@Module
abstract class FormModule{

    @Binds
    abstract fun provideFormActivityView(formActivity: FormActivity): FormContract.View

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideFormPresenter(dm: DataManager): FormPresenter<FormContract.View> = FormPresenter(dm)

        /*@JvmStatic
        @Provides
        fun provideJsonForm(activity: FormActivity): JsonFormEvents =
            JsonFormEvents(activity)*/
    }
}