package daoService.daoImpl;

import daoService.DoctorDaoService;
import db.DataBase;
import models.Department;
import models.Doctor;
import models.Hospital;
import models.MyGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoctorDao implements DoctorDaoService {
    private final DataBase dataBase;

    public DoctorDao(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public Doctor findById(Long doctorId) {
        return getAll().stream()
                .filter(doctor -> doctor.getId().equals(doctorId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Doctor with id: " + doctorId + " not found"));
    }

    @Override
    public String add(Long hospitalId, Doctor doctor) {

        Optional<Hospital> first = dataBase.getAll().stream()
                .filter(hospital -> hospital.getId().equals(hospitalId))
                .findFirst();
        if (first.isPresent()) {
            Hospital hospital = first.get();
            doctor.setId(MyGenerator.generatorDoctor());
            hospital.getDoctors().add(doctor);
            return "Successfully added";
        } else throw new RuntimeException("Hospital with id: " + hospitalId + " not found!");
    }

    @Override
    public String remove(Long id) {
        Optional<Doctor> first = getAll().stream()
                .filter(doctor -> doctor.getId().equals(id))
                .findFirst();
        if (first.isPresent()){
            getAll().remove(first.get());
            return "Successfully deleted";
        }
        else throw new RuntimeException("Doctor with id: " + id + " not found");
    }

    @Override
    public List<Doctor> getAll() {
        List<Doctor> doctors = new ArrayList<>();
        for (Hospital hospital : dataBase.getAll()) {
            doctors.addAll(hospital.getDoctors());
        }
        if (!doctors.isEmpty()) return doctors;
        else throw new RuntimeException("No doctors found in any hospital");
    }

    @Override
    public String assignDoctorToDepartment(Long departmentId, List<Long> doctorsId) {
        for (Hospital hospital : dataBase.getAll()) {
            for (Department department : hospital.getDepartments()) {
                if (departmentId.equals(department.getId())) {
                    List<Doctor> doctorsToAdd = hospital.getDoctors().stream()
                            .filter(doctor -> doctorsId.contains(doctor.getId()))
                            .toList();
                    if (doctorsToAdd.isEmpty()) {
                        throw new IllegalArgumentException("Doctors with ids: " + doctorsId + " not found!");
                    }
                    department.getDoctors().addAll(doctorsToAdd);
                    hospital.getDoctors().removeAll(doctorsToAdd);
                    return "Successfully assign doctors to department";
                }
            }
        }
        throw new RuntimeException("Department with id: " + departmentId + " not found");
    }

    @Override
    public List<Doctor> getAllDoctorsByHospitalId(Long id) {
        Optional<Hospital> first = dataBase.getAll().stream()
                .filter(hospital -> hospital.getId().equals(id))
                .findFirst();
        return first.map(Hospital::getDoctors)
                .orElseThrow(() -> new RuntimeException("Hospital with id: " + id + " not found"));
    }

    @Override
    public List<Doctor> getAllDoctorsByDepartmentId(Long id) {
        Optional<Department> first = dataBase.getAll().stream()
                .flatMap(hospital -> hospital.getDepartments().stream())
                .filter(department -> department.getId().equals(id))
                .findFirst();
        return first.map(Department::getDoctors)
                .orElseThrow(() -> new RuntimeException("Department with id: " + id + " not found"));
    }

    @Override
    public String updateById(Long id, Doctor doctor) {
        Optional<Doctor> first = getAll().stream()
                .filter(doctor1 -> doctor1.getId().equals(id))
                .findFirst();
        if (first.isPresent()) {
            Doctor doctor1 = first.get();
            doctor1.setFirstName(doctor.getFirstName());
            doctor1.setLastName(doctor.getLastName());
            doctor1.setGender(doctor.getGender());
            doctor1.setExperienceYear(doctor.getExperienceYear());
            return "Successfully updated";
        }
        Optional<Doctor> first1 = dataBase.getAll().stream()
                .flatMap(hospital -> hospital.getDepartments().stream())
                .flatMap(department -> department.getDoctors().stream())
                .filter(doctor1 -> doctor1.getId().equals(id))
                .findFirst();
        if (first1.isPresent()) {
            Doctor doctor1 = first1.get();
            doctor1.setFirstName(doctor.getFirstName());
            doctor1.setLastName(doctor.getLastName());
            doctor1.setGender(doctor.getGender());
            doctor1.setExperienceYear(doctor.getExperienceYear());
            return "Successfully updated";
        } else throw new RuntimeException("Doctor with id: " + id + " not found");
    }
}
