package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.AddPatientRequest;
import fudan.se.lab2.domain.Nucleic_acid_test_sheet;
import fudan.se.lab2.domain.Patient;
import fudan.se.lab2.exception.UsernameHasBeenRegisteredException;
import fudan.se.lab2.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
