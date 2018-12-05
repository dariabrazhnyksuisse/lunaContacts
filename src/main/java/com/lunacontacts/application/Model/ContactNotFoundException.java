package com.lunacontacts.application.Model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "contact not found")
public class ContactNotFoundException extends RuntimeException {
}
