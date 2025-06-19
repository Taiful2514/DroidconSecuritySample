package com.example.droidconsecuritysample.util

import com.example.droidconsecuritysample.util.Constant.PINNING_URL
import okhttp3.Interceptor
import okhttp3.Response
import okio.ByteString

/**
 * @author taiful
 * @since 16/6/25
 */
class CustomInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val peerCertificates = response.handshake?.peerCertificates
        peerCertificates?.forEach { peerCertificate ->
            val shah56 = ByteString.of(*peerCertificate.publicKey.encoded).sha256().base64()
            if (response.request.url.host == PINNING_URL &&
                (shah56 == Constant.SSL_PIN)
            ) {
                return response
            }
        }
        throw Exception()
    }
}