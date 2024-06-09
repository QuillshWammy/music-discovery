# 新しいイベントの作成

はい、Microsoft Graph API を使用して、イベントの出席者、開始時刻、終了時刻、詳細を指定してイベントを作成し、出席依頼を送信することができます。以下にそのフローを説明します。

## イベントを作成し出席依頼を送るフロー

1. アクセストークンを取得する
   - Azure AD にアプリケーションを登録し、必要なアクセス許可 (Calendars.ReadWrite) を設定する[5][17]
   - OAuth 2.0 の認証フローを使ってアクセストークンを取得する[3][16]

2. イベントを作成する
   - POST /me/calendar/events エンドポイントを呼び出す[18][19]
   - 要求本文に以下のようなイベントの詳細を JSON 形式で指定する[18][20]
     - subject: イベントの件名
     - body: イベントの詳細
     - start: 開始日時
     - end: 終了日時
     - location: 場所
     - attendees: 出席者のメールアドレスの配列

3. 出席依頼を送信する
   - イベント作成時の attendees プロパティに出席者のメールアドレスを指定することで、自動的に出席依頼が送信される[7][13]
   - 各出席者の予定表にもイベントが作成される

例えば、以下のような JSON をリクエスト本文に指定してイベントを作成すると、出席依頼も送信されます。

```json
{
  "subject": "Let's go for lunch",
  "body": {
    "contentType": "HTML",
    "content": "Does late morning work for you?"
  },
  "start": {
    "dateTime": "2019-06-16T12:00:00",
    "timeZone": "Pacific Standard Time"
  },
  "end": {
    "dateTime": "2019-06-16T14:00:00",
    "timeZone": "Pacific Standard Time"
  },
  "location":{
    "displayName":"Harry's Bar"
  },
  "attendees": [
    {
      "emailAddress": {
        "address":"AlexW@contoso.com"
      }
    }
  ]
}
```

このように、Microsoft Graph API を使えば、SPA からイベントを作成し、出席依頼を送信するフローを実装できます。アクセストークンの取得からイベント作成までを適切に行ってください。

Citations:
[1] https://stackoverflow.com/questions/72460771/create-a-calendar-event-for-a-whole-team-participants-using-the-graph-api
[2] https://github.com/microsoftgraph/microsoft-graph-docs-contrib/blob/main/api-reference/beta/api/participant-invite.md
[3] https://learn.microsoft.com/ja-jp/graph/tutorials/javascript
[4] https://stackoverflow.com/questions/58216580/how-to-sent-invitation-email-using-microsoft-graph-api
[5] https://learn.microsoft.com/ja-jp/graph/api/message-send?view=graph-rest-1.0
[6] https://github.com/microsoftgraph/msgraph-sdk-javascript
[7] https://stackoverflow.com/questions/66765761/how-to-create-an-event-with-extensions-for-attendees-in-outlook
[8] https://www.youtube.com/watch?v=nIYP2fCtnws
[9] https://learn.microsoft.com/ja-jp/graph/api/user-sendmail?view=graph-rest-1.0
[10] https://learn.microsoft.com/ja-jp/graph/call-api
[11] https://learn.microsoft.com/ja-jp/graph/api/participant-invite?view=graph-rest-1.0
[12] https://learn.microsoft.com/ja-jp/graph/api/invitation-post?view=graph-rest-1.0
[13] https://www.independentsoft.de/graph/tutorial/sendmeetingrequest.html
[14] https://stackoverflow.com/questions/51145245/multipart-http-request-with-microsoft-graph-javascript-sdk
[15] https://learn.microsoft.com/en-us/answers/questions/1159863/set-attendees-to-accepted-when-creating-a-calendar
[16] https://qiita.com/tetsuyahirose/items/55c6d90191643ced9b46
[17] https://learn.microsoft.com/ja-jp/graph/outlook-create-event-in-shared-delegated-calendar
[18] https://learn.microsoft.com/ja-jp/graph/api/calendar-post-events?view=graph-rest-1.0
[19] https://learn.microsoft.com/ja-jp/graph/api/user-post-events?view=graph-rest-1.0
[20] https://learn.microsoft.com/ja-jp/graph/api/group-post-events?view=graph-rest-1.0
