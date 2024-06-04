package com.example.pr172

import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class main : AppCompatActivity()
{
    private lateinit var spinner: Spinner
    private lateinit var inputsLayout: LinearLayout
    private lateinit var calculateButton: Button
    private lateinit var resultText: TextView
    private lateinit var sharedPreferences: SharedPreferences
    companion object
    {
        private const val LAST_SELECTED_FIGURE = "last_selected_figure"
    }
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spinner = findViewById(R.id.spinner)
        inputsLayout = findViewById(R.id.inputs_layout)
        calculateButton = findViewById(R.id.calculate_button)
        resultText = findViewById(R.id.result_text)
        sharedPreferences = getPreferences(MODE_PRIVATE)
        val lastSelectedPosition = sharedPreferences.getInt(LAST_SELECTED_FIGURE, 0)
        ArrayAdapter.createFromResource(
            this,
            R.array.figure,
            android.R.layout.simple_spinner_item
        ).also{ adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.setSelection(lastSelectedPosition)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, position: Int, id: Long)
            {
                updateInputs(position)
                with(sharedPreferences.edit())
                {
                    putInt(LAST_SELECTED_FIGURE, position)
                    apply()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>)
            {
            }
        }
        calculateButton.setOnClickListener{
            calculatePerimeter()
        }
    }
    private fun updateInputs(position: Int)
    {
        inputsLayout.removeAllViews()
        when (position)
        {
            0 -> {
                addInputField(getString(R.string.A))
                addInputField(getString(R.string.B))
                addInputField(getString(R.string.C))
            }
            1 -> {
                addInputField(getString(R.string.width))
                addInputField(getString(R.string.height))
            }
            2 -> {
                addInputField(getString(R.string.radius))
            }
        }
    }
    private fun addInputField(hint: String)
    {
        val editText = EditText(this)
        editText.hint = hint
        editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        inputsLayout.addView(editText)
    }
    private fun calculatePerimeter()
    {
        val position = spinner.selectedItemPosition
        var perimeter = 0.0
        try {
            when (position)
            {
                0 -> {
                    val side1 = inputsLayout.getChildAt(0) as EditText
                    val side2 = inputsLayout.getChildAt(1) as EditText
                    val side3 = inputsLayout.getChildAt(2) as EditText
                    perimeter = side1.text.toString().toDouble() +
                            side2.text.toString().toDouble() +
                            side3.text.toString().toDouble()
                }
                1 -> {
                    val width = inputsLayout.getChildAt(0) as EditText
                    val height = inputsLayout.getChildAt(1) as EditText
                    perimeter = 2 * (width.text.toString().toDouble() + height.text.toString().toDouble())
                }
                2 -> {
                    val radius = inputsLayout.getChildAt(0) as EditText
                    perimeter = 2 * Math.PI * radius.text.toString().toDouble()
                }
            }
            resultText.text = getString(R.string.perimeter_result,perimeter)
        } catch (e: NumberFormatException) {
            resultText.text = getString(R.string.invalid_input)
        }
    }
}