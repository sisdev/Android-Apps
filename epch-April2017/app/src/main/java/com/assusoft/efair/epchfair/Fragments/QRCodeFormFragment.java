package com.assusoft.efair.epchfair.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.epch.efair.delhifair.R;

public class QRCodeFormFragment extends Fragment{
	EditText name,desgn,compName,addrss,contact1,contact2,email,website;
	Button btnGenerateQr;
	 View v;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
	    v=inflater.inflate(R.layout.contacts, container,false);
		name=(EditText) v.findViewById(R.id.name);
		desgn=(EditText) v.findViewById(R.id.designation);
		compName=(EditText) v.findViewById(R.id.companyName);
		addrss=(EditText) v.findViewById(R.id.address);
		contact1=(EditText) v.findViewById(R.id.contact1);
		contact2=(EditText) v.findViewById(R.id.contact2);
		email=(EditText) v.findViewById(R.id.email);
		website=(EditText) v.findViewById(R.id.website);
		
		btnGenerateQr=(Button) v.findViewById(R.id.converToQrCode);
		btnGenerateQr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*name.getText().equals("")||name.getText()==null||addrss.getText().equals("")||addrss.getText()==null||
						contact1.getText().equals("")||contact1.getText()==null*/
				if(name.getText().toString().equals("")||
						contact1.getText().toString().equals("")){
					Toast.makeText(getActivity(),"Name, Contact No can not be blank.",Toast.LENGTH_LONG).show();
					return;
				}
				
				
				StringBuffer content=new StringBuffer("+assusoft+");
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
					
				
				/*final FragmentActivity activity = getActivity();
				activity.getSupportFragmentManager().popBackStackImmediate();*/
					FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
			        
			        QRCodeEncoderFragment fragment = new  QRCodeEncoderFragment();
			        Bundle bundle = new Bundle();
			        bundle.putString("CONTENTS",content.toString());
			        fragment.setArguments(bundle);
			        ft.replace(R.id.content_frame, fragment,"QRENCODER");
			        //ft.addToBackStack(null); 
			        ft.commit();
			}
		});
		
		return v;
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		Commons.getMyTracker(getActivity(), "QRCodeFormFragment");
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

}
