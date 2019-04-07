package com.oxford.dictionary.ocr;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import java.io.*;
import java.net.MalformedURLException;

public class Result extends AppCompatActivity {
    public String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Log.d("FILETAG", "Activity progression successful");
        String term = getIntent().getStringExtra("Term");
        Log.d("FILETAG", "Got term string: "+ term);


        File f = createForm(term.split("\n"));

        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText(str);
        WebView webView = (WebView)findViewById(R.id.webview);
        webView.setWebChromeClient(new WebChromeClient());
        try {
            String url = ""+f.toURI().toURL();
            webView.loadUrl(url);
        } catch(MalformedURLException e){
            Log.e("FILETAG", "File read failed! Bad URL");
        }



    }
    public File createForm(String[] formData)
    {

        int i=0;
        String path = Environment.getExternalStorageDirectory().getPath();
        try
        {
            String all= "";
            File form = new File(path+"", "form.html");// where there html fine is located, must use double \
            FileWriter writer = new FileWriter(form);

            writer.write("<html>\n");
            all+="<html>\n";
            writer.write("<head>");
            all+="<head>\n";
            writer.write("<title>FormConverter");
            all+="<title>FormConverter\n";
            writer.write("</title>");
            all+="</title>\n";
            writer.write("</head>");
            all+="</head>\n";

            writer.write("<body>");
            all+="<body>\n";
            writer.write("<dev>");
            all+="<dev>\n";
            writer.write("<h2> Form</h2>\n");
            all+="<h2> Form</h2>\n";


            writer.write("<form>");
            all+="<form>";
            str=all;
            //this is how the multiple options are created, and how the form can be any size
            while(formData.length>i)
            {
                writer.write(formData[i] +" <input type=\"text\" name=\"name\" placeholder=\"Enter "+formData[i]+" here\">");
                all+=formData[i] +": <input type=\"text\" name=\"name\" placeholder=\"Enter "+formData[i]+" here\">\n";
                writer.write("<p></p>");
                all+="<p></p>\n";
                i++;
            }
            writer.write("");
            all+="\n";
            writer.write("</form>");
            all+="</form>\n";
            writer.write("</dev>");
            all+="</dev>\n";
            writer.write("</body>");
            all+="</body>\n";
            writer.write("</html>");
            all+="</html>\n";

            writer.flush();
            writer.close();

            FileOutputStream out = new FileOutputStream(form);
            byte[] data = all.getBytes();
            out.write(data);
            out.close();
            Log.e("FILETAG", "File Save : " + form.getPath());
            
            return form;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}



