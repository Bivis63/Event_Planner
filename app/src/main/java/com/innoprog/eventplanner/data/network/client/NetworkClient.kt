package com.innoprog.eventplanner.data.network.client

import com.innoprog.eventplanner.network.Request
import com.innoprog.eventplanner.network.Response

interface NetworkClient {
    suspend fun doRequest(request: Request): Response
}