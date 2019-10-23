package cl.datageneral.dynamicforms.data

import cl.datageneral.dynamicforms.data.db.Query
import cl.datageneral.dynamicforms.data.db.models.Field
import org.json.JSONArray

/**
 * Created by Pablo Molina on 04-04-2019. s.pablo.molina@gmail.com
 */
class DataManager(val query: Query){
	/*private var mCompositeDisposable: CompositeDisposable? = null

	init{
		mCompositeDisposable = CompositeDisposable()
	}*/

	fun getFields(formId: Int): MutableList<Field>?{
		return query.getFields(formId)
	}





	/*fun getSessionStatus():SessionStatus{
		val status = sPrefs.get("USER_SESSION_STATUS")
		return when(status){
			"1" -> SessionStatus.LOGGED_IN
			"2" -> SessionStatus.FIRST_USE
			"3" -> SessionStatus.LOGGED_OUT
			"4" -> SessionStatus.SESSION_ENDED
			else -> SessionStatus.FIRST_USE
		}
	}

	fun getBiometricUsageOption():Boolean{
		return sPrefs.getBoolean("USER_FINGERPRINT_USAGE")
	}

	fun setFingerprintUsage(value:Boolean){
		sPrefs.set("USER_FINGERPRINT_USAGE",value)
	}

	fun getUser():String{
		return sPrefs.get("USER_SESSION_USERNAME")!!
	}

	fun getPassword():String{
		return sPrefs.get("USER_SESSION_PASSWORD")!!
	}*/

	fun setUser(value:String){
		//sPrefs.set("USER_SESSION_USERNAME",value)
	}

	fun setPassword(value:String){
		//sPrefs.set("USER_SESSION_PASSWORD",value)
	}

	/*fun setSessionStatus(v:SessionStatus){
		val vset = when(v){
			SessionStatus.LOGGED_IN -> "1"
			SessionStatus.FIRST_USE -> "2"
			SessionStatus.LOGGED_OUT -> "3"
			SessionStatus.SESSION_ENDED -> "4"
		}
		sPrefs.set("USER_SESSION_STATUS",vset)
	}

	fun validateCredentiales(user:String, pass:String): Observable<Boolean> {
		// Simulador de red
		// Validacin falsa
		var resp = false
		if(user == "123" && pass=="admin"){
			resp = true
		}
		return Observable.just(resp).delay(2, TimeUnit.SECONDS)
	}*/
}