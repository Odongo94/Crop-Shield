package com.example.data.soil

import com.example.model.soil.CompostRawMaterial
import com.example.model.soil.CompostingMethodGuide
import com.example.model.soil.CropManureSpec
import com.example.model.soil.ManureTonnageResult
import com.example.model.soil.ManureType
import java.util.Locale
import kotlin.math.max
import kotlin.math.roundToInt

object OrganicManureCatalog {

    val rawMaterials: List<CompostRawMaterial> = listOf(
        // Browns (Carbon-rich)
        CompostRawMaterial(
            name = "Dry Maize Stover / Sorghum Straw",
            category = "Brown (Carbon-Rich)",
            cnRatio = "60:1 – 70:1",
            moistureContent = "10% – 15%",
            decompositionSpeed = "Moderate (4–8 weeks)",
            roleAndPrecaution = "Provides physical pile aeration and structural pore space; chop into 5–10 cm lengths with machete to speed bacterial breakdown."
        ),
        CompostRawMaterial(
            name = "Dry Wheat / Rice Straw",
            category = "Brown (Carbon-Rich)",
            cnRatio = "80:1 – 90:1",
            moistureContent = "12% – 18%",
            decompositionSpeed = "Moderate (6–10 weeks)",
            roleAndPrecaution = "High cellulose and lignin; soak thoroughly with water or animal urine before layering to break dry hydrophobic resistance."
        ),
        CompostRawMaterial(
            name = "Dry Hardwood Sawdust / Wood Shavings",
            category = "Brown (Carbon-Rich)",
            cnRatio = "350:1 – 500:1",
            moistureContent = "8% – 12%",
            decompositionSpeed = "Slow (3–6 months)",
            roleAndPrecaution = "Extremely high carbon; use sparingly (max 10% of pile volume) and blend with high-nitrogen poultry manure to avoid long nitrogen immobilization."
        ),
        CompostRawMaterial(
            name = "Dry Deciduous Tree Leaves",
            category = "Brown (Carbon-Rich)",
            cnRatio = "45:1 – 60:1",
            moistureContent = "15% – 20%",
            decompositionSpeed = "Moderate (6–8 weeks)",
            roleAndPrecaution = "Rich in trace minerals brought up by deep tree taproots; shred or crush to prevent flat leaves matting into anaerobic slimy sheets."
        ),
        CompostRawMaterial(
            name = "Corrugated Cardboard & Waste Paper",
            category = "Brown (Carbon-Rich)",
            cnRatio = "300:1 – 350:1",
            moistureContent = "5% – 10%",
            decompositionSpeed = "Slow to Moderate",
            roleAndPrecaution = "Non-glossy, unprinted unbleached cardboard provides excellent carbon bedding for worm farms and compost pile bases; tear into strips and soak."
        ),
        CompostRawMaterial(
            name = "Coffee Husks / Parchment",
            category = "Brown (Carbon-Rich)",
            cnRatio = "40:1 – 50:1",
            moistureContent = "12% – 15%",
            decompositionSpeed = "Moderate",
            roleAndPrecaution = "Abundant in coffee growing zones; slightly acidic, balances well with alkaline wood ash dusting."
        ),

        // Greens (Nitrogen-rich)
        CompostRawMaterial(
            name = "Fresh Poultry Layer Droppings",
            category = "Green (Nitrogen-Rich)",
            cnRatio = "8:1 – 10:1",
            moistureContent = "65% – 75%",
            decompositionSpeed = "Extremely Fast (2–3 days)",
            roleAndPrecaution = "Ultimate microbial starter; explosive heat generator driving thermophilic phase (60°C). Always balance with 3 parts dry straw to prevent ammonia smell."
        ),
        CompostRawMaterial(
            name = "Fresh Dairy Cattle Dung / Slurry",
            category = "Green (Nitrogen-Rich)",
            cnRatio = "18:1 – 22:1",
            moistureContent = "75% – 85%",
            decompositionSpeed = "Fast",
            roleAndPrecaution = "Natural inoculant providing trillions of cellulolytic rumen bacteria and humifying enzymes; mix into water as an activating slurry."
        ),
        CompostRawMaterial(
            name = "Fresh Green Legume Biomass (Desmodium / Mucuna / Tithonia)",
            category = "Green (Nitrogen-Rich)",
            cnRatio = "12:1 – 15:1",
            moistureContent = "75% – 85%",
            decompositionSpeed = "Fast (1–2 weeks)",
            roleAndPrecaution = "Tithonia diversifolia (Mexican sunflower) and Mucuna pruriens foliage are nature's super-fertilisers, exceptionally rich in nitrogen and potassium."
        ),
        CompostRawMaterial(
            name = "Market & Kitchen Vegetable Scraps",
            category = "Green (Nitrogen-Rich)",
            cnRatio = "15:1 – 20:1",
            moistureContent = "80% – 90%",
            decompositionSpeed = "Fast (1–2 weeks)",
            roleAndPrecaution = "Provides readily fermentable sugars, organic acids, and moisture. Avoid cooked oily foods, meat, and dog/cat feces."
        ),
        CompostRawMaterial(
            name = "Fresh Coffee Pulp",
            category = "Green (Nitrogen-Rich)",
            cnRatio = "20:1 – 25:1",
            moistureContent = "70% – 80%",
            decompositionSpeed = "Fast",
            roleAndPrecaution = "High potassium and organic sugars; aerate well to prevent sour anaerobic fermentation."
        ),
        CompostRawMaterial(
            name = "Unbleached Wood Ash (Mineral Booster)",
            category = "Mineral Activator",
            cnRatio = "0:0 (Pure Minerals)",
            moistureContent = "0% – 5%",
            decompositionSpeed = "Instant",
            roleAndPrecaution = "Rich in potassium (5–8% K₂O), calcium, and magnesium. Dust very lightly between layers (1–2 kg per ton) to neutralize acidity; do not over-apply."
        )
    )

