package fudan.se.lab2.service;

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
    private PatientRepository patientRepository;
    Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    public DoctorService(DoctorRepository doctorRepository,
                         HeadNurseRepository headNurseRepository,
                         WardNurseRepository wardNurseRepository,
                         EmergencyNurseRepository emergencyNurseRepository,
                         PatientRepository patientRepository){
        this.doctorRepository = doctorRepository;
        this.headNurseRepository = headNurseRepository;
        this.wardNurseRepository  = wardNurseRepository;
        this.emergencyNurseRepository = emergencyNurseRepository;
        this.patientRepository = patientRepository;

    }

    /**
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
        Set<Patient>patients = patientRepository.findByTreatmentArea(area);
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
    public int ratingRevise(int patientID,int condition_rating){
        Long patientId  = new Long(patientID);
        Optional<Patient> customerOptional= patientRepository.findById(patientId);
        if (customerOptional.isPresent()) {
            Patient patient = customerOptional.get();
            patient.setCondition_rating(condition_rating);
            patientRepository.save(patient);
            return 0;
        }
        return -1;
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
     */
    public int statusRevise(int patientID,int living_status){
        Long patientId  = new Long(patientID);
        Optional<Patient> customerOptional= patientRepository.findById(patientId);
        if (customerOptional.isPresent()) {
            Patient patient = customerOptional.get();
            patient.setLiving_status(living_status);
            patientRepository.save(patient);
            return 0;
        }
        return -1;
    }

    /**
     * 支持不同条件的查询，返回满足request的条件对应的病人
     * 权限：doctor
     * '/select',
     * request：{
     *   areaID: int,//要查的area的id
     *   leave: int,//是否满足出院条件，0返回满足出院条件的病人，1否，2不筛选，即都可以返回
     *   出院： 连续3天体温低于37.3
     *   连续两次核酸检测间隔超过24h
     *   trans: int,//是否待转入其他治疗区域，0是，1否，2都可以
     *   status: int，//生命状态，0：住院 1：出院 2：死亡，3都可以
     * }
     * response：{
     *   patient_tableData：和/doctor中的patient_tableData一样
     * }

    public Map<String, Object> select(int type, int leave,int trans,int status){


    }*/

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

        }
        returnMap.put("sheetTable",p_set);
        return returnMap;

    }
}
