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
            scholarSearchQuery = "Principles of Field Crop Production Martin Leonard Stamp",
            isbn = "978-0130259677",
            keyTakeaways = listOf(
                "Optimum plant population balances Leaf Area Index (LAI 3–5) against intra-row lodging and moisture competition.",
                "Rhizobium-legume symbiosis fixes 40–250 kg N/ha/year when soil pH > 5.8 and molybdenum/phosphorus are sufficient.",
                "Harvest Index (HI) measures partitioning efficiency: Economic Yield / Total Biological Biomass."
            ),
            keyFormulasOrPrinciples = listOf(
                "Seeding Rate (kg/ha) = (Target Plants/m² × TGW g) / (Germination% × Purity% × Field Survival%)",
                "Harvest Index (HI) = Grain Dry Weight / Total Above-Ground Dry Matter"
            ),
            examAndFieldFocus = "Stand count math, Pure Live Seed (PLS), GDD (Growing Degree Days), and cereal-legume rotation design.",
            googleSearchQuery = "Principles of Field Crop Production Martin Leonard Stamp agronomy notes"
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
            scholarSearchQuery = "Plant Physiology and Development Taiz Zeiger",
            isbn = "978-1605352558",
            keyTakeaways = listOf(
                "C4 crops (maize, sorghum, sugarcane) use PEP carboxylase in mesophyll cells to concentrate CO2 around RuBisCO, eliminating photorespiration in hot climates.",
                "ABA-to-GA hormone ratio governs seed dormancy vs. germination mobilization of endosperm starch.",
                "Water flows strictly down water potential gradients: Ψw = Ψs + Ψp + Ψg + Ψm."
            ),
            keyFormulasOrPrinciples = listOf(
                "Water Potential: Ψw = Ψs (Solute) + Ψp (Pressure) + Ψg (Gravity)",
                "Photosynthetic WUE = CO2 Assimilated (μmol) / H2O Transpired (mmol)"
            ),
            examAndFieldFocus = "C3 vs C4 vs CAM anatomy, Kranz anatomy, phytohormone antagonisms (Auxin/Cytokinin/ABA/GA/Ethylene), and drought osmotic adjustment.",
            googleSearchQuery = "Plant Physiology and Development Taiz Zeiger C3 C4 photosynthesis water potential"
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
            scholarSearchQuery = "Hartmann Kester Plant Propagation Principles Practices",
            isbn = "978-0134480893",
            keyTakeaways = listOf(
                "Successful graft unions require intact vascular cambium alignment, callus bridge proliferation, and taxonomic compatibility.",
                "High Auxin-to-Cytokinin ratio in Murashige & Skoog (MS) medium promotes adventitious rooting; high Cytokinin promotes shoot proliferation.",
                "Orthodox seeds survive desiccation to 5–8% moisture at sub-zero temperatures, whereas recalcitrant seeds (mango, avocado, cocoa) die if dried."
            ),
            keyFormulasOrPrinciples = listOf(
                "Murashige & Skoog (MS): Auxin > Cytokinin -> Root Induction; Cytokinin > Auxin -> Shoot Multiplication",
                "Graft Polarity Law: Proximal-to-Distal vascular orientation must always be preserved"
            ),
            examAndFieldFocus = "Tissue culture organogenesis, T-budding/cleft grafting, rooting hormones (IBA/NAA), and dormancy scarification/stratification.",
            googleSearchQuery = "Hartmann Kester Plant Propagation grafting budding tissue culture MS medium"
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
            scholarSearchQuery = "Vegetable Production and Practices Gregory Welbaum CABI",
            isbn = "978-1780645346",
            keyTakeaways = listOf(
                "Blossom End Rot (BER) in tomato/pepper is caused by localized Ca2+ transport failure driven by erratic moisture and high ammonium/salinity, not merely low soil calcium.",
                "Protected greenhouse vents require 40-50 mesh insect exclusion screens to block whiteflies (Bemisia tabaci) and thrips without overheating crops.",
                "Mulching reduces soil evaporation by 25–45% and prevents soil-borne splashing onto lower Solanaceae foliage."
            ),
            keyFormulasOrPrinciples = listOf(
                "Target Fertigation EC: Tomato vegetative 1.8–2.2 mS/cm; fruiting 2.4–3.2 mS/cm",
                "Crop Rotation Rule: Minimum 3-year gap between Solanaceae (Tomato/Potato/Pepper) to suppress Ralstonia wilt"
            ),
            examAndFieldFocus = "Vegetable nursery hardening, physiological disorders (BER, bolting, concentric cracking), and protected plasticulture IPM.",
            googleSearchQuery = "Vegetable Production and Practices Welbaum CABI olericulture blossom end rot"
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
            scholarSearchQuery = "Breeding Field Crops Sleper Poehlman",
            isbn = "978-0813824284",
            keyTakeaways = listOf(
                "Narrow-sense heritability (h²) determines expected genetic gain under selection because only additive variance (VA) responds predictably.",
                "Cytoplasmic Male Sterility (CMS A-line × Maintainer B-line × Restorer R-line) enables commercial F1 hybrid seed without manual detasseling.",
                "Backcross breeding transfers a single major resistance gene (e.g., rust or MLND tolerance) into an elite adapted recurrent parent."
            ),
            keyFormulasOrPrinciples = listOf(
                "Genetic Gain: R = h² × S (Broad-sense H² = VG/VP; Narrow-sense h² = VA/VP)",
                "Mid-Parent Heterosis (%) = [(F1 - MP) / MP] × 100"
            ),
            examAndFieldFocus = "Self- vs cross-pollinated breeding schemes (Pedigree, Bulk, SSD, Recurrent Selection), Hardy-Weinberg equilibrium, and QTL/MAS.",
            googleSearchQuery = "Breeding Field Crops Sleper Poehlman heritability CMS hybrid breeding"
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
            scholarSearchQuery = "Principles Seed Science Technology Copeland McDonald",
            isbn = "978-0792373223",
            keyTakeaways = listOf(
                "Harrington's Rule of Thumb: Seed longevity doubles for every 1% drop in seed moisture (between 5-14%) and every 5°C drop in storage temperature.",
                "Tetrazolium Chloride (TZ) rapid viability test stains living respiring embryonic tissues bright red (formazan) within 2–24 hours.",
                "Seed certification classes follow strict isolation distances: Breeder -> Foundation/Basic -> Registered -> Certified Seed."
            ),
            keyFormulasOrPrinciples = listOf(
                "Pure Live Seed (PLS %) = (% Purity × % Germination) / 100",
                "Harrington Rule: Seed Moisture (%) + Storage Temp (°F) < 100"
            ),
            examAndFieldFocus = "ISTA germination/purity/vigor testing, tetrazolium staining, isolation distances, and orthodox seed storage curve.",
            googleSearchQuery = "Principles of Seed Science and Technology Copeland McDonald ISTA purity vigor"
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
            scholarSearchQuery = "Postharvest Introduction Physiology Handling Fruit Vegetables Wills Golding",
            isbn = "978-1786391483",
            keyTakeaways = listOf(
                "Climacteric fruits (tomato, avocado, mango, banana) display a sharp respiration and autocatalytic ethylene burst at ripening; non-climacteric fruits (citrus, strawberry, grape) do not.",
                "Every 10°C temperature rise roughly doubles/triples respiration rate (Q10 = 2–3), shortening shelf life proportionately.",
                "Chilling injury occurs in tropical crops (mango, banana, French bean) below 10–13°C, causing surface pitting and vascular browning."
            ),
            keyFormulasOrPrinciples = listOf(
                "Q10 Temperature Coefficient = Rate at (T + 10°C) / Rate at T",
                "Ideal Controlled Atmosphere (CA): Reduced O2 (2-5%) + Elevated CO2 (3-10%) + RH 90-95%"
            ),
            examAndFieldFocus = "Climacteric vs non-climacteric ethylene management (1-MCP), hydrocooling/forced-air precooling, and Botrytis cold-chain hygiene.",
            googleSearchQuery = "Postharvest Physiology Handling Fruit Vegetables Wills Golding climacteric ethylene"
        ),
        AgriBookReference(
            id = "book_altieri_agroecology",
            title = "Agroecology: The Science of Sustainable Agriculture",
            authors = "Miguel A. Altieri",
            edition = "2nd Edition",
            publisher = "CRC Press / Westview Press",
            year = 2018,
            primaryPillar = CurriculumPillarType.CROP_SCIENCE,
            coverSubject = "Agroecology & Sustainable Farming Systems",
            description = "Foundational treatise on ecological design of diversified agroecosystems, polycultures, push-pull habitat manipulation, soil food webs, and biological pest suppression.",
            keyChapters = listOf(
                "Chapter 3: The Agroecosystem: Determinants, Resources, and Processes",
                "Chapter 7: Polyculture Cropping Systems & Land Equivalent Ratio (LER)",
                "Chapter 10: Agroforestry and Alley Cropping Designs",
                "Chapter 12: Ecological Pest Management via Habitat Diversification"
            ),
            syllabusTopicsCovered = listOf("1.d Farming Systems", "1.f Crop Ecology", "Sustainable Agriculture"),
            whyItIsAuthoritative = "Global academic anchor for agroecological intensification, push-pull systems (maize-Desmodium-Napier), and climate-resilient smallholder farming.",
            scholarSearchQuery = "Agroecology The Science of Sustainable Agriculture Miguel Altieri",
            isbn = "978-0813317182",
            keyTakeaways = listOf(
                "Intercropping achieves Land Equivalent Ratio (LER) > 1.0 by complementing canopy light capture, rooting depth, and nutrient niches.",
                "Botanical diversity increases natural enemy residence time (parasitoid wasps, hoverflies, predatory beetles) and disrupts pest host-finding.",
                "Push-Pull technology suppresses both Stemborer/FAW and parasitic Striga hermonthica using Desmodium intercrops and Napier/Brachiaria borders."
            ),
            keyFormulasOrPrinciples = listOf(
                "Land Equivalent Ratio (LER) = (Yield_Intercrop_A / Yield_Sole_A) + (Yield_Intercrop_B / Yield_Sole_B)",
                "Relative Crowding Coefficient & Aggressivity Indices evaluate intercrop competition"
            ),
            examAndFieldFocus = "Calculating Land Equivalent Ratio (LER), biological nitrogen transfer in intercrops, and habitat manipulation IPM.",
            googleSearchQuery = "Agroecology Miguel Altieri Land Equivalent Ratio polyculture sustainable agriculture"
        ),
        AgriBookReference(
            id = "book_slater_plant_biotech",
            title = "Plant Biotechnology: The Genetic Manipulation of Plants",
            authors = "Adrian Slater, Nigel W. Scott, Mark R. Fowler",
            edition = "2nd Edition",
            publisher = "Oxford University Press",
            year = 2008,
            primaryPillar = CurriculumPillarType.CROP_SCIENCE,
            coverSubject = "Agricultural Biotechnology & Molecular Crop Improvement",
            description = "Authoritative textbook covering plant tissue culture, Agrobacterium tumefaciens Ti-plasmid transformation, biolistics, RNAi/CRISPR traits, Bt insect resistance, and biosafety.",
            keyChapters = listOf(
                "Chapter 2: Plant Genomes and Gene Expression",
                "Chapter 4: Agrobacterium-Mediated Transformation (T-DNA Biology)",
                "Chapter 7: Herbicide Tolerance & Bt Cry Toxin Engineering",
                "Chapter 10: Abiotic Stress & Nutritional Biofortification (Golden Rice)"
            ),
            syllabusTopicsCovered = listOf("1.e Agricultural Biotechnology", "Modern Agricultural Technologies"),
            whyItIsAuthoritative = "Standard undergraduate/MSc textbook explaining molecular crop transformation vectors, marker-free transgenic events, and biosafety regulation.",
            scholarSearchQuery = "Plant Biotechnology Genetic Manipulation of Plants Slater Scott Fowler",
            isbn = "978-0199282616",
            keyTakeaways = listOf(
                "Virulence (vir) genes on the Ti-plasmid respond to acetosyringone from wounded dicot tissue to excise and transfer T-DNA flanked by 25bp border repeats.",
                "Bacillus thuringiensis (Bt) Cry proteins require alkaline insect midgut pH and specific brush-border receptors to form lethal pores.",
                "High-dose + non-Bt refuge strategy delays resistance evolution in target Lepidopteran pests."
            ),
            keyFormulasOrPrinciples = listOf(
                "Vector Pipeline: Explant -> Agrobacterium Co-cultivation -> Antibiotic Selection -> Shoot/Root Regeneration",
                "Refuge Rule: Maintain 5–20% non-Bt structured refuge to preserve susceptible pest alleles"
            ),
            examAndFieldFocus = "Ti-plasmid structure, somaclonal variation, ELISA/PCR GMO detection, and resistance stewardship.",
            googleSearchQuery = "Plant Biotechnology Slater Scott Fowler Agrobacterium Ti plasmid Bt crops"
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
            scholarSearchQuery = "The Nature and Properties of Soils Weil Brady 15th Edition",
            isbn = "978-0133254488",
            keyTakeaways = listOf(
                "Five soil forming factors (CLORPT): Climate, Organisms, Relief/Topography, Parent Material, and Time.",
                "Exchangeable Al3+ dominates acidity below pH 5.2, stunting root tips and fixing soluble phosphorus into insoluble Al-phosphates.",
                "2:1 expanding smectite clays (Vertisols) possess CEC ~80–150 cmolc/kg, whereas 1:1 kaolinite/iron oxides (Nitisols/Oxisols) have CEC ~3–15 cmolc/kg."
            ),
            keyFormulasOrPrinciples = listOf(
                "Total Porosity (%) = [1 - (Bulk Density / Particle Density 2.65)] × 100",
                "Base Saturation (%) = [(Ca + Mg + K + Na) / CEC] × 100"
            ),
            examAndFieldFocus = "Master horizons (O-A-E-B-C-R), 12 Soil Orders, CEC/Base Saturation math, bulk density compaction, and Available Water Capacity (FC - PWP).",
            googleSearchQuery = "Nature and Properties of Soils Weil Brady CEC bulk density soil taxonomy"
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
            scholarSearchQuery = "Soil Fertility and Fertilizers Havlin Tisdale Nelson Beaton",
            isbn = "978-0135033739",
            keyTakeaways = listOf(
                "Liebig's Law of the Minimum: Crop yield is constrained by the scarcest essential nutrient relative to crop demand.",
                "Phosphorus is maximally available at pH 6.0–7.0; below pH 5.5 Fe/Al fix P, while above pH 7.5 Ca precipitates P.",
                "4R Nutrient Stewardship: Right Source, Right Rate, Right Time, Right Place."
            ),
            keyFormulasOrPrinciples = listOf(
                "Fertilizer Product Rate (kg/ha) = (Recommended Nutrient kg/ha × 100) / % Nutrient Grade",
                "Lime Requirement scales with Buffer pH, Exchangeable Al3+, and Calcium Carbonate Equivalent (CCE%)"
            ),
            examAndFieldFocus = "NPK oxide conversions (P = P2O5 × 0.436; K = K2O × 0.83), nitrification/denitrification losses, and CCE liming rates.",
            googleSearchQuery = "Soil Fertility and Fertilizers Havlin Tisdale nitrogen phosphorus liming calculation"
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
            scholarSearchQuery = "Soil Microbiology Ecology and Biochemistry Eldor Paul",
            isbn = "978-0124159556",
            keyTakeaways = listOf(
                "Incorporating residues with C:N > 30:1 (maize stover ~60:1) triggers microbial N immobilization; C:N < 20:1 (legumes ~12:1) drives net mineralization.",
                "Arbuscular Mycorrhizal Fungi (AMF, Glomeromycota) extend root P and Zn depletion zones up to 10x.",
                "Nitrogenase enzyme requires Mo-Fe cofactor, 16 ATP per N2 reduced, and anaerobic leghemoglobin protection."
            ),
            keyFormulasOrPrinciples = listOf(
                "C:N Threshold ~24:1 (1 unit microbial biomass N immobilized per ~8–10 units C assimilated)",
                "Biological N2 Fixation: N2 + 8H+ + 8e- + 16ATP -> 2NH3 + H2 + 16ADP + 16Pi"
            ),
            examAndFieldFocus = "Immobilization vs mineralization C:N balance, Nitrosomonas/Nitrobacter chemoautotrophy, and mycorrhizal P nutrition.",
            googleSearchQuery = "Soil Microbiology Ecology and Biochemistry Eldor Paul C:N ratio nitrogen fixation"
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
            scholarSearchQuery = "Soil Physics Jury Horton Wiley",
            isbn = "978-0471059653",
            keyTakeaways = listOf(
                "Field Capacity (-33 kPa) minus Permanent Wilting Point (-1500 kPa) equals Plant Available Water Holding Capacity (AWC).",
                "Hydrometer/pipette particle-size analysis applies Stokes' Law, where settling velocity scales with particle radius squared (r²).",
                "Sandy soils conduct rapidly when saturated (high Ks) but drain quickly; clays hold water tightly at low matric potentials."
            ),
            keyFormulasOrPrinciples = listOf(
                "Darcy's Law: q = -K × (ΔH / L)",
                "Available Water Capacity (mm) = [(θ_FC - θ_PWP) / 100] × Bulk Density/ρw × Root Depth (mm)"
            ),
            examAndFieldFocus = "Darcy hydraulic flux, gravimetric vs volumetric soil moisture, texture triangle calculations, and infiltration kinetics.",
            googleSearchQuery = "Soil Physics Jury Horton Darcy Law water retention field capacity"
        ),
        AgriBookReference(
            id = "book_fao56_irrigation",
            title = "Crop Evapotranspiration: Guidelines for Computing Crop Water Requirements (FAO Paper 56)",
            authors = "Richard G. Allen, Luis S. Pereira, Dirk Raes, Martin Smith",
            edition = "FAO Irrigation & Drainage Paper 56",
            publisher = "Food and Agriculture Organization (FAO)",
            year = 1998,
            primaryPillar = CurriculumPillarType.SOIL_SCIENCE,
            coverSubject = "Irrigation & Water Management",
            description = "The global engineering benchmark for calculating reference evapotranspiration (ETo) via FAO Penman-Monteith, stage crop coefficients (Kc), and irrigation scheduling.",
            keyChapters = listOf(
                "Chapter 2: FAO Penman-Monteith Equation for Reference ETo",
                "Chapter 6: Single Crop Coefficient Approach (ETc = Kc × ETo)",
                "Chapter 8: ETc Under Soil Water Stress (KS Factor & MAD)",
                "Chapter 9: Net Irrigation Requirement and Salinity Leaching Fraction"
            ),
            syllabusTopicsCovered = listOf("2.d Soil Physics", "Irrigation & Water Management", "Climate-Smart Agriculture"),
            whyItIsAuthoritative = "Open-access international standard published by the FAO and embedded into nearly every modern agronomic irrigation software worldwide.",
            scholarSearchQuery = "FAO Irrigation and Drainage Paper 56 Allen Pereira Crop Evapotranspiration",
            isbn = "978-9251042199",
            keyTakeaways = listOf(
                "Crop water demand rises from initial establishment (Kc_ini ~0.3–0.5) to full canopy mid-season (Kc_mid ~1.05–1.20) before declining at senescence.",
                "Management Allowed Depletion (MAD) is typically 50% of Total Available Water (TAW) for maize/beans and 35–40% for shallow-rooted vegetables.",
                "Drip irrigation delivers 85–95% application efficiency compared to 50–65% for unlined furrow systems."
            ),
            keyFormulasOrPrinciples = listOf(
                "Crop Evapotranspiration: ETc = Kc × ETo",
                "Gross Irrigation Depth = (Net Deficit / Application Efficiency) / (1 - Leaching Fraction)"
            ),
            examAndFieldFocus = "Penman-Monteith inputs (radiation, wind, vapor pressure deficit), Kc growth-stage curves, and drip scheduling intervals.",
            googleSearchQuery = "FAO 56 crop evapotranspiration Penman Monteith Kc irrigation scheduling"
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
            scholarSearchQuery = "Animal Science and Industry Ensminger Parker",
            isbn = "978-0130462565",
            keyTakeaways = listOf(
                "Crossbreeding exploits maternal heterosis for fertility/survival and individual heterosis for growth efficiency.",
                "Zero-grazing dairy housing requires clean dry bedding, 60–90 L clean drinking water/day for lactating cows, and mastitis teat dipping.",
                "Crop-livestock integration recycles maize stover/Brachiaria forage into milk/manure nutrient loops."
            ),
            keyFormulasOrPrinciples = listOf(
                "Feed Conversion Ratio (FCR) = Total Feed Consumed (kg) / Live Weight Gain (kg)",
                "Gestation Benchmarks: Cow ~283d | Sow ~114d | Ewe/Doe ~150d"
            ),
            examAndFieldFocus = "Gestation tables, FCR efficiency across poultry/swine/cattle, dairy housing hygiene, and crossbreeding systems.",
            googleSearchQuery = "Animal Science and Industry Ensminger livestock management ration breeding"
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
            scholarSearchQuery = "Animal Nutrition McDonald Greenhalgh Morgan Sinclair",
            isbn = "978-1292251660",
            keyTakeaways = listOf(
                "Rumen microbes ferment cellulose/starch into Acetate (milk fat), Propionate (gluconeogenesis/lactose), and Butyrate.",
                "Kjeldahl Crude Protein (CP) estimates nitrogen content multiplied by 6.25 (since average feed protein contains ~16% N).",
                "Pearson Square balances two feed ingredients precisely to a target Crude Protein or energy concentration."
            ),
            keyFormulasOrPrinciples = listOf(
                "Crude Protein (%) = Nitrogen (%) × 6.25",
                "NFE (%) = 100 - (Moisture% + CP% + EE% + CF% + Ash%)"
            ),
            examAndFieldFocus = "Proximate & Van Soest fiber (NDF/ADF), Pearson Square ration math, ME partition, and rumen acidosis prevention.",
            googleSearchQuery = "Animal Nutrition McDonald proximate analysis Pearson Square rumen VFA"
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
            scholarSearchQuery = "Reproduction in Farm Animals Hafez Hafez",
            isbn = "978-0683305777",
            keyTakeaways = listOf(
                "Cows ovulate 10–14 hours AFTER the end of standing estrus, making the AM/PM AI rule optimal for conception.",
                "PGF2α lyses the Corpus Luteum to synchronize estrus only during diestrus (days 5–17 of the bovine cycle).",
                "Prolactin initiates lactogenesis after progesterone declines at parturition; oxytocin triggers milk let-down."
            ),
            keyFormulasOrPrinciples = listOf(
                "Bovine Estrous Cycle: 21 days (Standing Heat ~18h; Ovulation ~28h post-onset)",
                "AM/PM AI Rule: Observed in heat AM -> Inseminate PM; Observed PM -> Inseminate next AM"
            ),
            examAndFieldFocus = "Hypothalamic-pituitary-gonadal axis, estrus synchronization (PGF2α/CIDR), and AI semen handling.",
            googleSearchQuery = "Reproduction in Farm Animals Hafez estrous cycle artificial insemination endocrinology"
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
            scholarSearchQuery = "Lawrie Meat Science Ledward Woodhead",
            isbn = "978-1845691592",
            keyTakeaways = listOf(
                "Postmortem anaerobic glycolysis converts muscle glycogen into lactic acid, lowering normal ultimate pH from 7.2 to 5.4–5.6.",
                "PSE (Pale Soft Exudative) results from rapid pH drop while carcass temperature is still >35°C; DFD (Dark Firm Dry) stems from pre-slaughter glycogen exhaustion.",
                "Cold shortening occurs when beef/lamb carcasses are chilled below 10°C before rigor onset."
            ),
            keyFormulasOrPrinciples = listOf(
                "Normal Ultimate Meat pH: 5.4–5.6 | DFD Dark Cutter pH: > 6.2",
                "ATP Depletion Triggers Actomyosin Cross-Bridging (Onset of Rigor Mortis)"
            ),
            examAndFieldFocus = "PSE vs DFD meat defects, carcass chilling curves, HACCP hygiene, and curing nitrite function.",
            googleSearchQuery = "Lawrie Meat Science rigor mortis ultimate pH PSE DFD"
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
            scholarSearchQuery = "Plant Pathology George N Agrios 5th Edition Elsevier",
            isbn = "978-0120445653",
            keyTakeaways = listOf(
                "Disease Triangle: Susceptible Host + Virulent Pathogen + Favorable Environment over Time.",
                "Oomycetes (Phytophthora infestans, Peronospora, Pythium) have cellulose cell walls and diploid coenocytic hyphae—making true-fungus sterol inhibitors ineffective without oomycete-targeted actives (metalaxyl, mandipropamid).",
                "Koch's Postulates verify causal pathogenicity of culturable bacteria and fungi."
            ),
            keyFormulasOrPrinciples = listOf(
                "True Fungi: Chitin Cell Walls vs Oomycetes: Cellulose/Beta-Glucan Cell Walls",
                "Disease Severity (%) = [Sum(Disease Rating × Frequency) / (Total Units × Max Scale)] × 100"
            ),
            examAndFieldFocus = "Oomycetes vs Ascomycetes vs Basidiomycetes, Koch's postulates, bacterial ooze test, nematode stylets, and viral vectors.",
            googleSearchQuery = "Plant Pathology George Agrios 5th edition disease triangle oomycetes bacteriology"
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
            scholarSearchQuery = "Entomology and Pest Management Pedigo Rice",
            isbn = "978-1478622857",
            keyTakeaways = listOf(
                "Economic Threshold (ET) is set BELOW Economic Injury Level (EIL) so control actions halt pest growth before economic loss occurs.",
                "Holometabolous pests (Lepidoptera, Coleoptera, Diptera) undergo Egg -> Larva -> Pupa -> Adult; Hemimetabolous (Aphids, Thrips, Whiteflies) undergo Egg -> Nymph -> Adult.",
                "Rotating IRAC Mode-of-Action groups across consecutive pest generations prevents target-site and metabolic insecticide resistance."
            ),
            keyFormulasOrPrinciples = listOf(
                "Economic Injury Level: EIL = C / (V × I × D × K) [Cost, Market Value, Injury/pest, Damage/injury, Proportion Controlled]",
                "IPM Pyramid: Cultural/Preventive -> Mechanical -> Biological -> Targeted IRAC Rotation Chemical"
            ),
            examAndFieldFocus = "Calculating EIL and ET, insect mouthparts (chewing vs piercing-sucking vs rasping), and IRAC resistance rotation.",
            googleSearchQuery = "Entomology and Pest Management Pedigo Rice Economic Injury Level IPM"
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
            scholarSearchQuery = "Applied Weed Science Ross Lembi Pearson",
            isbn = "978-0135028148",
            keyTakeaways = listOf(
                "Controlling weeds during the Critical Period of Weed Control (typically first 3–6 weeks after crop emergence in maize/beans) protects >95% of yield potential.",
                "Broadleaves (Dicots), Grasses (Monocots, hollow/round stems), and Sedges (Triangular solid stems) require distinct herbicide selectivities.",
                "Perennial sedges (Cyperus rotundus) regenerate from tuber chains and resist contact burn-down herbicides unless systemic translocation reaches basal bulbs."
            ),
            keyFormulasOrPrinciples = listOf(
                "Spray Volume (L/ha) = (600 × Nozzle Output L/min) / (Nozzle Spacing m × Speed km/h)",
                "HRAC Key Groups: Group 9 (EPSPS/Glyphosate), Group 2 (ALS), Group 4 (Synthetic Auxins 2,4-D), Group 15 (VLCFA)"
            ),
            examAndFieldFocus = "Weed morphology (grass vs sedge vs broadleaf), CPWC timing, pre- vs post-emergence selectivity, and HRAC mode of action.",
            googleSearchQuery = "Applied Weed Science Ross Lembi critical period weed control HRAC herbicide"
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
            syllabusTopicsCovered = listOf("4.e Problems in Insecticide Calculation", "Modern Agricultural Technologies"),
            whyItIsAuthoritative = "The World Health Organization (WHO) and FAO standard technical reference for pesticide delivery equipment and application mechanics.",
            scholarSearchQuery = "Pesticide Application Methods Matthews Bateman Miller Wiley",
            isbn = "978-1118351307",
            keyTakeaways = listOf(
                "Hollow-cone nozzles produce fine droplets ideal for high-coverage foliar fungicide/insecticide penetration; flat-fan/floodjet nozzles suit soil/herbicide drift reduction.",
                "Knapsack calibration must measure actual output (L/min) over a measured 25m or 100m walk before mixing chemicals.",
                "Strictly observe Pre-Harvest Interval (PHI) and Restricted Entry Interval (REI) on all PCPB product labels."
            ),
            keyFormulasOrPrinciples = listOf(
                "Product Dose (g or mL) = [(Recommended a.i. Rate/ha × Area ha) / Formulation % a.i.] × 100",
                "Knapsack Application Rate (L/ha) = (Volume Sprayed in Test Plot mL × 10) / Test Area m²"
            ),
            examAndFieldFocus = "Worked knapsack tank-mix math, active ingredient dilution, nozzle selection, and PPE/drift stewardship.",
            googleSearchQuery = "Pesticide Application Methods Graham Matthews knapsack calibration calculation"
        ),
        AgriBookReference(
            id = "book_flint_ipm_manual",
            title = "IPM in Practice: Principles and Methods of Integrated Pest Management",
            authors = "Mary Louise Flint",
            edition = "2nd Edition",
            publisher = "University of California ANR",
            year = 2012,
            primaryPillar = CurriculumPillarType.CROP_PROTECTION,
            coverSubject = "Applied IPM & Biocontrol Scouting",
            description = "Practical field manual detailing pest identification keys, degree-day phenology models, pheromone trap grids, biocontrol releases, and IPM decision charts.",
            keyChapters = listOf(
                "Chapter 2: Ecosystem & Population Principles in IPM",
                "Chapter 4: Accurate Pest vs. Abiotic/Pathogen Diagnosis",
                "Chapter 5: Monitoring & Action Threshold Decision Tools",
                "Chapter 7: Biological, Cultural, Physical, and Biorational Tactics"
            ),
            syllabusTopicsCovered = listOf("4.b Entomology", "4.d Management of Plant Diseases", "Sustainable Agriculture"),
            whyItIsAuthoritative = "UC Statewide IPM Program reference widely adapted across East African export horticulture and CABI Plantwise diagnostic clinics.",
            scholarSearchQuery = "IPM in Practice Principles Methods Mary Louise Flint UC ANR",
            isbn = "978-1601077868",
            keyTakeaways = listOf(
                "Always separate biotic pest/pathogen damage (spreading gradients, uneven spatial patterns, signs) from uniform abiotic stress (frost, spray drift, salt).",
                "Preserve generalist predators (ladybird beetles, lacewings, Orius bugs) by opting for selective biopesticides before broad-spectrum pyrethroids.",
                "Track scouting scores per 20–50 random plants weekly along a 'W' field transect."
            ),
            keyFormulasOrPrinciples = listOf(
                "Incidence (%) = (Infested Plants / Total Scouted Plants) × 100",
                "Degree-Days (DD) = [(Tmax + Tmin) / 2] - Developmental Base Temp (Tbase)"
            ),
            examAndFieldFocus = "Differentiating chewing/sucking pest injury vs pathogen symptoms, W-transect scouting, and degree-day forecasting.",
            googleSearchQuery = "IPM in Practice Mary Louise Flint scouting thresholds biocontrol"
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
            scholarSearchQuery = "Agricultural Economics Drummond Goodwin Pearson",
            isbn = "978-0136071921",
            keyTakeaways = listOf(
                "Rational economic production occurs exclusively in Stage II (where APP >= MPP >= 0 and Output Elasticity Ep is between 0 and 1).",
                "Profit is maximized where Value of Marginal Product equals Marginal Input Cost (VMP = MIC), or MR = MC.",
                "Because staple food demand is price-inelastic (|Ed| < 1), bumper harvests can paradoxically lower total farm revenue."
            ),
            keyFormulasOrPrinciples = listOf(
                "Profit Maximization: MR = MC and VMPx = Px (where VMP = MPP × Py)",
                "Price Elasticity: Ed = (% Δ Quantity Demanded) / (% Δ Price)"
            ),
            examAndFieldFocus = "Classical production stages (I, II, III), AVC/ATC/MC shutdown & break-even points, elasticity math, and gross margin budgets.",
            googleSearchQuery = "Agricultural Economics Drummond Goodwin production stages MR MC elasticity"
        ),
        AgriBookReference(
            id = "book_kay_farm_management",
            title = "Farm Management",
            authors = "Ronald D. Kay, William M. Edwards, Patricia A. Duffy",
            edition = "9th Edition",
            publisher = "McGraw-Hill Education",
            year = 2020,
            primaryPillar = CurriculumPillarType.AGRI_ECONOMICS,
            coverSubject = "Farm Management, Enterprise Budgets & Financial Ratios",
            description = "Comprehensive farm business handbook on whole-farm planning, enterprise/partial budgeting, depreciation, balance sheets, cash-flow risk management, and investment appraisal.",
            keyChapters = listOf(
                "Chapter 4: Depreciation, Balance Sheets, and Net Farm Income",
                "Chapter 10: Enterprise and Partial Budgeting for Farm Decisions",
                "Chapter 12: Whole-Farm Planning & Resource Allocation",
                "Chapter 17: Capital Budgeting (NPV, IRR, Payback Period)"
            ),
            syllabusTopicsCovered = listOf("5.d Cost of Production", "Farm Management", "Agricultural Economics"),
            whyItIsAuthoritative = "Standard global textbook for undergraduate Farm Management courses and agricultural credit analysts evaluating smallholder/commercial solvency.",
            scholarSearchQuery = "Farm Management Ronald Kay William Edwards Patricia Duffy",
            isbn = "978-1260002195",
            keyTakeaways = listOf(
                "Partial Budgeting evaluates marginal changes (Added Revenue + Reduced Costs vs. Added Costs + Reduced Revenue) without rewriting the whole farm plan.",
                "Gross Margin = Gross Enterprise Output minus Total Variable Costs (seeds, fertilizer, spray, casual labor).",
                "Current Ratio (Current Assets / Current Liabilities > 1.5) measures short-term farm liquidity."
            ),
            keyFormulasOrPrinciples = listOf(
                "Net Present Value: NPV = Σ [CFt / (1 + r)^t] - Initial Cost",
                "Straight-Line Depreciation = (Cost - Salvage Value) / Useful Life (Years)"
            ),
            examAndFieldFocus = "Preparing maize/tomato Gross Margin budgets, Partial Budgets, Cash Flow statements, and NPV/BCR investment ratios.",
            googleSearchQuery = "Farm Management Kay Edwards partial budget enterprise budget financial ratios"
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
            scholarSearchQuery = "Marketing of Agricultural Products Kohls Uhl Prentice Hall",
            isbn = "978-0130105844",
            keyTakeaways = listOf(
                "Marketing adds four core utilities: Form (milling/grading), Place (transport), Time (hermetic storage), and Possession (ownership transfer).",
                "Perishability, bulky weight, and seasonal gluts widen the Farm-to-Retail Price Spread unless cooperatives aggregate and grade produce.",
                "Warehouse Receipt Systems stabilize smallholder farm-gate prices across postharvest gluts."
            ),
            keyFormulasOrPrinciples = listOf(
                "Farmer's Share (%) = (Farm-Gate Price / Final Retail Price) × 100",
                "Marketing Margin = Retail Price - Farm-Gate Price"
            ),
            examAndFieldFocus = "Marketing margin decomposition, cooperative governance principles, grading standards, and seasonal arbitrage.",
            googleSearchQuery = "Marketing of Agricultural Products Kohls Uhl marketing margin farmer share"
        ),

        // AGRICULTURAL EXTENSION, CLIMATE-SMART & MODERN TECH
        AgriBookReference(
            id = "book_fao_climate_smart_ag",
            title = "Climate-Smart Agriculture Sourcebook",
            authors = "Food and Agriculture Organization (FAO)",
            edition = "2nd Edition",
            publisher = "FAO Rome (Open-Access Reference)",
            year = 2017,
            primaryPillar = CurriculumPillarType.AGRI_EXTENSION,
            coverSubject = "Climate-Smart Agriculture & Carbon Sequestration",
            description = "Authoritative FAO manual covering the 3 pillars of Climate-Smart Agriculture (sustainably increasing productivity, adapting resilience, and reducing GHG emissions) across crops, soils, and livestock.",
            keyChapters = listOf(
                "Module A2: Climate-Smart Soils & Conservation Agriculture (Zero-Till, Residue Cover)",
                "Module B1: Drought-Tolerant Germplasm and Climate-Smart Crop Production",
                "Module B6: Integrated Water Management & Rainwater Harvesting",
                "Module C4: Agro-Meteorological Advisories & Digital Early Warning"
            ),
            syllabusTopicsCovered = listOf("Climate-Smart Agriculture", "Modern Agricultural Technologies", "1.d Farming Systems"),
            whyItIsAuthoritative = "Produced by FAO technical divisions globally as the standard public-domain operational guide for national climate adaptation programs.",
            scholarSearchQuery = "FAO Climate-Smart Agriculture Sourcebook conservation agriculture",
            isbn = "978-9251077207",
            keyTakeaways = listOf(
                "Conservation Agriculture rests on 3 linked principles: Minimum mechanical soil disturbance, permanent organic soil cover, and species diversification.",
                "Zai pits, tied ridges, and contour bunds increase seasonal rainwater infiltration by 30–60% in semi-arid East Africa.",
                "Alternate Wetting and Drying (AWD) in lowland rice cuts methane (CH4) emissions by up to 48% without hurting grain yield."
            ),
            keyFormulasOrPrinciples = listOf(
                "3 CSA Pillars: 1) Sustainable Productivity + 2) Resilience/Adaptation + 3) GHG Mitigation",
                "Soil Organic Carbon Sequestration = Biomass Return × Humification Rate - Tillage Oxidation"
            ),
            examAndFieldFocus = "Conservation agriculture 3 principles, rainwater harvesting structures, enteric CH4/N2O mitigation, and index insurance.",
            googleSearchQuery = "FAO Climate Smart Agriculture Sourcebook 3 pillars conservation agriculture"
        ),
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
            scholarSearchQuery = "Diffusion of Innovations Everett Rogers 5th Edition Free Press",
            isbn = "978-0743222099",
            keyTakeaways = listOf(
                "Five Adopter Categories along the normal S-curve: Innovators (2.5%), Early Adopters (13.5%), Early Majority (34%), Late Majority (34%), Laggards (16%).",
                "Five Innovation Attributes explaining 49–87% of adoption speed: Relative Advantage, Compatibility, Complexity (inverse), Trialability, and Observability.",
                "Innovation-Decision stages: Knowledge -> Persuasion -> Decision -> Implementation -> Confirmation."
            ),
            keyFormulasOrPrinciples = listOf(
                "S-Curve Adopter Distribution: 2.5% | 13.5% | 34% | 34% | 16%",
                "High Relative Advantage + High Observability + Low Complexity = Rapid Farmer Adoption"
            ),
            examAndFieldFocus = "Adopter bell curve percentages, 5 perceived attributes of innovations, and homophily vs heterophily in extension.",
            googleSearchQuery = "Diffusion of Innovations Everett Rogers adopter categories five attributes agriculture"
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
            scholarSearchQuery = "Agricultural Extension van den Ban Hawkins Blackwell",
            isbn = "978-0632040537",
            keyTakeaways = listOf(
                "Method Demonstrations teach 'HOW' to execute a skill step-by-step (e.g., calibrating a knapsack); Result Demonstrations prove 'WHAT' outcomes occur side-by-side in a farmer's field.",
                "Farmer Field Schools (FFS) apply discovery-based adult learning across a full crop cycle rather than top-down lectures.",
                "Extension combines Voluntary Behavioral Change, Non-Formal Education, and Two-Way Feedback."
            ),
            keyFormulasOrPrinciples = listOf(
                "Extension Modes: Individual (Farm Visits) | Group (FFS, Demos) | Mass (Radio, Mobile SMS/Apps)",
                "Berlo SMCR Communication Model: Source -> Message -> Channel -> Receiver (+ Feedback)"
            ),
            examAndFieldFocus = "Method vs Result Demonstration differences, FFS Agroecosystem Analysis (AESA), and program impact evaluation.",
            googleSearchQuery = "Agricultural Extension van den Ban Hawkins method result demonstration FFS"
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
            scholarSearchQuery = "Whose Reality Counts Putting First Last Robert Chambers",
            isbn = "978-1853393860",
            keyTakeaways = listOf(
                "PRA emphasizes 'Handing Over the Stick' so farmers co-analyze community resource maps, seasonal rainfall/labor calendars, and wealth rankings.",
                "Triangulation validates rural observations across multiple informants, disciplines, and field tools.",
                "Indigenous Technical Knowledge (ITK) provides crucial local pest, varietal, and micro-climate context."
            ),
            keyFormulasOrPrinciples = listOf(
                "Core PRA Tools: Transect Walks | Social/Resource Maps | Seasonal Calendars | Matrix Scoring",
                "Reversal of Learning: Extension Officer as Facilitator, Farmer as Co-Researcher"
            ),
            examAndFieldFocus = "PRA vs RRA comparisons, seasonal calendar diagnostics, and participatory community needs assessment.",
            googleSearchQuery = "Whose Reality Counts Robert Chambers Participatory Rural Appraisal PRA tools"
        )
    )
}
