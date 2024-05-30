# Slack APIの連携

Slack Boltで`users.lookupByEmail`メソッドを使ってユーザーのメールアドレスからユーザーIDを取得する方法を、TypeScriptで示します。

1. 必要な依存関係をインストールします。

```bash
npm install @slack/bolt @slack/web-api
```

2. `app.ts`ファイルを作成し、以下のコードを記述します。

```typescript
import { App, LogLevel } from '@slack/bolt';
import { WebClient } from '@slack/web-api';

// Slack BotトークンとアプリトークンをEnvironment Variablesから取得
const botToken = process.env.SLACK_BOT_TOKEN;
const appToken = process.env.SLACK_APP_TOKEN;

const app = new App({
  token: botToken,
  appToken: appToken,
  logLevel: LogLevel.DEBUG, // デバッグログを有効化
  socketMode: true // ソケットモードを有効化
});

const client = new WebClient(botToken);

// メールアドレスからユーザーIDを取得する関数
async function getUserIdByEmail(email: string): Promise<string | undefined> {
  try {
    const result = await client.users.lookupByEmail({ email });
    if (result.ok && result.user) {
      return result.user.id;
    } else {
      console.error(`Error looking up email ${email}: ${result.error}`);
    }
  } catch (error) {
    console.error(`Error looking up email ${email}: ${error}`);
  }
}

// アプリの起動
(async () => {
  await app.start();
  console.log('⚡️ Bolt app started');
})();
```

3. アプリを実行します。

```bash
npm start
```

この例では、`getUserIdByEmail`関数を使って、メールアドレスからユーザーIDを取得しています。この関数は`users.lookupByEmail`メソッドを呼び出し、結果を処理しています。

- `users.lookupByEmail`メソッドを使うには、アプリに`users:read.email`スコープが必要です[1][4]。
- エラーハンドリングを適切に行うことが重要です[4]。
- ログレベルを`LogLevel.DEBUG`に設定すると、接続状態などの詳細なログが出力されます[1][2]。

この例では、ソケットモードを有効にしていますが、HTTPリクエストを処理する場合は`receiver`を設定する必要があります[2]。

Slack Boltを使えば、Slackアプリの開発がシンプルになり、WebSocketを使ったリアルタイム通信も簡単に実装できます。適切なスコープを設定し、エラーハンドリングを行えば、ユーザー情報の取得などの基本的な機能を実装できます。

情報源
[1] Bolt 入門ガイド - Slack | Bolt for JavaScript https://slack.dev/bolt-js/ja-jp/tutorial/getting-started
[2] Ack 関数 - Slack | Bolt for JavaScript https://slack.dev/bolt-js/ja-jp/concepts
[3] Bolt for JavaScript - Slack Platform Developer Tools https://slack.dev/bolt-js/concepts
[4] 全社員へのメールを個別にSlackで通知する - VisasQ Dev Blog https://tech.visasq.com/notify-mail-to-slack/
[5] Slack: ユーザ名取得 - Questetra Support https://support.questetra.com/ja/bpmn-icons/service-task-slack-username-get/


はい、Slack APIを使ってイベントページ管理システムと連携する具体的な方法をまとめると以下のようになります。

1. Slack APIアプリを作成する
- Slack API: Applicationsページから新しいアプリを作成[16]
- アプリ名と連携するワークスペースを指定

2. Bot機能の追加とトークンの取得
- OAuth & Permissionsから必要なBot Token Scopesを追加 (channels:manage, chat:write等)[15][16]
- Install Appでワークスペースにアプリをインストールし、Bot User OAuth Tokenを取得[15]

3. イベントページ管理システムからSlack APIを呼び出してチャンネルを作成
- conversations.createメソッドを使ってチャンネルを新規作成[17]
- リクエストにはBot Tokenを使用

4. 作成したチャンネルにイベント参加者を招待
- conversations.inviteメソッドを使ってチャンネルにユーザーを追加[15]
- 参加者のユーザーIDをリクエストに含める

