package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.AddPatientRequest;
import fudan.se.lab2.domain.Bed;
import fudan.se.lab2.domain.Nucleic_acid_test_sheet;
import fudan.se.lab2.domain.Patient;
import fudan.se.lab2.domain.Ward_nurse;
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
    private TreatmentAreaRepository treatmentAreaRepository;
    private BedRepository bedRepository;
    Logger logger = LoggerFactory.getLogger(ENurseService.class);

    @Autowired
    public ENurseService(DoctorRepository doctorRepository,
                       HeadNurseRepository headNurseRepository,
                       WardNurseRepository wardNurseRepository,
                       EmergencyNurseRepository emergencyNurseRepository,
                       PatientRepository patientRepository,
                         BedRepository bedRepository,
                         NucleicAcidTestSheetRepository nucleicAcidTestSheetRepository,
                         TreatmentAreaRepository treatmentAreaRepository){
        this.doctorRepository = doctorRepository;
        this.headNurseRepository = headNurseRepository;
        this.wardNurseRepository  = wardNurseRepository;
        this.emergencyNurseRepository = emergencyNurseRepository;
        this.patientRepository = patientRepository;
        this.treatmentAreaRepository = treatmentAreaRepository;
        this.bedRepository = bedRepository;
        this.nucleicAcidTestSheetRepository = nucleicAcidTestSheetRepository;
    }

    public Map<String, Set> initialENurse(String username){
        Map<String ,Set>returnMap = new HashMap<>();
        Iterable<Patient > allPatients = patientRepository.findAll();
        Set<Map> p_set = new HashSet<>();
        for(Patient patient: allPatients){
            Map<String , Object>temp = new HashMap<>();
            temp.put("patientID",patient.getId());
            temp.put("username",patient.getName());
            temp.put("condition_rating",patient.getCondition_rating());
            temp.put("living_status",patient.getLiving_status());
            temp.put("area", patient.getTreatmentArea());
            p_set.add(temp);
        }
        returnMap.put("patient_tableData",p_set);
        return returnMap;

    }

    /**
     *    area_type: int,//1 轻症 2 重症 4危重症，0隔离，3治疗
     *   rating: int,//0：轻症 1： 重症 2：危重，3不筛选
     *   status: int//0：住院 1：出院 2：死亡,3不筛选
     * @param rating
     * @param status
     * @return
     */
    public Map<String, Set> selectAll(int area_type,int rating,int status){
        Map <String,Set>returnMap = new HashMap<>();
        Iterable<Patient> patients = selectStatus(selectRating(selectArea(area_type),rating),status);

        Set<Map> p_set = new HashSet<>();
        for(Patient patient: patients){
            Map<String , Object>temp = new HashMap<>();
            temp.put("patientID",patient.getId());
            temp.put("username",patient.getName());
            temp.put("condition_rating",patient.getCondition_rating());
            temp.put("living_status",patient.getLiving_status());
            temp.put("area", patient.getTreatmentArea());
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
            allTreatment.addAll(patientRepository.findByTreatmentArea(1));
            allTreatment.addAll(patientRepository.findByTreatmentArea(2));
            allTreatment.addAll(patientRepository.findByTreatmentArea(4));

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
        System.out.println("selectStatus:status="+status);
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

    public Map<String, String> addPatient(AddPatientRequest addPatientRequest) {
        String name = addPatientRequest.getName();
        Date date = addPatientRequest.getDate();
        int result = addPatientRequest.getResult();
        int conditional_rating = addPatientRequest.getCondition_rating();
        if (patientRepository.findByName(name) != null) {
            throw new UsernameHasBeenRegisteredException(name);

        }
        Patient patient = new Patient(name, conditional_rating, 0);//living status
        patientRepository.save(patient);
        patient = patientRepository.findByName(name);
        Nucleic_acid_test_sheet nucleic_acid_test_sheet = new Nucleic_acid_test_sheet();
        nucleic_acid_test_sheet.setPatient(patient);
        nucleic_acid_test_sheet.setConditional_rating(conditional_rating);
        nucleic_acid_test_sheet.setDate(date);
        nucleic_acid_test_sheet.setResult(result);
        nucleicAcidTestSheetRepository.save(nucleic_acid_test_sheet);
        System.out.println("add nucleicAcidSheet of patient "+patient.getName());

        patient.add_Nucleic_acid_test_sheet(nucleic_acid_test_sheet);

        Map<String, String> returnMap = new HashMap<>();
        int type = 0;
        int patientNumPerNurse = 0;
        String description = "";
        switch (conditional_rating){
            case 0:{
                type = 1;
                description = "轻症区域";
                patientNumPerNurse = 3;
                break;
            }
            case 1:{
                type = 2;
                description = "重症区域";
                patientNumPerNurse = 2;
                break;
            }
            case 2:{
                type = 4;
                description = "危重症区域";
                patientNumPerNurse = 1;
                break;
            }
            default:{
                break;
            }
        }
        Set<Bed> beds = treatmentAreaRepository.findByType(type).getBeds();
        Set<Ward_nurse> ward_nurses = treatmentAreaRepository.findByType(type).getWard_nurses();
        int bedNum = beds.size();
        int patientNum = patientRepository.findByTreatmentArea(type).size();
        int nurseNum = ward_nurses.size();
        System.out.println("bed Num: "+bedNum+"  ,patientNum "+patientNum+
                "   nurseNum * patientNumPerNurse: "+ nurseNum * patientNumPerNurse);
        if(patientNum < bedNum && patientNum < nurseNum * patientNumPerNurse){
            System.out.println("patient "+patient.getName()+"  do not need to stay in isolated area");
            patient.setTreatmentArea(type);
            for(Bed bed: beds){
                if(bed.getPatient() == null){
                    bed.setPatient(patient);
                    patient.setBed(bed);
                    bedRepository.save(bed);
                    break;
                }
            }
            for(Ward_nurse nurse : ward_nurses){

                if(nurse.getPatients().size() < patientNumPerNurse){
                    nurse.addPatients(patient);
                    System.out.println("nurse "+nurse.getUsername()+" add patient "+patient.getName());
                    patient.setNurse(nurse);
                    wardNurseRepository.save(nurse);
                    break;
                }
            }
            returnMap.put("area",description);
        }
        else {
            returnMap.put("area","隔离区域");
        }
//        patientRepository.save(patient);//这里不用save就能将修改保存，save了反倒会包duplicate key的错，应该是上面已经save过一次
        return returnMap;
    }


}
