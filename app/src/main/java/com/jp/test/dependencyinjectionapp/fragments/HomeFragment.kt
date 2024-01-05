package com.jp.test.dependencyinjectionapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jp.test.dependencyinjectionapp.R
import com.jp.test.dependencyinjectionapp.adapters.AdapterExpensesHeader
import com.jp.test.dependencyinjectionapp.adapters.AdapterUserExpenseList
import com.jp.test.dependencyinjectionapp.databinding.FragmentHomeBinding
import com.jp.test.dependencyinjectionapp.interfaces.AlertDialogClickListeners
import com.jp.test.dependencyinjectionapp.models.DateCount
import com.jp.test.dependencyinjectionapp.models.UserExpenses
import com.jp.test.dependencyinjectionapp.utils.Preference
import com.jp.test.dependencyinjectionapp.utils.Utils
import com.jp.test.dependencyinjectionapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar

@AndroidEntryPoint
class HomeFragment : Fragment(), AlertDialogClickListeners {

    private lateinit var binding: FragmentHomeBinding
    private val userViewModel: UserViewModel by viewModels()
    private var dataList = arrayListOf<DateCount>()
    private lateinit var adapterExpensesHeader: AdapterExpensesHeader
    private var total = 0
    private var userExpenses: UserExpenses? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        init()
        return binding.root
    }

    private fun init() {
        binding.fragment = this
        adapterExpensesHeader = AdapterExpensesHeader(dataList, this)
        binding.rvExpenses.adapter = adapterExpensesHeader
        binding.tvTotalValue.text = total.toString()

        /*   userViewModel.getExpenses(
               Preference.getPreferencesIntWithDefault(
                   Preference.PREF_USER_ID,
                   -1
               ),
               SimpleDateFormat("dd-MM-yyyy").parse(Utils.getCurrentDate(Calendar.getInstance()))
           ).observe(requireActivity()) {
               dataList.clear()
               total = 0
               dataList.addAll(it)

               if (dataList.isNotEmpty()) {
                   binding.tvNoData.visibility = View.GONE
                   binding.rvExpenses.visibility = View.VISIBLE
                   dataList.forEach { amount -> total += amount.amountSpend ?: 0 }
                   adapterUserExpense.notifyDataSetChanged()
               } else {
                   binding.tvNoData.visibility = View.VISIBLE
                   binding.rvExpenses.visibility = View.GONE
               }
               binding.tvTotalValue.text = total.toString()
           }*/

        userViewModel.getExpenseGroupedByDate(
            Preference.getPreferencesIntWithDefault(
                Preference.PREF_USER_ID,
                -1
            )
        ).observe(requireActivity()) {
            println(">>> ${it.size}")
            total = 0
            dataList.clear()
            dataList.addAll(it)

            val toBeRemovedList = arrayListOf<DateCount>()
            dataList.forEach { data ->
                val dataList = data.expenseList
                val list: List<UserExpenses> = dataList.filter { userExpenses ->
                    userExpenses.userId == Preference.getPreferencesIntWithDefault(
                        Preference.PREF_USER_ID,
                        -1
                    )
                }
                data.expenseList = list
                if (list.isEmpty()) toBeRemovedList.add(data)

            }
            dataList.removeAll(toBeRemovedList.toSet())

            dataList.forEach { dateCount ->
                dateCount.expenseList.forEach { amount ->
                    total += amount.amountSpend ?: 0
                }
            }
            binding.tvTotalValue.text = total.toString()
            adapterExpensesHeader.notifyDataSetChanged()
            println(">>> ${dataList.size}")
        }

    }

    fun moveToUserExpenseScreen() {
        val action = HomeFragmentDirections.actionHomeFragmentToUserExpensesFragment(null)
        findNavController().navigate(action)
    }

    fun logout() {
        Utils.showAlertDialog(
            requireActivity(),
            "Alert",
            "Are you sure you want to logout?",
            "Yes",
            "No",
            "logout",
            this
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
    }

    fun onEditExpenseClick(userExpenses: UserExpenses) {
        val action = HomeFragmentDirections.actionHomeFragmentToUserExpensesFragment(userExpenses)
        findNavController().navigate(action)
    }

    fun onDeleteExpenseClick(userExpenses: UserExpenses) {
        this.userExpenses = userExpenses
        Utils.showAlertDialog(
            requireActivity(),
            "Alert",
            "Are you sure you want to delete this data?",
            "Yes",
            "No",
            "delete",
            this
        )
    }

    override fun positiveClick(fromFlag: String) {
        when (fromFlag) {
            "delete" -> {
                userExpenses?.let { userViewModel.deleteUserExpense(it) }
            }

            "logout" -> {
                Preference.savePreferencesBoolean(Preference.PREF_IS_LOGGED_IN, value = false)
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            }
        }
    }

    override fun negativeClick(fromFlag: String) {
        when (fromFlag) {
            "delete" -> {
                userExpenses = null
            }
        }
    }
}