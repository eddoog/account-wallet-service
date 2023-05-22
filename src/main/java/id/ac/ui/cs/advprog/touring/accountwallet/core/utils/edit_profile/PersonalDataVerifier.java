package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.edit_profile;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditPersonalDataRequest;

import java.util.ArrayList;
import java.util.List;

public class PersonalDataVerifier implements IVerifier {
    private final List<IValidator> steps;
    public PersonalDataVerifier(EditPersonalDataRequest request) {
        steps = new ArrayList<>();
        IValidator nameV = new EditFullNameValidator(request);
        this.steps.add(nameV);
        IValidator phoneV = new EditPhoneNumValidator(request);
        this.steps.add(phoneV);
        IValidator birthDateV = new EditBirthDateValidator(request);
        this.steps.add(birthDateV);
    }

    public synchronized List<String> verify() {
        List<String> verifiedData = new ArrayList<>();
        for(IValidator t : steps){
            String verified = t.validate();
            verifiedData.add(verified);
        }
        return verifiedData;
    }

}
