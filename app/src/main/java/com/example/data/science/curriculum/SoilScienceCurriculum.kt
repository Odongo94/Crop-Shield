package com.example.data.science.curriculum

import com.example.model.science.CurriculumPillarType
import com.example.model.science.CurriculumSubtopic
import com.example.model.science.CurriculumTopic

object SoilScienceCurriculum {

    val topics: List<CurriculumTopic> = listOf(
        CurriculumTopic(
            id = "soil_sci_genesis",
            pillar = CurriculumPillarType.SOIL_SCIENCE,
            code = "2.a",
            title = "Soil Genesis, Composition & Development",
            summary = "Pedogenic factors (Jenny's equation), physical and chemical weathering processes, master horizon development (O-A-E-B-C-R), and mineralogical composition.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "ss_pedogenesis",
                    code = "i",
                    title = "Soil Forming Factors & Horizon Differentiation",
                    coreDefinition = "The evolutionary formation of soil bodies driven by five independent soil-forming factors: S = f(Cl, O, R, P, T) - Climate, Organisms, Relief, Parent material, and Time.",
                    keyMechanismsAndFormulas = listOf(
                        "Hans Jenny's Equation of Soil Formation: S = f(Cl, O, R, P, T).",
                        "Weathering Index (Goldich Dissolution Series): Olivine -> Augite -> Hornblende -> Biotite -> K-Feldspar -> Muscovite -> Quartz (least stable to most stable).",
                        "Master Soil Horizons: O (organic layer), A (humus-enriched mineral topsoil), E (zone of eluviation/leaching), B (zone of illuviation/accumulation), C (weathered parent material), R (unweathered hard bedrock)."
                    ),
                    detailedElaboration = "Pedogenesis transforms parent rock into dynamic living soil through four fundamental pedogenic processes: Additions (organic matter, dust), Losses (leaching, erosion), Translocations (movement of clay, iron, organic matter downward via illuviation), and Transformations (weathering of primary minerals into secondary 1:1 and 2:1 clay minerals). In high-rainfall humid tropics, intense weathering leads to ferrallitization/laterization: silica is leached out (desilication), leaving behind resistant sesquioxides of iron and aluminum (hematite, goethite, gibbsite) characteristic of mature Oxisols.",
                    commonExamQuestions = listOf(
                        "State Hans Jenny's five soil-forming factors and explain how topography/relief influences catena development.",
                        "Differentiate eluviation from illuviation and identify the diagnostic master horizons where each dominates.",
                        "Explain the weathering susceptibility order of primary minerals according to Goldich's weathering series."
                    ),
                    practicalTakeaway = "Observe soil profile color changes along a toposequence (catena): red, well-drained soils occupy ridges, while grey, mottled, poorly drained soils (gleyed) accumulate in valley bottoms."
                )
            ),
            examFocusPoints = listOf(
                "Jenny's soil-forming equation and dominant pedogenic processes",
                "Epipedons (Mollic, Ochric, Umbric) and sub-surface diagnostic horizons (Argillic, Oxic, Spodic, Cambic)",
                "Physical disintegration vs. chemical weathering mechanisms (hydrolysis, hydration, carbonation, redox)"
            ),
            practicalFieldApplications = listOf(
                "Digging and describing a 1.5m standard soil pit using Munsell soil color charts and horizon demarcation",
                "Assessing soil profile drainage using redoximorphic feature identification (iron mottles)"
            ),
            recommendedTextbookIds = listOf("book_brady_weil_nature_soils", "book_birkeland_soils_geomorphology")
        ),
        CurriculumTopic(
            id = "soil_sci_taxonomy",
            pillar = CurriculumPillarType.SOIL_SCIENCE,
            code = "2.b",
            title = "Soil Taxonomy, Classification & Land Use",
            summary = "The 12 USDA Soil Taxonomy orders, FAO World Reference Base (WRB) classification, diagnostic horizons, and USDA Land Capability Classification (Classes I to VIII).",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "ss_taxonomy_orders",
                    code = "i",
                    title = "The 12 USDA Soil Orders & Land Capability",
                    coreDefinition = "Hierarchical categorical classification of soils based on observable diagnostic horizons and moisture/temperature regimes, linked to agricultural capability classes I–VIII.",
                    keyMechanismsAndFormulas = listOf(
                        "The 12 USDA Soil Orders: Entisols (recent, no horizons), Inceptisols (weak B horizon), Andisols (volcanic ash, high P-fixation), Gelisols (permafrost), Histosols (organic peat/muck), Aridisols (arid, salt/gypsum accumulation), Vertisols (shrinking/swelling 2:1 smectites), Mollisols (dark, fertile grassland soils, high base saturation), Alfisols (moderately leached, argillic horizon, BS >35%), Ultisols (strongly leached, acid, BS <35%), Spodosols (acid, coniferous, illuvial Al/Fe/humus), Oxisols (highly weathered tropical soils, low CEC, sesquioxide-dominated).",
                        "USDA Land Capability Classes: Classes I–IV (arable, suitable for cultivated crops with increasing conservation needs), Classes V–VIII (non-arable, restricted to pasture, forestry, wildlife, recreation, watershed protection)."
                    ),
                    detailedElaboration = "Soil taxonomy provides a universal vocabulary for land evaluation and transfer of agricultural technology. Vertisols exhibit high contents of expanding smectite clays that form deep cracks during dry spells (slickensides, gilgai microrelief), requiring careful traffic timing to avoid severe compaction. Andisols possess high amorphous allophane content with remarkable phosphate fixation capacity, necessitating banded superphosphate fertilization. Land Capability Class I has virtually no cultivation restrictions, whereas Class IV requires severe soil conservation practices (terracing, strip cropping), and Class VIII is entirely non-productive steep rock land.",
                    commonExamQuestions = listOf(
                        "Name the 12 USDA soil orders and give the diagnostic horizon or property defining each order.",
                        "Distinguish an Alfisol from an Ultisol based on subsoil base saturation percentage.",
                        "Differentiate Land Capability Class I from Class IV and list management interventions required for Class IV."
                    ),
                    practicalTakeaway = "Never cultivate Vertisols when wet (sticky plasticity causes severe structure damage) or when bone-dry (rock-hard consistency); cultivate within the narrow plastic-friable moisture window."
                )
            ),
            examFocusPoints = listOf(
                "Diagnostic criteria for all 12 USDA soil orders",
                "Distinguishing Alfisols vs. Ultisols vs. Oxisols in agricultural regions",
                "Land Capability Classification (LCC) subclasses: e (erosion), w (wetness), s (soil root zone limitation), c (climate)"
            ),
            practicalFieldApplications = listOf(
                "Matching crop enterprise suitability to USDA soil taxonomy order and soil series",
                "Delineating soil conservation structures (Fanya-Juu terraces, contour bunds) based on LCC class"
            ),
            recommendedTextbookIds = listOf("book_brady_weil_nature_soils", "book_usda_keys_soil_taxonomy")
        ),
        CurriculumTopic(
            id = "soil_sci_microbiology",
            pillar = CurriculumPillarType.SOIL_SCIENCE,
            code = "2.c",
            title = "Soil Microbiology",
            summary = "Rhizosphere microbiology, mycorrhizal symbioses, biological nitrogen fixation, carbon and nitrogen cycling, organic matter decomposition, and soil food web dynamics.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "ss_microbial_ecology",
                    code = "i",
                    title = "Biological N-Fixation, Mycorrhizae & Nutrient Cycles",
                    coreDefinition = "The metabolic actions of soil bacteria, fungi, actinomycetes, and protozoa driving organic matter humification and elemental biogeochemical cycling.",
                    keyMechanismsAndFormulas = listOf(
                        "Critical Soil C:N Ratio: C:N > 30:1 triggers net microbial immobilization of soil mineral nitrogen; C:N < 20:1 triggers net mineralization (release of plant-available NH4+ and NO3-).",
                        "Nitrification Two-Step Enzymatic Oxidation: Step 1 (Nitrosomonas): 2NH4+ + 3O2 -> 2NO2- + 4H+ + 2H2O; Step 2 (Nitrobacter): 2NO2- + O2 -> 2NO3-.",
                        "Arbuscular Mycorrhizal Fungi (AMF): Glomeromycota fungi extend extra-radical hyphae up to 100x beyond root depletion zones, solubilizing and transporting immobile orthophosphate (H2PO4-)."
                    ),
                    detailedElaboration = "The soil microbial biomass serves as the eye of the needle through which all organic carbon passes. When crop residues with a wide C:N ratio (e.g., wheat straw or maize stover, C:N ~80:1) are incorporated without supplemental nitrogen, heterotrophic microbes scavenge all available soil nitrate and ammonium, starving the subsequent crop (nitrogen drag). Nitrification produces 2 moles of H+ per mole of ammonium oxidized, making repeated ammoniacal fertilizer applications an important acidifying factor in agricultural soils.",
                    commonExamQuestions = listOf(
                        "Explain what happens to soil plant-available nitrogen when sawdust (C:N 400:1) vs. legume residue (C:N 15:1) is added.",
                        "Describe the two-step biological oxidation of ammonium to nitrate, naming the specific bacterial genera involved.",
                        "How do arbuscular mycorrhizal fungi (AMF) enhance phosphorus uptake in phosphorus-fixing soils?"
                    ),
                    practicalTakeaway = "When incorporating high-carbon residues (cereal straw), apply a starter nitrogen dose (20–30 kg N/ha) to satisfy microbial demand and prevent temporary crop nitrogen starvation."
                )
            ),
            examFocusPoints = listOf(
                "C:N ratio dynamics: mineralization vs. immobilization thresholds",
                "Biological nitrogen fixation symbioses (Rhizobium-legume, Frankia-actinorrhizal, Azolla-Anabaena)",
                "Denitrification biochemical conditions: anaerobic saturation, carbon substrate, warm temperatures"
            ),
            practicalFieldApplications = listOf(
                "Nodule scoring on legume roots: evaluating size, position on crown root, and internal pink leghaemoglobin color",
                "Brewing and quality testing of beneficial microbial teas (Trichoderma, Bacillus subtilis) for soil health"
            ),
            recommendedTextbookIds = listOf("book_soil_microbiol_paul", "book_brady_weil_nature_soils")
        ),
        CurriculumTopic(
            id = "soil_sci_physics",
            pillar = CurriculumPillarType.SOIL_SCIENCE,
            code = "2.d",
            title = "Soil Physics",
            summary = "Soil physical properties: particle size distribution (USDA textural triangle), Stokes' law sedimentation, soil structure, bulk density, porosity, soil water energy states, and Darcy's law.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "ss_physical_properties",
                    code = "i",
                    title = "Texture, Structure, Bulk Density & Soil Water",
                    coreDefinition = "The mechanical, volumetric, and energy relationships of the solid mineral matrix, pore spaces, air, and water within the soil matrix.",
                    keyMechanismsAndFormulas = listOf(
                        "Stokes' Law Sedimentation Velocity: v = (2/9) x [r² g (ρ_s - ρ_w)] / η (governs hydrometer and pipette particle size analysis).",
                        "Bulk Density (Db) & Porosity (f): Db = Dry Soil Mass (g) / Total Soil Core Volume (cm³); Total Porosity (%) = [1 - (Db / Dp)] x 100 (where Dp is particle density, typically 2.65 g/cm³).",
                        "Available Water Capacity (AWC): AWC = Field Capacity (FC, -33 kPa or -0.33 bar) - Permanent Wilting Point (PWP, -1500 kPa or -15 bar).",
                        "Darcy's Law for Saturated Flow: Q = K_sat x A x (ΔH / L)."
                    ),
                    detailedElaboration = "Soil texture is a permanent physical property determined by the relative proportion of sand (2.0–0.05 mm), silt (0.05–0.002 mm), and clay (<0.002 mm). Soil structure describes the aggregation of primary particles into peds (granular, blocky, prismatic, platy). A typical agricultural loam has a bulk density of 1.2–1.4 g/cm³. When bulk density exceeds 1.6 g/cm³ (clays) or 1.75 g/cm³ (sands) due to heavy farm machinery traffic, root penetration resistance exceeds 2.0 MPa, physically arresting root elongation.",
                    commonExamQuestions = listOf(
                        "Given an undisturbed soil core volume of 100 cm³ and oven-dry weight of 135 g, calculate bulk density and total porosity.",
                        "Define Field Capacity (FC) and Permanent Wilting Point (PWP) in terms of matric potential in kilopascals.",
                        "Explain why platy soil structure impedes downward water percolation compared to granular structure."
                    ),
                    practicalTakeaway = "Avoid driving heavy tractors or tillage implements on moist soils at field capacity, which represents maximum soil compressibility and vulnerability to subsoil plow-pan formation."
                )
            ),
            examFocusPoints = listOf(
                "Stokes' Law assumptions and particle size boundaries",
                "Bulk density and porosity calculations and critical root-limiting thresholds",
                "Soil moisture retention curves for sand, loam, and clay soils"
            ),
            practicalFieldApplications = listOf(
                "Determining soil texture by feel (ribbon length method) and hydrometer sedimentation test",
                "Measuring soil compaction using an electronic cone penetrometer (penetration resistance in PSI/MPa)"
            ),
            recommendedTextbookIds = listOf("book_soil_physics_jury", "book_brady_weil_nature_soils")
        ),
        CurriculumTopic(
            id = "soil_sci_fertility",
            pillar = CurriculumPillarType.SOIL_SCIENCE,
            code = "2.e",
            title = "Soil Fertility and Management",
            summary = "Essential plant nutrients (macro and micronutrients), Liebig's Law of the Minimum, nutrient mobility in soil and plants, soil testing calibration, fertilizer formulation, and acid soil liming.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "ss_nutrient_management",
                    code = "i",
                    title = "Essential Nutrients, Deficiency Diagnoses & Liming",
                    coreDefinition = "The management of the 17 essential plant elements to optimize economic crop yields while maintaining ecological soil quality and water safety.",
                    keyMechanismsAndFormulas = listOf(
                        "Liebig's Law of the Minimum: Crop yield is limited not by total resources, but by the scarcest nutrient relative to crop demand.",
                        "4R Nutrient Stewardship: Right Source, Right Rate, Right Time, Right Place.",
                        "Liming Neutralization Reaction: CaCO3 + 2H+ (exchangeable) -> Ca2+ (on colloid) + CO2 + H2O.",
                        "Calcium Carbonate Equivalent (CCE): Acid-neutralizing capacity relative to pure CaCO3 (e.g., CaO CCE = 179%, Dolomite CaMg(CO3)2 CCE = 109%)."
                    ),
                    detailedElaboration = "Nutrients mobile within the plant (N, P, K, Mg) translocate from older tissues to young growing tips during deficiency; therefore, deficiency symptoms manifest first on lower/older leaves (e.g., V-shaped yellowing on older maize leaves for N; marginal scorch for K; interveinal chlorosis for Mg). Immobile nutrients (Ca, Fe, B, Cu) cannot translocate; symptoms appear first on terminal buds and young leaves. In acidic soils (pH < 5.5), toxic soluble aluminum (Al3+) hydrolyzes water, generating protons and severely stunting root tips (stubby, club-shaped roots). Agricultural lime precipitates toxic Al3+ into insoluble gibbsite [Al(OH)3].",
                    commonExamQuestions = listOf(
                        "Explain why nitrogen deficiency appears on older leaves first, whereas calcium deficiency affects terminal buds.",
                        "Describe the mechanism of aluminum toxicity in acid soils and write the chemical reaction showing how agricultural lime neutralizes exchangeable Al3+.",
                        "Calculate the amount of DAP (18-46-0) and Urea (46-0-0) required to supply 120 kg N and 60 kg P2O5 per hectare."
                    ),
                    practicalTakeaway = "Always test soil pH before purchasing fertilizers; applying expensive compound NPK fertilizers to soils with pH < 5.2 results in >70% phosphorus fixation and poor nutrient use efficiency."
                )
            ),
            examFocusPoints = listOf(
                "Mobile vs. immobile nutrient deficiency symptom distribution",
                "Aluminum toxicity mechanisms at low soil pH and lime requirement calculations",
                "Phosphorus fixation chemistry in acidic (Fe/Al) vs. alkaline calcareous (Ca) soils"
            ),
            practicalFieldApplications = listOf(
                "Calculating fertilizer blend quantities to fulfill a soil test prescription (N-P2O5-K2O)",
                "Interpreting Mehlich-3 and Bray-1 soil test extraction results"
            ),
            recommendedTextbookIds = listOf("book_havlin_soil_fertility", "book_brady_weil_nature_soils")
        ),
        CurriculumTopic(
            id = "soil_sci_chemistry",
            pillar = CurriculumPillarType.SOIL_SCIENCE,
            code = "2.f",
            title = "Soil Chemistry / Chemical Properties of Soil",
            summary = "Colloidal chemistry, cation and anion exchange capacity (CEC/AEC), base saturation, diffuse double layer, soil buffering capacity, salinity, sodicity (SAR/ESP), and electrical conductivity.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "ss_colloidal_chemistry",
                    code = "i",
                    title = "CEC, Base Saturation, Salinity & Sodicity",
                    coreDefinition = "The surface electrical charge phenomena of inorganic clay minerals and humus colloids governing nutrient retention, exchange reactions, and soil salinity.",
                    keyMechanismsAndFormulas = listOf(
                        "Cation Exchange Capacity (CEC): Expressed in cmol(+)/kg or meq/100g dry soil. Permanent charge (isomorphous substitution) + pH-dependent variable charge.",
                        "Percentage Base Saturation (%BS): [%BS = (Ca2+ + Mg2+ + K+ + Na+) / CEC] x 100.",
                        "Lyotropic / Affinity Series: Al3+ > Ca2+ > Mg2+ > K+ = NH4+ > Na+ (higher charge density binds tighter to colloid).",
                        "Sodium Adsorption Ratio (SAR) & ESP: SAR = Na+ / sqrt[(Ca2+ + Mg2+) / 2]; ESP (%) = (Exchangeable Na+ / CEC) x 100. Sodic soil defined by ESP > 15% or SAR > 13."
                    ),
                    detailedElaboration = "Soil colloids carry negative charges that attract and reversibly hold cations against leaching. Permanent charge arises from isomorphous substitution during clay genesis (e.g., Al3+ substituting for Si4+ in tetrahedral sheets, or Mg2+ substituting for Al3+ in octahedral sheets). Organic matter possesses pH-dependent charge: as pH rises, carboxylic (-COOH) and phenolic (-OH) groups deprotonate, increasing negative exchange sites. Saline soils (ECe > 4 dS/m, ESP < 15%) restrict plant water uptake via osmotic potential, remediated by water leaching. Sodic soils (ESP > 15%, pH > 8.5) suffer severe clay dispersion and aggregate collapse, requiring gypsum (CaSO4.2H2O) to displace Na+ with Ca2+ before leaching.",
                    commonExamQuestions = listOf(
                        "Differentiate permanent charge from pH-dependent charge in soil colloids.",
                        "A soil has CEC of 20 cmol(+)/kg with 10 cmol Ca2+, 3 cmol Mg2+, 1 cmol K+, and 0.5 cmol Na+. Calculate % Base Saturation.",
                        "Contrast the reclamation protocol for a saline soil versus a sodic soil, explaining the critical role of gypsum."
                    ),
                    practicalTakeaway = "Never leach a sodic soil with fresh water before applying gypsum: without soluble calcium to maintain electrolyte concentration, clay aggregates disperse immediately, creating an impermeable seal."
                )
            ),
            examFocusPoints = listOf(
                "Lyotropic exchange series and cation displacement principles",
                "Calculations of CEC, Base Saturation, SAR, and ESP",
                "Classification of salt-affected soils: Saline, Sodic, and Saline-Sodic"
            ),
            practicalFieldApplications = listOf(
                "Calculating gypsum requirement (tons/ha) to reclaim sodic soil based on CEC and exchangeable sodium",
                "Operating hand-held soil EC and pH meters and performing 1:2.5 soil-water slurry calibrations"
            ),
            recommendedTextbookIds = listOf("book_havlin_soil_fertility", "book_brady_weil_nature_soils", "book_bohn_soil_chem")
        )
    )
}
