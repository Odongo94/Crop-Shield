package com.example.model.quarantine

/**
 * Data models for Regulated Quarantine Pests under International Plant Protection
 * Convention (IPPC), EPPO, USDA-APHIS, and Kenya Plant Health Inspectorate Service (KEPHIS).
 */

enum class QuarantineRegulatoryStatus(
    val badgeLabel: String,
    val description: String
) {
    KEPHIS_EXPORT_ZERO_TOLERANCE(
        "KEPHIS & EU Zero-Tolerance",
        "Regulated quarantine organism with strict zero-tolerance upon export to European Union and international markets. A single live interception results in automatic consignment rejection and potential trade alert."
    ),
    EPPO_A1_ABSENT(
        "EPPO A1 (Absent from Region)",
        "Quarantine pests absent from the EPPO region whose introduction represents high phytosanitary and economic disaster risk."
    ),
    EPPO_A2_REGULATED(
        "EPPO A2 (Locally Present, Regulated)",
        "Quarantine pests present in parts of the EPPO region but under official regulatory containment and strict phytosanitary movement control."
    ),
    USDA_APHIS_SELECT(
        "USDA-APHIS Select Agent / Regulated",
        "Regulated plant pest under USDA 7 CFR 319 / 330 with mandatory import permits, post-entry quarantine, or prescribed treatments (hot water / cold treatment / irradiation)."
    ),
    GLOBAL_INVASIVE_IPPC(
        "Global High-Priority Biosecurity Threat",
        "Notified priority pest under IPPC ISPM Standards with rapid global dispersion capacity across international trade corridors."
    )
}

enum class PestOrganismGroup(val displayName: String) {
    INSECT_DIPTERA("Diptera (Fruit Flies)"),
    INSECT_LEPIDOPTERA("Lepidoptera (Moths & Borers)"),
    INSECT_COLEOPTERA("Coleoptera (Weevils & Beetles)"),
    BACTERIUM("Phytopathogenic Bacteria"),
    NEMATODE("Plant-Parasitic Nematodes"),
    VIRUS_VIROID("Viruses & Viroids"),
    FUNGUS_OOMYCETE("Fungal & Oomycete Pathogens")
}

data class QuarantineStageDetail(
    val stageName: String, // e.g. "Egg", "Larva (L1 - L3)", "Pupa", "Adult"
    val durationDays: String,
    val description: String,
    val thermalRequirements: String,
    val physicalAppearance: String
)

data class QuarantinePest(
    val id: String,
    val commonName: String,
    val scientificName: String,
    val taxonomicGroup: PestOrganismGroup,
    val regulatoryStatus: QuarantineRegulatoryStatus,
    val quarantineRegionsAndJurisdictions: List<String>, // e.g. ["EU Annex II", "USA USDA-APHIS", "Kenya KEPHIS", "Japan MAFF", "EPPO"]
    val reasonForQuarantineListing: String,
    val biologyAndIdentification: String,
    val targetCropsAndHosts: List<String>,
    val natureOfAttack: String,
    val diagnosticDamageSymptoms: List<String>,
    val lifeCycleStages: List<QuarantineStageDetail>,
    val totalLifeCycleDuration: String,
    val internationalSpreadPathway: String,
    val preHarvestBiosecurityControls: List<String>, // e.g. Mass trapping, Male Annihilation Technique (MAT), certified virus-free seed
    val postHarvestPhytosanitaryTreatments: List<String>, // e.g. Cold treatment (1.1°C to 2.2°C for 16-22 days), Vapor Heat Treatment (46.5°C), Methyl Bromide
    val kephisAndIppcProtocolSummary: String
)
