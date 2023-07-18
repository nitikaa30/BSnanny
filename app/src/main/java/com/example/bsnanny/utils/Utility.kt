package com.example.bsnanny.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.location.LocationEngineCallback
import com.mapbox.android.core.location.LocationEngineResult
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.geojson.BoundingBox
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.toCameraOptions
import com.mapbox.search.result.SearchAddress
import com.mapbox.search.ui.view.place.SearchPlace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.Calendar

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): NetworkResults<T> {

    // Returning api response
    // wrapped in Resource class
    return withContext(Dispatchers.IO) {
        try {

            // Here we are calling api lambda
            // function that will return response
            // wrapped in Retrofit's Response class
            val response: Response<T> = apiToBeCalled()

            if (response.isSuccessful) {
                // In case of success response we
                // are returning Resource.Success object
                // by passing our data in it.
                NetworkResults.Success(data = response.body()!!)
            } else {
                // parsing api's own custom json error
                // response in ExampleErrorResponse pojo
                // val errorResponse: ExampleErrorResponse? = convertErrorBody(response.errorBody())
                // Simply returning api's own failure message
                var messageError = ""
                val jbjError = JSONObject(response.errorBody()!!.string())
                val jbjError1 = jbjError.getJSONObject("errors")
                messageError = if (jbjError1.getString("type") == "type") {
                    jbjError1.get("msg").toString()
                } else {
                    val jbjError2 = jbjError1.getJSONObject("raw")
                    jbjError2.get("message").toString()
                }


                NetworkResults.Error(errorMessage = messageError)
            }

        } catch (e: HttpException) {
            // Returning HttpException's message
            // wrapped in Resource.Error
            NetworkResults.Error(errorMessage = e.message ?: "Something went wrong")
        } catch (e: IOException) {
            // Returning no internet message
            // wrapped in Resource.Error
            NetworkResults.Error("Please check your network connection")
        } catch (e: Exception) {
            // Returning 'Something went wrong' in case
            // of unknown error wrapped in Resource.Error
            NetworkResults.Error(errorMessage = "Something went wrong: $e")
        } catch (e: SocketTimeoutException) {
            NetworkResults.Error(errorMessage = "Timeout")
        }
    }
}

fun showSnackBar(view: View, string: String, color: String) {
    val snackBar = Snackbar.make(view, string, Snackbar.LENGTH_SHORT)
    val snackBarView = snackBar.view
    snackBar.setTextColor(Color.WHITE)
    val tv = snackBar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)

    tv.textSize = 16F
    tv.typeface = Typeface.DEFAULT_BOLD

    snackBarView.setBackgroundColor(Color.parseColor(color))
    snackBar.show()
}

fun isCreditCardExpired(expiryYear: Int, expiryMonth: Int): String? {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1

    if (expiryYear < currentYear || (expiryYear == currentYear && expiryMonth < currentMonth)) {
        return "Credit card has expired"
    }

    if (expiryYear > currentYear + 50) {
        return "Invalid expiry year. Maximum allowed year is ${currentYear + 50}"
    }

    if (expiryMonth > 12) {
        return "Invalid expiry month"
    }

    return null
}
fun EditText.setEditTextCompoundDrawable(
    start: Int = 0,
    top: Int = 0,
    @DrawableRes end: Int,
    bottom: Int = 0
){
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom)
}
fun validateCard(input: String,
                 errorMsg: String = "",
                 textInputLayout: TextInputLayout?,
                 cardFields: CardFields) {
    when (cardFields) {
        CardFields.CVV -> {
            if (TextUtils.isEmpty(input.trim())){
                textInputLayout?.error = errorMsg
            }else{
                textInputLayout?.error = null
                textInputLayout?.isErrorEnabled = false
            }
        }
        CardFields.EXPIRY -> {
            if (TextUtils.isEmpty(input.trim())){
                textInputLayout?.error = errorMsg
            }else{
                textInputLayout?.error = null
                textInputLayout?.isErrorEnabled = false
            }

        }
        CardFields.CARDHOLDER_NAME -> {
            if (TextUtils.isEmpty(input.trim())) {
                textInputLayout?.error = errorMsg
            } else {
                textInputLayout?.error = null
                textInputLayout?.isErrorEnabled = false
            }
        }
        CardFields.CARD_NO -> {
            if (TextUtils.isEmpty(input.trim())){
                textInputLayout?.error = errorMsg
            }else{
                textInputLayout?.error = null
                textInputLayout?.isErrorEnabled = false
            }
        }
    }
}

enum class CardFields {
    CVV,
    EXPIRY,
    CARDHOLDER_NAME,
    CARD_NO,
}

const val placesAutoCompleteTag = "PlacesAutoComplete"



