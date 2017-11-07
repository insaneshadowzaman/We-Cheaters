package com.learning.insane.wecheaters.Util;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.learning.insane.wecheaters.ProfileActivity;
import com.learning.insane.wecheaters.R;
import com.learning.insane.wecheaters.model.Shortcut;
import com.learning.insane.wecheaters.model.User;

import net.cachapa.expandablelayout.ExpandableLayout;

public class ShortcutViewHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView description;
    private TextView vote;
    private TextView uploader;
    private LinearLayout linearLayout;
    private ExpandableLayout expandableLayout;
    private Button favButton;
    private Transaction.Handler incHandler;
    private Transaction.Handler decHandler;
    private Boolean isFav;
    private static final DatabaseReference userDb = FirebaseDatabase.getInstance().getReference()
                .child(Constants.USERS).child(
                        FirebaseAuth.getInstance().getCurrentUser().getUid()
                );

    public ShortcutViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.shortcut_name);
        description = itemView.findViewById(R.id.shortcut_description);
        vote = itemView.findViewById(R.id.shortcut_vote_count);
        uploader = itemView.findViewById(R.id.shortcut_uploader);
        linearLayout = itemView.findViewById(R.id.shortcut_key_layout);
        expandableLayout = itemView.findViewById(R.id.description_expander);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout.toggle();
            }
        };
        favButton = itemView.findViewById(R.id.shortcut_fav_button);
        itemView.setOnClickListener(clickListener);
    }

    public void setShortcut(final Shortcut shortcut) {

        final DatabaseReference shortcutDb = FirebaseDatabase.getInstance().getReference()
                .child(Constants.SHORTCUTS).child(shortcut.getId());

        setName(shortcut.getName());
        setDescription(shortcut.getDescription());
        setOwner(shortcut.getOwner());
        setVote(shortcut.getVoteCount());
        setHoldKey(shortcut);

        isFav = false;
        incHandler = new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Integer count = mutableData.getValue(Integer.class);
                if(count == null) count = 0;
                mutableData.setValue(count+1);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
            }
        };

        decHandler = new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Integer count = mutableData.getValue(Integer.class);
                if(count == null) mutableData.setValue(0);
                else mutableData.setValue(count-1);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
            }
        };

        // Set text to FAV button
        userDb.child(User.FAV_SHORTCUTS).child(shortcut.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String id = dataSnapshot.getValue(String.class);
                isFav = id != null;
                if(isFav) favButton.setText("UnFav");
                else favButton.setText("FAV");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        favButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                favButton.setEnabled(false);
                DatabaseReference uDb = userDb.child(User.FAV_SHORTCUTS).child(shortcut.getId());
                if(isFav) {
                    shortcutDb.child(Shortcut.VOTE_COUNT).runTransaction(decHandler);
                    uDb.setValue(null);
                } else {
                    shortcutDb.child(Shortcut.VOTE_COUNT).runTransaction(incHandler);
                    uDb.setValue(shortcut.getId());
                }
                favButton.setEnabled(true);
            }
        });
        favButton.setEnabled(true);


    }


    private void setName(String name) {
        this.name.setText(name);
    }

    private void setDescription(String description) {
        this.description.setText(description);
    }

    private void setOwner(final String ownerId) {
        FirebaseDatabase.getInstance().getReference().child(Constants.USERS).child(ownerId)
                .child(User.NAME).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ownerName = dataSnapshot.getValue(String.class);
                uploader.setText("Uploaded by " + ownerName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        uploader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(uploader.getContext(), ProfileActivity.class);
                intent.putExtra(User.ID, ownerId);
                uploader.getContext().startActivity(intent);
            }
        });
    }

    private void setVote(int vote) {
        this.vote.setText(String.valueOf(vote));
    }

    private void setHoldKey(Shortcut shortcut) {
        linearLayout.removeAllViews();
        if(shortcut.isLeftCtrl()) {
            TextView t = new TextView(linearLayout.getContext());
            t.setText("LCTRL");
            t.setGravity(Gravity.CENTER);
            t.setBackgroundResource(R.color.secondaryColor);
            float density = linearLayout.getResources().getDisplayMetrics().density;
            int padding = (int) (density*4);
            t.setPadding(padding, padding, padding, padding);
            t.setTextSize(15);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMarginEnd((int)(density*4));
            t.setLayoutParams(params);
            linearLayout.addView(t);
        }
        if(shortcut.isRightCtrl()) {
            TextView t = new TextView(linearLayout.getContext());
            t.setText("RCTRL");
            t.setGravity(Gravity.CENTER);
            t.setBackgroundResource(R.color.secondaryColor);
            float density = linearLayout.getResources().getDisplayMetrics().density;
            int padding = (int) (density*4);
            t.setPadding(padding, padding, padding, padding);
            t.setTextSize(15);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMarginEnd((int)(density*4));
            t.setLayoutParams(params);
            linearLayout.addView(t);
        }
        if(shortcut.isLeftAlt()) {
            TextView t = new TextView(linearLayout.getContext());
            t.setText("L-ALT");
            t.setGravity(Gravity.CENTER);
            t.setBackgroundResource(R.color.secondaryColor);
            float density = linearLayout.getResources().getDisplayMetrics().density;
            int padding = (int) (density*4);
            t.setPadding(padding, padding, padding, padding);
            t.setTextSize(15);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMarginEnd((int)(density*4));
            t.setLayoutParams(params);
            linearLayout.addView(t);
        }
        if(shortcut.isRightAlt()) {
            TextView t = new TextView(linearLayout.getContext());
            t.setText("R-ALT");
            t.setGravity(Gravity.CENTER);
            t.setBackgroundResource(R.color.secondaryColor);
            float density = linearLayout.getResources().getDisplayMetrics().density;
            int padding = (int) (density*4);
            t.setPadding(padding, padding, padding, padding);
            t.setTextSize(15);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMarginEnd((int)(density*4));
            t.setLayoutParams(params);
            linearLayout.addView(t);
        }
        if(shortcut.isLeftShift()) {
            TextView t = new TextView(linearLayout.getContext());
            t.setText("L-SHIFT");
            t.setGravity(Gravity.CENTER);
            t.setBackgroundResource(R.color.secondaryColor);
            float density = linearLayout.getResources().getDisplayMetrics().density;
            int padding = (int) (density*4);
            t.setPadding(padding, padding, padding, padding);
            t.setTextSize(15);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMarginEnd((int)(density*4));
            t.setLayoutParams(params);
            linearLayout.addView(t);
        }
        if(shortcut.isRightShift()) {
            TextView t = new TextView(linearLayout.getContext());
            t.setText("R-SHIFT");
            t.setGravity(Gravity.CENTER);
            t.setBackgroundResource(R.color.secondaryColor);
            float density = linearLayout.getResources().getDisplayMetrics().density;
            int padding = (int) (density*4);
            t.setPadding(padding, padding, padding, padding);
            t.setTextSize(15);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMarginEnd((int)(density*4));
            t.setLayoutParams(params);
            linearLayout.addView(t);
        }
        if(shortcut.isCaps()) {
            TextView t = new TextView(linearLayout.getContext());
            t.setText("CAPS");
            t.setGravity(Gravity.CENTER);
            t.setBackgroundResource(R.color.secondaryColor);
            float density = linearLayout.getResources().getDisplayMetrics().density;
            int padding = (int) (density*4);
            t.setPadding(padding, padding, padding, padding);
            t.setTextSize(15);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMarginEnd((int)(density*4));
            t.setLayoutParams(params);
            linearLayout.addView(t);
        }
        TextView t = new TextView(linearLayout.getContext());
        t.setText(shortcut.getKey());
        t.setGravity(Gravity.CENTER);
        t.setBackgroundResource(R.color.secondaryColor);
        float density = linearLayout.getResources().getDisplayMetrics().density;
        int padding = (int) (density*4);
        t.setPadding(padding, padding, padding, padding);
        t.setTextSize(15);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMarginEnd((int)(density*4));
        t.setLayoutParams(params);
        linearLayout.addView(t);
    }

}
