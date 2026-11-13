package com.example.base

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.MemoryCacheSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import java.net.Socket

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
@LooperMode(LooperMode.Mode.INSTRUMENTATION_TEST)
abstract class FirestoreEmulatorTestBase {

    protected lateinit var app: FirebaseApp
    protected lateinit var auth: FirebaseAuth
    protected lateinit var db: FirebaseFirestore

    companion object {
        const val PROJECT_ID = "demo-no-project"
        const val DEFAULT_DATABASE_ID = "ai-studio-android-cropshie-bf9fb39e-e7d1-45e0-a9c7-a5304d6dbde1"
        const val FIRESTORE_HOST = "127.0.0.1"
        const val FIRESTORE_PORT = 8085
        const val AUTH_HOST = "127.0.0.1"
        const val AUTH_PORT = 9099
        private const val AUTH_TIMEOUT_MS = 5000L

        fun isEmulatorRunning(): Boolean {
            return try {
                Socket(FIRESTORE_HOST, FIRESTORE_PORT).use { true } &&
                    Socket(AUTH_HOST, AUTH_PORT).use { true }
            } catch (_: Exception) {
                false
            }
        }
    }

    protected lateinit var databaseId: String

    @Before
    open fun setUpFirebaseEmulator() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val resId = context.resources.getIdentifier("firestore_database_id", "string", context.packageName)
        databaseId = if (resId != 0) context.getString(resId) else DEFAULT_DATABASE_ID

        val appName = "test_emulator_app"
        app = FirebaseApp.getApps(context).firstOrNull { it.name == appName }
            ?: FirebaseApp.initializeApp(
                context,
                FirebaseOptions.Builder()
                    .setProjectId(PROJECT_ID)
                    .setApplicationId("1:123456789:android:testapp")
                    .setApiKey("AIzaSyDummyKeyForEmulatorTestsOnly")
                    .build(),
                appName
            )

        auth = FirebaseAuth.getInstance(app)
        try {
            auth.useEmulator(AUTH_HOST, AUTH_PORT)
        } catch (_: IllegalStateException) {
            // Already bound to emulator in this JVM
        }

        db = FirebaseFirestore.getInstance(app, databaseId)
        try {
            db.useEmulator(FIRESTORE_HOST, FIRESTORE_PORT)
            db.firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setLocalCacheSettings(MemoryCacheSettings.newBuilder().build())
                .build()
        } catch (_: IllegalStateException) {
            // Already configured
        }
    }

    @After
    open fun tearDownFirebaseEmulator() {
        auth.signOut()
    }

    protected suspend fun signInTestUser(email: String, password: String = "TestPass123!"): String =
        withContext(Dispatchers.IO) {
            withTimeout(AUTH_TIMEOUT_MS) {
                auth.signOut()
                val result = try {
                    auth.signInWithEmailAndPassword(email, password).await()
                } catch (_: FirebaseAuthInvalidUserException) {
                    auth.createUserWithEmailAndPassword(email, password).await()
                } catch (_: Exception) {
                    auth.createUserWithEmailAndPassword(email, password).await()
                }
                checkNotNull(result.user?.uid) { "User auth failed" }
            }
        }

    protected fun clearEmulatorFirestore() {
        val client = OkHttpClient()
        val url = "http://$FIRESTORE_HOST:$FIRESTORE_PORT/emulator/v1/projects/$PROJECT_ID/databases/$databaseId/documents"
        val req = Request.Builder().url(url).delete().build()
        client.newCall(req).execute().close()
    }

    protected fun clearEmulatorAuth() {
        val client = OkHttpClient()
        val url = "http://$AUTH_HOST:$AUTH_PORT/emulator/v1/projects/$PROJECT_ID/accounts"
        val req = Request.Builder().url(url).delete().build()
        client.newCall(req).execute().close()
    }

    protected fun Throwable.unwrapFirestoreException(): FirebaseFirestoreException? {
        var current: Throwable? = this
        while (current != null) {
            if (current is FirebaseFirestoreException) return current
            current = current.cause
        }
        return null
    }

    protected fun verifyEmailInEmulator(idToken: String) {
        val client = OkHttpClient()
        val body = JSONObject().apply {
            put("idToken", idToken)
            put("emailVerified", true)
        }.toString().toRequestBody("application/json".toMediaType())
        val req = Request.Builder()
            .url("http://$AUTH_HOST:$AUTH_PORT/identitytoolkit.googleapis.com/v1/accounts:update?key=AIzaSyDummyKeyForEmulatorTestsOnly")
            .post(body)
            .build()
        client.newCall(req).execute().close()
    }
}
