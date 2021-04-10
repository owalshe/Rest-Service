package rest.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import rest.service.model.DataResource;
import rest.service.repository.DataResourceRepository;

@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ResourceCrudControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private DataResourceRepository repository;
	
	@BeforeEach
	public void setup() {
		this.repository.save(new DataResource("data1", "data2", "data3"));
	}
	
	@Test
	public void testGet() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.get("/data/1").accept(MediaType.APPLICATION_JSON))
	        .andExpect(status().isOk())
	        .andExpect(content().string(new ObjectMapper().writeValueAsString(new DataResource("data1", "data2", "data3"))));
	}
	
	@Test
	public void testGetAll() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.get("/data").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(new ObjectMapper().writeValueAsString(Collections.singletonList(new DataResource("data1", "data2", "data3")))));
	}
	
	@Test
	public void testPost() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.post("/data").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(new DataResource("data4", "data5", "data6"))))
            .andExpect(status().isCreated())
            .andExpect(content().string(new ObjectMapper().writeValueAsString(new DataResource("data4", "data5", "data6"))));
	}
	
	@Test
	public void testPut() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.put("/data/1").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(new DataResource("data1", "data2", "data4"))))
            .andExpect(status().isOk())
            .andExpect(content().string(new ObjectMapper().writeValueAsString(new DataResource("data1", "data2", "data4"))));
	}
	
	@Test
	public void testDelete() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/data/1"))
            .andExpect(status().isNoContent())
            .andExpect(content().string(""));
	}
	
	@Test
	public void testGetResourceNotFound() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.get("/data/2").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
	}
	
	@Test
	public void testDeleteResourceNotFound() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/data/2").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
	}
	
	@Test
	public void testResourceAlreadyExists() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.post("/data").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(new DataResource("data1", "data2", "data3"))))
        .andExpect(status().isConflict());
	}
}
