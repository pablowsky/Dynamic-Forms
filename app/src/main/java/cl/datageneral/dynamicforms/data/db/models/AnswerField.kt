package cl.datageneral.dynamicforms.data.db.models

import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

/**
 * Created by Pablo Molina on 09-10-2019. s.pablo.molina@gmail.com
 */
open class AnswerField : RealmObject() {
	@PrimaryKey
	@Required
	var answerFieldId:	Int? 		= null
	var result:			String? 	= null


	@LinkingObjects("answerfields")
	val answer: RealmResults<Answer>? = null

	@LinkingObjects("answerfield")
	val itemfield: RealmResults<ItemsField>? = null
}