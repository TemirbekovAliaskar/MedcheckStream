package daoService;

import models.Hospital;
import models.Patient;

import java.util.List;
import java.util.Map;

public interface HospitalDaoService {

    Hospital findById(Long hospitalId);

    Boolean add(Hospital hospital);

    String delete(Long id);

    List<Hospital> getAll();

    List<Patient> getAllPatientFromHospital(Long id);

    Map<String, Hospital> getAllHospitalByAddress (String address);
}
