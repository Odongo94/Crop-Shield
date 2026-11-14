package com.example.ui.components

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.data.AgronomyCatalog
import com.example.data.firebase.GoogleAuthHelper
import com.example.model.KenyaAgriculturalCounty
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

data class FarmerRegistrationInput(
    val displayName: String,
    val farmName: String,
    val county: KenyaAgriculturalCounty,
    val locality: String,
    val primaryCrops: String,
    val latitude: Double,
    val longitude: Double
)

@Composable
fun AuthSignUpCard(
    selectedCounty: KenyaAgriculturalCounty,
    title: String = "Farmer Email Sign Up & Sign In",
    subtitle: String = "Register or sign in with your Google email to join the Shamba Community, message agronomy support directly, back up farm data, and help shape the app.",
    onAuthCompleted: (FirebaseUser, FarmerRegistrationInput) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var isSignUpMode by remember { mutableStateOf(true) }
    var fullName by remember { mutableStateOf("") }
    var farmName by remember { mutableStateOf("") }
    var chosenCounty by remember(selectedCounty) { mutableStateOf(selectedCounty) }
    var locality by remember { mutableStateOf("") }
    var primaryCrops by remember { mutableStateOf("Maize, Tomato, Kale") }
    var customLat by remember(selectedCounty) { mutableStateOf(selectedCounty.latitude) }
    var customLng by remember(selectedCounty) { mutableStateOf(selectedCounty.longitude) }
    var gpsCaptured by remember { mutableStateOf(false) }

    var showCountyMenu by remember { mutableStateOf(false) }
    var isSigningIn by remember { mutableStateOf(false) }
    var authError by remember { mutableStateOf<String?>(null) }

    @SuppressLint("MissingPermission")
    fun captureGpsCoordinates() {
        val locMgr = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        val providers = listOf(
            LocationManager.GPS_PROVIDER,
            LocationManager.NETWORK_PROVIDER,
            LocationManager.PASSIVE_PROVIDER
        )
        var bestLat: Double? = null
        var bestLng: Double? = null
        if (locMgr != null) {
            for (p in providers) {
                if (locMgr.isProviderEnabled(p)) {
                    val loc = locMgr.getLastKnownLocation(p)
                    if (loc != null) {
                        bestLat = loc.latitude
                        bestLng = loc.longitude
                        break
                    }
                }
            }
        }
        if (bestLat != null && bestLng != null) {
            customLat = bestLat
            customLng = bestLng
            gpsCaptured = true
            if (locality.isBlank()) {
                locality = "${chosenCounty.name.substringBefore(" (")} Area"
            }
            Toast.makeText(context, "Locality GPS captured for farm pin", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(
                context,
                "GPS fix unavailable; using ${chosenCounty.name} hub coordinates.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            captureGpsCoordinates()
        } else {
            Toast.makeText(
                context,
                "Location permission skipped. County coordinates will be used.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.65f)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)),
        modifier = modifier
            .fillMaxWidth()
            .testTag("auth_signup_card")
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                    )
                }
            }

            // Sign Up vs Sign In segmented chips
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = isSignUpMode,
                    onClick = { isSignUpMode = true },
                    leadingIcon = { Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(16.dp)) },
                    label = { Text("Sign Up (New Farmer)") },
                    modifier = Modifier.testTag("tab_auth_sign_up")
                )
                FilterChip(
                    selected = !isSignUpMode,
                    onClick = { isSignUpMode = false },
                    leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = null, modifier = Modifier.size(16.dp)) },
                    label = { Text("Sign In (Returning)") },
                    modifier = Modifier.testTag("tab_auth_sign_in")
                )
            }

            AnimatedVisibility(visible = isSignUpMode) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("Your Name (optional — uses Google name if blank)") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_signup_name")
                    )
                    OutlinedTextField(
                        value = farmName,
                        onValueChange = { farmName = it },
                        label = { Text("Farm / Shamba Name") },
                        placeholder = { Text("e.g. Green Valley Shamba") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_signup_farm")
                    )

                    // County Selector
                    OutlinedButton(
                        onClick = { showCountyMenu = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumInteractiveComponentSize()
                            .testTag("btn_signup_county")
                    ) {
                        Text(
                            text = "County: ${chosenCounty.name}",
                            modifier = Modifier.weight(1f)
                        )
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Select County")
                    }
                    DropdownMenu(
                        expanded = showCountyMenu,
                        onDismissRequest = { showCountyMenu = false }
                    ) {
                        AgronomyCatalog.kenyanCounties.forEach { county ->
                            DropdownMenuItem(
                                text = { Text("${county.name} — ${county.region}") },
                                onClick = {
                                    chosenCounty = county
                                    if (!gpsCaptured) {
                                        customLat = county.latitude
                                        customLng = county.longitude
                                    }
                                    showCountyMenu = false
                                }
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = locality,
                            onValueChange = { locality = it },
                            label = { Text("Locality / Village / Town") },
                            placeholder = { Text("e.g. Njoro, Limuru, Karatina") },
                            singleLine = true,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_signup_locality")
                        )
                        OutlinedButton(
                            onClick = {
                                val hasPerm = ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) == PackageManager.PERMISSION_GRANTED
                                if (hasPerm) {
                                    captureGpsCoordinates()
                                } else {
                                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                                }
                            },
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("btn_capture_locality_gps")
                        ) {
                            Icon(Icons.Default.MyLocation, contentDescription = "Detect GPS locality", modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(if (gpsCaptured) "GPS ✓" else "GPS")
                        }
                    }

                    OutlinedTextField(
                        value = primaryCrops,
                        onValueChange = { primaryCrops = it },
                        label = { Text("Primary Crops / Livestock") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_signup_crops")
                    )
                }
            }

            // Privacy reassurance badge
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.75f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Email Privacy: Your email is stored securely for your account & Lead Agronomist support only—other farmers never see your email.",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            authError?.let { err ->
                Text(
                    text = err,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Button(
                onClick = {
                    if (!isSigningIn) {
                        isSigningIn = true
                        authError = null
                        scope.launch {
                            val res = GoogleAuthHelper.signInWithGoogle(context)
                            isSigningIn = false
                            res.fold(
                                onSuccess = { user ->
                                    val reg = FarmerRegistrationInput(
                                        displayName = fullName.ifBlank { user.displayName ?: "CropShield Farmer" },
                                        farmName = farmName.ifBlank { "${chosenCounty.name.substringBefore(" (")} Shamba" },
                                        county = chosenCounty,
                                        locality = locality.ifBlank { chosenCounty.name.substringBefore(" (") },
                                        primaryCrops = primaryCrops.ifBlank { "Mixed Crops" },
                                        latitude = customLat,
                                        longitude = customLng
                                    )
                                    onAuthCompleted(user, reg)
                                    Toast.makeText(
                                        context,
                                        "Signed in with ${user.email ?: "Google Email"}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                onFailure = { e ->
                                    authError = e.message ?: "Email Sign-In failed"
                                }
                            )
                        }
                    }
                },
                enabled = !isSigningIn,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("btn_google_sign_in")
            ) {
                if (isSigningIn) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Connecting Google Email...")
                } else {
                    Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isSignUpMode) "Sign Up with Google Email" else "Sign In with Google Email",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
