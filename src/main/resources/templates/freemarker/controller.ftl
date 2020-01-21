package ${basePackageController};

import ${basePackageService}.${ServiceName};
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import io.swagger.annotations.Api;

/**
* @Description:${controllerName}
* @Param:
* @return:
* @Author: ${author}
* @Date: ${date}
*/
@Api(tags = "${controllerName}")
@RestController
@RequestMapping("/${tableName}")
public class ${controllerName} {

    @Resource
    ${ServiceName} ${serviceName};
}