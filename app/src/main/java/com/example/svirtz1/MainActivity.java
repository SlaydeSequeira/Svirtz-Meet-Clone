package com.example.svirtz1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;
    private Button loginButton;

    // Retrofit service
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.button);

        // Initialize Retrofit service
        apiService = RetrofitClient.getClient("http://192.168.1.5:3000/").create(ApiService.class);

        // Set click listener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get username and password from EditText fields
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Create a login request object
                LoginRequest loginRequest = new LoginRequest(username, password);

                // Make the API call to the server
                Call<LoginResponse> call = apiService.login(loginRequest);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            // Login successful
                            LoginResponse loginResponse = response.body();
                            String message = loginResponse.getMessage();
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        } else {
                            // Login failed
                            Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        // Handle network error
                        Toast.makeText(MainActivity.this, "Network error. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
