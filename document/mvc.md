# MVC 三層モデルについて

## Tips

### Gradle
- [Gradle入門 - Qiita](https://qiita.com/vvakame/items/83366fbfa47562fafbf4)
- [Gradle使い方メモ - Qiita](https://qiita.com/opengl-8080/items/4c1aa85b4737bd362d9e)

### PostgreSQL

基本コマンド(Linux)
```bash
sudo service postgresql start # 起動
sudo service postgresql stop # 停止
sudo service postgresql restart # 再起動
sudo service postgresql status # 確認
sudo -u postgres psql # suログイン
psql -h localhost -p 5432 -U {your-name} -d {your-database} #ログイン
```

基本コマンド(Windows)
psql接続時
```bash
\l # データベース一覧の表示
\c {your-database} # データベースへの接続
\d # リレーション一覧表示
\dt # テーブル一覧表示 
\d {table-name} # テーブルの詳細表示
\du # ロールの一覧表示
\q # 終了
```

新規ユーザー作成

```bash
CREATE USER {your-name} WITH PASSWORD 'your_password'; 
```

ユーザー名は`whoami`と一致させる
<details>
<summary>peer認証</summary>

: `psql: error: FATAL: Peer authentication failed for user "postgres"`
> ローカルからのアクセス時にpostgres（OS側）のユーザー名がpostgres（データベース側）のものと一致している場合のみ接続を許可する認証方法

1.pg_hba.confを見つける

```bash
sudo find / -name pg_hba.conf
```

2.以下の部分を編集

```text
# Database administrative login by Unix domain socket  
local   all             postgres                                <s>peer</s> md5
```
3.再起動

```bash
sudo /etc/init.d/postgresql restart
```
</details>

新規データベース作成
```bash
CREATE DATABASE {your-database};
```
アクセス権限の付与
```bash
GRANT ALL PRIVILEGES ON DATABASE {your-database} TO {your-name};
```

[データベースの命名規則 - AVINTON](https://avinton.com/academy/database-naming-conventions/)

### Spring Boot

[Spring Boot × PostgreSQLによるデータ登録Tips.(解説付) - Qiita](https://qiita.com/Keichan_15/items/b732bea7c9868c1e9f6c)

[Spring MVC コントローラの引数 - Qiita](https://qiita.com/MizoguchiKenji/items/2a041f3a3eb13274e55c)

## Concerns

- 三層アーキテクチャ・MVCモデルの違い

[MVC、3 層アーキテクチャから設計を学び始めるための基礎知識 - Qiita](https://qiita.com/os1ma/items/7a229585ebdd8b7d86c2)

- DIコンテナがなぜ必要なのか

[DI・DIコンテナ、ちゃんと理解出来てる・・？ - Qiita](https://qiita.com/ritukiii/items/de30b2d944109521298f)

Spring Data JPAでSpecificationを使ってフィルタリングした結果をDTOとして出力する場合、以下のような設計になります。

1. DTOクラスの作成
フィルタリング結果として出力したいフィールドを持つDTOクラスを定義します。[1][3][14]

```java
public class EventSummaryDTO {
    private Long id;
    private String name;
    private LocalDateTime startDate;
    // コンストラクタ、ゲッター、セッターなど
}
```

2. リポジトリインターフェースの作成
`JpaSpecificationExecutor`を継承し、Specificationを使ったフィルタリングメソッドを定義します。[2][3][8][17]

```java
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    List<EventSummaryDTO> findAll(Specification<Event> spec, Class<EventSummaryDTO> type);
}
```

3. Specificationクラスの作成
フィルタリング条件を表すSpecificationクラスを作成します。[2][4][5][16]

```java
public class EventSpecification {
    public static Specification<Event> nameContains(String name) {
        return (root, query, builder) -> 
            builder.like(root.get("name"), "%" + name + "%");
    }
    
    public static Specification<Event> startDateAfter(LocalDateTime date) {
        return (root, query, builder) ->
            builder.greaterThan(root.get("startDate"), date);
    }
}
```

4. サービス層での利用
リポジトリのフィルタリングメソッドを呼び出し、DTOのリストを取得します。[1][5][8]

```java
@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    
    public List<EventSummaryDTO> getEventSummaries(String name, LocalDateTime startDate) {
        Specification<Event> spec = Specification.where(EventSpecification.nameContains(name))
                                                  .and(EventSpecification.startDateAfter(startDate));
        return eventRepository.findAll(spec, EventSummaryDTO.class);
    }
}
```

ポイントをまとめると以下の通りです。

- 出力したいフィールドを持つDTOクラスを定義する[1][3][14]
- リポジトリでJpaSpecificationExecutorを継承し、Specificationとクラスタイプを引数に取るメソッドを定義する[2][3][8][17]
- Specificationを使ってフィルタリング条件を表現する[2][4][5][16]
- サービス層でSpecificationを組み立て、リポジトリのフィルタリングメソッドを呼び出す[1][5][8]

これにより、Specificationによる動的なフィルタリングとDTOへのマッピングを同時に実現できます。
取得するフィールドを最小限に絞ることでパフォーマンスの最適化も期待できるでしょう。

ただし、DTOへのマッピングのオーバーヘッドや、複雑なSpecificationの可読性などには注意が必要です。
要件に合わせて適切な設計を選択することが肝要だと言えます。

情報源
[1] Spring Data JPA: Query Projections - Thorben Janssen https://thorben-janssen.com/spring-data-jpa-query-projections/
[2] Specifications :: Spring Data JPA https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html
[3] Example of Projections in Spring Data JPA - GitHub https://github.com/bytestree/spring-data-jpa-projections
[4] [Spring Data JPA] Specificationを利用して動的なクエリ生成 ... - Qiita https://qiita.com/umiushi_1/items/e62bc4b3bdfe13ae82df
[5] Spring Data Jpa Specificationを使って複数条件での検索機能を作る https://qiita.com/tamorieeeen/items/be3f8c46dfa725014008
[6] Spring Data JPA - Projection Aggregate Example - GitHub https://github.com/jitterted/spring-data-projection
[7] JPA Projection - Maciej Walkowiak https://maciejwalkowiak.com/guides/spring-data-jpa/projections/jpa-projections
[8] Spring Data JPA Specification With Projection - Stack Overflow https://stackoverflow.com/questions/76283573/spring-data-jpa-specification-with-projection
[9] Projections :: Spring Data JPA https://docs.spring.io/spring-data/jpa/reference/repositories/projections.html
[10] Adding projection support for JPASpecificationExecutor #2499 https://github.com/spring-projects/spring-data-jpa/issues/2499
[11] Spring data JPAで@EntityGraphとSpecificationがそれぞれ別々の ... https://ja.stackoverflow.com/questions/95511/spring-data-jpa%E3%81%A7entitygraph%E3%81%A8specification%E3%81%8C%E3%81%9D%E3%82%8C%E3%81%9E%E3%82%8C%E5%88%A5%E3%80%85%E3%81%AEjoin%E5%8F%A5%E3%82%92%E7%94%9F%E6%88%90%E3%81%99%E3%82%8B%E3%81%AE%E3%82%92%E4%B8%80%E3%81%A4%E3%81%AB-%E5%85%B1%E7%94%A8-%E3%81%97%E3%81%9F%E3%81%84
[12] Spring Data JPA https://docs.spring.io/spring-data/jpa/reference/index.html
[13] The best way to use the Spring Data JPA Specification - Vlad Mihalcea https://vladmihalcea.com/spring-data-jpa-specification/
[14] The best way to fetch a Spring Data JPA DTO Projection https://vladmihalcea.com/spring-jpa-dto-projection/
[15] Spring Data JPA Projections | Baeldung https://www.baeldung.com/spring-data-jpa-projections
[16] Specification (Spring Data JPA Parent API) - Javadoc https://spring.pleiades.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/domain/Specification.html
[17] JpaSpecificationExecutor (Spring Data JPA Parent API) - Javadoc https://spring.pleiades.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaSpecificationExecutor.html
