package com.aceleradora.pedidosentregas.client;

import com.aceleradora.pedidosentregas.model.email.EmailRequest;
import com.aceleradora.pedidosentregas.model.email.EmailResponse;

public interface EmailSenderClient {
    EmailResponse sendEmail(EmailRequest email);
}
