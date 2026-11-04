package com.example.data.compliance

import com.example.model.compliance.AuditAreaCategory
import com.example.model.compliance.AuditStandard
import com.example.model.compliance.ComplianceProtocolItem
import com.example.model.compliance.ComplianceSeverity
import com.example.model.compliance.FarmAuditSelfAssessmentScore

/**
 * Authoritative Repository of Agricultural Audit Standards, Compliance Protocols,
 * and Daily Field Operating Procedures for Kenyan and Global Commercial Operations.
 */
object AgriculturalAuditCatalog {

    val auditProtocols: List<ComplianceProtocolItem> = listOf(
        // ==========================================
        // 1. FOOD SAFETY & MRL COMPLIANCE
        // ==========================================
        ComplianceProtocolItem(
            id = "audit_mrl_phi_01",
            standard = AuditStandard.GLOBAL_GAP_IFA,
            category = AuditAreaCategory.FOOD_SAFETY_MRL,
            clauseNumber = "CB 7.6.1 / FV 5.1.1",
            severity = ComplianceSeverity.CRITICAL_MUST,
            requirementTitle = "Pre-Harvest Interval (PHI) & Maximum Residue Limit (MRL) Adherence",
            requirementDescription = "All plant protection product applications must respect the manufacturer's statutory Pre-Harvest Interval (PHI) and destination market MRLs. Harvesting before the designated PHI expires is strictly prohibited.",
            dailyFieldProtocol = "Scouts and harvest supervisors must consult the Daily Field Spray Register before signing off harvest release. Block spray-warning flags must remain erected at the block perimeter with the date of application, chemical sprayed, and exact release date/hour.",
            requiredRecordsAndDocuments = listOf(
                "Daily Field Spray Application Log (Block #, chemical, trade name, active ingredient, dose, operator name, PHI in days, earliest harvest date)",
                "Pre-Harvest Clearance Certificate signed by the Technical Agronomist",
                "Accredited Laboratory MRL Multi-Residue Analysis Test Reports (ISO 17025 accredited)",
                "Destination Market MRL Matrix (EU Pesticide Database / UK HSE MRL Register)"
            ),
            commonAuditNonCompliances = listOf(
                "Harvesting 1 to 2 days before designated PHI has officially elapsed",
                "Absence of spray warning flags at field block entry points",
                "MRL lab test reports older than 12 months or missing multi-residue screening"
            ),
            correctiveActionGuidance = "Immediately quarantine the harvested batch in a designated non-conforming holding area. Dispatch emergency samples for expedited GC-MS/LC-MS residue testing. If MRL exceeds destination limit, divert produce to local non-food processing or destroy with formal incineration certificate.",
            kenyanAndGlobalLegalBasis = "Kenya Crops Act 2013; PCPB Cap 346; Regulation (EC) No 396/2005 on maximum residue levels of pesticides"
        ),
        ComplianceProtocolItem(
            id = "audit_water_micro_02",
            standard = AuditStandard.KS_1758_KENYA,
            category = AuditAreaCategory.FOOD_SAFETY_MRL,
            clauseNumber = "KS 1758 Part 2: Cl 6.3.2",
            severity = ComplianceSeverity.CRITICAL_MUST,
            requirementTitle = "Agricultural Irrigation & Post-Harvest Water Microbiological Safety",
            requirementDescription = "Water used for crop irrigation (especially overhead edible contact) and post-harvest washing must undergo regular microbial testing. E. coli must be zero (undetected in 100 ml) for post-harvest wash water and < 100 CFU/100 ml for pre-harvest irrigation.",
            dailyFieldProtocol = "Daily visual inspection of water intake sources, river pumps, and borehole storage tanks to verify physical protection against livestock runoff, sewage, and chemical contamination. Maintain automated chlorine/ozone dosing logs for packhouse wash water (free chlorine 2 - 5 ppm).",
            requiredRecordsAndDocuments = listOf(
                "Annual Water Risk Assessment document covering microbial, chemical, and heavy metal hazards",
                "Quarterly ISO 17025 Water Test Certificates (Total Coliforms, E. coli, Salmonella, Heavy Metals)",
                "Daily Packhouse Free Available Chlorine & pH Monitoring Logbook"
            ),
            commonAuditNonCompliances = listOf(
                "Using untreated surface/river water for final produce rinsing or overhead spraying of ready-to-eat greens within 2 weeks of harvest",
                "Absence of backflow prevention valves at fertigation injection points"
            ),
            correctiveActionGuidance = "Halt produce washing immediately. Dose water tanks with calcium hypochlorite or food-grade peracetic acid. Re-test water within 48 hours. Install UV disinfection or sub-micron sand-media filtration at irrigation intakes.",
            kenyanAndGlobalLegalBasis = "EMCA 1999 Water Quality Regulations 2006; KS 1758:2016 Part 2; WHO Guidelines for Safe Use of Wastewater in Agriculture"
        ),

        // ==========================================
        // 2. AGROCHEMICAL MANAGEMENT & STORAGE
        // ==========================================
        ComplianceProtocolItem(
            id = "audit_chem_store_03",
            standard = AuditStandard.PCPB_STATUTORY_KENYA,
            category = AuditAreaCategory.AGROCHEMICAL_MANAGEMENT,
            clauseNumber = "PCPB Cap 346 / OSHA 2007 Sec 83",
            severity = ComplianceSeverity.CRITICAL_MUST,
            requirementTitle = "Agrochemical Storage Facility & Chemical Inventory Reconciliation",
            requirementDescription = "Pesticides must be stored in a dedicated, secure, well-ventilated, fire-resistant, locked room accessible only to trained, certified storekeepers. Liquids must be stored on lower shelves beneath powders, with impermeable bunding able to contain 110% of the largest container capacity.",
            dailyFieldProtocol = "Storekeeper must verify lock integrity daily. Check emergency eyewash/shower station water pressure daily. Record all chemical receipts and issues in the Agrochemical Stock Register with batch numbers and expiration dates. Spillage kit (sand/sawdust, shovel, chemical-resistant bags) must be ready.",
            requiredRecordsAndDocuments = listOf(
                "PCPB Product Registration Certificates for every chemical stocked on site",
                "Safety Data Sheets (SDS / MSDS) for 100% of chemicals, posted in English and Kiswahili",
                "Agrochemical Stock Balance Register with daily stock bin cards",
                "Emergency Action Plan & Fire Department Inspection Certificate",
                "Certified Storekeeper Competence Training Certificate"
            ),
            commonAuditNonCompliances = listOf(
                "Unregistered, unlabelled, or decanted chemicals in unapproved bottles",
                "Liquids stored above powder formulations causing cross-contamination risk if dripping",
                "Expired chemicals stored alongside active stock without quarantine labels"
            ),
            correctiveActionGuidance = "Immediately segregate and label any unapproved or expired chemical in a locked 'Obsolete Stock' cage. Clean any spillage with dry absorbent sand. Contact licensed waste handler for hazardous waste disposal.",
            kenyanAndGlobalLegalBasis = "Pest Control Products Act (Cap 346, Laws of Kenya); Occupational Safety and Health Act (OSHA 2007)"
        ),
        ComplianceProtocolItem(
            id = "audit_triple_rinse_04",
            standard = AuditStandard.GLOBAL_GAP_IFA,
            category = AuditAreaCategory.AGROCHEMICAL_MANAGEMENT,
            clauseNumber = "CB 7.9.1 / CB 7.9.2",
            severity = ComplianceSeverity.CRITICAL_MUST,
            requirementTitle = "Triple Rinsing & Puncturing of Empty Pesticide Containers",
            requirementDescription = "Empty pesticide containers must never be re-used for water, food, or general storage. Containers must be triple-rinsed immediately upon emptying, with rinse water poured back into the spray tank. Containers must then be punctured to render them unusable and stored in a locked container cage.",
            dailyFieldProtocol = "Spray operators must perform the 3-step pressure rinse: (1) Fill 1/4 container with water, (2) Shake vigorously for 30 seconds, (3) Drain into spray tank for 30 seconds. Repeat 3 times. Puncture bottom using an iron spike. Storekeeper signs off container receipt.",
            requiredRecordsAndDocuments = listOf(
                "Triple Rinse Standard Operating Procedure (SOP) posted at the mixing station",
                "Empty Container Inventory Log (Date, Container Type, Size, Operator Signature)",
                "NEMA Licensed Hazardous Waste Collector Consignment Receipts / Destruction Certificates"
            ),
            commonAuditNonCompliances = listOf(
                "Pesticide containers discarded in field trenches or burnt in open pits (strictly illegal)",
                "Unpunctured containers left around farm homesteads or worker houses"
            ),
            correctiveActionGuidance = "Retrieve all empty containers from fields immediately. Conduct mandatory re-training of spray operators. Retain NEMA waste consignment notes for 3 years for auditor verification.",
            kenyanAndGlobalLegalBasis = "NEMA Waste Management Regulations 2006; PCPB Agrochemical Disposal Guidelines; FAO Code of Conduct on Pesticide Management"
        ),

        // ==========================================
        // 3. WORKER HEALTH, SAFETY & WELFARE
        // ==========================================
        ComplianceProtocolItem(
            id = "audit_ppe_medical_05",
            standard = AuditStandard.GLOBAL_GAP_IFA,
            category = AuditAreaCategory.WORKER_WELFARE_HEALTH_SAFETY,
            clauseNumber = "AF 4.1.1 / AF 4.3.1",
            severity = ComplianceSeverity.CRITICAL_MUST,
            requirementTitle = "Personal Protective Equipment (PPE) & Annual Cholinesterase Medicals",
            requirementDescription = "All chemical handlers, spray operators, and storekeepers must be provided with complete, certified PPE (chemical coveralls, nitrile gloves, nitrile boots, face shield/goggles, A2P3 organic vapor respirators) at no cost. Handlers of organophosphates and carbamates must undergo annual blood Cholinesterase medical monitoring.",
            dailyFieldProtocol = "Daily safety briefing before spraying. Inspect respirator cartridge expiration dates and nitrile glove puncture integrity. After spraying, PPE must be laundered at the farm's dedicated PPE laundry (never taken home by workers to protect families).",
            requiredRecordsAndDocuments = listOf(
                "PPE Issue Register signed by every spray operator and storekeeper",
                "Annual Occupational Medical Examination Certificates & Blood Cholinesterase baseline/post-season reports from DOSH-registered medical practitioner",
                "Spray Operator Training Records on chemical safety, emergency decontamination, and respirator fit testing"
            ),
            commonAuditNonCompliances = listOf(
                "Torn gloves or saturated charcoal respirator filters used beyond 30-day operating limit",
                "Workers washing contaminated PPE in household domestic basins",
                "Spray operators with Cholinesterase depression > 30% still assigned to chemical spraying"
            ),
            correctiveActionGuidance = "Immediately replace damaged PPE. If worker cholinesterase levels drop by > 30% from baseline, reassign them to non-chemical duties for at least 30 days until blood enzyme recovery is medically certified.",
            kenyanAndGlobalLegalBasis = "Kenya Occupational Safety and Health Act (OSHA 2007 Sec 89); Employment Act 2007; ILO Convention 184 (Safety in Agriculture)"
        ),
        ComplianceProtocolItem(
            id = "audit_welfare_sanitation_06",
            standard = AuditStandard.FAIRTRADE_INTERNATIONAL,
            category = AuditAreaCategory.WORKER_WELFARE_HEALTH_SAFETY,
            clauseNumber = "FLO-CERT 3.1 / KS 1758 Cl 7.2",
            severity = ComplianceSeverity.CRITICAL_MUST,
            requirementTitle = "Field Sanitation, Potable Drinking Water & First Aid Readiness",
            requirementDescription = "Farm workers must have access to clean, ventilated, gender-segregated flush or VIP latrines with handwashing stations (soap, clean running water, disposable towels/air dryers) within 500 meters or 7 minutes walk from working plots. Clean, tested potable drinking water must be freely available.",
            dailyFieldProtocol = "Daily hygiene supervisor checklist: inspect toilet cleanliness, replenish hand soap and toilet paper, verify water dispenser cleanliness and refilling with potable water. Verify that designated first aid boxes are fully stocked with non-expired dressings, antiseptics, and eye irrigations.",
            requiredRecordsAndDocuments = listOf(
                "Sanitation Cleaning Log signed twice daily by hygiene attendants",
                "Bacteriological Potable Drinking Water Analysis Reports (Zero E. coli in 100 ml)",
                "First Aid Box Monthly Inspection Checklists signed by certified first aiders",
                "List of Certified Farm First Aiders (at least 1 per 50 workers)"
            ),
            commonAuditNonCompliances = listOf(
                "Handwashing stations without soap or running water in active harvesting blocks",
                "Expired antiseptics or empty first aid kits in tractors and field stores",
                "Workers drinking untreated irrigation water due to lack of dedicated potable stations"
            ),
            correctiveActionGuidance = "Immediately supply mobile clean water bowsers and portable handwashing stands. Restock all first aid kits within 24 hours.",
            kenyanAndGlobalLegalBasis = "Public Health Act (Cap 242, Laws of Kenya); Fairtrade Standard 3.1; OSHA 2007 (First Aid Rules)"
        ),

        // ==========================================
        // 4. TRACEABILITY & RECORD KEEPING
        // ==========================================
        ComplianceProtocolItem(
            id = "audit_traceability_07",
            standard = AuditStandard.GLOBAL_GAP_IFA,
            category = AuditAreaCategory.TRACEABILITY_RECORD_KEEPING,
            clauseNumber = "AF 1.1.1 / CB 1.1.1",
            severity = ComplianceSeverity.CRITICAL_MUST,
            requirementTitle = "Complete Forward and Backward Traceability & Annual Recall Mock Drill",
            requirementDescription = "A documented traceability system must guarantee that any harvested carton or export crate can be traced backward to the exact field block, planting date, and spray applications within 4 hours. An annual Mock Traceability Recall test must be conducted and timed.",
            dailyFieldProtocol = "Every harvest crate must receive a waterproof barcode or field slip indicating: Farm Name, Block ID, Crop Variety, Harvest Date, Harvest Team ID, and Packhouse Delivery Time. Never mix unlabelled crates.",
            requiredRecordsAndDocuments = listOf(
                "Traceability SOP and Batch/Lot Numbering Key Protocol",
                "Annual Mock Recall Exercise Report (Target: 100% product accounted for within 4 hours, mass balance reconciliation 98.5% - 101%)",
                "Harvest Crate Dispatch and Receiving Ledger",
                "Master Farm Field Map showing distinct block boundaries and acreage"
            ),
            commonAuditNonCompliances = listOf(
                "Missing lot number labels on field crates entering packhouse cooling bays",
                "Mass balance reconciliation discrepancies > 3% between field harvest weight and packhouse dispatch",
                "Failure to conduct an annual simulated product recall drill"
            ),
            correctiveActionGuidance = "Conduct an immediate mock recall exercise with the audit team. Re-calibrate field scales and implement dual-check signature protocols for crate weigh-ins.",
            kenyanAndGlobalLegalBasis = "General Food Law Regulation (EC) 178/2002 Article 18; KS 1758:2016 Traceability Protocols"
        ),

        // ==========================================
        // 5. ENVIRONMENTAL CONSERVATION & BIODIVERSITY
        // ==========================================
        ComplianceProtocolItem(
            id = "audit_riparian_buffer_08",
            standard = AuditStandard.RAINFOREST_ALLIANCE,
            category = AuditAreaCategory.ENVIRONMENT_BIODIVERSITY,
            clauseNumber = "RA 4.1 / EMCA 1999 Sec 42",
            severity = ComplianceSeverity.CRITICAL_MUST,
            requirementTitle = "Riparian Buffer Zones & Prohibition of Encroachment / Chemical Spraying",
            requirementDescription = "Commercial farming activities must maintain a minimum vegetated buffer zone of 10 to 30 meters along all natural rivers, streams, wetlands, and water bodies. Direct chemical spraying, fertilizer application, and mechanical tillage within the riparian corridor are strictly prohibited.",
            dailyFieldProtocol = "Inspect riparian boundary markers. Verify that sprayers shut off booms when approaching the 30-meter buffer boundary. Inspect indigenous vegetative cover (e.g. Napier grass strips, bamboo, acacia) planted to prevent runoff siltation.",
            requiredRecordsAndDocuments = listOf(
                "Farm Environmental & Biodiversity Management Plan",
                "Riparian Corridor Conservation Map with GPS Coordinates",
                "Soil and Water Conservation Soil Erosion Prevention Audit",
                "Record of Indigenous Trees and Vegetation Planted within Buffer"
            ),
            commonAuditNonCompliances = listOf(
                "Cultivating horticultural crops directly up to the river bank",
                "Washing knapsack sprayers directly in natural rivers or streams",
                "Destruction of natural riverine forest vegetation"
            ),
            correctiveActionGuidance = "Cease cultivation within the 30-meter riparian reserve immediately. Re-establish native grass strips and indigenous trees to stabilize the riverbank and trap potential agricultural runoff.",
            kenyanAndGlobalLegalBasis = "Kenya Environmental Management and Co-ordination Act (EMCA 1999); Water Act 2016; Rainforest Alliance Standard 4.1"
        ),

        // ==========================================
        // 6. PACKHOUSE & HACCP CRITICAL CONTROL POINTS
        // ==========================================
        ComplianceProtocolItem(
            id = "audit_haccp_packhouse_09",
            standard = AuditStandard.HACCP_ISO_22000,
            category = AuditAreaCategory.HYGIENE_PACKHOUSE,
            clauseNumber = "HACCP Principle 2 / ISO 22000:2018 Cl 8.5",
            severity = ComplianceSeverity.CRITICAL_MUST,
            requirementTitle = "Monitoring Critical Control Points (CCPs) in Post-Harvest Handling",
            requirementDescription = "All established CCPs—specifically Foreign Body Screening (glass, hard plastic, metal), Post-Harvest Sanitizer Concentration (chlorine 2 - 5 ppm or PAA 50 - 80 ppm), and Cold-Chain Storage Temperature (2 - 4°C for greens, 8 - 12°C for avocados)—must be monitored continuously with calibrated instruments.",
            dailyFieldProtocol = "Packhouse QC supervisor must inspect shatterproof lighting coverings daily. Verify metal detector operation with test wands (Fe 1.5mm, Non-Fe 2.0mm, SS 2.5mm) every 2 hours. Log cold-room temperatures every hour.",
            requiredRecordsAndDocuments = listOf(
                "HACCP Plan with Hazard Analysis, CCP Determination Tree, and Critical Limits",
                "Hourly Cold Room Temperature Monitoring Log Sheets",
                "Glass & Brittle Plastic Daily Register and Breakage Procedure",
                "Staff Health & Hygiene Daily Questionnaire (exclusion of staff with gastrointestinal illness or infected cuts)"
            ),
            commonAuditNonCompliances = listOf(
                "Broken glass light bulbs without shatterproof protective polycarbonate sleeves",
                "Workers wearing jewelry, rings, or false fingernails in the packing area",
                "Unmonitored cold-room temperature spikes above critical threshold (> 6°C)"
            ),
            correctiveActionGuidance = "Enforce strict jewelry and personal hygiene dress code. Immediately stop packing line if glass breaks, isolate produce within 5 meters, vacuum, inspect, and complete formal Glass Breakage Clearance Certificate.",
            kenyanAndGlobalLegalBasis = "Codex Alimentarius General Principles of Food Hygiene CXC 1-1969; Food, Drugs and Chemical Substances Act (Cap 254)"
        )
    )

