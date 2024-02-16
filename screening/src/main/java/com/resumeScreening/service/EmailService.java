package com.resumeScreening.service;

import com.resumeScreening.dto.EmailDetailsDto;

public interface EmailService {
    void sendEmail(EmailDetailsDto details);
}
