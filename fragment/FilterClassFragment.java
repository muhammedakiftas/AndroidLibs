package com.sinifdefterimpro.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFlat;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.Wave;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sinifdefterimpro.R;
import com.sinifdefterimpro.adapter.CoursePlansAdapter;
import com.sinifdefterimpro.adapter.CreateSyllabusAdapter;
import com.sinifdefterimpro.adapter.DataListAdapter;
import com.sinifdefterimpro.adapter.ParentChildAdapter;
import com.sinifdefterimpro.adapter.PlansAdapter;
import com.sinifdefterimpro.expandable.ExpandableRecyclerAdapter;
import com.sinifdefterimpro.helper.CircleProgress;
import com.sinifdefterimpro.helper.ClassInfo;
import com.sinifdefterimpro.helper.ParentInfo;
import com.sinifdefterimpro.helper.PlansInfo;
import com.sinifdefterimpro.helper.SyllabusInfo;
import com.sinifdefterimpro.network.RestClient;
import com.sinifdefterimpro.storage.SaveSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


public class FilterClassFragment extends Fragment implements View.OnClickListener, ParentChildAdapter.ClickListener {

    private static final String TAG = "FilterClassFragment";

    public static final String DERS = "0";
    public static final String KURS = "1";
    public static final String EGZERSİZ = "2";

    public static final String CLASS_TYPE = "TYPE";

    private View mRootView;

    private TextView tvTitle;
    private TextView tvSubTitle;
    private TextView tvResult;

    private Spinner spinnerClass;

    private List<ClassInfo> classList;

    private Button brBack;
    private Button brRequest;

    private RecyclerView rvList;

    private List<ParentInfo> parentInfos;

    private ParentChildAdapter parentChildAdapter;

    private List<PlansInfo> coursePlansList;

    private SaveSharedPreferences prefs;

    private ProgressBar pBarPlans;

    public FilterClassFragment() {
        super();
    }

    public FilterClassFragment newInstance(String _type) {
        FilterClassFragment fragment = new FilterClassFragment();

        // arguments
        Bundle arguments = new Bundle();
        arguments.putString(CLASS_TYPE, _type);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_filter_class, container, false);

        initialView();
        initExpandableView();
        buttonListener();
        getAllClass();

