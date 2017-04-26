package com.assusoft.efair.qrcode;

import com.epch.efair.delhifair.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ContactForm extends Activity{
	EditText name,desgn,compName,addrss,contact1,contact2,email,website;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts);
		name=(EditText) findViewById(R.id.name);
		desgn=(EditText) findViewById(R.id.designation);
		compName=(EditText) findViewById(R.id.companyName);
		addrss=(EditText) findViewById(R.id.address);
		contact1=(EditText) findViewById(R.id.contact1);
		contact2=(EditText) findViewById(R.id.contact2);
		email=(EditText) findViewById(R.id.email);
		website=(EditText) findViewById(R.id.website);
		
		
	}
	
	public void generateQRCODE(View v){
		StringBuffer content=new StringBuffer();
		content.append(name.getText().equals("")?"":name.getText().toString());
		content.append("\n");
		content.append(desgn.getText().equals("")?"":desgn.getText().toString());
		content.append("\n");
		content.append(compName.getText().equals("")?"":compName.getText().toString());
		content.append("\n");
		content.append(addrss.getText().equals("")?"":addrss.getText().toString());
		content.append("\n");
		content.append(contact1.getText().equals("")?"":contact1.getText().toString());
		content.append("\n");
		content.append(contact2.getText().equals("")?"":contact2.getText().toString());
		content.append("\n");
		content.append(email.getText().equals("")?"":email.getText().toString());
		content.append("\n");
		content.append(website.getText().equals("")?"":website.getText().toString());
		Intent intent=new Intent(this,EncoderActivity.class);
		intent.putExtra("CONTENTS",content.toString());
		startActivity(intent);
	}

}
