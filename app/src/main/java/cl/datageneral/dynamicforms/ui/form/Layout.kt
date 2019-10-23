package cl.datageneral.dynamicforms.ui.form

import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.widget.LinearLayout
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Pablo Molina on 16-10-2019. s.pablo.molina@gmail.com
 */
class Layout {

    companion object {
        val p1 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val p2 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val p3 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        /*val p3 = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1.0f
        )*/

        // object
        fun GroupParams(weight: Float?): LinearLayout.LayoutParams {
            return if(weight==null){
                p1
            }else{
                hWeightParam(weight)
            }
        }

        // object
        fun CheckBoxParams(/*context: Context, */weight: Float?): LinearLayout.LayoutParams {
            return if(weight==null){
                p1
            }else{
                hWeightParam(weight)
            }
        }

        /*// object
        fun GroupParams(
            preferedOrientation: String?,
            context: Context,
            weight: Float=2.0f
        ): LinearLayout.LayoutParams {
            var orientation = preferedOrientation
            if (preferedOrientation.isNullOrEmpty()) {
                orientation = procOrientation(context)
            }
            Log.e("EditTextParams", orientation)
            return if (orientation == "vertical") {
                p2
            } else {
                hWeightParam(weight)
            }
        }*/

        // object
        fun EditTextParams(
            preferedOrientation: String?,
            context: Context,
            weight: Float=2.0f
        ): LinearLayout.LayoutParams {
            var orientation = preferedOrientation
            if (preferedOrientation.isNullOrEmpty()) {
                orientation = procOrientation(context)
            }
            //Log.e("EditTextParams", orientation)
            return if (orientation == "vertical") {
                p2
            } else {
                hWeightParam(weight)
            }
        }

        fun LabelParams(preferedOrientation: String?, context: Context, weight: Float=1.0f): LinearLayout.LayoutParams {
            var orientation = preferedOrientation
            if (preferedOrientation.isNullOrEmpty()) {
                orientation = procOrientation(context)
            }
            //Log.e("LabelParams", orientation)
            return if (orientation == "vertical") {
                p2
            } else {
                hWeightParam(weight)
            }
        }

        fun BoxParams(): LinearLayout.LayoutParams {
            return p2
        }

        fun procOrientation(context: Context, opposite:Boolean=false): String {
            val currOri = context.resources.configuration.orientation
            return if (currOri == Configuration.ORIENTATION_LANDSCAPE) {
                if(opposite) {
                    "horizontal"
                }else{
                    "vertical"
                }
            } else {
                if(opposite) {
                    "vertical"
                }else{
                    "horizontal"
                }
            }
        }

        fun getLayourOrientation(orientation: String?): Int {
            return if (orientation == "vertical") {
                LinearLayout.VERTICAL
            } else {
                LinearLayout.HORIZONTAL
            }
        }

        fun hWeightParam(weight:Float):LinearLayout.LayoutParams{
            return LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                weight
            )
        }

    }
}