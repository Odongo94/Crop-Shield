package com.example.data.science.curriculum

import com.example.model.science.CurriculumPillarType
import com.example.model.science.CurriculumSubtopic
import com.example.model.science.CurriculumTopic

object CropProtectionCurriculum {

    val topics: List<CurriculumTopic> = listOf(
        CurriculumTopic(
            id = "crop_prot_pathology",
            pillar = CurriculumPillarType.CROP_PROTECTION,
            code = "4.a",
            title = "Plant Pathology",
            summary = "The scientific study of plant infectious diseases, diagnostic etiologies, host-pathogen interactions, and taxonomic subdivisions into Bacteriology, Mycology, Nematology, and General Plant Virology.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "cp_bacteriology",
                    code = "i",
                    title = "Plant Bacteriology",
                    coreDefinition = "The biology, infection mechanisms, and diagnostics of phytopathogenic bacteria (predominantly Gram-negative bacilli) that invade hosts through natural openings or wounds.",
                    keyMechanismsAndFormulas = listOf(
                        "Type III Secretion System (T3SS): Molecular 'hypodermic syringe' delivering bacterial effector proteins directly into the host cytoplasm to suppress plant immunity.",
                        "Bacterial Stream Test: Cut vascular stem suspended in clear water; milky exudate streams indicate Ralstonia solanacearum or Clavibacter.",
                        "Major Genera: Ralstonia (bacterial wilt), Xanthomonas (black rot, bacterial blight), Pseudomonas (cankers, specks), Pectobacterium/Dickeya (soft rots via pectolytic enzymes), Erwinia (fire blight)."
                    ),
                    detailedElaboration = "Phytopathogenic bacteria are single-celled prokaryotes lacking nuclear envelopes. Unlike fungi, bacteria cannot actively penetrate intact plant cuticles; they gain entry strictly through stomata, hydathodes, lenticels, or mechanical wounds (pruning, insect feeding, root abrasion). Ralstonia solanacearum (bacterial wilt of potato, tomato, banana) invades xylem vessels and produces copious extracellular polysaccharides (EPS), blocking water transport and causing sudden irreversible wilting without prior yellowing. Pectobacterium carotovorum produces pectate lyases that liquefy plant cell walls into foul-smelling soft rots.",
                    commonExamQuestions = listOf(
                        "Describe how to perform and interpret the bacterial streaming test to confirm Ralstonia solanacearum infection.",
                        "Contrast how fungal pathogens versus bacterial pathogens penetrate plant host tissues.",
                        "Explain the role of extracellular polysaccharides (EPS) in bacterial wilt pathogenesis."
                    ),
                    practicalTakeaway = "Disinfect pruning knives and secateurs between plants using 70% isopropyl alcohol or 10% household sodium hypochlorite bleach to prevent mechanical transmission of bacterial cankers."
                ),
                CurriculumSubtopic(
                    id = "cp_mycology",
                    code = "ii",
                    title = "Plant Mycology",
                    coreDefinition = "The taxonomy, morphology, and epidemiology of phytopathogenic fungi and fungal-like oomycetes causing rusts, smuts, mildews, and vascular wilts.",
                    keyMechanismsAndFormulas = listOf(
                        "Oomycota vs. True Fungi: Oomycetes (Phytophthora, Pythium, Downy mildews) possess cellulose cell walls, coenocytic hyphae, and diploid vegetative states; True Fungi (Ascomycota, Basidiomycota) possess chitin cell walls and haploid/dikaryotic states.",
                        "Appressorium & Infection Peg: Specialized swollen hyphal structure generating high turgor pressure (up to 8 MPa via glycerol accumulation) to mechanically puncture plant cuticle.",
                        "Downy Mildew vs. Powdery Mildew: Downy mildews (Oomycetes) sporulate primarily on abaxial (underside) leaf surfaces; Powdery mildews (Ascomycota - Erysiphales) produce white superficial mycelium on adaxial (upper) leaf surfaces."
                    ),
                    detailedElaboration = "Fungi constitute over 70% of all major plant diseases. Phytophthora infestans (Late blight of potato and tomato) is a devastating oomycete capable of destroying entire crops within 10 days under cool, humid conditions (15–20°C, RH >90%). True fungi include Ascomycetes (e.g., Fusarium oxysporum causing vascular wilt, Colletotrichum causing anthracnose with characteristic acervuli and pink spore masses) and Basidiomycetes (Puccinia rusts with up to five spore stages: pycniospores, aeciospores, urediniospores, teliospores, basidiospores).",
                    commonExamQuestions = listOf(
                        "Explain the biochemical and morphological differences between Oomycetes and True Fungi.",
                        "Distinguish the clinical signs and taxonomic classes of powdery mildews versus downy mildews.",
                        "List the five spore stages in the macrocyclic heteroecious life cycle of Puccinia graminis (cereal stem rust)."
                    ),
                    practicalTakeaway = "Time protective contact fungicide sprays (mancozeb, copper hydroxide) prior to forecast rain events: once fungal hyphae penetrate beneath the host epidermis, contact fungicides are ineffective."
                ),
                CurriculumSubtopic(
                    id = "cp_nematology",
                    code = "iii",
                    title = "Plant Nematology",
                    coreDefinition = "The biology and pathology of plant-parasitic microscopic roundworms equipped with hollow, protrusible stylets used to puncture root cells.",
                    keyMechanismsAndFormulas = listOf(
                        "Diagnostic Nematode Stylet: All plant-parasitic nematodes possess a sclerotized hollow spear (stylet) used to inject esophageal gland secretions and extract cytoplasmic contents.",
                        "Root-Knot Nematodes (Meloidogyne spp.): Sedentary endoparasites; J2 infective juveniles penetrate root elongation zone, inducing multinucleate giant cells and causing root galls/knots.",
                        "Cyst Nematodes (Heterodera, Globodera): Sedentary endoparasites; females swell into spherical bodies that harden post-mortem into resistant brown protective cysts viable for >15 years."
                    ),
                    detailedElaboration = "Nematodes are among the most overlooked soil pathogens. Meloidogyne species (M. incognita, M. javanica) cause diagnostic swollen galls on roots of solanaceous crops, brassicas, and carrots. Affected plants display patchy stunting, wilting during hot afternoons despite adequate soil moisture, and severe nutrient deficiency symptoms. Nematode feeding wounds create vital entry courts for soil-borne fungal wilts (e.g., Fusarium-Meloidogyne disease complex in tomato). Biological control utilizing Pochonia chlamydosporia or Purpureocillium lilacinum parasitizes nematode egg masses.",
                    commonExamQuestions = listOf(
                        "Explain the formation of 'giant cells' induced by Meloidogyne root-knot nematodes.",
                        "Describe how root-knot nematodes exacerbate fungal vascular wilt diseases (Fusarium wilt).",
                        "Distinguish sedentary endoparasitic nematodes from migratory ectoparasitic nematodes."
                    ),
                    practicalTakeaway = "Incorporate biofumigant brassica cover crops (e.g., brown mustard - Brassica juncea) into soil before planting: enzymatic hydrolysis of glucosinolates yields volatile isothiocyanates that suppress nematode juveniles."
                ),
                CurriculumSubtopic(
                    id = "cp_virology",
                    code = "iv",
                    title = "General Plant Virology",
                    coreDefinition = "The molecular structure, replication, vector transmission, and symptomatology of submicroscopic obligate intracellular nucleoprotein viral entities.",
                    keyMechanismsAndFormulas = listOf(
                        "Viral Genome Architecture: Predominantly positive-sense single-stranded RNA (+ssRNA, e.g., TMV, Potato Virus Y); also circular ssDNA (Geminiviridae e.g., Tomato Yellow Leaf Curl Virus - TYLCV).",
                        "Transmission Modes: Persistent/circulative (e.g., TYLCV by Bemisia tabaci whiteflies; circulates through insect hemolymph and salivary glands), Semi-persistent, and Non-persistent/stylet-borne (e.g., CMV and PVY by aphids in brief probes).",
                        "Diagnostic Assays: DAS-ELISA (Double Antibody Sandwich Enzyme-Linked Immunosorbent Assay) and RT-PCR."
                    ),
                    detailedElaboration = "Plant viruses cannot penetrate cell walls unaided; they enter exclusively through mechanical micro-wounds created by insect vectors (aphids, whiteflies, thrips, leafhoppers), grafting, or vegetative propagation. Once inside the cytoplasm, viral coat proteins uncoat, viral replicase translates, and viral movement proteins (MP) traffic the infectious genome from cell to cell through plasmodesmata, eventually reaching phloem sieve tubes for rapid systemic distribution. Symptoms include mosaic, mottling, vein clearing, leaf enation, shoe-stringing, stunting, and fruit malformation.",
                    commonExamQuestions = listOf(
                        "Contrast non-persistent (stylet-borne) viral transmission with persistent circulative transmission.",
                        "Explain why chemical insecticide sprays often fail to prevent non-persistent virus spread in field crops.",
                        "Describe the step-by-step mechanism of viral systemic movement through plasmodesmata and phloem sieve elements."
                    ),
                    practicalTakeaway = "In non-persistent virus epidemics (e.g., Aphid-borne PVY), apply reflective silver plastic mulches or mineral crop oils: insecticides kill aphids too slowly to prevent transmission during brief test probes."
                )
            ),
            examFocusPoints = listOf(
                "Diagnostic differences between bacterial, fungal, nematode, and viral symptoms",
                "Appressorium turgor mechanics and Oomycete vs. True Fungi taxonomy",
                "Vector transmission classes: Non-persistent (stylet-borne) vs. Persistent circulative"
            ),
            practicalFieldApplications = listOf(
                "Bacterial stem ooze test in water for vascular wilt differentiation from fungal wilt",
                "Using immunostrip test kits for rapid on-farm detection of Tomato Spotted Wilt Virus (TSWV)"
            ),
            recommendedTextbookIds = listOf("book_agrios_plant_pathology", "book_luc_plant_parasitic_nematodes")
        ),
        CurriculumTopic(
            id = "crop_prot_entomology",
            pillar = CurriculumPillarType.CROP_PROTECTION,
            code = "4.b",
            title = "Entomology",
            summary = "Insect anatomy, physiology, mouthpart adaptations, metamorphosis types, taxonomic orders of economic importance, population dynamics, Economic Injury Level (EIL), and Economic Threshold (ET).",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "cp_insect_biology_ipm",
                    code = "i",
                    title = "Insect Orders, Metamorphosis & Economic Thresholds",
                    coreDefinition = "The biology and population ecology of hexapod arthropods, focusing on pest management decision indices and integrated pest management (IPM) strategies.",
                    keyMechanismsAndFormulas = listOf(
                        "Metamorphosis Types: Ametabolous (no change), Hemimetabolous (gradual/incomplete: Egg -> Nymph -> Adult e.g., Hemiptera, Orthoptera), Holometabolous (complete: Egg -> Larva -> Pupa -> Adult e.g., Lepidoptera, Coleoptera, Diptera).",
                        "Economic Injury Level (EIL): EIL = C / (V x I x D x K), where C = Cost of management ($/ha), V = Market value ($/kg), I = Injury per pest density, D = Damage per unit injury, K = Proportionate reduction in pest population.",
                        "Economic Threshold (ET) / Action Threshold: Pest density at which control measures must be initiated to prevent the population from reaching the EIL (ET < EIL).",
                        "Major Orders: Lepidoptera (moths/butterflies), Coleoptera (beetles/weevils), Hemiptera (true bugs, aphids, whiteflies), Diptera (true flies), Thysanoptera (thrips)."
                    ),
                    detailedElaboration = "Understanding insect mouthparts is vital for insecticide selection: chewing insects (Lepidopteran larvae, Coleopteran adults) ingest plant foliage and are controlled by stomach poisons (Bt, diamides), whereas piercing-sucking insects (Hemiptera - aphids, whiteflies, planthoppers) ingest vascular sap and require systemic translaminar chemistries (neonicotinoids, flonicamid, spirotetramat). Beneficial predatory insects (Coccinellidae - ladybird beetles, Chrysopidae - green lacewings, Syrphidae larvae) and parasitoid wasps (Trichogramma, Encarsia formosa) provide vital biological suppression when broad-spectrum sprays are avoided.",
                    commonExamQuestions = listOf(
                        "Derive and explain the variables of the Economic Injury Level (EIL) equation.",
                        "Compare complete (holometabolous) metamorphosis with incomplete (hemimetabolous) metamorphosis with three crop pest examples.",
                        "Differentiate chewing mouthparts from piercing-sucking mouthparts in relation to pesticide mode of action selection."
                    ),
                    practicalTakeaway = "Never apply chemical insecticides when pest populations are below the Economic Threshold (ET): doing so destroys beneficial natural enemy complexes and induces secondary pest outbreaks."
                )
            ),
            examFocusPoints = listOf(
                "Derivation and practical interpretation of Economic Threshold (ET) and Economic Injury Level (EIL)",
                "Insect mouthpart morphology (mandibulate vs. haustellate) and insecticide targeting",
                "Complete (Holometabolous) vs. Incomplete (Hemimetabolous) insect life cycles"
            ),
            practicalFieldApplications = listOf(
                "Scouting pest density using sweep nets, sticky cards, and delta pheromone traps",
                "Calculating degree-day (thermal unit) accumulations to forecast insect egg hatch and larval emergence"
            ),
            recommendedTextbookIds = listOf("book_pedigo_entomology_pest_mgmt", "book_borror_delong_insects")
        ),
        CurriculumTopic(
            id = "crop_prot_weed_science",
            pillar = CurriculumPillarType.CROP_PROTECTION,
            code = "4.c",
            title = "Weed Science",
            summary = "Weed biology, persistence mechanisms, seed bank dormancy, classification (grasses, sedges, broadleaves), Critical Period of Weed Control (CPWC), and herbicide modes of action (HRAC groups).",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "cp_weed_ecology_herbicide",
                    code = "i",
                    title = "Weed Classification, CPWC & Herbicide Modes of Action",
                    coreDefinition = "The ecology of undesirable vegetation competing for light, water, nutrients, and space, alongside herbicide classification and resistance stewardship.",
                    keyMechanismsAndFormulas = listOf(
                        "Weed Botanical Classes: Grasses (Poaceae, round hollow stems, parallel veins), Sedges (Cyperaceae, triangular solid stems, 3-ranked leaves: 'Sedges have edges'), Broadleaves (Dicotyledons, net venation).",
                        "Critical Period of Weed Control (CPWC): The specific window in crop ontogeny (typically first 1/3 to 1/2 of crop life cycle, e.g., weeks 2 to 6 in maize) during which weeds must be controlled to prevent economic yield loss.",
                        "Herbicide Resistance Action Committee (HRAC) Groups: Group 4 (Synthetic Auxins - 2,4-D), Group 9 (EPSPS inhibitor - Glyphosate), Group 2 (ALS inhibitors - Sulfonylureas), Group 1 (ACCase inhibitors - Clethodim), Group 10 (Glufosinate)."
                    ),
                    detailedElaboration = "Weeds possess remarkable evolutionary adaptations: high fecundity (e.g., Amaranthus retroflexus produces >100,000 seeds/plant), polymorphic seed dormancy, and allelopathic exudation. Parasitic weeds (Striga hermonthica - Witchweed) latch onto host roots via haustoria, stimulated to germinate by host root strigolactones. Over-reliance on single herbicide modes of action creates massive selection pressure for herbicide resistance (both target-site mutation and metabolic resistance). Rotating herbicide HRAC groups and integrating cover crops are foundational to resistance mitigation.",
                    commonExamQuestions = listOf(
                        "Define the Critical Period of Weed Control (CPWC) and explain its practical significance in farm scheduling.",
                        "Distinguish grasses from sedges using stem and vegetative leaf morphology.",
                        "Explain the biochemical mode of action of Glyphosate (HRAC Group 9) and describe how target-site resistance arises."
                    ),
                    practicalTakeaway = "Maintain clean weed-free fields during the CPWC (first 30–45 days after emergence); weeds emerging after canopy closure generally do not reduce yield, though they may complicate mechanized harvesting."
                )
            ),
            examFocusPoints = listOf(
                "Critical Period of Weed Control (CPWC) concept and determination",
                "Herbicide Modes of Action according to HRAC/WSSA numerical classification",
                "Biology and suicidal germination control of parasitic Striga species"
            ),
            practicalFieldApplications = listOf(
                "Differentiating grass seedlings from sedges in flooded rice and upland cereal fields",
                "Rotating herbicide mechanisms of action to prevent resistant weed biotype evolution"
            ),
            recommendedTextbookIds = listOf("book_ross_lembi_applied_weed_sci", "book_monaco_weed_science")
        ),
        CurriculumTopic(
            id = "crop_prot_disease_management",
            pillar = CurriculumPillarType.CROP_PROTECTION,
            code = "4.d",
            title = "Management of Plant Diseases",
            summary = "The classic disease triangle and disease tetrahedron, principles of plant disease management (exclusion, eradication, protection, resistance), and Fungicide Resistance Action Committee (FRAC) rotation.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "cp_disease_triangle_frac",
                    code = "i",
                    title = "Disease Triangle, Plant Defense & FRAC Rotation",
                    coreDefinition = "The comprehensive integration of cultural, biological, genetic, and chemical interventions to suppress plant disease incidence below economic thresholds.",
                    keyMechanismsAndFormulas = listOf(
                        "The Disease Triangle: Susceptible Host + Virulent Pathogen + Conducive Environment (over Time = Disease Tetrahedron); altering any single component arrests epidemic development.",
                        "Whetzel's Principles of Plant Disease Management: Exclusion (quarantine, certified clean seed), Eradication (destroying inoculum sources, crop rotation), Protection (fungicidal barrier), Resistance (breeding resistant varieties), Therapy (curing infected plants).",
                        "FRAC Codes for Resistance: FRAC 11 (QoI / Strobilurins - inhibit mitochondrial respiration at complex III; high resistance risk), FRAC 3 (DMI / Triazoles - inhibit sterol biosynthesis), FRAC M (Multi-site contact fungicides e.g., Mancozeb, Copper; low resistance risk)."
                    ),
                    detailedElaboration = "Integrated disease management prioritizes genetic resistance (major gene vertical resistance conferring hypersensitive response vs. multi-genic horizontal quantitative resistance). Systemic Acquired Resistance (SAR) can be induced systemically throughout the plant following primary localized infection or chemical elicitor application (e.g., salicylic acid, acibenzolar-S-methyl), stimulating Pathogenesis-Related (PR) protein production. When applying single-site systemic fungicides (FRAC 1, 3, 7, 11), they should always be tank-mixed or alternated with multi-site contact protectants (FRAC M) to preserve molecule longevity.",
                    commonExamQuestions = listOf(
                        "Explain the four components of the disease tetrahedron and how manipulating the microclimate prevents disease.",
                        "Differentiate vertical (qualitative) resistance from horizontal (quantitative) resistance in crops.",
                        "State the management rules established by FRAC to prevent fungal resistance against single-site strobilurin fungicides."
                    ),
                    practicalTakeaway = "Always tank-mix single-site systemic fungicides (e.g., azoxystrobin) with a multi-site contact partner (e.g., mancozeb or chlorothalonil) to protect the single-site chemistry from rapid fungal resistance."
                )
            ),
            examFocusPoints = listOf(
                "Disease Triangle and Whetzel's 5 principles of disease control",
                "Vertical vs. horizontal disease resistance genetics",
                "FRAC code management and multi-site vs. single-site fungicide modes of action"
            ),
            practicalFieldApplications = listOf(
                "Adjusting plant spacing and row orientation to increase air circulation and reduce canopy leaf wetness duration",
                "Designing multi-fungicide rotation calendars alternating FRAC groups"
            ),
            recommendedTextbookIds = listOf("book_agrios_plant_pathology", "book_fry_plant_disease_epidemiol")
        ),
        CurriculumTopic(
            id = "crop_prot_calculations",
            pillar = CurriculumPillarType.CROP_PROTECTION,
            code = "4.e",
            title = "Problems in Insecticide & Dosing Calculations",
            summary = "Practical mathematical formulas and calibrations for pesticide active ingredients, field application rates, knapsack sprayer tank dosages, nozzle flow rates, and walking speed calibrations.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "cp_spray_calibration_formulas",
                    code = "i",
                    title = "Sprayer Calibration, Active Ingredient & Dosing Formulas",
                    coreDefinition = "The mathematical procedures to ensure precise chemical dose delivery to target crops without causing phytotoxicity, under-dosing resistance, or environmental run-off.",
                    keyMechanismsAndFormulas = listOf(
                        "Sprayer Application Volume (L/ha): Spray Volume (L/ha) = [Nozzle Output (L/min) x 600] / [Walking Speed (km/h) x Swath Width (m)].",
                        "Active Ingredient (a.i.) Formulation Equation: Amount of Commercial Product = (Recommended a.i. Dose Rate in kg/ha) / (% a.i. in formulation / 100).",
                        "Knapsack Tank Dose Formula: Product per Tank (mL or g) = [Recommended Field Dose per Hectare (L or kg) x Tank Capacity (L)] / Total Spray Volume per Hectare (L/ha).",
                        "Walking Speed Calculation: Speed (km/h) = [Distance (meters) / Time (seconds)] x 3.6.",
                        "Nozzle Flow Rate Check: Output (L/min) collected into graduated cylinder for exactly 60 seconds; replace nozzle if output deviates by >10% from manufacturer rating."
                    ),
                    detailedElaboration = "Accurate pesticide application is essential for efficacy, safety, and regulatory compliance. An operator spraying at 3.6 km/h with a 0.5m swath width and a nozzle discharging 0.6 L/min delivers: (0.6 x 600) / (3.6 x 0.5) = 360 / 1.8 = 200 Liters/ha spray volume. If the agronomist recommends applying 500g active ingredient/ha of imidacloprid from a 20% SL product, the farmer requires: 500 / 0.20 = 2,500 mL (2.5 L) of commercial formulation per hectare. In a 16-liter knapsack sprayer: (2.5 L x 16 L) / 200 L = 0.20 L = 200 mL of product per 16L tank.",
                    commonExamQuestions = listOf(
                        "An agronomist prescribes 1.5 kg a.i./ha of Atrazine. The commercial product is Atrazine 80% WP. How many kilograms of commercial powder are needed for 3.5 hectares?",
                        "A scout calibrates a 16L knapsack sprayer over a 50m test strip in 45 seconds with a 1.2m boom swath and collects 1,200 mL of water. Calculate the spray volume in L/ha and the number of knapsack refills needed for 1 hectare.",
                        "Why must spray nozzles be replaced if individual flow rates deviate by more than 10% from manufacturer specifications?"
                    ),
                    practicalTakeaway = "Always perform a clean-water calibration run on bare ground before mixing chemicals to determine your exact walking speed and knapsack delivery volume per hectare."
                )
            ),
            examFocusPoints = listOf(
                "Standard pesticide calibration formula: Volume (L/ha) = (L/min x 600) / (km/h x Swath m)",
                "Active ingredient conversion from % EC or WP to commercial product volume",
                "Knapsack tank dose calculations and knapsack refill counts per hectare"
            ),
            practicalFieldApplications = listOf(
                "Calibrating knapsack sprayers using 25-meter calibration track runs with water",
                "Measuring individual nozzle spray distribution patterns using a patternator tray"
            ),
            recommendedTextbookIds = listOf("book_matthews_pesticide_application", "book_bohn_agri_chem")
        )
    )
}
