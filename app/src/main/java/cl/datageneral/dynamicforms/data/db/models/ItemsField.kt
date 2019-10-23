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
open class ItemsField : RealmObject() {
	@PrimaryKey
	@Required
	var itemsFieldId:	Int? 		= null
	var value:			String? 	= null
	var nonconformity:	Boolean		= false
	var answerfield:    RealmList<AnswerField> = RealmList()

	@LinkingObjects("items")
	val field: RealmResults<Field>? = null
}