package com.example.data.science

import com.example.model.science.BioscienceDomain
import com.example.model.science.BioscienceSkillModule
import com.example.model.science.CodeRecipe
import com.example.model.science.ExternalScientificLink
import com.example.model.science.ScientificWebAppScaffold

/**
 * Authoritative scientific catalog for bioscience skills, bioinformatics pipelines,
 * molecular dynamics, chemoinformatics tools, biological databases, and scientific web applications.
 */
object BioscienceSkillsCatalog {

    val allModules: List<BioscienceSkillModule> = listOf(
        // -------------------------------------------------------------
        // MODULE 1: SCIENTIFIC WEB APP DEVELOPMENT
        // -------------------------------------------------------------
        BioscienceSkillModule(
            id = "web_app_streamlit_bioviz",
            title = "Building Scientific Web Apps with Streamlit & Mol*",
            domain = BioscienceDomain.WEB_APP_DEV,
            summary = "Architect rapid, reactive scientific web applications in pure Python. Integrate embedded Mol* and 3Dmol.js viewers for interactive 3D macromolecular structures, real-time calculation sliders, and automated reporting.",
            keyConcepts = listOf(
                "Reactive UI State in Scientific Dashboards",
                "Embedding 3D Molecular Graphics (Mol*, NGL, 3Dmol.js)",
                "Session State Management for Large PDB / FASTA Files",
                "Containerizing Scientific Apps with Docker & Conda",
                "FastAPI Microservices for Compute-Heavy Simulations"
            ),
            toolsAndLibraries = listOf(
                "Streamlit", "FastAPI", "stmol / 3dmol.js", "Biopython", "Plotly", "Docker"
            ),
            webAppArchitecture = "Frontend: Streamlit reactive components with stmol/py3Dmol iframe embeddings. Backend: FastAPI worker executing headless Biopython/RDKit computations. Data pipeline: Streams PDB files directly from RCSB REST APIs and parses coordinates client-side.",
            agriculturalRelevance = "Enables agronomists and plant breeders to build web dashboards displaying 3D crystal structures of crop disease targets (e.g. Phytophthora metalloproteases) and test novel biopesticide candidate bindings in real time.",
            codeRecipe = CodeRecipe(
                title = "Interactive 3D Protein Viewer Web App in Streamlit",
                language = "python",
                framework = "Streamlit + py3Dmol",
                code = """
import streamlit as st
import py3Dmol
from stmol import showmol
import requests

st.set_page_config(page_title="Agri-Bio 3D Viewer", layout="wide")
st.title("🌾 Crop Protein & Agrochemical 3D Viewer")

# User inputs PDB ID (e.g., 1TIM, 6VXX for Spike, or Plant Acetylcholinesterase)
pdb_id = st.text_input("Enter PDB Accession Code (e.g. 1CBS, 6LU7):", value="1CBS").strip().upper()
style = st.selectbox("Render Style:", ["cartoon", "stick", "sphere", "cross"])
color_scheme = st.selectbox("Color By:", ["spectrum", "chain", "secondary structure", "element"])

if pdb_id:
    # Fetch PDB coordinate data from RCSB REST API
    url = f"https://files.rcsb.org/download/{pdb_id}.pdb"
    response = requests.get(url)
    if response.status_code == 200:
        pdb_data = response.text
        # Initialize py3Dmol viewer
        view = py3Dmol.view(width=800, height=500)
        view.addModel(pdb_data, "pdb")
        view.setStyle({style: {'color': color_scheme}})
        view.zoomTo()
        showmol(view, height=500, width=800)
        st.success(f"Successfully rendered PDB: {pdb_id}")
    else:
        st.error(f"PDB {pdb_id} not found on RCSB Protein Data Bank.")
                """.trimIndent(),
                explanation = "This complete Streamlit script creates a production-grade web application that fetches 3D coordinates from the RCSB Protein Data Bank and renders interactive 3D protein structures directly inside the browser using WebGL."
            ),
            databaseLinks = listOf(
                ExternalScientificLink(
                    title = "RCSB Protein Data Bank REST API",
                    provider = "PDB",
                    url = "https://data.rcsb.org/",
                    description = "Comprehensive programmatic REST API for retrieving atomic coordinates, structure validation, and ligand annotations."
                ),
                ExternalScientificLink(
                    title = "Streamlit Scientific Documentation",
                    provider = "Google Web",
                    url = "https://docs.streamlit.io/",
                    description = "Official guides for deploying computational science dashboards and reactive Python applications."
                )
            ),
            recommendedSearchQueries = listOf(
                "Streamlit bioinformatics web app tutorial",
                "Embed Mol* 3D viewer in React web app",
                "FastAPI Biopython REST service architecture",
                "Dockerize computational biology workflow"
            ),
            geminiChatPrompts = listOf(
                "Write a Python Streamlit web app that accepts a FASTA DNA sequence and computes GC content, translation, and open reading frames.",
                "How do I containerize a scientific web app using Docker and Mamba for reproducible bioinformatics?",
                "Create a FastAPI endpoint that receives a SMILES string, generates a 2D depiction with RDKit, and returns SVG bytes."
            )
        ),

        // -------------------------------------------------------------
        // MODULE 2: GLOBAL PEST & DISEASE IDENTIFICATION PORTALS & GUIDES
        // -------------------------------------------------------------
        BioscienceSkillModule(
            id = "global_pest_identification_portals",
            title = "Global Pest & Disease Portals & Diagnostic Guides",
            domain = BioscienceDomain.GLOBAL_PEST_PORTALS,
            summary = "Comprehensive integration of 11 world-class agricultural identification portals (AHR Australia, Pests of Bhutan, CABI PlantwisePlus, Bayer CropScience ID, EPPO Quarantine A1/A2, Penn State Extension, Gardener's Supply Encyclopedia, Minnesota MDA Index, TAAT Africa, Corson Maize, and iNaturalist) plus the NUFLIP demonstration field factsheets (Solanaceae, Cucurbits, Brassicas, Alliums, physiological disorders, IRAC/FRAC classifications).",
            keyConcepts = listOf(
                "CABI PlantwisePlus Decision Guides (Green & Amber List Controls)",
                "EPPO International A1/A2 Quarantine Registers & Biosecurity Alerts",
                "AHR Australasian Field Guides for Vegetable Diseases & Pests",
                "Penn State Extension Pathology Keys & Microscopic Spores",
                "iNaturalist AI Computer Vision Species Observations & Geolocation",
                "TAAT Africa Pan-African Fall Armyworm & Crop Protection Toolkits",
                "NUFLIP JICA Uganda Vegetable Disorders (BER, Sunburn, Nutrients) & IRAC/FRAC Classes"
            ),
            toolsAndLibraries = listOf(
                "CABI PlantwisePlus", "EPPO Global DB", "iNaturalist API", "Penn State Extension", "Bayer CropScience ID", "TAAT Africa", "AHR Guides"
            ),
            webAppArchitecture = "Multi-database query aggregator: Frontend accepts any crop pest, weed, or disease; backend generates deep search links and REST queries to CABI, EPPO, and iNaturalist; displays side-by-side symptoms, quarantine status, and IRAC/FRAC rotation schedules.",
            agriculturalRelevance = "Empowers agronomists, quarantine inspectors, and smallholder farmers to cross-reference local field observations against 11 global authoritative databases and certified demonstration field factsheets.",
            codeRecipe = CodeRecipe(
                title = "Multi-Portal Agricultural Diagnostic Deep-Search Tool in Python",
                language = "python",
                framework = "Python (Requests + urllib)",
                code = """
import urllib.parse
import requests

# 11 Authoritative Agricultural Pest & Disease Identification Portals
PORTALS = {
    "AHR Australia Vegetable Guides": "https://ahr.com.au/?s=",
    "Pests of Bhutan (NPPC)": "https://pestsofbhutan.nppc.gov.bt/?s=",
    "CABI PlantwisePlus Knowledge Bank": "https://plantwiseplusknowledgebank.org/action/doSearch?AllField=",
    "Bayer CropScience Agronomy ID": "https://cropscience.bayer.co.uk/search?q=",
    "EPPO Global Quarantine Database": "https://gd.eppo.int/search?k=",
    "Penn State Extension Pest Library": "https://extension.psu.edu/search/?q=",
    "Gardener's Supply Encyclopedia": "https://www.gardeners.com/search?q=",
    "Minnesota MDA Pests Index": "https://www.mda.state.mn.us/search?q=",
    "TAAT Africa Technologies": "https://e-catalogs.taat-africa.org/gov/technologies?q=",
    "Corson Maize Pests & Diseases": "https://www.corsonmaize.co.nz/search?Search=",
    "iNaturalist Global Biodiversity": "https://www.inaturalist.org/search?q="
}

def query_global_portals(pest_or_disease: str):
    print(f"=== Sourcing Global Knowledge for: '{pest_or_disease}' ===")
    encoded_query = urllib.parse.quote_plus(pest_or_disease.strip())
    results = {}
    for name, base_url in PORTALS.items():
        search_link = f"{base_url}{encoded_query}"
        results[name] = search_link
        print(f" • [{name}]: {search_link}")
    return results

# Example: Query for Fall Armyworm (Spodoptera frugiperda)
query_global_portals("Spodoptera frugiperda")
                """.trimIndent(),
                explanation = "Generates verified deep search links across all 11 premier international pest and disease knowledge bases for any plant pathogen, insect vector, or physiological disorder."
            ),
            databaseLinks = listOf(
                ExternalScientificLink(
                    title = "AHR Pest & Disease Identification Guides",
                    provider = "AHR Australia",
                    url = "https://ahr.com.au/blog/news/pest-and-disease-identification-guides-available",
                    description = "Field vegetable diagnostic identification guides for Brassicas, Alliums, Solanaceae, and Cucurbits."
                ),
                ExternalScientificLink(
                    title = "Pests of Bhutan Portal",
                    provider = "NPPC Bhutan",
                    url = "https://pestsofbhutan.nppc.gov.bt/",
                    description = "National agricultural pest and disease registry detailing life cycles, symptoms, and control measures."
                ),
                ExternalScientificLink(
                    title = "CABI PlantwisePlus Knowledge Bank",
                    provider = "CABI",
                    url = "https://plantwiseplusknowledgebank.org/doi/10.5555/pwkb.20167800063",
                    description = "Open-access pest management decision guides, distribution maps, and factsheets worldwide."
                ),
                ExternalScientificLink(
                    title = "Bayer CropScience Agronomy ID",
                    provider = "Bayer CropScience",
                    url = "https://cropscience.bayer.co.uk/agronomy-id/pest-and-slugs",
                    description = "Agronomy identification directory for invertebrate pests, mollusc slugs, and foliar disease vectors."
                ),
                ExternalScientificLink(
                    title = "EPPO Plant Quarantine A1 & A2 Lists",
                    provider = "EPPO",
                    url = "https://www.eppo.int/ACTIVITIES/plant_quarantine/A1_list",
                    description = "Official international standard phytosanitary register of quarantine pests recommended for regulation."
                ),
                ExternalScientificLink(
                    title = "Penn State Extension Identification Library",
                    provider = "Penn State",
                    url = "https://extension.psu.edu/insects-pests-and-diseases/pest-disease-and-weed-identification/see-all-pest-disease-and-weed-identification",
                    description = "Academic university extension factsheets for insects, crop pathogens, and weed bio-indicators."
                ),
                ExternalScientificLink(
                    title = "Gardener's Supply Pest & Disease Encyclopedia",
                    provider = "Gardener's Supply",
                    url = "https://www.gardeners.com/blogs/pest-disease-encyclopedia",
                    description = "Horticultural encyclopedia detailing organic controls, beneficial insects, and physiological disorders."
                ),
                ExternalScientificLink(
                    title = "Minnesota MDA Plant Pests & Diseases Index",
                    provider = "Minnesota MDA",
                    url = "https://www.mda.state.mn.us/plant-pests-diseases-index",
                    description = "Statutory state database tracking agricultural plant pests, invasive insects, and noxious weeds."
                ),
                ExternalScientificLink(
                    title = "TAAT Africa Pest Control Technologies",
                    provider = "TAAT / AfDB",
                    url = "https://e-catalogs.taat-africa.org/gov/technologies?categories%5B%5D=Pest%20control",
                    description = "African Development Bank catalog deploying certified bio-pesticides and Fall Armyworm toolkits."
                ),
                ExternalScientificLink(
                    title = "Corson Maize Pests & Diseases",
                    provider = "Corson Maize",
                    url = "https://www.corsonmaize.co.nz/pests-and-diseases",
                    description = "Specialized cereal and maize diagnostic guide for foliar blights, cutworms, armyworms, and stalk rots."
                ),
                ExternalScientificLink(
                    title = "iNaturalist Global Biodiversity & AI ID",
                    provider = "iNaturalist",
                    url = "https://www.inaturalist.org/",
                    description = "Global biodiversity platform with 150M+ verified photo observations and computer vision AI identification."
                )
            ),
            recommendedSearchQueries = listOf(
                "CABI PlantwisePlus Fall Armyworm decision guide",
                "EPPO quarantine A1 list Xylella fastidiosa",
                "AHR Australia vegetable pest identification guide",
                "Penn State Extension tomato late blight diagnostic key",
                "NUFLIP Uganda vegetable pests and physiological disorders factsheet",
                "iNaturalist agricultural insect pest observation records"
            ),
            geminiChatPrompts = listOf(
                "Draw information from CABI PlantwisePlus, EPPO Quarantine list, and Penn State Extension to diagnose bacterial canker versus bacterial wilt in tomatoes.",
                "Cross-reference the NUFLIP Uganda vegetable factsheet to explain Blossom End Rot causes, calcium translocation, and preventive foliar calcium protocols.",
                "How do IRAC MoA Group 1B Organophosphates and Group 4A Neonicotinoids differ in preventing resistance in diamondback moth?"
            )
        ),

        // -------------------------------------------------------------
        // MODULE 3: MOLECULAR DYNAMICS & STRUCTURAL BIOLOGY
        // -------------------------------------------------------------
        BioscienceSkillModule(
            id = "mol_dynamics_alphafold_docking",
            title = "Molecular Dynamics, AlphaFold & Agrochemical Docking",
            domain = BioscienceDomain.MOLECULAR_DYNAMICS,
            summary = "Explore physics-based biomolecular simulations and AI structure prediction. Calculate thermodynamic ensembles (NVT, NPT), run force field parameterizations, predict tertiary structures with AlphaFold 2/3, and perform in silico pesticide docking.",
            keyConcepts = listOf(
                "Molecular Mechanics Force Fields (AMBER ff14SB, CHARMM36m, OPLS-AA)",
                "Explicit Solvation Models (TIP3P Water Box & Neutralizing Counterions)",
                "Energy Minimization (Steepest Descent -> Conjugate Gradient)",
                "Thermodynamic Equilibration: NVT (Berendsen Thermostat) & NPT (Parrinello-Rahman)",
                "AI Structure Prediction with AlphaFold 2/3 & ESMFold",
                "Receptor-Ligand Molecular Docking (AutoDock Vina, Binding Affinity in kcal/mol)"
            ),
            toolsAndLibraries = listOf(
                "OpenMM", "GROMACS", "AutoDock Vina", "AlphaFold", "PyMOL", "MDAnalysis"
            ),
            webAppArchitecture = "Client uploads ligand PDBQT and receptor PDB; backend runs AutoDock Vina binding grid calculation inside a GPU/CPU worker; returns affinity scoring and interactive 3D poses in 3Dmol.js.",
            agriculturalRelevance = "Screen novel eco-friendly biopesticides by simulating their binding affinity to Fall Armyworm Ryanodine receptors or insect acetylcholinesterase, predicting efficacy before synthesis.",
            codeRecipe = CodeRecipe(
                title = "OpenMM Molecular Dynamics Simulation Setup in Python",
                language = "python",
                framework = "OpenMM + PDBFixer",
                code = """
import openmm as mm
from openmm import app
from openmm import unit

# 1. Load biological structure
pdb = app.PDBFile('protein.pdb')

# 2. Select All-Atom Forcefield and Water Solvation Model
forcefield = app.ForceField('amber14-all.xml', 'amber14/tip3pfb.xml')

# 3. Add explicit water solvent box with 1.0 nm buffer and 0.15 M NaCl
modeller = app.Modeller(pdb.topology, pdb.positions)
modeller.addSolvent(forcefield, model='tip3p', padding=1.0*unit.nanometers, ionicStrength=0.15*unit.molar)

# 4. Construct OpenMM System with Particle Mesh Ewald (PME) electrostatics
system = forcefield.createSystem(
    modeller.topology,
    nonbondedMethod=app.PME,
    nonbondedCutoff=1.0*unit.nanometers,
    constraints=app.HBonds
)

# 5. Langevin Integrator: 300 K temperature, 1.0 ps friction, 2 fs timestep
integrator = mm.LangevinMiddleIntegrator(300*unit.kelvin, 1.0/unit.picoseconds, 0.002*unit.picoseconds)
simulation = app.Simulation(modeller.topology, system, integrator)
simulation.context.setPositions(modeller.positions)

# 6. Energy Minimization to eliminate steric clashes
print("Minimizing potential energy...")
simulation.minimizeEnergy(maxIterations=500)
print("Energy minimized. Starting NVT production run...")
simulation.reporters.append(app.StateDataReporter('trajectory.log', 1000, step=True, potentialEnergy=True, temperature=True))
simulation.step(10000) # 20 picoseconds demo run
print("Simulation completed successfully.")
                """.trimIndent(),
                explanation = "Production-ready OpenMM script configuring an explicit TIP3P solvent box, PME electrostatics, Langevin middle integrator, energy minimization, and trajectory reporting."
            ),
            databaseLinks = listOf(
                ExternalScientificLink(
                    title = "AlphaFold Protein Structure Database",
                    provider = "Google Web",
                    url = "https://alphafold.ebi.ac.uk/",
                    description = "Over 200 million highly accurate AI-predicted 3D protein structures covering crops, fungi, and insect vectors."
                ),
                ExternalScientificLink(
                    title = "BioExcel Molecular Dynamics Hub",
                    provider = "Google Web",
                    url = "https://bioexcel.eu/",
                    description = "State-of-the-art tutorials and workflows for GROMACS, Amber, and biomolecular simulation software."
                )
            ),
            recommendedSearchQueries = listOf(
                "OpenMM python molecular dynamics tutorial",
                "AlphaFold 3 plant pathogen protein structure",
                "AutoDock Vina pesticide receptor virtual screening",
                "GROMACS protein ligand binding free energy simulation"
            ),
            geminiChatPrompts = listOf(
                "Explain the mathematical formulation of Particle Mesh Ewald (PME) for long-range electrostatics in MD simulations.",
                "How do I set up an AutoDock Vina molecular docking web tool for screening fungal fungicide inhibitors?",
                "Write a Python script using MDAnalysis to compute RMSD and RMSF of an agrochemical receptor across a trajectory."
            )
        ),

        // -------------------------------------------------------------
        // MODULE 4: CHEMISTRY TOOLS & CHEMOINFORMATICS
        // -------------------------------------------------------------
        BioscienceSkillModule(
            id = "chemistry_rdkit_chemoinformatics",
            title = "Chemoinformatics & Computational Chemistry with RDKit",
            domain = BioscienceDomain.CHEMISTRY_TOOLS,
            summary = "Compute chemical descriptors, evaluate pesticide bioavailability with Lipinski Rule of 5, generate 2D/3D conformers from SMILES strings, and train Quantitative Structure-Activity Relationship (QSAR) models for agrochemical synthesis.",
            keyConcepts = listOf(
                "Chemical Identifiers: Canonical SMILES, InChI, InChIKey, SDF/MOL",
                "Lipinski Rule of Five: MW <= 500, LogP <= 5, HBD <= 5, HBA <= 10",
                "Topological Polar Surface Area (TPSA) & Blood-Brain / Cuticle Penetration",
                "Molecular Fingerprints: Morgan / Extended Connectivity Fingerprints (ECFP4)",
                "Tanimoto Similarity Index for Agrochemical Clustering",
                "Soil Degradation Kinetics: DT50, Hydrolysis, and Sorption Koc"
            ),
            toolsAndLibraries = listOf(
                "RDKit", "Open Babel", "PubChemPy", "SciKit-Learn", "Mordred", "PySCF"
            ),
            webAppArchitecture = "Web form accepts SMILES or draws molecules via Ketcher/JSME plugin; RDKit computes 2D coordinates, physicochemical properties, and agrochemical compliance metrics; displays radar charts.",
            agriculturalRelevance = "Predict soil persistence, leaching risk, and insect cuticle permeability of active pesticide molecules before field trials.",
            codeRecipe = CodeRecipe(
                title = "Compute Lipinski Descriptors and TPSA with RDKit",
                language = "python",
                framework = "RDKit (rdkit.Chem)",
                code = """
from rdkit import Chem
from rdkit.Chem import Descriptors, Lipinski, Draw

# Example: Chlorantraniliprole (Popular anthranilic diamide insecticide)
smiles = "CC1=C(C(=CC(=C1)Br)C(=O)NC2=CC(=NN2C3=CC(=CC=C3)Cl)C(=O)NC)Cl"
mol = Chem.MolFromSmiles(smiles)

if mol:
    # 1. Physicochemical calculations
    mw = Descriptors.MolWt(mol)
    logp = Descriptors.MolLogP(mol)
    hbd = Lipinski.NumHDonors(mol)
    hba = Lipinski.NumHAcceptors(mol)
    rot_bonds = Descriptors.NumRotatableBonds(mol)
    tpsa = Descriptors.TPSA(mol)

    # 2. Evaluate Lipinski Rule of 5
    is_lipinski = (mw <= 500) and (logp <= 5.0) and (hbd <= 5) and (hba <= 10)

    print("--- Agrochemical Molecular Profile ---")
    print(f"Molecular Weight: {mw:.2f} g/mol")
    print(f"Calculated LogP:   {logp:.2f}")
    print(f"H-Bond Donors:    {hbd}")
    print(f"H-Bond Acceptors: {hba}")
    print(f"Rotatable Bonds:  {rot_bonds}")
    print(f"TPSA:             {tpsa:.2f} Å²")
    print(f"Lipinski Drug-likeness: {'PASS' if is_lipinski else 'VIOLATION'}")

    # Generate 2D structure image
    Draw.MolToFile(mol, 'molecule_2d.png', size=(400, 300))
    print("Structure written to molecule_2d.png")
else:
    print("Invalid SMILES sequence.")
                """.trimIndent(),
                explanation = "Parses chemical SMILES into an RDKit Mol object, calculates essential physicochemical properties, assesses Lipinski compliance, and renders the 2D skeletal depiction."
            ),
            databaseLinks = listOf(
                ExternalScientificLink(
                    title = "PubChem Chemical Database",
                    provider = "PubChem",
                    url = "https://pubchem.ncbi.nlm.nih.gov/",
                    description = "National Library of Medicine repository of over 115 million chemical compounds, structures, bioassays, and safety data."
                ),
                ExternalScientificLink(
                    title = "PPDB: Pesticide Properties DataBase",
                    provider = "Google Web",
                    url = "https://sitem.herts.ac.uk/aeru/ppdb/",
                    description = "The gold-standard reference for pesticide environmental fate, toxicity, DT50 values, and ecotoxicology."
                )
            ),
            recommendedSearchQueries = listOf(
                "RDKit python chemoinformatics tutorial",
                "QSAR modeling for insecticide toxicity with scikit-learn",
                "Compute Tanimoto chemical similarity matrix RDKit",
                "Pesticide soil adsorption Koc calculation chemoinformatics"
            ),
            geminiChatPrompts = listOf(
                "How do I write a web app that compares two pesticide SMILES strings and calculates their Tanimoto similarity using Morgan fingerprints?",
                "Explain how Molecular LogP affects pesticide root uptake and systemic translocation in xylem vessels.",
                "Write a Python script that searches PubChem using PubChemPy for registered fungicides by chemical name and extracts their SMILES."
            )
        ),

        // -------------------------------------------------------------
        // MODULE 5: BIOLOGICAL & AGROCHEMICAL DATABASES
        // -------------------------------------------------------------
        BioscienceSkillModule(
            id = "bio_databases_apis_integration",
            title = "Biological Databases & REST APIs (NCBI, UniProt, PDB, PubChem)",
            domain = BioscienceDomain.BIOLOGICAL_DATABASES,
            summary = "Master programmatic access to the world's biological data. Build automated ETL pipelines pulling gene sequences from NCBI Entrez, protein functional domains from UniProt, 3D structures from PDB, and chemical properties from PubChem PUG REST.",
            keyConcepts = listOf(
                "NCBI Entrez E-Utilities (esearch, efetch, esummary)",
                "UniProt Knowledgebase REST API: JSON, FASTA, and GFF formats",
                "PubChem PUG REST: Chemical query by SMILES, CID, and InChIKey",
                "PDB GraphQL & REST APIs for Atomic Coordinate Streaming",
                "Agricultural Repositories: Phytozome, Gramene, AgBioData",
                "Rate-limiting, caching, and batch processing for scientific ETL"
            ),
            toolsAndLibraries = listOf(
                "Requests", "PubChemPy", "Biopython Entrez", "httpx", "Pandas", "SQLite"
            ),
            webAppArchitecture = "Scientific caching proxy: Frontend queries a FastAPI endpoint; backend caches UniProt/NCBI JSON responses in SQLite/Redis to comply with NCBI 3 req/sec rate limits; streams parsed data to interactive tables.",
            agriculturalRelevance = "Automate continuous surveillance of emerging viral plant strains by scheduling daily NCBI Entrez fetch jobs for Kenyan agricultural regions.",
            codeRecipe = CodeRecipe(
                title = "Programmatic UniProt REST API Query & Domain Parser in Python",
                language = "python",
                framework = "UniProt REST API + Python Requests",
                code = """
import requests
import json

# Example: Maize Photosynthetic Phosphoenolpyruvate carboxylase (PEPC)
uniprot_id = "P04711"
url = f"https://rest.uniprot.org/uniprotkb/{uniprot_id}.json"

headers = {"Accept": "application/json"}
response = requests.get(url, headers=headers)

if response.status_code == 200:
    data = response.json()
    gene_name = data.get("genes", [{}])[0].get("geneName", {}).get("value", "N/A")
    organism = data.get("organism", {}).get("scientificName", "N/A")
    seq_length = data.get("sequence", {}).get("length", 0)
    mass = data.get("sequence", {}).get("molWeight", 0)

    print(f"=== UniProt Protein Record: {uniprot_id} ===")
    print(f"Gene Name:  {gene_name}")
    print(f"Organism:   {organism}")
    print(f"Sequence:   {seq_length} amino acids ({mass:,} Da)")

    # Extract Active Sites and Functional Features
    features = data.get("features", [])
    print(f"\nAnnotated Functional Features ({len(features)} total):")
    for feat in features[:5]:
        feat_type = feat.get("type", "Unknown")
        desc = feat.get("description", "No description")
        location = feat.get("location", {}).get("start", {}).get("value", "?")
        print(f" • [{feat_type}] at position {location}: {desc}")
else:
    print(f"Error querying UniProt: HTTP {response.status_code}")
                """.trimIndent(),
                explanation = "Fetches complete programmatic JSON metadata for a protein from the UniProt REST API and parses gene names, taxonomic origins, molecular weights, and active site annotations."
            ),
            databaseLinks = listOf(
                ExternalScientificLink(
                    title = "UniProt Knowledgebase Portal",
                    provider = "UniProt",
                    url = "https://www.uniprot.org/",
                    description = "The world's leading high-quality, comprehensively annotated resource for protein sequence and functional information."
                ),
                ExternalScientificLink(
                    title = "PubChem PUG REST API Specification",
                    provider = "PubChem",
                    url = "https://pubchem.ncbi.nlm.nih.gov/docs/pug-rest",
                    description = "Comprehensive web service documentation for retrieving chemical structures, 3D conformers, and bioassays."
                )
            ),
            recommendedSearchQueries = listOf(
                "NCBI Entrez E-utilities python tutorial",
                "UniProt REST API programmatic protein extraction",
                "PubChem PUG REST API chemical query examples",
                "Agricultural plant genome databases comparison"
            ),
            geminiChatPrompts = listOf(
                "Write a Python script using NCBI Entrez E-utilities to search for 'Puccinia graminis' resistance genes and download their FASTA sequences.",
                "How do I use PubChem PUG REST to download 3D SDF structures of commercial triazole fungicides?",
                "Design a clean SQLite database schema to store crop pathogen genotypes, host plants, and NCBI accession numbers."
            )
        )
    )

