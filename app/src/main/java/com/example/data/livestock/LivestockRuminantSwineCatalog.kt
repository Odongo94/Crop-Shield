package com.example.data.livestock

import com.example.data.alerts.FarmReminderEngine
import com.example.model.livestock.LivestockBreedingRecord
import com.example.model.livestock.LivestockHealthProtocol
import com.example.model.livestock.LivestockRationResult
import com.example.model.livestock.LivestockSpecies
import com.example.model.livestock.LivestockStage
import java.util.concurrent.TimeUnit
import kotlin.math.ceil

object LivestockRuminantSwineCatalog {

    private const val DAY_MS = 24L * 60L * 60L * 1000L

    fun calculateDailyRation(
        species: LivestockSpecies,
        stage: LivestockStage,
        bodyWeightKg: Double,
        milkYieldLiters: Double = 0.0
    ): LivestockRationResult {
        val bw = bodyWeightKg.coerceAtLeast(10.0)
        val milk = milkYieldLiters.coerceAtLeast(0.0)

        return when (species) {
            LivestockSpecies.DAIRY_CATTLE -> {
                val baseDmPercent = when (stage) {
                    LivestockStage.LACTATING_HIGH -> 0.032
                    LivestockStage.PREGNANT_DRY -> 0.025
                    LivestockStage.GROWER_FINISHER -> 0.028
                    LivestockStage.MAINTENANCE -> 0.022
                }
                val dmKg = (bw * baseDmPercent) + (milk * 0.12)
                // Concentrate: 2 kg maintenance + 1 kg per 2.5L milk above 5L; steaming up 3 kg/day last 60 days
                val concentrateKg = when (stage) {
                    LivestockStage.LACTATING_HIGH -> (1.5 + ((milk - 4.0).coerceAtLeast(0.0) / 2.2))
                    LivestockStage.PREGNANT_DRY -> 3.0
                    LivestockStage.GROWER_FINISHER -> 2.0
                    LivestockStage.MAINTENANCE -> 0.5
                }
                val forageDmNeeded = (dmKg - (concentrateKg * 0.88)).coerceAtLeast(bw * 0.015)
                // Napier/green forage ~22% DM -> fresh kg = DM / 0.22
                val freshForageKg = forageDmNeeded / 0.22
                val mineralsGrams = 100.0 + (milk * 4.0)
                val waterLiters = (bw * 0.10) + (milk * 3.5)

                LivestockRationResult(
                    species = species,
                    stage = stage,
                    bodyWeightKg = bw,
                    milkYieldLiters = milk,
                    dryMatterIntakeKgPerDay = dmKg,
                    freshForageKgPerDay = freshForageKg,
                    dairyMealOrConcentrateKgPerDay = concentrateKg,
                    mineralSupplementGramsPerDay = mineralsGrams,
                    waterLitersPerDay = waterLiters,
                    rationNotes = listOf(
                        "Chop Napier grass / Boma Rhodes hay to 2-3 cm to minimize selective wastage.",
                        "Split Dairy Meal (16-18% CP) equally during morning and evening milking.",
                        "Add Lucerne/Desmodium/Calliandra (3 kg fresh = ~1 kg dairy meal protein) to cut concentrate costs.",
                        "Provide clean ad-libitum water; lactating cows drink ~3.5L extra per liter of milk produced."
                    )
                )
            }

            LivestockSpecies.BEEF_CATTLE -> {
                val dmKg = bw * if (stage == LivestockStage.GROWER_FINISHER) 0.028 else 0.022
                val concentrateKg = if (stage == LivestockStage.GROWER_FINISHER) bw * 0.01 else 0.5
                val freshForageKg = ((dmKg - concentrateKg * 0.88).coerceAtLeast(bw * 0.015)) / 0.25
                LivestockRationResult(
                    species = species,
                    stage = stage,
                    bodyWeightKg = bw,
                    milkYieldLiters = 0.0,
                    dryMatterIntakeKgPerDay = dmKg,
                    freshForageKgPerDay = freshForageKg,
                    dairyMealOrConcentrateKgPerDay = concentrateKg,
                    mineralSupplementGramsPerDay = 80.0,
                    waterLitersPerDay = bw * 0.09,
                    rationNotes = listOf(
                        "Feedlot finishers gain 0.9-1.2 kg/day with maize germ + cottonseed cake + molasses-urea block.",
                        "Provide high-phosphorus livestock salt lick ad-libitum."
                    )
                )
            }

            LivestockSpecies.GOAT -> {
                val dmPercent = if (stage == LivestockStage.LACTATING_HIGH) 0.045 else 0.035
                val dmKg = bw * dmPercent + (milk * 0.15)
                val concentrateKg = when (stage) {
                    LivestockStage.LACTATING_HIGH -> 0.4 + (milk * 0.35)
                    LivestockStage.PREGNANT_DRY -> 0.45
                    LivestockStage.GROWER_FINISHER -> 0.30
                    LivestockStage.MAINTENANCE -> 0.15
                }
                val freshForageKg = ((dmKg - concentrateKg * 0.88).coerceAtLeast(bw * 0.02)) / 0.25
                LivestockRationResult(
                    species = species,
                    stage = stage,
                    bodyWeightKg = bw,
                    milkYieldLiters = milk,
                    dryMatterIntakeKgPerDay = dmKg,
                    freshForageKgPerDay = freshForageKg,
                    dairyMealOrConcentrateKgPerDay = concentrateKg,
                    mineralSupplementGramsPerDay = 25.0,
                    waterLitersPerDay = (bw * 0.10) + (milk * 2.5),
                    rationNotes = listOf(
                        "Goats are selective browsers: hang Leucaena, Sesbania, Mulberry, or Sweet Potato vines above floor height.",
                        "Never feed wilted cassava leaves or wet mouldy hay (enterotoxemia risk)."
                    )
                )
            }

            LivestockSpecies.SHEEP -> {
                val dmKg = bw * if (stage == LivestockStage.LACTATING_HIGH) 0.040 else 0.032
                val concentrateKg = when (stage) {
                    LivestockStage.LACTATING_HIGH -> 0.50
                    LivestockStage.PREGNANT_DRY -> 0.40
                    LivestockStage.GROWER_FINISHER -> 0.35
                    LivestockStage.MAINTENANCE -> 0.15
                }
                val freshForageKg = ((dmKg - concentrateKg * 0.88).coerceAtLeast(bw * 0.02)) / 0.24
                LivestockRationResult(
                    species = species,
                    stage = stage,
                    bodyWeightKg = bw,
                    milkYieldLiters = 0.0,
                    dryMatterIntakeKgPerDay = dmKg,
                    freshForageKgPerDay = freshForageKg,
                    dairyMealOrConcentrateKgPerDay = concentrateKg,
                    mineralSupplementGramsPerDay = 20.0,
                    waterLitersPerDay = bw * 0.09,
                    rationNotes = listOf(
                        "CRITICAL: Never give sheep high-copper cattle mineral licks (copper toxicity causes fatal hemolytic jaundice).",
                        "Flushing ewes with 300g/day grain 3 weeks pre-mating boosts twinning rate."
                    )
                )
            }

            LivestockSpecies.SWINE -> {
                val feedKg = when (stage) {
                    LivestockStage.LACTATING_HIGH -> 2.5 + 3.5 // ~6 kg for nursing sow
                    LivestockStage.PREGNANT_DRY -> 2.4
                    LivestockStage.GROWER_FINISHER -> (bw * 0.04).coerceIn(1.0, 2.8)
                    LivestockStage.MAINTENANCE -> 2.0
                }
                val waterL = when (stage) {
                    LivestockStage.LACTATING_HIGH -> 22.0
                    LivestockStage.PREGNANT_DRY -> 12.0
                    else -> (bw * 0.12).coerceAtLeast(4.0)
                }
                LivestockRationResult(
                    species = species,
                    stage = stage,
                    bodyWeightKg = bw,
                    milkYieldLiters = 0.0,
                    dryMatterIntakeKgPerDay = feedKg * 0.89,
                    freshForageKgPerDay = 1.0, // optional sukuma/amaranth greens
                    dairyMealOrConcentrateKgPerDay = feedKg,
                    mineralSupplementGramsPerDay = 40.0,
                    waterLitersPerDay = waterL,
                    rationNotes = listOf(
                        "Growers (20-50 kg): 16-17% Crude Protein Sow & Weaner/Grower mash; Finishers (50-95 kg): 14% CP Finisher.",
                        "Boil all kitchen swill/potato peelings at 100°C for 30+ mins to kill African Swine Fever & Foot-and-Mouth viruses.",
                        "Administer Iron Dextran injection (200 mg IM) to piglets at Day 3 to prevent fatal anemia."
                    )
                )
            }
        }
    }

