# 概要

## 1. Microsoft Graph APIを使用してOutlookの予定表にアクセス

Microsoft Graph APIを使うと、Outlookの予定表の読み書きができます。以下のようなAPIを使用します。

- イベントの作成: POST /me/events
- イベントの更新: PATCH /me/events/{id}
- イベントの取得: GET /me/events/{id}

イベントの作成時に、場所、開始時刻、終了時刻、出席者などを指定できます

## 2. アクセストークンの取得

APIを呼び出すには、Azure ADに登録したアプリケーションのアクセストークンが必要です。以下の手順でアクセストークンを取得します。

1. Azure ADにアプリケーションを登録
2. クライアントIDとクライアントシークレットを取得
3. 必要なアクセス許可(Calendars.ReadWrite)を設定
4. OAuth 2.0の認証フローを使ってアクセストークンを取得

## 3. SPAからAPIを呼び出す

取得したアクセストークンを使って、SPAからMicrosoft Graph APIを呼び出します。JavaScriptのHTTPクライアントを使ってAPIリクエストを送信します。

会議の招待を送るには、イベントの作成APIを呼び出し、出席者のメールアドレスを`attendees`プロパティに指定します。`attendees`は以下のような配列で指定します。

```json
"attendees": [
  {
    "emailAddress": {
      "address": "user1@example.com",
      "name": "User 1"
    },
    "type": "required"
  },
  {
    "emailAddress": {
      "address": "user2@example.com",
      "name": "User 2"
    },
    "type": "required"
  }
]
```
