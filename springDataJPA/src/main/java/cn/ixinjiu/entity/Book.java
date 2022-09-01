package cn.ixinjiu.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by XinChen on 2022-08-30
 *
 * @Description:TODO
 * JPA自带的几种主键生成策略：
 *
 * TABLE：使用一个特定的数据库表格来保存主键
 * SEQUENCE：根据底层数据库的序列来生成主键，条件是数据库支持序列。这个值要于generator一起使用，generator指定生成主键的生成器
 * IDENTITY：主键由数据库自动生成（主要支持自动增长的数据库，如mysql）
 * AUTO：主键由程序控制，也是GenerationType的默认值
 */

@Data
/**
 * 表示该类是一个实体类，在项目启动时会根据该类自动生成一张表，表的名称即@Entity注解中name的值，
 * 如果不配置name，默认表名为类名
 */
@Entity(name = "t_book")
public class Book {
    @Id // 表示该属性是一个主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 表示主键自动生成，strategy则表示主键的生成策略
    private Integer id;
    @Column(name = "book_name") // 可以定制生成的字段的属性，name表示该属性对应的数据表中字段的名称，nullable表示该字段非空
    private String name;
    @Column(name = "book_author")
    private String author;
    private Float price;
    @Transient // 表示在生成数据库的表时，该属性被忽略，即不生成对应的字段
    private String description;
}
