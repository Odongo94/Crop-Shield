package com.example.data.ai

import android.util.Log
import com.example.model.ai.AiChatMessage
import com.example.model.ai.AiQuickPrompt
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.Content
import com.google.firebase.ai.type.content
import com.google.firebase.ai.type.generationConfig
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

/**
 * AI Chatbot and Computational Biology Advisor for Bioscience Skills.
 * Powered by Firebase AI Logic (`gemini-3.8-flash`) with deep offline scientific fallback knowledge.
 */
object BioscienceGeminiService {

    private const val TAG = "BioscienceGeminiService"
    private const val GEMINI_MODEL_NAME = "gemini-3.8-flash"
    private const val AI_TIMEOUT_MS = 60_000L

    private const val BIOSCIENCE_SYSTEM_PROMPT =
        "You are 'CropShield Bioscience & Agricultural Diagnostics AI', an elite Senior Plant Pathologist, Chemoinformatician, and Scientific Web Application Architect. " +
            "You guide researchers, agronomists, software engineers, and university students in plant protection, diagnostic portal integration, and building scientific web applications. " +
            "Your knowledge base integrates: " +
            "1. 11 Authoritative Global Pest & Disease Identification Portals (AHR Australia, NPPC Bhutan, CABI PlantwisePlus, Bayer Agronomy ID, EPPO Quarantine A1/A2, Penn State Extension, Gardener's Supply, Minnesota MDA, TAAT Africa, Corson Maize, and iNaturalist). " +
            "2. NUFLIP (JICA / MAAIF Northern Uganda) Demonstration Field Factsheets (22 pests, 22 diseases, 8 physiological disorders, IRAC & FRAC MoA rotation, Bronopol plant activator). " +
            "3. Scientific Web Applications (Streamlit, Dash, FastAPI, Next.js for Science, 3Dmol.js/Mol*). " +
            "4. Molecular Dynamics & Structural Biology (AlphaFold, AutoDock Vina, GROMACS). " +
            "5. Chemoinformatics & Biological Databases (RDKit, SMILES, Lipinski Rule of 5, NCBI Entrez, UniProt, PubChem). " +
            "Provide rigorous, mathematically sound, step-by-step guidance and code."

    private val bioscienceModel by lazy {
        Firebase.ai.generativeModel(
            modelName = GEMINI_MODEL_NAME,
            generationConfig = generationConfig {
                temperature = 0.2f
                topP = 0.95f
                maxOutputTokens = 2048
            },
            systemInstruction = content {
                text(BIOSCIENCE_SYSTEM_PROMPT)
            }
        )
    }

    val quickPrompts: List<AiQuickPrompt> = listOf(
        AiQuickPrompt(
            id = "bp_global_portals",
            iconEmoji = "🌍",
            category = "Global Pest Portals",
            shortLabel = "Query 11 Global Pest Websites",
            fullPrompt = "How do I cross-reference crop pests and diseases across CABI PlantwisePlus, EPPO Quarantine A1/A2, iNaturalist, Penn State Extension, and Bayer Agronomy ID, and how can an app deep-link into all 11 portals?"
        ),
        AiQuickPrompt(
            id = "bp_nuflip_disorders",
            iconEmoji = "🍅",
            category = "NUFLIP Field Factsheets",
            shortLabel = "NUFLIP Vegetable Disorders",
            fullPrompt = "What are the key physiological disorders in tomatoes and watermelons (Blossom End Rot, Potassium/Magnesium deficiency, Sunburn, Flooding damage) according to the NUFLIP / JICA Northern Uganda guide, and what are their field remedies?"
        ),
        AiQuickPrompt(
            id = "bp_irac_frac_moa",
            iconEmoji = "🛡️",
            category = "Resistance Management",
            shortLabel = "IRAC & FRAC MoA + Bronopol",
            fullPrompt = "Explain the IRAC insecticide MoA groups (1A, 1B, 4A, 6, 3A) and FRAC fungicide codes (M1, M2, M3, Group 1, 3, 4, 11, 27) and the role of Bronopol as a bacterial blight control and plant activator."
        ),
        AiQuickPrompt(
            id = "bp_streamlit_pdb",
            iconEmoji = "🌐",
            category = "Scientific Web Apps",
            shortLabel = "Build 3D PDB Viewer Web App",
            fullPrompt = "How do I build an interactive 3D protein viewer web application in Python using Streamlit and py3Dmol that fetches structures directly from the RCSB PDB REST API?"
        ),
        AiQuickPrompt(
            id = "bp_rdkit_lipinski",
            iconEmoji = "🧪",
            category = "Chemoinformatics",
            shortLabel = "RDKit Lipinski Rule of 5",
            fullPrompt = "Write a complete Python script using RDKit that parses a SMILES string, calculates Molecular Weight, LogP, TPSA, H-bond donors/acceptors, and tests Lipinski drug-likeness."
        ),
        AiQuickPrompt(
            id = "bp_mol_docking",
            iconEmoji = "⚛️",
            category = "Molecular Dynamics",
            shortLabel = "AutoDock Vina Pesticide Docking",
            fullPrompt = "How do I set up a molecular docking workflow using AutoDock Vina to dock an insecticide molecule against an insect Ryanodine receptor in silico?"
        )
    )

