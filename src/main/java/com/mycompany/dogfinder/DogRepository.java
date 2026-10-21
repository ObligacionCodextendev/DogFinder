/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dogfinder;
import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author codextendev
 */
public class DogRepository {
    
    private ArrayList<Dog> dogs = new ArrayList<Dog>();
    private String fileName;
    private int nextId = 1;
    
    public DogRepository(String fileName) {
        this.fileName = fileName;
        loadFromFile();
    }
    
    public void add(String name, String breed, int age, String gender, String size, String status, String notes) {
        Dog d = new Dog(nextId, name, breed, age, gender, size, status, java.time.LocalDate.now().toString(), notes);
        nextId++;
        dogs.add(d);
        saveToFile();
    }
    
    public void update(int id, String name, String breed, int age, String gender, String size, String status, String notes) {
        Dog d = getById(id);
        if(d != null) {
            d.setName(name);
            d.setBreed(breed);
            d.setAge(age);
            d.setGender(gender);
            d.setSize(size);
            d.setStatus(status);
            d.setNotes(notes);
            saveToFile();
        }
    }
    
    public void delete(int id) {
        Dog d = getById(id);
        if(d != null) {
            dogs.remove(d);
            saveToFile();
        }
    }
    
    public Dog getById(int id) {
        for(int i = 0; i < dogs.size(); i++) {
            if(dogs.get(i).getId() == id) {
                return dogs.get(i);
            }
        }
        
        return null;
    }
    
    public ArrayList<Dog> getAll() {
        return dogs;
    }
    
    public ArrayList<Dog> search(String keyword) {
        ArrayList<Dog> result = new ArrayList<Dog>();
        String k = keyword.toLowerCase();
        for(int i = 0; i < dogs.size(); i++) {
            Dog d = dogs.get(i);
            if(d.getName().toLowerCase().contains(k) || d.getBreed().toLowerCase().contains(k) || d.getStatus().toLowerCase().contains(k)) {
                result.add(d);
            }
        }
        
        return result;
    }
    
    public int countTotal() {
        return dogs.size();
    }
    
    public int countByStatus(String status) {
        int count = 0;
        for(int i = 0; i < dogs.size(); i++) {
            if(dogs.get(i).getStatus().equals(status)) {
                count++;
            }
        }
        return count;
    }
    
    private void saveToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for(int i = 0; i < dogs.size(); i++) {
                Dog d = dogs.get(i);
                writer.write(d.getId() + "," + d.getName() + "," + d.getBreed() + "," + d.getAge() + "," + d.getGender() + "," + d.getSize() + "," + d.getStatus() + "," + d.getDateAdded() + "," + d.getNotes());
                writer.newLine();
            }
            writer.close();
        } catch(Exception e) {
            System.out.println("Could not save file: " + e.getMessage());
        }
    }
    
    private void loadFromFile() {
        java.io.File f = new java.io.File(fileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String line = reader.readLine();
            while (line != null) {
                String[] p = line.split(",", -1);
                if (p.length >= 9) {
                    Dog d = new Dog(Integer.parseInt(p[0]), p[1], p[2], Integer.parseInt(p[3]), p[4], p[5], p[6], p[7], p[8]);
                    dogs.add(d);
                    if (d.getId() >= nextId) {
                        nextId = d.getId() + 1;
                    }
                }
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println("Could not load file: " + e.getMessage());
        }
    }
    
    private String clean(String s) {
        return s == null ? "" : s.replace(",", " ").replace("\n", " ");
    }
}
