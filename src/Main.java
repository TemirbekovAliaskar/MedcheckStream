import daoService.daoImpl.DepartmentDao;
import daoService.daoImpl.DoctorDao;
import daoService.daoImpl.HospitalDao;
import daoService.daoImpl.PatientDao;
import db.DataBase;
import models.*;
import service.HospitalService;
import service.impl.DepartmentServiceImpl;
import service.impl.DoctorServiceImpl;
import service.impl.HospitalServiceImpl;
import service.impl.PatientServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DataBase dataBase = new DataBase();
        HospitalService hospital = new HospitalServiceImpl(new HospitalDao(dataBase));
        DepartmentServiceImpl department = new DepartmentServiceImpl(new DepartmentDao(dataBase));
        DoctorServiceImpl doctor = new DoctorServiceImpl(new DoctorDao(dataBase));
        PatientServiceImpl patient = new PatientServiceImpl(new PatientDao(dataBase));
        Scanner scanner = new Scanner(System.in);
        OuterLoop:
        while (true) {
            System.out.println("""
                0 ->  Exit                                         13 -> Remove Doctor by ID
                1 ->  Add Hospital                                 14 -> Update Doctor by ID
                2 ->  Find Hospital by ID                          15 -> Find Doctor by ID
                3 ->  Get all Hospitals                            16 -> Assign Doctors to Department
                4 ->  Get all Patient from Hospital                17 -> Get all Doctors by Hospital ID
              
              
              
                5 ->  Delete Hospital by ID                        18 -> Get all Doctors by Department ID
                6 ->  Get all Hospitals by address                 19 -> Add Patient to Hospital by ID
                7 ->  Add Department to Hospital by ID             20 -> Remove Patient by ID
                8 ->  Remove Department by ID                      21 -> Update Patient by ID
              
              
              
                9 ->  Update Department by ID                      22 -> Add Patients to Hospital by ID
                10 -> Get all Department by Hospital ID            23 -> Get Patient by ID
                11 -> Find Department by name                      24 -> Get Patient by age
                12 -> Add Doctor to Hospital by ID                 25 -> Sort Patient by age
                """);
            System.out.print("enter command: ");
                switch (scanner.nextInt()) {
                    case 0 -> {
                        break OuterLoop;
                    }
                    case 1 -> System.out.println(hospital.addHospital(new Hospital("Город N3", "Alamudun 6")));
                    case 2 -> System.out.println(hospital.findHospitalById(1L));
                    case 3 -> System.out.println(hospital.getAllHospital());
                    case 4 -> System.out.println(hospital.getAllPatientFromHospital(1L));
                    case 5 -> System.out.println(hospital.deleteHospitalById(1L));
                    case 6 -> System.out.println(hospital.getAllHospitalByAddress("Alamudun 6"));
                    case 7 -> System.out.println(department.add(1L, new Department("Хирургия")));
                    case 8 -> System.out.println(department.removeById(1L));
                    case 9 -> System.out.println(department.updateById(1L, new Department("Невропотология")));
                    case 10 -> System.out.println(department.getAllDepartmentByHospital(1L));
                    case 11 -> System.out.println(department.findDepartmentByName("Хирургия"));
                    case 12 -> System.out.println(doctor.add(1L, new Doctor("Aliaskar", "Temirbekov", Gender.MALE, 20)));
                    case 13 -> System.out.println(doctor.removeById(1L));
                    case 14 -> System.out.println(doctor.updateById(1L, new Doctor("Мырзайым","Келдибекова", Gender.FEMALE, 15)));
                    case 15 -> System.out.println(doctor.findDoctorById(1L));
                    case 16 -> System.out.println(doctor.assignDoctorToDepartment(1L, ids()));
                    case 17 -> System.out.println(doctor.getAllDoctorsByHospitalId(1L));
                    case 18 -> System.out.println(doctor.getAllDoctorsByDepartmentId(1L));
                    case 19 -> System.out.println(patient.add(1L, new Patient("Мырзайым", "Келдибекова", 20, Gender.FEMALE)));
                    case 20 -> System.out.println(patient.removeById(1L));
                    case 21 -> System.out.println(patient.updateById(1L, new Patient("Мирлан", "Арстанбеков", 21, Gender.MALE)));
                    case 22 -> System.out.println(patient.addPatientsToHospital(1L, patients()));
                    case 23 -> System.out.println(patient.getPatientById(1L));
                    case 24 -> System.out.println(patient.getPatientByAge());
                    case 25 -> System.out.println(patient.sortPatientsByAge("asc"));
                    default -> System.out.println("Enter correct choice!!!");
                }
            }
        }
    private static List<Patient> patients(){
        return new ArrayList<>(Arrays.asList(
                new Patient("Argen", "Беков", 24, Gender.MALE),
                new Patient("Гулум", "Садирова", 19, Gender.FEMALE),
                new Patient("Айзат", "Дуйшеева", 18, Gender.FEMALE),
                new Patient("Датка", "Маматжанова", 21, Gender.FEMALE)
        ));
    }
    private static List<Long> ids(){
        return new ArrayList<>(List.of(1L, 2L, 3L, 4L, 5L, 6L));
    }

    }
