package hcl.in.mylistapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView list1 ;
    ArrayList<Emp> empList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        empList = new ArrayList<>();
        addEmp() ;

        EmpAdapter adpEmp = new EmpAdapter(empList,this);
        list1 = (ListView) findViewById(R.id.listView);
        list1.setAdapter(adpEmp);

        list1.setOnItemClickListener(this);
    }

    private void addEmp() {
        Emp e1 ;
        for (int i=0 ; i<140; i++) {
            e1 = new Emp("Emp" + i, "Location" + i);
            empList.add(e1);
        }
        Log.d("EmpList", empList.toString());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Emp selEmp = empList.get(position);

    }
}
