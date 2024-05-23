# カレンダーのコンポーネント

Vue3とTypeScriptを使用して親コンポーネントから子コンポーネントにデータを渡す場合、以下の方法があります。

1. Props を使用する方法
親コンポーネントから子コンポーネントにデータを渡すには、Propsを使用するのが一般的です。[1][8][11][12]

親コンポーネント（Parent.vue）:
```
<template>
  <ChildComponent :title="title" :content="content" />
</template>

<script setup lang="ts">
import { ref } from 'vue';
import ChildComponent from './ChildComponent.vue';

const title = ref('Hello');
const content = ref('This is a message from parent.');
</script>
```

子コンポーネント（ChildComponent.vue）:
```
<template>
  <h2>{{ title }}</h2>
  <p>{{ content }}</p>
</template>

<script setup lang="ts">
defineProps<{
  title: string;
  content: string;
}>();
</script>
```

2. provide/inject を使用する方法
親コンポーネントから孫コンポーネントなど、より深い階層のコンポーネントにデータを渡す場合は、provide/injectを使用できます。[7][9][13][14][17][20]

親コンポーネント（Parent.vue）:
```
<template>
  <ChildComponent />
</template>

<script setup lang="ts">
import { provide } from 'vue';
import ChildComponent from './ChildComponent.vue';

provide('message', 'Hello from parent');
</script>
```

子コンポーネント（ChildComponent.vue）:
```
<template>
  <p>{{ message }}</p>
</template>

<script setup lang="ts">
import { inject } from 'vue';

const message = inject<string>('message');
</script>
```

また、TypeScriptを使用する際の注意点として、以下があります。

- Props の型定義には、definePropsを使用し、ジェネリクスで型を指定する[10][15][16]
- provide/injectでキーを指定する際は、シンボルを使用したInjectionKeyを使うとよい[7][9][14][17]
- emitの型定義にはdefineEmitsを使用する[4][19]

以上のように、Vue3とTypeScriptを使用することで、型の安全性を保ちながら親子コンポーネント間でデータをやり取りすることができます。コンポーネントの責務や構成に応じて適切な方法を選択してください。

情報源
[1] How to pass data from parent to child components in Vue.js? https://compiletab.com/pass-data-parent-child-components-vuejs/
[2] A Guide to Passing Data from Parent to Child Component in Vue.js https://salkobalic.com/guide-to-passing-data-from-parent-to-child-component-in-vuejs
[3] How to use props to pass data to child components in Vue 3 https://blog.logrocket.com/use-props-pass-data-child-components-vue-3/
[4] vue.js - How to emit events with a strictly typed payload? https://stackoverflow.com/questions/67094213/how-to-emit-events-with-a-strictly-typed-payload-vue-3-composition-api-type
[5] Composition API: Dependency Injection - Vue.js https://vuejs.org/api/composition-api-dependency-injection
[6] How to Call a Parent Method from a Child Component in Vue 3 https://www.geeksforgeeks.org/how-to-call-a-parent-method-from-a-child-component-in-vue-3/
[7] ProvideとInjectをComposition APIとTypeScriptで解説 - TekRog https://tekrog.com/use-provide-and-inject-with-composition-api-and-typescript
[8] Techniques for Sharing Data between Vue.js Components https://vueschool.io/articles/vuejs-tutorials/techniques-for-sharing-data-between-vue-js-components/
[9] Composition API で TypeScript を使用する - Vue.js https://ja.vuejs.org/guide/typescript/composition-api
[10] 【Vue.js 3】TypeScriptで始めるVue3 - Zenn https://zenn.dev/knm/articles/09154e02dbe507
[11] Vue.js Patterns: Using Vue.js 3 Composition API for Reactive Parent ... https://lirantal.com/blog/vuejs-patterns-using-vuejs-3-composition-api-for-reactive-parent-to-child-communication/
[12] Components Basics - Vue.js https://vuejs.org/guide/essentials/component-basics
[13] Provide/Injectの使い方/コードサンプル/注意点【備忘録】 #Vue.js https://qiita.com/ta-hiro/items/d22ac3f597175e3d0c17
[14] Composition API: 依存関係の注入 - Vue.js https://ja.vuejs.org/api/composition-api-dependency-injection
[15] TypeScript with Composition API - Vue.js https://vuejs.org/guide/typescript/composition-api
[16] Vue3 Props Validation #TypeScript - Qiita https://qiita.com/kawabata324/items/5c8d3a789e080a426285
[17] Composition APIによる Provide / Inject を整理 #TypeScript - Qiita https://qiita.com/fujimmm331/items/5baa1e7b648ea8a71b03
[18] TypeScriptのサポートが強化されたVue3.3の新機能とは？    Propsと ... https://codezine.jp/article/detail/18306
[19] Working with emits in Vue 3 + Typescript - DEV Community https://dev.to/cn-2k/working-with-emits-in-vue-3-typescript-2a1j
[20] Vue3 - コンポーネント連携（Provide/Inject・Pinia） - Zenn https://zenn.dev/aono/articles/cd1eed66b39eff

PrimeVueを使って現在の月を表示するカレンダーを作成し、各日付にリンクを設定して日付ページに遷移する方法は以下の通りです。

