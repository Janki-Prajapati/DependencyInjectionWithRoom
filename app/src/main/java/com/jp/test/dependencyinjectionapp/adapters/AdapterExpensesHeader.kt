package com.jp.test.dependencyinjectionapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.jp.test.dependencyinjectionapp.databinding.ItemUserExpenseTitleBinding
import com.jp.test.dependencyinjectionapp.fragments.HomeFragment
import com.jp.test.dependencyinjectionapp.models.DateCount
import com.jp.test.dependencyinjectionapp.models.UserExpenses
import java.text.SimpleDateFormat

class AdapterExpensesHeader(
    private val dataList: List<DateCount>,
    private val homeFragment: HomeFragment
) : RecyclerView.Adapter<AdapterExpensesHeader.ExpenseViewHolder>() {
    inner class ExpenseViewHolder(private val binding: ItemUserExpenseTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dateCount: DateCount) {
            binding.apply {
                tvDateTitle.text = dateCount.date.let { SimpleDateFormat("dd-MM-yyyy").format(it) }
                val adapter = AdapterUserExpenseList(dateCount.expenseList,homeFragment)
                rvExpense.adapter = adapter
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = ItemUserExpenseTitleBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ExpenseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
       holder.bind(dataList[position])
    }
}