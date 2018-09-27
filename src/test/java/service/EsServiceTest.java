package service;

import com.quick.es.Application;
import com.quick.es.service.EsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class EsServiceTest {

	@Resource
	private EsService esService;

	@Test
	public void testInsert() {
		esService.insert();
	}
}
