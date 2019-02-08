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

package de.adorsys.psd2.xs2a.spi.service;

import de.adorsys.psd2.xs2a.core.consent.AspspConsentData;
import de.adorsys.psd2.xs2a.core.pis.TransactionStatus;
import de.adorsys.psd2.xs2a.spi.domain.SpiContextData;
import de.adorsys.psd2.xs2a.spi.domain.payment.SpiPaymentInfo;
import de.adorsys.psd2.xs2a.spi.domain.payment.response.SpiPaymentInitiationResponse;
import de.adorsys.psd2.xs2a.spi.domain.response.SpiResponse;
import de.adorsys.psd2.xs2a.spi.domain.response.SpiResponseStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Interface to be used for payment SPI implementation
 */
public interface CommonPaymentSpi extends PaymentSpi<SpiPaymentInfo, SpiPaymentInitiationResponse> {
    @Override
    @NotNull
    default SpiResponse<SpiPaymentInitiationResponse> initiatePayment(@NotNull SpiContextData contextData, @NotNull SpiPaymentInfo payment, @NotNull AspspConsentData initialAspspConsentData) {
        return SpiResponse.<SpiPaymentInitiationResponse>builder().fail(SpiResponseStatus.NOT_SUPPORTED);
    }

    @Override
    @NotNull
    default SpiResponse<SpiPaymentInfo> getPaymentById(@NotNull SpiContextData contextData, @NotNull SpiPaymentInfo payment, @NotNull AspspConsentData aspspConsentData) {
        return SpiResponse.<SpiPaymentInfo>builder().fail(SpiResponseStatus.NOT_SUPPORTED);
    }

    @Override
    @NotNull
    default SpiResponse<TransactionStatus> getPaymentStatusById(@NotNull SpiContextData contextData, @NotNull SpiPaymentInfo payment, @NotNull AspspConsentData aspspConsentData) {
        return SpiResponse.<TransactionStatus>builder().fail(SpiResponseStatus.NOT_SUPPORTED);
    }
}
