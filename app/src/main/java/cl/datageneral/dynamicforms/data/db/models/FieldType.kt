package cl.datageneral.dynamicforms.data.db.models

import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

/**
 * Created by Pablo Molina on 09-10-2019. s.pablo.molina@gmail.com
 */
open class FieldType : RealmObject() {
	@PrimaryKey
	@Required
	var fieldTypeId:		Int? 	= null
	var description:		String? = null

	@LinkingObjects("fieldtype")
	val field: RealmResults<Field>? = null
}