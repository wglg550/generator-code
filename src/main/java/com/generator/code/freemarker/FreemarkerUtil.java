package com.generator.code.freemarker;

import com.generator.code.config.ClassPathConfig;
import com.generator.code.config.Constant;
import com.generator.code.config.JdbcConfig;
import com.generator.code.enums.JavaFieldEnum;
import com.generator.code.jdbc.JdbcConnect;
import com.google.common.collect.Multimap;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * @Description: freemarker工具类
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/1/15
 */
@Slf4j
@Component
public class FreemarkerUtil {

    @Resource
    ClassPathConfig classPathConfig;

    @Resource
    JdbcConfig jdbcConfig;

    @Resource
    JdbcConnect jdbcConnect;

    public void createCode() throws IOException, MetaDataAccessException {
        Long start = System.currentTimeMillis();
        log.info("开始一键生成代码：{}", start);
        List<String> list = jdbcConfig.getTables();
        if (Objects.isNull(list) || list.size() == 0) {
            throw new RuntimeException("表名称没有配置");
        }
        String systemDir = System.getProperty(Constant.USER_DIR);
        String templeDir = null;
        if (Objects.nonNull(classPathConfig.getOutsidePath()) && !Objects.equals(classPathConfig.getOutsidePath(), "")) {
            templeDir = classPathConfig.getOutsidePath();
        } else {
            templeDir = System.getProperty(Constant.USER_DIR);
        }
        if (Objects.nonNull(classPathConfig.getOutsideProject()) && !Objects.equals(classPathConfig.getOutsideProject(), "")) {
            classPathConfig.setProject(classPathConfig.getOutsideProject());
        }
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_29);
        configuration.setDirectoryForTemplateLoading(new File(new StringJoiner("").add(systemDir).add(File.separator).add(classPathConfig.getTemplate()).toString()));
        Multimap<String, Map<String, String>> map = jdbcConnect.springJdbcConn();
        log.info("map.size:{}", map.size());
        for (String s : list) {
            Map serviceMap = createService(configuration, s, templeDir);
            Map entityMap = createEntity(configuration, s, templeDir, jdbcConfig.getDatabase(), map.get(s));
            Map repoMap = createRepo(configuration, s, templeDir, String.valueOf(entityMap.get(Constant.ENTITY_NAME)), String.valueOf(entityMap.get(Constant.BASE_PACKER_ENTITY)));
            createServiceImpl(configuration, s, templeDir, String.valueOf(serviceMap.get(Constant.SERVICE_NAME)), String.valueOf(serviceMap.get(Constant.BASE_PACKER_SERVICE)), String.valueOf(repoMap.get(Constant.REPO_NAME)), String.valueOf(repoMap.get(Constant.BASE_PACKER_REPO)));
            createController(configuration, s, templeDir, String.valueOf(serviceMap.get(Constant.SERVICE_NAME)), String.valueOf(serviceMap.get(Constant.BASE_PACKER_SERVICE)));
        }
        long end = System.currentTimeMillis();
        log.info("一键生成代码结束，生成了{}张表数据，共耗时:{}s", list.size(), (end - start) / 1000);
    }

    /**
     * 创建service
     *
     * @param configuration
     * @param tableName
     * @param templeDir
     * @return
     * @throws IOException
     */
    private Map<String, Object> createService(Configuration configuration, String tableName, String templeDir) throws IOException {
        Writer out = null;
        Map<String, Object> dataMap = null;
        try {
            // step3 创建数据模型
            String serviceName = new StringJoiner("").add(tableName).add(Constant.SERVICE).toString();
            serviceName = serviceName.replaceFirst(serviceName.substring(0, 1), serviceName.substring(0, 1).toUpperCase());
            String[] serviceNames = serviceName.split("_");
            if (Objects.nonNull(serviceNames) && serviceNames.length > 1) {
                StringJoiner sj = new StringJoiner("");
                sj.add(serviceNames[0]);
                for (int i = 1; i < serviceNames.length; i++) {
                    sj.add(serviceNames[i].replaceFirst(serviceNames[i].substring(0, 1), serviceNames[i].substring(0, 1).toUpperCase()));
                }
                serviceName = sj.toString();
            }
            dataMap = new HashMap<>();
            dataMap.put(Constant.BASE_PACKER_SERVICE, classPathConfig.getService().replaceAll(File.separator, "."));
            dataMap.put(Constant.SERVICE_NAME, serviceName);
            dataMap.put(Constant.AUTHOR, classPathConfig.getAuthor());
            dataMap.put(Constant.DATE, LocalDate.now());
            // step4 加载模版文件
            Template template = configuration.getTemplate(Constant.SERVICE_FTL);
            // step5 生成数据
            File fileDir = new File(new StringJoiner("").add(templeDir).add(File.separator).add(classPathConfig.getSystem()).add(File.separator).add(classPathConfig.getService()).toString());
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            File javaFile = new File(new StringJoiner("").add(fileDir.toString()).add(File.separator).add(serviceName).add(Constant.FILE_TYPE).toString());
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(javaFile)));
            // step6 输出文件
            template.process(dataMap, out);
            out.flush();
            log.info("{}{}文件创建成功", serviceName, Constant.FILE_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(out)) {
                out.close();
            }
        }
        return dataMap;
    }

    /**
     * 创建serviceImpl
     *
     * @param configuration
     * @param tableName
     * @param templeDir
     * @param serviceName
     * @param basePackerService
     * @param repoName
     * @param basePackageRepo
     * @return
     * @throws IOException
     */
    private Map<String, Object> createServiceImpl(Configuration configuration, String tableName, String templeDir, String serviceName, String basePackerService, String repoName, String basePackageRepo) throws IOException {
        Writer out = null;
        Map<String, Object> dataMap = null;
        try {
            // step3 创建数据模型
            String serviceImplName = new StringJoiner("").add(tableName).add(Constant.SERVICE_IMPL).toString();
            serviceImplName = serviceImplName.replaceFirst(serviceImplName.substring(0, 1), serviceImplName.substring(0, 1).toUpperCase());
            String[] serviceImplNames = serviceImplName.split("_");
            if (Objects.nonNull(serviceImplNames) && serviceImplNames.length > 1) {
                StringJoiner sj = new StringJoiner("");
                sj.add(serviceImplNames[0]);
                for (int i = 1; i < serviceImplNames.length; i++) {
                    sj.add(serviceImplNames[i].replaceFirst(serviceImplNames[i].substring(0, 1), serviceImplNames[i].substring(0, 1).toUpperCase()));
                }
                serviceImplName = sj.toString();
            }
            dataMap = new HashMap<>();
            dataMap.put(Constant.BASE_PACKER_SERVICE, basePackerService);
            dataMap.put(Constant.BASE_PACKER_SERVICE_IMPL, classPathConfig.getServiceImpl().replaceAll(File.separator, "."));
            dataMap.put(Constant.SERVICE_NAME, serviceName);
            dataMap.put(Constant.SERVICE_IMPL_NAME, serviceImplName);
            String upperRepoName = Constant.REPO_NAME;
            dataMap.put(upperRepoName.replaceFirst(upperRepoName.substring(0, 1), upperRepoName.substring(0, 1).toUpperCase()), repoName);
            dataMap.put(Constant.REPO_NAME, repoName.replaceFirst(repoName.substring(0, 1), repoName.substring(0, 1).toLowerCase()));
            dataMap.put(Constant.BASE_PACKER_REPO, basePackageRepo);
            dataMap.put(Constant.AUTHOR, classPathConfig.getAuthor());
            dataMap.put(Constant.DATE, LocalDate.now());
            // step4 加载模版文件
            Template template = configuration.getTemplate(Constant.SERVICE_IMPL_FTL);
            // step5 生成数据
            File fileDir = new File(new StringJoiner("").add(templeDir).add(File.separator).add(classPathConfig.getSystem()).add(File.separator).add(classPathConfig.getServiceImpl()).toString());
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            File javaFile = new File(new StringJoiner("").add(fileDir.toString()).add(File.separator).add(serviceImplName).add(Constant.FILE_TYPE).toString());
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(javaFile)));
            // step6 输出文件
            template.process(dataMap, out);
            out.flush();
            log.info("{}{}文件创建成功", serviceImplName, Constant.FILE_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(out)) {
                out.close();
            }
        }
        return dataMap;
    }

    /**
     * 创建entity
     *
     * @param configuration
     * @param tableName
     * @param templeDir
     * @param schemaName
     * @param map
     * @return
     * @throws IOException
     */
    private Map<String, Object> createEntity(Configuration configuration, String tableName, String templeDir, String schemaName, Collection<Map<String, String>> map) throws IOException {
        Writer out = null;
        Map<String, Object> dataMap = null;
        try {
            // step3 创建数据模型
            String entityName = new StringJoiner("").add(tableName).add(Constant.ENTITY).toString();
            entityName = entityName.replaceFirst(entityName.substring(0, 1), entityName.substring(0, 1).toUpperCase());
            String[] entityNames = entityName.split("_");
            if (Objects.nonNull(entityNames) && entityNames.length > 1) {
                StringJoiner sj = new StringJoiner("");
                sj.add(entityNames[0]);
                for (int i = 1; i < entityNames.length; i++) {
                    sj.add(entityNames[i].replaceFirst(entityNames[i].substring(0, 1), entityNames[i].substring(0, 1).toUpperCase()));
                }
                entityName = sj.toString();
            }
            dataMap = new HashMap<>();
            dataMap.put(Constant.BASE_PACKER_ENTITY, classPathConfig.getEntity().replaceAll(File.separator, "."));
            dataMap.put(Constant.ENTITY_NAME, entityName);
            dataMap.put(Constant.TABLE_NAME, tableName);
            dataMap.put(Constant.SCHEMA_NAME, schemaName);
            dataMap.put(Constant.COLUMN_LIST, map);
            dataMap.put(Constant.AUTHOR, classPathConfig.getAuthor());
            dataMap.put(Constant.DATE, LocalDate.now());
            // step4 加载模版文件
            Template template = configuration.getTemplate(Constant.ENTITY_FTL);
            // step5 生成数据
            File fileDir = new File(new StringJoiner("").add(templeDir).add(File.separator).add(classPathConfig.getSystem()).add(File.separator).add(classPathConfig.getEntity()).toString());
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            File javaFile = new File(new StringJoiner("").add(fileDir.toString()).add(File.separator).add(entityName).add(Constant.FILE_TYPE).toString());
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(javaFile)));
            // step6 输出文件
            template.process(dataMap, out);
            out.flush();
            log.info("{}{}文件创建成功", entityName, Constant.FILE_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(out)) {
                out.close();
            }
        }
        return dataMap;
    }

    /**
     * 创建repo
     *
     * @param configuration
     * @param tableName
     * @param templeDir
     * @param entityName
     * @param basePackageEntity
     * @return
     * @throws IOException
     */
    private Map<String, Object> createRepo(Configuration configuration, String tableName, String templeDir, String entityName, String basePackageEntity) throws IOException {
        Writer out = null;
        Map<String, Object> dataMap = null;
        try {
            // step3 创建数据模型
            String repoName = new StringJoiner("").add(tableName).add(Constant.REPO).toString();
            repoName = repoName.replaceFirst(repoName.substring(0, 1), repoName.substring(0, 1).toUpperCase());
            String[] repoNames = repoName.split("_");
            if (Objects.nonNull(repoNames) && repoNames.length > 1) {
                StringJoiner sj = new StringJoiner("");
                sj.add(repoNames[0]);
                for (int i = 1; i < repoNames.length; i++) {
                    sj.add(repoNames[i].replaceFirst(repoNames[i].substring(0, 1), repoNames[i].substring(0, 1).toUpperCase()));
                }
                repoName = sj.toString();
            }
            dataMap = new HashMap<>();
            dataMap.put(Constant.BASE_PACKER_REPO, classPathConfig.getDao().replaceAll(File.separator, "."));
            dataMap.put(Constant.REPO_NAME, repoName);
            dataMap.put(Constant.JAVA_FIELD_TYPE, JavaFieldEnum.BIGINT.getName());
            dataMap.put(Constant.ENTITY_NAME, entityName);
            dataMap.put(Constant.BASE_PACKER_ENTITY, basePackageEntity);
            dataMap.put(Constant.AUTHOR, classPathConfig.getAuthor());
            dataMap.put(Constant.DATE, LocalDate.now());
            // step4 加载模版文件
            Template template = configuration.getTemplate(Constant.REPO_FTL);
            // step5 生成数据
            File fileDir = new File(new StringJoiner("").add(templeDir).add(File.separator).add(classPathConfig.getSystem()).add(File.separator).add(classPathConfig.getDao()).toString());
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            File javaFile = new File(new StringJoiner("").add(fileDir.toString()).add(File.separator).add(repoName).add(Constant.FILE_TYPE).toString());
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(javaFile)));
            // step6 输出文件
            template.process(dataMap, out);
            out.flush();
            log.info("{}{}文件创建成功", repoName, Constant.FILE_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(out)) {
                out.close();
            }
        }
        return dataMap;
    }

    /**
     * 创建controller
     *
     * @param configuration
     * @param tableName
     * @param templeDir
     * @return
     * @throws IOException
     */
    private Map<String, Object> createController(Configuration configuration, String tableName, String templeDir, String serviceName, String basePackageService) throws IOException {
        Writer out = null;
        Map<String, Object> dataMap = null;
        try {
            // step3 创建数据模型
            String controllerName = new StringJoiner("").add(tableName).add(Constant.CONTROLLER).toString();
            controllerName = controllerName.replaceFirst(controllerName.substring(0, 1), controllerName.substring(0, 1).toUpperCase());
            String[] controllerNames = controllerName.split("_");
            if (Objects.nonNull(controllerNames) && controllerNames.length > 1) {
                StringJoiner sj = new StringJoiner("");
                sj.add(controllerNames[0]);
                for (int i = 1; i < controllerNames.length; i++) {
                    sj.add(controllerNames[i].replaceFirst(controllerNames[i].substring(0, 1), controllerNames[i].substring(0, 1).toUpperCase()));
                }
                controllerName = sj.toString();
            }
            String[] tableNames = tableName.split("_");
            if (Objects.nonNull(tableNames) && tableNames.length > 1) {
                StringJoiner sj = new StringJoiner("");
                sj.add(tableNames[0]);
                for (int i = 1; i < tableNames.length; i++) {
                    sj.add(tableNames[i].replaceFirst(tableNames[i].substring(0, 1), tableNames[i].substring(0, 1).toUpperCase()));
                }
                tableName = sj.toString();
            }
            dataMap = new HashMap<>();
            dataMap.put(Constant.BASE_PACKER_CONTROLLER, classPathConfig.getController().replaceAll(File.separator, "."));
            dataMap.put(Constant.CONTROLLER_NAME, controllerName);
            dataMap.put(Constant.SERVICE_NAME, serviceName);
            String upperServiceName = Constant.SERVICE_NAME;
            dataMap.put(upperServiceName.replaceFirst(upperServiceName.substring(0, 1), upperServiceName.substring(0, 1).toUpperCase()), serviceName);
            dataMap.put(Constant.SERVICE_NAME, serviceName.replaceFirst(serviceName.substring(0, 1), serviceName.substring(0, 1).toLowerCase()));
            dataMap.put(Constant.BASE_PACKER_SERVICE, basePackageService);
            dataMap.put(Constant.TABLE_NAME, tableName.replaceFirst(tableName.substring(0, 1), tableName.substring(0, 1).toUpperCase()));
            dataMap.put(Constant.AUTHOR, classPathConfig.getAuthor());
            dataMap.put(Constant.DATE, LocalDate.now());
            // step4 加载模版文件
            Template template = configuration.getTemplate(Constant.CONTROLLER_FTL);
            // step5 生成数据
            File fileDir = new File(new StringJoiner("").add(templeDir).add(File.separator).add(classPathConfig.getSystem()).add(File.separator).add(classPathConfig.getController()).toString());
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            File javaFile = new File(new StringJoiner("").add(fileDir.toString()).add(File.separator).add(controllerName).add(Constant.FILE_TYPE).toString());
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(javaFile)));
            // step6 输出文件
            template.process(dataMap, out);
            out.flush();
            log.info("{}{}文件创建成功", controllerName, Constant.FILE_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(out)) {
                out.close();
            }
        }
        return dataMap;
    }
}
