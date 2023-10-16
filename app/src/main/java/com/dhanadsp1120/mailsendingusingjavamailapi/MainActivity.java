package com.dhanadsp1120.mailsendingusingjavamailapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MainActivity extends AppCompatActivity {
public EditText e;
public  String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e=findViewById(R.id.mail);
        email="dhanadspprojects@gmail.com";
        password="dhanasekar1120";
    }
    public void mailsend(View v)
    {
        Properties properties=new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        Session session=Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email,password);
            }
        });
        try {
            Message message= new MimeMessage(session);
            message.setFrom( new InternetAddress(email));

            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(e.getText().toString().trim()));
            message.setSubject("hello its dhana");
            message.setText("just checking java mail api from android");


            new SendMail().execute(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }

    }

    private class SendMail extends AsyncTask<Message,String,String> {
       private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=ProgressDialog.show(MainActivity.this,"PLease Wait","Sending mail...",true,false);
        }

        @Override
        protected String doInBackground(Message... messages) {

            try {
                Transport.send(messages[0]);
            return "success";
            } catch (MessagingException ex) {
                ex.printStackTrace();

                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if (s.equals("success"))
            {
                Toast.makeText(MainActivity.this,"sended",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();

            }
        }
    }
}