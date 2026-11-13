package com.example.data.firebase

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import org.json.JSONObject

enum class OperationType(val value: String) {
    CREATE("create"),
    UPDATE("update"),
    DELETE("delete"),
    LIST("list"),
    GET("get"),
    WRITE("write")
}

object FirestoreErrorHandler {
    private const val TAG = "FirestoreSecurity"

    fun handleFirestoreError(
        error: Exception,
        operationType: OperationType,
        path: String?,
        auth: FirebaseAuth = Firebase.auth
    ): Nothing {
        val currentUser = auth.currentUser
        val authInfo = JSONObject().apply {
            put("userId", currentUser?.uid ?: JSONObject.NULL)
            put("email", currentUser?.email ?: JSONObject.NULL)
            put("emailVerified", currentUser?.isEmailVerified ?: false)
            put("isAnonymous", currentUser?.isAnonymous ?: false)
            put(
                "providerIds",
                currentUser?.providerData?.mapNotNull { it.providerId }?.joinToString(",") ?: ""
            )
        }
        val errPayload = JSONObject().apply {
            put("error", error.message ?: "Unknown Firestore error")
            put("operationType", operationType.value)
            put("path", path ?: "")
            put("authInfo", authInfo)
        }
        Log.e(TAG, "Firestore Error: $errPayload", error)
        throw IllegalStateException(
            "Firestore ${operationType.value} failed at '$path': ${error.message}",
            error
        )
    }
}
