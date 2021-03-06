package com.meipiao.index;

import io.swagger.models.auth.In;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 关于index的知识
 * @Author: Chenwx
 * @Date: 2020/5/30 17:21
 */
public class IndexInfo {

    /*
     *
     * 1. 在where 从句，group by 从句，order by 从句，on 从句中出现的列；
     *
     * 2. 索引字段越小越好；
     *
     * 3. 离散度大的列放到联合索引的前面；比如：
     *
     *     select * from payment where staff_id = 2 and customer_id = 236;
     *
     *     针对上面的查询是  index(sftaff_id, customer_id) 好？还是index(customer_id, staff_id)好？
     *
     *     因为customer_id的离散度更大，因此用后面的更合适！！
     *
     * 那么问题来了。怎么判断离散度呢，可以使用 select count(distinct customer_id), count(distinct staff_id) from 表名
     *
     * 谁的值大，说明这一些列的离散度更高！
     *
     *
     索引种类:

     * 1. 普通索引：仅加速查询
     *
     * 2. 唯一索引：加速查询 + 列值唯一（可以有null）
     *
     * 3. 主键索引：加速查询 + 列值唯一（不可以有null）+ 表中只有一个
     *
     * 4. 组合索引：多列值组成一个索引，专门用于组合搜索，其效率大于索引合并
     *
     * 5. 全文索引：对文本的内容进行分词，进行搜索
     *
     */

    /*
        1，创建索引

        对于查询占主要的应用来说，索引显得尤为重要。很多时候性能问题很简单的就是因为我们忘了添加索引而造成的，或者说没有添加更为有效的索引导致。如果不加

        索引的话，那么查找任何哪怕只是一条特定的数据都会进行一次全表扫描，如果一张表的数据量很大而符合条件的结果又很少，那么不加索引会引起致命的性能下降。
        但是也不是什么情况都非得建索引不可，比如性别可能就只有两个值，建索引不仅没什么优势，还会影响到更新速度，这被称为过度索引。

        2，复合索引

        比如有一条语句是这样的：select * from users where area=’beijing’ and age=22;

        如果我们是在area和age上分别创建单个索引的话，由于mysql查询每次只能使用一个索引，所以虽然这样已经相对不做索引时全表扫描提高了很多效

        率，但是如果在area、age两列上创建复合索引的话将带来更高的效率。如果我们创建了(area, age,salary)的复合索引，那么其实相当于创建了(area,age,salary)、(area,age)、(area)三个索引，这被称为最佳左前缀特性。
        因此我们在创建复合索引时应该将最常用作限制条件的列放在最左边，依次递减。

        3，索引不会包含有NULL值的列

        只要列中包含有NULL值都将不会被包含在索引中，复合索引中只要有一列含有NULL值，那么这一列对于此复合索引就是无效的。所以我们在数据库设计时不要让字段的默认值为NULL。

        4，使用短索引

        对串列进行索引，如果可能应该指定一个前缀长度。例如，如果有一个CHAR(255)的 列，如果在前10 个或20 个字符内，多数值是惟一的，那么就不要对整个列进行索引。短索引不仅可以提高查询速度而且可以节省磁盘空间和I/O操作。

        5，排序的索引问题

        mysql查询只使用一个索引，因此如果where子句中已经使用了索引的话，那么order by中的列是不会使用索引的。因此数据库默认排序可以符合要求的情况下不要使用排序操作；尽量不要包含多个列的排序，如果需要最好给这些列创建复合索引。

        6，like语句操作

        一般情况下不鼓励使用like操作，如果非使用不可，如何使用也是一个问题。like “%aaa%” 不会使用索引而like “aaa%”可以使用索引。

        7，不要在列上进行运算

        select * from users where

        YEAR(adddate)

        8，不使用NOT IN和操作

        NOT IN和操作都不会使用索引将进行全表扫描。NOT IN可以NOT EXISTS代替，id3则可使用id>3 or id
     */
    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();
        IndexInfo indexInfo = new IndexInfo();
        Date date = new Date(System.currentTimeMillis() + 60000 * 60 * 8);
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        System.out.println(currentTime);



    }

    public String getValue(){
        return "James";
    }
}
