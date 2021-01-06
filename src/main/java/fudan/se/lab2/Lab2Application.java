package fudan.se.lab2;

import fudan.se.lab2.domain.*;

//import fudan.se.lab2.repository.*;
import fudan.se.lab2.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.java2d.pipe.AAShapePipe;

import java.beans.beancontext.BeanContext;
import java.util.*;

/**
 * Welcome to 2020 Software Engineering Lab2.
 * This is your first lab to write your own code and build a spring boot application.
 * Enjoy it :)
 *
 * @author LBW
 */
@SpringBootApplication
public class Lab2Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab2Application.class, args);
    }

    /**
     * This is a function to create some basic entities when the application starts.
     * Now we are using a In-Memory database, so you need it.
     * You can change it as you like.
     */
    @Bean
    public CommandLineRunner dataLoader(TreatmentAreaRepository treatmentAreaRepository,
                                        DoctorRepository doctorRepository,
                                        HeadNurseRepository headNurseRepository,
                                        WardNurseRepository wardNurseRepository,
                                        EmergencyNurseRepository emergencyNurseRepository,
                                        PasswordEncoder passwordEncoder,
                                        PatientRepository patientRepository,
                                        BedRepository bedRepository
                                        ) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                //4 轻症 2 重症 1危重症
                //Treatment_area treatment_area1 = new Treatment_area(1);
                //treatmentAreaRepository.save(treatment_area1);

                init_treatmentArea(treatmentAreaRepository);
                init_doctor(treatmentAreaRepository,doctorRepository);
                init_headNurse(treatmentAreaRepository,headNurseRepository);
                init_wardNurse(treatmentAreaRepository,wardNurseRepository);
                set_treatmentArea(treatmentAreaRepository, doctorRepository, headNurseRepository);

                init_patient(patientRepository, wardNurseRepository);

                init_emergencyNurse(emergencyNurseRepository);
                init_bed(bedRepository, treatmentAreaRepository);

                set_wardNurse_patient_bed(treatmentAreaRepository,wardNurseRepository, patientRepository,bedRepository);
            }
        };
    }

    public void init_wardNurse(TreatmentAreaRepository treatmentAreaRepository,
                               WardNurseRepository wardNurseRepository){
        List<Ward_nurse> ward_nurseList = new ArrayList<>();
        Ward_nurse ward_nurse1  = new Ward_nurse("wardNurse1","123456");
        ward_nurse1.setTreatment_area(treatmentAreaRepository.findByType(1));
        ward_nurseList.add(ward_nurse1);
        Ward_nurse ward_nurse2  = new Ward_nurse("wardNurse2","123456");
        ward_nurse2.setTreatment_area(treatmentAreaRepository.findByType(1));
        ward_nurseList.add(ward_nurse2);
        Ward_nurse ward_nurse3  = new Ward_nurse("wardNurse3","123456");
        ward_nurse3.setTreatment_area(treatmentAreaRepository.findByType(1));
        ward_nurseList.add(ward_nurse3);
        Ward_nurse ward_nurse4  = new Ward_nurse("wardNurse4","123456");
        ward_nurse4.setTreatment_area(treatmentAreaRepository.findByType(2));
        ward_nurseList.add(ward_nurse4);
        Ward_nurse ward_nurse5  = new Ward_nurse("wardNurse5","123456");
        ward_nurse5.setTreatment_area(treatmentAreaRepository.findByType(2));
        ward_nurseList.add(ward_nurse5);
        Ward_nurse ward_nurse6  = new Ward_nurse("wardNurse6","123456");
        ward_nurse6.setTreatment_area(treatmentAreaRepository.findByType(2));
        ward_nurseList.add(ward_nurse6);
        Ward_nurse ward_nurse7  = new Ward_nurse("wardNurse7","123456");
        ward_nurse7.setTreatment_area(treatmentAreaRepository.findByType(4));
        ward_nurseList.add(ward_nurse7);
        Ward_nurse ward_nurse8  = new Ward_nurse("wardNurse8","123456");
        ward_nurse8.setTreatment_area(treatmentAreaRepository.findByType(4));
        ward_nurseList.add(ward_nurse8);
        Ward_nurse ward_nurse9  = new Ward_nurse("wardNurse9","123456");
        ward_nurse9.setTreatment_area(treatmentAreaRepository.findByType(4));
        ward_nurseList.add(ward_nurse9);

        Ward_nurse temp;
        for(int i = 1;i<10;i++){

        String name = "wardNurse"+i;
         if(wardNurseRepository.findByUsername(name)==null){
             wardNurseRepository.save(ward_nurseList.get(i-1));
         }

        }

    }
    public void init_headNurse(TreatmentAreaRepository treatmentAreaRepository,
                               HeadNurseRepository headNurseRepository){
        Head_nurse head_nurse1 = new Head_nurse("headNurse1","123456");
        head_nurse1.setTreatment_area(treatmentAreaRepository.findByType(1));
        Head_nurse head_nurse2 = new Head_nurse("headNurse2","123456");
        head_nurse2.setTreatment_area(treatmentAreaRepository.findByType(2));
        Head_nurse head_nurse4 = new Head_nurse("headNurse4","123456");
        head_nurse4.setTreatment_area(treatmentAreaRepository.findByType(4));


        Head_nurse tempHeadNurse;
        if((tempHeadNurse =headNurseRepository.findByUsername("headNurse1"))==null){
            headNurseRepository.save(head_nurse1);
        }
        if((tempHeadNurse =headNurseRepository.findByUsername("headNurse2"))==null){
            headNurseRepository.save(head_nurse2);
        }
        if((tempHeadNurse =headNurseRepository.findByUsername("headNurse4"))==null){
            headNurseRepository.save(head_nurse4);
        }



        System.out.println(" headNurse count:  "+headNurseRepository.count());
    }
    public void init_doctor(TreatmentAreaRepository treatmentAreaRepository,DoctorRepository doctorRepository){
        assert  treatmentAreaRepository.findByType(1)!= null;
        Doctor  doctor1 = new Doctor("doctor1","123456");
        Doctor tempDoctor;
        if((tempDoctor = doctorRepository.findByUsername("doctor1"))==null){
            doctor1.setTreatment_area(treatmentAreaRepository.findByType(1));
            doctorRepository.save(doctor1);
        }
        Doctor  doctor2 = new Doctor("doctor2","123456");
        if((tempDoctor = doctorRepository.findByUsername("doctor2"))==null){
            doctor2.setTreatment_area(treatmentAreaRepository.findByType(2));
            doctorRepository.save(doctor2);

        }
        Doctor  doctor4 = new Doctor("doctor4","123456");
        if((tempDoctor = doctorRepository.findByUsername("doctor4"))==null){
            doctor4.setTreatment_area(treatmentAreaRepository.findByType(4));
            doctorRepository.save(doctor4);

        }

        System.out.println(" doctor count:  "+doctorRepository.count());
    }

    public void init_treatmentArea(TreatmentAreaRepository treatmentAreaRepository){
        if(treatmentAreaRepository.findByType(1) ==null){
            Treatment_area treatment_area1 = new Treatment_area(1);
            treatmentAreaRepository.save(treatment_area1);
        }
        if(treatmentAreaRepository.findByType(2) ==null){
            Treatment_area treatment_area2 = new Treatment_area(2);
            treatmentAreaRepository.save(treatment_area2);
        }
        if(treatmentAreaRepository.findByType(4) ==null){
            Treatment_area treatment_area4 = new Treatment_area(4);
            treatmentAreaRepository.save(treatment_area4);
        }
        assert  treatmentAreaRepository.findByType(1)!= null;
        assert  treatmentAreaRepository.findByType(2)!= null;
        assert  treatmentAreaRepository.findByType(4)!= null;
        System.out.println(" treatment _area count:  "+treatmentAreaRepository.count());


    }

    public void set_treatmentArea(TreatmentAreaRepository treatmentAreaRepository,DoctorRepository doctorRepository,
                                  HeadNurseRepository headNurseRepository){
        if(treatmentAreaRepository.findByType(1) != null){
            Doctor doctor1 = doctorRepository.findByUsername("doctor1");
            Head_nurse head_nurse1 = headNurseRepository.findByUsername("headNurse1");
            Treatment_area treatment_area1 = treatmentAreaRepository.findByType(1);
            treatment_area1.setDoctor(doctor1);
            treatment_area1.setHead_nurse(head_nurse1);
            treatmentAreaRepository.save(treatment_area1);
        }
        if(treatmentAreaRepository.findByType(2) !=null){
            Doctor doctor2 = doctorRepository.findByUsername("doctor2");
            Head_nurse head_nurse2 = headNurseRepository.findByUsername("headNurse2");
            Treatment_area treatment_area2 = treatmentAreaRepository.findByType(2);
            treatment_area2.setDoctor(doctor2);
            treatment_area2.setHead_nurse(head_nurse2);
            treatmentAreaRepository.save(treatment_area2);
        }
        if(treatmentAreaRepository.findByType(4) !=null){
            Doctor doctor4 = doctorRepository.findByUsername("doctor4");
            Head_nurse head_nurse4 = headNurseRepository.findByUsername("headNurse4");
            Treatment_area treatment_area4 = treatmentAreaRepository.findByType(4);
            treatment_area4.setDoctor(doctor4);
            treatment_area4.setHead_nurse(head_nurse4);
            treatmentAreaRepository.save(treatment_area4);
        }
    }

    public void init_patient(PatientRepository patientRepository, WardNurseRepository wardNurseRepository){
        Ward_nurse ward_nurse = wardNurseRepository.findByUsername("wardNurse1");
        if(patientRepository.findByName("patient1")==null){
            Patient patient1 = new Patient("patient1", 0,0);
            patient1.setNurse(ward_nurse);
            patientRepository.save(patient1);
        }
        if(patientRepository.findByName("patient2") == null){
            Patient patient2 = new Patient("patient2", 0,0);
            patient2.setNurse(ward_nurse);
            patientRepository.save(patient2);
        }

    }

    /**
     * set the patient1 and 2 be wardNurse1's patients
     * @param wardNurseRepository
     * @param patientRepository
     */
    public void set_wardNurse_patient_bed(TreatmentAreaRepository treatmentAreaRepository, WardNurseRepository wardNurseRepository,
                                          PatientRepository patientRepository, BedRepository bedRepository){
        Ward_nurse ward_nurse = wardNurseRepository.findByUsername("wardNurse1");
        Set<Patient> patientSet = new HashSet<>();
        Treatment_area treatment_area = treatmentAreaRepository.findByType(1);
        Set<Bed> beds = treatment_area.getBeds();
        Patient patient1 = patientRepository.findByName("patient1");
        Patient patient2 = patientRepository.findByName("patient2");
        //nurse--patient
        patientSet.add(patient1);
        patientSet.add(patient2);
        ward_nurse.setPatients(patientSet);
        patient1.setNurse(ward_nurse);
        patient1.setTreatmentArea(1);
        patient2.setNurse(ward_nurse);
        patient2.setTreatmentArea(1);

        //bed--patient
        Iterator<Bed> bedIterable = beds.iterator();
        Bed bed1 = bedIterable.next();
        bed1.setPatient(patient1);
        patient1.setBed(bed1);
        bedRepository.save(bed1);
        Bed bed2 = bedIterable.next();
        bed2.setPatient(patient2);
        patient2.setBed(bed2);
        bedRepository.save(bed2);

        //save
        patientRepository.save(patient1);
        patientRepository.save(patient2);
    }

    public void init_emergencyNurse(EmergencyNurseRepository emergencyNurseRepository){
        if(emergencyNurseRepository.findByUsername("eNurse1")==null){
            Emergency_nurse emergency_nurse = new Emergency_nurse("eNurse1","123456");
            emergencyNurseRepository.save(emergency_nurse);
        }
    }

    public void init_bed(BedRepository bedRepository, TreatmentAreaRepository treatmentAreaRepository){
        Treatment_area treatment_area = treatmentAreaRepository.findByType(1);
        if(treatment_area.getBeds().size() == 0){
            for(int i = 0;i < 10; i++){
                Bed bed = new Bed(treatment_area);
                bedRepository.save(bed);
            }
        }
    }
}


