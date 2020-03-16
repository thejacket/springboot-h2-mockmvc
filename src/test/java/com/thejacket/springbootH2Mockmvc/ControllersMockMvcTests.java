package com.thejacket.springbootH2Mockmvc;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ControllersMockMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAuthorization() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/products")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
        this.mockMvc.perform(MockMvcRequestBuilders.post("/product")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/missions")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
        this.mockMvc.perform(MockMvcRequestBuilders.post("/mission")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
        this.mockMvc.perform(MockMvcRequestBuilders.post("/order")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/user_report/user1")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/user_report/user1").with(httpBasic("user1","user1Pass"))).andExpect(MockMvcResultMatchers.status().is4xxClientError());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/products/report/*").with(httpBasic("user1","user1Pass"))).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void testMissionCreationAndRetrieval() throws Exception {
        String json = "{\"id\":\"123\"," +
                "\"missionName\":\"TESTMISSION\"," +
                "\"missionStartDate\":\"2019-01-01\"," +
                "\"missionFinishDate\":\"2019-03-01\"," +
                "\"missionImageryType\":\"Panchromatic\"" +
                "}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/mission").with(httpBasic("manager1", "manager1Pass")).contentType(MediaType.APPLICATION_JSON).content(json)).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("SUCCESS")));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/missions?id=123").with(httpBasic("manager1", "manager1Pass"))).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("TESTMISSION")));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/missions?id=123456789").with(httpBasic("manager1", "manager1Pass"))).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
              .andExpect(MockMvcResultMatchers.content().string(Matchers.equalTo("[]")));
    }

    @Test
    public void testProductCreationAndRetrieval() throws Exception {
        String json = "{\"id\":\"123\"," +
                " \"productMissionName\":\"MISSION1\"," +
                " \"productAcquisitionDate\":\"2018-12-31T23:00:00.000+0000\"," +
                " \"productFootprint\":\"50.555555, 20.555555; 49.51072, 19.19010\"," +
                " \"productPrice\":\"560\"," +
                " \"productURL\":\"http://mission1photo1.com\"," +
                " \"orderCount\":\"16\"" +
                "}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/product").with(httpBasic("manager1", "manager1Pass")).contentType(MediaType.APPLICATION_JSON).content(json)).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("SUCCESS")));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/products").with(httpBasic("manager1", "manager1Pass"))).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("50.555555, 20.555555;")));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/products?id=123456789").with(httpBasic("user1", "user1Pass"))).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.equalTo("[]")));

    }
    @Test
    public void testOrderingAndOrdersRetrieval() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/order/100").with(httpBasic("manager1", "manager1Pass"))).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("SUCCESS")));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/order/101").with(httpBasic("user1", "user1Pass"))).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("SUCCESS")));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders").with(httpBasic("manager1", "manager1Pass"))).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(
                        "\"user_name\":\"manager1\"," +
                                "\"product_mission_name\":\"APOLLO11\"," +
                                "\"product_id\":100"
                )));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders?id=7").with(httpBasic("manager1", "manager1Pass"))).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(
                        "\"user_name\":\"user51\"," +
                                "\"product_mission_name\":\"APOLLO11\"," +
                                "\"product_id\":100"
                )));
    }
    @Test
    public void testOrderCountIncrementing() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/products?id=103").with(httpBasic("manager1", "manager1Pass"))).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("orderCount\":4")));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/order/103").with(httpBasic("manager1", "manager1Pass"))).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("SUCCESS")));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/products?id=103").with(httpBasic("manager1", "manager1Pass"))).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("orderCount\":5")));
    }
    @Test
    public void testUserOrderHistoryRetrieval() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/user_report/user1").with(httpBasic("manager1", "manager1Pass"))).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("" +
                                "{" +
                                "\"id\":9," +
                                "\"user_name\":\"user1\"," +
                                "\"product_mission_name\":\"MISSION1\"," +
                                "\"product_id\":101," +
                                "\"time\":\"2019-01-09T23:00:00.000+0000\""
                )));
    }
    @Test
    public void testReporting() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders").with(httpBasic("manager1", "manager1Pass"))).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("APOLLO11")));


        this.mockMvc.perform(MockMvcRequestBuilders.get("/products/report/3").with(httpBasic("manager1", "manager1Pass"))).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.startsWith(
                        "[{" +
                        "\"id\":123,"
                )));
    }
    @Test
    public void testSearchingForProducts() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/products?search=id:9999999").with(httpBasic("user1", "user1Pass"))).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.equalTo("[]")));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/products?search=id>10000").with(httpBasic("user1", "user1Pass"))).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.equalTo("[]")));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/products?search=id<103").with(httpBasic("user1", "user1Pass"))).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("APOLLO11")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("MISSION1")));
    }
}