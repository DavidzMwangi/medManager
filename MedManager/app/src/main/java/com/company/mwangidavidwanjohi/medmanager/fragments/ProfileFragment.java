package com.company.mwangidavidwanjohi.medmanager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.company.mwangidavidwanjohi.medmanager.R;
import com.company.mwangidavidwanjohi.medmanager.models.UserProfile;
import com.raizlabs.android.dbflow.sql.language.SQLite;

public class ProfileFragment extends Fragment {
    EditText name,email;
    Button update_button;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set the title for the toolbar
        getActivity().setTitle("Profile");
        name=(EditText)view.findViewById(R.id.user_name);
        email=(EditText)view.findViewById(R.id.user_email);
        update_button=(Button) view.findViewById(R.id.update_button);
      final UserProfile user_details=  SQLite.select().from(UserProfile.class).querySingle();
        name.setText(user_details.name.toString());
        email.setText(user_details.email.toString());

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().matches("")|| email.getText().toString().matches("")){
                    Toast.makeText(getActivity(),"You cannot Update to an empty Value",Toast.LENGTH_LONG).show();

                }else{
//                    Toast.makeText(getActivity(),name.getText().toString()+email.getText().toString(),Toast.LENGTH_LONG).show();
                    user_details.name=name.getText().toString();
                    user_details.email=email.getText().toString();
                    user_details.save();

                    Toast.makeText(getContext(),"Update successful",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



       return inflater.inflate(R.layout.profile_fragment,container,false);



    }

}
