package com.example.data

import com.example.model.CropFeedingRegime
import com.example.model.CropFeedingStage

object CropFeedingRegimeCatalog {

    val allRegimes: List<CropFeedingRegime> = listOf(
        // 1. Tomatoes
        CropFeedingRegime(
            cropId = "feed_tomato",
            cropName = "Commercial Tomatoes (Field & Greenhouse)",
            category = "Solanaceous",
            targetYieldPerHectare = "50 – 70 MT / ha",
            nitrogenRemovalPerTon = 2.8,
            phosphateRemovalPerTon = 0.8,
            potashRemovalPerTon = 4.5,
            calciumRemovalPerTon = 2.2,
            magnesiumRemovalPerTon = 0.6,
            optimalSoilPhRange = "6.0 – 6.8",
            maxTolerableSoilEC = "2.5 dS/m",
            stages = listOf(
                CropFeedingStage(
                    stageName = "1. Basal / Transplanting Establishment",
                    timingDaysAfterPlanting = "DAP 0 – 14",
                    nutritionalGoal = "Vigorous taproot and lateral root system formation; rapid shock recovery.",
                    primaryNutrientsNeeded = "High Phosphorus (P), Starter Nitrogen (N), Calcium (Ca), Zinc (Zn)",
                    standardSoilApplication = "DAP (18-46-0) at 50 kg/acre OR NPK 11-44-11 at 40 kg/acre + 1 kg cured compost per hole.",
                    fertigationRecipeDrip = "Monoammonium Phosphate (MAP 12-61-0) 2.5 kg/ha/day + Calcium Nitrate 3.0 kg/ha/day (separate tanks).",
                    targetFertigationEC = "1.2 – 1.4 mS/cm",
                    targetFertigationPH = "5.8 – 6.0",
                    foliarBoosterSpray = "Zinc Chelate (1.5 ml/L) + Seaweed Root Extract (2 ml/L) at DAP 10.",
                    agronomicPrecautions = "Never mix Calcium Nitrate with MAP or sulfates in the same concentrate tank to avoid calcium phosphate precipitation."
                ),
                CropFeedingStage(
                    stageName = "2. Early Vegetative Branching",
                    timingDaysAfterPlanting = "DAP 15 – 35",
                    nutritionalGoal = "Sturdy vine architecture, thick green leaf canopy, rapid internode development.",
                    primaryNutrientsNeeded = "Balanced Nitrogen (N), Magnesium (Mg), Sulfur (S)",
                    standardSoilApplication = "CAN (26% N) at 50 kg/acre side-dressed 10 cm from stem.",
                    fertigationRecipeDrip = "NPK 19-19-19 (water soluble) 4.0 kg/ha/day + Magnesium Sulfate 1.5 kg/ha/day.",
                    targetFertigationEC = "1.5 – 1.8 mS/cm",
                    targetFertigationPH = "5.8 – 6.2",
                    foliarBoosterSpray = "High-Nitrogen + Trace Elements foliar (2.5 g/L) weekly.",
                    agronomicPrecautions = "Excessive nitrogen at this stage causes lush leafy stems with delayed flower truss initiation."
                ),
                CropFeedingStage(
                    stageName = "3. Flowering & First Fruit Set",
                    timingDaysAfterPlanting = "DAP 36 – 55",
                    nutritionalGoal = "High pollen viability, flower cluster retention, cell division in newly set fruitlets.",
                    primaryNutrientsNeeded = "Phosphorus (P), Calcium (Ca), Boron (B), Potassium (K)",
                    standardSoilApplication = "NPK 17-17-17 at 50 kg/acre + Gypsum 50 kg/acre.",
                    fertigationRecipeDrip = "Calcium Nitrate 5.0 kg/ha/day + Monopotassium Phosphate (MKP 0-52-34) 3.0 kg/ha/day.",
                    targetFertigationEC = "1.8 – 2.2 mS/cm",
                    targetFertigationPH = "5.8 – 6.0",
                    foliarBoosterSpray = "Chelated Calcium-Boron (2.0 ml/L) sprayed directly onto flower trusses early morning.",
                    agronomicPrecautions = "Boron and Calcium are immobile in plants; foliar sprays must reach flower trusses to prevent Blossom End Rot."
                ),
                CropFeedingStage(
                    stageName = "4. Fruit Expansion & Sizing",
                    timingDaysAfterPlanting = "DAP 56 – 85",
                    nutritionalGoal = "Fruit weight accumulation, thick pericarp wall, sugar translocation.",
                    primaryNutrientsNeeded = "High Potassium (K₂O), Calcium (Ca), Nitrogen (N)",
                    standardSoilApplication = "Sulfate of Potash (SOP 0-0-50) 40 kg/acre + CAN 40 kg/acre.",
                    fertigationRecipeDrip = "Potassium Nitrate (13-0-46) 6.0 kg/ha/day + Calcium Nitrate 4.0 kg/ha/day.",
                    targetFertigationEC = "2.2 – 2.5 mS/cm",
                    targetFertigationPH = "5.8 – 6.2",
                    foliarBoosterSpray = "High-Potassium foliar (e.g. 10-10-40) at 3 g/L every 10 days.",
                    agronomicPrecautions = "Maintain steady moisture; erratic drying followed by heavy watering triggers fruit skin cracking."
                ),
                CropFeedingStage(
                    stageName = "5. Ripening & Extended Harvest",
                    timingDaysAfterPlanting = "DAP 86 – 120+",
                    nutritionalGoal = "Lycopene red color synthesis, high Brix sugars, firm firmness for shelf life.",
                    primaryNutrientsNeeded = "Potassium (K), Magnesium (Mg), Micronutrients",
                    standardSoilApplication = "Potassium Sulfate (SOP) 30 kg/acre every 3 weeks during harvesting.",
                    fertigationRecipeDrip = "Potassium Sulfate 4.0 kg/ha/day + Potassium Nitrate 3.0 kg/ha/day.",
                    targetFertigationEC = "2.0 – 2.4 mS/cm",
                    targetFertigationPH = "6.0 – 6.5",
                    foliarBoosterSpray = "Potassium Silicate (1.5 ml/L) for thick peel and extended shipping durability.",
                    agronomicPrecautions = "Stop nitrogen feeding 2 weeks before final harvest to prevent soft fruit and fungal transit decay."
                )
            ),
            deficiencySymptomsAndCure = listOf(
                "Blossom End Rot (BER): Black sunken leathery rot on fruit bottom caused by localized Calcium deficiency. Cure: Spray Calcium-Boron immediately and stabilize irrigation.",
                "Magnesium Interveinal Chlorosis: Yellowing between green veins on older bottom leaves. Cure: Fertigate Magnesium Sulfate (Epsom salts) at 20 kg/ha.",
                "Potassium Edge Scorch: Yellowing and burning of outer leaflet margins with poor fruit ripening (green shoulders). Cure: Apply soluble Potassium Sulfate."
            ),
            agronomistProTips = "Keep K:Ca ratio in fertigation at 1:1 during early flowering, then elevate to 2:1 or 3:1 during fruit bulking to maximize fruit size and sweetness."
        ),

        // 2. Irish Potatoes
        CropFeedingRegime(
            cropId = "feed_potato",
            cropName = "Irish Potatoes (Tubers)",
            category = "Tubers",
            targetYieldPerHectare = "35 – 45 MT / ha",
            nitrogenRemovalPerTon = 3.5,
            phosphateRemovalPerTon = 1.2,
            potashRemovalPerTon = 5.8,
            calciumRemovalPerTon = 0.9,
            magnesiumRemovalPerTon = 0.5,
            optimalSoilPhRange = "5.2 – 6.4",
            maxTolerableSoilEC = "1.7 dS/m",
            stages = listOf(
                CropFeedingStage(
                    stageName = "1. Furrow Planting & Sprout Emergence",
                    timingDaysAfterPlanting = "DAP 0 – 20",
                    nutritionalGoal = "Strong root emergence from seed tuber eyes and initial stolon development.",
                    primaryNutrientsNeeded = "High Phosphorus (P), Starter Nitrogen (N), Calcium (Ca)",
                    standardSoilApplication = "DAP (18-46-0) at 75–100 kg/acre placed in furrow 5 cm away from tuber + 2 tons cured manure/acre.",
                    fertigationRecipeDrip = "MAP (12-61-0) 3.0 kg/ha/day + Calcium Nitrate 2.5 kg/ha/day.",
                    targetFertigationEC = "1.2 – 1.4 mS/cm",
                    targetFertigationPH = "5.5 – 6.0",
                    foliarBoosterSpray = "Seaweed extract + Zinc (1.5 ml/L) at 80% sprout emergence.",
                    agronomicPrecautions = "Never allow raw fertilizer granules to touch the seed tuber; burns sprouts and introduces bacterial soft rot."
                ),
                CropFeedingStage(
                    stageName = "2. Canopy Emergence & Stolon Initiation",
                    timingDaysAfterPlanting = "DAP 21 – 40",
                    nutritionalGoal = "Rapid canopy closure and induction of underground stolons.",
                    primaryNutrientsNeeded = "Nitrogen (N), Magnesium (Mg), Phosphorus (P)",
                    standardSoilApplication = "CAN (26% N) at 50 kg/acre topdressed right before earthing up / hilling.",
                    fertigationRecipeDrip = "NPK 19-19-19 4.0 kg/ha/day + Magnesium Sulfate 2.0 kg/ha/day.",
                    targetFertigationEC = "1.5 – 1.8 mS/cm",
                    targetFertigationPH = "5.5 – 6.2",
                    foliarBoosterSpray = "Complete micronutrient spray (Fe, Mn, Zn, Cu, B, Mo).",
                    agronomicPrecautions = "Hilling/earthing up is critical at this stage to protect forming stolons from light exposure and tuber greening."
                ),
                CropFeedingStage(
                    stageName = "3. Tuber Initiation & Hooking",
                    timingDaysAfterPlanting = "DAP 41 – 60",
                    nutritionalGoal = "Stolon tips swell into tubers; determines total tuber count per plant.",
                    primaryNutrientsNeeded = "Phosphorus (P), Calcium (Ca), Potassium (K)",
                    standardSoilApplication = "NPK 17-17-17 at 50 kg/acre + Gypsum 50 kg/acre.",
                    fertigationRecipeDrip = "MKP (0-52-34) 4.0 kg/ha/day + Calcium Nitrate 3.5 kg/ha/day.",
                    targetFertigationEC = "1.8 – 2.0 mS/cm",
                    targetFertigationPH = "5.5 – 6.0",
                    foliarBoosterSpray = "Soluble Calcium + Boron (2 ml/L) to prevent internal brown spot.",
                    agronomicPrecautions = "Avoid water stress during tuber hooking; water deficit now reduces tuber set to only 3–4 tubers per hill."
                ),
                CropFeedingStage(
                    stageName = "4. Tuber Bulking (Peak Demand)",
                    timingDaysAfterPlanting = "DAP 61 – 85",
                    nutritionalGoal = "Maximum starch accumulation in tubers; high volume daily weight gain.",
                    primaryNutrientsNeeded = "Very High Potassium (K₂O), Magnesium (Mg)",
                    standardSoilApplication = "Sulfate of Potash (SOP 0-0-50) at 50 kg/acre side-dressed along ridge.",
                    fertigationRecipeDrip = "Potassium Sulfate (0-0-50) 6.0 kg/ha/day + Magnesium Sulfate 2.0 kg/ha/day.",
                    targetFertigationEC = "2.0 – 2.3 mS/cm",
                    targetFertigationPH = "5.5 – 6.2",
                    foliarBoosterSpray = "High-Potassium foliar (0-0-50) 3 g/L every 10 days.",
                    agronomicPrecautions = "Never apply Muriate of Potash (KCl): excess chloride lowers potato specific gravity (dry matter %) and causes soggy chips."
                ),
                CropFeedingStage(
                    stageName = "5. Skin Setting & De-haulming",
                    timingDaysAfterPlanting = "DAP 86 – 105",
                    nutritionalGoal = "Periderm skin thickening to withstand mechanized harvest abrasion.",
                    primaryNutrientsNeeded = "Zero Nitrogen; Low Potassium",
                    standardSoilApplication = "None. Withhold all mineral fertilizers.",
                    fertigationRecipeDrip = "Withhold all fertilizers 14 days before vine killing.",
                    targetFertigationEC = "< 1.0 mS/cm",
                    targetFertigationPH = "Neutral",
                    foliarBoosterSpray = "None. Cut haulms (vines) 14 days prior to digging.",
                    agronomicPrecautions = "De-haulm (slash vines) at 90 days and leave tubers in ground 14 days so skin cures; prevents storage rotting."
                )
            ),
            deficiencySymptomsAndCure = listOf(
                "Internal Rust Spot / Hollow Heart: Brown dead patches or cavities inside tuber caused by rapid uneven growth and localized Calcium/Boron deficit. Cure: Side-dress soluble gypsum during tuber initiation.",
                "Potassium Deficiency: Bronzing and curling of upper leaves with premature canopy collapse; small tubers. Cure: Apply SOP 0-0-50.",
                "Common Scab (Streptomyces): Raised corky lesions on tuber skin worsened by high soil pH (>6.5) and dry soil during tuber set. Cure: Maintain pH below 6.0 and irrigate uniformly."
            ),
            agronomistProTips = "Potatoes require 1.5 times more potassium than nitrogen. Always prioritize Sulfate of Potash (SOP) over Potassium Chloride (MOP) to guarantee high processing dry matter (>21%)."
        ),

        // 3. Hybrid Maize
        CropFeedingRegime(
            cropId = "feed_maize",
            cropName = "Hybrid Maize / Corn (Grain & Silage)",
            category = "Cereals",
            targetYieldPerHectare = "7.0 – 10.0 MT / ha",
            nitrogenRemovalPerTon = 18.0,
            phosphateRemovalPerTon = 8.0,
            potashRemovalPerTon = 16.0,
            calciumRemovalPerTon = 3.0,
            magnesiumRemovalPerTon = 2.5,
            optimalSoilPhRange = "5.8 – 7.0",
            maxTolerableSoilEC = "1.7 dS/m",
            stages = listOf(
                CropFeedingStage(
                    stageName = "1. Planting / Basal Starter",
                    timingDaysAfterPlanting = "DAP 0 (At Planting)",
                    nutritionalGoal = "Seminal and nodal root establishment; cold soil phosphorus uptake.",
                    primaryNutrientsNeeded = "High Phosphorus (P), Starter Nitrogen (N), Zinc (Zn)",
                    standardSoilApplication = "DAP (18-46-0) at 50 kg/acre OR NPK 23-23-0 at 60 kg/acre placed 5 cm to side and 5 cm below seed.",
                    fertigationRecipeDrip = "MAP (12-61-0) 25 kg/ha at emergence.",
                    targetFertigationEC = "1.2 – 1.4 mS/cm",
                    targetFertigationPH = "6.0 – 6.5",
                    foliarBoosterSpray = "Zinc Chelate seed coating or foliar spray at V3 (3-leaf stage).",
                    agronomicPrecautions = "Never place fertilizer in direct contact with seed kernel; salt index causes seedling burning and stand loss."
                ),
                CropFeedingStage(
                    stageName = "2. Knee-Height (V6 - V8 Stage)",
                    timingDaysAfterPlanting = "DAP 28 – 35",
                    nutritionalGoal = "Ear shoot initiation; determines number of kernel rows on the cob (e.g. 14, 16, 18 rows).",
                    primaryNutrientsNeeded = "High Nitrogen (N), Sulfur (S)",
                    standardSoilApplication = "First Topdressing: CAN (26% N) at 50 kg/acre or Urea (46% N) at 35 kg/acre side-dressed in moist soil.",
                    fertigationRecipeDrip = "Urea-Ammonium Nitrate (UAN) or dissolved Urea at 35 kg N/ha.",
                    targetFertigationEC = "1.5 – 1.8 mS/cm",
                    targetFertigationPH = "6.0 – 6.5",
                    foliarBoosterSpray = "Complete multimineral foliar spray with Sulfur and Zinc.",
                    agronomicPrecautions = "Do not apply Urea on dry soil surface without immediate rain or irrigation; volatile ammonia loss exceeds 30%."
                ),
                CropFeedingStage(
                    stageName = "3. Tasseling & Silking (VT - R1 Stage)",
                    timingDaysAfterPlanting = "DAP 55 – 65",
                    nutritionalGoal = "Maximum pollen shed, silk emergence, kernel fertilization.",
                    primaryNutrientsNeeded = "Nitrogen (N), Potassium (K), Boron (B)",
                    standardSoilApplication = "Second Topdressing: CAN at 50 kg/acre applied before tasseling if soil has high leaching.",
                    fertigationRecipeDrip = "Potassium Nitrate (13-0-46) 30 kg/ha through drip.",
                    targetFertigationEC = "1.6 – 2.0 mS/cm",
                    targetFertigationPH = "6.0 – 6.5",
                    foliarBoosterSpray = "Soluble Boron (1.0 g/L) for pollen tube elongation.",
                    agronomicPrecautions = "Water deficit at silking causes silk delay (pollen-silk desynchronization), resulting in blank cob tips."
                ),
                CropFeedingStage(
                    stageName = "4. Grain Filling / Dent Stage (R3 - R5)",
                    timingDaysAfterPlanting = "DAP 70 – 95",
                    nutritionalGoal = "Starch translocation into kernels; kernel test weight (density).",
                    primaryNutrientsNeeded = "Potassium (K), Magnesium (Mg) for stay-green",
                    standardSoilApplication = "None (relying on prior topdressing).",
                    fertigationRecipeDrip = "Potassium Sulfate 15 kg/ha if under center pivot/drip.",
                    targetFertigationEC = "1.5 mS/cm",
                    targetFertigationPH = "6.2 – 6.8",
                    foliarBoosterSpray = "High-K foliar + Magnesium for delayed leaf senescence (stay-green).",
                    agronomicPrecautions = "Protect green leaf canopy against Northern Corn Leaf Blight to ensure full grain filling."
                )
            ),
            deficiencySymptomsAndCure = listOf(
                "Nitrogen V-Shaped Chlorosis: Yellowing starting at leaf tip and progressing down the midrib in a V-pattern on older leaves. Cure: Side-dress CAN immediately.",
                "Phosphorus Purple Leaves: Dark purple/reddish pigmentation on young leaves and stunted roots during cool weather. Cure: Basal band DAP or apply foliar MAP.",
                "Zinc White Bud: Broad pale bleached bands on both sides of midrib on emerging leaves. Cure: Apply Zinc Sulfate (10 kg/ha) or foliar chelated zinc."
            ),
            agronomistProTips = "Maize absorbs 75% of its total nitrogen between knee-height (V8) and grain fill (R3). Split topdressing (half at knee-height, half at shoulder-height) yields 20% higher efficiency than single heavy application."
        ),

        // 4. Commercial Cut Roses
        CropFeedingRegime(
            cropId = "feed_rose",
            cropName = "Commercial Cut Roses (Greenhouse)",
            category = "Floriculture",
            targetYieldPerHectare = "220,000 stems / ha / year",
            nitrogenRemovalPerTon = 4.2,
            phosphateRemovalPerTon = 1.1,
            potashRemovalPerTon = 5.2,
            calciumRemovalPerTon = 3.0,
            magnesiumRemovalPerTon = 0.8,
            optimalSoilPhRange = "5.5 – 6.2",
            maxTolerableSoilEC = "1.5 dS/m",
            stages = listOf(
                CropFeedingStage(
                    stageName = "1. Sprout Flush & Shoot Elongation",
                    timingDaysAfterPlanting = "Days 1 – 15 of Flush Cycle",
                    nutritionalGoal = "Rapid cell division, long sturdy flowering stems, lush green foliage.",
                    primaryNutrientsNeeded = "Balanced N, P, Calcium (Ca), Magnesium (Mg)",
                    standardSoilApplication = "Drip fertigation continuous injection.",
                    fertigationRecipeDrip = "PPM Target in Dripper: N 160 ppm, P 40 ppm, K 180 ppm, Ca 140 ppm, Mg 35 ppm, Fe-DTPA 1.5 ppm.",
                    targetFertigationEC = "1.4 – 1.6 mS/cm",
                    targetFertigationPH = "5.6 – 5.8",
                    foliarBoosterSpray = "Amino acid chelate + Seaweed extract weekly.",
                    agronomicPrecautions = "Maintain strict substrate EC monitoring (slurry test daily) to avoid salt burn on tender root hairs."
                ),
                CropFeedingStage(
                    stageName = "2. Flower Bud Formation & Sepal Opening",
                    timingDaysAfterPlanting = "Days 16 – 30 of Flush Cycle",
                    nutritionalGoal = "Large flower bud diameter, thick sepal, strong pedicel neck.",
                    primaryNutrientsNeeded = "High Potassium (K), Calcium (Ca), Boron (B)",
                    standardSoilApplication = "Drip fertigation continuous injection.",
                    fertigationRecipeDrip = "PPM Target: N 140 ppm, P 35 ppm, K 240 ppm, Ca 160 ppm, Mg 40 ppm, Boron 0.3 ppm.",
                    targetFertigationEC = "1.6 – 1.8 mS/cm",
                    targetFertigationPH = "5.7 – 5.9",
                    foliarBoosterSpray = "Calcium Nitrate + Boron foliar spray directed onto flower buds.",
                    agronomicPrecautions = "Calcium deficiency during bud formation causes 'bent neck' disorder where flower stem droops postharvest."
                ),
                CropFeedingStage(
                    stageName = "3. Harvest & Post-Cut Resting",
                    timingDaysAfterPlanting = "Harvest to Day 5 Post-Harvest",
                    nutritionalGoal = "Rapid healing of cut stems, basal shoot activation for next harvest flush.",
                    primaryNutrientsNeeded = "Starter N and P for new shoot bud activation",
                    standardSoilApplication = "Drip fertigation.",
                    fertigationRecipeDrip = "MAP (12-61-0) flush + Calcium Nitrate to stimulate basal bottom breaks.",
                    targetFertigationEC = "1.4 mS/cm",
                    targetFertigationPH = "5.8",
                    foliarBoosterSpray = "Copper hydroxide fungicide + Zinc foliar to seal cut wounds.",
                    agronomicPrecautions = "Flush lines with fresh acidified water (pH 5.5, EC 0.4) once monthly to clear emitter salts."
                )
            ),
            deficiencySymptomsAndCure = listOf(
                "Bent Neck Disorder: Flower pedicel wilts and snaps just below the bloom due to low calcium in stem xylem. Cure: Increase Ca in fertigation and optimize greenhouse transpiration.",
                "Iron Interveinal Chlorosis: Youngest top leaves turn pale yellow/ivory with bright green veins caused by pH > 6.5. Cure: Lower water pH to 5.7 and inject Fe-EDDHA or Fe-DTPA chelate.",
                "Potassium Margin Burn: Browning and curling of leaflet margins. Cure: Elevate Potassium Nitrate and SOP."
            ),
            agronomistProTips = "Use two separate stock fertilizer tanks: Tank A (Calcium Nitrate, Potassium Nitrate, Iron Chelate) and Tank B (MAP, MKP, Potassium Sulfate, Magnesium Sulfate, Micronutrients). Combining them in one tank precipitates insoluble calcium sulfate and calcium phosphate sludge."
        ),

        // 5. Cabbages & Brassicas
        CropFeedingRegime(
            cropId = "feed_cabbage",
            cropName = "Cabbages & Brassicas (Heavy Feeders)",
            category = "Leafy Vegetables",
            targetYieldPerHectare = "60 – 80 MT / ha",
            nitrogenRemovalPerTon = 3.6,
            phosphateRemovalPerTon = 0.9,
            potashRemovalPerTon = 4.2,
            calciumRemovalPerTon = 2.8,
            magnesiumRemovalPerTon = 0.4,
            optimalSoilPhRange = "6.0 – 7.2",
            maxTolerableSoilEC = "1.8 dS/m",
            stages = listOf(
                CropFeedingStage(
                    stageName = "1. Nursery to Field Transplanting",
                    timingDaysAfterPlanting = "DAP 0 – 14",
                    nutritionalGoal = "Overcome transplanting shock; rapid fibrous root development.",
                    primaryNutrientsNeeded = "Phosphorus (P), Calcium (Ca), Starter N",
                    standardSoilApplication = "DAP (18-46-0) at 50 kg/acre or NPK 17-17-17 at 75 kg/acre + 1 scoop poultry manure/hole.",
                    fertigationRecipeDrip = "MAP (12-61-0) 3 kg/ha/day + Calcium Nitrate 3 kg/ha/day.",
                    targetFertigationEC = "1.2 – 1.4 mS/cm",
                    targetFertigationPH = "6.0 – 6.5",
                    foliarBoosterSpray = "Rooting hormone + Soluble Zinc foliar at transplant dip.",
                    agronomicPrecautions = "Brassicas are susceptible to Clubroot disease in acidic soils (pH < 6.5); lime soil heavily to pH 7.0 if history of clubroot."
                ),
                CropFeedingStage(
                    stageName = "2. Vegetative Rosette Expansion",
                    timingDaysAfterPlanting = "DAP 15 – 35",
                    nutritionalGoal = "Maximal leaf surface area development; thick wax bloom.",
                    primaryNutrientsNeeded = "High Nitrogen (N), Sulfur (S), Magnesium (Mg)",
                    standardSoilApplication = "First Topdressing: CAN (26% N) at 75 kg/acre or Ammonium Sulfate at 60 kg/acre.",
                    fertigationRecipeDrip = "Urea or CAN 4.0 kg/ha/day + Magnesium Sulfate 1.5 kg/ha/day.",
                    targetFertigationEC = "1.5 – 1.8 mS/cm",
                    targetFertigationPH = "6.0 – 6.5",
                    foliarBoosterSpray = "High-N foliar + Sulfur (brassicas have high sulfur requirement for glucosinolates).",
                    agronomicPrecautions = "Keep soil uniformly moist; dry spells trigger early pest flare-ups (diamondback moth and aphids)."
                ),
                CropFeedingStage(
                    stageName = "3. Head Cupping & Firming",
                    timingDaysAfterPlanting = "DAP 36 – 65",
                    nutritionalGoal = "Tight head compacting, solid internal leaf folding, weight density.",
                    primaryNutrientsNeeded = "Potassium (K), Calcium (Ca), Boron (B)",
                    standardSoilApplication = "Second Topdressing: CAN 50 kg/acre + Sulfate of Potash 30 kg/acre.",
                    fertigationRecipeDrip = "Potassium Nitrate (13-0-46) 5.0 kg/ha/day + Calcium Nitrate 3.0 kg/ha/day.",
                    targetFertigationEC = "1.8 – 2.2 mS/cm",
                    targetFertigationPH = "6.2 – 6.8",
                    foliarBoosterSpray = "Calcium + Boron foliar spray (2 ml/L) to prevent internal head tip-burn and hollow stem.",
                    agronomicPrecautions = "Calcium must be adequate during head cupping; internal leaf tip-burn is invisible from outside until cabbage is cut open."
                ),
                CropFeedingStage(
                    stageName = "4. Final Head Maturation",
                    timingDaysAfterPlanting = "DAP 66 – 85",
                    nutritionalGoal = "Head firmness, transport durability, split resistance.",
                    primaryNutrientsNeeded = "Potassium (K)",
                    standardSoilApplication = "Sulfate of Potash (SOP) 25 kg/acre.",
                    fertigationRecipeDrip = "Potassium Sulfate 3.0 kg/ha/day.",
                    targetFertigationEC = "1.6 – 1.8 mS/cm",
                    targetFertigationPH = "6.2 – 6.8",
                    foliarBoosterSpray = "Potassium Silicate for firm protective head wraps.",
                    agronomicPrecautions = "Cease heavy nitrogen feeding 3 weeks before harvest; excess nitrogen causes loose puffy heads that rot in transit."
                )
            ),
            deficiencySymptomsAndCure = listOf(
                "Internal Tip-Burn: Browning and rotting of internal head leaf edges caused by localized Calcium deficiency during rapid growth. Cure: Apply soluble Calcium-Boron and avoid excessive nitrogen.",
                "Hollow Stem: Large cavity in central cabbage core caused by Boron deficiency and rapid growth. Cure: Apply Borax (10 kg/ha) or foliar Solubor.",
                "Sulfur Deficiency: Uniform pale yellowing of young leaves (unlike nitrogen which affects older leaves first). Cure: Apply Ammonium Sulfate."
            ),
            agronomistProTips = "Cabbages are among the heaviest nitrogen and sulfur consumers in agriculture. Topdressing twice with CAN + Ammonium Sulfate guarantees dense, heavy heads that command premium market prices."
        )
    )

    fun getRegimeForCrop(cropId: String): CropFeedingRegime? {
        return allRegimes.find { it.cropId.equals(cropId, ignoreCase = true) }
            ?: allRegimes.firstOrNull { it.cropName.contains(cropId, ignoreCase = true) }
    }
}
