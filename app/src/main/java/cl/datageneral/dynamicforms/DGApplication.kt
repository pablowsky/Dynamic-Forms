package cl.datageneral.dynamicforms

import cl.datageneral.dynamicforms.di.component.DaggerAppComponent
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.realm.Realm

/**
 * Created by Pablo Molina on 18-07-2018. s.pablo.molina@gmail.com
 */
class DGApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        Stetho.initializeWithDefaults(this);
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder()
                .application(this)
                .build()
        appComponent.inject(this)
        return appComponent
    }
}