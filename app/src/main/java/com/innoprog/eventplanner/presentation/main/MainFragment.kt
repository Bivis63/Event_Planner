package com.innoprog.eventplanner.presentation.main

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.innoprog.eventplanner.R
import com.innoprog.eventplanner.databinding.FragmentMainBinding
import com.innoprog.eventplanner.domain.model.Event
import com.innoprog.eventplanner.presentation.EventApplication
import com.innoprog.eventplanner.presentation.ViewModelFactory
import com.innoprog.eventplanner.utils.SELECTED_EVENT
import javax.inject.Inject


class MainFragment : Fragment(), EventItemViewHolder.ClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var eventAdapter: EventListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as EventApplication).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        navigationToAddNewEvent()
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.eventList.observe(viewLifecycleOwner) { eventList ->
            eventAdapter.submitList(eventList)
        }
    }

    private fun navigationToAddNewEvent() {
        binding.buttonAddEventItem.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_eventFragment)
        }
    }

    private fun setupSwipeListener(rvShopList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = eventAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteEventItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun initRecyclerView() {
        eventAdapter = EventListAdapter(this)
        binding.rvEventList.adapter = eventAdapter
        setupSwipeListener(binding.rvEventList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(eventItem: Event) {
        val bundle = Bundle().apply {
            putSerializable(SELECTED_EVENT, eventItem)
        }
        findNavController().navigate(
            R.id.action_mainFragment_to_editEventFragment,
            bundle
        )
    }

    override fun onLongClick(eventItem: Event) {
        initBottomSheet(eventItem)
    }

    private fun initBottomSheet(eventItem: Event) {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.TransparentDialog)
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet, null)

        bottomSheetView.findViewById<Button>(R.id.buttonCancel).setOnClickListener {
            viewModel.deleteEventItem(eventItem)
            bottomSheetDialog.dismiss()
        }

        bottomSheetView.findViewById<TextView>(R.id.visited).setOnClickListener {
            val updatedStatus = getString(R.string.visited)
            viewModel.editEventItem(eventItem, updatedStatus)
            bottomSheetDialog.dismiss()
        }

        bottomSheetView.findViewById<TextView>(R.id.skipped).setOnClickListener {
            val updatedStatus = getString(R.string.skipped)
            viewModel.editEventItem(eventItem, updatedStatus)
            bottomSheetDialog.dismiss()
        }

        with(bottomSheetDialog) {
            setContentView(bottomSheetView)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            show()
        }
    }
}