    val compostingMethods: List<CompostingMethodGuide> = listOf(
        CompostingMethodGuide(
            id = "method_berkeley_18day",
            methodName = "18-Day Berkeley Fast Hot Composting Method",
            durationText = "18 Days (Fastest thermal turnover)",
            difficultyLevel = "Advanced / High Labor",
            targetTemperatureRange = "55°C – 65°C (Maintained for 3+ days)",
            turningSchedule = "Turn on Days 4, 6, 8, 10, 12, 14, and 16",
            stepByStepProtocol = listOf(
                "Step 1 (Day 1 - Pile Assembly): Construct a pile minimum 1m x 1m x 1.5m high (at least 1 cubic meter) so heat is retained. Layer Browns and Greens in a 1:1 volume ratio (yielding ideal 25:1 to 30:1 C:N ratio).",
                "Step 2 (Moisture Activation): Water each layer until it reaches 55–60% moisture (the Fist Squeeze Test: squeeze a handful of compost firmly—one or two drops of water should seep between knuckles without running stream).",
                "Step 3 (Resting Phase, Days 1–4): Leave pile untouched. Thermophilic microbes multiply exponentially; core temperature surges to 55°C–65°C within 48–72 hours.",
                "Step 4 (First Turn on Day 4): Turn the pile from outside in: move the dry, cool outer layer into the center of the new pile, and shift the hot core material to the outer perimeter.",
                "Step 5 (Rapid Turning, Days 6, 8, 10, 12, 14): Turn every 2 days. This injects fresh atmospheric oxygen, preventing anaerobic odours and distributing heat evenly across all biomass.",
                "Step 6 (Finishing, Days 16–18): Temperature drops naturally to ambient 30°C–35°C. Earthworms begin colonizing the bottom edge. Volume contracts by 40–50%; sweet, earthy humus is ready for use."
            ),
            managementRules = listOf(
                "Minimum pile volume is 1 m³ (smaller heaps cannot retain thermophilic heat).",
                "If temperature exceeds 68°C, turn immediately: extreme heat kills beneficial actinomycetes and volatilizes nitrogen as ammonia.",
                "Cover pile with a breathable tarp to prevent heavy tropical rains leaching soluble nitrates."
            ),
            maturityChecklist = listOf(
                "Temperature has returned to ambient air temperature.",
                "Dark chocolate brown crumbly texture with zero recognizable original food/manure pieces.",
                "Pleasant forest floor earthy smell (geosmin odor, zero ammonia or rotten egg stench)."
            )
        ),
        CompostingMethodGuide(
            id = "method_aerobic_windrow",
            methodName = "Aerobic Windrow Farm Composting (Commercial Field Scale)",
            durationText = "8 – 12 Weeks",
            difficultyLevel = "Moderate (Tractor or Farm Crew)",
            targetTemperatureRange = "50°C – 60°C",
            turningSchedule = "Turn once per week for first 4 weeks, then fortnightly",
            stepByStepProtocol = listOf(
                "Step 1 (Windrow Layout): Form long trapezoidal windrows 2 to 3 meters wide at base, 1.5 meters high, and as long as field allows, oriented north-south for uniform solar warming.",
                "Step 2 (Raw Material Blending): Blend tractor-pulled cereal stover (maize/wheat) with dairy cattle manure or poultry litter using 60% Browns and 40% Greens.",
                "Step 3 (Watering & Inoculation): Spray dilute cow dung slurry or fermented compost tea across windrow to introduce dense populations of humifying fungi and cellulolytic bacteria.",
                "Step 4 (Scheduled Mechanical Turning): Turn with tractor front-end loader or compost turner weekly. Ensure internal core is swapped with outer crust.",
                "Step 5 (Maturation & Curing Phase, Weeks 7–12): Pile cools down; mesophilic fungi convert complex lignins into stable humic and fulvic acids; screen through 12 mm wire mesh."
            ),
            managementRules = listOf(
                "Keep base well-drained on slight slope (1–2%) to collect any runoff leachate.",
                "Monitor moisture weekly using the hand-squeeze test; sprinkle water during dry, windy weather."
            ),
            maturityChecklist = listOf(
                "Solvita Compost Maturity Index score of 7 or 8.",
                "C:N ratio stabilised between 12:1 and 15:1.",
                "Germination bioassay index > 80% on sensitive radish or cress seeds."
            )
        ),
        CompostingMethodGuide(
            id = "method_indore_heap_pit",
            methodName = "Indore Heap & Pit Composting (Sir Albert Howard System)",
            durationText = "10 – 14 Weeks",
            difficultyLevel = "Low to Moderate (Smallholder Friendly)",
            targetTemperatureRange = "45°C – 55°C",
            turningSchedule = "Turn on Week 2, Week 6, and Week 10",
            stepByStepProtocol = listOf(
                "Step 1 (Base Construction): Dig pit 1 meter deep, 1.8 meters wide, or build above-ground heap under shade tree. Place 15 cm base layer of rough brushwood for drainage.",
                "Step 2 (Layering Sequence): Alternate 15 cm of carbonaceous plant waste (weeds, straw, stover), 5 cm of animal dung/manure, and a light dusting of garden topsoil and wood ash.",
                "Step 3 (Heap Saturation): Moisten each layer with water until uniformly damp like a wrung-out sponge.",
                "Step 4 (Ventilation Chimneys): Insert 2 or 3 bamboo poles vertically through the center of heap, then withdraw them once pile is complete to leave vertical breathing chimneys.",
                "Step 5 (Three Strategic Turns): Turn after 2 weeks, after 6 weeks, and after 10 weeks to ensure complete breakdown of tough stalks."
            ),
            managementRules = listOf(
                "Locate pit/heap under dense shade to avoid scorching tropical sun drying the edges.",
                "Never allow standing stagnant water in pit bottom; construct drainage channel."
            ),
            maturityChecklist = listOf(
                "Uniform black-brown powdery crumb structure.",
                "Compost passes freely through a 10 mm sieve.",
                "No visual presence of weed seeds or insect pupae."
            )
        ),
        CompostingMethodGuide(
            id = "method_vermicompost",
            methodName = "Vermicomposting (Earthworm Castings with Eisenia fetida)",
            durationText = "6 – 8 Weeks Continuous",
            difficultyLevel = "Low (Requires Moisture Care)",
            targetTemperatureRange = "18°C – 25°C (Strictly non-thermophilic)",
            turningSchedule = "Never turn; worms do all turning and aeration",
            stepByStepProtocol = listOf(
                "Step 1 (Worm Bedding): Line wooden crate, plastic bin, or concrete worm trough with 20 cm of damp shredded corrugated cardboard, coconut coir, and aged leaf litter.",
                "Step 2 (Worm Stocking): Introduce Red Wiggler worms (Eisenia fetida) at stocking rate of 1 kg worms (approx. 1,000–1,500 worms) per square meter.",
                "Step 3 (Pre-Composted Feedstock): Feed aged, cooled manure (dairy or horse), vegetable kitchen scraps, and coffee grounds. NEVER add fresh hot poultry manure or citrus peels.",
                "Step 4 (Moisture Control): Maintain constant moisture at 65–75% (damp sponge consistency) in full shade (20°C–25°C); protect from ants using water moats.",
                "Step 5 (Castings Harvesting): Move feed to one side of the bin for 7 days so worms migrate toward fresh food, then scoop pure dark castings from the opposite side."
            ),
            managementRules = listOf(
                "Never let temperatures exceed 30°C: red worms die in hot compost heaps.",
                "Feed in shallow 3–5 cm layers every 3–5 days rather than dumping deep rotting waste."
            ),
            maturityChecklist = listOf(
                "Pure granular black 'coffee ground' consistency.",
                "pH naturally buffered to 6.8 – 7.2.",
                "Abundant worm cocoons (small golden amber beads) indicating healthy reproduction."
            )
        ),
        CompostingMethodGuide(
            id = "method_bokashi",
            methodName = "Bokashi Anaerobic Microbial Fermentation",
            durationText = "14 Days Ferment + 14 Days Soil Burying",
            difficultyLevel = "Easy / Fast",
            targetTemperatureRange = "Ambient (Airtight Fermentation)",
            turningSchedule = "Zero turning (Strictly anaerobic sealed bucket)",
            stepByStepProtocol = listOf(
                "Step 1 (Microbial Bran Inoculation): Prepare Bokashi bran by fermenting wheat/rice bran with Effective Microorganisms (EM-1: lactic acid bacteria, yeasts, photosynthetic bacteria) and molasses.",
                "Step 2 (Layering Food Waste): Chop raw and cooked kitchen scraps, vegetable trimmings, or dairy waste into small pieces; layer in airtight Bokashi bucket.",
                "Step 3 (Bran Dusting): Sprinkle 1 handful of Bokashi bran per 3–5 cm layer of food waste; press firmly with tamper to expel all air pockets.",
                "Step 4 (Airtight Sealing & Liquid Drain): Seal lid hermetically. Every 2–3 days, drain golden Bokashi tea from bottom tap (dilute 1:500 as potent liquid foliar bio-fertiliser).",
                "Step 5 (Soil Trenching): After 14 days of fermentation, bury pickled biomass into garden trench 25 cm deep; soil microbes fully digest fermented matter into plant-ready humus in 14 days."
            ),
            managementRules = listOf(
                "Keep bucket lid completely airtight; oxygen entry spoils fermentation into foul rot.",
                "White mycelial fungal fluff on surface is normal and beneficial; black mold indicates air leak."
            ),
            maturityChecklist = listOf(
                "Pleasant sweet-and-sour pickled cider vinegar aroma.",
                "Biomass appears pickled and softened (does not decompose until buried in soil)."
            )
        )
    )

