# Music Discovery

## Environment

- wsl2
- PostgreSQL
- Spring Boot
- Vue.js

ホームディレクトリへのパスを使用して、誰でも開けるリンクを作成することは可能です。ただし、VSCodeのMarkdown自体は`%HOMEPATH%`のような環境変数を解釈しないため、いくつかの工夫が必要です。

## 方法1: 環境変数を利用したリンク（バッシュスクリプトを使う）

VSCodeのタスクを使用してホームディレクトリへのリンクを作成し、そのタスクをMarkdownから呼び出します。

1. `.vscode/tasks.json`ファイルを作成し、以下の内容を追加します。

```json
{
  "version": "2.0.0",
  "tasks": [
    {
      "label": "Open Home Folder in Explorer",
      "type": "shell",
      "command": "explorer.exe",
      "args": [
        "%HOMEPATH%"
      ],
      "problemMatcher": []
    }
  ]
}
```

2. Markdownファイルからタスクを呼び出すリンクを作成します。

```markdown
[ホームフォルダをエクスプローラーで開く](command:workbench.action.tasks.runTask?Open%20Home%20Folder%20in%20Explorer)
```

これにより、リンクをクリックするとホームディレクトリがエクスプローラーで開かれます。

## 方法2: VSCode拡張機能を使う

より柔軟な方法として、VSCodeの拡張機能を使用することも考えられます。例えば、「Markdown Preview Enhanced」などの拡張機能を使うと、カスタムスクリプトを実行できる可能性があります。

### 新規ウィンドウで開く

新規ウィンドウで開くには、エクスプローラーコマンドに追加の引数を渡す必要がありますが、標準の`explorer.exe`コマンドには直接のオプションがありません。代わりに、新しいプロセスとしてエクスプローラーを起動することで実現できます。

1. `.vscode/tasks.json`を以下のように編集します。

```json
{
  "version": "2.0.0",
  "tasks": [
    {
      "label": "Open Home Folder in New Explorer Window",
      "type": "shell",
      "command": "cmd.exe",
      "args": [
        "/c",
        "start",
        "explorer.exe",
        "%HOMEPATH%"
      ],
      "problemMatcher": []
    }
  ]
}
```

2. Markdownファイルからタスクを呼び出すリンクを作成します。

```markdown
[新しいウィンドウでホームフォルダを開く](command:workbench.action.tasks.runTask?Open%20Home%20Folder%20in%20New%20Explorer%20Window)
```

この方法を使えば、ホームフォルダを新しいエクスプローラーウィンドウで開くことができます。

これらの方法を使用すると、VSCodeのMarkdown内で誰でもホームフォルダを開くことができるリンクを作成でき、新しいウィンドウで開くことも可能です。