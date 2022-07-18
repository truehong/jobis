package com.test.jabis.tax.service;

import com.test.jabis.tax.dto.RefundResponse;
import com.test.jabis.tax.dto.ScrapResponse;
import com.test.jabis.user.dao.User;

public interface TaxService {
    ScrapResponse getUserScrap(User user, String token);

    RefundResponse getRefund(User user, String token);
}
