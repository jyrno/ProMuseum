package ee.proekspert.promuseum.search

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.widget.EditText
import android.widget.TextView
import ee.proekspert.promuseum.ItemActivity
import ee.proekspert.promuseum.R

class SearchActivity : AppCompatActivity() {

    private lateinit var inputField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        inputField = findViewById<EditText>(R.id.insert_code_field);

        findViewById<EditText>(R.id.insert_code_field).setOnEditorActionListener { view, i, keyEvent ->
            goToItem(inputField.text)
            true
        }
    }

    private fun goToItem(text: Editable?) {
        val intent = Intent(applicationContext, ItemActivity::class.java)
        ItemActivity.item_code = text
        startActivity(intent)

    }


}
