package models;

public class MyGenerator {


    public static Long idHospital =0L;

    public static Long idDepartment = 0L;

    public static Long idDoctor = 0L;

    public static Long idPatient = 0L;


    public static Long generatorHospital(){
        return (++idHospital);
    }

    public static Long generatorDepartment(){
        return (++idDepartment);
    }

    public static Long generatorDoctor(){
        return (++idDoctor);
    }

    public static Long generatorPatient(){
        return (++idPatient);
    }
}
