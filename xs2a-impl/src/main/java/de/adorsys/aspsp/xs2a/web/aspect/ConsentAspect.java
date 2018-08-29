/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.aspsp.xs2a.web.aspect;

import de.adorsys.aspsp.xs2a.domain.Links;
import de.adorsys.aspsp.xs2a.domain.consent.CreateConsentResponse;
import de.adorsys.aspsp.xs2a.web.ConsentInformationController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class ConsentAspect extends AbstractLinkAspect<ConsentInformationController> {

    @AfterReturning(pointcut = "execution(* de.adorsys.aspsp.xs2a.web.ConsentInformationController.createAccountConsent(..)) && args(psuId, ..)", returning = "result")
    public ResponseEntity<CreateConsentResponse> invokeCreateAccountConsentAspect(ResponseEntity<CreateConsentResponse> result, String psuId) {
        if (!hasError(result)) {
            CreateConsentResponse body = result.getBody();
            body.setLinks(buildLinksForConsentResponse(body));
        }
        return new ResponseEntity<>(result.getBody(), result.getHeaders(), result.getStatusCode());
    }

    private Links buildLinksForConsentResponse(CreateConsentResponse response) {
        Links links = new Links();
        links.setScaRedirect(aspspProfileService.getAisRedirectUrlToAspsp() + response.getConsentId());

        return links;
    }
}