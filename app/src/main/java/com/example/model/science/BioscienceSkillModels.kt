package com.example.model.science

import androidx.compose.ui.graphics.Color

/**
 * Core scientific domains for bioscience skills and scientific web applications.
 */
enum class BioscienceDomain(
    val title: String,
    val subtitle: String,
    val badgeColor: Color
) {
    WEB_APP_DEV(
        title = "Scientific Web Apps",
        subtitle = "Streamlit, Dash, Mol* 3D, FastAPI & BioAssembly",
        badgeColor = Color(0xFF00897B)
    ),
    BIOINFORMATICS(
        title = "Bioinformatics",
        subtitle = "Sequence alignment, NGS pipelines & CRISPR genomics",
        badgeColor = Color(0xFF1E88E5)
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
 * Direct link to scientific search engines and biological databases.
 */
data class ExternalScientificLink(
    val title: String,
    val provider: String, // "Google Search", "Google Scholar", "PubMed", "NCBI", "PubChem", "PDB", "UniProt"
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
 * Search engine target for scientific investigations.
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
        helperText = "Search scientific publications, tools, and developer documentation"
    ),
    GOOGLE_SCHOLAR(
        displayName = "Google Scholar",
        baseUrl = "https://scholar.google.com/scholar",
        queryParam = "q",
        helperText = "Search peer-reviewed agricultural & molecular biology papers"
    ),
    PUBMED(
        displayName = "PubMed Central",
        baseUrl = "https://pubmed.ncbi.nlm.nih.gov/",
        queryParam = "term",
        helperText = "Search biomedical, crop genomics, and toxicology literature"
    ),
    NCBI_GENBANK(
        displayName = "NCBI Entrez",
        baseUrl = "https://www.ncbi.nlm.nih.gov/search/all/",
        queryParam = "term",
        helperText = "Search gene sequences, BLAST queries, and nucleotide accessions"
    ),
    PUBCHEM(
        displayName = "PubChem Chemistry",
        baseUrl = "https://pubchem.ncbi.nlm.nih.gov/#query=",
        queryParam = "", // Direct query path
        helperText = "Search agrochemical structures, pesticide SMILES, and bioactivity"
    ),
    PDB_STRUCTURES(
        displayName = "RCSB PDB 3D",
        baseUrl = "https://www.rcsb.org/search?request=",
        queryParam = "",
        helperText = "Search 3D crystal structures of crop receptors and pathogen enzymes"
    )
}
