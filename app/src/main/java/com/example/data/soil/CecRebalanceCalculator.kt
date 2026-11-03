package com.example.data.soil

import com.example.model.soil.CecCalculationResult
import com.example.model.soil.CecRatingCategory
import com.example.model.soil.SoilAmendmentRecommendation
import java.util.Locale
import kotlin.math.max
import kotlin.math.roundToInt

object CecRebalanceCalculator {

    /**
     * Calculates CEC, Base Saturation %, Cation Ratios, and amendment requirements
     * from laboratory soil test cation values (meq/100g or cmol(+)/kg).
     */
    fun calculateFromLabCations(
        calciumMeq: Double,
        magnesiumMeq: Double,
        potassiumMeq: Double,
        sodiumMeq: Double,
        exchangeableAcidityMeq: Double,
        soilPh: Double = 6.2
    ): CecCalculationResult {
        val totalBases = calciumMeq + magnesiumMeq + potassiumMeq + sodiumMeq
        val totalCec = max(0.5, totalBases + exchangeableAcidityMeq)

        val baseSat = (totalBases / totalCec) * 100.0
        val caSat = (calciumMeq / totalCec) * 100.0
        val mgSat = (magnesiumMeq / totalCec) * 100.0
        val kSat = (potassiumMeq / totalCec) * 100.0
        val naSat = (sodiumMeq / totalCec) * 100.0
        val acidSat = (exchangeableAcidityMeq / totalCec) * 100.0

        val caToMg = if (magnesiumMeq > 0.01) calciumMeq / magnesiumMeq else 0.0
        val mgToK = if (potassiumMeq > 0.01) magnesiumMeq / potassiumMeq else 0.0

        val rating = when {
            totalCec < 6.0 -> CecRatingCategory.VERY_LOW
            totalCec < 12.0 -> CecRatingCategory.LOW
            totalCec < 20.0 -> CecRatingCategory.MODERATE
            totalCec < 30.0 -> CecRatingCategory.HIGH
            else -> CecRatingCategory.VERY_HIGH
        }

        val stepByStep = listOf(
            "1. Total CEC Formula: Total CEC = Ca²⁺ + Mg²⁺ + K⁺ + Na⁺ + (Al³⁺ + H⁺)",
            "   Total CEC = ${f(calciumMeq)} + ${f(magnesiumMeq)} + ${f(potassiumMeq)} + ${f(sodiumMeq)} + ${f(exchangeableAcidityMeq)} = ${f(totalCec)} meq/100g (cmol/kg)",
            "2. Base Saturation (BS%): BS% = (Sum of Basic Cations / Total CEC) × 100",
            "   BS% = (${f(totalBases)} / ${f(totalCec)}) × 100 = ${f(baseSat)}%",
            "3. Basic Cation Saturation Ratios (BCSR Albrecht System):",
            "   • Calcium Saturation: (${f(calciumMeq)} / ${f(totalCec)}) × 100 = ${f(caSat)}% (Ideal: 65% – 75%)",
            "   • Magnesium Saturation: (${f(magnesiumMeq)} / ${f(totalCec)}) × 100 = ${f(mgSat)}% (Ideal: 10% – 15%)",
            "   • Potassium Saturation: (${f(potassiumMeq)} / ${f(totalCec)}) × 100 = ${f(kSat)}% (Ideal: 3% – 5%)",
            "   • Sodium Saturation (ESP): (${f(sodiumMeq)} / ${f(totalCec)}) × 100 = ${f(naSat)}% (Ideal: < 3%)",
            "   • Exchangeable Acidity: (${f(exchangeableAcidityMeq)} / ${f(totalCec)}) × 100 = ${f(acidSat)}% (Ideal: < 5%)",
            "4. Cation Balance Ratios:",
            "   • Ca:Mg Ratio = ${f(caToMg)}:1 (Ideal: 5.0:1 – 7.0:1 for optimal clay flocculation and aeration)",
            "   • Mg:K Ratio = ${f(mgToK)}:1 (Ideal: 2.0:1 – 3.0:1 to avoid potassium-induced magnesium deficiency)"
        )

        val notes = mutableListOf<String>()
        if (caSat < 65.0) {
            notes.add("Calcium Deficit (${f(caSat)}% vs 65% target): Weak soil flocculation, prone to crusting; cell wall fragility in crops (tip-burn, blossom end rot).")
        } else if (caSat > 75.0) {
            notes.add("Calcium Excess (${f(caSat)}%): May suppress magnesium and potassium uptake through competitive cation inhibition.")
        } else {
            notes.add("Calcium in Ideal Zone (${f(caSat)}%): Excellent cell wall stability and soil physical aggregation.")
        }

        if (mgSat < 10.0) {
            notes.add("Magnesium Deficit (${f(mgSat)}% vs 10% target): Risk of interveinal chlorosis on older leaves; poor chlorophyll synthesis.")
        } else if (mgSat > 18.0) {
            notes.add("Magnesium Excess (${f(mgSat)}%): High Mg tightens heavy clay platelets, creating sticky, poorly aerated, compacted soil.")
        }

        if (kSat < 3.0) {
            notes.add("Potassium Deficit (${f(kSat)}% vs 3% target): Reduced drought tolerance, weak stems, poor fruit sizing, and leaf edge necrosis.")
        } else if (kSat > 7.0) {
            notes.add("Potassium Luxury Level (${f(kSat)}%): Excess potassium directly blocks root magnesium and calcium absorption.")
        }

        if (naSat > 4.0) {
            notes.add("High Sodium Hazard (ESP = ${f(naSat)}%): Soil clay dispersion and surface sealing. Requires gypsum and leaching.")
        }

        if (baseSat < 60.0) {
            notes.add("Low Base Saturation (${f(baseSat)}%): Soil is acidic with high exchangeable aluminum; phosphorus is chemically fixed.")
        }

        val amendments = generateAmendments(totalCec, caSat, mgSat, kSat, naSat, soilPh)

        val omAdvice = when (rating) {
            CecRatingCategory.VERY_LOW, CecRatingCategory.LOW ->
                "CRITICAL: Soil colloidal bank is deficient. Apply 15–20 tons/ha of matured compost or farmyard manure annually. Incorporate biochar (10 tons/ha) for permanent negative charge sites that prevent potassium and nitrogen leaching."
            CecRatingCategory.MODERATE ->
                "Apply 8–12 tons/ha of compost every season to maintain biological humus. Plant legume green manures (Mucuna or Desmodium) in crop rotations to supply active carbon."
            CecRatingCategory.HIGH, CecRatingCategory.VERY_HIGH ->
                "Soil has massive colloidal holding capacity. Focus on organic surface mulching (3–5 tons/ha) to prevent surface sealing and maintain aerobic root aeration in heavy clay."
        }

        return CecCalculationResult(
            totalCec = totalCec,
            baseSaturationPercent = baseSat,
            caSaturationPercent = caSat,
            mgSaturationPercent = mgSat,
            kSaturationPercent = kSat,
            naSaturationPercent = naSat,
            aciditySaturationPercent = acidSat,
            caToMgRatio = caToMg,
            mgToKRatio = mgToK,
            rating = rating,
            soilConditionSummary = "${rating.title} (${f(totalCec)} meq/100g) with ${f(baseSat)}% Base Saturation. ${rating.nutrientHoldingAbility}",
            stepByStepDerivations = stepByStep,
            cationEvaluationNotes = notes,
            amendmentRecommendations = amendments,
            organicMatterImprovementAdvice = omAdvice
        )
    }

