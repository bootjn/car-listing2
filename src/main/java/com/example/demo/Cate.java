package com.example.demo;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Cate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String catetype;

    @OneToMany(mappedBy = "cate",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    public Set<Car> car;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatetype() {
        return catetype;
    }

    public void setCatetype(String catetype) {
        this.catetype = catetype;
    }

    public Set<Car> getCar() {
        return car;
    }

    public void setCar(Set<Car> car) {
        this.car = car;
    }
}
