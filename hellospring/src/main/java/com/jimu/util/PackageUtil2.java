package com.jimu.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.SystemPropertyUtils;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bcl on 16/4/22.
 */

public class PackageUtil2 {
    static Logger log = LoggerFactory.getLogger(PackageUtil2.class);
    protected static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
    public static void main(String[] args) {
        String basePackage = "com.jimu.hello.translater";
        Set<String> clazzSet = new HashSet<String>();
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                org.springframework.util.ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage)) + "/" + DEFAULT_RESOURCE_PATTERN;
        try {
            Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
            for (Resource resource : resources) {
                //检查resource，这里的resource都是class
                String clazz = loadClassName(metadataReaderFactory, resource);
                clazzSet.add(clazz);
            }
        } catch (Exception e) {
            log.error("获取包下面的类信息失败,package:" + basePackage, e);
        }
//        clazzSet.stream().forEach(clazz-> System.out.println(clazz));
    }
    /**
     * 加载资源，根据resource获取className
     *
     * @param metadataReaderFactory spring中用来读取resource为class的工具
     * @param resource              这里的资源就是一个Class
     * @throws IOException
     */
    private static String loadClassName(MetadataReaderFactory metadataReaderFactory, Resource resource) throws IOException {
        try {
            if (resource.isReadable()) {
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                if (metadataReader != null) {
//                    System.out.println(metadataReader.getClassMetadata().getClassName());
                    if(metadataReader.getClassMetadata().getClassName().endsWith("IntToStringTranslater")){
                        System.out.println(metadataReader.getClassMetadata().getClassName());
                        Class clazz = Class.forName(metadataReader.getClassMetadata().getClassName());
                        Arrays.stream(clazz.getInterfaces()).forEach(i-> System.out.println(i.getName()));
//                        Arrays.stream(clazz.getDeclaredMethods()).filter(method -> method.getAnnotation(NotNull.class)!=null).forEach(method -> {
//                            System.out.println(method.getName());
//                            Arrays.stream(method.getParameterTypes()).forEach(type-> System.out.println(type.getName()));
//                            System.out.println(method.getReturnType().getName());
//                        });
                    }
//                    Arrays.stream(metadataReader.getClassMetadata().getClass().getDeclaredMethods()).forEach(method->{
//                        System.out.println(method.getName());
////                        Arrays.stream(method.getParameterTypes()).forEach(p->{
////                            System.out.println(p.getClass().getSimpleName());
////                        });
//                    });
                    return metadataReader.getClassMetadata().getClassName();
                }
            }
        } catch (Exception e) {
            log.error("根据resource获取类名称失败", e);
        }
        return null;
    }
}