5. 指定日時にメッセージを投稿するようにスケジュール
- chat.scheduleMessageメソッドを使って指定日時にメッセージ投稿[14][12]
- UNIXタイムスタンプで投稿日時を指定

6. Slack Event APIを使ってメッセージ投稿のトリガーを設定
- Event Subscriptionsを有効化し、イベント受信用のエンドポイントURLを設定[11][13]
- 特定イベント発生時のメッセージ投稿をトリガーできる

7. 定期的なメッセージ投稿にはSlack Workflowsを活用
- 一定間隔でのメッセージ自動投稿が可能[7]

以上のように、Slack APIのBot機能とEvent APIを組み合わせることで、イベントページ管理システムからチャンネル作成、参加者招待、スケジュールメッセージ投稿などが実現できます。
APIを直接呼び出すのが難しい場合は、Zapier等のサードパーティ製連携ツールを使うのも一案です。[1][19][20]

イベント管理システムとSlackを連携させることで、参加者への円滑な情報共有とコミュニケーションが可能になるでしょう。
ぜひ具体的な実装にチャレンジしてみてください。

Citations:
[1] https://slack.dev/java-slack-sdk/guides/ja/events-api
[2] https://qiita.com/taidong5588/items/0f2ae3dac0bea18ff01d
[3] https://dev.classmethod.jp/articles/slack-api-send-dm/
[4] https://zenn.dev/kou_pg_0131/articles/slack-api-post-message
[5] https://developer.so-tech.co.jp/entry/2022/04/05/131934
[6] https://qiita.com/kanaxx/items/c29267d88c3fb2cc381c
[7] https://christina04.hatenablog.com/entry/sending-messages-with-slack-app
[8] https://api.slack.com/lang/ja-jp/which-api
[9] https://api.slack.com/lang/ja-jp
[10] https://qiita.com/mizuki_takahashi/items/3f77c2e5b6142563ce66
[11] https://zenn.dev/mokomoka/articles/6d281d27aa344e
[12] https://zenn.dev/aidemy/articles/0a71d03fd2a085
[13] https://qiita.com/MakiMatsu/items/d47f351139866025825c
[14] https://codelife.cafe/entry/GAS-Slack-Slash-Command-2
[15] https://dev.classmethod.jp/articles/post-messages-to-slack-channel/
[16] https://slack.com/intl/ja-jp/help/articles/223431088-%E5%88%A9%E7%94%A8%E3%81%97%E3%81%A6%E3%81%84%E3%82%8B%E3%83%84%E3%83%BC%E3%83%AB%E3%82%92-Slack-%E3%81%AB%E9%80%A3%E6%90%BA%E3%81%95%E3%81%9B%E3%82%8B
[17] https://slack.com/intl/ja-jp/help/articles/201402297-%E3%83%81%E3%83%A3%E3%83%B3%E3%83%8D%E3%83%AB%E3%82%92%E4%BD%9C%E6%88%90%E3%81%99%E3%82%8B
[18] https://slack.com/intl/ja-jp/help/articles/360001638428-%E3%83%A1%E3%83%BC%E3%83%AB%E3%81%8B%E3%82%89-Slack-%E3%81%AB%E7%A7%BB%E8%A1%8C%E3%81%99%E3%82%8B
[19] https://slack.com/intl/ja-jp/help/articles/1500003875981-API-%E3%82%92%E4%BD%BF%E3%81%A3%E3%81%A6-Enterprise-Grid-%E3%82%AA%E3%83%BC%E3%82%AC%E3%83%8A%E3%82%A4%E3%82%BC%E3%83%BC%E3%82%B7%E3%83%A7%E3%83%B3%E3%82%92%E7%AE%A1%E7%90%86%E3%81%99%E3%82%8B
[20] https://zenn.dev/barusu/articles/dcfe632515403a
