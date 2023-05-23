package com.example.tmdbclient.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerOnScrollListener(
    private val mLinearLayoutManager: LinearLayoutManager,
    private val pageSize: Int = 5
) : RecyclerView.OnScrollListener() {
    private var firstVisibleItem: Int = 0
    private var visibleItemCount: Int = 0
    private var presentTotalItemCount: Int = 0
    private var previousTotal = 0 // The total number of items in the dataset after the last load
    private var loading = true // True if we are still waiting for the last set of data to load.
    private var visibleThreshold = pageSize // The minimum amount of items to have below your current scroll position before loading more.
    private var mFullTotalItemCount: Int? = null
    var currentPage = 1

    fun refresh() {
        currentPage = 1
        previousTotal = 0
        mFullTotalItemCount = null
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView.childCount
        presentTotalItemCount = mLinearLayoutManager.itemCount
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition()
        val lastVisibleItem = mLinearLayoutManager.findLastCompletelyVisibleItemPosition()

        if (loading) {
            if (presentTotalItemCount > previousTotal + pageSize - 1) {
                loading = false
                previousTotal = presentTotalItemCount
            }
        }
        if (!loading && presentTotalItemCount == lastVisibleItem + 1) {
            // End has been reached

            // Do something
            if (mFullTotalItemCount != null && presentTotalItemCount >= currentPage * pageSize && currentPage * pageSize < mFullTotalItemCount!!) {
                currentPage++

                onLoadMore(currentPage)

                loading = true
            }
        }
    }

    abstract fun onLoadMore(currentPage: Int)

    fun setTotalItemCount(count: Int) {
        mFullTotalItemCount = count
    }
}