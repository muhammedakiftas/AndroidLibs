package matas.com.hayalhanem.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import matas.com.hayalhanem.R;
import matas.com.hayalhanem.VideoAdapter;
import matas.com.hayalhanem.VideoInfo;

public class VideoFragment extends Fragment implements VideoAdapter.ClickListener {

    private View mRootView;

    @BindView(R.id.rv_video) RecyclerView rvVideo;

    private VideoAdapter videoAdapter;
    private List<VideoInfo> videoList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("LOG","VideoFragment");
        mRootView = inflater.inflate(R.layout.fragment_video, container, false);

        ButterKnife.bind(this, mRootView);

        initListVideos();

        return mRootView;
    }

    @OnClick(R.id.iv_back)
    public void onClickBack() {
        MyContains.replaceFragment(new HomePageFragment(),true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initListVideos() {
        videoList = new ArrayList<>();
        rvVideo.setLayoutManager(new LinearLayoutManager(getActivity()));
        videoAdapter = new VideoAdapter(getActivity());
        videoAdapter.setClickListener(this);
        rvVideo.setAdapter(videoAdapter);

        videoList.add(new VideoInfo("0","Mehmet Yıldız"));
        videoList.add(new VideoInfo("1","Yunus Oran"));
        videoList.add(new VideoInfo("2","Onur Kaplan"));
        videoList.add(new VideoInfo("3","Sinan Çetin"));
        videoList.add(new VideoInfo("4","İrfan Aykut"));
        videoList.add(new VideoInfo("5","Diğerleri"));

        videoAdapter.setList(videoList);
        //videoAdapter.notifyDataSetChanged();
    }


    @Override
    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void itemClicked(View view, int position) {

    }
}