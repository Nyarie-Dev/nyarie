package eu.nyarie.core;

import eu.nyarie.core.api.ApiClass;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class CoreClass {

    String accessApi() {
        log.error("Hello World!");
        val apiClass = new ApiClass();
        return apiClass.getMessage();
    }
}
