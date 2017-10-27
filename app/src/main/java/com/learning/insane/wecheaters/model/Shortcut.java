package com.learning.insane.wecheaters.model;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Map;

public class Shortcut {
    public String id, name, description, owner;
    public ArrayList<String> tags;
    public boolean leftCtrl, leftAlt, leftShift, rightShift, rightCtrl, rightAlt, caps, tab;
    public String key;
    public String previousRequiredShortcutId;
    public Map<String, String> timeStamp;


    public Shortcut() {
        //requied for firebase
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner(@Nullable String owner) {
        this.owner = owner;
    }

    public void setLeftCtrl(boolean leftCtrl) {
        this.leftCtrl = leftCtrl;
    }

    public void setLeftAlt(boolean leftAlt) {
        this.leftAlt = leftAlt;
    }

    public void setLeftShift(boolean leftShift) {
        this.leftShift = leftShift;
    }

    public void setRightShift(boolean rightShift) {
        this.rightShift = rightShift;
    }

    public void setRightCtrl(boolean rightCtrl) {
        this.rightCtrl = rightCtrl;
    }

    public void setRightAlt(boolean rightAlt) {
        this.rightAlt = rightAlt;
    }

    public void setCaps(boolean caps) {
        this.caps = caps;
    }

    public void setTab(boolean tab) {
        this.tab = tab;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setTimeStamp(Map<String, String> time) {
        this.timeStamp = time;
    }

    public void setPreviousRequiredShortcutId(String previousRequiredShortcutId) {
        this.previousRequiredShortcutId = previousRequiredShortcutId;
    }
}
