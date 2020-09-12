package za.co.gadsrank.submission

import android.app.Dialog
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_project_submission.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import za.co.gadsrank.R
import za.co.gadsrank.home.retrofit.GoogleFormEndPoint
import za.co.gadsrank.home.retrofit.ServiceFormBuilder
import kotlin.coroutines.CoroutineContext

class ProjectSubmissionActivity : AppCompatActivity(), CoroutineScope {

    private val TAG = ProjectSubmissionActivity::class.java.simpleName

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private lateinit var firstNameET: TextInputEditText
    private lateinit var lastNameET: TextInputEditText
    private lateinit var emailAddress: TextInputEditText
    private lateinit var projectLink: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_submission)
        job = Job()

        val toolbar = submission_toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.navigationIcon?.setColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)

        firstNameET = first_name
        lastNameET = last_name
        emailAddress = email_address
        projectLink = project_link

        project_submit_btn.setOnClickListener {
            verifySubmit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun verifySubmit() {
        if(validateFields()) {
            displayVerifyDialog()
        }
    }

    private fun displayVerifyDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.are_you_sure_dialog)
        val yesBtn = dialog.findViewById(R.id.yes_btn) as MaterialButton
        val noBtn = dialog.findViewById(R.id.close_btn) as ShapeableImageView
        yesBtn.setOnClickListener {
            launch {
                submitProjectDetails()
            }
            dialog.dismiss()
        }
        noBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun validateFields(): Boolean {
        return validateField(firstNameET) &&
                validateField(lastNameET) &&
                validateField(emailAddress) &&
                validateField(projectLink)
    }

    private fun validateField(input: TextInputEditText): Boolean {
        if (input.length() == 0) {
            input.error = "Field required"
            return false
        }
        return true
    }

    private suspend fun submitProjectDetails() {
        return withContext(Dispatchers.IO) {
            val request = ServiceFormBuilder.buildService(GoogleFormEndPoint::class.java)
            val call = request.submitData(emailAddress.text.toString(),
                firstNameET.text.toString(),
                lastNameET.text.toString(),
                projectLink.text.toString())
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "Data submitted successfully")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d(TAG, "${t.message}")
                }

            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun displaySuccessDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.submission_successful_dialog)
        dialog.show()
    }

    private fun displayNotSuccessDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.submission_not_successful_dialog)
        dialog.show()
    }

}