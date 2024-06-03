# MVC 三層モデルについて

import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '@/views/Home.vue'
import About from '@/views/About.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    component: About
  },
  // 他のルートもここに追加
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    // 戻る操作の場合、保存された位置にスクロール
    if (savedPosition) {
      return savedPosition
    } else {
      // 特定のパスに対してはページトップにスクロール、それ以外は位置を引き継ぐ
      if (to.path === '/about') {
        return { top: 0 }
      }
      // デフォルトでは位置を保持
      return { left: 0, top: 0 }
    }
  }
})

export default router

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
