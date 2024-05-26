package com.innoprog.eventplanner.data.network.client

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.innoprog.eventplanner.data.network.ForecaApi
import com.innoprog.eventplanner.network.Request
import com.innoprog.eventplanner.network.Response
import retrofit2.HttpException
import javax.inject.Inject

class NetworkClientImpl @Inject constructor(
    private val context: Context,
    private val api: ForecaApi
) : NetworkClient {

    override suspend fun doRequest(request: Request): Response {

        if (!isConnected()) {
            return Response().apply { resultCode = NO_INTERNET_CONNECTION_CODE }
        }

        var response = Response()
        return try {
            response = when (request) {
                is Request.GetLocation -> {
                    api.getLocations(
                        query = request.query
                    )
                }

                is Request.GetForecast -> {
                    api.getForecast(
                        locationId = request.locationId
                    )
                }
            }
            response.apply { resultCode = SUCCESS_CODE }

        } catch (exception: HttpException) {
            response.apply { resultCode = exception.code() }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> return true
            }
        }
        return false
    }

    companion object {
        private const val NO_INTERNET_CONNECTION_CODE = -1
        private const val SUCCESS_CODE = 200
    }
}