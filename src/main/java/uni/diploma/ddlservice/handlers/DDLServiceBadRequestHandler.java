package uni.diploma.ddlservice.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uni.diploma.ddlservice.exceptions.DDLServiceBadRequestException;
import uni.diploma.ddlservice.handlers.beans.BadRequestBean;

@ControllerAdvice
public class DDLServiceBadRequestHandler {

    @ExceptionHandler(DDLServiceBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BadRequestBean handleBadRequestException(DDLServiceBadRequestException e) {
        BadRequestBean badRequestBean = new BadRequestBean();
        badRequestBean.setMessage(e.getMessage());
        return badRequestBean;
    }
}
