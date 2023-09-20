package com.example.project.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.project.R
import com.example.project.databinding.ActivityHomeBinding
import com.example.project.ui.list.ListActivity
import com.example.project.util.UiUtil
import com.example.project.util.UserHelper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeActivityVM
    private lateinit var authFirebase: FirebaseAuth

    private val RC_SIGN_IN = 2
    private var showOneTapUI = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("TAG", "onCreate: HomeActivity")

        binding = ActivityHomeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[HomeActivityVM::class.java]
        setContentView(binding.root)


        binding.versionText.text = buildString {
            append(getString(R.string.version))
            append(viewModel.getAppVersion())
        }

        authFirebase = FirebaseAuth.getInstance()

        // Set up Google Sign-In options
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Provide your Web Client ID here
            .requestEmail()
            .build()


        binding.googleButton.setOnClickListener {
            val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
            startActivityForResult(googleSignInClient.signInIntent, RC_SIGN_IN)
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    account.idToken?.let { firebaseAuthWithGoogle(it) }
                    startActivity(Intent(this@HomeActivity, ListActivity::class.java).setAction(""))
                }
            } catch (e: ApiException) {
                e.localizedMessage?.let { UiUtil.customAlertDialog(this@HomeActivity, it) }
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        authFirebase.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = authFirebase.currentUser
                    if (user != null) {
                        UserHelper.firebaseUser = user
                    }
                } else {
                    // Sign-in failed
                }
            }
    }

}