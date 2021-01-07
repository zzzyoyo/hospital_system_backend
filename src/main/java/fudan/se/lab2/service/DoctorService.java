package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.AddAcidTestRequest;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.exception.UsernameNotFoundException;
import fudan.se.lab2.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.util.*;

@Service
public class DoctorService {
    private DoctorRepository doctorRepository;
    private HeadNurseRepository headNurseRepository;
    private WardNurseRepository wardNurseRepository;
    private EmergencyNurseRepository emergencyNurseRepository;
    private TreatmentAreaRepository treatmentAreaRepository;
    private PatientRepository patientRepository;
    private BedRepository bedRepository;
    private NucleicAcidTestSheetRepository nucleicAcidTestSheetRepository;
    Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    public DoctorService(DoctorRepository doctorRepository,
                         HeadNurseRepository headNurseRepository,
                         WardNurseRepository wardNurseRepository,
                         EmergencyNurseRepository emergencyNurseRepository,
                         PatientRepository patientRepository,
                         BedRepository bedRepository,
                         NucleicAcidTestSheetRepository nucleicAcidTestSheetRepository,
                         TreatmentAreaRepository treatmentAreaRepository)
    {
        this.doctorRepository = doctorRepository;
        this.headNurseRepository = headNurseRepository;
        this.wardNurseRepository  = wardNurseRepository;
        this.emergencyNurseRepository = emergencyNurseRepository;
        this.patientRepository = patientRepository;
        this.bedRepository = bedRepository;
        this.nucleicAcidTestSheetRepository = nucleicAcidTestSheetRepository;
        this.treatmentAreaRepository = treatmentAreaRepository;

    }


    /**
     * anyCanLeave：//0：没有可以出院的 1：有可以出院的
     *  area：int//医生负责的病区id号，
     *   headNurse：string//该病区的护士长的username
     *   wardNurse_tableData：[object]//病区的病房护士以及她们负责的病人，都返回名字，不用id，比如
     *   [{name:'111', patients:['aaa','bbb','ccc']},
     *   {name:'121', patients:['aaa','bbb','ccc']},
     *   {name:'131', patients:['aaa','bbb','ccc']}]
     *   patient_tableData：[object]//病区的病人的列表，比如[{
     *   patientID:1,
     *   username: '王小虎',
     *   condition_rating: 0,
     *   living_status:0,
     * },{
     *   patientID:2,
     *   username: '王小虎',
     *   condition_rating: 0,
     *   living_status:0,
     * }]
     * @param username
     * @return
     */

