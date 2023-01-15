package model.entities;

import java.io.Serializable;

public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    // ATRIBUTOS 
    private int id;
    private String name;
        
    // CONSTRUTORES
    public Department() {
    }

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Department(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // HASH e EQUALS
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Department other = (Department) obj;
        if (id != other.id)
            return false;
        return true;
    }

    // METODOS
    @Override
    public String toString() {
        return "Department [id=" + id + ", name=" + name + "]";
    }

    
    


}