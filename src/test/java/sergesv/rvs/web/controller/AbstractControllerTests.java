package sergesv.rvs.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import sergesv.rvs.RvsPropertyResolver;

import static sergesv.rvs.util.TestData.ADMIN;
import static sergesv.rvs.util.TestData.USER_1;

public abstract class AbstractControllerTests {
    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected RvsPropertyResolver propertyResolver;

    protected TestRestTemplate adminRestTemplate() {
        return restTemplate.withBasicAuth(ADMIN.getNickName(), ADMIN.getPassword());
    }

    protected TestRestTemplate userRestTemplate() {
        return restTemplate.withBasicAuth(USER_1.getNickName(), USER_1.getPassword());
    }
}
