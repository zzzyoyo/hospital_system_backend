package fudan.se.lab2.service;

import fudan.se.lab2.domain.Patient;
import fudan.se.lab2.domain.Treatment_area;
import fudan.se.lab2.domain.Ward_nurse;
import fudan.se.lab2.exception.UsernameNotFoundException;
import fudan.se.lab2.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class WNurseService {

    private DoctorRepository doctorRepository;
    private HeadNurseRepository headNurseRepository;
    private WardNurseRepository wardNurseRepository;
    private EmergencyNurseRepository emergencyNurseRepository;
    private PatientRepository patientRepository;
    Logger logger = LoggerFactory.getLogger(AuthService.class);

    public WNurseService(DoctorRepository doctorRepository,
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
}
