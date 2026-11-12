package com.example.data.ai

import android.util.Log
import com.example.BuildConfig
import com.example.model.ai.AiChatMessage
import com.example.model.ai.AiQuickPrompt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

/**
 * AI Chatbot and Computational Biology Advisor for Bioscience Skills.
 * Powered by Google Gemini 2.5 Flash with deep offline scientific fallback knowledge.
 */
object BioscienceGeminiService {

    private const val TAG = "BioscienceGeminiService"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(25, TimeUnit.SECONDS)
        .readTimeout(25, TimeUnit.SECONDS)
        .writeTimeout(25, TimeUnit.SECONDS)
        .build()

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
     * Ask a question to the Bioscience AI Chatbot.
     */
    suspend fun askBioscienceAi(
        question: String,
        history: List<AiChatMessage> = emptyList()
    ): AiChatMessage = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY.trim()
        val hasValidKey = apiKey.isNotBlank() &&
            !apiKey.equals("MY_GEMINI_API_KEY", ignoreCase = true) &&
            !apiKey.contains("placeholder", ignoreCase = true)

        if (hasValidKey) {
            try {
                val geminiResponse = callGeminiBioscienceApi(question, history, apiKey)
                if (geminiResponse != null && geminiResponse.isNotBlank()) {
                    return@withContext AiChatMessage(
                        text = geminiResponse,
                        isUser = false,
                        isOnlineGemini = true,
                        topicCategory = detectScientificCategory(question),
                        suggestedFollowUps = generateScientificFollowUps(question),
                        relatedCalculatorScreen = null
                    )
                }
            } catch (e: Exception) {
                Log.w(TAG, "Gemini Bioscience call failed, using offline scientific engine: ${e.message}")
            }
        }

        // Offline Fallback
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

