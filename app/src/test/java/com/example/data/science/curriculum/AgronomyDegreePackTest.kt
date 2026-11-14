package com.example.data.science.curriculum

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AgronomyDegreePackTest {

    @Test
    fun `complete pack covers all 3 bachelor years and all 15 required subjects`() {
        assertEquals(3, AgronomyStudyVolume.values().size)
        assertEquals(15, AgronomyDegreePackCatalog.allSubjects.size)

        val expectedSubjects = setOf(
            "Crop Production",
            "Soil Science",
            "Plant Physiology",
            "Genetics & Plant Breeding",
            "Crop Protection",
            "Agronomy & Crop Science",
            "Horticulture",
            "Irrigation & Water Management",
            "Agroecology",
            "Agricultural Economics",
            "Farm Management",
            "Sustainable Agriculture",
            "Agricultural Biotechnology",
            "Climate-Smart Agriculture",
            "Modern Agricultural Technologies"
        )

        val actualNames = AgronomyDegreePackCatalog.allSubjects.map { it.subjectName }.toSet()
        assertEquals(expectedSubjects, actualNames)

        // Verify each volume has subjects, formulas, lab protocols, assignments, and revision QAs
        AgronomyStudyVolume.values().forEach { volume ->
            val subjects = AgronomyDegreePackCatalog.subjectsForVolume(volume)
            assertTrue("Volume ${volume.yearLabel} should not be empty", subjects.isNotEmpty())
            subjects.forEach { subject ->
                assertTrue(subject.coreConcepts.isNotEmpty())
                assertTrue(subject.keyFormulasAndMetrics.isNotEmpty())
                assertTrue(subject.fieldAndLabProtocols.isNotEmpty())
                assertTrue(subject.assignmentPrompts.isNotEmpty())
                assertTrue(subject.examRevisionQas.isNotEmpty())
            }
        }
    }

    @Test
    fun `searchSubjects filters accurately across volumes and technical terms`() {
        val allResults = AgronomyDegreePackCatalog.searchSubjects("", null)
        assertEquals(15, allResults.size)

        val ndviMatches = AgronomyDegreePackCatalog.searchSubjects("NDVI", null)
        assertEquals(1, ndviMatches.size)
        assertEquals("Modern Agricultural Technologies", ndviMatches.first().subjectName)

        val year2Fao = AgronomyDegreePackCatalog.searchSubjects("FAO-56", AgronomyStudyVolume.YEAR_2)
        assertEquals(1, year2Fao.size)
        assertEquals("Irrigation & Water Management", year2Fao.first().subjectName)

        val year1Only = AgronomyDegreePackCatalog.subjectsForVolume(AgronomyStudyVolume.YEAR_1)
        assertEquals(5, year1Only.size)
        assertEquals(6, AgronomyDegreePackCatalog.subjectsForVolume(AgronomyStudyVolume.YEAR_2).size)
        assertEquals(4, AgronomyDegreePackCatalog.subjectsForVolume(AgronomyStudyVolume.YEAR_3).size)
    }
}
