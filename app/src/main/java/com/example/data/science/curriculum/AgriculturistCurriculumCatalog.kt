package com.example.data.science.curriculum

import com.example.model.science.AgriBookReference
import com.example.model.science.CurriculumPillarType
import com.example.model.science.CurriculumQuizItem
import com.example.model.science.CurriculumSubtopic
import com.example.model.science.CurriculumTopic

object AgriculturistCurriculumCatalog {

    val allTopics: List<CurriculumTopic> by lazy {
        CropScienceCurriculum.topics +
        SoilScienceCurriculum.topics +
        AnimalScienceCurriculum.topics +
        CropProtectionCurriculum.topics +
        AgriEconomicsCurriculum.topics +
        AgriExtensionCurriculum.topics
    }

    val allBooks: List<AgriBookReference> = AgriCurriculumBooksCatalog.books

    val allQuizzes: List<CurriculumQuizItem> = CurriculumQuizCatalog.quizItems

    fun getTopicsForPillar(pillar: CurriculumPillarType): List<CurriculumTopic> {
        return allTopics.filter { it.pillar == pillar }
    }

    fun getBooksForPillar(pillar: CurriculumPillarType): List<AgriBookReference> {
        return allBooks.filter { it.primaryPillar == pillar }
    }

    fun getQuizzesForPillar(pillar: CurriculumPillarType): List<CurriculumQuizItem> {
        return allQuizzes.filter { it.pillar == pillar }
    }

    fun searchCurriculum(query: String): List<CurriculumTopic> {
        if (query.isBlank()) return allTopics
        val q = query.trim().lowercase()
        return allTopics.filter { topic ->
            topic.title.lowercase().contains(q) ||
            topic.code.lowercase().contains(q) ||
            topic.summary.lowercase().contains(q) ||
            topic.subtopics.any { sub ->
                sub.title.lowercase().contains(q) ||
                sub.coreDefinition.lowercase().contains(q) ||
                sub.detailedElaboration.lowercase().contains(q) ||
                sub.keyMechanismsAndFormulas.any { it.lowercase().contains(q) }
            }
        }
    }

    fun searchBooks(query: String): List<AgriBookReference> {
        if (query.isBlank()) return allBooks
        val q = query.trim().lowercase()
        return allBooks.filter { book ->
            book.title.lowercase().contains(q) ||
            book.authors.lowercase().contains(q) ||
            book.coverSubject.lowercase().contains(q) ||
            book.description.lowercase().contains(q) ||
            book.keyChapters.any { it.lowercase().contains(q) } ||
            book.syllabusTopicsCovered.any { it.lowercase().contains(q) }
        }
    }
}
