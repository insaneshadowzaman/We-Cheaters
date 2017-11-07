package com.learning.insane.wecheaters.model;

public class Shortcut {

    public static final String APP_NAME = "appName";
    public static final String CAPS = "caps";
    public static final String DESCRIPTION = "description";
    public static final String ID = "id";
    public static final String KEY = "key";
    public static final String LEFT_CTRL = "leftCtrl";
    public static final String LEFT_ALT = "leftAlt";
    public static final String LEFT_SHIFT = "leftShift";
    public static final String RIGHT_CTRL = "rightCtrl";
    public static final String RIGHT_ALT = "rightAlt";
    public static final String RIGHT_SHIFT = "rightShift";
    public static final String OWNER = "owner";
    public static final String PREV_SHORTCUT = "previousRequiredShortcutId";
    public static final String VOTE_COUNT = "voteCount";

    public String id, name, description, owner, appName;
    public boolean leftCtrl, leftAlt, leftShift, rightCtrl, rightAlt, rightShift, caps;
    public String key;
    public String previousRequiredShortcutId;
    public int voteCount = 0;

    //required for firebase
    public Shortcut() {
    }

    //Required for ViewHolder
    public Shortcut(
            String name,
            String description,
            String owner,
            String appName,
            boolean leftCtrl,
            boolean leftAlt,
            boolean leftShift,
            boolean rightCtrl,
            boolean rightAlt,
            boolean rightShift,
            boolean caps,
            String key,
            String previousRequiredShortcutId)
    {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.appName = appName;
        this.leftCtrl = leftCtrl;
        this.leftAlt = leftAlt;
        this.leftShift = leftShift;
        this.rightShift = rightShift;
        this.rightCtrl = rightCtrl;
        this.rightAlt = rightAlt;
        this.caps = caps;
        this.key = key;
        this.previousRequiredShortcutId = previousRequiredShortcutId;
        voteCount = 0;
    }

    public Shortcut(String id) {
        this.id = id;
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

    public void setOwner(String owner) {
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

    public void setKey(String key) {
        this.key = key;
    }

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

    public String getKey() {
        return key;
    }

    public String getPreviousRequiredShortcutId() {
        return previousRequiredShortcutId;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return getName() + ": " + getId();
    }
}
