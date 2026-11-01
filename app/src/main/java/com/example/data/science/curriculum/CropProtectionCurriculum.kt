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
            summary = "The scientific study of infectious plant diseases, cellular etiologies, host-pathogen interactions, and taxonomic subdivisions into Bacteriology, Mycology, Nematology, and General Plant Virology.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "cp_bacteriology",
                    code = "i",
                    title = "Plant Bacteriology",
                    coreDefinition = "The taxonomy, pathogenicity mechanisms, and clinical diagnostics of phytopathogenic bacteria (predominantly Gram-negative non-spore-forming rods) that invade host plants through natural openings or mechanical wounds.",
                    keyMechanismsAndFormulas = listOf(
                        "Type III Secretion System (T3SS / Hrp Pilus): Molecular syringe traversing bacterial inner and outer membranes and the host plant cell wall, injecting bacterial effector proteins (TAL effectors, avr proteins) directly into the host cytoplasm to suppress basal innate immunity (PAMP-triggered immunity).",
                        "Bacterial Stem Streaming Test: Cut surface of symptomatic wilted stem suspended in clear water; milky-white viscous streaming of bacterial cells and slime exudes from xylem bundles within 1–3 minutes (confirms Ralstonia solanacearum or Clavibacter; fungal wilts do not stream).",
                        "Gram Reaction & Major Genera: Gram-negative rods: Ralstonia (bacterial wilt), Xanthomonas (black rot of brassicas, bacterial blight), Pseudomonas (cankers, syringae leaf specks), Erwinia (fire blight of rosaceae), Pectobacterium/Dickeya (pectolytic soft rots). Gram-positive: Clavibacter (bacterial canker), Streptomyces (potato common scab, actinomycete)."
                    ),
                    detailedElaboration = "Phytopathogenic bacteria cannot directly penetrate intact plant cuticles. They enter strictly through natural openings: 1) Stomata (leaf surface gas exchange pores); 2) Hydathodes (vein endings at leaf margins responsible for guttation e.g., Xanthomonas campestris causing V-shaped black rot lesions); 3) Lenticels; and 4) Mechanical wounds created by pruning, transplanting abrasion, or root-chewing nematodes.\n\nRalstonia solanacearum Vascular Wilt: Ralstonia enters via lateral root emergence cracks or nematode feeding wounds, colonizes xylem vessels, and synthesizes copious amounts of high-molecular-weight extracellular polysaccharides (EPS, predominantly EPS I). EPS I drastically increases xylem sap viscosity, mechanically plugging vessel pits and tracheids. The plant suffers sudden, catastrophic water deprivation, wilting while foliage remains completely green without prior yellowing.\n\nPectolytic Soft Rots: Pectobacterium carotovorum (formerly Erwinia carotovora) secretes massive amounts of pectate lyases, polygalacturonases, and cellulases via its Type II Secretion System. These enzymes hydrolyze calcium pectate in middle lamellae and primary cell walls, liquefying plant tissues into a foul-smelling, water-soaked, macerated soft rot in fleshy vegetables and potato tubers.",
                    biologicalAndChemicalDetails = listOf(
                        "Quorum Sensing in Phytobacteria: Density-dependent gene regulation mediated by N-acyl homoserine lactones (AHL) in Gram-negative bacteria or Autoinducer-2 (AI-2); virulence factors and degrading enzymes are held in check until bacterial population reaches a threshold density (10⁷ CFU/g tissue), preventing early triggering of host defense.",
                        "Hypersensitive Response (HR): Programmed cell death of host cells immediately surrounding the bacterial infection court, accompanied by localized oxidative burst (H2O2, superoxide) to starve obligate and biotrophic pathogens."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "Diagnostic Field Bacterial Ooze Stream Test Protocol: Select a tomato or potato plant showing rapid unilateral wilting. Cut the lower stem cleanly 5 cm above ground level with a sharp razor. Suspend the cut stem vertically so the lower 2 cm dips into a clear glass or test tube of clean, still water. Hold against a dark background without shaking: within 60 to 180 seconds, observe if fine, milky-white threads (consisting of billions of bacterial cells and EPS) stream down from the vascular bundles. A positive stream definitively diagnoses bacterial wilt (Ralstonia solanacearum).",
                        "Secateur & Grafting Tool Chemical Sterilization: To prevent pruning transmission of Xanthomonas and Clavibacter, maintain two pairs of shears in a bucket of 10% household sodium hypochlorite bleach (0.5% active NaOCl) or 70% isopropyl alcohol. Alternate shears every 5 plants, allowing minimum 30 seconds of contact disinfection time."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Green Wilt Symptom: Entire plant wilts rapidly and collapses while foliage remains deep green; characteristic of Ralstonia solanacearum.",
                        "V-Shaped Marginal Chlorosis: Yellow V-shaped chlorotic lesions with base at leaf margin and apex pointing inward along veins, followed by blackening of vein bundles; diagnostic of Black Rot in cabbage (Xanthomonas campestris pv. campestris)."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: An agronomist prescribes a prophylactic copper bactericide spray of Copper Hydroxide 77% WP (containing 50% metallic copper equivalent) to protect a 2.5-hectare commercial bell pepper crop against Bacterial Spot (Xanthomonas euvesicatoria). The recommended label dosage is 2.0 kg of commercial product per hectare. Calculate: 1) Total kilograms of commercial Copper Hydroxide product required for the field; and 2) The total mass of active metallic copper (in kg) applied per hectare.\nSolution:\n1) Total commercial product needed = 2.5 ha x 2.0 kg/ha = 5.0 kg of Copper Hydroxide 77% WP.\n2) Metallic copper applied per hectare = 2.0 kg/ha x (50% metallic copper / 100) = 1.0 kg metallic copper/ha."
                    ),
                    commonExamQuestions = listOf(
                        "Describe the protocol and biological principles of the bacterial streaming test used to differentiate bacterial wilt from fungal vascular wilt.",
                        "Explain the role of extracellular polysaccharides (EPS) in the pathogenesis of Ralstonia solanacearum.",
                        "Compare the infection pathways of phytopathogenic bacteria versus phytopathogenic fungi."
                    ),
                    practicalTakeaway = "Once Ralstonia solanacearum bacterial wilt infects a solanaceous field, synthetic chemical bactericides are completely ineffective: enforce strict 4-to-5 year non-host crop rotations with cereals and avoid flood irrigation.",
                    authoritativeTextbookRef = "Plant Pathology (Agrios, 5th Ed., Ch. 12); Laboratory Guide for Identification of Plant Pathogenic Bacteria (Schaad et al.)"
                ),
                CurriculumSubtopic(
                    id = "cp_mycology",
                    code = "ii",
                    title = "Plant Mycology",
                    coreDefinition = "The taxonomy, life cycles, infection structures, and epidemiology of phytopathogenic fungi (Ascomycota, Basidiomycota) and fungal-like Oomycetes causing rusts, smuts, mildews, and blights.",
                    keyMechanismsAndFormulas = listOf(
                        "Oomycota vs. True Fungi: Oomycetes (Phytophthora, Pythium, Downy mildews) belong to Kingdom Stramenopila; possess cell walls of β-1,3 and β-1,6 glucans and cellulose (no chitin), coenocytic diploid hyphae, biflagellate zoospores (tinsel and whiplash flagella), and oospores. True Fungi (Ascomycota, Basidiomycota) Kingdom Fungi; possess chitin and β-glucan cell walls, septate haploid/dikaryotic hyphae, and produce ascospores/basidiospores.",
                        "Appressorium Turgor Mechanics: Melanized swollen spore adhesion structure that accumulates intracellular glycerol (up to 3–5 Molar), generating colossal osmotic turgor pressure (up to 8.0 MPa / 80 atmospheres) to mechanically drive an infection peg through intact plant cuticle.",
                        "Late Blight Disease Forecasting (Smith Periods): Two consecutive days with minimum temperature ≥ 10°C and relative humidity ≥ 90% for at least 11 hours per day trigger high-risk Phytophthora infestans sporulation and zoospore infection."
                    ),
                    detailedElaboration = "Fungi and oomycetes cause over 70% of all economically destructive plant diseases.\n\nLate Blight (Phytophthora infestans): The historic agent of the Great Irish Famine. In cool, humid weather (15–20°C, RH >90%), sporangia germinate indirectly to release 6 to 8 motile biflagellate zoospores. Zoospores encyst, germinate, and penetrate host tissue. The pathogen switches from an initial asymptomatic biotrophic phase to an aggressive necrotrophic phase within 48–72 hours, releasing cell-death elicitors that turn foliage and stems into water-soaked, rotting black lesions with white sporulation on abaxial surfaces. Entire fields collapse within 7–10 days.\n\nRust Fungi (Basidiomycota, Pucciniales): Exhibit intricate macrocyclic heteroecious life cycles producing up to 5 distinct spore stages across two unrelated host species (e.g., Puccinia graminis - Cereal Stem Rust):\n1) Stage 0: Pycnia / Spermogonia producing pycniospores (haploid, on alternate host Berberis vulgaris / Barberry);\n2) Stage I: Aecia producing dikaryotic aeciospores (infects primary cereal host);\n3) Stage II: Uredinia producing repeat dikaryotic repeating urediniospores (brick-red pustules spreading epidemic in wheat);\n4) Stage III: Telia producing overwintering diploid teliospores (black survival resting spores);\n5) Stage IV: Basidia producing haploid basidiospores following meiosis (re-infects barberry).\n\nPowdery vs. Downy Mildews: Powdery mildews (Ascomycota, Erysiphales) are obligate ectoparasites producing white powdery mycelium and conidiophores on upper (adaxial) leaf surfaces under dry, warm conditions with high humidity. Downy mildews (Oomycetes, Peronosporaceae) grow internally and produce violaceous downy sporulation exclusively on lower (abaxial) leaf surfaces through open stomata under wet, cool conditions.",
                    biologicalAndChemicalDetails = listOf(
                        "Melanin in Appressorial Penetration: In Magnaporthe oryzae (Rice Blast) and Colletotrichum, dihydroxynaphthalene (DHN) melanin lines the inner appressorium wall, forming a semi-permeable barrier that retains glycerol while allowing water influx, generating the 8.0 MPa physical piercing force.",
                        "Acervulus in Anthracnose: Sub-epidermal saucer-shaped fungal fruiting body erupting through the host epidermis, bearing short conidiophores, pink mucilaginous conidial masses, and dark protective setae spines."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "Preventive vs. Curative Late Blight Fungicide Spray Scheduling: Monitor relative humidity and rainfall. Prior to rain events (preventive window), spray multi-site contact protectants (Mancozeb 80% WP @ 2.5 kg/ha or Copper Hydroxide). If disease symptoms already appear or wet periods exceed 48 hours (curative window), apply systemic translaminar oomycete chemistries (Cymoxanil + Mancozeb, or Metalaxyl-M + Mancozeb, or Fluopicolide) within 24–48 hours of infection.",
                        "Microscopic Identification of Fungal Spores: Scrape sporulating margin of leaf lesion onto a glass slide. Mount in a drop of lactophenol cotton blue stain. Examine under 400x magnification: identify Phytophthora by lemon-shaped papillate sporangia; Alternaria by dark brown, beaked, multicellular muriform conidia with transverse and longitudinal septa; Powdery mildew by barrel-shaped catenulate conidia (Oidium type)."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Late Blight Water-Soaked Lesions: Dark brown to black lesions with pale green water-soaked margins, accompanied by delicate white cottony fungal sporulation on the underside of leaves during morning dew.",
                        "Powdery Mildew White Talcum-Powder Coating: Superficial white powdery fungal patches covering upper leaf surfaces, causing leaf distortion and premature senescence under warm, shady canopy microclimates."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: An agronomist scouts a 4.0-hectare potato field and detects early lesions of Late Blight (Phytophthora infestans) following 3 days of rainy weather. The agronomist prescribes Ridomil Gold MZ 68 WG (4% Mefenoxam + 64% Mancozeb) applied at a rate of 2.50 kg of commercial product per hectare. Calculate: 1) Total mass of commercial product required for the 4 hectares; 2) The mass of systemic Mefenoxam active ingredient applied per hectare; and 3) The mass of contact Mancozeb active ingredient applied per hectare.\nSolution:\n1) Total commercial product = 4.0 ha x 2.50 kg/ha = 10.0 kg Ridomil Gold MZ.\n2) Systemic Mefenoxam applied/ha = 2.50 kg x (4.0% / 100) = 0.10 kg a.i./ha (100g/ha).\n3) Contact Mancozeb applied/ha = 2.50 kg x (64.0% / 100) = 1.60 kg a.i./ha (1,600g/ha).\nAgronomic Rationale: Tank-mixing a low-dose systemic penetrant (mefenoxam) with a high-dose multi-site contact protectant (mancozeb) provides curative efficacy while suppressing the selection of mefenoxam-resistant strains."
                    ),
                    commonExamQuestions = listOf(
                        "Explain the biochemical, structural, and evolutionary differences between Oomycota (Kingdom Stramenopila) and True Fungi (Kingdom Fungi).",
                        "List the five spore stages in the macrocyclic heteroecious life cycle of cereal stem rust (Puccinia graminis) and state the host plant for each stage.",
                        "Explain how the fungal appressorium generates turgor pressures exceeding 8 MPa to penetrate intact plant cuticle."
                    ),
                    practicalTakeaway = "Always spray protective contact fungicides (mancozeb/copper) before rain forecasts: contact fungicides form an external shield that kills germinating fungal spores, but cannot cure infections once hyphae enter leaf tissue.",
                    authoritativeTextbookRef = "Plant Pathology (Agrios, Ch. 11); Introduction to Fungi (Webster & Weber)"
                ),
                CurriculumSubtopic(
                    id = "cp_nematology",
                    code = "iii",
                    title = "Plant Nematology",
                    coreDefinition = "The morphology, feeding behavior, and disease complexes of plant-parasitic microscopic roundworms equipped with sclerotized, hollow, protrusible stylets used to puncture and parasitize plant root cells.",
                    keyMechanismsAndFormulas = listOf(
                        "Plant-Parasitic Nematode Diagnostic Stylet: All plant-parasitic nematodes possess an oral, hollow, protrusible spear (stylet / stomatostylet / odontostylet) equipped with basal knobs that inject glandular esophageal secretions into host cells and ingest liquefied cytoplasm.",
                        "Root-Knot Nematode (Meloidogyne spp.) Giant Cell Induction: Infective second-stage juveniles (J2) penetrate root elongation zone, migrate intercellularly to the vascular cylinder, and inject pharyngeal secretions that induce 5 to 7 cortical parenchyma cells to undergo repeated nuclear mitosis without cytokinesis, forming hypertrophied, multinucleate, metabolically active 'Giant Cells' (nurse cells) surrounded by galling/knotting.",
                        "Cyst Nematodes (Heterodera / Globodera): Sedentary endoparasites; lemon-shaped or spherical adult females die and their cuticles undergo polyphenol oxidase tanning into a tough, brown, resistant cyst containing 200–500 viable eggs, surviving in soil for over 15–20 years in the absence of a host."
                    ),
                    detailedElaboration = "Plant-parasitic nematodes are microscopic roundworms (0.3–3.0 mm long) inhabiting soil pore water films. They cause an estimated $100+ billion in global crop losses annually, frequently misdiagnosed as nutrient deficiencies or drought stress.\n\nFeeding Types & Guilds:\n1) Sedentary Endoparasites: Enter root tissue completely and establish permanent feeding sites without moving (e.g., Root-Knot Nematodes - Meloidogyne incognita, M. javanica; Cyst Nematodes - Globodera rostochiensis / Potato Cyst Nematode, Heterodera glycines / Soybean Cyst Nematode).\n2) Migratory Endoparasites: Move through root cortical tissues while feeding, destroying cell walls and creating dark necrotic cavities (e.g., Root-Lesion Nematodes - Pratylenchus penetrans; Burrowing Nematodes - Radopholus similis in banana roots causing toppling disease).\n3) Ectoparasites: Remain outside in bulk soil and insert long stylets into outer root epidermal and cortical cells (e.g., Dagger Nematodes - Xiphinema, which also vector plant nepoviruses like Tobacco Ringspot Virus; Stubby-Root Nematodes - Trichodorus, vectoring Tobacco Rattle Virus).\n\nDisease Complexes: Nematode feeding wounds destroy the physical integrity of root periderm and endodermis, leaking carbohydrates and amino acids that attract soil-borne fungal pathogens. In the classic Fusarium-Meloidogyne wilt complex in tomatoes and cotton, fungal wilt incidence spikes tenfold in the presence of root-knot nematodes, and Fusarium-resistant cultivars completely lose their genetic resistance.",
                    biologicalAndChemicalDetails = listOf(
                        "Biofumigation via Glucosinolate Hydrolysis: Soil incorporation of chopped brassica cover crops (e.g., Indian mustard - Brassica juncea) releases glucosinolates (sinigrin) hydrolyzed by endogenous myrosinase enzymes in the presence of water into volatile allyl isothiocyanates (AITC), a natural chemical biofumigant that suppresses nematode J2 juveniles.",
                        "Biological Nematicides: Purpureocillium lilacinum (formerly Paecilomyces lilacinus) and Pochonia chlamydosporia produce subtilisin-like serine proteases and chitinases that penetrate and destroy nematode eggshells and juvenile cuticles."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "Baermann Funnel Nematode Extraction Protocol: Place a fresh 50g composite root or soil sample onto a paper filter tissue supported by a wire mesh screen inside a glass funnel filled with clean water. Clamp the rubber tubing at the funnel bottom. Living nematodes actively migrate through the wet paper mesh and settle by gravity into the clamped tubing neck. After 24 to 48 hours, draw off 15 mL of water into a counting dish and enumerate active nematodes under a stereomicroscope at 40x magnification.",
                        "Root-Knot Gall Index Scoring (Bridge & Page 0–10 Scale): Excavate 10 representative crop root systems. Score root galling: 0 = Clean roots, no galls; 2 = Few small galls on lateral roots; 5 = 50% of root system knotted, stunted lateral roots; 8 = All roots heavily galled, clubbed, no fine root hairs; 10 = Completely knotted and rotting root ball, dead plant."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Root Galls / Knots: Distinct irregular swollen swellings, beads, or clubs along root axes containing glistening pearly-white spherical female nematodes embedded within root cortex.",
                        "Midday Reversible Wilting: Affected fields display circular or irregular patches of severely stunted plants that wilt during hot afternoon hours despite adequate soil moisture, caused by root vascular disruption."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: A potato farmer suspects Potato Cyst Nematode (PCN - Globodera rostochiensis) in a 2.0-hectare field. A soil test laboratory extracts 45 viable PCN cysts per 100g of dry soil, with an average of 180 viable eggs per cyst. Economic damage thresholds dictate that yield losses commence when egg density exceeds 5 eggs per gram of soil. 1) Calculate the current PCN egg density (eggs/gram soil); and 2) Evaluate if this density will cause economic yield loss.\nSolution:\n1) Total eggs in 100g soil = 45 cysts x 180 eggs/cyst = 8,100 eggs per 100g soil.\nEgg density per gram of soil = 8,100 eggs / 100g = 81.0 eggs/gram soil.\n2) Evaluation: The current egg density (81 eggs/g) is over 16 times higher than the economic damage threshold (5 eggs/g). Without management (nematicides, resistant cultivars like Innovator, or long 6-year rotations), the crop will suffer catastrophic yield collapse exceeding 60–80%."
                    ),
                    commonExamQuestions = listOf(
                        "Explain how Root-Knot Nematodes (Meloidogyne spp.) induce and maintain multinucleate 'Giant Cells' in host vascular cylinders.",
                        "Distinguish migratory endoparasitic nematodes from sedentary endoparasitic nematodes with two crop examples of each.",
                        "Describe the biochemical mechanism of biofumigation using brassica cover crops for soil-borne nematode suppression."
                    ),
                    practicalTakeaway = "Rotate solanaceous crops with non-host Sunn Hemp (Crotalaria juncea) or African Marigolds (Tagetes erecta): Tagetes root exudates contain α-terthienyl, an allelopathic thiophene that is photoactivated by light to kill root-knot nematode juveniles.",
                    authoritativeTextbookRef = "Plant Parasitic Nematodes in Subtropical and Tropical Agriculture (Luc, Sikora & Bridge); Plant Pathology (Agrios, Ch. 15)"
                ),
                CurriculumSubtopic(
                    id = "cp_virology",
                    code = "iv",
                    title = "General Plant Virology",
                    coreDefinition = "The molecular architecture, replication strategies, insect vector transmission mechanisms, and diagnostics of submicroscopic obligate intracellular nucleoprotein viral complexes.",
                    keyMechanismsAndFormulas = listOf(
                        "Viral Genome Architecture: Majority of plant viruses possess positive-sense single-stranded RNA (+ssRNA, e.g., Tobacco Mosaic Virus - TMV, Potato Virus Y - PVY, Cucumber Mosaic Virus - CMV); circular single-stranded DNA (ssDNA, Geminiviridae e.g., Tomato Yellow Leaf Curl Virus - TYLCV); or segmented negative-sense RNA (Tospoviruses e.g., Tomato Spotted Wilt Virus - TSWV).",
                        "Non-Persistent (Stylet-Borne) vs. Persistent (Circulative) Transmission: 1) Non-Persistent (e.g., PVY, CMV by Aphids): Acquired by insect stylet within seconds of probing epidermal cells; transmitted immediately to next plant; virions retained on cuticular stylet lining without latent period; viruliferous state lost upon molting. 2) Persistent Circulative (e.g., TYLCV by Bemisia tabaci Whiteflies): Requires hours of phloem ingestion; virions cross insect gut wall into hemolymph, circulate to salivary glands (latent period 8–24 hrs); transmitted via saliva during feeding for days or lifetime.",
                        "Systemic Plant Movement: Viral movement proteins (MP) bind viral RNA and expand plasmodesmata size exclusion limits (SEL) from 1 kDa to >20 kDa, facilitating cell-to-cell transit to phloem sieve elements for rapid source-to-sink translocation."
                    ),
                    detailedElaboration = "Plant viruses are obligate molecular parasites that subvert host cellular machinery to replicate.\n\nTransmission Dynamics & Vector Relationships: Because plant cells possess rigid cellulosic cell walls, plant viruses cannot enter cells via receptor-mediated endocytosis. They depend exclusively on mechanical wounds created by vectors (piercing-sucking insects, fungi, nematodes) or human vegetative propagation.\n\nFailure of Insecticides to Stop Non-Persistent Viruses: In non-persistent stylet-borne viruses (e.g., Potato Virus Y transmitted by aphids), an aphid lands on a crop, conducts a brief 10–30 second test probe of epidermal cells to test plant palatability, transmits the virus, and flies away. Synthetic insecticides (e.g., pyrethroids, organophosphates) take 15 to 60 minutes to kill the aphid; in fact, insecticide-induced nervous excitation often causes the dying aphid to probe multiple adjacent plants, accelerating viral epidemic spread. Management requires reflective silver plastic mulches (which confuse incoming flying aphids via polarized light reflection) or stylet-blocking mineral crop oils.\n\nDiagnostic Assays: 1) DAS-ELISA (Double Antibody Sandwich Enzyme-Linked Immunosorbent Assay): Utilizes monoclonal antibodies coated on microtiter plates; alkaline phosphatase enzyme conjugates yield yellow p-nitrophenol color proportional to viral antigen titer; 2) Reverse Transcription Polymerase Chain Reaction (RT-PCR): Synthesizes cDNA from viral RNA using reverse transcriptase, followed by exponential PCR amplification of specific coat protein gene fragments.",
                    biologicalAndChemicalDetails = listOf(
                        "RNA Interference (RNAi) Antiviral Defense: Host Dicer-Like (DCL) endonucleases recognize viral double-stranded RNA (dsRNA) replication intermediates and cleave them into 21–24 nucleotide small interfering RNAs (siRNAs). siRNAs are loaded into the RNA-Induced Silencing Complex (RISC) containing Argonaute (AGO) to specifically slice and destroy matching viral mRNA. Viruses counter-attack by expressing Viral Suppressors of RNA Silencing (VSR e.g., HC-Pro in potyviruses, p19 in tombusviruses).",
                        "Tobacco Mosaic Virus (TMV) Mechanical Durability: Rigid rod-shaped helical particle (300 x 18 nm) containing 2,130 identical coat protein subunits protecting a single 6.4-kb +ssRNA. Incredibly stable: withstands heating to 93°C, survives in dry tobacco leaves for decades, and spreads via workers' hands and smoking."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "Rapid Field Lateral Flow Strip (Immunostrip) Assay for TSWV/TMV: Grind 0.5g of symptomatic leaf tissue in an extraction buffer bag with a blunt pen. Insert the test strip vertically into the lysate for 5 to 10 minutes. Gold-conjugated antibodies bind viral antigen and migrate along nitrocellulose membrane by capillary action: one control line (C) indicates valid negative test; two lines (Control + Test) confirms positive viral infection.",
                        "Sanitation Protocol for Tobacco Mosaic Virus (TMV) in Greenhouse Solanaceae: Prohibit all workers from smoking or handling cured tobacco products prior to entering greenhouses. Require hand washing in 20% non-fat dry milk solution or 10% trisodium phosphate (TSP): milk proteins bind and inactivate TMV virions on skin, preventing contact transmission during tomato pruning and trellising."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Tomato Yellow Leaf Curl Virus (TYLCV): Severe upward cupping and curling of leaf margins, bright interveinal yellowing, thick leathery texture, extreme plant stunting, and complete abortion of flower blossoms, transmitted by sweetpotato whiteflies (Bemisia tabaci).",
                        "Maize Lethal Necrosis (MLN): Synergistic devastating co-infection of Maize Chlorotic Mottle Virus (MCMV, chrysomelid beetle-borne machlomovirus) and Sugarcane Mosaic Virus (SCMV, aphid-borne potyvirus), causing diagnostic bright yellow chlorotic mottling, dead heart necrosis, premature plant death, and barren deformed ears."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: An agronomist designs an aphid management strategy to control Potato Virus Y (PVY, a non-persistent virus) in a 5.0-hectare seed potato field. Field trials show that applying a synthetic pyrethroid insecticide alone reduces aphid populations by 92% but increases PVY incidence by 14% due to hyperactive probe flights. Adding a paraffinic mineral crop oil (applied at 1.0% concentration in 200 L/ha water) coats the leaf cuticle, cleaning aphid stylets and reducing PVY transmission by 75%. Calculate the total volume of commercial mineral crop oil (in Liters) required per weekly spray application for the 5 hectares.\nSolution:\n1) Total spray volume per hectare = 200 Liters/ha.\nFor 5.0 hectares = 5.0 ha x 200 L/ha = 1,000 Liters of spray mixture.\n2) Mineral crop oil concentration = 1.0% v/v.\nVolume of oil required = 1,000 L x (1.0 / 100) = 10.0 Liters of mineral crop oil per weekly application."
                    ),
                    commonExamQuestions = listOf(
                        "Contrast non-persistent (stylet-borne) viral transmission with persistent circulative transmission with respect to acquisition time, inoculation time, and vector retention.",
                        "Explain why chemical insecticide sprays frequently fail to stop the spread of non-persistent plant viruses in field crops.",
                        "Describe the mechanism of plant antiviral defense via RNA interference (RNAi) and explain how viral suppressors of silencing (VSR) counter this defense."
                    ),
                    practicalTakeaway = "To control non-persistent aphid-transmitted viruses (PVY, CMV), apply mineral crop oils and reflective silver mulches rather than contact insecticides: insecticides kill aphids too slowly to prevent inoculation during 20-second feeding probes.",
                    authoritativeTextbookRef = "Plant Pathology (Agrios, Ch. 14); Plant Virology (Hull, 5th Ed.)"
                )
            ),
            examFocusPoints = listOf(
                "Diagnostic bacterial streaming test and Type III Secretion Systems (T3SS)",
                "Oomycetes (cellulose, diploid, zoospores) vs. True Fungi (chitin, haploid/dikaryotic)",
                "Appressorium glycerol turgor mechanics (>8 MPa) and Late Blight epidemiology",
                "Root-knot nematode Meloidogyne giant cell induction and biofumigation chemistry",
                "Non-persistent vs. persistent circulative viral transmission and RNAi antiviral defense"
            ),
            practicalFieldApplications = listOf(
                "Performing field bacterial ooze tests in water to distinguish Ralstonia from Fusarium",
                "Operating lateral flow immunostrip test kits for rapid tomato virus detection",
                "Counting root galls using the Bridge & Page 0–10 scoring scale"
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
                    coreDefinition = "The functional morphology, taxonomy, life cycle bioenergetics, and population ecology of hexapod arthropods, focusing on quantitative economic decision thresholds and integrated pest management (IPM).",
                    keyMechanismsAndFormulas = listOf(
                        "Economic Injury Level (EIL) Formula (Pedigo et al.): EIL = C / (V x I x D x K), where C is cost of management ($/ha), V is market value of crop ($/kg), I is injury per pest density (injury/insect), D is damage per unit injury (yield loss/injury), and K is proportional reduction in pest population achieved by the treatment.",
                        "Economic Threshold (ET) / Action Threshold: The pest density at which management action must be initiated to prevent an increasing pest population from reaching the Economic Injury Level (ET is always lower than EIL: ET < EIL).",
                        "Metamorphosis Classifications: 1) Ametabolous (no change in form; silverfish); 2) Hemimetabolous (incomplete/gradual: Egg -> Nymph -> Adult; nymphs resemble adults but lack wings/genitalia e.g., Hemiptera, Orthoptera); 3) Holometabolous (complete metamorphosis: Egg -> Larva -> Pupa -> Adult; internal wing buds, distinct pupal restructuring stage e.g., Lepidoptera, Coleoptera, Diptera, Hymenoptera).",
                        "Degree-Day (Thermal Unit) Accumulation: Degree-Days (DD) = [(T_max + T_min) / 2] - T_base, where T_base is the minimum developmental threshold temperature below which physiological development ceases."
                    ),
                    detailedElaboration = "Entomology forms the technical foundation of crop protection.\n\nMouthpart Morphology & Pesticide Mode of Action Selection:\n1) Mandibulate (Chewing) Mouthparts: Strong heavily sclerotized toothed mandibles and maxillae adapted for biting, tearing, and chewing plant foliage, stems, and fruits (e.g., Lepidopteran larvae, Coleopteran adults and larvae, Orthopteran grasshoppers). Controlled effectively by stomach poisons, ingestible biologicals (Bt Cry proteins, spinosyns), and foliar diamides (chlorantraniliprole).\n2) Haustellate (Piercing-Sucking) Mouthparts: Mandibles and maxillae modified into slender, flexible stylets housed within a grooved labium rostrum. The stylets interlock to form two microscopic channels: a salivary channel (injecting digestive and anticoagulant enzymes) and a food channel (aspirating vascular phloem or xylem sap e.g., Hemiptera - aphids, whiteflies, planthoppers, stink bugs). Chewing stomach insecticides fail completely against piercing-sucking pests because they ingest exclusively internal vascular fluids without consuming the leaf surface. Control requires systemic translaminar chemistries (neonicotinoids, sulfoxaflor, flonicamid, spirotetramat).\n\nMajor Economic Orders:\n- Lepidoptera (Butterflies & Moths): Adults possess coiled siphoning proboscis; larvae (caterpillars) possess chewing mouthparts and abdominal prolegs with crochets (Fall Armyworm - Spodoptera frugiperda, African Bollworm - Helicoverpa armigera, Diamondback Moth - Plutella xylostella, Tomato Leafminer - Tuta absoluta).\n- Coleoptera (Beetles & Weevils): Forewings modified into rigid protective elytra meeting in a straight mid-dorsal suture; chewing mouthparts in larvae and adults (Maize Weevil - Sitophilus zeamais, Colorado Potato Beetle, Flea Beetles).\n- Hemiptera (True Bugs & Sap-Suckers): Piercing-sucking beak arising from front or bottom of head; forewings often hemelytra (leathery basal half, membranous apical half).\n- Diptera (True Flies): Single pair of membranous flight wings; hindwings reduced to balancing halteres (Fruit flies - Bactrocera dorsalis, Leafminers - Liriomyza).\n- Thysanoptera (Thrips): Minute insects with asymmetrical rasping-sucking mouthparts (single left mandible) and fringed wings with long hair setae.",
                    biologicalAndChemicalDetails = listOf(
                        "IRAC Insecticide Mode of Action Groups: Group 1 (Acetylcholinesterase inhibitors: OP/Carbamates); Group 3 (Sodium channel modulators: Pyrethroids); Group 4 (Nicotinic acetylcholine receptor competitive modulators: Neonicotinoids); Group 5 (nAChR allosteric modulators: Spinosyns); Group 6 (Glutamate-gated chloride channel allosteric modulators: Avermectins); Group 28 (Ryanodine receptor modulators: Diamides).",
                        "Juvenile Hormone (JH) vs. Ecdysone: Prothoracicotropic hormone (PTTH) stimulates prothoracic glands to secrete Ecdysone (molting hormone). If Juvenile Hormone (from corpora allata) is high, molt yields another larval instar; when JH drops to zero, ecdysone triggers pupation."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "Fall Armyworm (FAW - Spodoptera frugiperda) Field Scouting & Scouting Protocol: Walk field in a W-pattern, inspecting 20 consecutive maize plants at 5 distinct locations (100 plants total). Examine whorls and leaf funnels. Record plants with characteristic 'window-pane' feeding and moist sawdust-like frass. Action Threshold: Initiate treatment if ≥20% of plants show active larval infestation in early vegetative stage (V1–V6), or ≥40% in late vegetative stage (V7–V12). Apply targeted sprays (Bt, chlorantraniliprole, emamectin benzoate) directly into the whorl cone using a coarse pencil-stream nozzle.",
                        "Pheromone Delta Trap Monitoring: Assemble triangular plastic delta trap with replaceable sticky bottom insert. Suspend rubber septum dispenser impregnated with female sex pheromone lure (e.g., tuta-lure for Tuta absoluta) 1.0m above crop canopy. Inspect sticky insert twice weekly: catch >30 moths/trap/week indicates imminent egg-laying surge, triggering release of Trichogramma parasitoids or bio-insecticide sprays."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Sawdust-Like Whorl Frass: Heavy accumulations of coarse, wet, yellowish-brown fecal frass deep within the central leaf whorl accompanied by ragged, shredded leaf tears, diagnostic of Fall Armyworm larvae.",
                        "Leaf Window-Paning: Translucent papery epidermal membranes remaining after young 1st and 2nd instar lepidopteran larvae scrape away green parenchyma without piercing the opposite epidermis.",
                        "Sooty Mold Growth: Superficial black velvety mold covering leaves and fruit, developing on sugary honeydew excreted by dense colonies of aphids, whiteflies, or mealybugs."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: An agronomist calculates the Economic Injury Level (EIL) for African Bollworm (Helicoverpa armigera) on commercial green beans. Cost of management (C) is $80/ha. Market value of beans (V) is $1.20/kg. Research shows that each bollworm larva per square meter causes an average yield loss of 15 kg/ha of pods (Injury x Damage, I x D = 15 kg/ha/larva). The insecticide spray has an efficacy rating of 90% (K = 0.90). Calculate the Economic Injury Level (EIL) in larvae per square meter.\nSolution:\n1) Formula: EIL = C / (V x [I x D] x K).\n2) Substitute values: C = 80; V = 1.20; (I x D) = 15; K = 0.90.\nDenominator = 1.20 x 15 x 0.90 = 16.20 $/ha per larva/m².\n3) EIL = 80 / 16.20 = 4.94 larvae/m².\nConclusion: The Economic Injury Level is approximately 5 larvae per square meter. The Economic Threshold (ET) should be set at approximately 3.0 to 3.5 larvae/m² to allow lead time for insecticide application before economic damage equals the cost of treatment."
                    ),
                    commonExamQuestions = listOf(
                        "Derive the variables of the Economic Injury Level (EIL) equation (C / [V x I x D x K]) and explain the practical operational difference between EIL and Economic Threshold (ET).",
                        "Compare complete (holometabolous) metamorphosis with incomplete (hemimetabolous) metamorphosis, listing three economically important agricultural pest orders for each.",
                        "Explain why chemical contact pyrethroid sprays fail against piercing-sucking aphids while systemic neonicotinoids succeed."
                    ),
                    practicalTakeaway = "Never apply synthetic insecticides when pest populations are below the Economic Threshold: doing so wastes money, destroys natural predators (ladybird beetles, hoverfly larvae), and triggers rapid secondary pest resurgences.",
                    authoritativeTextbookRef = "Entomology and Pest Management (Pedigo & Rice, Ch. 3, 7 & 13); Borror and DeLong's Introduction to the Study of Insects (Triplehorn & Johnson)"
                )
            ),
            examFocusPoints = listOf(
                "Economic Injury Level (EIL) and Economic Threshold (ET) mathematical derivations",
                "Mouthpart morphology (mandibulate vs. haustellate) and insecticide class selection",
                "Holometabolous vs. Hemimetabolous insect developmental orders",
                "IRAC Mode of Action classification and rotational resistance stewardship",
                "Degree-day thermal unit modeling for insect emergence forecasting"
            ),
            practicalFieldApplications = listOf(
                "Conducting 100-plant W-pattern scouting for Fall Armyworm whorl damage",
                "Assembling and servicing delta pheromone traps for pest monitoring"
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
                    coreDefinition = "The ecological biology of competitive vegetation interfering with agricultural crops, alongside herbicide physiological modes of action, absorption kinetics, and resistance management.",
                    keyMechanismsAndFormulas = listOf(
                        "Critical Period of Weed Control (CPWC): The specific window in crop ontogeny during which weeds must be prevented from competing to avoid unacceptable economic yield loss (>5%). Spans from the 'critical time of weed removal' (CTWR) to the 'critical weed-free period' (CWFP), typically weeks 2 through 6 in maize and cereals.",
                        "Botanical Weed Classes: 1) Grasses (Poaceae: round or flat hollow stems with nodes, parallel leaf venation, ligules and auricles e.g., Digitaria horizontalis, Eleusine indica); 2) Sedges (Cyperaceae: solid triangular stems without nodes, 3-ranked leaves, 'Sedges have edges' e.g., Cyperus rotundus / Purple Nutsedge); 3) Broadleaves (Dicotyledoneae: net venation, taproots, epigeal or hypogeal cotyledons e.g., Amaranthus hybridus, Galinsoga parviflora, Datura stramonium).",
                        "Herbicide Resistance Action Committee (HRAC) Groups: Group 9 (EPSPS inhibitor - Glyphosate); Group 4 (Synthetic Auxins - 2,4-D, Dicamba); Group 2 (ALS/AHAS inhibitors - Sulfonylureas, Imidazolinones); Group 1 (ACCase inhibitors - Clethodim, Fluazifop); Group 5 (Photosystem II inhibitors - Atrazine); Group 10 (Glutamine synthetase inhibitor - Glufosinate)."
                    ),
                    detailedElaboration = "Weeds possess remarkable evolutionary adaptations including explosive fecundity, polymorphic seed dormancy, and allelopathic chemical interference.\n\nPurple Nutsedge (Cyperus rotundus) - The World's Worst Weed: Propagates primarily through extensive subterranean chains of tubers connected by rhizomes. A single tuber can produce over 2,000 tubers within a single season, withstanding conventional cultivation which merely severs rhizome chains and multiplies dormant tuber buds. Management requires systemic translocated herbicides (e.g., halosulfuron-methyl) applied when foliage has 4–6 leaves to translocate active ingredient deep into basal bulbs.\n\nParasitic Weeds (Striga hermonthica - Witchweed): An obligate root hemiparasite of cereals. Dormant seeds remain viable in soil for >15 years. Seed germination is triggered exclusively by host root exudation of strigolactones. Upon germination, the radicle differentiates into a specialized haustorium that penetrates the host root cortex, taps directly into host xylem vessels, and withdraws water, carbohydrates, and amino acids while secreting phytotoxic enzymes that dwarf the host crop before Striga even emerges above ground.\n\nHerbicide Resistance Mechanisms: 1) Target-Site Resistance (TSR): Point mutations in the gene encoding the target enzyme (e.g., Pro197 substitution in ALS enzyme conferring 1,000-fold resistance to sulfonylureas), preventing herbicide binding; 2) Non-Target-Site Resistance (NTSR / Metabolic Resistance): Enhanced metabolic detoxification mediated by Cytochrome P450 monooxygenases and Glutathione S-Transferases (GST), conferring broad cross-resistance across chemically unrelated herbicide modes of action.",
                    biologicalAndChemicalDetails = listOf(
                        "Glyphosate Mode of Action (HRAC Group 9): Specifically inhibits 5-enolpyruvylshikimate-3-phosphate (EPSPS) synthase in the shikimic acid pathway, blocking the biosynthesis of essential aromatic amino acids (Phenylalanine, Tyrosine, Tryptophan); causes accumulation of shikimate and rapid plant death.",
                        "Synthetic Auxin Mode of Action (HRAC Group 4): 2,4-D mimics indole-3-acetic acid (IAA), binding to TIR1/AFB receptors and triggering uncontrolled cellular elongation, epinasty (stem twisting), vascular phloem crushing, and hydrogen peroxide accumulation."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "Herbicide Tank Mixing Order (WALES / DALES Rule): When mixing multiple formulations in spray tanks, follow the universal WALES sequence to prevent chemical curdling: 1) W = Wettable Powders (WP) and Water-Dispersible Granules (WDG/WG); 2) A = Agitate thoroughly until completely dissolved; 3) L = Liquid flowables and Suspensions (SC/F); 4) E = Emulsifiable Concentrates (EC); 5) S = Surfactants and Water-Soluble Concentrates (SL).",
                        "Pre-Emergence Herbicide Soil Application Protocol: Apply pre-emergence herbicides (e.g., Atrazine + S-Metolachlor) within 48 hours after crop planting onto a smooth, clod-free seedbed. Moisture is critical: herbicides require 10–15 mm of rain or irrigation within 7 days of application to dissolve and activate the chemical barrier in the top 2–3 cm of soil where weed seeds germinate."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Synthetic Auxin Herbicide Injury (2,4-D / Dicamba Drift): Severe downward epinasty, twisting of stems, fiddle-neck bending of petioles, strap-like leaves with parallel veins in broadleaf crops (tomatoes, cotton) exposed to volatile vapor drift.",
                        "Photosystem II Inhibitor Injury (Atrazine): Interveinal chlorosis and necrotic scorch beginning at margins and tips of older leaves, while veins remain temporarily green."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: An agronomist prescribes a pre-emergence herbicide treatment of Atrazine 500 SC (Suspension Concentrate, containing 500 g/L active ingredient) for a 3.5-hectare commercial maize field. The recommended application rate is 1.50 kg of active ingredient (a.i.) per hectare. Calculate: 1) The volume of commercial Atrazine 500 SC product required per hectare (in Liters); and 2) The total volume of commercial herbicide product needed for the entire 3.5 hectares.\nSolution:\n1) Formulation contains 500 g/L = 0.50 kg a.i. per Liter.\nVolume per hectare = Prescribed Dose (kg a.i./ha) / Concentration (kg a.i./L) = 1.50 kg / 0.50 kg/L = 3.0 Liters/ha.\n2) Total volume for 3.5 hectares = 3.5 ha x 3.0 L/ha = 10.50 Liters of Atrazine 500 SC."
                    ),
                    commonExamQuestions = listOf(
                        "Define the Critical Period of Weed Control (CPWC) and explain how identifying it maximizes economic return and reduces herbicide usage.",
                        "Differentiate Grasses, Sedges, and Broadleaves based on stem morphology and leaf venation.",
                        "Explain the biochemical mode of action of Glyphosate (HRAC Group 9) and detail the differences between target-site and metabolic herbicide resistance."
                    ),
                    practicalTakeaway = "Maintain clean weed-free fields during the Critical Period of Weed Control (the first 30–40 days after crop emergence): weeds emerging after canopy closure rarely reduce yield.",
                    authoritativeTextbookRef = "Applied Weed Science: Including the Ecology and Management of Invasive Plants (Ross & Lembi, Ch. 3, 5 & 7); Weed Science: Principles and Practices (Monaco, Weller & Ashton)"
                )
            ),
            examFocusPoints = listOf(
                "Critical Period of Weed Control (CPWC) concept and crop yield loss models",
                "Botanical differences: Poaceae (round/hollow) vs. Cyperaceae (triangular/solid)",
                "Herbicide Modes of Action (HRAC Groups 1, 2, 4, 5, 9, 10)",
                "WALES tank mixing formulation sequence rules",
                "Striga hermonthica hemiparasitic biology and suicidal germination triggers"
            ),
            practicalFieldApplications = listOf(
                "Identifying Cyperus rotundus tubers and vegetative sedge morphology in the field",
                "Calibrating boom sprayers for pre-emergence soil-residual herbicide barriers"
            ),
            recommendedTextbookIds = listOf("book_ross_lembi_applied_weed_sci", "book_matthews_pesticide_application")
        ),
        CurriculumTopic(
            id = "crop_prot_disease_management",
            pillar = CurriculumPillarType.CROP_PROTECTION,
            code = "4.d",
            title = "Management of Plant Diseases",
            summary = "The classic disease triangle and disease tetrahedron, principles of plant disease management (exclusion, eradication, protection, resistance, therapy), and Fungicide Resistance Action Committee (FRAC) rotation.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "cp_disease_triangle_frac",
                    code = "i",
                    title = "Disease Triangle, Plant Defense & FRAC Rotation",
                    coreDefinition = "The comprehensive integration of regulatory exclusion, inoculum eradication, physical protection, genetic resistance, and rotational fungicides to suppress disease epidemics below economic injury levels.",
                    keyMechanismsAndFormulas = listOf(
                        "The Disease Triangle & Tetrahedron: Infectious plant disease requires three simultaneous conditions: Susceptible Host + Virulent Pathogen + Conducive Environment. Adding Time as the 4th axis creates the Disease Tetrahedron, measuring the rate of epidemic development (van der Plank apparent infection rate, r).",
                        "Whetzel's 5 Core Principles of Plant Disease Management: 1) Exclusion (quarantine, certified clean seed, preventing pathogen entry into uninfected areas); 2) Eradication (destroying or reducing established inoculum e.g., crop residue burning, roguing, crop rotation); 3) Protection (placing chemical or physical barrier between host and pathogen e.g., contact fungicides); 4) Resistance (breeding genetically resistant host cultivars); 5) Therapy (curing an already infected plant e.g., thermotherapy or systemic antibiotics).",
                        "Fungicide Resistance Action Committee (FRAC) Codes: FRAC 11 (QoI / Strobilurins e.g., Azoxystrobin; inhibits mitochondrial respiration at Complex III; high resistance risk via single G143A gene mutation); FRAC 3 (DMI / Triazoles e.g., Difenoconazole; inhibits sterol 14α-demethylase; medium resistance risk); FRAC 7 (SDHI e.g., Boscalid; inhibits succinate dehydrogenase; medium-high risk); FRAC M (Multi-Site Contact Fungicides e.g., Mancozeb, Chlorothalonil, Copper; low resistance risk; no known fungal resistance)."
                    ),
                    detailedElaboration = "Integrated disease management requires moving away from single-chemical reliance toward multi-tactic biological and cultural systems.\n\nHost Plant Resistance Mechanics: Genetic resistance is divided into:\n1) Vertical (Qualitative) Resistance: Governed by single major R-genes (Flor's Gene-for-Gene concept: host R-gene product recognizes pathogen Avr-gene product). Confers complete hypersensitive immunity. However, vertical resistance is genetically fragile: a single virulent point mutation in the pathogen's Avr gene overcomes the R-gene, causing catastrophic resistance breakdown ('boom-and-bust cycle' in cereal rusts).\n2) Horizontal (Quantitative) Resistance: Governed by multiple minor genes (polygenic). Does not prevent infection, but slows epidemic development by reducing sporulation rate, extending latent period, and reducing lesion size. Highly durable and cannot be easily overcome by single pathogen mutations.\n\nSystemic Acquired Resistance (SAR) vs. Induced Systemic Resistance (ISR): SAR is induced systemically throughout the plant following a localized necrotizing infection or chemical elicitor application (e.g., acibenzolar-S-methyl, salicylic acid). Salicylic acid activates the NPR1 regulatory protein, translocating it to the nucleus to induce transcription of Pathogenesis-Related (PR) proteins (PR-1, β-1,3-glucanases, chitinases). In contrast, ISR is triggered by beneficial non-pathogenic rhizosphere bacteria (e.g., Pseudomonas fluorescens, Bacillus amyloliquefaciens) and is mediated by jasmonic acid (JA) and ethylene signaling pathways.\n\nFungicide Resistance Stewardship: Single-site fungicides (FRAC 1, 3, 7, 11) must never be applied consecutively or used alone. FRAC guidelines mandate: 1) Limiting single-site chemistries to maximum 2–3 applications per season; 2) Mandatory tank-mixing or block-alternating with multi-site contact protectants (FRAC M1 copper or FRAC M3 dithiocarbamate mancozeb).",
                    biologicalAndChemicalDetails = listOf(
                        "Flor's Gene-for-Gene Model (H. H. Flor, 1942): For each gene conditioning resistance in the host, there is a corresponding specific gene conditioning avirulence in the pathogen. Disease resistance (hypersensitive response) occurs ONLY when host possesses dominant R-allele AND pathogen possesses matching dominant Avr-allele; any other combination (R + avr, r + Avr, r + avr) results in compatible disease.",
                        "G143A Mutation in Strobilurin Resistance: Single nucleotide point mutation replacing glycine with alanine at position 143 in cytochrome b gene; completely prevents strobilurin binding to the Qo site of cytochrome bc1 complex."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "Designing an Anti-Resistance Fungicide Rotation Calendar for Export French Beans: Week 1 (Vegetative): Spray Mancozeb 80% WP (FRAC M3, Multi-Site contact). Week 3 (Flowering): Tank-mix Azoxystrobin (FRAC 11, Strobilurin) + Chlorothalonil (FRAC M5). Week 5 (Pod Set): Switch to Difenoconazole (FRAC 3, Triazole) + Copper Hydroxide (FRAC M1). Week 7 (Harvesting window): Apply biological Bacillus subtilis (FRAC 44) to comply with Zero Maximum Residue Limits (MRL). Never apply FRAC 11 twice in succession.",
                        "Hot Water Seed Treatment Protocol for Bacterial Seed-Borne Diseases: Enclose vegetable seeds (e.g., tomato, cabbage) in a porous mesh bag. Pre-warm seeds in warm water (38°C) for 10 minutes. Transfer immediately into a constantly stirred water bath maintained at exactly 50°C for brassicas (or 52°C for tomatoes) for exactly 20 minutes (monitor with precision digital thermometer). Immediately immerse seeds into cold water to stop heat, then spread thinly to dry at room temperature. Destroys internal bacterial pathogens (Xanthomonas) without reducing germination."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Boom-and-Bust Resistant Cultivar Failure: Sudden 100% loss of resistance in a previously immune wheat or potato cultivar, accompanied by explosive regional epidemic development as a new virulent pathogen race emerges.",
                        "Fungicide Failure / Target-Site Resistance: Complete lack of disease suppression despite applying systemic fungicide at double label dosage under optimal weather conditions."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: An agronomist calculates the Van der Plank Apparent Infection Rate (r) for Late Blight in a susceptible potato field. On Day 30, disease severity (x1) is measured at 1.0% (x1 = 0.01). On Day 44 (14 days later), disease severity (x2) has exploded to 55.0% (x2 = 0.55). Using Van der Plank's logarithmic epidemic formula: r = [1 / (t2 - t1)] x [ln(x2 / (1 - x2)) - ln(x1 / (1 - x1))]. Calculate the apparent infection rate (r in units per day) and evaluate the epidemic speed.\nSolution:\n1) Time interval: Δt = 44 - 30 = 14 days.\n2) Term 1: x2 / (1 - x2) = 0.55 / (1 - 0.55) = 0.55 / 0.45 = 1.2222 -> ln(1.2222) = +0.2007.\n3) Term 2: x1 / (1 - x1) = 0.01 / (1 - 0.01) = 0.01 / 0.99 = 0.0101 -> ln(0.0101) = -4.5951.\n4) Difference = +0.2007 - (-4.5951) = +4.7958.\n5) Rate r = (1 / 14) x 4.7958 = 0.3426 per day.\nEvaluation: An infection rate of r = 0.34/day represents an explosive compound-interest polycyclic epidemic; disease severity doubles every 2.0 days, requiring emergency systemic fungicide interventions."
                    ),
                    commonExamQuestions = listOf(
                        "Explain Flor's Gene-for-Gene hypothesis and contrast vertical (qualitative) resistance with horizontal (quantitative) resistance.",
                        "Describe the biochemical signaling pathways of Systemic Acquired Resistance (SAR) versus Induced Systemic Resistance (ISR).",
                        "List the five principles of plant disease management established by Whetzel and give one concrete field example of each."
                    ),
                    practicalTakeaway = "Never apply single-site systemic fungicides (FRAC 1, 3, 7, 11) consecutively or alone: always tank-mix with multi-site contact protectants (mancozeb/copper) to protect single-site molecules from rapid fungal resistance mutations.",
                    authoritativeTextbookRef = "Plant Pathology (Agrios, Ch. 2, 4 & 5); Plant Disease Epidemics and Control (van der Plank)"
                )
            ),
            examFocusPoints = listOf(
                "Disease Triangle & Tetrahedron and van der Plank apparent infection rate (r)",
                "Whetzel's 5 principles: Exclusion, Eradication, Protection, Resistance, Therapy",
                "Flor's Gene-for-Gene model and vertical vs. horizontal resistance genetics",
                "Systemic Acquired Resistance (SAR) salicylic acid and PR-protein induction",
                "FRAC code anti-resistance rules and single-site vs. multi-site mechanics"
            ),
            practicalFieldApplications = listOf(
                "Designing anti-resistance fungicide rotation schedules across crop growth stages",
                "Performing hot-water seed sanitation at 50°C for vegetable seeds"
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
                    coreDefinition = "The mathematical calibrations and physical measurements required to deliver precise chemical pesticide doses to agricultural targets without phytotoxicity, chemical run-off, or under-dosing resistance.",
                    keyMechanismsAndFormulas = listOf(
                        "Standard Sprayer Application Volume Formula: Spray Volume (L/ha) = [Total Nozzle Output (L/min) x 600] / [Walking Speed (km/h) x Effective Swath Width (m)].",
                        "Walking Speed Formula: Speed (km/h) = [Distance (meters) / Time (seconds)] x 3.6.",
                        "Commercial Formulation from Active Ingredient: Quantity of Commercial Product (L or kg) = (Recommended a.i. Rate in kg/ha) / (% a.i. in formulation / 100).",
                        "Knapsack Tank Dosing Formula: Product per Knapsack Tank (mL or g) = [Recommended Field Dose per Hectare (L or kg) x Tank Capacity (L)] / Total Spray Volume per Hectare (L/ha).",
                        "Number of Knapsack Refills per Hectare: Refills/ha = Total Spray Volume (L/ha) / Tank Capacity (L).",
                        "Nozzle Wear Tolerance Rule: Replace nozzle tips if individual discharge rate deviates by >10% from the manufacturer's nominal catalog flow rate at standard pressure (typically 2.0–3.0 bar)."
                    ),
                    detailedElaboration = "Pesticide calculations represent the primary practical engineering skill of the professional field agronomist. Mathematical errors cause either under-dosing (failing to kill pests and selecting for resistant biotypes) or over-dosing (causing crop phytotoxicity, illegal export chemical residues, and environmental poisoning).\n\nStep-by-Step Sprayer Calibration Mechanics:\nTo calibrate a knapsack sprayer:\n1) Measure out a 50-meter test strip on field terrain.\n2) Fill knapsack half-full with clean water.\n3) Walk the 50m strip at a normal, comfortable, sustainable spraying pace while pumping to maintain operating pressure (2.0 bar / 30 PSI). Record time taken in seconds (e.g., 45 seconds). Calculate speed: (50m / 45s) x 3.6 = 4.0 km/h.\n4) Measure effective spray swath width on dry ground (e.g., 1.2 meters with a flat fan nozzle held at 50 cm height).\n5) Hold nozzle over a graduated cylinder and pump at constant operating pressure for exactly 60 seconds; measure volume in Liters (e.g., 0.80 L/min).\n6) Compute Volume Delivery: Spray Volume = (0.80 x 600) / (4.0 x 1.2) = 480 / 4.8 = 100 Liters/ha.\n\nFormulation Classifications:\n- EC (Emulsifiable Concentrate): Liquid active ingredient dissolved in petroleum solvent with emulsifiers; forms white milky emulsion when added to water.\n- WP (Wettable Powder): Insoluble technical powder milled with wetting agents; forms suspension requiring continuous mechanical agitation.\n- SC / FS (Suspension Concentrate): Solid active ingredient suspended in liquid carrier.\n- WDG / WG (Water-Dispersible Granules): Dustless dry granules that disperse into suspension upon dilution.",
                    biologicalAndChemicalDetails = listOf(
                        "Droplet Size & VMD (Volume Median Diameter): Fine sprays (VMD < 150 µm) provide excellent coverage for contact insecticides but suffer extreme wind drift; Coarse sprays (VMD 300–450 µm) minimize drift and are mandated for systemic herbicides (glyphosate, 2,4-D).",
                        "Water pH & Alkaline Hydrolysis: Many organophosphates (dimethoate, chlorpyrifos) and carbamates undergo rapid alkaline hydrolysis when mixed with hard or alkaline water (pH > 7.5), halving chemical efficacy in hours; require acidifying buffering agents (citric acid or ammonium sulfate) to maintain tank pH at 5.5–6.5."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "Knapsack Calibration Step-by-Step SOP: 1) Fill clean knapsack with exactly 10.0 Liters of water. 2) Spray a test area of 250 m² (e.g., 5m x 50m) at target walking speed and nozzle height. 3) Measure the remaining water in the tank; water sprayed = 10.0 L - Remaining. 4) Calculate Spray Volume (L/ha) = Water Sprayed (L) x (10,000 m² / 250 m²) = Water Sprayed x 40.",
                        "Sprayer Triple-Rinse Cleaning Protocol: Immediately after finishing spraying: 1) Empty residual chemical into designated waste pit; 2) Add 5L clean water, shake vigorously, and spray through boom for 2 minutes; 3) Add 5L water + 50g household washing soda (detergent), flush, and let soak 15 minutes; 4) Final rinse with clean water. Remove and clean nozzle tips with an old soft toothbrush (never use wire or pins which ruin nozzle orifices)."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Chemical Phytotoxicity (Over-dosing): Necrotic edge scorch, yellow halo spotting, leaf puckering, and necrotic flecking appearing 24–48 hours after application.",
                        "Streak Spraying (Uneven Boom Distribution): Alternating stripes of green surviving weeds and dead weeds across the field, caused by clogged nozzles, uneven nozzle heights, or worn tip fan patterns."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem 1: An agronomist is calibrating a 16-liter knapsack sprayer. The operator collects 900 mL of water from the nozzle in 60 seconds (Output = 0.90 L/min). The operator walks a 50-meter test course in 40 seconds. The effective spray swath width is 1.25 meters. 1) Calculate the operator's walking speed in km/h; 2) Calculate the sprayer application volume in Liters per hectare; and 3) If an insecticide is to be applied at a field rate of 1.50 Liters of commercial product per hectare, calculate how many milliliters of insecticide must be added to each 16-liter knapsack tank.\nSolution:\n1) Walking Speed = (Distance / Time) x 3.6 = (50m / 40s) x 3.6 = 1.25 x 3.6 = 4.50 km/h.\n2) Spray Application Volume (L/ha) = (Nozzle Output x 600) / (Speed x Swath) = (0.90 x 600) / (4.50 x 1.25) = 540 / 5.625 = 96.0 Liters/ha.\n3) Product per 16L Tank:\nFormula: Product/tank (mL) = [Field Rate (L/ha) x Tank Capacity (L) / Spray Volume (L/ha)] x 1,000 mL/L\nProduct/tank = [(1.50 L x 16 L) / 96.0 L] x 1,000 = (24.0 / 96.0) x 1,000 = 0.25 L x 1,000 = 250.0 mL of commercial insecticide per 16L tank.\nRefills per hectare = 96.0 L / 16 L = exactly 6 knapsack refills per hectare.",
                        "Problem 2: An agronomist must apply 800 grams of active ingredient (a.i.) per hectare of Imidacloprid to control whiteflies in a 4.0-hectare greenhouse. The available commercial product is Confidor 200 SL (Soluble Liquid, containing 200 g a.i. per Liter). 1) Calculate the volume of Confidor 200 SL required per hectare; and 2) Calculate the total volume required for the 4.0 hectares.\nSolution:\n1) Product per hectare = 800 g a.i./ha / 200 g a.i./L = 4.0 Liters/ha.\n2) Total Product for 4.0 hectares = 4.0 ha x 4.0 L/ha = 16.0 Liters of Confidor 200 SL."
                    ),
                    commonExamQuestions = listOf(
                        "State the universal sprayer calibration formula and explain how walking speed and nozzle swath width mathematically affect the delivered spray volume per hectare.",
                        "An operator doubles their walking speed from 3 km/h to 6 km/h without altering nozzle output or pressure. What happens to the spray volume (L/ha) delivered to the crop?",
                        "Describe the WALES sequence rule for tank mixing diverse pesticide formulations and explain why adding EC before WP causes curdling."
                    ),
                    practicalTakeaway = "Always calibrate knapsack sprayers using clean water on field ground before mixing chemicals: a walking speed difference of just 1 km/h alters pesticide dosage by 25%, causing either crop scorch or treatment failure.",
                    authoritativeTextbookRef = "Pesticide Application Methods (Matthews, Bateman, Miller, 4th Ed., Ch. 4, 6 & 9); Knapsack Sprayer Maintenance Guide (FAO)"
                )
            ),
            examFocusPoints = listOf(
                "Standard pesticide calibration formula: Volume (L/ha) = (L/min x 600) / (km/h x Swath m)",
                "Active ingredient to commercial product conversion mathematics",
                "Knapsack tank dose calculations and refill counts per hectare",
                "Walking speed formula: Speed (km/h) = (Distance m / Time s) x 3.6",
                "Nozzle wear tolerance limit (replace if discharge deviates by >10%)"
            ),
            practicalFieldApplications = listOf(
                "Executing a 50-meter knapsack calibration run with stopwatches and measuring cylinders",
                "Measuring individual nozzle spray distribution patterns using patternator corrugated trays"
            ),
            recommendedTextbookIds = listOf("book_matthews_pesticide_application", "book_pedigo_entomology_pest_mgmt")
        )
    )
}
