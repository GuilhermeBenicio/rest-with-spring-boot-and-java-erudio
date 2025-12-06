package br.com.erudio.controllers;

import br.com.erudio.services.PersonServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/test/v1")
public class TestLogController {
    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @GetMapping
    public String test() {
        logger.debug("Esse é um DEBUG log");
        logger.info("Esse é um INFO log");
        logger.warn("Esse é um WARN log");
        logger.error("Esse é um ERROR log");
        return "Logs gerados com sucesso";
    }
}
