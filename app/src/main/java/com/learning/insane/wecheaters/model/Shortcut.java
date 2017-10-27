package com.learning.insane.wecheaters.model;

import android.support.annotation.Nullable;

import java.util.Map;

public class Shortcut {
    public String id, name, description, owner, appName;
    public int voteCount = 0;
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

//    public void setTimeStamp(Map<String, String> time) {
//        this.timeStamp = time;
//    }

    public void setPreviousRequiredShortcutId(String previousRequiredShortcutId) {
        this.previousRequiredShortcutId = previousRequiredShortcutId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getOwner() {
        return owner;
    }

    public String getAppName() {
        return appName;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isLeftCtrl() {
        return leftCtrl;
    }

    public boolean isLeftAlt() {
        return leftAlt;
    }

    public boolean isLeftShift() {
        return leftShift;
    }

    public boolean isRightShift() {
        return rightShift;
    }

    public boolean isRightCtrl() {
        return rightCtrl;
    }

    public boolean isRightAlt() {
        return rightAlt;
    }

    public boolean isCaps() {
        return caps;
    }

    public boolean isTab() {
        return tab;
    }

    public String getKey() {
        return key;
    }

    public String getPreviousRequiredShortcutId() {
        return previousRequiredShortcutId;
    }

//    public Map<String, String> getTimeStamp() {
//        return timeStamp;
//    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return id + " {\n"
                + "name : " + name
                + "\n}\n";
    }
}
