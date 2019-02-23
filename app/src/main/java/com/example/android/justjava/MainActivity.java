package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

//JustJava source code
public class MainActivity extends AppCompatActivity {
    // Global variable "quantity"
    int quantity = 1;


    @Override
    //OnCreate method that calls the activity_main layout
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void submitOrder(View view) {
        //Edit text allows the user to change their name
        EditText textName = (EditText) findViewById(R.id.name_view);
        String name = textName.getText().toString();
        Log.wtf("MainActivity", "name" + name);
        //cream checkbox
        CheckBox creamCheckBox = (CheckBox) findViewById(R.id.cream_checkBox);
        boolean hasCream = creamCheckBox.isChecked();
        //chocolate checkbox
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkBox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        Log.wtf("MainActivity", "Has whipped cream: " + hasCream);
        int price = calcPrice(hasCream, hasChocolate);
        String priceMessage = orderSummary(name, price, hasCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL, "eni.odubawo@gmail.com");
        intent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { "eni.odubawo@gmail.com" });
        intent.putExtra(Intent.EXTRA_CC, "eni.odubawo@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava Order Summary for "+ name );
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    //increment method that increases quantity variable by one
    public void increment(View view) {
        quantity += 1;
        if (quantity >= 1 & quantity <= 100) {
            displayQuantity(quantity);


        } else {
            Toast.makeText(this, "You Can't Order More Than 100 Cups", Toast.LENGTH_SHORT).show();
            quantity = 100;
        }
    }

    //decrement method that reduces quantity variable by one
    public void decrement(View view) {
        quantity -= 1;
        if (quantity >= 1 & quantity <= 100) {
            displayQuantity(quantity);
        } else {
            Toast.makeText(this, "You Can't Order Less Than 1 Cup", Toast.LENGTH_SHORT).show();
            quantity = 1;

        }
    }

    public void reset(View view) {
        quantity = 1;
        displayQuantity(quantity);

    }

    // orderSummary method that takes the user order summary to the email intent
    private String orderSummary(String name, int price2, boolean hasWhippedCream, boolean hasChocolate) {

        //  String priceMessaage = "Name: " + name + "\n";
        String priceMessaage = getString(R.string.os_name, name + "\n");
        priceMessaage += "Add whipped cream?  : " + hasWhippedCream + "\n";
        priceMessaage += "Add Chocolate?  : " + hasChocolate + "\n";
        priceMessaage += "Quantity: " + quantity + "\n";
        priceMessaage += "Total: $ " + price2 + "\n";
        priceMessaage += " Thanks  \n";
        return priceMessaage;

    }

    //calcPrice method that updates price variable depending on what is ordered(plain, with cream,  with chocolate)
    private int calcPrice(boolean cream, boolean choco) {
        int price = 5;
        if (cream) {
            price += 2;
        }
        if (choco) {
            price += 3;
        }
        int rate = price * quantity;
        // displayPrice(rate);
        Toast.makeText(this, "TOTAL PRICE = $" + rate, Toast.LENGTH_LONG).show();
        return rate;


    }

    // displays quantity
    public void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


}
