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
                                        BedRepository bedRepository,
                                        NucleicAcidTestSheetRepository nucleicAcidTestSheetRepository
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
                set_nucleic_test(nucleicAcidTestSheetRepository, patientRepository);
                set_wardNurse_patient_bed(treatmentAreaRepository,wardNurseRepository, patientRepository,bedRepository);


                System.out.println("Initiation finished");
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

        if(wardNurseRepository.findByUsername("wardNurse10") == null){
            Ward_nurse ward_nurse14  = new Ward_nurse("wardNurse10","123456");
            ward_nurse14.setTreatment_area(treatmentAreaRepository.findByType(4));
            wardNurseRepository.save(ward_nurse14);
        }

        if(wardNurseRepository.findByUsername("wardNurse11") == null){
            Ward_nurse ward_nurse13  = new Ward_nurse("wardNurse11","123456");
            ward_nurse13.setTreatment_area(treatmentAreaRepository.findByType(4));
            wardNurseRepository.save(ward_nurse13);
        }
        if(wardNurseRepository.findByUsername("wardNurse12") == null){
            Ward_nurse ward_nurse12  = new Ward_nurse("wardNurse12","123456");
            ward_nurse12.setTreatment_area(treatmentAreaRepository.findByType(4));
            wardNurseRepository.save(ward_nurse12);
        }

        if(wardNurseRepository.findByUsername("wardNurse13") == null){
            //空闲的护士
            Ward_nurse ward_nurse10  = new Ward_nurse("wardNurse13","123456");
            wardNurseRepository.save(ward_nurse10);
        }

        if(wardNurseRepository.findByUsername("wardNurse14") == null){
            //空闲的护士
            Ward_nurse ward_nurse11  = new Ward_nurse("wardNurse14","123456");
            wardNurseRepository.save(ward_nurse11);
        }


        if(wardNurseRepository.findByUsername("wardNurse15") == null){
            //空闲的护士
            Ward_nurse ward_nurse10  = new Ward_nurse("wardNurse15","123456");
            wardNurseRepository.save(ward_nurse10);
        }

        if(wardNurseRepository.findByUsername("wardNurse16") == null){
            //空闲的护士
            Ward_nurse ward_nurse11  = new Ward_nurse("wardNurse16","123456");
            wardNurseRepository.save(ward_nurse11);
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
        //16轻症病人
        for (int i = 1;i <= 16; i++){
            if(patientRepository.findByName("patient"+i)==null){
                Patient patient = new Patient("patient"+i, 0,0);
                patientRepository.save(patient);
            }
        }

        //重症4个
        for (int i = 17;i <= 20; i++){
            if(patientRepository.findByName("patient"+i)==null){
                Patient patient = new Patient("patient"+i, 1,0);
                patientRepository.save(patient);
            }
        }
        //危重症5个
        for (int i = 21;i <= 25; i++){
            if(patientRepository.findByName("patient"+i)==null){
                Patient patient = new Patient("patient"+i, 2,0);
                patientRepository.save(patient);
            }
        }

    }

    /**
     * set the patient1 and 2 be wardNurse1's patients
     * @param wardNurseRepository
     * @param patientRepository
     */
    public void set_wardNurse_patient_bed(TreatmentAreaRepository treatmentAreaRepository, WardNurseRepository wardNurseRepository,
                                          PatientRepository patientRepository, BedRepository bedRepository){
        //16个轻症病人，有3个护士，所以其中9个在轻症病区，5个在隔离区,还有1个在重症区，1个在危重
        Treatment_area treatment_area = treatmentAreaRepository.findByType(1);
        Set<Bed> beds = treatment_area.getBeds();
        Iterator<Bed> bedIterable = beds.iterator();
        for(int i = 1; i<= 3; i ++){
            String wardNurseName = "wardNurse"+i;
            Ward_nurse ward_nurse = wardNurseRepository.findByUsername(wardNurseName);
            for(int j = 1; j <= 3; j++){
                String patientName = "patient"+((i-1)*3+(j));
                if(patientRepository.findByName(patientName)!=null){
                    //护士、病区、病床
                    Patient patient = patientRepository.findByName(patientName);
                    patient.setNurse(ward_nurse);
                    patient.setTreatmentArea(1);
                    Bed bed = bedIterable.next();
                    patient.setBed(bed);
                    bed.setPatient(patient);
                    patientRepository.save(patient);
                    bedRepository.save(bed);
                    ward_nurse.addPatients(patient);
                }
            }
            //护士和病人两边都要save
            wardNurseRepository.save(ward_nurse);
        }

        //重症4个,3个护士(456)，够的
        treatment_area = treatmentAreaRepository.findByType(2);
        beds = treatment_area.getBeds();
        bedIterable = beds.iterator();
        for(int i = 4; i<= 5; i ++){
            String wardNurseName = "wardNurse"+i;
            Ward_nurse ward_nurse = wardNurseRepository.findByUsername(wardNurseName);
            for(int j = 1; j <= 2; j++){
                String patientName = "patient"+(16+(i-4)*2+(j));
                if(patientRepository.findByName(patientName)!=null){
                    //护士、病区、病床
                    Patient patient = patientRepository.findByName(patientName);
                    patient.setNurse(ward_nurse);
                    patient.setTreatmentArea(2);
                    Bed bed = bedIterable.next();
                    patient.setBed(bed);
                    bed.setPatient(patient);
                    patientRepository.save(patient);
                    bedRepository.save(bed);
                    ward_nurse.addPatients(patient);
                }
            }
            //护士和病人两边都要save
            wardNurseRepository.save(ward_nurse);
        }
        //轻症但是还在重症区的病人（因为轻症区护士都有病人）
        Patient patient10 = patientRepository.findByName("patient10");
        patient10.setTreatmentArea(2);
        Ward_nurse ward_nurse6 = wardNurseRepository.findByUsername("wardNurse6");
        patient10.setNurse(ward_nurse6);
        ward_nurse6.addPatients(patient10);
        Bed bed1 = bedIterable.next();
        patient10.setBed(bed1);
        bed1.setPatient(patient10);
        patientRepository.save(patient10);
        wardNurseRepository.save(ward_nurse6);
        bedRepository.save(bed1);

        //5个危重症,6个护士7-12
        treatment_area = treatmentAreaRepository.findByType(4);
        beds = treatment_area.getBeds();
        bedIterable = beds.iterator();
        for(int i = 7; i<= 11; i ++){
            String wardNurseName = "wardNurse"+i;
            Ward_nurse ward_nurse = wardNurseRepository.findByUsername(wardNurseName);
            String patientName = "patient"+(20+(i-6));
            if(patientRepository.findByName(patientName)!=null){
                //护士、病区、病床
                Patient patient = patientRepository.findByName(patientName);
                patient.setNurse(ward_nurse);
                patient.setTreatmentArea(4);
                Bed bed = bedIterable.next();
                patient.setBed(bed);
                bed.setPatient(patient);
                patientRepository.save(patient);
                bedRepository.save(bed);
                ward_nurse.addPatients(patient);
                //护士和病人两边都要save
                wardNurseRepository.save(ward_nurse);
            }
        }
        //轻症但是还在危重症区的病人（因为轻症区护士都有病人）
        Patient patient11 = patientRepository.findByName("patient11");
        patient11.setTreatmentArea(4);
        Ward_nurse ward_nurse12 = wardNurseRepository.findByUsername("wardNurse12");
        patient11.setNurse(ward_nurse12);
        ward_nurse12.addPatients(patient11);
        Bed bed2 = bedIterable.next();
        patient11.setBed(bed2);
        bed2.setPatient(patient11);
        patientRepository.save(patient11);
        wardNurseRepository.save(ward_nurse12);
        bedRepository.save(bed2);
    }

    public void init_emergencyNurse(EmergencyNurseRepository emergencyNurseRepository){
        if(emergencyNurseRepository.findByUsername("eNurse1")==null){
            Emergency_nurse emergency_nurse = new Emergency_nurse("eNurse1","123456");
            emergencyNurseRepository.save(emergency_nurse);
        }
    }

    public void init_bed(BedRepository bedRepository, TreatmentAreaRepository treatmentAreaRepository){
        //轻症、重症、危重症治疗区域的一间病房内分别设有 4 张、2 张和 1 张病床
        //各设10间病房
        Treatment_area treatment_area = treatmentAreaRepository.findByType(1);
        if(treatment_area.getBeds().size() == 0){
            for(int i = 0;i < 10*4; i++){
                Bed bed = new Bed(treatment_area);
                bedRepository.save(bed);
                treatment_area.addBeds(bed);
            }
            treatmentAreaRepository.save(treatment_area);
        }
        Treatment_area treatment_area2 = treatmentAreaRepository.findByType(2);
        if(treatment_area2.getBeds().size() == 0){
            for(int i = 0;i < 10*2; i++){
                Bed bed = new Bed(treatment_area2);
                bedRepository.save(bed);
                treatment_area2.addBeds(bed);
            }
            treatmentAreaRepository.save(treatment_area2);
        }
        Treatment_area treatment_area4 = treatmentAreaRepository.findByType(4);
        if(treatment_area4.getBeds().size() == 0){
            for(int i = 0;i < 10; i++){
                Bed bed = new Bed(treatment_area4);
                bedRepository.save(bed);
                treatment_area4.addBeds(bed);
            }
            treatmentAreaRepository.save(treatment_area4);
        }

    }
    public void set_nucleic_test(NucleicAcidTestSheetRepository nucleicAcidTestSheetRepository, PatientRepository patientRepository){
        //每个入院的病人都必须有至少一张核酸检测单
        for(int i = 1; i <= 25;i++){
            Patient patient = patientRepository.findByName("patient"+i);
            Nucleic_acid_test_sheet nucleic_acid_test_sheet = new Nucleic_acid_test_sheet(0, 0, patient);
            nucleicAcidTestSheetRepository.save(nucleic_acid_test_sheet);
            patient.add_Nucleic_acid_test_sheet(nucleic_acid_test_sheet);
            patientRepository.save(patient);
        }
        //patient12有两张
        Patient patient1 = patientRepository.findByName("patient1");
        Nucleic_acid_test_sheet nucleic_acid_test_sheet1 = new Nucleic_acid_test_sheet(0,0,patient1);
        nucleicAcidTestSheetRepository.save(nucleic_acid_test_sheet1);
        patient1.add_Nucleic_acid_test_sheet(nucleic_acid_test_sheet1);
        patientRepository.save(patient1);
        Patient patient2 = patientRepository.findByName("patient2");
        Nucleic_acid_test_sheet nucleic_acid_test_sheet2 = new Nucleic_acid_test_sheet(0,0,patient2);
        nucleicAcidTestSheetRepository.save(nucleic_acid_test_sheet2);
        patient2.add_Nucleic_acid_test_sheet(nucleic_acid_test_sheet2);
        patientRepository.save(patient2);
    }
}


