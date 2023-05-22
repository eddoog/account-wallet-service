package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.edit_profile;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.edit_profile.EditUsernameRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.edit_profile.InvalidUsernameFormatException;

public class EditUsernameValidator implements IValidator {

    private final EditUsernameRequest request;
    EditUsernameValidator(EditUsernameRequest request){
        this.request = request;
    }

    @Override
    public String validate() {
        String username = request.getUsername();
        var pattern = "^[A-Za-z0-9_-]+$";
        if (!username.matches(pattern)){
            throw new InvalidUsernameFormatException();
        } else {
            return username;
        }
    }
}