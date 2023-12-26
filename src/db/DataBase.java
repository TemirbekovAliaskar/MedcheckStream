package db;

import models.Hospital;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private List<Hospital> hospitals = new ArrayList<>();

    public Boolean save(Hospital hospital){
        return hospitals.add(hospital);
    }

    public Boolean remove(Hospital hospital){
        return hospitals.remove(hospital);
    }

    public List<Hospital> getAll(){
        return hospitals;
    }
}
