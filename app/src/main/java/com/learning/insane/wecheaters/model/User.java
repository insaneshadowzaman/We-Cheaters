package com.learning.insane.wecheaters.model;

import java.util.ArrayList;

public class User {
    public static final String NAME = "name";
    public static final String FAV_SHORTCUTS = "favShortcuts";
    public static final String MAIL = "Mail";
    public static final String SHORTCUT_COUNT = "ShortcutCount";
    public static final String SHORTCUTS = "Shortcuts";
    public static final String ID = "id";

    private String name, id, email;
    private Integer shortcutCount;
    private ArrayList<String> shortcuts;
    private ArrayList<String> favShortcuts;

    public User(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<String> getShortcuts() {
        return shortcuts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getFavShortcuts() {
        return favShortcuts;
    }

    public void setShortcutCount(Integer shortcutCount) {
        this.shortcutCount = shortcutCount;
    }

    public Integer getShortcutCount() {
        return shortcutCount;
    }

    public void setShortcuts(ArrayList<String> shortcuts) {
        this.shortcuts = shortcuts;
    }



    public void setFavShortcuts(ArrayList<String> favShortcuts) {
        this.favShortcuts = favShortcuts;
    }
}
