package com.yusufalicezik.drvirtual.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.yusufalicezik.drvirtual.AcilisActivity;
import com.yusufalicezik.drvirtual.HastaIslemleri.HastaAnaMenuActivity;
import com.yusufalicezik.drvirtual.HastaIslemleri.OnMuayeneMesajActivity;
import com.yusufalicezik.drvirtual.R;

public class FragmentDoktorGiris extends Fragment {
    Button butonGiris;
    private EditText doktorTc, doktorSifre;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lay2,container, false);

        butonGiris = v.findViewById(R.id.doktorGiris);
        doktorSifre = v.findViewById(R.id.doktorSifre);
        doktorTc = v.findViewById(R.id.tcDoktor);
        mAuth = FirebaseAuth.getInstance();


        butonGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doktorGirisYap(view);

            }
        });
        return v;

    }

    public void doktorGirisYap(View view){
            if (!doktorSifre.getText().toString().isEmpty() && !doktorTc.getText().toString().isEmpty()) {


                String email = doktorTc.getText().toString() + "@hotmail.com"; //giriş yapanın doktor/hasta olup olmadığını anlamak için gmail/hotmail
                String sifre = doktorSifre.getText().toString();

                mAuth.signInWithEmailAndPassword(email, sifre)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    AcilisActivity.girisYapan=2; //doktor ise 1, hasta ise 2 olacak.
                                    SharedPreferences ayarlar = PreferenceManager.getDefaultSharedPreferences(getContext());
                                    SharedPreferences.Editor editor = ayarlar.edit();
                                    editor.putInt("girisYapan",AcilisActivity.girisYapan);
                                    editor.commit();
                                    Intent intent = new Intent(getContext(), OnMuayeneMesajActivity.class);
                                    startActivity(intent);
                                    //finish();
                                } else {
                                    Toast.makeText(getContext(), "TC veya şifre yanlış", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
    }
}
