# blog5
钱宇豪的个人博客

一个重型博客，采用spring mybatis thymeleaf开发

当前版本: **5.9 dev**

特性：

* 空间替代了分类，每个空间都可以独立设置一套模板

* 自定义路径和页面

* 文章置顶、定时发布以及草稿箱

* lucene搜索，结果高亮

* 评论服务，评论|回复邮件通知

* RSS订阅

* 文件管理（可拓展七牛云等存储支持），缩略图服务

* 文章，空间锁保护

* ping服务

* sitemap

## demo: https://www.qyh.me

## detail: https://www.qyh.me/project/blog

## changelog: https://www.qyh.me/changelog


## version
5.5.1 -> 5.1  
...  
5.5.5 -> 5.5   
...

## upgrade  
5.9    
1.
``` sql
CREATE TABLE IF NOT EXISTS blog_history_template (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `template_name` varchar(255) NOT NULL,
  `template_tpl` mediumtext NOT NULL,
  `template_time` datetime NOT NULL,
  `remark` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
);
```


