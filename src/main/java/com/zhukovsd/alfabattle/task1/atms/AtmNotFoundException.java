package com.zhukovsd.alfabattle.task1.atms;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "asdf")
class AtmNotFoundException extends RuntimeException {}
