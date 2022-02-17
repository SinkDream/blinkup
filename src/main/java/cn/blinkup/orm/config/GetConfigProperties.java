package cn.blinkup.orm.config;

import org.yaml.snakeyaml.Yaml;

import java.util.List;

/**
 * @author zhangguosheng
 */
public class GetConfigProperties {

    public GetConfigProperties(){
        Yaml yaml = new Yaml();
        List<String> ret = yaml.load(this.getClass().getClassLoader().getResourceAsStream("application.yaml"));
        ret.forEach(System.out::println);
    }

}