    private fun callGeminiBioscienceApi(
        question: String,
        history: List<AiChatMessage>,
        apiKey: String
    ): String? {
        val systemPrompt = buildString {
            append("You are 'CropShield Bioscience & Agricultural Diagnostics AI', an elite Senior Plant Pathologist, Chemoinformatician, and Scientific Web Application Architect. ")
            append("You guide researchers, agronomists, software engineers, and university students in plant protection, diagnostic portal integration, and building scientific web applications. ")
            append("Your knowledge base integrates: ")
            append("1. 11 Authoritative Global Pest & Disease Identification Portals: AHR Australia (Brassica/Allium/Solanaceae keys), NPPC Bhutan (entomology/pathology registry), CABI PlantwisePlus Knowledge Bank (Pest Management Decision Guides), Bayer CropScience Agronomy ID (economic thresholds & timings), EPPO Global Quarantine A1/A2 Registers, Penn State Extension Library, Gardener's Supply Encyclopedia (organic & cultural solutions), Minnesota MDA Statutory Index, TAAT Africa Technologies (CGIAR & AfDB validated IPM), Corson Maize Pests & Diseases (cereal pathology), and iNaturalist Global Biodiversity AI ID. ")
            append("2. NUFLIP (JICA / MAAIF Northern Uganda) Demonstration Field Factsheets: 22 insect pests, 22 diseases, and 8 physiological disorders (Blossom End Rot, Potassium/Magnesium deficiency, Sunburn, Flooding, Pollination failure), IRAC MoA insecticide rotation (1A, 1B, 4A, 6, 3A), and FRAC fungicide classifications with Bronopol plant activator. ")
            append("3. Scientific Web Applications: Streamlit, Dash, FastAPI, Next.js for Science, Dockerizing diagnostic tools, Mol* / 3Dmol.js WebGL molecular visualization. ")
            append("4. Molecular Dynamics & Structural Biology: AlphaFold structure prediction, AutoDock Vina molecular docking, binding affinity (kcal/mol), RMSD/RMSF trajectory analysis. ")
            append("5. Chemoinformatics & Chemistry Tools: RDKit, canonical SMILES, InChI, Lipinski Rule of 5, TPSA, QSAR modeling, pesticide soil degradation kinetics. ")
            append("6. Google Web & Google Scholar integration for peer-reviewed citations. ")
            append("Provide rigorous, mathematically sound, step-by-step guidance and code with explanations.")
        }

        val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=$apiKey"
        val requestJson = JSONObject().apply {
            put("system_instruction", JSONObject().apply {
                put("parts", JSONArray().put(JSONObject().put("text", systemPrompt)))
            })

            val contentsArray = JSONArray()
            val recentHistory = history.takeLast(4)
            for (msg in recentHistory) {
                contentsArray.put(JSONObject().apply {
                    put("role", if (msg.isUser) "user" else "model")
                    put("parts", JSONArray().put(JSONObject().put("text", msg.text)))
                })
            }

            contentsArray.put(JSONObject().apply {
                put("role", "user")
                put("parts", JSONArray().put(JSONObject().put("text", question)))
            })

            put("contents", contentsArray)

            put("generationConfig", JSONObject().apply {
                put("temperature", 0.2)
                put("topP", 0.95)
                put("maxOutputTokens", 2048)
            })
        }

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = requestJson.toString().toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        val response = okHttpClient.newCall(request).execute()
        val responseBody = response.body?.string() ?: return null

        if (!response.isSuccessful) {
            Log.e(TAG, "Gemini API HTTP Error ${response.code}: $responseBody")
            return null
        }

        val root = JSONObject(responseBody)
        val candidates = root.optJSONArray("candidates") ?: return null
        if (candidates.length() == 0) return null

        val firstCandidate = candidates.getJSONObject(0)
        val content = firstCandidate.optJSONObject("content") ?: return null
        val parts = content.optJSONArray("parts") ?: return null
        if (parts.length() == 0) return null

        return parts.getJSONObject(0).optString("text")
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
                append("1. **AHR Australia**: Field identification guides for Brassicas, Alliums, Cucurbits, and Solanaceae (`https://ahr.com.au/blog/news/pest-and-disease-identification-guides-available`).\n")
                append("2. **NPPC Bhutan**: National Plant Protection Centre diagnostic registry for life histories, biology, and economic thresholds (`https://pestsofbhutan.nppc.gov.bt/`).\n")
                append("3. **CABI PlantwisePlus**: World-renowned Pest Management Decision Guides (PMDGs) and global distribution maps (`https://plantwiseplusknowledgebank.org/doi/10.5555/pwkb.20167800063`).\n")
                append("4. **Bayer CropScience Agronomy ID**: Commercial field keys for invertebrate pests and foliar vectors (`https://cropscience.bayer.co.uk/agronomy-id/pest-and-slugs`).\n")
                append("5. **EPPO Global Quarantine A1/A2**: International phytosanitary alert and regulated quarantine registers (`https://www.eppo.int/ACTIVITIES/plant_quarantine/A1_list`).\n")
                append("6. **Penn State Extension**: Academic university diagnostic keys for insect, pathogen, and weed identification (`https://extension.psu.edu/insects-pests-and-diseases/pest-disease-and-weed-identification/see-all-pest-disease-and-weed-identification`).\n")
                append("7. **Gardener's Supply Encyclopedia**: Organic controls, cultural sanitation, and companion planting (`https://www.gardeners.com/blogs/pest-disease-encyclopedia`).\n")
                append("8. **Minnesota MDA Index**: State statutory plant pest and noxious weed regulatory directory (`https://www.mda.state.mn.us/plant-pests-diseases-index`).\n")
                append("9. **TAAT Africa Technologies**: CGIAR & AfDB validated African IPM technologies and FAW biocontrol (`https://e-catalogs.taat-africa.org/gov/technologies?categories%5B%5D=Pest%20control`).\n")
                append("10. **Corson Maize Pests & Diseases**: Maize and cereal foliar blights, cutworms, and stalk rots (`https://www.corsonmaize.co.nz/pests-and-diseases`).\n")
                append("11. **iNaturalist Global Biodiversity & AI**: 150M+ verified photo observations and computer vision AI species ID (`https://www.inaturalist.org/`).\n\n")
                append("All 11 portals can be searched simultaneously in the **11 Global Portals tab** in Bioscience Skills or directly when inspecting any threat in the scouting tool.")
            }