    /**
     * Estimates CEC from Soil Texture Fractions and Organic Matter %.
     */
    fun estimateFromSoilTexture(
        sandPercent: Double,
        siltPercent: Double,
        clayPercent: Double,
        organicMatterPercent: Double,
        soilPh: Double = 6.5
    ): CecCalculationResult {
        // Enforce normalization
        val total = sandPercent + siltPercent + clayPercent
        val s = if (total > 0) (sandPercent / total) * 100.0 else 40.0
        val si = if (total > 0) (siltPercent / total) * 100.0 else 40.0
        val c = if (total > 0) (clayPercent / total) * 100.0 else 20.0
        val om = max(0.1, organicMatterPercent)

        // Standard pedotransfer CEC model:
        // Sand CEC ≈ 0.05 meq/100g per 1%; Silt CEC ≈ 0.15; Clay (mixed kaolinite/smectite) ≈ 0.65; Humus OM ≈ 2.0 meq/100g per 1%
        val cecSand = 0.05 * s
        val cecSilt = 0.15 * si
        val cecClay = 0.65 * c
        val cecOm = 2.0 * om
        val estimatedCec = max(1.0, cecSand + cecSilt + cecClay + cecOm)

        // Estimate typical base distribution based on soil pH
        val estimatedBaseSat = when {
            soilPh < 5.0 -> 35.0
            soilPh < 5.5 -> 50.0
            soilPh < 6.0 -> 65.0
            soilPh < 6.5 -> 78.0
            soilPh < 7.2 -> 88.0
            else -> 95.0
        }

        val totalBases = estimatedCec * (estimatedBaseSat / 100.0)
        val estimatedCa = totalBases * 0.70
        val estimatedMg = totalBases * 0.18
        val estimatedK = totalBases * 0.08
        val estimatedNa = totalBases * 0.04
        val estimatedAcidity = max(0.0, estimatedCec - totalBases)

        val result = calculateFromLabCations(
            calciumMeq = estimatedCa,
            magnesiumMeq = estimatedMg,
            potassiumMeq = estimatedK,
            sodiumMeq = estimatedNa,
            exchangeableAcidityMeq = estimatedAcidity,
            soilPh = soilPh
        )

        val updatedDerivations = mutableListOf(
            "Colloidal Pedotransfer Formula for Estimated CEC:",
            "CEC_est = (0.05 × %Sand) + (0.15 × %Silt) + (0.65 × %Clay) + (2.0 × %Organic Matter)",
            "• Sand Contribution (${f(s)}%): ${f(cecSand)} meq/100g",
            "• Silt Contribution (${f(si)}%): ${f(cecSilt)} meq/100g",
            "• Clay Contribution (${f(c)}%): ${f(cecClay)} meq/100g",
            "• Humic Organic Matter (${f(om)}%): ${f(cecOm)} meq/100g",
            "Total Estimated Colloidal CEC = ${f(estimatedCec)} meq/100g (cmol/kg)"
        )
        updatedDerivations.addAll(result.stepByStepDerivations)

        return result.copy(
            stepByStepDerivations = updatedDerivations,
            soilConditionSummary = "Estimated ${result.rating.title} of ${f(estimatedCec)} meq/100g based on ${f(c)}% Clay and ${f(om)}% Organic Matter."
        )
    }

