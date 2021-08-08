package com.example.demo.user;


import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.BaseConrollerTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;


@Slf4j
public class UserControllerTest extends BaseConrollerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void getOne() throws Exception {
    String id = "id01";
    mockMvc.perform(get("/data/users/{id}", id))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("_links.self").exists())
        .andDo(document("users-get",
            links(
                linkWithRel("self").description("This <<resources-user,user>>"),
                linkWithRel("user").description("This <<resources-user,user>>"),
                linkWithRel("orders").description("This <<resources-user,user>>")
            ),
            responseFields(
                fieldWithPath("id").description("id"),
                fieldWithPath("name").description("Name"),
                fieldWithPath("username").description("Username"),
                subsectionWithPath("_links").description("<<resources-user-links,Links>> to other resources")
            )
        ))
    ;
  }

  @Test
  void getList() throws Exception {
    mockMvc.perform(get("/data/users")
            .param("page", "0")
            .param("size", "5")
            .param("sort", "name,DESC")
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded.users[0]._links.self").exists())
        .andExpect(jsonPath("page").exists())
        .andDo(document("users-list",
            responseFields(
                fieldWithPath("page").description(""),
                subsectionWithPath("_embedded.users").description("An array of <<resources-user, User resources>>"),
                subsectionWithPath("_links").description("<<resources-user-links,Links>> to other resources"),
                subsectionWithPath("page").description("")
            )
        ))
    ;
  }


  @Test
  void getOne_404() throws Exception {
    String id = "id404";
    mockMvc.perform(get("/data/users/{id}", id))
        .andDo(print())
        .andExpect(status().isBadRequest())
    ;
  }


}