            q.contains("nuflip") || q.contains("disorder") || q.contains("blossom end rot") ||
                q.contains("sunburn") || q.contains("sunscald") || q.contains("potassium") ||
                q.contains("flooding") || q.contains("pollination") -> buildString {
                append("### 🍅 NUFLIP Vegetable Factsheets & Physiological Disorders (Acholi Sub-Region / JICA)\n\n")
                append("Key non-pathogenic physiological disorders documented in Northern Uganda demonstration fields:\n\n")
                append("1. **Blossom End Rot (BER)**:\n")
                append("   - **Cause**: Inadequate calcium translocated to rapidly expanding distal fruit tissue under irregular watering or high NH4+ nitrogen.\n")
                append("   - **Symptoms**: Flat, leathery, dark brown/black sunken scar at blossom end of tomato, pepper, and watermelon fruits.\n")
                append("   - **Remedy**: Apply Calcium Nitrate foliar spray (5–10 g/L) weekly. Maintain constant drip irrigation moisture.\n\n")
                append("2. **Potassium & Magnesium Deficiency**:\n")
                append("   - **Potassium**: Yellowing and necrosis starting on outer leaf margins of older leaves; poor fruit coloring ('yellow shoulders'). Remedy: Potassium sulphate (K2SO4) or wood ash topdressing.\n")
                append("   - **Magnesium**: Interveinal chlorosis (bright yellow tissue between dark green veins). Remedy: Epsom salts (Magnesium sulphate 10–20 g/L) foliar spray.\n\n")
                append("3. **Sunburn / Sunscald**:\n")
                append("   - **Cause**: Sudden fruit exposure to direct intense tropical sun (>35°C) following insect defoliation or excessive pruning.\n")
                append("   - **Symptoms**: Shiny, bleached, papery white to tan sunken patch with sharp boundary.\n")
                append("   - **Remedy**: Maintain canopy cover; spray Kaolin clay reflective barrier.\n\n")
                append("4. **Flooding & Hypoxia**:\n")
                append("   - **Cause**: Waterlogged root zone starving root cells of oxygen (anoxic root rot).\n")
                append("   - **Remedy**: Raised beds (20–30 cm), perimeter drainage trenches, avoid planting in low-lying swamp basins during peak rains.\n\n")
                append("5. **Watermelon Poor Pollination**:\n")
                append("   - **Cause**: Lack of bee visits (minimum 8–10 visits per female flower required).\n")
                append("   - **Symptoms**: Misshapen, gourd-like, bottle-necked fruit with shriveled blossom ends.\n")
                append("   - **Remedy**: Never spray insecticides between 6:00 AM and 11:00 AM when honeybees forage; intercrop bee-friendly herbs.")
            }

            q.contains("irac") || q.contains("frac") || q.contains("bronopol") || q.contains("moa") || q.contains("resistance") -> buildString {
                append("### 🛡️ IRAC & FRAC Mode of Action (MoA) Classification & Bronopol\n\n")
                append("Preventing chemical resistance through systematic MoA rotation:\n\n")
                append("1. **IRAC Insecticide MoA Groups**:\n")
                append("   - **Group 1A & 1B (AChE Inhibitors)**: Carbamates (Carbofuran) & Organophosphates (Acephate, Chlorpyrifos, Dimethoate, Malathion).\n")
                append("   - **Group 4A (Nicotinic AChR Agonists)**: Neonicotinoids (Acetamiprid, Imidacloprid, Thiamethoxam) - high systemic translocation.\n")
                append("   - **Group 6 (Glutamate-gated Chloride Channels)**: Avermectins (Abamectin, Emamectin benzoate) - ideal for spider mites and leafminers.\n")
                append("   - **Group 3A (Sodium Channel Modulators)**: Pyrethroids (Lambda-cyhalothrin, Cypermethrin, Deltamethrin) - rapid knockdown.\n\n")
                append("2. **FRAC Fungicide Groups & Bronopol**:\n")
                append("   - **Group M1, M2, M3 (Multi-Site Protectants)**: Copper hydroxide, Sulphur, Mancozeb - low resistance risk; mix with systemics.\n")
                append("   - **Group 1 (Benzimidazoles)**: Carbendazim, Thiophanate-methyl.\n")
                append("   - **Group 3 (DMI Triazoles)**: Difenoconazole, Tebuconazole - curative ergosterol inhibitors.\n")
                append("   - **Group 4 (PA Phenylamides)**: Metalaxyl - specific for Oomycetes (Late Blight, Downy Mildew).\n")
                append("   - **Group 11 (QoI Strobilurins)**: Azoxystrobin - high single-site resistance risk; limit to max 2 applications per season.\n")
                append("   - **Bronopol (Plant Activator & Bactericide)**: Induces Systemic Acquired Resistance (SAR) against Bacterial Wilt, Bacterial Canker, and Black Rot.")
            }