    private fun generateAmendments(
        totalCec: Double,
        caSat: Double,
        mgSat: Double,
        kSat: Double,
        naSat: Double,
        soilPh: Double
    ): List<SoilAmendmentRecommendation> {
        val list = mutableListOf<SoilAmendmentRecommendation>()

        // 1. Calcium Rebalancing
        if (caSat < 65.0) {
            val caDeficitPercent = 68.0 - caSat // target 68%
            val caDeficitMeq = (caDeficitPercent / 100.0) * totalCec

            if (soilPh < 6.2) {
                // Acidic: Agricultural Limestone (CaCO3)
                // 1 meq Ca/100g = 200 mg Ca/kg = 400 kg Ca/ha in top 15cm (2,000,000 kg soil).
                // Pure CaCO3 is 40% Ca => requires ~1,000 kg CaCO3 per 1 meq Ca deficit / ha.
                val limeKgHa = caDeficitMeq * 1000.0 * 1.15 // adjusting for 87% CCE efficiency
                list.add(
                    SoilAmendmentRecommendation(
                        amendmentName = "Agricultural Limestone (Calcium Carbonate - CaCO₃)",
                        targetDeficit = "Calcium saturation is low (${f(caSat)}% vs 68% target) with acidic soil pH (${f(soilPh)}).",
                        primaryFunction = "Neutralizes toxic exchangeable aluminum (Al³⁺), raises soil pH, and supplies essential calcium for root and fruit cell wall elasticity.",
                        rateKgPerHectare = limeKgHa,
                        rateKgPerAcre = limeKgHa * 0.4047,
                        applicationTimingAndMethod = "Broadcast uniformly across dry plowed land 4–6 weeks before planting; thoroughly incorporate to 15–20 cm depth with disc harrow.",
                        precautions = "Do not apply simultaneously with ammonium fertilizers (DAP or Urea) to avoid volatile ammonia gas loss. Split if rate exceeds 4,000 kg/ha."
                    )
                )
            } else {
                // Neutral/Alkaline: Gypsum (CaSO4.2H2O) - supplies calcium without raising pH
                val gypsumKgHa = caDeficitMeq * 1720.0
                list.add(
                    SoilAmendmentRecommendation(
                        amendmentName = "Agricultural Gypsum (Calcium Sulfate - CaSO₄·2H₂O)",
                        targetDeficit = "Calcium saturation is low (${f(caSat)}% vs 68% target) while soil pH (${f(soilPh)}) is already optimal/neutral.",
                        primaryFunction = "Supplies highly soluble calcium (22% Ca) and plant-available sulfate sulfur (18% S) without increasing soil pH; promotes soil crumb aggregation.",
                        rateKgPerHectare = gypsumKgHa,
                        rateKgPerAcre = gypsumKgHa * 0.4047,
                        applicationTimingAndMethod = "Incorporate into seedbed prior to planting or side-dress along crop rows before flowering (e.g. for groundnuts/potatoes).",
                        precautions = "Ensure adequate irrigation to dissolve gypsum crystals into the root zone."
                    )
                )
            }
        }

        // 2. Magnesium Rebalancing
        if (mgSat < 10.0) {
            val mgDeficitPercent = 12.0 - mgSat
            val mgDeficitMeq = (mgDeficitPercent / 100.0) * totalCec

            if (soilPh < 6.0) {
                val doloKgHa = mgDeficitMeq * 920.0
                list.add(
                    SoilAmendmentRecommendation(
                        amendmentName = "Dolomitic Limestone (CaCO₃·MgCO₃)",
                        targetDeficit = "Magnesium saturation is deficient (${f(mgSat)}% vs 12% target) in acidic soil.",
                        primaryFunction = "Dual-action liming agent supplying both magnesium (~10% Mg) and calcium (~20% Ca) while raising pH.",
                        rateKgPerHectare = doloKgHa,
                        rateKgPerAcre = doloKgHa * 0.4047,
                        applicationTimingAndMethod = "Broadcast and incorporate 4 weeks pre-planting.",
                        precautions = "Avoid using if soil Ca:Mg ratio is already below 3:1."
                    )
                )
            } else {
                val epsomKgHa = mgDeficitMeq * 1230.0
                list.add(
                    SoilAmendmentRecommendation(
                        amendmentName = "Epsom Salt (Magnesium Sulfate - MgSO₄·7H₂O)",
                        targetDeficit = "Magnesium deficit (${f(mgSat)}% vs 12% target) in neutral or alkaline soil.",
                        primaryFunction = "Rapidly delivers 100% water-soluble magnesium (9.8% Mg) and sulfur without shifting soil pH.",
                        rateKgPerHectare = epsomKgHa.coerceAtMost(350.0),
                        rateKgPerAcre = (epsomKgHa * 0.4047).coerceAtMost(140.0),
                        applicationTimingAndMethod = "Apply as basal side-dress or inject through drip fertigation system in 3 split doses.",
                        precautions = "Keep separate from concentrated calcium solutions in stock tanks to prevent calcium sulfate precipitation."
                    )
                )
            }
        }

        // 3. Potassium Rebalancing
        if (kSat < 3.0) {
            val kDeficitPercent = 4.0 - kSat
            val kDeficitMeq = (kDeficitPercent / 100.0) * totalCec
            // 1 meq K/100g = 391 mg K/kg = 782 kg K/ha => ~1,884 kg K2SO4/ha per meq K
            val potSulfateKgHa = (kDeficitMeq * 1884.0).coerceAtMost(500.0)
            list.add(
                SoilAmendmentRecommendation(
                    amendmentName = "Sulfate of Potash (Potassium Sulfate - K₂SO₄ / 0-0-50)",
                    targetDeficit = "Potassium saturation is sub-optimal (${f(kSat)}% vs 4% target).",
                    primaryFunction = "Replenishes exchangeable potassium pool for enzyme activation, stomatal regulation, drought tolerance, and fruit sugar accumulation.",
                    rateKgPerHectare = potSulfateKgHa,
                    rateKgPerAcre = potSulfateKgHa * 0.4047,
                    applicationTimingAndMethod = "Split 50% at planting and 50% at flowering/fruit set.",
                    precautions = "On very low CEC soils (<8 meq/100g), never apply in single heavy dose; split into 3–4 micro-applications."
                )
            )
        }

        // 4. Sodium Displacement (Sodic Soil Reclamation)
        if (naSat > 4.0) {
            val excessNaPercent = naSat - 2.0 // target reduction to 2%
            val naMeqToDisplace = (excessNaPercent / 100.0) * totalCec
            val gypsumReclaimKgHa = naMeqToDisplace * 1720.0 * 1.25 // 25% leaching excess
            list.add(
                SoilAmendmentRecommendation(
                    amendmentName = "Gypsum for Sodic Soil Reclamation (CaSO₄·2H₂O)",
                    targetDeficit = "High Exchangeable Sodium Percentage (ESP = ${f(naSat)}% > 4.0%), causing clay dispersion and crusting.",
                    primaryFunction = "Soluble calcium (Ca²⁺) chemically displaces toxic sodium (Na⁺) from clay colloids, which is then flushed below the root zone.",
                    rateKgPerHectare = gypsumReclaimKgHa,
                    rateKgPerAcre = gypsumReclaimKgHa * 0.4047,
                    applicationTimingAndMethod = "Broadcast across surface; follow immediately with 150–200 mm of heavy leaching irrigation or flooding during monsoon to flush displaced sodium down.",
                    precautions = "Must have functional deep drainage ditches or tile drains to carry away leached sodium salts."
                )
            )
        }

        // 5. Humic Compost & Biochar Colloid Booster
        if (totalCec < 12.0) {
            val compostTonsHa = if (totalCec < 6.0) 20.0 else 12.0
            list.add(
                SoilAmendmentRecommendation(
                    amendmentName = "Fully Matured Humic Compost & Inoculated Biochar",
                    targetDeficit = "Low soil colloidal cation exchange sites (${f(totalCec)} meq/100g).",
                    primaryFunction = "Builds permanent negatively-charged carboxylic (-COO⁻) and phenolic (-O⁻) surface functional groups, increasing effective CEC by 2.0 meq/100g for every 1% organic matter gained.",
                    rateKgPerHectare = compostTonsHa * 1000.0,
                    rateKgPerAcre = (compostTonsHa * 0.4047) * 1000.0,
                    applicationTimingAndMethod = "Incorporate into top 15 cm before every planting cycle.",
                    precautions = "Ensure compost is fully cured and odorless (C:N < 15:1) to prevent nitrogen immobilization drawdown."
                )
            )
        }

        return list
    }

    private fun f(value: Double): String = String.format(Locale.US, "%.1f", value)
}
