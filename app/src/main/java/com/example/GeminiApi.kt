package com.example

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@JsonClass(generateAdapter = true)
data class GenerateContentRequest(
    val contents: List<Content>,
    val systemInstruction: Content? = null
)

@JsonClass(generateAdapter = true)
data class Content(
    val parts: List<Part>
)

@JsonClass(generateAdapter = true)
data class Part(
    val text: String
)

@JsonClass(generateAdapter = true)
data class GenerateContentResponse(
    val candidates: List<Candidate>? = null
)

@JsonClass(generateAdapter = true)
data class Candidate(
    val content: Content? = null
)

interface GeminiApiService {
    @POST("v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GenerateContentRequest
    ): GenerateContentResponse
}

object RetrofitClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val service: GeminiApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        retrofit.create(GeminiApiService::class.java)
    }
}

object GeminiRepository {
    suspend fun generateResponse(prompt: String, systemPrompt: String? = null): String = withContext(Dispatchers.IO) {
        try {
            val apiKey = try { BuildConfig.GEMINI_API_KEY } catch (t: Throwable) { "" }
            if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
                // Key is not configured, trigger the automatic fallback helper
                return@withContext getLocalFallbackResponse(prompt)
            }

            val request = GenerateContentRequest(
                contents = listOf(Content(parts = listOf(Part(text = prompt)))),
                systemInstruction = systemPrompt?.let { Content(parts = listOf(Part(text = it))) }
            )

            val response = RetrofitClient.service.generateContent(apiKey, request)
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text 
                ?: getLocalFallbackResponse(prompt)
        } catch (t: Throwable) {
            t.printStackTrace()
            getLocalFallbackResponse(prompt)
        }
    }

    /**
     * Local Emotional Parser Fallback (satisfies reliable interaction even when offline
     * or when API Key is pending configuration)
     */
    private fun getLocalFallbackResponse(prompt: String): String {
        val normalized = prompt.lowercase()
        return when {
            normalized.contains("estres") || normalized.contains("estresada") || normalized.contains("estresado") -> {
                "Te entiendo perfectamente. El estrés puede hacernos sentir que cargamos con un peso inmenso. No tienes que resolver todo hoy. Te aconsejo respirar profundo y regalarte un espacio de 5 minutos."
            }
            normalized.contains("triste") || normalized.contains("bajoneada") || normalized.contains("bajoneado") || normalized.contains("llor") -> {
                "Siento mucho que te sientas así. Está bien no estar al cien por ciento todo el tiempo. ¿Quieres contarme qué te preocupa o prefieres que hagamos un breve ritual para liberar la mente?"
            }
            normalized.contains("cansada") || normalized.contains("cansado") || normalized.contains("agotad") || normalized.contains("sin fuerza") -> {
                "Tu cuerpo y tu mente te están pidiendo un descanso real. No te sabotees exigiéndote más hoy. Te propongo una infusión reconfortante o un ritual rápido antes de dormir."
            }
            normalized.contains("ansia") || normalized.contains("ansiosa") || normalized.contains("ansioso") || normalized.contains("nervio") -> {
                "La ansiedad nos hace vivir en el futuro. Vuelve aquí conmigo, al presente. Siente el suelo bajo tus pies. Respira lento en 4 tiempos... estás a salvo."
            }
            normalized.contains("saturad") || normalized.contains("muchas cosas") || normalized.contains("bloquead") -> {
                "Cuando hay mil cosas flotando en la cabeza, lo mejor es vaciarlas en papel. ¿Llevas tu diario a la mano? Escribe tres pendientes principales y dejemos el resto para mañana de forma amable."
            }
            else -> {
                "Comprendo lo que dices. Recuerda que no hay emociones buenas ni malas, solo mensajeros de lo que nuestro cuerpo necesita. Estoy aquí para acompañarte en tu autocuidado."
            }
        }
    }
}
