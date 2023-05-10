package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.edit_profile;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile.InvalidFullNameFormatException;

public class EditFullNameValidator implements IValidator {
    private final EditPersonalDataRequest request;
    EditFullNameValidator(EditPersonalDataRequest request){
        this.request = request;
    }
    public String validate(){
        String name = request.getFullName();
        if (name == null) return null;
        int spaceCount = 0;

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                throw new InvalidFullNameFormatException();
            }
            if (Character.isWhitespace(c)) {
                spaceCount++;
                if (spaceCount > 2) {
                    throw new InvalidFullNameFormatException();
                }
            }
        }
        return name;
    }
}
