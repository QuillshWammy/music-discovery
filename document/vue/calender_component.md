# カレンダーのコンポーネント

PrimeVueを使って現在の月を表示するカレンダーを作成し、各日付にリンクを設定して日付ページに遷移する方法は以下の通りです。

```vue
<template>
  <div>
    <Calendar v-model="selectedDate" :inline="true" :manualInput="false" 
      @date-select="onDateSelect" class="custom-calendar" />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import Calendar from 'primevue/calendar';
import router from './router';

const selectedDate = ref(new Date());

const onDateSelect = (event) => {
  const year = event.year;
  const month = event.month + 1;
  const day = event.day;
  router.push(`/date/${year}/${month}/${day}`);
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
