package com.abelsalcedo.ubereats.models;

public class Delivery {

    String id;
    String name;
    String ape;
    String dni;
    String telf;
    String email;

    public Delivery() {
    }

    public Delivery(String id, String name, String ape, String dni, String telf, String email) {
        this.id = id;
        this.name = name;
        this.ape = ape;
        this.dni = dni;
        this.telf = telf;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApe() {
        return ape;
    }

    public void setApe(String ape) {
        this.ape = ape;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelf() {
        return telf;
    }

    public void setTelf(String telf) {
        this.telf = telf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
