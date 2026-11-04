package com.example.data.poultry

import com.example.model.poultry.ChickVaccineEntry
import com.example.model.poultry.PoultryDailyPlanSummary
import com.example.model.poultry.PoultryFeedAgeStage
import com.example.model.poultry.PoultryFlockType
import com.example.model.poultry.VaccineAdministrationRoute

/**
 * Authoritative Poultry Management Catalog:
 * 1. Comprehensive Chick Vaccination Calendar from Day 1 to adult layers/broilers.
 * 2. Age-Stratified Precision Feeding Programs (Pre-Starter, Starter, Grower, Pre-Lay, Layers, Broilers).
 */
object PoultryManagementCatalog {

    val vaccinationSchedule: List<ChickVaccineEntry> = listOf(
        // Day 1
        ChickVaccineEntry(
            id = "vax_day1_mareks",
            targetFlockType = setOf(PoultryFlockType.LAYERS, PoultryFlockType.INDIGENOUS_IMPROVED, PoultryFlockType.BROILERS),
            ageDays = 1,
            ageDisplay = "Day 1 (Hatchery)",
            targetDisease = "Marek's Disease & Infectious Bronchitis (IB)",
            vaccineNameAndStrain = "HVT + Rispens (CVI988) & IB Primer (Ma5 / H120)",
            route = VaccineAdministrationRoute.SUBCUTANEOUS_INJECTION,
            importanceAndPathology = "Marek's is an oncogenic herpesvirus causing visceral tumors, leg paralysis ('one leg forward, one leg back'), and mortality. Vaccination at Day 1 provides lifelong cell-mediated immune protection.",
            preparationAndHandlingSop = "Typically administered at hatchery. If done on-farm, inject 0.2 ml subcutaneously into the back of the neck using an automatic vaccinator. Coarse spray IB vaccine simultaneously over chick crates.",
            contraindicationsAndAlerts = "Never inject intramuscularly into neck vertebrae. Must maintain cold chain (-196°C liquid nitrogen for cell-associated Marek's; 2-8°C for freeze-dried)."
        ),
        // Day 7
        ChickVaccineEntry(
            id = "vax_day7_newcastle_ib",
            targetFlockType = setOf(PoultryFlockType.LAYERS, PoultryFlockType.BROILERS, PoultryFlockType.INDIGENOUS_IMPROVED),
            ageDays = 7,
            ageDisplay = "Day 7 (Week 1)",
            targetDisease = "Newcastle Disease (ND) & Infectious Bronchitis (IB)",
            vaccineNameAndStrain = "Newcastle Disease HB1 (Hitchner B1) + IB H120 / Ma5",
            route = VaccineAdministrationRoute.EYE_DROP,
            importanceAndPathology = "Newcastle is a devastating paramyxovirus causing greenish diarrhea, torticollis (twisted neck), respiratory distress, and 100% flock mortality. Eye drop delivery bypasses maternal antibodies and stimulates mucosal immunity in the Harderian gland.",
            preparationAndHandlingSop = "Reconstitute freeze-dried pellet using sterile diluent provided. Hold chick horizontally and apply exactly one calibrated drop (0.03 ml) into open eye. Hold chick until drop is absorbed by blinking.",
            contraindicationsAndAlerts = "Do not expose reconstituted vaccine to direct sunlight. Discard any reconstituted vaccine not used within 2 hours."
        ),
        // Day 14
        ChickVaccineEntry(
            id = "vax_day14_gumboro",
            targetFlockType = setOf(PoultryFlockType.LAYERS, PoultryFlockType.BROILERS, PoultryFlockType.INDIGENOUS_IMPROVED),
            ageDays = 14,
            ageDisplay = "Day 14 (Week 2)",
            targetDisease = "Infectious Bursal Disease (IBD / Gumboro) - 1st Dose",
            vaccineNameAndStrain = "Gumboro Intermediate Strain (D78 / Bursine-2)",
            route = VaccineAdministrationRoute.DRINKING_WATER,
            importanceAndPathology = "IBD virus targets and destroys the Bursa of Fabricius, causing severe permanent immunosuppression in young chicks and making them susceptible to all secondary bacterial infections.",
            preparationAndHandlingSop = "Withdraw drinking water for 1.5 - 2 hours prior to vaccination. Mix skimmed milk powder (2.5 g/L) into non-chlorinated water to neutralize residual chlorine and protect live viral titer. All chicks must drink within 90 - 120 minutes.",
            contraindicationsAndAlerts = "Never use chlorinated tap water (chlorine kills live vaccine virus). Avoid water sanitizers 48 hours before and 24 hours after vaccination."
        ),
        // Day 21
        ChickVaccineEntry(
            id = "vax_day21_gumboro_booster",
            targetFlockType = setOf(PoultryFlockType.LAYERS, PoultryFlockType.BROILERS, PoultryFlockType.INDIGENOUS_IMPROVED),
            ageDays = 21,
            ageDisplay = "Day 21 (Week 3)",
            targetDisease = "Infectious Bursal Disease (IBD / Gumboro) - Booster",
            vaccineNameAndStrain = "Gumboro Intermediate Plus Strain (228E / Winterfield 2512)",
            route = VaccineAdministrationRoute.DRINKING_WATER,
            importanceAndPathology = "Breaks through waning maternal antibodies to reinforce long-term bursal mucosal immunity during the high-susceptibility window (3 - 6 weeks).",
            preparationAndHandlingSop = "Same as Day 14: 1.5 hr water starvation, non-chlorinated well/rain water stabilized with 2.5 g/L non-fat skimmed milk powder. Provide sufficient drinker bell space so all chicks access vaccine simultaneously.",
            contraindicationsAndAlerts = "Intermediate Plus strains can cause mild bursal atrophy in very young birds; strictly avoid administering earlier than Day 18."
        ),
        // Day 28 (Week 4)
        ChickVaccineEntry(
            id = "vax_day28_newcastle_lasota",
            targetFlockType = setOf(PoultryFlockType.LAYERS, PoultryFlockType.BROILERS, PoultryFlockType.INDIGENOUS_IMPROVED),
            ageDays = 28,
            ageDisplay = "Day 28 (Week 4)",
            targetDisease = "Newcastle Disease (ND) - 2nd Dose / Booster",
            vaccineNameAndStrain = "Newcastle Disease LaSota Strain",
            route = VaccineAdministrationRoute.DRINKING_WATER,
            importanceAndPathology = "LaSota is a lentogenic strain that provides stronger systemic immunity than HB1 to protect against virulent velogenic field challenge during the growing phase.",
            preparationAndHandlingSop = "Administer in drinking water with skimmed milk powder (2.5 g/L) early in the morning before temperatures rise. Ensure drinkers are clean and free of disinfectant residue.",
            contraindicationsAndAlerts = "Can cause transient mild snicking (respiratory cough) 3 - 5 days post-vaccination. Keep brooding house warm and well-ventilated."
        ),
        // Week 6 (Day 42)
        ChickVaccineEntry(
            id = "vax_week6_fowl_pox",
            targetFlockType = setOf(PoultryFlockType.LAYERS, PoultryFlockType.INDIGENOUS_IMPROVED),
            ageDays = 42,
            ageDisplay = "Week 6 (Day 42)",
            targetDisease = "Fowl Pox (Avipoxvirus)",
            vaccineNameAndStrain = "Live Attenuated Fowl Pox Vaccine",
            route = VaccineAdministrationRoute.WING_WEB_STAB,
            importanceAndPathology = "Transmitted by mosquitoes and skin abrasions. Causes cutaneous dry pox (wart-like scabs on comb, wattle, eyelids) and wet diphtheritic pox (necrotic caseous plugs in trachea causing asphyxiation).",
            preparationAndHandlingSop = "Dip double-grooved wing-web needle into reconstituted vaccine. Pierce membranous web on underside of wing avoiding bone, muscle, and major blood vessels. Check for successful 'take' (swollen scab nodule) on day 7 on 10% of birds.",
            contraindicationsAndAlerts = "Do not spill vaccine on feathers or skin. If less than 90% of birds show a 'take' on day 7, re-vaccinate the entire flock immediately."
        ),
        // Week 8 (Day 56)
        ChickVaccineEntry(
            id = "vax_week8_fowl_typhoid",
            targetFlockType = setOf(PoultryFlockType.LAYERS, PoultryFlockType.INDIGENOUS_IMPROVED),
            ageDays = 56,
            ageDisplay = "Week 8 (Day 56)",
            targetDisease = "Fowl Typhoid (Salmonella gallinarum)",
            vaccineNameAndStrain = "Live Attenuated Salmonella gallinarum (9R Strain)",
            route = VaccineAdministrationRoute.SUBCUTANEOUS_INJECTION,
            importanceAndPathology = "Causes severe septicemia, bronze-colored enlarged liver with necrotic foci, splenomegaly, watery yellow-green diarrhea, and up to 50% mortality in growing pullets and adult layers.",
            preparationAndHandlingSop = "Inject 0.2 ml per bird subcutaneously under neck skin or intramuscularly into breast. Use sterile needles, changing needle every 100 birds.",
            contraindicationsAndAlerts = "Never administer antibiotics (Sulfonamides, Enrofloxacin, Amoxicillin) within 7 days before or 14 days after 9R vaccination as antibiotics kill the live vaccine strain."
        ),
        // Week 10 (Day 70)
        ChickVaccineEntry(
            id = "vax_week10_deworming",
            targetFlockType = setOf(PoultryFlockType.LAYERS, PoultryFlockType.INDIGENOUS_IMPROVED),
            ageDays = 70,
            ageDisplay = "Week 10 (Day 70)",
            targetDisease = "Internal Parasites & Roundworms (Ascaridia galli & Heterakis)",
            vaccineNameAndStrain = "Veterinary Anthelmintic (Piperazine Citrate or Levamisole HCl)",
            route = VaccineAdministrationRoute.DRINKING_WATER,
            importanceAndPathology = "Roundworms cause intestinal obstruction, nutrient competition, unthriftiness, poor feed conversion ratio (FCR), and stunted pullet skeletal frame.",
            preparationAndHandlingSop = "Dissolve anthelmintic powder into daily drinking water volume according to body weight instructions. Repeat single booster treatment after 14 days to break worm life cycle.",
            contraindicationsAndAlerts = "Ensure clean litter. Remove damp floor patches around drinkers to prevent re-infection from embryonated worm eggs."
        ),
        // Week 12 (Day 84)
        ChickVaccineEntry(
            id = "vax_week12_coryza",
            targetFlockType = setOf(PoultryFlockType.LAYERS, PoultryFlockType.INDIGENOUS_IMPROVED),
            ageDays = 84,
            ageDisplay = "Week 12 (Day 84)",
            targetDisease = "Infectious Coryza (Avibacterium paragallinarum - Serovars A, B, C)",
            vaccineNameAndStrain = "Inactivated Trivalent Coryza Oil Emulsion Bacterin",
            route = VaccineAdministrationRoute.INTRAMUSCULAR_INJECTION,
            importanceAndPathology = "Causes acute facial swelling, foul-smelling nasal discharge, conjunctivitis, and severe drop in egg production (10 - 40%). Inactivated vaccine provides high protective circulating antibodies.",
            preparationAndHandlingSop = "Bring oil emulsion vaccine to room temperature (20 - 25°C) before injecting. Shake vial vigorously. Inject 0.5 ml intramuscularly into breast muscle.",
            contraindicationsAndAlerts = "Never inject cold vaccine directly from refrigerator (causes severe tissue shock and muscle necrosis)."
        ),
        // Week 16 (Day 112)
        ChickVaccineEntry(
            id = "vax_week16_newcastle_lasota_booster",
            targetFlockType = setOf(PoultryFlockType.LAYERS, PoultryFlockType.INDIGENOUS_IMPROVED),
            ageDays = 112,
            ageDisplay = "Week 16 (Day 112)",
            targetDisease = "Newcastle Disease & Infectious Bronchitis Pre-Lay Booster",
            vaccineNameAndStrain = "ND LaSota + IB Primer",
            route = VaccineAdministrationRoute.DRINKING_WATER,
            importanceAndPathology = "Pre-lay booster ensures maximum maternal antibody titer in upcoming eggs and prevents catastrophic production drops during peak lay.",
            preparationAndHandlingSop = "Administer in drinking water with skimmed milk powder (2.5 g/L).",
            contraindicationsAndAlerts = "Must be administered at least 2 weeks before first egg drops."
        ),
        // Week 18 (Day 126)
        ChickVaccineEntry(
            id = "vax_week18_eds",
            targetFlockType = setOf(PoultryFlockType.LAYERS, PoultryFlockType.INDIGENOUS_IMPROVED),
            ageDays = 126,
            ageDisplay = "Week 18 (Day 126 - Point of Lay)",
            targetDisease = "Egg Drop Syndrome (EDS-76) + ND + IB (Inactivated Triple)",
            vaccineNameAndStrain = "Killed Triple Oil Emulsion (EDS-76 + ND + IB)",
            route = VaccineAdministrationRoute.INTRAMUSCULAR_INJECTION,
            importanceAndPathology = "EDS adenovirus causes soft-shelled, shell-less, and depigmented eggs with severe persistent production collapse. Inactivated oil vaccine protects flock throughout peak lay.",
            preparationAndHandlingSop = "Warm to room temperature (25°C). Shake well. Inject 0.5 ml intramuscularly into breast muscle or subcutaneously at neck base.",
            contraindicationsAndAlerts = "Must be administered before onset of egg lay (point of lay at 18 - 19 weeks). Repeat ND LaSota live booster in drinking water every 8 - 10 weeks during the laying cycle."
        )
    )

