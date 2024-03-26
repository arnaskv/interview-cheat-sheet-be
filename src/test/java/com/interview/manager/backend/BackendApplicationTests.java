package com.interview.manager.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

	@Test
	void contextLoads() {
		System.out.println(System.getenv("POSTGRES_HOST"));
		System.out.println(System.getenv("POSTGRES_DB"));
		System.out.println(System.getenv("POSTGRES_USER"));
	}

}
