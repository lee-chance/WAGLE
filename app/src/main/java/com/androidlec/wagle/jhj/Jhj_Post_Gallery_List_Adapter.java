package com.androidlec.wagle.jhj;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.androidlec.wagle.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

public class Jhj_Post_Gallery_List_Adapter extends RecyclerView.Adapter<Jhj_Post_Gallery_List_Adapter.ViewHolder> {

    private ArrayList<Jhj_Gallery_DTO> data = null ;
    // Glide 라이브러리를 사용전에 전역변수로 선언
    private RequestManager manager;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            imageView = itemView.findViewById(R.id.Post_Gallery_List_Item_ImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    new AlertDialog.Builder(Jhj_Post_Gallery_List.Gallery_List_Context)
                            .setTitle("이미지 삭제하기")
                            .setIcon(R.drawable.waglelogo)
                            .setCancelable(false)   // 배경 눌러도 안닫힘
                            .setNegativeButton("삭제",new DialogInterface.OnClickListener() {
                                @SuppressLint("LongLogTag")
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String Seqno = data.get(position).getSeqno();
                                    String url = "http://192.168.0.82:8080/wagle/Post_Gallery_Delete.jsp?seqno=" + Seqno;

                                    connectionDeleteData(url);
                                    // 리스트 새로고침
                                    ((Jhj_Post_Gallery_List)Jhj_Post_Gallery_List.Gallery_List_Context).onResume();
                                }
                            })
                            .setPositiveButton("닫기", null)   // 닫기 누르면 사라짐
                            .show();
                }
            });
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    Jhj_Post_Gallery_List_Adapter(ArrayList<Jhj_Gallery_DTO> list, Activity activity) {
        data = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public Jhj_Post_Gallery_List_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.jhj_post_gallery_list_item, parent, false) ;
        Jhj_Post_Gallery_List_Adapter.ViewHolder vh = new Jhj_Post_Gallery_List_Adapter.ViewHolder(view) ;
        //선언한 contextdp parent.getContext 값을 넣어준다 (호출한 Context)
        context = parent.getContext();
        //Glide 라이브러리의 Context 값을 미리 넣어준다 ! 넣어주지 않고 한번에 호출해서 불러오면 값을 불러오기전에 CONTEXT 가 닫힘)
        manager = Glide.with(context);

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(Jhj_Post_Gallery_List_Adapter.ViewHolder holder, int position) {
        String imgurl = "http://192.168.0.82:8080/wagle/moimImgs/gallery/" + data.get(position).getImageName();
        manager.load(imgurl).into(holder.imageView);
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return data.size() ;
    }

    // FileDelete 시키기 위한 메소드
    private void connectionDeleteData(String urlAddr) {
        // Jsp 서버 전송
        try {
            Jhj_MySql_Insert_Delete_Update_NetworkTask insNetworkTask = new Jhj_MySql_Insert_Delete_Update_NetworkTask(Jhj_Post_Gallery_List.Gallery_List_Context, urlAddr);
            insNetworkTask.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

