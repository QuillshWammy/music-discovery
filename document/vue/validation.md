# バリデーション

## 値のバリデーションをしたいとき

```ts
<template>
  <form @submit.prevent="onSubmit">
    <div>
      <label for="ticker">Ticker:</label>
      <input id="ticker" v-model="form.ticker" type="text" name="ticker" />
      <span class="error">{{ errors.ticker }}</span>
    </div>
    <div>
      <label for="name">Name:</label>
      <input id="name" v-model="form.name" type="text" name="name" />
      <span class="error">{{ errors.name }}</span>
    </div>
    <div>
      <label for="sharesIssued">Shares Issued:</label>
      <input id="sharesIssued" v-model="form.sharesIssued" type="number" name="sharesIssued" />
      <span class="error">{{ errors.sharesIssued }}</span>
    </div>
    <button :disabled="!isValid">Submit</button>
  </form>
</template>

<script lang="ts">
import { defineComponent, reactive, computed } from 'vue'

export default defineComponent({
  setup() {
    const form = reactive({
      ticker: '',
      name: '',
      sharesIssued: '',
    })

    const errors = reactive({
      ticker: '',
      name: '',
      sharesIssued: '',
    })

    const isValid = computed(() => {
      return Object.values(errors).every(error => error === '')
    })

    const validateTicker = (value: string) => {
      if (!/^\d{4}$/.test(value)) {
        errors.ticker = '4桁の半角数字を入力してください'
      } else {
        errors.ticker = ''
      }
    }

    const validateName = (value: string) => {
      if (!/^[^ -~｡-ﾟ]+$/.test(value)) {
        errors.name = '全角文字で入力してください'
      } else if (value.length > 100) {
        errors.name = '100文字以内で入力してください'
      } else {
        errors.name = ''
      }
    }

    const validateSharesIssued = (value: string) => {
      const num = Number(value)
      if (num < 1 || num > 1000000000) {
        errors.sharesIssued = '1以上10億以下の半角数字を入力してください'
      } else {
        errors.sharesIssued = ''
      }
    }

    const onSubmit = () => {
      validateTicker(form.ticker)
      validateName(form.name)
      validateSharesIssued(form.sharesIssued)

      if (isValid.value) {
        alert(JSON.stringify(form))
      }
    }

    return {
      form,
      errors,
      isValid,
      onSubmit,
    }
  },
})
</script>
```