    /**
     * Ask a question to the Bioscience AI Chatbot via Firebase AI Logic (`gemini-3.8-flash`).
     */
    suspend fun askBioscienceAi(
        question: String,
        history: List<AiChatMessage> = emptyList()
    ): AiChatMessage = withContext(Dispatchers.IO) {
        try {
            FirebaseApp.getInstance()
            val geminiResponse = callFirebaseBioscienceAi(question, history)
            if (!geminiResponse.isNullOrBlank()) {
                return@withContext AiChatMessage(
                    text = geminiResponse.trim(),
                    isUser = false,
                    isOnlineGemini = true,
                    topicCategory = detectScientificCategory(question),
                    suggestedFollowUps = generateScientificFollowUps(question),
                    relatedCalculatorScreen = null
                )
            }
        } catch (e: TimeoutCancellationException) {
            safeLogE(TAG, "Bioscience Gemini timed out after ${AI_TIMEOUT_MS}ms", e)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            safeLogE(TAG, "Bioscience Gemini call failed, using offline scientific engine: ${e.message}", e)
        }

        val offlineResponse = generateOfflineBioscienceResponse(question)
        AiChatMessage(
            text = offlineResponse,
            isUser = false,
            isOnlineGemini = false,
            topicCategory = detectScientificCategory(question),
            suggestedFollowUps = generateScientificFollowUps(question),
            relatedCalculatorScreen = null
        )
    }

    private suspend fun callFirebaseBioscienceAi(
        question: String,
        history: List<AiChatMessage>
    ): String? {
        val cleanHistory = mutableListOf<Content>()
        var expectedRole = "user"
        for (msg in history.takeLast(6)) {
            val role = if (msg.isUser) "user" else "model"
            val textBody = msg.text.trim()
            if (textBody.isBlank()) continue
            if (role == expectedRole) {
                cleanHistory += content(role) { text(textBody.take(2000)) }
                expectedRole = if (expectedRole == "user") "model" else "user"
            }
        }
        if (cleanHistory.lastOrNull()?.role == "user") {
            cleanHistory.removeAt(cleanHistory.lastIndex)
        }

        val response = withTimeout(AI_TIMEOUT_MS) {
            if (cleanHistory.isNotEmpty()) {
                bioscienceModel.startChat(history = cleanHistory).sendMessage(question)
            } else {
                bioscienceModel.generateContent(question)
            }
        }
        return response.text
    }

    private fun safeLogE(tag: String, message: String, tr: Throwable? = null) {
        try {
            Log.e(tag, message, tr)
        } catch (_: Throwable) {
        }
    }

    private fun detectScientificCategory(query: String): String {
        val q = query.lowercase()
        return when {
            q.contains("streamlit") || q.contains("web app") || q.contains("fastapi") || q.contains("dash") || q.contains("3dmol") -> "Scientific Web Apps"
            q.contains("blast") || q.contains("fasta") || q.contains("alignment") || q.contains("crispr") || q.contains("dna") || q.contains("rna") || q.contains("ngs") -> "Bioinformatics & Genomics"
            q.contains("dock") || q.contains("dynamics") || q.contains("openmm") || q.contains("gromacs") || q.contains("alphafold") || q.contains("vina") -> "Molecular Dynamics"
            q.contains("rdkit") || q.contains("smiles") || q.contains("lipinski") || q.contains("logp") || q.contains("tpsa") || q.contains("chemoinformatic") -> "Chemoinformatics"
            q.contains("ncbi") || q.contains("uniprot") || q.contains("pubchem") || q.contains("pdb") || q.contains("database") || q.contains("entrez") -> "Biological Databases"
            else -> "Bioscience Engineering"
        }
    }

