# Music Discovery

## Environment

- wsl2
- PostgreSQL 
- Spring Boot
- Vue.js 

## Tips

Spring Data JDBCを使用してJOINクエリを実行する際に、同一カラム名によるエラー「列名はありません」が発生する問題は、以下の方法で解決できます。

## 1. 列にエイリアスを付ける[1][6]

JOINしたテーブルに同一のカラム名が存在する場合、SELECTする列にエイリアスを付けることで曖昧さを解消できます。

```sql
SELECT 
  s.name AS Sname, s.city AS Scity,
  f.name AS Fname, f.city AS Fcity,
  su.name AS Suname, su.city AS Sucity
FROM order_details ot
INNER JOIN order o ON ot.odr_id = o.odr_id
INNER JOIN product p ON ot.pro_id = p.id
INNER JOIN firm f ON o.firm_id = f.id
INNER JOIN shipp s ON o.shipp_id = s.id
INNER JOIN supplier su ON o.sup_id = su.id
```

## 2. エンティティのプロパティ名をスネークケースに変更する[5]

Spring Data JPAではデフォルトでカラム名をスネークケースに変換します。エンティティのプロパティ名もスネークケースにすることで、名前の不一致を防げます。

```java
@Entity
public class Student {
  @Id
  private Long id;
  private String name;
  private String passport_number; // スネークケースを使用
}
```

## 3. @Columnアノテーションでカラム名を明示する[5]

エンティティのプロパティに`@Column`アノテーションを付けて、マッピングするカラム名を明示的に指定できます。

```java
@Entity
public class Student {
  @Id  
  private Long id;
  private String name;
  @Column(name = "passportNumber") 
  private String passportNumber;
}
```

## 4. NamingStrategyを設定してカラム名の変換ルールをカスタマイズする[3]

`application.properties`でNamingStrategyを設定することで、Spring Data JPAがカラム名を変換するルールをカスタマイズできます。

```properties
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
```

以上の方法を状況に応じて適用することで、Spring Data JDBCを使用する際の同一カラム名によるエラーを解決できるはずです。
エイリアスの使用が最も一般的で確実な方法だと思います。

情報源
[1] Thread: PSQLException: The column name was not found in this ResultSet. https://postgrespro.com/list/thread-id/1937060
[2] Generated sql for Postgres is problematic [DATAJDBC-593] #815 https://github.com/spring-projects/spring-data-relational/issues/815
[3] Migrating to Spring Data JDBC 2.0 https://spring.io/blog/2020/05/20/migrating-to-spring-data-jdbc-2-0/
[4] `SimpleJdbcInsert` does not quote table and column names [SPR-9236] https://github.com/spring-projects/spring-framework/issues/13874
[5] java - Invalid column name exception - JdbcPagingItemReader query with ... https://stackoverflow.com/questions/49762248/invalid-column-name-exception-jdbcpagingitemreader-query-with-alias
[6] Spring JPA error on DB column returns error related to column name https://stackoverflow.com/questions/63641307/spring-jpa-error-on-db-column-returns-error-related-to-column-name
[7] Same column names in JOIN getting "column not found" error in ResultSet https://stackoverflow.com/questions/49984631/same-column-names-in-join-getting-column-not-found-error-in-resultset
[8] Spring JDBC DataSource Initializer fails for schema with stored ... - GitHub https://github.com/spring-projects/spring-boot/issues/6217
[9] SpringのDBコネクションの共有方法(DBトランザクション)を理解する https://qiita.com/kazuki43zoo/items/48061b82933e9b3d4ca8
[10] 12. Data access with JDBC - Spring https://docs.spring.io/spring-framework/docs/3.0.x/spring-framework-reference/html/jdbc.html
[11] 5.1. データベースアクセス（共通編） - terasoluna.org https://terasolunaorg.github.io/guideline/1.0.5.RELEASE/ja/ArchitectureInDetail/DataAccessCommon.html
[12] Spring Data JDBC and R2DBC https://docs.spring.io/spring-data/relational/reference/index.html
[13] 6.2. データベースアクセス（MyBatis3編） - terasoluna.org https://terasolunaorg.github.io/guideline/5.2.0.RELEASE/ja/ArchitectureInDetail/DataAccessDetail/DataAccessMyBatis3.html
[14] ErrorCode - H2 Database Engine https://www.h2database.com/javadoc/org/h2/api/ErrorCode.html
[15] Initializing the Driver - PostgreSQL JDBC Driver https://jdbc.postgresql.org/documentation/use/
[16] Same type (java.sql.Timestamp) turns into different SQL-types #1089 https://github.com/spring-projects/spring-data-relational/issues/1089
[17] How to use tables and column aliases when building a PostgreSQL query https://www.enterprisedb.com/postgres-tutorials/how-use-tables-and-column-aliases-when-building-postgresql-query
[18] INNER JOINでエラーメッセージ「Column 'カラム名' in field list is ... https://qiita.com/takaya_hiyama/items/2e7087b6d07079a15e29
[19] Spring Bootでデータベースにアクセスしよう - CodeZine https://codezine.jp/article/detail/11584
[20] Spring JDBC Batch Inserts | Baeldung https://www.baeldung.com/spring-jdbc-batch-inserts
