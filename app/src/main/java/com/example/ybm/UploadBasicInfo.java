package com.example.ybm;

public class UploadBasicInfo
{
    String name, age, gender, belongyes, districtyes, currentyes;


    public UploadBasicInfo()
    {

    }


    public UploadBasicInfo(String name, String age, String gender, String belongyes, String districtyes, String currentyes) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.belongyes = belongyes;
        this.districtyes = districtyes;
        this.currentyes = currentyes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBelongyes() {
        return belongyes;
    }

    public void setBelongyes(String belongyes) {
        this.belongyes = belongyes;
    }

    public String getDistrictyes() {
        return districtyes;
    }

    public void setDistrictyes(String districtyes) {
        this.districtyes = districtyes;
    }

    public String getCurrentyes() {
        return currentyes;
    }

    public void setCurrentyes(String currentyes) {
        this.currentyes = currentyes;
    }
}
