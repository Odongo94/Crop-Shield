package com.example.ui.components

import android.graphics.Paint as AndroidPaint
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.map.OfflineTileProvider
import com.example.model.MapLayerType
import com.example.model.cloud.FarmerRegistryEntry
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.hypot
import kotlin.math.pow
import kotlin.math.sin

/**
 * Interactive Web Mercator locality map for the Super Admin to trace where registered farmers
 * are located across Kenya and East Africa.
 */
@Composable
fun AdminFarmerMapCanvas(
    farmers: List<FarmerRegistryEntry>,
    selectedFarmerId: String?,
    centerLat: Double,
    centerLng: Double,
    zoomLevel: Int,
    layerType: MapLayerType,
    onCenterChanged: (lat: Double, lng: Double) -> Unit,
    onZoomChanged: (Int) -> Unit,
    onLayerChanged: (MapLayerType) -> Unit,
    onResetToKenya: () -> Unit,
    onFarmerSelected: (FarmerRegistryEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val tileProvider = remember(context) {
        OfflineTileProvider(context.cacheDir)
    }
    var tileRevision by remember { mutableIntStateOf(0) }

    DisposableEffect(tileProvider) {
        tileProvider.onTileLoaded = { tileRevision++ }
        onDispose { tileProvider.onTileLoaded = null }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "farmer_pin_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.75f,
        animationSpec = infiniteRepeatable(
            animation = tween(1300),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulseScale"
    )

    // Deterministic spiral offset if multiple farmers share exact county coordinates
    val plottedPositions = remember(farmers, zoomLevel) {
        val buckets = HashMap<String, Int>()
        farmers.map { farmer ->
            val key = "${(farmer.latitude * 100).toInt()}_${(farmer.longitude * 100).toInt()}"
            val indexInBucket = buckets.getOrDefault(key, 0)
            buckets[key] = indexInBucket + 1
            val angle = indexInBucket * (2.0 * PI / 6.0)
            val spreadMeters = if (indexInBucket == 0) 0.0 else 0.03 * ((indexInBucket + 5) / 6)
            val adjLat = (farmer.latitude + sin(angle) * spreadMeters).coerceIn(-85.0, 85.0)
            val adjLng = (farmer.longitude + cos(angle) * spreadMeters).coerceIn(-180.0, 180.0)
            Triple(farmer, adjLat, adjLng)
        }
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .testTag("admin_farmer_map_canvas")
            .pointerInput(centerLat, centerLng, zoomLevel) {
                detectTransformGestures { _, pan, zoomFactor, _ ->
                    if (pan != Offset.Zero) {
                        val (cx, cy) = tileProvider.latLngToWorldPixel(centerLat, centerLng, zoomLevel)
                        val newGeo = tileProvider.worldPixelToLatLng(cx - pan.x, cy - pan.y, zoomLevel)
                        onCenterChanged(newGeo.latitude, newGeo.longitude)
                    }
                    if (zoomFactor > 1.08f) {
                        onZoomChanged((zoomLevel + 1).coerceIn(5, 15))
                    } else if (zoomFactor < 0.92f) {
                        onZoomChanged((zoomLevel - 1).coerceIn(5, 15))
                    }
                }
            }
            .pointerInput(plottedPositions, centerLat, centerLng, zoomLevel) {
                detectTapGestures { tap ->
                    val w = size.width
                    val h = size.height
                    val (centerWorldX, centerWorldY) = tileProvider.latLngToWorldPixel(centerLat, centerLng, zoomLevel)
                    var closest: FarmerRegistryEntry? = null
                    var bestDist = 56f
                    for ((farmer, lat, lng) in plottedPositions) {
                        val (fx, fy) = tileProvider.latLngToWorldPixel(lat, lng, zoomLevel)
                        val sx = (fx - centerWorldX + w / 2.0).toFloat()
                        val sy = (fy - centerWorldY + h / 2.0).toFloat()
                        val dist = hypot(tap.x - sx, tap.y - sy)
                        if (dist < bestDist) {
                            bestDist = dist
                            closest = farmer
                        }
                    }
                    closest?.let(onFarmerSelected)
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val _rev = tileRevision
            val canvasWidth = size.width
            val canvasHeight = size.height
            val (centerWorldX, centerWorldY) = tileProvider.latLngToWorldPixel(centerLat, centerLng, zoomLevel)

            renderAdminMapTiles(
                canvasWidth = canvasWidth,
                canvasHeight = canvasHeight,
                centerWorldX = centerWorldX,
                centerWorldY = centerWorldY,
                zoom = zoomLevel,
                layerType = layerType,
                tileProvider = tileProvider
            )

            val labelPaint = AndroidPaint().apply {
                color = android.graphics.Color.WHITE
                textSize = 26f
                isFakeBoldText = true
                isAntiAlias = true
                textAlign = AndroidPaint.Align.CENTER
                setShadowLayer(5f, 0f, 1f, android.graphics.Color.BLACK)
            }
            val subLabelPaint = AndroidPaint().apply {
                color = android.graphics.Color.rgb(220, 245, 220)
                textSize = 21f
                isAntiAlias = true
                textAlign = AndroidPaint.Align.CENTER
                setShadowLayer(4f, 0f, 1f, android.graphics.Color.BLACK)
            }

            plottedPositions.forEach { (farmer, lat, lng) ->
                val (fx, fy) = tileProvider.latLngToWorldPixel(lat, lng, zoomLevel)
                val sx = (fx - centerWorldX + canvasWidth / 2.0).toFloat()
                val sy = (fy - centerWorldY + canvasHeight / 2.0).toFloat()

                if (sx in -60f..(canvasWidth + 60f) && sy in -60f..(canvasHeight + 60f)) {
                    val isSelected = farmer.uid == selectedFarmerId || farmer.id == selectedFarmerId
                    val pinColor = if (isSelected) Color(0xFFFF6F00) else Color(0xFF1B5E20)

                    if (isSelected) {
                        drawCircle(
                            color = Color(0x55FF6F00),
                            radius = 24f * pulseScale,
                            center = Offset(sx, sy)
                        )
                    }

                    // Outer white ring
                    drawCircle(
                        color = Color.White,
                        radius = if (isSelected) 16f else 13f,
                        center = Offset(sx, sy)
                    )
                    // Inner marker
                    drawCircle(
                        color = pinColor,
                        radius = if (isSelected) 12.5f else 9.5f,
                        center = Offset(sx, sy)
                    )
                    drawCircle(
                        color = Color.White,
                        radius = if (isSelected) 16f else 13f,
                        center = Offset(sx, sy),
                        style = Stroke(width = 2.5f)
                    )

                    val shortName = farmer.displayName.substringBefore(" ").ifBlank { "Farmer" }
                    val localityTag = farmer.locality.ifBlank { farmer.county.substringBefore(" (") }
                    drawContext.canvas.nativeCanvas.drawText(shortName, sx, sy - 20f, labelPaint)
                    if (isSelected || zoomLevel >= 7) {
                        drawContext.canvas.nativeCanvas.drawText(localityTag, sx, sy + 30f, subLabelPaint)
                    }
                }
            }
        }

        // Top-left map status badge
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.88f),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
        ) {
            Text(
                text = "${farmers.size} Farmer Pins • Zoom $zoomLevel • ${layerType.title}",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
            )
        }

        // Right-side map controls (Zoom +/-, Layer toggle, Reset Kenya view)
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FilledTonalIconButton(
                onClick = {
                    val nextLayer = when (layerType) {
                        MapLayerType.GOOGLE_SATELLITE -> MapLayerType.AGRO_ECOLOGICAL
                        MapLayerType.AGRO_ECOLOGICAL -> MapLayerType.TOPOGRAPHIC
                        else -> MapLayerType.GOOGLE_SATELLITE
                    }
                    onLayerChanged(nextLayer)
                },
                modifier = Modifier.size(44.dp).testTag("btn_admin_map_layer")
            ) {
                Icon(Icons.Default.Layers, contentDescription = "Switch Map Layer")
            }
            FilledTonalIconButton(
                onClick = onResetToKenya,
                modifier = Modifier.size(44.dp).testTag("btn_admin_map_reset")
            ) {
                Icon(Icons.Default.MyLocation, contentDescription = "Center Kenya Farmers Map")
            }
            FilledTonalIconButton(
                onClick = { onZoomChanged((zoomLevel + 1).coerceIn(5, 15)) },
                modifier = Modifier.size(44.dp).testTag("btn_admin_map_zoom_in")
            ) {
                Icon(Icons.Default.Add, contentDescription = "Zoom In")
            }
            FilledTonalIconButton(
                onClick = { onZoomChanged((zoomLevel - 1).coerceIn(5, 15)) },
                modifier = Modifier.size(44.dp).testTag("btn_admin_map_zoom_out")
            ) {
                Icon(Icons.Default.Remove, contentDescription = "Zoom Out")
            }
        }
    }
}

