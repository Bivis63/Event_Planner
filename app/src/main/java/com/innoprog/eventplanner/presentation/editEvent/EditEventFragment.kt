package com.innoprog.eventplanner.presentation.editEvent

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.innoprog.eventplanner.R
import com.innoprog.eventplanner.databinding.FragmentEditEventBinding
import com.innoprog.eventplanner.domain.model.Event
import com.innoprog.eventplanner.domain.model.ForecastLocation
import com.innoprog.eventplanner.presentation.EventApplication
import com.innoprog.eventplanner.presentation.ViewModelFactory
import com.innoprog.eventplanner.presentation.event.EventItemViewModel
import com.innoprog.eventplanner.presentation.weather.WeatherAdapter
import com.innoprog.eventplanner.presentation.weather.WeatherViewHolder
import com.innoprog.eventplanner.presentation.weather.state.ForecastState
import com.innoprog.eventplanner.presentation.weather.state.LocationState
import com.innoprog.eventplanner.utils.SELECTED_EVENT
import javax.inject.Inject


class EditEventFragment : Fragment(), WeatherViewHolder.ClickListener {

    private var _binding: FragmentEditEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: EventItemViewModel
    private lateinit var weatherAdapter: WeatherAdapter
    private var selectedTemperature = 0f
    private var imageName = ""
    private var locationId = 0

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
        _binding = FragmentEditEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(EventItemViewModel::class.java)

        initAdapter()

        viewModel.locationState.observe(viewLifecycleOwner) {
            renderLocation(it)
        }

        viewModel.temperatureState.observe(viewLifecycleOwner) {
            renderForecast(it)
        }

        initButton()
        maskForInputDate()

        val selectedEvent = arguments?.getSerializable(SELECTED_EVENT) as Event
        launchEditMode(selectedEvent)
    }

    private fun launchEditMode(selectedEvent: Event) {
        viewModel.getEventItem(selectedEvent.id)
        viewModel.eventItem.observe(viewLifecycleOwner) {
            with(binding) {
                textInputName.setText(it.eventName)
                textInputDescriptions.setText(it.description)
                textInputLocation.setText(it.location)
                textInputCity.setText(it.city)
                textInputDate.setText(it.data)

            }
            val query = binding.textInputCity.text.toString()
            viewModel.getLocation(query)
        }
        binding.saveBtn.setOnClickListener {
            viewModel.editEventItem(
                binding.textInputName.text?.toString(),
                binding.textInputDescriptions.text?.toString(),
                binding.textInputLocation.text?.toString(),
                binding.textInputCity.text?.toString(),
                binding.textInputDate.text?.toString(),
                selectedTemperature.toString(),
                imageName
                )
            findNavController().navigateUp()
        }
    }



    private fun initAdapter() {
        weatherAdapter = WeatherAdapter(this)
        binding.locations.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = weatherAdapter
        }
    }

    private fun initButton() {
        binding.saveBtn.isEnabled = false

        binding.buttonSearch.setOnClickListener {
            val query = binding.textInputCity.text.toString()
            viewModel.getLocation(query)
            binding.locations.visibility = View.VISIBLE

        }
    }

    private fun renderForecast(state: ForecastState) {
        when (state) {
            is ForecastState.Content -> {
                val date = binding.textInputDate.text.toString()
                val forecastForDate = state.currentInfo.forecast.find { it.date == date }

                selectedTemperature = forecastForDate?.maxTemp ?: 0f
                imageName = forecastForDate?.symbol ?: ""

            }

            is ForecastState.Error -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.failed_to_retrieve_temperature),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun renderLocation(state: LocationState) {
        when (state) {
            is LocationState.Content -> {
                weatherAdapter.setLocation(state.locationInfo.locations)
                binding.placeholderMessage.visibility =
                    if (state.locationInfo.locations.isEmpty()) View.VISIBLE else View.GONE
            }

            is LocationState.Error -> {
                Toast.makeText(requireContext(), getString(R.string.error_occurred), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun maskForInputDate() {
        binding.textInputDate.addTextChangedListener(object : TextWatcher {
            var current = ""
            var isDeleting = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                isDeleting = count > after
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() != current) {
                    val clean = s.toString().replace("""[^0-9]""".toRegex(), "")

                    val formatted = when {
                        clean.length >= 7 && !isDeleting -> {
                            val year = clean.substring(0, 4)
                            val month = clean.substring(4, 6)
                            val day = clean.substring(6)

                            "$year-$month-$day"
                        }
                        clean.length >= 5 && !isDeleting -> {
                            val year = clean.substring(0, 4)
                            val monthDay = clean.substring(4)

                            "$year-$monthDay"
                        }
                        else -> clean
                    }

                    current = formatted
                    binding.textInputDate.setText(current)
                    binding.textInputDate.setSelection(current.length)
                }
            }

            override fun afterTextChanged(s: Editable?) {}

        })
    }

    override fun onClick(location: ForecastLocation) {
        locationId=location.id
        viewModel.getTemperature(locationId)
        binding.locations.visibility = View.GONE
        binding.saveBtn.isEnabled = true

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}