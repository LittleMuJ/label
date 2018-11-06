package com.label.generate;

import com.label.common.util.ValidateUtil;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;


@Component
public class CodeGenerateUtils {

	private static Logger logger = LoggerFactory.getLogger(CodeGenerateUtils.class);
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final String packagePath = "business";
	
	private final String AUTHOR = "MuRunSen";//作者
	private final String CURRENT_DATE = sdf.format(new Date());//创建时间
	private final String tableName = "classify";//表名
	private final String packageName = "com.label.web."+packagePath+"";//包路径
    private final String bizBasePackage = "com.label.biz."+packagePath+"";//包路径
	private final String tableAnnotation = "标注平台用户";//表描述
	private final String URL = "jdbc:postgresql://52.83.114.189:5432/smalldata_temp?searchpath=label";//数据库连接url
	private final String USER = "smalldata";//用户名
	private final String PASSWORD = "smalldata2018";//密码
	private final String DRIVER = "org.postgresql.Driver";//数据库驱动
	private final String diskPath = "D://com//xiaobu//web//";//生成到哪个盘
	private final String changeTableName = replaceUnderLineAndUpperCase(tableName);
	private final List<ColumnClass> columnClassList = new ArrayList<>();
	private String pk_Key="";
	
	
	public static void main(String[] args) throws Exception {
		 CodeGenerateUtils codeGenerateUtils = new CodeGenerateUtils();
	     codeGenerateUtils.generate();
	}
    
    
    
    public Connection getConnection() throws Exception{
        Class.forName(DRIVER);
        Connection connection= DriverManager.getConnection(URL, USER, PASSWORD);
        return connection;
    }
    
    public String replaceUnderLineAndUpperCase(String str){
        StringBuffer sb = new StringBuffer();
        sb.append(str);
        int count = sb.indexOf("_");
        while(count!=0){
            int num = sb.indexOf("_",count);
            count = num + 1;
            if(num != -1){
                char ss = sb.charAt(count);
                char ia = (char) (ss - 32);
                sb.replace(count , count + 1,ia + "");
            }
        }
        String result = sb.toString().replaceAll("_","");
        return StringUtils.capitalize(result);
    }
    
    /**
     * 生成
     * @param templateName
     * @param file
     * @param dataMap
     * @throws Exception
     */
    public void generateFileByTemplate(final String templateName,File file,Map<String,Object> dataMap) throws Exception{
        Template template = FreeMarkerTemplateUtils.getTemplate(templateName);
        FileOutputStream fos = new FileOutputStream(file);

        
        if(ValidateUtil.isNotEmpty(dataMap.get("pkField"))) {
        	dataMap.put("pkField",dataMap.get("pkField"));
        }
        if(ValidateUtil.isNotEmpty(dataMap.get("model_column"))) {
        	List<ColumnClass> columnClassList = (List<ColumnClass>) dataMap.get("model_column");
        	dataMap.put("columns",columnClassList);
        }
        dataMap.put("table_name_small",tableName);
        dataMap.put("table_name",changeTableName);
        dataMap.put("author",AUTHOR);
        dataMap.put("date",CURRENT_DATE);
        dataMap.put("package_name",packageName);
        dataMap.put("bizBasePackage",bizBasePackage);
        dataMap.put("table_annotation",tableAnnotation);
        
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"),10240);
        template.process(dataMap,out);
    }
	
