

# MySQL  索引优化方法、步骤

### 建索引的原则

Reference: 美团 https://tech.meituan.com/2014/06/30/mysql-index.html

1. 最左前缀匹配原则，非常重要的原则，mysql会一直向右匹配直到遇到范围查询(>、<、between、like)就停止匹配，比如a = 1 and b = 2 and c > 3 and d = 4 如果建立(a,b,c,d)顺序的索引，d是用不到索引的，如果建立(a,b,d,c)的索引则都可以用到，a,b,d的顺序可以任意调整。

2. =和in可以乱序，比如a = 1 and b = 2 and c = 3 建立(a,b,c)索引可以任意顺序，mysql的查询优化器会帮你优化成索引可以识别的形式。

3. 尽量选择区分度高的列作为索引，区分度的公式是count(distinct col)/count(*)，表示字段不重复的比例，比例越大我们扫描的记录数越少，唯一键的区分度是1，而一些状态、性别字段可能在大数据面前区分度就是0，那可能有人会问，这个比例有什么经验值吗？使用场景不同，这个值也很难确定，一般需要join的字段我们都要求是0.1以上，即平均1条扫描10条记录。

4. 索引列不能参与计算，保持列“干净”，比如from_unixtime(create_time) = ’2014-05-29’就不能使用到索引，原因很简单，b+树中存的都是数据表中的字段值，但进行检索时，需要把所有元素都应用函数才能比较，显然成本太大。所以语句应该写成create_time = unix_timestamp(’2014-05-29’)。

5. 尽量的扩展索引，不要新建索引。比如表中已经有a的索引，现在要加(a,b)的索引，那么只需要修改原来的索引即可。



### 慢查询优化步骤

Reference: 美团 https://tech.meituan.com/2014/06/30/mysql-index.html

0. 先运行看看是否真的很慢，注意设置SQL_NO_CACHE

1. where条件单表查，锁定最小返回记录表。这句话的意思是把查询语句的where都应用到表中返回的记录数最小的表开始查起，单表每个字段分别查询，看哪个字段的区分度最高

2. explain查看执行计划，是否与1预期一致（从锁定记录较少的表开始查询）

- Explain Output: https://dev.mysql.com/doc/refman/8.0/en/explain-output.html

3. order by limit 形式的sql语句让排序的表优先查

4. 了解业务方使用场景

5. 加索引时参照建索引的几大原则

6. 观察结果，不符合预期继续从0分析



---

### 不同Storage Engine下的索引类型

| Storage Engine | Permissible Index Types |
| -------------- | ----------------------- |
| InnoDB         | BTREE                   |
| MyISAM         | BTREE                   |
| MEMORY/HEAP    | HASH, BTREE             |
| NDB            | HASH, BTREE             |

Some Storage engines permit you to specify an index type when creating an index:

```sql
CREATE TABLE lookup (id INT) ENGINE = MEMORY;
CREATE INDEX id_index ON lookup (id) USING BTREE;
```

If you specify an index type that is not valid for a given storage engine, but another index type is available that the engine can use without affecting query results, the engine uses the available type.



### Hash vs. B-tree

Reference: https://dev.mysql.com/doc/refman/8.0/en/index-btree-hash.html

#### B-Tree Index Characteristics

