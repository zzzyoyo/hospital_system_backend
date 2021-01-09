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



    public int addNurse(String nurseName, int area_type){
        System.out.println("add nurse "+nurseName);
        Ward_nurse ward_nurse = wardNurseRepository.findByUsername(nurseName);
        if(ward_nurse.getTreatment_area() == null){
            Treatment_area treatment_area = treatmentAreaRepository.findByType(area_type);
            Set<Ward_nurse> ward_nurses = treatment_area.getWard_nurses();
            System.out.println("before ward nurse num："+ward_nurses.size());
            if(!ward_nurses.contains(ward_nurse)){
                ward_nurses.add(ward_nurse);
                treatmentAreaRepository.save(treatment_area);
                ward_nurse.setTreatment_area(treatment_area);
                wardNurseRepository.save(ward_nurse);
                System.out.println("after ward nurse num："+
                        treatmentAreaRepository.findByType(area_type).getWard_nurses().size());
                int pNum = area2nurseNum(area_type);
                for(int i =0;i<pNum;i++){
                    movePatient(area_type);
                }
               // System.out.println("add patient success?  "+movePatient(area_type));
                return 0;
            }
        }
        return -1;
    }
    public int area2nurseNum(int area){
        int nurseNum =0;
        switch (area){
            case 1:
                nurseNum =3;
                break;
            case 2:
                nurseNum = 2;
                break;
            case  4:
                nurseNum = 1;
                break;

        }
        return  nurseNum;
    }

    public int rate2area(int rate){
        int area = 0;
        switch (rate){
            case 0:
                area =1;
                break;

            case 1:
                area = 2;
                break;
            case 2:
                area = 4;
                break;

        }
        return  area;
    }
    public int movingPresentPatient(Long patientId){
        Optional<Patient> customerOptional= patientRepository.findById(patientId);
        Patient patient =null;
        if (customerOptional.isPresent()) {
            patient = customerOptional.get();}

        assert patient !=null;
        if(patient.getLiving_status() != 0)
            return -1;

        int conditional_rating = patient.getCondition_rating();
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
        Set<Patient> allAreaP = patientRepository.findByTreatmentArea(type);
        int patientNum = 0;
        for(Patient patient1:allAreaP){
            if(patient1.getLiving_status() == 0)
                patientNum++;
        }
        int nurseNum = ward_nurses.size();
        System.out.println("bed Num: "+bedNum+"  ,patientNum "+patientNum+
                "   nurseNum * patientNumPerNurse: "+ nurseNum * patientNumPerNurse);
        if(patientNum < bedNum && patientNum < nurseNum * patientNumPerNurse) {

            System.out.println("patient " + patient.getName() + "  do not need to stay in present area");
            if(patient.getTreatmentArea()!=0){
                //隔离区的没有病床所以不需要解绑和病床的关系
                //非隔离区：和旧的病床/护士解绑
                Bed oldBed = patient.getBed();
                oldBed.setPatient(null);
                bedRepository.save(oldBed);
                Ward_nurse oldNurse = patient.getNurse();
                oldNurse.getPatients().remove(patient);
                wardNurseRepository.save(oldNurse);
            }

            patient.setNewPatient(1);//newPatient
            patient.setTreatmentArea(type);
            patient.setBed(null);
            for (Bed bed : beds) {
                if (bed.getPatient() == null) {
                    bed.setPatient(patient);
                    patient.setBed(bed);
                    bedRepository.save(bed);
                    break;
                }
            }

            for (Ward_nurse nurse : ward_nurses) {
                if (nurse.getPatients().size() < patientNumPerNurse) {
                    nurse.addPatients(patient);
                    wardNurseRepository.save(nurse);
                    System.out.println("nurse " + nurse.getUsername() + " add patient " + patient.getName());
                    patient.setNurse(nurse);
                    break;
                }
            }
            patientRepository.save(patient);
            return type;//移动病人成功
        }
        return -1;//移动病人失败

    }
    private int movePatient( int area) {
        Set<Patient> waitingPatient = patientRepository.findByTreatmentArea(0);
        if (!waitingPatient.isEmpty()) {//隔离区有病人等待
            for (Patient patient1 : waitingPatient) {
                System.out.println("waitint patient: "+patient1.getName());
                if ((rate2area(patient1.getCondition_rating()) == area)
                      && patient1.getLiving_status() == 0) {
                    return movingPresentPatient(patient1.getId());
                }
            }
        }
        Iterable<Patient> wrongPatient = patientRepository.findAll();
        for (Patient patient1 : wrongPatient) {
            if ((rate2area(patient1.getCondition_rating()) == area)
                    && patient1.getTreatmentArea() != rate2area(patient1.getCondition_rating())
                    && patient1.getLiving_status() == 0) {
                System.out.println("wrong are patient: "+patient1.getName()+
                        "  from "+patient1.getTreatmentArea()+" to "+area);
               return  movingPresentPatient(patient1.getId());

            }
        }

        return -1;
    }



}
