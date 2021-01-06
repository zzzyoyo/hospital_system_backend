package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.AddPatientRequest;
import fudan.se.lab2.domain.Nucleic_acid_test_sheet;
import fudan.se.lab2.domain.Patient;
import fudan.se.lab2.exception.UsernameHasBeenRegisteredException;
import fudan.se.lab2.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
public class ENurseService {
    private DoctorRepository doctorRepository;
    private HeadNurseRepository headNurseRepository;
    private WardNurseRepository wardNurseRepository;
    private EmergencyNurseRepository emergencyNurseRepository;
    private PatientRepository patientRepository;
    private NucleicAcidTestSheetRepository nucleicAcidTestSheetRepository;
    Logger logger = LoggerFactory.getLogger(ENurseService.class);

    @Autowired
    public ENurseService(DoctorRepository doctorRepository,
                       HeadNurseRepository headNurseRepository,
                       WardNurseRepository wardNurseRepository,
                       EmergencyNurseRepository emergencyNurseRepository,
                       PatientRepository patientRepository,
                         NucleicAcidTestSheetRepository nucleicAcidTestSheetRepository){
        this.doctorRepository = doctorRepository;
        this.headNurseRepository = headNurseRepository;
        this.wardNurseRepository  = wardNurseRepository;
        this.emergencyNurseRepository = emergencyNurseRepository;
        this.patientRepository = patientRepository;
        this.nucleicAcidTestSheetRepository = nucleicAcidTestSheetRepository;
    }

    public Map<String, Set> intialENurse(String username){
        Map<String ,Set>returnMap = new HashMap<>();
        Iterable<Patient > allPatients = patientRepository.findAll();
        Set<Map> p_set = new HashSet<>();
        for(Patient patient: allPatients){
            Map<String , Object>temp = new HashMap<>();
            temp.put("patientID",patient.getId());
            temp.put("username",patient.getName());
            temp.put("condition_rating",patient.getCondition_rating());
            temp.put("living_status",patient.getLiving_status());
            p_set.add(temp);
        }
        returnMap.put("patient_tableData",p_set);
        return returnMap;

    }

    /**
     *    area_type: int,//轻、重、危重区域分别为0、1、2，-1代表不筛选
     *   isolated: int,//表示是否在隔离区，0是，1否，2不筛选
     *   rating: int,//0：轻症 1： 重症 2：危重，3不筛选
     *   status: int//0：住院 1：出院 2：死亡,3不筛选
     * @param area_type
     * @param isolated
     * @param rating
     * @param status
     * @return
     */
    public Map<String, Set> selectAll(int area_type,int isolated,int rating,int status){
        Map <String,Set>returnMap = new HashMap<>();
        Iterable<Patient> patients = selectStatus(selectRating(selectArea(area_type),rating),status);

        Set<Map> p_set = new HashSet<>();
        for(Patient patient: patients){
            Map<String , Object>temp = new HashMap<>();
            temp.put("patientID",patient.getId());
            temp.put("username",patient.getName());
            temp.put("condition_rating",patient.getCondition_rating());
            temp.put("living_status",patient.getLiving_status());
            p_set.add(temp);
        }
        returnMap.put("patient_tableData",p_set);
        return returnMap;

    }
    public Iterable<Patient>selectArea(int area_type) {
        //1 轻症 2 重症 4危重症 0 隔离 3 治疗区域 -1 不筛选

        if (area_type == 1 || area_type == 2 || area_type == 4 || area_type == 0) {
            return patientRepository.findByTreatmentArea(area_type);
        }
        if(area_type ==3){
            Set<Patient> allTreatment = new HashSet<>();
            for(Patient  patient: patientRepository.findByTreatmentArea(1)){
                allTreatment.add(patient);
            }
            for(Patient  patient: patientRepository.findByTreatmentArea(2)){
                allTreatment.add(patient);
            }
            for(Patient  patient: patientRepository.findByTreatmentArea(4)){
                allTreatment.add(patient);
            }
            return allTreatment;
        }
        //default 返回所有病人
        return patientRepository.findAll();
    }
    public Iterable<Patient> selectRating(Iterable<Patient> areaPatients,int rating){
        Set<Patient> ratingPatients = new HashSet<>();
        if(rating ==1||rating ==2||rating ==0){
            for(Patient patient:areaPatients){
                if(patient.getCondition_rating() == rating)
                    ratingPatients.add(patient);
            }
            return ratingPatients;
        }
        return areaPatients;
    }
    public Iterable <Patient> selectStatus(Iterable<Patient> ratingPatients,int status){
        Set<Patient> statusPatients = new HashSet<>();
        if(status ==1||status ==2||status ==0){
            for(Patient patient:ratingPatients){
                if(patient.getLiving_status()== status)
                    statusPatients.add(patient);
            }
            return statusPatients;
        }
        return ratingPatients;
    }

    public String addPatient(AddPatientRequest addPatientRequest) {
        String name = addPatientRequest.getName();
        Date date = addPatientRequest.getDate();
        String result = addPatientRequest.getResult();
        int conditional_rating = addPatientRequest.getCondition_rating();
        if (patientRepository.findByName(name) != null) {
            throw new UsernameHasBeenRegisteredException(name);

        }
        Patient patient = new Patient(name, conditional_rating, 0);//living status
        patientRepository.save(patient);
        Nucleic_acid_test_sheet nucleic_acid_test_sheet = new Nucleic_acid_test_sheet();
        nucleic_acid_test_sheet.setPatient(patientRepository.findByName(name));
        nucleic_acid_test_sheet.setConditional_rating(conditional_rating);
        nucleic_acid_test_sheet.setDate(date);
        nucleic_acid_test_sheet.setResult(result);
        nucleicAcidTestSheetRepository.save(nucleic_acid_test_sheet);
        patient.add_Nucleic_acid_test_sheet(nucleic_acid_test_sheet);
        patientRepository.save(patient);



        return "success";
    }


}
