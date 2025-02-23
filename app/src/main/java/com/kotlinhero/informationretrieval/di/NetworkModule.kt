package com.kotlinhero.informationretrieval.di

import com.kotlinhero.informationretrieval.data.exceptions.ServerException
import com.kotlinhero.informationretrieval.data.models.ErrorResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import timber.log.Timber

val NetworkModule = module {
    single {
        HttpClient(Android) {
            expectSuccess = true
            HttpResponseValidator {
                validateResponse {
                    /*
                    * NOTE: This only intercepts successful responses.
                    */
                }
                handleResponseExceptionWithRequest { exception, _ ->
                    val clientException = exception as? ClientRequestException
                        ?: return@handleResponseExceptionWithRequest
                    /*
                    * NOTE: This only intercepts failed responses.
                    */
                    val exceptionResponse = clientException.response.body<ErrorResponse>()
                    throw ServerException(
                        message = exceptionResponse.message,
                        code = exceptionResponse.code
                    )
                }
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            engine {
                connectTimeout = 6000 // 15 Seconds
                socketTimeout = 6000 // 15 Seconds
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.d("Logger Ktor => $message")
                    }
                }
                level = LogLevel.ALL
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }
}