    // =========================================================================
    // AGE-STRATIFIED FEEDING PROGRAMS (Layers, Broilers, Improved Kienyeji)
    // =========================================================================
    val feedingPrograms: List<PoultryFeedAgeStage> = listOf(
        // LAYERS
        PoultryFeedAgeStage(
            id = "layer_pre_starter",
            targetFlockType = PoultryFlockType.LAYERS,
            stageName = "Chick Pre-Starter Crumbs",
            ageStartDays = 1,
            ageEndDays = 10,
            ageDisplay = "Day 1 - 10",
            crudeProteinPercent = 20.5,
            metabolizableEnergyKcalKg = 2950.0,
            calciumPercent = 1.05,
            availablePhosphorusPercent = 0.48,
            lysinePercent = 1.25,
            methioninePercent = 0.50,
            dailyFeedGramsPerBird = 15.0,
            cumulativeFeedKgPerBird = 0.15,
            dailyWaterMlPerBird = 35.0,
            targetBodyWeightGrams = 85.0,
            broodingTemperatureTargetCelsius = "33 - 35°C (Day 1-3), 32°C (Day 4-7), 30°C (Day 8-10)",
            lightingHoursPerDay = "23 hrs (Day 1-3), 20 hrs (Day 4-7), 18 hrs (Day 8-10)",
            nutritionalRoleAndFocus = "Promote rapid yolk sac absorption, early digestive enzyme secretion, gut villi elongation, and immune system establishment.",
            managementAndFeederHygieneTips = listOf(
                "Spread chick feeder paper over 50% of brooding floor; scatter 50 g feed/chick directly on paper for immediate discovery",
                "Ensure nipple/bell drinkers are at eye level; add 5% glucose + multi-vitamins into drinking water for the first 24 hours to counter transport stress",
                "Inspect crop fill: 8 hrs post-placement, 80% of chicks must have soft, full crops; 24 hrs post-placement, 95%+ must have full crops"
            )
        ),
        PoultryFeedAgeStage(
            id = "layer_starter",
            targetFlockType = PoultryFlockType.LAYERS,
            stageName = "Chick Starter Mash",
            ageStartDays = 11,
            ageEndDays = 42,
            ageDisplay = "Day 11 - 42 (Week 2 - 6)",
            crudeProteinPercent = 19.5,
            metabolizableEnergyKcalKg = 2850.0,
            calciumPercent = 1.00,
            availablePhosphorusPercent = 0.45,
            lysinePercent = 1.10,
            methioninePercent = 0.45,
            dailyFeedGramsPerBird = 32.0,
            cumulativeFeedKgPerBird = 1.15,
            dailyWaterMlPerBird = 70.0,
            targetBodyWeightGrams = 450.0,
            broodingTemperatureTargetCelsius = "28°C (Week 2), 26°C (Week 3), 24°C (Week 4), 21°C (Week 5-6)",
            lightingHoursPerDay = "16 hrs step-down to 12 hrs natural light",
            nutritionalRoleAndFocus = "Rapid muscle protein deposition, feather development, and internal organ sizing.",
            managementAndFeederHygieneTips = listOf(
                "Gradually transition to tube feeders; feeder lip level with chick's back height",
                "Never fill feeders more than 1/3 full to prevent feed wastage and billing-out onto litter",
                "Weigh random sample of 50 pullets weekly; ensure flock uniformity is > 85%"
            )
        ),
        PoultryFeedAgeStage(
            id = "layer_grower",
            targetFlockType = PoultryFlockType.LAYERS,
            stageName = "Pullet Grower Mash",
            ageStartDays = 43,
            ageEndDays = 112,
            ageDisplay = "Day 43 - 112 (Week 7 - 16)",
            crudeProteinPercent = 16.0,
            metabolizableEnergyKcalKg = 2750.0,
            calciumPercent = 1.00,
            availablePhosphorusPercent = 0.40,
            lysinePercent = 0.78,
            methioninePercent = 0.36,
            dailyFeedGramsPerBird = 68.0,
            cumulativeFeedKgPerBird = 5.90,
            dailyWaterMlPerBird = 150.0,
            targetBodyWeightGrams = 1350.0,
            broodingTemperatureTargetCelsius = "Ambient / 20 - 22°C (brooding heaters removed)",
            lightingHoursPerDay = "10 - 12 hrs natural daylight (NEVER increase day length during rearing to prevent premature sexual maturity)",
            nutritionalRoleAndFocus = "Develop large skeletal frame (shank length > 105 mm) and gastrointestinal capacity without depositing excessive abdominal fat.",
            managementAndFeederHygieneTips = listOf(
                "Provide insoluble granite grit (2 - 4 mm size) at 1 g/bird/week to develop muscular gizzard walls",
                "Avoid high-calcium diets during this stage (< 1.2% Ca) to prevent permanent kidney nephrosis and visceral gout",
                "Track bodyweight against breed standard (Hy-Line, ISA Brown, Lohmann); adjust feed allowance if flock is underweight"
            )
        ),
        PoultryFeedAgeStage(
            id = "layer_pre_lay",
            targetFlockType = PoultryFlockType.LAYERS,
            stageName = "Pre-Lay Mash",
            ageStartDays = 113,
            ageEndDays = 126,
            ageDisplay = "Day 113 - 126 (Week 17 - 18)",
            crudeProteinPercent = 17.5,
            metabolizableEnergyKcalKg = 2750.0,
            calciumPercent = 2.50,
            availablePhosphorusPercent = 0.45,
            lysinePercent = 0.85,
            methioninePercent = 0.40,
            dailyFeedGramsPerBird = 88.0,
            cumulativeFeedKgPerBird = 7.15,
            dailyWaterMlPerBird = 200.0,
            targetBodyWeightGrams = 1550.0,
            broodingTemperatureTargetCelsius = "Ambient 20 - 24°C",
            lightingHoursPerDay = "Step-up lighting to 14 hours/day (stimulates pituitary FSH/LH release for egg follicle development)",
            nutritionalRoleAndFocus = "Build medullary bone calcium reserves (sponge-like bone inside marrow cavities) required for strong eggshell formation before egg laying starts.",
            managementAndFeederHygieneTips = listOf(
                "Introduce nest boxes at 16 weeks (1 nest box per 4 - 5 birds) lined with clean dry wood shavings to prevent floor eggs",
                "Ensure seamless transition from grower to pre-lay feed over 5 days (75/25, 50/50, 25/75)",
                "Verify water lines are sanitized and nipple drinkers are flowing at 70 - 90 ml/min"
            )
        ),
        PoultryFeedAgeStage(
            id = "layer_phase_1",
            targetFlockType = PoultryFlockType.LAYERS,
            stageName = "Layer Phase 1 Mash / Pellets (Peak Lay)",
            ageStartDays = 127,
            ageEndDays = 315,
            ageDisplay = "Day 127 - 315 (Week 19 - 45: Peak 90%+ Lay)",
            crudeProteinPercent = 18.0,
            metabolizableEnergyKcalKg = 2750.0,
            calciumPercent = 4.00,
            availablePhosphorusPercent = 0.42,
            lysinePercent = 0.90,
            methioninePercent = 0.44,
            dailyFeedGramsPerBird = 115.0,
            cumulativeFeedKgPerBird = 28.5,
            dailyWaterMlPerBird = 250.0,
            targetBodyWeightGrams = 1950.0,
            broodingTemperatureTargetCelsius = "Ambient 18 - 24°C (heat stress above 28°C reduces eggshell quality)",
            lightingHoursPerDay = "16 hours constant light per day (never reduce photoperiod during laying)",
            nutritionalRoleAndFocus = "Support simultaneous daily egg synthesis (60 g egg contains 6 g protein, 2 g calcium) while maintaining body maintenance and plumage.",
            managementAndFeederHygieneTips = listOf(
                "Provide 65% of dietary calcium as coarse limestone chips / oyster shell (2 - 4 mm) in the afternoon so calcium dissolves in gizzard overnight during active shell calcification",
                "Clean drinker troughs twice daily; water intake will be 2.0 to 2.5 times feed intake",
                "Collect eggs at least 3 times daily (9 AM, 12 PM, 4 PM) to prevent breakage, soiling, and egg eating"
            )
        ),

        // BROILERS
        PoultryFeedAgeStage(
            id = "broiler_pre_starter",
            targetFlockType = PoultryFlockType.BROILERS,
            stageName = "Broiler Pre-Starter Crumbs",
            ageStartDays = 1,
            ageEndDays = 10,
            ageDisplay = "Day 1 - 10",
            crudeProteinPercent = 23.0,
            metabolizableEnergyKcalKg = 3025.0,
            calciumPercent = 1.00,
            availablePhosphorusPercent = 0.50,
            lysinePercent = 1.40,
            methioninePercent = 0.52,
            dailyFeedGramsPerBird = 28.0,
            cumulativeFeedKgPerBird = 0.28,
            dailyWaterMlPerBird = 60.0,
            targetBodyWeightGrams = 300.0,
            broodingTemperatureTargetCelsius = "34°C Day 1, step down to 30°C by Day 10",
            lightingHoursPerDay = "24 hrs Day 1, 23 hrs Day 2-7",
            nutritionalRoleAndFocus = "Maximize 7-day body weight (7-day weight correlates 4x to final harvest weight at Day 35).",
            managementAndFeederHygieneTips = listOf(
                "Broiler 7-day weight target is at least 4.5 to 5.0 times Day 1 chick weight (e.g. 42 g chick -> 200 g at Day 7)",
                "Supply fresh crumble feed continuously; keep litter dry and friable (20 - 25% moisture)"
            )
        ),
        PoultryFeedAgeStage(
            id = "broiler_starter",
            targetFlockType = PoultryFlockType.BROILERS,
            stageName = "Broiler Starter Pellets",
            ageStartDays = 11,
            ageEndDays = 21,
            ageDisplay = "Day 11 - 21 (Week 2 - 3)",
            crudeProteinPercent = 21.5,
            metabolizableEnergyKcalKg = 3150.0,
            calciumPercent = 0.90,
            availablePhosphorusPercent = 0.45,
            lysinePercent = 1.25,
            methioninePercent = 0.48,
            dailyFeedGramsPerBird = 78.0,
            cumulativeFeedKgPerBird = 1.10,
            dailyWaterMlPerBird = 160.0,
            targetBodyWeightGrams = 950.0,
            broodingTemperatureTargetCelsius = "27°C at Day 14, 24°C at Day 21",
            lightingHoursPerDay = "20 hrs light, 4 hrs dark period (darkness develops cardiovascular strength and reduces ascites/heart attacks)",
            nutritionalRoleAndFocus = "Fast skeletal frame development and pectoral breast muscle accretion with optimal Feed Conversion Ratio (FCR < 1.45).",
            managementAndFeederHygieneTips = listOf(
                "Gradually shift to 2.5 mm mini-pellets; pelleted feed improves feed conversion by 8% over mash",
                "Ensure adequate drinker nipple pressure; water restriction directly halts broiler feed intake"
            )
        ),
        PoultryFeedAgeStage(
            id = "broiler_finisher",
            targetFlockType = PoultryFlockType.BROILERS,
            stageName = "Broiler Finisher Pellets",
            ageStartDays = 22,
            ageEndDays = 42,
            ageDisplay = "Day 22 - 42 (Week 4 - 6)",
            crudeProteinPercent = 19.5,
            metabolizableEnergyKcalKg = 3200.0,
            calciumPercent = 0.85,
            availablePhosphorusPercent = 0.40,
            lysinePercent = 1.05,
            methioninePercent = 0.42,
            dailyFeedGramsPerBird = 145.0,
            cumulativeFeedKgPerBird = 4.10,
            dailyWaterMlPerBird = 320.0,
            targetBodyWeightGrams = 2400.0,
            broodingTemperatureTargetCelsius = "20 - 21°C (cool ventilation essential to prevent heat prostration)",
            lightingHoursPerDay = "20 hrs light, 4 hrs dark",
            nutritionalRoleAndFocus = "Maximum breast meat yield and rapid daily live weight gain (> 75 g/day gain).",
            managementAndFeederHygieneTips = listOf(
                "Withdraw coccidiostats or medicated feeds 5 - 7 days prior to slaughter (statutory withdrawal compliance)",
                "Harvest broilers early in the morning when ambient temperature is cool; withdraw feed 8 - 10 hours before slaughter to empty intestines"
            )
        ),

        // IMPROVED KIENYEJI
        PoultryFeedAgeStage(
            id = "kienyeji_starter",
            targetFlockType = PoultryFlockType.INDIGENOUS_IMPROVED,
            stageName = "Improved Kienyeji Chick Starter",
            ageStartDays = 1,
            ageEndDays = 28,
            ageDisplay = "Week 1 - 4 (Day 1 - 28)",
            crudeProteinPercent = 20.0,
            metabolizableEnergyKcalKg = 2850.0,
            calciumPercent = 1.00,
            availablePhosphorusPercent = 0.45,
            lysinePercent = 1.15,
            methioninePercent = 0.45,
            dailyFeedGramsPerBird = 25.0,
            cumulativeFeedKgPerBird = 0.70,
            dailyWaterMlPerBird = 55.0,
            targetBodyWeightGrams = 320.0,
            broodingTemperatureTargetCelsius = "33°C Week 1, down to 24°C Week 4",
            lightingHoursPerDay = "Natural daylight + 2 hrs evening light",
            nutritionalRoleAndFocus = "Robust early growth, immune vigor, and adaptation to dual-purpose hardiness.",
            managementAndFeederHygieneTips = listOf(
                "Vaccinate strictly against Gumboro and Newcastle (improved Kienyeji are highly susceptible)",
                "Keep chicks in warm brooder ring with charcoal jiko or infrared bulbs for the first 3 weeks"
            )
        ),
        PoultryFeedAgeStage(
            id = "kienyeji_grower",
            targetFlockType = PoultryFlockType.INDIGENOUS_IMPROVED,
            stageName = "Improved Kienyeji Grower / Free-Range Supplement",
            ageStartDays = 29,
            ageEndDays = 126,
            ageDisplay = "Week 5 - 18 (Day 29 - 126)",
            crudeProteinPercent = 16.5,
            metabolizableEnergyKcalKg = 2700.0,
            calciumPercent = 1.00,
            availablePhosphorusPercent = 0.40,
            lysinePercent = 0.80,
            methioninePercent = 0.35,
            dailyFeedGramsPerBird = 65.0,
            cumulativeFeedKgPerBird = 7.10,
            dailyWaterMlPerBird = 140.0,
            targetBodyWeightGrams = 1450.0,
            broodingTemperatureTargetCelsius = "Ambient 20 - 24°C",
            lightingHoursPerDay = "Natural outdoor daylight",
            nutritionalRoleAndFocus = "Bone structure, scavenging stamina, and lean meat development.",
            managementAndFeederHygieneTips = listOf(
                "Can be allowed into fenced green pastures/paddocks from Week 6 onwards",
                "Supplement commercial grower mash with clean greens (Kales, Lucerne, Desmodium) and black soldier fly larvae",
                "Deworm regularly every 6 - 8 weeks to control internal worms from pasture grazing"
            )
        )
    )

