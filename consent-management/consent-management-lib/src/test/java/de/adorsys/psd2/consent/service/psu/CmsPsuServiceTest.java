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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CmsPsuServiceTest {
    private static final String PSU_ID_1 = "psu id 1";
    private static final String PSU_ID_2 = "psu id 2";
    private static final String PSU_ID_3 = "psu id 3";
    private final PsuData PSU_DATA_1 = new PsuData(PSU_ID_1, "psu type", "corp id", "corp type");
    private final PsuData PSU_DATA_2 = new PsuData(PSU_ID_2, "psu type", "corp id", "corp type");
    private final PsuData PSU_DATA_3 = new PsuData(PSU_ID_3, "psu type", "corp id", "corp type");
    private final List<PsuData> PSU_DATA_LIST = new ArrayList<>(Arrays.asList(PSU_DATA_1, PSU_DATA_2));

    @InjectMocks
    CmsPsuService cmsPsuService;

    @Test
    public void definePsuDataForAuthorisation_PsuIsExistInList() {
        PsuData actualResult = cmsPsuService.definePsuDataForAuthorisation(PSU_DATA_1, PSU_DATA_LIST);

        assertThat(actualResult).isEqualTo(PSU_DATA_LIST.get(0));
    }

    @Test
    public void definePsuDataForAuthorisation_PsuIsNotExistInList() {
        PsuData actualResult = cmsPsuService.definePsuDataForAuthorisation(PSU_DATA_3, PSU_DATA_LIST);

        assertThat(actualResult).isEqualTo(PSU_DATA_3);
    }

    @Test
    public void enrichPsuData_PsuIsExistInList() {
        List<PsuData> actualResult = cmsPsuService.enrichPsuData(PSU_DATA_1, PSU_DATA_LIST);

        assertThat(actualResult.size()).isEqualTo(2);
        assertThat(actualResult.containsAll(Arrays.asList(PSU_DATA_1, PSU_DATA_2))).isTrue();
    }

    @Test
    public void enrichPsuData_PsuIsNotExistInList() {
        List<PsuData> actualResult = cmsPsuService.enrichPsuData(PSU_DATA_3, PSU_DATA_LIST);

        assertThat(actualResult.size()).isEqualTo(3);
        assertThat(actualResult.containsAll(Arrays.asList(PSU_DATA_1, PSU_DATA_2, PSU_DATA_3))).isTrue();
    }

    @Test
    public void isPsuDataNew_PsuIsExistInList() {
        boolean actualResult = cmsPsuService.isPsuDataNew(PSU_DATA_1, PSU_DATA_LIST);

        assertThat(actualResult).isFalse();
    }

    @Test
    public void isPsuDataNew_PsuIsNull() {
        boolean actualResult = cmsPsuService.isPsuDataNew(null, PSU_DATA_LIST);

        assertThat(actualResult).isFalse();
    }

    @Test
    public void isPsuDataNew_PsuIsNotExistInList() {
        boolean actualResult = cmsPsuService.isPsuDataNew(PSU_DATA_3, PSU_DATA_LIST);

        assertThat(actualResult).isTrue();
    }
}
