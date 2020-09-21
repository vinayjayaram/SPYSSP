package com.vj.yoga.spyss.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.vj.yoga.spyss.R;
import com.vj.yoga.spyss.activity.MainActivity;
import com.vj.yoga.spyss.fragments.BranchFragment;
import com.vj.yoga.spyss.model.BranchList;

import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class BranchListAdapter extends RecyclerView.Adapter<BranchListAdapter.MyViewHolder> {

    private List<BranchList> branchLists;
    private Context context;
    View view;
    private RecyclerViewClickListener mListener;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    private int[] branchImage = {
            R.drawable.branch1,
            R.drawable.branch2,
            R.drawable.branch3,
            R.drawable.branch4
    };

    public BranchListAdapter(List<BranchList> branchLists, Context context, RecyclerViewClickListener listener) {
        this.branchLists = branchLists;
        this.context = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(context).inflate(R.layout.fragment_branch, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final BranchList branchList = branchLists.get(position);
        holder.bind(branchLists.get(position), mListener);
        holder.branchName.setText(branchList.getBranchName());
        holder.branchContact.setText("Contact Number: " + branchList.getContactNumber());
        holder.branchTime.setText("Timings: " + branchList.getBranchTimings());
        holder.branchType.setText("Type: " + branchList.getBranchType());
        Random r = new Random();
        int i1 = r.nextInt(4 - 1) + 1;
        Glide.with(holder.itemView)
                .load(branchImage[i1])
                .placeholder(R.drawable.branch1)
                .into(holder.branchImage);


        holder.branchMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String OpenLat = "" + branchList.getLatitude();
                String OpenLong = "" + branchList.getLongitude();
                String URL = "https://www.google.com/maps/search/?api=1&query=" + OpenLat + "," + OpenLong + "&query_place_id=" + branchList.getPlaceId();
                Log.d("BranchListAdapter", "" + URL);
                Uri gmmIntentUri = Uri.parse(URL);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(mapIntent);
                } else {
                    Toast.makeText(context, "Unable to Open maps", Toast.LENGTH_LONG).show();
                }
            }
        });

        holder.branchShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Will Open Share", Toast.LENGTH_LONG).show();
            }
        });

        holder.branchCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+branchList.getContactNumber()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(context,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        boolean provideCallpermission = ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,Manifest.permission.CALL_PHONE);
                        if (provideCallpermission) {
                            Log.i(TAG, "Displaying permission rationale to provide additional context.");

                            showSnackbar(holder.itemView,R.string.permission_rationale_call, android.R.string.ok,
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            // Request permission
                                            startCallPermissionRequest();
                                        }
                                    });

                        } else {
                            Log.i(TAG, "Requesting permission");
                            startCallPermissionRequest();
                        }
                        return;
                    }
                }
                context.startActivity(callIntent);
            }
        });
    }
    private void startCallPermissionRequest() {
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{Manifest.permission.CALL_PHONE},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }
    private void showSnackbar(View v , final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(view.getRootView().findViewById(android.R.id.content),
                context.getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(context.getString(actionStringId), listener).show();
    }


    @Override
    public int getItemCount() {
        return branchLists.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder  {

         TextView branchName,branchContact,branchTime,branchType;
         LinearLayout branchShare,branchMaps,branchCall,snackBarLayout;
         ImageView branchImage;
         View itemView;
         MyViewHolder(@NonNull View itemView) {
            super(itemView);
            branchName = (TextView) itemView.findViewById(R.id.branchname);
            branchContact = (TextView) itemView.findViewById(R.id.branchcontact);
            branchTime = (TextView) itemView.findViewById(R.id.branchtime);
            branchType = (TextView) itemView.findViewById(R.id.branchtype);
            branchShare = (LinearLayout) itemView.findViewById(R.id.share_button);
            branchMaps = (LinearLayout) itemView.findViewById(R.id.google_button);
            branchCall = (LinearLayout) itemView.findViewById(R.id.button_call);
            branchImage = (ImageView) itemView.findViewById(R.id.branchimage);
            snackBarLayout= (LinearLayout) itemView.findViewById(android.R.id.content);
             this.itemView = itemView;

         }

        public void bind(final BranchList item, final RecyclerViewClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onClick(item);
                }
            });
        }

    }

    public interface RecyclerViewClickListener {

        void onClick(BranchList branchList);
    }
}
