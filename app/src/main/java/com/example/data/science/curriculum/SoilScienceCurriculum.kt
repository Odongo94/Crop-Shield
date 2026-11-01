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
            summary = "Pedogenic factors (Jenny's equation), physical and chemical weathering pathways, mineral stability series, master soil horizons, and profile differentiation.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "ss_pedogenesis",
                    code = "i",
                    title = "Soil Forming Factors & Horizon Differentiation",
                    coreDefinition = "The evolutionary processes by which parent rock and geological sediments are transformed into organized, dynamic natural soil bodies under the influence of five independent state factors: Climate, Organisms, Relief, Parent material, and Time.",
                    keyMechanismsAndFormulas = listOf(
                        "Hans Jenny's Equation of Soil Formation (1941): S = f(Cl, O, R, P, T, ...), where S is any soil property, Cl is climate (temperature and precipitation), O is organisms (vegetation, macro/microbiota), R is relief/topography, P is parent material, and T is time.",
                        "Goldich Dissolution / Weathering Series (Susceptibility to Chemical Weathering): Olivine > Augite (Pyroxene) > Hornblende (Amphibole) > Biotite > Albite (Na-feldspar) > Anorthite (Ca-feldspar) > K-Feldspar > Muscovite > Quartz. Quartz is the most chemically resistant primary mineral.",
                        "Four Fundamental Pedogenic Processes (Simonson): Additions (organic matter, atmospheric dust), Losses (leaching, erosion), Translocations (illuviation of clay, Fe/Al sesquioxides, humus), and Transformations (weathering of primary minerals to 1:1 kaolinite or 2:1 smectite clays).",
                        "Master Horizon Sequence: O (organic layer: Oi fibric, Oe hemic, Oa sapric) -> A (humus-mineral topsoil) -> E (zone of eluviation/leaching of clay, Fe, Al) -> B (zone of illuviation/accumulation) -> C (unconsolidated parent material) -> R (hard bedrock)."
                    ),
                    detailedElaboration = "Soil genesis (pedogenesis) transforms raw parent rock into living soil through physical disintegration and chemical decomposition.\n\nWeathering Chemistry: Chemical weathering processes include: 1) Hydrolysis (reaction with water dissociating into H+ and OH-, e.g., KAlSi3O8 + H2O -> HAlSi3O8 + K+ + OH-); 2) Hydration (incorporation of water molecules into mineral crystal lattices, e.g., Hematite Fe2O3 + H2O -> Goethite FeOOH); 3) Carbonation (dissolution by carbonic acid H2CO3 formed from rainwater and soil CO2 respiration); and 4) Redox reactions (oxidation of ferrous Fe2+ to ferric Fe3+ producing bright red/orange iron oxides).\n\nToposequences & Soil Catenas: Along a hillside slope (crest -> shoulder -> backslope -> footslope -> toeslope), relief dictates drainage, lateral water flow, and pedogenesis. Ridge tops and steep slopes experience high internal drainage and oxidation, forming well-aerated, deep red soils (Nitisols/Ferralsols). Valley bottoms (toeslopes) accumulate leached bases, silica, and drainage water, resulting in anaerobic, poorly drained soils with prominent grey-blue mottling (Gleysols) or high-swelling dark smectite clays (Vertisols).\n\nTropical Ferrallitization: In hot, humid tropical environments with high rainfall, extreme prolonged weathering leaches out soluble silica (desilication) and basic cations (Ca, Mg, K). Residual soil profiles become dominated by resistant 1:1 kaolinite clays and insoluble sesquioxides of iron and aluminum (gibbsite, hematite, goethite), producing highly weathered Oxisols with low effective cation exchange capacity (ECEC).",
                    biologicalAndChemicalDetails = listOf(
                        "Diagnostic Subsurface Horizon Notations: Bt (illuvial accumulation of silicate clay, argillic), Bhs (illuvial accumulation of humus and sesquioxides, spodic), Bw (development of color or structure, cambic), Bo (extreme weathering, oxic horizon), Bk (accumulation of secondary carbonates, calcic), Cr (weathered, soft rock saprolite).",
                        "Silicate Clay Mineral Genesis: Primary mica/feldspars weather sequentially through illite -> vermiculite -> smectite (2:1 expanding) under moderate leaching, or weather directly to kaolinite (1:1 non-expanding) and gibbsite under intensive tropical leaching."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "Standard Soil Pit Excavation & Horizon Demarcation: Dig a soil pit 1.5m wide x 1.5m long x 1.5m deep (or to bedrock), orienting the viewing face towards sunlight. Clean the face vertically with a sharp spade. Use a knife point to probe soil compaction and delineate natural horizon boundaries (sharp, clear, gradual, or diffuse). Describe soil color for each horizon using a Munsell Soil Color Chart (Hue, Value, Chroma, e.g., 2.5YR 3/6) under moist and dry conditions.",
                        "Field Soil Structure Description: Classify ped structure into Type (Granular, Blocky, Subangular Blocky, Prismatic, Columnar, Platy, or Structureless Single Grain/Massive), Grade (Weak, Moderate, Strong), and Class (Fine, Medium, Coarse)."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Redoximorphic Features (Gleying): Bluish-grey, greenish-grey, or olive soil matrix colors (chroma ≤ 2) interspersed with bright reddish-orange ferric iron mottles along root channels, indicating periodic saturation and waterlogging (anaerobic iron reduction).",
                        "Platy Subsoil Structure: Flat, horizontal plate-like aggregates indicating mechanical compaction from heavy tillage equipment (plow pan) or vehicle traffic, severely restricting downward root growth and water percolation."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: A soil profile sample is analyzed in the laboratory. The total soil mass is 100g, consisting of 60g quartz sand, 20g secondary 1:1 kaolinite clay, 15g iron and aluminum sesquioxides (hematite and gibbsite), and 5g primary biotite mica. Calculate the percentage of highly weathered residual minerals (kaolinite + sesquioxides + quartz) and evaluate the pedogenic maturity of this soil.\nSolution: 1) Residual Weathering Products = 60g (quartz) + 20g (kaolinite) + 15g (sesquioxides) = 95g. 2) Percentage = (95g / 100g) x 100 = 95.0%. 3) Evaluation: A profile containing 95% highly resistant residual minerals and only 5% weatherable primary minerals (biotite) represents an advanced stage of tropical pedogenic development (an Oxisol or Ultisol) with exhausted mineral nutrient reserves."
                    ),
                    commonExamQuestions = listOf(
                        "State Hans Jenny's five soil-forming factors and explain how topography/relief governs catena formation.",
                        "List the Goldich weathering series minerals in order of increasing resistance to chemical weathering.",
                        "Differentiate an eluvial (E) horizon from an illuvial (Bt) horizon in terms of clay and iron mobility."
                    ),
                    practicalTakeaway = "Always examine subsoil profile colors before buying or planting farm land: bright uniform red or brown colors indicate deep, well-drained root zones, while dull grey mottles indicate poor subsoil drainage and root rot hazards.",
                    authoritativeTextbookRef = "The Nature and Properties of Soils (Brady & Weil, Ch. 2 & 3); Soils and Geomorphology (Birkeland)"
                )
            ),
            examFocusPoints = listOf(
                "Jenny's soil-forming equation and dominant pedogenic processes",
                "Goldich mineral weathering series and chemical weathering reactions",
                "Diagnostic master horizons (O, A, E, B, C, R) and subordinate distinctions (t, h, s, w, o, k, g)",
                "Catena toposequences and redoximorphic waterlogging indicators"
            ),
            practicalFieldApplications = listOf(
                "Describing soil profile pits using Munsell Color Charts and structural ped classification",
                "Identifying subsoil plow pans using knife blade penetration tests"
            ),
            recommendedTextbookIds = listOf("book_brady_weil_nature_soils", "book_usda_keys_soil_taxonomy")
        ),
        CurriculumTopic(
            id = "soil_sci_taxonomy",
            pillar = CurriculumPillarType.SOIL_SCIENCE,
            code = "2.b",
            title = "Soil Taxonomy, Classification & Land Use",
            summary = "The 12 USDA Soil Taxonomy orders, diagnostic epipedons and endopedons, FAO/WRB reference soil groups, and Land Capability Classification (Classes I to VIII).",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "ss_taxonomy_orders",
                    code = "i",
                    title = "The 12 USDA Soil Orders & Land Capability",
                    coreDefinition = "The hierarchical classification of soils based on observable diagnostic horizons and soil moisture/temperature regimes, linked directly to agricultural land evaluation and capability classes I through VIII.",
                    keyMechanismsAndFormulas = listOf(
                        "The 12 USDA Soil Orders: Entisols (-ents, recent, no diagnostic horizons), Inceptisols (-epts, embryonic Bw horizon), Andisols (-ands, volcanic ash, high P-fixation), Gelisols (-els, permafrost within 1m), Histosols (-ists, organic peat/muck >20% OC), Aridisols (-ids, arid, ochric epipedon, salic/calcic horizons), Vertisols (-erts, shrinking/swelling 2:1 smectites, slickensides), Mollisols (-olls, deep dark fertile grassland soils, mollic epipedon, base saturation >50%), Alfisols (-alfs, forest soils, argillic Bt horizon, base saturation ≥35%), Ultisols (-ults, highly leached acid soils, argillic Bt, base saturation <35%), Spodosols (-ods, coniferous forest, spodic Bhs horizon of illuvial Al/Fe/humus), Oxisols (-ox, intensely weathered tropical soils, oxic Bo horizon, low CEC).",
                        "USDA Land Capability Classification (LCC): Classes I–IV (arable lands suitable for cultivated crops with increasing conservation restrictions); Classes V–VIII (non-arable lands restricted to pasture, range, commercial forestry, wildlife, and watershed protection).",
                        "LCC Subclasses: e (erosion risk), w (excess wetness/drainage), s (shallow soil, stoniness, low fertility), c (climatic limitation: drought or extreme cold)."
                    ),
                    detailedElaboration = "Soil Taxonomy provides a scientific, objective framework for organizing soils into Orders, Suborders, Great Groups, Subgroups, Families, and Series.\n\nContrasting Key Agricultural Soil Orders:\n1) Vertisols (Black Cotton Soils): Dominated by 2:1 expanding smectite (montmorillonite) clays (>30% clay to 1m depth). During dry seasons, shrinking clays cause deep, wide open cracks (>1 cm wide to 50 cm depth) into which surface organic matter falls. Upon wet re-expansion, swelling pressures cause shearing along planes, producing polished, grooved ped surfaces termed slickensides and undulating micro-topography called gilgai. They have high CEC (30–60 cmol/kg) and natural fertility, but have a very narrow moisture workability window: extremely sticky and plastic when wet, and rock-hard when dry.\n2) Andisols (Volcanic Ash Soils): Formed from volcanic ejecta, dominated by short-range-order amorphous aluminosilicates (allophane, imogolite) and ferrihydrite. They exhibit low bulk density (<0.90 g/cm³), high water holding capacity, and exceptional phosphate fixation capacity (>85% P fixed into insoluble forms), requiring banded phosphate applications.\n3) Alfisols vs. Ultisols: Both possess an argillic (Bt) clay accumulation horizon. The critical diagnostic boundary is Base Saturation (by sum of cations at pH 8.2): Alfisols have ≥35% base saturation at 1.8m depth (fertile, mildly acidic to neutral), while Ultisols have <35% base saturation (heavily leached, acidic, prone to aluminum toxicity).\n\nLand Capability Classification: Class I land has deep, fertile, nearly level soils with no cultivation limitations. Class II has gentle slopes or minor erosion risks requiring contour plowing. Class III requires intensive terracing and strip cropping. Class IV is marginal for cultivation (steep, shallow, or droughty). Class VI and VII are suitable only for grazing or timber, and Class VIII is rocky, steep watershed land that must remain in undisturbed natural vegetation.",
                    biologicalAndChemicalDetails = listOf(
                        "Diagnostic Epipedons: Mollic (thick, dark, organic-rich, base saturation >50%), Ochric (thin, light-colored, low organic carbon), Umbric (dark and thick like mollic, but base saturation <50%), Histic (thick organic layer saturated with water).",
                        "FAO/WRB Equivalents: USDA Vertisols -> WRB Vertisols; USDA Oxisols -> WRB Ferralsols; USDA Andisols -> WRB Andosols; USDA Mollisols -> WRB Chernozems/Kastanozems; USDA Nitisols (deep red, highly aggregated tropical soils with shiny polyhedral peds)."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "Field Soil Textural Differentiation by Feel (Ribbon Method): Knead 25g of soil with water to putty consistency. Roll into a 1 cm thick ball, then push out between thumb and forefinger to form a uniform ribbon. Measure ribbon length before it breaks under its own weight: <2.5 cm ribbon = Loam/Sandy Loam; 2.5–5.0 cm ribbon = Clay Loam; >5.0 cm long continuous ribbon = Clay/Vertisol. If it feels smooth and floury = Silt; if gritty = Sand.",
                        "Delineating Land Capability Class on Farm Maps: Measure field slope percentage using an Abney level or clinometer. Pit test for rooting depth: Deep (>100 cm), Moderate (50–100 cm), Shallow (<50 cm). Cross-reference slope and depth against erosion hazard: Slopes >12% or rooting depth <25 cm automatically downgrade land to Class VI or VII, prohibiting mechanized annual row cropping."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Gilgai Micro-relief: Micro-mounds and micro-depressions (hummocks) spaced 2–5m apart on ground surfaces, characteristic of active swelling Vertisols.",
                        "Argillic Cutans (Clay Skins): Shiny, wax-like coatings of illuvial oriented clay particles lining ped surfaces and pore walls in B horizons, diagnostic of Alfisols and Ultisols."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: A soil survey laboratory analyzes a subsoil B horizon sample from 100 cm depth. The exchangeable cations extracted at pH 8.2 are: Ca2+ = 6.0 cmol(+)/kg, Mg2+ = 2.0 cmol(+)/kg, K+ = 0.5 cmol(+)/kg, Na+ = 0.1 cmol(+)/kg, and exchangeable acidity (Al3+ + H+) = 16.4 cmol(+)/kg. 1) Calculate the Cation Exchange Capacity (CEC) by sum of cations; 2) Calculate the Percentage Base Saturation (%BS); and 3) Classify whether the soil order is an Alfisol or an Ultisol.\nSolution: 1) Sum of Bases = Ca + Mg + K + Na = 6.0 + 2.0 + 0.5 + 0.1 = 8.6 cmol(+)/kg. Total CEC = Sum of Bases + Acidity = 8.6 + 16.4 = 25.0 cmol(+)/kg. 2) Base Saturation (%BS) = (Sum of Bases / CEC) x 100 = (8.6 / 25.0) x 100 = 34.4%. 3) Classification: Because the diagnostic subsoil has an argillic horizon with a Base Saturation of 34.4% (which is strictly less than the critical 35% threshold), the soil is classified as an Ultisol."
                    ),
                    commonExamQuestions = listOf(
                        "List the 12 USDA Soil Orders, state the diagnostic horizon or property defining each, and identify which order represents high-swelling 2:1 clays.",
                        "Differentiate an Alfisol from an Ultisol based on subsoil base saturation percentage and discuss their liming requirements.",
                        "Outline the 8 USDA Land Capability Classes and describe the management restrictions of Class IV versus Class I."
                    ),
                    practicalTakeaway = "On swelling Vertisols, install broadbed and furrow (BBF) systems to drain surface water from raised planting beds during heavy rains, avoiding root asphyxiation.",
                    authoritativeTextbookRef = "The Nature and Properties of Soils (Brady & Weil, Ch. 3); Keys to Soil Taxonomy (USDA NRCS, 13th Ed.)"
                )
            ),
            examFocusPoints = listOf(
                "Diagnostic criteria and formative elements for all 12 USDA Soil Orders",
                "Alfisols (%BS ≥ 35%) vs. Ultisols (%BS < 35%) mathematical distinction",
                "Vertisol smectite dynamics, slickensides, and tillage moisture windows",
                "Land Capability Classes (I to VIII) and conservation subclasses (e, w, s, c)"
            ),
            practicalFieldApplications = listOf(
                "Determining soil textural ribbon lengths and hand-texturing classes in the field",
                "Delineating contour conservation bunds and grass strips based on Land Capability Classes"
            ),
            recommendedTextbookIds = listOf("book_brady_weil_nature_soils", "book_usda_keys_soil_taxonomy")
        ),
        CurriculumTopic(
            id = "soil_sci_microbiology",
            pillar = CurriculumPillarType.SOIL_SCIENCE,
            code = "2.c",
            title = "Soil Microbiology",
            summary = "Rhizosphere microbial ecology, mycorrhizal symbioses, biological nitrogen fixation, carbon and nitrogen cycling, organic matter humification, and mineralization-immobilization dynamics.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "ss_microbial_ecology",
                    code = "i",
                    title = "Biological N-Fixation, Mycorrhizae & Nutrient Cycles",
                    coreDefinition = "The metabolic activities of soil bacteria, fungi, actinomycetes, and microfauna driving organic matter humification and biogeochemical nutrient transformations in the rhizosphere.",
                    keyMechanismsAndFormulas = listOf(
                        "Critical Soil C:N Ratio Dynamics: When organic amendments have C:N > 30:1 (e.g., cereal straw 80:1, sawdust 400:1), heterotrophic microbes immobilize all available mineral soil N (NH4+ and NO3-), causing temporary plant nitrogen deficiency ('nitrogen drag'). When C:N < 20:1 (e.g., legume residues 15:1, poultry manure 10:1), net mineralization releases plant-available NH4+.",
                        "Two-Step Nitrification Oxidation: Step 1 (Ammonia oxidation by Nitrosomonas / Nitrosococcus): 2NH4+ + 3O2 -> 2NO2- + 4H+ + 2H2O + Energy; Step 2 (Nitrite oxidation by Nitrobacter / Nitrospira): 2NO2- + O2 -> 2NO3- + Energy. Note: Produces 2 moles of H+ per mole of ammonium, biologically acidifying soil.",
                        "Biological Nitrogen Fixation Enzymology: Atmospheric N2 reduced to NH3 by bacterial nitrogenase complex (dinitrogenase reductase with Fe-protein + dinitrogenase with Mo-Fe-protein). Requires 16 ATP per N2 molecule reduced.",
                        "Denitrification Gaseous Loss: Anaerobic respiratory reduction of nitrate under waterlogging: NO3- -> NO2- -> NO -> N2O (greenhouse gas) -> N2."
                    ),
                    detailedElaboration = "Soil microorganisms constitute the dynamic biological engine of soil fertility, cycling carbon, nitrogen, phosphorus, and sulfur.\n\nRhizosphere Ecology & Root Exudates: The rhizosphere (soil zone 1–2 mm surrounding plant roots) supports microbial populations 10 to 100 times denser than bulk soil. Roots exude 10–25% of photosynthetically fixed carbon as mucilage, organic acids (malate, citrate), sugars, and secondary metabolites. In low-phosphorus soils, roots exude citrate and oxalate, which chelate Fe3+ and Al3+, solubilizing insoluble mineral phosphates.\n\nMycorrhizal Symbioses: Over 80% of terrestrial plant species form mutualistic associations with Arbuscular Mycorrhizal Fungi (AMF, phylum Glomeromycota). AMF hyphae penetrate root cortical cells, forming highly branched intracellular haustoria termed arbuscules (the site of nutrient exchange). Extra-radical hyphae extend several decimeters into the soil, vastly increasing effective root absorptive surface area. AMF hyphae secrete glomalin (a persistent hydrophobic glycoprotein that binds soil particles into stable water-resistant macroaggregates) and produce phosphatase enzymes that hydrolyze organic phosphorus, increasing P uptake by 200–500% in phosphorus-fixing soils.\n\nActinomycetes & Humification: Actinomycetes (filamentous bacteria e.g., Streptomyces) decompose resistant recalcitrant organic polymers (cellulose, chitin, lignin) in neutral to alkaline soils. They synthesize geosmin (the volatile compound giving soil its characteristic fresh earthy aroma) and produce broad-spectrum antimicrobial compounds that suppress plant pathogens.",
                    biologicalAndChemicalDetails = listOf(
                        "Frankia Actinorrhizal Symbiosis: Non-leguminous woody shrubs (Alnus, Casuarina) forming nitrogen-fixing root nodules with Frankia actinomycetes; vesicles encapsulate nitrogenase within specialized lipid envelopes to protect against oxygen.",
                        "Glomalin-Related Soil Protein (GRSP): Heat-stable glycoprotein constituting up to 15–20% of soil organic carbon in undisturbed soils, critically stabilizing 1–2 mm soil macroaggregates against water droplet dispersion."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "Rhizobium Nodule Quality Assessment Protocol: Carefully excavate 5 representative flowering legume plants with root balls intact. Soak roots in a bucket of clean water to gently wash away clay without stripping nodules. Count total nodules and note location (crown root nodules indicate effective seed inoculation; lateral root nodules indicate native soil strains). Slice 10 large nodules open with a clean razor blade: bright ruby-red/pink interior confirms active nitrogenase and leghaemoglobin; white/pale green nodules indicate ineffective non-fixing strains.",
                        "Trichoderma Bio-Fungicide Soil Inoculation: Mix 1 kg of commercial Trichoderma asperellum / T. harzianum carrier formulation (10⁸ CFU/g) into 100 kg of well-rotted farmyard manure or compost. Moisten to 50% moisture, cover with a tarp under shade for 7 days to allow fungal mycelial multiplication. Broadcast onto seedbeds at 500 kg/ha and rake into top 5 cm to biological control soil-borne damping off (Pythium, Rhizoctonia, Fusarium)."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Nitrogen Immobilization Stunting: Uniform yellowing (chlorosis) of cereal crops occurring 2 to 4 weeks after incorporating fresh high-carbon residues (e.g., dry maize stover or wheat straw) without supplemental starter nitrogen.",
                        "Denitrification Leaf Yellowing: Rapid yellowing of lower crop leaves following 48–72 hours of field soil flooding/waterlogging during warm weather (anaerobic bacteria rapidly reduce all soil nitrate into gaseous N2O and N2)."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: A farmer incorporates 4,000 kg/ha of dry wheat straw containing 45.0% Carbon and 0.50% Nitrogen into a field. Soil heterotrophic microbes require 1 kg of Nitrogen for every 10 kg of Carbon incorporated into their biomass (microbial C:N = 10:1) and operate at an average carbon assimilation efficiency of 35% (35% of carbon is built into cells, 65% respired as CO2). Calculate: 1) Total Carbon and Nitrogen added by the straw; 2) The straw C:N ratio; 3) The microbial nitrogen requirement; and 4) The net nitrogen balance (will there be net mineralization or net immobilization?).\nSolution: 1) Carbon added = 4,000 kg x 0.45 = 1,800 kg C. Nitrogen added = 4,000 kg x 0.0050 = 20 kg N. 2) Straw C:N = 1,800 / 20 = 90:1. 3) Microbial Assimilation: Microbial biomass carbon formed = 1,800 kg C x 0.35 = 630 kg microbial C. At a microbial C:N of 10:1, Nitrogen needed = 630 kg C / 10 = 63 kg N. 4) Net Nitrogen Balance = N added - N needed = 20 kg N - 63 kg N = -43 kg N. Conclusion: There is a severe net nitrogen deficit of 43 kg N/ha; microbes will immobilize 43 kg of mineral soil nitrogen, starving the subsequent crop unless 40–50 kg N/ha of starter fertilizer is applied."
                    ),
                    commonExamQuestions = listOf(
                        "Explain what occurs to plant-available mineral nitrogen when high-carbon residue (C:N 80:1) is incorporated into soil versus legume residue (C:N 15:1).",
                        "Detail the two biochemical steps of nitrification, naming the specific bacterial genera responsible and explaining why nitrification acidifies soil.",
                        "Describe the symbiotic association between Arbuscular Mycorrhizal Fungi (AMF) and plant roots and explain how extra-radical hyphae enhance phosphorus uptake."
                    ),
                    practicalTakeaway = "When plowing under high-carbon cereal straw or wood chips, always apply 25–35 kg N/ha of starter urea or CAN to feed soil microbes and avert severe crop nitrogen drag.",
                    authoritativeTextbookRef = "Soil Microbiology, Ecology and Biochemistry (Paul, Ch. 6, 9 & 11); The Nature and Properties of Soils (Brady & Weil, Ch. 11)"
                )
            ),
            examFocusPoints = listOf(
                "Critical C:N ratio thresholds: >30:1 (immobilization) vs. <20:1 (mineralization)",
                "Nitrification biochemistry, stoichiometry, and soil acidification (2 H+ per NH4+)",
                "Biological nitrogen fixation enzymology (Mo-Fe nitrogenase) and oxygen sensitivity",
                "Arbuscular Mycorrhizal Fungi (AMF) glomalin production and phosphorus uptake"
            ),
            practicalFieldApplications = listOf(
                "Performing field legume nodule scoring using razor blade bisection",
                "Compost tea and beneficial Trichoderma microbial mass inoculation techniques"
            ),
            recommendedTextbookIds = listOf("book_soil_microbiol_paul", "book_brady_weil_nature_soils")
        ),
        CurriculumTopic(
            id = "soil_sci_physics",
            pillar = CurriculumPillarType.SOIL_SCIENCE,
            code = "2.d",
            title = "Soil Physics",
            summary = "Soil physical properties: particle size distribution, Stokes' law sedimentation, soil structure, bulk density, porosity, soil water potential components, and Darcy's law.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "ss_physical_properties",
                    code = "i",
                    title = "Texture, Structure, Bulk Density & Soil Water",
                    coreDefinition = "The mechanical, volumetric, and thermodynamic relationships governing the solid mineral matrix, pore geometry, air exchange, and water transport in soils.",
                    keyMechanismsAndFormulas = listOf(
                        "Stokes' Law Sedimentation Velocity: v = (2/9) x [r² g (ρ_s - ρ_w)] / η, where v is settling velocity (cm/s), r is particle radius (cm), g is gravitational acceleration (981 cm/s²), ρ_s is particle density (~2.65 g/cm³), ρ_w is water density (~1.0 g/cm³), and η is water dynamic viscosity (~0.01 poise at 20°C).",
                        "Bulk Density (Db) & Particle Density (Dp): Db = Dry Soil Mass (g) / Total Core Volume (cm³). Particle Density Dp is typically assumed at 2.65 g/cm³ (density of quartz).",
                        "Total Porosity (f %): Total Porosity (%) = [1 - (Db / Dp)] x 100. Typical agricultural soils possess 45–55% total porosity.",
                        "Soil Water Potential (Ψ_m) & Available Water Capacity (AWC): AWC = Field Capacity (FC, -33 kPa or -0.33 bar) - Permanent Wilting Point (PWP, -1,500 kPa or -15 bar).",
                        "Darcy's Law for Saturated Water Flow: Q / A = q = -K_sat x (ΔH / L), where q is water flux density (cm/s), K_sat is saturated hydraulic conductivity, and ΔH/L is hydraulic gradient."
                    ),
                    detailedElaboration = "Soil physics governs root penetration mechanics, aeration, and water availability.\n\nSoil Texture vs. Structure: Texture is the permanent relative proportion of Sand (2.0–0.05 mm), Silt (0.05–0.002 mm), and Clay (<0.002 mm per USDA system). Stokes' law explains why sand settles out of water in seconds, silt settles in hours, and clay remains in suspension indefinitely unless flocculated by polyvalent cations (Ca2+). Soil structure describes the secondary spatial aggregation of primary particles into peds (granular, blocky, prismatic, platy). Granular structure in topsoil provides optimal balance between macropores (>0.08 mm, for drainage and air exchange) and micropores (<0.03 mm, for water retention).\n\nBulk Density & Root Impedance: Typical uncompacted loams have bulk densities of 1.20–1.40 g/cm³. When heavy tractors or livestock traffic compact soil, bulk density increases. Critical root-limiting thresholds are reached at 1.45–1.55 g/cm³ in clay soils, and 1.70–1.80 g/cm³ in sandy soils. Above these densities, soil penetration resistance exceeds 2.0 MPa (300 PSI), mechanically stopping root elongation.\n\nSoil Moisture States: Following saturation from heavy rain, gravitational water drains freely over 24–48 hours until downward drainage ceases; this state is Field Capacity (FC, -33 kPa). Plants extract water between FC and the Permanent Wilting Point (PWP, -1,500 kPa), at which point soil moisture tension exceeds the root's osmotic suction capacity, causing irreversible daytime and nighttime wilting.",
                    biologicalAndChemicalDetails = listOf(
                        "Bimodal Soil Pore Size Distribution: Macropores (structural pores between peds) drain water under gravity; Mesopores retain available water; Micropores (intra-aggregate textural pores) hold water so tightly (>1,500 kPa) that plant roots cannot extract it.",
                        "Soil Penetrometer Mechanics: Cone penetrometer measures mechanical impedance (resistance to penetration in MPa or PSI). Resistance >2.0 MPa halves root elongation; >3.0 MPa completely stops taproot penetration."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "Undisturbed Core Bulk Density Sampling Protocol: Drive a stainless steel core sampler of known volume (e.g., 5.0 cm diameter x 5.0 cm height = 98.17 cm³ volume) vertically into the horizon face without shattering structure. Carefully trim excess soil flush with core ends using a flat blade. Transfer soil into a tin; oven dry at 105°C for 24 hours until constant weight. Weigh dry soil mass. Calculate: Db = Dry Mass (g) / 98.17 cm³.",
                        "Tensiometer Installation & Irrigation Triggering: Fill tensiometer tube with de-aired distilled water and insert into soil root zone (e.g., 30 cm depth for vegetable crops), ensuring tight ceramic cup contact with soil. As soil dries, matric suction pulls water out of ceramic cup, creating a vacuum measured on the gauge. Trigger drip irrigation when gauge reaches 25–35 kPa in sandy loams, or 40–55 kPa in clays."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Plow Pan Compaction Layer: Thin, dense horizontal layer of soil at 20–25 cm depth exhibiting platy structure, high penetrometer resistance (>300 PSI), and severe horizontal root deflection.",
                        "Surface Crusting / Slaking: Dense, hard, brittle 2–5 mm surface crust formed on low-organic matter silty soils following heavy raindrop kinetic impact, preventing seedling plumule emergence."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: An agronomist collects an undisturbed cylindrical soil core of 100.0 cm³ volume. The field moist weight is 165.0 g. After oven-drying at 105°C for 24 hours, the dry soil weighs 132.5 g. Assuming standard particle density Dp = 2.65 g/cm³, calculate: 1) Soil Bulk Density (Db); 2) Gravimetric Water Content (θg); 3) Volumetric Water Content (θv); and 4) Total Porosity (f %).\nSolution: 1) Bulk Density (Db) = Dry Mass / Core Volume = 132.5 g / 100.0 cm³ = 1.325 g/cm³. 2) Gravimetric Water Content (θg) = (Moist Mass - Dry Mass) / Dry Mass = (165.0 - 132.5) / 132.5 = 32.5 g / 132.5 g = 0.2453 = 24.53%. 3) Volumetric Water Content (θv) = θg x (Db / ρ_water) = 0.2453 x 1.325 = 0.325 = 32.50% (or 32.5 cm³ water per 100 cm³ soil). 4) Total Porosity = [1 - (Db / Dp)] x 100 = [1 - (1.325 / 2.65)] x 100 = [1 - 0.50] x 100 = 50.0%."
                    ),
                    commonExamQuestions = listOf(
                        "Derive and explain Stokes' Law equation for particle size sedimentation and state three fundamental assumptions of Stokes' law.",
                        "Define Field Capacity (FC), Permanent Wilting Point (PWP), and Available Water Capacity (AWC) in terms of soil matric potential in kilopascals.",
                        "Explain why clay soils possess higher total porosity than sandy soils, yet sandy soils exhibit superior drainage and aeration."
                    ),
                    practicalTakeaway = "Never cultivate or drive tractors on soil when it is at or above Field Capacity: wet soil particles lubricate under load, destroying macropores and creating indestructible compaction pans.",
                    authoritativeTextbookRef = "Soil Physics (Jury & Horton, Ch. 2, 3 & 4); The Nature and Properties of Soils (Brady & Weil, Ch. 4 & 5)"
                )
            ),
            examFocusPoints = listOf(
                "Stokes' Law sedimentation velocity formula and hydrometer textural analysis",
                "Bulk density, particle density, and total porosity calculations",
                "Soil moisture retention curve (matric potential vs. volumetric water content)",
                "Darcy's Law hydraulic gradient and saturated conductivity (Ksat)"
            ),
            practicalFieldApplications = listOf(
                "Measuring core bulk density using cylinder rings and drying ovens",
                "Installing and reading vacuum tensiometers for automated irrigation scheduling"
            ),
            recommendedTextbookIds = listOf("book_soil_physics_jury", "book_brady_weil_nature_soils")
        ),
        CurriculumTopic(
            id = "soil_sci_fertility",
            pillar = CurriculumPillarType.SOIL_SCIENCE,
            code = "2.e",
            title = "Soil Fertility and Management",
            summary = "The 17 essential plant nutrients, Liebig's Law of the Minimum, 4R nutrient stewardship, nutrient mobility in plants and soil, diagnostic deficiency symptoms, fertilizer formulation, and acid soil liming.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "ss_nutrient_management",
                    code = "i",
                    title = "Essential Nutrients, Deficiency Diagnoses & Liming",
                    coreDefinition = "The scientific management of the 17 essential plant nutrients to optimize economic crop yields while sustaining soil quality and preventing environmental eutrophication.",
                    keyMechanismsAndFormulas = listOf(
                        "Liebig's Law of the Minimum (Justus von Liebig): Crop yield is governed not by total resources available, but by the scarcest nutrient factor relative to plant demand (the shortest stave in the water barrel).",
                        "The 17 Essential Plant Nutrients: Structural from air/water: C, H, O. Primary Macronutrients: N, P, K. Secondary Macronutrients: Ca, Mg, S. Micronutrients: Fe, Mn, Zn, Cu, B, Mo, Cl, Ni.",
                        "Nutrient Mobility Rule: Mobile nutrients inside plant vascular phloem (N, P, K, Mg) translocate from older tissues to young growing tips during deficiency; therefore, symptoms appear first on lower/older leaves. Immobile nutrients (Ca, Fe, B, Cu) cannot translocate; deficiency symptoms appear first on terminal buds and young expanding leaves.",
                        "Acid Soil Liming Neutralization: CaCO3 (Calcitic lime) + 2H+ (exchangeable) -> Ca2+ (on colloid) + CO2 + H2O. Soluble toxic aluminum (Al3+) hydrolyzed and precipitated: Al3+ + 3OH- -> Al(OH)3 (insoluble gibbsite precipitate).",
                        "Calcium Carbonate Equivalent (CCE %): Relative acid-neutralizing power compared to pure CaCO3 (100%): Pure CaO = 179%; Ca(OH)2 = 136%; Dolomite CaMg(CO3)2 = 109%."
                    ),
                    detailedElaboration = "Soil fertility management requires understanding elemental chemical forms and root uptake mechanisms.\n\nPhosphorus Fixation Dynamics: In acidic tropical soils (pH < 5.5), phosphate anions (H2PO4-) react instantly with soluble Al3+ and Fe3+ ions, precipitating into insoluble variscite [AlPO4.2H2O] and strengite [FePO4.2H2O], or chemically adsorbing onto sesquioxide surfaces. In alkaline calcareous soils (pH > 7.5), phosphate reacts with excess calcium to precipitate into insoluble dicalcium phosphate and apatite. Maximum phosphate availability occurs in a narrow pH window between 6.0 and 6.8. Banding granulated phosphate fertilizers near the seed row minimizes contact with fixing soil minerals, tripling phosphorus use efficiency compared to broadcasting.\n\nAluminum Toxicity in Acid Soils: When soil pH drops below 5.2, aluminum mineral lattices dissolve, releasing hexaaquaaluminum [Al(H2O)6]3+ ions into the soil solution. Al3+ binds to root apical pectins and DNA in root meristems, inhibiting cell division within hours. Root systems become severely stunted, swollen, coralloid, and 'club-shaped', completely losing the ability to explore subsoil moisture and nutrients. Applying agricultural lime (CaCO3 or dolomite) elevates pH, generating hydroxyl ions (OH-) that neutralize exchangeable Al3+.\n\n4R Nutrient Stewardship: 1) Right Source (matching fertilizer chemistry to crop and soil); 2) Right Rate (calibrating based on laboratory soil test and target yield goal); 3) Right Time (synchronizing application with crop nutrient uptake curves e.g., split topdressing); 4) Right Place (banding starter fertilizers and incorporating volatile urea).",
                    biologicalAndChemicalDetails = listOf(
                        "Forms Absorbed by Roots: Nitrogen (NO3- and NH4+); Phosphorus (orthophosphates H2PO4- at pH < 7.2, HPO42- at pH > 7.2); Potassium (K+); Calcium (Ca2+); Magnesium (Mg2+); Sulfur (SO42-); Boron [B(OH)3]; Zinc (Zn2+); Iron (Fe2+); Molybdenum (MoO42-).",
                        "Bray-1 vs. Mehlich-3 vs. Olsen Phosphorus Extractions: Bray-1 (acid fluoride) suited for acid soils (pH < 6.5); Olsen (sodium bicarbonate at pH 8.5) suited for neutral to calcareous soils; Mehlich-3 (multi-element chelating extractant) widely used across diverse soil types."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "Soil Sampling Protocol for Laboratory Analysis: Divide farm into uniform management zones based on soil color, slope, and cropping history. Using an auger or spade, collect 15–20 sub-samples per zone in a zigzag pattern to a depth of 0–20 cm (topsoil). Remove surface debris. Combine sub-samples in a clean plastic bucket and mix thoroughly. Air-dry composite sample on clean paper under shade. Pack 500g of composite soil into an official soil sample bag, label clearly, and dispatch to an accredited testing laboratory.",
                        "Agricultural Lime Application Protocol: Broadcast agricultural limestone (ground so >50% passes through a 100-mesh sieve and 100% through a 10-mesh sieve) evenly across plowed land 4 to 8 weeks before planting. Thoroughly incorporate into top 15 cm via disc harrowing: liming materials are poorly soluble and must contact soil particles physically with moisture to neutralize exchangeable acidity."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Nitrogen Deficiency: V-shaped chlorosis beginning at tip and advancing down the midrib of older lower leaves, followed by general stunting.",
                        "Potassium Deficiency: Marginal chlorosis and scorching (necrosis) along the outer edges of older lower leaves; weak lodging-prone stems.",
                        "Iron Deficiency: Sharp, prominent interveinal chlorosis on the youngest upper leaves (veins remain dark green while interveinal areas turn bleach-white/yellow); common in high-pH calcareous soils.",
                        "Magnesium Deficiency: Inverted V-shaped interveinal chlorosis on older lower leaves with green veins and reddish-purple tinting on leaf margins."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: A soil test recommendation prescribes 120 kg N, 60 kg P2O5, and 40 kg K2O per hectare for a hybrid maize field. The agronomist has available Diammonium Phosphate (DAP, 18-46-0), Urea (46-0-0), and Muriate of Potash (MOP, 0-0-60). Calculate the exact mass (in kg/ha) of each commercial fertilizer product required to supply the prescribed nutrients.\nSolution:\nStep 1: Satisfy Phosphorus using DAP (18-46-0):\nMass of DAP = Prescribed P2O5 / (% P2O5 / 100) = 60 / 0.46 = 130.43 kg/ha DAP.\nStep 2: Calculate Nitrogen supplied by DAP:\nN from DAP = 130.43 kg DAP x 0.18 = 23.48 kg N.\nStep 3: Calculate remaining Nitrogen needed from Urea (46-0-0):\nRemaining N = 120 kg - 23.48 kg = 96.52 kg N.\nMass of Urea = 96.52 / 0.46 = 209.83 kg/ha Urea.\nStep 4: Satisfy Potassium using MOP (0-0-60):\nMass of MOP = Prescribed K2O / (% K2O / 100) = 40 / 0.60 = 66.67 kg/ha MOP.\nSummary: Apply 130 kg DAP + 67 kg MOP at planting (basal), and topdress 210 kg Urea split at knee-height and tasseling."
                    ),
                    commonExamQuestions = listOf(
                        "State Liebig's Law of the Minimum and explain why applying compound NPK fertilizer to a zinc-deficient soil fails to increase crop yield.",
                        "Explain why potassium deficiency symptoms manifest on older lower leaves while iron deficiency appears on the youngest upper leaves.",
                        "Write the chemical reactions showing how agricultural limestone (CaCO3) neutralizes exchangeable aluminum (Al3+) and hydrogen (H+) ions in acidic soils."
                    ),
                    practicalTakeaway = "Always test soil pH before purchasing expensive fertilizers: in acidic soils (pH < 5.2), up to 75% of applied phosphorus fertilizer is chemically locked up and wasted unless agricultural lime is applied first.",
                    authoritativeTextbookRef = "Soil Fertility and Fertilizers (Havlin, Tisdale, Nelson, Beaton, Ch. 4, 5, 6 & 9); The Nature and Properties of Soils (Brady & Weil, Ch. 8 & 9)"
                )
            ),
            examFocusPoints = listOf(
                "Liebig's Law of the Minimum and 4R Nutrient Stewardship",
                "Phloem mobile vs. immobile nutrient deficiency symptom distributions",
                "Phosphorus fixation chemistry in acidic (Al/Fe) vs. alkaline (Ca) soils",
                "Multi-nutrient fertilizer grade blend calculations (NPK sourcing)",
                "Aluminum toxicity mechanisms and lime requirement equations"
            ),
            practicalFieldApplications = listOf(
                "Calculating fertilizer blend formulations from straight carriers (Urea, DAP, MOP)",
                "Conducting soil sampling using zigzag composite protocols"
            ),
            recommendedTextbookIds = listOf("book_havlin_soil_fertility", "book_brady_weil_nature_soils")
        ),
        CurriculumTopic(
            id = "soil_sci_chemistry",
            pillar = CurriculumPillarType.SOIL_SCIENCE,
            code = "2.f",
            title = "Soil Chemistry / Chemical Properties of Soil",
            summary = "Colloidal chemistry, cation and anion exchange capacity (CEC/AEC), base saturation, diffuse double layer, buffering capacity, salinity, and sodic soil reclamation.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "ss_colloidal_chemistry",
                    code = "i",
                    title = "CEC, Base Saturation, Salinity & Sodicity",
                    coreDefinition = "The surface electrochemistry and ionic exchange equilibria occurring on inorganic clay minerals and organic humus colloids governing nutrient retention, pH buffering, salinity, and sodicity.",
                    keyMechanismsAndFormulas = listOf(
                        "Cation Exchange Capacity (CEC): Measured in cmol(+)/kg dry soil or meq/100g. Arises from Permanent Charge (isomorphous substitution in clay crystal lattices e.g., Al3+ for Si4+ in tetrahedral sheets, or Mg2+ for Al3+ in octahedral sheets) and Variable pH-Dependent Charge (deprotonation of -COOH and -OH groups on humus and edges of kaolinite).",
                        "Percentage Base Saturation (%BS): %BS = [(Ca2+ + Mg2+ + K+ + Na+) / CEC] x 100. Soils with %BS > 80% are fertile and neutral; %BS < 50% indicates significant acidity.",
                        "Lyotropic / Affinity Series of Cation Adsorption: Al3+ > Ca2+ > Mg2+ > K+ = NH4+ > Na+. Higher ionic valence and smaller hydrated radius bind with greater electrostatic strength to colloidal exchange surfaces.",
                        "Sodium Adsorption Ratio (SAR) & Exchangeable Sodium Percentage (ESP): SAR = Na+ / sqrt[(Ca2+ + Mg2+) / 2]; ESP (%) = (Exchangeable Na+ / CEC) x 100. Sodic soil defined by ESP > 15% or SAR > 13 with pH > 8.5.",
                        "Classification of Salt-Affected Soils: 1) Saline: ECe > 4.0 dS/m, ESP < 15%, pH < 8.5; 2) Sodic: ECe < 4.0 dS/m, ESP > 15%, pH > 8.5; 3) Saline-Sodic: ECe > 4.0 dS/m, ESP > 15%, pH variable."
                    ),
                    detailedElaboration = "Soil colloids (clay and humus) represent the chemically reactive core of agricultural soils.\n\nColloidal Charge & Double Layer: Clay particles carry permanent negative structural charges. Organic humus has extreme pH-dependent CEC (100–300 cmol/kg): as soil pH rises from 4.5 to 7.0, carboxylic and phenolic functional groups dissociate H+ protons, releasing negative exchange sites that attract essential basic cations. The Gouy-Chapman Diffuse Double Layer describes the cloud of counter-cations surrounding the negatively charged clay surface. When polyvalent cations (Ca2+, Mg2+) dominate, the diffuse layer compresses, allowing clay particles to approach within van der Waals attraction distances, forming stable flocculated aggregates.\n\nSaline vs. Sodic Soil Reclamation:\n1) Saline Soils (White Alkali): Contain excess soluble salts (chlorides and sulfates of Ca, Mg, Na). The high ionic concentration lowers the soil osmotic water potential, causing physiological drought (crop cannot extract water even from moist soil). Reclamation is straightforward: flush and leach salts through the root zone with high-volume, low-salt irrigation water, provided subsoil drainage is adequate.\n2) Sodic Soils (Black Alkali): Dominated by exchangeable sodium (Na+) with low total salts. Because Na+ is monovalent with a huge hydrated ionic radius, it expands the diffuse double layer, pushing clay particles apart (dispersion). Upon wetting, clay aggregates explode into individual platelets, completely clogging all soil macropores. The soil turns into an impermeable black slurry when wet and dries into brick-like columns. Adding freshwater alone causes instantaneous aggregate collapse. Reclamation strictly requires applying Agricultural Gypsum (CaSO4.2H2O): the soluble Ca2+ displaces adsorbed Na+ from colloids (forming soluble Na2SO4), flocculating the clay and restoring soil structure, after which the displaced sodium can be leached out.",
                    biologicalAndChemicalDetails = listOf(
                        "Gypsum Requirement Reaction: 2Na+-Clay + CaSO4.2H2O -> Ca2+-Clay + Na2SO4 + 2H2O. Displaced sodium sulfate is leached below the root zone.",
                        "Anion Exchange Capacity (AEC): Highly weathered tropical soils (Oxisols) dominated by iron and aluminum sesquioxides develop net positive surface charges under acidic pH (pH < 4.5), reversibly holding nitrate (NO3-) and sulfate (SO42-) against leaching."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "Laboratory Sodic Soil Reclamation (Gypsum Requirement Calculation): Determine soil CEC and Exchangeable Sodium. Target reducing ESP from initial value (e.g., 25%) down to a safe 5% threshold. Calculate Gypsum Requirement: GR (tonnes/ha of pure CaSO4.2H2O) = 0.086 x Db (g/cm³) x Depth (cm) x Area Factor x [CEC x (Initial ESP - Target ESP) / 100]. Incorporate calculated gypsum into top 15 cm and apply heavy leaching irrigation.",
                        "Measuring Saturated Paste Electrical Conductivity (ECe): Weigh 200g of dry soil. Slowly add distilled water while stirring with a spatula until a glistening, saturated soil paste is formed (paste flows slightly when container is tipped). Let stand for 1 hour. Extract soil solution using a vacuum Buchner funnel. Measure electrical conductivity of the extract with a calibrated conductivity meter at 25°C in dS/m."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Black Alkali Crust: Dark brown or black organic surface film formed on sodic soils when dispersed, dissolved organic matter is wicked upward by capillary evaporation under high pH (>8.5).",
                        "White Alkali Surface Efflorescence: White, crystalline salt crusts visible on furrow ridges during dry weather, characteristic of saline soils.",
                        "Salt Leaf Margin Necrosis: Scorching and necrotic burning of leaf tips and margins on older leaves, caused by toxic cellular sodium or chloride accumulation."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: A soil laboratory report shows the following cation concentrations extracted from an agricultural soil: Ca2+ = 12.0 cmol(+)/kg, Mg2+ = 4.0 cmol(+)/kg, K+ = 1.2 cmol(+)/kg, Na+ = 0.8 cmol(+)/kg, and Exchangeable Acidity (Al3+ + H+) = 2.0 cmol(+)/kg. Calculate: 1) Total Cation Exchange Capacity (CEC); 2) Percentage Base Saturation (%BS); and 3) Exchangeable Sodium Percentage (ESP). State whether the soil is sodic.\nSolution:\n1) Total CEC = Ca + Mg + K + Na + Acidity = 12.0 + 4.0 + 1.2 + 0.8 + 2.0 = 20.0 cmol(+)/kg.\n2) Sum of Bases = 12.0 + 4.0 + 1.2 + 0.8 = 18.0 cmol(+)/kg.\nPercentage Base Saturation (%BS) = (18.0 / 20.0) x 100 = 90.0%.\n3) Exchangeable Sodium Percentage (ESP) = (Exchangeable Na / CEC) x 100 = (0.8 / 20.0) x 100 = 4.0%.\nConclusion: The soil is NOT sodic (ESP = 4.0%, well below the 15% threshold). With %BS at 90.0% and low exchangeable acidity, this is a highly fertile, well-buffered agricultural soil."
                    ),
                    commonExamQuestions = listOf(
                        "Differentiate permanent charge from pH-dependent charge on soil colloids, identifying the specific mineral and organic sources of each.",
                        "Explain the Lyotropic affinity series and explain why trivalent aluminum (Al3+) binds tighter to clay colloids than monovalent sodium (Na+).",
                        "Explain why leaching a sodic soil with fresh water before applying gypsum ruins soil physical permeability."
                    ),
                    practicalTakeaway = "Never apply fresh water alone to reclaim a sodic soil: without calcium from gypsum to displace sodium and flocculate clays, the soil aggregates instantly disperse into an impenetrable seal.",
                    authoritativeTextbookRef = "The Nature and Properties of Soils (Brady & Weil, Ch. 8 & 10); Soil Chemistry (Bohn, McNeal, O'Connor)"
                )
            ),
            examFocusPoints = listOf(
                "Permanent structural charge vs. pH-dependent variable charge on humus",
                "Calculations of CEC, Percentage Base Saturation (%BS), SAR, and ESP",
                "Lyotropic adsorption series (Al3+ > Ca2+ > Mg2+ > K+ > Na+)",
                "Chemical reclamation protocols for Saline vs. Sodic vs. Saline-Sodic soils"
            ),
            practicalFieldApplications = listOf(
                "Calculating exact gypsum tonnage required to reclaim sodic farm acreage",
                "Operating hand-held EC and pH meters on saturated soil pastes"
            ),
            recommendedTextbookIds = listOf("book_brady_weil_nature_soils", "book_havlin_soil_fertility")
        )
    )
}
