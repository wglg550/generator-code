package ${basePackageServiceImpl};

import ${basePackageService}.${serviceName};
import org.springframework.stereotype.Service;
import ${basePackageRepo}.${RepoName};
import javax.annotation.Resource;

/**
* @Description:${serviceNameImpl}接口
* @Param:
* @return:
* @Author: ${author}
* @Date: ${date}
*/
@Service
public class ${serviceNameImpl} implements ${serviceName} {

    @Resource
    ${RepoName} ${repoName};
}