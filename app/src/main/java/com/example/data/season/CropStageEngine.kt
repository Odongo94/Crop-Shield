package com.example.data.season

import com.example.data.alerts.FarmReminderEngine
import com.example.model.season.ActiveCropSeason
import com.example.model.season.CropGrowthStageRule
import com.example.model.season.CropLifecycleProfile
import com.example.model.season.CropSeasonStatus
import java.util.concurrent.TimeUnit

object CropStageEngine {

    private const val DAY_MS = 24L * 60L * 60L * 1000L

    val cropProfiles: List<CropLifecycleProfile> = listOf(
        CropLifecycleProfile(
            cropName = "Maize",
            totalDaysToMaturity = 135,
            stages = listOf(
                CropGrowthStageRule("Emergence (VE - V2)", 0, 14, listOf("Check emergence gap-filling", "Apply pre/early post-emergence weed control"), listOf("Cutworms", "Wireworms", "Shoot Fly"), "Keep topsoil moist; avoid waterlogging seedbed."),
                CropGrowthStageRule("Early Vegetative (V3 - V6)", 15, 38, listOf("1st Top-Dressing with CAN/Urea (21-28 DAP)", "Hand weed or selective post-emergence herbicide"), listOf("Fall Armyworm in whorls", "Maize Streak Virus aphids/leafhoppers"), "Critical leaf initiation; apply 25-30 mm/week."),
                CropGrowthStageRule("Rapid Stem Elongation (V7 - VT)", 39, 65, listOf("2nd Split N top-dress before tasseling", "Inspect ear-leaf vigor"), listOf("Stalk Borer", "Turcicum Leaf Blight", "Striga emergence"), "Peak water demand begins; prevent drought stress."),
                CropGrowthStageRule("Flowering & Silking (R1 - R2)", 66, 90, listOf("Maintain uniform soil moisture for pollination", "Zero leaf damage tolerance"), listOf("African Bollworm on silks", "Pollen beetles", "Common Rust"), "Most moisture-sensitive stage (~45 mm/week)."),
                CropGrowthStageRule("Grain Filling & Dough (R3 - R5)", 91, 120, listOf("Stop late foliar N sprays", "Protect cob husks from bird/rodent entry"), listOf("Ear Rots", "Stalk lodging", "Late Stalk Borer"), "Reduce irrigation frequency gently as husks yellow."),
                CropGrowthStageRule("Maturity & Harvest Drying (R6)", 121, 135, listOf("Check black layer at kernel base", "Harvest promptly when moisture < 20% and dry to 13%"), listOf("Maize Weevil field infestation", "Aflatoxin mold on lodged cobs"), "Stop irrigation; keep cobs off bare damp ground.")
            )
        ),
        CropLifecycleProfile(
            cropName = "Tomato",
            totalDaysToMaturity = 95,
            stages = listOf(
                CropGrowthStageRule("Transplant Establishment", 0, 14, listOf("Drench root zone with humic/Trichoderma", "Gap fill dead seedlings by Day 7"), listOf("Cutworms", "Whiteflies (TYLCV vector)", "Damping-off"), "Light frequent drip (2-3 L/m²/day)."),
                CropGrowthStageRule("Vegetative Branching", 15, 32, listOf("Install stakes/trellis wires", "Prune side suckers for indeterminate types", "Apply vegetative NPK + Calcium Nitrate"), listOf("Tuta absoluta leaf mines", "Thrips", "Early Blight"), "Avoid overhead splash; drip irrigate at morning."),
                CropGrowthStageRule("Flowering & Fruit Set", 33, 55, listOf("Apply Boron + Calcium foliar to stop Blossom End Rot", "Switch fertigation to high-K"), listOf("Late Blight in cool mist", "Whiteflies", "Blossom thrips"), "Consistent soil moisture prevents blossom drop."),
                CropGrowthStageRule("Fruit Sizing & Ripening", 56, 80, listOf("Maintain strict PHI logs for all sprays!", "Switch to short-PHI biopesticides (1-3 days)"), listOf("Bollworm fruit borers", "Spider Mites", "Anthracnose"), "Avoid sudden excess water that cracks ripening fruits."),
                CropGrowthStageRule("Peak Harvest Window", 81, 95, listOf("Verify 0 days PHI remaining before every pick", "Pick at breaker/pink stage early morning"), listOf("Fruit Flies", "Botrytis Gray Mold", "Sunscald"), "Pick into clean ventilated crates; keep shaded.")
            )
        ),
        CropLifecycleProfile(
            cropName = "Irish Potato",
            totalDaysToMaturity = 105,
            stages = listOf(
                CropGrowthStageRule("Sprout Emergence", 0, 20, listOf("Ensure deep well-drained ridges", "Remove volunteer diseased solanaceous weeds"), listOf("Blackleg / Soft Rot", "Cutworms"), "Moderate moisture; excess water rots seed tubers."),
                CropGrowthStageRule("Vegetative Canopy & First Earthing-Up", 21, 42, listOf("Earth up ridges (hilling) at 20-25 cm height", "Top-dress CAN before tuber initiation"), listOf("Potato Aphids", "Early Blight", "Colorado/Epilachna beetles"), "Ensure loose ridge aeration for stolon growth."),
                CropGrowthStageRule("Tuber Initiation & Flowering", 43, 70, listOf("2nd Earthing-up to cover shallow tubers", "Protective Late Blight fungicide rotation (7-10d)"), listOf("Late Blight", "Potato Tuber Moth", "Bacterial Wilt wilting"), "CRITICAL moisture phase; dry spells cause knobby tubers."),
                CropGrowthStageRule("Tuber Bulking", 71, 92, listOf("Observe 7-14 day chemical PHI strictly", "Rogue any Bacterial Wilt symptomatic hills"), listOf("Late Blight", "Tuber Moth cracking soil", "Scab"), "Steady irrigation; seal soil cracks by hilling."),
                CropGrowthStageRule("Haulm Senescence & Dehaulming", 93, 105, listOf("Cut haulms 10-14 days before digging to set skins", "Cure harvested tubers in dark airy store"), listOf("Tuber Moth post-dehaulm entry", "Mechanical bruising"), "Withhold irrigation 10-14 days pre-harvest.")
            )
        ),
        CropLifecycleProfile(
            cropName = "French Beans",
            totalDaysToMaturity = 60,
            stages = listOf(
                CropGrowthStageRule("Germination & Unifoliate", 0, 10, listOf("Protect seedlings from Bean Fly", "Thin/weed gently without disturbing shallow roots"), listOf("Bean Fly", "Root Rot", "Cutworms"), "Light even moisture for uniform germination."),
                CropGrowthStageRule("Trifoliate Vegetative", 11, 25, listOf("Top-dress CAN/NPK 17:17:17 at 21 DAP", "Keep furrows weed-free"), listOf("Bean Rust", "Aphids", "Thrips"), "30 mm/week; avoid compacting wet clay."),
                CropGrowthStageRule("Budding & Flowering", 26, 40, listOf("Foliar Calcium + Boron for pod set", "Strictly follow export MRL/PHI rules"), listOf("Flower Thrips", "Angular Leaf Spot", "Whiteflies"), "Peak irrigation demand; flower drop occurs if dry."),
                CropGrowthStageRule("Pod Filling & Grading Harvest", 41, 60, listOf("Verify export PHI clearance daily", "Harvest extra-fine/fine pods every 2 days"), listOf("African Bollworm", "Pod Borers", "Anthracnose"), "Cool picked pods immediately in shade damp sacks.")
            )
        ),
        CropLifecycleProfile(
            cropName = "Onion",
            totalDaysToMaturity = 120,
            stages = listOf(
                CropGrowthStageRule("Transplant Recovery", 0, 20, listOf("Check shallow planting depth (bulb neck exposed)", "Gentle hand weeding"), listOf("Onion Thrips", "Damping-off"), "Frequent light drip/sprinkler every 2-3 days."),
                CropGrowthStageRule("Leaf Multiplication", 21, 55, listOf("Split N top-dressing at Week 3 & Week 6", "Scout leaf sheaths for silvery thrips streaks"), listOf("Onion Thrips", "Purple Blotch", "Downy Mildew"), "Steady irrigation to maximize leaf number (bigger bulbs)."),
                CropGrowthStageRule("Bulbing Phase", 56, 95, listOf("Switch to Potassium-rich feeding (SOP/KNO3)", "Stop high-N fertilization to prevent thick necks"), listOf("Purple Blotch", "Thrips", "Stemphylium Blight"), "Consistent moisture until 30% tops fall."),
                CropGrowthStageRule("Neck Fall & Field Curing", 96, 120, listOf("Withhold water completely when 50% necks fall", "Lift bulbs & cure 10-14 days before bagging"), listOf("Basal Rot", "Storage Black Mold"), "STOP irrigation completely during curing.")
            )
        )
    )

