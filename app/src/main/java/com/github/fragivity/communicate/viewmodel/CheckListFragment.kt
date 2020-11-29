package com.github.fragivity.communicate.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.fragivity.AbsBaseFragment
import com.github.fragivity.communicate.CheckListAdapter
import com.github.fragivity.communicate.Item
import com.github.fragivity.push
import com.my.example.R
import kotlinx.android.synthetic.main.fragivity_comm_list.*

class CheckListFragment : AbsBaseFragment() {
    private val _viewModel: ListViewModel by activityViewModels()
    private val _adapter by lazy {
        CheckListAdapter { id, checked ->
            push(
                CheckItemFragment::class,
                bundleOf(
                    CheckItemFragment.ARGUMENTS_ID to id,
                    CheckItemFragment.ARGUMENTS_CHECKED to checked
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragivity_comm_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(recycler) {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = _adapter
        }

        _viewModel.liveData.observe(this.viewLifecycleOwner, object : Observer<List<Item>> {
            override fun onChanged(t: List<Item>?) {
                _adapter.submitList(t)
            }
        })
    }
}
