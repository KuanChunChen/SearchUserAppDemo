package elegant.access.apidemo.http

import com.google.gson.*
import elegant.access.apidemo.base.BaseConstants
import elegant.access.apidemo.http.deserialize.*
import elegant.access.apidemo.http.interceptor.RetryRequestByFailed
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.net.Proxy
import java.util.concurrent.TimeUnit

object RetrofitClient {


    private var gsonBuilder: GsonBuilder = GsonBuilder()


    init {
        gsonBuilder
            .setPrettyPrinting()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

//        gsonBuilder.registerTypeAdapter(Result::class.java, Deserializer())
        gsonBuilder.registerTypeAdapter(Boolean::class.java, BooleanDeserializer())
            .registerTypeAdapter(Boolean::class.javaPrimitiveType, BooleanDeserializer())
            .registerTypeAdapter(Int::class.java, IntegerDeserializer())
            .registerTypeAdapter(Integer.TYPE, IntegerDeserializer())
            .registerTypeAdapter(String::class.java, StringDeserializer())
            .registerTypeAdapter(Float::class.java, FloatDeserializer())
            .registerTypeAdapter(Float::class.javaPrimitiveType, FloatDeserializer())
            .registerTypeAdapter(Long::class.java, LongDeserializer())
            .registerTypeAdapter(Long::class.javaPrimitiveType, LongDeserializer())
            .registerTypeAdapter(Double::class.java, DoubleDeserializer())
            .registerTypeAdapter(Double::class.javaPrimitiveType, DoubleDeserializer())
    }

    @Synchronized
    fun http(
        hostName: String = BaseConstants.Http.HTTP_GITHUB_SERVER,
        connectTimeout: Long = 20,
        readTimeout: Long = 30,
        writeTimeout: Long = 30
    ): Retrofit {

        synchronized(RetrofitClient::class.java) {

            val okHttpClient = build(
                connectTimeout = connectTimeout,
                readTimeout = readTimeout,
                writeTimeout = writeTimeout
            )


            return Retrofit.Builder()
                .baseUrl(hostName)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }


    }


    @JvmOverloads
    fun build(showBodyLog: Boolean = true,
              connectTimeout: Long = 20,
              readTimeout:Long = 30,
              writeTimeout:Long = 30,
              retryRequest: RetryRequestByFailed = RetryRequestByFailed.Builder().build()): OkHttpClient.Builder {


        val httpBuilder = OkHttpClient().newBuilder()
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
            .addInterceptor(retryRequest)
            .retryOnConnectionFailure(true)
            .proxy(Proxy.NO_PROXY)

        if (BaseConstants.Http.isDebugMode) {
            val loggingInterceptor = HttpLoggingInterceptor()

            if (showBodyLog) {
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
            }

            httpBuilder.addInterceptor(loggingInterceptor)
        }

        httpBuilder.connectionPool(ConnectionPool(0, 1, TimeUnit.SECONDS))
        return httpBuilder
    }

    class Deserializer : JsonDeserializer<Result<*>> {
        @Throws(JsonParseException::class)
        override fun deserialize(jElement: JsonElement, type: Type, jdContext: JsonDeserializationContext): Result<Any>? {
            println("jElement : $jElement , type: $type , jdContext: $jdContext")
            return Gson().fromJson(jElement, type)
        }
    }
}
