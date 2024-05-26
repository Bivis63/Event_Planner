package com.innoprog.eventplanner.presentation.event

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
import com.innoprog.eventplanner.databinding.FragmentEventBinding
import com.innoprog.eventplanner.domain.model.ForecastLocation
import com.innoprog.eventplanner.presentation.EventApplication
import com.innoprog.eventplanner.presentation.ViewModelFactory
import com.innoprog.eventplanner.presentation.weather.WeatherAdapter
import com.innoprog.eventplanner.presentation.weather.WeatherViewHolder
import com.innoprog.eventplanner.presentation.weather.state.ForecastState
import com.innoprog.eventplanner.presentation.weather.state.LocationState
import javax.inject.Inject


class EventFragment : Fragment(), WeatherViewHolder.ClickListener {

    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: EventItemViewModel
    private lateinit var weatherAdapter: WeatherAdapter
    private var selectedTemperature = 0f
    private var imageName = ""



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
        _binding = FragmentEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(EventItemViewModel::class.java)
        launchAddMode()

        initAdapter()

        maskForInputDate()

        viewModel.locationState.observe(viewLifecycleOwner) {
            renderLocation(it)
        }

        viewModel.temperatureState.observe(viewLifecycleOwner) {
            renderForecast(it)
        }

        initButton()

        checkingForTextInput()
    }

    private fun checkingForTextInput() {
        binding.textInputDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isDateFilled = !s.isNullOrBlank()
                binding.buttonSearch.isEnabled = isDateFilled
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun launchAddMode() {
        binding.saveBtn.setOnClickListener {
            viewModel.addEventItem(
                binding.textInputName.text?.toString(),
                binding.textInputDescriptions.text?.toString(),
                binding.textInputLocation.text?.toString(),
                binding.textInputCity.text?.toString(),
                binding.textInputDate.text?.toString(),
                selectedTemperature.toString(),
                imageName

            )
            navigateUp()
        }
    }

    private fun navigateUp() {
        findNavController().navigateUp()
    }

    private fun initAdapter() {
        weatherAdapter = WeatherAdapter(this)
        binding.locations.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = weatherAdapter
        }
    }

    private fun initButton() {
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
        viewModel.getTemperature(location.id)
        binding.locations.visibility = View.GONE

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}