    /**
     * Calculate Farm Audit Self-Assessment Score from user compliance ticks.
     */
    fun evaluateAuditCompliance(
        checkedProtocolIds: Set<String>,
        allProtocols: List<ComplianceProtocolItem> = auditProtocols
    ): FarmAuditSelfAssessmentScore {
        val total = allProtocols.size
        val compliantCount = checkedProtocolIds.size
        val nonCompliantItems = allProtocols.filter { !checkedProtocolIds.contains(it.id) }
        val criticalFailures = nonCompliantItems.filter { it.severity == ComplianceSeverity.CRITICAL_MUST }.size
        val minorNonCompliant = nonCompliantItems.filter { it.severity == ComplianceSeverity.MINOR_MUST }.size
        val percentage = if (total > 0) (compliantCount.toDouble() / total.toDouble()) * 100.0 else 0.0

        val passed = criticalFailures == 0 && percentage >= 95.0

        val summary = when {
            criticalFailures > 0 -> "AUDIT FAILED: $criticalFailures Critical Major Must non-conformance(s) identified. GlobalG.A.P. and KS 1758 regulations prohibit certification until all Critical Musts are 100% resolved via formal Corrective Action."
            percentage < 95.0 -> "PROVISIONAL ACTION REQUIRED: Compliance rate is ${"%.1f".format(percentage)}% (minimum 95% Minor Musts required). A Corrective Action Plan (CAPA) must be executed within 28 days."
            else -> "CONGRATULATIONS: Fully Compliant (${"%.1f".format(percentage)}%). Farm meets GlobalG.A.P. IFA v6, KS 1758, and PCPB statutory standards for international export certification."
        }

        return FarmAuditSelfAssessmentScore(
            totalChecked = total,
            compliantCount = compliantCount,
            minorNonCompliantCount = minorNonCompliant,
            criticalFailureCount = criticalFailures,
            compliancePercentage = percentage,
            passStatus = passed,
            auditorSummary = summary
        )
    }
}
