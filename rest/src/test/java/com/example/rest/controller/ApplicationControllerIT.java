package com.example.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;


@DisplayName("integration test for ApplicationController with endpoint invoking")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
class ApplicationControllerIT {

    @Autowired
    MockMvc mvc;

    @Test
    void getRandomInfo_shouldReturnCorrectString_returned() throws Exception {
        ResultMatcher response = new ResultMatcher() {
            @Override
            public void match(MvcResult result) throws Exception {
                Objects.requireNonNull(result.getResponse().getContentAsString()).startsWith("MVC application №");
            }
        };

        mvc.perform(get("/security/info"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(response);
    }

}