    public Map<String, Object>initialDoctor(String username){
        Map<String,Object>returnMap = new HashMap<>();
        Set<Map> n_set = new HashSet<>();
        Doctor doctor = doctorRepository.findByUsername(username);
        if(doctor ==null){
            throw new UsernameNotFoundException(username);
        }
        Treatment_area treatmentArea = doctor.getTreatment_area();
        int area = treatmentArea.getType();
        int anyCanLeave = 0;
        Set<Patient>patients = patientRepository.findByTreatmentArea(area);

        if(selectLeave(0,patients).size()>0){
            anyCanLeave =1;
        };
        returnMap.put("anyCanLeave",anyCanLeave);

        returnMap.put("area",area);
        String headNurse = treatmentArea.getHead_nurse().getUsername();
        returnMap.put("headNurse",headNurse);
        Set<Ward_nurse> ward_nurseSet = treatmentArea.getWard_nurses();
        for(Ward_nurse nurse : ward_nurseSet) {
            Map<String, Object> temp = new HashMap<>();
            temp.put("name", nurse.getUsername());

            Set<String> patientSet = new HashSet<>();
            for (Patient p : nurse.getPatients()) {
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
        return returnMap;

    }

    /**
     * 修改病人的病情评级
     * 权限：doctor
     * /ratingRevise
     * request:{
     *   patiendID:int,
     *   condition_rating:int//0：轻症 1： 重症 2：危重症
     * }
     * response:{
     *   no
     * }
     * code:{
     *   200:success,
     *   other:failure
     * }
     */
    public int movingPresentPatient(Long patientId,int conditional_rating){
        Optional<Patient> customerOptional= patientRepository.findById(patientId);
        Patient patient =null;
        if (customerOptional.isPresent()) {
            patient = customerOptional.get();}

        assert patient !=null;
        patient.setCondition_rating(conditional_rating);
        patientRepository.save(patient);//无论怎样评级都是要改的

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
        if(patientNum < bedNum && patientNum < nurseNum * patientNumPerNurse) {

            System.out.println("patient " + patient.getName() + "  do not need to stay in present area");
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
            Ward_nurse oldNurse = patient.getNurse();
            oldNurse.getPatients().remove(patient);
            patient.setNurse(null);
            wardNurseRepository.save(oldNurse);
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

    public Map<String,Integer> ratingRevise(int patientID,int condition_rating){
        Long patientId  = new Long(patientID);
        Map<String,Integer> returnMap = new HashMap<>();
        Optional<Patient> customerOptional= patientRepository.findById(patientId);
        if (customerOptional.isPresent()) {
            Patient patient = customerOptional.get();
            int old_condition_rating  = patient.getCondition_rating();
            int old_area = patient.getTreatmentArea();
            int accType = movingPresentPatient(patientId,condition_rating);
            if( accType!= -1){//当前病区移动成功
                int isolateFlag = 0;
                Set<Patient> waitingPatient = patientRepository.findByTreatmentArea(0);
                if(!waitingPatient.isEmpty()){//隔离区有病人等待
                    for(Patient patient1:waitingPatient ){
                        if(patient1.getCondition_rating()==old_condition_rating){
                            movingPresentPatient(patient1.getId(),condition_rating);
                            isolateFlag = 1;
                            break;
                        }
                    }

                }
                if(isolateFlag ==0){//隔离区没有病人
                    Iterable<Patient>wrongPatient = patientRepository.findAll();
                    for(Patient patient1:wrongPatient){
                        if(patient1.getCondition_rating() ==old_condition_rating&&patient1.getTreatmentArea()!=old_area){
                            movingPresentPatient(patient1.getId(),old_condition_rating);
                            break;
                        }
                    }

                }
                returnMap.put("transSuccess",1);
                return returnMap;//至少有一个病人移动
            }

        }
        //失败或者没有病人移动
        returnMap.put("transSuccess",0);
        return returnMap;
    }


    /**
     *修改病人的生命状态
     * 权限：doctor
     * /statusRevise
     * request:{
     *   patiendID:int,
     *   living_status:int//0：住院 1：出院 2：死亡
     * }
     * response:{
     *   no
     * }
     * code:{
     *   200:success,
     *   other:failure
     * }
     * 出院，死亡从医护关系，病床病人关系中解绑
     */
    public int statusRevise(int patientID,int living_status){
        Long patientId  = new Long(patientID);
        Optional<Patient> customerOptional= patientRepository.findById(patientId);
        if (customerOptional.isPresent()) {
            Patient patient = customerOptional.get();
            patient.setLiving_status(living_status);
            patientRepository.save(patient);
            if(living_status ==1||living_status==2){//出院/死亡
                Bed bed = patient.getBed();
                bed.setPatient(null);
                bedRepository.save(bed);
                Ward_nurse ward_nurse = patient.getNurse();
                ward_nurse.getPatients().remove(patient);
                wardNurseRepository.save(ward_nurse);
                patient.setTreatmentArea(0);
                patient.setBed(null);
                patient.setNurse(null);
                patientRepository.save(patient);
            }

            return 0;
        }
        return -1;
    }

    /**
     * 支持不同条件的查询，返回满足request的条件对应的病人
     * 权限：doctor
     * '/select',
     * request：{
     *   type: int,//要查的area的type
     *   leave: int,//是否满足出院条件，0返回满足出院条件的病人，1否，2不筛选，即都可以返回
     *   出院： 连续3天体温低于37.3
     *   连续两次核酸检测间隔超过24h
     *   trans: int,//是否待转入其他治疗区域，0是，1否，2都可以
     *   status: int，//生命状态，0：住院 1：出院 2：死亡，3都可以
     * }
     * response：{
     *   patient_tableData：和/doctor中的patient_tableData一样
     * }
     * */

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
            if(condition ==2||treatment!=4)//病人为危重2
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
            if(patient.getCondition_rating() != 0){
                //必须是轻症患者!
                continue;
            }
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

    /**
     * 查询给定病人的所有记录
     * '/dailyStatusRecord',
     * request：{
     *   patientID: this.patientID
     * })
     * response：{
     *   recordTable：像这样的表格
     *   [{date:'2021年1月2日15:19:08', result:'阴性', temperature:'37℃',symptom:'正常',living_status:0},
     *   {date:'2021年1月2日15:30:36', result:'阴性', temperature:'37℃',symptom:'正常',living_status:0}]
     * }
     */
    public Map<String,Set>dailyStatusRecord(int patientID){
        Long patientId  = new Long(patientID);
      Map<String,Set>returnMap = new HashMap<>();

        Set<Map> p_set = new HashSet<>();
        Optional<Patient> customerOptional= patientRepository.findById(patientId);
        if (customerOptional.isPresent()) {
            Patient patient = customerOptional.get();
            Set<Daily_state_records> daily_state_records = patient.getDaily_state_records();
            for(Daily_state_records daily_state_record:daily_state_records){
                Map<String , Object>temp = new HashMap<>();
                temp.put("date",daily_state_record.getDate());
                temp.put("result",daily_state_record.getNucleic_acid_test_result());
                temp.put("temperature",daily_state_record.getTemperature());
                temp.put("symptom",daily_state_record.getSymptom());
                temp.put("living_status",daily_state_record.getLiving_status());
                p_set.add(temp);
            }
            System.out.println("dailyStatusRecord num"+p_set.size());

        }
        returnMap.put("recordTable",p_set);
        return returnMap;

    }

    /**
     * 查询给定病人的所有核酸检测单
     * '/nucleicAcidTestSheet',
     * request：{
     *   patientID: this.patientID
     * })
     * response：{
     *   sheetTable：像这样的表格
     *   [{date:'2021年1月2日15:19:08', result:'阴性', condition_rating:0},
     *   {date:'2021年1月2日15:19:08', result:'阳性', condition_rating:0}]
     * }
     */
    public Map<String,Set>nucleicAcidTestSheet(int patientID){
        Long patientId  = new Long(patientID);
        Map<String,Set>returnMap = new HashMap<>();

        Set<Map> p_set = new HashSet<>();
        Optional<Patient> customerOptional= patientRepository.findById(patientId);
        if (customerOptional.isPresent()) {
            Patient patient = customerOptional.get();
            Set<Nucleic_acid_test_sheet>nucleic_acid_test_sheets = patient.getNucleic_acid_test_sheets();
            for(Nucleic_acid_test_sheet nucleic_acid_test_sheet: nucleic_acid_test_sheets){
                Map<String , Object>temp = new HashMap<>();
                temp.put("date",nucleic_acid_test_sheet.getDate());
                temp.put("result",nucleic_acid_test_sheet.getResult());
                temp.put("condition_rating",nucleic_acid_test_sheet.getConditional_rating());
                 p_set.add(temp);
            }
            System.out.println("nucleic acid test sheets num"+p_set.size());
        }
        returnMap.put("sheetTable",p_set);
        return returnMap;

    }

    public String addAcidTest(AddAcidTestRequest addAcidTestRequest){
        String username = addAcidTestRequest.getPatientName();
         int condition_rating = addAcidTestRequest.getCondition_rating();//012,
        int result = addAcidTestRequest.getResult();//阴性 = 0，阳性 =1,
         Date date = addAcidTestRequest.getDate();// date类型

        Patient patient = patientRepository.findByName(addAcidTestRequest.getPatientName());
        if(patient ==null)
            throw new UsernameNotFoundException(username);
        Nucleic_acid_test_sheet nucleic_acid_test_sheet = new Nucleic_acid_test_sheet();
        nucleic_acid_test_sheet.setPatient(patient);
        nucleic_acid_test_sheet.setResult(result);
        nucleic_acid_test_sheet.setDate(date);
        nucleic_acid_test_sheet.setConditional_rating(condition_rating);
        nucleicAcidTestSheetRepository.save(nucleic_acid_test_sheet);
        patient.add_Nucleic_acid_test_sheet(nucleic_acid_test_sheet);
        patientRepository.save(patient);
        return "success";


    }
}
