package fudan.se.lab2.service;

import fudan.se.lab2.domain.*;
import fudan.se.lab2.exception.UsernameNotFoundException;
import fudan.se.lab2.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class HNurseService {
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
    public HNurseService(DoctorRepository doctorRepository, HeadNurseRepository headNurseRepository, WardNurseRepository wardNurseRepository, EmergencyNurseRepository emergencyNurseRepository, PatientRepository patientRepository, NucleicAcidTestSheetRepository nucleicAcidTestSheetRepository, TreatmentAreaRepository treatmentAreaRepository, BedRepository bedRepository) {
        this.doctorRepository = doctorRepository;
        this.headNurseRepository = headNurseRepository;
        this.wardNurseRepository = wardNurseRepository;
        this.emergencyNurseRepository = emergencyNurseRepository;
        this.patientRepository = patientRepository;
        this.nucleicAcidTestSheetRepository = nucleicAcidTestSheetRepository;
        this.treatmentAreaRepository = treatmentAreaRepository;
        this.bedRepository = bedRepository;
    }

    public Map<String, Object> initialHNurse(String username){
        Map<String,Object>returnMap = new HashMap<>();
        Set<Map> n_set = new HashSet<>();
        Head_nurse head_nurse = headNurseRepository.findByUsername(username);
        if(head_nurse == null){
            throw new UsernameNotFoundException(username);
        }
        Treatment_area treatmentArea = head_nurse.getTreatment_area();
        int area = treatmentArea.getType();
        Set<Patient>patients = patientRepository.findByTreatmentArea(area);
        ArrayList<String>newPatients = new ArrayList<>();

        for(Patient patient:patients){
            if(patient.getNewPatient() ==1){
                System.out.println("new patient "+patient.getName());
                newPatients.add(patient.getName());
                patient.setNewPatient(-1);
                patientRepository.save(patient);

            }
        }
        System.out.println("new patient num "+newPatients.size());
        returnMap.put("newPatients",newPatients);
        returnMap.put("area",area);
        Set<Ward_nurse> ward_nurseSet = treatmentArea.getWard_nurses();
        for(Ward_nurse nurse : ward_nurseSet) {
            Map<String, Object> temp = new HashMap<>();
            temp.put("name", nurse.getUsername());

            Set<Patient> patientOfNurse = nurse.getPatients();
            Set<String> patientSet = new HashSet<>();
            for (Patient p : patientOfNurse) {
                patientSet.add(p.getName());
            }
            temp.put("patients", patientSet);
            n_set.add(temp);
        }
        returnMap.put("wardNurse_tableData",n_set);
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
        Set<Map> bed_patient_tableData = new HashSet<>();
        Set<Bed> beds = treatmentArea.getBeds();
        for(Bed bed : beds){
            Map<String, Object> temp = new HashMap<>();
            temp.put("bedID", bed.getBedId());
            temp.put("patientName", bed.getPatient()==null?"无":bed.getPatient().getName());
            bed_patient_tableData.add(temp);
        }
        returnMap.put("bed_patient_tableData", bed_patient_tableData);
        Set<String> nullWardNurse = new HashSet<>();
        Set<Ward_nurse> wardNurseSet = wardNurseRepository.findAllWardNurses();
        for(Ward_nurse ward_nurse : wardNurseSet){
            if(ward_nurse.getTreatment_area() == null){
                nullWardNurse.add(ward_nurse.getUsername());
            }
        }
        returnMap.put("nullWardNurse", nullWardNurse);
        returnMap.put("nullWardNurse", nullWardNurse);
        //先随便返回一个newPatients

        return returnMap;
    }

    public int deleteNurse(String nurseName, int area_type){
        System.out.println("delete nurse "+nurseName);
        Ward_nurse ward_nurse = wardNurseRepository.findByUsername(nurseName);
        if(ward_nurse.getTreatment_area().getType() == area_type){

            Treatment_area treatment_area = treatmentAreaRepository.findByType(area_type);
            treatment_area.getWard_nurses().remove(ward_nurse);
            treatmentAreaRepository.save(treatment_area);
            ward_nurse.setTreatment_area(null);
            wardNurseRepository.save(ward_nurse);
            return 0;
        }
        return -1;
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

    public Map<String, Object> select(int type, int leave,int trans,int status){
        System.out.println("type: "+type+"  leave: "+leave+"  trans: "+trans+"  status: "+status);
        Map<String,Object>returnMap = new HashMap<>();
        System.out.println("area type "+type);
        Set<Patient> areaPatients = patientRepository.findByTreatmentArea(type);
        System.out.println("area patient num : "+areaPatients.size());
        Set<Patient>patients =selectLeave(leave,selectTrans(trans,selectStatus(status,areaPatients)));
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

    public int addNurse(String nurseName, int area_type){
        System.out.println("add nurse "+nurseName);
        Ward_nurse ward_nurse = wardNurseRepository.findByUsername(nurseName);
        if(ward_nurse.getTreatment_area() == null){
            Treatment_area treatment_area = treatmentAreaRepository.findByType(area_type);
            Set<Ward_nurse> ward_nurses = treatment_area.getWard_nurses();
            if(!ward_nurses.contains(ward_nurse)){
                ward_nurses.add(ward_nurse);
                treatmentAreaRepository.save(treatment_area);
                ward_nurse.setTreatment_area(treatment_area);
                wardNurseRepository.save(ward_nurse);
                System.out.println("add patient NUM"+addPatientByNurse(area_type));

                return 0;
            }
        }

        return -1;
    }



    public int addPatientByNurse(int type){
        int addP = 0;
        Set<Patient>isolated = patientRepository.findByTreatmentArea(0);
        System.out.println("isolated num: "+isolated.size());
        for(Patient patient:isolated){
            int conditional_rating = patient.getCondition_rating();
            int living_status = patient.getLiving_status();
            if(living_status!=0)
                continue;//这个病人没住院，不管他
            String description = null;
            int patientNumPerNurse = 0;
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
            int patientNum = patientRepository.findByTreatmentArea(type).size();//当前病人
            int nurseNum = ward_nurses.size();//当前护士
            System.out.println("bed Num: "+bedNum+"  ,patientNum "+patientNum+
                    "   nurseNum * patientNumPerNurse: "+ nurseNum * patientNumPerNurse);
            if(patientNum < bedNum &&
                    patientNum < nurseNum * patientNumPerNurse){
                System.out.println("patient "+patient.getName()+"  do not need to stay in isolated area");
                addP+=1;
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

            }

        }

        return addP;
    }
}
