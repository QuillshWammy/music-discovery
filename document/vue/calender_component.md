# カレンダーのコンポーネント

PrimeVueを使って現在の月を表示するカレンダーを作成し、各日付にリンクを設定して日付ページに遷移する方法は以下の通りです。

```vue
<template>
  <div>
    <Calendar v-model="selectedDate" :inline="true" :manualInput="false" 
      @date-select="onDateSelect" />
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

Citations:
[1] https://primevue.org/calendar/
[2] https://github.com/primefaces/primevue/issues/3624
[3] https://tomosterlund.github.io/qalendar/
[4] https://github.com/fullcalendar/fullcalendar-vue
[5] https://github.com/icai/vue3-calendar
[6] https://github.com/primefaces/primevue/issues/4393
[7] https://www.npmjs.com/package/vue-calendar-3
[8] https://ej2.syncfusion.com/vue/documentation/calendar/getting-started-vue3
[9] https://v2.vcalendar.io/installation.html
[10] https://www.reddit.com/r/vuejs/comments/t7kg9o/best_calendar_component_for_vuejs_3/
[11] https://fullcalendar.io/docs/vue
[12] https://madewithvuejs.com/primevue
[13] https://github.com/ankurk91/vue-flatpickr-component
[14] https://bootstrap-vue.org/docs/components/calendar
[15] https://tailwind.primevue.org/calendar/
[16] https://stackoverflow.com/questions/76723651/component-vdatepicker-from-vcalendor-not-showing-date-in-v-textfield-vuejs
[17] https://github.com/primefaces/primevue/issues/3827
[18] https://stackoverflow.com/questions/69181439/how-to-change-v-model-format-in-primevue-calendar-selection
[19] https://stackoverflow.com/questions/49619999/how-can-i-change-format-datetime-to-date-in-vue-component
[20] https://forum.primefaces.org/viewtopic.php?t=77631
