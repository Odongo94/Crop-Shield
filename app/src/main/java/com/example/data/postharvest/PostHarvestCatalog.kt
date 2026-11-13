package com.example.data.postharvest

import com.example.model.postharvest.CropStorageStandard
import com.example.model.postharvest.DryingCalculationResult
import com.example.model.postharvest.StoragePestProfile
import kotlin.math.ceil

object PostHarvestCatalog {

    val cropStandards: List<CropStorageStandard> = listOf(
        CropStorageStandard(
            id = "maize",
            cropName = "Maize Grain (Shelled)",
            maxSafeMoisturePercent = 13.0,
            typicalHarvestMoisturePercent = 21.0,
            bulkDensityKgPerCubicMeter = 720.0,
            dryingGuide = "Dry on raised tarpaulins or cribs in thin 5 cm layers; rake every 2 hours. Never dry on bare soil (Aspergillus flavus spore source). Perform salt-jar test if no moisture meter is available.",
            aflatoxinRiskLevel = "HIGH — Warm (>25°C) + damp (>13.5% MC) maize rapidly develops carcinogenic Aflatoxin B1."
        ),
        CropStorageStandard(
            id = "beans",
            cropName = "Dry Beans / Cowpeas / Green Grams",
            maxSafeMoisturePercent = 11.5,
            typicalHarvestMoisturePercent = 17.0,
            bulkDensityKgPerCubicMeter = 780.0,
            dryingGuide = "Sun-dry pods before gentle threshing (avoids seed coat cracking). Dry clean grain to <=11.5% MC before sealing in PICS bags to suffocate Bruchids.",
            aflatoxinRiskLevel = "MODERATE — Bruchid insect damage creates moisture pockets that trigger mold growth."
        ),
        CropStorageStandard(
            id = "groundnuts",
            cropName = "Groundnuts (Peanuts)",
            maxSafeMoisturePercent = 8.0,
            typicalHarvestMoisturePercent = 24.0,
            bulkDensityKgPerCubicMeter = 600.0,
            dryingGuide = "Invert windrows for 3-5 days, then dry pods on raised mats/A-frames to <=8% kernel moisture. Sort out shriveled/discolored pods immediately.",
            aflatoxinRiskLevel = "EXTREME — Highest aflatoxin risk crop; even minor pod insect injury or soil contact contaminates batches."
        ),
        CropStorageStandard(
            id = "rice",
            cropName = "Paddy Rice",
            maxSafeMoisturePercent = 13.0,
            typicalHarvestMoisturePercent = 22.0,
            bulkDensityKgPerCubicMeter = 580.0,
            dryingGuide = "Dry within 24 hours of harvest in shade/gentle sun (<45°C) to avoid fissure cracking during milling.",
            aflatoxinRiskLevel = "MODERATE — Stack heating ('yellow rice') occurs rapidly if bagged above 14% MC."
        ),
        CropStorageStandard(
            id = "sorghum",
            cropName = "Sorghum / Finger Millet",
            maxSafeMoisturePercent = 12.0,
            typicalHarvestMoisturePercent = 18.0,
            bulkDensityKgPerCubicMeter = 710.0,
            dryingGuide = "Winnow chaff thoroughly before bagging; fine grains compact tightly and retain heat if damp.",
            aflatoxinRiskLevel = "MODERATE — Weathering rain on panicles before harvest raises mold risk."
        ),
        CropStorageStandard(
            id = "coffee",
            cropName = "Coffee Parchment",
            maxSafeMoisturePercent = 10.5,
            typicalHarvestMoisturePercent = 52.0,
            bulkDensityKgPerCubicMeter = 420.0,
            dryingGuide = "Skin-dry rapidly then slow-dry on raised wire mesh tables over 10-14 days, covering at midday heat (>35°C) and at night to prevent Ochratoxin A and fading.",
            aflatoxinRiskLevel = "HIGH (Ochratoxin A) — Re-wetting parchment ruins cup quality and causes mould."
        )
    )