A B-tree index can be used for column comparisons in expressions that use the [`=`](https://dev.mysql.com/doc/refman/8.0/en/comparison-operators.html#operator_equal), [`>`](https://dev.mysql.com/doc/refman/8.0/en/comparison-operators.html#operator_greater-than), [`>=`](https://dev.mysql.com/doc/refman/8.0/en/comparison-operators.html#operator_greater-than-or-equal), [`<`](https://dev.mysql.com/doc/refman/8.0/en/comparison-operators.html#operator_less-than), [`<=`](https://dev.mysql.com/doc/refman/8.0/en/comparison-operators.html#operator_less-than-or-equal), or [`BETWEEN`](https://dev.mysql.com/doc/refman/8.0/en/comparison-operators.html#operator_between) operators. The index also can be used for [`LIKE`](https://dev.mysql.com/doc/refman/8.0/en/string-comparison-functions.html#operator_like) comparisons if the argument to [`LIKE`](https://dev.mysql.com/doc/refman/8.0/en/string-comparison-functions.html#operator_like) is a constant string that does not start with a wildcard character. For example, the following [`SELECT`](https://dev.mysql.com/doc/refman/8.0/en/select.html) statements use indexes:

```sql
SELECT * FROM tbl_name WHERE key_col LIKE 'Patrick%';
SELECT * FROM tbl_name WHERE key_col LIKE 'Pat%_ck%';
```

In the first statement, only rows with `'Patrick' <= *`key_col`* < 'Patricl'` are considered. In the second statement, only rows with `'Pat' <= *`key_col`* < 'Pau'` are considered.

The following [`SELECT`](https://dev.mysql.com/doc/refman/8.0/en/select.html) statements do not use indexes:

```sql
SELECT * FROM tbl_name WHERE key_col LIKE '%Patrick%';
SELECT * FROM tbl_name WHERE key_col LIKE other_col;
```

In the first statement, the [`LIKE`](https://dev.mysql.com/doc/refman/8.0/en/string-comparison-functions.html#operator_like) value begins with a wildcard character. In the second statement, the [`LIKE`](https://dev.mysql.com/doc/refman/8.0/en/string-comparison-functions.html#operator_like) value is not a constant.

#### Hash Index Characteristics

Hash indexes have somewhat different characteristics from those just discussed:

- They are used only for equality comparisons that use the `=` or `<=>` operators (but are *very* fast). They are not used for comparison operators such as `<` that find a range of values. Systems that rely on this type of single-value lookup are known as “key-value stores”; to use MySQL for such applications, use hash indexes wherever possible.
- The optimizer cannot use a hash index to speed up `ORDER BY` operations. (This type of index cannot be used to search for the next entry in order.)
- MySQL cannot determine approximately how many rows there are between two values (this is used by the range optimizer to decide which index to use). This may affect some queries if you change a `MyISAM` or `InnoDB` table to a hash-indexed `MEMORY` table.
- Only whole keys can be used to search for a row. (With a B-tree index, any leftmost prefix of the key can be used to find rows.)



### 单列索引和组合索引

#### 单列索引 Column Index

Reference: https://dev.mysql.com/doc/refman/8.0/en/create-index.html

```sql
/* Case 1: Create index when creating the table*/
CREATE TABLE t1 (
  col1 VARCHAR(10),
  col2 VARCHAR(20),
  INDEX (col1, col2(10))
);

/* Case 2: MySQL 8.0.13 and higher: Use of functional key parts enables indexing of values not stored directly in the table.*/
CREATE TABLE t1 (col1 INT, col2 INT, INDEX func_index ((ABS(col1))));
CREATE INDEX idx1 ON t1 ((col1 + col2));
CREATE INDEX idx2 ON t1 ((col1 + col2), (col1 - col2), col1);
ALTER TABLE t1 ADD INDEX ((col1 * 40) DESC);
```



#### 组合索引 Multiple-Column Index

Reference: https://dev.mysql.com/doc/refman/8.0/en/multiple-column-indexes.html

An index may consist of up to **16** columns. For certain data types, you can index a prefix of the column.

```sql
/* Case 1 Crease index on (last_name, first_name)*/
CREATE TABLE test (
    id         INT NOT NULL,
    last_name  CHAR(30) NOT NULL,
    first_name CHAR(30) NOT NULL,
    PRIMARY KEY (id),
    INDEX name (last_name,first_name)
);
```

The index can be used for lookups in queries that specify values in a known range for combinations of `last_name` and `first_name` values. It can also be used for queries that specify just a `last_name` value because that column is a leftmost prefix of the index (as described later in this section).

- Used Index

  ```sql
  SELECT * FROM test WHERE last_name='Jones';
  
  SELECT * FROM test
    WHERE last_name='Jones' AND first_name='John';
  
  SELECT * FROM test
    WHERE last_name='Jones'
    AND (first_name='John' OR first_name='Jon');
  
  SELECT * FROM test
    WHERE last_name='Jones'
    AND first_name >='M' AND first_name < 'N';
  ```

- Not used index

  ```sql
  SELECT * FROM test WHERE first_name='John';
  
  SELECT * FROM test
    WHERE last_name='Jones' OR first_name='John';
  ```

  

### 用索引做 `ORDER BY`

Reference: https://dev.mysql.com/doc/refman/8.0/en/order-by-optimization.html

**If the index does not contain all columns accessed by the query, the index is used only if index access is cheaper than other access methods**

Assume that there is an index on `(key_part1, key_part2)`.

- Use index on `(key_part1, key_part2)` to avoid sorting

  ```sql
  SELECT * FROM table1
  	ORDER BY key_part1, key_part2;
  ```

  The query uses `SELECT *`, which may select more columns than `key_part1` and `key_part2`. Scanning an entire index and looking up table rows to find columns not in the index may be more expensive than scanning the table and sorting the results.

  如果其他列太多，Scan整个索引加上找表里对应的其他列很可能不划算。

- The index on `(key_part1, key_part2)` avoid sorting if the `WHERE` clause is selective enough to make an index range scan cheaper than a table.

  ```sql
  SELECT * FROM t1
  	WHERE key_part1 = constant
  	ORDER BY key_part2;
  	
  SELECT * FROM t1
  	WHERE key_part1 > constant
  	ORDER BY key_part1;
  
  SELECT * FROM t1
  	WHERE key_part1 = constant1 AND key_part2 > constant2
  	ORDER BY key_part2;
  ```

- **Cannot** use indexes to resolve the `ORDER BY`

  - Uses `ORDER BY` on nonconsecutive parts of an index:

    ```sql
    SELECT * FROM t1 WHERE key2=constant ORDER BY key_part1, key_part3;
    ```



