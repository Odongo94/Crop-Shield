package com.example.data.science.curriculum

import com.example.model.science.CurriculumPillarType
import com.example.model.science.CurriculumSubtopic
import com.example.model.science.CurriculumTopic

object AnimalScienceCurriculum {

    val topics: List<CurriculumTopic> = listOf(
        CurriculumTopic(
            id = "animal_sci_breeding",
            pillar = CurriculumPillarType.ANIMAL_SCIENCE,
            code = "3.a",
            title = "Animal Breeding & Genetics",
            summary = "Quantitative genetics in farm animals, heritability of production traits, selection index, Estimated Breeding Values (EBVs), inbreeding depression, and structured crossbreeding systems.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "as_genetics_selection",
                    code = "i",
                    title = "Heritability, EBVs & Crossbreeding Systems",
                    coreDefinition = "The application of quantitative genetic principles to evaluate, rank, and select parent animals for superior progeny performance in meat, milk, egg, and fiber traits.",
                    keyMechanismsAndFormulas = listOf(
                        "Estimated Breeding Value (EBV): EBV = h² x (Individual Phenotypic Record - Contemporary Group Mean). Expected Progeny Difference (EPD) = 0.5 x EBV.",
                        "Breeder's Equation for Livestock: ΔG = (h² x S) / L, where ΔG is annual genetic progress, h² is narrow-sense heritability, S is selection differential, and L is generation interval (years).",
                        "Inbreeding Coefficient (Wright's F): F_X = Σ [(1/2)^(n1 + n2 + 1) x (1 + F_A)], measuring the probability that two alleles at any locus are identical by descent (IBD).",
                        "Heterosis in Crossbreeding: Heterosis (%) = [(Crossbred Progeny Mean - Purebred Parents Mean) / Purebred Parents Mean] x 100. Retained heterosis in 3-breed rotational cross = 85.7%."
                    ),
                    detailedElaboration = "Animal breeding focuses on altering the genetic merit of livestock populations over successive generations.\n\nHeritability (h²) Classes:\n1) Reproductive & Fitness Traits (Calving interval, litter size, conception rate, embryo survival): Exhibit low heritability (h² < 0.15). Phenotypic selection is slow and ineffective because environmental factors (nutrition, body condition, disease) dominate phenotypic variance. However, reproductive traits respond dramatically to crossbreeding (maternal heterosis).\n2) Growth & Production Traits (Average Daily Gain ADG, weaning weight, yearling weight, milk volume): Exhibit moderate heritability (h² = 0.20 to 0.40), responding well to both selection and management.\n3) Carcass & Composition Traits (Backfat thickness, ribeye area, marbling, dressing percentage): Exhibit high heritability (h² > 0.40). They are governed by additive genetic variance and respond swiftly to direct phenotypic selection or ultrasound scanning.\n\nCrossbreeding Systems: Modern commercial meat production uses structured crossbreeding: 1) Two-Breed Rotational Cross: Alternates two breeds (e.g., Angus x Hereford); retains 66.7% of maximum heterosis at equilibrium. 2) Three-Breed Rotational Cross: Rotates three breeds; retains 85.7% heterosis. 3) Terminal Crossbreeding: Mates a crossbred maternal F1 dam (e.g., Landrace x Large White pig, providing 100% maternal heterosis for litter size and milking ability) to a purebred terminal sire with exceptional carcass muscling (e.g., Pietrain or Duroc). 100% of progeny are marketed for slaughter, capturing maximum individual and maternal hybrid vigor.",
                    biologicalAndChemicalDetails = listOf(
                        "Genomic Selection (SNP Chips): High-density Illumina bovine/porcine 50K SNP arrays; calculates Genomic Estimated Breeding Values (GEBVs) in young bulls at birth, slashing dairy generation intervals from 5.5 years down to 1.8 years.",
                        "Inbreeding Depression Mechanism: Homozygosity exposes lethal or sub-lethal recessive alleles (e.g., Complex Vertebral Malformation - CVM, Bovine Leukocyte Adhesion Deficiency - BLAD) and reduces immunocompetence."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "Bovine Contemporary Group Standardization: Group calves born within a 60-day window, managed under identical nutritional and pasture environments. Measure weaning weight at 205 days (adjusted for calf sex and age of dam). Calculate contemporary group ratio: Ratio = (Individual Adjusted Weight / Group Average Adjusted Weight) x 100.",
                        "Ear-Notching Identification in Piglets: Use sterile V-notchers within 72 hours of birth. Right ear indicates Litter Number (notches at 1, 3, 9, 27, 81); Left ear indicates Individual Pig Number (notches at 1, 3, 9). Record immediately in breeding herd book to prevent accidental inbreeding."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Inbreeding Depression in Cattle: Reduced calf birth vigor, increased stillbirths, congenital crooked calf syndrome, and prolonged calving intervals (>420 days).",
                        "Scrotal Hypoplasia: Undersized testicles in young breeding bulls (<30 cm scrotal circumference at 14 months) indicating low daily sperm output and subfertility (scrotal size is highly heritable, h² ~0.45)."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: In a commercial beef herd, the average weaning weight of the contemporary heifer group is 200 kg. A seedstock producer selects an elite bull with a 205-day weaning weight of 240 kg. The narrow-sense heritability (h²) for weaning weight is 0.30. Calculate: 1) The bull's Estimated Breeding Value (EBV); and 2) The Expected Progeny Difference (EPD) that the bull transmits to his future calves.\nSolution:\n1) Selection Differential = Bull's Record - Group Mean = 240 kg - 200 kg = +40 kg.\nEstimated Breeding Value (EBV) = h² x Selection Differential = 0.30 x (+40 kg) = +12.0 kg.\n2) Expected Progeny Difference (EPD) = 0.5 x EBV = 0.5 x (+12.0 kg) = +6.0 kg.\nInterpretation: Calves sired by this bull are genetically predicted to weigh 6.0 kg heavier at 205-day weaning compared to calves from an average contemporary bull."
                    ),
                    commonExamQuestions = listOf(
                        "Explain why crossbreeding produces substantial improvements in reproductive traits despite low trait heritability.",
                        "Distinguish between Estimated Breeding Value (EBV) and Expected Progeny Difference (EPD) in livestock sire summaries.",
                        "Design a three-breed rotational crossbreeding system and calculate the percentage of maximum heterosis retained at genetic equilibrium."
                    ),
                    practicalTakeaway = "In commercial pork and beef production, always utilize a terminal sire on crossbred maternal cows/sows: marketing 100% of terminal progeny captures full hybrid vigor and superior slaughter muscling.",
                    authoritativeTextbookRef = "Understanding Animal Breeding (Bourdon); Animal Science and Industry (Ensminger & Parker, Ch. 4)"
                )
            ),
            examFocusPoints = listOf(
                "Heritability classifications: reproductive (<0.15), growth (0.20-0.40), carcass (>0.40)",
                "Heterosis retention formulas in rotational (67%, 86%) vs. terminal (100%) crossbreeding",
                "Breeder's Equation in animals: ΔG = (h² x S) / L",
                "Sire summary EPD interpretation and contemporary group ratios"
            ),
            practicalFieldApplications = listOf(
                "Standardizing 205-day bovine weaning weights for dam age and calf sex",
                "Applying the Universal ear-notching numbering system in swine breeding herds"
            ),
            recommendedTextbookIds = listOf("book_animal_breeding_bourdon", "book_ensminger_animal_science")
        ),
        CurriculumTopic(
            id = "animal_sci_physiology",
            pillar = CurriculumPillarType.ANIMAL_SCIENCE,
            code = "3.b",
            title = "Animal Physiology",
            summary = "Endocrine regulation, estrous cycle phases, gametogenesis, fertilization, gestation, parturition, lactation physiology, and thermoregulation in livestock.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "as_repro_lactation",
                    code = "i",
                    title = "Endocrinology, Estrous Cycle & Lactation",
                    coreDefinition = "The physiological and neuroendocrine mechanisms governing estrus, ovulation, gestation, parturition, alveolar milk synthesis, and environmental thermoregulation in domestic livestock.",
                    keyMechanismsAndFormulas = listOf(
                        "The 21-Day Bovine Estrous Cycle: Follicular phase (proestrus 3 days, estrus 12–18 hours; dominated by 17β-estradiol from Graafian follicle); Luteal phase (metestrus 3 days, diestrus 12–15 days; dominated by progesterone from Corpus Luteum).",
                        "Luteolysis Signaling: If no embryo signals maternal recognition of pregnancy (Interferon-tau in cattle), pulsatile oxytocin from posterior pituitary binds endometrial receptors, stimulating endometrial secretion of Prostaglandin F2α (PGF2α). PGF2α crosses via local countercurrent transfer from utero-ovarian vein into ovarian artery, lysing the CL.",
                        "Neuroendocrine Milk Letdown Reflex: Teat tactile stimulation -> afferent dorsal nerve -> spinothalamic tract -> paraventricular and supraoptic nuclei of hypothalamus -> posterior pituitary pulsatile release of Oxytocin -> bloodstream (30–60s transit) -> myoepithelial cell contraction around mammary alveoli -> milk ejection.",
                        "Temperature-Humidity Index (THI): THI = 0.8 x T_db + RH x (T_db - 14.4) + 46.4, where T_db is dry bulb temperature (°C) and RH is relative humidity fraction. THI > 72 triggers mild heat stress; THI > 82 causes severe lactation collapse and embryonic mortality."
                    ),
                    detailedElaboration = "Reproductive endocrinology forms the basis of artificial insemination (AI) and herd synchronization programs.\n\nHormonal Axis of Reproduction: Pulsatile Gonadotropin-Releasing Hormone (GnRH) from the tonic and surge centers of the hypothalamus stimulates the anterior pituitary to secrete Follicle-Stimulating Hormone (FSH, driving follicular recruitment and wave emergence) and Luteinizing Hormone (LH). High estrogen from the pre-ovulatory Graafian follicle triggers the GnRH surge center, releasing a massive LH surge that induces follicular rupture and ovulation approximately 28–32 hours later (in cattle, ovulation occurs 10–14 hours AFTER the end of standing estrus).\n\nMaternal Recognition of Pregnancy (MRP): In cows, the blastocyst elongates into a filamentous conceptus at Day 15–17, secreting Interferon-tau (IFN-τ). IFN-τ blocks the transcription of oxytocin receptors in the uterine endometrium, preventing the pulsatile release of PGF2α. The Corpus Luteum survives and continues secreting Progesterone (the hormone of pregnancy), maintaining uterine quiescence and closed cervix.\n\nLactation Physiology: Mammary alveoli are lined by epithelial secretory cells that convert blood precursors (glucose, amino acids, acetate, beta-hydroxybutyrate) into milk lactose, caseins, and butterfat. Alpha-lactalbumin synthesized under prolactin stimulation forms the catalytic subunit of lactose synthase. Adrenaline/Epinephrine released during fear, yelling, or rough handling induces immediate peripheral vasoconstriction and blocks oxytocin receptors on myoepithelial cells, completely shutting down milk letdown.",
                    biologicalAndChemicalDetails = listOf(
                        "Sperm Capacitation & Acrosome Reaction: Removal of seminal plasma decapacitation glycoproteins from sperm head in female tract -> influx of Ca2+ -> fusion of outer acrosomal membrane with sperm plasma membrane -> release of hyaluronidase and acrosin to digest zona pellucida.",
                        "Heat Stress Cellular Mechanism: Elevated core temperature (>39.5°C) stimulates cellular heat shock proteins (HSP70), alters follicular fluid steroidogenesis, suppresses LH pulse frequency, and induces thermal shock apoptosis in pre-attachment Day 1–7 embryos."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "Ovsynch Heat Synchronization Protocol in Dairy Cattle: Day 0 (Monday): Inject GnRH (100 µg i.m.) to synchronize follicular wave emergence. Day 7 (following Monday): Inject PGF2α (25 mg dinoprost or 500 µg cloprostenol) to lyse Corpus Luteum. Day 9 (Wednesday, 48 hrs post-PGF): Inject second GnRH (100 µg) to induce synchronized LH surge and ovulation. Day 10 (Thursday morning, 16 hrs post-second GnRH): Perform Timed Artificial Insemination (TAI) on all cows without estrus detection.",
                        "AM/PM Breeding Rule for Cattle Artificial Insemination: If standing heat is observed in the morning (standing firmly when mounted by other cows), artificially inseminate the cow the same evening (12 hours later). If standing heat is observed in the evening, inseminate the following morning. Deposit semen into the uterine body immediately past the internal cervical ring."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Standing Heat Primary Sign: Cow stands completely immobile and rigid when mounted from behind by other herd mates; secondary signs include clear stringy mucus hanging from vulva, restless bellowing, and roughed-up tailhead hair.",
                        "Follicular Cyst: Large thin-walled ovarian structure (>25 mm diameter) persisting for >10 days in the absence of a Corpus Luteum, accompanied by continuous nymphomania or erratic estrus behavior; treated with GnRH or hCG to induce luteinization."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: A dairy farm experiences a hot afternoon with ambient dry bulb temperature of 32.0°C and a relative humidity of 60% (RH = 0.60). Calculate the Temperature-Humidity Index (THI) using the standard Kibler formula and determine the heat stress risk category for high-producing Holstein cows (Thresholds: THI < 72 = Comfort; 72–78 = Mild Stress; 79–88 = Moderate/Severe Stress).\nSolution: 1) Formula: THI = 0.8 x T_db + RH x (T_db - 14.4) + 46.4. 2) Calculation: THI = 0.8 x (32.0) + 0.60 x (32.0 - 14.4) + 46.4 = 25.6 + 0.60 x (17.6) + 46.4 = 25.6 + 10.56 + 46.4 = 82.56. Conclusion: THI is 82.6, placing the herd in Moderate to Severe Heat Stress. High-producing cows will exhibit panting (>80 breaths/min), dry matter intake will drop by 15–25%, daily milk yield will decline by 4–8 liters/cow, and conception rates will crash unless evaporative tunnel cooling fans and low-pressure soaking sprinklers are operated."
                    ),
                    commonExamQuestions = listOf(
                        "Diagram the hormonal feedback loops governing the 21-day bovine estrous cycle, detailing GnRH, FSH, LH, Estrogen, Progesterone, and PGF2α.",
                        "Trace the neuroendocrine reflex pathway of milk letdown from teat tactile stimulation to alveolar contraction, explaining why stress impairs milking.",
                        "Explain how Interferon-tau (IFN-τ) accomplishes maternal recognition of pregnancy in cattle."
                    ),
                    practicalTakeaway = "Follow the AM/PM rule for artificial insemination: breeding cows too early (at the beginning of heat) or too late (24 hours after heat) causes sperm death or aged oocyte fertilization failure.",
                    authoritativeTextbookRef = "Pathways to Pregnancy and Parturition (Senger, Ch. 7, 8, 9 & 14); Reproduction in Farm Animals (Hafez & Hafez)"
                )
            ),
            examFocusPoints = listOf(
                "Endocrine regulation of the estrous cycle and Ovsynch synchronization protocols",
                "AM/PM breeding rule and timing of bovine ovulation (12 hrs after standing heat)",
                "Neuroendocrine milk ejection reflex and adrenaline-induced letdown inhibition",
                "Temperature-Humidity Index (THI) calculation and dairy heat stress mitigation"
            ),
            practicalFieldApplications = listOf(
                "Diagnosing standing heat vs. secondary estrus signs in grazing dairy herds",
                "Operating artificial insemination guns and thawing semen straws in 35°C water for exactly 45 seconds"
            ),
            recommendedTextbookIds = listOf("book_senger_pathways_repro", "book_hafez_repro_farm_animals")
        ),
        CurriculumTopic(
            id = "animal_sci_nutrition",
            pillar = CurriculumPillarType.ANIMAL_SCIENCE,
            code = "3.c",
            title = "Animal Nutrition",
            summary = "Digestive anatomy (ruminant vs. non-ruminant), Weende proximate analysis, Van Soest fiber fractions, rumen fermentation kinetics, volatile fatty acids, and least-cost ration balancing.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "as_digestive_nutrition",
                    code = "i",
                    title = "Rumen Fermentation, Proximate Analysis & Pearson Square",
                    coreDefinition = "The comparative biochemical digestion, microbial fermentation, and metabolic utilization of dietary carbohydrates, proteins, lipids, minerals, and vitamins in ruminant and monogastric farm animals.",
                    keyMechanismsAndFormulas = listOf(
                        "Rumen Volatile Fatty Acids (VFAs) Fermentation: 1) Acetate (CH3COO-, C2, fiber digestion; primary precursor for lipogenesis and milk butterfat synthesis); 2) Propionate (CH3CH2COO-, C3, starch/sugar digestion; sole gluconeogenic VFA converted in liver to glucose via methylmalonyl-CoA pathway); 3) Butyrate (CH3CH2CH2COO-, C4; oxidized in rumen epithelial wall to β-hydroxybutyrate fueling epithelial growth). Normal high-forage molar ratio = 65:25:10 (Acetate:Propionate:Butyrate).",
                        "Weende Proximate Analysis Fractions: Dry Matter (DM), Crude Protein (CP = %Total N x 6.25), Ether Extract (EE / Crude Fat), Crude Fiber (CF), Total Ash (inorganic minerals), and Nitrogen-Free Extract (NFE = 100 - Moisture - CP - EE - CF - Ash).",
                        "Van Soest Detergent Fiber System: Neutral Detergent Fiber (NDF = Cellulose + Hemicellulose + Lignin; inversely correlated with dry matter intake, DMI % of BW = 120 / %NDF); Acid Detergent Fiber (ADF = Cellulose + Lignin; inversely correlated with digestibility); Acid Detergent Lignin (ADL = pure indigestible lignin).",
                        "Pearson Square Formulation Equation: Cross-subtraction method determining exact blending proportions of two feed ingredients to achieve a target ration crude protein level."
                    ),
                    detailedElaboration = "Livestock nutrition divides animals into non-ruminant monogastrics (swine, poultry, horses) and multi-compartment ruminants (cattle, sheep, goats, buffalo, camels).\n\nRuminant Stomach Anatomy & Microbiology: The ruminant stomach consists of: 1) Rumen (large anaerobic fermentation vat, 100–150L in adult cattle, maintaining pH 6.0–6.8 and 39°C); 2) Reticulum (honeycomb lining, sorting particles and regurgitating boluses during rumination); 3) Omasum (manyplies leaves, absorbing water, potassium, and residual VFAs); and 4) Abomasum (the true glandular stomach, secreting HCl and pepsin to digest microbial biomass and bypass proteins). The rumen hosts dense symbiotic anaerobic bacteria (10¹⁰/mL), ciliate protozoa (10⁶/mL), and anaerobic fungi that express cellulase and hemicellulase enzymes to hydrolyze beta-1,4-glycosidic bonds in structural plant cell walls.\n\nMicrobial Protein & Non-Protein Nitrogen (NPN): Rumen microbes hydrolyze dietary true protein and NPN (e.g., feed-grade urea) into ammonia (NH3). If adequate fermentable carbohydrates (starches/molasses) are available, microbes combine NH3 with carbon keto-acids to synthesize microbial protein. Microbial protein flows into the abomasum and small intestine, supplying 60–85% of the ruminant's required amino acids with a biological value equivalent to egg protein.\n\nSubacute Ruminal Acidosis (SARA): Feeding excessive rapidly fermentable starch (grain) without adequate physically effective NDF (peNDF) causes lactic acid bacteria (Streptococcus bovis) to proliferate. Rumen pH crashes below 5.5, destroying ciliate protozoa and cellulolytic bacteria, causing rumen wall parakeratosis, systemic endotoxemia, liver abscesses, and laminitis.",
                    biologicalAndChemicalDetails = listOf(
                        "Urea Toxicity Mechanism: Feeding excessive urea (>1% of DM) without adequate readily available energy exceeds the microbial capacity to assimilate NH3. Free unionized NH3 rapidly absorbs across the rumen wall into portal blood, overwhelming the liver's urea cycle; blood ammonia spikes, inhibiting the Krebs cycle and causing neurological tremors, bloat, and death within 1–2 hours.",
                        "First-Limiting Amino Acids: In pigs, Lysine is first-limiting, followed by Threonine and Tryptophan. In poultry, Methionine (a sulfur amino acid essential for feathering) is first-limiting, followed by Lysine."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "Pearson Square Calculation Protocol for Pig Grower Ration: To formulate 1,000 kg of a 16.0% Crude Protein (CP) grower feed using Maize (8.5% CP) and Soybean Meal (44.0% CP):\n1) Place target CP (16.0) in center of square.\n2) Place Maize (8.5) at upper left; Soybean Meal (44.0) at lower left.\n3) Subtract diagonally ignoring negative signs: Upper right (Soybean parts) = |8.5 - 16.0| = 7.5 parts Soybean Meal. Lower right (Maize parts) = |44.0 - 16.0| = 28.0 parts Maize.\n4) Total Parts = 7.5 + 28.0 = 35.5 parts.\n5) Percentage Maize = (28.0 / 35.5) x 100 = 78.87%.\n6) Percentage Soybean Meal = (7.5 / 35.5) x 100 = 21.13%.\n7) For 1,000 kg batch: Mix 788.7 kg Maize + 211.3 kg Soybean Meal (plus minerals/vitamins).",
                        "Treating Urea-Ammoniated Crop Residues: To improve digestibility of dry cereal straw (maize stover/wheat straw): Dissolve 4 kg of fertilizer-grade urea in 40–50 liters of clean water for every 100 kg of chopped dry straw. Spray evenly while packing into an airtight pit or plastic stack. Seal completely for 3 to 4 weeks. Urease enzymes hydrolyze urea to NH3 gas, breaking lignocellulose bonds, increasing crude protein from 3.5% to 8.0%, and boosting voluntary dry matter intake by 30%."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Subacute Ruminal Acidosis (SARA) Signs: Rumen fluid pH < 5.6 on rumocentesis, frothy yellow diarrhea with undigested grain particles, decreased rumination (<50% of resting cows chewing cud), and high herd incidence of sole ulcers and laminitis.",
                        "Milk Fat Depression: Butterfat dropping below 3.0% in Holstein dairy cows fed low-fiber, high-concentrate rations, caused by altered rumen biohydrogenation producing trans-10, cis-12 conjugated linoleic acid (CLA), which potently downregulates mammary fatty acid synthesis."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: A 600-kg dairy cow is fed a Total Mixed Ration (TMR) containing 34.0% Neutral Detergent Fiber (NDF) on a Dry Matter basis. Calculate: 1) The estimated maximum voluntary Dry Matter Intake (DMI) based on the Mertens NDF intake formula (DMI capacity = 1.20% of Body Weight as NDF); 2) The daily dry matter intake in kilograms; and 3) If the TMR is 50.0% moisture (50% DM), calculate the daily as-fed weight of feed the cow must consume.\nSolution:\n1) Maximum NDF Intake Capacity = 1.20% of Body Weight = 0.012 x 600 kg = 7.20 kg NDF/day.\n2) Maximum Dry Matter Intake (DMI) = Max NDF Capacity / (% NDF / 100) = 7.20 kg / 0.34 = 21.18 kg DM/day.\n3) Daily As-Fed Feed Required = Daily DMI / (% DM / 100) = 21.18 kg / 0.50 = 42.36 kg as-fed TMR/day."
                    ),
                    commonExamQuestions = listOf(
                        "Explain how rumen volatile fatty acids (Acetate, Propionate, Butyrate) are synthesized and trace the metabolic fate of propionate in liver gluconeogenesis.",
                        "Compare the Weende Proximate Analysis with the Van Soest Detergent System, explaining why NDF and ADF provide superior nutritional predictions.",
                        "Detail the biochemical etiology and treatment of acute urea/ammonia toxicity in cattle."
                    ),
                    practicalTakeaway = "Always ensure lactating dairy cows receive minimum 28–30% dietary NDF with adequate fiber length (>2.5 cm): fiber scratches the rumen wall, stimulating regurgitation, cud chewing, and sodium bicarbonate saliva buffering.",
                    authoritativeTextbookRef = "Animal Nutrition (McDonald, Greenhalgh, Morgan et al., Ch. 2, 8, 13 & 20); Feeds and Feeding (Ensminger)"
                )
            ),
            examFocusPoints = listOf(
                "Rumen anatomy, VFA metabolic pathways (Acetate:Propionate:Butyrate ratios)",
                "Weende proximate analysis vs. Van Soest fiber fractions (NDF, ADF, ADL)",
                "Pearson Square ration formulation calculations",
                "Non-protein nitrogen (Urea) feeding rules (max 1% of diet DM) and toxicity",
                "Subacute Ruminal Acidosis (SARA) and Milk Fat Depression etiologies"
            ),
            practicalFieldApplications = listOf(
                "Formulating least-cost balanced pig grower and broiler rations using Pearson Square",
                "Operating the Penn State Particle Separator to evaluate dairy TMR physical effective fiber"
            ),
            recommendedTextbookIds = listOf("book_mcdonald_animal_nutrition", "book_ensminger_animal_science")
        ),
        CurriculumTopic(
            id = "animal_sci_products",
            pillar = CurriculumPillarType.ANIMAL_SCIENCE,
            code = "3.d",
            title = "Animal Products Processing",
            summary = "Slaughtering procedures, antemortem/postmortem inspection, muscle-to-meat conversion (rigor mortis, PSE and DFD meat), meat preservation, dairy science (pasteurization, cheese), and egg quality grading.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "as_meat_dairy_egg",
                    code = "i",
                    title = "Slaughtering, Meat Conversion, Dairy & Egg Processing",
                    coreDefinition = "The sanitary handling, food safety inspection, and biochemical processing of livestock carcasses, raw milk, and poultry eggs into commercial consumer products.",
                    keyMechanismsAndFormulas = listOf(
                        "Muscle-to-Meat Conversion & Ultimate pH: Upon slaughter, circulatory oxygen failure triggers anaerobic glycolysis. Muscle glycogen is cleaved into lactic acid; pH drops from 7.0–7.2 (living muscle) down to ultimate pH 5.4–5.7. When ATP reserves deplete, actin and myosin form permanent, irreversible actomyosin cross-bridges (Rigor Mortis).",
                        "Pale, Soft, Exudative (PSE) vs. Dark, Firm, Dry (DFD) Meat: PSE caused by acute preslaughter stress in pigs with Halothane (RYR1) gene mutation; triggers rapid postmortem glycolysis while carcass is still hot (>38°C), dropping pH < 5.4 in 45 minutes, denaturing myofibrillar proteins and destroying water-holding capacity. DFD caused by chronic long-term exhaustion depleting all glycogen; ultimate pH remains high (>6.0), creating dark, dry, gummy meat prone to rapid spoilage.",
                        "High-Temperature Short-Time (HTST) Pasteurization Standard: 72°C (161°F) for exactly 15 seconds (calibrated to eliminate Coxiella burnetii, the most heat-resistant non-spore-forming milk-borne pathogen, and Mycobacterium bovis).",
                        "Egg Freshness Haugh Unit (HU): HU = 100 x log(H - 1.7W^0.37 + 7.6), where H is albumen height (mm) and W is egg weight (g). Grade AA: HU ≥ 72; Grade A: 60–71; Grade B: <60."
                    ),
                    detailedElaboration = "Animal products processing bridges live animal husbandry with human food hygiene and cold chain preservation.\n\nHumane Slaughtering & Meat Quality: Animals must be rested in lairage with clean water for 12–24 hours before slaughter to restore muscle glycogen. Humane stunning renders the animal insensible to pain prior to bleeding: 1) Captive Bolt Stunning (penetrating or non-penetrating bolt for cattle); 2) Electrical Stunning (head-only or head-to-body electrical current for sheep/pigs); or 3) CO2 Gas Stunning (70–90% CO2 in pits for swine). Following stunning, sticking and exsanguination must be performed within 20–30 seconds to prevent blood splash and ensure complete blood evacuation (residual blood promotes rapid spoilage).\n\nDairy Processing & Milk Safety: Freshly drawn raw milk must be cooled to <4°C within 2 hours. Milk fat testing utilizes the Gerber or Babcock sulfuric acid method. Homogenization passes hot milk (60°C) through a microscopic orifice under high pressure (15–20 MPa / 2,500 PSI), mechanically shearing fat globules from 4–10 µm down to <1 µm; this prevents gravitational creaming separation and yields a uniform, creamy mouthfeel. Cultured dairy processing (yogurt) utilizes symbiotic thermophilic cultures (Lactobacillus bulgaricus and Streptococcus thermophilus) fermenting lactose to lactic acid, dropping pH to 4.5 and isoelectrically coagulating casein micelles into a gel.\n\nTable Egg Quality: Fresh eggs possess a high, firm thick-albumen gel supported by ovomucin networks. As eggs age, CO2 diffuses out through porous eggshell pores; albumen pH rises from 7.6 to 9.5, disintegrating ovomucin and causing albumen liquefaction. Concurrently, water migrates into the air cell, increasing air cell depth. Industrial egg candling illuminates the internal contents to grade air cell depth, yolk shadow mobility, and blood spots.",
                    biologicalAndChemicalDetails = listOf(
                        "Meat Tenderization via Calpain Proteolysis: Postmortem aging at 2–4°C for 7–21 days tenderizes meat through endogenous calcium-activated proteases (µ-calpain and m-calpain) that degrade titin, nebulin, and desmin z-disk structural proteins (calpastatin is the endogenous inhibitor).",
                        "Alkaline Phosphatase (ALP) Pasteurization Verification: Milk enzyme with thermal denaturation kinetics slightly higher than Coxiella burnetii; a negative ALP test proves that pasteurization temperature and time were achieved."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "California Mastitis Test (CMT) Cowside Screening: Strip the first 2 streams of foremilk. Express 2 mL of milk from each of the 4 individual teats into the 4 corresponding wells of a CMT plastic paddle. Add equal volume (2 mL) of CMT reagent (alkyl aryl sulfonate detergent + bromocresol purple indicator). Swirl paddle gently in horizontal circles for 10–15 seconds. If somatic cells (leukocytes) are high, the detergent lyses cell membranes, liberating cellular DNA which forms a thick, viscous jelly/slime (scored negative, trace, 1, 2, or 3), confirming subclinical mastitis.",
                        "Egg Candling & Grading Protocol: In a darkened room, hold the egg at a 45° angle with the large end against the light aperture of a high-intensity candling lamp. Twirl the egg rapidly clockwise and counterclockwise. Observe: 1) Air cell depth (<3 mm for Grade AA, 3–5 mm for Grade A, >5 mm for Grade B); 2) Yolk outline (Grade AA yolk is only dimly visible); 3) Freedom from meat or blood spots (>3 mm blood spots are condemned as inedible)."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "PSE Pork Appearance: Carcass loin muscle appears exceedingly pale/greyish-white, with wet surface exuding free water, soft texture, and severe cooking shrink loss.",
                        "DFD Beef Appearance: Dark purplish-red muscle that fails to bloom into bright cherry red upon air exposure, sticky/gummy surface, and high pH (>6.0) causing spoilage within days."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: An inspector breaks out a fresh commercial Grade A table egg. The measured height of the thick albumen (H) on a micrometer spherometer is 6.50 mm. The mass of the whole egg (W) is 60.0 grams. Calculate the Haugh Unit (HU) score using the official USDA formula and determine the official egg quality grade (Grade AA: ≥ 72; Grade A: 60–71; Grade B: < 60).\nSolution:\n1) Formula: HU = 100 x log10(H - 1.7W^0.37 + 7.6).\n2) Calculate W^0.37: 60.0^0.37 = 4.5516.\n3) Multiply by 1.7: 1.7 x 4.5516 = 7.7377.\n4) Calculate inside term: H - 7.7377 + 7.6 = 6.50 - 7.7377 + 7.6 = 6.3623.\n5) Take log10: log10(6.3623) = 0.8036.\n6) Multiply by 100: HU = 100 x 0.8036 = 80.36.\nConclusion: The Haugh Unit score is 80.4, placing this egg in the premium Grade AA category."
                    ),
                    commonExamQuestions = listOf(
                        "Detail the postmortem biochemical events of rigor mortis and contrast the physiological causes of PSE pork versus DFD beef.",
                        "Define the time and temperature parameters of HTST pasteurization and explain why the alkaline phosphatase test verifies pasteurization efficacy.",
                        "Explain how the Haugh Unit is calculated and describe the physical and biochemical changes that occur as table eggs age."
                    ),
                    practicalTakeaway = "Always allow slaughter animals 12–24 hours of resting lairage with access to clean water: hauling exhausted, dehydrated animals straight to slaughter guarantees dark, firm, dry (DFD) unmarketable meat.",
                    authoritativeTextbookRef = "Lawrie's Meat Science (Lawrie & Ledward, Ch. 4, 5 & 6); Dairy Processing Handbook (Tetra Pak); Commercial Chicken Meat and Egg Production (Bell & Weaver)"
                )
            ),
            examFocusPoints = listOf(
                "Biochemical events of muscle-to-meat conversion and rigor mortis curve",
                "Etiology and prevention of PSE (acute stress) and DFD (chronic stress) conditions",
                "Dairy processing standards: HTST pasteurization (72°C for 15s) and alkaline phosphatase test",
                "Haugh Unit egg grading formula and candling inspection criteria"
            ),
            practicalFieldApplications = listOf(
                "Performing California Mastitis Tests (CMT) to screen lactating herds for subclinical mastitis",
                "Operating egg candling lamps to measure air cell depth and identify interior blood spots"
            ),
            recommendedTextbookIds = listOf("book_lawrie_meat_science", "book_dairy_processing_handbook")
        ),
        CurriculumTopic(
            id = "animal_sci_health",
            pillar = CurriculumPillarType.ANIMAL_SCIENCE,
            code = "3.e",
            title = "Animal Health and Diseases",
            summary = "Biosecurity protocols, epidemiology, major viral (FMD, ASF, Newcastle), bacterial (Anthrax, Mastitis, Brucellosis), and parasitic diseases, vaccination schedules, and antimicrobial stewardship.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "as_pathology_biosecurity",
                    code = "i",
                    title = "Major Livestock Diseases & Biosecurity Protocols",
                    coreDefinition = "The clinical diagnosis, epidemiological containment, immunological prophylaxis, and strict biosecurity protocols required to safeguard livestock herds against infectious transboundary and zoonotic pathogens.",
                    keyMechanismsAndFormulas = listOf(
                        "Three Pillars of Farm Biosecurity: 1) Bio-Exclusion (preventing pathogen introduction onto farm); 2) Bio-Management (preventing pathogen transmission across pens/units); 3) Bio-Containment (preventing pathogen escape from farm into community).",
                        "Anthrax (Bacillus anthracis) Spore Pathology: Gram-positive, spore-forming rod. In vegetative state inside living blood, encapsulated by poly-D-glutamic acid capsule resisting phagocytosis; produces lethal toxin (protective antigen + lethal factor) and edema toxin. Upon air exposure from opened carcass, vegetative bacilli sporulate into indestructible endospores surviving in soil for >50 years. Strict rule: NEVER OPEN AN ANTHRAX CARCASS.",
                        "Foot-and-Mouth Disease (FMD) Aphthovirus: Non-enveloped Picornavirus (7 serotypes: O, A, C, SAT-1, SAT-2, SAT-3, Asia-1; no cross-immunity). Vesicular erosions on tongue, dental pad, interdigital cleft, and coronary band; high morbidity (~100%), low adult mortality, but causes irreversible loss of milk yield and heart myocarditis in young calves ('tiger heart').",
                        "African Swine Fever (ASF) Asfarvirus: Large double-stranded enveloped DNA virus; mortality approaches 100% in naive domestic pigs. Replicates in macrophages/monocytes, inducing massive cytokine storm, endothelial hemorrhage, DIC, and splenic infarction. No commercial vaccine; controlled solely by biosecurity stamping-out."
                    ),
                    detailedElaboration = "Livestock disease management prioritizes preventive biosecurity and immunological protection over post-infection antibiotic treatments.\n\nZoonoses & Public Health Hazards: Zoonotic diseases transmit between animals and humans. Brucellosis (Brucella abortus in cattle, B. melitensis in goats) causes contagious abortion in late gestation (third trimester) accompanied by retained placenta and metritis; in humans, unpasteurized dairy consumption causes chronic debilitating 'undulant fever' and orchitis. Bovine Tuberculosis (Mycobacterium bovis) causes granulomatous caseous tubercles in lungs and retropharyngeal lymph nodes, confirmed by the Single Intradermal Comparative Tuberculin Test (SICTT).\n\nBovine Mastitis & NMC 5-Point Plan: Mastitis is inflammation of the mammary gland parenchymal tissue, categorized into Contagious mastitis (Streptococcus agalactiae, Staphylococcus aureus; spreads during milking) and Environmental mastitis (Escherichia coli, Streptococcus uberis; from muddy bedding). The National Mastitis Council (NMC) 5-point plan: 1) Post-milking teat disinfection with 0.5–1.0% iodine; 2) Dry cow antibiotic therapy on all quarters at dry-off; 3) Prompt therapeutic treatment of clinical mastitis cases; 4) Culling of chronic, non-responsive cows; 5) Routine milking machine maintenance and cluster sanitization.\n\nTransboundary Animal Diseases (TADs): Contagious Bovine Pleuropneumonia (CBPP, Mycoplasma mycoides subsp. mycoides) causes unilateral fibrinous pleuropneumonia, massive pleural effusion, and 'marbled lung' hepatization. Peste des Petits Ruminants (PPR, Morbillivirus in goats/sheep) causes necrotizing stomatitis, purulent oculonasal discharge, and fatal bronchopneumonia.",
                    biologicalAndChemicalDetails = listOf(
                        "Newcastle Disease Virus (NDV) Pathotypes: Paramyxovirus divided into: 1) Velogenic (highly virulent, causing acute respiratory distress, green diarrhea, neurological torticollis/twisted neck, 90–100% mortality); 2) Mesogenic (moderate virulence, respiratory/egg drops); 3) Lentogenic (mild, used as live vaccines e.g., Hitchner B1, LaSota).",
                        "Anthrax Polychrome Methylene Blue Staining (McFadyean Reaction): Peripheral blood smear stained with polychrome methylene blue reveals blue bacilli surrounded by prominent reddish-purple amorphous capsular material, definitively confirming Anthrax within 5 minutes."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "Anthrax Carcass Safe Disposal Protocol: 1) Upon suspecting Anthrax (sudden death, absence of rigor mortis, uncoagulated tarry dark blood oozing from mouth, nose, anus), do NOT make any incision. 2) Collect peripheral ear vein blood using a sterile needle onto a glass slide for McFadyean staining. 3) Dig a disposal pit at least 2.0 meters (6.5 feet) deep, well away from watercourses. 4) Cover pit bottom with agricultural quicklime (CaO, 5 cm layer). 5) Carefully roll intact carcass into pit, cover carcass with thick layer of quicklime, and backfill with soil mounded above ground level. 6) Disinfect all surrounding soil with 10% formalin or 5% sodium hydroxide (lye).",
                        "Farm Gate Biosecurity Disinfection Footbath & Wheelbath Protocol: Install vehicle wheel-wash dip (minimum 30 cm deep) and pedestrian boot-dip at entrance. Fill with a broad-spectrum DEFRA-approved disinfectant (e.g., Virkon S @ 1:100 dilution or Glutaraldehyde). Mandate that all visitors scrub boots clean of organic manure first (organic matter deactivates disinfectants), step into footbath for 30 seconds, and don farm-dedicated coveralls."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Anthrax Postmortem Signs: Incomplete rigor mortis, dark unclotted tarry blood exuding from all natural body openings, rapid carcass putrefaction and abdominal bloat.",
                        "African Swine Fever Signs: High fever (>41°C), cyanosis (purplish-blue blotching) on ears, snout, belly, and inner thighs, and postmortem finding of an enlarged, dark friable 'blackberry jam' spleen.",
                        "Brucellosis Abortion 'Moroccan Leather' Placenta: Leathery, thickened cotyledons and intercotyledonary areas covered in dirty brownish-yellow exudate."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: A commercial poultry farm with 20,000 broilers experiences an outbreak of coccidiosis. The veterinarian prescribes toltrazuril (Baycox 2.5% oral solution) to be administered in drinking water at a dosage rate of 7.0 mg of active ingredient per kilogram of body weight per day for 2 consecutive days. The average live body weight of the flock is 1.50 kg. The birds consume an average of 200 mL of water per bird per day. Calculate: 1) Total active toltrazuril required per day (in grams); 2) Volume of commercial Baycox 2.5% solution (containing 25 mg/mL) required per day; and 3) The required concentration of Baycox solution in the central drinking water medication tank (in mL per Liter of water).\nSolution:\n1) Total flock liveweight = 20,000 birds x 1.50 kg = 30,000 kg.\nActive toltrazuril needed = 30,000 kg x 7.0 mg/kg = 210,000 mg = 210.0 grams a.i./day.\n2) Volume of Baycox 2.5% (25 mg/mL): Volume = 210,000 mg / 25 mg/mL = 8,400 mL = 8.40 Liters of Baycox per day.\n3) Total daily water consumed = 20,000 birds x 0.200 L = 4,000 Liters of water.\nMedication concentration = 8,400 mL Baycox / 4,000 L Water = 2.10 mL Baycox per Liter of drinking water."
                    ),
                    commonExamQuestions = listOf(
                        "Why is making an incision or opening the carcass of an animal suspected of dying from Anthrax strictly prohibited by international veterinary law?",
                        "Outline the 5-point mastitis control plan recommended by the National Mastitis Council (NMC).",
                        "Differentiate the clinical pathology and gross lesions of African Swine Fever (ASF) from Classical Swine Fever (CSF)."
                    ),
                    practicalTakeaway = "Never open or skin an animal that died suddenly with uncoagulated black blood exuding from orifices: opening the carcass exposes vegetative Anthrax bacilli to air, triggering sporulation that contaminates pasture for 50 years.",
                    authoritativeTextbookRef = "Veterinary Medicine: A Textbook of the Diseases of Cattle, Horses, Sheep, Pigs and Goats (Radostits, Gay, Hinchcliff & Constable); Animal Diseases (Blood)"
                )
            ),
            examFocusPoints = listOf(
                "Notifiable OIE/WOAH transboundary animal diseases (FMD, ASF, CBPP, Newcastle)",
                "Anthrax clinical pathology, McFadyean capsule staining, and deep burial protocols",
                "NMC 5-point mastitis control program and teat dipping chemistry",
                "Zoonotic transmission pathways of Brucellosis and Bovine Tuberculosis"
            ),
            practicalFieldApplications = listOf(
                "Executing farm biosecurity Danish-entry bench and footbath disinfection rotations",
                "Operating routine herd vaccination schedules (LSD, Blackquarter, Anthrax, Newcastle)"
            ),
            recommendedTextbookIds = listOf("book_radostits_veterinary_medicine", "book_ensminger_animal_science")
        ),
        CurriculumTopic(
            id = "animal_sci_poultry",
            pillar = CurriculumPillarType.ANIMAL_SCIENCE,
            code = "3.f",
            title = "Poultry Production",
            summary = "Broiler and commercial egg layer management, hatchery incubation parameters, brooding thermal curves, lighting programs, and Feed Conversion Ratio (FCR) optimization.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "as_poultry_management",
                    code = "i",
                    title = "Broiler & Layer Management, Incubation & FCR",
                    coreDefinition = "The intensive scientific production systems for commercial meat chickens (broilers) and table-egg laying hens, including embryonic incubation and flock bioenergetics.",
                    keyMechanismsAndFormulas = listOf(
                        "Feed Conversion Ratio (FCR): FCR = Total Feed Consumed (kg) / Total Liveweight Gain (kg). Modern commercial broilers achieve FCR 1.45–1.55 at 35 days (2.0–2.2 kg liveweight).",
                        "European Production Efficiency Factor (EPEF / PEF): EPEF = [(Livability % x Average Liveweight in kg) / (Age in Days x FCR)] x 100. Target EPEF > 380 in elite broiler operations.",
                        "Chicken Egg Incubation Parameters: Total 21 days; Setter phase (Days 1–18): 37.5°C (99.5°F), 55–60% RH, eggs turned hourly through 90°; Hatcher phase (Days 18–21): 36.8°C (98.2°F), 70–75% RH (high moisture prevents chick drying out upon pipping), turning stopped.",
                        "Layer Photoperiod Lighting Rule: Never decrease daylength during the egg production cycle. Provide 16 hours of light (natural + artificial) at 10–20 lux to stimulate hypothalamic GnRH and pituitary LH surges driving daily ovulation."
                    ),
                    detailedElaboration = "Commercial poultry production represents the most efficient conversion of feed grain into high-quality animal protein.\n\nBrooding Management & Thermal Comfort: Day-old chicks are poikilothermic (unable to physiologically regulate internal body temperature until 10–14 days of age). Brooder temperature must start at 32°C–34°C at chick level during Week 1 and step down by 3°C each week until ambient room temperature (21°C) is reached at Week 4. Chick distribution beneath brooder lamps is the ultimate behavioral thermometer: 1) Chicks huddled tightly under the lamp indicate chilling; 2) Chicks panting at pen perimeters indicate overheating; 3) Chicks huddled on one side indicate drafts; 4) Evenly spaced, actively chirping and feeding chicks confirm optimal thermal comfort.\n\nPhase Feeding in Broilers: 1) Pre-Starter (Days 0–10): High crude protein (23%), high digestible lysine (1.25%), easily digestible extruded crumble to stimulate rapid intestinal villi elongation; 2) Starter/Grower (Days 11–24): 21% CP, balanced for amino acids to build skeletal frame and muscle mass; 3) Finisher (Days 25–35): 19% CP with higher metabolizable energy (3,200 kcal/kg ME) utilizing added vegetable oil to drive rapid abdominal and breast muscle finishing.\n\nCommercial Layer Management: Pullets are reared under a declining or constant 8–9 hour light regimen to prevent premature sexual maturity before skeletal frames are built. At 18–19 weeks (or when body weight reaches 1.45–1.50 kg), photostimulation is initiated by stepping light up to 14, then 16 hours/day. Peak egg production (>92–95% Hen-Day) occurs at 28–32 weeks.",
                    biologicalAndChemicalDetails = listOf(
                        "Oviposition Timeline (25–26 Hours): Infundibulum (fertilization, 15 min) -> Magnum (thick albumen synthesis, 3 hrs) -> Isthmus (inner/outer shell membranes, 1.25 hrs) -> Shell Gland / Uterus (calcium carbonate calcite shell deposition, 20 hrs) -> Vagina (cuticle bloom, oviposition).",
                        "Medullary Bone Calcium Resorption: High-producing laying hens mobilize up to 30–40% of their eggshell calcium from specialized woven medullary bone in long bones during dark night hours, requiring coarse particulate limestone (2–4 mm calcite grit) in evening rations."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "Day-Old Chick Placement & Crop Fill Assessment: Pre-heat poultry house to 32°C for 24 hours prior to chick delivery so litter floor is warm (30°C floor temp). Place chicks on paper feed sheets with fresh chick starter crumbs and clean warm water containing 5% glucose and electrolytes. Check Crop Fill on 100 sample chicks: At 8 hours post-placement, >80% of chicks must have full, soft, pliable crops; at 24 hours post-placement, >95% must have full crops. Hard crops indicate insufficient water; empty crops indicate feed line failure.",
                        "Commercial Egg Incubation Candling Protocol: Candle eggs on Day 10 of incubation using an LED egg candler. Infertile eggs appear completely clear (no embryo, 'clears'); dead embryos display a distinct dark 'blood ring'; living viable embryos exhibit a dark spider-like central eye with branching red blood vessels radiating into the yolk."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Ascites (Water Belly) in Broilers: Fluid accumulation in peritoneal cavity, cyanotic purple combs, gasping, and sudden death occurring in fast-growing broilers exposed to cold temperatures or inadequate house ventilation (hypoxia drives right ventricular hypertrophy).",
                        "Cage Layer Fatigue / Rickets: Paralyzed laying hens lying on sides in cages, rubbery soft beaks and ribs, and thinned fragile eggshells, caused by negative calcium balance or vitamin D3 deficiency."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: A broiler producer places 10,000 day-old chicks. At 35 days of age, 9,650 live broilers are marketed with a total live flock weight of 21,230 kg. The flock consumed a total of 32,500 kg of commercial feed over the 35 days. Calculate: 1) Livability percentage; 2) Average body weight per broiler (kg); 3) Feed Conversion Ratio (FCR); and 4) European Production Efficiency Factor (EPEF / PEF).\nSolution:\n1) Livability (%) = (9,650 / 10,000) x 100 = 96.50% (Mortality = 3.5%).\n2) Average Body Weight = 21,230 kg / 9,650 birds = 2.20 kg/bird.\n3) Feed Conversion Ratio (FCR) = Total Feed / Total Weight Gain = 32,500 kg / 21,230 kg = 1.53.\n4) EPEF = [(Livability % x Average Weight in kg) / (Age in Days x FCR)] x 100\nEPEF = [(96.50 x 2.20) / (35 x 1.53)] x 100 = [212.30 / 53.55] x 100 = 396.45.\nEvaluation: An EPEF score of 396 indicates outstanding, world-class broiler flock management and feed efficiency (benchmark target > 380)."
                    ),
                    commonExamQuestions = listOf(
                        "Calculate Feed Conversion Ratio (FCR) and European Production Efficiency Factor (EPEF) given broiler flock production records.",
                        "Explain why decreasing daylength during the commercial egg laying cycle causes a catastrophic collapse in egg production.",
                        "Detail the physical parameters (temperature, humidity, ventilation, turning) required for chicken egg incubation from Day 1 to 21."
                    ),
                    practicalTakeaway = "Conduct chick crop fill checks 8 and 24 hours after arrival: failure of chicks to find feed and water within 24 hours permanently stunts gastrointestinal development and impairs lifetime FCR.",
                    authoritativeTextbookRef = "Commercial Chicken Meat and Egg Production (Bell & Weaver, Ch. 12, 18 & 24); Poultry Nutrition (Leeson & Summers)"
                )
            ),
            examFocusPoints = listOf(
                "FCR and European Production Efficiency Factor (EPEF) mathematical calculations",
                "Brooding temperature step-down schedule (32°C down to 21°C) and chick behavior",
                "Incubation parameters (Setter Days 1-18 vs. Hatcher Days 18-21)",
                "Hen-Day egg production (%) vs. Hen-Housed egg production (%)",
                "Layer photoperiod lighting regimens (never decrease light in lay)"
            ),
            practicalFieldApplications = listOf(
                "Performing 100-chick crop fill assessments at 8 and 24 hours post-placement",
                "Operating tunnel ventilation static pressure gauges to maintain 0.05–0.10 inches of water"
            ),
            recommendedTextbookIds = listOf("book_bell_commercial_chicken", "book_leeson_poultry_nutrition")
        ),
        CurriculumTopic(
            id = "animal_sci_swine",
            pillar = CurriculumPillarType.ANIMAL_SCIENCE,
            code = "3.g",
            title = "Swine Production",
            summary = "Gilt selection, sow breeding and gestation management, farrowing crate management, neonatal piglet processing, and grower-finisher feeding regimens.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "as_swine_lifecycle",
                    code = "i",
                    title = "Breeding Herd, Farrowing & Piglet Processing",
                    coreDefinition = "The comprehensive reproductive, nutritional, and health management of commercial swine herds (Sus domesticus) from artificial insemination to market slaughter.",
                    keyMechanismsAndFormulas = listOf(
                        "Gestation Length: 3 months, 3 weeks, 3 days (114 days average, normal range 112–116 days).",
                        "The Lordosis Reflex (Back-Pressure Test): In the presence of a mature boar emitting 5α-androstenone pheromones in saliva, an estrous sow exhibits rigid immobility, cocked ears, and stands rock-solid when downward back pressure is applied by the herdsman.",
                        "Neonatal Piglet Iron Anemia: Sows' milk supplies only ~1 mg of iron per day, while rapidly growing piglets require ~7 mg/day. Without exogenous iron injection within 72 hours, hemoglobin drops from 12 g/dL to <5 g/dL, causing fatal microcytic hypochromic nutritional anemia.",
                        "Average Daily Gain (ADG): ADG (g/day) = (Final Weight - Initial Weight) / Days on Feed. Target in commercial grower-finishers = 750–900 g/day with FCR 2.4–2.7."
                    ),
                    detailedElaboration = "Commercial swine production operates on tight generational cycles requiring strict biosecurity and environmental control.\n\nBreeding Herd Management: Gilts are selected at 130–140 kg liveweight (at their 2nd or 3rd natural estrus) with at least 14 well-spaced, functional teats. Artificial insemination is performed twice (12 and 24 hours after detection of standing heat) using spirette or foam-tip catheters inserted past the cervical ridges into the uterine body.\n\nFarrowing House Management: Sows are moved into clean, disinfected farrowing crates 5–7 days prior to their 114-day due date. Farrowing crates reduce piglet crushing mortality by the sow (the leading cause of pre-weaning death) from >25% to <6%. The farrowing room microclimate requires dual thermal zones: 1) Sow zone maintained at 18–20°C (sows suffer heat stress and lactation failure above 22°C); 2) Piglet creep zone equipped with infrared heat lamps or heated floor mats maintained at 32–34°C.\n\nNeonatal Piglet Processing (First 72 Hours): 1) Colostrum Ingestion: Neonatal piglets must nurse within 2 hours; intestinal macromolecular antibody closure occurs within 24–36 hours; 2) Umbilical Cord Care: Tie cord 3 cm from body, trim, and dip in 7% tincture of iodine; 3) Needle Teeth Clipping/Grinding: Blunt the tips of 8 sharp needle teeth (3rd incisors and canines) to prevent sow udder lacerations; 4) Iron Dextran Injection: Administer 200 mg iron dextran deep intramuscularly in the neck muscle; 5) Tail Docking: Dock tail leaving 2 cm to prevent vicious tail biting; 6) Castration of market males.",
                    biologicalAndChemicalDetails = listOf(
                        "Halothane (RYR1) Gene: Point mutation in the ryanodine receptor calcium-channel gene; homozygous recessive pigs (nn) exhibit extreme susceptibility to Porcine Stress Syndrome (PSS), malignant hyperthermia during transport, and pale soft exudative (PSE) meat.",
                        "Creep Feeding Bioenergetics: Introducing highly palatable, lactose- and extruded-cereal-based creep feed from Day 7 stimulates endogenous secretion of pancreatic amylase, maltase, and pepsin, facilitating smooth weaning at 21–28 days."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "Intrauterine Artificial Insemination (IUAI) Protocol in Sows: Clean sow vulva with dry paper towel. Introduce guide catheter into vagina at an upward 45° angle until locked into cervix. Pass inner flexible catheter through guide catheter, penetrating through the cervix 15–20 cm directly into the uterine body. Attach semen pouch containing 1.0–1.5 billion sperm in 40–50 mL extender; allow semen to enter by gravity/uterine suction without squeezing. Withdraw gently.",
                        "Piglet Iron Dextran Intramuscular Injection Protocol: Hold piglet by hind legs. Disinfect skin behind the ear in the neck muscle with 70% alcohol. Using a sterile 20-gauge, 1/2-inch needle, inject exactly 200 mg (1.0 or 2.0 mL depending on concentration) deep into the neck muscle. Never inject in the ham/rump (causes permanent iron tattooing and abscesses that condemn valuable commercial carcass hams)."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Piglet Anemia (Thumps): Pallor of skin, ears, and mucous membranes, rough hair coat, lethargy, and spasmodic jerking of diaphragm muscles ('thumps') during mild exertion.",
                        "Swine Dysentery: Mucohemorrhagic diarrhea containing flecks of bright red blood and greyish mucus, caused by Brachyspira hyodysenteriae colonizing the cecum and spiral colon."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: A commercial swine farrow-to-finish facility weans 120 pigs at an average body weight of 6.5 kg at 24 days of age. The batch is fed through nursery, grower, and finisher phases until marketed at an average slaughter weight of 105.0 kg over a total post-weaning feeding period of 125 days. The total feed consumed by the 120 pigs during this period is 30,732 kg. Calculate: 1) Total liveweight gain per pig (kg); 2) Average Daily Gain (ADG in g/day); and 3) Lifetime post-weaning Feed Conversion Ratio (FCR).\nSolution:\n1) Gain per pig = Market Weight - Weaning Weight = 105.0 kg - 6.5 kg = 98.5 kg/pig.\nTotal batch liveweight gain = 98.5 kg x 120 pigs = 11,820 kg.\n2) Average Daily Gain (ADG) = Total Gain per pig / Days = 98.5 kg / 125 days = 0.788 kg/day = 788 g/day.\n3) Feed Conversion Ratio (FCR) = Total Feed Consumed / Total Batch Weight Gain = 30,732 kg / 11,820 kg = 2.60.\nEvaluation: An ADG of 788 g/day and FCR of 2.60 reflect superior commercial performance."
                    ),
                    commonExamQuestions = listOf(
                        "Explain the physiological basis of nutritional anemia in neonatal piglets and describe how it is prevented in commercial swine production.",
                        "Describe the lordosis reflex in estrous sows and explain how boar exposure facilitates standing heat detection.",
                        "Why must iron injections never be administered into the ham muscle of market piglets?"
                    ),
                    practicalTakeaway = "Always inject 200 mg iron dextran into the neck muscle of piglets within their first 72 hours of life: sows' milk contains virtually no iron, and untreated fast-growing piglets develop lethal nutritional anemia.",
                    authoritativeTextbookRef = "The Science and Practice of Pig Production (Whittemore); Diseases of Swine (Zimmerman, Karriker, Ramirez et al.)"
                )
            ),
            examFocusPoints = listOf(
                "Swine 114-day gestation period (3-3-3 rule) and lordosis reflex detection",
                "Physiological basis and protocols for 200 mg iron dextran injection",
                "Farrowing crate thermal microclimates (sow 18°C vs. piglet creep 32°C)",
                "Average Daily Gain (ADG) and grower-finisher FCR calculations"
            ),
            practicalFieldApplications = listOf(
                "Detecting standing heat in sows using the boar-exposure back-pressure test",
                "Performing piglet processing: needle teeth grinding, tail docking, and neck iron injections"
            ),
            recommendedTextbookIds = listOf("book_whittemore_science_practice_pig", "book_ensminger_animal_science")
        ),
        CurriculumTopic(
            id = "animal_sci_ruminants",
            pillar = CurriculumPillarType.ANIMAL_SCIENCE,
            code = "3.h",
            title = "Ruminant Production",
            summary = "Management of small and large ruminants: (i) Goat/Caprine, (ii) Sheep/Ovine, (iii) Cattle/Bovine, and (iv) Carabao/Water Buffalo production systems.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "as_caprine_goat",
                    code = "i",
                    title = "Goat / Caprine Production",
                    coreDefinition = "The breeding, nutrition, and husbandry of meat, dairy, and dual-purpose goats (Capra hircus), focusing on browsing ecology and targeted parasite management.",
                    keyMechanismsAndFormulas = listOf(
                        "FAMACHA Anemia Eye-Scoring System: Clinical assessment of conjunctival mucous membrane coloration to evaluate Haemonchus contortus blood-loss anemia on a 1 to 5 scale (1 = Optimal red, non-anemic; 2 = Acceptable pink-red; 3 = Borderline pink; 4 = Pale, anemic; 5 = Severe white, critical anemia). Deworm only scores 4 and 5.",
                        "Refugia Concept in Anthelmintic Stewardship: Proportion of worm population unexposed to anthelmintics (in untreated animals and on pasture); maintains susceptible wild-type alleles to dilute out drug-resistant genes.",
                        "Tannin-Binding Proline-Rich Salivary Proteins: Anatomical adaptation allowing goats to consume high-tannin acacia browse (>5% condensed tannins) without precipitating digestive enzymes."
                    ),
                    detailedElaboration = "Goats are specialized bipedal browsers capable of standing on hind legs to browse vertical woody shrubs up to 2 meters. Their mobile, prehensile upper lips and narrow muzzles allow meticulous selection of nutrient-dense leaves, shoot tips, and bark.\n\nBreeds & Enterprise Specialization: 1) Meat Breeds: Boer (heavy muscling, white body with red head), Savanna, Galla/Boran; 2) Dairy Breeds: Saanen (high volume milk), Toggenburg, Anglo-Nubian (high butterfat, dual-purpose). Goat milk contains smaller fat globules (average 3.2 µm vs. 4.5 µm in cow milk) and lacks agglutinin, forming a soft, easily digestible curd with high concentrations of medium-chain triglycerides (caproic, caprylic, capric acids) suitable for infants and lactose-sensitive consumers.\n\nParasite Control & Haemonchus contortus: Haemonchus contortus (the barber pole worm) is a blood-feeding abomasal nematode. A single female worm sucks 0.05 mL of blood per day and lays 5,000–10,000 eggs/day. Heavy burdens cause catastrophic blood-loss anemia, submandibular edema ('bottle jaw'), and death without diarrhea. Blanket flock deworming has triggered widespread multi-drug anthelmintic resistance; targeted selective treatment (TST) via the FAMACHA system preserves drug longevity.",
                    biologicalAndChemicalDetails = listOf(
                        "Caprine Copper Tolerance: Goats have higher dietary copper requirements (10–25 ppm Cu) and tolerance than sheep, metabolizing copper similarly to cattle.",
                        "Polled / Intersex Genetic Linkage: The dominant gene for hornlessness (Polled, P) is closely linked to a recessive gene causing female pseudohermaphroditism/intersexuality in homozygous polled does (PP); therefore, commercial breeders never mate polled x polled."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "FAMACHA Diagnostic Field Scoring Protocol: Examine goats in natural sunlight. Push upper eyelid down with thumb; pull lower eyelid down with other thumb; gently press eyeball in slightly to pop out the inner mucous membrane of the lower eyelid. Compare conjunctival color to the official 5-color laminated FAMACHA chart. Deworm immediately with effective anthelmintic (e.g., albendazole or levamisole) if score is 4 or 5. Repeat flock checks every 2–3 weeks during wet seasons.",
                        "Colostrum Administration in Neonatal Kids: Ensure kid nurses within 2 hours of birth. Feed 10% of kid birthweight in colostrum (approx. 300–400 mL) divided across 3–4 feedings during the first 24 hours. Dip navel in 7% iodine."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Submandibular Edema ('Bottle Jaw'): Fluctuating, fluid-filled swelling beneath the jaw caused by severe hypoproteinemia/hypoalbuminemia induced by blood-sucking Haemonchus contortus worms.",
                        "Contagious Ecthyma (Orf / Sore Mouth): Zoonotic parapoxvirus causing proliferative, scabby, crusted pustules around lips, nostrils, and eyelids of kids and teats of does."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: A caprine producer checks a flock of 250 meat goats using the FAMACHA eye-scoring chart. The results are: Score 1 = 60 does; Score 2 = 110 does; Score 3 = 55 does; Score 4 = 20 does; Score 5 = 5 does. 1) Calculate the percentage of the flock requiring deworming under Targeted Selective Treatment (TST); and 2) Calculate the refugia percentage of the parasite population preserved from anthelmintic selection pressure.\nSolution:\n1) Under TST, only animals scoring 4 and 5 are dewormed: Animals treated = 20 + 5 = 25 does.\nPercentage of flock treated = (25 / 250) x 100 = 10.0%.\n2) Refugia preserved: Animals left untreated (Scores 1, 2, and 3) harbor susceptible worms = 60 + 110 + 55 = 225 does.\nRefugia percentage = (225 / 250) x 100 = 90.0% of the flock's internal parasites are preserved in refugia, effectively preventing drug resistance development."
                    ),
                    commonExamQuestions = listOf(
                        "Explain how the FAMACHA eye color chart operates and detail why maintaining parasite refugia prevents anthelmintic resistance.",
                        "Describe the morphological and behavioral differences between sheep (grazers) and goats (browsers).",
                        "Explain why breeding two homozygous polled (hornless) dairy goats produces intersex female offspring."
                    ),
                    practicalTakeaway = "Deworm only goats scoring 4 or 5 on the FAMACHA chart: treating the entire herd wipes out susceptible parasites and accelerates multi-drug resistant superworms.",
                    authoritativeTextbookRef = "Goat Medicine (Smith & Sherman); Animal Science and Industry (Ensminger & Parker, Ch. 23)"
                ),
                CurriculumSubtopic(
                    id = "as_ovine_sheep",
                    code = "ii",
                    title = "Sheep / Ovine Production",
                    coreDefinition = "The production of domestic sheep (Ovis aries) for meat, wool, and milk, utilizing selective grazing, flocking instincts, and maternal flushing.",
                    keyMechanismsAndFormulas = listOf(
                        "Nutritional Flushing of Ewes: Increasing dietary energy intake (feeding 250–400g grain/ewe/day or moving to lush fresh pasture) 2–3 weeks prior to ram turnout; elevates ovulation rate by 15–25% and reduces early embryonic mortality.",
                        "Extreme Copper Toxicity Sensitivity: Sheep absorb copper with extreme efficiency but lack effective hepatic excretion mechanisms; dietary copper levels exceeding 15–20 ppm cause chronic hepatic accumulation followed by sudden, fatal intravascular hemolytic crisis.",
                        "Wool Micron Count & Spinning Count: 1 Micron = 0.001 mm. Fine wool (Merino: <19.5 µm); Medium wool (Corriedale: 20–26 µm); Coarse wool (>30 µm). Lower micron count indicates superior softness and textile value."
                    ),
                    detailedElaboration = "Sheep are selective grazers equipped with a split upper lip (philtrum) that allows them to bite vegetation exceedingly close to the ground.\n\nBreeds & Climate Adaptation: 1) Tropical Hair Sheep (Dorper, Red Maasai, Blackhead Persian, Katahdin): Shed their coats naturally, require no shearing, and possess high genetic resistance to gastrointestinal parasites; 2) Wool Sheep (Merino, Corriedale, Romney): Grown in temperate or highland zones for fine fiber.\n\nCopper Toxicity Hazard: Sheep are uniquely susceptible to copper toxicosis. In other species (cattle, pigs), excess dietary copper is excreted into bile; in sheep, copper binds to liver cells. When liver storage capacity is exceeded or when acute stress occurs, copper bursts into the bloodstream, precipitating massive intravascular hemolysis. Hemoglobin precipitates in kidney tubules (gunmetal kidneys), causing profound jaundice and death within 24–48 hours. Never feed cattle mineral supplements or swine feeds to sheep.\n\nRoutine Flock Management: 1) Tail Docking: Performed within 2 weeks of birth to prevent flystrike (myiasis by Lucilia sericata blowflies attracted to fecal-soiled wool); 2) Crutching: Shearing wool from around the tail and perineum prior to lambing and summer fly season; 3) Foot Trimming & Footbaths: Trimming overgrown hooves to combat Foot Rot caused by synergistic infection of Fusobacterium necrophorum and Dichelobacter nodosus.",
                    biologicalAndChemicalDetails = listOf(
                        "Enterotoxemia (Pulpy Kidney Disease): Overeating disease in fast-growing lambs fed high-grain rations; Clostridium perfringens type D proliferates in the small intestine, producing lethal epsilon toxin that increases vascular permeability, causing rapid kidney autolysis and death.",
                        "Molybdenum-Sulfur-Copper Antagonism: Tetrathiomolybdate complexes formed in the rumen from dietary molybdenum and sulfate bind copper, forming insoluble copper thiomolybdate that prevents toxic copper absorption."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "Flushing Ewe Protocol Prior to Breeding Season: 21 days before introducing fertile rams, assess Body Condition Score (BCS) on all ewes. Separate ewes with BCS < 2.5 (1–5 scale). Supplement with 300g cracked maize or barley plus 50g soybean meal per head per day, or turn onto fresh irrigated clover/lucerne pasture. Continue flushing through the first 2 weeks of mating: stimulates follicular wave maturation and multiple ovulations.",
                        "Foot Rot Treatment & Zinc Sulfate Footbath Protocol: Pare affected hooves with sharp shears to expose necrotic tissue beneath the hoof horn. Walk flock through a 6-meter concrete footbath containing 10% zinc sulfate (ZnSO4) solution with 0.2% sodium lauryl sulfate surfactant. Mandate 10 minutes stand time in solution; hold flock on clean dry concrete for 1 hour to allow chemical drying."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Acute Copper Toxicity Signs: Sudden onset of profound yellow jaundice (icterus) in sclera and gums, dark red to black urine ('port-wine' hemoglobinuria), and postmortem finding of characteristic metallic dark 'gunmetal kidneys'.",
                        "Flystrike (Myiasis): Restless tail twitching, dark wet wool patches emitting a foul putrid odor, and thousands of active blowfly maggots eating living subcutaneous tissue beneath fleece."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: A commercial sheep flock of 200 breeding ewes achieves a 145% lamb crop born (145 lambs born per 100 ewes mated). At weaning (90 days), 270 lambs survive. Calculate: 1) Total number of lambs born; 2) Pre-weaning lamb mortality percentage; and 3) Weaning percentage (Lambs Weaned per Ewe Mated %).\nSolution:\n1) Lambs Born = 200 ewes x (145 / 100) = 290 lambs born.\n2) Lamb Mortality: Dead lambs = 290 born - 270 weaned = 20 lambs.\nMortality (%) = (20 / 290) x 100 = 6.90%.\n3) Weaning Percentage = (270 lambs weaned / 200 ewes mated) x 100 = 135.0%."
                    ),
                    commonExamQuestions = listOf(
                        "Explain the biochemical and physiological rationale for flushing ewes prior to the breeding season.",
                        "Detail the mechanism of chronic copper toxicity in sheep and explain why cattle mineral pre-mixes are fatal to sheep.",
                        "Describe the etiology and management of contagious Foot Rot in sheep herds."
                    ),
                    practicalTakeaway = "Never feed cattle mineral salt blocks or pig rations to sheep: sheep accumulate copper in the liver and die swiftly from massive intravascular hemolytic crisis.",
                    authoritativeTextbookRef = "The Meat Goat and Sheep Handbook (Ensminger); Diseases of Sheep (Aitken)"
                )
            ),
            examFocusPoints = listOf(
                "Nutritional flushing timeline, energy feeding, and ovulation increases",
                "Copper toxicity threshold (<15–20 ppm) and 'gunmetal kidney' pathology",
                "Enterotoxemia (pulpy kidney) Clostridium perfringens type D etiology",
                "Lambing percentage and pre-weaning mortality calculations"
            ),
            practicalFieldApplications = listOf(
                "Operating 10% zinc sulfate footbaths for contagious ovine foot rot",
                "Evaluating body condition scoring (BCS 1 to 5) across lumbar transverse processes"
            ),
            recommendedTextbookIds = listOf("book_ensminger_animal_science", "book_radostits_veterinary_medicine")
        ),
        CurriculumTopic(
            id = "animal_sci_bovine",
            pillar = CurriculumPillarType.ANIMAL_SCIENCE,
            code = "3.h.iii",
            title = "Cattle / Bovine Production",
            summary = "Dairy (Bos taurus) and beef (Bos indicus / Bos taurus) herd management, transition cow metabolic diseases, calf rearing, and silage nutrition.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "as_bovine_cattle",
                    code = "iii",
                    title = "Cattle / Bovine Production",
                    coreDefinition = "The breeding, rearing, transition metabolic management, and feeding of specialized dairy and beef cattle herds for milk and meat production.",
                    keyMechanismsAndFormulas = listOf(
                        "The 10% Colostrum Rule: Calves are born agammaglobulinemic (zero circulating antibodies). Must receive 10% of their birth body weight (3.5–4.0 Liters for Holstein calf) of clean, high-quality colostrum (IgG > 50 g/L or Brix > 22%) within the first 2 to 4 hours of life. Intestinal macromolecular pinocytosis closes completely by 24 hours.",
                        "Transition Cow Metabolic Window (-3 weeks to +3 weeks post-calving): Onset of copious lactation creates severe Negative Energy Balance (NEB). Mobilization of non-esterified fatty acids (NEFAs) from adipose tissue exceeds hepatic oxidative capacity, leading to ketone bodies (β-hydroxybutyrate, acetoacetate) and hepatic lipidosis (fatty liver).",
                        "Milk Fever (Parturient Hypocalcemia): Sudden calcium draw into colostrum (20–30g in hours) drops blood ionic Ca2+ below 1.5 mmol/L (normal >2.1 mmol/L). Loss of neuromuscular acetylcholine release causes flaccid paralysis (downer cow)."
                    ),
                    detailedElaboration = "Cattle production is categorized into commercial dairy and beef production systems.\n\nTransition Cow Biology: The transition period (21 days pre-calving to 21 days post-calving) dictates herd health and profitability. Pre-calving dry matter intake drops by 30% right when fetal demand peaks. Post-calving, daily milk production surges faster than dry matter intake can rise. Cows mobilize body fat reserves as NEFAs. If liver uptake exceeds peroxisomal and mitochondrial oxidation, NEFAs are re-esterified into triglycerides; because bovine liver is deficient in exporting very low-density lipoproteins (VLDL), triglycerides accumulate in hepatocytes (fatty liver). Simultaneously, acetyl-CoA is shunted into ketogenesis, causing clinical Ketosis (sweet acetone breath, anorexia, rapid body condition score loss).\n\nCalf Rearing & Rumen Development: Neonatal calves possess an esophageal groove (reticular groove) that shunts milk directly from the esophagus past the reticulorumen into the abomasum upon reflex stimulation by suckling. Liquid milk does not develop the rumen; feeding dry, textured calf starter grain (rich in starch) generates butyrate and propionate upon microbial fermentation, which potently stimulates the growth and vascularization of rumen papillae. Calves can be weaned when consuming 1.0–1.2 kg of starter grain daily.",
                    biologicalAndChemicalDetails = listOf(
                        "DCAD (Dietary Cation-Anion Difference) Diet: Formulating pre-partum rations with negative DCAD [DCAD = (Na+ + K+) - (Cl- + S2-)] using anionic salts (ammonium chloride, magnesium sulfate) to induce mild metabolic acidosis (urine pH 6.0–6.5). Acidosis sensitizes parathyroid hormone (PTH) bone receptors, rapidly mobilizing bone calcium to prevent milk fever upon calving.",
                        "Tick-Borne Diseases in Tropical Cattle: East Coast Fever (Theileria parva transmitted by Rhipicephalus appendiculatus ticks; causing 'turning sickness' and pulmonary edema); Anaplasmosis; Babesiosis (Redwater)."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "Brix Refractometer Colostrum Quality Testing: Place 2 drops of fresh maternal colostrum on optical or digital Brix refractometer prism. Reading > 22% Brix indicates superior colostrum with > 50 g/L IgG; reading < 18% indicates poor quality that should be discarded or fed to older calves. Feed using a sanitized esophageal tube feeder if calf refuses to nurse.",
                        "Emergency Treatment of Bovine Milk Fever (Hypocalcemia): Warm 500 mL of 23% Calcium Borogluconate solution to body temperature (38°C). Administer slowly via intravenous jugular catheter over 15 to 20 minutes while auscultating the heart: if heart rate spikes or becomes arrhythmic, stop infusion immediately to prevent fatal cardiac arrest. Administer second 500 mL bottle subcutaneously behind the shoulder."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Parturient Hypocalcemia (Milk Fever) Stages: Stage 1: Hypersensitive, wobbly gait, ear twitching. Stage 2 (Sternally recumbent): S-shaped neck curvature, cold extremities, dry muzzle, dilated pupils. Stage 3 (Comatose): Lateral recumbency, severe bloat, flaccid paralysis.",
                        "Ketosis Signs: Depressed appetite (rejects grain but eats hay), distinct sweet chloroform/acetone smell on breath and milk, rapid loss of >1.0 point of BCS, and milk ketone dipstick turning purple (>100 µmol/L BHB)."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: A dairy herd nutritionist formulates a close-up dry cow ration. The elemental dietary analysis on a dry matter basis is: Sodium (Na) = 0.15%, Potassium (K) = 1.20%, Chlorine (Cl) = 0.85%, and Sulfur (S) = 0.22%. Using the standard veterinary DCAD formula in mEq/100g DM: DCAD = [(%Na/0.023) + (%K/0.039)] - [(%Cl/0.0355) + (%S/0.016)]. Calculate the current DCAD and evaluate if it prevents milk fever.\nSolution:\n1) Calculate cation milliequivalents: Na = 0.15 / 0.023 = +6.52 mEq; K = 1.20 / 0.039 = +30.77 mEq. Total Cations = 6.52 + 30.77 = +37.29 mEq/100g DM.\n2) Calculate anion milliequivalents: Cl = 0.85 / 0.0355 = -23.94 mEq; S = 0.22 / 0.016 = -13.75 mEq. Total Anions = 23.94 + 13.75 = -37.69 mEq/100g DM.\n3) DCAD = Cations - Anions = +37.29 - 37.69 = -0.40 mEq/100g DM.\nEvaluation: The DCAD is slightly negative (-0.4 mEq/100g), which is close to the ideal target of -5 to -10 mEq/100g DM, successfully inducing mild metabolic acidosis to stimulate PTH bone calcium resorption and avert clinical milk fever."
                    ),
                    commonExamQuestions = listOf(
                        "Explain why feeding calves liquid milk does not stimulate rumen papillae development, while feeding starter grain does.",
                        "Detail the etiology, clinical stages, and emergency treatment for acute parturient hypocalcemia (milk fever) in dairy cows.",
                        "Explain the metabolic cascade linking Negative Energy Balance (NEB) to hepatic lipidosis and clinical ketosis in high-yielding dairy cows."
                    ),
                    practicalTakeaway = "Always feed newborn calves 4 liters of tested colostrum (>22% Brix) within 2 hours of life: delayed colostrum ingestion causes failure of passive transfer, leaving calves vulnerable to fatal scours and pneumonia.",
                    authoritativeTextbookRef = "Dairy Cattle Production (Bath et al.); Veterinary Medicine (Radostits et al., Ch. on Metabolic Diseases)"
                )
            ),
            examFocusPoints = listOf(
                "The 10% colostrum rule and timing of intestinal closure (24 hrs)",
                "Transition cow negative energy balance, ketosis, and fatty liver syndrome",
                "Milk fever etiology, DCAD diet mechanics, and IV calcium borogluconate protocol",
                "Calf rumen papillae development via butyrate/propionate fermentation"
            ),
            practicalFieldApplications = listOf(
                "Testing maternal colostrum quality using optical Brix refractometers",
                "Administering slow intravenous calcium borogluconate with cardiac auscultation"
            ),
            recommendedTextbookIds = listOf("book_radostits_veterinary_medicine", "book_ensminger_animal_science")
        ),
        CurriculumTopic(
            id = "animal_sci_carabao",
            pillar = CurriculumPillarType.ANIMAL_SCIENCE,
            code = "3.h.iv",
            title = "Carabao / Water Buffalo Production",
            summary = "Husbandry, physiological thermoregulation, dairy and draft power utilization of Swamp and Riverine Water Buffalo (Bubalus bubalis).",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "as_carabao_buffalo",
                    code = "iv",
                    title = "Carabao / Water Buffalo Production",
                    coreDefinition = "The breeding, physiological management, draft power utilization, and high-fat dairy production of the domestic water buffalo (Bubalus bubalis).",
                    keyMechanismsAndFormulas = listOf(
                        "Subspecies Classification & Cytogenetics: 1) Swamp Buffalo (Carabao): 2n = 48 chromosomes; stocky build, crescent-shaped backward-swept horns, light grey neck chevron bands; primary purpose is draft power and meat; 2) Riverine Buffalo: 2n = 50 chromosomes; larger frame, tightly coiled horns (Murrah, Nili-Ravi); primary purpose is high-volume dairy and meat. Hybridization yields fertile 2n = 49 crossbreds.",
                        "Carabao Milk Nutritional Superiority: Contains 7.0–9.0% butterfat (almost double bovine milk), 4.5% crude protein, and 18–20% total solids. Pure white color results from complete enzymatic cleavage of β-carotene into colorless vitamin A in the intestinal mucosa.",
                        "Thermoregulatory Sweat Gland Density: Water buffalo skin possesses only 100–200 sweat glands/cm² (approx. 1/6th the density of Bos indicus cattle) with thick black epidermal melanin. Consequently, mud/water wallowing is physiologically essential to prevent fatal hyperthermia."
                    ),
                    detailedElaboration = "The water buffalo (Bubalus bubalis) is the premier multi-purpose draft and dairy animal of tropical wetland agro-ecosystems.\n\nThermoregulation & Wallowing Ecology: Because carabaos have sparse sweat glands and dark pigmented skin that absorbs intense solar radiation, evaporative sweating accounts for only a fraction of heat loss. When worked in flooded rice paddies under hot tropical sun, internal body temperature, respiration rate, and pulse spike rapidly. Wallowing in muddy pools coats the skin with a protective layer of wet mud: as the mud crust slowly dries over 3 to 5 hours, it provides sustained evaporative cooling and forms a physical barrier against blood-sucking flies and biting mosquitoes.\n\nDraft Power Mechanics: A mature carabao bull (500–600 kg) exerts a continuous draft pull equivalent to 10–12% of its body weight (50–65 kg draft force) at a plowing speed of 2.5–3.5 km/h for 4 to 6 hours daily. Their wide, spreading hooves and flexible pastern joints prevent them from sinking deeply into soft, sticky wetland clay soils where tractors become hopelessly mired.\n\nGenetic Upgrading Programs: Native swamp carabaos produce only 1.5–2.5 liters of milk/day. National breeding programs (e.g., Philippine Carabao Center) artificially inseminate native does (2n=48) with frozen semen from purebred dairy Riverine Murrah or Nili-Ravi bulls (2n=50). The resulting F1 crossbreds (2n=49) are fully fertile, retain full draft hardiness and heat tolerance, and produce 6.0–10.0 liters of milk/day, catalyzing rural village dairy cooperatives for mozzarella and native cheese production.",
                    biologicalAndChemicalDetails = listOf(
                        "Rumen Digestive Superiority: Buffalo rumen microbes exhibit higher cellulolytic activity and higher volatile fatty acid (VFA) production rates on low-quality, high-lignin crop residues (rice straw, sugarcane bagasse) than cattle, attributed to higher populations of Ruminococcus albus and lower rumen turnover rates.",
                        "Silent Heat & Reproduction: Carabaos exhibit quiet/silent estrus with subtle behavioral signs; estrus lasts 18–24 hours, and gestation is longer than cattle (310–330 days)."
                    ),
                    stepByStepFieldProtocols = listOf(
                        "Carabao Draft Working Schedule & Wallowing Routine: In tropical rice production, work working carabaos early in the morning (6:00 AM to 10:00 AM) and late in the afternoon (3:30 PM to 6:00 PM). Never work carabaos during peak solar irradiance (11:00 AM to 2:00 PM). Provide 1 hour of continuous muddy pond wallowing or hose sprinkler cooling immediately following morning work to restore core body temperature.",
                        "Artificial Insemination of Carabaos (Overcoming Silent Heat): Because carabaos display weak standing heat signs, utilize teaser bulls with chin-ball markers to identify cycling females, or implement fixed-time AI (Ovsynch protocol: GnRH on Day 0, PGF2α on Day 7, GnRH on Day 9, AI at 16 hrs post-second GnRH) to achieve 45–55% first-service conception."
                    ),
                    clinicalOrDiagnosticSigns = listOf(
                        "Thermal Distress in Carabaos: Salivation, tongue protrusion, severe panting (respiration rate > 80 breaths/min), and refusal to move or pull when denied wallowing or shade during hot weather.",
                        "Haemorrhagic Septicaemia (HS): Acute, fatal bacterial infection caused by Pasteurella multocida serotype B:2; characterized by high fever, hot painful swelling of the throat and brisket, severe respiratory gasping, and sudden death within 24 hours."
                    ),
                    boardExamWorkedProblems = listOf(
                        "Problem: A smallholder dairy farmer milks 4 upgraded crossbred carabaos producing an average of 7.5 Liters of milk per head daily with 8.0% butterfat and 4.5% protein. A local artisanal dairy purchases carabao milk at $1.50 per Liter to manufacture premium fresh mozzarella cheese (which requires 5.0 Liters of carabao milk per 1.0 kg of cheese, compared to 10.0 Liters of bovine milk). Calculate: 1) Total daily milk volume produced; 2) Daily gross farm revenue; and 3) Total kilograms of mozzarella cheese manufactured daily from this milk.\nSolution:\n1) Total Daily Milk = 4 carabaos x 7.5 L/day = 30.0 Liters/day.\n2) Daily Gross Revenue = 30.0 Liters x $1.50/L = $45.00/day ($1,350/month).\n3) Mozzarella Yield = 30.0 Liters / 5.0 L per kg = 6.0 kg of fresh mozzarella cheese daily."
                    ),
                    commonExamQuestions = listOf(
                        "Contrast Swamp Buffalo and Riverine Buffalo in terms of chromosome number, horn morphology, and primary agricultural purpose.",
                        "Explain the physiological and anatomical reasons why water wallowing is biologically essential for working carabaos.",
                        "Discuss the nutritional characteristics of carabao milk (butterfat, protein, vitamin A) and explain why it yields double the mozzarella cheese of bovine milk."
                    ),
                    practicalTakeaway = "Always provide wallowing ponds or water sprinklers for working carabaos during midday: their low sweat gland density makes them highly vulnerable to fatal hyperthermia if denied evaporative cooling.",
                    authoritativeTextbookRef = "The Water Buffalo (FAO Animal Production and Health Series No. 4); Buffalo Production and Management (Cockrill)"
                )
            ),
            examFocusPoints = listOf(
                "Swamp (2n=48) vs. Riverine (2n=50) water buffalo genetics and crossbreeding (2n=49)",
                "Thermoregulatory physiology: low sweat gland density (1/6th cattle) and mud wallowing",
                "High butterfat milk composition (8%) and mozzarella cheese yield efficiency",
                "Haemorrhagic Septicaemia Pasteurella multocida pathology and vaccination"
            ),
            practicalFieldApplications = listOf(
                "Scheduling work-rest-wallow cycles for draft animals in wetland paddy preparation",
                "Implementing synchronization protocols to overcome silent heat in water buffaloes"
            ),
            recommendedTextbookIds = listOf("book_ensminger_animal_science", "book_fao_buffalo_production")
        )
    )
}
