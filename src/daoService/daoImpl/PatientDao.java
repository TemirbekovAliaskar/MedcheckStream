package daoService.daoImpl;

import daoService.PatientDaoService;
import db.DataBase;
import models.Hospital;
import models.MyGenerator;
import models.Patient;

import java.util.*;
import java.util.stream.Collectors;

public class PatientDao implements PatientDaoService {
    private final DataBase dataBase;

    public PatientDao(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public Patient findById(Long patientId) {
        return getAll().stream()
                .filter(patient -> patient.getId().equals(patientId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Patient with id: " + patientId + " not found"));
    }

    @Override
    public Boolean add(Long hospitalId, Patient patient) {
        patient.setId(MyGenerator.generatorPatient());
        Optional<Hospital> first = dataBase.getAll().stream()
                .filter(hospital -> hospital.getId().equals(hospitalId))
                .findFirst();
        return first.map(hospital -> hospital.getPatients().add(patient))
                .orElseThrow(() -> new RuntimeException("Hospital with id: " + hospitalId + " not found"));
    }

    @Override
    public String delete(Long id) {
        List<Patient> patients = getAll();
        Optional<Patient> first = patients.stream()
                .filter(patient -> patient.getId().equals(id))
                .findFirst();
        if (first.isPresent()) {
            patients.remove(first.get());
            return "Successfully deleted";
        } else throw new RuntimeException("Patient with id: " + id + " not found");
    }

    @Override
    public List<Patient> getAll() {
        List<Patient> patients = new ArrayList<>();
        for (Hospital hospital : dataBase.getAll()) {
            patients.addAll(hospital.getPatients());
        }
        if (!patients.isEmpty()) return patients;
        else throw new RuntimeException("No patients found in any hospital");
    }

    @Override
    public String updateById(Long id, Patient patient) {
        Optional<Patient> first = getAll().stream()
                .filter(patient1 -> patient1.getId().equals(id))
                .findFirst();
        if (first.isPresent()) {
            Patient patient1 = first.get();
            patient1.setFirstName(patient.getFirstName());
            patient1.setLastName(patient.getLastName());
            patient1.setAge(patient.getAge());
            patient1.setGender(patient.getGender());
            return "Successfully updated";
        } else throw new RuntimeException("Patient with id: " + id + " not found");
    }

    @Override
    public String addPatientsToHospital(Long id, List<Patient> patients) {
        patients.forEach(patient -> patient.setId(MyGenerator.generatorPatient()));
        Optional<Hospital> hospitalOptional = dataBase.getAll().stream()
                .filter(hospital -> hospital.getId().equals(id))
                .findFirst();
        hospitalOptional.ifPresent(hospital -> hospital.getPatients().addAll(patients));
        if (hospitalOptional.isPresent()) return "Successfully added Patients";
        else throw new RuntimeException("Hospital with id: " + id + " not found");
    }

    @Override
    public Map<Integer, List<Patient>> getPatientByAge() {
        return dataBase.getAll().stream()
                .flatMap(hospital -> hospital.getPatients().stream())
                .collect(Collectors.groupingBy(Patient::getAge));
    }

    @Override
    public List<Patient> sortPatientsByAge(String ascOrDesc) {
        List<Patient> patients = dataBase.getAll().stream()
                .flatMap(hospital -> hospital.getPatients().stream())
                .toList();
        Comparator<Patient> comparator;
        if (ascOrDesc.equalsIgnoreCase("asc")) {
            comparator = Comparator.comparing(Patient::getAge);
        } else if (ascOrDesc.equalsIgnoreCase("desc")) {
            comparator = Comparator.comparing(Patient::getAge).reversed();
        } else throw new RuntimeException("Enter only asc or desc. You wrote: " + ascOrDesc);
        return patients.stream().sorted(comparator).toList();
    }
}