            q.contains("streamlit") || q.contains("web app") || q.contains("viewer") -> buildString {
                append("### 🌐 Building Scientific Web Apps with Streamlit & 3Dmol.js\n\n")
                append("To build a reactive scientific web application in pure Python:\n\n")
                append("1. **Architecture Stack**:\n")
                append("   - **Frontend UI**: Streamlit (`stmol` component for WebGL 3D rendering).\n")
                append("   - **Structure Data**: RCSB PDB REST API (`https://files.rcsb.org/download/{PDB_ID}.pdb`).\n")
                append("   - **Bioinformatics Engine**: Biopython or RDKit.\n\n")
                append("2. **Core Python Implementation**:\n")
                append("```python\n")
                append("import streamlit as st\n")
                append("import py3Dmol\n")
                append("from stmol import showmol\n")
                append("import requests\n\n")
                append("st.title('🔬 Macromolecular 3D Structure Viewer')\n")
                append("pdb_id = st.text_input('Enter PDB Code (e.g. 1CBS, 6LU7):', '1CBS').upper()\n\n")
                append("if pdb_id:\n")
                append("    res = requests.get(f'https://files.rcsb.org/download/{pdb_id}.pdb')\n")
                append("    if res.ok:\n")
                append("        view = py3Dmol.view(width=700, height=450)\n")
                append("        view.addModel(res.text, 'pdb')\n")
                append("        view.setStyle({'cartoon': {'color': 'spectrum'}})\n")
                append("        view.zoomTo()\n")
                append("        showmol(view, height=450, width=700)\n")
                append("```\n\n")
                append("3. **Deployment**: Package into a lightweight Docker image based on `python:3.11-slim` with Mamba/Conda for native scientific binary packages.")
            }

            q.contains("blast") || q.contains("alignment") || q.contains("needleman") || q.contains("smith") -> buildString {
                append("### 🧬 Bioinformatics Sequence Alignment & BLAST+\n\n")
                append("Sequence alignment identifies homologous genes, evolutionary distance, and pesticide resistance mutations.\n\n")
                append("1. **Dynamic Programming Comparison**:\n")
                append("   - **Needleman-Wunsch (Global Alignment)**: Aligns end-to-end. Maximizes total similarity across entire sequences. Used for closely related genes.\n")
                append("   - **Smith-Waterman (Local Alignment)**: Identifies conserved motifs/domains within divergent sequences. Ignores poorly matching flanking regions.\n\n")
                append("2. **Heuristic BLAST+ (Basic Local Alignment Search Tool)**:\n")
                append("   - Uses word hits (k-mers: default 11 for blastn, 3 for blastp) to rapidly seed candidate alignments without calculating the full O(MN) dynamic programming matrix.\n")
                append("   - **E-value (Expectation Value)**: The number of chance alignments with an equivalent or higher score expected in a database of size N. Values < 1e-5 indicate statistically significant biological homology.\n\n")
                append("3. **Biopython Automated Alignment Code**:\n")
                append("```python\n")
                append("from Bio import Align\n")
                append("aligner = Align.PairwiseAligner()\n")
                append("aligner.mode = 'global' # or 'local'\n")
                append("aligner.match_score = 1.0\n")
                append("aligner.mismatch_score = -1.0\n")
                append("aligner.open_gap_score = -0.5\n")
                append("alignments = aligner.align('ATGCGATCG', 'ATGCGATAG')\n")
                append("print(alignments[0])\n")
                append("```")
            }

            q.contains("dock") || q.contains("openmm") || q.contains("dynamics") || q.contains("gromacs") || q.contains("alphafold") -> buildString {
                append("### ⚛️ Molecular Dynamics & In Silico Agrochemical Docking\n\n")
                append("Simulate atomic movements and screen pesticide molecules against receptor binding pockets.\n\n")
                append("1. **Simulation Workflow**:\n")
                append("   - **Structure Acquisition**: RCSB PDB crystal structures or AlphaFold 2/3 coordinates.\n")
                append("   - **Force Field**: AMBER ff14SB or CHARMM36m parameterizes bonded (bond length, angle, dihedral) and non-bonded (van der Waals, Coulomb electrostatics) forces.\n")
                append("   - **Solvation & Neutralization**: Place molecule in an explicit TIP3P water box with 0.15 M NaCl to mimic physiological conditions.\n")
                append("   - **Energy Minimization**: Remove atomic clashes using Steepest Descent.\n")
                append("   - **Equilibration**: NVT ensemble (constant volume & temperature) followed by NPT ensemble (constant pressure at 1.0 bar).\n\n")
                append("2. **AutoDock Vina Virtual Screening**:\n")
                append("   - Converts receptor to `.pdbqt` (charges and atom types assigned).\n")
                append("   - Generates 3D search grid around the active catalytic site.\n")
                append("   - Computes free energy of binding (kcal/mol). Negative scores indicate tighter receptor affinity (e.g. -8.5 kcal/mol indicates high binding potential).")
            }