```vue
<template>
  <div>
    <Calendar 
      v-model="selectedDate" 
      :inline="true" 
      :manualInput="false" 
      @date-select="onDateSelect" 
      class="custom-calendar" 
    />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import Calendar from 'primevue/calendar';
import { useRouter } from 'vue-router';

const selectedDate = ref<Date>(new Date());
const router = useRouter();

interface DateSelectEvent {
  year: number;
  month: number;
  day: number;
}

const onDateSelect = (event: DateSelectEvent) => {
  const { year, month, day } = event;
  router.push(`/date/${year}/${month + 1}/${day}`);
};
</script>

<style scoped>
.custom-calendar {
  font-size: 1.5rem;
}
.custom-calendar .p-datepicker table {
  width: 100%;
  font-size: 1.2rem;
}
.custom-calendar .p-datepicker-header {
  font-size: 1.5rem;
}
</style>
```

ポイントは以下の通りです。

- `Calendar`コンポーネントの`inline`プロパティを`true`に設定することで、インラインモードでカレンダーを表示できます。[1]
- `manualInput`プロパティを`false`に設定することで、ユーザーが直接日付を入力できないようにします。[1]
- `v-model`で選択された日付を`selectedDate`にバインドします。初期値は`new Date()`で現在の日付を設定します。
- `date-select`イベントをハンドリングする`onDateSelect`メソッドを定義します。[1]
- `onDateSelect`では、選択された日付の年、月、日を取得し、`router.push`で該当の日付ページのURLにリダイレクトします。

あとは、Vue Routerを使って`/date/:year/:month/:day`のようなURLに対応するルートを定義し、日付ページのコンポーネントを作成します。

```js
// router.js
import { createRouter, createWebHistory } from 'vue-router';
import DatePage from './DatePage.vue';

const routes = [
  {
    path: '/date/:year/:month/:day',
    name: 'date',
    component: DatePage,
    props: true,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
```

```vue
<!-- DatePage.vue -->
<template>
  <div>
    <h1>{{ year }}年{{ month }}月{{ day }}日</h1>
    <!-- 日付ページの内容 -->
  </div>
</template>

<script setup>
const props = defineProps({
  year: Number,
  month: Number,
  day: Number,
});
</script>
```

以上で、PrimeVueのCalendarコンポーネントを使って現在の月を表示し、各日付にリンクを設定して日付ページに遷移する機能が実装できます。

カレンダーのスタイルは、PrimeVueのテーマや独自のCSSを使ってカスタマイズできます。[7][14]

参考になれば幸いです。

TypeScriptを使用して、外部ファイルからコンポーネントをインポートする方法を含めて、コードを修正します。

まず、`Calendar.vue`ファイルを作成し、以下のようにカレンダーコンポーネントを定義します。

```vue
<!-- Calendar.vue -->
<template>
  <div>
    <Calendar v-model="selectedDate" :inline="true" :manualInput="false" 
      @date-select="onDateSelect" />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import Calendar from 'primevue/calendar';
import { useRouter } from 'vue-router';

const props = defineProps<{
  modelValue: Date;
}>();

const emit = defineEmits<{
  (e: 'update:modelValue', value: Date): void;
}>();

const selectedDate = ref(props.modelValue);

const router = useRouter();

const onDateSelect = (event: { year: number; month: number; day: number }) => {
  const { year, month, day } = event;
  emit('update:modelValue', new Date(year, month, day));
  router.push(`/date/${year}/${month + 1}/${day}`);
};
</script>
```

変更点は以下の通りです。

- `selectedDate`をpropsとして受け取り、`v-model`で双方向バインディングを実現。
- `defineEmits`を使って`update:modelValue`イベントを定義し、日付選択時に親コンポーネントに通知。
- `useRouter`を使ってルーターインスタンスを取得。
- `onDateSelect`の引数の型を明示的に定義。

次に、このカレンダーコンポーネントを使用する親コンポーネントを定義します。

```vue
<!-- App.vue -->
<template>
  <div>
    <h1>Calendar Example</h1>
    <Calendar v-model="selectedDate" />
    <p>Selected Date: {{ selectedDate }}</p>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import Calendar from './Calendar.vue';

const selectedDate = ref(new Date());
</script>
```

これで、`Calendar`コンポーネントを外部ファイルから読み込み、`v-model`を使って選択された日付を同期できるようになりました。

TypeScriptの型推論により、`selectedDate`は自動的に`Date`型になります。
また、`onDateSelect`の引数の型を明示的に定義することで、TypeScriptによる型チェックが効くようになります。

コンポーネントを外部ファイルに分割することで、コードの可読性と再利用性が向上します。
TypeScriptを使うことで、型の安全性も確保できます。

参考になれば幸いです。

Citations:
[1] https://qiita.com/gone0021/items/e57b191975c688f6390d
[2] https://github.com/primefaces/primevue/issues/4475
[3] https://ej2.syncfusion.com/vue/documentation/calendar/vue3-getting-started
[4] https://www.youtube.com/watch?v=UE38VFrKlmo
[5] https://www.youtube.com/watch?v=9zCP8CcaomI
[6] https://www.npmjs.com/package/vue-calendar-3
[7] https://github.com/Teamlinker/TLCalendar
[8] https://primevue.org/calendar/
[9] https://fullcalendar.io/docs/vue
[10] https://github.com/vuejs/core/discussions/8137
[11] https://github.com/fullcalendar/fullcalendar-vue
[12] https://stackoverflow.com/questions/77796744/vue-3-how-do-i-wrap-a-component-in-vue-with-typescript
[13] https://www.reddit.com/r/vuejs/comments/xfh8z9/importing_components_inside_another_component/
[14] https://github.com/primefaces/primevue/issues/2703
[15] https://forum.primefaces.org/viewtopic.php?t=53812
[16] https://primevue.org
[17] https://vuejs.org/guide/essentials/component-basics
[18] https://vuejs.org/guide/components/registration
[19] https://vuejs.org/guide/extras/web-components
[20] https://stackoverflow.com/questions/72909806/vue3-import-props-from-another-file
