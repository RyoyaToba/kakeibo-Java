<template>
  <div>
    <h1>ホーム画面</h1>

    <div v-if="pending">読み込み中...</div>
    <div v-else-if="error">エラー: {{ error.message }}</div>
    <div v-else>
      <pre>{{ viewModel }}</pre> <!-- JSONを整形表示 -->
    </div>
  </div>
</template>

<script setup lang="ts">

declare const useFetch: any;
// useFetchでデータを取得
const { data: viewModel, pending, error } = await useFetch('http://localhost:8081/api/home');
console.log("viewModel:", viewModel)
</script>
