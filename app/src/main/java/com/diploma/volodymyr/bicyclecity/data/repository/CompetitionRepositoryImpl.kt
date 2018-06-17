package com.diploma.volodymyr.bicyclecity.data.repository

import com.diploma.volodymyr.bicyclecity.common.Const.COMPETITIONS
import com.diploma.volodymyr.bicyclecity.data.BaseRepository
import com.diploma.volodymyr.bicyclecity.data.objects.competition.Competition
import com.diploma.volodymyr.bicyclecity.model.CompetitionRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import java.util.*

class CompetitionRepositoryImpl(db: FirebaseFirestore) :
        BaseRepository(db), CompetitionRepository {

    override fun getCompetitions(): CollectionReference =
            db.collection(COMPETITIONS)

    override fun getCompetitionById(competitionId: String): DocumentReference =
            db.collection(COMPETITIONS).document(competitionId)

    override fun createCompetition(competition: Competition): Task<Void> =
            db.collection(COMPETITIONS).document().set(competition)

    override fun saveLocationTrack(competitionId: String, locationTrack: Map<GeoPoint, Date>) {

    }

    override fun getLocationTrack(competitionId: String) {

    }
}