package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.editProfile;

import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditPersonalDataRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.AgeRestrictionException;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.editProfile.InvalidBirthDateFormatException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class EditBirthDateValidator implements IValidator {
    private EditPersonalDataRequest request;
    EditBirthDateValidator(EditPersonalDataRequest request){
        this.request = request;
    }

    @Override
    public String validate() throws AgeRestrictionException, DateTimeParseException {
        try {
            String birthDate = request.getBirthDate();
            LocalDate dateOfBirth = LocalDate.parse(birthDate, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int age = LocalDate.now().getYear() - dateOfBirth.getYear();
            if (age < 17 || age > 100) {
                throw new AgeRestrictionException();
            }
            return birthDate;
        } catch (DateTimeParseException e) {
             throw new InvalidBirthDateFormatException();
        }
    }
}
