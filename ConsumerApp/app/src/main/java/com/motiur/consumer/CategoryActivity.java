package com.motiur.consumer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


public class CategoryActivity extends AppCompatActivity {
    private String[] mainCategoryList = { "Select One", "Food", "Other" };
    private String[] menFashionList = { "Select One", "Offline","Online" };
    private String[] womenFashoinList = { "Select One", "Offline", "Offline" };
    private Button submitButton;

    //private String[] categoryList = {"Demo Category One", "Demo Category Two", "Demo Category Three", "Demo Category Four", "Demo Category Five"};
    private Spinner categorySpinner, categorySpinner2, mainCategorySprinner;
    private String mainCategory, subCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        categorySpinner =  findViewById(R.id.categorySpinnerId);
        categorySpinner2 = findViewById(R.id.categorySpinnerId2);
        mainCategorySprinner = findViewById(R.id.mainCategoryId);
        submitButton = findViewById(R.id.submitBtn);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ArrayAdapter<String> mainCategoryListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, mainCategoryList);
        mainCategorySprinner.setAdapter(mainCategoryListAdapter);


        final ArrayAdapter<String> demoCategoryListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, menFashionList);
        final ArrayAdapter<String> demoCategoryListAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, womenFashoinList);

        categorySpinner.setAdapter(demoCategoryListAdapter);
        categorySpinner2.setAdapter(demoCategoryListAdapter2);

        mainCategorySprinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mainCategory = mainCategoryList[position];
                Toast.makeText(getApplicationContext(), mainCategory + " selected", Toast.LENGTH_SHORT).show();
                if (mainCategory.equals("Food")){
                    categorySpinner.setVisibility(View.VISIBLE);
                    categorySpinner2.setVisibility(View.GONE);

                }else if(mainCategory.equals("Others")){
                    categorySpinner2.setVisibility(View.VISIBLE);
                    categorySpinner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subCategory = menFashionList[i];
                Toast.makeText(getApplicationContext(), subCategory+" selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Select None", Toast.LENGTH_SHORT).show();

            }
        });

        categorySpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subCategory = womenFashoinList[i];
                Toast.makeText(getApplicationContext(), subCategory+" selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Select None", Toast.LENGTH_SHORT).show();

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), mainCategory+" "+subCategory, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), CreateObjectionActivity.class);
                intent.putExtra("mainCategory", mainCategory);
                intent.putExtra("subCategory", subCategory);
                startActivity(new Intent(getApplicationContext(), CreateObjectionActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}