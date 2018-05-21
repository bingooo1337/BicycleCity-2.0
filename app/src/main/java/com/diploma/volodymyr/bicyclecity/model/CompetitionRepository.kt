package com.diploma.volodymyr.bicyclecity.model

import com.diploma.volodymyr.bicyclecity.data.objects.competition.Competition
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference

interface CompetitionRepository {
    fun getCompetitions(): CollectionReference
    fun createCompetition(competition: Competition): Task<Void>
}