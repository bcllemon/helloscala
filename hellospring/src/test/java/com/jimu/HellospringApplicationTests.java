package com.jimu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HellospringApplication.class)
@WebAppConfiguration("server.port:0")
public class HellospringApplicationTests {
	RestTemplate template = new TestRestTemplate();
	@Value("${local.server.port}")
	int port;
	@Test
	public void contextLoads() {
	}


	@Test
	public void testApi(){
		System.out.println(port);
//		template.getForEntity("http://localhost:8080/api/java/add/asdfsadf", String.class);
	}

}
