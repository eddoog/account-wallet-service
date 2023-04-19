package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.editProfile;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditPersonalDataRequest;

import java.util.ArrayList;
import java.util.List;

public class PersonalDataVerifier implements IVerifier {
    private EditPersonalDataRequest request;
    private final List<IValidator> steps;
    public PersonalDataVerifier(EditPersonalDataRequest request) {
        this.request = request;
        steps = new ArrayList<>();
        IValidator nameV = new EditNameValidator(request);
        this.steps.add(nameV);
        IValidator phoneV = new EditPhoneNumValidator(request);
        this.steps.add(phoneV);
        IValidator birthDateV = new EditBirthDateValidator(request);
        this.steps.add(birthDateV);
    }

    public List<String> verify() {
        List<String> verifiedData = new ArrayList<>();
        for(IValidator t : steps){
            String verified = t.validate();
            verifiedData.add(verified);
        }
        return verifiedData;
    }

}
