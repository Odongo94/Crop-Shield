package com.example.data.science

import com.example.model.science.GlobalPestIdentificationPortal
import com.example.model.science.PortalSearchResult
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Authoritative global and regional pest and disease identification portals,
 * diagnostic factsheet repositories, and quarantine databases.
 * Directly integrates the 11 premier international websites.
 */
object GlobalPestPortalsCatalog {

    val allPortals: List<GlobalPestIdentificationPortal> = listOf(
        GlobalPestIdentificationPortal(
            id = "ahr_australia",
            name = "AHR Pest & Disease Identification Guides",
            organization = "Applied Horticultural Research (Australia)",
            url = "https://ahr.com.au/blog/news/pest-and-disease-identification-guides-available",
            searchUrlTemplate = "https://ahr.com.au/?s=",
            coverageDescription = "High-definition diagnostic field identification guides for vegetable crops including Brassicas, Alliums, Solanaceae (Tomato, Pepper, Eggplant), and Cucurbits with symptom keys and damage illustrations.",
            keySpecialties = listOf(
                "Vegetable Brassica & Allium Pests",
                "Solanaceous Pathology Field Guides",
                "Foliar Spot & Mildew Symptom Keys",
                "Integrated Crop Protection Factsheets"
            ),
            diagnosticGuideType = "Vegetable Diagnostic Field Factsheets",
            iconEmoji = "🇦🇺"
        ),

        GlobalPestIdentificationPortal(
            id = "nppc_bhutan",
            name = "Pests of Bhutan Diagnostic Portal",
            organization = "National Plant Protection Centre (NPPC) Bhutan",
            url = "https://pestsofbhutan.nppc.gov.bt/",
            searchUrlTemplate = "https://pestsofbhutan.nppc.gov.bt/?s=",
            coverageDescription = "Comprehensive national agricultural entomology and plant pathology registry detailing life histories, crop hosts, seasonal occurrence, biology, and IPM recommendations.",
            keySpecialties = listOf(
                "Insect Pest Biology & Life Cycles",
                "Himalayan & Subtropical Crop Diseases",
                "Non-Chemical IPM Thresholds",
                "Taxonomic Identification Keys"
            ),
            diagnosticGuideType = "National Plant Protection Factsheets",
            iconEmoji = "🇧🇹"
        ),

        GlobalPestIdentificationPortal(
            id = "cabi_plantwise",
            name = "CABI PlantwisePlus Knowledge Bank",
            organization = "Centre for Agriculture and Bioscience International (CABI)",
            url = "https://plantwiseplusknowledgebank.org/doi/10.5555/pwkb.20167800063",
            searchUrlTemplate = "https://plantwiseplusknowledgebank.org/action/doSearch?AllField=",
            coverageDescription = "The premier global open-access diagnostic repository featuring evidence-based Pest Management Decision Guides (PMDGs), biological control options, and worldwide geographic distribution maps.",
            keySpecialties = listOf(
                "Pest Management Decision Guides (Green & Amber lists)",
                "CABI BioProtection Registered Alternatives",
                "Global Geospatial Distribution Maps",
                "Quarantine Inoculum Pathways"
            ),
            diagnosticGuideType = "Global Plantwise Diagnostic Factsheets",
            iconEmoji = "🌍"
        ),

        GlobalPestIdentificationPortal(
            id = "bayer_cropscience",
            name = "Bayer CropScience Agronomy ID",
            organization = "Bayer Crop Science UK",
            url = "https://cropscience.bayer.co.uk/agronomy-id/pest-and-slugs",
            searchUrlTemplate = "https://cropscience.bayer.co.uk/search?q=",
            coverageDescription = "Commercial agronomic identification directory for invertebrate pests, mollusc slugs, and foliar vectors. Includes lifecycles, crop threshold triggers, and spray timing recommendations.",
            keySpecialties = listOf(
                "Broadacre & Vegetable Pest Identification",
                "Slugs & Soil-Dwelling Invertebrates",
                "Economic Threshold Action Triggers",
                "Mode of Action Resistance Management"
            ),
            diagnosticGuideType = "Commercial Agronomy Diagnostic Keys",
            iconEmoji = "🔬"
        ),

        GlobalPestIdentificationPortal(
            id = "eppo_quarantine",
            name = "EPPO Global Quarantine A1 & A2 Lists",
            organization = "European and Mediterranean Plant Protection Organization (EPPO)",
            url = "https://www.eppo.int/ACTIVITIES/plant_quarantine/A1_list",
            searchUrlTemplate = "https://gd.eppo.int/search?k=",
            coverageDescription = "Official international standard phytosanitary register of quarantine pests recommended for international regulation. Categorized into A1 (absent) and A2 (locally present, under official control) lists.",
            keySpecialties = listOf(
                "Official International Quarantine A1 & A2 Registers",
                "Phytosanitary Inspection & Interception SOPs",
                "Global Distribution & Host Range Data",
                "Biosecurity Risk Assessments"
            ),
            diagnosticGuideType = "Phytosanitary & Quarantine Regulation Lists",
            iconEmoji = "🛡️"
        ),

        GlobalPestIdentificationPortal(
            id = "psu_extension",
            name = "Penn State Extension Identification Library",
            organization = "Penn State College of Agricultural Sciences",
            url = "https://extension.psu.edu/insects-pests-and-diseases/pest-disease-and-weed-identification/see-all-pest-disease-and-weed-identification",
            searchUrlTemplate = "https://extension.psu.edu/search/?q=",
            coverageDescription = "Extensive academic university extension guide for insect pests, bacterial and fungal pathogens, and weed bio-indicators across horticultural and cereal cropping systems.",
            keySpecialties = listOf(
                "University Extension Diagnostic Factsheets",
                "Microscopic Pathology & Spore Characteristics",
                "Field Scouting & Trapping Methods",
                "Weed Competition & Seed Biology"
            ),
            diagnosticGuideType = "Academic Extension Pest Library",
            iconEmoji = "🎓"
        ),

        GlobalPestIdentificationPortal(
            id = "gardeners_supply",
            name = "Gardener's Supply Pest & Disease Encyclopedia",
            organization = "Gardener's Supply Horticultural Knowledgebase",
            url = "https://www.gardeners.com/blogs/pest-disease-encyclopedia",
            searchUrlTemplate = "https://www.gardeners.com/search?q=",
            coverageDescription = "Practical horticultural diagnostic encyclopedia detailing organic pest management, cultural sanitation, companion planting barriers, and physiological disorder mitigation.",
            keySpecialties = listOf(
                "Organic & Mechanical Pest Barriers",
                "Beneficial Predator Insect Attraction",
                "Physiological & Nutritional Disorder Remedies",
                "Smallholder & Greenhouse Pest Solutions"
            ),
            diagnosticGuideType = "Horticultural Pest Encyclopedia",
            iconEmoji = "🌱"
        ),

        GlobalPestIdentificationPortal(
            id = "mda_minnesota",
            name = "Minnesota MDA Plant Pests & Diseases Index",
            organization = "Minnesota Department of Agriculture",
            url = "https://www.mda.state.mn.us/plant-pests-diseases-index",
            searchUrlTemplate = "https://www.mda.state.mn.us/search?q=",
            coverageDescription = "Regulatory and surveillance database tracking agricultural pests, invasive insects, noxious weeds, and crop pathogens with official statutory monitoring guidelines.",
            keySpecialties = listOf(
                "Statutory Plant Pest Index",
                "Invasive Species Early Detection",
                "Noxious Weed Eradication Mandates",
                "County-Level Field Survey Protocols"
            ),
            diagnosticGuideType = "State Agricultural Regulatory Index",
            iconEmoji = "🏛️"
        ),

        GlobalPestIdentificationPortal(
            id = "taat_africa",
            name = "TAAT Africa Pest Control Technologies",
            organization = "Technologies for African Agricultural Transformation (AfDB / CGIAR)",
            url = "https://e-catalogs.taat-africa.org/gov/technologies?categories%5B%5D=Pest%20control",
            searchUrlTemplate = "https://e-catalogs.taat-africa.org/gov/technologies?q=",
            coverageDescription = "African Development Bank and CGIAR high-impact technology catalog deploying certified bio-pesticides, Fall Armyworm management toolkits, and climate-smart crop protection inputs across Africa.",
            keySpecialties = listOf(
                "African Agricultural Transformation Toolkits",
                "Fall Armyworm (FAW) Biocontrol Products",
                "Push-Pull Intercropping Desmodium Systems",
                "Smallholder Certified Seed & Inputs"
            ),
            diagnosticGuideType = "Pan-African Technology Transfer Catalog",
            iconEmoji = "🌾"
        ),

        GlobalPestIdentificationPortal(
            id = "corson_maize",
            name = "Corson Maize Pests & Diseases Portal",
            organization = "Corson Maize Seeds (Australasia & International)",
            url = "https://www.corsonmaize.co.nz/pests-and-diseases",
            searchUrlTemplate = "https://www.corsonmaize.co.nz/search?Search=",
            coverageDescription = "Specialized cereal and maize diagnostic guide targeting Northern Corn Leaf Blight, Eyespot, Common Rust, Cutworms, Wireworms, Armyworms, and Stalk Rots.",
            keySpecialties = listOf(
                "Maize & Cereal Specific Pathology",
                "Seedling Pests (Cutworm, Wireworm, Black Beetle)",
                "Foliar Blights & Rust Thresholds",
                "Hybrid Resistance Selection"
            ),
            diagnosticGuideType = "Maize & Cereal Crop Diagnostic Portal",
            iconEmoji = "🌽"
        ),

        GlobalPestIdentificationPortal(
            id = "inaturalist",
            name = "iNaturalist Global Biodiversity & AI Species ID",
            organization = "California Academy of Sciences & National Geographic",
            url = "https://www.inaturalist.org/",
            searchUrlTemplate = "https://www.inaturalist.org/search?q=",
            coverageDescription = "The world's largest open crowdsourced biodiversity platform featuring over 150 million photo-verified observations, computer vision AI species identification, and real-time geographic occurrence maps.",
            keySpecialties = listOf(
                "Machine Learning Computer Vision Insect & Plant ID",
                "Crowdsourced Photo Observations & Verified Records",
                "Real-Time Geospatial Presence Maps",
                "Taxonomic Synonyms & Global Common Names"
            ),
            diagnosticGuideType = "Global Biodiversity Citizen Science & AI ID",
            iconEmoji = "📸"
        )
    )

    /**
     * Generate active deep search links across all 11 portals for any query.
     */
    fun searchAllPortals(query: String): List<PortalSearchResult> {
        val q = query.trim()
        val encodedQuery = URLEncoder.encode(q, StandardCharsets.UTF_8.toString())
        
        return allPortals.map { portal ->
            val deepUrl = if (!portal.searchUrlTemplate.isNullOrBlank()) {
                "${portal.searchUrlTemplate}$encodedQuery"
            } else {
                "${portal.url}?q=$encodedQuery"
            }

            PortalSearchResult(
                portal = portal,
                query = q,
                deepSearchUrl = deepUrl,
                title = "${portal.name}: Search for '$q'",
                description = "Query official records, diagnostic keys, and control protocols for '$q' in ${portal.organization}."
            )
        }
    }

    /**
     * Get a specific portal by ID.
     */
    fun getPortalById(id: String): GlobalPestIdentificationPortal? {
        return allPortals.find { it.id == id }
    }
}
