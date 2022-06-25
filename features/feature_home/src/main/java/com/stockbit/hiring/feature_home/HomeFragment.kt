package com.stockbit.hiring.feature_home

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.stockbit.common.base.BaseFragment
import com.stockbit.common.base.BaseViewModel
import com.stockbit.hiring.feature_home.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val stockAdapter: StockAdapter = StockAdapter()

    override fun getViewModel(): BaseViewModel {
        return object : BaseViewModel() {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.menu_home)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        stockAdapter.stocks = listOf(
            Stock(1, "AALI", "Astra Agro Lestari Tbk.", 9050, "+200(+2.26%)"),
            Stock(2, "ADRO", "Adaro Energi Tbk.", 1125, "-5(-0.44%)"),
            Stock(3, "BKSL", "Sentul City Tbk.", 50, "0(0.00%)", status = StockStatus.NotAvailable),
            Stock(4, "BOGA-W", "Warrant Bintang Oto Global Tbk.", 1000, "0(0.00%)"),
            Stock(5, "BUMI", "Bumi Resource Tbk.", 50, "0(0.00%)")
        )

        binding.stockList.apply {
            adapter = stockAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL).apply {
                    setDrawable(
                        ColorDrawable().apply {
                            color = ContextCompat.getColor(requireContext(), R.color.colorLighterGray)
                        }
                    )
                }
            )
        }
    }
}
