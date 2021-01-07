package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.AddDailyRecordRequest;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.exception.UsernameNotFoundException;
import fudan.se.lab2.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WNurseService {

    private DoctorRepository doctorRepository;
    private HeadNurseRepository headNurseRepository;
    private WardNurseRepository wardNurseRepository;
    private EmergencyNurseRepository emergencyNurseRepository;
    private PatientRepository patientRepository;
    private DailyRecordRepository dailyRecordRepository;
    Logger logger = LoggerFactory.getLogger(AuthService.class);

    public WNurseService(DoctorRepository doctorRepository,
                         HeadNurseRepository headNurseRepository,
                         WardNurseRepository wardNurseRepository,
                         EmergencyNurseRepository emergencyNurseRepository,
                         PatientRepository patientRepository,
                         DailyRecordRepository dailyRecordRepository){
        this.doctorRepository = doctorRepository;
        this.headNurseRepository = headNurseRepository;
        this.wardNurseRepository  = wardNurseRepository;
        this.emergencyNurseRepository = emergencyNurseRepository;
        this.patientRepository = patientRepository;
        this.dailyRecordRepository = dailyRecordRepository;

    }

    public Map<String, Object> initialWardNurse(String username){
        Map<String,Object> returnMap = new HashMap<>();
        Ward_nurse ward_nurse = wardNurseRepository.findByUsername(username);
        if(ward_nurse ==null){
            throw new UsernameNotFoundException(username);
        }

        Treatment_area treatmentArea = ward_nurse.getTreatment_area();
        int area = treatmentArea.getType();
        returnMap.put("area",area);

        String doctor = treatmentArea.getDoctor().getUsername();
        returnMap.put("doctor",doctor);
        String headNurse = treatmentArea.getHead_nurse().getUsername();
        returnMap.put("headNurse",headNurse);

        Set<Patient> patients = ward_nurse.getPatients();
        Set<Map> patient_tableData = new HashSet<>();
        for(Patient patient : patients){
            Map<String, Object> patientEntry = new HashMap<>();
            patientEntry.put("patientID", patient.getId());
            patientEntry.put("username", patient.getName());
            patientEntry.put("condition_rating", patient.getCondition_rating());
            patientEntry.put("living_status", patient.getLiving_status());
            patient_tableData.add(patientEntry);
        }
        returnMap.put("patient_tableData", patient_tableData);
        return returnMap;
    }

    public Set<Patient> selectStatus(int status,Set<Patient>areaPatients){
        if(status ==3)
            return areaPatients;
        Set<Patient>temp = new HashSet<>();
        if(status ==1||status==2||status ==0){
            for (Patient patient:areaPatients){
                if(patient.getLiving_status()==status)
                    temp.add(patient);
            }
            System.out.println("status patient num : "+temp.size());
            return temp;
        }
        return areaPatients;

    }
    public Set<Patient> selectTrans(int trans,Set<Patient>statusPatients){
        if(trans==2)//先返回一个，防止出错
            return statusPatients;
        Set<Patient>temp = new HashSet<>();//等待转院的
        for(Patient patient:statusPatients){
            int condition = patient.getCondition_rating();
            int treatment = patient.getTreatmentArea();
            if(condition ==2||treatment!=4)//病人为危重
                temp.add(patient);
            else if(condition ==1||treatment!=2)//病人为重
                temp.add(patient);
            else if(condition ==0||treatment!=1)//病人为轻
                temp.add(patient);
        }//trans: int 是否待转入其他治疗区域，0是，1否，2都可以
        if(trans ==0){
            System.out.println("trans patient num : "+temp.size());
            return temp;
        }

        else if (trans ==1){//所有 -需要转移
            if(statusPatients.removeAll(temp) ==true)//有修改返回true
                System.out.println("trans patient num : "+statusPatients.size());
            return statusPatients;
        }
        System.out.println("trans patient num : "+statusPatients.size());
        return statusPatients;

    }
    public Set<Patient> selectLeave(int leave,Set<Patient>transPatients) {
        if (leave == 2) return transPatients;
        Set<Patient> canLeave = new HashSet<>();
        for (Patient patient : transPatients) {
            List<Daily_state_records> daily_state_recordsList = new ArrayList<Daily_state_records>(patient.getDaily_state_records());
            Collections.sort(daily_state_recordsList);
            List<Nucleic_acid_test_sheet> nucleic_acid_test_sheetList = new ArrayList<Nucleic_acid_test_sheet>(patient.getNucleic_acid_test_sheets());
            int nulen = nucleic_acid_test_sheetList.size();
            int len = daily_state_recordsList.size();
            if (len > 2 && nulen > 1) {
                if (daily_state_recordsList.get(len - 1).getTemperature() < 37.3 &&
                        daily_state_recordsList.get(len - 2).getTemperature() < 37.3 &&
                        daily_state_recordsList.get(len - 3).getTemperature() < 37.3&&
                        daily_state_recordsList.get(len - 1).getNucleic_acid_test_result() ==0&&
                        daily_state_recordsList.get(len - 2).getNucleic_acid_test_result() ==0
                )
                    canLeave.add(patient);
            }
        }
        if(leave ==0){
            System.out.println("can leave patient num : "+canLeave.size());
            return canLeave;}
        else if (leave ==1){
            transPatients.removeAll(canLeave);
            System.out.println("can not leave patient num : "+transPatients.size());
            return transPatients;
        }
        System.out.println("all patient num : "+transPatients.size());
        return transPatients;

    }

    public Map<String, Object> select(int type,String wardNursename, int leave,int trans,int status){
        System.out.println("wardnurse: "+wardNursename+"  type: "+type+"  leave: "+leave+"  trans: "+trans+"  status: "+status);
        Map<String,Object>returnMap = new HashMap<>();
        System.out.println("area type "+type);
        Ward_nurse ward_nurse = wardNurseRepository.findByUsername(wardNursename);
        if(ward_nurse ==null)
            throw  new UsernameNotFoundException(wardNursename);
        Set<Patient> wardPatients =ward_nurse.getPatients();
        System.out.println("area patient num : "+wardPatients.size());
        Set<Patient>patients =selectLeave(leave,selectTrans(trans,selectStatus(status,wardPatients)));
        Set<Map> p_set = new HashSet<>();
        for(Patient patient:patients){
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

    public String addDailyRecord(AddDailyRecordRequest addDailyRecordRequest){
        String username = addDailyRecordRequest.getPatientName();
        float temp = addDailyRecordRequest.getTemperature();
        Date date = addDailyRecordRequest.getDate();
        String symptom = addDailyRecordRequest.getSymptom();

        Patient patient = patientRepository.findByName(username);
        if(patient ==null)
            throw new UsernameNotFoundException(username);
        Set<Nucleic_acid_test_sheet>nucleic_acid_test_sheets = patient.getNucleic_acid_test_sheets();
        int size = nucleic_acid_test_sheets.size();
        ArrayList<Nucleic_acid_test_sheet> list = new ArrayList<>(nucleic_acid_test_sheets);
        Collections.sort(list);
        int result = list.get(size-1).getResult();
        Daily_state_records daily_state_records = new Daily_state_records();
        daily_state_records.setPatient(patient);
        daily_state_records.setTemperature( temp);
        daily_state_records.setDate(date);
        daily_state_records.setLiving_status(patient.getLiving_status());
        daily_state_records.setSymptom(symptom);

        daily_state_records.setNucleic_acid_test_result(result);
        dailyRecordRepository.save(daily_state_records);
        patient.add_Daily_state_records(daily_state_records);
        patientRepository.save(patient);
        return "success";
    }

}
