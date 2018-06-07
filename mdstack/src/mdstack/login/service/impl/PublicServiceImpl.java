package mdstack.login.service.impl;

import org.framework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mdstack.login.service.PublicService;

@Service("publicService")
@Transactional
public class PublicServiceImpl extends CommonServiceImpl implements PublicService {

}