    // -------------------------------------------------------------
    // READY-TO-USE WEB APPLICATION SCAFFOLDS
    // -------------------------------------------------------------
    val webAppScaffolds: List<ScientificWebAppScaffold> = listOf(
        ScientificWebAppScaffold(
            id = "scaffold_streamlit_pdb_viewer",
            name = "Streamlit 3D Macromolecular & Ligand Explorer",
            tag = "3D Molecular Visualization",
            domain = BioscienceDomain.WEB_APP_DEV,
            description = "A lightweight scientific web application that accepts any PDB code or local .pdb/.mol2 upload and displays an interactive 3D WebGL viewer with surface representations, hydrogen bonds, and ligand docking pockets.",
            techStack = listOf("Python 3.10+", "Streamlit", "stmol / 3Dmol.js", "Biopython", "Requests"),
            architectureComponents = listOf(
                "app.py: Main reactive dashboard entry point",
                "fetcher.py: RCSB REST API downloader with local cache",
                "visualizer.py: 3Dmol.js WebGL canvas configuration with cartoon/surface toggles",
                "requirements.txt: Dependencies pin (streamlit, stmol, biopython)"
            ),
            demoInputDescription = "PDB ID: '1CBS' (Cellular retinoic acid-binding protein) or '6LU7' (Main Protease).",
            expectedOutputDescription = "Interactive 3D canvas with orbital rotation, zooming, ligand pocket highlighting, and residue sequence viewer.",
            sampleCode = """
# app.py - 3D Molecule Web Application
import streamlit as st
import py3Dmol
from stmol import showmol
import requests

st.title("🔬 3D Bioscience Structure Explorer")
pdb_id = st.sidebar.text_input("PDB Accession ID", value="1CBS").upper()
surface_mode = st.sidebar.checkbox("Show Solvent Surface", value=False)
color_by = st.sidebar.selectbox("Color Palette", ["spectrum", "chain", "element"])

url = f"https://files.rcsb.org/download/{pdb_id}.pdb"
res = requests.get(url)
if res.ok:
    view = py3Dmol.view(width=800, height=500)
    view.addModel(res.text, "pdb")
    view.setStyle({'cartoon': {'color': color_by}})
    if surface_mode:
        view.addSurface(py3Dmol.VDW, {'opacity': 0.7, 'color': 'white'})
    view.zoomTo()
    showmol(view, height=500, width=800)
    st.info(f"Loaded {len(res.text.splitlines())} atom records from RCSB.")
else:
    st.error("Failed to load PDB from RCSB.")
            """.trimIndent()
        ),

        ScientificWebAppScaffold(
            id = "scaffold_global_pest_diagnostic_hub",
            name = "Global Pest Identification & Diagnostics Web Hub",
            tag = "Pest & Disease Portal",
            domain = BioscienceDomain.GLOBAL_PEST_PORTALS,
            description = "Production-grade Streamlit & FastAPI diagnostic portal that connects to 11 global agricultural databases (CABI PlantwisePlus, EPPO Quarantine, Bayer CropScience ID, Penn State Extension, TAAT Africa, AHR, and iNaturalist) to cross-reference pest biology, symptoms, and IRAC/FRAC chemical rotations.",
            techStack = listOf("Python 3.11", "Streamlit", "Requests", "Pandas", "BeautifulSoup4", "Urllib"),
            architectureComponents = listOf(
                "app.py: Interactive multi-database search interface with category filters",
                "portals.py: 11-website deep-linking engine and query formatter",
                "nuflip_data.py: Embedded NUFLIP demonstration field factsheets & IRAC/FRAC matrices",
                "requirements.txt: Dependencies (streamlit, requests, pandas)"
            ),
            demoInputDescription = "Search: 'Fall Armyworm' or 'Bacterial Wilt' or 'Blossom End Rot'.",
            expectedOutputDescription = "Unified diagnostic cards showing direct links into CABI, EPPO, iNaturalist, Penn State, Bayer ID, and NUFLIP control recommendations.",
            sampleCode = """
# app.py - Global Pest Identification Web Hub
import streamlit as st
import urllib.parse

st.set_page_config(page_title="Global Pest & Disease Hub", layout="wide")
st.title("🌾 Global Pest & Disease Identification Hub")

query = st.text_input("Search any pest, pathogen, or physiological disorder:", value="Fall Armyworm")

PORTALS = {
    "CABI PlantwisePlus Knowledge Bank": "https://plantwiseplusknowledgebank.org/action/doSearch?AllField=",
    "EPPO Global Quarantine Database": "https://gd.eppo.int/search?k=",
    "iNaturalist Species Observations": "https://www.inaturalist.org/search?q=",
    "Penn State Extension Identification": "https://extension.psu.edu/search/?q=",
    "Bayer CropScience Agronomy ID": "https://cropscience.bayer.co.uk/search?q=",
    "AHR Australia Vegetable Guides": "https://ahr.com.au/?s=",
    "TAAT Africa Technologies": "https://e-catalogs.taat-africa.org/gov/technologies?q=",
    "Corson Maize Pests & Diseases": "https://www.corsonmaize.co.nz/search?Search="
}

if query:
    encoded = urllib.parse.quote_plus(query.strip())
    st.subheader(f"Direct Diagnostic Records for '{query}':")
    cols = st.columns(2)
    for idx, (portal_name, url_template) in enumerate(PORTALS.items()):
        col = cols[idx % 2]
        with col:
            st.markdown(f"**[{portal_name}]({url_template}{encoded})**")
            st.caption(f"Search official factsheets on {portal_name}")
            """.trimIndent()
        ),

        ScientificWebAppScaffold(
            id = "scaffold_rdkit_agrochemical_dashboard",
            name = "RDKit Agrochemical Bioavailability & QSAR Dashboard",
            tag = "Chemoinformatics Tool",
            domain = BioscienceDomain.CHEMISTRY_TOOLS,
            description = "Web tool that calculates chemical descriptors, evaluates Lipinski Rule of 5 compliance for systemic plant uptake, computes logP, TPSA, and predicts soil mobility index for pesticide candidates.",
            techStack = listOf("Python 3.10", "Streamlit", "RDKit", "Pandas", "Matplotlib", "Seaborn"),
            architectureComponents = listOf(
                "dashboard.py: Interactive form for SMILES input and batch CSV upload",
                "cheminfo.py: RDKit descriptor calculation engine (MW, LogP, TPSA, HBD, HBA)",
                "visuals.py: Radar charts comparing candidate molecules with standard pesticides",
                "database.py: Pre-loaded library of 50 common insecticides and fungicides"
            ),
            demoInputDescription = "SMILES: 'CC1=C(C(=O)NC2=CC(=NN2C3=CC(=CC=C3)Cl)...' or upload a .smi file.",
            expectedOutputDescription = "Radar chart of bioavailability, 2D chemical structure rendering, and pesticide soil leaching classification.",
            sampleCode = """
# dashboard.py - Chemoinformatics Dashboard
import streamlit as st
from rdkit import Chem
from rdkit.Chem import Descriptors, Draw
import io

st.title("🧪 Agrochemical Chemoinformatics & QSAR Analyzer")
smiles = st.text_input("Enter Molecule SMILES string:", value="CC(=O)OC1=CC=CC=C1C(=O)O")

if smiles:
    mol = Chem.MolFromSmiles(smiles)
    if mol:
        col1, col2 = st.columns(2)
        with col1:
            st.metric("Molecular Weight", f"{Descriptors.MolWt(mol):.1f} g/mol")
            st.metric("Calculated LogP", f"{Descriptors.MolLogP(mol):.2f}")
            st.metric("TPSA (Polar Area)", f"{Descriptors.TPSA(mol):.1f} Å²")
        with col2:
            img = Draw.MolToImage(mol, size=(350, 250))
            st.image(img, caption="2D Chemical Structure")
    else:
        st.error("Invalid SMILES format.")
            """.trimIndent()
        ),

        ScientificWebAppScaffold(
            id = "scaffold_pubchem_database_etl",
            name = "PubChem & UniProt Automated Biological Data Pipeline",
            tag = "Biological Data ETL",
            domain = BioscienceDomain.BIOLOGICAL_DATABASES,
            description = "Asynchronous data pipeline and web viewer that synchronizes with PubChem PUG REST and UniProt to download active agrochemical compounds, bioassays, and target protein crystal structures into a searchable database.",
            techStack = listOf("Python 3.10+", "PubChemPy", "Requests", "SQLite3", "FastAPI", "Pandas"),
            architectureComponents = listOf(
                "pipeline.py: Automated batch fetcher with exponential backoff rate limiter",
                "db_schema.sql: Relational tables for Compounds, Targets, and Bioassays",
                "api.py: Lightweight query API returning paginated JSON records",
                "cron_sync.sh: Scheduled synchronization job for emerging crop protectants"
            ),
            demoInputDescription = "Chemical Search: 'Imidacloprid' or Target Search: 'Acetylcholinesterase'.",
            expectedOutputDescription = "Integrated table showing PubChem CID, 2D structure, target UniProt accession, and experimental IC50 inhibition values.",
            sampleCode = """
# pipeline.py - Biological Data Pipeline
import pubchempy as pcp
import requests

def fetch_agrochemical_data(compound_name: str):
    print(f"Fetching data for: {compound_name}")
    compounds = pcp.get_compounds(compound_name, 'name')
    if not compounds:
        return None
    comp = compounds[0]
    return {
        "cid": comp.cid,
        "iupac_name": comp.iupac_name,
        "molecular_formula": comp.molecular_formula,
        "molecular_weight": comp.molecular_weight,
        "canonical_smiles": comp.canonical_smiles,
        "xlogp": comp.xlogp
    }

data = fetch_agrochemical_data("Chlorpyrifos")
print(data)
            """.trimIndent()
        )
    )

    /**
     * Search modules by keyword.
     */
    fun searchModules(query: String): List<BioscienceSkillModule> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return allModules
        return allModules.filter { module ->
            module.title.lowercase().contains(q) ||
            module.summary.lowercase().contains(q) ||
            module.keyConcepts.any { it.lowercase().contains(q) } ||
            module.toolsAndLibraries.any { it.lowercase().contains(q) } ||
            module.agriculturalRelevance.lowercase().contains(q)
        }
    }

    /**
     * Filter modules by domain.
     */
    fun getModulesByDomain(domain: BioscienceDomain?): List<BioscienceSkillModule> {
        if (domain == null) return allModules
        return allModules.filter { it.domain == domain }
    }
}
