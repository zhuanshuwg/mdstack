package mdstack.user.service.impl;

import org.framework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mdstack.user.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl extends CommonServiceImpl implements UserService {

}
