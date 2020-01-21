package ${basePackageRepo};

import ${basePackageEntity}.${entityName};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
* @Description:${repoName} dao
* @Param:
* @return:
* @Author: ${author}
* @Date: ${date}
*/
@Repository
public interface ${repoName} extends JpaRepository<${entityName}, ${javaFieldType}>, JpaSpecificationExecutor<${entityName}> {

}