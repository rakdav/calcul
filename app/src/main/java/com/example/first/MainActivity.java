package com.example.first;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private TextView resultField;
    private EditText numberField;
    private TextView operationfield;
    private Double operand=null;
    private String lastOperation="=";
    private Button clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultField=findViewById(R.id.resultField);
        numberField=findViewById(R.id.number);
        operationfield=findViewById(R.id.operationField);
        clear=findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operand=null;
                lastOperation="";
                resultField.setText("");
                operationfield.setText("");
                numberField.setText("");
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("OPERATION",lastOperation);
        if(operand!=null) outState.putDouble("OPERAND",operand);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation=savedInstanceState.getString("OPERATION");
        operand=savedInstanceState.getDouble("OPERAND");
        operationfield.setText(lastOperation);
        resultField.setText(operand.toString());
    }

    public void onNumberClick(View view)
    {
        Button button=(Button)view;
        numberField.append(button.getText().toString());
        Toast toast=Toast.makeText(this,numberField.getText().toString(),Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,160);
        toast.setMargin(120,70);
        toast.show();
        if(lastOperation.equals("=")&&operand!=null) operand=null;
    }
    public void onOperationClick(View view)
    {
        Button button=(Button)view;
        String op=button.getText().toString();
        String number=numberField.getText().toString();

        Snackbar snackbar=Snackbar.make(view,op,Snackbar.LENGTH_LONG);
        snackbar.setDuration(6000);
        snackbar.show();

        if(number.length()>0)
        {
            number=number.replace(',','.');
            try
            {
                performOperation(Double.valueOf(number),op);
            }
            catch (NumberFormatException e)
            {
                numberField.setText("");
            }
            lastOperation=op;
            operationfield.setText(lastOperation);
        }
    }
    private void performOperation(Double number,String operation)
    {
        if(operand==null)
        {
            operand=number;
        }
        else
        {
            if(lastOperation.equals("=")) lastOperation=operation;
            switch (lastOperation){
                case "=": operand=number;break;
                case "/":if(number==0) operand=0.0;
                            else operand/=number;
                            break;
                case "+": operand+=number;break;
                case "-": operand-=number;break;
                case "*": operand*=number;break;
            }
        }
        resultField.setText(operand.toString().replace(".",","));
        numberField.setText("");
    }
}