    /**
     * Compute comprehensive Daily Flock Plan: Feed, Water, Bags, Upcoming Vaccines.
     */
    fun calculateDailyFlockPlan(
        flockType: PoultryFlockType,
        flockSizeBirds: Int,
        ageDays: Int
    ): PoultryDailyPlanSummary {
        val safeFlockSize = if (flockSizeBirds <= 0) 100 else flockSizeBirds
        val safeAgeDays = if (ageDays <= 0) 1 else ageDays

        // Find current feeding stage
        val stagesForType = feedingPrograms.filter { it.targetFlockType == flockType }
        val currentStage = stagesForType.find { safeAgeDays in it.ageStartDays..it.ageEndDays }
            ?: stagesForType.last()

        val dailyFeedPerBirdKg = currentStage.dailyFeedGramsPerBird / 1000.0
        val totalDailyFeedKg = dailyFeedPerBirdKg * safeFlockSize
        val bags50kg = totalDailyFeedKg / 50.0
        val totalWaterLiters = (currentStage.dailyWaterMlPerBird * safeFlockSize) / 1000.0
        val cumulativeFeedTotalKg = currentStage.cumulativeFeedKgPerBird * safeFlockSize

        // Filter vaccines for this flock type
        val flockVaccines = vaccinationSchedule.filter { it.targetFlockType.contains(flockType) }
        val upcomingVaccines = flockVaccines.filter { it.ageDays in safeAgeDays..(safeAgeDays + 7) }
        val nextScheduled = flockVaccines.filter { it.ageDays >= safeAgeDays }.minByOrNull { it.ageDays }

        return PoultryDailyPlanSummary(
            flockType = flockType,
            flockSizeBirds = safeFlockSize,
            ageDays = safeAgeDays,
            currentFeedStage = currentStage,
            dailyFeedTotalKg = totalDailyFeedKg,
            dailyFeed50kgBags = bags50kg,
            cumulativeFeedConsumedKg = cumulativeFeedTotalKg,
            dailyWaterRequiredLiters = totalWaterLiters,
            currentBroodingTemperature = currentStage.broodingTemperatureTargetCelsius,
            currentLightingProgram = currentStage.lightingHoursPerDay,
            targetBodyWeightGrams = currentStage.targetBodyWeightGrams,
            upcomingVaccinesNext7Days = upcomingVaccines,
            nextScheduledVaccine = nextScheduled
        )
    }
}
