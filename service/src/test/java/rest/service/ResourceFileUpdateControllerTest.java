package rest.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collection;

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

@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ResourceFileUpdateControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testPostFile() throws Exception{
		Collection<DataResource> reference = new ArrayList<>();
		reference.add(new DataResource("data1", "data2-1", "data3-1"));
		reference.add(new DataResource("data2", "data2-2", "data3-2"));
		reference.add(new DataResource("data3", "data2-3", "data3-3"));
		reference.add(new DataResource("data4", "data2-4", "data3-4"));
		reference.add(new DataResource("data5", "data2-5", "data3-5"));
		reference.add(new DataResource("data6", "data2-6", "data3-6"));
		reference.add(new DataResource("data7", "data2-7", "data3-7"));
		reference.add(new DataResource("data8", "data2-8", "data3-8"));
		reference.add(new DataResource("data9", "data2-9", "data3-9"));
		reference.add(new DataResource("data10", "data2-10", "data3-10"));
		this.mockMvc.perform(MockMvcRequestBuilders.post("/data/file/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().string(new ObjectMapper().writeValueAsString(reference)));
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/data").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(new ObjectMapper().writeValueAsString(reference)));
	}
	
	@Test
	public void testPostFileEven() throws Exception {
		Collection<DataResource> reference = new ArrayList<>();
		reference.add(new DataResource("data2", "data2-2", "data3-2"));
		reference.add(new DataResource("data4", "data2-4", "data3-4"));
		reference.add(new DataResource("data6", "data2-6", "data3-6"));
		reference.add(new DataResource("data8", "data2-8", "data3-8"));
		reference.add(new DataResource("data10", "data2-10", "data3-10"));
		this.mockMvc.perform(MockMvcRequestBuilders.post("/data/file/even").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().string(new ObjectMapper().writeValueAsString(reference)));
	}
	
	@Test
	public void testPostFileOdd() throws Exception {
		Collection<DataResource> reference = new ArrayList<>();
		reference.add(new DataResource("data1", "data2-1", "data3-1"));
		reference.add(new DataResource("data3", "data2-3", "data3-3"));
		reference.add(new DataResource("data5", "data2-5", "data3-5"));
		reference.add(new DataResource("data7", "data2-7", "data3-7"));
		reference.add(new DataResource("data9", "data2-9", "data3-9"));
		this.mockMvc.perform(MockMvcRequestBuilders.post("/data/file/odd").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().string(new ObjectMapper().writeValueAsString(reference)));
	}
	
	@Test
	public void testPostFileNotOddOrEven() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/data/file/other").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
	}
}
