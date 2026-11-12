package com.example.data.nuflip

/**
 * Factsheets on vegetable pests, diseases, and physiological disorders
 * from demonstration fields in Acholi sub-region (NUFLIP / JICA / MAAIF Uganda).
 */
data class NuflipFactsheetEntry(
    val id: String,
    val name: String,
    val scientificName: String,
    val category: NuflipCategory, // PEST, DISEASE, PHYSIOLOGICAL_DISORDER
    val hostCrops: List<String>,
    val symptomsAndDamage: String,
    val favorableConditions: String,
    val biologicalFeatures: String,
    val controlMeasures: String,
    val chemicalRotations: List<String>,
    val imageDescription: String
)

enum class NuflipCategory(val displayName: String) {
    PEST("Insect Pest"),
    DISEASE("Plant Disease"),
    PHYSIOLOGICAL_DISORDER("Physiological Disorder")
}

data class IracClassificationEntry(
    val moaGroup: String,
    val chemicalClass: String,
    val activeIngredients: List<String>,
    val modeOfActionDescription: String
)

data class FracClassificationEntry(
    val fracCode: String,
    val chemicalGroup: String,
    val activeIngredients: List<String>,
    val riskLevel: String,
    val applicationTiming: String // Preventive, Curative, Plant Activator
)

object NuflipFieldFactsheetCatalog {

    val iracInsecticideList: List<IracClassificationEntry> = listOf(
        IracClassificationEntry(
            moaGroup = "1A",
            chemicalClass = "Carbamates",
            activeIngredients = listOf("Carbofuran"),
            modeOfActionDescription = "Systemic acetylcholinesterase (AChE) inhibitors."
        ),
        IracClassificationEntry(
            moaGroup = "1B",
            chemicalClass = "Organophosphates",
            activeIngredients = listOf("Acephate", "Chlorpyrifos", "Dichlorvos", "Dimethoate", "Malathion"),
            modeOfActionDescription = "Systemic and contact acetylcholinesterase inhibitors."
        ),
        IracClassificationEntry(
            moaGroup = "4A",
            chemicalClass = "Neonicotinoids",
            activeIngredients = listOf("Acetamiprid", "Imidacloprid", "Thiamethoxam"),
            modeOfActionDescription = "Systemic nicotinic acetylcholine receptor (nAChR) competitive modulators."
        ),
        IracClassificationEntry(
            moaGroup = "6",
            chemicalClass = "Avermectins, Milbemycins",
            activeIngredients = listOf("Abamectin"),
            modeOfActionDescription = "Glutamate-gated chloride channel (GluCl) allosteric modulators."
        ),
        IracClassificationEntry(
            moaGroup = "3A",
            chemicalClass = "Pyrethroids, Pyrethrins",
            activeIngredients = listOf("Alpha-Cypermethrin", "Cypermethrin", "Fenvalerate", "Lambda-cyhalothrin", "Tefluthrin"),
            modeOfActionDescription = "Contact sodium channel modulators causing rapid knockdown."
        )
    )