    val cropManureSpecs: List<CropManureSpec> = listOf(
        CropManureSpec(
            cropId = "crop_maize",
            cropName = "Hybrid Maize / Corn",
            category = "Cereals",
            targetYieldPerHectare = "6.0 – 8.0 Metric Tons / ha (35–45 bags/acre)",
            baseTargetNitrogenKgPerHa = 120.0,
            baseTargetP2O5KgPerHa = 60.0,
            baseTargetK2OKgPerHa = 90.0,
            standardSpacingCm = "75 cm × 25 cm",
            plantPopulationPerHa = 53333,
            recommendedApplicationMethod = "Broadcast and plow down 3 weeks before planting, or spot-apply 2 handfuls per planting station mixed with topsoil."
        ),
        CropManureSpec(
            cropId = "crop_tomato",
            cropName = "Commercial Tomatoes (Field & Greenhouse)",
            category = "Solanaceous Vegetables",
            targetYieldPerHectare = "40.0 – 60.0 Metric Tons / ha",
            baseTargetNitrogenKgPerHa = 160.0,
            baseTargetP2O5KgPerHa = 80.0,
            baseTargetK2OKgPerHa = 220.0,
            standardSpacingCm = "60 cm × 45 cm",
            plantPopulationPerHa = 37000,
            recommendedApplicationMethod = "Incorporate heavily into planting ridges (1–2 kg cured manure per planting hole); topdress with vermicompost at first flower cluster."
        ),
        CropManureSpec(
            cropId = "crop_potato",
            cropName = "Irish Potatoes (Tubers)",
            category = "Root & Tuber Crops",
            targetYieldPerHectare = "30.0 – 40.0 Metric Tons / ha",
            baseTargetNitrogenKgPerHa = 140.0,
            baseTargetP2O5KgPerHa = 90.0,
            baseTargetK2OKgPerHa = 180.0,
            standardSpacingCm = "75 cm × 30 cm",
            plantPopulationPerHa = 44444,
            recommendedApplicationMethod = "Apply along planting furrows before laying seed tubers; ensure manure is 100% cured to prevent Streptomyces common scab."
        ),
        CropManureSpec(
            cropId = "crop_cabbage",
            cropName = "Cabbages & Brassicas (Heavy Feeders)",
            category = "Leafy Vegetables",
            targetYieldPerHectare = "50.0 – 70.0 Metric Tons / ha",
            baseTargetNitrogenKgPerHa = 180.0,
            baseTargetP2O5KgPerHa = 80.0,
            baseTargetK2OKgPerHa = 150.0,
            standardSpacingCm = "60 cm × 50 cm",
            plantPopulationPerHa = 33333,
            recommendedApplicationMethod = "High nitrogen layer manure is ideal; place 1 generous scoop (500g) in each transplant hole, mixed thoroughly with soil."
        ),
        CropManureSpec(
            cropId = "crop_onion",
            cropName = "Bulb Onions & Garlic",
            category = "Bulb Vegetables",
            targetYieldPerHectare = "25.0 – 35.0 Metric Tons / ha",
            baseTargetNitrogenKgPerHa = 110.0,
            baseTargetP2O5KgPerHa = 70.0,
            baseTargetK2OKgPerHa = 130.0,
            standardSpacingCm = "30 cm × 10 cm",
            plantPopulationPerHa = 330000,
            recommendedApplicationMethod = "Broadcast fine sifted compost across raised nursery and main beds 3 weeks before transplanting; avoid fresh manure near bulb neck."
        ),
        CropManureSpec(
            cropId = "crop_beans",
            cropName = "Common Dry Beans / Legumes",
            category = "Legumes",
            targetYieldPerHectare = "2.5 – 3.5 Metric Tons / ha",
            baseTargetNitrogenKgPerHa = 40.0, // Low N due to biological nitrogen fixation
            baseTargetP2O5KgPerHa = 50.0,
            baseTargetK2OKgPerHa = 60.0,
            standardSpacingCm = "45 cm × 15 cm",
            plantPopulationPerHa = 148000,
            recommendedApplicationMethod = "Apply moderate manure rich in phosphorus (poultry or pig) along planting line to stimulate early Rhizobium nodulation."
        ),
        CropManureSpec(
            cropId = "crop_avocado",
            cropName = "Hass Avocado (Perennial Tree)",
            category = "Fruit Trees",
            targetYieldPerHectare = "15.0 – 20.0 Metric Tons / ha",
            baseTargetNitrogenKgPerHa = 130.0,
            baseTargetP2O5KgPerHa = 45.0,
            baseTargetK2OKgPerHa = 160.0,
            standardSpacingCm = "600 cm × 600 cm (6m × 6m)",
            plantPopulationPerHa = 277,
            recommendedApplicationMethod = "Apply 20–40 kg of cured cattle/goat manure per tree spread evenly under canopy drip-line twice per year (at onset of rains); keep 20 cm away from trunk."
        ),
        CropManureSpec(
            cropId = "crop_coffee",
            cropName = "Arabica Coffee",
            category = "Cash Crops",
            targetYieldPerHectare = "2.5 – 3.5 MT clean coffee / ha",
            baseTargetNitrogenKgPerHa = 120.0,
            baseTargetP2O5KgPerHa = 40.0,
            baseTargetK2OKgPerHa = 140.0,
            standardSpacingCm = "275 cm × 275 cm",
            plantPopulationPerHa = 1320,
            recommendedApplicationMethod = "Place 1 debbe (20-liter bucket ≈ 10–15 kg) of cured farmyard manure per tree under foliage canopy; ring-mulch with dry grass."
        ),
        CropManureSpec(
            cropId = "crop_banana",
            cropName = "Highland Bananas / Plantains",
            category = "Fruit Crops",
            targetYieldPerHectare = "35.0 – 50.0 Metric Tons / ha",
            baseTargetNitrogenKgPerHa = 150.0,
            baseTargetP2O5KgPerHa = 40.0,
            baseTargetK2OKgPerHa = 350.0, // Extremely high potassium feeder
            standardSpacingCm = "300 cm × 300 cm (3m × 3m)",
            plantPopulationPerHa = 1111,
            recommendedApplicationMethod = "Apply 2 to 3 wheelbarrows (40–60 kg) of high-potassium goat or cattle manure per mat/stool every 6 months."
        ),
        CropManureSpec(
            cropId = "crop_roses",
            cropName = "Commercial Cut Roses",
            category = "Floriculture",
            targetYieldPerHectare = "200,000 – 250,000 stems / ha",
            baseTargetNitrogenKgPerHa = 180.0,
            baseTargetP2O5KgPerHa = 70.0,
            baseTargetK2OKgPerHa = 240.0,
            standardSpacingCm = "Bed density 8–10 plants/m²",
            plantPopulationPerHa = 80000,
            recommendedApplicationMethod = "Heavy pre-planting bed incorporation of pure vermicompost and steam-sterilised compost (30–40 tons/ha); surface mulch with decomposed pine bark."
        )
    )

