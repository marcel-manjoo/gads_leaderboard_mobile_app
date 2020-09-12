package za.co.gadsrank.home.retrofit

import retrofit2.Call
import retrofit2.http.GET
import za.co.gadsrank.home.model.TopLearnerHours
import za.co.gadsrank.home.model.TopLearnerSkillIq

interface GadEndPoints {

    @GET(" api/hours")
    fun getTopLearnerHours(): Call<List<TopLearnerHours>>

    @GET("api/skilliq")
    fun getTopLearnerskillIq(): Call<List<TopLearnerSkillIq>>
}