# チャンネルへの招待

Slackのチャンネルにユーザーを招待するためには、主に以下のものが必要です。

1. 招待するチャンネルのチャンネルID (例: C1234567890) [2][5][15]
- チャンネルIDは、チャンネル名の代わりに使用する一意の識別子
- APIリクエストでチャンネルを指定する際に必要

2. 招待するユーザーのユーザーID (例: U0123456789) [3][5][15]
- ユーザーIDは、ユーザー名やメールアドレスの代わりに使用する一意の識別子
- ユーザー名やメールアドレスからユーザーIDを取得するには、`users.lookupByEmail`などのAPIメソッドを使用する[14][18]

3. 適切なスコープ権限を持ったSlack APIトークン [2][5][15]
- `channels:manage` - パブリックチャンネルのメンバー管理に必要
- `groups:write` - プライベートチャンネルのメンバー管理に必要
- ボットトークンではなく、ユーザートークンを使用する必要がある[3]

4. `conversations.invite`メソッドを呼び出すコード[1][2][5][15][19]
- 必要なパラメータを指定してPOSTリクエストを送信する
- レスポンスから結果を確認する

以上を揃えた上で、`conversations.invite`メソッドを呼び出すことで、指定したチャンネルに指定したユーザーを招待することができます。
ユーザーIDの取得や適切なトークンの発行など、事前準備が必要な点に注意が必要です。

Citations:
[1] https://api.slack.com/reference/functions/invite_user_to_channel
[2] https://api.slack.com/apis/conversations-api
[3] https://stackoverflow.com/questions/69308778/slack-api-add-a-user-to-a-channel
[4] https://api.slack.com/methods/users.identity
[5] https://api.slack.com/methods/conversations.invite
[6] https://stackoverflow.com/questions/36491784/how-to-read-a-slack-user-id-from-response-and-get-the-username
[7] https://hexdocs.pm/slack_web/Slack.Web.Conversations-function-invite.html
[8] https://api.slack.com/apis/connect
[9] https://github.com/slackapi/node-slack-sdk/issues/73
[10] https://api.slack.com/methods/admin.users.invite
[11] https://stackoverflow.com/questions/54466903/how-can-a-slack-bot-find-out-its-own-id
[12] https://api.slack.com/methods/admin.conversations.invite
[13] https://api.slack.com/types/user
[14] https://qiita.com/samiii/items/102727d77758c6a0c906
[15] https://dev.classmethod.jp/articles/google-apps-script-slack-api-launch-01/
[16] https://api.slack.com/methods/users.profile.get
[17] https://api.slack.com/admins/inviting
[18] https://qiita.com/mu5dvlp/items/18b72d69a3aabac418ff
[19] https://github.com/slack-ruby/slack-api-ref/blob/master/methods/conversations/conversations.invite.json
[20] https://pipedream.com/apps/slack/actions/invite-user-to-channel
