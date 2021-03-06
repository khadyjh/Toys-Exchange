package com.example.toys_exchange.fragmenrs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Toy;
import com.example.toys_exchange.R;
import com.example.toys_exchange.UI.ToyDetailActivity;
import com.example.toys_exchange.adapter.CustomToyAdapter;

import java.util.ArrayList;
import java.util.List;


public class ToyFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Toy> toyList = new ArrayList<>();
    private Handler handler;
    private View mView;

    String[] toyRadioButton =new String[]{"ALL","SELL","REQUEST","DONATION"};
    String[] toyRadioButton2 =new String[]{"Condition","NEW","USED"};
    Spinner mSpinnerCondition;
    Spinner mConditionSpinner;

    public ToyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_toy, container, false);

        setRecyclerView();

        mSpinnerCondition= mView.findViewById(R.id.spinner_condition_toy);
        mConditionSpinner = mView.findViewById(R.id.spinner_condition_toy2);

        setSpinnerWithRadioButton();
        setToyRadioButtonConditionListener();

        return mView;
    }

    @Override
    public void onResume() {

        setRecyclerView();
        setToyRadioButtonConditionListener();
        setSpinnerWithRadioButton();

        super.onResume();
    }


    private void setSpinnerWithRadioButton(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, toyRadioButton);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, toyRadioButton2);
        adapter1.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        mSpinnerCondition.setAdapter(adapter);
        mConditionSpinner.setAdapter(adapter1);
        mSpinnerCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String condition = parent.getItemAtPosition(position).toString();

                if(condition.equals("ALL")){
                    mConditionSpinner.setAdapter(adapter1);
                    mConditionSpinner.setVisibility(View.GONE);
                    toyList = new ArrayList<>();
                    Amplify.API.query(ModelQuery.list(Toy.class), success -> {
                                if (success.hasData()) {
                                    for (Toy toy : success.getData()) {
                                        Log.i("get toy ALL", toy.toString());
                                        toyList.add(toy);
                                    }
                                }
                                Bundle bundle = new Bundle();
                                bundle.putString("data", "done");

                                Message message = new Message();
                                message.setData(bundle);
                                handler.sendMessage(message);
                            }, error -> Log.e("error: ", "-> ", error)
                    );

                }else if (condition.equals("SELL")){
                    mConditionSpinner.setVisibility(View.VISIBLE);
                    toyList = new ArrayList<>();
                    Amplify.API.query(ModelQuery.list(Toy.class), success -> {
                                if (success.hasData()) {
                                    for (Toy toy : success.getData()) {
                                        if (toy.getTypetoy().toString().equals(condition)) {
                                            Log.i("get toy SELL ", toy.toString());
                                            toyList.add(toy);
                                        }
                                    }
                                }
                                Bundle bundle = new Bundle();
                                bundle.putString("data", "done");

                                Message message = new Message();
                                message.setData(bundle);
                                handler.sendMessage(message);
                            }, error -> Log.e("error: ", "-> ", error)
                    );

                }else{
                    mConditionSpinner.setAdapter(adapter1);
                    mConditionSpinner.setVisibility(View.GONE);
                    toyList = new ArrayList<>();
                    Amplify.API.query(ModelQuery.list(Toy.class), success -> {
                                if (success.hasData()) {
                                    for (Toy toy : success.getData()) {
                                        Log.i("get toy ", toy.toString());
                                        if (toy.getTypetoy().toString().equals(condition)){
                                            Log.i("get toy Req & don ", toy.toString());
                                            toyList.add(toy);
                                        }
                                    }
                                }
                                Bundle bundle = new Bundle();
                                bundle.putString("data", "done");

                                Message message = new Message();
                                message.setData(bundle);
                                handler.sendMessage(message);
                            }, error -> Log.e("error: ", "-> ", error)
                    );
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void setToyRadioButtonConditionListener(){
        mConditionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String condition = adapterView.getItemAtPosition(i).toString();

                if(condition.equals("NEW")){
                    toyList =new ArrayList<>();
                    Amplify.API.query(ModelQuery.list(Toy.class), success ->{
                                if(success.hasData()) {
                                    for (Toy toy : success.getData()) {
                                        if (toy.getTypetoy().toString().equals("SELL")
                                                && toy.getCondition().toString().equals("NEW")){
                                            Log.i("get toy NEW ", toy.toString());
                                            toyList.add(toy);

                                        }
                                    }
                                }
                                Bundle bundle =new Bundle();
                                bundle.putString("data", "done");

                                Message message = new Message();
                                message.setData(bundle);
                                handler.sendMessage(message);
                            },error -> Log.e("error: ","-> ",error)
                    );

                }else if(condition.equals("USED")){
                    toyList =new ArrayList<>();
                    Amplify.API.query(ModelQuery.list(Toy.class), success ->{
                                if(success.hasData()) {
                                    for (Toy toy : success.getData()) {
                                        Log.i("get toy ", toy.toString());
                                        if (toy.getTypetoy().toString().equals("SELL")
                                                && toy.getCondition().toString().equals("USED")){
                                            Log.i("get toy Used ", toy.toString());
                                            toyList.add(toy);

                                        }
                                    }
                                }
                                Bundle bundle =new Bundle();
                                bundle.putString("data", "done");

                                Message message = new Message();
                                message.setData(bundle);
                                handler.sendMessage(message);
                            },error -> Log.e("error: ","-> ",error)
                    );

                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void  setRecyclerView(){

        handler = new Handler(Looper.getMainLooper(), msg -> {

            recyclerView = mView.findViewById(R.id.recycler_view);

            CustomToyAdapter customAdapter = new CustomToyAdapter(toyList, new CustomToyAdapter.CustomClickListener() {
                @Override
                public void onTaskClickListener(int position) {
                    Intent intent = new Intent(getContext(), ToyDetailActivity.class);
                    intent.putExtra("toyName",toyList.get(position).getToyname());
                    intent.putExtra("description",toyList.get(position).getToydescription());
                    intent.putExtra("image",toyList.get(position).getImage());
                    intent.putExtra("price",toyList.get(position).getPrice());
                    intent.putExtra("condition",toyList.get(position).getCondition().toString());
                    intent.putExtra("contactInfo",toyList.get(position).getContactinfo());
                    intent.putExtra("id",toyList.get(position).getAccountToysId());
                    intent.putExtra("toyId",toyList.get(position).getId());
                    intent.putExtra("toyType",toyList.get(position).getTypetoy().toString());
                    startActivity(intent);
                }


                @Override
                public void ontItemClickListener(int position) {

                }
            });

            recyclerView.setAdapter(customAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));
            recyclerView.setHasFixedSize(true);
            return true;
        });

    }


}