package com.stockbit.hiring.feature_home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stockbit.common.utils.NumberUtils.formatThousand
import com.stockbit.hiring.feature_home.databinding.ItemStockBinding

data class Stock(
    val id: Long,
    val name: String,
    val desc: String,
    val price: Long,
    val percentage: String,
    val status: StockStatus = StockStatus.Available
)

sealed class StockStatus {
    object Available : StockStatus()
    object NotAvailable : StockStatus()
}

class StockAdapter : RecyclerView.Adapter<StockAdapter.ViewHolder>() {

    var stocks: List<Stock> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stocks[position])
    }

    override fun getItemCount(): Int = stocks.size

    class ViewHolder(
        private val binding: ItemStockBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(stock: Stock) {
            binding.apply {
                stockTitleText.text = stock.name
                stockDescriptionText.text = stock.desc
                stockPriceText.text = stock.price.formatThousand()
                stockPercentageText.text = stock.percentage
                stockStatusText.visibility = if (stock.status == StockStatus.Available) {
                    View.GONE
                } else View.VISIBLE
            }
        }
    }
}