    fun calculateBreedingDates(
        id: String,
        animalNameOrTag: String,
        species: LivestockSpecies,
        serviceDateMillis: Long,
        nowMillis: Long = System.currentTimeMillis()
    ): LivestockBreedingRecord {
        val dueAt = serviceDateMillis + species.gestationDays * DAY_MS
        val dryOffAt = dueAt - species.dryOffDaysBeforeBirth * DAY_MS
        val daysLeft = ceil((dueAt - nowMillis).toDouble() / DAY_MS.toDouble()).toInt()
        return LivestockBreedingRecord(
            id = id,
            animalNameOrTag = animalNameOrTag,
            species = species,
            serviceDateMillis = serviceDateMillis,
            expectedDueDateMillis = dueAt,
            dryOffOrSteamingDateMillis = dryOffAt,
            daysRemaining = daysLeft
        )
    }

    fun formatDate(millis: Long): String = FarmReminderEngine.formatDate(millis)

    val healthProtocols: List<LivestockHealthProtocol> = listOf(
        LivestockHealthProtocol(
            id = "tick_acaricide",
            species = setOf(LivestockSpecies.DAIRY_CATTLE, LivestockSpecies.BEEF_CATTLE, LivestockSpecies.GOAT, LivestockSpecies.SHEEP),
            category = "Tick Control / Dipping",
            title = "Weekly Acaricide Spray / Plunge Dip (ECF & Heartwater Prevention)",
            intervalDays = 7,
            activeDrugOrVaccine = "Amitraz 12.5% (Triatix) OR Synthetic Pyrethroid (Dominex/Ectomin) — rotate every 6-12 months",
            dosageAndRoute = "Amitraz: 20 mL per 10L water; wet ears, dewlap, udder, brisket, and tail switch thoroughly.",
            withdrawalPeriodNotes = "Milk withdrawal: 1-3 days depending on label; Meat withdrawal: 7-14 days."
        ),
        LivestockHealthProtocol(
            id = "deworming_ruminants",
            species = setOf(LivestockSpecies.DAIRY_CATTLE, LivestockSpecies.BEEF_CATTLE, LivestockSpecies.GOAT, LivestockSpecies.SHEEP),
            category = "Deworming (Helminths & Liver Fluke)",
            title = "Strategic Wet-Season Deworming Rotation",
            intervalDays = 90,
            activeDrugOrVaccine = "Albendazole 10% OR Levamisole + Oxyclozanide (for Liver Fluke) OR Ivermectin",
            dosageAndRoute = "Albendazole oral drench: 7.5-10 mg/kg BW (fluke dose). Check FAMACHA eyelid score in goats/sheep monthly.",
            withdrawalPeriodNotes = "Albendazole: avoid in 1st 45 days of pregnancy! Milk withdrawal 3-5 days; Meat 14 days."
        ),
        LivestockHealthProtocol(
            id = "vax_fmd_anthrax_lsd",
            species = setOf(LivestockSpecies.DAIRY_CATTLE, LivestockSpecies.BEEF_CATTLE),
            category = "Notifiable Cattle Vaccines",
            title = "Foot & Mouth (FMD), Lumpy Skin Disease (LSD) & Anthrax/Blackquarter",
            intervalDays = 180,
            activeDrugOrVaccine = "FMD Quadrivalent (6-monthly), Blanthax (Annual), Lumpyvax (Annual)",
            dosageAndRoute = "Subcutaneous injection in neck region using sterile 16G needle per animal.",
            withdrawalPeriodNotes = "Do not vaccinate sick or feverish cows; keep cold chain 2-8°C."
        ),
        LivestockHealthProtocol(
            id = "vax_ccpp_ppr",
            species = setOf(LivestockSpecies.GOAT, LivestockSpecies.SHEEP),
            category = "Small Ruminant Vaccines",
            title = "CCPP (Contagious Caprine Pleuropneumonia) & PPR (Goat Plague)",
            intervalDays = 365,
            activeDrugOrVaccine = "Caprivax (CCPP) + PPR Live Attenuated + Enterotoxemia (Pulpy Kidney)",
            dosageAndRoute = "1 mL subcutaneous behind shoulder; vaccinate kids/lambs at 3 months then annual booster.",
            withdrawalPeriodNotes = "Zero meat/milk withdrawal for inactivated CCPP; avoid stressed goats in heavy rain."
        ),
        LivestockHealthProtocol(
            id = "swine_deworm_erysipelas",
            species = setOf(LivestockSpecies.SWINE),
            category = "Swine Deworming & Biosecurity",
            title = "Ascaris Roundworm, Mange Control & Erysipelas",
            intervalDays = 90,
            activeDrugOrVaccine = "Ivermectin 1% SC (1 mL per 33 kg BW) + Piperazine/Fenbendazole + Iron Dextran Day 3",
            dosageAndRoute = "Treat sows 14 days before farrowing and wash udders before entering farrowing pen.",
            withdrawalPeriodNotes = "Ivermectin meat withdrawal: 28 days before slaughter. Strict footbath biosecurity for African Swine Fever!"
        )
    )

    val defaultSampleBreeding: List<LivestockBreedingRecord>
        get() {
            val now = System.currentTimeMillis()
            return listOf(
                calculateBreedingDates("b1", "Friesian #04 (Nyota)", LivestockSpecies.DAIRY_CATTLE, now - TimeUnit.DAYS.toMillis(210), now),
                calculateBreedingDates("b2", "Toggenburg Doe #12", LivestockSpecies.GOAT, now - TimeUnit.DAYS.toMillis(105), now),
                calculateBreedingDates("b3", "Large White Sow #02", LivestockSpecies.SWINE, now - TimeUnit.DAYS.toMillis(82), now)
            )
        }
}