    fun calculateDryingAndStorage(
        standard: CropStorageStandard,
        wetWeightKg: Double,
        initialMoisturePercent: Double,
        targetMoisturePercent: Double = standard.maxSafeMoisturePercent
    ): DryingCalculationResult {
        val wIn = wetWeightKg.coerceAtLeast(1.0)
        val mIn = initialMoisturePercent.coerceIn(5.0, 65.0)
        val mOut = targetMoisturePercent.coerceIn(5.0, 25.0)

        // Dry matter conservation: W_final = W_initial * (100 - M_initial) / (100 - M_final)
        val finalDryKg = if (mIn <= mOut) wIn else wIn * ((100.0 - mIn) / (100.0 - mOut))
        val waterLoss = (wIn - finalDryKg).coerceAtLeast(0.0)
        val shrinkagePct = (waterLoss / wIn) * 100.0

        val picsBags = ceil(finalDryKg / 90.0).toInt()
        val silos500 = ceil(finalDryKg / 500.0).toInt()
        val isSafe = mOut <= standard.maxSafeMoisturePercent && mIn <= standard.maxSafeMoisturePercent

        val advice = when {
            mIn > standard.maxSafeMoisturePercent + 4.0 ->
                "TOO WET FOR SEALING: Evaporate ${String.format("%.1f", waterLoss)} kg water first. Sealing damp grain in PICS bags causes fermentation and severe aflatoxin!"
            mIn > standard.maxSafeMoisturePercent ->
                "NEEDS 1-2 DAYS SUN DRYING: Reduce moisture from ${String.format("%.1f", mIn)}% down to ${standard.maxSafeMoisturePercent}% before tying inner PICS liners."
            else ->
                "SAFE TO STORE: Moisture is within <=${standard.maxSafeMoisturePercent}% limit. Pack tightly into $picsBags PICS hermetic bags or $silos500 x 500kg metal silos on wooden pallets."
        }

        return DryingCalculationResult(
            cropName = standard.cropName,
            wetWeightKg = wIn,
            initialMoisturePercent = mIn,
            targetMoisturePercent = mOut,
            finalDryWeightKg = finalDryKg,
            waterLossKg = waterLoss,
            shrinkagePercent = shrinkagePct,
            pics90KgBagsNeeded = picsBags,
            metalSilo500KgNeeded = silos500,
            isSafeToStore = isSafe,
            advisoryMessage = advice
        )
    }

    val storagePests: List<StoragePestProfile> = listOf(
        StoragePestProfile(
            id = "lgb",
            commonName = "Larger Grain Borer (LGB / Osama)",
            scientificName = "Prostephanus truncatus",
            targetCommodities = listOf("Maize", "Dried Cassava", "Sorghum"),
            damageDescription = "Destructive cylindrical beetle boring deep tunnels through maize kernels and even wooden cribs, turning up to 35% of stored grain into fine dust within 3 months.",
            culturalAndHermeticControl = "Shell maize completely and seal in triple-layer PICS hermetic bags (squeeze out air, tie each layer separately) or airtight metal silos. Oxygen drops below 5% and kills adults + larvae without chemicals.",
            chemicalFumigationSop = "In open woven sacks: Actellic Super Dust (Pirimiphos-methyl 1.6% + Permethrin 0.3%) at 50 g per 90 kg bag mixed evenly. Aluminum Phosphide (Phostoxin) MUST ONLY be handled by licensed PCPB fumigators in gas-tight enclosures (never inside living houses!)."
        ),
        StoragePestProfile(
            id = "maize_weevil",
            commonName = "Maize Weevil",
            scientificName = "Sitophilus zeamais",
            targetCommodities = listOf("Maize", "Rice", "Wheat", "Sorghum"),
            damageDescription = "Snout beetle laying eggs inside kernels; larvae hollow grains from inside leaving characteristic round exit holes and heating/mold spots.",
            culturalAndHermeticControl = "Clean store thoroughly and burn old cob residues 2 weeks before harvest. Use hermetic PICS bags, metal silos, or food-grade Diatomaceous Earth (Dryacide) at 100g/90kg bag.",
            chemicalFumigationSop = "Actellic Super / Skana Super Dust at 50 g per 90 kg bag; store bags 15 cm off floor on wooden pallets and 50 cm from walls."
        ),
        StoragePestProfile(
            id = "bean_bruchid",
            commonName = "Bean Bruchid (Bean Weevil)",
            scientificName = "Acanthoscelides obtectus / Zabrotes subfasciatus",
            targetCommodities = listOf("Dry Beans", "Cowpeas", "Pigeon Peas"),
            damageDescription = "Infestation often starts in drying pods in the field; larvae bore clean circular windows in beans, ruining germination and market grade.",
            culturalAndHermeticControl = "Solarize dried beans on black polythene sheets in bright sun (55°C for 3 hours) or coat with 2-5 mL/kg vegetable oil (for food) or seal immediately in PICS hermetic bags.",
            chemicalFumigationSop = "For seed preservation only: Actellic Super Dust (50 g / 90 kg). Hermetic PICS bags preserve 100% food safety with zero chemical residue."
        ),
        StoragePestProfile(
            id = "flour_beetle",
            commonName = "Red Flour Beetle",
            scientificName = "Tribolium castaneum",
            targetCommodities = listOf("Milled Flour", "Crushed Feeds", "Groundnuts", "Broken Grain"),
            damageDescription = "Secondary pest thriving on broken kernels and dust left by weevils/LGB; secretes benzoquinones giving grain a foul pungent taint and pink discoloration.",
            culturalAndHermeticControl = "Winnow and sieve grain cleanly before storage; practice FIFO (First-In, First-Out) stock rotation and sweep store crevices weekly.",
            chemicalFumigationSop = "Residual store wall/floor spray with Pirimiphos-methyl EC BEFORE loading new harvest bags."
        )
    )
}
