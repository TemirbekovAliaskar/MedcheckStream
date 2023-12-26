package service.impl;

import daoService.daoImpl.DoctorDao;
import models.Doctor;
import service.DoctorService;
import service.GenericService;

import java.rmi.NotBoundException;
import java.util.List;

public class DoctorServiceImpl implements GenericService<Doctor>, DoctorService {

    private final DoctorDao dao;

    public DoctorServiceImpl(DoctorDao dao) {
        this.dao = dao;
    }

    @Override
    public Doctor findDoctorById(Long id) {
        try {
            return dao.findById(id);
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String assignDoctorToDepartment(Long departmentId, List<Long> doctorsId) {
        try {
            return dao.assignDoctorToDepartment(departmentId, doctorsId);
        } catch (RuntimeException  e){
            return e.getMessage();
        }
    }

    @Override
    public List<Doctor> getAllDoctorsByHospitalId(Long id) {
        try {
            return dao.getAllDoctorsByHospitalId(id);
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Doctor> getAllDoctorsByDepartmentId(Long id) {
        try {
            return dao.getAllDoctorsByDepartmentId(id);
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public String add(Long hospitalId, Doctor doctor) {
        try {
            return dao.add(hospitalId, doctor);
        } catch (RuntimeException e){
            return e.getMessage();
        }
    }

    @Override
    public String removeById(Long id) {
        try {
            return dao.remove(id);
        } catch (RuntimeException e){
            return e.getMessage();
        }
    }

    @Override
    public String updateById(Long id, Doctor doctor) {
        try {
            return dao.updateById(id, doctor);
        } catch (RuntimeException e){
            return e.getMessage();
        }
    }
}
