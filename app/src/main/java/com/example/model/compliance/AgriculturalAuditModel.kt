package com.example.model.compliance

/**
 * Data models for Agricultural Audits and Daily Field Compliance Standards
 * covering GlobalG.A.P. IFA v6, KS 1758 Kenya Horticulture Code of Practice,
 * Rainforest Alliance, Fairtrade, HACCP / ISO 22000, and PCPB Statutory Biosecurity.
 */

enum class AuditStandard(
    val code: String,
    val title: String,
    val geographicScope: String,
    val issuingBody: String,
    val primaryFocus: String
) {
    GLOBAL_GAP_IFA(
        code = "GLOBALGAP-IFA-V6",
        title = "GlobalG.A.P. IFA v6 (Smart / GFS)",
        geographicScope = "Global Export (EU, UK, Middle East, USA)",
        issuingBody = "FoodPLUS GmbH / GLOBALG.A.P. Secretariat",
        primaryFocus = "Food safety, worker health & safety, environmental conservation, and traceability"
    ),
    KS_1758_KENYA(
        code = "KS-1758-2016",
        title = "KS 1758 Kenya Horticulture Code of Practice",
        geographicScope = "Kenya National & Fresh Produce Exports",
        issuingBody = "Kenya Bureau of Standards (KEBS) & AFA-Horticultural Crops Directorate",
        primaryFocus = "Part 1 (Flowers & Ornamentals) & Part 2 (Fruits & Vegetables): Food safety, worker welfare, environmental stewardship"
    ),
    RAINFOREST_ALLIANCE(
        code = "RA-SAS-2020",
        title = "Rainforest Alliance Sustainable Agriculture Standard",
        geographicScope = "Global (Coffee, Tea, Avocado, Flowers, Cocoa)",
        issuingBody = "Rainforest Alliance / Sustainable Agriculture Network (SAN)",
        primaryFocus = "Biodiversity conservation, climate-smart farming, prohibition of highly hazardous pesticides (HHPs)"
    ),
    FAIRTRADE_INTERNATIONAL(
        code = "FAIRTRADE-HL-SPO",
        title = "Fairtrade International Production Standard",
        geographicScope = "Global (Flowers, Tea, Coffee, Herbs, Fresh Fruits)",
        issuingBody = "Fairtrade International (FLO-CERT)",
        primaryFocus = "Fair trading terms, minimum floor pricing, worker freedom of association, Fairtrade Premium stewardship"
    ),
    HACCP_ISO_22000(
        code = "HACCP-ISO-22000",
        title = "HACCP & ISO 22000 Food Safety Management",
        geographicScope = "Global Packhouses & Processing Facilities",
        issuingBody = "Codex Alimentarius & International Organization for Standardization",
        primaryFocus = "Identification and monitoring of Critical Control Points (CCPs), biological/chemical/physical hazard mitigation"
    ),
    PCPB_STATUTORY_KENYA(
        code = "PCPB-CAP-346",
        title = "PCPB Statutory Agrochemical Compliance & OSHA 2007",
        geographicScope = "Kenya National Statutory (All Commercial Farms)",
        issuingBody = "Pest Control Products Board (PCPB) & Directorate of Occupational Safety & Health (DOSH)",
        primaryFocus = "Safe storage of Class II/III chemicals, locked pesticide stores, triple-rinsing disposal, licensed operators"
    )
}

enum class AuditAreaCategory(val displayName: String, val iconName: String) {
    FOOD_SAFETY_MRL("Food Safety & MRL Compliance", "Medication"),
    WORKER_WELFARE_HEALTH_SAFETY("Worker Health, Safety & Welfare", "Safety"),
    ENVIRONMENT_BIODIVERSITY("Environmental Stewardship & Water", "Eco"),
    TRACEABILITY_RECORD_KEEPING("Traceability, Batching & Records", "Assignment"),
    AGROCHEMICAL_MANAGEMENT("Pesticide Storage & Empty Container Disposal", "Science"),
    HYGIENE_PACKHOUSE("Sanitation, Post-Harvest & Packhouse CCPs", "CleanHands")
}

enum class ComplianceSeverity(val label: String, val impact: String) {
    CRITICAL_MUST("Critical / Major Must", "100% compliance mandatory. Any failure leads to immediate audit failure and certification suspension."),
    MINOR_MUST("Minor Must", "Minimum 95% overall compliance required. Corrective Action Plan (CAPA) must be closed within 28 days."),
    RECOMMENDATION("Recommendation", "Best agricultural management practice; scored for progressive improvement.")
}

data class ComplianceProtocolItem(
    val id: String,
    val standard: AuditStandard,
    val category: AuditAreaCategory,
    val clauseNumber: String,
    val severity: ComplianceSeverity,
    val requirementTitle: String,
    val requirementDescription: String,
    val dailyFieldProtocol: String,
    val requiredRecordsAndDocuments: List<String>,
    val commonAuditNonCompliances: List<String>,
    val correctiveActionGuidance: String,
    val kenyanAndGlobalLegalBasis: String
)

data class FarmAuditSelfAssessmentScore(
    val totalChecked: Int,
    val compliantCount: Int,
    val minorNonCompliantCount: Int,
    val criticalFailureCount: Int,
    val compliancePercentage: Double,
    val passStatus: Boolean,
    val auditorSummary: String
)
