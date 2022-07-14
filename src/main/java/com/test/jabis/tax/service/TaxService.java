package com.test.jabis.tax.service;

import com.test.jabis.common.dto.ScrapResponse;

public interface TaxService {
    ScrapResponse getUserScrap();

    RefundResponse getRefund();
}