    private fun generateScientificFollowUps(query: String): List<String> {
        val q = query.lowercase()
        return when {
            q.contains("streamlit") || q.contains("web") -> listOf(
                "How do I deploy this Streamlit scientific app to Docker?",
                "Can I embed Mol* instead of py3Dmol for bigger structures?",
                "How do I add user authentication to a scientific dashboard?"
            )
            q.contains("blast") || q.contains("align") -> listOf(
                "How do I run a local standalone BLAST+ database?",
                "Show me Clustal Omega multiple sequence alignment in Python.",
                "How does the BLOSUM62 matrix calculate gap penalties?"
            )
            q.contains("dock") || q.contains("dynamics") -> listOf(
                "How do I prepare PDBQT files using AutoDockTools or Meeko?",
                "How do I calculate RMSD and hydrogen bonds from GROMACS trajectories?",
                "Can AlphaFold 3 predict ligand-protein complex interactions?"
            )
            q.contains("rdkit") || q.contains("smiles") -> listOf(
                "How do I calculate Morgan Fingerprints and Tanimoto similarity?",
                "How to predict pesticide environmental fate and Koc with QSAR?",
                "How to convert 2D SMILES into 3D conformers with energy minimization?"
            )
            q.contains("database") || q.contains("ncbi") || q.contains("uniprot") -> listOf(
                "How do I batch download 100 UniProt FASTA sequences with Python?",
                "How to extract ligand IC50 values from PubChem bioassays?",
                "What is the rate limit for NCBI Entrez E-utilities?"
            )
            else -> listOf(
                "Show me a working Streamlit scientific dashboard example.",
                "How do I use RDKit to screen pesticide molecules?",
                "Explain the AlphaFold 2 architecture."
            )
        }
    }

