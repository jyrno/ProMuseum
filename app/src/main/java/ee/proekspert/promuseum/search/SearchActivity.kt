package ee.proekspert.promuseum.search

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import ee.proekspert.promuseum.ItemActivity
import ee.proekspert.promuseum.R
import ee.proekspert.promuseum.itemlist.ItemListActivity



class SearchActivity : AppCompatActivity() {

    private lateinit var inputField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        inputField = findViewById(R.id.insert_code_field)

        inputField.setOnEditorActionListener { view, i, keyEvent ->
            codeLookup(inputField.text.toString())
            true
        }
    }

    private fun codeLookup(barcode: String) {
        if (barcode.startsWith("02")){
            goToItem(barcode)
        }
        else if (barcode.startsWith("01")){
            goToLocation(barcode)
        }
        else {
            notFound()
        }
    }

    private fun notFound() {
        findViewById<TextView>(R.id.result_textview).text = "404 Not Found"
        inputField.selectAll()
    }

    private fun goToItem(text: String) {
        val intent = Intent(applicationContext, ItemActivity::class.java)
        ItemActivity.item_code = text
        startActivity(intent)
    }

    private fun goToLocation(text: String) {
        val intent = Intent(applicationContext, ItemListActivity::class.java)
        ItemListActivity.location_code = text
        startActivity(intent)
    }

}
