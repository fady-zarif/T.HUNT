package com.treasurehunt.treasurehunt.model;


/**
 * Created by kerolos on 07/11/2016.
 */

public class PlayerInfo {

    private String avatarName;
    private String eMail;
    private String password;
    private String birthDate;
    private String phone;

    public PlayerInfo() {

    }

    public PlayerInfo(String avatarName, String eMail, String password, String phone, String birthDate) {
        this.avatarName = avatarName;
        this.eMail = eMail;
        this.password = password;
        this.phone = phone;
        this.birthDate = birthDate;
    }


    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public String geteMail() {
        return eMail;
    }

    public String getPassword() {
        return password;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getPhone() {
        return phone;
    }
}
