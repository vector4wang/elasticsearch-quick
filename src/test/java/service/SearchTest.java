package service;

import com.quick.es.Application;
import com.quick.es.service.RecipesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author vector
 * @date: 2019/4/1 0001 13:58
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SearchTest {

    @Resource
    private RecipesService recipesService;

    @Test
    public void test() {
        recipesService.getBigData();
    }
}
