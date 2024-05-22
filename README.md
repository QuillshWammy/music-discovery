# Music Discovery

## Environment

- wsl2
- PostgreSQL
- Spring Boot
- Vue.js

はい、Spring Data JPAのJpaSpecificationExecutorインターフェースには、ソート機能を含むfindAllメソッドのオーバーロードがあります。以下のようにSpecificationとSortを組み合わせることができます。

```java
public interface MyEntityRepository 
    extends JpaRepository<MyEntity, Long>, JpaSpecificationExecutor<MyEntity> {

    List<MyEntity> findAll(Specification<MyEntity> spec, Sort sort);
}
```

使用例:

```java
// Specificationの定義
Specification<MyEntity> spec = // ...

// ソート条件の定義
Sort sort = Sort.by(Sort.Direction.ASC, "propertyName");

// 検索の実行
List<MyEntity> results = myEntityRepository.findAll(spec, sort);
```

ポイント:

- `findAll`メソッドに`Specification`と`Sort`の両方を渡すことで、動的な検索条件とソート条件を同時に指定できる[1][3][4]
- `Sort`オブジェクトは`Sort.by`メソッドで生成し、ソートの方向（昇順/降順）とソートのプロパティ名を指定する[2][8]
- 複数の条件でソートしたい場合は、`Sort.by(...).and(...)`のように`and`メソッドでソート条件を連結できる[2][8]

これにより、動的な検索条件（Specification）と柔軟なソート条件（Sort）を組み合わせて、リポジトリのfindAllメソッドを呼び出すことができます。

ページネーションも必要な場合は、`findAll(Specification, Pageable)`を使用します。
Pageableインターフェースは、ページ番号とページサイズに加えて、Sortオブジェクトも保持できるので、これにより「動的検索×ページネーション×ソート」の3つを同時に実現できます。[4][10][13]

情報源
[1] Spring JPA Specification with Sort - java - Stack Overflow https://stackoverflow.com/questions/43028457/spring-jpa-specification-with-sort
[2] Spring Data JPAによるソート（複合キーのソート付き） - Qiita https://qiita.com/shikazuki/items/c864ac51dcbdf52554cf
[3] JpaSpecificationExecutor (Spring Data JPA Parent API) - Javadoc https://spring.pleiades.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaSpecificationExecutor.html
[4] The best way to use the Spring Data JPA Specification - Vlad Mihalcea https://vladmihalcea.com/spring-data-jpa-specification/
[5] Spring DATA JPA Specification - Oboe吹きプログラマの黙示録 https://oboe2uran.hatenablog.com/entry/2024/04/14/125502
[6] Specifications with sort creates additional join even though the entity ... https://github.com/spring-projects/spring-data-jpa/issues/2253
[7] JpaSpecificationExecutor JOIN + ORDER BY in Specification https://stackoverflow.com/questions/37203878/jpaspecificationexecutor-join-order-by-in-specification
[8] Spring Data JPAのソート - Qiita https://qiita.com/parapore/items/4acffd670fc913e05d85
[9] spring-data-jpa アンチパターン #Java - Qiita https://qiita.com/sis-yoshiday/items/c29cc8eb3d529f7044ff
[10] 【JPA徹底入門】JPQLによる検索処理 - Zenn https://zenn.dev/sooogle/articles/1a13f48acd1a02
[11] Sorting with JPA | Baeldung https://www.baeldung.com/jpa-sort
[12] Spring Data JPA で遊んでみる 〜その3〜 - Yamkazu's Blog https://yamkazu.hatenablog.com/entry/20111012/1318441620
[13] Specification × Pagination × Sorting - Zenn https://zenn.dev/crsc1206/books/d8166194fd58f2/viewer/2456ee

Spring Data JPAでtimestampで格納されている項目について、引数のLocalDateTimeと同じ日付を取り出すには、以下のようなクエリメソッドを定義することができます。

```java
List<Entity> findByDateColumnBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
```

ここで、`DateColumn`はtimestamp型のカラム名に置き換えてください。

このクエリメソッドは、指定した日付の00:00:00から23:59:59の範囲に収まるレコードを検索します。引数には、`LocalDateTime`を使用しています。

使用例:

```java
LocalDate localDate = LocalDate.of(2023, 5, 23);
LocalDateTime startOfDay = localDate.atStartOfDay(); 
LocalDateTime endOfDay = localDate.atTime(23, 59, 59);
List<Entity> entities = repository.findByDateColumnBetween(startOfDay, endOfDay);
```

ポイント:

- `LocalDate`の`atStartOfDay()`メソッドで、指定日付の00:00:00の`LocalDateTime`が取得できる[7]
- `LocalDate`の`atTime()`メソッドで、指定日付の任意の時刻の`LocalDateTime`が取得できる[7]
- クエリメソッドでは`Between`キーワードを使い、開始日時と終了日時を指定する[1][6]
- DB上のtimestamp型と、Java側の`LocalDateTime`型をマッピングできる[4][14]

以上のように、Spring Data JPAの既存機能とJava 8のDate and Time APIを組み合わせることで、比較的シンプルに実装できます。
ネイティブクエリを使う必要はありません。

情報源
[1] Spring Data JPA Fetching data based on a timestamp column https://stackoverflow.com/questions/43828186/spring-data-jpa-fetching-data-based-on-a-timestamp-column
[2] PostgreSql: operator does not exist: timestamp without time zone ... https://github.com/spring-projects/spring-data-jpa/issues/2491
[3] Spring Data JPA Query Method - Find by Between - YouTube https://www.youtube.com/watch?v=4dndK_8MMHA
[4] How to map Date and Timestamp with JPA and Hibernate https://vladmihalcea.com/date-timestamp-jpa-hibernate/
[5] How to search by Dates and Times with Spring Data JPA? https://stackoverflow.com/questions/57327134/how-to-search-by-dates-and-times-with-spring-data-jpa
[6] [Spring Data JPA] Specificationを利用して動的なクエリ生成 ... - Qiita https://qiita.com/umiushi_1/items/e62bc4b3bdfe13ae82df
[7] JPA Query Methods - Spring https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
[8] Spring Data JPA - Reference Documentation https://docs.spring.io/spring-data/jpa/docs/current-SNAPSHOT/reference/html/
[9] Check date between two other dates spring data jpa - CodeProject https://www.codeproject.com/Questions/5364644/Check-date-between-two-other-dates-spring-data-jpa
[10] 【Spring Data JPA】自動実装されるメソッドの命名ルール - Qiita https://qiita.com/shindo_ryo/items/af7d12be264c2cc4b252
[11] 【Spring Boot+JPA】レコードの作成・更新日時を扱う - Qiita https://qiita.com/vossibop/items/dbb2edf67a72a7fe8b41
[12] incompatible data type in conversion in hsqldb when query IS NULL ... https://github.com/spring-projects/spring-data-jpa/issues/2486
[13] Query Entities by Dates and Times with Spring Data JPA | Baeldung https://www.baeldung.com/spring-data-jpa-query-by-date
[14] JPA 2.2 Support for Java 8 Date/Time Types | Baeldung https://www.baeldung.com/jpa-java-time
[15] Wrong error hint for JPA current_timestamp() function in Spring Data ... https://youtrack.jetbrains.com/issue/IDEA-208486
[16] SpringのDateを使用したJPAクエリの作成をする - Zenn https://zenn.dev/gakkie555/articles/51c22218090d3f
[17] 5.3. データベースアクセス（JPA編） - terasoluna.org https://terasolunaorg.github.io/guideline/5.1.1.RELEASE/ja/ArchitectureInDetail/DataAccessJpa.html
