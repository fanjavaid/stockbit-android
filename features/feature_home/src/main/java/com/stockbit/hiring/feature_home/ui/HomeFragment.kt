package com.stockbit.hiring.feature_home.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.stockbit.common.base.BaseFragment
import com.stockbit.common.base.BaseViewModel
import com.stockbit.hiring.feature_home.R
import com.stockbit.hiring.feature_home.databinding.FragmentHomeBinding
import com.stockbit.hiring.feature_home.ui.adapters.Stock
import com.stockbit.hiring.feature_home.ui.adapters.StockAdapter
import com.stockbit.model.CryptoCompare
import com.stockbit.repository.utils.Resource.Status.ERROR
import com.stockbit.repository.utils.Resource.Status.LOADING
import com.stockbit.repository.utils.Resource.Status.SUCCESS
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val stockAdapter: StockAdapter = StockAdapter()

    private val homeViewModel: HomeViewModel by viewModel()

    override fun getViewModel(): BaseViewModel = homeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun collectCryptoCompares() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.getTotalTopTierVolume().collect {
                    when (it.status) {
                        LOADING -> renderLoading()
                        SUCCESS -> renderStocks(it.data)
                        ERROR -> renderError()
                    }
                }
            }
        }
    }

    private fun renderEmpty() {
        binding.apply {
            emptyLayout.visibility = VISIBLE
            emptyTitleText.text = getString(R.string.empty_title)
            emptyDescText.text = getString(R.string.empty_desc)
        }
    }

    private fun renderError() {
        binding.emptyLayout.visibility = VISIBLE
        binding.loading.visibility = GONE
    }

    private fun renderLoading() {
        binding.emptyLayout.visibility = GONE
        binding.loading.visibility = VISIBLE
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

        collectCryptoCompares()
    }

    private fun setupRecyclerView() {
        binding.stockList.apply {
            adapter = stockAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL).apply {
                    setDrawable(
                        ColorDrawable().apply {
                            color =
                                ContextCompat.getColor(requireContext(), R.color.colorLighterGray)
                        }
                    )
                }
            )
        }
    }

    private fun renderStocks(data: CryptoCompare?) {
        binding.loading.visibility = GONE
        binding.emptyLayout.visibility = GONE

        if (data == null) {
            renderEmpty()
            return
        }

        stockAdapter.stocks = data.Data.map {
            Stock(
                id = it.CoinInfo.Id,
                name = it.CoinInfo.Name.orEmpty(),
                desc = it.CoinInfo.FullName.orEmpty(),
                price = it.DISPLAY.USD?.CHANGEHOUR.orEmpty(),
                percentage = it.DISPLAY.USD?.CHANGEPCTHOUR.orEmpty()
            )
        }
        stockAdapter.notifyDataSetChanged()
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}
