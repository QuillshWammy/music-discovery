<script setup lang="ts">
import { ref, computed } from 'vue'

const form = ref({
  ticker: '',
  name: '',
  sharesIssued: ''
})

const errors = ref({
  ticker: '',
  name: '',
  sharesIssued: ''
})

const isValid = computed(() => {
  return Object.values(errors.value).every((error) => error === '')
})

const validateTicker = (value: string) => {
  if (!/^\d{4}$/.test(value)) {
    errors.value.ticker = '4桁の半角数字を入力してください'
  } else {
    errors.value.ticker = ''
  }
}

const validateName = (value: string) => {
  if (!/^[^ -~｡-ﾟ]+$/.test(value)) {
    errors.value.name = '全角文字で入力してください'
  } else if (value.length > 100) {
    errors.value.name = '100文字以内で入力してください'
  } else {
    errors.value.name = ''
  }
}

const validateSharesIssued = (value: string) => {
  if (!/^\d+$/.test(value)) {
    errors.value.sharesIssued = '半角数字を入力してください'
  } else {
    errors.value.sharesIssued = ''
  }
}

const onSubmit = () => {
  validateTicker(form.value.ticker)
  validateName(form.value.name)
  validateSharesIssued(form.value.sharesIssued)

  if (isValid.value) {
    console.log('submit')
    // フォーム送信処理
  } else {
    console.log('validation farled')
    // エラーメッセージを表示してフォームの再入力を促す
    alert('入力内容にエラーがあります。再度ご確認ください。')
  }
}
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div>
      <label for="ticker">Ticker:</label>
      <input
        id="ticker"
        v-model="form.ticker"
        type="text"
        name="ticker"
        @blur="validateTicker(form.ticker)"
      />
      <span class="error">{{ errors.ticker }}</span>
    </div>
    <div>
      <label for="name">Name:</label>
      <input
        id="name"
        v-model="form.name"
        type="text"
        name="name"
        @blur="validateName(form.name)"
      />
      <span class="error">{{ errors.name }}</span>
    </div>
    <div>
      <label for="sharesIssued">Shares Issued:</label>
      <input
        id="sharesIssued"
        v-model="form.sharesIssued"
        type="number"
        name="sharesIssued"
        @blur="validateSharesIssued(form.sharesIssued)"
      />
      <span class="error">{{ errors.sharesIssued }}</span>
    </div>
    <button :disabled="!isValid">Submit</button>
  </form>
</template>
