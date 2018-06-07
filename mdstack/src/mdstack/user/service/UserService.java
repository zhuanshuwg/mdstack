package mdstack.user.service;

import org.framework.core.common.service.CommonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
public interface UserService extends CommonService {

}
