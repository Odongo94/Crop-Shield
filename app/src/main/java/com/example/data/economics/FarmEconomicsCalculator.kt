package com.example.data.economics

import com.example.model.economics.AgroDealerLineItem
import com.example.model.economics.CropEconomicsPreset
import com.example.model.economics.FarmEconomicsInput
import com.example.model.economics.FarmEconomicsResult
import kotlin.math.ceil

object FarmEconomicsCalculator {

    val cropPresets: List<CropEconomicsPreset> = listOf(
        CropEconomicsPreset(
            id = "maize",
            cropName = "Maize (Dry Grain)",
            expectedYieldKgPerHa = 4500.0,
            typicalMarketPriceKesPerKg = 45.0,
            seedCostKesPerHa = 9500.0,
            basalBags50KgPerHa = 3.0,
            topdressBags50KgPerHa = 3.0,
            chemicalSprayCostKesPerHa = 7500.0,
            organicManureTonnesPerHa = 5.0,
            laborAndOpsCostKesPerHa = 32000.0,
            irrigationCostKesPerHa = 0.0,
            notes = "25kg certified hybrid seed/ha; ~50 bags (90kg) grain/ha under good agronomy."
        ),
        CropEconomicsPreset(
            id = "tomato",
            cropName = "Tomato (Open Field)",
            expectedYieldKgPerHa = 32000.0,
            typicalMarketPriceKesPerKg = 55.0,
            seedCostKesPerHa = 28000.0,
            basalBags50KgPerHa = 4.0,
            topdressBags50KgPerHa = 5.0,
            chemicalSprayCostKesPerHa = 45000.0,
            organicManureTonnesPerHa = 15.0,
            laborAndOpsCostKesPerHa = 95000.0,
            irrigationCostKesPerHa = 42000.0,
            notes = "Includes nursery seedlings, trellising twine, preventive blight sprays, and drip/pump fuel."
        ),
        CropEconomicsPreset(
            id = "potato",
            cropName = "Irish Potato (Shangi)",
            expectedYieldKgPerHa = 22000.0,
            typicalMarketPriceKesPerKg = 38.0,
            seedCostKesPerHa = 85000.0,
            basalBags50KgPerHa = 5.0,
            topdressBags50KgPerHa = 3.0,
            chemicalSprayCostKesPerHa = 28000.0,
            organicManureTonnesPerHa = 10.0,
            laborAndOpsCostKesPerHa = 65000.0,
            irrigationCostKesPerHa = 18000.0,
            notes = "Certified seed tubers (~20 bags/ha) are the largest upfront cost; protects against Bacterial Wilt."
        ),
        CropEconomicsPreset(
            id = "french_beans",
            cropName = "French Beans (Export)",
            expectedYieldKgPerHa = 9500.0,
            typicalMarketPriceKesPerKg = 95.0,
            seedCostKesPerHa = 38000.0,
            basalBags50KgPerHa = 3.0,
            topdressBags50KgPerHa = 2.0,
            chemicalSprayCostKesPerHa = 24000.0,
            organicManureTonnesPerHa = 8.0,
            laborAndOpsCostKesPerHa = 85000.0,
            irrigationCostKesPerHa = 35000.0,
            notes = "Requires strict EU/GlobalG.A.P. PHI logs and intensive hand-picking labor."
        ),
        CropEconomicsPreset(
            id = "onion",
            cropName = "Red Bulb Onion",
            expectedYieldKgPerHa = 28000.0,
            typicalMarketPriceKesPerKg = 65.0,
            seedCostKesPerHa = 34000.0,
            basalBags50KgPerHa = 4.0,
            topdressBags50KgPerHa = 4.0,
            chemicalSprayCostKesPerHa = 32000.0,
            organicManureTonnesPerHa = 12.0,
            laborAndOpsCostKesPerHa = 78000.0,
            irrigationCostKesPerHa = 36000.0,
            notes = "Bulb curing & thrips/downy mildew spray rotation critical for Grade 1 marketability."
        ),
        CropEconomicsPreset(
            id = "cabbage",
            cropName = "Cabbage",
            expectedYieldKgPerHa = 40000.0,
            typicalMarketPriceKesPerKg = 25.0,
            seedCostKesPerHa = 18000.0,
            basalBags50KgPerHa = 4.0,
            topdressBags50KgPerHa = 4.0,
            chemicalSprayCostKesPerHa = 22000.0,
            organicManureTonnesPerHa = 12.0,
            laborAndOpsCostKesPerHa = 58000.0,
            irrigationCostKesPerHa = 25000.0,
            notes = "Focus budget on Diamondback Moth Bt/spinosad rotation and boron supplementation."
        )
    )