    internal fun generateOfflineBioscienceResponse(query: String): String {
        val q = query.lowercase()
        return when {
            q.contains("portal") || q.contains("website") || q.contains("cabi") || q.contains("eppo") ||
                q.contains("inaturalist") || q.contains("penn state") || q.contains("bayer") ||
                q.contains("ahr") || q.contains("bhutan") || q.contains("taat") || q.contains("corson") -> buildString {
                append("### 🌍 11 Authoritative International Pest & Disease Diagnostic Portals\n\n")
                append("CropShield integrates deep search links and identification keys across 11 premier global repositories:\n\n")
                append("1. **AHR Australia**: Field identification guides for Brassicas, Alliums, Cucurbits, and Solanaceae.\n")
                append("2. **NPPC Bhutan**: National Plant Protection Centre diagnostic registry for life histories, biology, and thresholds.\n")
                append("3. **CABI PlantwisePlus**: Pest Management Decision Guides (PMDGs) and global distribution maps.\n")
                append("4. **Bayer CropScience Agronomy ID**: Commercial field keys for invertebrate pests and foliar vectors.\n")
                append("5. **EPPO Global Quarantine A1/A2**: International phytosanitary alert and regulated quarantine registers.\n")
                append("6. **Penn State Extension**: University diagnostic keys for insect, pathogen, and weed identification.\n")
                append("7. **Gardener's Supply Encyclopedia**: Organic controls, cultural sanitation, and companion planting.\n")
                append("8. **Minnesota MDA Index**: Statutory plant pest and noxious weed regulatory directory.\n")
                append("9. **TAAT Africa Technologies**: CGIAR & AfDB validated African IPM technologies and FAW biocontrol.\n")
                append("10. **Corson Maize Pests & Diseases**: Maize and cereal foliar blights, cutworms, and stalk rots.\n")
                append("11. **iNaturalist Global Biodiversity & AI**: 150M+ verified photo observations and species ID.")
            }

            q.contains("nuflip") || q.contains("disorder") || q.contains("blossom end rot") ||
                q.contains("sunburn") || q.contains("sunscald") || q.contains("potassium") ||
                q.contains("flooding") || q.contains("pollination") -> buildString {
                append("### 🍅 NUFLIP Vegetable Factsheets & Physiological Disorders (Acholi Sub-Region / JICA)\n\n")
                append("1. **Blossom End Rot (BER)**: Inadequate calcium translocation under irregular watering. Remedy: Calcium Nitrate foliar spray (5–10 g/L) + steady drip moisture.\n")
                append("2. **Potassium & Magnesium Deficiency**: Marginal scorching (K) vs. interveinal chlorosis (Mg). Remedy: Potassium sulphate or Epsom salts (10–20 g/L).\n")
                append("3. **Sunburn / Sunscald**: Maintain leaf canopy cover or apply Kaolin clay reflective barrier.\n")
                append("4. **Flooding & Hypoxia**: Raised beds (20–30 cm) and perimeter drainage trenches.\n")
                append("5. **Watermelon Poor Pollination**: Avoid spraying insecticides between 6:00 AM and 11:00 AM while honeybees forage.")
            }

            q.contains("irac") || q.contains("frac") || q.contains("bronopol") || q.contains("moa") || q.contains("resistance") -> buildString {
                append("### 🛡️ IRAC & FRAC Mode of Action (MoA) Classification & Bronopol\n\n")
                append("1. **IRAC Insecticides:** 1A/1B (Carbamates/OPs), 3A (Pyrethroids), 4A (Neonicotinoids), 6 (Avermectins like Abamectin/Emamectin), 28 (Diamides like Chlorantraniliprole).\n")
                append("2. **FRAC Fungicides:** M1/M2/M3 (Copper/Sulphur/Mancozeb multi-site), Group 3 (Triazoles), Group 4 (Metalaxyl), Group 11 (Strobilurins), **Bronopol** (plant activator/bactericide inducing SAR).")
            }

            q.contains("streamlit") || q.contains("web app") || q.contains("viewer") -> buildString {
                append("### 🌐 Building Scientific Web Apps with Streamlit & 3Dmol.js\n\n")
                append("Use `streamlit` + `stmol`/`py3Dmol` fetching PDB structures from `https://files.rcsb.org/download/{PDB_ID}.pdb` and containerize with `python:3.11-slim`.")
            }

            q.contains("blast") || q.contains("alignment") || q.contains("needleman") || q.contains("smith") -> buildString {
                append("### 🧬 Bioinformatics Sequence Alignment & BLAST+\n\n")
                append("Use Needleman-Wunsch for global alignment, Smith-Waterman for local conserved domains, and BLAST+ (`E-value < 1e-5`) for rapid k-mer database homology search.")
            }

            q.contains("dock") || q.contains("openmm") || q.contains("dynamics") || q.contains("gromacs") || q.contains("alphafold") -> buildString {
                append("### ⚛️ Molecular Dynamics & In Silico Agrochemical Docking\n\n")
                append("Acquire structure (PDB / AlphaFold), parameterize with AMBER/CHARMM36m, solvate in TIP3P + 0.15M NaCl, minimize, equilibrate NVT/NPT, and dock ligands via AutoDock Vina (`kcal/mol`).")
            }

            q.contains("rdkit") || q.contains("smiles") || q.contains("lipinski") || q.contains("tpsa") || q.contains("logp") -> buildString {
                append("### 🧪 Chemoinformatics & RDKit Lipinski Rule of 5 Profiling\n\n")
                append("Evaluate plant uptake & systemic translocation with Lipinski Rule of 5 via RDKit descriptors (`Descriptors.MolWt`, `MolLogP`, `TPSA` topological polar surface area, `NumHDonors`, `NumHAcceptors`): MW <= 500 g/mol, LogP 1.5–3.5 optimal for xylem mobility, TPSA <= 140 Å², HBD <= 5, HBA <= 10.")
            }

            q.contains("ncbi") || q.contains("uniprot") || q.contains("pubchem") || q.contains("entrez") || q.contains("database") -> buildString {
                append("### 📚 Biological Databases & REST API Integration\n\n")
                append("Query NCBI Entrez (`esearch.fcgi`/`efetch.fcgi`), UniProtKB REST JSON, and PubChem PUG REST for curated biological and agrochemical compound data.")
            }

            else -> buildString {
                append("### 🧬 Bioscience & Scientific Web Application Suite\n\n")
                append("Ask about the 11 Global Pest Portals, NUFLIP physiological disorders, IRAC/FRAC rotation, RDKit QSAR, AutoDock Vina, or Streamlit 3D viewers.")
            }
        }
    }
}
