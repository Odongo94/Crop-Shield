package com.example.model.science

import androidx.compose.ui.graphics.Color

/**
 * Core scientific domains for bioscience skills, scientific web applications,
 * and authoritative global pest & disease identification portals.
 */
enum class BioscienceDomain(
    val title: String,
    val subtitle: String,
    val badgeColor: Color
) {
    GLOBAL_PEST_PORTALS(
        title = "Pest & Disease Portals",
        subtitle = "AHR, CABI, EPPO, PennState, Bayer, TAAT, iNaturalist",
        badgeColor = Color(0xFF1E88E5)
    ),
    WEB_APP_DEV(
        title = "Scientific Web Apps",
        subtitle = "Streamlit, Dash, Mol* 3D, FastAPI & Field Apps",
        badgeColor = Color(0xFF00897B)
    ),
    MOLECULAR_DYNAMICS(
        title = "Molecular Dynamics",
        subtitle = "GROMACS, OpenMM, AlphaFold & pesticide docking",
        badgeColor = Color(0xFF8E24AA)
    ),
    CHEMISTRY_TOOLS(
        title = "Chemoinformatics",
        subtitle = "RDKit, SMILES, Lipinski rules & agrochemical QSAR",
        badgeColor = Color(0xFFE65100)
    ),
    BIOLOGICAL_DATABASES(
        title = "Biological Databases",
        subtitle = "NCBI GenBank, UniProt, PDB, PubChem & Ensembl",
        badgeColor = Color(0xFF43A047)
    )
}

/**
 * Global and regional plant protection organizations, pest identification portals,
 * and diagnostic field guide registries.
 */
data class GlobalPestIdentificationPortal(
    val id: String,
    val name: String,
    val organization: String,
    val url: String,
    val searchUrlTemplate: String?, // Search URL template where query can be appended
    val coverageDescription: String,
    val keySpecialties: List<String>,
    val diagnosticGuideType: String,
    val iconEmoji: String
)

/**
 * Deep search result matching an active pest or disease query against an authoritative global portal.
 */
data class PortalSearchResult(
    val portal: GlobalPestIdentificationPortal,
    val query: String,
    val deepSearchUrl: String,
    val title: String,
    val description: String
)

/**
 * Executable code recipe for a scientific web application or data processing pipeline.
 */
data class CodeRecipe(
    val title: String,
    val language: String, // "python", "javascript", "bash", "r"
    val framework: String, // "Streamlit", "FastAPI", "RDKit", "Biopython", "OpenMM"
    val code: String,
    val explanation: String
)

/**
 * Direct link to scientific search engines, biological databases, or pest portals.
 */
data class ExternalScientificLink(
    val title: String,
    val provider: String,
    val url: String,
    val description: String
)

/**
 * Comprehensive learning and execution module in the bioscience curriculum.
 */
data class BioscienceSkillModule(
    val id: String,
    val title: String,
    val domain: BioscienceDomain,
    val summary: String,
    val keyConcepts: List<String>,
    val toolsAndLibraries: List<String>,
    val webAppArchitecture: String,
    val agriculturalRelevance: String,
    val codeRecipe: CodeRecipe,
    val databaseLinks: List<ExternalScientificLink>,
    val recommendedSearchQueries: List<String>,
    val geminiChatPrompts: List<String>
)

/**
 * Complete blueprint for building a scientific web application from scratch.
 */
data class ScientificWebAppScaffold(
    val id: String,
    val name: String,
    val tag: String,
    val domain: BioscienceDomain,
    val description: String,
    val techStack: List<String>,
    val architectureComponents: List<String>,
    val sampleCode: String,
    val demoInputDescription: String,
    val expectedOutputDescription: String
)

/**
 * Search engine target for scientific investigations and authoritative agricultural databases.
 */
enum class ScientificSearchTarget(
    val displayName: String,
    val baseUrl: String,
    val queryParam: String,
    val helperText: String
) {
    GOOGLE(
        displayName = "Google Web",
        baseUrl = "https://www.google.com/search",
        queryParam = "q",
        helperText = "Search global scientific publications, pest databases, and agricultural literature"
    ),
    GOOGLE_SCHOLAR(
        displayName = "Google Scholar",
        baseUrl = "https://scholar.google.com/scholar",
        queryParam = "q",
        helperText = "Search peer-reviewed agronomy, pathology, and entomology journals"
    ),
    CABI_PLANTWISE(
        displayName = "CABI PlantwisePlus",
        baseUrl = "https://plantwiseplusknowledgebank.org/action/doSearch",
        queryParam = "AllField",
        helperText = "CABI PlantwisePlus diagnostic factsheets & management decision guides"
    ),
    EPPO_QUARANTINE(
        displayName = "EPPO Quarantine",
        baseUrl = "https://gd.eppo.int/search",
        queryParam = "k",
        helperText = "EPPO A1/A2 Global Quarantine and Phytosanitary alert pest registers"
    ),
    INATURALIST(
        displayName = "iNaturalist Global",
        baseUrl = "https://www.inaturalist.org/search",
        queryParam = "q",
        helperText = "Crowdsourced biological species observations and AI identification"
    ),
    PENN_STATE(
        displayName = "Penn State Extension",
        baseUrl = "https://extension.psu.edu/search/",
        queryParam = "q",
        helperText = "Penn State university insect, pathogen, and weed diagnostic library"
    ),
    BAYER_AGRONOMY(
        displayName = "Bayer CropScience ID",
        baseUrl = "https://cropscience.bayer.co.uk/search",
        queryParam = "q",
        helperText = "Bayer agronomy pest & slug diagnostic keys and chemical timings"
    ),
    PUBCHEM(
        displayName = "PubChem Chemistry",
        baseUrl = "https://pubchem.ncbi.nlm.nih.gov/#query=",
        queryParam = "",
        helperText = "Search agrochemical active ingredients, pesticide SMILES, and bioactivity"
    )
}
