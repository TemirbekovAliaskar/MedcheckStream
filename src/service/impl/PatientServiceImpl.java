package service.impl;

import daoService.daoImpl.PatientDao;
import models.Patient;
import service.GenericService;
import service.PatientService;

import java.util.List;
import java.util.Map;

public class PatientServiceImpl implements GenericService<Patient>, PatientService {

    private final PatientDao dao;

    public PatientServiceImpl(PatientDao dao) {
        this.dao = dao;
    }

    @Override
    public String add(Long hospitalId, Patient patient) {
        try {
            dao.add(hospitalId, patient);
            return "Successfully added";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String removeById(Long id) {
        try {
            return dao.delete(id);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String updateById(Long id, Patient patient) {
        try {
            return dao.updateById(id, patient);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String addPatientsToHospital(Long id, List<Patient> patients) {
        try {
            return dao.addPatientsToHospital(id, patients);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public Patient getPatientById(Long id) {
        try {
            return dao.findById(id);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Map<Integer, List<Patient>> getPatientByAge() {
        return dao.getPatientByAge();
    }

    @Override
    public List<Patient> sortPatientsByAge(String ascOrDesc) {
        try {
            return dao.sortPatientsByAge(ascOrDesc);
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