    val fracFungicideList: List<FracClassificationEntry> = listOf(
        FracClassificationEntry(
            fracCode = "M1",
            chemicalGroup = "Inorganic Copper",
            activeIngredients = listOf("Copper oxychloride", "Copper hydroxide"),
            riskLevel = "Low Risk",
            applicationTiming = "Preventive multi-site contact fungicide"
        ),
        FracClassificationEntry(
            fracCode = "M2",
            chemicalGroup = "Inorganic Sulphur",
            activeIngredients = listOf("Sulphur 80% WDG"),
            riskLevel = "Low Risk",
            applicationTiming = "Preventive multi-site contact against powdery mildew & rusts"
        ),
        FracClassificationEntry(
            fracCode = "M3",
            chemicalGroup = "Dithiocarbamates",
            activeIngredients = listOf("Mancozeb 800 g/kg"),
            riskLevel = "Low Risk",
            applicationTiming = "Preventive broad-spectrum multi-site barrier"
        ),
        FracClassificationEntry(
            fracCode = "Bio",
            chemicalGroup = "Microbial Biocontrol Agents",
            activeIngredients = listOf("Trichoderma viride", "Paecilomyces lilacinus", "Pseudomonas fluorescens"),
            riskLevel = "Resistance not known",
            applicationTiming = "Preventive root and seedling bio-inoculant"
        ),
        FracClassificationEntry(
            fracCode = "FRAC 1",
            chemicalGroup = "Methyl Benzimidazole Carbamates (MBC)",
            activeIngredients = listOf("Thiophanate methyl", "Carbendazim"),
            riskLevel = "High Risk",
            applicationTiming = "Curative systemic beta-tubulin assembly inhibitor"
        ),
        FracClassificationEntry(
            fracCode = "FRAC 3",
            chemicalGroup = "Demethylation Inhibitors (DMI / Triazoles)",
            activeIngredients = listOf("Hexaconazole", "Difenoconazole", "Tebuconazole"),
            riskLevel = "Medium Risk",
            applicationTiming = "Curative sterol biosynthesis inhibitor in fungal cell membranes"
        ),
        FracClassificationEntry(
            fracCode = "FRAC 4",
            chemicalGroup = "Phenylamides (PA)",
            activeIngredients = listOf("Metalaxyl", "Mefenoxam"),
            riskLevel = "High Risk",
            applicationTiming = "Curative systemic RNA polymerase I inhibitor for oomycetes"
        ),
        FracClassificationEntry(
            fracCode = "FRAC 11",
            chemicalGroup = "Quinone Outside Inhibitors (QoI / Strobilurins)",
            activeIngredients = listOf("Azoxystrobin", "Pyraclostrobin"),
            riskLevel = "High Risk",
            applicationTiming = "Curative and protective mitochondrial respiration inhibitor"
        ),
        FracClassificationEntry(
            fracCode = "FRAC 27",
            chemicalGroup = "Cyanoacetamide-oximes",
            activeIngredients = listOf("Cymoxanil"),
            riskLevel = "Low - Medium Risk",
            applicationTiming = "Curative penetrant with kickback activity on blights"
        ),
        FracClassificationEntry(
            fracCode = "Activator",
            chemicalGroup = "Plant Activator / Bactericide",
            activeIngredients = listOf("Bronopol (2-Bromo-2-Nitropropane-1,3-Diol)"),
            riskLevel = "Unknown",
            applicationTiming = "Induces systemic resistance against bacterial canker, spot, and wilt"
        )
    )

