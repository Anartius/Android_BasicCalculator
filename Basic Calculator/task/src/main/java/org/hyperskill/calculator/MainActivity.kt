package org.hyperskill.calculator

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnAttach
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText.setText("")

        clearButton.setOnClickListener {
            editText.setText("")
            editText.hint = "0"
            value1 = ""
            value2 = ""
            opType = ""
            isDot = false
        }

        equalButton.setOnClickListener {
            value2 = editText.text.toString()

            if (value1.isNotEmpty() &&
                    value2.isNotEmpty() &&
                    opType.isNotEmpty()) {

                val n1 = value1.toBigDecimal()
                val n2 = value2.toBigDecimal()
                val result = when(opType) {
                    "+" -> n1.plus(n2).stripTrailingZeros().toString()
                    "-" -> n1.minus(n2).stripTrailingZeros().toString()
                    "*" -> n1.multiply(n2).stripTrailingZeros().toString()
                    else -> if (n2 == BigDecimal.ZERO) {
                        "Error"
                    } else n1.divide(n2).stripTrailingZeros().toString()
                }

                editText.setText(result)



            }
        }
    }


    private var isDot = false
    private var value1 = ""
    private var value2 = ""
    private var opType = ""

    fun numberButtonEvent (view: View) {
        var output = editText.text.toString()
        val numButton = view as Button
        val numButtonValue = numButton.text.toString()
        editText.setText(output)

        if (editText.text.toString() != "") {
            if (numButton != dotButton) {
                output += numButtonValue
            } else if (!isDot){
                output += if (output.isEmpty()) {
                    "0."
                } else "."
                isDot = true
            }

        } else {
            output = if (numButton != dotButton) {
                numButtonValue
            } else {
                isDot = true
                "0$numButtonValue"
            }
        }

        editText.setText(output)
    }


    fun opButtonEvent (view: View) {
        value1 = editText.text.toString()
        isDot = false
        val opButton = view as Button
        if (value1.isEmpty() && opButton == subtractButton) {
            editText.setText("-")
            return
        }
        opType = opButton.text.toString()
        editText.setText("")
        editText.hint = value1
    }
}