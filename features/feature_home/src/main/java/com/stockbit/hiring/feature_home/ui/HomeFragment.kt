package com.stockbit.hiring.feature_home.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.stockbit.common.base.BaseFragment
import com.stockbit.common.base.BaseViewModel
import com.stockbit.common.utils.ResourceUtils.getColor
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

class HomeFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentHomeBinding
    private val stockAdapter: StockAdapter = StockAdapter()

    private val homeViewModel: HomeViewModel by viewModel()

    private var isLoadMore = false
    private var page = 0
    private val endlessScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val linearLayoutManager = binding.stockList.layoutManager as? LinearLayoutManager
            if (!isLoadMore) {
                if (linearLayoutManager != null &&
                    linearLayoutManager.findLastCompletelyVisibleItemPosition() ==
                    stockAdapter.itemCount - 1
                ) {
                    loadMore()
                    isLoadMore = true
                }
            }
        }
    }

    override fun getViewModel(): BaseViewModel = homeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun collectCryptoCompares() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getData(page = 0)
            }
        }
    }

    private suspend fun getData(page: Int) {
        homeViewModel.getTotalTopTierVolume(page).collect {
            when (it.status) {
                LOADING -> if (!binding.swipeRefreshLayout.isRefreshing) {
                    renderLoading()
                }
                SUCCESS -> renderStocks(it.data)
                ERROR -> renderError()
            }
        }
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
        binding.swipeRefreshLayout.setOnRefreshListener(this)
        setupRecyclerView()

        collectCryptoCompares()
    }

    override fun onRefresh() {
        viewLifecycleOwner.lifecycleScope.launch {
            getData(page = 0)
        }
    }

    private fun setupRecyclerView() {
        binding.stockList.apply {
            adapter = stockAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null
            addItemDecoration(createDivider())
            addOnScrollListener(endlessScrollListener)
        }
    }

    private fun loadMore() {
        page++
        stockAdapter.stocks.add(null)
        binding.stockList.post {
            stockAdapter.notifyItemInserted(stockAdapter.itemCount - 1)
            viewLifecycleOwner.lifecycleScope.launch {
                getData(page = page)
            }
        }
    }

    private fun renderStocks(data: CryptoCompare?) {
        binding.loading.visibility = GONE
        binding.emptyLayout.visibility = GONE

        if (data == null) {
            renderEmpty()
            return
        }

        // Remove load more loading item
        if (stockAdapter.stocks.size > 0) {
            stockAdapter.stocks.removeAt(stockAdapter.stocks.size - 1)
            stockAdapter.notifyItemRemoved(stockAdapter.stocks.size)
            isLoadMore = false
        }

        val items = data.data.map {
            Stock(
                id = it.coinInfo.id,
                name = it.coinInfo.name.orEmpty(),
                desc = it.coinInfo.fullName.orEmpty(),
                price = it.display.usdCurrency?.price.orEmpty(),
                changePrice = it.display.usdCurrency?.changeHour.orEmpty(),
                percentage = it.display.usdCurrency?.changePercentageHour.orEmpty()
            )
        }

        if (binding.swipeRefreshLayout.isRefreshing) {
            binding.swipeRefreshLayout.isRefreshing = false
            stockAdapter.stocks = items.toMutableList()
        } else {
            stockAdapter.stocks.addAll(items)
        }

        stockAdapter.notifyItemRangeChanged(
            stockAdapter.itemCount,
            (stockAdapter.itemCount + HomeViewModel.LIMIT)
        )
    }

    private fun renderEmpty() {
        if (page == 0) { // Show info only first page empty
            binding.apply {
                emptyLayout.visibility = VISIBLE
                emptyTitleText.text = getString(R.string.empty_title)
                emptyDescText.text = getString(R.string.empty_desc)
            }
        }
    }

    private fun renderError() {
        binding.apply {
            swipeRefreshLayout.isRefreshing = false
            emptyLayout.visibility = VISIBLE
            loading.visibility = GONE

            emptyTitleText.text = getString(R.string.error_title)
            emptyDescText.text = getString(R.string.error_desc)
        }
    }

    private fun renderLoading() {
        binding.emptyLayout.visibility = GONE
        binding.loading.visibility = VISIBLE
    }

    private fun createDivider(): RecyclerView.ItemDecoration {
        return DividerItemDecoration(
            requireContext(),
            DividerItemDecoration.VERTICAL
        ).apply {
            setDrawable(
                ColorDrawable().apply {
                    color = getColor(requireContext(), R.color.colorLighterGray)
                }
            )
        }
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}