private fun DrawScope.renderAdminMapTiles(
    canvasWidth: Float,
    canvasHeight: Float,
    centerWorldX: Double,
    centerWorldY: Double,
    zoom: Int,
    layerType: MapLayerType,
    tileProvider: OfflineTileProvider
) {
    val tileSize = OfflineTileProvider.TILE_SIZE.toDouble()
    val maxTiles = 2.0.pow(zoom.toDouble()).toInt().coerceAtLeast(1)

    val minWorldX = centerWorldX - canvasWidth / 2.0
    val maxWorldX = centerWorldX + canvasWidth / 2.0
    val minWorldY = centerWorldY - canvasHeight / 2.0
    val maxWorldY = centerWorldY + canvasHeight / 2.0

    val minTileX = floor(minWorldX / tileSize).toInt().coerceIn(0, maxTiles - 1)
    val maxTileX = floor(maxWorldX / tileSize).toInt().coerceIn(0, maxTiles - 1)
    val minTileY = floor(minWorldY / tileSize).toInt().coerceIn(0, maxTiles - 1)
    val maxTileY = floor(maxWorldY / tileSize).toInt().coerceIn(0, maxTiles - 1)

    for (tileX in minTileX..maxTileX) {
        for (tileY in minTileY..maxTileY) {
            val tileBitmap = tileProvider.getTile(tileX, tileY, zoom, layerType)
            val screenX = (tileX * tileSize - minWorldX).toFloat()
            val screenY = (tileY * tileSize - minWorldY).toFloat()
            drawImage(
                image = tileBitmap,
                topLeft = Offset(screenX, screenY)
            )
        }
    }
}
