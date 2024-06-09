# Azure ADへのアプリケーション登録

Azure ADにアプリケーションを登録するには、以下のような基本的なフローが必要です。

1. Azure ADテナントを用意する
   - 既存のAzure ADテナントを使うか、新規に作成する[1][3]

2. Azure ADポータルでアプリケーションを登録する
   - アプリケーションの名前、サポートするアカウントの種類、リダイレクトURLなどを指定[1][8][10]
   - アプリケーションID(クライアントID)とテナントIDを取得[8][14]

3. 認証方式を選択する
   - 公開クライアント(パスワード認証、証明書認証)か機密クライアント(クライアントシークレット)かを選択[13][14]
   - 必要に応じて、クライアントシークレットや証明書を生成[8][10]

4. アプリケーションに必要なAPIアクセス許可を設定する
   - Microsoft GraphやAzure ADなどのAPIを利用する場合は、適切なアクセス許可(scope)を設定[8][10]

5. ユーザーの同意を得る
   - 管理者の同意またはユーザーの同意が必要。アプリケーションの種類によって異なる[10]

ローカル環境で開発する場合も、基本的には同じフローでアプリケーションを登録できます。

- ローカル開発用とプロダクション用で別のアプリ登録を作成するのが一般的[1]
- ローカルのURLもリダイレクトURLに追加しておく。localhostなどを指定可能[1]
- ローカル開発用の専用のAzure ADセキュリティグループを作っておくと便利[2][9]
- 環境変数などを使って、ローカルとプロダクションで異なる設定を切り替えられる[9][11]

以上のように、ローカル環境でもAzure ADにアプリケーションを登録し、Azure SDKなどから認証・認可の機能を利用することができます。
開発者は自身のAzureアカウントでサインインすることで、ローカルのアプリからAzureリソースにアクセスできるようになります。

Citations:
[1] https://stackoverflow.com/questions/52690775/azure-active-directory-application-registration-for-localhost-and-production
[2] https://docs.tibco.com/pub/bwplugindynamicscrm/6.7.0/doc/html/GUID-CA81FDE1-E0B7-46E2-B4E1-15647AD86209.html
[3] https://www.connecting-software.com/ja/blog/how-to-create-an-azure-ad-app-registration-step-by-step-tutorial/
[4] https://jpazureid.github.io/blog/azure-active-directory/users-can-register-applications/
[5] https://jpazureid.github.io/blog/azure-active-directory/oauth2-application-resource-and-api-permissions/
[6] https://qiita.com/ryuichi-f/items/392fae9ed74eeba5c397
[7] https://learn.microsoft.com/ja-jp/dotnet/azure/sdk/authentication/local-development-dev-accounts
[8] https://learn.microsoft.com/ja-jp/entra/identity-platform/quickstart-register-app
[9] https://learn.microsoft.com/ja-jp/dotnet/azure/sdk/authentication/local-development-service-principal
[10] https://learn.microsoft.com/ja-jp/graph/auth-register-app-v2
[11] https://qiita.com/saurus12/items/711124d0eff198f911c1
[12] https://learn.microsoft.com/ja-jp/azure/developer/python/sdk/authentication-local-development-dev-accounts
[13] https://learn.microsoft.com/ja-jp/power-apps/developer/data-platform/walkthrough-register-app-azure-active-directory
[14] https://learn.microsoft.com/ja-jp/azure/healthcare-apis/register-application
[15] https://learn.microsoft.com/ja-jp/skype-sdk/trusted-application-api/docs/registrationinazureactivedirectory

## reference
- [Microsoft Graph Toolkit で使用するMicrosoft Entra アプリを作成する](https://learn.microsoft.com/ja-jp/graph/toolkit/get-started/add-aad-app-registration)
- [Azure AD に登録できる 「アプリ」と「リソース」、「API 権限」を理解する](https://jpazureid.github.io/blog/azure-active-directory/oauth2-application-resource-and-api-permissions/)
