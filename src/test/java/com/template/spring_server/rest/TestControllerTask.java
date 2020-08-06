package com.template.spring_server.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TestControllerTask {
    @Autowired
    private MockMvc mvc;

 /*   @TestConfiguration
    static class TestModelConfiguration {
        @Bean
        TestModel beanTestModel() {return new TestModel("ok");}
    }*/

    @Test
    public void ping() throws Exception {
        mvc.perform(get("/tasks/ping"))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));
    }
}
