# IME切替

## VsCode

1. VSCodeの拡張機能から、"Vim" 拡張機能をインストールする

2. VSCodeの設定（settings.json）を開く
 - メニューから「ファイル」→「基本設定」→「設定」を選択
 - 右上の「設定（JSON）を開く」アイコンをクリック

3. settings.jsonに以下の設定を追加する

zenhanをインストールし、そのパスを確認します。
 ```cson
 "vim.autoSwitchInputMethod.enable": true,
 "vim.autoSwitchInputMethod.defaultIM": "1",
 "vim.autoSwitchInputMethod.obtainIMCmd": "/path/to/im-select",
 "vim.autoSwitchInputMethod.switchIMCmd": "/path/to/im-select {im}"
 ```
 - `defaultIM`は、デフォルトのIME（`1`は英数）
 - `obtainIMCmd`と`switchIMCmd`は、OSに応じたIME切り替えコマンドのパス

4. VSCodeを再起動する

## IDEA

IMEの自動切り替えを設定するには、`.ideavimrc`に以下の設定を追加すれば良いでしょう。

```
autocmd InsertLeave * set iminsert=0 imsearch=0
```

この設定を`.ideavimrc`に追加することで、インサートモードから抜けると自動的にIMEが英数モードに切り替わるようになります。

## Pulsar

Pulsar (Atomのコミュニティ版) でVimモードを使用する際に、インサートモードから抜けたときに自動的にIMEを英数モードに切り替える設定は以下の手順で行えます。

1. Pulsarの設定ファイル (`config.cson`) を開く[2]
   - メニューから「Edit」→「Config...」を選択するか、`Ctrl+,`（Macの場合は`Cmd+,`）を押す

2. `config.cson`の`editor`セクションに、以下の設定を追加する
   ```cson
   "vim-mode-plus":
     autoSwitchInputMethod: true
     autoSwitchInputMethodOnInsertLeave: true
   ```

3. ファイルを保存して、Pulsarを再起動する

これで、Pulsar内のVimモードでインサートモードから抜けると、自動的にIMEが英数モードに切り替わるようになります。

ただし、この設定にはいくつか注意点があります。

- Pulsar内のVimモードは、純粋なVimとは完全互換ではない[2]
- IMEの切り替えは、OSやIMEの種類に依存する
- `vim-mode-plus`パッケージがインストールされている必要がある
