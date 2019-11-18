package pablo.molina.jsonform

import android.graphics.drawable.Drawable

/**
 * Created by Pablo Molina on 14-11-2019. s.pablo.molina@gmail.com
 */
data class FormStyles(val name:String) {
    var messageError = String()
    var spinnerNormal: Drawable? = null
    var spinnerError: Drawable? = null
    var spinnerSelected: Int = 0

    var dateImage: Int = 0
    var dateBackgroundColor: Int = 0
    var timeImage: Int = 0
    var timeBackgroundColor: Int = 0

    var editTextNormal: Int = 0
    var editTextError: Int = 0

    var labelNormal: Int = 0
    var labelError: Int = 0

    var title: Int = 0
}
