package com.example.data.quarantine

import com.example.model.quarantine.PestOrganismGroup
import com.example.model.quarantine.QuarantinePest
import com.example.model.quarantine.QuarantineRegulatoryStatus
import com.example.model.quarantine.QuarantineStageDetail

/**
 * Authoritative International & Kenya Quarantine Pest Catalog.
 * Curated from IPPC ISPM registers, EPPO A1/A2 Priority Lists,
 * KEPHIS Export Quarantine Regulations, and USDA-APHIS 7 CFR 319.
 */
object QuarantinePestCatalog {

    val quarantinePests: List<QuarantinePest> = listOf(
        // ==============================================================
        // 1. THAUMATOTIBIA LEUCOTRETA (False Codling Moth - FCM)
        // ==============================================================
        QuarantinePest(
            id = "qp_fcm",
            commonName = "False Codling Moth (FCM)",
            scientificName = "Thaumatotibia leucotreta (Meyrick)",
            taxonomicGroup = PestOrganismGroup.INSECT_LEPIDOPTERA,
            regulatoryStatus = QuarantineRegulatoryStatus.KEPHIS_EXPORT_ZERO_TOLERANCE,
            quarantineRegionsAndJurisdictions = listOf(
                "European Union (Commission Implementing Regulation EU 2019/2072 Annex II)",
                "United States (USDA-APHIS Regulated Pest List)",
                "Kenya (KEPHIS Mandatory Zero-Tolerance Export Surveillance)",
                "EPPO (A2 List No. 352)"
            ),
            reasonForQuarantineListing = "Zero-tolerance priority quarantine pest on cut roses (Rosa spp.), Capsicum spp. (chilli and sweet peppers), Citrus, and Avocado exported to the EU. A single intercepted live caterpillar results in immediate consignment destruction, phytosanitary audit notification, and threat of an EU trade ban on Kenyan horticulture.",
            biologyAndIdentification = "Adult moth has a wingspan of 15 - 20 mm; mottled brown, grey, black, and orange with a distinct white dot on the forewing and a raised brush of scales on the hind legs of males. Larvae are 12 - 18 mm long, creamy white becoming conspicuously bright pinkish-red when mature, with a dark brown head capsule and two dark pinacula on the 8th abdominal segment.",
            targetCropsAndHosts = listOf(
                "Cut Roses (Rosa spp.) - flower buds and calyxes",
                "Capsicum spp. (Chilli, Bell Pepper, Paprika)",
                "Citrus spp. (Orange, Grapefruit, Lemon, Mandarin)",
                "Avocado (Persea americana - Hass & Fuerte)",
                "Macadamia, Pomegranate, Maize, and Cotton"
            ),
            natureOfAttack = "Females oviposit single translucent scale-like eggs onto fruits or flower calyxes. Upon hatching, the larva bores directly into the fruit pulp or rose calyx within 2 - 4 hours. Once inside, it feeds internally on the flesh and seeds, leaving exit holes plugged with granular frass, inviting secondary rot organisms (Aspergillus, Rhizopus).",
            diagnosticDamageSymptoms = listOf(
                "Pin-hole entry puncture on Capsicum or Citrus fruit with a sunken, discolored brown halo",
                "Internal fruit flesh rot, liquefaction, and accumulation of moist dark frass",
                "Premature fruit coloration and pre-harvest fruit drop",
                "Bored petals and necrotic lesions at the base of cut rose flower buds"
            ),
            lifeCycleStages = listOf(
                QuarantineStageDetail(
                    stageName = "Egg",
                    durationDays = "3 - 8 days",
                    description = "Flattened, oval, scale-like; creamy white with reticulate surface pattern; laid singly on fruit surface.",
                    thermalRequirements = "Develops above 11.9°C; incubation takes 3 days at 30°C.",
                    physicalAppearance = "0.9 mm wide, translucent turning reddish before eclosion."
                ),
                QuarantineStageDetail(
                    stageName = "Larva (5 Instars)",
                    durationDays = "14 - 28 days",
                    description = "Bores internally into fruit tissue. First two instars are creamy white; 4th and 5th instars turn brilliant pinkish-red.",
                    thermalRequirements = "Optimal feeding at 25 - 28°C. Cold sensitive.",
                    physicalAppearance = "15 - 20 mm long, bright pink-red with prominent dark brown prothoracic shield."
                ),
                QuarantineStageDetail(
                    stageName = "Pupa",
                    durationDays = "10 - 20 days",
                    description = "Mature larva drops to soil or litter on a silken thread, spinning an oval silk cocoon incorporated with soil particles.",
                    thermalRequirements = "Pupates in top 2 cm of soil or leaf mulch. Highly resistant to desiccation.",
                    physicalAppearance = "8 - 10 mm long, yellowish-brown turning dark mahogany brown."
                ),
                QuarantineStageDetail(
                    stageName = "Adult Moth",
                    durationDays = "10 - 25 days",
                    description = "Nocturnal flyer; mating occurs 1 - 2 days after emergence; females lay 100 - 400 eggs.",
                    thermalRequirements = "Poor flier (< 100 m/day); dispersion driven by wind and infested fruit transport.",
                    physicalAppearance = "15 - 20 mm wingspan; mottled brownish-grey with prominent white central forewing dot."
                )
            ),
            totalLifeCycleDuration = "30 - 60 days depending on ambient temperature; 4 - 6 overlapping generations per year in warm climates.",
            internationalSpreadPathway = "Commercial passenger luggage carrying fresh fruit, international air-freight consignments of Capsicum, avocado, and cut flowers without certified phytosanitary cold-treatment.",
            preHarvestBiosecurityControls = listOf(
                "Pheromone Monitoring: Deploy delta traps baited with (E)-8-dodecenyl acetate + (Z)-8-dodecenyl acetate (FCM Lure) at 2 traps/ha.",
                "Orchard Sanitation: Collect and deeply bury or drum-cook all fallen and stung fruits twice weekly (destroys 75% of larvae).",
                "Mating Disruption: Deploy Isomate-FCM or SPLAT-FCM pheromone dispensers (100 - 300 dispensers/ha) to prevent mating.",
                "Biological Control: Inundative releases of egg parasitoid Trichogrammatoidea cryptophlebiae and Cryptophlebia leucotreta granulovirus (Cryptovirus / Cryptex)."
            ),
            postHarvestPhytosanitaryTreatments = listOf(
                "Mandatory EU Cold Treatment for Citrus: Continuous pulp temperature of 1.1°C to 2.2°C for 16 - 22 consecutive days.",
                "Packhouse 100% Sorting: Optical grading tables and 200-lux illuminated inspection lines to detect entry pinholes.",
                "Fumigation: Phosphine or Ethyl Formate where registered under strict NPPO supervisory protocols."
            ),
            kephisAndIppcProtocolSummary = "Under KEPHIS FCM Systems Approach, farms must be audited and registered, maintain zero trap catches within 3 weeks of harvest, submit to 100% packing line sample cuts (minimum 200 fruit per batch), and obtain official KEPHIS Phytosanitary Certificates before airport dispatch."
        ),

        // ==============================================================
        // 2. BACTROCERA DORSALIS (Oriental Fruit Fly)
        // ==============================================================
        QuarantinePest(
            id = "qp_bactrocera",
            commonName = "Oriental Fruit Fly",
            scientificName = "Bactrocera dorsalis (Hendel)",
            taxonomicGroup = PestOrganismGroup.INSECT_DIPTERA,
            regulatoryStatus = QuarantineRegulatoryStatus.GLOBAL_INVASIVE_IPPC,
            quarantineRegionsAndJurisdictions = listOf(
                "European Union (Annex II / Quarantine List)",
                "United States (USDA-APHIS 7 CFR 319.56)",
                "Chile (SAG Quarantine)",
                "Japan (MAFF Phytosanitary Quarantine)",
                "Kenya (KEPHIS Regulated Export Pest)"
            ),
            reasonForQuarantineListing = "Highly destructive, invasive polyphagous fruit fly attacking over 400 commercial fruit and vegetable crops. Causes up to 80% loss in mangoes, avocados, and citrus in sub-Saharan Africa. Imposes severe export embargoes from non-infested importing nations.",
            biologyAndIdentification = "Adult fly is 6 - 8 mm long with a 13 - 15 mm wingspan. Vibrant yellow and dark brown thorax with a distinct T-shaped black marking on the third abdominal segment (tergite) and clear wings with a narrow dark costal band. Females have a needle-sharp retractable ovipositor capable of piercing thick fruit peels.",
            targetCropsAndHosts = listOf(
                "Mango (Mangifera indica) - primary host",
                "Avocado (Persea americana)",
                "Citrus (Orange, Mandarin, Grapefruit)",
                "Guava, Papaya, Banana, Passion Fruit",
                "Tomato, Capsicum, and Cucurbits"
            ),
            natureOfAttack = "Female punctures the ripening fruit epidermis using her serrated ovipositor and inserts clutches of 10 - 40 eggs just beneath the skin. Maggots hatch, burrow into the pulp, and feed voraciously, secreting digestive enzymes and bacteria that liquefy the flesh into an acidic, rotten slurry.",
            diagnosticDamageSymptoms = listOf(
                "Depressed oviposition puncture (sting mark) with leaking gum or brown necrotic spot",
                "Soft, water-soaked, rotting zones under the skin that collapse under gentle thumb pressure",
                "Internal pulp infested with hundreds of writhing white maggots",
                "Massive premature fruit drop onto orchard floor"
            ),
            lifeCycleStages = listOf(
                QuarantineStageDetail(
                    stageName = "Egg",
                    durationDays = "1 - 2 days",
                    description = "White, elongated, slightly curved; laid in batches of 10 - 40 eggs beneath fruit epidermis.",
                    thermalRequirements = "Rapid development at 26 - 32°C. Eggs fail below 10°C.",
                    physicalAppearance = "1.2 mm long, elliptical, glistening white."
                ),
                QuarantineStageDetail(
                    stageName = "Larva (Maggot - 3 Instars)",
                    durationDays = "6 - 12 days",
                    description = "Active, cylindrical, legless maggots with dark mouth hooks. Vigorously feeds on pulp and can hop/skip several centimeters when disturbed.",
                    thermalRequirements = "Survives inside fruit pulp between 15°C and 35°C.",
                    physicalAppearance = "10 mm long at maturity; creamy white to light yellow."
                ),
                QuarantineStageDetail(
                    stageName = "Pupa",
                    durationDays = "8 - 14 days",
                    description = "Mature larva drops from fallen fruit and burrows 2 - 5 cm into topsoil to pupate inside a hardened puparium.",
                    thermalRequirements = "Pupal mortality increases sharply if soil is dry (< 20% RH) or saturated.",
                    physicalAppearance = "4 - 5 mm, barrel-shaped, tan to dark brownish-red."
                ),
                QuarantineStageDetail(
                    stageName = "Adult Fly",
                    durationDays = "30 - 90 days",
                    description = "Highly mobile; can fly 20 - 50 km in search of hosts. Females lay 1,200 - 1,500 eggs over their lifetime.",
                    thermalRequirements = "Adult feeding on yeast/sugar required for sexual maturation (takes 8 - 12 days post-emergence).",
                    physicalAppearance = "6 - 8 mm, bright yellow sides, black T-mark on abdomen."
                )
            ),
            totalLifeCycleDuration = "16 - 28 days under tropical conditions; multiple continuous generations year-round.",
            internationalSpreadPathway = "International passenger air travel carrying undeclared fresh tropical fruits; commercial air and sea fruit shipments without post-harvest thermal quarantine treatments.",
            preHarvestBiosecurityControls = listOf(
                "Male Annihilation Technique (MAT): Deploy wooden blocks or mineral boards saturated with Methyl Eugenol (ME) + 5% Malathion at 10 blocks/ha to eliminate all male flies.",
                "Bait Application Technique (BAT): Spot-spray protein hydrolysate bait + Spinosad (e.g. GF-120 / Success Appat) at 5 - 10 liters/ha to attract and kill protein-seeking females.",
                "Orchard Fruit Bagging: Enclose individual mangoes in double-layer breathable paper bags 30 - 40 days before harvest.",
                "Biological Parasitoids: Inundative release of Fopius arisanus (egg-pupal parasitoid) and Diachasmimorpha longicaudata."
            ),
            postHarvestPhytosanitaryTreatments = listOf(
                "Vapor Heat Treatment (VHT): Treat fruit core to 46.5°C to 48°C and hold for 20 - 30 minutes at 95% RH to kill all internal eggs and larvae.",
                "Hot Water Dipping (HWD): Submerge fruit in circulating water at 46.1°C for 65 - 90 minutes depending on fruit weight.",
                "Ionizing Irradiation: Certified irradiation dosage of 150 to 400 Gray for USDA-APHIS compliance."
            ),
            kephisAndIppcProtocolSummary = "All Kenyan export mangoes destined for the EU, Middle East, or US must originate from registered Pest Free Places of Production (PFPP) or undergo mandatory KEPHIS-certified Vapor Heat Treatment with tamper-proof phytosanitary seal certification."
        ),

        // ==============================================================
        // 3. RALSTONIA SOLANACEARUM (Bacterial Wilt / Brown Rot of Potato)
        // ==============================================================
        QuarantinePest(
            id = "qp_ralstonia",
            commonName = "Bacterial Wilt / Brown Rot (Race 3 Biovar 2)",
            scientificName = "Ralstonia solanacearum (Smith) Yabuuchi et al.",
            taxonomicGroup = PestOrganismGroup.BACTERIUM,
            regulatoryStatus = QuarantineRegulatoryStatus.EPPO_A2_REGULATED,
            quarantineRegionsAndJurisdictions = listOf(
                "EPPO (A2 List No. 125 - Strict Quarantine)",
                "European Union (Council Directive 98/57/EC on control of Ralstonia)",
                "United States (USDA Select Agent Program - High Biosecurity Concern)",
                "Kenya (KEPHIS Regulated Seed Potato Pathogen)"
            ),
            reasonForQuarantineListing = "Catastrophic vascular bacterial disease of potatoes, tomatoes, and other Solanaceae. Race 3 Biovar 2 is cold-tolerant (adapted to temperate highlands) and persists in soil and weed hosts (Solanum dulcamara) for decades. Causes 100% field wilt and total rejection of certified seed potatoes.",
            biologyAndIdentification = "Gram-negative, aerobic, non-spore-forming, motile rod-shaped bacterium (0.5 x 1.5 µm). Possesses a Type III Secretion System (T3SS) that injects over 70 effector proteins directly into plant host cells, suppressing plant immunity and generating heavy extracellular polysaccharide (EPS) slime that clogs the vascular xylem vessels.",
            targetCropsAndHosts = listOf(
                "Potato (Solanum tuberosum) - tuber brown rot",
                "Tomato (Solanum lycopersicum) - rapid bacterial wilt",
                "Eggplant (Solanum melongena)",
                "Pepper (Capsicum spp.)",
                "Solanaceous weed reservoirs (Black Nightshade - Solanum nigrum)"
            ),
            natureOfAttack = "Invades plant roots through natural emergence points of lateral roots or cultivation wounding/nematode wounds. Colonizes xylem vessels, multiplies exponentially, and produces dense EPS I slime that blocks water and nutrient translocation, causing sudden, irreversible plant collapse.",
            diagnosticDamageSymptoms = listOf(
                "Rapid green wilting: foliage wilts and dies rapidly while retaining green color (unlike slow yellowing of fungal wilt)",
                "Vascular browning: cutting the lower stem reveals a dark brown ring in the vascular bundle",
                "Bacterial Streaming: suspending a cut stem section in a clear glass of clean water produces milky-white bacterial threads streaming from xylem within 2 minutes",
                "Tuber Eyes Oozing: eyes on infected potato tubers ooze bacterial slime that adheres soil particles ('soil clumps on eyes'); vascular ring of sliced tuber shows brown discoloration that oozes whitish bacterial drops when squeezed"
            ),
            lifeCycleStages = listOf(
                QuarantineStageDetail(
                    stageName = "Soil Survival Phase",
                    durationDays = "Years to decades",
                    description = "Survives saprophytically in soil, deep rhizosphere, crop residues, and asymptomatic weed root zones.",
                    thermalRequirements = "Race 3 Biovar 2 survives in cool soils (12 - 20°C); tropical strains thrive at 28 - 35°C.",
                    physicalAppearance = "Microscopic rods; forms fluidal, irregular colonies on Kelman's TZC agar."
                ),
                QuarantineStageDetail(
                    stageName = "Infection & Colonization",
                    durationDays = "3 - 7 days post-entry",
                    description = "Enters root wounds, traverses cortex, penetrates xylem vessels, and ascends vascular system systemically.",
                    thermalRequirements = "Accelerated by high soil moisture (matric potential > -10 kPa).",
                    physicalAppearance = "High bacterial titers (> 10^8 CFU/g fresh tissue)."
                ),
                QuarantineStageDetail(
                    stageName = "Systemic Wilt & Tuber Rot",
                    durationDays = "7 - 21 days",
                    description = "Vessels become completely obstructed with bacterial mass and host tyloses, starving canopy of transpiration stream.",
                    thermalRequirements = "Rapid visual collapse during warm sunny afternoons.",
                    physicalAppearance = "Vascular rings in tubers turn dark brown and exude whitish bacterial slime."
                )
            ),
            totalLifeCycleDuration = "Continuous bacterial binary fission (doubling time 90 minutes under optimal host conditions).",
            internationalSpreadPathway = "Trade in latently infected seed tubers, movement of infested soil on farm machinery, infected irrigation water from contaminated rivers.",
            preHarvestBiosecurityControls = listOf(
                "Use 100% Certified Disease-Free Seed Tubers with official KEPHIS / NPPO seed certification blue tags.",
                "Strict Farm Sanitation: Disinfect tractor tires, boots, and tools with 10% sodium hypochlorite or quaternary ammonium compounds between fields.",
                "Long Crop Rotation: Minimum 5 to 7-year rotation with non-solanaceous non-hosts (Maize, Sorghum, Grasses, Brassicas).",
                "Eradicate Solanaceous Weeds: Destroy volunteer potatoes and black nightshade (Solanum nigrum) around fields."
            ),
            postHarvestPhytosanitaryTreatments = listOf(
                "Zero Tolerance for Seed Potatoes: Any detection in seed crop lots leads to total de-certification and condemnation.",
                "Immediate Destruction of Infested Tubers: Deep burial with quicklime (CaO) or controlled high-temperature autoclaving/burning.",
                "No Chemical Cure: No systemic bactericide exists that can cure vascular bacterial wilt inside living plant tissues."
            ),
            kephisAndIppcProtocolSummary = "Official KEPHIS seed potato inspection protocol requires mandatory field inspections during flowering, sampling of 200 tubers per hectare for laboratory ELISA and PCR screening, and immediate quarantine quarantine demarcations upon any laboratory confirmation."
        ),

        // ==============================================================
        // 4. GLOBODERA ROSTOCHIENSIS & G. PALLIDA (Potato Cyst Nematodes - PCN)
        // ==============================================================
        QuarantinePest(
            id = "qp_pcn",
            commonName = "Potato Cyst Nematode (PCN) - Golden & Pale",
            scientificName = "Globodera rostochiensis & Globodera pallida",
            taxonomicGroup = PestOrganismGroup.NEMATODE,
            regulatoryStatus = QuarantineRegulatoryStatus.EPPO_A2_REGULATED,
            quarantineRegionsAndJurisdictions = listOf(
                "EPPO (A2 Quarantine List No. 124)",
                "European Union (Council Directive 2007/33/EC on PCN control)",
                "Kenya (KEPHIS Strict National Quarantine & Seed Certification Protocol)",
                "USA & Canada (USDA-APHIS / CFIA Regulated Quarantine Pest)"
            ),
            reasonForQuarantineListing = "Severe soil-borne cyst nematodes causing up to 80% potato yield loss in Kenya (Nyandarua, Meru, Nakuru, Elgeyo Marakwet). Cysts are hardened dead female bodies containing 200 - 500 viable eggs that remain dormant and infective in soil for over 20 to 30 years without a host.",
            biologyAndIdentification = "Microscopic roundworms. Infective second-stage juveniles (J2) are 0.45 mm long. Adult females become swollen and spherical (0.5 - 0.8 mm). In G. rostochiensis, the female turns from white to bright golden-yellow before turning into a hard brown cyst; in G. pallida, the female turns directly from white to cream then brown without a yellow phase.",
            targetCropsAndHosts = listOf(
                "Irish Potato (Solanum tuberosum) - main host",
                "Tomato (Solanum lycopersicum)",
                "Eggplant (Solanum melongena)",
                "Solanaceous weeds (Datura stramonium, Solanum dulcamara)"
            ),
            natureOfAttack = "J2 juveniles hatch from cysts in response to host root exudates (solanoeclepin A), enter roots just behind root tips, migrate intracellularly, and establish permanent feeding syncytia inside the vascular stele. This disrupts water and nutrient uptake, causing root stunting and abnormal root branching.",
            diagnosticDamageSymptoms = listOf(
                "Circular patches of stunted, yellowing, unthrifty potato plants in the field that expand over successive seasons",
                "Foliage shows premature wilting and nutrient deficiency symptoms (purpling, scorched leaf margins) despite adequate fertilization",
                "Root system is severely reduced, stunted, and heavily branched ('bearded root' appearance)",
                "Visible tiny pinhead-sized white, golden-yellow, or brown spheres (cysts) attached to roots when dug up gently during flowering"
            ),
            lifeCycleStages = listOf(
                QuarantineStageDetail(
                    stageName = "Cyst Dormancy Phase",
                    durationDays = "Up to 30 years in soil",
                    description = "Hardened dead female cuticle filled with 200 - 500 eggs. Extremely resistant to drought, frost, and chemical nematicides.",
                    thermalRequirements = "Hatching requires host root diffusates + soil temperature > 10°C.",
                    physicalAppearance = "0.5 - 0.8 mm spherical brown cyst with punctate surface."
                ),
                QuarantineStageDetail(
                    stageName = "Infective J2 Juvenile",
                    durationDays = "1 - 3 weeks",
                    description = "Hatches from cyst, swims in soil water films, locates potato roots via chemotaxis, and invades.",
                    thermalRequirements = "Optimum activity at 15 - 20°C.",
                    physicalAppearance = "0.45 mm slender, transparent vermiform nematode with distinct stylet."
                ),
                QuarantineStageDetail(
                    stageName = "Parasitic Development & Swelling",
                    durationDays = "35 - 50 days",
                    description = "Molts through J3 and J4. Females swell into spheres, rupture root epidermis, exposing their posterior; males remain vermiform and fertilize exposed females.",
                    thermalRequirements = "Takes approx. 40 days at 18°C.",
                    physicalAppearance = "Round white pearls emerging from root surface, turning golden yellow."
                )
            ),
            totalLifeCycleDuration = "50 - 70 days; one generation per potato cropping cycle.",
            internationalSpreadPathway = "Movement of soil adhering to seed potato tubers, unwashed farm implements, harvesting crates, footwear, and livestock traversing infested fields.",
            preHarvestBiosecurityControls = listOf(
                "Mandatory Soil Testing: Soil cores sampled at 1 kg per hectare and extracted using Fenwick can to confirm 0 cysts before planting seed potatoes.",
                "Resistant Potato Varieties: Plant certified varieties with H1 resistance gene against G. rostochiensis (e.g. Shangi is susceptible; Destiny and Markies exhibit strong resistance).",
                "Trap Cropping: Plant Solanum sisymbriifolium (Sticky Nightshade), which stimulates 95% PCN egg hatch but prevents nematode reproduction, starving juveniles.",
                "Biofumigation: Incorporate high-glucosinolate Brassica cover crops (Mustard, Radish) into moist soil to release natural biocidal isothiocyanates."
            ),
            postHarvestPhytosanitaryTreatments = listOf(
                "Mandatory Tuber Washing & Brushing: Complete removal of all adhering soil particles from export or seed tubers.",
                "No Chemical Eradication: Granular nematicides (Oxamyl, Fluopyram) only suppress J2 invasion during early growth and cannot eradicate soil cysts.",
                "Quarantine Soil Containment: Infested seed potato plots are officially quarantined by KEPHIS and prohibited from potato cultivation for a minimum of 6 years."
            ),
            kephisAndIppcProtocolSummary = "Under the Kenya National PCN Management Strategy, all certified seed potato fields must undergo mandatory pre-plant soil cyst extraction by KEPHIS laboratories; zero cysts allowed for certified seed qualification."
        ),

        // ==============================================================
        // 5. TROGODERMA GRANARIUM (Khapra Beetle)
        // ==============================================================
        QuarantinePest(
            id = "qp_khapra",
            commonName = "Khapra Beetle",
            scientificName = "Trogoderma granarium (Everts)",
            taxonomicGroup = PestOrganismGroup.INSECT_COLEOPTERA,
            regulatoryStatus = QuarantineRegulatoryStatus.USDA_APHIS_SELECT,
            quarantineRegionsAndJurisdictions = listOf(
                "United States (USDA-APHIS 7 CFR 319 - Top Quarantine Stored Product Pest)",
                "European Union (Annex II Regulated Quarantine Pest)",
                "Australia (DAFF Strict Biosecurity Quarantine)",
                "Kenya (KEPHIS Port of Entry Grain Inspection Protocol)"
            ),
            reasonForQuarantineListing = "Ranked among the world's 100 worst invasive alien species. Devastating stored grain pest that thrives in extremely dry conditions (< 2% moisture) where other insects starve. Larvae can enter facultative diapause and survive without food inside building cracks and shipping containers for up to 8 years.",
            biologyAndIdentification = "Adult beetle is 1.6 - 3.0 mm long, oval, dark brown to black with faint indistinct reddish-brown markings on elytra and covered in fine hairs. Larvae are 2 - 5 mm, densely hairy with characteristic barbed spear-headed hairs (hastisetae) that detach and cause severe human gastrointestinal irritation and allergic respiratory reactions when consumed in flour.",
            targetCropsAndHosts = listOf(
                "All Stored Cereals (Wheat, Maize, Barley, Rice, Sorghum, Millet)",
                "Oilseeds (Groundnuts, Sesame, Sunflower)",
                "Pulses (Beans, Peas, Lentils, Chickpeas)",
                "Dried Spices, Cocoa Beans, and Animal Feeds"
            ),
            natureOfAttack = "Larvae feed preferentially on the embryo of grain kernels, hollowing them out completely. Infestation produces vast quantities of cast larval skins (exuviae) and irritating hairs that render food grains completely inedible and unfit for human or animal consumption.",
            diagnosticDamageSymptoms = listOf(
                "Accumulation of dense fuzzy masses of cast skins (exuviae) and barbed hairs on the surface of grain bags",
                "Hollowed-out grain kernels reduced to powdery frass and shell fragments",
                "Unpleasant sickening oily odor in grain silos and shipping containers",
                "Severe contamination of shipping containers with diapausing larvae hidden in floorboard seams"
            ),
            lifeCycleStages = listOf(
                QuarantineStageDetail(
                    stageName = "Egg",
                    durationDays = "3 - 14 days",
                    description = "Cylindrical with rounded ends; laid loosely amongst grain dust.",
                    thermalRequirements = "Optimal at 35°C; incubation takes 4 days at 35°C.",
                    physicalAppearance = "0.7 mm, milky white turning yellowish."
                ),
                QuarantineStageDetail(
                    stageName = "Larva (4 - 15 Instars)",
                    durationDays = "30 days (or up to 8 years in diapause)",
                    description = "Extremely resilient. Can enter diapause under starvation or cold; hides in deep timber cracks.",
                    thermalRequirements = "Thrives up to 40°C. Tolerates extreme grain dryness (< 8% moisture).",
                    physicalAppearance = "Yellowish-brown, covered in dense barbed spear hairs (hastisetae)."
                ),
                QuarantineStageDetail(
                    stageName = "Adult Beetle",
                    durationDays = "5 - 12 days",
                    description = "Short-lived; does not feed or fly; mates immediately after emerging from pupal skin inside old larval exuviae.",
                    thermalRequirements = "Cannot fly; dispersion is 100% human-mediated via cargo.",
                    physicalAppearance = "1.6 - 3.0 mm oval dark brown beetle."
                )
            ),
            totalLifeCycleDuration = "26 - 30 days under hot conditions (35°C); prolonged over years in diapause.",
            internationalSpreadPathway = "Used burlap bags, international marine shipping containers, bulk grain cargo vessels, military equipment shipments.",
            preHarvestBiosecurityControls = listOf(
                "Post-Harvest Silo Hygiene: Thorough vacuum cleaning of grain silos and bin cracks before storing new harvest.",
                "Pheromone Trapping: Deploy wall-mounted Trogoderma pitfall and dome traps baited with trogodermal pheromone lure.",
                "Diatomaceous Earth (DE): Mix food-grade amorphous silica dust (1 kg per ton) with dry grain to abrade larval cuticles and cause desiccation."
            ),
            postHarvestPhytosanitaryTreatments = listOf(
                "Mandatory Methyl Bromide Fumigation: USDA-APHIS T302-d protocol (80 g/m³ for 48 hours at 21°C) or Phosphine gas (1.5 - 2.0 g/m³ held for 7 - 10 continuous days).",
                "Thermal Heat Disinfestation: Heat empty silos and sea containers to 60°C and maintain for 6 continuous hours.",
                "Immediate Rejection & Quarantine: Any detection of live or dead Khapra beetle in export/import grain results in immediate refusal of discharge."
            ),
            kephisAndIppcProtocolSummary = "Strict KEPHIS port of entry protocol requires sampling 1% of all imported grain bags, sieving for cast skins and hastisetae hairs, and mandatory container fumigation prior to clearance."
        ),

        // ==============================================================
        // 6. XYLELLA FASTIDIOSA (Olive Quick Decline / Pierce's Disease)
        // ==============================================================
        QuarantinePest(
            id = "qp_xylella",
            commonName = "Xylella / Olive Quick Decline Syndrome",
            scientificName = "Xylella fastidiosa (Wells et al.)",
            taxonomicGroup = PestOrganismGroup.BACTERIUM,
            regulatoryStatus = QuarantineRegulatoryStatus.EPPO_A2_REGULATED,
            quarantineRegionsAndJurisdictions = listOf(
                "European Union (EU Emergency Phytosanitary Measures Regulation EU 2020/1201)",
                "EPPO (A2 List No. 166 - High Priority Quarantine Organism)",
                "United States (California CDFA Quarantine for Pierce's Disease)",
                "Kenya (KEPHIS Regulated Import Pathogen on Propagative Material)"
            ),
            reasonForQuarantineListing = "One of the most dangerous phytopathogenic bacteria on Earth. Has destroyed millions of ancient olive trees in Southern Italy (Apulia), decimated California vineyards (Pierce's disease), and kills almonds, citrus (Citrus Variegated Chlorosis), and ornamentals. Strict zero-tolerance ban on import of host nursery stock.",
            biologyAndIdentification = "Gram-negative, slow-growing, rod-shaped bacterium strictly restricted to the plant xylem vessels and the foregut (cibarium) of xylem-feeding insect vectors. Forms a sticky biofilm that occludes water conduction.",
            targetCropsAndHosts = listOf(
                "Olive (Olea europaea)",
                "Grapevine (Vitis vinifera) - Pierce's Disease",
                "Citrus spp. - Citrus Variegated Chlorosis (CVC)",
                "Almond and Stone Fruits (Prunus spp.)",
                "Ornamentals (Oleander, Lavender, Rosemary, Polygala)"
            ),
            natureOfAttack = "Injected into xylem vessels by sharpshooter leafhoppers (Cicadellidae) and spittlebugs (Aphrophoridae). Bacteria multiply, produce gum and tyloses, and form a continuous biofilm that mechanically suffocates water ascent, producing acute leaf scorch and branch dieback.",
            diagnosticDamageSymptoms = listOf(
                "Marginal leaf scorch: outer leaf edge turns necrotic brown with a chlorotic yellow halo separating dead and green tissue",
                "Rapid branch dieback ('Quick Decline'): entire tree crowns dry out and look burned while leaves cling to branches",
                "Matchstick symptom on grapes: leaf blades drop while petioles remain attached to the shoot cane",
                "Stunted fruit development and irreversible tree mortality within 2 - 3 years"
            ),
            lifeCycleStages = listOf(
                QuarantineStageDetail(
                    stageName = "Insect Vector Colonization",
                    durationDays = "Persists for vector lifespan",
                    description = "Colonizes cuticular lining of the insect foregut; insect vectors remain infective for life without transovarial transmission.",
                    thermalRequirements = "Insects active at temperatures > 15°C.",
                    physicalAppearance = "Xylem-feeding insects (Meadow Spittlebug - Philaenus spumarius)."
                ),
                QuarantineStageDetail(
                    stageName = "Systemic Xylem Occlusion",
                    durationDays = "Months to 2 years",
                    description = "Slow bacterial movement up and down xylem vessels via twitching motility using Type IV pili.",
                    thermalRequirements = "Cold-sensitive; winter freezing can cure vines in northern temperate zones.",
                    physicalAppearance = "Vessels filled with dense bacterial biofilms."
                )
            ),
            totalLifeCycleDuration = "Continuous bacterial multiplication inside plant xylem and insect vector foregut.",
            internationalSpreadPathway = "Trade in asymptomatic ornamental host plants (Polygala myrtifolia, Lavandula), olive nursery stock, and hitchhiking insect vectors.",
            preHarvestBiosecurityControls = listOf(
                "Strict Import Ban: Prohibit unauthorized import of living plants for planting from known Xylella outbreak zones.",
                "Vector Suppression: Control meadow spittlebugs and sharpshooters using mechanical weed mowing in spring to destroy vector nymphs.",
                "Mandatory Molecular Testing: RT-qPCR and LAMP testing of all imported propagative plant cuttings by KEPHIS quarantine laboratories."
            ),
            postHarvestPhytosanitaryTreatments = listOf(
                "Eradication Demarcation Zone: EU protocol requires mandatory destruction of all host plants within a 50-meter radius around any confirmed infected plant.",
                "Hot Water Treatment of Dormant Grapevine Cuttings: Submerge dormant cuttings in hot water at 50°C for 45 minutes to eliminate internal bacteria."
            ),
            kephisAndIppcProtocolSummary = "KEPHIS operates post-entry quarantine screening at Muguga Quarantine Station; all imported Prunus, Olea, and Vitis propagation germplasm must undergo minimum 1-year quarantine isolation with PCR indexing."
        )
    )

    fun getPestById(id: String): QuarantinePest? = quarantinePests.find { it.id == id }

    fun filterPests(query: String): List<QuarantinePest> {
        if (query.isBlank()) return quarantinePests
        return quarantinePests.filter {
            it.commonName.contains(query, ignoreCase = true) ||
            it.scientificName.contains(query, ignoreCase = true) ||
            it.targetCropsAndHosts.any { crop -> crop.contains(query, ignoreCase = true) } ||
            it.quarantineRegionsAndJurisdictions.any { reg -> reg.contains(query, ignoreCase = true) }
        }
    }
}
