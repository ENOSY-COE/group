package com.example.group;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private static final int PICK_FILE_REQUEST = 1;
    private Uri selectedFileUri;
    private EditText editTextTitle;
    private TextView textViewFileName;
    private Button btnUploadProposal, btnSubmit;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize views
        editTextTitle = view.findViewById(R.id.editTextTitle);
        textViewFileName = view.findViewById(R.id.textViewFileName); // make sure it's added in XML
        btnUploadProposal = view.findViewById(R.id.btnUploadProposal);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        // Upload proposal click
        btnUploadProposal.setOnClickListener(v -> openFilePicker());

        // Submit click
        btnSubmit.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString().trim();

            if (title.isEmpty()) {
                Toast.makeText(getContext(), "Please enter a project title", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedFileUri == null) {
                Toast.makeText(getContext(), "Please select a proposal document", Toast.LENGTH_SHORT).show();
                return;
            }

            // For now, just show confirmation
            Toast.makeText(getContext(), "Project submitted!\nTitle: " + title, Toast.LENGTH_LONG).show();

            // TODO: Upload the file and title to Firebase/Backend
        });

        return view;
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select Proposal Document"), PICK_FILE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedFileUri = data.getData();
            if (textViewFileName != null) {
                textViewFileName.setText("Selected: " + selectedFileUri.getLastPathSegment());
            }
        }
    }
}
