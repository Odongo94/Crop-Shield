package com.example.data.science.curriculum

import com.example.model.science.CurriculumPillarType
import com.example.model.science.CurriculumSubtopic
import com.example.model.science.CurriculumTopic

object AgriExtensionCurriculum {

    val topics: List<CurriculumTopic> = listOf(
        CurriculumTopic(
            id = "agri_ext_foundations",
            pillar = CurriculumPillarType.AGRI_EXTENSION,
            code = "6.a-d",
            title = "Foundations, Philosophy & Development Communication",
            summary = "The principles of agricultural extension, andragogy (adult learning), development communication (DevCom), participatory rural appraisal (PRA), and agricultural commodity policies.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "aex_elements_principles",
                    code = "a",
                    title = "Elements & Principles of Agricultural Extension",
                    coreDefinition = "The non-formal, out-of-school educational system designed to transmit research-backed agricultural innovations to rural populations, assist in problem identification, and empower community self-determination.",
                    keyMechanismsAndFormulas = listOf(
                        "Four Fundamental Elements of Extension: 1) Target Clientele (farmers, rural farm youth, farm homemakers); 2) Extension Agents / Educators (change agents and subject matter specialists); 3) Technical Subject Matter (agronomic innovations, farm economics, postharvest tech); 4) Extension Organization (public ministry, NGO, or private agribusiness).",
                        "Ten Foundational Principles of Extension: Need-based programming; grassroots participatory planning; democratic process; continuous educational process; working with whole family; cultural adaptability; voluntary participation; reliance on local indigenous leadership; systematic evaluation; and institutional neutrality.",
                        "Kolb's Experiential Learning Cycle: Concrete Experience (hands-on field practice) -> Reflective Observation (reviewing what occurred) -> Abstract Conceptualization (understanding underlying agronomic principles) -> Active Experimentation (applying new practice on own farm plot)."
                    ),
                    detailedElaboration = "Agricultural extension is fundamentally an educational endeavor, not a regulatory enforcement body or a relief agency. It bridges the critical divide between agricultural research stations (universities, international institutes like IRRI, CIMMYT) and the practicing farming community.\n\nNon-Formal vs. Formal Education: Extension operates outside formal school classrooms without fixed curricula, grades, or degrees. Participation is entirely voluntary: if an extension agent fails to address real, urgent farmer problems (such as armyworm attacks or soil acidification), farmers simply walk away. The guiding principle is 'Helping People Help Themselves'—fostering self-reliance and critical thinking rather than permanent dependency on government hand-outs.",
                    biologicalAndChemicalDetails = listOf(
                        "Translating Science into Vernacular: Scientific concepts like 'cation exchange capacity' or 'bacterial vascular occlusion' must be translated into observable agronomic metaphors (e.g., 'the soil's nutrient pantry magnet' or 'slimy bacterial traffic jams choking plant straws').",
                        "Biological Heterogeneity of Farm Micro-Environments: Unlike factory floors, every farmer's field has distinct microtopography, soil microbial profiles, and moisture pockets, making rigid uniform recipe recommendations disastrous."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "SOP for Conducting a Baseline Community Needs Assessment: 1) Secure formal protocol entry via local village administrative leadership (e.g., Barangay Captain, Village Chief); 2) Assemble a representative 30-farmer focus group stratified by gender, farm size (smallholder vs. commercial), and tenure status (landowner vs. tenant); 3) Facilitate open-ended problem listing regarding top 5 yield-limiting constraints; 4) Conduct pairwise preference ranking to prioritize top agricultural intervention targets; 5) Co-design season-long demonstration activities with selected volunteer lead farmers."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Top-Down Extension Failure Syndrome: Modern demonstration plots abandoned and overgrown with weeds, low attendance at meetings (<10%), farmers politely agreeing during visits but making zero changes in their own fields.",
                        "Institutional Dependency Trap: Farmers refusing to adopt proven high-yield practices unless free seed, free fertilizer, or daily lunch allowances are provided by the government."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: An agricultural extension district comprises 16,000 farming households scattered across 20 rural villages. The recommended FAO guideline for effective smallholder crop extension is 1 extension agent per 400 farming households. Currently, the local government unit deploys only 8 field extension workers.\n1) Calculate the current Extension Worker-to-Farmer Ratio in the district.\n2) Calculate the staffing deficit (number of additional agents required to achieve the FAO 1:400 benchmark).\n3) If the agency implements a Farmer-to-Farmer Lead Facilitator Model—where each professional extension agent trains and mentors 10 community lead farmers, each of whom directly advises 40 neighbor farmers—calculate how many total households can be serviced by the existing 8 extension workers.\n\nSolution:\n1) Current Ratio:\nRatio = 16,000 households / 8 extension agents = 1 : 2,000.\n\n2) Staffing Deficit:\nIdeal agents needed = 16,000 / 400 = 40 agents.\nStaffing Deficit = 40 ideal - 8 existing = 32 additional agents required.\n\n3) Farmer-to-Farmer Multiplier Reach:\n1 Agent trains 10 Lead Farmers.\nEach Lead Farmer advises 40 neighbor households.\nCapacity per Agent = 10 x 40 = 400 households.\nTotal households serviced by 8 existing agents = 8 x 400 = 3,200 households.\n(Note: This covers 20% of the district's 16,000 households, demonstrating that even with the lead-farmer multiplier, staffing must expand or mass media methods must supplement)."
                    ),
                    commonExamQuestions = listOf(
                        "State the primary differences between formal, non-formal, and informal education in agricultural development.",
                        "List five foundational principles of agricultural extension and explain why voluntary participation is paramount.",
                        "Describe Kolb's Experiential Learning Cycle and illustrate how it is applied in an on-farm training workshop."
                    ),
                    practicalTakeaway = "Never deliver a monologue lecture to farmers: design every extension interaction around experiential learning where farmers touch, observe, discuss, and practice the physical skill themselves.",
                    authoritativeTextbookRef = "Agricultural Extension (Van den Ban & Hawkins, 2nd Ed., Blackwell Science, Ch. 1-2); Improving Agricultural Extension (FAO, Swanson et al., Ch. 1)"
                ),
                CurriculumSubtopic(
                    id = "aex_devcom_pra",
                    code = "b",
                    title = "Development Communication (DevCom) & Participatory Rural Appraisal",
                    coreDefinition = "The systematic, purposive application of communication processes, participatory media, and adult learning tools to catalyze societal transformation and alleviate rural poverty (pioneered by Dr. Nora C. Quebral, UPLB).",
                    keyMechanismsAndFormulas = listOf(
                        "Development Communication (DevCom) Definition: The art and science of human communication applied to the speedy transformation of a country and the mass of its people from poverty to a dynamic state of economic growth that makes possible greater social equality and the larger fulfillment of human potential (Nora Quebral).",
                        "Participatory Rural Appraisal (PRA) vs. Rapid Rural Appraisal (RRA): RRA is extractive (outsiders collect data for their own analysis); PRA is participatory and empowering ('handing over the stick' so local people analyze, plan, and execute).",
                        "Core PRA Visual Methodologies: 1) Transect Walk (cross-sectional elevation slice of soils, crops, and land use); 2) Community Resource Map; 3) Seasonal Calendar (labor, rainfall, disease, hunger months); 4) Venn / Chapati Diagram (institutional influence and trust); 5) Matrix Scoring / Pairwise Ranking."
                    ),
                    detailedElaboration = "Development communication moves fundamentally beyond commercial advertising or public relations. It is purposive, value-laden, and pragmatic, prioritizing marginalized smallholder farmers who lack access to capital and technology. DevCom treats the rural villager as an active participant and co-creator of knowledge rather than a passive target of one-way propaganda.\n\nParticipatory Rural Appraisal (PRA): Developed by Robert Chambers, PRA fundamentally reverses traditional top-down expert hierarchies. Outsiders do not lecture; they facilitate. Local farmers use local materials (seeds, sticks, colored powders on bare ground) to map their own village resources, trace irrigation canal networks, and chart seasonal labor shortages. Because local farmers create the visual representations, they retain ownership of the data and commit to collective community action.",
                    biologicalAndChemicalDetails = listOf(
                        "Indigenous Technical Knowledge (ITK) Synergy: Smallholder farmers have accumulated centuries of localized ecological observations (e.g., indicator plants predicting early rains, moon phases correlating with stem-borer moth flights); DevCom validates and integrates ITK with modern agricultural science.",
                        "Visual Literacy in Low-Literacy Populations: Rural visual comprehension requires simple, unshaded line drawings with clear perspective, avoiding abstract symbols, cross-sectional cutaways, or confusing arrows that low-literacy farmers misinterpret."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "SOP for Conducting a Village Transect Walk: 1) Form a multidisciplinary team (agronomist, soil specialist, extension agent) paired with 3 local community elders (including at least one woman farmer); 2) Traverse a straight line cutting across the full ecological elevation gradient of the community (from mountain ridge to lowland valley); 3) Stop at every distinct ecological transition zone; 4) Observe and record soil color/texture, slope, cropping systems, water availability, pest incidence, and tenure status; 5) Synthesize findings into a cross-sectional transect matrix; 6) Present the diagram back to the village assembly for communal validation."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Tarmac / Spatial Bias: Extension agents conducting appraisals strictly within 50 meters of paved roads, missing the severe poverty, soil erosion, and pest epidemics of interior off-road hamlets.",
                        "Elite Capture Bias: PRA meetings dominated by wealthy, literate village elders while landless laborers and female farmers sit silently in the back without voicing their constraints."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: In a remote highland municipality, a PRA seasonal calendar workshop with 120 smallholder potato farmers documents the annual rainfall, pest pressure, and family food security profile:\n- Month 1–4: Dry season; minimal potato cultivation; high food security from cereal stores.\n- Month 5–8: Heavy monsoon rains; intensive potato production; severe Late Blight (Phytophthora infestans) epidemics causing 45% crop loss.\n- Month 9–11: Post-monsoon lean season; household food grain stores exhausted ('Hunger Gap'); farmers forced into usurious debt (10% monthly interest) from local input merchants.\n- Month 12: Harvest of irrigated second crop.\n\n1) Design an integrated DevCom extension intervention addressing the two identified crises: Late Blight crop failure and the Month 9–11 Hunger Gap.\n2) If introducing a community-managed seed bank and hermetic grain storage costs $1,500 and saves 20,000 kg of maize grain worth $0.40/kg, calculate the Benefit-Cost Ratio (BCR) of the storage intervention.\n\nSolution:\n1) Strategic Extension Plan:\n- Crisis 1 (Late Blight in M5-M8): Launch community radio alerts and SMS forecasting 48 hours prior to monsoon humidity spikes, coupled with Farmer Field School (FFS) method demonstrations on preventive fungicide rotation (FRAC codes) before symptoms appear.\n- Crisis 2 (Hunger Gap in M9-M11): Establish community-managed hermetic grain storage (PICS bags) right after M4 harvest and link farmers to low-interest cooperative microcredit to eliminate reliance on usurious merchant loans.\n\n2) Benefit-Cost Ratio (BCR):\nGross Economic Value Saved = 20,000 kg x $0.40/kg = $8,000.\nTotal Project Cost = $1,500.\nBCR = Total Benefits / Total Costs = $8,000 / $1,500 = 5.33.\n(A BCR of 5.33 demonstrates an outstanding 533% economic return, easily justifying donor and municipal funding)."
                    ),
                    commonExamQuestions = listOf(
                        "Define Development Communication according to Dr. Nora C. Quebral and differentiate it from commercial corporate communications.",
                        "Describe the purpose, steps, and diagnostic value of a Transect Walk in Participatory Rural Appraisal.",
                        "Identify and explain four common researcher biases in conventional rural surveys (tarmac bias, elite bias, dry-season bias, male bias) and how PRA overcomes them."
                    ),
                    practicalTakeaway = "Always 'hand over the stick' during village assessments: let farmers draw the maps and place the stones on matrix ranking charts; community ownership of the diagnosis is the only guarantee of adoption.",
                    authoritativeTextbookRef = "Development Communication: In Praise of the Practitioners (Nora C. Quebral, UPLB); Whose Reality Counts? Putting the First Last (Robert Chambers, ITDG, Ch. 4-6)"
                ),
                CurriculumSubtopic(
                    id = "aex_commodity_policy",
                    code = "c",
                    title = "Agricultural Commodity Policy & Extension Linkages",
                    coreDefinition = "The public policies, market price stabilization schemes, and regulatory frameworks governing major agricultural commodities, and the extension mechanisms required for their successful implementation.",
                    keyMechanismsAndFormulas = listOf(
                        "Core Agricultural Policy Levers: Minimum Support Prices (MSP) / Floor Prices; Strategic Buffer Stock Operations; Targeted Input Subsidies (e-vouchers for seed and fertilizer); Tariff-Rate Quotas (TRQ) and Rice Tariffication laws; Good Agricultural Practices (GAP) certification; Crop Insurance Programs (PCIC, indemnity vs. parametric weather index).",
                        "Policy Dilemma in Food Staples: Food policy must simultaneously ensure high remunerative prices for farmers to encourage rural production, while guaranteeing low, affordable food prices for poor urban consumers.",
                        "Deadweight Loss of Price Distortions: Imposing price ceilings below market equilibrium causes shortages and black markets; price floors above equilibrium cause costly unsaleable government grain surpluses."
                    ),
                    detailedElaboration = "Agricultural extension does not operate in a macroeconomic vacuum; national commodity policies dictate the economic incentives that govern farmer behavior. An extension program promoting certified rice seed will fail completely if government commodity policy allows cheap, untaxed foreign grain imports that crash domestic farm-gate prices below the cost of production.\n\nGood Agricultural Practices (GAP) & Export Standards: In globalized export supply chains (mango, banana, coffee, cocoa), extension workers serve as regulatory auditors and trainers. They instruct smallholders on maximum residue limits (MRLs), pre-harvest intervals (PHIs), traceability record-keeping, and worker sanitation necessary to secure international phytosanitary export certifications.",
                    biologicalAndChemicalDetails = listOf(
                        "Maximum Residue Limits (MRLs): The legal maximum concentration of pesticide residue (in mg/kg or ppm) permitted in or on food commodities; breaching MRLs results in immediate rejection and destruction of export shipments.",
                        "Pre-Harvest Interval (PHI) Enforcement: The mandatory minimum number of days that must elapse between the last pesticide spray and crop harvest, ensuring biological enzymatic and ultraviolet degradation of chemical residues."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "SOP for Enrolling a Smallholder Farming Cooperative in National Good Agricultural Practices (GAP): 1) Audit farm water source for fecal coliform bacteria (E. coli < 100 CFU/100ml); 2) Construct a secure, locked agrochemical storage shed equipped with concrete bunding, sand bucket for spills, and warning signage; 3) Train all applicators on mandatory Personal Protective Equipment (PPE); 4) Install permanent farm record logbooks logging date, crop, chemical trade name, active ingredient, dosage, target pest, and harvest date; 5) Verify that pre-harvest intervals are strictly respected before submitting formal GAP certification dossier to Ministry auditors."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Export Border Rejection Notice: Commercial fruit shipments detained at foreign ports of entry due to pesticide residues exceeding EU or USDA MRLs or detection of quarantine pests (e.g., Bactrocera fruit fly larvae).",
                        "Subsidized Input Smuggling: Subsidized government fertilizer bags being diverted across regional borders or purchased by commercial plantation cartels rather than reaching targeted subsistence smallholders."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: Under a national agricultural modernization act, the government replaces quantitative rice import restrictions with an import tariff of 35%. Milled rice has an international world price of $380/MT CIF (Cost, Insurance, and Freight at domestic port). Port offloading, customs clearance, and inland trucking add $45/MT.\n1) Calculate the landed wholesale price of imported rice per MT and per kg in the domestic market.\n2) If local domestic rice farmers have an average production cost of $0.48/kg of milled rice, can they compete with imported rice under this tariff structure?\n3) If the total volume of imported rice is 2,000,000 MT, calculate the total tariff revenue generated for the Rice Competitiveness Enhancement Fund (RCEF).\n\nSolution:\n1) Landed Wholesale Price Calculation:\nCIF Import Price = $380/MT\nTariff (35%) = 0.35 x $380 = $133/MT\nCustoms and inland transport = $45/MT\nTotal Landed Wholesale Price = $380 + $133 + $45 = $558/MT.\nPrice per kilogram = $558 / 1,000 kg = $0.558/kg (approx. $0.56/kg).\n\n2) Domestic Competitiveness:\nLocal production cost = $0.48/kg.\nLanded import price = $0.56/kg.\nLocal farmers can compete favorably, as domestic costs ($0.48/kg) are lower than the landed import price ($0.56/kg) by $0.08/kg, providing domestic growers with a viable operating margin.\n\n3) Total Tariff Revenue for RCEF:\nTariff Revenue = Total Volume x Tariff per MT\nTariff Revenue = 2,000,000 MT x $133/MT = $266,000,000.\n(This $266M fund is legally earmarked to provide free certified inbred seed, combine harvesters, and extension training to domestic farmers)."
                    ),
                    commonExamQuestions = listOf(
                        "Explain the economic rationale of replacing quantitative import quotas with ad valorem tariffs in agricultural trade policy.",
                        "Describe the purpose and components of Good Agricultural Practices (GAP) certification in horticultural commodity supply chains.",
                        "Analyze the trade-offs of government price support operations (buffer stocking) on domestic market equilibrium and fiscal budgets."
                    ),
                    practicalTakeaway = "Train smallholder groups to maintain rigorous daily farm record logbooks: traceability documentation is the mandatory gateway to securing Good Agricultural Practices (GAP) certification and accessing premium export prices.",
                    authoritativeTextbookRef = "Agricultural Policy Analysis (Norton, Alwang, Masters, Cambridge Univ. Press, Ch. 2-5); Economics of Agricultural Development (Norton et al., Ch. 8)"
                ),
                CurriculumSubtopic(
                    id = "aex_philosophy_andragogy",
                    code = "d",
                    title = "Philosophy of Agricultural Extension & Andragogy",
                    coreDefinition = "The educational and ethical foundation of extension work: promoting human development, self-reliance, and democratic governance through Malcolm Knowles' theory of Andragogy (adult learning).",
                    keyMechanismsAndFormulas = listOf(
                        "Knowles' Six Assumptions of Andragogy (Adult Learning Theory): 1) Need to Know (adults must know why they need to learn something before undertaking it); 2) Self-Concept (adults are autonomous, self-directed individuals who resent having will imposed on them); 3) Prior Experience (adults enter training with vast reservoir of lived experience that serves as the richest learning resource); 4) Readiness to Learn (learning readiness is directly linked to real-life developmental tasks and economic problems); 5) Orientation to Learning (problem-centered and performance-centered rather than subject-centered); 6) Motivation to Learn (internal motivators such as farm viability, self-esteem, and quality of life far outweigh external pressures).",
                        "Edgar Dale's Cone of Experience (Retention Hierarchy): 10% of what is Read; 20% of what is Heard (lecture); 30% of what is Seen (charts); 50% of what is Seen & Heard (video, demo); 70% of what is Discussed/Said (group debates); 90% of what is Done in Real Hands-On Practice (psychomotor execution).",
                        "Seaman A. Knapp's Educational Axiom: 'What a man hears, he may doubt; what he sees, he may possibly doubt; but what he does himself, he cannot doubt.' (Father of the modern agricultural demonstration plot method)."
                    ),
                    detailedElaboration = "Pedagogy is the art and science of teaching children, who are dependent learners with limited experience and a subject-centered orientation. Andragogy is the art and science of facilitating adult learning. Extension educators who treat adult smallholder farmers like school children (giving rigid classroom lectures, scolding them, or ignoring their indigenous farming knowledge) fail catastrophically.\n\nAdults learn best through experiential learning in informal settings. They bring deep, highly contextualized knowledge of local soils, rainfall variations, and economic constraints. The extension agent's role is not an authoritarian professor ('sage on the stage'), but an empathetic facilitator ('guide on the side'). Training must be organized around immediate, pragmatic farming challenges (e.g., 'How do we stop our maize from rotting in storage this month?'), not abstract academic botany.",
                    biologicalAndChemicalDetails = listOf(
                        "Cognitive Dissonance in Agricultural Practice Change: When an extension agent tells an elder farmer that burning crop stubble destroys soil health, it clashes with 40 years of their lived practice; overcoming this mental dissonance requires non-confrontational peer demonstration and evidence.",
                        "Neurobiology of Experiential Learning: Tactile motor execution (handling seedlings, calibrating sprayer pressure gauges) activates motor cortex and cerebellar memory circuits, producing durable synaptic potentiation that passive auditory lectures cannot achieve."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "SOP for Designing an Andragogical Adult Field Training Workshop: 1) Schedule training at a time and location convenient for farmers (e.g., early morning or late afternoon under a village tree, avoiding heavy field labor hours); 2) Open with an icebreaker eliciting farmers' own past experiences with the problem; 3) Limit formal lecturing to no more than 15–20 minutes; 4) Transition immediately into hands-on small-group field exercises (e.g., 4 farmers per knapsack sprayer to practice nozzle calibration); 5) Have farmer representatives present their group findings and lead peer discussion; 6) Conclude by agreeing on immediate on-farm trial actions."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Pedagogical Failure in Extension: Farmers sitting in silent, passive compliance during a lecture, checking phones, nodding politely to authority, but adopting zero practices afterward.",
                        "Adult Resistance Syndrome: Outright skepticism and defensiveness triggered when an arrogant young agronomist publicly criticizes an elder farmer's traditional techniques."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: An agricultural extension specialist evaluates the effectiveness of two distinct training methods for teaching 120 smallholder farmers how to prune fruit trees:\n- Method A (Pedagogical Lecture): 2-hour classroom lecture with slides showing correct pruning angles.\n- Method B (Andragogical Practicum): 2-hour hands-on field session where farmers prune trees under coaching.\nBased on Edgar Dale's Cone of Experience benchmarks (30% retention for passive audio-visual vs. 90% retention for active hands-on doing) evaluated 60 days later on a 25-point practical pruning test:\n1) Calculate the expected average test score (points out of 25) for Method A versus Method B.\n2) If improper pruning reduces orchard yield by 1.5 MT/ha on a 200-hectare cooperative worth $600/MT, calculate the total monetary value saved by selecting Method B over Method A (assuming proper pruning adoption rate directly mirrors retention rate).\n\nSolution:\n1) Expected Test Retention Scores:\nMethod A (Lecture/AV, 30% retention) = 25 points x 0.30 = 7.5 points out of 25.\nMethod B (Hands-on Doing, 90% retention) = 25 points x 0.90 = 22.5 points out of 25.\n\n2) Economic Impact Calculation:\nTotal Cooperative Area = 200 ha.\nPotential Yield Loss = 1.5 MT/ha x 200 ha = 300 MT.\nTotal Potential Economic Loss = 300 MT x $600/MT = $180,000.\nAdoption/Execution Success Rate:\n- Method A saves 30% of potential loss = 0.30 x $180,000 = $54,000.\n- Method B saves 90% of potential loss = 0.90 x $180,000 = $162,000.\nNet Monetary Advantage of Andragogical Method B = $162,000 - $54,000 = $108,000 across the cooperative."
                    ),
                    commonExamQuestions = listOf(
                        "Explain Malcolm Knowles' six core assumptions of Andragogy and contrast them with pedagogical teaching models.",
                        "Analyze Edgar Dale's Cone of Experience and discuss why hands-on field practice achieves superior knowledge retention compared to classroom lectures.",
                        "Quote and discuss Seaman A. Knapp's classic axiom regarding the power of farm demonstration."
                    ),
                    practicalTakeaway = "Always respect adult farmers' life experience: start every training by asking what has worked and what has failed in their own fields, then build new scientific concepts upon their practical wisdom.",
                    authoritativeTextbookRef = "The Adult Learner (Malcolm S. Knowles et al., 8th Ed., Elsevier, Ch. 1-4); Agricultural Extension (Van den Ban & Hawkins, Ch. 3)"
                )
            ),
            examFocusPoints = listOf(
                "Core philosophical tenets of Agricultural Extension ('Helping people to help themselves')",
                "Differences between Pedagogy (child learning) and Andragogy (adult learning)",
                "Participatory Rural Appraisal (PRA) methodologies: Transect Walk, Seasonal Calendar, Resource Mapping",
                "Agricultural commodity policies: Tariffication, MSP price supports, and GAP export certification"
            ),
            practicalFieldApplications = listOf(
                "Facilitating community seasonal calendar workshops to map labor and pest outbreaks",
                "Constructing participatory problem trees linking root causes of low farm yields to actionable solutions",
                "Conducting village transect walks along elevation gradients to audit microclimates and land degradation"
            ),
            recommendedTextbookIds = listOf("book_van_den_ban_extension", "book_chambers_pra", "book_quebral_devcom")
        ),
        CurriculumTopic(
            id = "agri_ext_methods_adoption",
            pillar = CurriculumPillarType.AGRI_EXTENSION,
            code = "6.e-g",
            title = "Extension Methods, Communication Models & Adoption Process",
            summary = "Individual, group, and mass extension methods, Berlo's SMCRF communication model, the 5 stages of the adoption process (AIETA), and Everett Rogers' 5 adopter categories.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "aex_extension_methods",
                    code = "e",
                    title = "Individual, Group & Mass Extension Methods",
                    coreDefinition = "The classified operational channels utilized by extension services to engage agricultural clienteles according to group size, contact intensity, feedback speed, and economic cost efficiency.",
                    keyMechanismsAndFormulas = listOf(
                        "Individual Methods: Farm and home visits, office consultations, telephone/messaging advisory, farm clinic; provides highest personal trust and tailored advice, but highest economic cost per contact and severely limited coverage.",
                        "Group Methods: Method Demonstrations ('how-to-do'), Result Demonstrations ('why-to-do', side-by-side treated vs. control plots), Farmer Field Schools (FFS), field days, study tours / cross-visits; offers peer validation and experiential group discovery at moderate per-contact cost.",
                        "Mass Methods: Rural community radio, television, printed extension bulletins/flyers, automated bulk SMS, smartphone digital apps; achieves massive geographic reach at lowest per-contact cost, but lacks personalized feedback and has low direct adoption conversion.",
                        "Method Demo vs. Result Demo: Method Demo teaches a specific psychomotor manual skill in a single session (e.g., whip-and-tongue grafting, sprayer nozzle calibration); Result Demo is a season-long comparative trial proving the biological and economic superiority of a new practice over local farmer practice under identical field conditions."
                    ),
                    detailedElaboration = "A professional agricultural extension strategy never relies on a single method in isolation. Instead, it deploys an integrated multi-tier communication campaign:\n1) Mass Methods (rural radio broadcasts, mobile alerts) generate widespread initial awareness across entire valleys regarding an impending pest invasion or high-yielding drought-tolerant variety.\n2) Group Methods (Farmer Field Schools, Method Demonstrations) allow 25–30 farmers to collectively analyze agroecosystems, test the technology in discovery plots, and build social consensus.\n3) Individual Farm Visits are reserved for complex, high-stakes diagnostic interventions (troubleshooting severe soil salinity, financial restructuring of defaulted farm loans).\n\nFarmer Field Schools (FFS): Pioneered by FAO for rice IPM, FFS operates over an entire cropping season (seed to harvest). Meeting weekly, farmers maintain side-by-side plots: one managed using conventional prophylactic pesticide spraying, and one using ecological IPM scouting. Farmers count beneficial predatory spiders versus pest leafhoppers, draw Agro-Ecosystem Analysis (AESA) charts, and make democratic management decisions based on real economic thresholds.",
                    biologicalAndChemicalDetails = listOf(
                        "Agro-Ecosystem Analysis (AESA) Dynamics: Weekly biological field scouting mapping crop phenology, root development, soil moisture, and natural enemy-to-pest ratios (e.g., Lycosa spider to Nilaparvata brown planthopper) to biologically justify withholding chemical sprays.",
                        "Result Demonstration Control Plot Protocol: The control plot must represent typical local farmer practice (e.g., broadcast seed, uncalibrated fertilizer), NOT an artificially starved plot, ensuring valid scientific and economic comparison."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "SOP for Conducting a Result Demonstration: 1) Select a prominent, easily accessible roadside field owned by a reputable, representative farmer; 2) Divide field into two equal halves: Plot A (New Improved Package: certified hybrid seed, soil-test fertilizer, integrated pest scouting) and Plot B (Conventional Farmer Practice); 3) Erect clear, weatherproof roadside signboards detailing planting date, variety, and treatments; 4) Maintain strict identical planting dates and weed management across both plots; 5) Host a community Field Day at physiological maturity; 6) Harvest, weigh, and compute gross margins side-by-side in full view of the assembled farming community."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Demonstration Plot Bias: Result demo placed in an unrepresentative, exceptionally fertile valley bottom with excessive irrigation, causing visiting farmers to dismiss results as 'only working because of special government favoritism.'",
                        "Extension Fatigue: Community farmers exhausted by endless uncoordinated surveys and repetitive theoretical meetings that fail to produce tangible yield gains."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: An agricultural extension director has an annual outreach budget of $45,000 to combat Fall Armyworm (Spodoptera frugiperda) across a province of 30,000 maize farmers. The director evaluates three distinct extension delivery channels:\n- Strategy 1 (Individual Farm Visits): Cost = $30 per farm visit; Historical adoption rate = 65% of visited farmers.\n- Strategy 2 (Farmer Field Schools - Group): Cost = $600 per 25-farmer cohort season; Historical adoption rate = 40% of cohort participants.\n- Strategy 3 (Community Radio Campaign - Mass): Cost = $9,000 total production and airtime; Reaches 60% of all farmers in province (18,000 farmers); Historical adoption rate = 5% of listeners.\n\n1) Calculate the total number of farmers reached and the number of adopting farmers under Strategy 1 if the entire $45,000 budget is spent on farm visits.\n2) Calculate the total number of farmers reached and the number of adopting farmers under Strategy 2 if the entire budget is spent on Farmer Field Schools.\n3) Calculate the adopting farmers under Strategy 3 (costing $9,000) and allocate the remaining $36,000 to Strategy 2 (FFS) to determine total adopters in an integrated blended strategy.\n4) Calculate the Cost Per Adopter ($/adopting farmer) for each pure strategy and for the blended strategy.\n\nSolution:\n1) Strategy 1 (Individual Visits):\nVisits conducted = $45,000 / $30 = 1,500 farmers reached.\nAdopters = 1,500 x 0.65 = 975 adopting farmers.\nCost per Adopter = $45,000 / 975 = $46.15/adopter.\n\n2) Strategy 2 (Farmer Field Schools):\nFFS Cohorts funded = $45,000 / $600 = 75 cohorts.\nFarmers reached = 75 x 25 = 1,875 farmers.\nAdopters = 1,875 x 0.40 = 750 adopting farmers.\nCost per Adopter = $45,000 / 750 = $60.00/adopter.\n\n3) Strategy 3 Combined with Strategy 2 (Blended Model):\n- Radio Campaign: Cost = $9,000. Reaches 18,000 farmers. Adopters = 18,000 x 0.05 = 900 adopting farmers.\n- Remaining Budget for FFS = $45,000 - $9,000 = $36,000.\nFFS cohorts funded = $36,000 / $600 = 60 cohorts.\nFFS participants = 60 x 25 = 1,500 farmers.\nFFS adopters = 1,500 x 0.40 = 600 adopting farmers.\nTotal Adopters (Blended) = 900 (radio) + 600 (FFS) = 1,500 adopting farmers.\n\n4) Cost per Adopter (Blended) = $45,000 / 1,500 = $30.00/adopter.\n(The blended model achieves 1,500 adopters at $30/adopter, vastly outperforming pure individual visits (975 adopters at $46.15) or pure FFS (750 adopters at $60.00))."
                    ),
                    commonExamQuestions = listOf(
                        "Differentiate between a Method Demonstration and a Result Demonstration in terms of purpose, duration, and execution protocol.",
                        "Explain the operational philosophy, weekly structure, and tools (AESA) of a season-long Farmer Field School (FFS).",
                        "Compare individual, group, and mass extension methods in terms of cost per contact, feedback speed, and behavioral change impact."
                    ),
                    practicalTakeaway = "Always establish a side-by-side untreated farmer-practice control plot right next to any new technology demonstration: farmers will only adopt when they witness the visual and economic contrast with their own eyes.",
                    authoritativeTextbookRef = "Agricultural Extension (Van den Ban & Hawkins, Ch. 5-7); Farmer Field School: Guidance Document (FAO, Rome)"
                ),
                CurriculumSubtopic(
                    id = "aex_smcrf_model",
                    code = "f",
                    title = "Source, Message, Channel, Receiver, and Feedback (SMCRF Model)",
                    coreDefinition = "David K. Berlo's linear and transactional communication framework adapted to agricultural technology transfer, dissecting encoding, sensory channels, decoding, noise, homophily/heterophily, and the critical feedback loop.",
                    keyMechanismsAndFormulas = listOf(
                        "Five Core Components of SMCRF: 1) Source (originator/encoder: researcher, agronomist, extension agent; requires high credibility, empathy, and technical competence); 2) Message (content, code, treatment, structure); 3) Channel (sensory transmission vehicle: Seeing, Hearing, Touching, Smelling, Tasting); 4) Receiver (decoder: smallholder farmer, rural household; requires matching decoding capability); 5) Feedback (return response validating whether message was understood, rejected, or modified).",
                        "Homophily vs. Heterophily (Rogers): Homophily is the degree to which interacting pairs share similar attributes (language, socio-economic status, beliefs); heterophily is the degree to which they differ. Communication is most effective when source and receiver are homophilous.",
                        "Four Types of Communication Noise: Physical noise (noisy tractor engine, heavy downpour during meeting); Physiological noise (hunger, illness, hearing impairment); Psychological noise (mistrust of government, deep-seated risk aversion); Semantic noise (incomprehensible academic jargon, confusing chemical dilution ratios)."
                    ),
                    detailedElaboration = "Effective agricultural communication requires close alignment between the source's communication skills and the receiver's decoding capacity. If an urban university-trained agronomist transmits recommendations using incomprehensible technical jargon (e.g., 'apply 50 kg/ha of diammonium phosphate at crown root initiation') without translating into local farmer measures (e.g., 'one soda bottle cap per planting hole at knee-height'), decoding fails completely due to semantic noise.\n\nThe Feedback Loop is the most vital component in two-way agricultural communication. Without feedback, extension is a one-way megaphone. Continuous feedback alerts the extension service that a recommended hybrid seed is too difficult to pound with traditional pestles, or that a recommended pesticide kills beneficial pollinators, enabling rapid technical adjustments before widespread economic damage occurs.",
                    biologicalAndChemicalDetails = listOf(
                        "Multisensory Encoding in Agronomy: Farmers evaluate crop and soil health through multisensory channels: visual leaf color (SPAD chlorophyll meter), olfactory smell of healthy humic compost versus anaerobic rot, tactile crumb texture of soil ribbons, and auditory crunch of dry grain at 14% storage moisture.",
                        "Semantic Translation of Chemical Formulations: Translating technical acronyms (EC = Emulsifiable Concentrate, WP = Wettable Powder, SC = Suspension Concentrate) into physical instructions (e.g., 'milky liquid that turns cloudy in water' vs. 'powder requiring pre-slurry stirring')."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "SOP for Designing Low-Literacy Extension Advisory Flyers: 1) Limit text to maximum 15 words per illustration; 2) Use photorealistic full-color images or clear local line drawings with local clothing and tools; 3) Depict physical dosage using familiar local containers (e.g., empty matchbox = 10 grams, crown soda bottle cap = 5 ml); 4) Sequence steps strictly from left-to-right and top-to-bottom using numbered panels; 5) Pre-test prototype flyer on 15 non-literate farmers: ask them to describe the procedure without prompting; 6) Revise any visual element that causes misinterpretation before mass printing."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Semantic Noise Failure: Farmers applying a concentrated systemic herbicide as a foliar insect spray because the agro-dealer used the vague term 'medicine' for all chemicals.",
                        "Feedback Breakdown Indicator: Government extension agents continuing to distribute a specific cassava variety for three consecutive years despite farmers routinely feeding it to pigs because of bitter taste and cooking hardness."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: An extension bulletin instructs smallholders: 'To control diamondback moth larvae, apply Chlorantraniliprole 20% SC at a concentration of 0.3 ml per liter of water.' A field audit of 150 vegetable farmers finds that 80% own standard 16-liter knapsack sprayers and use standard crown bottle caps (which hold exactly 5.0 ml of liquid) to measure chemicals.\n1) Calculate the exact volume of Chlorantraniliprole 20% SC required per 16-liter knapsack sprayer.\n2) Calculate how many crown bottle caps of chemical should be added per knapsack tank.\n3) Identify the specific communication noise in the original bulletin and rewrite the instructional text to eliminate this noise for local farmers.\n\nSolution:\n1) Required Chemical Volume per Tank:\nConcentration = 0.3 ml/L\nTank capacity = 16 Liters\nChemical volume required = 16 L x 0.3 ml/L = 4.8 ml per knapsack sprayer.\n\n2) Bottle Cap Conversion:\n1 Crown bottle cap = 5.0 ml.\n4.8 ml / 5.0 ml per cap = 0.96 bottle cap (essentially 1 level crown bottle cap).\n\n3) Noise Analysis and Message Rewrite:\n- Identified Noise: Semantic and operational calculation noise (farmers do not possess 0.1 ml graduated pipettes in the field).\n- Rewritten Farmer Message: 'For each 16-liter knapsack sprayer tank filled with clean water, add exactly ONE LEVEL CROWN SODA BOTTLE CAP (5 ml) of medicine. Stir well before spraying.'"
                    ),
                    commonExamQuestions = listOf(
                        "Diagram David Berlo's SMCRF communication model and explain how semantic noise disrupts message adoption.",
                        "Explain the concept of homophily and describe how village-level lead farmers leverage homophily to accelerate tech adoption.",
                        "Why is the feedback loop considered the most critical component in two-way agricultural communication?"
                    ),
                    practicalTakeaway = "Translate all technical dosage recommendations into familiar local farm metrics (e.g., tin cups, matchboxes, crown bottle caps) and always ask the farmer to demonstrate the procedure back to you to verify feedback.",
                    authoritativeTextbookRef = "The Process of Communication (David K. Berlo, Holt, Rinehart & Winston); Communication for Rural Development (FAO, Rome)"
                ),
                CurriculumSubtopic(
                    id = "aex_adoption_process",
                    code = "g",
                    title = "The Innovation Adoption Process & Adopter Categories",
                    coreDefinition = "The psychological stages an individual passes through from first knowledge of an agricultural innovation to sustained integration, and the sociometric diffusion of innovations through social systems over time (Everett M. Rogers).",
                    keyMechanismsAndFormulas = listOf(
                        "The Five Stages of Adoption (AIETA): 1. Awareness (individual first learns of innovation existence but lacks details); 2. Interest (actively seeks additional technical and economic facts); 3. Evaluation (mental trial; weighs risks, profitability, and family feasibility); 4. Trial (small-scale physical application to test performance on own farm); 5. Adoption (commits to full-scale continuous application).",
                        "Rogers' Five Adopter Categories (Bell Curve Distribution): Innovators (2.5%, venturesome, high risk tolerance, wealthy, cosmopolite); Early Adopters (13.5%, respected community opinion leaders, key target for extension); Early Majority (34.0%, deliberate, adopt just before average member); Late Majority (34.0%, skeptical, adopt due to economic necessity or peer pressure); Laggards (16.0%, traditional, suspicious of change, resource-constrained).",
                        "Five Perceived Attributes of Innovations Influencing Adoption Rate: 1. Relative Advantage (perceived economic profitability or labor savings; adoption accelerates if return >30%); 2. Compatibility (alignment with local farming values, past habits, and cultural food preferences); 3. Complexity (perceived difficulty to understand and execute; inversely related to adoption); 4. Trialability / Divisibility (ability to test on small garden scale without sunk debt); 5. Observability (tangibility of visible green contrast or yield difference to neighbors)."
                    ),
                    detailedElaboration = "Technological change follows an S-shaped diffusion curve over time. In the early phase, diffusion is slow while only Innovators and Early Adopters experiment with the technology. Once the adoption rate reaches the 'tipping point' (approximately 16% cumulative adoption, encompassing Innovators + Early Adopters), diffusion accelerates dramatically as the Early Majority jumps in through peer imitation, before finally plateauing as the Late Majority and Laggards adopt.\n\nThe Crucial Role of Early Adopters: A frequent mistake made by inexperienced extension workers is focusing exclusively on Innovators (2.5%). Innovators are often seen as wealthy eccentrics or outsiders by ordinary villagers ('He can afford to lose money if that new crop fails; we cannot'). In contrast, Early Adopters (13.5%) are respected, local opinion leaders whose farming practices serve as the community benchmark. When an Early Adopter adopts, the rest of the village follows.",
                    biologicalAndChemicalDetails = listOf(
                        "Biological Divisibility of Agricultural Technologies: Seed and chemical fertilizers are highly divisible (a farmer can purchase 1 kg of seed and test it on 100 m²), resulting in high trialability. Combine harvesters and four-wheel tractors are indivisible capital assets, exhibiting low trialability and slower adoption unless custom-hiring markets exist.",
                        "Taste and Cooking Compatibility Constraints: Many biologically superior, high-yielding crop varieties (e.g., high-protein quality maize or high-amylose rice) fail in the adoption phase because of unpalatable texture, poor milling recovery, or undesirable porridge cooking aroma."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "SOP for Sociometric Identification of Village Opinion Leaders (Early Adopters): 1) Conduct a brief confidential survey of 50 randomly selected farming household heads in the village; 2) Ask two sociometric questions: 'If you faced a severe unknown pest in your crop, which fellow farmer in this village would you visit for advice?' and 'Whose farming decisions in this community do you respect most?'; 3) Tally the citation frequencies to construct a sociogram network; 4) Identify the individuals receiving the highest clustering of peer nominations (true Opinion Leaders); 5) Recruit these identified Opinion Leaders as primary demonstration plot partners."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Discontinuance / Post-Adoption Abandonment: Farmers who adopted a new high-yielding crop for two seasons suddenly abandon it and revert to ancestral varieties due to lack of local market demand or high cost of hybrid seed renewal.",
                        "Innovator Isolation: Demonstration plots established on the farm of a wealthy absentee landlord being actively ignored by surrounding subsistence tenant farmers."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: An agricultural extension service introduces a drought-tolerant hybrid sorghum variety to a communal agrarian district consisting of 5,000 farming households. Based on Everett Rogers' classic diffusion of innovations distribution:\n1) Calculate the exact number of farming households expected to fall into each of the five adopter categories:\n   - Innovators (2.5%)\n   - Early Adopters (13.5%)\n   - Early Majority (34.0%)\n   - Late Majority (34.0%)\n   - Laggards (16.0%)\n2) Identify the critical cumulative 'Tipping Point' percentage and calculate the exact total number of adopting households required to achieve self-sustaining social diffusion.\n3) Explain why an extension campaign that sells hybrid sorghum seed exclusively in 50-kg commercial bags will fail during the 'Trial' stage for smallholders, and prescribe an operational packaging solution.\n\nSolution:\n1) Adopter Category Population Breakdown:\n- Innovators (2.5%) = 5,000 x 0.025 = 125 households.\n- Early Adopters (13.5%) = 5,000 x 0.135 = 675 households.\n- Early Majority (34.0%) = 5,000 x 0.340 = 1,700 households.\n- Late Majority (34.0%) = 5,000 x 0.340 = 1,700 households.\n- Laggards (16.0%) = 5,000 x 0.160 = 800 households.\nTotal = 5,000 households.\n\n2) Tipping Point Threshold:\nThe tipping point occurs when Innovators (2.5%) and Early Adopters (13.5%) have both adopted:\nCumulative Tipping Point = 2.5% + 13.5% = 16.0%.\nNumber of adopting households needed = 125 + 675 = 800 households.\nOnce these 800 households adopt, peer-to-peer social observation and word-of-mouth drive spontaneous adoption among the 1,700 Early Majority households without heavy promotional subsidization.\n\n3) Trial Stage Packaging Solution:\n- Constraint: A 50-kg bag requires substantial cash outlay and plants several hectares, violating the principle of 'Trialability' (divisibility). Risk-averse smallholders in the Evaluation/Trial stage refuse to gamble their entire season's land on an unproven seed.\n- Extension Solution: Repackage seed into 1-kg or 2-kg trial mini-packs sold at nominal cost. This allows farmers to test the variety on a 500 m² trial plot, visually observe drought resilience with near-zero financial risk, and confidently transition from Trial to full Adoption."
                    ),
                    commonExamQuestions = listOf(
                        "List the five stages of the adoption process (AIETA) and identify which extension methods are most effective at each stage.",
                        "Diagram Everett Rogers' bell curve of adopter categories, indicating the percentage of the population in each group and their primary behavioral characteristics.",
                        "Explain the five perceived attributes of innovations and discuss how high trialability accelerates technology adoption."
                    ),
                    practicalTakeaway = "Package new agricultural technologies into affordable trial-sized sample packs (e.g., 1–2 kg seed packets): maximizing 'trialability' dramatically lowers risk perception, accelerating the transition from Evaluation to Trial.",
                    authoritativeTextbookRef = "Diffusion of Innovations (Everett M. Rogers, 5th Ed., Free Press, Ch. 5-7); Adoption of New Ideas and Practices (Herbert F. Lionberger, Iowa State Univ. Press)"
                )
            ),
            examFocusPoints = listOf(
                "The 5 stages of the adoption process: Awareness, Interest, Evaluation, Trial, Adoption (AIETA)",
                "Everett Rogers' 5 adopter categories and their exact percentages (2.5%, 13.5%, 34%, 34%, 16%)",
                "The five perceived attributes of innovations influencing rate of adoption",
                "Method Demonstration vs. Result Demonstration and Farmer Field Schools (FFS)"
            ),
            practicalFieldApplications = listOf(
                "Conducting sociometric peer surveys to identify respected community Early Adopters",
                "Designing promotional campaigns tailored to the information channel preferences of each adoption stage",
                "Establishing roadside side-by-side Result Demonstration plots with untreated control comparisons"
            ),
            recommendedTextbookIds = listOf("book_rogers_diffusion_innovations", "book_van_den_ban_extension", "book_lionberger_adoption")
        )
    )
}