    fun defaultInputFor(preset: CropEconomicsPreset, hectares: Double = 1.0): FarmEconomicsInput {
        return FarmEconomicsInput(
            cropPreset = preset,
            areaHectares = hectares.coerceAtLeast(0.1),
            seedCostPerHa = preset.seedCostKesPerHa,
            fertilizerBagPriceKes = 3500.0, // Typical 50kg bag benchmark (KES)
            chemicalBudgetPerHa = preset.chemicalSprayCostKesPerHa,
            manureCostPerTonne = 2200.0,
            laborCostPerHa = preset.laborAndOpsCostKesPerHa,
            irrigationCostPerHa = preset.irrigationCostKesPerHa,
            expectedYieldKgPerHa = preset.expectedYieldKgPerHa,
            marketPriceKesPerKg = preset.typicalMarketPriceKesPerKg
        )
    }

    fun calculateBudget(input: FarmEconomicsInput): FarmEconomicsResult {
        val ha = input.areaHectares.coerceAtLeast(0.05)
        val acres = ha * 2.47105

        val basalBags = ceil(input.cropPreset.basalBags50KgPerHa * ha).toInt()
        val topdressBags = ceil(input.cropPreset.topdressBags50KgPerHa * ha).toInt()
        val totalFertBags = basalBags + topdressBags

        val totalSeedCost = input.seedCostPerHa * ha
        val totalFertCost = totalFertBags * input.fertilizerBagPriceKes
        val totalChemCost = input.chemicalBudgetPerHa * ha
        val manureTonnes = input.cropPreset.organicManureTonnesPerHa * ha
        val totalManureCost = manureTonnes * input.manureCostPerTonne
        val totalLaborCost = input.laborCostPerHa * ha
        val totalIrrigationCost = input.irrigationCostPerHa * ha

        val totalInputCost = totalSeedCost + totalFertCost + totalChemCost +
            totalManureCost + totalLaborCost + totalIrrigationCost

        val totalYieldKg = input.expectedYieldKgPerHa * ha
        val grossRevenue = totalYieldKg * input.marketPriceKesPerKg
        val netProfit = grossRevenue - totalInputCost
        val roiPercent = if (totalInputCost > 0.0) (netProfit / totalInputCost) * 100.0 else 0.0
        val breakEvenPrice = if (totalYieldKg > 0.0) totalInputCost / totalYieldKg else 0.0
        val breakEvenYield = if (input.marketPriceKesPerKg > 0.0) totalInputCost / input.marketPriceKesPerKg else 0.0

        // Agro-dealer retail pack shopping list
        val bottles500ml = ceil((totalChemCost / 1800.0).coerceAtLeast(1.0)).toInt()
        val knapsackSprayersNeeded = ceil(ha * 6.0).toInt()

        val shoppingList = listOf(
            AgroDealerLineItem(
                itemName = "Certified Seed / Seedlings (${input.cropPreset.cropName})",
                quantityText = "For ${String.format("%.2f", ha)} ha (${String.format("%.2f", acres)} acres)",
                estimatedCostKes = totalSeedCost,
                category = "Planting Material"
            ),
            AgroDealerLineItem(
                itemName = "Basal Planting Fertilizer (50 kg Bags)",
                quantityText = "$basalBags x 50kg bags (${basalBags * 50} kg)",
                estimatedCostKes = basalBags * input.fertilizerBagPriceKes,
                category = "Nutrition"
            ),
            AgroDealerLineItem(
                itemName = "Top-Dress Fertilizer (50 kg Bags)",
                quantityText = "$topdressBags x 50kg bags (${topdressBags * 50} kg)",
                estimatedCostKes = topdressBags * input.fertilizerBagPriceKes,
                category = "Nutrition"
            ),
            AgroDealerLineItem(
                itemName = "Composted Manure / Soil Amendment",
                quantityText = "${String.format("%.1f", manureTonnes)} tonnes (~${ceil(manureTonnes * 20).toInt()} wheelbarrows)",
                estimatedCostKes = totalManureCost,
                category = "Organic Matter"
            ),
            AgroDealerLineItem(
                itemName = "Crop Protection Packs (Fungicides / Insecticides)",
                quantityText = "~$bottles500ml retail packs (500 mL / 500 g equiv.)",
                estimatedCostKes = totalChemCost,
                category = "Pest & Disease"
            ),
            AgroDealerLineItem(
                itemName = "Irrigation, Pump Fuel & Labor Allocation",
                quantityText = "Full season ops (~$knapsackSprayersNeeded spray passes est.)",
                estimatedCostKes = totalLaborCost + totalIrrigationCost,
                category = "Operations"
            )
        )

        return FarmEconomicsResult(
            areaHectares = ha,
            areaAcres = acres,
            totalSeedCostKes = totalSeedCost,
            totalFertilizerCostKes = totalFertCost,
            totalChemicalCostKes = totalChemCost,
            totalManureCostKes = totalManureCost,
            totalLaborCostKes = totalLaborCost,
            totalIrrigationCostKes = totalIrrigationCost,
            totalInputCostKes = totalInputCost,
            totalExpectedYieldKg = totalYieldKg,
            expectedGrossRevenueKes = grossRevenue,
            netProfitKes = netProfit,
            roiPercent = roiPercent,
            breakEvenPriceKesPerKg = breakEvenPrice,
            breakEvenYieldKgTotal = breakEvenYield,
            shoppingList = shoppingList
        )
    }
}