    val factsheets: List<NuflipFactsheetEntry> = listOf(
        // PESTS
        NuflipFactsheetEntry(
            id = "nuflip_field_crickets",
            name = "Field Crickets",
            scientificName = "Gryllus spp.",
            category = NuflipCategory.PEST,
            hostCrops = listOf("Tomato", "Eggplant", "Green Pepper", "Watermelon", "Onion"),
            symptomsAndDamage = "Feeds on or cuts off sprouts, cotyledons, and young seedlings at the soil line (cut hypocotyl). Hides in underground tunnels and chews roots.",
            favorableConditions = "Dry nursery beds with unmulched soil or heavy straw shelters.",
            biologicalFeatures = "Nocturnal soil-dwelling crickets that burrow during daylight and emerge at dusk.",
            controlMeasures = "Spray insecticides on the nursery bed as repellent. Mancozeb fungicide has repellent side effects. Protect nursery beds with insect netting or boxed frames >20 cm in height.",
            chemicalRotations = listOf("Carbofuran 5% G in soil", "Alpha-Cypermethrin 100 EC", "Chlorpyrifos soil drench"),
            imageDescription = "Adult field cricket on rice husk mulch and severed vegetable hypocotyls."
        ),

        NuflipFactsheetEntry(
            id = "nuflip_variegated_grasshopper",
            name = "Variegated Grasshopper",
            scientificName = "Zonocerus variegatus",
            category = NuflipCategory.PEST,
            hostCrops = listOf("Tomato", "Eggplant", "Green Pepper", "Watermelon", "Cabbage", "Cassava"),
            symptomsAndDamage = "Feeds voraciously on leaves and stems of both young and mature vegetable crops, leaving bare stalks. Nymphs feed in dense aggregations.",
            favorableConditions = "Dry sunny spells following early rains when nymphs hatch from communal egg pods.",
            biologicalFeatures = "Gregarious aposematic grasshoppers with yellow, black, and orange warning coloration. Emits noxious pyrrolizidine alkaloid foam when disturbed.",
            controlMeasures = "Spray insecticides in early morning before ambient air temperature rises when grasshoppers are lethargic. Spray border vegetation and surrounding bush.",
            chemicalRotations = listOf("Lambda-cyhalothrin 50 g/L", "Cypermethrin 100 EC", "Dimethoate 400 EC"),
            imageDescription = "Nymphs aggregated on eggplant leaves and large solitary adults chewing stems."
        ),

        NuflipFactsheetEntry(
            id = "nuflip_thrips",
            name = "Thrips (Onion & Melon Thrips)",
            scientificName = "Thrips tabaci, Thrips palmi",
            category = NuflipCategory.PEST,
            hostCrops = listOf("Onion", "Tomato", "Eggplant", "Green Pepper", "Watermelon", "Cabbage"),
            symptomsAndDamage = "White dots and silvery splashed patterns on young leaf and fruit surfaces. Flower rasping causes blossom drop and fruit scarring/malformation.",
            favorableConditions = "High temperatures (28°C–32°C) accompanied by dry weather and light rain.",
            biologicalFeatures = "Minute slender fringed-wing insects that feed in protected crevices, flower buds, and leaf sheaths.",
            controlMeasures = "Spray insecticides 2–3 times at 7- to 10-day intervals. Silver reflective plastic mulches disorient and paralyze thrips flight.",
            chemicalRotations = listOf("Imidacloprid 200 SL", "Acetamiprid 200 SP", "Abamectin 18 g/L", "Spinetoram"),
            imageDescription = "Splashed white silvering on tomato/onion leaves and corky scarring on young tomato fruits."
        ),

        NuflipFactsheetEntry(
            id = "nuflip_eggplant_lace_bugs",
            name = "Eggplant Lace Bug",
            scientificName = "Urentius hystricellus",
            category = NuflipCategory.PEST,
            hostCrops = listOf("Eggplant", "Tomato", "Green Pepper", "Cucurbit vegetables"),
            symptomsAndDamage = "Leaves develop yellow patches, turn wavy, and are heavily soiled with black tar-like excreta droplets. Severe stunting occurs in nursery beds.",
            favorableConditions = "Hot and dry conditions; prolonged drought stress.",
            biologicalFeatures = "Adults are delicate yellowish-brown lace-like insects; nymphs are spiny and cluster on the undersides of leaves.",
            controlMeasures = "Spray systemic insecticides in the nursery bed upon first sighting of spiny nymphs.",
            chemicalRotations = listOf("Imidacloprid 200 SL", "Thiamethoxam 250 WDG", "Acetamiprid 200 SP"),
            imageDescription = "Spiny lace bug nymph and bleached chlorotic yellow patches on eggplant foliage."
        ),

        NuflipFactsheetEntry(
            id = "nuflip_ladybird_herbivorous",
            name = "Herbivorous Ladybird Beetle (Epilachna)",
            scientificName = "Epilachna spp.",
            category = NuflipCategory.PEST,
            hostCrops = listOf("Eggplant", "Potato", "Tomato", "Solanaceous vegetables"),
            symptomsAndDamage = "Adults and spiny larvae feed on the undersides of leaves creating distinct step-like skeletonized feeding marks.",
            favorableConditions = "Warm humid cropping periods with overlapping Solanaceous crops.",
            biologicalFeatures = "Unlike predatory ladybugs (which have lustrous, shiny elytra), Epilachna ladybirds have dull, lusterless elytra covered in fine hairs.",
            controlMeasures = "Hand-pick swarming adults in morning. Spray contact insecticides.",
            chemicalRotations = listOf("Lambda-cyhalothrin 50 g/L", "Alpha-Cypermethrin 100 EC", "Azadirachtin (Neem)"),
            imageDescription = "Dull spotted beetle feeding on leaf underside with characteristic step-like windowpane lines."
        ),

        NuflipFactsheetEntry(
            id = "nuflip_blister_beetles",
            name = "Blister Beetles",
            scientificName = "Epicauta spp. (Meloidae)",
            category = NuflipCategory.PEST,
            hostCrops = listOf("Eggplant", "Potato", "Groundnut", "Soybean", "Green Pepper"),
            symptomsAndDamage = "Adults aggregate rapidly in large swarms, devouring flowers and leaves in hours. Can completely destroy floral flushes.",
            favorableConditions = "Hot and dry conditions. High grasshopper populations (since blister beetle larvae feed on subterranean grasshopper egg pods).",
            biologicalFeatures = "Elongated soft-bodied beetles containing blistering cantharidin chemical defense. Do not crush against bare skin.",
            controlMeasures = "Spray contact insecticides on border plants. Hand-collect with gloves into soapy water.",
            chemicalRotations = listOf("Lambda-cyhalothrin 50 g/L", "Cypermethrin 100 EC"),
            imageDescription = "Yellow and black striped beetles clustered on eggplant flowers."
        ),

        NuflipFactsheetEntry(
            id = "nuflip_gall_midges",
            name = "Gall Midges",
            scientificName = "Family Cecidomyiidae",
            category = NuflipCategory.PEST,
            hostCrops = listOf("Eggplant", "Tomato", "Pepper", "Solanaceae crops"),
            symptomsAndDamage = "Abnormal puff galls covered with dense velvety trichomes develop on leaves, stems, and flowers. Severely infested plant parts become thickened, twisted, and stunted.",
            favorableConditions = "High humidity and continuous Solanaceous cropping in tropical lowlands.",
            biologicalFeatures = "Tiny fragile flies that lay eggs inside plant meristematic tissues; larvae feed inside the central chamber of the puff gall.",
            controlMeasures = "Remove and incinerate galls promptly. Spray systemic insecticides preventively before larvae enter tissue.",
            chemicalRotations = listOf("Thiamethoxam 250 WDG", "Dimethoate 400 EC"),
            imageDescription = "Velvety swollen puff galls on eggplant stems and developing floral buds."
        ),

        NuflipFactsheetEntry(
            id = "nuflip_fruit_flies",
            name = "Fruit Flies (Melon Flies)",
            scientificName = "Bactrocera spp.",
            category = NuflipCategory.PEST,
            hostCrops = listOf("Watermelon", "Cucurbit vegetables", "Mango", "Tomato"),
            symptomsAndDamage = "Females puncture soft young fruits to lay eggs. Maggots feed inside the flesh causing water-soaked soft rot, fruit premature drop, and fruit decay.",
            favorableConditions = "Active in early morning and dusk; rest beneath dense foliage during hot midday.",
            biologicalFeatures = "Tephritid fruit flies with patterned wings. Attracted to cue-lure and methyl eugenol pheromones.",
            controlMeasures = "Cover young fruits with paper bags after flowering. Install male annihilation pheromone traps every 10 meters. Spray systemic insecticides on border weeds where adults roost at midday.",
            chemicalRotations = listOf("Malathion 50% EC bait spray", "Spinosad bait", "Chlorpyrifos border spray"),
            imageDescription = "Larvae feeding inside watermelon pulp and paper bags covering immature melons."
        ),

        NuflipFactsheetEntry(
            id = "nuflip_diamondback_moth",
            name = "Diamondback Moth (DBM)",
            scientificName = "Plutella xylostella",
            category = NuflipCategory.PEST,
            hostCrops = listOf("Cabbage", "Kale", "Broccoli", "Cauliflower"),
            symptomsAndDamage = "Larvae feed on the lower leaf surfaces leaving windowpane feeding scars. Disrupts cabbage head formation and fouls heads with green frass.",
            favorableConditions = "Dry weather; heavy rain barrages and dislodges larvae naturally.",
            biologicalFeatures = "Small grey moth with diamond patterns when wings are folded. Larvae wriggle backward and drop on silken threads when disturbed.",
            controlMeasures = "Strict insecticide rotation across different IRAC MoA classes is vital because DBM develops chemical resistance rapidly. Conserve ground spiders and parasitic Diadegma wasps.",
            chemicalRotations = listOf("Bacillus thuringiensis (Bt)", "Emamectin Benzoate 50 g/kg", "Chlorantraniliprole 200 SC", "Abamectin 18 g/L"),
            imageDescription = "Windowpaned cabbage leaves, light green larvae, and pupal silk cocoons."
        ),

        NuflipFactsheetEntry(
            id = "nuflip_white_grubs",
            name = "White Grubs (May / June Beetle Larvae)",
            scientificName = "Phyllophaga spp. and related Melolonthinae",
            category = NuflipCategory.PEST,
            hostCrops = listOf("Cabbage", "Tomato", "Onion", "Maize", "Watermelon"),
            symptomsAndDamage = "Grubs chew roots and cut young seedlings beneath the soil line. Plants show sudden wilting, stunting, and can easily be pulled from the soil.",
            favorableConditions = "Sandy loam soils with high organic matter or poorly decomposed manure.",
            biologicalFeatures = "Fat C-shaped white larvae with brown chitinized heads and three pairs of thoracic legs.",
            controlMeasures = "Apply granular insecticide in the soil furrow or drench seedling collar with insecticide dilution upon finding severed plants. Use well-composted manure.",
            chemicalRotations = listOf("Carbofuran 5G", "Chlorpyrifos 480 EC drench", "Imidacloprid drench"),
            imageDescription = "C-shaped white grub dug from soil near severed cabbage seedling collar."
        ),

        // DISEASES
        NuflipFactsheetEntry(
            id = "nuflip_bacterial_canker",
            name = "Bacterial Canker",
            scientificName = "Clavibacter michiganensis",
            category = NuflipCategory.DISEASE,
            hostCrops = listOf("Tomato", "Pepper", "Eggplant", "Nakati"),
            symptomsAndDamage = "Marginal leaf firing (necrosis). Stems show dark necrotic lesions and light-brown vascular discoloration. Fruits show distinct 'bird's-eye spots' (small white halos with dark raised centers).",
            favorableConditions = "High temperatures (25°C–28°C), persistent moisture, and neutral to alkaline soil pH (6.9–7.9).",
            biologicalFeatures = "Seed-borne and systemic vascular bacterium. Readily transmitted on pruners, trellising twine, and workers' hands.",
            controlMeasures = "Certified clean seed. Nursery drench with copper oxychloride. Bronopol plant activator induces systemic defense. Disinfect pruning tools with 10% bleach.",
            chemicalRotations = listOf("Copper oxychloride 50% WP", "Bronopol (2-bromo-2-nitropropane-1,3-diol)", "Streptomycin sulphate where permitted"),
            imageDescription = "Tomato fruit with classic white bird's-eye spots and marginal foliage necrosis."
        ),

        NuflipFactsheetEntry(
            id = "nuflip_southern_stem_blight",
            name = "Southern Stem Blight (Sclerotium Rot)",
            scientificName = "Sclerotium rolfsii",
            category = NuflipCategory.DISEASE,
            hostCrops = listOf("Tomato", "Eggplant", "Green Pepper", "Cabbage", "Legumes", "Cucurbits"),
            symptomsAndDamage = "Sudden wilting of healthy plants. Stems at the soil collar develop dark water-soaked rot covered by a conspicuous white fan-like fungal mycelium mat studded with tiny brown mustard-seed spherical sclerotia.",
            favorableConditions = "High soil temperatures (around 30°C) combined with high soil moisture and buried un-decomposed organic matter.",
            biologicalFeatures = "Soil-borne pathogen. Persistent sclerotia survive in the top 5 cm of soil for multiple seasons.",
            controlMeasures = "Deep plowing to bury sclerotia. Rotate with cereal crops (maize, sorghum). Keep stem collar dry and weed-free. Avoid piling green uncomposted organic matter against stems.",
            chemicalRotations = listOf("Azoxystrobin 250 SC", "Thiophanate methyl 70% WP", "Trichoderma viride soil inoculant"),
            imageDescription = "White fan of mycelium around tomato collar with round brown spherical sclerotia."
        ),

        NuflipFactsheetEntry(
            id = "nuflip_bacterial_soft_rot",
            name = "Bacterial Soft Rot",
            scientificName = "Pectobacterium carotovorum (Erwinia carotovora)",
            category = NuflipCategory.DISEASE,
            hostCrops = listOf("Cabbage", "Onion", "Tomato", "Potato", "Pepper"),
            symptomsAndDamage = "Rapid water-soaked mushy breakdown of leaves, cabbage heads, and onion bulbs. Emits an overpowering, sickening foul odor. Head tissue collapses into watery slime.",
            favorableConditions = "Warm (25°C–30°C) humid weather, heavy rainfall, waterlogged soils, and mechanical insect/harvest wounds.",
            biologicalFeatures = "Pectolytic bacterium producing enzymes that dissolve plant cell wall pectins into mush.",
            controlMeasures = "Prevent insect feeding wounds. Harvest only during dry weather. Provide raised drainage beds. Disinfect storage crates and dry onion bulbs thoroughly before packing.",
            chemicalRotations = listOf("Copper oxychloride 50% WP", "Copper hydroxide", "Bronopol seed treatment"),
            imageDescription = "Collapsed rotting cabbage head with watery decay and offensive odor."
        ),

        NuflipFactsheetEntry(
            id = "nuflip_cabbage_black_rot",
            name = "Cabbage Bacterial Black Rot",
            scientificName = "Xanthomonas campestris pv. campestris",
            category = NuflipCategory.DISEASE,
            hostCrops = listOf("Cabbage", "Kale", "Broccoli", "Cauliflower"),
            symptomsAndDamage = "Distinct V-shaped yellow-to-brown chlorotic lesions progressing inward from leaf margins with black discolored leaf veins (vascular blackening).",
            favorableConditions = "Warm humid conditions (27°C–30°C) with persistent morning dew, overhead irrigation, or heavy rain splashing.",
            biologicalFeatures = "Enters hydathodes at leaf margins and insect bites; moves systemically through xylem vessels.",
            controlMeasures = "Use hot-water treated disease-free seeds. 3-year crop rotation without brassicas. Avoid working in wet fields. Preventive copper sprays.",
            chemicalRotations = listOf("Copper oxychloride 50% WP", "Copper sulphate", "Bronopol"),
            imageDescription = "Marginal V-shaped yellow lesion on cabbage leaf with blackened vein network."
        ),

        NuflipFactsheetEntry(
            id = "nuflip_onion_purple_blotch",
            name = "Purple Blotch & Alternaria Leaf Spot",
            scientificName = "Alternaria porri",
            category = NuflipCategory.DISEASE,
            hostCrops = listOf("Onion", "Garlic", "Leek", "Shallot"),
            symptomsAndDamage = "Small water-soaked lesions that rapidly expand into sunken, elliptical zonate patches with purplish-brown centers and yellow halos. Causes leaves to bend and break.",
            favorableConditions = "Warm temperatures (25°C–30°C) and prolonged leaf wetness from rain or dense morning fog.",
            biologicalFeatures = "Fungal airborne spores penetrate stomata and thrips feeding scars.",
            controlMeasures = "Apply preventive mancozeb at late growth stage. Rotate with non-allium crops. Ensure good air drainage and avoid high plant densities.",
            chemicalRotations = listOf("Mancozeb 800 g/kg", "Difenoconazole 250 EC", "Azoxystrobin + Difenoconazole"),
            imageDescription = "Sunken purple-centered elliptical eye spots on tubular onion leaves."
        ),

        // PHYSIOLOGICAL DISORDERS
        NuflipFactsheetEntry(
            id = "nuflip_blossom_end_rot",
            name = "Blossom End Rot (BER)",
            scientificName = "Calcium Deficiency & Water Stress Disorder",
            category = NuflipCategory.PHYSIOLOGICAL_DISORDER,
            hostCrops = listOf("Tomato", "Green Pepper", "Eggplant", "Watermelon"),
            symptomsAndDamage = "Water-soaked brown to black flat sunken leathery patch developing on the blossom end (distal end) of green and ripening fruits.",
            favorableConditions = "Fluctuating soil moisture, prolonged dry spells, excessive nitrogen fertilizer (which stimulates leaf growth at the expense of fruit calcium), and acidic soils.",
            biologicalFeatures = "Non-pathogenic physiological disorder caused by inadequate calcium translocation to rapidly expanding distal fruit cells.",
            controlMeasures = "Maintain uniform soil moisture via drip irrigation and grass mulching. Apply calcitic lime or calcium nitrate at flowering. Avoid excess ammonium nitrogen. Remove affected fruits promptly.",
            chemicalRotations = listOf("Foliar Calcium Chloride 0.5%", "Calcium Nitrate foliar spray", "Agricultural Lime soil application"),
            imageDescription = "Black sunken leathery spot on the bottom blossom end of green tomato and pepper fruit."
        ),

        NuflipFactsheetEntry(
            id = "nuflip_potassium_deficiency",
            name = "Potassium Deficiency (Tip Burn & Blotchy Ripening)",
            scientificName = "Nutritional Macronutrient Disorder (K Deficiency)",
            category = NuflipCategory.PHYSIOLOGICAL_DISORDER,
            hostCrops = listOf("Tomato", "Green Pepper", "Watermelon", "Eggplant"),
            symptomsAndDamage = "Marginal leaf chlorosis developing from edges inward (tip burn). Necrotic brown scorch on leaf edges while veins stay green. Tomato fruits show blotchy uneven ripening with hard yellow-green shoulders.",
            favorableConditions = "Sandy soils subject to heavy leaching, heavily cropped soils without basal K replenishment, high fruit load periods.",
            biologicalFeatures = "Potassium is highly mobile in plants; deficiency symptoms appear first on older lower leaves.",
            controlMeasures = "Apply cow dung compost or poultry manure rich in potassium before planting. Apply Potassium Sulphate (SOP) or Muriate of Potash (MOP) as topdressing during fruit enlargement.",
            chemicalRotations = listOf("Potassium Nitrate (Multi-K) foliar spray", "Sulphate of Potash (0-0-50)", "High-K NPK (15-5-34)"),
            imageDescription = "Scorched leaf margins (tip burn) with green veins and blotchy green-shouldered tomato fruits."
        ),

        NuflipFactsheetEntry(
            id = "nuflip_magnesium_deficiency",
            name = "Magnesium Deficiency",
            scientificName = "Nutritional Secondary Nutrient Disorder (Mg Deficiency)",
            category = NuflipCategory.PHYSIOLOGICAL_DISORDER,
            hostCrops = listOf("Tomato", "Watermelon", "Green Pepper", "Eggplant"),
            symptomsAndDamage = "Striking interveinal chlorosis on older lower leaves. Pale yellowing between the green veins followed by necrotic brown patches. Leaves curl upward.",
            favorableConditions = "Acidic sandy soils, or soils with excessive potassium application (potassium is antagonistic to magnesium uptake).",
            biologicalFeatures = "Magnesium is the central atom of the chlorophyll molecule; deficiency directly reduces photosynthetic assimilation.",
            controlMeasures = "Apply dolomitic limestone (containing Mg) to acidic soils. Apply well-decomposed compost. Apply foliar sprays of Epsom salts.",
            chemicalRotations = listOf("Magnesium Sulphate (Epsom Salts) 10-20 g/L foliar spray", "Dolomitic Lime soil conditioner"),
            imageDescription = "Bright yellow interveinal chlorosis with dark green veins on older watermelon and tomato leaves."
        ),

        NuflipFactsheetEntry(
            id = "nuflip_sunburn_sunscald",
            name = "Sunburn / Sunscald",
            scientificName = "Solar Radiation & Thermal Shock Disorder",
            category = NuflipCategory.PHYSIOLOGICAL_DISORDER,
            hostCrops = listOf("Tomato", "Green Pepper", "Watermelon"),
            symptomsAndDamage = "Shiny, bleached, papery white to tan sunken patch on the side of fruit exposed to direct sunlight. The border between damaged and healthy tissue is sharp and distinct.",
            favorableConditions = "High ambient temperatures (>35°C), intense solar radiation, and sudden fruit exposure caused by pest defoliation (hornworms, blights) or aggressive pruning.",
            biologicalFeatures = "Internal fruit tissue temperatures exceed 50°C under direct solar radiation, denaturing proteins and cell walls.",
            controlMeasures = "Maintain adequate canopy leaf cover. Avoid excessive leaf pruning. Control defoliating caterpillars and blights. Provide overhead shade netting where feasible.",
            chemicalRotations = listOf("Kaolin clay (Surround WP) reflective canopy spray"),
            imageDescription = "Bleached papery white sunken patch on sun-facing side of green bell pepper and tomato."
        ),

        NuflipFactsheetEntry(
            id = "nuflip_fertilizer_pesticide_burn",
            name = "Fertilizer & Pesticide Burn (Phytotoxicity)",
            scientificName = "Chemical Injury & Salt Scorch Disorder",
            category = NuflipCategory.PHYSIOLOGICAL_DISORDER,
            hostCrops = listOf("Tomato", "Eggplant", "Green Pepper", "Watermelon", "Cabbage", "Onion"),
            symptomsAndDamage = "Scorched necrotic leaf margins, curling growing points, stunted root systems, and seedling death. White salt crusts on soil around burnt stems.",
            favorableConditions = "Placing dry granular fertilizers too close to seedling stems, spraying pesticides during hot midday sun (>28°C), overdosing chemical rates, or tank-mixing incompatible stickers with azoxystrobin.",
            biologicalFeatures = "High osmotic salt concentration draws water out of root hairs, causing root cell plasmolysis and chemical desiccation.",
            controlMeasures = "Never place fertilizer in direct contact with stems or root balls (keep 5–10 cm ring distance). Spray chemicals only in early morning (6:30–9:00 AM) or late evening. Strictly measure with calibrated measuring cups. Do not mix wetting agent/sticker with azoxystrobin on tomatoes.",
            chemicalRotations = listOf("Flush soil with copious fresh irrigation water to leach excess salts"),
            imageDescription = "Scorched seedling leaf margins and necrotic growing points following midday chemical spray."
        ),

        NuflipFactsheetEntry(
            id = "nuflip_flooding_damage",
            name = "Flooding & Waterlogging Hypoxia",
            scientificName = "Soil Asphyxiation & Root Oxygen Deprivation",
            category = NuflipCategory.PHYSIOLOGICAL_DISORDER,
            hostCrops = listOf("Cabbage", "Tomato", "Watermelon", "Onion"),
            symptomsAndDamage = "Lower leaves yellow and wilt suddenly despite saturated soil. Fine feeder root hairs rot and turn dark brown. Plants take weeks to recover or collapse completely.",
            favorableConditions = "Heavy prolonged rain in low-lying, poorly drained clay soils without drainage channels.",
            biologicalFeatures = "Roots require oxygen for aerobic respiration; prolonged water saturation causes anoxic root death within 24–48 hours.",
            controlMeasures = "Cultivate crops on raised beds (20–30 cm). Dig deep drainage ditches around field borders. Never plant sensitive vegetables in swampy valley bottoms during rainy season.",
            chemicalRotations = listOf("Foliar micro-nutrient tonic after waters recede to stimulate root recovery"),
            imageDescription = "Stunted yellowing cabbage plants in standing flood water with rotten feeder roots."
        )
    )

    fun searchFactsheets(query: String): List<NuflipFactsheetEntry> {
        val q = query.trim().lowercase()
        if (q.isBlank()) return factsheets
        return factsheets.filter { entry ->
            entry.name.lowercase().contains(q) ||
            entry.scientificName.lowercase().contains(q) ||
            entry.hostCrops.any { it.lowercase().contains(q) } ||
            entry.symptomsAndDamage.lowercase().contains(q) ||
            entry.controlMeasures.lowercase().contains(q)
        }
    }
}
