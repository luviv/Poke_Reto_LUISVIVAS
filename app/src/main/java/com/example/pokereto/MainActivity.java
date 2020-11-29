package com.example.pokereto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pokereto.model.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText userName;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.userName);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(this::login);


    }

    private void login(View v) {
        String user = userName.getText().toString();

        if(user.trim().isEmpty()) {
            Toast.makeText(this, "Escribe un nombre de usuario", Toast.LENGTH_LONG).show();
        } else {
            Intent i = new Intent(this, PokeActivity.class);
            Query query = db.collection("users").whereEqualTo("name", user);
            query.get().addOnCompleteListener(
                    task -> {
                        if(task.getResult().getDocuments().size() > 0) {
                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                User user1 = document.toObject(User.class);
                                i.putExtra("id", user1.getId());
                                Toast.makeText(this, "Inicio de sesión con usuario existente", Toast.LENGTH_LONG).show();
                                startActivity(i);
                                finish();
                            }
                        } else {
                            User user1 = new User(user, UUID.randomUUID().toString());
                            db.collection("users").document(user1.getId()).set(user1);
                            i.putExtra("id", user1.getId());
                            Toast.makeText(this, "Usuario creado con éxito", Toast.LENGTH_LONG).show();
                            startActivity(i);
                            finish();
                        }
                    }
            );
        }
    }
}