    /**
     * JDBC读取数据库表信息
     * @throws Exception
     */
	public void generate() throws Exception{
        try {
            Connection connection = getConnection();
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getColumns(null,"%", tableName,"%");
            ResultSet pkRSet = databaseMetaData.getPrimaryKeys(null, null, tableName);
            ColumnClass columnClass = null;
           
            while(resultSet.next()){
                //id字段略过
                //if(resultSet.getString("COLUMN_NAME").equals("id")) continue;
                /*if(resultSet.getString("COLUMN_NAME").equals("created_at")) continue;
                if(resultSet.getString("COLUMN_NAME").equals("updated_at")) continue;
                if(resultSet.getString("COLUMN_NAME").equals("is_delete")) continue;*/
                columnClass = new ColumnClass();
                
                //获取字段名称
                columnClass.setColumnName(resultSet.getString("COLUMN_NAME"));
                //获取字段类型
                columnClass.setColumnType(resultSet.getString("TYPE_NAME"));
                //转换字段名称，如 sys_name 变成 SysName
                columnClass.setChangeColumnName(replaceUnderLineAndUpperCase(resultSet.getString("COLUMN_NAME")));
                //字段在数据库的注释
                columnClass.setColumnComment(resultSet.getString("REMARKS"));
                columnClassList.add(columnClass);
            }
            while(pkRSet.next()) {
            	pk_Key = (String) pkRSet.getObject(4);
            }
            File mapperFile = new File(diskPath);
            if(!mapperFile.exists()){
                mapperFile.mkdirs();
            }
            //生成Model文件
            generateModelFile(resultSet);
            logger.info("创建entity文件成功");
            //生成Mapper文件
            generateMapperFile(resultSet,pkRSet);
            logger.info("创建Mapper文件成功");
            //生成Dao文件
            generateDaoFile(resultSet);
            logger.info("创建Dao文件成功");
            //生成服务层接口文件
            generateServiceInterfaceFile(resultSet);
            logger.info("创建Service文件成功");
            //生成服务实现层文件
            generateServiceImplFile(resultSet);
            logger.info("创建ServiceImpl文件成功");
            //生成Controller层文件
            generateControllerFile(resultSet,pkRSet);
            logger.info("创建Controller文件成功");
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{

        }
    }
	
	/**
	 * entity文件生成
	 * @param resultSet
	 * @throws Exception
	 */
	public void generateModelFile(ResultSet resultSet) throws Exception{

        final String suffix = ".java";
        final String path = diskPath + changeTableName + suffix;
        final String templateName = "Model.ftl";
        File mapperFile = new File(path);
        
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("model_column",columnClassList);
       
        generateFileByTemplate(templateName,mapperFile,dataMap);
    }
	
	
	/**
	 * 生成Controller文件
	 * @param resultSet
	 * @throws Exception
	 */
	private void generateControllerFile(ResultSet resultSet,ResultSet pkRSet) throws Exception{
        final String suffix = "Controller.java";
        final String path = diskPath + changeTableName + suffix;
        final String templateName = "Controller.ftl";
        File mapperFile = new File(path);
       
        
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("pkField", pk_Key);
        generateFileByTemplate(templateName,mapperFile,dataMap);
    }
	
	/**
	 * 生成ServiceImpl
	 * @param resultSet
	 * @throws Exception
	 */
	private void generateServiceImplFile(ResultSet resultSet) throws Exception{
        final String suffix = "ServiceImpl.java";
        final String path = diskPath + changeTableName + suffix;
        final String templateName = "ServiceImpl.ftl";
        File mapperFile = new File(path);
        Map<String,Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName,mapperFile,dataMap);
    }
	
	/**
	 * 生成Service
	 * @param resultSet
	 * @throws Exception
	 */
	private void generateServiceInterfaceFile(ResultSet resultSet) throws Exception{
        final String suffix = "Service.java";
        final String path = diskPath + changeTableName + suffix;
        final String templateName = "Service.ftl";
        File mapperFile = new File(path);
        Map<String,Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName,mapperFile,dataMap);
    }
	
	/**
	 * 生成dao文件
	 * @param resultSet
	 * @throws Exception
	 */
	private void generateDaoFile(ResultSet resultSet) throws Exception{
        final String suffix = "Dao.java";
        final String path = diskPath + changeTableName + suffix;
        final String templateName = "Dao.ftl";
        File mapperFile = new File(path);
        Map<String,Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName,mapperFile,dataMap);

    }

	/**
	 * 生成Mapper文件
	 * @param resultSet
	 * @throws Exception
	 */
    private void generateMapperFile(ResultSet resultSet,ResultSet pkRSet) throws Exception{
        final String suffix = "Mapper.xml";
        final String path = diskPath + changeTableName + suffix;
        final String templateName = "Mapper.ftl";
        File mapperFile = new File(path);
       
        
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("pkField", pk_Key);
        dataMap.put("model_column",columnClassList);
        
        generateFileByTemplate(templateName,mapperFile,dataMap);

    }
    
    
    
}
