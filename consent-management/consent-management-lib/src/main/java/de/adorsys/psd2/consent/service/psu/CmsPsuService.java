/*
 * Copyright 2018-2019 adorsys GmbH & Co KG
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

package de.adorsys.psd2.consent.service.psu;

import de.adorsys.psd2.consent.domain.PsuData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CmsPsuService {
    /**
     * Checks if the specified psuData is in the psu list, if it is true then returns an element from the list.
     * If psuData is absent then returns this psu
     *
     * @param psuData     psu which will search in the list
     * @param psuDataList list where will search psuData
     * @return if the psuData is not in the psuDataList then will return this psuData otherwise return element form list
     */
    public PsuData definePsuDataForAuthorisation(PsuData psuData, List<PsuData> psuDataList) {
        return psuDataList.stream()
                   .filter(psu -> StringUtils.equals(psu.getPsuId(), psuData.getPsuId()))
                   .findAny()
                   .orElse(psuData);
    }

    /**
     * Adds psuData to psuDataList if it is not there
     *
     * @param psuData     psu which will search in the list
     * @param psuDataList list where will search psuData
     * @return List with psuData
     */
    public List<PsuData> enrichPsuData(PsuData psuData, List<PsuData> psuDataList) {
        if (isPsuDataNew(psuData, psuDataList)) {
            psuDataList.add(psuData);
        }
        return psuDataList;
    }

    /**
     * Checks if the specified psuData is in the psu list
     *
     * @param psuData     psu which will search in the list
     * @param psuDataList list where will search psuData
     * @return if the psuData is not in the psuDataList then will return true otherwise return false
     */
    public boolean isPsuDataNew(PsuData psuData, List<PsuData> psuDataList) {
        return Optional.ofNullable(psuData)
                   .map(psu -> !isPsuDataInList(psu, psuDataList))
                   .orElse(false);
    }

    /**
     * Checks if the specified psuData is in the psu list
     *
     * @param psuData     psu which will search in the list
     * @param psuDataList list where will search psuData
     * @return if the psuData is not in the psuDataList then will return false otherwise will return true
     */
    private boolean isPsuDataInList(PsuData psuData, List<PsuData> psuDataList) {
        return psuData.isNotEmpty()
                   && psuDataList.stream()
                          .anyMatch(psuData::contentEquals);
    }
}
