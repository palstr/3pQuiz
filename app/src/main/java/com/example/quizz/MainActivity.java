package com.example.quizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView textViewPytanie;
    private Button buttonT;
    private Button buttonN;
    private Button buttonNext;
    private Button buttonHint;
    private ArrayList<Pytanie> pytania = new ArrayList<>();
    private int numerWyswietlanegoPytania = 0;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textViewPytanie = findViewById(R.id.textViewPytanie);
        buttonT = findViewById(R.id.buttonTak);
        buttonN = findViewById(R.id.buttonNie);
        buttonNext = findViewById(R.id.buttonNext);
        buttonHint = findViewById(R.id.buttonHint);
        imageView = findViewById(R.id.imageView);

        pytania.add(new Pytanie("Czy Niedźwiedź polarny je pingwiny?", false,"Zastanów się gdzie każde zwierzę żyje", R.drawable.polarbear));
        pytania.add(new Pytanie("Czy żyrafa może polizać się po uchu?", true, "Żyrafy mają bardzo długi język", R.drawable.giraffe));
        pytania.add(new Pytanie("Zebry śpią na leżąco?", false, "???", R.drawable.zebra));

        wyswietlPytanie(0);

        buttonT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pytania.get(numerWyswietlanegoPytania).isPoprawnaOdpowiedz()){
                    pytania.get(numerWyswietlanegoPytania).setCzyOdpowiedzianoPoprawnie(true);
                    Toast.makeText(MainActivity.this, "Dobra odpowiedź", Toast.LENGTH_SHORT).show();
                }
                else {

                    Toast.makeText(MainActivity.this, "Zła odpowiedź", Toast.LENGTH_SHORT).show();

                }
            }
        });

        buttonN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pytania.get(numerWyswietlanegoPytania).isPoprawnaOdpowiedz()){
                    pytania.get(numerWyswietlanegoPytania).setCzyOdpowiedzianoPoprawnie(true);
                    Toast.makeText(MainActivity.this, "Dobra odpowiedź", Toast.LENGTH_SHORT).show();

                }
                else {

                    Toast.makeText(MainActivity.this, "Zła odpowiedź", Toast.LENGTH_SHORT).show();

                }
            }
        });


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numerWyswietlanegoPytania++;

                if(numerWyswietlanegoPytania == pytania.size()){
                    textViewPytanie.setText("To już koniec quizu.");
                    buttonT.setVisibility(View.INVISIBLE);
                    buttonN.setVisibility(View.INVISIBLE);
                    buttonNext.setVisibility(View.INVISIBLE);
                    buttonHint.setVisibility(View.INVISIBLE);

                    textViewPytanie.setText("ilość zdobytych punktów: " + String.valueOf(podliczPunkty()) + "/3");
                }
                else{
                    wyswietlPytanie(numerWyswietlanegoPytania);
                }
            }
        });

        buttonHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencja = new Intent(MainActivity.this, PodpowiedzActivity.class);
                intencja.putExtra("PODPOWIEDZ", pytania.get(numerWyswietlanegoPytania).getPodpowiedz());
                startActivity(intencja);
            }
        });

    }

    private int podliczPunkty(){
        int suma = 0;
        for (Pytanie pytanko: pytania) {
            if (pytanko.isCzyOdpowiedzianoPoprawnie()){
                suma++;
            }
        }
        return suma;
    }

    private void wyswietlPytanie(int i){
        textViewPytanie.setText(pytania.get(i).getTresc());
        imageView.setImageResource(pytania.get(i).getIdObrazka());
    }


}