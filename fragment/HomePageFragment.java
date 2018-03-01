package matas.com.hayalhanem.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import matas.com.hayalhanem.R;

public class HomePageFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private View mRootView;

    @BindView(R.id.slider) SliderLayout mDemoSlider;

    @BindView(R.id.bt_akademi) Button btAkademi;

    @BindView(R.id.iv_video) ImageView ivVideo;
    @BindView(R.id.iv_galeri) ImageView ivGaleri;
    @BindView(R.id.iv_proje) ImageView ivProje;
    @BindView(R.id.iv_canli_yayin) ImageView ivCanliYayin;

    @BindView(R.id.tv_video) TextView tvVideo;
    @BindView(R.id.tv_galeri) TextView tvGaleri;
    @BindView(R.id.tv_proje) TextView tvProje;
    @BindView(R.id.tv_canli_yayin) TextView tvCanliYayin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("LOG","Tab1Fragment");
        mRootView = inflater.inflate(R.layout.fragment_homepage, container, false);

        ButterKnife.bind(this, mRootView);

        btAkademi.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/LinLibertine_RB.ttf"));

        tvVideo.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/LinLibertine_R.ttf"));
        tvGaleri.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/LinLibertine_R.ttf"));
        tvProje.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/LinLibertine_R.ttf"));
        tvCanliYayin.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/LinLibertine_R.ttf"));

        loadSlider();

        setRoundEdit(ivVideo);
        setRoundEdit(ivGaleri);
        setRoundEdit(ivProje);
        setRoundEdit(ivCanliYayin);

        return mRootView;
    }

    public void setRoundEdit(ImageView imageView) {
        Bitmap mbitmap = ((BitmapDrawable) getActivity().getResources().getDrawable(R.drawable.wallpaper2)).getBitmap();
        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 120, 80, mpaint);// Round Image Corner 100 100 100 100
        imageView.setImageBitmap(imageRounded);
    }

    @OnClick(R.id.iv_video)
    public void onClickVideo() {
        MyContains.replaceFragment(new VideoFragment(),false);
    }

    @OnClick(R.id.iv_galeri)
    public void onClickGaleri() {
        MyContains.replaceFragment(new GaleriFragment(),false);
    }

    @OnClick(R.id.bt_akademi)
    public void onClickAkademi() {

    }

    @OnClick(R.id.iv_proje)
    public void onClickProje() {

    }

    @OnClick(R.id.iv_canli_yayin)
    public void onClickCanliYayin() {

    }

    @Override
    public void onDestroy() {
        mDemoSlider.stopAutoCycle();
        super.onDestroy();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.e("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getActivity(), slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    public void loadSlider(){

        HashMap<String,String> file_maps = new HashMap<String, String>();

        file_maps.put("İrfan Aykut", "http://www.hayalhanem.com.tr/wp-content/uploads/2017/05/17966586_763939743788258_6762315700508613104_o.jpg");
        file_maps.put("Mehmet Yıldız", "http://www.hayalhanem.com.tr/wp-content/uploads/2017/05/18402962_777981865717379_6474720416879829195_n.jpg");
        file_maps.put("Yunus Oran", "http://www.hayalhanem.com.tr/wp-content/uploads/2017/05/17966035_768531766662389_4336102409035629455_o.jpg");
        file_maps.put("Onur Kaplan", "http://www.hayalhanem.com.tr/wp-content/uploads/2017/05/18076815_768531339995765_3302027545924912306_o.jpg");
        file_maps.put("Sinan Çetin", "https://i1.imgiz.com/rshots/9228/tesetture-girmekte-zorlaniyor-musun-sinan-cetin_9228876-37670_1920x1080.jpg");


        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(HomePageFragment.this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(3000);
        mDemoSlider.addOnPageChangeListener(HomePageFragment.this);
    }

    public void replaceFragment(Fragment _fragment, boolean isBack){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (isBack)
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        else
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.main_fragment, _fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}