    /**
     * Calculates the exact organic manure tonnage required to satisfy crop nutrient demands,
     * accounting for manure dry matter, total N %, and first-year mineralization rate.
     */
    fun calculateTonnage(
        cropSpec: CropManureSpec,
        manureType: ManureType,
        areaHectares: Double,
        organicFertilityAdjustment: Double = 1.0 // 0.8 for high organic soil, 1.2 for depleted soil
    ): ManureTonnageResult {
        val ha = max(0.01, areaHectares)
        val acres = ha * 2.47105

        // Effective Target N in kg/ha
        val targetN = cropSpec.baseTargetNitrogenKgPerHa * organicFertilityAdjustment

        // Compute available N per metric ton (1,000 kg) of fresh manure:
        // Fresh manure -> Dry Matter (DM) = 1,000 kg * (DM% / 100)
        // Total N in dry matter = DM * (totalNPercentDry / 100)
        // Available N in Year 1 = Total N * (year1NAvailabilityPercent / 100)
        val dryMatterPerTon = 1000.0 * (manureType.dryMatterPercent / 100.0)
        val totalNPerTon = dryMatterPerTon * (manureType.totalNPercentDry / 100.0)
        val availNPerTon = totalNPerTon * (manureType.year1NAvailabilityPercent / 100.0)

        // Tonnage needed per hectare to fulfill Target N:
        val tonsPerHa = max(1.0, targetN / availNPerTon)
        val tonsPerAcre = tonsPerHa * 0.4047

        val totalTonsHa = tonsPerHa * ha
        val totalTonsAcre = tonsPerAcre * acres

        val bags50kgHa = (totalTonsHa * 1000.0 / 50.0).roundToInt()
        val bags50kgAcre = (totalTonsAcre * 1000.0 / 50.0).roundToInt()

        // Per plant hole rate in kg
        val kgPerPlantHole = if (cropSpec.plantPopulationPerHa > 0) {
            (tonsPerHa * 1000.0) / cropSpec.plantPopulationPerHa
        } else 0.5

        val kgPerSquareMeter = (tonsPerHa * 1000.0) / 10000.0 // 1 ha = 10,000 m²

        // Nutrients supplied in Year 1:
        val totalP2O5PerTon = dryMatterPerTon * (manureType.totalP2O5PercentDry / 100.0)
        val availP2O5PerTon = totalP2O5PerTon * (manureType.year1P2O5AvailabilityPercent / 100.0)

        val totalK2OPerTon = dryMatterPerTon * (manureType.totalK2OPercentDry / 100.0)
        val availK2OPerTon = totalK2OPerTon * (manureType.year1K2OAvailabilityPercent / 100.0)

        val nSupplied = availNPerTon * tonsPerHa
        val p2o5Supplied = availP2O5PerTon * tonsPerHa
        val k2oSupplied = availK2OPerTon * tonsPerHa
        val dryMatterAdded = (tonsPerHa * (manureType.dryMatterPercent / 100.0)) * ha

        val steps = listOf(
            "1. Crop Target Nitrogen Demand: ${f(targetN)} kg N / hectare (for ${cropSpec.cropName}).",
            "2. Manure Nutritional Profile (${manureType.title}):",
            "   • Dry Matter: ${manureType.dryMatterPercent}% => 1 MT of fresh manure contains ${f(dryMatterPerTon)} kg dry matter.",
            "   • Total Nitrogen: ${manureType.totalNPercentDry}% on dry basis => ${f(totalNPerTon)} kg total N / MT.",
            "   • Year 1 Mineralization Rate: ${manureType.year1NAvailabilityPercent}% is available to crop roots this season.",
            "   • Available N per MT of fresh manure = ${f(totalNPerTon)} × (${manureType.year1NAvailabilityPercent}/100) = ${f(availNPerTon)} kg available N / MT.",
            "3. Manure Tonnage Formula:",
            "   Tonnage (MT/ha) = Target N (${f(targetN)} kg/ha) ÷ Available N per MT (${f(availNPerTon)} kg/MT)",
            "   Tonnage = ${f(tonsPerHa)} MT / hectare (${f(tonsPerAcre)} MT / acre).",
            "4. Packaging & Field Units:",
            "   • Equivalent in 50-kg sacks: ${(tonsPerHa * 20).roundToInt()} bags/ha (${(tonsPerAcre * 20).roundToInt()} bags/acre).",
            "   • Application per planting hole: ${f(kgPerPlantHole)} kg/hole (at ${cropSpec.plantPopulationPerHa} plants/ha).",
            "   • Application per m² of bed: ${f(kgPerSquareMeter)} kg/m².",
            "5. Total Plant-Available Nutrients Delivered in Year 1:",
            "   • Nitrogen (N): ${f(nSupplied)} kg / ha",
            "   • Phosphate (P₂O₅): ${f(p2o5Supplied)} kg / ha",
            "   • Potash (K₂O): ${f(k2oSupplied)} kg / ha",
            "   • Organic Dry Matter Added to Humus Pool: ${f(dryMatterAdded)} MT."
        )

        val tips = listOf(
            "Application Timing: Always apply manure 3–4 weeks prior to seed sowing to allow initial microbial breakdown and prevent ammonia burning of tender germinating roots.",
            "Incorporation Depth: Mix into the top 15–20 cm of topsoil immediately after broadcasting; leaving manure exposed to hot tropical sun causes up to 40% of nitrogen to volatilize into the atmosphere as ammonia gas within 48 hours.",
            "Curing Check: Never apply fresh, hot, smelling animal manure directly to crop roots. Ensure it has completed thermophilic fermentation (dark crumbly texture, earthy smell).",
            "Planting Hole Placement: Mix the recommended ${f(kgPerPlantHole)} kg of manure with an equal quantity of topsoil at the bottom of the hole, cover with 2 cm of plain soil, then place the seed or transplant on top."
        )

        return ManureTonnageResult(
            cropName = cropSpec.cropName,
            manureType = manureType,
            areaHectares = ha,
            areaAcres = acres,
            totalTonsRequiredHectare = totalTonsHa,
            totalTonsRequiredAcre = totalTonsAcre,
            total50kgBagsHectare = bags50kgHa,
            total50kgBagsAcre = bags50kgAcre,
            rateKgPerPlantHole = kgPerPlantHole,
            rateKgPerSquareMeter = kgPerSquareMeter,
            availableNSuppliedKgHa = nSupplied,
            availableP2O5SuppliedKgHa = p2o5Supplied,
            availableK2OSuppliedKgHa = k2oSupplied,
            totalDryMatterAddedTons = dryMatterAdded,
            calculationSteps = steps,
            agronomicApplicationTips = tips
        )
    }

    private fun f(value: Double): String = String.format(Locale.US, "%.1f", value)
}
