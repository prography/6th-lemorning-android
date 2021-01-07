package org.prography.lemorning.src.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import org.prography.lemorning.ApplicationClass.Companion.sharedPref
import org.prography.lemorning.src.utils.Constants.X_ACCESS_TOKEN

class XAccessTokenInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val builder = chain.request().newBuilder()
    val jwtToken: String? = sharedPref.getString(X_ACCESS_TOKEN, null)
    if (jwtToken != null) {
      builder.addHeader("Authorization", "Bearer $jwtToken")
    }
    return chain.proceed(builder.build())
  }
}