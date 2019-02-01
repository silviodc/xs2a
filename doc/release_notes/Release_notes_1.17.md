# Release notes v. 1.17

## Bugfix: not able to retrieve the payment by redirect id when psu-id is not set in the initial request
When retrieving the payment by redirect id (endpoint GET /psu-api/v1/pis/consent/redirects/{redirect-id} in consent-management) now there is no need
to provide psu data and the payment can be retrieved without it, only by redirect id and instance id.

This also applies for getting payment for cancellation (endpoint GET /psu-api/v1/payment/cancellation/redirect/{redirect-id} in consent-management)

## Bugfix: Remove PSU Data from endpoint for getting consent by redirect id in CMS-PSU-API
PSU Data is no longer required for getting consent by redirect id.
As a result, headers `psu-id`, `psu-id-type`, `psu-corporate-id` and `psu-corporate-id-type` are no longer used in `psu-api/v1/ais/consent/redirect/{redirect-id}` endpoint.
PsuIdData was also removed as an argument from `de.adorsys.psd2.consent.psu.api.CmsPsuAisService#checkRedirectAndGetConsent` method.

## Delete old 'tpp demo' 
Deleted extra functional in 'tpp-demo' package

## Support of multiple SCA approaches
ASPSP can support several SCA approaches, so now XS2A Interface supports multiple SCA approaches also.
ASPSP profile was extended with list of approaches (in order of priority - first one with the highest priority) instead of one single approach.

| Option                                   | Meaning                                                                                                | Default value | 
|------------------------------------------|--------------------------------------------------------------------------------------------------------|---------------|
| scaApproaches                            | List of SCA Approach supported by ASPSP ordered by priority                                            |  - REDIRECT   |
|                                          |                                                                                                        |  - EMBEDDED   |
|                                          |                                                                                                        |  - DECOUPLED  |

Choice of SCA approaches depends on header parameter in payment,consent or signing basket initiation request - `TPP-Redirect-Preferred`. For Payment Cancellation parameter "TPP-Redirect-Preferred" is not used.
If `TPP-Redirect-Preferred` is true and ASPSP supports REDIRECT approach, then `REDIRECT` approach is used. Otherwise first approach in ASPSP-profile option `scaApproaches` is used.