package com.example.data.science.curriculum

import com.example.model.science.CurriculumPillarType
import com.example.model.science.CurriculumSubtopic
import com.example.model.science.CurriculumTopic

object CropScienceCurriculum {

    val topics: List<CurriculumTopic> = listOf(
        CurriculumTopic(
            id = "crop_sci_agronomy",
            pillar = CurriculumPillarType.CROP_SCIENCE,
            code = "1.a",
            title = "Agronomy",
            summary = "The scientific principles and practices of field crop management, soil-plant-water interactions, and large-scale grain, legume, tuber, and industrial commodity production.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "cs_cereals_legumes",
                    code = "i",
                    title = "Cereals and Field Legumes",
                    coreDefinition = "Gramineous grass crops (Poaceae) cultivated for carbohydrate-dense endosperm and Fabaceous legume crops (Fabaceae) valued for protein-dense cotyledons and biological nitrogen fixation.",
                    keyMechanismsAndFormulas = listOf(
                        "Biological Nitrogen Fixation (BNF): N2 + 8H+ + 8e- + 16 ATP -> 2NH3 + H2 + 16 ADP + 16 Pi (catalyzed by nitrogenase complex).",
                        "Harvest Index (HI): Economic Yield / Biological Yield (e.g., modern semi-dwarf cereals achieve HI 0.45 - 0.55).",
                        "Tillering Dynamics: Primary, secondary, and tertiary tillers driven by phyllochron thermal time and phytochrome R:FR light ratio."
                    ),
                    detailedElaboration = "Cereal crops (Maize - Zea mays, Rice - Oryza sativa, Wheat - Triticum aestivum, Sorghum - Sorghum bicolor) supply over 60% of human caloric intake. Their tillering and panicle development depend critically on basal phosphorus for early crown root nodal initiation. Field legumes (Common bean - Phaseolus vulgaris, Soybean - Glycine max, Cowpea - Vigna unguiculata, Groundnut - Arachis hypogaea) form symbiotic root nodules with Rhizobium and Bradyrhizobium bacteria. Effective nodulation requires active leghaemoglobin (indicated by a distinct pink/red nodule interior). Over-application of synthetic inorganic nitrogen inhibits nodule initiation via autoregulation of nodulation (AON).",
                    commonExamQuestions = listOf(
                        "Differentiate determinate from indeterminate growth habit in legumes and discuss their harvesting windows.",
                        "What is the critical physiological role of leghaemoglobin in Rhizobium root nodules?",
                        "Calculate the Harvest Index (HI) given grain weight of 4.2 t/ha and total dry biomass of 9.0 t/ha."
                    ),
                    practicalTakeaway = "Inoculate legume seeds with specific Rhizobium strains (e.g., Biofix) when planting in virgin soils, and ensure starter phosphorus (P2O5) is banded to stimulate early nodulation."
                ),
                CurriculumSubtopic(
                    id = "cs_root_tuber",
                    code = "ii",
                    title = "Root & Tuber Crops",
                    coreDefinition = "Starch-accumulating storage organs derived from modified stems (tubers e.g., Solanum tuberosum), modified roots (storage roots e.g., Manihot esculenta, Ipomoea batatas), or corms/rhizomes (Colocasia, Dioscorea).",
                    keyMechanismsAndFormulas = listOf(
                        "Tuberization Stimulus: Short photoperiods + cool night temperatures (<18°C) trigger StSP6A (tuberigen) florigen homolog transport via phloem.",
                        "Cyanogenic Glucosides in Cassava: Linamarin & Lotaustralin hydrolyzed by linamarase into toxic hydrogen cyanide (HCN). Safe threshold: <10 mg HCN/kg fresh root.",
                        "Specific Gravity (Dry Matter): SG = Weight in Air / (Weight in Air - Weight in Water); correlates directly with starch percentage."
                    ),
                    detailedElaboration = "Root and tuber crops provide vital food security due to drought tolerance and high caloric productivity per hectare. Irish potatoes require loose, well-aerated hilling/ridging to prevent greening (solanine synthesis) and minimize tuber blight (Phytophthora infestans). Cassava (Manihot esculenta) tolerates extreme drought by stomatal closure and deciduous leaf shedding, but requires thorough post-harvest soaking, fermentation, or drying to detoxify linamarin. Sweet potato (Ipomoea batatas) varieties rich in beta-carotene (Orange-Fleshed Sweet Potato - OFSP) mitigate vitamin A deficiency in developing regions.",
                    commonExamQuestions = listOf(
                        "Explain the biochemical pathway responsible for solanine production in exposed potato tubers.",
                        "What postharvest procedures effectively reduce cyanogenic glucosides in bitter cassava?",
                        "How does planting orientation of cassava stem cuttings (vertical vs. horizontal) influence root bulking depth?"
                    ),
                    practicalTakeaway = "Perform thorough earthing-up (hilling) 4–6 weeks after potato emergence to insulate stolons from high soil temperatures and prevent solanine greening."
                ),
                CurriculumSubtopic(
                    id = "cs_seed_physiol",
                    code = "iii",
                    title = "Seed Physiology",
                    coreDefinition = "The physiological, biochemical, and biophysical processes governing seed maturation, desiccation tolerance, dormancy mechanisms, and imbibition-driven germination.",
                    keyMechanismsAndFormulas = listOf(
                        "Three-Phase Germination Curve: Phase I (rapid physical imbibition), Phase II (plateau/lag metabolic reactivation & DNA repair), Phase III (radicle emergence & cell expansion).",
                        "Hormonal Balance: Dormancy maintained by Abscisic Acid (ABA); broken by Gibberellins (GA3) inducing de novo synthesis of α-amylase in aleurone layer.",
                        "Tetrazolium Reaction: 2,3,5-triphenyltetrazolium chloride reduced by dehydrogenase enzymes into red, water-insoluble formazan in living embryo tissue."
                    ),
                    detailedElaboration = "Orthodox seeds withstand desiccation down to 5–7% moisture content and can be stored long-term at sub-zero temperatures (e.g., maize, beans, wheat). Recalcitrant seeds (e.g., avocado, mango, cocoa, tea) rapidly lose viability if dried below 20–30% moisture or chilled below 10–15°C. Dormancy classes include physical (hard seed coats impermeable to water, relieved by scarification), physiological (endogenous ABA inhibitors, relieved by cold stratification or GA3), and morphological (underdeveloped embryos requiring warm ripening).",
                    commonExamQuestions = listOf(
                        "Distinguish orthodox from recalcitrant seeds and explain why recalcitrant seeds cannot be preserved in conventional gene banks.",
                        "Trace the hormonal signaling cascade from GA perception to starch hydrolysis in cereal endosperm.",
                        "How is the Tetrazolium (TZ) test interpreted to differentiate dormant viable seeds from dead seeds?"
                    ),
                    practicalTakeaway = "Store orthodox commercial seeds in hermetic containers at <12% grain moisture to prevent storage fungi (Aspergillus, Penicillium) and metabolic deterioration."
                ),
                CurriculumSubtopic(
                    id = "cs_industrial_crops",
                    code = "iv",
                    title = "Industrial Crop Production",
                    coreDefinition = "High-value commercial cash crops cultivated primarily as industrial raw materials for processing into sugar, beverages, textiles, vegetable oil, rubber, or stimulants.",
                    keyMechanismsAndFormulas = listOf(
                        "Sugarcane Brix (°Bx) & Pol: Brix measures total dissolved solids; Pol measures apparent sucrose by polarimetry; Sucrose Purity (%) = (Pol / Brix) x 100.",
                        "Tea Plucking Quality: Two leaves and a bud yields optimal epigallocatechin gallate (EGCG) and polyphenol oxidase activity.",
                        "Coffee Roasting & Decarboxylation: Trigonelline, chlorogenic acids (CGA), and caffeine thermodynamics during the Maillard reaction."
                    ),
                    detailedElaboration = "Industrial crop production demands strict agronomic quality standards. Sugarcane (Saccharum officinarum) requires prolonged dry, sunny ripening to maximize sucrose storage in stem internodes and suppress vegetative leaf flush prior to harvest. Arabica coffee (Coffea arabica) thrives at high altitudes (1,500–2,200m) with slow cherry maturation, which concentrates aromatic chlorogenic acids, whereas Robusta (Coffea canephora) is cultivated at lower altitudes for high caffeine content and soluble coffee processing. Tea (Camellia sinensis) pruning cycles maintain a waist-height plucking table for continuous vegetative flush harvesting.",
                    commonExamQuestions = listOf(
                        "Discuss the agronomic and chemical ripening techniques used to increase sucrose recovery in sugarcane.",
                        "Contrast the physiological and environmental requirements of Coffea arabica versus Coffea canephora.",
                        "What is the significance of the two-leaves-and-a-bud plucking standard in black tea manufacturing?"
                    ),
                    practicalTakeaway = "Withhold irrigation 4–6 weeks prior to sugarcane harvesting to induce moisture stress that halts vegetative growth and shifts photosynthates into sucrose accumulation."
                )
            ),
            examFocusPoints = listOf(
                "Cereal vs. legume physiological synergies in intercropping",
                "Physiological basis of dormancy breaking and orthodox seed storage rules",
                "Critical growth stages sensitive to moisture deficit (e.g., silking/tasseling in maize)"
            ),
            practicalFieldApplications = listOf(
                "Field stand density calibration using row spacing and plant-to-plant distance formulas",
                "Rapid seed viability testing using warm water soaking and Tetrazolium staining",
                "Inoculant slurry preparation using 10% sugar solution as sticker for Rhizobium peat"
            ),
            recommendedTextbookIds = listOf("book_principles_field_crop", "book_plant_physiol_taiz", "book_seed_technology_copeland")
        ),
        CurriculumTopic(
            id = "crop_sci_horticulture",
            pillar = CurriculumPillarType.CROP_SCIENCE,
            code = "1.b",
            title = "Horticulture",
            summary = "The art and science of cultivating garden crops, partitioned into Pomology (fruit production), Olericulture (vegetable farming), Floriculture (ornamental flowers), and Landscape Management.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "cs_pomology",
                    code = "i",
                    title = "Pomology (Fruit Science)",
                    coreDefinition = "The science and cultivation of woody and herbaceous perennial fruit and nut crops, focusing on flower induction, rootstock selection, canopy training, and orchard management.",
                    keyMechanismsAndFormulas = listOf(
                        "Chilling Requirement (Chill Hours): Cumulative hours spent between 0°C and 7.2°C (32°F - 45°F) required to break endodormancy in deciduous fruit buds.",
                        "Apical Dominance & Auxin/Cytokinin Ratio: High indole-3-acetic acid (IAA) from terminal shoot apex inhibits axillary bud break; pruning head cuts remove auxin source to promote branching.",
                        "Parthenocarpy: Natural or hormone-induced development of fruit without fertilization (e.g., Cavendish banana, seedless citrus treated with gibberellic acid)."
                    ),
                    detailedElaboration = "Orchard establishment requires careful integration of rootstocks and scions. Clonal dwarfing rootstocks (e.g., M.9 for apples, Quince for pears) restrict scion vegetative vigor by altering xylem sap cytokinin flow, facilitating high-density trellising (central leader, open vase) and precocious bearing. Canopy training optimizes photosynthetically active radiation (PAR) penetration into inner fruit clusters, preventing shade-induced bud abortion and enhancing anthocyanin fruit coloration. Fruit thinning (chemical thinning using NAA or manual spacing) balances vegetative-to-reproductive sink strength and overcomes alternate (biennial) bearing.",
                    commonExamQuestions = listOf(
                        "Explain the biochemical role of chilling accumulation in overcoming bud endodormancy in temperate fruit trees.",
                        "Compare the open center (vase) canopy system with the central leader system in commercial orchards.",
                        "Explain how dwarfing rootstocks induce precocity and reduce vegetative shoot extension."
                    ),
                    practicalTakeaway = "Perform fruitlet thinning within 30 days after full bloom (post-bloom drop) to achieve a leaf-to-fruit ratio of 30–40 leaves per fruit, preventing biennial bearing."
                ),
                CurriculumSubtopic(
                    id = "cs_olericulture",
                    code = "ii",
                    title = "Olericulture (Vegetable Science)",
                    coreDefinition = "The branch of horticulture dealing with the production, storage, processing, and marketing of herbaceous plants utilized for culinary food purposes.",
                    keyMechanismsAndFormulas = listOf(
                        "Bolting Trigger: Premature elongation of floral axis in biennial vegetables (e.g., lettuce, cabbage, onions) induced by vernalization chilling followed by long days.",
                        "Blossom-End Rot (BER) Etiology: Localized calcium deficiency in distal fruit tissues caused by rapid cell expansion coupled with fluctuating transpiration.",
                        "Electrical Conductivity (EC) of Fertigation Solution: Target 1.8–2.5 dS/m for solanaceous greenhouse crops to maintain osmotic balance."
                    ),
                    detailedElaboration = "Vegetable crops are classified into cool-season (Brassicas, Alliums, Apiaceae) and warm-season (Solanaceae, Cucurbitaceae). Modern olericulture utilizes protected cultivation (greenhouses, high tunnels) with drip fertigation to fine-tune nutrient ratios (e.g., N:K ratio shifted from 1:1 during vegetative growth to 1:2 during fruiting). Blossom-end rot in tomato and capsicum is primarily a water management failure: calcium is transported solely via the xylem transpiration stream; when transpirational demand spikes or soil dries, calcium cannot reach fast-expanding distal cells, causing cell wall collapse.",
                    commonExamQuestions = listOf(
                        "Discuss the environmental triggers and hormonal pathways responsible for bolting in leafy brassicas.",
                        "Distinguish between physiological calcium deficiency (BER) and pathogen-induced fruit rot.",
                        "Describe nursery bed solarization techniques and seedling hardening protocols prior to field transplanting."
                    ),
                    practicalTakeaway = "Harden vegetable seedlings 7–10 days prior to transplanting by gradually withholding water and increasing direct solar exposure to accumulate cuticular waxes and reduce transplant shock."
                ),
                CurriculumSubtopic(
                    id = "cs_floriculture",
                    code = "iii",
                    title = "Floriculture (Commercial Flowers)",
                    coreDefinition = "The commercial production, breeding, and postharvest marketing of cut flowers, cut foliage, potted flowering plants, and bedding plants.",
                    keyMechanismsAndFormulas = listOf(
                        "Photoperiodism & Flowering: Short-day plants (SDP e.g., Chrysanthemum) flower when night length exceeds critical dark period; Long-day plants (LDP) flower during short nights.",
                        "Pulsing Solution: High-sucrose (10–20%) + biocide (8-HQC or chlorine dioxide) + silver thiosulfate (STS) to block ethylene action and supply respiratory substrate.",
                        "Vase Life Equation: Longevity determined by stem xylem hydraulic conductance, bacterial vascular plugging, and endogenous ethylene sensitivity."
                    ),
                    detailedElaboration = "Commercial cut flower production (e.g., export roses, carnations, alstroemeria, hypericum) requires strict greenhouse environmental management. Night-break lighting (cyclic lighting with red incandescent or LED lamps) prevents premature bud set in mother stock chrysanthemums. In cut roses, harvesting at the proper aperture stage (petals starting to loosen but sepals fully reflexed) ensures maximum vase life. Postharvest floral preservatives must address three challenges: bacterial vascular blockage in stem xylem, respiratory carbohydrate depletion, and ethylene-induced petal abscission.",
                    commonExamQuestions = listOf(
                        "How does silver thiosulfate (STS) or 1-methylcyclopropene (1-MCP) protect cut carnations and roses from ethylene?",
                        "Describe the critical photoperiodic lighting schedule used to produce year-round flowering in Chrysanthemums.",
                        "Explain why bent-neck occurs in cut roses and describe preventive postharvest cold-chain protocols."
                    ),
                    practicalTakeaway = "Recut stems under water (or at a sharp 45° angle) immediately before hydrating in floral preservative solutions at pH 3.5–4.5 to minimize vascular embolism and bacterial proliferation."
                ),
                CurriculumSubtopic(
                    id = "cs_landscape",
                    code = "iv",
                    title = "Landscape Management",
                    coreDefinition = "The planning, installation, and ongoing horticultural maintenance of aesthetic, functional, and recreational plantscapes, including turfgrass, arboriculture, and xeriscaping.",
                    keyMechanismsAndFormulas = listOf(
                        "Turfgrass Mowing One-Third Rule: Never remove more than 1/3 of the vertical leaf blade height in a single mowing event to prevent carbohydrate root starve.",
                        "Xeriscape Hydrozoning: Grouping landscape ornamentals into distinct water-use zones: Oasis (high water), Transition (moderate), and Arid/Desert (natural rainfall).",
                        "Target Pruning (CODIT): Compartmentalization Of Decay In Trees; cuts made outside the branch bark ridge and branch collar without flush cutting."
                    ),
                    detailedElaboration = "Professional landscape horticulture blends environmental engineering and plant biology. Warm-season turfgrasses (Cynodon dactylon - Bermuda grass, Pennisetum clandestinum - Kikuyu, Zoysia japonica) utilize C4 photosynthesis with high drought tolerance, whereas cool-season grasses (Festuca arundinacea - Tall fescue, Lolium perenne) thrive in cooler upland parks. Tree pruning must respect Alex Shigo’s CODIT model: pruning cuts that wound the branch collar destroy the tree's chemical barrier zones, allowing wood-decay fungi to penetrate the trunk heartwood.",
                    commonExamQuestions = listOf(
                        "Explain the CODIT principles of tree pruning and explain why flush cuts cause structural decay.",
                        "Formulate a water-wise hydrozone plan for an institutional landscape utilizing indigenous drought-tolerant flora.",
                        "State the agronomic rationale behind the 'One-Third Rule' in athletic turf management."
                    ),
                    practicalTakeaway = "Always prune branches immediately outside the branch collar and branch bark ridge; never apply pruning paints or asphalt tar, as they seal in fungal moisture."
                )
            ),
            examFocusPoints = listOf(
                "Chilling hours calculation and rest-breaking chemical agents (e.g., Hydrogen Cyanamide)",
                "Postharvest cut flower pulsing chemistry and ethylene antagonists",
                "Physiological disorders in vegetable production (bolting, BER, catfacing)"
            ),
            practicalFieldApplications = listOf(
                "Calculating budbreak chill units using the Utah or Dynamic Model",
                "Formulating pulsing solutions for export cut flowers (sucrose + citric acid + 8-HQC)",
                "Drip fertigation EC and pH balancing in commercial polyhouse vegetable beds"
            ),
            recommendedTextbookIds = listOf("book_hartmann_kester_propagation", "book_vegetable_welbaum", "book_postharvest_wills")
        ),
        CurriculumTopic(
            id = "crop_sci_breeding",
            pillar = CurriculumPillarType.CROP_SCIENCE,
            code = "1.c",
            title = "Plant Breeding and Genetics",
            summary = "Principles of Mendelian inheritance, quantitative genetics, heterosis, selection methodologies, hybrid seed production, and molecular breeding tools for crop improvement.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "cs_breeding_methods",
                    code = "i",
                    title = "Selection & Breeding Methodologies",
                    coreDefinition = "Systematic procedures to isolate, recombine, and stabilize desirable plant genotypes, including pedigree selection, bulk breeding, single-seed descent (SSD), backcrossing, and recurrent selection.",
                    keyMechanismsAndFormulas = listOf(
                        "Heritability (Broad Sense H² vs. Narrow Sense h²): H² = V_G / V_P; h² = V_A / V_P (where V_A is additive genetic variance).",
                        "Selection Differential (S) & Genetic Gain (R): Breeder's Equation R = h² x S (predicts response to phenotypic selection).",
                        "Heterosis (Mid-parent & High-parent): Heterosis (%) = [(F1 - MP) / MP] x 100."
                    ),
                    detailedElaboration = "Breeding self-pollinated crops (wheat, rice, soybean) focuses on developing homozygous pure line varieties through pedigree selection (individual plant record keeping through F2–F6 generations) or single-seed descent (rapid inbreeding under greenhouse conditions). Cross-pollinated crops (maize, pearl millet) suffer from severe inbreeding depression when selfed, but crossing distinct inbred lines produces outstanding F1 hybrid vigor (heterosis) explained by overdominance and dominance hypotheses. Backcross breeding is utilized to transfer a specific single qualitative gene (e.g., rust resistance gene) from a wild donor into an elite recurrent parent while recovering >98% of the recurrent background by BC6.",
                    commonExamQuestions = listOf(
                        "Apply the Breeder's Equation (R = h² x S) to calculate predicted genetic advance in grain yield.",
                        "Compare pedigree selection with single seed descent (SSD) in terms of labor, facility needs, and generation speed.",
                        "Diagram a 6-generation backcrossing program and calculate the recurrent parent genome recovery percentage at BC4."
                    ),
                    practicalTakeaway = "For quantitative traits with low narrow-sense heritability (e.g., drought yield), avoid early generation (F2) individual selection; instead, utilize replicated multi-location progeny testing at F4–F6."
                )
            ),
            examFocusPoints = listOf(
                "Breeder's Equation and heritability calculations",
                "Cytoplasmic Male Sterility (CMS) three-line system (A-line, B-line, R-line) for hybrid seed production",
                "Marker-Assisted Selection (MAS) vs. Phenotypic selection"
            ),
            practicalFieldApplications = listOf(
                "Emasculation and hand pollination protocols in maize and tomato crossing blocks",
                "Estimating genetic purity of commercial hybrid seed lots using grow-out tests (GOT) and SSR markers"
            ),
            recommendedTextbookIds = listOf("book_breeding_field_crops_sleper", "book_principles_plant_genetics")
        ),
        CurriculumTopic(
            id = "crop_sci_farming_systems",
            pillar = CurriculumPillarType.CROP_SCIENCE,
            code = "1.d",
            title = "Farming Systems",
            summary = "Spatial and temporal agroecological arrangements of crops, livestock, and trees to maximize biological synergies, resource-use efficiency, and resilience against climatic shocks.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "cs_farming_systems_models",
                    code = "i",
                    title = "Cropping Systems & Land Equivalent Ratio (LER)",
                    coreDefinition = "The spatial, temporal, and managerial arrangement of crops on a given land area, including monoculture, sequential cropping, intercropping, alley cropping, and agroforestry.",
                    keyMechanismsAndFormulas = listOf(
                        "Land Equivalent Ratio (LER): LER = (Y_A_inter / Y_A_sole) + (Y_B_inter / Y_B_sole); if LER > 1.0, intercropping provides land-use advantage.",
                        "Competition Ratio (CR): Measures competitive ability of one crop relative to another in mixed stands.",
                        "Three Pillars of Conservation Agriculture (CA): Minimum soil disturbance (zero-till), permanent organic soil cover (residue retention), and diversified crop rotations."
                    ),
                    detailedElaboration = "Sustainable farming systems exploit niche differentiation in space (canopy layering, rooting depth diversity) and time (staggered maturity periods). In maize-bean intercrops, LER frequently reaches 1.25–1.40, signifying that 25–40% more sole-crop land would be required to produce the same total yield. Agroforestry systems (e.g., Faidherbia albida parklands) exhibit 'reverse phenology': the tree sheds its leaves during the wet crop season, releasing nitrogen from decomposing biomass without shading the companion cereal crop.",
                    commonExamQuestions = listOf(
                        "Calculate LER given intercrop yields: Maize 3.5 t/ha (sole 4.2 t/ha) and Cowpea 0.8 t/ha (sole 1.2 t/ha). Is there an advantage?",
                        "Describe the ecological and agronomic benefits of Faidherbia albida reverse phenology in African dryland systems.",
                        "Contrast continuous tillage monoculture with Conservation Agriculture in terms of soil organic carbon accumulation."
                    ),
                    practicalTakeaway = "Retain at least 30% crop residue cover on the soil surface to reduce raindrop impact kinetic energy, prevent surface crusting, and increase infiltration rates by up to 300%."
                )
            ),
            examFocusPoints = listOf(
                "Calculating and interpreting Land Equivalent Ratio (LER)",
                "Conservation Agriculture principles and weed dynamics during zero-till transition",
                "Integrated Crop-Livestock-Forestry (ICLF) systems nutrient cycling"
            ),
            practicalFieldApplications = listOf(
                "Designing relay intercropping spatial patterns for maximum solar radiation capture",
                "Calibrating no-till seed drills for high-residue soil conditions"
            ),
            recommendedTextbookIds = listOf("book_farming_systems_norman", "book_agroecology_gliessman")
        ),
        CurriculumTopic(
            id = "crop_sci_biotech",
            pillar = CurriculumPillarType.CROP_SCIENCE,
            code = "1.e",
            title = "Agricultural Biotechnology",
            summary = "Cellular and molecular tools including plant tissue culture, recombinant DNA technology, Marker-Assisted Selection (MAS), and CRISPR/Cas gene editing to solve complex biotic and abiotic stresses.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "cs_biotech_tools",
                    code = "i",
                    title = "Plant Tissue Culture & Molecular Breeding",
                    coreDefinition = "In vitro culture of plant cells, tissues, and organs under aseptic conditions on defined nutrient media, alongside modern molecular marker technologies.",
                    keyMechanismsAndFormulas = listOf(
                        "Cellular Totipotency: The genetic capacity of a single differentiated somatic plant cell to regenerate into an entire functional fertile plant.",
                        "Skoog & Miller Auxin/Cytokinin Organogenesis Ratio: High Auxin:Cytokinin promotes root formation (rhizogenesis); High Cytokinin:Auxin promotes shoot bud differentiation (caulogenesis); Balanced promotes undifferentiated callus.",
                        "Bt Endotoxin Mechanism: Cry1Ab/Cry1Ac proteins solubilized in alkaline insect midgut (pH > 9.0), binding cadherin receptors to form pore-forming channels causing osmotic lysis."
                    ),
                    detailedElaboration = "Micropropagation enables disease-free clonal multiplication of vegetatively propagated crops (e.g., banana meristem tip culture producing virus-indexed plantlets). Agrobacterium tumefaciens utilizes its Ti-plasmid vir genes to transfer T-DNA encoding target traits into the plant nuclear genome. Modern breeding relies heavily on Marker-Assisted Selection (MAS) and genomic selection (GS), where DNA polymorphisms (SNPs, InDels, SSRs) tightly linked to QTLs allow breeders to cull undesirable seedlings in the nursery without waiting for mature field phenotypes.",
                    commonExamQuestions = listOf(
                        "Explain the hormonal manipulation required in Murashige & Skoog (MS) media to induce shoot vs. root regeneration from callus.",
                        "Outline the molecular mechanism of Bacillus thuringiensis (Bt) Cry protein toxicity in target lepidopteran larvae.",
                        "How does Marker-Assisted Backcrossing (MABC) accelerate foreground and background selection?"
                    ),
                    practicalTakeaway = "Utilize heat-treated meristem tip culture (0.2–0.5 mm explants) to produce certified virus-free seed tubers or suckers, escaping vascular virus translocation."
                )
            ),
            examFocusPoints = listOf(
                "Auxin to cytokinin ratio manipulation in organogenesis",
                "Biosafety frameworks and Cartagena Protocol regulations on LMOs",
                "Difference between transgenic (GMO) and CRISPR/Cas9 cisgenic gene edits"
            ),
            practicalFieldApplications = listOf(
                "Aseptic explant sterilization protocols using sodium hypochlorite and tween-20 surfactant",
                "PCR marker genotyping workflow for drought tolerance (Sub1 QTL in rice)"
            ),
            recommendedTextbookIds = listOf("book_plant_biotech_slater", "book_tissue_culture_razdan")
        ),
        CurriculumTopic(
            id = "crop_sci_physiol_ecology",
            pillar = CurriculumPillarType.CROP_SCIENCE,
            code = "1.f",
            title = "Crop Physiology & Ecology",
            summary = "Carbon assimilation pathways (C3, C4, CAM), light interception, canopy architecture, stomatal bio-energetics, water potential gradients, and environmental stress physiology.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "cs_photosynthesis_water",
                    code = "i",
                    title = "Photosynthetic Pathways & Plant Water Relations",
                    coreDefinition = "The biophysical processes of solar energy capture, carbon fixation (Rubisco vs. PEP carboxylase), and the soil-plant-atmosphere continuum (SPAC) water gradient.",
                    keyMechanismsAndFormulas = listOf(
                        "Water Potential Gradient: Ψ_leaf = Ψ_solute (osmotic) + Ψ_pressure (turgor) + Ψ_matric; water flows from higher (less negative) to lower (more negative) potential.",
                        "Rubisco Oxygenase Activity & Photorespiration: At high temperatures (>30°C), Rubisco binds O2 to produce 2-phosphoglycolate, losing up to 35% of fixed carbon in C3 plants.",
                        "Kranz Anatomy in C4 Plants: Initial carboxylation by PEP carboxylase in mesophyll cells shuttles 4-carbon malate into bundle sheath cells, concentrating CO2 around Rubisco."
                    ),
                    detailedElaboration = "Crop productivity is bounded by radiation use efficiency (RUE) and water use efficiency (WUE). C4 crops (maize, sorghum, sugarcane) exhibit no detectable photorespiration due to the CO2-concentrating mechanism in bundle sheath cells, granting superior nitrogen and water efficiency in tropical, high-irradiance climates. In contrast, C3 crops (wheat, rice, potato) achieve higher quantum yields under cooler temperatures and lower irradiance. The Soil-Plant-Atmosphere Continuum operates as an unbroken hydraulic column under tension, where xylem cavitation occurs if soil water potential drops below leaf turgor maintenance thresholds.",
                    commonExamQuestions = listOf(
                        "Explain Kranz anatomy and trace the biochemical movement of dicarboxylic acids in C4 photosynthesis.",
                        "Why does high temperature exacerbate photorespiratory carbon loss in C3 plants but not C4 plants?",
                        "Define osmotic adjustment in drought stress and explain how proline and glycine betaine protect cellular organelles."
                    ),
                    practicalTakeaway = "Monitor canopy temperature depression (CTD) using infrared thermometers: cooler canopies relative to ambient air indicate open stomata, active transpiration, and adequate root zone moisture."
                )
            ),
            examFocusPoints = listOf(
                "Biochemical comparison of C3, C4, and CAM photosynthetic mechanisms",
                "Components of water potential (Ψ_w = Ψ_s + Ψ_p + Ψ_m) and turgor loss point",
                "Photoperiodic flowering control: phytochrome red (Pr) and far-red (Pfr) ratios"
            ),
            practicalFieldApplications = listOf(
                "Measuring leaf area index (LAI) and light extinction coefficient (k) in field canopies",
                "Interpreting pressure chamber (Scholander bomb) stem water potential readings for irrigation scheduling"
            ),
            recommendedTextbookIds = listOf("book_plant_physiol_taiz", "book_crop_ecology_loomis")
        ),
        CurriculumTopic(
            id = "crop_sci_seed_tech",
            pillar = CurriculumPillarType.CROP_SCIENCE,
            code = "1.g",
            title = "Seed Technology & Production",
            summary = "The scientific techniques of high-purity certified seed production, seed multiplication generations, processing, conditioning, quality testing, and international ISTA protocols.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "cs_seed_classes_cert",
                    code = "i",
                    title = "Seed Multiplication Classes & Quality Standards",
                    coreDefinition = "The systematic regulatory hierarchy of seed multiplication (Breeder -> Foundation/Basic -> Registered -> Certified) to maintain genetic purity and vigor.",
                    keyMechanismsAndFormulas = listOf(
                        "Four Certified Seed Classes: Breeder Seed (white label with purple band, 100% purity), Foundation Seed (white label), Registered Seed (purple label), Certified Seed (blue label).",
                        "Pure Live Seed (PLS): PLS (%) = (% Purity x % Germination) / 100.",
                        "Isolation Distance Standard: Physical spatial separation (e.g., minimum 200–400m for hybrid maize) or temporal isolation to prevent foreign pollen contamination."
                    ),
                    detailedElaboration = "Certified seed production requires rigorous field inspections at critical vegetative, flowering, and pre-harvest stages. Roguing—the manual removal of off-types, diseased plants, and noxious weeds (e.g., Striga, Datura)—must be executed before anthesis. Seed conditioning facilities clean, gravity-grade, slurry-treat with fungicides/insecticides, and dry seeds down to safe equilibrium moisture content. International Seed Testing Association (ISTA) protocols govern official sampling, purity analysis, moisture determination by oven-drying, and germination vigor assays.",
                    commonExamQuestions = listOf(
                        "List the four seed certification classes and identify their official certification tag colors.",
                        "A seed lot has 98% physical purity and 85% germination. Calculate the Pure Live Seed (PLS) percentage.",
                        "State the minimum isolation distance required for certified hybrid maize seed production and explain why."
                    ),
                    practicalTakeaway = "Always adjust field seeding rates based on Pure Live Seed (PLS): Seeding Rate (kg/ha) = Target Seeding Rate / (PLS / 100)."
                )
            ),
            examFocusPoints = listOf(
                "Pure Live Seed (PLS) calculation and calibration of planting rates",
                "Field inspection standards and roguing timings for hybrid seed crops",
                "ISTA standard germination testing vs. accelerated aging vigor tests"
            ),
            practicalFieldApplications = listOf(
                "Performing official primary, composite, and submitted seed sampling per ISTA guidelines",
                "Conducting accelerated aging and electrical conductivity tests to assess seed vigor"
            ),
            recommendedTextbookIds = listOf("book_seed_technology_copeland", "book_ista_seed_rules")
        ),
        CurriculumTopic(
            id = "crop_sci_postharvest",
            pillar = CurriculumPillarType.CROP_SCIENCE,
            code = "1.h",
            title = "Postharvest Management",
            summary = "Post-harvest physiology, respiratory kinetics, climacteric fruit ripening, cold chain logistics, packinghouse sanitation, modified atmosphere storage, and grain loss mitigation.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "cs_postharvest_physiology",
                    code = "i",
                    title = "Respiration Kinetics, Ethylene & Cold Chain",
                    coreDefinition = "The control of biological degradation in harvested produce through temperature suppression, atmospheric gas alteration, and ethylene inhibition.",
                    keyMechanismsAndFormulas = listOf(
                        "Respiratory Quotient (RQ): Volume of CO2 evolved / Volume of O2 consumed (RQ = 1.0 for carbohydrates, 0.7 for lipids, 1.3 for organic acids).",
                        "Q10 Temperature Coefficient: Rate of deterioration increases by a factor of 2 to 3 for every 10°C increase in storage temperature.",
                        "Climacteric vs. Non-Climacteric: Climacteric fruit (banana, mango, avocado, tomato) exhibit a pronounced respiratory peak accompanied by ethylene burst; Non-climacteric (citrus, pineapple, strawberry) do not."
                    ),
                    detailedElaboration = "Postharvest deterioration commences immediately upon severing the plant organ from its root and water source. Removing field heat via rapid precooling (hydro-cooling, forced-air cooling, vacuum cooling) within hours of harvest is the single most effective postharvest intervention. Ethylene (C2H4) action can be irreversibly blocked by 1-methylcyclopropene (1-MCP), which occupies membrane receptor sites (ETR1). Modified Atmosphere Packaging (MAP) lowers O2 (typically 2–5%) and elevates CO2 (3–7%) to suppress mitochondrial respiration and retard chlorophyll degradation.",
                    commonExamQuestions = listOf(
                        "Classify the following as climacteric or non-climacteric: Avocado, Orange, Banana, Pineapple, Tomato, Table Grape.",
                        "Explain the biochemical mode of action of 1-methylcyclopropene (1-MCP) in extending fruit storage life.",
                        "Describe the symptoms, physiological cause, and temperature thresholds of chilling injury in tropical commodities."
                    ),
                    practicalTakeaway = "Never store chilling-sensitive tropical fruits (e.g., banana, mango, tomato) below 10°C–12°C, as membrane phase transitions cause electrolyte leakage, surface pitting, and failure to ripen."
                )
            ),
            examFocusPoints = listOf(
                "Climacteric vs. non-climacteric physiological ripening profiles",
                "Chilling injury vs. freezing injury mechanisms in horticultural perishables",
                "Hermetic grain storage technologies (e.g., PICS bags) for insect suppression through oxygen starvation"
            ),
            practicalFieldApplications = listOf(
                "Measuring fruit firmness using penetrometer (Magness-Taylor) and soluble solids using refractometer (°Brix)",
                "Maintaining relative humidity at 90–95% in cold storage to minimize transpirational vapor pressure deficit (VPD) weight loss"
            ),
            recommendedTextbookIds = listOf("book_postharvest_wills", "book_postharvest_kader")
        )
    )
}
