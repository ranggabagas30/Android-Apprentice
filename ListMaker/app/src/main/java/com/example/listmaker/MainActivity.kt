package com.example.listmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener {

    lateinit var listsRecyclerView: RecyclerView
    val listDataManager: ListDataManager = ListDataManager(this)
    companion object {
        val INTENT_LIST_KEY = "list"
        const val LIST_DETAIL_REQUEST_CODE = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listsRecyclerView = findViewById(R.id.listsRecyclerView)
        listsRecyclerView.layoutManager = LinearLayoutManager(this)

        val lists = listDataManager.readLists()
        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this)
        fab.setOnClickListener { view -> showCreateListDialog() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LIST_DETAIL_REQUEST_CODE) {
            data?.let {
                listDataManager.saveList(data.getParcelableExtra(INTENT_LIST_KEY))
                updateList()
            }
        }
    }

    private fun updateList() {
        val lists = listDataManager.readLists()
        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this)
    }

    private fun showCreateListDialog() {
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)

        val builder = AlertDialog.Builder(this)
        val listTitleEditText = EditText(this)
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)
        builder.setPositiveButton(positiveButtonTitle, { dialog, which ->
            val list = TaskList(listTitleEditText.text.toString())
            listDataManager.saveList(list) // save to SharedPreferences

            val recyclerAdapter = listsRecyclerView.adapter as ListSelectionRecyclerViewAdapter
            recyclerAdapter.addList(list)

            dialog.dismiss()
            showListDetail(list)
        })
        builder.create().show()
    }

    private fun showListDetail(list: TaskList) {
        val listDetailIntent = Intent(this, ListDetailActivity::class.java)
        listDetailIntent.putExtra(INTENT_LIST_KEY, list)
        startActivityForResult(listDetailIntent, LIST_DETAIL_REQUEST_CODE)
    }

    override fun listItemClicked(list: TaskList) {
        showListDetail(list)
    }
}
