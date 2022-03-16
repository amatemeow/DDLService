package uni.diploma.ddlservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DDLServiceMissingUserException extends DDLServiceBadRequestException {
    public DDLServiceMissingUserException() {
        super("It seems like entered user is missing! Try entering a valid one.");
    }
}
