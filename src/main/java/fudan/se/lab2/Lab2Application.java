package fudan.se.lab2;

import fudan.se.lab2.domain.*;

//import fudan.se.lab2.repository.*;
import fudan.se.lab2.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

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
                                        PasswordEncoder passwordEncoder
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





            }
        };
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
        if((tempHeadNurse =headNurseRepository.findByUsername("headNurse1"))!=null){
            headNurseRepository.delete(tempHeadNurse);
        }
        if((tempHeadNurse =headNurseRepository.findByUsername("headNurse2"))!=null){
            headNurseRepository.delete(tempHeadNurse);
        }
        if((tempHeadNurse =headNurseRepository.findByUsername("headNurse4"))!=null){
            headNurseRepository.delete(tempHeadNurse);
        }
        headNurseRepository.save(head_nurse1);
        headNurseRepository.save(head_nurse2);
        headNurseRepository.save(head_nurse4);
        System.out.println(" headNurse count:  "+headNurseRepository.count());
    }
    public void init_doctor(TreatmentAreaRepository treatmentAreaRepository,DoctorRepository doctorRepository){
        Doctor  doctor1 = new Doctor("doctor1","123456");
        Doctor tempDoctor;
        if((tempDoctor = doctorRepository.findByUsername("doctor1"))!=null){
            doctorRepository.delete(tempDoctor);
        }
        Doctor  doctor2 = new Doctor("doctor2","123456");
        if((tempDoctor = doctorRepository.findByUsername("doctor2"))!=null){
            doctorRepository.delete(tempDoctor);
        }
        Doctor  doctor4 = new Doctor("doctor4","123456");
        if((tempDoctor = doctorRepository.findByUsername("doctor4"))!=null){
            doctorRepository.delete(tempDoctor);
        }
        assert  treatmentAreaRepository.findByType(1)!= null;

        doctor1.setTreatment_area(treatmentAreaRepository.findByType(1));
        doctor2.setTreatment_area(treatmentAreaRepository.findByType(2));
        doctor4.setTreatment_area(treatmentAreaRepository.findByType(4));

        doctorRepository.save(doctor1);
        doctorRepository.save(doctor2);
        doctorRepository.save(doctor4);
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

}


