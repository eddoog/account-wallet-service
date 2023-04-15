package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.editProfile;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditUsernameRequest;

public class EditUsernameValidator implements IValidator {

    private EditUsernameRequest request;
    EditUsernameValidator(EditUsernameRequest request){
        this.request = request;
    }

    @Override
    public String validate() {
        return null;
    }
}