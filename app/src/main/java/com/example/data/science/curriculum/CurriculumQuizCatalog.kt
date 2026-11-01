package com.example.data.science.curriculum

import com.example.model.science.CurriculumPillarType
import com.example.model.science.CurriculumQuizItem

object CurriculumQuizCatalog {

    val quizItems: List<CurriculumQuizItem> = listOf(
        // CROP SCIENCE
        CurriculumQuizItem(
            id = "quiz_cs_1",
            pillar = CurriculumPillarType.CROP_SCIENCE,
            topicCode = "1.a.iii",
            question = "Which plant hormone maintains seed dormancy, and which hormone promotes germination by inducing de novo synthesis of α-amylase in the aleurone layer?",
            options = listOf(
                "Auxin maintains dormancy; Cytokinin induces α-amylase",
                "Abscisic Acid (ABA) maintains dormancy; Gibberellic Acid (GA) induces α-amylase",
                "Ethylene maintains dormancy; Brassinosteroids induce α-amylase",
                "Gibberellic Acid maintains dormancy; Abscisic Acid induces α-amylase"
            ),
            correctIndex = 1,
            explanation = "Seed dormancy is governed by the antagonistic ratio of ABA to GA. ABA synthesized during embryo maturation prevents vivipary; upon imbibition, GA synthesized by the embryo diffuses to the aleurone layer to trigger transcription of hydrolytic α-amylase enzymes that break down starch.",
            referencedBookId = "book_plant_physiol_taiz"
        ),
        CurriculumQuizItem(
            id = "quiz_cs_2",
            pillar = CurriculumPillarType.CROP_SCIENCE,
            topicCode = "1.d",
            question = "In an intercropping trial, sole maize yields 4.0 t/ha and sole bean yields 1.0 t/ha. When intercropped, maize yields 3.2 t/ha and bean yields 0.6 t/ha. What is the Land Equivalent Ratio (LER)?",
            options = listOf(
                "0.95 (Disadvantageous)",
                "1.10 (Marginal)",
                "1.40 (40% land saving)",
                "1.85 (85% land saving)"
            ),
            correctIndex = 2,
            explanation = "LER = (Y_maize_inter / Y_maize_sole) + (Y_bean_inter / Y_bean_sole) = (3.2 / 4.0) + (0.6 / 1.0) = 0.80 + 0.60 = 1.40. An LER of 1.40 means that 40% more sole-crop land area would be needed to produce equal yield.",
            referencedBookId = "book_principles_field_crop"
        ),

        // SOIL SCIENCE
        CurriculumQuizItem(
            id = "quiz_ss_1",
            pillar = CurriculumPillarType.SOIL_SCIENCE,
            topicCode = "2.e",
            question = "Why does severe phosphorus deficiency in crops frequently occur in acidic tropical soils with pH < 5.0, even when total soil phosphorus is high?",
            options = listOf(
                "Phosphorus volatilizes rapidly as phosphine gas in warm temperatures",
                "Soluble aluminum (Al3+) and iron (Fe3+) react with orthophosphate to form insoluble precipitates (strengite and variscite)",
                "Calcium carbonate binds all available orthophosphate into rock phosphate apatite",
                "Soil microbes consume all phosphorus and transport it below the rooting depth"
            ),
            correctIndex = 1,
            explanation = "In acid soils (pH < 5.5), high concentrations of soluble ionic Al3+ and Fe3+ react vigorously with H2PO4- to form insoluble aluminum and iron phosphate precipitates, fixing up to 80–90% of applied fertilizer P into forms unavailable to roots.",
            referencedBookId = "book_havlin_soil_fertility"
        ),
        CurriculumQuizItem(
            id = "quiz_ss_2",
            pillar = CurriculumPillarType.SOIL_SCIENCE,
            topicCode = "2.f",
            question = "A soil testing laboratory reports an Exchangeable Sodium Percentage (ESP) of 22% and Electrical Conductivity (ECe) of 1.8 dS/m. How is this soil classified and what is the required chemical amendment?",
            options = listOf(
                "Saline soil; remediated by washing with distilled water only",
                "Sodic soil; remediated by applying Gypsum (CaSO4.2H2O) followed by leaching",
                "Acid soil; remediated by applying Calcitic Agricultural Lime (CaCO3)",
                "Saline-Sodic soil; remediated by sulfur burning"
            ),
            correctIndex = 1,
            explanation = "An ESP > 15% with ECe < 4.0 dS/m defines a Sodic soil. High exchangeable sodium causes severe clay dispersion and aggregate collapse. Gypsum must be applied first: its soluble calcium (Ca2+) displaces adsorbed sodium (Na+) into the soil solution, after which excess sodium can be safely leached out with irrigation water.",
            referencedBookId = "book_brady_weil_nature_soils"
        ),

        // ANIMAL SCIENCE
        CurriculumQuizItem(
            id = "quiz_as_1",
            pillar = CurriculumPillarType.ANIMAL_SCIENCE,
            topicCode = "3.c",
            question = "In the ruminant stomach, which volatile fatty acid (VFA) serves as the primary gluconeogenic substrate for liver synthesis of glucose and milk lactose?",
            options = listOf(
                "Acetate (Acetic Acid)",
                "Butyrate (Butyric Acid)",
                "Propionate (Propionic Acid)",
                "Valerate (Valeric Acid)"
            ),
            correctIndex = 2,
            explanation = "Propionate is the sole volatile fatty acid in ruminants that is gluconeogenic. It is absorbed through the rumen papillae and transported to the liver, where it enters the citric acid cycle to synthesize glucose via oxaloacetate, directly driving milk volume and lactose secretion.",
            referencedBookId = "book_mcdonald_animal_nutrition"
        ),
        CurriculumQuizItem(
            id = "quiz_as_2",
            pillar = CurriculumPillarType.ANIMAL_SCIENCE,
            topicCode = "3.h.iv",
            question = "Why do domestic carabaos / water buffaloes (Bubalus bubalis) require regular water wallowing or shade during hot daytime hours compared to cattle?",
            options = listOf(
                "They lack a gallbladder and cannot metabolize fats without cooling",
                "They have approximately 1/6th the density of sweat glands compared to cattle and dark skin, making evaporative sweating inadequate",
                "They breathe exclusively through their mouth and require water cooling of the tongue",
                "Their rumen produces 10 times more heat than a bovine rumen"
            ),
            correctIndex = 1,
            explanation = "Water buffalo skin possesses only about 100–200 sweat glands per square centimeter (compared to ~1,000 in zebu cattle) and has a thick epidermis with dark melanin. Consequently, physical wallowing in water or mud is physiologically essential to dissipate heat by conduction and evaporation of mud crusts.",
            referencedBookId = "book_ensminger_animal_science"
        ),

        // CROP PROTECTION
        CurriculumQuizItem(
            id = "quiz_cp_1",
            pillar = CurriculumPillarType.CROP_PROTECTION,
            topicCode = "4.a.i",
            question = "When inspecting a field of wilting tomatoes, a scout cuts a symptomatic lower stem and suspends the cut end in a clear glass of water. A white, cloudy, viscous stream exudes from the cut xylem vessels within 2 minutes. What does this confirm?",
            options = listOf(
                "Fusarium oxysporum (fungal vascular wilt)",
                "Ralstonia solanacearum (bacterial wilt)",
                "Meloidogyne incognita (root-knot nematode infestation)",
                "Physiological blossom-end rot"
            ),
            correctIndex = 1,
            explanation = "The bacterial streaming test is a diagnostic field test for Ralstonia solanacearum. Millions of bacterial cells and slimy extracellular polysaccharides (EPS) streaming out of severed xylem bundles into water produce a distinctive milky white plume, differentiating bacterial wilt from fungal wilts (which do not stream).",
            referencedBookId = "book_agrios_plant_pathology"
        ),
        CurriculumQuizItem(
            id = "quiz_cp_2",
            pillar = CurriculumPillarType.CROP_PROTECTION,
            topicCode = "4.e",
            question = "A knapsack sprayer has a nozzle output of 0.8 L/min. The operator walks at 3.6 km/h with an effective spray swath width of 1.0 meter. What is the spray application volume in Liters per hectare?",
            options = listOf(
                "133.3 L/ha",
                "200.0 L/ha",
                "250.0 L/ha",
                "320.0 L/ha"
            ),
            correctIndex = 0,
            explanation = "Spray Volume (L/ha) = [Nozzle Output (L/min) x 600] / [Speed (km/h) x Swath (m)] = [0.8 x 600] / [3.6 x 1.0] = 480 / 3.6 = 133.33 L/ha.",
            referencedBookId = "book_matthews_pesticide_application"
        ),

        // AGRICULTURAL ECONOMICS & MARKETING
        CurriculumQuizItem(
            id = "quiz_ae_1",
            pillar = CurriculumPillarType.AGRI_ECONOMICS,
            topicCode = "5.c",
            question = "In neoclassical agricultural production theory, why is a rational farm manager strictly constrained to operate inside Stage II of the production function?",
            options = listOf(
                "In Stage I Marginal Product is negative; in Stage III Total Product is zero",
                "In Stage I efficiency of fixed resources is increasing (MPP > APP); in Stage III additional input reduces total output (MPP < 0)",
                "Stage II is the only stage where prices of inputs are legally regulated by the ministry",
                "Stage II guarantees that fixed costs equal zero"
            ),
            correctIndex = 1,
            explanation = "Stage I is irrational because the variable input is insufficient to fully exploit the fixed resource (Average Physical Product is still rising). Stage III is irrational because adding more input causes physical toxicity or crowding, resulting in negative marginal product (MPP < 0). Only Stage II exhibits positive, diminishing marginal returns (MPP < APP and MPP > 0) where profit maximization occurs.",
            referencedBookId = "book_drummond_agri_economics"
        ),

        // AGRICULTURAL EXTENSION & COMMUNICATIONS
        CurriculumQuizItem(
            id = "quiz_aex_1",
            pillar = CurriculumPillarType.AGRI_EXTENSION,
            topicCode = "6.g",
            question = "According to Everett Rogers' diffusion of innovations theory, which adopter category represents the local community opinion leaders whom extension agents should primarily target to catalyze mass adoption?",
            options = listOf(
                "Innovators (2.5%)",
                "Early Adopters (13.5%)",
                "Late Majority (34%)",
                "Laggards (16%)"
            ),
            correctIndex = 1,
            explanation = "Early Adopters (13.5%) are respected, local opinion leaders whose judgment is trusted by peers in the community. Innovators (2.5%), while first to try, are often perceived as cosmopolite or eccentric by locals. Once Early Adopters validate an innovation, the Early Majority (34%) follows swiftly.",
            referencedBookId = "book_rogers_diffusion_innovations"
        )
    )
}