            q.contains("rdkit") || q.contains("smiles") || q.contains("lipinski") || q.contains("tpsa") || q.contains("logp") -> buildString {
                append("### 🧪 Chemoinformatics & RDKit Molecule Profiling\n\n")
                append("Compute physicochemical descriptors and evaluate agrochemical systemic translocation.\n\n")
                append("1. **Lipinski Rule of Five (Bioavailability Filter)**:\n")
                append("   - **Molecular Weight (MW)**: <= 500 g/mol (crucial for plant xylem / cuticle penetration).\n")
                append("   - **Octanol-Water Partition (LogP)**: <= 5.0 (optimal range 1.5 - 3.5 for systemic crop protection).\n")
                append("   - **Hydrogen Bond Donors (HBD)**: <= 5 (OH, NH groups).\n")
                append("   - **Hydrogen Bond Acceptors (HBA)**: <= 10 (O, N atoms).\n\n")
                append("2. **RDKit Python Script**:\n")
                append("```python\n")
                append("from rdkit import Chem\n")
                append("from rdkit.Chem import Descriptors, Lipinski\n\n")
                append("smiles = 'CC1=C(C(=CC(=C1)Br)C(=O)NC2=CC(=NN2C3=CC(=CC=C3)Cl)C(=O)NC)Cl'\n")
                append("mol = Chem.MolFromSmiles(smiles)\n")
                append("mw = Descriptors.MolWt(mol)\n")
                append("logp = Descriptors.MolLogP(mol)\n")
                append("tpsa = Descriptors.TPSA(mol)\n")
                append("print(f'MW: {mw:.1f} | LogP: {logp:.2f} | TPSA: {tpsa:.1f} A^2')\n")
                append("```\n\n")
                append("3. **Agricultural Application**: Predicts whether a foliar insecticide will translocate acropetally in the xylem to protect newly expanding leaves from insect attack.")
            }

            q.contains("ncbi") || q.contains("uniprot") || q.contains("pubchem") || q.contains("entrez") || q.contains("database") -> buildString {
                append("### 📚 Biological Databases & REST API Integration\n\n")
                append("Programmatic data pipelines for automated genomic and chemical intelligence.\n\n")
                append("1. **NCBI Entrez E-Utilities**:\n")
                append("   - `esearch.fcgi`: Search PubMed, Nucleotide, or Protein for accessions matching a query.\n")
                append("   - `efetch.fcgi`: Retrieve full FASTA, GenBank, or XML records for returned UIDs.\n")
                append("   - Rate limit: 3 requests/sec without API key (10 requests/sec with personal NCBI API key).\n\n")
                append("2. **UniProt REST API**:\n")
                append("   - Query endpoint: `https://rest.uniprot.org/uniprotkb/{ACCESSION}.json`\n")
                append("   - Returns annotated active sites, catalytic residues, post-translational modifications, and GO terms.\n\n")
                append("3. **PubChem PUG REST**:\n")
                append("   - Query endpoint: `https://pubchem.ncbi.nlm.nih.gov/rest/pug/compound/name/{NAME}/cids/JSON`\n")
                append("   - Retrieve 3D SDF structures, canonical SMILES, bioassays, and pesticide safety data directly via HTTP GET.")
            }

            else -> buildString {
                append("### 🧬 Bioscience & Scientific Web Application Suite\n\n")
                append("CropShield Bioscience Skills empowers you to build full-stack scientific applications integrating:\n\n")
                append("1. **Scientific Web Dashboards**: Streamlit, Dash, and FastAPI for real-time computational biology, 3D molecular viewers (3Dmol.js, Mol*), and containerized pipelines.\n")
                append("2. **Bioinformatics**: Sequence alignment (BLAST+, Biopython), phylogenetic tree reconstruction, and NGS variant calling for crop pathology.\n")
                append("3. **Molecular Dynamics**: AlphaFold structure modeling, OpenMM/GROMACS simulations, and AutoDock Vina in silico pesticide binding.\n")
                append("4. **Chemoinformatics**: RDKit descriptor calculation, Lipinski Rule of 5 evaluation, and QSAR environmental fate modeling.\n")
                append("5. **Biological Databases**: Automated data extraction from NCBI GenBank, UniProt, RCSB PDB, and PubChem.\n\n")
                append("Tap any of the **Quick Prompts** above or ask a specific question to generate working code and architecture recipes!")
            }
        }
    }
}
