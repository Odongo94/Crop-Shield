package com.example.model.science

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * The 6 Core Curriculum Pillars from the Agriculturist Licensure & Academic Syllabus.
 */
enum class CurriculumPillarType(
    val pillarNumber: Int,
    val title: String,
    val shortName: String,
    val icon: ImageVector,
    val description: String
) {
    CROP_SCIENCE(
        pillarNumber = 1,
        title = "Crop Science",
        shortName = "Crop Sci",
        icon = Icons.Default.Agriculture,
        description = "Agronomy, Horticulture, Plant Breeding, Farming Systems, Seed Technology & Crop Physiology"
    ),
    SOIL_SCIENCE(
        pillarNumber = 2,
        title = "Soil Science",
        shortName = "Soil Sci",
        icon = Icons.Default.Terrain,
        description = "Genesis, Taxonomy, Microbiology, Physics, Soil Fertility & Chemical Properties"
    ),
    ANIMAL_SCIENCE(
        pillarNumber = 3,
        title = "Animal Science",
        shortName = "Animal Sci",
        icon = Icons.Default.Pets,
        description = "Breeding, Physiology, Nutrition, Products Processing, Health, Poultry, Swine & Ruminants"
    ),
    CROP_PROTECTION(
        pillarNumber = 4,
        title = "Crop Protection",
        shortName = "Crop Protect",
        icon = Icons.Default.BugReport,
        description = "Plant Pathology (Bacteriology, Mycology, Nematology, Virology), Entomology, Weed Science & Calculations"
    ),
    AGRI_ECONOMICS(
        pillarNumber = 5,
        title = "Agricultural Economics & Marketing",
        shortName = "Agri-Econ",
        icon = Icons.Default.AttachMoney,
        description = "Market Equilibrium, Elasticities, Production Costs, Macroeconomics, Fiscal Policy & Marketing Channels"
    ),
    AGRI_EXTENSION(
        pillarNumber = 6,
        title = "Agricultural Extension & Communications",
        shortName = "Agri-Extension",
        icon = Icons.Default.Campaign,
        description = "Extension Philosophy, Development Communication, Policy, Methods, SMCRF Model & Adoption Process"
    )
}

/**
 * A major topic inside a syllabus pillar (e.g. "a. Agronomy", "b. Horticulture", "b. Entomology").
 */
data class CurriculumTopic(
    val id: String,
    val pillar: CurriculumPillarType,
    val code: String, // e.g. "1.a", "2.c", "4.e"
    val title: String,
    val summary: String,
    val subtopics: List<CurriculumSubtopic>,
    val examFocusPoints: List<String>,
    val practicalFieldApplications: List<String>,
    val recommendedTextbookIds: List<String>
)

/**
 * Detailed subtopic with deep scientific & agronomic elaboration.
 */
data class CurriculumSubtopic(
    val id: String,
    val code: String, // e.g. "i", "ii", "iii"
    val title: String,
    val coreDefinition: String,
    val keyMechanismsAndFormulas: List<String>,
    val detailedElaboration: String,
    val commonExamQuestions: List<String>,
    val practicalTakeaway: String,
    val biologicalAndChemicalDetails: List<String> = emptyList(),
    val stepByStepFieldProtocols: List<String> = emptyList(),
    val clinicalOrDiagnosticSigns: List<String> = emptyList(),
    val boardExamWorkedProblems: List<String> = emptyList(),
    val authoritativeTextbookRef: String = ""
)

/**
 * Authoritative textbook or academic reference material in the agricultural library.
 */
data class AgriBookReference(
    val id: String,
    val title: String,
    val authors: String,
    val edition: String,
    val publisher: String,
    val year: Int,
    val primaryPillar: CurriculumPillarType,
    val coverSubject: String,
    val description: String,
    val keyChapters: List<String>,
    val syllabusTopicsCovered: List<String>,
    val whyItIsAuthoritative: String,
    val scholarSearchQuery: String
)

/**
 * Curriculum-aligned practice quiz item.
 */
data class CurriculumQuizItem(
    val id: String,
    val pillar: CurriculumPillarType,
    val topicCode: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val referencedBookId: String
)
