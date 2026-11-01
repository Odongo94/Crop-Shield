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
                    coreDefinition = "The application of quantitative genetic principles to evaluate and select parent animals for superior progeny performance in meat, milk, egg, and fiber traits.",
                    keyMechanismsAndFormulas = listOf(
                        "Estimated Breeding Value (EBV): EBV = h² x (Individual Phenotypic Record - Contemporary Group Mean).",
                        "Selection Index: I = b1(X1) + b2(X2) + ... + bn(Xn), where weights (b) reflect economic and genetic parameters.",
                        "Crossbreeding Systems: Two-breed rotational, three-breed rotational, and terminal cross (e.g., F1 maternal cross mated to terminal sire to harvest 100% individual and maternal heterosis)."
                    ),
                    detailedElaboration = "Heritability (h²) dictates selection strategy in livestock. Reproductive and fitness traits (litter size, calving interval, fertility) exhibit low heritability (<0.15) and do not respond rapidly to individual phenotypic selection; they benefit most from crossbreeding heterosis and environmental/nutritional management. Production and growth traits (ADG, yearling weight, milk volume) have moderate heritability (0.20–0.40). Carcass traits (backfat thickness, ribeye area) exhibit high heritability (>0.40) and respond swiftly to direct selection. Inbreeding coefficient (F) increases when close relatives are mated, causing inbreeding depression (reduced embryonic survival, congenital defects, and suppressed vigor).",
                    commonExamQuestions = listOf(
                        "Explain why crossbreeding produces substantial improvements in reproductive traits despite low trait heritability.",
                        "Distinguish between Estimated Breeding Value (EBV) and Expected Progeny Difference (EPD).",
                        "Design a three-breed rotational crossbreeding system and calculate the percentage of maximum heterosis maintained at equilibrium."
                    ),
                    practicalTakeaway = "In commercial meat production (beef, swine, sheep), utilize a terminal sire system (e.g., mating F1 Landrace x Large White sows to a Pietrain terminal boar) so all progeny inherit maximum hybrid vigor and muscling."
                )
            ),
            examFocusPoints = listOf(
                "Heritability classifications: reproductive (<0.15), growth (0.20-0.40), carcass (>0.40)",
                "Heterosis (hybrid vigor) calculation and retention in rotational vs. terminal crossbreeding",
                "Sire summary interpretation (EPDs for birth weight, weaning weight, milk)"
            ),
            practicalFieldApplications = listOf(
                "Calculating Estimated Progeny Differences (EPDs) from contemporary group performance records",
                "Establishing ear-notching and RFID electronic tagging systems for pedigree tracking"
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
                    coreDefinition = "The physiological mechanisms governing reproduction, neuroendocrine signaling, milk synthesis, and environmental adaptation in domestic livestock.",
                    keyMechanismsAndFormulas = listOf(
                        "Estrous Cycle Phases: Follicular phase (proestrus, estrus - dominated by Estrogen from Graafian follicle) and Luteal phase (metestrus, diestrus - dominated by Progesterone from Corpus Luteum).",
                        "Luteolysis Cascade: Non-pregnant uterine endometrium secretes PGF2α, which lyses the Corpus Luteum, dropping progesterone and enabling GnRH pulse frequency surge.",
                        "Milk Ejection Reflex: Teat tactile stimulation -> afferent sensory nerve -> hypothalamus -> posterior pituitary release of Oxytocin into bloodstream -> myoepithelial cell contraction around alveoli."
                    ),
                    detailedElaboration = "Hormonal coordination dictates fertility in livestock. Gonadotropin-Releasing Hormone (GnRH) from the hypothalamus stimulates the anterior pituitary to secrete Follicle-Stimulating Hormone (FSH) for antral follicle recruitment and Luteinizing Hormone (LH) for ovulation. Progesterone maintains pregnancy (pro-gestation) and prevents ovulation. Heat stress in dairy cattle (Temperature-Humidity Index THI > 72) elevates body temperature, reduces dry matter intake, impairs oocyte quality, and induces early embryonic mortality.",
                    commonExamQuestions = listOf(
                        "Diagram the hormonal feedback loops governing the 21-day bovine estrous cycle.",
                        "Trace the neuroendocrine reflex pathway of milk letdown from teat stimulation to oxytocin alveolar contraction.",
                        "Explain the physiological mechanism by which PGF2α synchronizes estrus in cycling heifers."
                    ),
                    practicalTakeaway = "Maintain calm handling and clean milking parlor routines: adrenaline release during fear or rough handling causes peripheral vasoconstriction that blocks oxytocin from reaching the udder, halting milk letdown."
                )
            ),
            examFocusPoints = listOf(
                "Hormonal regulation of the estrous cycle (GnRH, FSH, LH, Estrogen, Progesterone, PGF2α)",
                "Milk letdown neuroendocrine reflex and alveolar physiology",
                "Spermatogenesis vs. oogenesis timelines and artificial insemination (AI) timing"
            ),
            practicalFieldApplications = listOf(
                "Accurate estrus detection in cattle: standing heat detection, AM/PM breeding rule for artificial insemination",
                "Calculating Temperature-Humidity Index (THI) and managing evaporative cooling fans and misters"
            ),
            recommendedTextbookIds = listOf("book_hafez_repro_farm_animals", "book_senger_pathways_repro")
        ),
        CurriculumTopic(
            id = "animal_sci_nutrition",
            pillar = CurriculumPillarType.ANIMAL_SCIENCE,
            code = "3.c",
            title = "Animal Nutrition",
            summary = "Digestive anatomy (ruminant vs. non-ruminant), proximate analysis (Weende system), Van Soest fiber fractions, rumen fermentation, volatile fatty acids, and least-cost ration balancing.",
            subtopics = listOf(
                CurriculumSubtopic(
                    id = "as_digestive_nutrition",
                    code = "i",
                    title = "Rumen Fermentation, Proximate Analysis & Pearson Square",
                    coreDefinition = "The digestion, absorption, and metabolic utilization of carbohydrates, proteins, lipids, minerals, and vitamins in ruminant and monogastric animals.",
                    keyMechanismsAndFormulas = listOf(
                        "Volatile Fatty Acids (VFAs) in Rumen: Acetate (C2, fiber digestion, precursor for milk fat), Propionate (C3, starch digestion, gluconeogenic precursor for glucose/lactose), Butyrate (C4, epithelial fuel).",
                        "Weende Proximate Analysis Fractions: Moisture, Crude Protein (CP = %N x 6.25), Ether Extract (EE / Crude Fat), Crude Fiber (CF), Ash (minerals), Nitrogen-Free Extract (NFE / soluble carbohydrates).",
                        "Van Soest Fiber System: NDF (Neutral Detergent Fiber = hemicellulose, cellulose, lignin; predicts voluntary intake); ADF (Acid Detergent Fiber = cellulose, lignin; predicts digestibility).",
                        "Pearson Square Formulation: Balances two ingredients with disparate nutrient concentrations to achieve target ration level."
                    ),
                    detailedElaboration = "Ruminants (cattle, sheep, goats, buffalo) possess a four-compartment stomach (Rumen, Reticulum, Omasum, Abomasum - the true stomach). Millions of symbiotic anaerobic bacteria, protozoa, and fungi ferment structural carbohydrates (cellulose, hemicellulose) into VFAs, which furnish 70–80% of the animal's energy. Rumen microbes also synthesize microbial protein (high biological value) and B-complex vitamins from non-protein nitrogen (NPN, e.g., feed-grade urea). In monogastrics (swine, poultry), digestion is enzymatic and acid-based; dietary amino acid balance (especially lysine and methionine as first-limiting amino acids) must be met strictly through intact proteins or crystalline synthetic amino acids.",
                    commonExamQuestions = listOf(
                        "Explain why ruminants can utilize non-protein nitrogen (NPN) like urea, whereas monogastrics cannot.",
                        "Describe the energetic fate of acetate versus propionate absorbed across the rumen wall.",
                        "Using the Pearson Square, formulate a 16% CP grower ration using Maize (9% CP) and Soybean Meal (44% CP)."
                    ),
                    practicalTakeaway = "When feeding urea to ruminants, limit it to maximum 1% of total dry matter ration (or 1/3 of total dietary nitrogen), and ensure adequate readily fermentable carbohydrates (molasses/grain) are co-fed to prevent lethal ammonia toxicity."
                )
            ),
            examFocusPoints = listOf(
                "Rumen anatomy and microbial VFA synthesis (Acetate:Propionate ratio dynamics)",
                "Proximate analysis (CP = N x 6.25) vs. Van Soest fiber analysis (NDF vs. ADF)",
                "First-limiting amino acids in pigs (lysine) and poultry (methionine)"
            ),
            practicalFieldApplications = listOf(
                "Formulating feed rations using Pearson Square and multi-ingredient spreadsheets",
                "Evaluating total mixed ration (TMR) particle size distribution using the Penn State Particle Separator"
            ),
            recommendedTextbookIds = listOf("book_mcdonald_animal_nutrition", "book_ensminger_feeds_feeding")
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
                    coreDefinition = "The post-harvest handling, food safety inspection, and biochemical processing of livestock carcasses, raw milk, and poultry eggs into commercial consumer products.",
                    keyMechanismsAndFormulas = listOf(
                        "Muscle-to-Meat Conversion & Rigor Mortis: Glycogen stores converted via anaerobic glycolysis to lactic acid; muscle pH drops from 7.0 to ultimate pH 5.4–5.7 as ATP depletes, locking actin and myosin into irreversible actomyosin crossbridges.",
                        "Pale, Soft, Exudative (PSE) vs. Dark, Firm, Dry (DFD): PSE caused by acute preslaughter stress + rapid postmortem pH drop while carcass is hot (<5.4); DFD caused by chronic glycogen exhaustion (ultimate pH > 6.0).",
                        "High-Temperature Short-Time (HTST) Pasteurization: Milk heated to 72°C (161°F) for 15 seconds to destroy Coxiella burnetii and Mycobacterium tuberculosis.",
                        "Haugh Unit for Egg Freshness: HU = 100 x log(H - 1.7W^0.37 + 7.6), where H is albumen height (mm) and W is egg weight (g)."
                    ),
                    detailedElaboration = "Humanitarian slaughtering requires rest in the lairage, antemortem inspection, effective stunning (captive bolt for cattle, electrical waterbath for poultry, CO2 for swine) to induce immediate insensibility, followed by swift exsanguination (bleeding). Meat tenderization occurs during postmortem aging via endogenous proteolytic calpain and cathepsin enzymes. In dairy processing, raw milk undergoes clarofixation, homogenization (pulverizing milk fat globules under high pressure to prevent creaming), and pasteurization. Egg candling illuminates the internal shell contents to detect blood spots, cracks, and air cell depth (Grade AA: air cell ≤ 3 mm).",
                    commonExamQuestions = listOf(
                        "Explain the biochemical etiology of PSE pork and describe preslaughter handling interventions to prevent it.",
                        "Define the parameters of HTST pasteurization and name the target index pathogen it is calibrated to eliminate.",
                        "How is egg interior quality evaluated using the Haugh Unit equation?"
                    ),
                    practicalTakeaway = "Provide animals at least 12–24 hours of rest with access to clean water in lairage prior to slaughter to restore muscle glycogen reserves and eliminate DFD/PSE meat quality defects."
                )
            ),
            examFocusPoints = listOf(
                "Biochemical events of rigor mortis and ultimate pH curve",
                "Etiology and prevention of PSE and DFD meat conditions",
                "Dairy processing standards: HTST pasteurization, homogenization, somatic cell count (SCC) limits"
            ),
            practicalFieldApplications = listOf(
                "Performing California Mastitis Test (CMT) to assess subclinical somatic cell counts at the cow side",
                "Grading shell eggs using candling lamps and micrometers for air cell depth and albumen firmness"
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
                    coreDefinition = "The prevention, clinical diagnosis, epidemiological containment, and immunological protection of farm animals against infectious and non-infectious pathologies.",
                    keyMechanismsAndFormulas = listOf(
                        "Three Lines of Farm Biosecurity: Exclusion (keeping disease off farm), Segregation/Internal Biosecurity (preventing disease spread between pens), and Cleaning/Disinfection.",
                        "Foot-and-Mouth Disease (FMD): Picornavirus (Aphthovirus); cloven-hoofed animals; vesicles/erosions on tongue, dental pad, coronet band; high morbidity, low adult mortality, high economic loss.",
                        "African Swine Fever (ASF): Asfarviridae; large enveloped DNA virus; mortality approaches 100% in naive herds; high fever, cyanosis of ear tips, widespread internal hemorrhages; no commercial vaccine.",
                        "Anthrax (Bacillus anthracis): Zoonotic spore-forming bacterium; sudden death, uncoagulated tarry blood exuding from natural body orifices, absence of rigor mortis. Do NOT open carcass."
                    ),
                    detailedElaboration = "Biosecurity is the most cost-effective veterinary defense. Zoonotic diseases (e.g., Brucellosis, Anthrax, Bovine Tuberculosis, Avian Influenza) pose critical occupational hazards to veterinarians, farmers, and consumers. Contagious Bovine Pleuropneumonia (CBPP) and Contagious Caprine Pleuropneumonia (CCPP) caused by Mycoplasma cause severe fibrinous lung consolidation and 'marbled lung' pathology. Mastitis control follows the National Mastitis Council (NMC) 5-point plan: teat dipping before/after milking, dry cow antibiotic therapy, prompt treatment of clinical cases, culling chronic cows, and regular milking machine maintenance.",
                    commonExamQuestions = listOf(
                        "Why is opening the carcass of an animal suspected of dying from Anthrax strictly forbidden?",
                        "Outline the 5-point mastitis control plan recommended by the National Mastitis Council.",
                        "Differentiate the clinical pathology of African Swine Fever (ASF) from Classical Swine Fever (CSF)."
                    ),
                    practicalTakeaway = "If an animal dies suddenly with tarry uncoagulated blood exuding from the nose, mouth, or anus, immediately suspect Anthrax: do not perform a necropsy; notify veterinary authorities and incinerate or bury carcass 2 meters deep covered in quicklime."
                )
            ),
            examFocusPoints = listOf(
                "Notifiable OIE/WOAH transboundary animal diseases (FMD, ASF, CBPP, Newcastle)",
                "Anthrax clinical pathology, diagnostic blood smear, and biosecurity disposal",
                "Zoonotic transmission pathways of Brucella abortus and Mycobacterium bovis"
            ),
            practicalFieldApplications = listOf(
                "Establishing Danish entry bench systems and tire disinfection footbaths at farm gates",
                "Executing routine herd vaccination calendars (Lumpy Skin Disease, Blackquarter, Anthrax, Newcastle)"
            ),
            recommendedTextbookIds = listOf("book_radostits_veterinary_medicine", "book_blood_vet_handbook")
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
                    coreDefinition = "The commercial production systems for meat chickens (broilers) and table egg-laying hens (layers), including embryonic incubation and flock management.",
                    keyMechanismsAndFormulas = listOf(
                        "Feed Conversion Ratio (FCR): Total Feed Consumed (kg) / Total Live Weight Gain (kg); modern commercial broilers achieve FCR 1.45–1.60 at 35 days (2.0–2.2 kg liveweight).",
                        "Incubation Parameters (Gallus gallus): 21 days total; Days 1–18 at 37.5°C (99.5°F), 55–60% RH, turned every 1–2 hours; Days 18–21 (hatcher) at 36.8°C, 70–75% RH, turning stopped.",
                        "Layer Lighting Program: Never decrease daylength during the laying cycle; provide 16 hours of light (natural + artificial) at 10–20 lux to stimulate LH/FSH ovulatory surges."
                    ),
                    detailedElaboration = "Brooding is the most critical phase in poultry rearing: day-old chicks are poikilothermic (unable to regulate body temperature until 10–14 days old). Brooder temperature must start at 32°C–35°C at chick level during week 1 and step down by ~3°C each week until ambient temperature (21°C) is reached. Flock behavior indicates comfort: chicks huddling under the heat source indicate chilling; panting at periphery indicates overheating; evenly distributed, active feeding chicks indicate optimal brooding thermal comfort.",
                    commonExamQuestions = listOf(
                        "Calculate the FCR for a flock of 10,000 broilers that consumed 38,000 kg of feed and achieved an average liveweight of 2.3 kg with 3% mortality.",
                        "Why must daylength never be decreased during the commercial egg production phase?",
                        "State the optimal physical conditions (temperature, humidity, egg turning) for chicken egg incubation."
                    ),
                    practicalTakeaway = "Always check chick crop fill 8 hours and 24 hours post-placement: 80% of chicks should have full, soft, pliable crops at 8 hours, and >95% at 24 hours, ensuring proper feed and water intake."
                )
            ),
            examFocusPoints = listOf(
                "Broiler brooding temperature schedule and behavior indicators",
                "Hen-day egg production (%) vs. Hen-housed egg production (%)",
                "Poultry biosecurity and vaccination against Newcastle Disease and Infectious Bursal Disease (Gumboro)"
            ),
            practicalFieldApplications = listOf(
                "Calculating European Production Efficiency Factor (EPEF / PEF) for broiler flocks",
                "Operating automated poultry tunnel ventilation systems to prevent heat stress"
            ),
            recommendedTextbookIds = listOf("book_leeson_poultry_nutrition", "book_bell_commercial_chicken")
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
                    coreDefinition = "The complete reproductive and rearing cycle of commercial domestic pigs (Sus domesticus) from artificial insemination to market slaughter.",
                    keyMechanismsAndFormulas = listOf(
                        "Swine Gestation Period: 3 months, 3 weeks, 3 days (~114 days, range 112–116 days).",
                        "Piglet Iron Injection: 200 mg iron dextran administered intramuscularly within 3 days of life to prevent microcytic hypochromic anemia (sow milk provides only ~1 mg/day; piglet requires ~7 mg/day).",
                        "Average Daily Gain (ADG): ADG (g/day) = (Final Body Weight - Initial Body Weight) / Days on Feed; target 750–900 g/day in grower-finishers."
                    ),
                    detailedElaboration = "Sows exhibit an estrous cycle of 21 days with estrus lasting 48–72 hours. Ovulation occurs approximately 36–40 hours from estrus onset; artificial insemination is performed twice (12 and 24 hours after detection of standing heat). Farrowing crates reduce piglet crushing mortality by the sow. Neonatal processing within the first 72 hours includes: colostrum intake (>250 g/piglet), needle teeth clipping (or grinding), umbilical cord disinfection with 7% iodine tincture, iron dextran injection, tail docking, and castrating male market pigs.",
                    commonExamQuestions = listOf(
                        "Why do neonatal piglets inevitably develop anemia unless administered supplemental iron within the first week?",
                        "State the standard gestation length of pigs and describe the lordosis reflex used in estrus detection.",
                        "List the five key neonatal processing procedures conducted on piglets during the first 72 hours of life."
                    ),
                    practicalTakeaway = "Maintain farrowing crate heat lamps at 32°C for neonatal piglets while keeping the sow area at 18°C–20°C: sows suffer severe heat stress and lactation failure above 22°C."
                )
            ),
            examFocusPoints = listOf(
                "Lordosis reflex (back pressure test) and standing heat signs in sows",
                "Physiological basis for neonatal piglet iron deficiency anemia",
                "Phase feeding in swine: starter, grower, and finisher amino acid specifications"
            ),
            practicalFieldApplications = listOf(
                "Administering intrauterine artificial insemination (IUAI) using spirette catheters",
                "Evaluating backfat thickness at P2 position using A-mode ultrasound for gilt replacement"
            ),
            recommendedTextbookIds = listOf("book_whittemore_science_practice_pig", "book_diseases_swine_zimmerman")
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
                    coreDefinition = "Breeding, nutrition, and husbandry of meat, dairy, and dual-purpose goats (Capra hircus), with emphasis on browsing ecology and parasite resilience.",
                    keyMechanismsAndFormulas = listOf(
                        "FAMACHA Eye Color Chart: Scores conjunctival mucous membrane pallor from 1 (optimal red) to 5 (severely pale/anemic) to selectively drench for Haemonchus contortus.",
                        "Bipedal Browsing: Anatomical adaptation allowing goats to browse vertical woody vegetation up to 2m, with high salivary proline-rich proteins to neutralize dietary tannins."
                    ),
                    detailedElaboration = "Goats are versatile browsers capable of consuming shrubbery and aromatic forbs unpalatable to cattle. Dairy breeds (Saanen, Toggenburg, Anglo-Nubian) produce easily digestible milk with small fat globules and high short-chain fatty acids. Meat breeds (Boer, Savanna, Galla) exhibit rapid growth rates. Haemonchus contortus (barber pole worm) is the most devastating blood-sucking abomasal parasite; blanket herd deworming triggers rapid anthelmintic resistance, making targeted selective treatment (TST) via FAMACHA scoring mandatory.",
                    commonExamQuestions = listOf(
                        "Explain how the FAMACHA system slows the development of anthelmintic resistance in caprine herds.",
                        "Compare the grazing and browsing behavioral differences between cattle and goats."
                    ),
                    practicalTakeaway = "Deworm only goats scoring 4 or 5 on the FAMACHA chart; leaving animals in score 1 and 2 untreated preserves a refugia population of unselected, susceptible parasites."
                ),
                CurriculumSubtopic(
                    id = "as_ovine_sheep",
                    code = "ii",
                    title = "Sheep / Ovine Production",
                    coreDefinition = "The production of sheep (Ovis aries) for wool, meat (mutton/lamb), and milk, utilizing selective grazing behavior and flocking instinct.",
                    keyMechanismsAndFormulas = listOf(
                        "Flushing Ewes: Increasing dietary energy density 2–3 weeks prior to breeding to elevate ovulation rate and twinning percentage by 15–25%.",
                        "Copper Sensitivity: Sheep accumulate copper in hepatic tissue; dietary Cu levels exceeding 15–20 ppm cause acute hemolytic crisis and jaundice."
                    ),
                    detailedElaboration = "Sheep are selective grazers with split upper lips capable of grazing very close to the soil surface. Meat breeds include hair sheep adapted to tropical environments (Dorper, Blackhead Persian, Katahdin) that do not require shearing, and wool breeds (Merino, Corriedale). Management involves docking (hygiene against flystrike/myiasis), castration, and hoof trimming to combat foot rot (Dichelobacter nodosus). Never feed cattle or pig mineral pre-mixes to sheep due to lethal copper toxicity.",
                    commonExamQuestions = listOf(
                        "Explain the nutritional rationale and physiological mechanism of flushing ewes prior to the breeding season.",
                        "Why are sheep uniquely vulnerable to dietary copper toxicity compared to cattle or goats?"
                    ),
                    practicalTakeaway = "Never use cattle mineral blocks or poultry manure in sheep feed rations: the high copper content causes chronic liver accumulation followed by massive intravascular hemolysis."
                ),
                CurriculumSubtopic(
                    id = "as_bovine_cattle",
                    code = "iii",
                    title = "Cattle / Bovine Production",
                    coreDefinition = "Commercial management of dairy (Bos taurus) and beef (Bos taurus / Bos indicus) cattle herds for milk and meat production.",
                    keyMechanismsAndFormulas = listOf(
                        "Colostrum Rule: Feed 10% of calf birth weight (approx. 3–4 liters) within the first 6 hours of life to achieve successful passive transfer of IgG antibodies (gut closes at 24 hours).",
                        "Body Condition Scoring (BCS 1–5 scale in dairy): Target 3.0–3.25 at calving; avoid BCS > 3.75 to prevent fatty liver syndrome and ketosis."
                    ),
                    detailedElaboration = "Dairy production relies on Bos taurus breeds (Holstein-Friesian for high volume, Jersey for high butterfat/protein) while tropical beef relies heavily on Bos indicus (Brahman, Boran) possessing heat tolerance, tick resistance, and dewlap skin surface area. The transition period (3 weeks before to 3 weeks after calving) represents the greatest metabolic challenge: cows enter negative energy balance (NEB), mobilizing body fat (NEFAs) to fuel milk synthesis, predisposing them to ketosis, displaced abomasum, and hypocalcemia (milk fever).",
                    commonExamQuestions = listOf(
                        "Explain why colostrum ingestion is time-critical within the first 6 hours of a calf's life.",
                        "Describe the etiology, clinical signs, and emergency treatment for acute bovine hypocalcemia (milk fever)."
                    ),
                    practicalTakeaway = "Administer slow intravenous calcium borogluconate (23%) immediately upon diagnosing downer cows with milk fever, monitoring cardiac rhythm to prevent arrhythmia."
                ),
                CurriculumSubtopic(
                    id = "as_carabao_buffalo",
                    code = "iv",
                    title = "Carabao / Water Buffalo Production",
                    coreDefinition = "Husbandry, breeding, and utilization of the domestic water buffalo (Bubalus bubalis) for draft power, high-fat dairy, and meat in wetland and tropical agro-ecosystems.",
                    keyMechanismsAndFormulas = listOf(
                        "Subspecies Classification: Swamp Buffalo (2n = 48 chromosomes, utilized primarily for draft, stocky build, crescent horns) vs. Riverine Buffalo (2n = 50 chromosomes, dairy specialized e.g., Murrah and Nili-Ravi, curled horns).",
                        "Milk Composition: Water buffalo milk contains 7.0–8.5% butterfat (almost double bovine milk), 4.5% protein, and pure white color due to complete enzymatic conversion of beta-carotene to vitamin A."
                    ),
                    detailedElaboration = "The carabao (swamp buffalo) is the quintessential draft and multipurpose animal of tropical rice farming systems. Water buffalo possess fewer sweat glands per unit skin area (approx. 1/6th that of cattle) and dark pigmented skin; consequently, wallowing in mud or water ponds is an essential physiological thermoregulatory behavior to prevent hyperthermia. Crossbreeding swamp buffalo cows with purebred dairy Murrah bulls (resulting in 2n = 49 fertile crossbreds) dramatically increases milk yields from 1.5–2.0 L/day up to 6–8 L/day while retaining heat and disease tolerance.",
                    commonExamQuestions = listOf(
                        "Contrast Swamp Buffalo and Riverine Buffalo in terms of chromosome number, horns, and primary agricultural purpose.",
                        "Why is wallowing biologically essential for water buffalo working in tropical rice paddies?",
                        "Discuss the nutritional characteristics of carabao milk that make it ideal for mozzarella cheese production."
                    ),
                    practicalTakeaway = "Ensure wallowing pools or water sprinklers are provided for carabaos during hot midday periods (11 AM - 3 PM) to avoid severe thermal distress, reduced feed intake, and drop in working endurance."
                )
            ),
            examFocusPoints = listOf(
                "Colostrum timing and passive IgG antibody absorption window",
                "FAMACHA selective drenching criteria and Haemonchus contortus control",
                "Difference between Swamp (2n=48) and Riverine (2n=50) water buffalo"
            ),
            practicalFieldApplications = listOf(
                "Performing FAMACHA mucous membrane eye scoring on small ruminant flocks",
                "Brix refractometer colostrum quality testing (>22% Brix indicates >50 g/L IgG)"
            ),
            recommendedTextbookIds = listOf("book_ensminger_animal_science", "book_fao_buffalo_production")
        )
    )
}
