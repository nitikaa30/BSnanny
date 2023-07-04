package com.example.bsnanny

import android.content.res.Resources
import com.example.bsnanny.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException

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
                NetworkResults.Error(errorMessage = "Something went wrong")
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
        }
    }
}

// If you don't wanna handle api's own
// custom error response then ignore this function
//private fun convertErrorBody(errorBody: ResponseBody?): ExampleErrorResponse? {
//    return try {
//        errorBody?.source()?.let {
//            val moshiAdapter = Moshi.Builder().build().adapter(ExampleErrorResponse::class.java)
//            moshiAdapter.fromJson(it)
//        }
//    } catch (exception: Exception) {
//        null
//    }
//}