# TypeScript

## importの際の型宣言について

TypeScriptでインポートする際に型を定義しないと、問題が発生する。型を明示的に定義することで、コンパイラがコードの型安全性をチェックできるため、潜在的な型エラーを防ぐことが可能。

【上のケース】のように、`vue-router`から`useRouter`関数をインポートする場合、`Router`型をインポートしている。これにより、`useRouter`の返り値が`Router`型であることが保証されている。

よって、`router.push`などのメソッドを安全に使用できる仕組み。

一方、【下のケース】のように型を定義しない場合は`router`変数の型はコンパイラによって推論され、`any`型になる可能性がある。 `any.push`は定義されていない。

> そもそも`router.push`について、[公式ドキュメント](https://nextjs.org/docs/pages/api-reference/functions/use-router#router-object)にはuseRouterもしくはwithRouterのメソッドと記されている。