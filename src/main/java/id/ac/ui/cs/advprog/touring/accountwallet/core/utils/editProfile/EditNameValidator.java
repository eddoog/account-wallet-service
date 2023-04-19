package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.editProfile;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.editProfile.InvalidNameFormatException;

public class EditNameValidator implements IValidator {
    private EditPersonalDataRequest request;
    EditNameValidator(EditPersonalDataRequest request){
        this.request = request;
    }
    public String validate(){
        String name = request.getFullName();
        int spaceCount = 0;

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                throw new InvalidNameFormatException();
            }
            if (Character.isWhitespace(c)) {
                spaceCount++;
                if (spaceCount > 2) {
                    throw new InvalidNameFormatException();
                }
            }
        }
        return name;
    }
}
