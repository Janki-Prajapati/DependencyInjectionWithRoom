package com.jp.test.dependencyinjectionapp.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jp.test.dependencyinjectionapp.R
import com.jp.test.dependencyinjectionapp.databinding.FragmentRegistrationBinding
import com.jp.test.dependencyinjectionapp.models.UserData
import com.jp.test.dependencyinjectionapp.utils.Preference
import com.jp.test.dependencyinjectionapp.utils.showToast
import com.jp.test.dependencyinjectionapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegistrationBinding.inflate(layoutInflater, container, false)
        binding.fragment = this
        clickListeners()
        return binding.root
    }

    private fun clickListeners() {
        binding.tvLogin.setOnClickListener { findNavController().navigateUp() }

    }

    fun registerClick() {

        val message = checkFields()

        if (message.isNotEmpty()) {
            requireActivity().showToast(message)
        } else {

            val isUserExists = userViewModel.isUserExists(binding.etMail.text.toString().trim())

            if (isUserExists) {
                requireActivity().showToast("This email id already registered!! Please use another one.")
            } else {
                val user = UserData(
                    email = binding.etMail.text.toString().trim(),
                    password = binding.etPassword.text.toString().trim()
                )
                userViewModel.insertUser(user)
                Handler(Looper.getMainLooper()).postDelayed({
                    val userData = userViewModel.getUser(binding.etMail.text.toString().trim())
                    Preference.savePreferencesInt(Preference.PREF_USER_ID, userData.id)
//                    userViewModel.insertExpense(UserExpenses(null, null, null, null, userData.id))
                    findNavController().navigate(R.id.action_registrationFragment_to_homeFragment)
//                    findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToHomeFragment())
                    Preference.savePreferencesBoolean(Preference.PREF_IS_LOGGED_IN, true)
                }, 3000)

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
        } else if (binding.etConfirmPassword.text.toString().trim().isEmpty()) {
            message = "Please enter valid Confirm Password"
        } else if (binding.etConfirmPassword.text.toString()
                .trim() != binding.etPassword.text.toString().trim()
        ) {
            message = "password and confirm password are not matched!"
        }

        return message

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}