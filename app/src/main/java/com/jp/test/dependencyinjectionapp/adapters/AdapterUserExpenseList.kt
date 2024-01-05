package com.jp.test.dependencyinjectionapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jp.test.dependencyinjectionapp.databinding.ItemUserExpenseBinding
import com.jp.test.dependencyinjectionapp.fragments.HomeFragment
import com.jp.test.dependencyinjectionapp.fragments.UserExpensesFragment
import com.jp.test.dependencyinjectionapp.models.UserExpenses
import java.text.SimpleDateFormat

class AdapterUserExpenseList(private val dataList: List<UserExpenses>, private val homeFragment: HomeFragment) :
    RecyclerView.Adapter<AdapterUserExpenseList.ExpenseHolder>() {
    inner class ExpenseHolder(private val itemUserExpenseBinding: ItemUserExpenseBinding) :
        RecyclerView.ViewHolder(itemUserExpenseBinding.root) {
        fun setData(userExpenses: UserExpenses) {
            itemUserExpenseBinding.apply {
                userExpense = userExpenses
                tvDateValue.text = userExpenses.date?.let { SimpleDateFormat("dd-MM-yyyy").format(it) }
                fragment = homeFragment
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseHolder {
        val view =
            ItemUserExpenseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpenseHolder(view)
    }

    override fun getItemCount(): Int {
        println("data size >>> ${dataList.size}")
        return dataList.size
    }

    override fun onBindViewHolder(holder: ExpenseHolder, position: Int) {

        holder.setData(dataList[position])
    }
}