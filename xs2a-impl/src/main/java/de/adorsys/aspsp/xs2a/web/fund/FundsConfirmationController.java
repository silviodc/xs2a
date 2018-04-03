package de.adorsys.aspsp.xs2a.web.fund;

import de.adorsys.aspsp.xs2a.domain.fund.FundsConfirmationRequest;
import de.adorsys.aspsp.xs2a.domain.fund.FundsConfirmationResponse;
import de.adorsys.aspsp.xs2a.service.FundsConfirmationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class FundsConfirmationController implements IFundsConfirmationController {
    private FundsConfirmationService fundsConfirmationService;

    @Autowired
    public FundsConfirmationController(FundsConfirmationService fundsConfirmationService) {
        this.fundsConfirmationService = fundsConfirmationService;
    }

    @PostMapping
    public FundsConfirmationResponse fundConfirmation(@RequestBody FundsConfirmationRequest request) {
        return new FundsConfirmationResponse(fundsConfirmationService.fundsConfirmation(request));
    }
}
