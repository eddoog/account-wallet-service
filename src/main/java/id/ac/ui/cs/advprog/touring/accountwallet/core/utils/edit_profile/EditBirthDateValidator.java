package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.edit_profile;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile.InvalidBirthDateFormatException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile.AgeRestrictionException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class EditBirthDateValidator implements IValidator {
    private final EditPersonalDataRequest request;
    EditBirthDateValidator(EditPersonalDataRequest request){
        this.request = request;
    }

    @Override
    public String validate(){
        String birthDate = request.getBirthDate();
        if (birthDate == null) return null;
        try {
            LocalDate dateOfBirth = LocalDate.parse(birthDate, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int age = LocalDate.now().getYear() - dateOfBirth.getYear();
            if (age < 13 || age > 100) {
                throw new AgeRestrictionException();
            }
            return birthDate;
        } catch (DateTimeParseException e) {
             throw new InvalidBirthDateFormatException();
        }
    }
}
