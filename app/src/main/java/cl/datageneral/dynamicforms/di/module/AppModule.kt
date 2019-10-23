package cl.datageneral.dynamicforms.di.module

import android.content.Context
import cl.datageneral.dynamicforms.DGApplication
import cl.datageneral.dynamicforms.data.DataManager
import cl.datageneral.dynamicforms.data.db.Query
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * Created by Pablo Molina on 29-03-2019. s.pablo.molina@gmail.com
 */
@Module
abstract class AppModule {

    @Binds
    abstract fun provideContext(application: DGApplication) : Context

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideDataManager(q:Query): DataManager = DataManager(q)

        @JvmStatic
        @Provides
        fun provideQuery(): Query = Query()
    }
}