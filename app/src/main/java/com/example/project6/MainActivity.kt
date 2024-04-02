package com.example.project6

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    data class Dish(val imageUrl: String, val dishName: String)

    private lateinit var rvDish: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvDish = findViewById(R.id.dish_list)

        findViewById<Button>(R.id.beef).setOnClickListener {
            handleButtonClick(R.id.beef)
        }

        findViewById<Button>(R.id.chicken).setOnClickListener {
            handleButtonClick(R.id.chicken)
        }

        findViewById<Button>(R.id.pork).setOnClickListener {
            handleButtonClick(R.id.pork)
        }

        findViewById<Button>(R.id.seafood).setOnClickListener {
            handleButtonClick(R.id.seafood)
        }

        findViewById<Button>(R.id.dessert).setOnClickListener {
            handleButtonClick(R.id.dessert)
        }
    }

    private fun handleButtonClick(buttonID: Int) {
        val buttonIDs = listOf(R.id.beef, R.id.chicken, R.id.pork, R.id.seafood, R.id.dessert)
        for (id in buttonIDs) {
            findViewById<Button>(id).visibility = View.GONE
        }

        when (buttonID) {
            R.id.beef -> getSeafoodDishImage("Beef")
            R.id.chicken -> getSeafoodDishImage("Chicken")
            R.id.pork -> getSeafoodDishImage("Pork")
            R.id.seafood -> getSeafoodDishImage("Seafood")
            R.id.dessert -> getSeafoodDishImage("Dessert")
        }
    }

    private fun getSeafoodDishImage(type: String) {
        val client = AsyncHttpClient()
        client["https://www.themealdb.com/api/json/v1/1/filter.php?c=$type", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Dish", "response successful$json")
                val dishImageArray = json.jsonObject.getJSONArray("meals")
                val dishes = mutableListOf<Dish>()
                for (i in 0 until dishImageArray.length()) {
                    val dishObject = dishImageArray.getJSONObject(i)
                    val strMealThumb = dishObject.getString("strMealThumb")
                    val strMeal = dishObject.getString("strMeal")
                    dishes.add(Dish(strMealThumb, strMeal))
                }
                val adapter = DishAdapter(dishes)
                rvDish.adapter = adapter
                rvDish.layoutManager = LinearLayoutManager(this@MainActivity)
                rvDish.addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Dish Error", errorResponse)
            }
        }]
    }
}