        return mRootView;
    }

    private void initialView() {

        tvTitle = (TextView)mRootView.findViewById(R.id.tv_title);
        tvSubTitle = (TextView)mRootView.findViewById(R.id.tv_sub_title);
        tvResult = (TextView)mRootView.findViewById(R.id.tv_result);

        spinnerClass = (Spinner) mRootView.findViewById(R.id.spinner_filter_class);
        rvList = (RecyclerView) mRootView.findViewById(R.id.rv_list);

        brBack = (Button)mRootView.findViewById(R.id.br_back);
        brRequest = (Button) mRootView.findViewById(R.id.br_request);

        prefs = new SaveSharedPreferences(getContext());

        tvTitle.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Medium.ttf"));
        tvSubTitle.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf"));

        classList = new ArrayList<>();

        coursePlansList = new ArrayList<>();

        parentInfos = new ArrayList<>();


        pBarPlans = (ProgressBar) mRootView.findViewById(R.id.spin_kit);
        //DoubleBounce doubleBounce = new DoubleBounce();
        Wave indeterminate = new Wave();
        indeterminate.setColor(getResources().getColor(R.color.material_blue_700));
        pBarPlans.setIndeterminateDrawable(indeterminate);


        //CircleProgress mProgressView = (CircleProgress) mRootView.findViewById(R.id.progress);

        //mProgressView.setDuration(900);
        //mProgressView.setInterpolator(new BounceInterpolator());
        //mProgressView.setInterpolator(new LinearInterpolator());
        //mProgressView.setInterpolator(new AccelerateDecelerateInterpolator());
        //mProgressView.setInterpolator(new AccelerateInterpolator(1.0f));
        //mProgressView.startAnim();

        /*
        mProgressView.startAnim();
        mProgressView.stopAnim();
        mProgressView.reset();
        mProgressView.setDuration();
        mProgressView.setInterpolator();*/

    }

    private void buttonListener() {
        brBack.setOnClickListener(this);
        brRequest.setOnClickListener(this);
        parentChildAdapter.setClickListener(this);
    }

    private void initExpandableView() {

        parentChildAdapter = new ParentChildAdapter(getContext(), parentInfos);
        parentChildAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
            @UiThread
            @Override
            public void onParentExpanded(int parentPosition) {
                ParentInfo expandedParentInfo = parentInfos.get(parentPosition);
                //String toastMsg = getResources().getString(R.string.expanded, expandedParentInfo.getName());

            }

            @UiThread
            @Override
            public void onParentCollapsed(int parentPosition) {
                ParentInfo collapsedParentInfo = parentInfos.get(parentPosition);
                //String toastMsg = getResources().getString(R.string.collapsed, collapsedParentInfo.getName());

            }
        });

        rvList.setAdapter(parentChildAdapter);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void getAllClass() {

        if (!classList.isEmpty()) {
            classList.clear();
        }

        StringEntity entity = null;
        try {
            JSONObject parms = new JSONObject();
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("userLoginId", prefs.getUserId());

            parms.put("parms",jsonParams.toString());
            parms.put("cookieData","F+uF6AtV8+cROiFTZiS13B/8L85PNniYVrvcsQVLlPj5r5RJeBxHg8t9FjQaG0paM6esvw3U3mKPAgh20cf6/rU2nWLBKlXS");

            Log.e("LOG",""+parms.toString());

            entity = new StringEntity(parms.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "post");
        RestClient.post(getContext(), "/CallHelper?Method=CONST_ConstHelper.CONST_SinifListele", entity, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
                Log.e(TAG, response.toString());

                try {
                    if (!response.getBoolean("HasError")){
                        parseJSONAllClass(new JSONArray(response.getString("Data")));
                    }else{
                        Log.e(TAG,"HasError=TRUE");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Parse Control");
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("ERROR","onFailure");
            }
        });
    }

    private void parseJSONAllClass(JSONArray array) throws JSONException {
        classList.clear();
        for (int i = 0; i < array.length(); i++)
        {
            String classId = "", classAdi = "";

            JSONObject object = array.getJSONObject(i);
            //Log.e("objecjt = company fargment =",object.toString());

            Log.e("array["+i+"] = ",object.getString("Ad")+"");

            try{classId = object.getString("Id");                  }catch(Exception e){Log.e(TAG,"Failed to parse attrib.");}
            try{classAdi = object.getString("Ad");          }catch(Exception e){Log.e(TAG,"Failed to parse attrib.");}

            classList.add(new ClassInfo(classId, classAdi));
        }

        spinnerClassLoad();
/*
        if (pBarPlans.getVisibility() == View.VISIBLE){
            pBarPlans.setVisibility(View.GONE);
        }
*/
    }

    public void getCourses(String _sinifID) {

        if (!coursePlansList.isEmpty()) {
            coursePlansList.clear();
        }

        StringEntity entity = null;

        try {
            JSONObject parms = new JSONObject();
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("userLoginId", prefs.getUserId());
            jsonParams.put("sinifId", _sinifID);
            jsonParams.put("tur", getArguments().getString(CLASS_TYPE));

            parms.put("parms",jsonParams.toString());
            parms.put("cookieData",prefs.getCookieData());

            Log.e("LOG",""+parms.toString());

            entity = new StringEntity(parms.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //"/CallHelper?Method=KZN_KazanimHelper.KZN_DersCizelgesiListele"
        //"/login"
        Log.e(TAG, "post");
        RestClient.post(getContext(), "/CallHelper?Method=KZN_KazanimHelper.KZN_PlanOzelliklerListeleBySinifId", entity, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
                Log.e(TAG, response.toString());

                try {
                    if (!response.getBoolean("HasError")){
                        parseJSON(new JSONArray(response.getString("Data")));
                    }else{
                        Log.e(TAG,"HasError=TRUE");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Parse Control");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("ERROR","onFailure");
            }
        });
    }

    private void parseJSON(JSONArray array) throws JSONException {

        coursePlansList.clear();
        parentInfos.clear();

        for (int i = 0; i < array.length(); i++)
        {
            String id = "", ad = "", link = "", haftalikDersSaati = "", tur = "",
                    mail = "", dersAdi = "", sinifAdi="", createdTime = "", adSoyad="";

            JSONObject object = array.getJSONObject(i);
            //Log.e("objecjt = company fargment =",object.toString());

            Log.e("array["+i+"] = ",object.getString("Ad")+"");

            try{id = object.getString("Id");                  }catch(Exception e){Log.e(TAG,"Failed to parse attrib.");}
            try{ad = object.getString("Ad");          }catch(Exception e){Log.e(TAG,"Failed to parse attrib.");}
            try{link = object.getString("Link");                }catch(Exception e){Log.e(TAG,"Failed to parse attrib.");}
            try{haftalikDersSaati = object.getString("HaftalikDersSaati");                    }catch(Exception e){Log.e(TAG,"Failed to parse attrib.");}
            try{tur = object.getString("Tur");                    }catch(Exception e){Log.e(TAG,"Failed to parse attrib.");}
            try{mail = object.getString("mail");                    }catch(Exception e){Log.e(TAG,"Failed to parse attrib.");}
            try{dersAdi = object.getString("DersAdi");                    }catch(Exception e){Log.e(TAG,"Failed to parse attrib.");}
            try{sinifAdi = object.getString("SinifAdi");                    }catch(Exception e){Log.e(TAG,"Failed to parse attrib.");}
            try{createdTime = object.getString("CreatedTime");                    }catch(Exception e){Log.e(TAG,"Failed to parse attrib.");}
            try{adSoyad = object.getString("AdSoyad");                    }catch(Exception e){Log.e(TAG,"Failed to parse attrib.");}

            coursePlansList.add(new PlansInfo(id, ad, link, haftalikDersSaati, tur, mail, dersAdi, sinifAdi,
                    createdTime, adSoyad));
        }
        //coursePlansAdapter.setList(coursePlansList);
        //coursePlansAdapter.notifyDataSetChanged();



        for (int i=0; i<coursePlansList.size(); i++){

            boolean same = false;

            if (i==0){
                parentInfos.add(new ParentInfo(coursePlansList.get(i).getAd().toString(),new ArrayList<PlansInfo>(Arrays.asList(coursePlansList.get(i)))));
            }else{

                for (int j=0; j<parentInfos.size(); j++){

                    if (coursePlansList.get(i).getAd().toString().equals(parentInfos.get(j).getName().toString())){
                        parentInfos.get(j).addPlan(coursePlansList.get(i));
                        //parentInfos.set(j, (ParentInfo) Arrays.asList(parentInfos.get(j).getChildList(),coursePlansList.get(i)));
                        same = true;
                        break;
                    }
                }

                if (!same){
                    parentInfos.add(new ParentInfo(coursePlansList.get(i).getAd().toString(),new ArrayList<PlansInfo>(Arrays.asList(coursePlansList.get(i)))));
                }
            }

        }

        if (array.length()<1)
            tvResult.setText("Plan Bulunamadı.");
        else
            tvResult.setText(array.length()+" Plan Bulundu.");

        parentChildAdapter.setList(parentInfos);
        parentChildAdapter.notifyParentDataSetChanged(true);



        if (pBarPlans.getVisibility() == View.VISIBLE){
            pBarPlans.setVisibility(View.GONE);
        }

    }

    private void spinnerClassLoad() {

        List<String> list = new ArrayList<String>();

        for(int i=0; i< classList.size(); i++){//liste ekleniyor
            if (i==classList.size()-1)
                list.add(classList.get(i).getClassAd()+"");
            else
                list.add(classList.get(i).getClassAd() + ". Sınıf");
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),//liste adaptor e ataniyor
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerClass.setAdapter(dataAdapter);//liste tamamlandi, spinner doldu str1 gorunuyor
        spinnerClass.setSelection(0);
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("LOG","Spinner - Class:"+position);
                tvResult.setText(null);
                parentChildAdapter.clear();
                parentChildAdapter.notifyParentDataSetChanged(true);
                if (pBarPlans.getVisibility() == View.GONE){
                    pBarPlans.setVisibility(View.VISIBLE);
                }


                getCourses(classList.get(position).getClassId()+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    protected void showOpenRequestDialog(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before

        View view  = getActivity().getLayoutInflater().inflate(R.layout.dialog_request_program, null);
        dialog.setContentView(view);

        TextView dialogTitle = (TextView) view.findViewById(R.id.dialog_title);
        final TextInputEditText etContent = (TextInputEditText) view.findViewById(R.id.et_content);
        ButtonFlat flatAccept = (ButtonFlat) view.findViewById(R.id.button_accept);
        ButtonFlat flatCancel = (ButtonFlat) view.findViewById(R.id.button_cancel);

        flatAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestProgram(etContent.getText().toString());
                dialog.dismiss();
            }
        });
        flatCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.br_back:{
                Log.e("LOG","brBack");
                replaceFragment(new ChooseCourseFragment(), true);
                break;
            }
            case R.id.br_request:{
                Log.e("LOG","br_request");
                showOpenRequestDialog();
                break;
            }
            default:{

            }
        }
    }

    public void replaceFragment(Fragment _fragment, boolean isBack){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (isBack)
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        else
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.frame_container_tab2, _fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void itemClicked(View view, int parentPosition, int childPosition) {
        Log.e("LOG","parentPosition:"+parentPosition);
        Log.e("LOG","childPosition:"+childPosition);

        if (view.getId() == R.id.bt_add_plan) {
            /*Button b = (Button) view;
            b.setBackgroundColor(getResources().getColor(R.color.material_green_700));
            b.setText("Plan Sisteme Eklendi");
            b.setTextColor(getResources().getColor(R.color.md_white_1000));
*/
            Log.e("LOG","Plan added system");
            Log.e("LOG",""+parentInfos.get(parentPosition).getChildList().get(childPosition).getAd());

            planAdd(parentInfos.get(parentPosition).getChildList().get(childPosition).getId());

        }
    }

    public void planAdd(String _planId) {

        if (!coursePlansList.isEmpty()) {
            coursePlansList.clear();
        }

        StringEntity entity = null;

        try {
            JSONObject parms = new JSONObject();
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("userLoginId", prefs.getUserId());
            jsonParams.put("planOzelliklerId", _planId);

            parms.put("parms",jsonParams.toString());
            parms.put("cookieData","F+uF6AtV8+cROiFTZiS13B/8L85PNniYVrvcsQVLlPj5r5RJeBxHg8t9FjQaG0paM6esvw3U3mKPAgh20cf6/rU2nWLBKlXS");

            Log.e("LOG",""+parms.toString());

            entity = new StringEntity(parms.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "post");
        RestClient.post(getContext(), "/CallHelper?Method=KZN_KazanimHelper.KZN_SecilenPlanlarEkle", entity, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
                Log.e(TAG, response.toString());

                try {
                    if (!response.getBoolean("HasError")){
                        //Toast.makeText(getContext(), "Plan Sisteme Eklendi", Toast.LENGTH_SHORT).show();
                        LayoutInflater inflater = getActivity().getLayoutInflater();
                        View toastLayout = inflater.inflate(R.layout.custom_toast, (ViewGroup) mRootView.findViewById(R.id.custom_toast_layout));
                        TextView toastText = (TextView) toastLayout.findViewById(R.id.custom_toast_message);
                        toastText.setText("Plan sisteme eklendi..");
                        ImageView toastImage = (ImageView) toastLayout.findViewById(R.id.custom_toast_image);
                        toastImage.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_dialog_info));

                        Toast toast = new Toast(getContext());
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(toastLayout);
                        toast.show();
                    }else{
                        Log.e(TAG,"HasError=TRUE");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Parse Control");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("ERROR","onFailure");
            }
        });
    }

    public void requestProgram(String _content) {

        if (!coursePlansList.isEmpty()) {
            coursePlansList.clear();
        }

        StringEntity entity = null;

        try {
            JSONObject parms = new JSONObject();
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("userLoginId", prefs.getUserId());
            jsonParams.put("icerik", _content);
            jsonParams.put("dosya", "");

            parms.put("parms",jsonParams.toString());
            parms.put("cookieData","F+uF6AtV8+cROiFTZiS13B/8L85PNniYVrvcsQVLlPj5r5RJeBxHg8t9FjQaG0paM6esvw3U3mKPAgh20cf6/rU2nWLBKlXS");

            Log.e("LOG",""+parms.toString());

            entity = new StringEntity(parms.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "post");
        RestClient.post(getContext(), "/CallHelper?Method=USR_UserLoginHelper.USR_IletisimEkle", entity, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
                Log.e(TAG, response.toString());

                try {
                    if (!response.getBoolean("HasError")){
                        //Toast.makeText(getContext(), "Plan Sisteme Eklendi", Toast.LENGTH_SHORT).show();
                        LayoutInflater inflater = getActivity().getLayoutInflater();
                        View toastLayout = inflater.inflate(R.layout.custom_toast, (ViewGroup) mRootView.findViewById(R.id.custom_toast_layout));
                        TextView toastText = (TextView) toastLayout.findViewById(R.id.custom_toast_message);
                        toastText.setText("Teşekkürler.. Program isteği iletildi..");
                        ImageView toastImage = (ImageView) toastLayout.findViewById(R.id.custom_toast_image);
                        toastImage.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_dialog_info));

                        Toast toast = new Toast(getContext());
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(toastLayout);
                        toast.show();
                    }else{
                        Log.e(TAG,"HasError=TRUE");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Parse Control");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("ERROR","onFailure");
            }
        });
    }


}