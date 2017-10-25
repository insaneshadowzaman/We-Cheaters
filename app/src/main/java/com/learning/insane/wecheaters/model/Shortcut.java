package com.learning.insane.wecheaters.model;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;

public class Shortcut {
    private String id, name, description;
    private ArrayList<String> tags;
    private boolean leftCtrl, leftAlt, leftShift, rightShift, rightCtrl, rightAlt;
    private char key;
    private String previousRequiredShortcutId;
    private BitSet maskedControlKey;
    private int timeStamp;

    public Shortcut() {

    }

    public Shortcut(String name, String description, boolean leftCtrl, boolean leftAlt, char key) {
        this.name = name;
        this.description = description;
        this.leftCtrl = leftCtrl;
        this.leftAlt = leftAlt;
        this.key = key;
    }

    public boolean upload(FirebaseUser user) {
        String uid = user.getUid();
        DatabaseReference fdb =  FirebaseDatabase.getInstance().getReference().child("Shortcuts");
        HashMap<String, String> sMap = new HashMap<>();
        sMap.put("Name", name);
        sMap.put("Description", description);
        sMap.put("Hold Keys", maskedControlKey.toString());
        sMap.put("Owner", uid);

        String newId = fdb.push().getKey();
        fdb.child(newId).setValue(sMap);
        return true;
    }

    public Shortcut generateMaskControlKeyFromBools() {
        maskedControlKey = new BitSet(8);
        if(leftCtrl) maskedControlKey.set(0,true);
        if(leftAlt) maskedControlKey.set(1,true);
        if(leftShift) maskedControlKey.set(2,true);
        if(rightCtrl) maskedControlKey.set(3,true);
        if(rightAlt) maskedControlKey.set(4,true);
        if(rightShift) maskedControlKey.set(5,true);
        maskedControlKey.set(6,false);
        maskedControlKey.set(7,false);
        return this;
    }

    public Shortcut generateBoolsFromMaskControl() {
        leftCtrl = maskedControlKey.get(0);
        leftAlt = maskedControlKey.get(1);
        leftShift = maskedControlKey.get(2);
        rightCtrl = maskedControlKey.get(3);
        rightAlt = maskedControlKey.get(4);
        rightShift = maskedControlKey.get(5);
        return this;
    }

    public String getId() {
        return id;
    }

    public char getKey() {
        return key;
    }

    public String getPreviousRequiredShortcutId() {
        return previousRequiredShortcutId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isLeftCtrl() {
        return leftCtrl;
    }

    public void setLeftCtrl(boolean leftCtrl) {
        this.leftCtrl = leftCtrl;
    }

    public boolean isLeftAlt() {
        return leftAlt;
    }

    public void setLeftAlt(boolean leftAlt) {
        this.leftAlt = leftAlt;
    }

    public boolean isLeftShift() {
        return leftShift;
    }

    public void setLeftShift(boolean leftShift) {
        this.leftShift = leftShift;
    }

    public boolean isRightShift() {
        return rightShift;
    }

    public void setRightShift(boolean rightShift) {
        this.rightShift = rightShift;
    }

    public boolean isRightCtrl() {
        return rightCtrl;
    }

    public void setRightCtrl(boolean rightCtrl) {
        this.rightCtrl = rightCtrl;
    }

    public boolean isRightAlt() {
        return rightAlt;
    }

    public void setRightAlt(boolean rightAlt) {
        this.rightAlt = rightAlt;
    }
}
