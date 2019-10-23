package cl.datageneral.dynamicforms.data.db.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

/**
 * Created by Pablo Molina on 29-11-2018. s.pablo.molina@gmail.com
 */
open class Answer : RealmObject() {
	@PrimaryKey
	@Required
	var answerId:		Int? 	= null
	var status:			String		= NO_SINC // Sincronizado, No sincronizado, Eliminado
	var answerfields: 	RealmList<AnswerField> 	= RealmList()



	companion object {
		val NO_SINC = "N"
		val SINC    = "S"
		val ELIMINADO    = "E"
	}
}