package com.diploma.volodymyr.bicyclecity.model

import com.diploma.volodymyr.bicyclecity.data.objects.competition.Competition
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.GeoPoint
import java.util.*

interface CompetitionRepository {
    fun getCompetitions(): CollectionReference
    fun getCompetitionById(id: String): DocumentReference
    fun updateCompetition(id: String, competition: Competition): Task<Void>
    fun createCompetition(competition: Competition): Task<Void>
    fun saveLocationTrack(competitionId: String, locationTrack: Map<GeoPoint, Date>)
    fun getLocationTrack(competitionId: String)
}