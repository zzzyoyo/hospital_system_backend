package fudan.se.lab2.service;

import fudan.se.lab2.domain.*;
import fudan.se.lab2.exception.UsernameNotFoundException;
import fudan.se.lab2.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        return returnMap;
    }

    public int deleteNurse(String nurseName, int area_type){
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
        Ward_nurse ward_nurse = wardNurseRepository.findByUsername(nurseName);
        if(ward_nurse.getTreatment_area() == null){
            Treatment_area treatment_area = treatmentAreaRepository.findByType(area_type);
            Set<Ward_nurse> ward_nurses = treatment_area.getWard_nurses();
            if(!ward_nurses.contains(ward_nurse)){
                ward_nurses.add(ward_nurse);
                treatmentAreaRepository.save(treatment_area);
                ward_nurse.setTreatment_area(treatment_area);
                wardNurseRepository.save(ward_nurse);
                return 0;
            }
        }
        return -1;
    }
}
