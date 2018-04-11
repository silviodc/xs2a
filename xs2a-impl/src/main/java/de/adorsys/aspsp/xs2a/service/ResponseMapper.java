package de.adorsys.aspsp.xs2a.service;

import de.adorsys.aspsp.xs2a.domain.*;
import de.adorsys.aspsp.xs2a.exception.MessageCategory;
import de.adorsys.aspsp.xs2a.exception.MessageError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.valueOf;

@Service
public class ResponseMapper {

    public ResponseEntity ok(ResponseObject response) {
        return getEntity(response, HttpStatus.OK);
    }

    public ResponseEntity okOrNotFound(ResponseObject response) {
        return getEntity(response, response.getBody() != null
                                   ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    public ResponseEntity okOrByTransactionStatus(ResponseObject response) {
        PaymentInitialisationResponse pi = (PaymentInitialisationResponse) response.getBody();
        return (pi.getTransaction_status() == TransactionStatus.ACCP)
               ? new ResponseEntity<>(response.getBody(), HttpStatus.OK)
               : new ResponseEntity<>(
        new MessageError(pi.getTransaction_status(), new TppMessageInformation(MessageCategory.ERROR, MessageCode.PAYMENT_FAILED)),
        HttpStatus.valueOf(MessageCode.PAYMENT_FAILED.getCode()));
    }

    private ResponseEntity getEntity(ResponseObject response, HttpStatus status) {
        MessageError messageError = response.getError();
        return messageError != null
               ? new ResponseEntity<>(messageError, valueOf(messageError.getTppMessage().getCode().getCode()))
               : new ResponseEntity<>(response.getBody(), status);
    }
}
