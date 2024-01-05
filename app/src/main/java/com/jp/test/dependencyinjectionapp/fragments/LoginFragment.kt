package com.jp.test.dependencyinjectionapp.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.jp.test.dependencyinjectionapp.R
import com.jp.test.dependencyinjectionapp.databinding.FragmentLoginBinding
import com.jp.test.dependencyinjectionapp.models.UserExpenses
import com.jp.test.dependencyinjectionapp.utils.Preference
import com.jp.test.dependencyinjectionapp.utils.showToast
import com.jp.test.dependencyinjectionapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(), TextView.OnEditorActionListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        binding.fragment = this
        setImage()
        setClick()
        editTextListeners()
        return binding.root
    }

    private fun editTextListeners() {
        binding.etPassword.setOnEditorActionListener(this)
    }

    private fun setClick() {
        binding.tvRegister.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_registrationFragment) }
    }

    private fun setImage() {
        Glide.with(this).load(R.drawable.ic_account).into(binding.imgIcon);
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId and EditorInfo.IME_ACTION_GO != 0) {
            Toast.makeText(requireActivity(), "Login process starts", Toast.LENGTH_LONG).show()
        }
        return false
    }

    fun onLoginClick() {
        val message = checkFields()

        if (message.isNotEmpty()) {
            requireActivity().showToast(message)
        } else {

            val isUserExists = userViewModel.isUserExists(binding.etMail.text.toString().trim())

            if (isUserExists) {

                val userData = userViewModel.getUser(binding.etMail.text.toString().trim())
                Preference.savePreferencesInt(Preference.PREF_USER_ID, userData.id)
//                userViewModel.insertExpense(UserExpenses(null, null, null, null, userData.id))
//                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                Preference.savePreferencesBoolean(Preference.PREF_IS_LOGGED_IN, true)
            } else {
                requireActivity().showToast("This email id not registered yet, Please register first!")
            }
        }
    }

    private fun checkFields(): String {

        var message = ""

        if (binding.etMail.text.toString().trim().isEmpty() || (binding.etMail.text.toString()
                .trim().isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(
                binding.etMail.text.toString().trim()
            ).matches())
        ) {
            message = "Please enter valid Email"
        } else if (binding.etPassword.text.toString().trim().isEmpty()) {
            message = "Please enter valid Password"
        }

        return message

    }
}