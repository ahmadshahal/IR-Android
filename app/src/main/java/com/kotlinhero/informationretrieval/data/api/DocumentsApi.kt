package com.kotlinhero.informationretrieval.data.api

import com.kotlinhero.informationretrieval.data.models.DataResponse
import com.kotlinhero.informationretrieval.data.models.Document
import com.kotlinhero.informationretrieval.data.models.Suggestion
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "http://192.168.1.102:8000"

class DocumentsApi(private val httpClient: HttpClient) {
    suspend fun getDocuments(query: String, dataset: String): List<Document> {
        return httpClient.get("$BASE_URL/ranking") {
            parameter("query", query)
            parameter("dataset", dataset)
        }
            .body<DataResponse<List<Document>>>()
            .data
    }

    suspend fun getSuggestions(query: String, dataset: String): List<Suggestion> {
        return httpClient.get("$BASE_URL/correct-query") {
            parameter("query", query)
            parameter("dataset", "$dataset-queries")
        }
            .body<DataResponse<List<Suggestion>>>()
            .data
    }
}