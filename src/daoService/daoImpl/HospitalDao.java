package daoService.daoImpl;

import daoService.HospitalDaoService;
import db.DataBase;
import models.Hospital;
import models.MyGenerator;
import models.Patient;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HospitalDao implements HospitalDaoService {

    private final DataBase dataBase;

    public HospitalDao(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public Hospital findById(Long hospitalId) {
        return dataBase.getAll().stream()
                .filter(hospital -> hospital.getId().equals(hospitalId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Hospital with id: " + hospitalId + " not found"));
    }

    @Override
    public Boolean add(Hospital hospital) {
        boolean b = dataBase.getAll().stream()
                .anyMatch(hospital1 -> hospital1.getHospitalName().equalsIgnoreCase(hospital.getHospitalName()));
        if (b) throw new RuntimeException("Hospital with name: " + hospital.getHospitalName() + " is already have");
        hospital.setId(MyGenerator.generatorHospital());
        return dataBase.save(hospital);
    }

    @Override
    public String delete(Long id) {
        Optional<Hospital> first = dataBase.getAll().stream()
                .filter(hospital -> hospital.getId().equals(id))
                .findFirst();
        if (first.isPresent()) {
            dataBase.remove(first.get());
            return "Successfully deleted";
        } else throw new RuntimeException("Hospital with id: " + id + " not found");
    }

    @Override
    public List<Hospital> getAll() {
        return dataBase.getAll();
    }

    @Override
    public List<Patient> getAllPatientFromHospital(Long id) {
        Optional<Hospital> first = dataBase.getAll().stream()
                .filter(hospital -> hospital.getId().equals(id))
                .findFirst();
        if (first.isPresent()) return first.get().getPatients();
        else throw new RuntimeException("Hospital with id: " + id + " not found");
    }

    @Override
    public Map<String, Hospital> getAllHospitalByAddress(String address) {
        List<Hospital> hospitals = dataBase.getAll().stream()
                .filter(hospital -> hospital.getAddress().equalsIgnoreCase(address))
                .toList();
        if (!hospitals.isEmpty()) {
            Hospital firstHospital = hospitals.get(0);
            return Collections.singletonMap(firstHospital.getAddress(), firstHospital);
        } else {
            throw new RuntimeException("Hospital with address: " + address + " not found");
        }
    }
}
