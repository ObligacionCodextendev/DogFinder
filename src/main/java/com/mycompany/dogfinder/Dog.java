/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dogfinder;

/**
 *
 * @author codextendev
 */
public class Dog {
    
    private int id;
    private String name;
    private String breed;
    private int age;
    private String gender;
    private String size;
    private String status;
    private String dateAdded;
    private String notes;
    
    public Dog(int id, String name, String breed, int age, String gender, String size, String status, String dateAdded, String notes) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.size = size;
        this.status = status;
        this.dateAdded = dateAdded;
        this.notes = notes;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getBreed() {
        return breed;
    }
    
    public int getAge() {
        return age;
    }
    
    public String getGender() {
        return gender;
    }
    
    public String getSize() {
        return size;
    }
    
    public String getStatus() {
        return status;
    }
    
    public String getDateAdded() {
        return dateAdded;
    }
    
    public String getNotes() {
        return notes;
    }
    
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setBreed(String breed) {
        this.breed = breed;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public void setSize(String size) {
        this.size = size;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
}
