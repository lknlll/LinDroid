package com.example.lindroidcode.greendaoatoz.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * MakeProject，便发现GreenDao为UserBean实体类生成了对应的Getter、Setter方法以及俩个构造函数，同时在我们配置的com.lin.greendao.gen包下生成了三个对应类文件DaoMaster、DaoSession和UserBeanDao
 *
 * DaoMaster creates DaoSession, DaoSession creates & manages XXXDao, XXXDao loads & saves XXXEntity
 *
 * DaoMaster：使用greenDAO的切入点。DaoMaster保存数据库对象（SQLiteDatabase）并管理特定模式的DAO类（而不是对象）。 它具有静态方法来创建表或将它们删除。 其内部类OpenHelper和DevOpenHelper是在SQLite数据库中创建模式的SQLiteOpenHelper实现。一个DaoMaster就代表着一个数据库的连接。
 * DaoSession：管理特定模式的所有可用DAO对象，您可以使用其中一个getter方法获取。 DaoSession还为实体提供了一些通用的持久性方法，如插入，加载，更新，刷新和删除。 DaoSession可以让我们使用一些Entity的基本操作和获取Dao操作类，DaoSession可以创建多个，每一个都是属于同一个数据库连接的。
 * XxxDAO：数据访问对象（DAO）持续存在并查询实体。 对于每个实体，GreenDAO生成一个DAO。 它比DaoSession有更多的持久化方法，例如：count，loadAll和insertInTx。
 */
@Entity //注解含义：将我们的java普通类变为一个能够被greenDAO识别的数据库类型的实体类
public class UserBean {

    @Id(autoincrement = true) //注解含义：选择一个long / Long属性作为实体ID。 在数据库方面，它是主键。 参数autoincrement是设置ID值自增；
    private Long _id;
    private String source;
    @NotNull //注解含义：使该属性在数据库端成为“NOT NULL”列。 通常使用@NotNull标记原始类型（long，int，short，byte）是有意义的；
    private String url;
    @Generated(hash = 1645086964)
    public UserBean(Long _id, String source, @NotNull String url) {
        this._id = _id;
        this.source = source;
        this.url = url;
    }
    @Generated(hash = 1203313951)
    public UserBean() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getSource() {
        return this.source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}

//其他注解含义
//@nameInDb：在数据库中的名字，如不写则为实体中类名；
//@Transient：表明这个字段不会被写入数据库，只是作为一个普通的java类字段，用来临时存储数据的，不会被持久化。