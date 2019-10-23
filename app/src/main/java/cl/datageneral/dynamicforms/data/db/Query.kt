package cl.datageneral.dynamicforms.data.db

import android.util.Log
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults
import io.realm.kotlin.where
import io.realm.RealmConfiguration
import cl.datageneral.dynamicforms.data.db.models.Answer
import cl.datageneral.dynamicforms.data.db.models.Field


/**
 * Created by Pablo Molina on 19-07-2018. s.pablo.molina@gmail.com
 */
open class Query{
    val realm: Realm

    init {
        println("First initializer block that prints Query")
		val buffer = ByteArray(64)
		val config = RealmConfiguration.Builder()
				.name("myrealm_designtv.realm")
				.encryptionKey(buffer)
				.build()
		Realm.setDefaultConfiguration(config)
        realm = Realm.getDefaultInstance()
    }

	fun getFields(formId: Int): MutableList<Field> {
		val results = realm.where<Field>()
			.equalTo("form.formId",formId)
			.findAll()
		return realm.copyFromRealm(results)
	}

	fun close(){
		realm.close()
	}

}