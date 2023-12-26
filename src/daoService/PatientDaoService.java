package daoService;

import models.Patient;

import java.util.List;
import java.util.Map;

public interface PatientDaoService {

    Patient findById(Long patientId);

    Boolean add(Long hospitalId, Patient patient);

    String delete(Long id);

    List<Patient> getAll();

    String updateById(Long id, Patient patient);

    String addPatientsToHospital(Long id, List<Patient> patients);

    Map<Integer, List<Patient>> getPatientByAge();

    List<Patient> sortPatientsByAge(String ascOrDesc);
}
