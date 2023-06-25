package com.example.borderpane;

import java.io.Serializable;

public abstract class Manager implements Serializable {
    String name;
    String id;
    float hours;

    public Manager(String name, String id, float hours) {
        this.name = name;
        this.id = id;
        this.hours = hours;
    }

    abstract float calculerCout();
}

class Senior extends Manager {
    public Senior(String name, String id, float hours) {
        super(name, id, hours);
    }

    float calculerCout() {
        if (hours > 2000) {
            return 2500 * 2000 + (hours - 2000) * 3500;
        } else if (hours < 2000) {
            return hours * 2000;
        } else {
            return 2500 * 2000;
        }
    }
}

class Junior extends Manager {
    public Junior(String name, String id, float hours) {
        super(name, id, hours);
    }

    float calculerCout() {
        if (hours > 2000) {
            return 2000 * 2000 + (hours - 2000) * 3000;
        } else if (hours < 2000) {
            return hours * 1500;
        } else {
            return 2500 * 2000;
        }
    }
}
