package com.sameer.silentecho.view
import com.sameer.silentecho.model.AixplainRequest
import com.sameer.silentecho.model.AixplainResponse
import com.sameer.silentecho.model.PollingResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.GET

interface AixplainApiService {
    @Headers("Content-Type: application/json", "x-api-key: c6768ad9216bd3ec5e487f929f09932dcad90401f6312c31635a06657b6cca7b")
    @POST("api/v1/execute/640b517694bf816d35a59125")
    fun runModel(@Body requestBody: AixplainRequest): Call<AixplainResponse>

    @Headers("x-api-key: c6768ad9216bd3ec5e487f929f09932dcad90401f6312c31635a06657b6cca7b", "Content-Type: application/json")
    @GET
    fun pollModel(@retrofit2.http.Url url: String): Call<PollingResponse>
}
