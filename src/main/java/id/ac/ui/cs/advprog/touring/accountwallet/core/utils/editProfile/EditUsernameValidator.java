package id.ac.ui.cs.advprog.touring.accountwallet.core.utils.editProfile;
import id.ac.ui.cs.advprog.touring.accountwallet.dto.editProfile.EditUsernameRequest;
import id.ac.ui.cs.advprog.touring.accountwallet.exception.editProfile.InvalidUsernameFormatException;

public class EditUsernameValidator implements IValidator {

    private final EditUsernameRequest request;
    EditUsernameValidator(EditUsernameRequest request){
        this.request = request;
    }

    @Override
    public String validate() {
        String username = request.getUsername();
        if (username.equals("")) return "";
        String pattern = "^[A-Za-z0-9_-]+$";
        if (!username.matches(pattern)){
            throw new InvalidUsernameFormatException();
        } else {
            return username;
        }
    }
}