package daoService.daoImpl;

import daoService.DepartmentDaoService;
import db.DataBase;
import models.Department;
import models.Hospital;
import models.MyGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentDao implements DepartmentDaoService {

    private final DataBase dataBase;

    public DepartmentDao(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public Boolean add(Long hospitalId, Department department) {
        boolean b = dataBase.getAll().stream()
                .flatMap(hospital -> hospital.getDepartments().stream())
                .anyMatch(department1 -> department1.getDepartmentName().equalsIgnoreCase(department.getDepartmentName()));
        if (b) throw new RuntimeException("Department with name: "+department.getDepartmentName()+" already have");
        Optional<Hospital> first = dataBase.getAll().stream()
                .filter(hospital -> hospital.getId().equals(hospitalId))
                .findFirst();
        if (first.isPresent()) {
            Hospital hospital = first.get();
            department.setId(MyGenerator.generatorDepartment());
            return hospital.getDepartments().add(department);
        } else throw new RuntimeException("Hospital with id: " + hospitalId + " not found");
    }

    @Override
    public String remove(Long id) {
        Optional<Department> first = getAllDepartments().stream()
                .filter(department -> department.getId().equals(id))
                .findFirst();
        if (first.isPresent()){
            getAllDepartments().remove(first.get());
            return "Successfully deleted";
        }
        else throw new RuntimeException("Department with id: " + id + " not found");
    }

    @Override
    public List<Department> getAll(Long hospitalId) {
        Optional<Hospital> first = dataBase.getAll().stream()
                .filter(hospital -> hospital.getId().equals(hospitalId))
                .findFirst();
        return first.map(Hospital::getDepartments)
                .orElseThrow(() -> new RuntimeException("Hospital with id: " + hospitalId + " not found"));
    }

    @Override
    public List<Department> getAllDepartments() {
        List<Department> allDepartments = new ArrayList<>();
        for (Hospital hospital : dataBase.getAll()) allDepartments.addAll(hospital.getDepartments());
        if (!allDepartments.isEmpty()) return allDepartments;
        else throw new IllegalArgumentException("No departments found in any hospital");
    }

    @Override
    public String update(Long id, Department department) {
        Optional<Department> first = getAllDepartments().stream()
                .filter(department1 -> department1.getId().equals(id))
                .findFirst();
        if (first.isPresent()) {
            Department hospital = first.get();
            hospital.setDepartmentName(department.getDepartmentName());
            return "Successfully updated";
        } else throw new RuntimeException("Department with id: " + id + " not found!");
    }

    @Override
    public Department findDepartmentByName(String name) {
        return getAllDepartments().stream()
                .filter(department -> department.getDepartmentName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Department with name: " + name + " not found  !"));
    }
}
