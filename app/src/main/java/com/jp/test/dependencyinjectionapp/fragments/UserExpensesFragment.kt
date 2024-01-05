package com.jp.test.dependencyinjectionapp.fragments

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.jp.test.dependencyinjectionapp.databinding.FragmentUserExpensesBinding
import com.jp.test.dependencyinjectionapp.models.UserExpenses
import com.jp.test.dependencyinjectionapp.utils.Preference
import com.jp.test.dependencyinjectionapp.utils.Utils
import com.jp.test.dependencyinjectionapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat


@AndroidEntryPoint
class UserExpensesFragment : Fragment() {

    private var _binding: FragmentUserExpensesBinding? = null
    private val binding get() = _binding!!
    private val calendar: Calendar by lazy { Calendar.getInstance() }
    private val userViewModel: UserViewModel by viewModels()
    private val args: UserExpensesFragmentArgs by navArgs()
    private var userExpenses: UserExpenses? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUserExpensesBinding.inflate(layoutInflater)
        init()
        return binding.root
    }

    private fun init() {
        binding.etDate.text = Utils.getCurrentDate(calendar)
        binding.fragment = this
        userExpenses = args.userExpense
        binding.userExpenses = userExpenses
    }

    fun onAddDataClick() {
        val date = SimpleDateFormat("dd-MM-yyyy").parse(binding.etDate.text.toString().trim())

        if (userExpenses != null){
            userExpenses?.let {
                it.amountSpend = binding.etAmount.text.toString().trim().toInt()
                it.description = binding.etDescription.text.toString().trim()
                userViewModel.updateUserExpense(it)
            }

        }else {
            val userExpense = UserExpenses(
                0,
                amountSpend = binding.etAmount.text.toString().trim().toInt(),
                description = binding.etDescription.text.toString().trim(),
                date = date,
                userId = Preference.getPreferencesIntWithDefault(Preference.PREF_USER_ID, -1)
            )
            userViewModel.insertExpense(userExpense)

        }

        findNavController().navigateUp()

    }

    fun openDatePicker() {

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(
            requireActivity(), { view, year, monthOfYear, dayOfMonth ->
                // on below line we are setting
                // date to our text view.
                val formattedDayMonth = Utils.getFormattedDayAndMonth(dayOfMonth, monthOfYear + 1)
                "$formattedDayMonth-$year".also { binding.etDate.text = it }
            }, year, month, day
        )

        datePickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}