package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.editProfile;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.editProfile.InvalidPhoneNumFormatException;

public class EditPhoneNumValidator implements IValidator {
    private EditPersonalDataRequest request;
    EditPhoneNumValidator(EditPersonalDataRequest request){
        this.request = request;
    }

    @Override
    public String validate(){
        String phoneNum = request.getPhoneNum();
        if (!phoneNum.matches("\\d+")) {
            throw new InvalidPhoneNumFormatException();
        }
        // Check if the input has between 10 and 15 digits
        if (phoneNum.length() < 10 || phoneNum.length() > 15) {
            throw new InvalidPhoneNumFormatException();
        }
        return phoneNum;
    }
}
