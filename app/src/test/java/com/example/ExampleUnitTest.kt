package com.example

import com.example.data.map.FieldGeometryUtil
import com.example.data.scouting.ScoutingProtocolCatalog
import com.example.model.DeveloperInfo
import com.example.model.GeoPoint
import com.example.model.ScoutingInspectionRow
import com.example.model.ScoutingSheetReport
import com.example.data.science.AgriculturalSciencesCatalog
import com.example.data.science.WeedSoilScienceCatalog
import com.example.model.science.SoilPhCategory
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun developerInfo_matchesRequestedCredentials() {
        assertEquals("Odongo Vincent", DeveloperInfo.NAME)
        assertEquals("Crop Scientist", DeveloperInfo.TITLE)
        assertEquals("0741886046", DeveloperInfo.PHONE)
        assertEquals("odongovincento@gmail.com", DeveloperInfo.EMAIL)
    }

    @Test
    fun autoDetectFarmBoundary_generatesFourCornersAroundGpsPoint() {
        val userLocation = GeoPoint(-0.3031, 36.0800) // Nakuru
        val boundary = FieldGeometryUtil.autoDetectFarmBoundary(userLocation, 1.0)

        assertEquals(4, boundary.size)

        // Verify centroid is approximately the center coordinate
        val centroid = FieldGeometryUtil.calculateCentroid(boundary)
        assertNotNull(centroid)
        assertEquals(userLocation.latitude, centroid!!.latitude, 0.0001)
        assertEquals(userLocation.longitude, centroid.longitude, 0.0001)

        // Verify area calculated is close to 1.0 hectare (10,000 m²)
        val metrics = FieldGeometryUtil.computeMetrics(boundary)
        assertEquals(1.0, metrics.hectares, 0.05)
        assertEquals(2.47, metrics.acres, 0.15)
        assertTrue(metrics.perimeterMeters > 350.0 && metrics.perimeterMeters < 450.0)
    }

    @Test
    fun scoutingSheetReport_exportsValidCsv() {
        val sampleRow = ScoutingInspectionRow(
            stationNumber = 1,
            plotLocation = "Block 1A",
            cropName = "Maize",
            threatName = "Fall Armyworm (Spodoptera frugiperda)",
            plantsSampled = 20,
            plantsInfested = 4,
            incidencePercent = 20.0,
            severityRating = 4,
            stageObserved = "L2-L3 Larvae",
            actionStatus = "ACTION REQUIRED",
            notes = "Window feeding observed on whorls; spray Emamectin benzoate"
        )

        val report = ScoutingSheetReport(
            farmName = "Rongai Shamba",
            blockName = "Block 1",
            cropName = "Maize",
            scoutName = "John K.",
            dateInspected = "Oct 24, 2026",
            gpsCoordinates = "-0.3031, 36.0800",
            rows = listOf(sampleRow)
        )

        val csv = report.toCsv()
        assertTrue(csv.contains("Station #,Plot Location,Crop,Target Threat,Plants Sampled,Plants Infested,Incidence (%),Severity (1-5),Stage Observed,Action Status,Scout Notes"))
        assertTrue(csv.contains("Fall Armyworm"))
        assertTrue(csv.contains("Emamectin benzoate"))
        assertTrue(csv.contains("Rongai Shamba"))
        assertTrue(csv.contains(DeveloperInfo.NAME))
        assertTrue(csv.contains(DeveloperInfo.PHONE))
    }

    @Test
    fun scoutingProtocols_containKenyanIPMStandards() {
        val protocols = ScoutingProtocolCatalog.protocols
        assertTrue(protocols.isNotEmpty())

        val crops = protocols.map { it.cropName }.distinct()
        assertTrue(crops.any { it.contains("Maize", ignoreCase = true) })
        assertTrue(crops.any { it.contains("Tomato", ignoreCase = true) })
        assertTrue(crops.any { it.contains("Potato", ignoreCase = true) })

        val sampleRows = ScoutingProtocolCatalog.getSampleInspectionRows()
        assertEquals(4, sampleRows.size)
    }

    @Test
    fun weedSoilDiagnosis_strigaDetectsDegradedFertilityAndPushPull() {
        val report = WeedSoilScienceCatalog.diagnoseSoilFromWeeds(listOf("striga"))
        assertEquals(1, report.selectedWeeds.size)
        assertTrue(report.diagnosedNutrientDeficiencies.any { it.contains("Nitrogen", ignoreCase = true) })
        assertTrue(report.diagnosedNutrientDeficiencies.any { it.contains("Phosphorus", ignoreCase = true) })
        assertTrue(report.recommendedCommercialFertilizers.any { it.contains("DAP", ignoreCase = true) || it.contains("CAN", ignoreCase = true) || it.contains("Mavuno", ignoreCase = true) })
        assertTrue(report.urgentSoilAmendments.any { it.contains("Desmodium", ignoreCase = true) || it.contains("Organic", ignoreCase = true) || it.contains("Manure", ignoreCase = true) })
        assertTrue(report.agronomicRehabilitationPlan.isNotEmpty())
        assertTrue(report.technicalAdvisorySignoff.contains(DeveloperInfo.NAME))
    }

    @Test
    fun weedSoilDiagnosis_brackenFernDetectsAcidicSoilAndLiming() {
        val report = WeedSoilScienceCatalog.diagnoseSoilFromWeeds(listOf("bracken_fern"))
        assertEquals(1, report.selectedWeeds.size)
        assertEquals(SoilPhCategory.VERY_STRONGLY_ACIDIC, report.dominantPhCategory)
        assertTrue(report.diagnosedNutrientExcesses.any { it.contains("Aluminum", ignoreCase = true) })
        assertTrue(report.urgentSoilAmendments.any { it.contains("Lime", ignoreCase = true) })
    }

    @Test
    fun agriculturalSciencesCatalog_containsAllFiveCorePillars() {
        val soilPillar = AgriculturalSciencesCatalog.soilSciencePillar
        assertNotNull(soilPillar)
        assertTrue(soilPillar.kenyanSoilTaxonomy.isNotEmpty())
        assertTrue(soilPillar.kenyanSoilTaxonomy.any { it.name.contains("Nitisol", ignoreCase = true) })
        assertTrue(soilPillar.chemicalParameters.isNotEmpty())
        assertTrue(soilPillar.limeRequirementGuidelines.isNotEmpty())

        val pathologyPillar = AgriculturalSciencesCatalog.cropPathologyPillar
        assertNotNull(pathologyPillar)
        assertTrue(pathologyPillar.pathogenClasses.isNotEmpty())
        assertTrue(pathologyPillar.fracFungicideGuide.isNotEmpty())
        assertTrue(pathologyPillar.diseaseSeverityIndexFormula.contains("DSI", ignoreCase = true))

        val entomologyPillar = AgriculturalSciencesCatalog.entomologyPillar
        assertNotNull(entomologyPillar)
        assertTrue(entomologyPillar.insectOrders.isNotEmpty())
        assertTrue(entomologyPillar.beneficialPredators.isNotEmpty())
        assertTrue(entomologyPillar.iracInsecticideGuide.isNotEmpty())

        val agronomyPillar = AgriculturalSciencesCatalog.agronomyPillar
        assertNotNull(agronomyPillar)
        assertTrue(agronomyPillar.physiologyPrinciples.isNotEmpty())
        assertTrue(agronomyPillar.plantingGeometryStandards.isNotEmpty())

        val conservationPillar = AgriculturalSciencesCatalog.conservationPillar
        assertNotNull(conservationPillar)
        assertTrue(conservationPillar.engineeringStructures.isNotEmpty())
        assertTrue(conservationPillar.biologicalMeasures.isNotEmpty())
    }

    @Test
    fun cecRebalanceCalculator_computesCorrectCecAndRatios() {
        // Sample soil lab test: Ca=10.0, Mg=2.0, K=0.5, Na=0.2, Acidity=1.3 => Total CEC = 14.0 meq/100g
        val result = com.example.data.soil.CecRebalanceCalculator.calculateFromLabCations(
            calciumMeq = 10.0,
            magnesiumMeq = 2.0,
            potassiumMeq = 0.5,
            sodiumMeq = 0.2,
            exchangeableAcidityMeq = 1.3,
            soilPh = 6.0
        )

        assertEquals(14.0, result.totalCec, 0.01)
        assertEquals(com.example.model.soil.CecRatingCategory.MODERATE, result.rating)
        // Base Saturation = (12.7 / 14.0) * 100 = 90.71%
        assertEquals(90.71, result.baseSaturationPercent, 0.1)
        // Calcium Saturation = (10.0 / 14.0) * 100 = 71.43%
        assertEquals(71.43, result.caSaturationPercent, 0.1)
        // Ca:Mg ratio = 10.0 / 2.0 = 5.0
        assertEquals(5.0, result.caToMgRatio, 0.01)
        assertTrue(result.stepByStepDerivations.isNotEmpty())
    }

    @Test
    fun organicManureCalculator_calculatesCorrectTonnageAndNutrients() {
        val maizeSpec = com.example.data.soil.OrganicManureCatalog.cropManureSpecs.first { it.cropId == "crop_maize" }
        val result = com.example.data.soil.OrganicManureCatalog.calculateTonnage(
            cropSpec = maizeSpec,
            manureType = com.example.model.soil.ManureType.COW_FARMYARD,
            areaHectares = 1.0
        )

        // Verify positive tonnage and bags
        assertTrue(result.totalTonsRequiredHectare > 0.0)
        assertTrue(result.total50kgBagsHectare > 0)
        assertTrue(result.availableNSuppliedKgHa > 0.0)
        assertTrue(result.calculationSteps.isNotEmpty())
    }

    @Test
    fun irrigationCalculator_computesCorrectEtcAndLeachingFraction() {
        val tomato = com.example.data.soil.IrrigationWaterCatalog.crops.first { it.id == "irrig_tomato" }
        val result = com.example.data.soil.IrrigationWaterCatalog.calculateIrrigationRequirement(
            crop = tomato,
            growthStage = com.example.model.soil.CropGrowthStage.MID_SEASON,
            referenceET0MmDay = 5.0,
            effectiveRainfallMmDay = 0.0,
            irrigationMethod = com.example.model.soil.IrrigationMethod.DRIP_LINE,
            soilECe = 2.0,
            waterECw = 0.8
        )

        // ETc = 5.0 * 1.15 = 5.75 mm/day
        assertEquals(5.75, result.cropEvapotranspirationETcMmDay, 0.01)
        // Gross depth > ETc due to leaching fraction and 90% efficiency
        assertTrue(result.grossIrrigationDepthMmDay > result.cropEvapotranspirationETcMmDay)
        assertTrue(result.grossVolumeM3PerHaPerDay > 0.0)
        assertTrue(result.litersPerPlantPerDay > 0.0)
        assertTrue(result.stepByStepMathFormulas.isNotEmpty())
    }

    @Test
    fun nozzleCalibrationCalculator_computesCorrectFlowRateAndTable() {
        val yellowNozzle = com.example.model.spray.IsoNozzleColor.YELLOW_02 // 0.80 L/min at 3.0 bar
        // At 3.0 bar, single flow rate must equal 0.80 L/min exactly
        val flowAt3Bar = com.example.data.spray.NozzleCalibrationCalculator.calculateFlowRate(yellowNozzle, 3.0)
        assertEquals(0.80, flowAt3Bar, 0.001)

        // At 4.0 bar, q = 0.80 * sqrt(4.0 / 3.0) = 0.92376 L/min
        val flowAt4Bar = com.example.data.spray.NozzleCalibrationCalculator.calculateFlowRate(yellowNozzle, 4.0)
        assertEquals(0.9238, flowAt4Bar, 0.002)

        val fullResult = com.example.data.spray.NozzleCalibrationCalculator.calculateCompleteNozzleDischarge(
            nozzle = yellowNozzle,
            selectedPressureBars = 3.0,
            customDurationMinutes = 15.0,
            customDurationHours = 1.0,
            numberOfNozzlesOnBoom = 1,
            walkingSpeedKmH = 4.0,
            nozzleSpacingMeters = 0.50
        )

        // In 15 minutes: 0.80 * 15 = 12.0 Liters
        assertEquals(12.0, fullResult.volumeInMinutesLiters, 0.01)
        // In 1 hour: 0.80 * 60 = 48.0 Liters
        assertEquals(48.0, fullResult.volumeInHoursLiters, 0.01)
        // Table must contain 7 standard pressure rows
        assertEquals(7, fullResult.dischargeTable.size)
        // Application rate L/ha = (0.80 * 600) / (4.0 * 0.5) = 240.0 L/ha
        assertEquals(240.0, fullResult.applicationRateLitersPerHectare, 0.1)

        // Wear test: 880 ml in 1 min against nominal 800 ml = +10.0%
        val wearCheck = com.example.data.spray.NozzleCalibrationCalculator.checkNozzleWear(yellowNozzle, 3.0, 920.0)
        assertTrue(wearCheck.isWorn)
        assertTrue(wearCheck.differencePercent > 10.0)
    }

    @Test
    fun agriculturalAuditCatalog_evaluatesComplianceAndScore() {
        val protocols = com.example.data.compliance.AgriculturalAuditCatalog.auditProtocols
        assertTrue("Must contain compliance protocols", protocols.size >= 8)

        val hasGlobalGap = protocols.any { it.standard == com.example.model.compliance.AuditStandard.GLOBAL_GAP_IFA }
        val hasKs1758 = protocols.any { it.standard == com.example.model.compliance.AuditStandard.KS_1758_KENYA }
        val hasPcpb = protocols.any { it.standard == com.example.model.compliance.AuditStandard.PCPB_STATUTORY_KENYA }
        assertTrue(hasGlobalGap)
        assertTrue(hasKs1758)
        assertTrue(hasPcpb)

        // Test scoring with 0 checked (failed)
        val scoreZero = com.example.data.compliance.AgriculturalAuditCatalog.evaluateAuditCompliance(emptySet())
        assertFalse(scoreZero.passStatus)
        assertTrue(scoreZero.criticalFailureCount > 0)

        // Test scoring with all checked (passed)
        val allIds = protocols.map { it.id }.toSet()
        val scoreAll = com.example.data.compliance.AgriculturalAuditCatalog.evaluateAuditCompliance(allIds)
        assertTrue(scoreAll.passStatus)
        assertEquals(100.0, scoreAll.compliancePercentage, 0.01)
        assertEquals(0, scoreAll.criticalFailureCount)
    }

    @Test
    fun quarantinePestCatalog_containsKeyInternationalPestsAndKephisProtocols() {
        val pests = com.example.data.quarantine.QuarantinePestCatalog.quarantinePests
        assertTrue("Must contain quarantine pests", pests.size >= 5)

        val fcm = com.example.data.quarantine.QuarantinePestCatalog.getPestById("qp_fcm")
        assertNotNull("FCM must exist", fcm)
        assertTrue(fcm!!.targetCropsAndHosts.any { it.contains("Roses", ignoreCase = true) })
        assertTrue(fcm.postHarvestPhytosanitaryTreatments.any { it.contains("Cold Treatment", ignoreCase = true) })
        assertTrue(fcm.lifeCycleStages.isNotEmpty())

        val bactrocera = com.example.data.quarantine.QuarantinePestCatalog.getPestById("qp_bactrocera")
        assertNotNull("Bactrocera must exist", bactrocera)
        assertTrue(bactrocera!!.targetCropsAndHosts.any { it.contains("Mango", ignoreCase = true) })
        assertTrue(bactrocera.postHarvestPhytosanitaryTreatments.any { it.contains("Vapor Heat", ignoreCase = true) })

        val pcn = com.example.data.quarantine.QuarantinePestCatalog.getPestById("qp_pcn")
        assertNotNull("PCN must exist", pcn)
        assertTrue(pcn!!.targetCropsAndHosts.any { it.contains("Potato", ignoreCase = true) })
    }

    @Test
    fun poultryManagementCatalog_calculatesCorrectFeedWaterAndUpcomingVaccines() {
        val vaccines = com.example.data.poultry.PoultryManagementCatalog.vaccinationSchedule
        assertTrue("Must contain vaccines", vaccines.size >= 10)
        assertTrue(vaccines.any { it.targetDisease.contains("Marek", ignoreCase = true) })
        assertTrue(vaccines.any { it.targetDisease.contains("Gumboro", ignoreCase = true) })
        assertTrue(vaccines.any { it.targetDisease.contains("Newcastle", ignoreCase = true) })

        // Test calculation for 500 layers at Day 14
        val plan = com.example.data.poultry.PoultryManagementCatalog.calculateDailyFlockPlan(
            flockType = com.example.model.poultry.PoultryFlockType.LAYERS,
            flockSizeBirds = 500,
            ageDays = 14
        )

        assertEquals(500, plan.flockSizeBirds)
        assertEquals(14, plan.ageDays)
        assertTrue(plan.dailyFeedTotalKg > 0.0)
        assertTrue(plan.dailyWaterRequiredLiters > 0.0)
        assertTrue(plan.currentBroodingTemperature.isNotEmpty())
        // At day 14, Gumboro 1st dose is due
        assertTrue(plan.upcomingVaccinesNext7Days.any { it.targetDisease.contains("Gumboro", ignoreCase = true) })
    }

    @Test
    fun aiAgronomistService_quickPromptsAreComprehensive() {
        val prompts = com.example.data.ai.AiAgronomistService.quickPrompts
        assertTrue("Must contain quick prompts", prompts.size >= 8)

        val hasPest = prompts.any { it.category == "Pest Control" }
        val hasPoultry = prompts.any { it.category == "Poultry Health" }
        val hasSoil = prompts.any { it.category == "Soil Fertility" }
        val hasIrrig = prompts.any { it.category == "Irrigation Science" }
        assertTrue(hasPest)
        assertTrue(hasPoultry)
        assertTrue(hasSoil)
        assertTrue(hasIrrig)
    }

    @Test
    fun aiAgronomistService_offlineResponsesDeliverAccuratePrescriptions() {
        // 1. FAW Query
        val fawResponse = com.example.data.ai.AiAgronomistService.generateOfflineAgronomicResponse("How do I control Fall Armyworm in my maize?")
        assertTrue(fawResponse.contains("Spodoptera frugiperda"))
        assertTrue(fawResponse.contains("Emamectin Benzoate") || fawResponse.contains("Coragen"))
        assertTrue(fawResponse.contains("knapsack"))

        // 2. Tomato Blight Query
        val blightResponse = com.example.data.ai.AiAgronomistService.generateOfflineAgronomicResponse("How do I treat Late Blight on tomatoes?")
        assertTrue(blightResponse.contains("Phytophthora infestans"))
        assertTrue(blightResponse.contains("Mancozeb") || blightResponse.contains("Ridomil"))

        // 3. Poultry Vaccine Query
        val poultryResponse = com.example.data.ai.AiAgronomistService.generateOfflineAgronomicResponse("What is the vaccination schedule for chicks?")
        assertTrue(poultryResponse.contains("Marek"))
        assertTrue(poultryResponse.contains("Gumboro"))
        assertTrue(poultryResponse.contains("skimmed milk"))

        // 4. Soil pH Query
        val soilResponse = com.example.data.ai.AiAgronomistService.generateOfflineAgronomicResponse("How to manage soil pH 4.8?")
        assertTrue(soilResponse.contains("Lime") || soilResponse.contains("CaCO"))
        assertTrue(soilResponse.contains("Albrecht") || soilResponse.contains("Aluminum"))
    }

    @Test
    fun bioscienceSkillsCatalog_containsAllCoreDomainsAndScaffolds() {
        val modules = com.example.data.science.BioscienceSkillsCatalog.allModules
        assertTrue("Must contain all 5 core bioscience modules", modules.size >= 5)

        val domains = modules.map { it.domain }.toSet()
        assertTrue(domains.contains(com.example.model.science.BioscienceDomain.WEB_APP_DEV))
        assertTrue(domains.contains(com.example.model.science.BioscienceDomain.BIOINFORMATICS))
        assertTrue(domains.contains(com.example.model.science.BioscienceDomain.MOLECULAR_DYNAMICS))
        assertTrue(domains.contains(com.example.model.science.BioscienceDomain.CHEMISTRY_TOOLS))
        assertTrue(domains.contains(com.example.model.science.BioscienceDomain.BIOLOGICAL_DATABASES))

        val scaffolds = com.example.data.science.BioscienceSkillsCatalog.webAppScaffolds
        assertTrue("Must contain web application scaffolds", scaffolds.size >= 4)
        assertTrue(scaffolds.any { it.name.contains("Streamlit") })
        assertTrue(scaffolds.any { it.name.contains("FastAPI") })
        assertTrue(scaffolds.any { it.name.contains("RDKit") })
        assertTrue(scaffolds.any { it.name.contains("PubChem") })
    }

    @Test
    fun bioscienceSkillsCatalog_searchFiltersAccurately() {
        val rdkitHits = com.example.data.science.BioscienceSkillsCatalog.searchModules("RDKit")
        assertTrue(rdkitHits.isNotEmpty())
        assertTrue(rdkitHits.any { it.title.contains("RDKit") || it.toolsAndLibraries.contains("RDKit") })

        val blastHits = com.example.data.science.BioscienceSkillsCatalog.searchModules("BLAST")
        assertTrue(blastHits.isNotEmpty())

        val dynamicsModules = com.example.data.science.BioscienceSkillsCatalog.getModulesByDomain(
            com.example.model.science.BioscienceDomain.MOLECULAR_DYNAMICS
        )
        assertEquals(1, dynamicsModules.size)
        assertTrue(dynamicsModules.first().toolsAndLibraries.contains("OpenMM"))
    }

    @Test
    fun bioscienceGeminiService_quickPromptsAndOfflineEngine() {
        val prompts = com.example.data.ai.BioscienceGeminiService.quickPrompts
        assertTrue("Must have bioscience quick prompts", prompts.size >= 5)
        assertTrue(prompts.any { it.shortLabel.contains("PDB Viewer") })
        assertTrue(prompts.any { it.shortLabel.contains("BLAST") })
        assertTrue(prompts.any { it.shortLabel.contains("AutoDock") })

        // 1. Streamlit 3D query
        val streamlitResponse = com.example.data.ai.BioscienceGeminiService.generateOfflineBioscienceResponse("How do I build a Streamlit 3D molecule viewer web app?")
        assertTrue(streamlitResponse.contains("Streamlit"))
        assertTrue(streamlitResponse.contains("py3Dmol"))

        // 2. BLAST alignment query
        val blastResponse = com.example.data.ai.BioscienceGeminiService.generateOfflineBioscienceResponse("Explain Needleman-Wunsch and BLAST alignment")
        assertTrue(blastResponse.contains("Needleman-Wunsch"))
        assertTrue(blastResponse.contains("Biopython"))

        // 3. Chemoinformatics query
        val chemResponse = com.example.data.ai.BioscienceGeminiService.generateOfflineBioscienceResponse("How do I calculate Lipinski Rule of 5 and LogP with RDKit?")
        assertTrue(chemResponse.contains("Lipinski"))
        assertTrue(chemResponse.contains("RDKit"))
        assertTrue(chemResponse.contains("TPSA"))
    }
}

