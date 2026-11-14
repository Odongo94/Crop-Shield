package com.example.data.science.curriculum

enum class AgronomyStudyVolume(
    val volumeNumber: Int,
    val yearLabel: String,
    val volumeTitle: String,
    val overview: String
) {
    YEAR_1(
        volumeNumber = 1,
        yearLabel = "Year 1",
        volumeTitle = "Volume 1: Bachelor's Year 1 — Foundations",
        overview = "Core biological, pedological, ecological, and economic principles anchoring undergraduate Agronomy & Agricultural Sciences."
    ),
    YEAR_2(
        volumeNumber = 2,
        yearLabel = "Year 2",
        volumeTitle = "Volume 2: Bachelor's Year 2 — Applied Sciences",
        overview = "Breeding, crop protection, horticulture, irrigation engineering, and farm enterprise management for commercial productivity."
    ),
    YEAR_3(
        volumeNumber = 3,
        yearLabel = "Year 3",
        volumeTitle = "Volume 3: Bachelor's Year 3 — Advanced & Frontier Systems",
        overview = "Sustainability transitions, agricultural biotechnology, climate-smart resilience, and precision digital technologies."
    )
}

data class AgronomyRevisionQa(
    val question: String,
    val modelAnswer: String
)

data class AgronomyPackSubject(
    val id: String,
    val courseCode: String,
    val volume: AgronomyStudyVolume,
    val subjectName: String,
    val credits: Int,
    val executiveSummary: String,
    val coreConcepts: List<String>,
    val keyFormulasAndMetrics: List<String>,
    val fieldAndLabProtocols: List<String>,
    val assignmentPrompts: List<String>,
    val examRevisionQas: List<AgronomyRevisionQa>
)

object AgronomyDegreePackCatalog {

