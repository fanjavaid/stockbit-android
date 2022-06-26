package com.stockbit.hiring.feature_home.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.stockbit.common.utils.ResourceUtils.getColor
import com.stockbit.hiring.feature_home.R
import com.stockbit.hiring.feature_home.databinding.ItemLoadingBinding
import com.stockbit.hiring.feature_home.databinding.ItemStockBinding

class StockAdapter : RecyclerView.Adapter<StockAdapter.ViewHolder>() {

    var stocks = mutableListOf<Stock?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = if (viewType == TYPE_ITEM) {
            ItemStockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        } else {
            ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stocks[position])
    }

    override fun getItemCount(): Int = stocks.size

    override fun getItemViewType(position: Int): Int {
        return if (stocks[position] == null) TYPE_LOADING else TYPE_ITEM
    }

    class ViewHolder(
        private val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val context: Context
            get() = binding.root.context

        fun bind(stock: Stock?) {
            if (binding is ItemStockBinding && stock != null) {
                val stockPercentage = stock.percentage.ifBlank { "0.0" }.toDouble()
                binding.apply {
                    stockTitleText.text = stock.name
                    stockDescriptionText.text = stock.desc
                    stockPriceText.text = stock.price
                    stockPercentageText.text = "${stock.changePrice}($stockPercentage%)"
                    stockPercentageText.setTextColor(
                        when {
                            stockPercentage < 0.0 -> getColor(context, R.color.colorDanger)
                            stockPercentage == 0.0 -> getColor(context, R.color.colorLightGray)
                            else -> getColor(context, R.color.colorAccent)
                        }
                    )
                    stockStatusText.visibility = if (stock.status == StockStatus.Available) {
                        View.GONE
                    } else View.VISIBLE
                }
            } else {
                binding as ItemLoadingBinding
            }
        }
    }

    companion object {
        const val TYPE_LOADING = 0
        const val TYPE_ITEM = 1
    }
}
