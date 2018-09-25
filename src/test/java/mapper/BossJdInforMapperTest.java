package mapper;

import com.quick.es.Application;
import com.quick.es.entity.BossJdInfo;
import com.quick.es.mapper.BossJdInfoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class BossJdInforMapperTest {

	@Autowired
	private BossJdInfoMapper bossJdInfoMapper;


	@Test
	public void testSelect() {
		BossJdInfo bossJdInfo = bossJdInfoMapper.selectByPrimaryKey(1345L);
		System.out.println(bossJdInfo);

	}
}
