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
                    id = "aex_philosophy_andragogy",
                    code = "a, d",
                    title = "Philosophy of Extension & Andragogy",
                    coreDefinition = "The educational philosophy of 'helping people help themselves' through participatory non-formal education and experiential adult learning.",
                    keyMechanismsAndFormulas = listOf(
                        "Core Extension Philosophy: 'Helping rural people help themselves' through self-reliance, democratic decision-making, and learning-by-doing.",
                        "Knowles' Principles of Andragogy (Adult Learning): Adults are self-directed learners; bring rich reservoir of life experience; learning is problem-centered and immediate in application; driven by internal motivation.",
                        "Cone of Experience (Edgar Dale): People retain 10% of what they read, 20% of what they hear, 30% of what they see, 50% of what they see and hear, 70% of what they say/discuss, and 90% of what they do in real hands-on practice."
                    ),
                    detailedElaboration = "Agricultural extension is not a top-down technical lecture; it is a collaborative non-formal educational process. Adult farmers reject condescending theoretical teaching. Extension workers succeed by respecting farmers' Indigenous Technical Knowledge (ITK), validating their existing survival strategies, and framing new innovations around immediate, practical livelihood problems. The extension educator acts as a catalyst and facilitator rather than an authoritarian instructor.",
                    commonExamQuestions = listOf(
                        "Explain Malcolm Knowles' core assumptions of andragogy and contrast adult learners with pediatric learners in agricultural training.",
                        "Analyze Edgar Dale's Cone of Experience in the context of agricultural demonstration plots versus classroom lectures.",
                        "What is the foundational philosophy of agricultural extension and how does it prevent dependency?"
                    ),
                    practicalTakeaway = "In training sessions, spend no more than 20% of time lecturing: spend 80% on hands-on field practice (touching seedlings, mixing sprayers, pruning trees) to achieve 90% knowledge retention."
                ),
                CurriculumSubtopic(
                    id = "aex_devcom_policy",
                    code = "b, c",
                    title = "Development Communication & Commodity Policy",
                    coreDefinition = "The systematic application of communication processes, participatory media, and agricultural commodity frameworks to catalyze socio-economic transformation.",
                    keyMechanismsAndFormulas = listOf(
                        "Development Communication (DevCom) Tenet: Purposive, goal-oriented, participatory communication designed to transform attitudes and uplift marginalized rural communities (Nora Quebral).",
                        "Participatory Rural Appraisal (PRA) Tools: Transect walks, resource mapping, seasonal calendars, Venn diagrams of village institutions, and matrix scoring.",
                        "Agricultural Commodity Policy Levers: Minimum Support Prices (MSP), strategic grain reserves, tariff protections, phytosanitary export protocols, and crop revenue insurance."
                    ),
                    detailedElaboration = "Development communication moves beyond conventional information dissemination to empower community dialogue. Through Participatory Rural Appraisal (PRA), outside specialists 'hand over the stick'—enabling villagers to map their own soils, track seasonal labor bottlenecks, and articulate their felt needs. Agricultural commodity policies establish price floors, buffer stocks, and quality standards that protect farmers from ruinous postharvest price crashes and global commodity dumping.",
                    commonExamQuestions = listOf(
                        "Define Development Communication (DevCom) according to Nora Quebral and contrast it with commercial advertising.",
                        "Explain how a seasonal calendar constructed during a PRA exercise helps plan extension campaign interventions.",
                        "Discuss the trade-offs of government Minimum Support Price (MSP) policies on market efficiency and fiscal budgets."
                    ),
                    practicalTakeaway = "Conduct a transect walk with village elders before launching an extension project: walking the physical elevation gradient reveals historical flood lines, pest hot spots, and soil variations unseen in official maps."
                )
            ),
            examFocusPoints = listOf(
                "Core philosophical tenets of Agricultural Extension ('Helping people to help themselves')",
                "Differences between Pedagogy (child learning) and Andragogy (adult learning)",
                "Participatory Rural Appraisal (PRA) methodology and community resource mapping"
            ),
            practicalFieldApplications = listOf(
                "Facilitating community seasonal calendar workshops to map labor and pest outbreaks",
                "Constructing participatory problem trees linking root causes of low farm yields to actionable solutions"
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
                    coreDefinition = "The classified operational channels utilized by extension services to engage agricultural clienteles according to group size, cost, and contact depth.",
                    keyMechanismsAndFormulas = listOf(
                        "Individual Methods: Farm/home visits, office consultations, telephone advisory; highest direct impact and trust, but extremely high cost per contact and low coverage.",
                        "Group Methods: Farmer Field Schools (FFS), Method Demonstrations ('how-to-do'), Result Demonstrations ('why-to-do', comparing treated vs. control plots side-by-side), field days, study tours.",
                        "Mass Methods: Community radio, agricultural TV, printed bulletins/leaflets, bulk SMS, mobile smartphone advisory apps; massive reach at ultra-low per-contact cost, but low personal feedback.",
                        "Method Demonstration vs. Result Demonstration: Method demo teaches a specific physical skill (e.g., pruning, grafting); Result demo proves economic superiority of a practice over time (e.g., hybrid seed vs. local seed)."
                    ),
                    detailedElaboration = "A balanced extension program combines all three tiers: mass methods (radio, SMS) create widespread initial awareness of an outbreak or new variety; group methods (Farmer Field Schools) provide experiential group discovery learning and peer validation; individual farm visits resolve complex, site-specific agronomic crises. Seaman Knapp’s classic maxim anchors demonstration teaching: 'What a man sees, he may doubt; what he hears, he may also doubt; but what he does himself, he cannot doubt.'",
                    commonExamQuestions = listOf(
                        "Differentiate between a Method Demonstration and a Result Demonstration in agricultural extension.",
                        "Compare individual, group, and mass extension methods in terms of cost per contact, feedback speed, and behavioral impact.",
                        "Describe the structure and operational principles of a season-long Farmer Field School (FFS)."
                    ),
                    practicalTakeaway = "Always establish a side-by-side untreated farmer-practice control plot next to any new technology demonstration: farmers will only adopt if they witness the visual and economic contrast with their own eyes."
                ),
                CurriculumSubtopic(
                    id = "aex_smcrf_model",
                    code = "f",
                    title = "The SMCRF Communication Model",
                    coreDefinition = "David Berlo's linear communication framework consisting of Source, Message, Channel, Receiver, and Feedback, alongside communication noise and barriers.",
                    keyMechanismsAndFormulas = listOf(
                        "Five Components of SMCRF: Source (encoder e.g., researcher, extension agent), Message (content, code, treatment), Channel (sensory transmission vehicle e.g., spoken, visual, print), Receiver (decoder e.g., farmer, rural household), Feedback (response validating message reception and decoding).",
                        "Homophily vs. Heterophily: Homophily (similarity in language, culture, socioeconomic status between source and receiver accelerates trust); Heterophily (divergent backgrounds can create perceptual barriers).",
                        "Communication Noise: Physical noise, physiological noise, psychological prejudice, and semantic distortion (e.g., using complex academic jargon instead of local vernacular)."
                    ),
                    detailedElaboration = "Effective agricultural communication requires close alignment between the source's communication skills and the receiver's decoding capacity. If an agronomist transmits recommendations using incomprehensible technical jargon (e.g., 'apply 50 kg/ha of diammonium phosphate at crown root initiation') without translating into local farmer measures (e.g., 'one soda bottle cap per planting hole at knee-height'), decoding fails completely. Feedback is indispensable: without an active feedback loop, the extension agent operates blindly, unaware of misunderstandings or local resource constraints.",
                    commonExamQuestions = listOf(
                        "Diagram Berlo's SMCRF communication model and explain how semantic noise disrupts message adoption.",
                        "Explain the concept of homophily and describe how village-level lead farmers leverage homophily to accelerate tech adoption.",
                        "Why is the feedback loop considered the most critical component in two-way agricultural communication?"
                    ),
                    practicalTakeaway = "Translate all technical dosage recommendations into familiar local farm metrics (e.g., tin cups, matchboxes, crown bottle caps) and always ask the farmer to demonstrate the procedure back to you to verify feedback."
                ),
                CurriculumSubtopic(
                    id = "aex_adoption_process",
                    code = "g",
                    title = "The Innovation Adoption Process & Adopter Categories",
                    coreDefinition = "The psychological stages an individual passes from first knowledge of an innovation to sustained adoption, and the bell-curve categorization of population adopter speed.",
                    keyMechanismsAndFormulas = listOf(
                        "The 5 Stages of Adoption (AIETA): 1. Awareness (first hears of innovation), 2. Interest (actively seeks additional information), 3. Evaluation (mentally weighs costs, benefits, and risks), 4. Trial (tests innovation on small, low-risk scale), 5. Adoption (commits to full-scale continuous application).",
                        "Everett Rogers' 5 Adopter Categories: Innovators (2.5%, venturesome, risk-takers, high resources), Early Adopters (13.5%, respected community opinion leaders, key target for extension), Early Majority (34%, deliberate, adopt just before average), Late Majority (34%, skeptical, adopt due to economic necessity or peer pressure), Laggards (16%, traditional, suspicious of change, resource-constrained).",
                        "Five Attributes of Innovations: Relative Advantage, Compatibility, Complexity, Trialability (divisibility), and Observability."
                    ),
                    detailedElaboration = "Technological change follows an S-shaped diffusion curve over time. Extension agents must strategically target Early Adopters (13.5%): these individuals are local opinion leaders whose endorsement triggers peer-to-peer imitation by the Early Majority. Innovators (2.5%), while eager to try new ideas, are often viewed as eccentric by villagers and lack opinion leadership. An innovation will diffuse rapidly if it possesses high relative advantage (economic return >30%), high compatibility with existing farming values, low complexity, high trialability (can be tested on a 10m x 10m garden plot), and high observability (visible green color or yield difference).",
                    commonExamQuestions = listOf(
                        "List the five stages of the adoption process (AIETA) and identify which extension methods are most effective at each stage.",
                        "Diagram Everett Rogers' bell curve of adopter categories, indicating the percentage of the population in each group.",
                        "Explain why extension agents should target Early Adopters rather than Innovators to achieve rapid technology diffusion."
                    ),
                    practicalTakeaway = "Package new agricultural technologies into trial-sized sample packs (e.g., 250g seed packets): maximizing 'trialability' dramatically lowers risk perception, accelerating transition from Evaluation to Trial."
                )
            ),
            examFocusPoints = listOf(
                "The 5 stages of the adoption process: Awareness, Interest, Evaluation, Trial, Adoption (AIETA)",
                "Everett Rogers' 5 adopter categories and their exact percentages (2.5%, 13.5%, 34%, 34%, 16%)",
                "The five perceived attributes of innovations influencing rate of adoption"
            ),
            practicalFieldApplications = listOf(
                "Identifying and training respected community Early Adopters as lead demo farmers",
                "Designing promotional campaigns tailored to the information channel preferences of each adoption stage"
            ),
            recommendedTextbookIds = listOf("book_rogers_diffusion_innovations", "book_van_den_ban_extension", "book_lionberger_adoption")
        )
    )
}
