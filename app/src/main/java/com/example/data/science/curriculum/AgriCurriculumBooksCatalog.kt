package com.example.data.science.curriculum

import com.example.model.science.AgriBookReference
import com.example.model.science.CurriculumPillarType

object AgriCurriculumBooksCatalog {

    val books: List<AgriBookReference> = listOf(
        // CROP SCIENCE
        AgriBookReference(
            id = "book_principles_field_crop",
            title = "Principles of Field Crop Production",
            authors = "John H. Martin, Warren H. Leonard, David L. Stamp",
            edition = "4th Edition",
            publisher = "Pearson / Macmillan Publishing",
            year = 2006,
            primaryPillar = CurriculumPillarType.CROP_SCIENCE,
            coverSubject = "Agronomy & Cereals / Grain Legumes",
            description = "The definitive classic agronomy textbook covering physiological principles of grain, fiber, oil, and forage crop production, botanical origins, and modern crop management practices.",
            keyChapters = listOf(
                "Chapter 3: Environmental Factors Influencing Crop Distribution",
                "Chapter 6: Seeding Practices and Stand Establishment",
                "Chapter 11: Corn and Sorghum Production and Physiology",
                "Chapter 14: Small Grains (Wheat, Barley, Oats, Rye)",
                "Chapter 18: Grain Legumes and Biological Nitrogen Fixation"
            ),
            syllabusTopicsCovered = listOf("1.a.i Cereals and Field Legumes", "1.a.iv Industrial Crops", "1.d Farming Systems"),
            whyItIsAuthoritative = "Used as the benchmark university text for agronomy worldwide for over 50 years, linking plant physiology directly to field tillage, seeding rates, and harvesting.",
            scholarSearchQuery = "Principles of Field Crop Production Martin Leonard Stamp"
        ),
        AgriBookReference(
            id = "book_plant_physiol_taiz",
            title = "Plant Physiology and Development",
            authors = "Lincoln Taiz, Eduardo Zeiger, Ian M. Møller, Angus Murphy",
            edition = "6th Edition",
            publisher = "Sinauer Associates / Oxford University Press",
            year = 2018,
            primaryPillar = CurriculumPillarType.CROP_SCIENCE,
            coverSubject = "Crop Physiology & Plant Biochemistry",
            description = "The world's leading textbook on plant physiology, detailing photosynthesis, cellular energetics, stomatal biophysics, hormone signaling cascades, and abiotic stress responses.",
            keyChapters = listOf(
                "Chapter 3: Water and Plant Cells (Water Potential Gradients)",
                "Chapter 8: Photosynthesis: The Light Reactions and Carbon Reactions (C3, C4, CAM)",
                "Chapter 15: Abscisic Acid and Seed Maturation / Dormancy",
                "Chapter 20: Gibberellins and α-Amylase Mobilization",
                "Chapter 24: Physiology of Environmental Stress"
            ),
            syllabusTopicsCovered = listOf("1.a.iii Seed Physiology", "1.f Crop Physiology & Ecology", "1.h Postharvest Management"),
            whyItIsAuthoritative = "Standard reference cited in peer-reviewed plant science literature, with definitive diagrams of photosynthetic electron transport and hormonal signal transduction.",
            scholarSearchQuery = "Plant Physiology and Development Taiz Zeiger"
        ),
        AgriBookReference(
            id = "book_hartmann_kester_propagation",
            title = "Hartmann & Kester's Plant Propagation: Principles and Practices",
            authors = "Fred T. Davies Jr., Robert L. Geneve, Sandra B. Wilson",
            edition = "9th Edition",
            publisher = "Pearson Higher Education",
            year = 2018,
            primaryPillar = CurriculumPillarType.CROP_SCIENCE,
            coverSubject = "Horticulture & Plant Propagation",
            description = "The global horticultural authority on vegetative propagation, grafting, budding, clonal rootstock physiology, seed biology, and aseptic micropropagation.",
            keyChapters = listOf(
                "Chapter 7: Seed Development and Dormancy Mechanisms",
                "Chapter 11: Biology of Grafting and Budding (Cambial Union)",
                "Chapter 12: Rootstock Selection and Scion Interactions in Orchards",
                "Chapter 17: Principles of Micropropagation and Tissue Culture Media"
            ),
            syllabusTopicsCovered = listOf("1.b.i Pomology", "1.b.iii Floriculture", "1.e Agricultural Biotechnology"),
            whyItIsAuthoritative = "Regarded as the 'bible of plant propagation' for commercial nursery managers and university horticultural faculties globally.",
            scholarSearchQuery = "Hartmann Kester Plant Propagation Principles Practices"
        ),
        AgriBookReference(
            id = "book_vegetable_welbaum",
            title = "Vegetable Production and Practices",
            authors = "Gregory E. Welbaum",
            edition = "1st Edition",
            publisher = "CABI Publishing",
            year = 2015,
            primaryPillar = CurriculumPillarType.CROP_SCIENCE,
            coverSubject = "Olericulture & Greenhouse Vegetable Farming",
            description = "Comprehensive guide to cool and warm season vegetable production, nursery raising, greenhouse plasticulture, irrigation scheduling, and nutritional disorders.",
            keyChapters = listOf(
                "Chapter 4: Greenhouse Production and Plasticulture",
                "Chapter 7: Solanaceous Crops (Tomato, Pepper, Eggplant, Potato)",
                "Chapter 9: Cucurbits (Melons, Cucumbers, Squashes)",
                "Chapter 12: Physiological Disorders (BER, Bolting, Catfacing)"
            ),
            syllabusTopicsCovered = listOf("1.b.ii Olericulture", "1.b.iv Landscape Management"),
            whyItIsAuthoritative = "Authored by CABI expert combining cutting-edge scientific research with practical commercial field grower guides across warm and temperate vegetable crops.",
            scholarSearchQuery = "Vegetable Production and Practices Gregory Welbaum CABI"
        ),
        AgriBookReference(
            id = "book_breeding_field_crops_sleper",
            title = "Breeding Field Crops",
            authors = "David A. Sleper, John M. Poehlman",
            edition = "5th Edition",
            publisher = "Blackwell Publishing / Wiley",
            year = 2006,
            primaryPillar = CurriculumPillarType.CROP_SCIENCE,
            coverSubject = "Plant Breeding & Quantitative Genetics",
            description = "Detailed coverage of plant reproductive systems, quantitative heritability, pedigree and bulk breeding, male sterility systems, and marker-assisted selection.",
            keyChapters = listOf(
                "Chapter 4: Quantitative Inheritance in Plant Breeding",
                "Chapter 6: Breeding Self-Pollinated Species (Pedigree, SSD)",
                "Chapter 7: Breeding Cross-Pollinated Species and Heterosis",
                "Chapter 9: Cytoplasmic Male Sterility and Hybrid Seed Production",
                "Chapter 10: Molecular Markers and Marker-Assisted Selection"
            ),
            syllabusTopicsCovered = listOf("1.c Plant Breeding and Genetics", "1.e Agricultural Biotechnology"),
            whyItIsAuthoritative = "The core university reference for plant breeders training for national agricultural research institutes and multinational seed corporations.",
            scholarSearchQuery = "Breeding Field Crops Sleper Poehlman"
        ),
        AgriBookReference(
            id = "book_seed_technology_copeland",
            title = "Principles of Seed Science and Technology",
            authors = "Lawrence O. Copeland, Miller B. McDonald",
            edition = "4th Edition",
            publisher = "Springer Science+Business Media",
            year = 2001,
            primaryPillar = CurriculumPillarType.CROP_SCIENCE,
            coverSubject = "Seed Physiology & Production Standards",
            description = "Covers seed chemistry, longevity equations, orthodox vs. recalcitrant storage, seed certification rules, conditioning machinery, and ISTA testing procedures.",
            keyChapters = listOf(
                "Chapter 4: Seed Longevity and Deterioration Kinetics",
                "Chapter 6: Seed Dormancy Mechanisms and Breaking Techniques",
                "Chapter 9: Seed Conditioning and Cleaning Technology",
                "Chapter 12: Seed Certification, Purity and Vigor Testing"
            ),
            syllabusTopicsCovered = listOf("1.a.iii Seed Physiology", "1.g Seed Technology & Production"),
            whyItIsAuthoritative = "Aligned with International Seed Testing Association (ISTA) and Association of Official Seed Analysts (AOSA) standards.",
            scholarSearchQuery = "Principles Seed Science Technology Copeland McDonald"
        ),
        AgriBookReference(
            id = "book_postharvest_wills",
            title = "Postharvest: An Introduction to the Physiology & Handling of Fruit and Vegetables",
            authors = "Ron Wills, John Golding",
            edition = "6th Edition",
            publisher = "CABI Publishing / UNSW Press",
            year = 2016,
            primaryPillar = CurriculumPillarType.CROP_SCIENCE,
            coverSubject = "Postharvest Management & Cold Chain",
            description = "A textbook focusing on postharvest respiration, climacteric fruit physiology, ethylene regulation via 1-MCP, modified atmospheres, and fungal decay mitigation.",
            keyChapters = listOf(
                "Chapter 2: Respiration and Climacteric Behavior",
                "Chapter 4: Ethylene Synthesis and Receptor Blockade",
                "Chapter 6: Temperature Management and Precooling Methods",
                "Chapter 8: Modified Atmosphere Packaging (MAP) and Storage"
            ),
            syllabusTopicsCovered = listOf("1.h Postharvest Management"),
            whyItIsAuthoritative = "The most widely adopted postharvest technology university text, bridging biochemical theory with industrial export packhouse logistics.",
            scholarSearchQuery = "Postharvest Introduction Physiology Handling Fruit Vegetables Wills Golding"
        ),

        // SOIL SCIENCE
        AgriBookReference(
            id = "book_brady_weil_nature_soils",
            title = "The Nature and Properties of Soils",
            authors = "Ray R. Weil, Nyle C. Brady",
            edition = "15th Edition",
            publisher = "Pearson Higher Education",
            year = 2016,
            primaryPillar = CurriculumPillarType.SOIL_SCIENCE,
            coverSubject = "Comprehensive Soil Science & Pedology",
            description = "The undisputed global master text of soil science, covering genesis, USDA soil taxonomy, soil physics, colloidal chemistry, microbiology, fertility, and land capability.",
            keyChapters = listOf(
                "Chapter 2: Formation of Soils from Parent Materials (Soil Horizons)",
                "Chapter 3: Soil Classification (The 12 USDA Soil Orders)",
                "Chapter 4: Soil Architecture and Physical Properties (Bulk Density, Porosity)",
                "Chapter 5: Soil Water: Characteristics and Behavior (AWC, Darcy's Law)",
                "Chapter 8: Soil Colloids: Seat of Soil Chemical and Physical Activity (CEC)",
                "Chapter 9: Soil Acidity, Liming, and Aluminum Toxicity",
                "Chapter 11: Soil Microbiology and the Soil Food Web"
            ),
            syllabusTopicsCovered = listOf(
                "2.a Soil Genesis & Development",
                "2.b Soil Taxonomy & Land Use",
                "2.c Soil Microbiology",
                "2.d Soil Physics",
                "2.e Soil Fertility and Management",
                "2.f Soil Chemistry"
            ),
            whyItIsAuthoritative = "Cited in tens of thousands of scientific publications; the indispensable textbook for soil scientists, agronomists, and civil engineers.",
            scholarSearchQuery = "The Nature and Properties of Soils Weil Brady 15th Edition"
        ),
        AgriBookReference(
            id = "book_havlin_soil_fertility",
            title = "Soil Fertility and Fertilizers: An Introduction to Nutrient Management",
            authors = "John L. Havlin, Samuel L. Tisdale, Werner L. Nelson, James D. Beaton",
            edition = "8th Edition",
            publisher = "Pearson Higher Education",
            year = 2013,
            primaryPillar = CurriculumPillarType.SOIL_SCIENCE,
            coverSubject = "Soil Fertility & Nutrient Chemistry",
            description = "Comprehensive treatise on nitrogen, phosphorus, potassium, secondary, and micronutrient cycles in agricultural soils, fertilizer formulations, and diagnostic soil testing.",
            keyChapters = listOf(
                "Chapter 4: Nitrogen Transformations and Fertilizer Efficiency",
                "Chapter 5: Phosphorus Chemistry and Fixation in Acid/Calcareous Soils",
                "Chapter 6: Potassium Dynamics and Mineral Fixation",
                "Chapter 9: Soil Acidity, Neutralization Kinetics, and Liming",
                "Chapter 11: Soil Testing, Plant Analysis, and Fertilizer Recommendations"
            ),
            syllabusTopicsCovered = listOf("2.e Soil Fertility and Management", "2.f Chemical Properties of Soil"),
            whyItIsAuthoritative = "The definitive advanced soil fertility book used by certified crop advisers (CCA) and university agronomy departments worldwide.",
            scholarSearchQuery = "Soil Fertility and Fertilizers Havlin Tisdale Nelson Beaton"
        ),
        AgriBookReference(
            id = "book_soil_microbiol_paul",
            title = "Soil Microbiology, Ecology and Biochemistry",
            authors = "Eldor A. Paul",
            edition = "4th Edition",
            publisher = "Academic Press / Elsevier",
            year = 2014,
            primaryPillar = CurriculumPillarType.SOIL_SCIENCE,
            coverSubject = "Soil Microbiology & Biogeochemistry",
            description = "Detailed coverage of the rhizosphere microbiome, mycorrhizal fungal networks, bacterial nitrogen fixation biochemistry, and soil carbon humification.",
            keyChapters = listOf(
                "Chapter 6: The Ecology of Soil Microorganisms",
                "Chapter 9: Physiology and Biochemistry of Biological N2 Fixation",
                "Chapter 11: Carbon Turnover and Soil Organic Matter Dynamics (C:N Ratios)",
                "Chapter 13: Mycorrhizal Symbioses and Phosphorus Uptake"
            ),
            syllabusTopicsCovered = listOf("2.c Soil Microbiology"),
            whyItIsAuthoritative = "Authored by leading soil biochemists, providing molecular pathways of microbial transformations in natural and agricultural soils.",
            scholarSearchQuery = "Soil Microbiology Ecology and Biochemistry Eldor Paul"
        ),
        AgriBookReference(
            id = "book_soil_physics_jury",
            title = "Soil Physics",
            authors = "William A. Jury, Robert Horton",
            edition = "6th Edition",
            publisher = "John Wiley & Sons",
            year = 2004,
            primaryPillar = CurriculumPillarType.SOIL_SCIENCE,
            coverSubject = "Soil Physical Properties & Water Dynamics",
            description = "Mathematical and physical foundations of soil water movement, matric potential, hydraulic conductivity, Stokes' law, solute transport, and heat transfer.",
            keyChapters = listOf(
                "Chapter 2: Soil Solid Phase (Texture, Surface Area, Particle Size Analysis)",
                "Chapter 3: Soil Water Energy State (Potential Components)",
                "Chapter 4: Water Flow in Saturated Soil (Darcy's Law)",
                "Chapter 5: Unsaturated Water Flow and Infiltration"
            ),
            syllabusTopicsCovered = listOf("2.d Soil Physics"),
            whyItIsAuthoritative = "Standard graduate-level reference for quantitative water and solute transport modeling in porous agricultural media.",
            scholarSearchQuery = "Soil Physics Jury Horton Wiley"
        ),

        // ANIMAL SCIENCE
        AgriBookReference(
            id = "book_ensminger_animal_science",
            title = "Animal Science and Industry",
            authors = "M. E. Ensminger, Ronald O. Parker",
            edition = "7th Edition",
            publisher = "Prentice Hall / Pearson",
            year = 2006,
            primaryPillar = CurriculumPillarType.ANIMAL_SCIENCE,
            coverSubject = "Comprehensive Livestock Production",
            description = "The foundational livestock textbook covering genetics, anatomy, feeds and feeding, meat processing, dairy science, and specific beef, dairy, swine, sheep, goat, and poultry systems.",
            keyChapters = listOf(
                "Chapter 4: Genetics and Animal Breeding Systems",
                "Chapter 5: Feeds, Digestion, and Ration Formulation",
                "Chapter 7: Animal Health, Diseases, and Biosecurity",
                "Chapter 15: Beef Cattle Management and Nutrition",
                "Chapter 18: Dairy Cattle and Milk Secretion Physiology",
                "Chapter 21: Swine Production and Farrowing Protocols",
                "Chapter 23: Sheep and Goat Husbandry Systems"
            ),
            syllabusTopicsCovered = listOf(
                "3.a Animal Breeding & Genetics",
                "3.c Animal Nutrition",
                "3.d Animal Products Processing",
                "3.e Animal Health and Diseases",
                "3.f Poultry Production",
                "3.g Swine Production",
                "3.h Ruminant Production"
            ),
            whyItIsAuthoritative = "Authored by Marion Eugene Ensminger, the legendary animal scientist whose publications trained generations of livestock producers and veterinarians.",
            scholarSearchQuery = "Animal Science and Industry Ensminger Parker"
        ),
        AgriBookReference(
            id = "book_mcdonald_animal_nutrition",
            title = "Animal Nutrition",
            authors = "Peter McDonald, J. F. D. Greenhalgh, C. A. Morgan, R. Edwards, Liam Sinclair, R. G. Wilkinson",
            edition = "8th Edition",
            publisher = "Prentice Hall / Pearson Education",
            year = 2022,
            primaryPillar = CurriculumPillarType.ANIMAL_SCIENCE,
            coverSubject = "Digestive Physiology & Feeds Formulation",
            description = "The premier textbook on livestock biochemistry, proximate analysis, Van Soest fiber fractions, rumen microbiological fermentation, and metabolizable energy systems.",
            keyChapters = listOf(
                "Chapter 2: Carbohydrates and Rumen VFA Fermentation",
                "Chapter 4: Protein Digestion and Microbial Protein Synthesis",
                "Chapter 8: Evaluation of Foods: Proximate Analysis & Digestibility",
                "Chapter 13: Energy Metabolism: Gross, Digestible, and Metabolizable Energy",
                "Chapter 20: Ration Formulation (Pearson Square and Linear Programming)"
            ),
            syllabusTopicsCovered = listOf("3.c Animal Nutrition"),
            whyItIsAuthoritative = "Regarded across the British Commonwealth and international universities as the gold standard in comparative animal nutritional biochemistry.",
            scholarSearchQuery = "Animal Nutrition McDonald Greenhalgh Morgan Sinclair"
        ),
        AgriBookReference(
            id = "book_hafez_repro_farm_animals",
            title = "Reproduction in Farm Animals",
            authors = "B. Hafez, E. S. E. Hafez",
            edition = "7th Edition",
            publisher = "Lippincott Williams & Wilkins / Wiley-Blackwell",
            year = 2000,
            primaryPillar = CurriculumPillarType.ANIMAL_SCIENCE,
            coverSubject = "Animal Physiology & Reproductive Endocrinology",
            description = "Detailed coverage of reproductive endocrinology, gamete physiology, estrous cycles, artificial insemination, embryo transfer, gestation, and lactation.",
            keyChapters = listOf(
                "Chapter 3: Endocrine Mechanisms in Reproduction (GnRH, LH, FSH)",
                "Chapter 4: The Estrous Cycle and Ovulation in Farm Mammals",
                "Chapter 10: Semen Processing and Artificial Insemination",
                "Chapter 13: Pregnancy, Parturition, and Mammary Gland Function"
            ),
            syllabusTopicsCovered = listOf("3.b Animal Physiology"),
            whyItIsAuthoritative = "The classic text cited worldwide for reproductive parameters, hormonal regulation curves, and artificial breeding protocols in cattle, swine, and sheep.",
            scholarSearchQuery = "Reproduction in Farm Animals Hafez Hafez"
        ),
        AgriBookReference(
            id = "book_lawrie_meat_science",
            title = "Lawrie's Meat Science",
            authors = "R. A. Lawrie, D. A. Ledward",
            edition = "7th Edition",
            publisher = "Woodhead Publishing / CRC Press",
            year = 2006,
            primaryPillar = CurriculumPillarType.ANIMAL_SCIENCE,
            coverSubject = "Animal Products Processing & Meat Biochemistry",
            description = "The definitive scientific work on the conversion of muscle to meat, glycolysis, rigor mortis, water-holding capacity, and postmortem defects (PSE and DFD).",
            keyChapters = listOf(
                "Chapter 4: The Conversion of Muscle to Meat (Rigor Mortis)",
                "Chapter 5: Postmortem Glycolysis and Ultimate pH",
                "Chapter 6: Abnormal Postmortem Glycolysis: PSE and DFD Conditions",
                "Chapter 10: Meat Hygiene, Storage, and Preservation"
            ),
            syllabusTopicsCovered = listOf("3.d Animal Products Processing"),
            whyItIsAuthoritative = "Ralston Lawrie is universally recognized as the father of modern meat science; essential for abattoir managers and food technologists.",
            scholarSearchQuery = "Lawrie Meat Science Ledward Woodhead"
        ),

        // CROP PROTECTION
        AgriBookReference(
            id = "book_agrios_plant_pathology",
            title = "Plant Pathology",
            authors = "George N. Agrios",
            edition = "5th Edition",
            publisher = "Academic Press / Elsevier",
            year = 2005,
            primaryPillar = CurriculumPillarType.CROP_PROTECTION,
            coverSubject = "Comprehensive Plant Pathology",
            description = "The worldwide standard plant pathology textbook, with exhaustive illustrated chapters on phytopathogenic bacteria, fungi, plant-parasitic nematodes, and plant viruses.",
            keyChapters = listOf(
                "Chapter 2: Parasitism and Disease Development (Disease Triangle)",
                "Chapter 4: How Pathogens Attack Plants (Enzymes, Toxins, Growth Regulators)",
                "Chapter 5: How Plants Defend Themselves (Preformed and Induced Resistance, SAR)",
                "Chapter 11: Plant Diseases Caused by Fungi and Oomycetes",
                "Chapter 12: Plant Diseases Caused by Prokaryotes (Bacteria, Mollicutes)",
                "Chapter 14: Plant Diseases Caused by Viruses",
                "Chapter 15: Plant Diseases Caused by Nematodes"
            ),
            syllabusTopicsCovered = listOf(
                "4.a.i Bacteriology",
                "4.a.ii Mycology",
                "4.a.iii Nematology",
                "4.a.iv General Plant Virology",
                "4.d Management of Plant Diseases"
            ),
            whyItIsAuthoritative = "The universal foundational text used in virtually every university plant pathology course across the globe.",
            scholarSearchQuery = "Plant Pathology George N Agrios 5th Edition Elsevier"
        ),
        AgriBookReference(
            id = "book_pedigo_entomology_pest_mgmt",
            title = "Entomology and Pest Management",
            authors = "Larry P. Pedigo, Marlin E. Rice",
            edition = "6th Edition",
            publisher = "Prentice Hall / Waveland Press",
            year = 2014,
            primaryPillar = CurriculumPillarType.CROP_PROTECTION,
            coverSubject = "Agricultural Entomology & IPM",
            description = "The premier textbook linking insect anatomy, orders, and population biology to Economic Thresholds (ET), Economic Injury Levels (EIL), and practical IPM programs.",
            keyChapters = listOf(
                "Chapter 3: Insect Anatomy and Metamorphosis",
                "Chapter 7: Economic Decision Levels for Pest Populations (EIL and ET)",
                "Chapter 8: Insect Scouting, Sampling, and Trapping Systems",
                "Chapter 11: Biological Control and Conservation of Natural Enemies",
                "Chapter 13: Insecticides, Mode of Action Classes (IRAC), and Resistance Management"
            ),
            syllabusTopicsCovered = listOf("4.b Entomology"),
            whyItIsAuthoritative = "Dr. Larry Pedigo was the seminal developer of the quantitative Economic Injury Level (EIL) concept adopted worldwide by crop protection scientists.",
            scholarSearchQuery = "Entomology and Pest Management Pedigo Rice"
        ),
        AgriBookReference(
            id = "book_ross_lembi_applied_weed_sci",
            title = "Applied Weed Science: Including the Ecology and Management of Invasive Plants",
            authors = "Merrill A. Ross, Carole A. Lembi",
            edition = "3rd Edition",
            publisher = "Prentice Hall / Pearson",
            year = 2008,
            primaryPillar = CurriculumPillarType.CROP_PROTECTION,
            coverSubject = "Weed Ecology & Herbicide Chemistry",
            description = "Covers weed seed persistence, Critical Period of Weed Control (CPWC), competition biology, herbicide classification by HRAC mode of action, and sprayer calibration.",
            keyChapters = listOf(
                "Chapter 3: Weed Biology, Seed Banks, and Dormancy",
                "Chapter 5: Weed-Crop Competition and the Critical Weed-Free Period",
                "Chapter 7: Herbicide Classification by Mode of Action (HRAC Groups)",
                "Chapter 12: Herbicide Resistance Mechanisms and Stewardship",
                "Chapter 14: Sprayer Calibration and Liquid Application Mathematics"
            ),
            syllabusTopicsCovered = listOf("4.c Weed Science", "4.e Problems in Insecticide Calculation"),
            whyItIsAuthoritative = "Distinguished for its crystal-clear explanation of herbicide biochemical target sites and mathematical field sprayer calibration formulas.",
            scholarSearchQuery = "Applied Weed Science Ross Lembi Pearson"
        ),
        AgriBookReference(
            id = "book_matthews_pesticide_application",
            title = "Pesticide Application Methods",
            authors = "Graham Matthews, Roy Bateman, Paul Miller",
            edition = "4th Edition",
            publisher = "Wiley-Blackwell",
            year = 2014,
            primaryPillar = CurriculumPillarType.CROP_PROTECTION,
            coverSubject = "Sprayer Calibration & Application Mathematics",
            description = "Comprehensive engineering and agronomic guide to pesticide atomization, hydraulic nozzle physics, walking speed calibrations, drift reduction, and tank dosing calculations.",
            keyChapters = listOf(
                "Chapter 4: Hydraulic Nozzle Droplet Spectra and VMD",
                "Chapter 6: Lever-Operated Knapsack Sprayers and Field Calibration",
                "Chapter 9: Calculations: Active Ingredients, Dosing Rates, and Swath Widths",
                "Chapter 14: Operator Safety and Drift Management"
            ),
            syllabusTopicsCovered = listOf("4.e Problems in Insecticide Calculation"),
            whyItIsAuthoritative = "The World Health Organization (WHO) and FAO standard technical reference for pesticide delivery equipment and application mechanics.",
            scholarSearchQuery = "Pesticide Application Methods Matthews Bateman Miller Wiley"
        ),

        // AGRICULTURAL ECONOMICS & MARKETING
        AgriBookReference(
            id = "book_drummond_agri_economics",
            title = "Agricultural Economics",
            authors = "H. Evan Drummond, John W. Goodwin",
            edition = "3rd Edition",
            publisher = "Prentice Hall / Pearson",
            year = 2010,
            primaryPillar = CurriculumPillarType.AGRI_ECONOMICS,
            coverSubject = "Comprehensive Agricultural Economics",
            description = "Accessible yet rigorous coverage of supply/demand mechanics, elasticities, classical production functions (Stages I, II, III), cost curves, farm business analysis, and agricultural policy.",
            keyChapters = listOf(
                "Chapter 3: The Theory of Consumer Demand and Elasticity",
                "Chapter 4: The Theory of Firm Production (TPP, MPP, APP, Rational Stage II)",
                "Chapter 5: Costs of Production and Profit Maximization (MR = MC)",
                "Chapter 8: Market Structure: Perfect Competition to Monopoly",
                "Chapter 14: Macroeconomics, Fiscal/Monetary Policy, and Agriculture",
                "Chapter 15: Agricultural Marketing Channels and Marketing Margins"
            ),
            syllabusTopicsCovered = listOf(
                "5.a Demand, Supply and Equilibrium",
                "5.b Elasticities of Demand and Supply",
                "5.c Production Analysis",
                "5.d Cost of Production",
                "5.e Price Determination",
                "5.g-k Macroeconomics & Policy",
                "5.l Marketing"
            ),
            whyItIsAuthoritative = "The most widely assigned undergraduate textbook for agricultural business, farm management, and agricultural economics students.",
            scholarSearchQuery = "Agricultural Economics Drummond Goodwin Pearson"
        ),
        AgriBookReference(
            id = "book_kohls_uhl_agri_marketing",
            title = "Marketing of Agricultural Products",
            authors = "Richard L. Kohls, Joseph N. Uhl",
            edition = "9th Edition",
            publisher = "Prentice Hall / Pearson",
            year = 2002,
            primaryPillar = CurriculumPillarType.AGRI_ECONOMICS,
            coverSubject = "Agricultural Marketing & Value Chains",
            description = "The classic text on food marketing systems, marketing margins, price spreads, market structure-conduct-performance (SCP), commodity storage, and forward pricing.",
            keyChapters = listOf(
                "Chapter 1: The Agricultural Marketing System and Marketing Utilities",
                "Chapter 3: Food Marketing Margins and Price Spreads",
                "Chapter 9: Storage and Time Utility: Seasonality in Grain Markets",
                "Chapter 14: Agricultural Cooperatives and Collective Bargaining",
                "Chapter 20: Future Markets and Risk Hedging for Farm Commodities"
            ),
            syllabusTopicsCovered = listOf("5.l Agricultural Marketing"),
            whyItIsAuthoritative = "The foundational market structure text defining marketing utilities (form, place, time, possession) and farmer's share calculations.",
            scholarSearchQuery = "Marketing of Agricultural Products Kohls Uhl Prentice Hall"
        ),

        // AGRICULTURAL EXTENSION & COMMUNICATIONS
        AgriBookReference(
            id = "book_rogers_diffusion_innovations",
            title = "Diffusion of Innovations",
            authors = "Everett M. Rogers",
            edition = "5th Edition",
            publisher = "Free Press / Simon & Schuster",
            year = 2003,
            primaryPillar = CurriculumPillarType.AGRI_EXTENSION,
            coverSubject = "Innovation Adoption & Diffusion Theory",
            description = "The groundbreaking sociological and agricultural treatise establishing the 5 stages of adoption (AIETA), the bell curve of adopter categories, and opinion leadership dynamics.",
            keyChapters = listOf(
                "Chapter 1: Elements of Diffusion (Innovation, Communication, Time, Social System)",
                "Chapter 5: The Innovation-Decision Process (Awareness to Adoption)",
                "Chapter 6: Attributes of Innovations and Their Rate of Adoption",
                "Chapter 7: Adopter Categorization (Innovators to Laggards)",
                "Chapter 8: Opinion Leadership and the Change Agent"
            ),
            syllabusTopicsCovered = listOf(
                "6.f Source, Message, Channel, Receiver and Feedback",
                "6.g The Innovation Adoption Process"
            ),
            whyItIsAuthoritative = "One of the most cited social science books in human history; universally required reading for agricultural extension officers and development planners.",
            scholarSearchQuery = "Diffusion of Innovations Everett Rogers 5th Edition Free Press"
        ),
        AgriBookReference(
            id = "book_van_den_ban_extension",
            title = "Agricultural Extension",
            authors = "A. W. van den Ban, H. S. Hawkins",
            edition = "2nd Edition",
            publisher = "Blackwell Science",
            year = 1996,
            primaryPillar = CurriculumPillarType.AGRI_EXTENSION,
            coverSubject = "Extension Philosophy & Educational Methods",
            description = "Comprehensive guide to adult education principles, individual/group/mass extension methods, farmer field schools, program planning, and evaluation in developing agriculture.",
            keyChapters = listOf(
                "Chapter 2: The Role of Extension in Agricultural Development",
                "Chapter 4: The Communication Process and Extension Methods",
                "Chapter 6: Individual and Group Extension Methods (Method vs. Result Demos)",
                "Chapter 7: Mass Media in Extension and Advisory Campaigns",
                "Chapter 10: Evaluation of Extension Programs"
            ),
            syllabusTopicsCovered = listOf(
                "6.a Elements of Extension",
                "6.d Philosophy of Agricultural Extension",
                "6.e Methods of Agricultural Extension"
            ),
            whyItIsAuthoritative = "Wageningen University standard textbook that codified participatory extension methods across Europe, Africa, and Latin America.",
            scholarSearchQuery = "Agricultural Extension van den Ban Hawkins Blackwell"
        ),
        AgriBookReference(
            id = "book_chambers_pra",
            title = "Whose Reality Counts? Putting the First Last",
            authors = "Robert Chambers",
            edition = "1st Edition",
            publisher = "Intermediate Technology Publications / Practical Action",
            year = 1997,
            primaryPillar = CurriculumPillarType.AGRI_EXTENSION,
            coverSubject = "Participatory Rural Appraisal (PRA)",
            description = "The foundational philosophy and methodological toolkit for Participatory Rural Appraisal (PRA), transect walks, seasonal calendars, and indigenous technical knowledge.",
            keyChapters = listOf(
                "Chapter 4: Handing Over the Stick: The Evolution of PRA",
                "Chapter 6: Methods of Participatory Mapping and Seasonal Calendars",
                "Chapter 8: Professional Behavior and Reversals in Extension Advisory"
            ),
            syllabusTopicsCovered = listOf("6.b Development Communication", "6.d Philosophy of Extension"),
            whyItIsAuthoritative = "The seminal text that revolutionized international development and shifted agricultural extension from top-down lecturing to bottom-up participatory empowerment.",
            scholarSearchQuery = "Whose Reality Counts Putting First Last Robert Chambers"
        )
    )
}
