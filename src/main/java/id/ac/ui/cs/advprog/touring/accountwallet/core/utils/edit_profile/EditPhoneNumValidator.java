package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.edit_profile;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile.InvalidPhoneNumFormatException;

public class EditPhoneNumValidator implements IValidator {
    private final EditPersonalDataRequest request;
    EditPhoneNumValidator(EditPersonalDataRequest request){
        this.request = request;
    }

    @Override
    public String validate(){
        String phoneNum = request.getPhoneNum();
        if (phoneNum == null || phoneNum.equals("")) return null;
        if (!phoneNum.matches("\\d+")) {
            throw new InvalidPhoneNumFormatException();
        }
        if (phoneNum.length() < 10 || phoneNum.length() > 15) {
            throw new InvalidPhoneNumFormatException();
        }
        return phoneNum;
    }
}
