package ee.proekspert.promuseum.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import ee.proekspert.promuseum.R

class SearchActivity : AppCompatActivity() {

    private lateinit var mCodeInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        mCodeInput = findViewById<EditText>(R.id.insert_code_field)

        mCodeInput.setOnEditorActionListener { textView, i, keyEvent ->


            true
        }
    }

}
