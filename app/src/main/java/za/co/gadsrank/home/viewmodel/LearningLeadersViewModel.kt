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
import za.co.gadsrank.home.model.TopLearnerHours
import za.co.gadsrank.home.retrofit.GadEndPoints
import za.co.gadsrank.home.retrofit.ServiceBuilder

class LearningLeadersViewModel : ViewModel() {

    private val TAG = LearningLeadersViewModel::class.java.simpleName

    var items = MutableLiveData<List<TopLearnerHours>>()

    fun init() {
        viewModelScope.launch {
            getTopHours()
        }
    }

    private suspend fun getTopHours() {
        return withContext(Dispatchers.IO) {
            val request = ServiceBuilder.buildService(GadEndPoints::class.java)
            val call = request.getTopLearnerHours()
            call.enqueue(object : Callback<List<TopLearnerHours>> {
                override fun onResponse(call: Call<List<TopLearnerHours>>, response: Response<List<TopLearnerHours>>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        items.value = data
                        Log.d(TAG, "data size " + data?.size )
                    }
                }

                override fun onFailure(call: Call<List<TopLearnerHours>>, t: Throwable) {
                    Log.d(TAG, "${t.message}")
                }

            })
        }
    }

}