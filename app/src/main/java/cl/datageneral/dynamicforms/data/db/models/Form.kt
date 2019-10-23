package cl.datageneral.dynamicforms.data.db.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

/**
 * Created by Pablo Molina on 06-11-2018. s.pablo.molina@gmail.com
 */
open class Form : RealmObject() {
	@PrimaryKey
	@Required
	var formId:			Int? 	= null
	var fields: 		RealmList<Field> = RealmList()
}