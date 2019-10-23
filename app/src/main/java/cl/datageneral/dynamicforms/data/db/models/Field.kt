package cl.datageneral.dynamicforms.data.db.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

/**
 * Created by Pablo Molina on 09-10-2019. s.pablo.molina@gmail.com
 */
open class Field : RealmObject() {
	@PrimaryKey
	@Required
	var fieldId:		Int? 		= null
	var fieldtype:		FieldType? 	= null
	var name:			String? 	= null
	var mandatory:		Boolean 	= false
	var length:			Int 		= 0
	var items: 			RealmList<ItemsField> = RealmList()

	@LinkingObjects("fields")
	val form: RealmResults<Form>? = null
}