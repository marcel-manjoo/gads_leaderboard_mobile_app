package za.co.gadsrank.home.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import za.co.gadsrank.home.model.TopLearnerSkillIq
import za.co.gadsrank.home.retrofit.GadEndPoints
import za.co.gadsrank.home.retrofit.ServiceBuilder

class SkillIqViewModel : ViewModel() {

    private val TAG = SkillIqViewModel::class.java.simpleName

    var items = MutableLiveData<List<TopLearnerSkillIq>>()

    fun init() {
        viewModelScope.launch {
            getTopSkillIq()
        }
    }

    private suspend fun getTopSkillIq() {
        return withContext(Dispatchers.IO) {
            val request = ServiceBuilder.buildService(GadEndPoints::class.java)
            val call = request.getTopLearnerskillIq()
            call.enqueue(object : Callback<List<TopLearnerSkillIq>> {
                override fun onResponse(call: Call<List<TopLearnerSkillIq>>, response: Response<List<TopLearnerSkillIq>>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        items.value = data
                        Log.d(TAG, "data size " + data?.size )
                    }
                }

                override fun onFailure(call: Call<List<TopLearnerSkillIq>>, t: Throwable) {
                    Log.d(TAG, "${t.message}")
                }

            })
        }
    }
}