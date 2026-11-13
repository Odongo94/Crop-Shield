package com.example

import com.example.data.alerts.FarmReminderEngine
import com.example.data.economics.FarmEconomicsCalculator
import com.example.data.livestock.LivestockRuminantSwineCatalog
import com.example.data.postharvest.PostHarvestCatalog
import com.example.data.season.CropStageEngine
import com.example.data.voice.AgriculturalLanguagePack
import com.example.data.voice.AppLanguage
import com.example.model.alerts.PhiSafetyStatus
import com.example.model.alerts.PoultryBatchTracker
import com.example.model.alerts.SprayPhiLog
import com.example.model.livestock.LivestockSpecies
import com.example.model.livestock.LivestockStage
import com.example.model.season.ActiveCropSeason
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.TimeUnit

class FarmSuiteFeatureUnitTest {

    @Test
    fun `phi countdown detects DO NOT HARVEST vs SAFE TO HARVEST accurately`() {
        val now = 1_750_000_000_000L
        val activeSpray = SprayPhiLog(
            id = "p1",
            cropName = "Tomato",
            fieldBlock = "Block A",
            productName = "Ridomil Gold",
            activeIngredient = "Metalaxyl-M + Mancozeb",
            phiDays = 7,
            sprayedAtMillis = now - TimeUnit.DAYS.toMillis(2)
        )
        val status = FarmReminderEngine.evaluatePhiAlert(activeSpray, now)
        assertEquals(5, status.daysRemaining)
        assertEquals(PhiSafetyStatus.DO_NOT_HARVEST, status.safetyStatus)

        val clearedSpray = activeSpray.copy(sprayedAtMillis = now - TimeUnit.DAYS.toMillis(8))
        val clearedStatus = FarmReminderEngine.evaluatePhiAlert(clearedSpray, now)
        assertEquals(0, clearedStatus.daysRemaining)
        assertEquals(PhiSafetyStatus.SAFE_TO_HARVEST, clearedStatus.safetyStatus)
    }

    @Test
    fun `poultry batch tracker surfaces due vaccines for chick age`() {
        val now = 1_750_000_000_000L
        val batch = PoultryBatchTracker(
            id = "f1",
            batchName = "Layers #1",
            flockType = "LAYERS",
            birdCount = 200,
            hatchDateMillis = now - TimeUnit.DAYS.toMillis(13)
        )
        val alerts = FarmReminderEngine.evaluatePoultryBatchAlerts(batch, now)
        assertTrue(alerts.any { it.targetDisease.contains("Gumboro") && it.dueInDays == 1 })
    }

    @Test
    fun `farm economics calculates break-even price and positive ROI`() {
        val maizePreset = FarmEconomicsCalculator.cropPresets.first { it.id == "maize" }
        val input = FarmEconomicsCalculator.defaultInputFor(maizePreset, hectares = 2.0)
        val result = FarmEconomicsCalculator.calculateBudget(input)

        assertEquals(2.0, result.areaHectares, 0.001)
        assertEquals(9000.0, result.totalExpectedYieldKg, 0.1)
        assertTrue(result.totalInputCostKes > 0.0)
        assertTrue(result.breakEvenPriceKesPerKg in 10.0..40.0)
        assertEquals(6, result.shoppingList.size)
    }

    @Test
    fun `crop stage engine calculates DAP and matching phenological stage`() {
        val now = 1_750_000_000_000L
        val season = ActiveCropSeason(
            id = "s1",
            fieldName = "Plot 1",
            cropName = "Maize",
            variety = "H6213",
            areaHectares = 1.5,
            plantedAtMillis = now - TimeUnit.DAYS.toMillis(30)
        )
        val status = CropStageEngine.evaluateSeason(season, now)
        assertEquals(30, status.daysAfterPlanting)
        assertTrue(status.currentStage.stageName.contains("Vegetative"))
        assertTrue(status.currentStage.scoutingFocus.any { it.contains("Fall Armyworm") })
    }

    @Test
    fun `livestock ration and gestation calculator work for dairy goat and swine`() {
        val cowRation = LivestockRuminantSwineCatalog.calculateDailyRation(
            species = LivestockSpecies.DAIRY_CATTLE,
            stage = LivestockStage.LACTATING_HIGH,
            bodyWeightKg = 450.0,
            milkYieldLiters = 20.0
        )
        assertTrue(cowRation.freshForageKgPerDay > 30.0)
        assertTrue(cowRation.waterLitersPerDay > 90.0)

        val now = 1_750_000_000_000L
        val goatBreed = LivestockRuminantSwineCatalog.calculateBreedingDates(
            id = "g1",
            animalNameOrTag = "Doe #1",
            species = LivestockSpecies.GOAT,
            serviceDateMillis = now - TimeUnit.DAYS.toMillis(100),
            nowMillis = now
        )
        assertEquals(50, goatBreed.daysRemaining)
    }

    @Test
    fun `post harvest drying calculator computes dry weight shrinkage and PICS bags`() {
        val maize = PostHarvestCatalog.cropStandards.first { it.id == "maize" }
        val res = PostHarvestCatalog.calculateDryingAndStorage(
            standard = maize,
            wetWeightKg = 2000.0,
            initialMoisturePercent = 20.0,
            targetMoisturePercent = 13.0
        )
        assertFalse(res.isSafeToStore)
        assertTrue(res.finalDryWeightKg in 1830.0..1850.0)
        assertTrue(res.pics90KgBagsNeeded == 21)
    }

    @Test
    fun `language pack covers all 4 languages cleanly`() {
        val phiMsg = AgriculturalLanguagePack.uiLabels["phi_warning"]!!
        assertTrue(phiMsg.textFor(AppLanguage.EN).isNotBlank())
        assertTrue(phiMsg.textFor(AppLanguage.SW).contains("PHI"))
        assertTrue(phiMsg.textFor(AppLanguage.LG).contains("PHI"))
        assertTrue(phiMsg.textFor(AppLanguage.LUO).contains("PHI"))
    }
}
