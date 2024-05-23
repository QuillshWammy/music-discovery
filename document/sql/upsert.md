# UPSERT文

## Positon
渡されたレコードのtickerが既に存在していればquantityを更新し、tickerが新規であればレコードを挿入するUPSERT操作

テーブル定義:
```sql
CREATE TABLE positions (
  id SERIAL PRIMARY KEY,
  ticker VARCHAR(10) UNIQUE,
  quantity INTEGER
);
```

```sql
INSERT INTO positions (ticker, quantity)
VALUES ('AAPL', 100), ('GOOGL', 50), ('AAPL', 150), ('AMZN', 200)  
ON CONFLICT (ticker) DO UPDATE
  SET quantity = EXCLUDED.quantity;
```

- INSERTでtickerとquantityの値を指定
- ON CONFLICT句でtickerカラムを指定。ここでtickerの重複が発生した場合、UPDATEが実行される
- DO UPDATE SET でquantityをEXCLUDED.quantityで更新。EXCLUDED.quantityとすることで、もともとINSERTしようとしていたquantityの値を使って更新できる

id | ticker | quantity
-- | ------ | --------
1  | AAPL   | 150
2  | GOOGL  | 50
3  | AMZN   | 200

## SpringJDBC

### バッチ更新

JdbcTemplateの`batchUpdate`メソッドを使うことで、複数のSQLを一括で実行できます。

- SQLとBatchPreparedStatementSetterを引数に渡す
- BatchPreparedStatementSetterでは、各バッチのパラメータをセットする
- getBatchSizeでバッチサイズを返す
- batchUpdateは各バッチの更新行数の配列を返す

### Query

```java
public void upsertPositions(List<Position> positions) {
    String sql = "INSERT INTO positions (ticker, quantity) " +
                 "VALUES (?, ?) " +
                 "ON CONFLICT (ticker) DO UPDATE SET " +
                 "quantity = EXCLUDED.quantity";

    jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
        @Override
        public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setString(1, positions.get(i).getTicker());
            ps.setInt(2, positions.get(i).getQuantity());
        }

        @Override
        public int getBatchSize() {
            return positions.size();
        }
    });
}
```

- バッチ処理なし

```java
public void upsertPosition(Position position) {
    String sql = "INSERT INTO positions (ticker, quantity) " +
                 "VALUES (?, ?) " +
                 "ON CONFLICT (ticker) DO UPDATE SET " +
                 "quantity = positions.quantity + ?";

    jdbcTemplate.update(sql,
        position.getTicker(),
        position.getQuantity(),
        position.getQuantity());
}
```