    fun evaluateSeason(
        season: ActiveCropSeason,
        nowMillis: Long = System.currentTimeMillis()
    ): CropSeasonStatus {
        val profile = cropProfiles.firstOrNull {
            season.cropName.contains(it.cropName, ignoreCase = true) ||
                it.cropName.contains(season.cropName, ignoreCase = true)
        } ?: cropProfiles.first()

        val dap = ((nowMillis - season.plantedAtMillis).coerceAtLeast(0L) / DAY_MS).toInt()
        val stageIndex = profile.stages.indexOfFirst { dap in it.startDap..it.endDap }
            .let { if (it >= 0) it else profile.stages.lastIndex }

        val currentStage = profile.stages[stageIndex]
        val nextStage = profile.stages.getOrNull(stageIndex + 1)
        val daysToNext = if (nextStage != null) (nextStage.startDap - dap).coerceAtLeast(0) else 0
        val progress = (dap.toFloat() / profile.totalDaysToMaturity.toFloat()).coerceIn(0f, 1f)
        val harvestAt = season.plantedAtMillis + profile.totalDaysToMaturity * DAY_MS

        return CropSeasonStatus(
            season = season,
            daysAfterPlanting = dap,
            totalCycleDays = profile.totalDaysToMaturity,
            progressFraction = progress,
            currentStage = currentStage,
            nextStageName = nextStage?.stageName,
            daysUntilNextStage = daysToNext,
            estimatedHarvestDateFormatted = FarmReminderEngine.formatDate(harvestAt)
        )
    }

    val defaultSampleSeasons: List<ActiveCropSeason>
        get() {
            val now = System.currentTimeMillis()
            return listOf(
                ActiveCropSeason(
                    id = "season_1",
                    fieldName = "Njoro Central Shamba - Plot 1",
                    cropName = "Maize",
                    variety = "H6213 Hybrid",
                    areaHectares = 1.25,
                    plantedAtMillis = now - TimeUnit.DAYS.toMillis(28)
                ),
                ActiveCropSeason(
                    id = "season_2",
                    fieldName = "Greenhouse Block A",
                    cropName = "Tomato",
                    variety = "Anna F1",
                    areaHectares = 0.25,
                    plantedAtMillis = now - TimeUnit.DAYS.toMillis(44)
                )
            )
        }
}
