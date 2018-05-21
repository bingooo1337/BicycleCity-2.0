package com.diploma.volodymyr.bicyclecity.data.repository

import com.diploma.volodymyr.bicyclecity.common.Const.COMPETITIONS
import com.diploma.volodymyr.bicyclecity.data.BaseRepository
import com.diploma.volodymyr.bicyclecity.data.objects.competition.Competition
import com.diploma.volodymyr.bicyclecity.model.CompetitionRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class CompetitionRepositoryImpl(db: FirebaseFirestore) :
        BaseRepository(db), CompetitionRepository {

    override fun getCompetitions(): CollectionReference =
            db.collection(COMPETITIONS)

    override fun createCompetition(competition: Competition): Task<Void> =
            db.collection(COMPETITIONS).document().set(competition)
}