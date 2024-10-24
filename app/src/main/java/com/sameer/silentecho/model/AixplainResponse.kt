package com.sameer.silentecho.model

data class AixplainResponse(
    val data: String, // URL to poll for results
    val completed: Boolean // Add any other relevant fields from response
)

data class PollingResponse(
    val completed: Boolean,
    val data: String // The final result from the model
)
