package mdstack.login.service;

import org.framework.core.common.service.CommonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("publicService")
@Transactional
public interface PublicService extends CommonService  {

}
