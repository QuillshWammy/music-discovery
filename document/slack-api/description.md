# Slack APIの連携

Slack BoltとSpring MVCの連携は、Webhookなしでも可能ですが、その実装には直接的なAPI通信やイベントリスナーを使うことが必要になります。以下はその方法についての概要です。

### 1. Bolt for Javaを使用したSlackアプリの実装

Bolt for Javaは、SlackのAPIと連携するためのライブラリです。これを使って、Slackアプリケーションを作成し、Spring MVCと連携させることができます。

#### 主要ステップ：
- **Bolt for Javaの設定**：Slackアプリケーションを作成し、APIトークンを取得します。これを使ってBoltの設定を行います。
- **Spring BootアプリケーションにBoltを組み込む**：Spring Bootアプリケーションの設定にBoltを組み込み、必要なエンドポイントを作成します。
- **イベントリスナーの実装**：Slackのイベントをリッスンするリスナーを実装し、必要な処理を行います。

### 2. SlackのAPIエンドポイントの設定

Slack APIエンドポイントを設定し、Bolt for Javaと連携することで、SlackのイベントをSpring MVCで処理できます。これには、OAuth 2.0認証やイベントサブスクリプションの設定が含まれます。

#### 主要ステップ：
- **APIエンドポイントの作成**：Spring MVCでSlackのイベントを受け取るためのエンドポイントを作成します。
- **イベントの処理**：受け取ったイベントを適切に処理するためのサービスやコントローラーを実装します。

### 3. サンプルコード

以下は、基本的なセットアップの例です。

```java
// Spring Bootアプリケーションのメインクラス
@SpringBootApplication
public class SlackApp {

    public static void main(String[] args) {
        SpringApplication.run(SlackApp.class, args);
    }

    @Bean
    public App initSlackApp() {
        AppConfig config = new AppConfig();
        config.setSingleTeamBotToken("YOUR_BOT_TOKEN");
        return new App(config);
    }
}

// Slackイベントを処理するコントローラー
@RestController
@RequestMapping("/slack/events")
public class SlackEventController {

    @Autowired
    private App slackApp;

    @PostMapping
    public ResponseEntity<String> handleEvent(@RequestBody String payload) {
        return slackApp.run(payload).toEntity();
    }
}
```

### まとめ
Webhookなしでも、Slack Bolt for JavaとSpring MVCを使って連携することは可能です。ただし、この場合は直接APIリクエストやイベントリスナーを使って処理を実装する必要があります。設定や実装が多少複雑になることがあるため、詳細なドキュメントやサンプルコードを参考にしながら進めると良いでしょう。
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