    val allSubjects: List<AgronomyPackSubject> = listOf(
        // =====================================================================
        // VOLUME 1: BACHELOR'S YEAR 1
        // =====================================================================
        AgronomyPackSubject(
            id = "y1_crop_production",
            courseCode = "AGR 101",
            volume = AgronomyStudyVolume.YEAR_1,
            subjectName = "Crop Production",
            credits = 4,
            executiveSummary = "Principles of field crop establishment, land preparation, planting geometry, phenological staging, and harvest index optimization across cereals, legumes, roots, and cash crops.",
            coreConcepts = listOf(
                "Seed quality triad: analytical purity (%), germination capacity (>=85% certified maize), and seed vigor / thousand-grain weight (TGW).",
                "Tillage regimes: conventional moldboard/disc plowing vs. conservation minimum tillage and subsoiling to break hardpans.",
                "Plant population geometry: inter-row and intra-row spacing governing light interception, LAI, and intra-specific competition.",
                "Phenological scales (BBCH / Zadoks): matching weeding, topdressing, and water supply to sensitive V6-tasseling-silking windows."
            ),
            keyFormulasAndMetrics = listOf(
                "Plant Population (/ha) = 10,000 m² / (Inter-row spacing (m) × Intra-row spacing (m)) × Plants per hill",
                "Seed Rate (kg/ha) = [Target Population (/ha) × TGW (g)] / [Purity (%) × Germination (%) × 100]",
                "Harvest Index (HI) = Economic Yield (Grain dry weight) / Total Above-Ground Biological Biomass × 100%"
            ),
            fieldAndLabProtocols = listOf(
                "Conduct a 100-seed rolled-towel germination test at 25°C; count normal seedlings on Day 4 and Day 7.",
                "Calibrate planting depth to 2–3× seed diameter (3–5 cm for maize/beans, 1–2 cm for small-seeded brassicas)."
            ),
            assignmentPrompts = listOf(
                "Calculate the exact seed quantity (kg) needed for 2.5 ha of hybrid maize spaced at 75 cm × 30 cm with 1 seed/hill, TGW = 320 g, purity = 98%, germination = 90%.",
                "Compare yield components (ears/m², kernels/ear, TGW) under drought during vegetative V4 vs. anthesis R1."
            ),
            examRevisionQas = listOf(
                AgronomyRevisionQa(
                    question = "A farmer plants beans at 50 cm × 10 cm (2 seeds/hill). What is the theoretical plant population per hectare?",
                    modelAnswer = "Area per hill = 0.50 m × 0.10 m = 0.05 m². Hills/ha = 10,000 / 0.05 = 200,000 hills/ha. With 2 seeds/hill = 400,000 plants/ha."
                ),
                AgronomyRevisionQa(
                    question = "Why is subsoiling recommended every 3–4 seasons on mechanized clay-loam fields?",
                    modelAnswer = "Disc harrows and tractor wheel traffic form a compacted plow pan at 15–25 cm depth, restricting root penetration, lowering infiltration, and worsening waterlogging."
                )
            )
        ),
        AgronomyPackSubject(
            id = "y1_soil_science",
            courseCode = "SOL 102",
            volume = AgronomyStudyVolume.YEAR_1,
            subjectName = "Soil Science",
            credits = 4,
            executiveSummary = "Soil formation factors (CLORPT), mineral texture & structure, bulk density, cation exchange capacity (CEC), soil reaction (pH), and macronutrient/micronutrient cycling.",
            coreConcepts = listOf(
                "Pedogenesis & horizons (O, A, E, B, C, R) and USDA textural triangle (Sand 2.0–0.05 mm, Silt 0.05–0.002 mm, Clay <0.002 mm).",
                "Bulk density (Db) vs. Particle density (Dp ≈ 2.65 g/cm³) and total pore space governing aeration and root impedance.",
                "Soil colloids (2:1 smectite, 1:1 kaolinite, humus) providing negative charge sites for Ca²⁺, Mg²⁺, K⁺, NH₄⁺ retention.",
                "Soil acidity mechanics: active acidity (soil solution H⁺) vs. reserve/exchangeable acidity (Al³⁺ & H⁺ on colloids) requiring lime buffering."
            ),
            keyFormulasAndMetrics = listOf(
                "Porosity (%) = [1 - (Bulk Density Db / Particle Density 2.65)] × 100",
                "Base Saturation (%) = [(Ca²⁺ + Mg²⁺ + K⁺ + Na⁺) in cmolc/kg / CEC] × 100",
                "Lime Requirement (kg CaCO₃/ha) ≈ Exchangeable Acidity × Buffer Factor × Incorporation Depth Factor"
            ),
            fieldAndLabProtocols = listOf(
                "Composite soil sampling: collect 15–20 cores in a W-pattern at 0–20 cm depth, quarter-mix, air-dry, and sieve through 2 mm mesh.",
                "Hydrometer texture analysis (Bouyoucos method): disperse with sodium hexametaphosphate and read 40-second (silt+clay) & 2-hour (clay) readings."
            ),
            assignmentPrompts = listOf(
                "Diagnose why continuous DAP (18:46:0) lowers soil pH over 5 years and propose an integrated lime + organic matter remediation plan."
            ),
            examRevisionQas = listOf(
                AgronomyRevisionQa(
                    question = "A loam soil core has dry mass 132.5 g and volume 100 cm³. Calculate bulk density and total porosity.",
                    modelAnswer = "Db = 132.5 / 100 = 1.325 g/cm³. Porosity = [1 - (1.325 / 2.65)] × 100 = 50.0% pore space."
                ),
                AgronomyRevisionQa(
                    question = "Why is phosphorus strongly fixed below soil pH 5.2 and above pH 7.8?",
                    modelAnswer = "Below pH 5.2, soluble Fe³⁺ and Al³⁺ precipitate H₂PO₄⁻ into insoluble Fe/Al-phosphates; above pH 7.8, excess Ca²⁺ precipitates phosphate into insoluble calcium-phosphates."
                )
            )
        ),
        AgronomyPackSubject(
            id = "y1_plant_physiology",
            courseCode = "BOT 103",
            volume = AgronomyStudyVolume.YEAR_1,
            subjectName = "Plant Physiology",
            credits = 3,
            executiveSummary = "Water relations, transpiration cohesion-tension, C3/C4/CAM carbon assimilation, mineral transport, source-sink partitioning, and phytohormone regulation.",
            coreConcepts = listOf(
                "Water potential components: Ψw = Ψs + Ψp + Ψg + Ψm guiding root uptake from soil to xylem and stomatal transpiration.",
                "Photosynthetic pathways: C3 (wheat, beans, rice — Rubisco photorespiration), C4 (maize, sorghum, sugarcane — Kranz anatomy & PEP carboxylase), CAM (pineapple, sisal).",
                "Phloem translocation (Münch pressure-flow hypothesis) moving photoassimilates from mature source leaves to developing grain/fruit sinks.",
                "Plant growth regulators: Auxins (apical dominance), Gibberellins (stem elongation/germination), Cytokinins (cell division), ABA (drought stomatal closure), Ethylene (ripening/senescence)."
            ),
            keyFormulasAndMetrics = listOf(
                "Water Potential: Ψw = Ψs (solute, <=0) + Ψp (turgor pressure)",
                "Crop Growth Rate (CGR, g/m²/day) = (W₂ - W₁) / [Ground Area × (t₂ - t₁)]",
                "Water Use Efficiency (WUE) = Dry Biomass or Yield Produced (kg) / Evapotranspiration Water Used (m³)"
            ),
            fieldAndLabProtocols = listOf(
                "Measure relative water content (RWC = [(FW - DW) / (TW - DW)] × 100) on midday leaf discs to quantify drought stress.",
                "Use SPAD chlorophyll meter readings to correlate leaf N sufficiency before split topdressing."
            ),
            assignmentPrompts = listOf(
                "Explain physiologically why C4 maize outperforms C3 beans in high irradiance and 34°C tropical afternoons."
            ),
            examRevisionQas = listOf(
                AgronomyRevisionQa(
                    question = "What triggers stomatal closure during soil drying even before leaf wilting is visible?",
                    modelAnswer = "Drying roots synthesize Abscisic Acid (ABA), which travels via the xylem to guard cells, causing K⁺ and Cl⁻ efflux, loss of guard-cell turgor, and stomatal closure."
                )
            )
        ),
        AgronomyPackSubject(
            id = "y1_agroecology",
            courseCode = "AGE 104",
            volume = AgronomyStudyVolume.YEAR_1,
            subjectName = "Agroecology",
            credits = 3,
            executiveSummary = "Application of ecological principles to agricultural ecosystems: trophic food webs, biodiversity, biological nitrogen fixation, allelopathy, and push-pull habitat engineering.",
            coreConcepts = listOf(
                "Nutrient and energy cycling: primary producers, herbivores, parasitoids/predators, and saprophytic soil decomposers.",
                "Complementarity & facilitation in intercropping: Maize-Legume strip cropping and Land Equivalent Ratio (LER > 1.0).",
                "Push-Pull technology (ICIPE): Silverleaf Desmodium repels stemborer/FAW moths ('push') & suppresses Striga while Brachiaria/Napier traps oviposition ('pull').",
                "Ecological succession and disturbance management in annual vs. perennial agroforestry systems."
            ),
            keyFormulasAndMetrics = listOf(
                "Land Equivalent Ratio (LER) = (Intercrop Yield A / Sole Yield A) + (Intercrop Yield B / Sole Yield B)",
                "Shannon Diversity Index (H') = -Σ (pi × ln pi) for agroecosystem species evenness & richness"
            ),
            fieldAndLabProtocols = listOf(
                "Sample 20 quadrat plots across monocrop vs. polyculture fields to compute LER and beneficial predator-to-pest ratios.",
                "Score active pink leghemoglobin nodules on legume roots at 5 weeks after emergence."
            ),
            assignmentPrompts = listOf(
                "Design a 1-hectare smallholder agroecological layout integrating Desmodium push-pull, nitrogen-fixing hedgerows, and pollinator strips."
            ),
            examRevisionQas = listOf(
                AgronomyRevisionQa(
                    question = "If sole maize yields 4.0 t/ha, sole beans 1.6 t/ha, and an intercrop yields 3.4 t/ha maize + 0.8 t/ha beans, calculate LER and interpret it.",
                    modelAnswer = "LER = (3.4 / 4.0) + (0.8 / 1.6) = 0.85 + 0.50 = 1.35. An LER of 1.35 means intercropping delivers 35% higher land-use efficiency than separate sole stands."
                )
            )
        ),
        AgronomyPackSubject(
            id = "y1_agri_economics",
            courseCode = "AEC 105",
            volume = AgronomyStudyVolume.YEAR_1,
            subjectName = "Agricultural Economics",
            credits = 3,
            executiveSummary = "Microeconomic decision rules in agriculture: demand & supply elasticities, law of diminishing returns, production factor allocation, value chains, and price risk.",
            coreConcepts = listOf(
                "Classical Production Function (TPP, APP, MPP): Stages I, II (Rational Economic Zone), and III of input response.",
                "Profit-maximizing input rule: apply variable input (e.g., fertilizer) up to the point where Value of Marginal Product equals Marginal Factor Cost (VMP = MFC).",
                "Price & Income Elasticity of Demand explaining farm-gate price volatility (Cobweb theorem & inelastic food demand).",
                "Marketing margins, cooperative aggregation, warehouse receipt systems, and export contract standards."
            ),
            keyFormulasAndMetrics = listOf(
                "Profit Maximization: MPP × Price of Output (Py) = Price of Input (Px)  [VMP = MFC]",
                "Least-Cost Input Combination: MPP₁ / P₁ = MPP₂ / P₂",
                "Gross Margin (KES/ha) = Gross Revenue (Yield × Price) - Total Variable Costs"
            ),
            fieldAndLabProtocols = listOf(
                "Construct a farm-gate to wholesale price spread table for tomatoes across peak glut vs. dry-season scarcity."
            ),
            assignmentPrompts = listOf(
                "Using diminishing returns data for nitrogen on maize, determine the rational Stage II fertilizer dose when urea costs KES 4,500/50kg bag and maize sells at KES 45/kg."
            ),
            examRevisionQas = listOf(
                AgronomyRevisionQa(
                    question = "Why is Stage II of the classical production function called the Rational Zone?",
                    modelAnswer = "In Stage I, APP is still rising so increasing variable input boosts efficiency per unit; in Stage III, MPP is negative (extra input reduces total yield). Only Stage II balances positive declining marginal returns where VMP = MFC can maximize profit."
                )
            )
        ),

        // =====================================================================
        // VOLUME 2: BACHELOR'S YEAR 2
        // =====================================================================
        AgronomyPackSubject(
            id = "y2_genetics_breeding",
            courseCode = "GEN 201",
            volume = AgronomyStudyVolume.YEAR_2,
            subjectName = "Genetics & Plant Breeding",
            credits = 4,
            executiveSummary = "Mendelian & quantitative inheritance, heterosis, self- vs. cross-pollinated breeding pipelines, OPVs vs. F1 hybrids, participatory variety selection, and resistance breeding.",
            coreConcepts = listOf(
                "Qualitative monogenic traits vs. polygenic quantitative traits (yield, drought tolerance) governed by G × E interaction.",
                "Components of phenotypic variance: Vp = Vg + Ve + Vge and broad-sense vs. narrow-sense heritability (h² = Va / Vp).",
                "Breeding methods: Mass Selection, Pedigree & Bulk methods (self-pollinators like beans/wheat), Recurrent Selection & Three-Way Cross Hybrids (cross-pollinated maize).",
                "Vertical (gene-for-gene, R-gene) vs. Horizontal (polygenic, durable) disease resistance strategies."
            ),
            keyFormulasAndMetrics = listOf(
                "Broad-Sense Heritability (H²) = Genetic Variance (Vg) / Total Phenotypic Variance (Vp)",
                "Genetic Gain per Cycle (ΔG) = Selection Intensity (i) × Narrow-Sense Heritability (h²) × Phenotypic Std Dev (σp)",
                "Mid-Parent Heterosis (%) = [(F₁ - MP) / MP] × 100"
            ),
            fieldAndLabProtocols = listOf(
                "Perform controlled emasculation and hand-pollination bagging on self-pollinated legume or solanaceous flowers before anther dehiscence.",
                "Lay out a Randomized Complete Block Design (RCBD) multi-location variety trial with 3–4 replications."
            ),
            assignmentPrompts = listOf(
                "Contrast the seed-saving advantages of Open Pollinated Varieties (OPVs) against the yield heterosis and F2 segregation penalty of hybrid maize."
            ),
            examRevisionQas = listOf(
                AgronomyRevisionQa(
                    question = "Why should farmers NOT recycle seed harvested from F1 hybrid maize?",
                    modelAnswer = "F1 hybrids are heterozygous across key loci. In the F2 generation, Mendelian segregation and loss of dominance/overdominance cause severe inbreeding depression, uneven maturity, and 20–35% yield drop."
                )
            )
        ),
        AgronomyPackSubject(
            id = "y2_crop_protection",
            courseCode = "CPR 202",
            volume = AgronomyStudyVolume.YEAR_2,
            subjectName = "Crop Protection",
            credits = 4,
            executiveSummary = "Plant pathology, agricultural entomology, weed biology, Disease Triangle epidemiology, Economic Injury Levels (EIL), and IRAC/FRAC/HRAC resistance rotation in IPM.",
            coreConcepts = listOf(
                "Plant Disease Triangle: Susceptible Host × Virulent Pathogen (Fungi, Oomycetes, Bacteria, Viruses, Nematodes) × Conducive Environment.",
                "Insect metamorphosis (Holometabolous Egg-Larva-Pupa-Adult vs. Hemimetabolous) and feeding guilds (chewing vs. piercing-sucking vectors).",
                "Critical Period of Weed Control (CPWC): keeping cereals/horticulture weed-free during the first 25–45 days after emergence.",
                "IPM Pyramid & Resistance Stewardship: rotate IRAC/FRAC/HRAC Mode-of-Action codes, observe statutory PHIs, and protect natural enemies."
            ),
            keyFormulasAndMetrics = listOf(
                "Economic Injury Level (EIL) = C / (V × I × D × K)  [C=control cost, V=market value, I=injury/pest, D=damage/injury, K=control efficacy]",
                "Disease Severity Index (DSI %) = [Σ(Score × Plants in Score) / (Total Plants × Max Scale Score)] × 100",
                "Knapsack Active Dilution: Tank Dose = (Recommended Rate/ha × Tank Volume L) / Spray Volume (L/ha)"
            ),
            fieldAndLabProtocols = listOf(
                "Conduct bacterial streaming diagnostic test in clear water for suspected Ralstonia solanacearum wilt.",
                "Calibrate flat-fan vs. hollow-cone nozzles using the 60-second catch test and replace any nozzle >10% above nominal flow."
            ),
            assignmentPrompts = listOf(
                "Design a 3-spray FRAC-compliant rotation schedule for Tomato Late Blight that prevents Metalaxyl-M resistance."
            ),
            examRevisionQas = listOf(
                AgronomyRevisionQa(
                    question = "Why does spraying a broad-spectrum pyrethroid often cause a secondary outbreak of Red Spider Mites?",
                    modelAnswer = "Pyrethroids wipe out predatory mites (Phytoseiulus persimilis) and beneficial beetles faster than spider mites, releasing the mite population from natural biological control ('pest resurgence')."
                )
            )
        ),
        AgronomyPackSubject(
            id = "y2_agronomy_crop_science",
            courseCode = "AGR 203",
            volume = AgronomyStudyVolume.YEAR_2,
            subjectName = "Agronomy & Crop Science",
            credits = 4,
            executiveSummary = "Quantitative soil-crop-atmosphere interactions, fertilizer stoichiometry, split nutrient scheduling, crop rotation design, and multi-environment yield gap analysis.",
            coreConcepts = listOf(
                "4R Nutrient Stewardship: Right Source, Right Rate, Right Time, Right Place matched to crop uptake curves.",
                "Soil-fertility matching: neutralizing acidic Nitisols/Acrisols before P-basal application to maximize Fertilizer Use Efficiency.",
                "Crop sequence architecture: breaking host-specific soil inoculum by rotating Solanaceae -> Legumes -> Cereals -> Brassicas.",
                "Yield gap decomposition: Potential Yield (Yp) vs. Water-Limited Yield (Yw) vs. Actual Farmer Yield (Ya)."
            ),
            keyFormulasAndMetrics = listOf(
                "Fertilizer Nutrient Weight (kg product) = Required Element (kg/ha) × 100 / % Nutrient Grade in Bag",
                "Agronomic Efficiency (AE) = (Yield Fertilized - Yield Unfertilized) / Nutrient Applied (kg)",
                "Growing Degree Days (GDD) = [(Tmax + Tmin) / 2] - Tbase"
            ),
            fieldAndLabProtocols = listOf(
                "Perform foliar vs. basal split-dose economic comparison in maize (50% N basal + 50% N at V6 knee-high).",
                "Estimate pre-harvest maize yield using sample transects (ears/10m × rows/ear × kernels/row × TGW)."
            ),
            assignmentPrompts = listOf(
                "Calculate how many 50-kg bags of DAP (18-46-0) and CAN (26-0-0) are needed per hectare to supply 60 kg P₂O₅ and 90 kg N."
            ),
            examRevisionQas = listOf(
                AgronomyRevisionQa(
                    question = "Compute the exact kg of DAP (18-46-0) needed to supply 46 kg P₂O₅/ha, and how much N that DAP simultaneously delivers.",
                    modelAnswer = "DAP required = (46 × 100) / 46 = 100 kg DAP/ha (2 × 50 kg bags). Since DAP is 18% N, 100 kg DAP simultaneously supplies 18 kg N/ha."
                )
            )
        ),
        AgronomyPackSubject(
            id = "y2_horticulture",
            courseCode = "HRT 204",
            volume = AgronomyStudyVolume.YEAR_2,
            subjectName = "Horticulture",
            credits = 4,
            executiveSummary = "Olericulture (vegetables), Pomology (orchard fruits), Floriculture (protected cut flowers), nursery propagation, pruning, fertigation, and cold-chain postharvest biology.",
            coreConcepts = listOf(
                "Nursery propagation: disease-free cocopeat/vermiculite plug trays, grafting wilt-resistant tomato/avocado rootstocks, and hardening off.",
                "Canopy training & pruning: single/double-stem trellising and desuckering in indeterminate greenhouse tomatoes; formative pruning in avocado/mango.",
                "Protected agriculture & fertigation: matching EC (1.6–2.4 mS/cm) and Ca:K balance to prevent Blossom End Rot and tipburn.",
                "Postharvest physiology: Climacteric (tomato, avocado, mango, banana) vs. Non-climacteric (strawberry, citrus, grape) ethylene & cold-chain management."
            ),
            keyFormulasAndMetrics = listOf(
                "Target Fertigation EC ≈ Raw Water EC + 1.2 to 1.8 mS/cm (stage-dependent)",
                "Vapor Pressure Deficit (VPD, kPa) = SVP × (1 - RH/100)  [Optimal greenhouse target: 0.6–1.2 kPa]",
                "Q₁₀ Respiration Rule: Metabolic spoilage roughly doubles/triples for every 10°C rise above optimal storage temperature"
            ),
            fieldAndLabProtocols = listOf(
                "Graft scions onto Bacterial Wilt-resistant Solanaceous rootstocks and heal in a 90% RH mist chamber for 7 days.",
                "Verify harvest maturity using a handheld Brix refractometer and fruit firmness penetrometer."
            ),
            assignmentPrompts = listOf(
                "Draft a GlobalG.A.P. IFA v6 & KS 1758 compliant harvest, packhouse cooling, and MRL traceability protocol for export French beans."
            ),
            examRevisionQas = listOf(
                AgronomyRevisionQa(
                    question = "What causes Blossom End Rot in tomatoes/capsicums and how is it corrected?",
                    modelAnswer = "Localized Calcium (Ca²⁺) deficiency in expanding distal fruit tissue—usually triggered by erratic soil moisture, high EC salinity, or excess NH₄⁺ competing with Ca²⁺. Fix via steady drip irrigation + Calcium Nitrate (Nitrabor/Calcinit) fertigation."
                )
            )
        ),
        AgronomyPackSubject(
            id = "y2_irrigation_water",
            courseCode = "IWM 205",
            volume = AgronomyStudyVolume.YEAR_2,
            subjectName = "Irrigation & Water Management",
            credits = 3,
            executiveSummary = "Soil-water constants (FC, PWP, TAW, RAW), FAO-56 Penman-Monteith crop water demand (ETc = ET₀ × Kc), drip hydraulics, salinity leaching, and rainwater harvesting.",
            coreConcepts = listOf(
                "Soil moisture tension: Saturation (0 kPa), Field Capacity (-10 to -33 kPa), Permanent Wilting Point (-1500 kPa), Readily Available Water (RAW).",
                "FAO-56 Crop Evapotranspiration: initial, development, mid-season (Kc 1.05–1.20), and late-season crop coefficients.",
                "Pressurized irrigation hydraulics: emitter flow uniformity (EU >= 90%), mainline friction loss (Hazen-Williams), and sand/disc filtration.",
                "Water quality diagnostics: Electrical Conductivity (ECw), Sodium Adsorption Ratio (SAR), and Leaching Fraction (LF) to prevent salinization."
            ),
            keyFormulasAndMetrics = listOf(
                "ETc (mm/day) = ET₀ (mm/day) × Kc",
                "Gross Irrigation Depth (mm) = (Net Requirement - Effective Rainfall) / Application Efficiency (Ea)",
                "Leaching Fraction (LF) = ECw / (5 × ECe_threshold - ECw)",
                "Total Available Water (TAW, mm) = [(FC% - PWP%) / 100] × Bulk Density × Root Depth (mm)"
            ),
            fieldAndLabProtocols = listOf(
                "Measure drip uniformity by catching flow from 16 emitters (head, middle, tail lines) for 3 minutes and computing lower-quarter Distribution Uniformity (DUlq).",
                "Construct micro-catchment Zai pits and roof/pan water harvesting reservoirs scaled to dry-spell bridge days."
            ),
            assignmentPrompts = listOf(
                "Calculate daily liters per tomato plant when ET₀ = 5.2 mm/day, Kc = 1.15, spacing = 0.6 m × 0.5 m, and drip efficiency = 90%."
            ),
            examRevisionQas = listOf(
                AgronomyRevisionQa(
                    question = "A tomato field (0.6 m × 0.5 m spacing) has ET₀ = 5.0 mm/day, Kc = 1.15, and drip efficiency = 90%. What is the gross water per plant per day?",
                    modelAnswer = "ETc = 5.0 × 1.15 = 5.75 mm/day = 5.75 L/m²/day. Plant area = 0.30 m², so net water = 5.75 × 0.30 = 1.725 L/plant/day. Gross = 1.725 / 0.90 = 1.92 L/plant/day."
                )
            )
        ),
        AgronomyPackSubject(
            id = "y2_farm_management",
            courseCode = "FRM 206",
            volume = AgronomyStudyVolume.YEAR_2,
            subjectName = "Farm Management",
            credits = 3,
            executiveSummary = "Enterprise budgeting, partial budgets, whole-farm cashflow planning, machinery/labor scheduling, depreciation, credit risk, and break-even sensitivity analysis.",
            coreConcepts = listOf(
                "Enterprise Gross Margin vs. Net Farm Income (subtracting fixed costs: depreciation, permanent labor, land rent, loan interest).",
                "Partial Budgeting (Added Returns + Reduced Costs vs. Added Costs + Reduced Returns) to evaluate technology switches (e.g., manual weeding vs. pre-emergence herbicide).",
                "Time value of money & investment appraisal: Payback Period, Benefit-Cost Ratio (BCR > 1), Net Present Value (NPV), and IRR for greenhouse/irrigation capex.",
                "Risk management instruments: enterprise diversification, crop insurance, forward contracts, and working-capital cashflow calendars."
            ),
            keyFormulasAndMetrics = listOf(
                "Break-Even Yield (kg/ha) = Total Production Costs (KES/ha) / Unit Farm-Gate Price (KES/kg)",
                "Break-Even Price (KES/kg) = Total Production Costs (KES/ha) / Expected Marketable Yield (kg/ha)",
                "Straight-Line Depreciation (/yr) = (Purchase Cost - Salvage Value) / Useful Life (years)"
            ),
            fieldAndLabProtocols = listOf(
                "Audit a real 1-acre horticultural farm ledger: separate fixed vs. variable costs and calculate ROI% and break-even yield."
            ),
            assignmentPrompts = listOf(
                "Prepare a 12-month monthly cashflow projection for a diversified dairy + maize + French beans smallholder enterprise."
            ),
            examRevisionQas = listOf(
                AgronomyRevisionQa(
                    question = "Total cost for 1 acre of potatoes is KES 110,000. Expected yield is 8,000 kg. What is the break-even price per kg?",
                    modelAnswer = "Break-even price = 110,000 / 8,000 = KES 13.75 per kg. Any farm-gate price above KES 13.75/kg generates net profit."
                )
            )
        ),

        // =====================================================================
        // VOLUME 3: BACHELOR'S YEAR 3
        // =====================================================================
        AgronomyPackSubject(
            id = "y3_sustainable_agriculture",
            courseCode = "SAG 301",
            volume = AgronomyStudyVolume.YEAR_3,
            subjectName = "Sustainable Agriculture",
            credits = 4,
            executiveSummary = "Long-term ecological & socio-economic viability of farming systems: conservation agriculture (CA), agroforestry, circular nutrient recovery, and regenerative soil carbon.",
            coreConcepts = listOf(
                "Three Pillars of Conservation Agriculture (FAO): minimal soil disturbance (zero/strip till), permanent organic soil cover (>=30% mulch/cover crops), and species diversification.",
                "Integrated Soil Fertility Management (ISFM): combining mineral fertilizers with quality organic manures/compost and adapted germplasm.",
                "Circular bio-economy: 18-day Berkeley hot composting, vermicomposting, black soldier fly frass, and biodigester bioslurry.",
                "Watershed governance & soil conservation engineering: Fanya Juu/Fanya Chini terraces, contour vetiver strips, and riparian buffers."
            ),
            keyFormulasAndMetrics = listOf(
                "Target Compost C:N Ratio = 25:1 to 30:1 (Thermophilic pasteurization core: 55–65°C)",
                "Soil Organic Carbon Stock (t C/ha) = SOC (%) × Bulk Density (g/cm³) × Depth (cm) × (1 - Gravel Fraction)",
                "Nutrient Partial Factor Productivity (PFP) = Grain Yield (kg/ha) / Nutrient Applied (kg/ha)"
            ),
            fieldAndLabProtocols = listOf(
                "Build and monitor an 18-day Berkeley hot compost pile (4 days rest, turn Days 4, 6, 8, 10, 12, 14) logging core temperature.",
                "Lay out contour terraces using an A-frame level across sloping farmland (>8% slope)."
            ),
            assignmentPrompts = listOf(
                "Evaluate how integrating Desmodium/Calliandra agroforestry hedgerows improves soil organic carbon, livestock protein, and erosion control."
            ),
            examRevisionQas = listOf(
                AgronomyRevisionQa(
                    question = "What happens if raw sawdust (C:N ≈ 400:1) is incorporated directly into soil without composting?",
                    modelAnswer = "Soil microbes rapidly consume soil nitrate/ammonium to metabolize the excess carbon, causing acute 'nitrogen immobilization' and severe yellowing/stunting of the growing crop."
                )
            )
        ),
        AgronomyPackSubject(
            id = "y3_agri_biotechnology",
            courseCode = "BTC 302",
            volume = AgronomyStudyVolume.YEAR_3,
            subjectName = "Agricultural Biotechnology",
            credits = 4,
            executiveSummary = "Plant tissue culture, micropropagation of clean planting material, molecular markers (MAS/QTL), CRISPR-Cas gene editing, biofertilizers, and biosafety regulation.",
            coreConcepts = listOf(
                "Totipotency & micropropagation stages (0–IV): meristem-tip culture to eliminate viruses in banana, sweetpotato, cassava, and Irish potato.",
                "Marker-Assisted Selection (MAS) using SNP/SSR markers linked to drought tolerance, Striga resistance, and rust genes (Sr/Yr).",
                "Genome editing (CRISPR-Cas9 targeted knockouts) vs. transgenic crops (Bt maize Cry proteins) and biosafety frameworks (NBA).",
                "Microbial biotechnology: Rhizobium inoculants, mycorrhizal biofertilizers (AMF), and entomopathogenic biopesticides (Metarhizium, Beauveria, Trichoderma)."
            ),
            keyFormulasAndMetrics = listOf(
                "MS Medium Hormone Ratio: High Auxin:Cytokinin -> Rooting; High Cytokinin:Auxin -> Shoot Multiplication",
                "Polymorphism Information Content (PIC) = 1 - Σ(pᵢ²) for molecular marker informativeness",
                "Clean-Seed Multiplication Factor: 1 meristem plantlet -> 500–2,000 virus-free plantlets within 6–8 subculture cycles"
            ),
            fieldAndLabProtocols = listOf(
                "Prepare Murashige & Skoog (MS) basal medium adjusted to pH 5.7–5.8, autoclave at 121°C (15 psi) for 15 minutes, and culture surface-sterilized explants.",
                "Perform ELISA or lateral-flow immunostrip screening to certify virus-free mother stock."
            ),
            assignmentPrompts = listOf(
                "Critically assess the biosafety, intellectual property, and smallholder adoption pathways of virus-resistant cassava and Bt maize in East Africa."
            ),
            examRevisionQas = listOf(
                AgronomyRevisionQa(
                    question = "Why is meristem-tip culture effective at producing virus-free banana and potato plantlets from infected mother plants?",
                    modelAnswer = "The apical meristem dome (<0.3 mm) lacks differentiated vascular xylem/phloem connections and divides faster than viral replication/movement, yielding pathogen-free daughter shoots."
                )
            )
        ),
        AgronomyPackSubject(
            id = "y3_climate_smart_ag",
            courseCode = "CSA 303",
            volume = AgronomyStudyVolume.YEAR_3,
            subjectName = "Climate-Smart Agriculture",
            credits = 4,
            executiveSummary = "FAO Triple-Win framework (Productivity, Adaptation/Resilience, Mitigation): drought-tolerant varieties, seasonal climate forecasts, AWD rice, and enteric/N₂O emission cuts.",
            coreConcepts = listOf(
                "CSA 3-Pillar Mandate: sustainably increase yields/incomes, build resilience to climate shocks, and reduce GHG emissions per unit output.",
                "Abiotic stress adaptation: short-cycle drought-escaping varieties, staggered planting windows, conservation basins, and index-based weather insurance.",
                "Agricultural GHG inventory: CH₄ from flooded rice & ruminant enteric fermentation, N₂O from over-applied N fertilizers, and CO₂ from tillage/deforestation.",
                "Mitigation levers: Alternate Wetting & Drying (AWD) in rice (cuts CH₄ by 30–50%), nitrification inhibitors, biochar, and agroforestry carbon sequestration."
            ),
            keyFormulasAndMetrics = listOf(
                "Global Warming Potential (100-yr): CO₂ = 1, CH₄ ≈ 27–30, N₂O ≈ 273",
                "Emission Intensity = Total kg CO₂-eq Emitted / kg Grain or Liter Milk Produced",
                "Rainfall Onset Rule: Plant after >=20–25 mm cumulative rain over 3 days with no >7-day dry spell forecast"
            ),
            fieldAndLabProtocols = listOf(
                "Install perforated AWD field water tubes in paddy rice to trigger irrigation only when perched water table drops 15 cm below soil surface.",
                "Interpret 7-day seasonal rainfall, VPD, and spray-window weather advisories to schedule foliar inputs without wash-off."
            ),
            assignmentPrompts = listOf(
                "Quantify how switching from continuous flooding to AWD rice + 4R nitrogen management lowers farm emission intensity (kg CO₂e/kg grain)."
            ),
            examRevisionQas = listOf(
                AgronomyRevisionQa(
                    question = "Why does nitrous oxide (N₂O) spike when urea is surface-applied onto waterlogged soils?",
                    modelAnswer = "Anaerobic waterlogged conditions trigger microbial denitrification (NO₃⁻ -> NO -> N₂O -> N₂), volatilizing costly nitrogen as N₂O—a greenhouse gas 273× more potent than CO₂."
                )
            )
        ),
        AgronomyPackSubject(
            id = "y3_modern_ag_tech",
            courseCode = "AGT 304",
            volume = AgronomyStudyVolume.YEAR_3,
            subjectName = "Modern Agricultural Technologies",
            credits = 4,
            executiveSummary = "Precision agriculture, GNSS field boundary mapping, multispectral NDVI/GNDVI remote sensing, IoT soil moisture telemetry, variable-rate application (VRA), and AI crop diagnostics.",
            coreConcepts = listOf(
                "Spatial agronomy: GNSS polygon boundary mapping, soil electrical conductivity (ECa) zoning, and Management Zone delineation.",
                "Optical & multispectral indices: NDVI and NDRE from satellites/UAV drones to detect pre-visual nitrogen stress, water deficit, and pest hotspots.",
                "Autonomous & precision actuation: UAV variable-rate spot spraying, sensor-guided drip fertigation, and smart greenhouse climate controllers.",
                "Digital decision support: computer-vision leaf disease classification, IoT cold-chain sensors, and mobile digital scouting/PHI audit trails."
            ),
            keyFormulasAndMetrics = listOf(
                "NDVI = (NIR - Red) / (NIR + Red)   [-1 to +1; healthy dense crop canopy typically 0.65–0.90]",
                "NDRE (Red-Edge Chlorophyll) = (NIR - RedEdge) / (NIR + RedEdge)",
                "UAV Spray Application Rate (L/ha) = (Nozzle Flow L/min × 600) / (Flight Speed km/h × Swath Width m)"
            ),
            fieldAndLabProtocols = listOf(
                "Walk perimeter vertices with GNSS mapping to compute exact field area (hectares/acres) for precise knapsack/fertilizer dosing.",
                "Capture leaf lesion macro photos under diffuse daylight against neutral backgrounds for high-confidence AI visual diagnosis."
            ),
            assignmentPrompts = listOf(
                "Design an end-to-end Precision IPM workflow combining NDVI drone zoning, mobile AI scouting, and variable-rate spot spraying to cut pesticide use by 35%."
            ),
            examRevisionQas = listOf(
                AgronomyRevisionQa(
                    question = "Why is NDRE (Red-Edge) superior to standard NDVI for timing topdress nitrogen in tall maize canopies?",
                    modelAnswer = "Standard NDVI saturates once Leaf Area Index exceeds ~3.0 because Red light is absorbed in the upper leaves; Red-Edge wavelengths penetrate deeper into the canopy and remain linearly sensitive to mid-season chlorophyll/N changes."
                )
            )
        )
    )

    fun subjectsForVolume(volume: AgronomyStudyVolume?): List<AgronomyPackSubject> {
        return if (volume == null) allSubjects else allSubjects.filter { it.volume == volume }
    }

    fun searchSubjects(query: String, volume: AgronomyStudyVolume?): List<AgronomyPackSubject> {
        val base = subjectsForVolume(volume)
        val q = query.trim()
        if (q.isBlank()) return base
        return base.filter { subject ->
            subject.subjectName.contains(q, ignoreCase = true) ||
                subject.courseCode.contains(q, ignoreCase = true) ||
                subject.executiveSummary.contains(q, ignoreCase = true) ||
                subject.coreConcepts.any { it.contains(q, ignoreCase = true) } ||
                subject.keyFormulasAndMetrics.any { it.contains(q, ignoreCase = true) } ||
                subject.examRevisionQas.any {
                    it.question.contains(q, ignoreCase = true) || it.modelAnswer.contains(q, ignoreCase = true)
                }
        }
    }
}
